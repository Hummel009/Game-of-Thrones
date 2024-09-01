package got.common;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.common.brotherhood.*;
import got.common.command.GOTCommandAdminHideMap;
import got.common.database.*;
import got.common.entity.dragon.GOTEntityDragon;
import got.common.entity.essos.golden.GOTEntityGoldenCompanyMan;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.*;
import got.common.item.other.GOTItemArmor;
import got.common.network.*;
import got.common.quest.GOTMiniQuest;
import got.common.quest.GOTMiniQuestEvent;
import got.common.quest.GOTMiniQuestSelector;
import got.common.quest.GOTMiniQuestWelcome;
import got.common.util.GOTCrashHandler;
import got.common.util.GOTLog;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Stream;

public class GOTPlayerData {
	private static final int TICKS_UNTIL_FT_MAX = 200;

	private final Collection<GOTFaction> bountiesPlaced = new ArrayList<>();
	private final Collection<CWPSharedKey> cwpSharedHidden = new HashSet<>();
	private final Collection<CWPSharedKey> cwpSharedUnlocked = new HashSet<>();
	private final Collection<GOTBrotherhoodInvite> brotherhoodInvites = new ArrayList<>();

	private final List<GOTMiniQuest> miniQuests = new ArrayList<>();
	private final List<GOTMiniQuest> miniQuestsCompleted = new ArrayList<>();
	private final List<GOTAchievement> achievements = new ArrayList<>();
	private final List<GOTCustomWaypoint> customWaypoints = new ArrayList<>();
	private final List<GOTCustomWaypoint> customWaypointsShared = new ArrayList<>();
	private final List<GOTBrotherhoodClient> brotherhoodInvitesClient = new ArrayList<>();
	private final List<GOTBrotherhoodClient> brotherhoodsClient = new ArrayList<>();
	private final List<UUID> brotherhoodIDs = new ArrayList<>();

	private final Map<GOTDimension.DimensionRegion, GOTFaction> prevRegionFactions = new EnumMap<>(GOTDimension.DimensionRegion.class);
	private final Map<GOTFaction, Float> reputations = new EnumMap<>(GOTFaction.class);
	private final Map<GOTFaction, GOTFactionData> factionDataMap = new EnumMap<>(GOTFaction.class);
	private final Map<GOTGuiMessageTypes, Boolean> sentMessageTypes = new EnumMap<>(GOTGuiMessageTypes.class);
	private final Map<GOTWaypoint, Integer> wpUseCounts = new EnumMap<>(GOTWaypoint.class);
	private final Map<CWPSharedKey, Integer> cwpSharedUseCounts = new HashMap<>();
	private final Map<Integer, Integer> cwpUseCounts = new HashMap<>();

	private final Set<GOTFaction> takenReputationRewards = EnumSet.noneOf(GOTFaction.class);
	private final Set<GOTWaypoint.Region> unlockedFTRegions = EnumSet.noneOf(GOTWaypoint.Region.class);

	private final GOTPlayerQuestData questData = new GOTPlayerQuestData(this);
	private final UUID playerUUID;

	private GOTCapes cape;
	private GOTFaction betrayedOathFaction;
	private GOTFaction oathFaction;
	private GOTFaction viewingFaction;
	private GOTShields shield;
	private GOTWaypoint lastWaypoint;

	private GOTAbstractWaypoint targetFTWaypoint;
	private GOTBiome lastBiome;

	private ChunkCoordinates deathPoint;
	private GOTTitle.PlayerTitle playerTitle;
	private UUID chatBoundBrotherhoodID;
	private UUID trackingMiniQuestID;
	private UUID uuidToMount;

	private boolean adminHideMap;
	private boolean askedForJaqen;
	private boolean checkedMenu;
	private boolean conquestKills = true;
	private boolean feminineRanks;
	private boolean friendlyFire;
	private boolean hideReputation;
	private boolean hideOnMap;
	private boolean hiredDeathMessages = true;
	private boolean needsSave;
	private boolean showCustomWaypoints = true;
	private boolean showHiddenSharedWaypoints = true;
	private boolean showWaypoints = true;
	private boolean structuresBanned;
	private boolean tableSwitched;
	private boolean teleportedKW;

	private int alcoholTolerance;
	private int balance;
	private int completedBountyQuests;
	private int completedMiniquestCount;
	private int deathDim;
	private int ftSinceTick;
	private int dragonFireballSinceTick;
	private int nextCwpID = 20000;
	private int pdTick;
	private int oathBetrayCooldown;
	private int oathBetrayCooldownStart;
	private int oathKillCooldown;
	private int siegeActiveTime;
	private int ticksUntilFT;
	private int uuidToMountTime;

	private long lastOnlineTime = -1L;


	public GOTPlayerData(UUID uuid) {
		playerUUID = uuid;
		viewingFaction = GOTFaction.NORTH;
		ftSinceTick = GOTLevelData.getWaypointCooldownMax() * 20;
		dragonFireballSinceTick = GOTConfig.dragonFireballCooldown * 20;
	}

	private static ItemArmor.ArmorMaterial getFullArmorMaterial(EntityLivingBase entity) {
		ItemArmor.ArmorMaterial material = null;
		for (int i = 1; i <= 4; ++i) {
			ItemStack item = entity.getEquipmentInSlot(i);
			if (item == null || !(item.getItem() instanceof ItemArmor)) {
				return null;
			}
			ItemArmor.ArmorMaterial itemMaterial = ((ItemArmor) item.getItem()).getArmorMaterial();
			if (material != null && itemMaterial != material) {
				return null;
			}
			material = itemMaterial;
		}
		return material;
	}

	private static ItemArmor.ArmorMaterial getFullArmorMaterialExceptSlot(EntityLivingBase entity, int slot) {
		ItemArmor.ArmorMaterial material = null;
		for (int i = 0; i <= 3; ++i) {
			if (i != slot) {
				ItemStack item = entity.getEquipmentInSlot(i);
				if (item == null || !(item.getItem() instanceof GOTItemArmor)) {
					return null;
				}
				ItemArmor.ArmorMaterial itemMaterial = ((ItemArmor) item.getItem()).getArmorMaterial();
				if (material != null && itemMaterial != material) {
					return null;
				}
				material = itemMaterial;
			}
		}
		return material;
	}

	private static boolean isTimerAutosaveTick() {
		return MinecraftServer.getServer() != null && MinecraftServer.getServer().getTickCounter() % 200 == 0;
	}

	public static void customWaypointAddSharedBrotherhoodClient(GOTCustomWaypoint waypoint, GOTBrotherhoodClient fs) {
		waypoint.addSharedBrotherhood(fs.getBrotherhoodID());
	}

	public static void customWaypointRemoveSharedBrotherhoodClient(GOTCustomWaypoint waypoint, UUID fsID) {
		waypoint.removeSharedBrotherhood(fsID);
	}

	private static boolean doesFactionPreventOath(GOTFaction oathFac, GOTFaction otherFac) {
		return oathFac.isMortalEnemy(otherFac);
	}

	private static <T extends EntityLiving> T fastTravelEntity(WorldServer world, T entity, int i, int j, int k) {
		String entityID = EntityList.getEntityString(entity);
		NBTTagCompound nbt = new NBTTagCompound();
		entity.writeToNBT(nbt);
		entity.setDead();
		EntityLiving entityLiving = (EntityLiving) EntityList.createEntityByName(entityID, world);
		entityLiving.readFromNBT(nbt);
		entityLiving.setLocationAndAngles(i + 0.5D, j, k + 0.5D, entityLiving.rotationYaw, entityLiving.rotationPitch);
		entityLiving.fallDistance = 0.0F;
		entityLiving.getNavigator().clearPathEntity();
		entityLiving.setAttackTarget(null);
		world.spawnEntityInWorld(entityLiving);
		world.updateEntityWithOptionalForce(entityLiving, false);
		return (T) entityLiving;
	}

	private static long getCurrentOnlineTime() {
		return MinecraftServer.getServer().worldServerForDimension(0).getTotalWorldTime();
	}

	private static EntityPlayer getOtherPlayer(UUID uuid) {
		for (WorldServer worldServer : MinecraftServer.getServer().worldServers) {
			EntityPlayer entityplayer = worldServer.func_152378_a(uuid);
			if (entityplayer != null) {
				return entityplayer;
			}
		}
		return null;
	}

	public static void renameCustomWaypointClient(GOTCustomWaypoint waypoint, String newName) {
		waypoint.rename(newName);
	}

	private static void sendAchievementPacket(EntityPlayerMP entityplayer, GOTAchievement achievement, boolean display) {
		IMessage packet = new GOTPacketAchievement(achievement, display);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}

	private static void sendAchievementRemovePacket(EntityPlayerMP entityplayer, GOTAchievement achievement) {
		IMessage packet = new GOTPacketAchievementRemove(achievement);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}

	private static void sendFTBouncePacket(EntityPlayerMP entityplayer) {
		IMessage packet = new GOTPacketFTBounceClient();
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}

	private static void sendFTPacket(EntityPlayerMP entityplayer, GOTAbstractWaypoint waypoint, int startX, int startZ) {
		IMessage packet = new GOTPacketFTScreen(waypoint, startX, startZ);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}

	private static void sendMiniQuestPacket(EntityPlayerMP entityplayer, GOTMiniQuest quest, boolean completed) {
		NBTTagCompound nbt = new NBTTagCompound();
		quest.writeToNBT(nbt);
		IMessage packet = new GOTPacketMiniquest(nbt, completed);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
	}

	public int getTimeSinceDragonFireball() {
		return dragonFireballSinceTick;
	}

	public void setTimeSinceDragonFireball(int i) {
		setTimeSinceDragonFireball(i, false);
	}

	public void setShowWaypoints(boolean flag) {
		showWaypoints = flag;
		markDirty();
	}

	public void setShowHiddenSharedWaypoints(boolean flag) {
		showHiddenSharedWaypoints = flag;
		markDirty();
	}

	public void setShowCustomWaypoints(boolean flag) {
		showCustomWaypoints = flag;
		markDirty();
	}

	public void setCheckedMenu(boolean flag) {
		if (checkedMenu != flag) {
			checkedMenu = flag;
			markDirty();
		}
	}

	public void setTrackingMiniQuestID(UUID npcID) {
		trackingMiniQuestID = npcID;
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage packet = new GOTPacketMiniquestTrackClient(trackingMiniQuestID);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void setChatBoundBrotherhoodID(UUID fsID) {
		chatBoundBrotherhoodID = fsID;
		markDirty();
	}

	private void setTicksUntilFT(int i) {
		if (ticksUntilFT != i) {
			ticksUntilFT = i;
			if (ticksUntilFT == TICKS_UNTIL_FT_MAX || ticksUntilFT == 0) {
				markDirty();
			}
		}
	}

	public void acceptBrotherhoodInvite(GOTBrotherhood fs, boolean respectSizeLimit) {
		UUID fsID = fs.getBrotherhoodID();
		GOTBrotherhoodInvite existingInvite = null;
		for (GOTBrotherhoodInvite invite : brotherhoodInvites) {
			if (invite.getBrotherhoodID().equals(fsID)) {
				existingInvite = invite;
				break;
			}
		}
		if (existingInvite != null) {
			EntityPlayer entityplayer = getPlayer();
			if (fs.isDisbanded()) {
				rejectBrotherhoodInvite(fs);
				if (entityplayer != null && !entityplayer.worldObj.isRemote) {
					IMessage resultPacket = new GOTPacketBrotherhoodAcceptInviteResult(fs, GOTPacketBrotherhoodAcceptInviteResult.AcceptInviteResult.DISBANDED);
					GOTPacketHandler.NETWORK_WRAPPER.sendTo(resultPacket, (EntityPlayerMP) entityplayer);
				}
			} else {
				int limit = GOTConfig.brotherhoodMaxSize;
				if (respectSizeLimit && limit >= 0 && fs.getPlayerCount() >= limit) {
					rejectBrotherhoodInvite(fs);
					if (entityplayer != null && !entityplayer.worldObj.isRemote) {
						IMessage resultPacket = new GOTPacketBrotherhoodAcceptInviteResult(fs, GOTPacketBrotherhoodAcceptInviteResult.AcceptInviteResult.TOO_LARGE);
						GOTPacketHandler.NETWORK_WRAPPER.sendTo(resultPacket, (EntityPlayerMP) entityplayer);
					}
				} else {
					fs.addMember(playerUUID);
					brotherhoodInvites.remove(existingInvite);
					markDirty();
					sendBrotherhoodInviteRemovePacket(fs);
					if (entityplayer != null && !entityplayer.worldObj.isRemote) {
						IMessage resultPacket = new GOTPacketBrotherhoodAcceptInviteResult(fs, GOTPacketBrotherhoodAcceptInviteResult.AcceptInviteResult.JOINED);
						GOTPacketHandler.NETWORK_WRAPPER.sendTo(resultPacket, (EntityPlayerMP) entityplayer);
						UUID inviterID = existingInvite.getInviterID();
						if (inviterID == null) {
							inviterID = fs.getOwner();
						}
						EntityPlayer inviter = getOtherPlayer(inviterID);
						if (inviter != null) {
							GOTBrotherhood.sendNotification(inviter, "got.gui.brotherhoods.notifyAccept", entityplayer.getCommandSenderName());
						}
					}
				}
			}
		}
	}

	public void addAchievement(GOTAchievement achievement) {
		GOTAchievement gotAchievement = achievement;
		while (true) {
			if (hasAchievement(gotAchievement) || isSiegeActive()) {
				return;
			}
			achievements.add(gotAchievement);
			markDirty();
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				boolean canEarn = gotAchievement.canPlayerEarn(entityplayer);
				sendAchievementPacket((EntityPlayerMP) entityplayer, gotAchievement, canEarn);
				if (canEarn) {
					gotAchievement.broadcastEarning(entityplayer);
					List<GOTAchievement> earnedAchievements = getEarnedAchievements(GOTDimension.GAME_OF_THRONES);
					int biomes = 0;
					for (GOTAchievement earnedAchievement : earnedAchievements) {
						if (earnedAchievement.isBiomeAchievement()) {
							biomes++;
						}
					}
					if (biomes >= 20) {
						addAchievement(GOTAchievement.travel20);
					}
					if (biomes >= 40) {
						addAchievement(GOTAchievement.travel40);
					}
					if (biomes >= 60) {
						addAchievement(GOTAchievement.travel60);
					}
					if (biomes >= 80) {
						addAchievement(GOTAchievement.travel80);
					}
					if (biomes >= 100) {
						gotAchievement = GOTAchievement.travel100;
						continue;
					}
				}
			}
			return;
		}
	}

	private GOTReputationBonusMap addReputation(EntityPlayer entityplayer, GOTReputationValues.ReputationBonus source, GOTFaction faction, Collection<GOTFaction> forcedBonusFactions, double posX, double posY, double posZ) {
		float bonus = source.getBonus();
		GOTReputationBonusMap factionBonusMap = new GOTReputationBonusMap();
		float prevMainReputation = getReputation(faction);
		float conquestBonus = 0.0F;
		if (source.isKill()) {
			List<GOTFaction> killBonuses = faction.getBonusesForKilling();
			for (GOTFaction bonusFaction : killBonuses) {
				if (bonusFaction.isPlayableReputationFaction() && (bonusFaction.isApprovesWarCrimes() || !source.isCivilianKill())) {
					if (!source.isKillByHiredUnit()) {
						float mplier;
						if (forcedBonusFactions != null && forcedBonusFactions.contains(bonusFaction)) {
							mplier = 1.0F;
						} else {
							mplier = bonusFaction.getControlZoneReputationMultiplier(entityplayer);
						}
						if (mplier > 0.0F) {
							float reputation = getReputation(bonusFaction);
							float factionBonus = Math.abs(bonus);
							factionBonus *= mplier;
							if (reputation >= bonusFaction.getOathReputation() && !hasTakenOathTo(bonusFaction)) {
								factionBonus *= 0.5F;
							}
							factionBonus = checkBonusForOathEnemyLimit(bonusFaction, factionBonus);
							reputation += factionBonus;
							setReputation(bonusFaction, reputation);
							factionBonusMap.put(bonusFaction, factionBonus);
						}
					}
					if (bonusFaction == oathFaction) {
						float conq = bonus;
						if (source.isKillByHiredUnit()) {
							conq *= 0.25F;
						}
						conquestBonus = GOTConquestGrid.onConquestKill(entityplayer, bonusFaction, faction, conq);
						getFactionData(bonusFaction).addConquest(Math.abs(conquestBonus));
					}
				}
			}
			List<GOTFaction> killPenalties = faction.getPenaltiesForKilling();
			for (GOTFaction penaltyFaction : killPenalties) {
				if (penaltyFaction.isPlayableReputationFaction() && !source.isKillByHiredUnit()) {
					float mplier;
					if (penaltyFaction == faction) {
						mplier = 1.0F;
					} else {
						mplier = penaltyFaction.getControlZoneReputationMultiplier(entityplayer);
					}
					if (mplier > 0.0F) {
						float reputation = getReputation(penaltyFaction);
						float factionPenalty = -Math.abs(bonus);
						factionPenalty *= mplier;
						factionPenalty = GOTReputationValues.ReputationBonus.scalePenalty(factionPenalty, reputation);
						reputation += factionPenalty;
						setReputation(penaltyFaction, reputation);
						factionBonusMap.put(penaltyFaction, factionPenalty);
					}
				}
			}
		} else if (faction.isPlayableReputationFaction()) {
			float reputation = getReputation(faction);
			float factionBonus = bonus;
			if (factionBonus > 0.0F && reputation >= faction.getOathReputation() && !hasTakenOathTo(faction)) {
				factionBonus *= 0.5F;
			}
			factionBonus = checkBonusForOathEnemyLimit(faction, factionBonus);
			reputation += factionBonus;
			setReputation(faction, reputation);
			factionBonusMap.put(faction, factionBonus);
		}
		if (!factionBonusMap.isEmpty() || conquestBonus != 0.0F) {
			sendReputationBonusPacket(source, faction, prevMainReputation, factionBonusMap, conquestBonus, posX, posY, posZ);
		}
		return factionBonusMap;
	}

	public GOTReputationBonusMap addReputation(EntityPlayer entityplayer, GOTReputationValues.ReputationBonus source, GOTFaction faction, Collection<GOTFaction> forcedBonusFactions, Entity entity) {
		return addReputation(entityplayer, source, faction, forcedBonusFactions, entity.posX, entity.boundingBox.minY + entity.height * 0.7D, entity.posZ);
	}

	public void addReputation(EntityPlayer entityplayer, GOTReputationValues.ReputationBonus source, GOTFaction faction, double posX, double posY, double posZ) {
		addReputation(entityplayer, source, faction, null, posX, posY, posZ);
	}

	public GOTReputationBonusMap addReputation(EntityPlayer entityplayer, GOTReputationValues.ReputationBonus source, GOTFaction faction, Entity entity) {
		return addReputation(entityplayer, source, faction, null, entity);
	}

	public void addReputationFromCommand(GOTFaction faction, float add) {
		float reputation = getReputation(faction);
		reputation += add;
		setReputation(faction, reputation);
	}

	public void addCompletedBountyQuest() {
		completedBountyQuests++;
		markDirty();
	}

	public void addCustomWaypoint(GOTCustomWaypoint waypoint) {
		customWaypoints.add(waypoint);
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketCreateCWPClient packet = waypoint.getClientPacket();
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			GOTCustomWaypointLogger.logCreate(entityplayer, waypoint);
		}
		GOTCustomWaypoint shareCopy = waypoint.createCopyOfShared(playerUUID);
		List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedBrotherhoods();
		for (UUID sharedPlayerUUID : sharedPlayers) {
			EntityPlayer sharedPlayer = getOtherPlayer(sharedPlayerUUID);
			if (sharedPlayer != null && !sharedPlayer.worldObj.isRemote) {
				GOTLevelData.getData(sharedPlayerUUID).addOrUpdateSharedCustomWaypoint(shareCopy);
			}
		}
	}

	public void addCustomWaypointClient(GOTCustomWaypoint waypoint) {
		customWaypoints.add(waypoint);
	}

	public void addBrotherhood(GOTBrotherhood fs) {
		if (fs.containsPlayer(playerUUID)) {
			UUID fsID = fs.getBrotherhoodID();
			if (!brotherhoodIDs.contains(fsID)) {
				brotherhoodIDs.add(fsID);
				markDirty();
				sendBrotherhoodPacket(fs);
				addSharedCustomWaypointsFromAllIn(fs.getBrotherhoodID());
			}
		}
	}

	private void addBrotherhoodInvite(GOTBrotherhood fs, UUID inviterUUID, String inviterUsername) {
		UUID fsID = fs.getBrotherhoodID();
		boolean hasInviteAlready = false;
		for (GOTBrotherhoodInvite invite : brotherhoodInvites) {
			if (invite.getBrotherhoodID().equals(fsID)) {
				hasInviteAlready = true;
				break;
			}
		}
		if (!hasInviteAlready) {
			brotherhoodInvites.add(new GOTBrotherhoodInvite(fsID, inviterUUID));
			markDirty();
			sendBrotherhoodInvitePacket(fs);
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTBrotherhood.sendNotification(entityplayer, "got.gui.brotherhoods.notifyInvite", inviterUsername);
			}
		}
	}

	public void addMiniQuest(GOTMiniQuest quest) {
		miniQuests.add(quest);
		updateMiniQuest(quest);
	}

	public void addMiniQuestCompleted(GOTMiniQuest quest) {
		miniQuestsCompleted.add(quest);
		markDirty();
	}

	public void addOrUpdateClientBrotherhood(GOTBrotherhoodClient fs) {
		UUID fsID = fs.getBrotherhoodID();
		GOTBrotherhoodClient inList = null;
		for (GOTBrotherhoodClient fsInList : brotherhoodsClient) {
			if (fsInList.getBrotherhoodID().equals(fsID)) {
				inList = fsInList;
				break;
			}
		}
		if (inList != null) {
			inList.updateDataFrom(fs);
		} else {
			brotherhoodsClient.add(fs);
		}
	}

	public void addOrUpdateClientBrotherhoodInvite(GOTBrotherhoodClient fs) {
		UUID fsID = fs.getBrotherhoodID();
		GOTBrotherhoodClient inList = null;
		for (GOTBrotherhoodClient fsInList : brotherhoodInvitesClient) {
			if (fsInList.getBrotherhoodID().equals(fsID)) {
				inList = fsInList;
				break;
			}
		}
		if (inList != null) {
			inList.updateDataFrom(fs);
		} else {
			brotherhoodInvitesClient.add(fs);
		}
	}

	public void addOrUpdateSharedCustomWaypoint(GOTCustomWaypoint waypoint) {
		if (!waypoint.isShared()) {
			FMLLog.warning("Hummel009: Warning! Tried to cache a shared custom waypoint with no owner!");
			return;
		}
		if (waypoint.getSharingPlayerID().equals(playerUUID)) {
			FMLLog.warning("Hummel009: Warning! Tried to share a custom waypoint to its own player (%s)!", playerUUID.toString());
			return;
		}
		CWPSharedKey key = CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
		if (cwpSharedUnlocked.contains(key)) {
			waypoint.setSharedUnlocked();
		}
		waypoint.setSharedHidden(cwpSharedHidden.contains(key));
		GOTCustomWaypoint existing = getSharedCustomWaypointByID(waypoint.getSharingPlayerID(), waypoint.getID());
		if (existing == null) {
			customWaypointsShared.add(waypoint);
		} else {
			if (existing.isSharedUnlocked()) {
				waypoint.setSharedUnlocked();
			}
			waypoint.setSharedHidden(existing.isSharedHidden());
			int i = customWaypointsShared.indexOf(existing);
			customWaypointsShared.set(i, waypoint);
		}
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketCreateCWPClient packet = waypoint.getClientPacketShared();
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	private void addSharedCustomWaypointsFrom(UUID onlyOneBrotherhoodID, Iterable<UUID> checkSpecificPlayers) {
		List<UUID> checkBrotherhoodIDs;
		if (onlyOneBrotherhoodID != null) {
			checkBrotherhoodIDs = new ArrayList<>();
			checkBrotherhoodIDs.add(onlyOneBrotherhoodID);
		} else {
			checkBrotherhoodIDs = brotherhoodIDs;
		}
		Collection<UUID> checkFellowPlayerIDs = new ArrayList<>();
		if (checkSpecificPlayers != null) {
			for (UUID player : checkSpecificPlayers) {
				if (!player.equals(playerUUID)) {
					checkFellowPlayerIDs.add(player);
				}
			}
		} else {
			for (UUID fsID : checkBrotherhoodIDs) {
				GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
				if (fs != null) {
					Set<UUID> playerIDs = fs.getWaypointSharerUUIDs();
					for (UUID player : playerIDs) {
						if (!player.equals(playerUUID) && !checkFellowPlayerIDs.contains(player)) {
							checkFellowPlayerIDs.add(player);
						}
					}
				}
			}
		}
		for (UUID player : checkFellowPlayerIDs) {
			GOTPlayerData pd = GOTLevelData.getData(player);
			for (GOTCustomWaypoint waypoint : pd.customWaypoints) {
				boolean inSharedBrotherhood = false;
				for (UUID fsID : checkBrotherhoodIDs) {
					if (waypoint.hasSharedBrotherhood(fsID)) {
						inSharedBrotherhood = true;
						break;
					}
				}
				if (inSharedBrotherhood) {
					addOrUpdateSharedCustomWaypoint(waypoint.createCopyOfShared(player));
				}
			}
		}
	}

	private void addSharedCustomWaypointsFromAllBrotherhoods() {
		addSharedCustomWaypointsFrom(null, null);
	}

	private void addSharedCustomWaypointsFromAllIn(UUID onlyOneBrotherhoodID) {
		addSharedCustomWaypointsFrom(onlyOneBrotherhoodID, null);
	}

	public boolean anyMatchingBrotherhoodNames(String name, boolean client) {
		String name1 = StringUtils.strip(name).toLowerCase(Locale.ROOT);
		if (client) {
			for (GOTBrotherhoodClient fs : brotherhoodsClient) {
				String otherName = fs.getName();
				otherName = StringUtils.strip(otherName).toLowerCase(Locale.ROOT);
				if (name1.equals(otherName)) {
					return true;
				}
			}
		} else {
			for (UUID fsID : brotherhoodIDs) {
				GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
				if (fs != null) {
					String otherName = fs.getName();
					otherName = StringUtils.strip(otherName).toLowerCase(Locale.ROOT);
					if (name1.equals(otherName)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void cancelFastTravel() {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null) {
			if (targetFTWaypoint != null && !entityplayer.capabilities.isCreativeMode) {
				setTargetFTWaypoint(null);
				if (!entityplayer.worldObj.isRemote) {
					entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.motion"));
				}
			}
		}
	}

	public boolean canCreateBrotherhoods(boolean client) {
		int max = getMaxLeadingBrotherhoods();
		int leading = 0;
		if (client) {
			for (GOTBrotherhoodClient fs : brotherhoodsClient) {
				if (fs.isOwned()) {
					leading++;
					if (leading >= max) {
						return false;
					}
				}
			}
		} else {
			for (UUID fsID : brotherhoodIDs) {
				GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
				if (fs != null && fs.isOwner(playerUUID)) {
					leading++;
					if (leading >= max) {
						return false;
					}
				}
			}
		}
		return leading < max;
	}

	public boolean canFastTravel() {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null) {
			World world = entityplayer.worldObj;
			if (!entityplayer.capabilities.isCreativeMode) {
				double range = 16.0D;
				List<EntityLiving> entities = world.getEntitiesWithinAABB(EntityLiving.class, entityplayer.boundingBox.expand(range, range, range));
				for (EntityLiving entity : entities) {
					if (entity.getAttackTarget() == entityplayer) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public boolean canMakeNewOath() {
		return oathBetrayCooldown <= 0;
	}

	public boolean canTakeOathTo(GOTFaction fac) {
		return fac.isPlayableReputationFaction() && hasOathReputation(fac);
	}

	private void checkReputationAchievements(GOTFaction faction) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			faction.checkReputationAchievements(entityplayer);
		}
	}

	private float checkBonusForOathEnemyLimit(GOTFaction fac, float bonus) {
		if (isOathEnemyReputationLimited(fac)) {
			float reputation = getReputation(fac);
			float limit = 0.0f;
			if (reputation > limit) {
				return 0.0f;
			}
			if (reputation + bonus > limit) {
				return limit - reputation;
			}
		}
		return bonus;
	}

	private void checkCustomWaypointsSharedBy(Collection<UUID> checkSpecificPlayers) {
		Collection<GOTCustomWaypoint> removes = new ArrayList<>();
		for (GOTCustomWaypoint waypoint : customWaypointsShared) {
			UUID waypointSharingPlayer = waypoint.getSharingPlayerID();
			if (checkSpecificPlayers == null || checkSpecificPlayers.contains(waypointSharingPlayer)) {
				GOTCustomWaypoint wpOriginal = GOTLevelData.getData(waypointSharingPlayer).getCustomWaypointByID(waypoint.getID());
				if (wpOriginal != null) {
					List<UUID> sharedFellowPlayers = wpOriginal.getPlayersInAllSharedBrotherhoods();
					if (!sharedFellowPlayers.contains(playerUUID)) {
						removes.add(waypoint);
					}
				}
			}
		}
		for (GOTCustomWaypoint waypoint : removes) {
			removeSharedCustomWaypoint(waypoint);
		}
	}

	private void checkCustomWaypointsSharedFromBrotherhoods() {
		checkCustomWaypointsSharedBy(null);
	}

	private void checkIfStillWaypointSharerForBrotherhood(GOTBrotherhood fs) {
		if (!hasAnyWaypointsSharedToBrotherhood(fs)) {
			fs.markIsWaypointSharer(playerUUID, false);
		}
	}

	public void completeMiniQuest(GOTMiniQuest quest) {
		if (miniQuests.remove(quest)) {
			addMiniQuestCompleted(quest);
			completedMiniquestCount++;
			getFactionData(quest.getEntityFaction()).completeMiniQuest();
			markDirty();
			GOT.proxy.setTrackedQuest(quest);
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				IMessage packet = new GOTPacketMiniquestRemove(quest, false, true);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
		} else {
			FMLLog.warning("Warning: Attempted to remove a miniquest which does not belong to the player data");
		}
	}

	public void createBrotherhood(String name, boolean normalCreation) {
		if (normalCreation && (!GOTConfig.enableBrotherhoodCreation || !canCreateBrotherhoods(false))) {
			return;
		}
		if (!anyMatchingBrotherhoodNames(name, false)) {
			GOTBrotherhood brotherhood = new GOTBrotherhood(playerUUID, name);
			brotherhood.createAndRegister();
		}
	}

	public void customWaypointAddSharedBrotherhood(GOTCustomWaypoint waypoint, GOTBrotherhood fs) {
		UUID fsID = fs.getBrotherhoodID();
		waypoint.addSharedBrotherhood(fsID);
		markDirty();
		fs.markIsWaypointSharer(playerUUID, true);
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketShareCWPClient packet = waypoint.getClientAddBrotherhoodPacket(fsID);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
		GOTCustomWaypoint shareCopy = waypoint.createCopyOfShared(playerUUID);
		for (UUID player : fs.getAllPlayerUUIDs()) {
			if (!player.equals(playerUUID)) {
				GOTLevelData.getData(player).addOrUpdateSharedCustomWaypoint(shareCopy);
			}
		}
	}

	public void customWaypointRemoveSharedBrotherhood(GOTCustomWaypoint waypoint, GOTBrotherhood fs) {
		UUID fsID = fs.getBrotherhoodID();
		waypoint.removeSharedBrotherhood(fsID);
		markDirty();
		checkIfStillWaypointSharerForBrotherhood(fs);
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketShareCWPClient packet = waypoint.getClientRemoveBrotherhoodPacket(fsID);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
		GOTCustomWaypoint shareCopy = waypoint.createCopyOfShared(playerUUID);
		for (UUID player : fs.getAllPlayerUUIDs()) {
			if (!player.equals(playerUUID)) {
				GOTPlayerData pd = GOTLevelData.getData(player);
				pd.addOrUpdateSharedCustomWaypoint(shareCopy);
				pd.checkCustomWaypointsSharedBy(ImmutableList.of(playerUUID));
			}
		}
	}

	public void disbandBrotherhood(GOTBrotherhood fs, String disbanderUsername) {
		if (fs.isOwner(playerUUID)) {
			Iterable<UUID> memberUUIDs = new ArrayList<>(fs.getMemberUUIDs());
			fs.setDisbandedAndRemoveAllMembers();
			removeBrotherhood(fs);
			for (UUID memberID : memberUUIDs) {
				EntityPlayer member = getOtherPlayer(memberID);
				if (member != null && !member.worldObj.isRemote) {
					GOTBrotherhood.sendNotification(member, "got.gui.brotherhoods.notifyDisband", disbanderUsername);
				}
			}
		}
	}

	public void distributeMQEvent(GOTMiniQuestEvent event) {
		for (GOTMiniQuest quest : miniQuests) {
			if (quest.isActive()) {
				quest.handleEvent(event);
			}
		}
	}

	private void fastTravelTo(GOTAbstractWaypoint waypoint) {
		EntityPlayer player = getPlayer();
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) player;
			WorldServer world = (WorldServer) entityplayer.worldObj;
			int startX = MathHelper.floor_double(entityplayer.posX);
			int startZ = MathHelper.floor_double(entityplayer.posZ);
			double range = 256.0D;
			List<EntityLiving> entities = world.getEntitiesWithinAABB(EntityLiving.class, entityplayer.boundingBox.expand(range, range, range));
			Collection<EntityLiving> entitiesToTransport = new HashSet<>();
			for (EntityLiving entity : entities) {
				if (!(entity instanceof GOTEntityDragon)) {
					if (entity instanceof GOTEntityNPC) {
						GOTEntityNPC npc = (GOTEntityNPC) entity;
						if (npc.getHireableInfo().isActive() && npc.getHireableInfo().getHiringPlayer() == entityplayer && npc.getHireableInfo().shouldFollowPlayer()) {
							entitiesToTransport.add(npc);
							continue;
						}
					}
					if (entity instanceof EntityTameable) {
						EntityTameable pet = (EntityTameable) entity;
						if (pet.getOwner() == entityplayer && !pet.isSitting()) {
							entitiesToTransport.add(pet);
							continue;
						}
					}
					if (entity.getLeashed() && entity.getLeashedToEntity() == entityplayer) {
						entitiesToTransport.add(entity);
					}
				}
			}
			Collection<EntityLiving> removes = new HashSet<>();
			for (EntityLiving entity : entitiesToTransport) {
				Entity rider = entity.riddenByEntity;
				if (rider != null && entitiesToTransport.contains(rider)) {
					removes.add(entity);
				}
			}
			entitiesToTransport.removeAll(removes);
			int i = waypoint.getCoordX();
			int k = waypoint.getCoordZ();
			world.theChunkProviderServer.provideChunk(i >> 4, k >> 4);
			int j = waypoint.getCoordY(world, i, k);
			Entity playerMount = entityplayer.ridingEntity;
			entityplayer.mountEntity(null);
			entityplayer.setPositionAndUpdate(i + 0.5D, j, k + 0.5D);
			entityplayer.fallDistance = 0.0F;
			if (playerMount instanceof EntityLiving) {
				playerMount = fastTravelEntity(world, (EntityLiving) playerMount, i, j, k);
			}
			if (playerMount != null) {
				setUUIDToMount(playerMount.getUniqueID());
			}
			for (EntityLiving entity : entitiesToTransport) {
				Entity mount = entity.ridingEntity;
				entity.mountEntity(null);
				entity = fastTravelEntity(world, entity, i, j, k);
				if (mount instanceof EntityLiving) {
					mount = fastTravelEntity(world, (EntityLiving) mount, i, j, k);
					entity.mountEntity(mount);
				}
			}
			sendFTPacket(entityplayer, waypoint, startX, startZ);
			setTimeSinceFTWithUpdate(0);
			incrementWPUseCount(waypoint);
			if (waypoint instanceof GOTWaypoint) {
				lastWaypoint = (GOTWaypoint) waypoint;
				markDirty();
			}
			if (waypoint instanceof GOTCustomWaypoint) {
				GOTCustomWaypointLogger.logTravel(entityplayer, (GOTCustomWaypoint) waypoint);
			}
		}
	}

	public List<GOTAchievement> getAchievements() {
		return achievements;
	}

	public List<GOTMiniQuest> getActiveMiniQuests() {
		return selectMiniQuests(new GOTMiniQuestSelector().setActiveOnly());
	}

	public boolean getAdminHideMap() {
		return adminHideMap;
	}

	public void setAdminHideMap(boolean flag) {
		adminHideMap = flag;
		markDirty();
	}

	public int getAlcoholTolerance() {
		return alcoholTolerance;
	}

	public void setAlcoholTolerance(int i) {
		alcoholTolerance = i;
		markDirty();
		if (alcoholTolerance >= 250) {
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				addAchievement(GOTAchievement.gainHighAlcoholTolerance);
			}
		}
	}

	public float getReputation(GOTFaction faction) {
		if (faction.isHasFixedReputation()) {
			return faction.getFixedReputation();
		}
		Float reputation = reputations.get(faction);
		if (reputation != null) {
			return reputation;
		}
		return 0.0F;
	}

	public List<GOTAbstractWaypoint> getAllAvailableWaypoints() {
		List<GOTAbstractWaypoint> waypoints = new ArrayList<>(GOTWaypoint.listAllWaypoints());
		waypoints.addAll(customWaypoints);
		waypoints.addAll(customWaypointsShared);
		return waypoints;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int b) {
		balance = b;
		markDirty();
	}

	public GOTFaction getBetrayedOathFaction() {
		return betrayedOathFaction;
	}

	public void setBetrayedOathFaction(GOTFaction f) {
		betrayedOathFaction = f;
		markDirty();
	}

	public GOTCapes getCape() {
		return cape;
	}

	public void setCape(GOTCapes cape) {
		this.cape = cape;
		markDirty();
	}

	public GOTBrotherhood getChatBoundBrotherhood() {
		if (chatBoundBrotherhoodID != null) {
			return GOTBrotherhoodData.getActiveBrotherhood(chatBoundBrotherhoodID);
		}
		return null;
	}

	public void setChatBoundBrotherhood(GOTBrotherhood fs) {
		setChatBoundBrotherhoodID(fs.getBrotherhoodID());
	}

	public GOTBrotherhoodClient getClientBrotherhoodByID(UUID fsID) {
		for (GOTBrotherhoodClient fs : brotherhoodsClient) {
			if (fs.getBrotherhoodID().equals(fsID)) {
				return fs;
			}
		}
		return null;
	}

	public GOTBrotherhoodClient getClientBrotherhoodByName(String fsName) {
		for (GOTBrotherhoodClient fs : brotherhoodsClient) {
			if (fs.getName().equalsIgnoreCase(fsName)) {
				return fs;
			}
		}
		return null;
	}

	public List<GOTBrotherhoodClient> getClientBrotherhoodInvites() {
		return brotherhoodInvitesClient;
	}

	public List<GOTBrotherhoodClient> getClientBrotherhoods() {
		return brotherhoodsClient;
	}

	public int getCompletedBountyQuests() {
		return completedBountyQuests;
	}

	public int getCompletedMiniQuestsTotal() {
		return completedMiniquestCount;
	}

	public GOTCustomWaypoint getCustomWaypointByID(int ID) {
		for (GOTCustomWaypoint waypoint : customWaypoints) {
			if (waypoint.getID() == ID) {
				return waypoint;
			}
		}
		return null;
	}

	public List<GOTCustomWaypoint> getCustomWaypoints() {
		return customWaypoints;
	}

	public int getDeathDimension() {
		return deathDim;
	}

	public void setDeathDimension(int dim) {
		deathDim = dim;
		markDirty();
	}

	public ChunkCoordinates getDeathPoint() {
		return deathPoint;
	}

	public List<GOTAchievement> getEarnedAchievements(GOTDimension dimension) {
		List<GOTAchievement> earnedAchievements = new ArrayList<>();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null) {
			for (GOTAchievement achievement : achievements) {
				if (achievement.getCategory().getDimension() == dimension && achievement.canPlayerEarn(entityplayer)) {
					earnedAchievements.add(achievement);
				}
			}
		}
		return earnedAchievements;
	}

	public boolean getEnableConquestKills() {
		return conquestKills;
	}

	public void setEnableConquestKills(boolean flag) {
		conquestKills = flag;
		markDirty();
		sendOptionsPacket(5, flag);
	}

	public boolean getEnableHiredDeathMessages() {
		return hiredDeathMessages;
	}

	public void setEnableHiredDeathMessages(boolean flag) {
		hiredDeathMessages = flag;
		markDirty();
		sendOptionsPacket(1, flag);
	}

	public GOTFactionData getFactionData(GOTFaction faction) {
		return factionDataMap.computeIfAbsent(faction, k -> new GOTFactionData(this, faction));
	}

	public GOTBrotherhood getBrotherhoodByName(String fsName) {
		for (UUID fsID : brotherhoodIDs) {
			GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
			if (fs != null && fs.getName().equalsIgnoreCase(fsName)) {
				return fs;
			}
		}
		return null;
	}

	public List<UUID> getBrotherhoodIDs() {
		return brotherhoodIDs;
	}

	public List<GOTBrotherhood> getBrotherhoods() {
		List<GOTBrotherhood> brotherhoods = new ArrayList<>();
		for (UUID fsID : brotherhoodIDs) {
			GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
			if (fs != null) {
				brotherhoods.add(fs);
			}
		}
		return brotherhoods;
	}

	public boolean getFeminineRanks() {
		return feminineRanks;
	}

	public void setFeminineRanks(boolean flag) {
		feminineRanks = flag;
		markDirty();
		sendOptionsPacket(4, flag);
	}

	public boolean getFriendlyFire() {
		return friendlyFire;
	}

	public void setFriendlyFire(boolean flag) {
		friendlyFire = flag;
		markDirty();
		sendOptionsPacket(0, flag);
	}

	public boolean getHideReputation() {
		return hideReputation;
	}

	public void setHideReputation(boolean flag) {
		hideReputation = flag;
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTLevelData.sendReputationToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
		}
	}

	public boolean getHideMapLocation() {
		return hideOnMap;
	}

	public void setHideMapLocation(boolean flag) {
		hideOnMap = flag;
		markDirty();
		sendOptionsPacket(3, flag);
	}

	public GOTBiome getLastKnownBiome() {
		return lastBiome;
	}

	public GOTWaypoint getLastKnownWaypoint() {
		return lastWaypoint;
	}

	public int getMaxCustomWaypoints() {
		int achievements = getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size();
		return 5 + achievements / 5;
	}

	private int getMaxLeadingBrotherhoods() {
		int achievements = getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size();
		return 1 + achievements / 20;
	}

	public GOTMiniQuest getMiniQuestForID(UUID id, boolean completed) {
		List<GOTMiniQuest> threadSafe;
		if (completed) {
			threadSafe = new ArrayList<>(miniQuestsCompleted);
		} else {
			threadSafe = new ArrayList<>(miniQuests);
		}
		for (GOTMiniQuest quest : threadSafe) {
			if (quest.getQuestUUID().equals(id)) {
				return quest;
			}
		}
		return null;
	}

	public List<GOTMiniQuest> getMiniQuests() {
		return miniQuests;
	}

	public List<GOTMiniQuest> getMiniQuestsCompleted() {
		return miniQuestsCompleted;
	}

	public List<GOTMiniQuest> getMiniQuestsForEntity(Entity npc, boolean activeOnly) {
		return getMiniQuestsForEntityID(npc.getUniqueID(), activeOnly);
	}

	private List<GOTMiniQuest> getMiniQuestsForEntityID(UUID npcID, boolean activeOnly) {
		GOTMiniQuestSelector.EntityId sel = new GOTMiniQuestSelector.EntityId(npcID);
		if (activeOnly) {
			sel.setActiveOnly();
		}
		return selectMiniQuests(sel);
	}

	public List<GOTMiniQuest> getMiniQuestsForFaction(GOTFaction f, boolean activeOnly) {
		GOTMiniQuestSelector.Faction sel = new GOTMiniQuestSelector.Faction(() -> f);
		if (activeOnly) {
			sel.setActiveOnly();
		}
		return selectMiniQuests(sel);
	}

	public int getNextCwpID() {
		return nextCwpID;
	}

	private EntityPlayer getPlayer() {
		World[] searchWorlds;
		if (GOT.proxy.isClient()) {
			searchWorlds = new World[]{GOT.proxy.getClientWorld()};
		} else {
			searchWorlds = MinecraftServer.getServer().worldServers;
		}
		for (World world : searchWorlds) {
			EntityPlayer entityplayer = world.func_152378_a(playerUUID);
			if (entityplayer != null) {
				return entityplayer;
			}
		}
		return null;
	}

	public GOTTitle.PlayerTitle getPlayerTitle() {
		return playerTitle;
	}

	public void setPlayerTitle(GOTTitle.PlayerTitle title) {
		playerTitle = title;
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage packet = new GOTPacketTitle(playerTitle);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
		for (UUID fsID : brotherhoodIDs) {
			GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
			if (fs != null) {
				fs.updateForAllMembers(new BrotherhoodUpdateType.UpdatePlayerTitle(playerUUID, playerTitle));
			}
		}
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}

	public int getOathBetrayCooldown() {
		return oathBetrayCooldown;
	}

	public void setOathBetrayCooldown(int i) {
		int preCD = oathBetrayCooldown;
		GOTFaction preBetrayed = betrayedOathFaction;
		oathBetrayCooldown = Math.max(0, i);
		boolean bigChange = (oathBetrayCooldown == 0 || preCD == 0) && oathBetrayCooldown != preCD;
		if (oathBetrayCooldown > oathBetrayCooldownStart) {
			setOathBetrayCooldownStart(oathBetrayCooldown);
			bigChange = true;
		}
		if (oathBetrayCooldown <= 0 && preBetrayed != null) {
			setOathBetrayCooldownStart(0);
			setBetrayedOathFaction(null);
			bigChange = true;
		}
		if (bigChange || isTimerAutosaveTick()) {
			markDirty();
		}
		if (bigChange || oathBetrayCooldown % 5 == 0) {
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				IMessage packet = new GOTPacketBetrayedOath(oathBetrayCooldown, oathBetrayCooldownStart, betrayedOathFaction);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
		}
		if (oathBetrayCooldown == 0 && preCD != oathBetrayCooldown) {
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				String brokenName;
				if (preBetrayed == null) {
					brokenName = StatCollector.translateToLocal("got.gui.factions.oathUnknown");
				} else {
					brokenName = preBetrayed.factionName();
				}
				IChatComponent chatComponentTranslation = new ChatComponentTranslation("got.chat.oathBetrayCooldown", brokenName);
				entityplayer.addChatMessage(chatComponentTranslation);
			}
		}
	}

	public int getOathBetrayCooldownStart() {
		return oathBetrayCooldownStart;
	}

	public void setOathBetrayCooldownStart(int i) {
		oathBetrayCooldownStart = i;
		markDirty();
	}

	public GOTFaction getOathFaction() {
		return oathFaction;
	}

	public void setOathFaction(GOTFaction fac) {
		oathFaction = fac;
		oathKillCooldown = 0;
		markDirty();
		if (fac != null) {
			checkReputationAchievements(fac);
			addAchievement(GOTAchievement.oathService);
		}
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			if (fac != null) {
				World world = entityplayer.worldObj;
				world.playSoundAtEntity(entityplayer, "got:event.oath", 1.0F, 1.0F);
			}
			IMessage packet = new GOTPacketOath(fac);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public GOTPlayerQuestData getQuestData() {
		return questData;
	}

	public GOTFaction getRegionLastViewedFaction(GOTDimension.DimensionRegion region) {
		return prevRegionFactions.computeIfAbsent(region, key -> {
			GOTFaction fac = region.getFactionList().get(0);
			prevRegionFactions.put(region, fac);
			return fac;
		});
	}

	public GOTCustomWaypoint getSharedCustomWaypointByID(UUID owner, int id) {
		for (GOTCustomWaypoint waypoint : customWaypointsShared) {
			if (waypoint.getSharingPlayerID().equals(owner) && waypoint.getID() == id) {
				return waypoint;
			}
		}
		return null;
	}

	public GOTShields getShield() {
		return shield;
	}

	public void setShield(GOTShields gotshield) {
		shield = gotshield;
		markDirty();
	}

	public boolean getStructuresBanned() {
		return structuresBanned;
	}

	public void setStructuresBanned(boolean flag) {
		structuresBanned = flag;
		markDirty();
	}

	public boolean getTableSwitched() {
		return tableSwitched;
	}

	public void setTableSwitched(boolean flag) {
		tableSwitched = flag;
		markDirty();
		sendOptionsPacket(9, flag);
	}

	public boolean getTeleportedKW() {
		return teleportedKW;
	}

	public void setTeleportedKW(boolean flag) {
		teleportedKW = flag;
		markDirty();
	}

	public int getTimeSinceFT() {
		return ftSinceTick;
	}

	public void setTimeSinceFT(int i) {
		setTimeSinceFT(i, false);
	}

	public GOTMiniQuest getTrackingMiniQuest() {
		if (trackingMiniQuestID == null) {
			return null;
		}
		return getMiniQuestForID(trackingMiniQuestID, false);
	}

	public void setTrackingMiniQuest(GOTMiniQuest quest) {
		if (quest == null) {
			setTrackingMiniQuestID(null);
		} else {
			setTrackingMiniQuestID(quest.getQuestUUID());
		}
	}

	public GOTFaction getViewingFaction() {
		return viewingFaction;
	}

	public void setViewingFaction(GOTFaction faction) {
		if (faction != null) {
			viewingFaction = faction;
			markDirty();
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				IMessage packet = new GOTPacketUpdateViewingFaction(viewingFaction);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
		}
	}

	public int getWaypointFTTime(GOTAbstractWaypoint wp, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			return 0;
		}
		int baseMin = GOTLevelData.getWaypointCooldownMin();
		int baseMax = GOTLevelData.getWaypointCooldownMax();
		int useCount = getWPUseCount(wp);
		double dist = entityplayer.getDistance(wp.getCoordX() + 0.5D, wp.getCoordYSaved(), wp.getCoordZ() + 0.5D);
		double time = baseMin;
		double added = (baseMax - baseMin) * Math.pow(0.9D, useCount);
		time += added;
		time *= Math.max(1.0D, dist * 1.2E-5D);
		int seconds = (int) Math.round(time);
		seconds = Math.max(seconds, 0);
		return seconds * 20;
	}

	private int getWPUseCount(GOTAbstractWaypoint wp) {
		if (wp instanceof GOTCustomWaypoint) {
			GOTCustomWaypoint cwp = (GOTCustomWaypoint) wp;
			int ID = cwp.getID();
			if (cwp.isShared()) {
				UUID sharingPlayer = cwp.getSharingPlayerID();
				CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
				if (cwpSharedUseCounts.containsKey(key)) {
					return cwpSharedUseCounts.get(key);
				}
			} else if (cwpUseCounts.containsKey(ID)) {
				return cwpUseCounts.get(ID);
			}
		} else if (wpUseCounts.containsKey(wp)) {
			return wpUseCounts.get(wp);
		}
		return 0;
	}

	public void givePureConquestBonus(EntityPlayer entityplayer, GOTFaction bonusFac, GOTFaction enemyFac, float conq, String title, double posX, double posY, double posZ) {
		float conq1 = GOTConquestGrid.onConquestKill(entityplayer, bonusFac, enemyFac, conq);
		getFactionData(bonusFac).addConquest(Math.abs(conq1));
		if (conq1 != 0.0F) {
			GOTReputationValues.ReputationBonus source = new GOTReputationValues.ReputationBonus(0.0F, title);
			IMessage packet = new GOTPacketReputationBonus(bonusFac, getReputation(bonusFac), new GOTReputationBonusMap(), conq1, posX, posY, posZ, source);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public boolean hasAchievement(GOTAchievement achievement) {
		for (GOTAchievement a : achievements) {
			if (a.getCategory() == achievement.getCategory() && a.getId() == achievement.getId()) {
				return true;
			}
		}
		return false;
	}

	private boolean hasActiveOrCompleteMQType(Class<? extends GOTMiniQuest> type) {
		Collection<GOTMiniQuest> allQuests = new ArrayList<>();
		for (GOTMiniQuest q : miniQuests) {
			if (q.isActive()) {
				allQuests.add(q);
			}
		}
		allQuests.addAll(miniQuestsCompleted);
		for (GOTMiniQuest q : allQuests) {
			if (type.isAssignableFrom(q.getClass())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAnyJHQuest() {
		return hasActiveOrCompleteMQType(GOTMiniQuestWelcome.class);
	}

	public boolean hasAnyWaypointsSharedToBrotherhood(GOTBrotherhood fs) {
		for (GOTCustomWaypoint waypoint : customWaypoints) {
			if (waypoint.hasSharedBrotherhood(fs)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasOathReputation(GOTFaction fac) {
		float reputation = getReputation(fac);
		return reputation >= fac.getOathReputation();
	}

	public void hideOrUnhideSharedCustomWaypoint(GOTCustomWaypoint waypoint, boolean hide) {
		if (!waypoint.isShared()) {
			FMLLog.warning("Hummel009: Warning! Tried to unlock a shared custom waypoint with no owner!");
			return;
		}
		waypoint.setSharedHidden(hide);
		CWPSharedKey key = CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
		if (hide) {
			cwpSharedHidden.add(key);
		} else {
			cwpSharedHidden.remove(key);
		}
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketCWPSharedHideClient packet = waypoint.getClientSharedHidePacket(hide);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void incrementNextCwpID() {
		nextCwpID++;
		markDirty();
	}

	private void incrementWPUseCount(GOTAbstractWaypoint wp) {
		setWPUseCount(wp, getWPUseCount(wp) + 1);
	}

	public void invitePlayerToBrotherhood(GOTBrotherhood fs, UUID invitedPlayerUUID, String inviterUsername) {
		if (fs.isOwner(playerUUID) || fs.isAdmin(playerUUID)) {
			GOTLevelData.getData(invitedPlayerUUID).addBrotherhoodInvite(fs, playerUUID, inviterUsername);
		}
	}

	public boolean isFTRegionUnlocked(Iterable<GOTWaypoint.Region> regions) {
		for (GOTWaypoint.Region region : regions) {
			if (unlockedFTRegions.contains(region)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasTakenOathTo(GOTFaction fac) {
		return oathFaction == fac;
	}

	private boolean isOathEnemyReputationLimited(GOTFaction fac) {
		return oathFaction != null && doesFactionPreventOath(oathFaction, fac);
	}

	public boolean isSiegeActive() {
		return siegeActiveTime > 0;
	}

	public void setSiegeActive(int duration) {
		siegeActiveTime = Math.max(siegeActiveTime, duration);
	}

	public void leaveBrotherhood(GOTBrotherhood fs) {
		if (!fs.isOwner(playerUUID)) {
			fs.removeMember(playerUUID);
			if (brotherhoodIDs.contains(fs.getBrotherhoodID())) {
				removeBrotherhood(fs);
			}
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				EntityPlayer owner = getOtherPlayer(fs.getOwner());
				if (owner != null) {
					GOTBrotherhood.sendNotification(owner, "got.gui.brotherhoods.notifyLeave", entityplayer.getCommandSenderName());
				}
			}
		}
	}

	public List<String> listAllBrotherhoodNames() {
		List<String> list = new ArrayList<>();
		for (UUID fsID : brotherhoodIDs) {
			GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
			if (fs != null && fs.containsPlayer(playerUUID)) {
				list.add(fs.getName());
			}
		}
		return list;
	}

	public List<String> listAllLeadingBrotherhoodNames() {
		List<String> list = new ArrayList<>();
		for (UUID fsID : brotherhoodIDs) {
			GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
			if (fs != null && fs.isOwner(playerUUID)) {
				list.add(fs.getName());
			}
		}
		return list;
	}

	public void load(NBTTagCompound playerData) {
		reputations.clear();
		NBTTagList reputationTags = playerData.getTagList("ReputationMap", 10);
		for (int i = 0; i < reputationTags.tagCount(); i++) {
			NBTTagCompound nbt = reputationTags.getCompoundTagAt(i);
			GOTFaction faction = GOTFaction.forName(nbt.getString("Faction"));
			if (faction != null) {
				float reputation;
				if (nbt.hasKey("Reputation")) {
					reputation = nbt.getInteger("Reputation");
				} else {
					reputation = nbt.getFloat("RepF");
				}
				reputations.put(faction, reputation);
			}
		}
		factionDataMap.clear();
		NBTTagList factionDataTags = playerData.getTagList("FactionData", 10);
		for (int j = 0; j < factionDataTags.tagCount(); j++) {
			NBTTagCompound nbt = factionDataTags.getCompoundTagAt(j);
			GOTFaction faction = GOTFaction.forName(nbt.getString("Faction"));
			if (faction != null) {
				GOTFactionData data = new GOTFactionData(this, faction);
				data.load(nbt);
				factionDataMap.put(faction, data);
			}
		}
		if (playerData.hasKey("CurrentFaction")) {
			GOTFaction cur = GOTFaction.forName(playerData.getString("CurrentFaction"));
			if (cur != null) {
				viewingFaction = cur;
			}
		}
		prevRegionFactions.clear();
		NBTTagList prevRegionFactionTags = playerData.getTagList("PrevRegionFactions", 10);
		for (int k = 0; k < prevRegionFactionTags.tagCount(); k++) {
			NBTTagCompound nbt = prevRegionFactionTags.getCompoundTagAt(k);
			GOTDimension.DimensionRegion region = GOTDimension.DimensionRegion.forName(nbt.getString("Region"));
			GOTFaction faction = GOTFaction.forName(nbt.getString("Faction"));
			if (region != null && faction != null) {
				prevRegionFactions.put(region, faction);
			}
		}
		hideReputation = playerData.getBoolean("HideReputation");
		takenReputationRewards.clear();
		NBTTagList takenRewardsTags = playerData.getTagList("TakenReputationRewards", 10);
		for (int m = 0; m < takenRewardsTags.tagCount(); m++) {
			NBTTagCompound nbt = takenRewardsTags.getCompoundTagAt(m);
			GOTFaction faction = GOTFaction.forName(nbt.getString("Faction"));
			if (faction != null) {
				takenReputationRewards.add(faction);
			}
		}
		oathFaction = null;
		if (playerData.hasKey("OathFac")) {
			oathFaction = GOTFaction.forName(playerData.getString("OathFac"));
		}
		oathKillCooldown = playerData.getInteger("OathKillCD");
		oathBetrayCooldown = playerData.getInteger("OathBetrayCD");
		oathBetrayCooldownStart = playerData.getInteger("OathBetrayCDStart");
		betrayedOathFaction = null;
		if (playerData.hasKey("BetrayedOathFac")) {
			betrayedOathFaction = GOTFaction.forName(playerData.getString("BetrayedOathFac"));
		}
		hideOnMap = playerData.getBoolean("HideOnMap");
		adminHideMap = playerData.getBoolean("AdminHideMap");
		if (playerData.hasKey("ShowWP")) {
			showWaypoints = playerData.getBoolean("ShowWP");
		}
		if (playerData.hasKey("ShowCWP")) {
			showCustomWaypoints = playerData.getBoolean("ShowCWP");
		}
		if (playerData.hasKey("ShowHiddenSWP")) {
			showHiddenSharedWaypoints = playerData.getBoolean("ShowHiddenSWP");
		}
		if (playerData.hasKey("ConquestKills")) {
			conquestKills = playerData.getBoolean("ConquestKills");
		}
		achievements.clear();
		NBTTagList achievementTags = playerData.getTagList("Achievements", 10);
		for (int n = 0; n < achievementTags.tagCount(); n++) {
			NBTTagCompound nbt = achievementTags.getCompoundTagAt(n);
			String category = nbt.getString("Category");
			int ID = nbt.getInteger("ID");
			GOTAchievement achievement = GOTAchievement.achievementForCategoryAndID(GOTAchievement.categoryForName(category), ID);
			if (achievement != null && !achievements.contains(achievement)) {
				achievements.add(achievement);
			}
		}
		shield = null;
		if (playerData.hasKey("Shield")) {
			GOTShields savedShield = GOTShields.shieldForName(playerData.getString("Shield"));
			if (savedShield != null) {
				shield = savedShield;
			}
		}
		if (playerData.hasKey("FriendlyFire")) {
			friendlyFire = playerData.getBoolean("FriendlyFire");
		}
		if (playerData.hasKey("HiredDeathMessages")) {
			hiredDeathMessages = playerData.getBoolean("HiredDeathMessages");
		}
		deathPoint = null;
		//noinspection StreamToLoop
		if (Stream.of("DeathX", "DeathY", "DeathZ").allMatch(playerData::hasKey)) {
			deathPoint = new ChunkCoordinates(playerData.getInteger("DeathX"), playerData.getInteger("DeathY"), playerData.getInteger("DeathZ"));
			if (playerData.hasKey("DeathDim")) {
				deathDim = playerData.getInteger("DeathDim");
			} else {
				deathDim = GOTDimension.GAME_OF_THRONES.getDimensionID();
			}
		}
		alcoholTolerance = playerData.getInteger("Alcohol");
		miniQuests.clear();
		NBTTagList miniquestTags = playerData.getTagList("MiniQuests", 10);
		for (int i1 = 0; i1 < miniquestTags.tagCount(); i1++) {
			NBTTagCompound nbt = miniquestTags.getCompoundTagAt(i1);
			GOTMiniQuest quest = GOTMiniQuest.loadQuestFromNBT(nbt, this);
			if (quest != null) {
				miniQuests.add(quest);
			}
		}
		miniQuestsCompleted.clear();
		NBTTagList miniquestCompletedTags = playerData.getTagList("MiniQuestsCompleted", 10);
		for (int i2 = 0; i2 < miniquestCompletedTags.tagCount(); i2++) {
			NBTTagCompound nbt = miniquestCompletedTags.getCompoundTagAt(i2);
			GOTMiniQuest quest = GOTMiniQuest.loadQuestFromNBT(nbt, this);
			if (quest != null) {
				miniQuestsCompleted.add(quest);
			}
		}
		completedMiniquestCount = playerData.getInteger("MQCompleteCount");
		completedBountyQuests = playerData.getInteger("MQCompletedBounties");
		trackingMiniQuestID = null;
		if (playerData.hasKey("MiniQuestTrack")) {
			String s = playerData.getString("MiniQuestTrack");
			trackingMiniQuestID = UUID.fromString(s);
		}
		bountiesPlaced.clear();
		NBTTagList bountyTags = playerData.getTagList("BountiesPlaced", 8);
		for (int i3 = 0; i3 < bountyTags.tagCount(); i3++) {
			String fName = bountyTags.getStringTagAt(i3);
			GOTFaction fac = GOTFaction.forName(fName);
			if (fac != null) {
				bountiesPlaced.add(fac);
			}
		}
		lastWaypoint = null;
		if (playerData.hasKey("LastWP")) {
			String lastWPName = playerData.getString("LastWP");
			lastWaypoint = GOTWaypoint.waypointForName(lastWPName);
		}
		lastBiome = null;
		if (playerData.hasKey("LastBiome")) {
			short lastBiomeID = playerData.getShort("LastBiome");
			GOTBiome[] biomeList = GOTDimension.GAME_OF_THRONES.getBiomeList();
			if (lastBiomeID >= 0 && lastBiomeID < biomeList.length) {
				lastBiome = biomeList[lastBiomeID];
			}
		}
		sentMessageTypes.clear();
		NBTTagList sentMessageTags = playerData.getTagList("SentMessageTypes", 10);
		for (int i4 = 0; i4 < sentMessageTags.tagCount(); i4++) {
			NBTTagCompound nbt = sentMessageTags.getCompoundTagAt(i4);
			GOTGuiMessageTypes message = GOTGuiMessageTypes.forSaveName(nbt.getString("Message"));
			if (message != null) {
				boolean sent = nbt.getBoolean("Sent");
				sentMessageTypes.put(message, sent);
			}
		}
		playerTitle = null;
		if (playerData.hasKey("PlayerTitleID")) {
			GOTTitle title = GOTTitle.forID(playerData.getInteger("PlayerTitleID"));
			if (title != null) {
				int colorCode = playerData.getInteger("PlayerTitleColor");
				EnumChatFormatting color = GOTTitle.PlayerTitle.colorForID(colorCode);
				playerTitle = new GOTTitle.PlayerTitle(title, color);
			}
		}
		if (playerData.hasKey("FemRankOverride")) {
			feminineRanks = playerData.getBoolean("FemRankOverride");
		}
		if (playerData.hasKey("FTSince")) {
			ftSinceTick = playerData.getInteger("FTSince");
		}
		if (playerData.hasKey("DragonFireballSince")) {
			dragonFireballSinceTick = playerData.getInteger("DragonFireballSince");
		}
		targetFTWaypoint = null;
		uuidToMount = null;
		if (playerData.hasKey("MountUUID")) {
			uuidToMount = UUID.fromString(playerData.getString("MountUUID"));
		}
		uuidToMountTime = playerData.getInteger("MountUUIDTime");
		if (playerData.hasKey("LastOnlineTime")) {
			lastOnlineTime = playerData.getLong("LastOnlineTime");
		}
		unlockedFTRegions.clear();
		NBTTagList unlockedFTRegionTags = playerData.getTagList("UnlockedFTRegions", 10);
		for (int i5 = 0; i5 < unlockedFTRegionTags.tagCount(); i5++) {
			NBTTagCompound nbt = unlockedFTRegionTags.getCompoundTagAt(i5);
			String regionName = nbt.getString("Name");
			GOTWaypoint.Region region = GOTWaypoint.regionForName(regionName);
			if (region != null) {
				unlockedFTRegions.add(region);
			}
		}
		customWaypoints.clear();
		NBTTagList customWaypointTags = playerData.getTagList("CustomWaypoints", 10);
		for (int i6 = 0; i6 < customWaypointTags.tagCount(); i6++) {
			NBTTagCompound nbt = customWaypointTags.getCompoundTagAt(i6);
			GOTCustomWaypoint waypoint = GOTCustomWaypoint.readFromNBT(nbt, this);
			customWaypoints.add(waypoint);
		}
		cwpSharedUnlocked.clear();
		NBTTagList cwpSharedUnlockedTags = playerData.getTagList("CWPSharedUnlocked", 10);
		for (int i7 = 0; i7 < cwpSharedUnlockedTags.tagCount(); i7++) {
			NBTTagCompound nbt = cwpSharedUnlockedTags.getCompoundTagAt(i7);
			UUID sharingPlayer = UUID.fromString(nbt.getString("SharingPlayer"));
			int ID = nbt.getInteger("CustomID");
			CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
			cwpSharedUnlocked.add(key);
		}
		cwpSharedHidden.clear();
		NBTTagList cwpSharedHiddenTags = playerData.getTagList("CWPSharedHidden", 10);
		for (int i8 = 0; i8 < cwpSharedHiddenTags.tagCount(); i8++) {
			NBTTagCompound nbt = cwpSharedHiddenTags.getCompoundTagAt(i8);
			UUID sharingPlayer = UUID.fromString(nbt.getString("SharingPlayer"));
			int ID = nbt.getInteger("CustomID");
			CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
			cwpSharedHidden.add(key);
		}
		wpUseCounts.clear();
		NBTTagList wpCooldownTags = playerData.getTagList("WPUses", 10);
		for (int i9 = 0; i9 < wpCooldownTags.tagCount(); i9++) {
			NBTTagCompound nbt = wpCooldownTags.getCompoundTagAt(i9);
			String name = nbt.getString("WPName");
			int count = nbt.getInteger("Count");
			GOTWaypoint wp = GOTWaypoint.waypointForName(name);
			if (wp != null) {
				wpUseCounts.put(wp, count);
			}
		}
		cwpUseCounts.clear();
		NBTTagList cwpCooldownTags = playerData.getTagList("CWPUses", 10);
		for (int i10 = 0; i10 < cwpCooldownTags.tagCount(); i10++) {
			NBTTagCompound nbt = cwpCooldownTags.getCompoundTagAt(i10);
			int ID = nbt.getInteger("CustomID");
			int count = nbt.getInteger("Count");
			cwpUseCounts.put(ID, count);
		}
		cwpSharedUseCounts.clear();
		NBTTagList cwpSharedCooldownTags = playerData.getTagList("CWPSharedUses", 10);
		for (int i11 = 0; i11 < cwpSharedCooldownTags.tagCount(); i11++) {
			NBTTagCompound nbt = cwpSharedCooldownTags.getCompoundTagAt(i11);
			UUID sharingPlayer = UUID.fromString(nbt.getString("SharingPlayer"));
			int ID = nbt.getInteger("CustomID");
			CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
			int count = nbt.getInteger("Count");
			cwpSharedUseCounts.put(key, count);
		}
		nextCwpID = 20000;
		if (playerData.hasKey("NextCWPID")) {
			nextCwpID = playerData.getInteger("NextCWPID");
		}
		brotherhoodIDs.clear();
		NBTTagList brotherhoodTags = playerData.getTagList("Brotherhoods", 10);
		for (int i12 = 0; i12 < brotherhoodTags.tagCount(); i12++) {
			NBTTagCompound nbt = brotherhoodTags.getCompoundTagAt(i12);
			UUID fsID = UUID.fromString(nbt.getString("ID"));
			brotherhoodIDs.add(fsID);
		}
		brotherhoodInvites.clear();
		NBTTagList brotherhoodInviteTags = playerData.getTagList("BrotherhoodInvites", 10);
		for (int i13 = 0; i13 < brotherhoodInviteTags.tagCount(); i13++) {
			NBTTagCompound nbt = brotherhoodInviteTags.getCompoundTagAt(i13);
			UUID fsID = UUID.fromString(nbt.getString("ID"));
			UUID inviterID = null;
			if (nbt.hasKey("InviterID")) {
				inviterID = UUID.fromString(nbt.getString("InviterID"));
			}
			brotherhoodInvites.add(new GOTBrotherhoodInvite(fsID, inviterID));
		}
		chatBoundBrotherhoodID = null;
		if (playerData.hasKey("ChatBoundBrotherhood")) {
			chatBoundBrotherhoodID = UUID.fromString(playerData.getString("ChatBoundBrotherhood"));
		}
		structuresBanned = playerData.getBoolean("StructuresBanned");
		teleportedKW = playerData.getBoolean("TeleportedKW");
		if (playerData.hasKey("QuestData")) {
			NBTTagCompound questNBT = playerData.getCompoundTag("QuestData");
			questData.load(questNBT);
		}
		askedForJaqen = playerData.getBoolean("AskedForJaqen");
		balance = playerData.getInteger("Balance");
		cape = null;
		if (playerData.hasKey("Cape")) {
			GOTCapes savedCape = GOTCapes.capeForName(playerData.getString("Cape"));
			if (savedCape != null) {
				cape = savedCape;
			}
		}
		checkedMenu = playerData.getBoolean("CheckedMenu");
		tableSwitched = playerData.getBoolean("TableSwitched");
	}

	public void lockFTRegion(GOTWaypoint.Region region) {
		if (unlockedFTRegions.remove(region)) {
			markDirty();
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				IMessage packet = new GOTPacketWaypointRegion(region, false);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
		}
	}

	public void markDirty() {
		needsSave = true;
	}

	public boolean needsSave() {
		return needsSave;
	}

	public void onOathKill(EntityPlayer entityplayer) {
		oathKillCooldown += 24000;
		markDirty();
		if (oathKillCooldown > 24000) {
			oathBetrayFaction(entityplayer, false);
		} else if (oathFaction != null) {
			IChatComponent chatComponentTranslation = new ChatComponentTranslation("got.chat.oathKillWarn", oathFaction.factionName());
			entityplayer.addChatMessage(chatComponentTranslation);
		}
	}

	public void onUpdate(EntityPlayerMP entityplayer, WorldServer world) {
		pdTick++;
		GOTDimension.DimensionRegion currentRegion = viewingFaction.getFactionRegion();
		GOTDimension currentDim = GOTDimension.GAME_OF_THRONES;
		if (currentRegion.getDimension() != currentDim) {
			currentRegion = currentDim.getDimensionRegions().get(0);
			setViewingFaction(getRegionLastViewedFaction(currentRegion));
		}
		if (!isSiegeActive()) {
			runAchievementChecks(entityplayer, world);
		}
		if (!checkedMenu) {
			IMessage packet = new GOTPacketMenuPrompt(GOTPacketMenuPrompt.Type.MENU);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
		}
		if (playerTitle != null && !playerTitle.getTitle().canPlayerUse(entityplayer)) {
			IChatComponent chatComponentTranslation = new ChatComponentTranslation("got.chat.loseTitle", playerTitle.getFullTitleComponent(entityplayer));
			entityplayer.addChatMessage(chatComponentTranslation);
			setPlayerTitle(null);
		}
		if (oathKillCooldown > 0) {
			oathKillCooldown--;
			if (oathKillCooldown == 0 || isTimerAutosaveTick()) {
				markDirty();
			}
		}
		if (oathBetrayCooldown > 0) {
			setOathBetrayCooldown(oathBetrayCooldown - 1);
		}
		if (trackingMiniQuestID != null && getTrackingMiniQuest() == null) {
			setTrackingMiniQuest(null);
		}
		List<GOTMiniQuest> activeMiniquests = getActiveMiniQuests();
		for (GOTMiniQuest quest : activeMiniquests) {
			quest.onPlayerTick();
		}
		if (!bountiesPlaced.isEmpty()) {
			for (GOTFaction fac : bountiesPlaced) {
				IChatComponent chatComponentTranslation = new ChatComponentTranslation("got.chat.bountyPlaced", fac.factionName());
				chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				entityplayer.addChatMessage(chatComponentTranslation);
			}
			bountiesPlaced.clear();
			markDirty();
		}
		setTimeSinceFT(ftSinceTick + 1);
		setTimeSinceDragonFireball(dragonFireballSinceTick + 1);
		if (targetFTWaypoint != null) {
			if (entityplayer.isPlayerSleeping()) {
				entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.inBed"));
				setTargetFTWaypoint(null);
			} else if (ticksUntilFT > 0) {
				int seconds = ticksUntilFT / 20;
				if (ticksUntilFT == TICKS_UNTIL_FT_MAX) {
					entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.travelTicksStart", seconds));
				} else if (ticksUntilFT % 20 == 0 && seconds <= 5) {
					entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.travelTicks", seconds));
				}
				ticksUntilFT--;
				setTicksUntilFT(ticksUntilFT);
			} else {
				sendFTBouncePacket(entityplayer);
			}
		} else {
			setTicksUntilFT(0);
		}
		lastOnlineTime = getCurrentOnlineTime();
		if (uuidToMount != null) {
			if (uuidToMountTime > 0) {
				uuidToMountTime--;
			} else {
				double range = 32.0D;
				List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, entityplayer.boundingBox.expand(range, range, range));
				for (EntityLivingBase entity : entities) {
					if (entity.getUniqueID().equals(uuidToMount)) {
						entityplayer.mountEntity(entity);
						break;
					}
				}
				setUUIDToMount(null);
			}
		}
		if (pdTick % 24000 == 0 && alcoholTolerance > 0) {
			alcoholTolerance--;
			setAlcoholTolerance(alcoholTolerance);
		}
		unlockSharedCustomWaypoints(entityplayer);
		if (pdTick % 100 == 0 && world.provider instanceof GOTWorldProvider) {
			int i = MathHelper.floor_double(entityplayer.posX);
			int k = MathHelper.floor_double(entityplayer.posZ);
			GOTBiome biome = (GOTBiome) GOTCrashHandler.getBiomeGenForCoords(world.provider, i, k);
			if (biome.getBiomeDimension() == GOTDimension.GAME_OF_THRONES) {
				GOTBiome prevLastBiome = lastBiome;
				lastBiome = biome;
				if (prevLastBiome != biome) {
					markDirty();
				}
			}
		}
		if (adminHideMap) {
			boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile());
			if (!entityplayer.capabilities.isCreativeMode || !isOp) {
				setAdminHideMap(false);
				GOTCommandAdminHideMap.notifyUnhidden(entityplayer);
			}
		}
		if (siegeActiveTime > 0) {
			siegeActiveTime--;
		}
	}

	public void placeBountyFor(GOTFaction f) {
		bountiesPlaced.add(f);
		markDirty();
	}

	public void receiveFTBouncePacket() {
		if (targetFTWaypoint != null && ticksUntilFT <= 0) {
			fastTravelTo(targetFTWaypoint);
			setTargetFTWaypoint(null);
		}
	}

	public void rejectBrotherhoodInvite(GOTBrotherhood fs) {
		UUID fsID = fs.getBrotherhoodID();
		GOTBrotherhoodInvite existingInvite = null;
		for (GOTBrotherhoodInvite invite : brotherhoodInvites) {
			if (invite.getBrotherhoodID().equals(fsID)) {
				existingInvite = invite;
				break;
			}
		}
		if (existingInvite != null) {
			brotherhoodInvites.remove(existingInvite);
			markDirty();
			sendBrotherhoodInviteRemovePacket(fs);
		}
	}

	public void removeAchievement(GOTAchievement achievement) {
		if (!hasAchievement(achievement)) {
			return;
		}
		if (achievements.remove(achievement)) {
			markDirty();
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				sendAchievementRemovePacket((EntityPlayerMP) entityplayer, achievement);
			}
		}
	}

	public void removeClientBrotherhood(UUID fsID) {
		GOTBrotherhoodClient inList = null;
		for (GOTBrotherhoodClient fsInList : brotherhoodsClient) {
			if (fsInList.getBrotherhoodID().equals(fsID)) {
				inList = fsInList;
				break;
			}
		}
		if (inList != null) {
			brotherhoodsClient.remove(inList);
		}
	}

	public void removeClientBrotherhoodInvite(UUID fsID) {
		GOTBrotherhoodClient inList = null;
		for (GOTBrotherhoodClient fsInList : brotherhoodInvitesClient) {
			if (fsInList.getBrotherhoodID().equals(fsID)) {
				inList = fsInList;
				break;
			}
		}
		if (inList != null) {
			brotherhoodInvitesClient.remove(inList);
		}
	}

	public void removeCustomWaypoint(GOTCustomWaypoint waypoint) {
		if (customWaypoints.remove(waypoint)) {
			markDirty();
			for (UUID fsID : waypoint.getSharedBrotherhoodIDs()) {
				GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
				if (fs != null) {
					checkIfStillWaypointSharerForBrotherhood(fs);
				}
			}
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTPacketDeleteCWPClient packet = waypoint.getClientDeletePacket();
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
				GOTCustomWaypointLogger.logDelete(entityplayer, waypoint);
			}
			GOTCustomWaypoint shareCopy = waypoint.createCopyOfShared(playerUUID);
			List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedBrotherhoods();
			for (UUID sharedPlayerUUID : sharedPlayers) {
				EntityPlayer sharedPlayer = getOtherPlayer(sharedPlayerUUID);
				if (sharedPlayer != null && !sharedPlayer.worldObj.isRemote) {
					GOTLevelData.getData(sharedPlayerUUID).removeSharedCustomWaypoint(shareCopy);
				}
			}
		}
	}

	public void removeCustomWaypointClient(GOTCustomWaypoint waypoint) {
		customWaypoints.remove(waypoint);
	}

	public void removeBrotherhood(GOTBrotherhood fs) {
		if (fs.isDisbanded() || !fs.containsPlayer(playerUUID)) {
			UUID fsID = fs.getBrotherhoodID();
			if (brotherhoodIDs.contains(fsID)) {
				brotherhoodIDs.remove(fsID);
				markDirty();
				sendBrotherhoodRemovePacket(fs);
				unshareBrotherhoodFromOwnCustomWaypoints(fs);
				checkCustomWaypointsSharedFromBrotherhoods();
			}
		}
	}

	public void removeMiniQuest(GOTMiniQuest quest, boolean completed) {
		List<GOTMiniQuest> removeList;
		if (completed) {
			removeList = miniQuestsCompleted;
		} else {
			removeList = miniQuests;
		}
		if (removeList.remove(quest)) {
			markDirty();
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				IMessage packet = new GOTPacketMiniquestRemove(quest, quest.isCompleted(), false);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
		} else {
			FMLLog.warning("Warning: Attempted to remove a miniquest which does not belong to the player data");
		}
	}

	public void removePlayerFromBrotherhood(GOTBrotherhood fs, UUID player, String removerUsername) {
		if (fs.isOwner(playerUUID) || fs.isAdmin(playerUUID)) {
			fs.removeMember(player);
			EntityPlayer removed = getOtherPlayer(player);
			if (removed != null && !removed.worldObj.isRemote) {
				GOTBrotherhood.sendNotification(removed, "got.gui.brotherhoods.notifyRemove", removerUsername);
			}
		}
	}

	public void removeSharedCustomWaypoint(GOTCustomWaypoint waypoint) {
		if (!waypoint.isShared()) {
			FMLLog.warning("Hummel009: Warning! Tried to remove a shared custom waypoint with no owner!");
			return;
		}
		GOTCustomWaypoint existing;
		if (customWaypointsShared.contains(waypoint)) {
			existing = waypoint;
		} else {
			existing = getSharedCustomWaypointByID(waypoint.getSharingPlayerID(), waypoint.getID());
		}
		if (existing != null) {
			customWaypointsShared.remove(existing);
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTPacketDeleteCWPClient packet = waypoint.getClientDeletePacketShared();
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
		} else {
			FMLLog.warning("Hummel009: Warning! Tried to remove a shared custom waypoint that does not exist!");
		}
	}

	public void renameCustomWaypoint(GOTCustomWaypoint waypoint, String newName) {
		waypoint.rename(newName);
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketRenameCWPClient packet = waypoint.getClientRenamePacket();
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			GOTCustomWaypointLogger.logRename(entityplayer, waypoint);
		}
		GOTCustomWaypoint shareCopy = waypoint.createCopyOfShared(playerUUID);
		List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedBrotherhoods();
		for (UUID sharedPlayerUUID : sharedPlayers) {
			EntityPlayer sharedPlayer = getOtherPlayer(sharedPlayerUUID);
			if (sharedPlayer != null && !sharedPlayer.worldObj.isRemote) {
				GOTLevelData.getData(sharedPlayerUUID).renameSharedCustomWaypoint(shareCopy, newName);
			}
		}
	}

	public void renameBrotherhood(GOTBrotherhood fs, String name) {
		if (fs.isOwner(playerUUID)) {
			fs.setName(name);
		}
	}

	public void renameSharedCustomWaypoint(GOTCustomWaypoint waypoint, String newName) {
		if (!waypoint.isShared()) {
			FMLLog.warning("Hummel009: Warning! Tried to rename a shared custom waypoint with no owner!");
			return;
		}
		waypoint.rename(newName);
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketRenameCWPClient packet = waypoint.getClientRenamePacketShared();
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void oathBetrayFaction(EntityPlayer entityplayer, boolean intentional) {
		GOTFaction wasOath = oathFaction;
		float oathLvl = wasOath.getOathReputation();
		float prevRep = getReputation(wasOath);
		float diff = prevRep - oathLvl;
		float cd = diff / 5000.0F;
		cd = MathHelper.clamp_float(cd, 0.0F, 1.0F);
		int cdTicks = 36000;
		cdTicks += Math.round(cd * 150.0F * 60.0F * 20.0F);
		setOathFaction(null);
		setBetrayedOathFaction(wasOath);
		setOathBetrayCooldown(cdTicks);
		World world = entityplayer.worldObj;
		if (!world.isRemote) {
			GOTFactionRank rank = wasOath.getRank(prevRep);
			GOTFactionRank rankBelow = wasOath.getRankBelow(rank);
			GOTFactionRank rankBelow2 = wasOath.getRankBelow(rankBelow);
			float newRep = rankBelow2.getReputation();
			newRep = Math.max(newRep, oathLvl / 2.0F);
			float repPenalty = newRep - prevRep;
			if (repPenalty < 0.0F) {
				GOTReputationValues.ReputationBonus penalty = GOTReputationValues.createOathPenalty(repPenalty);
				double repX;
				double repY;
				double repZ;
				double lookRange = 2.0D;
				Vec3 posEye = Vec3.createVectorHelper(entityplayer.posX, entityplayer.boundingBox.minY + entityplayer.getEyeHeight(), entityplayer.posZ);
				Vec3 look = entityplayer.getLook(1.0F);
				Vec3 posSight = posEye.addVector(look.xCoord * lookRange, look.yCoord * lookRange, look.zCoord * lookRange);
				MovingObjectPosition mop = world.rayTraceBlocks(posEye, posSight);
				if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					repX = mop.blockX + 0.5D;
					repY = mop.blockY + 0.5D;
					repZ = mop.blockZ + 0.5D;
				} else {
					repX = posSight.xCoord;
					repY = posSight.yCoord;
					repZ = posSight.zCoord;
				}
				addReputation(entityplayer, penalty, wasOath, repX, repY, repZ);
			}
			world.playSoundAtEntity(entityplayer, "got:event.oathBetray", 1.0F, 1.0F);
			ChatComponentTranslation chatComponentTranslation;
			if (intentional) {
				chatComponentTranslation = new ChatComponentTranslation("got.chat.oathBetray", wasOath.factionName());
			} else {
				chatComponentTranslation = new ChatComponentTranslation("got.chat.autoOathBetray", wasOath.factionName());
			}
			entityplayer.addChatMessage(chatComponentTranslation);
			checkReputationAchievements(wasOath);
		}
	}

	private void runAchievementChecks(EntityPlayer entityplayer, World world) {
		int i = MathHelper.floor_double(entityplayer.posX);
		int k = MathHelper.floor_double(entityplayer.posZ);
		BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(world, i, k);
		if (biome instanceof GOTBiome) {
			GOTBiome gotbiome = (GOTBiome) biome;
			GOTAchievement ach = gotbiome.getBiomeAchievement();
			if (ach != null) {
				addAchievement(ach);
			}
			GOTWaypoint.Region biomeRegion = gotbiome.getBiomeWaypoints();
			if (biomeRegion != null) {
				unlockFTRegion(biomeRegion);
			}
		}
		if (entityplayer.dimension == GOTDimension.GAME_OF_THRONES.getDimensionID()) {
			addAchievement(GOTAchievement.enterKnownWorld);
		}
		if (!hasAchievement(GOTAchievement.hundreds) && pdTick % 20 == 0) {
			int hiredUnits = 0;
			List<GOTEntityNPC> nearbyNPCs = world.getEntitiesWithinAABB(GOTEntityNPC.class, entityplayer.boundingBox.expand(64.0D, 64.0D, 64.0D));
			for (GOTEntityNPC npc : nearbyNPCs) {
				if (npc.getHireableInfo().isActive() && npc.getHireableInfo().getHiringPlayer() == entityplayer) {
					hiredUnits++;
				}
			}
			if (hiredUnits >= 100) {
				addAchievement(GOTAchievement.hundreds);
			}
		}
		if (!hasAchievement(GOTAchievement.hireGoldenCompany) && pdTick % 20 == 0) {
			int hiredUnits = 0;
			List<GOTEntityGoldenCompanyMan> nearbyNPCs = world.getEntitiesWithinAABB(GOTEntityGoldenCompanyMan.class, entityplayer.boundingBox.expand(64.0D, 64.0D, 64.0D));
			for (GOTEntityNPC npc : nearbyNPCs) {
				if (npc.getHireableInfo().isActive() && npc.getHireableInfo().getHiringPlayer() == entityplayer) {
					hiredUnits++;
				}
			}
			if (hiredUnits >= 10) {
				addAchievement(GOTAchievement.hireGoldenCompany);
			}
		}
		ItemArmor.ArmorMaterial fullMaterial = getFullArmorMaterial(entityplayer);
		if (GOTAchievement.ARMOR_ACHIEVEMENTS.containsKey(fullMaterial)) {
			addAchievement(GOTAchievement.ARMOR_ACHIEVEMENTS.get(fullMaterial));
		}
		fullMaterial = getFullArmorMaterialExceptSlot(entityplayer, 0);
		if (fullMaterial != null && fullMaterial == GOTMaterial.MOSSOVY) {
			addAchievement(GOTAchievement.wearFullMossovy);
		}
		fullMaterial = getFullArmorMaterialExceptSlot(entityplayer, 0);
		if (fullMaterial != null && fullMaterial == GOTMaterial.ICE) {
			addAchievement(GOTAchievement.wearFullWhiteWalkers);
		}
		fullMaterial = getFullArmorMaterialExceptSlot(entityplayer, 0);
		if (fullMaterial != null && fullMaterial == GOTMaterial.IBBEN) {
			addAchievement(GOTAchievement.wearFullIbben);
		}
	}

	public void save(NBTTagCompound playerData) {
		NBTTagList reputationTags = new NBTTagList();
		for (Map.Entry<GOTFaction, Float> entry : reputations.entrySet()) {
			GOTFaction faction = entry.getKey();
			float reputation = entry.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Faction", faction.codeName());
			nbt.setFloat("RepF", reputation);
			reputationTags.appendTag(nbt);
		}
		playerData.setTag("ReputationMap", reputationTags);
		NBTTagList factionDataTags = new NBTTagList();
		for (Map.Entry<GOTFaction, GOTFactionData> entry : factionDataMap.entrySet()) {
			GOTFaction faction = entry.getKey();
			GOTFactionData data = entry.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Faction", faction.codeName());
			data.save(nbt);
			factionDataTags.appendTag(nbt);
		}
		playerData.setTag("FactionData", factionDataTags);
		playerData.setString("CurrentFaction", viewingFaction.codeName());
		NBTTagList prevRegionFactionTags = new NBTTagList();
		for (Map.Entry<GOTDimension.DimensionRegion, GOTFaction> entry : prevRegionFactions.entrySet()) {
			GOTDimension.DimensionRegion region = entry.getKey();
			GOTFaction faction = entry.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Region", region.codeName());
			nbt.setString("Faction", faction.codeName());
			prevRegionFactionTags.appendTag(nbt);
		}
		playerData.setTag("PrevRegionFactions", prevRegionFactionTags);
		playerData.setBoolean("HideReputation", hideReputation);
		NBTTagList takenRewardsTags = new NBTTagList();
		for (GOTFaction faction : takenReputationRewards) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Faction", faction.codeName());
			takenRewardsTags.appendTag(nbt);
		}
		playerData.setTag("TakenReputationRewards", takenRewardsTags);
		if (oathFaction != null) {
			playerData.setString("OathFac", oathFaction.codeName());
		}
		playerData.setInteger("OathKillCD", oathKillCooldown);
		playerData.setInteger("OathBetrayCD", oathBetrayCooldown);
		playerData.setInteger("OathBetrayCDStart", oathBetrayCooldownStart);
		if (betrayedOathFaction != null) {
			playerData.setString("BetrayedOathFac", betrayedOathFaction.codeName());
		}
		playerData.setBoolean("HideOnMap", hideOnMap);
		playerData.setBoolean("AdminHideMap", adminHideMap);
		playerData.setBoolean("ShowWP", showWaypoints);
		playerData.setBoolean("ShowCWP", showCustomWaypoints);
		playerData.setBoolean("ShowHiddenSWP", showHiddenSharedWaypoints);
		playerData.setBoolean("ConquestKills", conquestKills);
		NBTTagList achievementTags = new NBTTagList();
		for (GOTAchievement achievement : achievements) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Category", achievement.getCategory().name());
			nbt.setInteger("ID", achievement.getId());
			achievementTags.appendTag(nbt);
		}
		playerData.setTag("Achievements", achievementTags);
		if (shield != null) {
			playerData.setString("Shield", shield.name());
		}
		playerData.setBoolean("FriendlyFire", friendlyFire);
		playerData.setBoolean("HiredDeathMessages", hiredDeathMessages);
		if (deathPoint != null) {
			playerData.setInteger("DeathX", deathPoint.posX);
			playerData.setInteger("DeathY", deathPoint.posY);
			playerData.setInteger("DeathZ", deathPoint.posZ);
			playerData.setInteger("DeathDim", deathDim);
		}
		playerData.setInteger("Alcohol", alcoholTolerance);
		NBTTagList miniquestTags = new NBTTagList();
		for (GOTMiniQuest quest : miniQuests) {
			NBTTagCompound nbt = new NBTTagCompound();
			quest.writeToNBT(nbt);
			miniquestTags.appendTag(nbt);
		}
		playerData.setTag("MiniQuests", miniquestTags);
		NBTTagList miniquestCompletedTags = new NBTTagList();
		for (GOTMiniQuest quest : miniQuestsCompleted) {
			NBTTagCompound nbt = new NBTTagCompound();
			quest.writeToNBT(nbt);
			miniquestCompletedTags.appendTag(nbt);
		}
		playerData.setTag("MiniQuestsCompleted", miniquestCompletedTags);
		playerData.setInteger("MQCompleteCount", completedMiniquestCount);
		playerData.setInteger("MQCompletedBounties", completedBountyQuests);
		if (trackingMiniQuestID != null) {
			playerData.setString("MiniQuestTrack", trackingMiniQuestID.toString());
		}
		NBTTagList bountyTags = new NBTTagList();
		for (GOTFaction fac : bountiesPlaced) {
			String fName = fac.codeName();
			bountyTags.appendTag(new NBTTagString(fName));
		}
		playerData.setTag("BountiesPlaced", bountyTags);
		if (lastWaypoint != null) {
			String lastWPName = lastWaypoint.getCodeName();
			playerData.setString("LastWP", lastWPName);
		}
		if (lastBiome != null) {
			int lastBiomeID = lastBiome.biomeID;
			playerData.setShort("LastBiome", (short) lastBiomeID);
		}
		NBTTagList sentMessageTags = new NBTTagList();
		for (Map.Entry<GOTGuiMessageTypes, Boolean> entry : sentMessageTypes.entrySet()) {
			GOTGuiMessageTypes message = entry.getKey();
			boolean sent = entry.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Message", message.getSaveName());
			nbt.setBoolean("Sent", sent);
			sentMessageTags.appendTag(nbt);
		}
		playerData.setTag("SentMessageTypes", sentMessageTags);
		if (playerTitle != null) {
			playerData.setInteger("PlayerTitleID", playerTitle.getTitle().getTitleID());
			playerData.setInteger("PlayerTitleColor", playerTitle.getColor().getFormattingCode());
		}
		playerData.setBoolean("FemRankOverride", feminineRanks);
		playerData.setInteger("FTSince", ftSinceTick);
		if (uuidToMount != null) {
			playerData.setString("MountUUID", uuidToMount.toString());
		}
		playerData.setInteger("MountUUIDTime", uuidToMountTime);
		playerData.setLong("LastOnlineTime", lastOnlineTime);
		NBTTagList unlockedFTRegionTags = new NBTTagList();
		for (GOTWaypoint.Region region : unlockedFTRegions) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Name", region.name());
			unlockedFTRegionTags.appendTag(nbt);
		}
		playerData.setTag("UnlockedFTRegions", unlockedFTRegionTags);
		NBTTagList customWaypointTags = new NBTTagList();
		for (GOTCustomWaypoint waypoint : customWaypoints) {
			NBTTagCompound nbt = new NBTTagCompound();
			waypoint.writeToNBT(nbt, this);
			customWaypointTags.appendTag(nbt);
		}
		playerData.setTag("CustomWaypoints", customWaypointTags);
		NBTTagList cwpSharedUnlockedTags = new NBTTagList();
		for (CWPSharedKey key : cwpSharedUnlocked) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("SharingPlayer", key.getSharingPlayer().toString());
			nbt.setInteger("CustomID", key.getWaypointID());
			cwpSharedUnlockedTags.appendTag(nbt);
		}
		playerData.setTag("CWPSharedUnlocked", cwpSharedUnlockedTags);
		NBTTagList cwpSharedHiddenTags = new NBTTagList();
		for (CWPSharedKey key : cwpSharedHidden) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("SharingPlayer", key.getSharingPlayer().toString());
			nbt.setInteger("CustomID", key.getWaypointID());
			cwpSharedHiddenTags.appendTag(nbt);
		}
		playerData.setTag("CWPSharedHidden", cwpSharedHiddenTags);
		NBTTagList wpUseTags = new NBTTagList();
		for (Map.Entry<GOTWaypoint, Integer> e : wpUseCounts.entrySet()) {
			GOTAbstractWaypoint wp = e.getKey();
			int count = e.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("WPName", wp.getCodeName());
			nbt.setInteger("Count", count);
			wpUseTags.appendTag(nbt);
		}
		playerData.setTag("WPUses", wpUseTags);
		NBTTagList cwpUseTags = new NBTTagList();
		for (Map.Entry<Integer, Integer> e : cwpUseCounts.entrySet()) {
			int ID = e.getKey();
			int count = e.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("CustomID", ID);
			nbt.setInteger("Count", count);
			cwpUseTags.appendTag(nbt);
		}
		playerData.setTag("CWPUses", cwpUseTags);
		NBTTagList cwpSharedUseTags = new NBTTagList();
		for (Map.Entry<CWPSharedKey, Integer> e : cwpSharedUseCounts.entrySet()) {
			CWPSharedKey key = e.getKey();
			int count = e.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("SharingPlayer", key.getSharingPlayer().toString());
			nbt.setInteger("CustomID", key.getWaypointID());
			nbt.setInteger("Count", count);
			cwpSharedUseTags.appendTag(nbt);
		}
		playerData.setTag("CWPSharedUses", cwpSharedUseTags);
		playerData.setInteger("NextCWPID", nextCwpID);
		NBTTagList brotherhoodTags = new NBTTagList();
		for (UUID fsID : brotherhoodIDs) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("ID", fsID.toString());
			brotherhoodTags.appendTag(nbt);
		}
		playerData.setTag("Brotherhoods", brotherhoodTags);
		NBTTagList brotherhoodInviteTags = new NBTTagList();
		for (GOTBrotherhoodInvite invite : brotherhoodInvites) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("ID", invite.getBrotherhoodID().toString());
			if (invite.getInviterID() != null) {
				nbt.setString("InviterID", invite.getInviterID().toString());
			}
			brotherhoodInviteTags.appendTag(nbt);
		}
		playerData.setTag("BrotherhoodInvites", brotherhoodInviteTags);
		if (chatBoundBrotherhoodID != null) {
			playerData.setString("ChatBoundBrotherhood", chatBoundBrotherhoodID.toString());
		}
		playerData.setBoolean("StructuresBanned", structuresBanned);
		playerData.setBoolean("TeleportedKW", teleportedKW);
		NBTTagCompound questNBT = new NBTTagCompound();
		questData.save(questNBT);
		playerData.setTag("QuestData", questNBT);

		playerData.setBoolean("AskedForJaqen", askedForJaqen);
		playerData.setInteger("Balance", balance);
		if (cape != null) {
			playerData.setString("Cape", cape.name());
		}
		playerData.setBoolean("CheckedMenu", checkedMenu);
		playerData.setBoolean("TableSwitched", tableSwitched);
		needsSave = false;
	}

	public List<GOTMiniQuest> selectMiniQuests(GOTMiniQuestSelector selector) {
		List<GOTMiniQuest> ret = new ArrayList<>();
		Iterable<GOTMiniQuest> threadSafe = new ArrayList<>(miniQuests);
		for (GOTMiniQuest quest : threadSafe) {
			if (selector.include(quest)) {
				ret.add(quest);
			}
		}
		return ret;
	}

	private void sendReputationBonusPacket(GOTReputationValues.ReputationBonus source, GOTFaction faction, float prevMainReputation, GOTReputationBonusMap factionMap, float conqBonus, double posX, double posY, double posZ) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null) {
			IMessage packet = new GOTPacketReputationBonus(faction, prevMainReputation, factionMap, conqBonus, posX, posY, posZ, source);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	private void sendBrotherhoodInvitePacket(GOTBrotherhood fs) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage packet = new GOTPacketBrotherhood(this, fs, true);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	private void sendBrotherhoodInviteRemovePacket(GOTBrotherhood fs) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage packet = new GOTPacketBrotherhoodRemove(fs, true);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	private void sendBrotherhoodPacket(GOTBrotherhood fs) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage packet = new GOTPacketBrotherhood(this, fs, false);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	private void sendBrotherhoodRemovePacket(GOTBrotherhood fs) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage packet = new GOTPacketBrotherhoodRemove(fs, false);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void sendMessageIfNotReceived(GOTGuiMessageTypes message) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			boolean sent = sentMessageTypes.computeIfAbsent(message, k -> false);
			if (!sent) {
				sentMessageTypes.put(message, true);
				markDirty();
				IMessage packet = new GOTPacketMessage(message);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
		}
	}

	private void sendOptionsPacket(int option, boolean flag) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage packet = new GOTPacketOptions(option, flag);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void sendPlayerData(EntityPlayerMP entityplayer) {
		NBTTagCompound nbt = new NBTTagCompound();
		save(nbt);
		nbt.removeTag("Achievements");
		nbt.removeTag("MiniQuests");
		nbt.removeTag("MiniQuestsCompleted");
		nbt.removeTag("CustomWaypoints");
		nbt.removeTag("Brotherhoods");
		nbt.removeTag("BrotherhoodInvites");
		IMessage packet = new GOTPacketLoginPlayerData(nbt);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
		for (GOTAchievement achievement : achievements) {
			sendAchievementPacket(entityplayer, achievement, false);
		}
		for (GOTMiniQuest quest : miniQuests) {
			sendMiniQuestPacket(entityplayer, quest, false);
		}
		for (GOTMiniQuest quest : miniQuestsCompleted) {
			sendMiniQuestPacket(entityplayer, quest, true);
		}
		for (GOTCustomWaypoint waypoint : customWaypoints) {
			GOTPacketCreateCWPClient cwpPacket = waypoint.getClientPacket();
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(cwpPacket, entityplayer);
		}
		for (UUID fsID : brotherhoodIDs) {
			GOTBrotherhood fs = GOTBrotherhoodData.getActiveBrotherhood(fsID);
			if (fs != null) {
				sendBrotherhoodPacket(fs);
				fs.doRetroactiveWaypointSharerCheckIfNeeded();
				checkIfStillWaypointSharerForBrotherhood(fs);
			}
		}
		Collection<GOTBrotherhoodInvite> staleBrotherhoodInvites = new HashSet<>();
		for (GOTBrotherhoodInvite invite : brotherhoodInvites) {
			GOTBrotherhood fs = GOTBrotherhoodData.getBrotherhood(invite.getBrotherhoodID());
			if (fs != null) {
				sendBrotherhoodInvitePacket(fs);
				continue;
			}
			staleBrotherhoodInvites.add(invite);
		}
		if (!staleBrotherhoodInvites.isEmpty()) {
			brotherhoodInvites.removeAll(staleBrotherhoodInvites);
			markDirty();
		}
		addSharedCustomWaypointsFromAllBrotherhoods();
	}

	public void setReputation(GOTFaction faction, float reputation) {
		EntityPlayer entityplayer = getPlayer();
		if (faction.isPlayableReputationFaction()) {
			reputations.put(faction, reputation);
			markDirty();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTLevelData.sendReputationToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
			}
			checkReputationAchievements(faction);
		}
		if (entityplayer != null && !entityplayer.worldObj.isRemote && oathFaction != null && !canTakeOathTo(oathFaction)) {
			oathBetrayFaction(entityplayer, false);
		}
	}

	public void setReputationFromCommand(GOTFaction faction, float set) {
		setReputation(faction, set);
	}

	public void setDeathPoint(int i, int j, int k) {
		deathPoint = new ChunkCoordinates(i, j, k);
		markDirty();
	}

	public void setBrotherhoodAdmin(GOTBrotherhood fs, UUID player, boolean flag, String granterUsername) {
		if (fs.isOwner(playerUUID)) {
			fs.setAdmin(player, flag);
			EntityPlayer subjectPlayer = getOtherPlayer(player);
			if (subjectPlayer != null && !subjectPlayer.worldObj.isRemote) {
				if (flag) {
					GOTBrotherhood.sendNotification(subjectPlayer, "got.gui.brotherhoods.notifyOp", granterUsername);
				} else {
					GOTBrotherhood.sendNotification(subjectPlayer, "got.gui.brotherhoods.notifyDeop", granterUsername);
				}
			}
		}
	}

	public void setBrotherhoodIcon(GOTBrotherhood fs, ItemStack itemstack) {
		if (fs.isOwner(playerUUID) || fs.isAdmin(playerUUID)) {
			fs.setIcon(itemstack);
		}
	}

	public void setBrotherhoodPreventHiredFF(GOTBrotherhood fs, boolean prevent) {
		if (fs.isOwner(playerUUID) || fs.isAdmin(playerUUID)) {
			fs.setPreventHiredFriendlyFire(prevent);
		}
	}

	public void setBrotherhoodPreventPVP(GOTBrotherhood fs, boolean prevent) {
		if (fs.isOwner(playerUUID) || fs.isAdmin(playerUUID)) {
			fs.setPreventPVP(prevent);
		}
	}

	public void setBrotherhoodShowMapLocations(GOTBrotherhood fs, boolean show) {
		if (fs.isOwner(playerUUID)) {
			fs.setShowMapLocations(show);
		}
	}

	public void setRegionLastViewedFaction(GOTDimension.DimensionRegion region, GOTFaction fac) {
		if (region.getFactionList().contains(fac)) {
			prevRegionFactions.put(region, fac);
			markDirty();
		}
	}

	private void setTimeSinceFT(int i, boolean forceUpdate) {
		int preTick = ftSinceTick;
		ftSinceTick = Math.max(0, i);
		boolean bigChange = (ftSinceTick == 0 || preTick == 0) && ftSinceTick != preTick || preTick < 0;
		if (bigChange || isTimerAutosaveTick() || forceUpdate) {
			markDirty();
		}
		if (bigChange || ftSinceTick % 5 == 0 || forceUpdate) {
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				IMessage packet = new GOTPacketFTTimer(ftSinceTick);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
		}
	}

	private void setTimeSinceDragonFireball(int i, boolean forceUpdate) {
		int preTick = dragonFireballSinceTick;
		dragonFireballSinceTick = Math.max(0, i);
		boolean bigChange = (dragonFireballSinceTick == 0 || preTick == 0) && dragonFireballSinceTick != preTick || preTick < 0;
		if (bigChange || isTimerAutosaveTick() || forceUpdate) {
			markDirty();
		}
		if (bigChange || dragonFireballSinceTick % 5 == 0 || forceUpdate) {
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				IMessage packet = new GOTPacketDragonFireballTimer(dragonFireballSinceTick);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
		}
	}

	public void setTimeSinceFTWithUpdate(int i) {
		setTimeSinceFT(i, true);
	}

	private void setUUIDToMount(UUID uuid) {
		uuidToMount = uuid;
		if (uuidToMount != null) {
			uuidToMountTime = 10;
		} else {
			uuidToMountTime = 0;
		}
		markDirty();
	}

	public void setWPUseCount(GOTAbstractWaypoint wp, int count) {
		if (wp instanceof GOTCustomWaypoint) {
			GOTCustomWaypoint cwp = (GOTCustomWaypoint) wp;
			int ID = cwp.getID();
			if (cwp.isShared()) {
				UUID sharingPlayer = cwp.getSharingPlayerID();
				CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
				cwpSharedUseCounts.put(key, count);
			} else {
				cwpUseCounts.put(ID, count);
			}
		} else {
			wpUseCounts.put((GOTWaypoint) wp, count);
		}
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage packet = new GOTPacketWaypointUseCount(wp, count);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public boolean showCustomWaypoints() {
		return showCustomWaypoints;
	}

	public boolean showHiddenSharedWaypoints() {
		return showHiddenSharedWaypoints;
	}

	public boolean showWaypoints() {
		return showWaypoints;
	}

	public void transferBrotherhood(GOTBrotherhood fs, UUID player, String prevOwnerUsername) {
		if (fs.isOwner(playerUUID)) {
			fs.setOwner(player);
			EntityPlayer newOwner = getOtherPlayer(player);
			if (newOwner != null && !newOwner.worldObj.isRemote) {
				GOTBrotherhood.sendNotification(newOwner, "got.gui.brotherhoods.notifyTransfer", prevOwnerUsername);
			}
		}
	}

	public void unlockFTRegion(GOTWaypoint.Region region) {
		if (isSiegeActive()) {
			return;
		}
		if (unlockedFTRegions.add(region)) {
			markDirty();
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				IMessage packet = new GOTPacketWaypointRegion(region, true);
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
			}
		}
	}

	public void unlockSharedCustomWaypoint(GOTCustomWaypoint waypoint) {
		if (!waypoint.isShared()) {
			FMLLog.warning("Hummel009: Warning! Tried to unlock a shared custom waypoint with no owner!");
			return;
		}
		waypoint.setSharedUnlocked();
		CWPSharedKey key = CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
		cwpSharedUnlocked.add(key);
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketCWPSharedUnlockClient packet = waypoint.getClientSharedUnlockPacket();
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	private void unlockSharedCustomWaypoints(EntityPlayer entityplayer) {
		if (pdTick % 20 == 0 && entityplayer.dimension == GOTDimension.GAME_OF_THRONES.getDimensionID()) {
			Collection<GOTCustomWaypoint> unlockWaypoints = new ArrayList<>();
			for (GOTCustomWaypoint waypoint : customWaypointsShared) {
				if (waypoint.isShared() && !waypoint.isSharedUnlocked() && waypoint.canUnlockShared(entityplayer)) {
					unlockWaypoints.add(waypoint);
				}
			}
			for (GOTCustomWaypoint waypoint : unlockWaypoints) {
				unlockSharedCustomWaypoint(waypoint);
			}
		}
	}

	private void unshareBrotherhoodFromOwnCustomWaypoints(GOTBrotherhood fs) {
		for (GOTCustomWaypoint waypoint : customWaypoints) {
			if (waypoint.hasSharedBrotherhood(fs)) {
				customWaypointRemoveSharedBrotherhood(waypoint, fs);
			}
		}
	}

	public void updateFactionData(GOTFaction faction, GOTFactionData factionData) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			markDirty();
			NBTTagCompound nbt = new NBTTagCompound();
			factionData.save(nbt);
			IMessage packet = new GOTPacketFactionData(faction, nbt);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void updateFastTravelClockFromLastOnlineTime(ICommandSender player) {
		if (lastOnlineTime <= 0L) {
			return;
		}
		MinecraftServer server = MinecraftServer.getServer();
		if (!server.isSinglePlayer()) {
			long currentOnlineTime = getCurrentOnlineTime();
			int diff = (int) (currentOnlineTime - lastOnlineTime);
			double offlineFactor = 0.1D;
			int ftClockIncrease = (int) (diff * offlineFactor);
			if (ftClockIncrease > 0) {
				setTimeSinceFTWithUpdate(ftSinceTick + ftClockIncrease);
				IChatComponent chatComponentTranslation = new ChatComponentTranslation("got.chat.ft.offlineTick", GOTLevelData.getHMSTime_Ticks(ftClockIncrease));
				player.addChatMessage(chatComponentTranslation);
			}
		}
	}

	public void updateBrotherhood(GOTBrotherhood fs, BrotherhoodUpdateType updateType) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage updatePacket = updateType.createUpdatePacket(this, fs);
			if (updatePacket != null) {
				GOTPacketHandler.NETWORK_WRAPPER.sendTo(updatePacket, (EntityPlayerMP) entityplayer);
			} else {
				GOTLog.getLogger().error("No associated packet for brotherhood update type {}", updateType.getClass().getName());
			}
		}
		List<UUID> playersToCheckSharedWaypointsFrom = updateType.getPlayersToCheckSharedWaypointsFrom(fs);
		if (playersToCheckSharedWaypointsFrom != null && !playersToCheckSharedWaypointsFrom.isEmpty()) {
			addSharedCustomWaypointsFrom(fs.getBrotherhoodID(), playersToCheckSharedWaypointsFrom);
			checkCustomWaypointsSharedBy(playersToCheckSharedWaypointsFrom);
		}
	}

	public void updateMiniQuest(GOTMiniQuest quest) {
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			sendMiniQuestPacket((EntityPlayerMP) entityplayer, quest, false);
		}
	}

	public void setTargetFTWaypoint(GOTAbstractWaypoint wp) {
		targetFTWaypoint = wp;
		markDirty();
		if (wp != null) {
			setTicksUntilFT(TICKS_UNTIL_FT_MAX);
		} else {
			setTicksUntilFT(0);
		}
	}

	private static class CWPSharedKey {
		private final UUID sharingPlayer;
		private final int waypointID;

		private CWPSharedKey(UUID player, int id) {
			sharingPlayer = player;
			waypointID = id;
		}

		private static CWPSharedKey keyFor(UUID player, int id) {
			return new CWPSharedKey(player, id);
		}

		private UUID getSharingPlayer() {
			return sharingPlayer;
		}

		private int getWaypointID() {
			return waypointID;
		}
	}
}