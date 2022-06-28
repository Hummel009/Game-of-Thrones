package got.common;

import java.io.IOException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.common.block.table.GOTBlockCraftingTable;
import got.common.command.GOTCommandAdminHideMap;
import got.common.database.*;
import got.common.entity.dragon.GOTEntityDragon;
import got.common.entity.essos.gold.GOTEntityGoldenMan;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.*;
import got.common.fellowship.*;
import got.common.item.other.GOTItemArmor;
import got.common.item.weapon.GOTItemCrossbowBolt;
import got.common.network.*;
import got.common.quest.*;
import got.common.util.GOTLog;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTPlayerData {
	private static int ticksUntilFT_max = 200;
	private UUID playerUUID;
	private boolean needsSave = false;
	private int pdTick = 0;
	private Map<GOTFaction, Float> alignments = new HashMap<>();
	private Map<GOTFaction, GOTFactionData> factionDataMap = new HashMap<>();
	private GOTFaction viewingFaction;
	private Map<GOTDimension.DimensionRegion, GOTFaction> prevRegionFactions = new HashMap<>();
	private boolean hideAlignment = false;
	private Set<GOTFaction> takenAlignmentRewards = new HashSet<>();
	private GOTFaction pledgeFaction;
	private int pledgeKillCooldown = 0;
	private int pledgeBreakCooldown;
	private int pledgeBreakCooldownStart;
	private GOTFaction brokenPledgeFaction = null;
	private boolean hideOnMap = false;
	private boolean adminHideMap = false;
	private boolean showWaypoints = true;
	private boolean showCustomWaypoints = true;
	private boolean showHiddenSharedWaypoints = true;
	private boolean conquestKills = true;
	private List<GOTAchievement> achievements = new ArrayList<>();
	private GOTShields shield;
	private boolean friendlyFire = false;
	private boolean hiredDeathMessages = true;
	private ChunkCoordinates deathPoint;
	private int deathDim;
	private int alcoholTolerance;
	private List<GOTMiniQuest> miniQuests = new ArrayList<>();
	private List<GOTMiniQuest> miniQuestsCompleted = new ArrayList<>();
	private int completedMiniquestCount;
	private int completedBountyQuests;
	private UUID trackingMiniQuestID;
	private List<GOTFaction> bountiesPlaced = new ArrayList<>();
	private GOTWaypoint lastWaypoint;
	private GOTBiome lastBiome;
	private Map<GOTGuiMessageTypes, Boolean> sentMessageTypes = new HashMap<>();
	private GOTTitle.PlayerTitle playerTitle;
	private boolean femRankOverride = false;
	private int ftSinceTick;
	private GOTAbstractWaypoint targetFTWaypoint;
	private int ticksUntilFT;
	private UUID uuidToMount;
	private int uuidToMountTime;
	private long lastOnlineTime = -1L;
	private Set<GOTWaypoint.Region> unlockedFTRegions = new HashSet<>();
	private List<GOTCustomWaypoint> customWaypoints = new ArrayList<>();
	private List<GOTCustomWaypoint> customWaypointsShared = new ArrayList<>();
	private Set<CWPSharedKey> cwpSharedUnlocked = new HashSet<>();
	private Set<CWPSharedKey> cwpSharedHidden = new HashSet<>();
	private Map<GOTWaypoint, Integer> wpUseCounts = new HashMap<>();
	private Map<Integer, Integer> cwpUseCounts = new HashMap<>();
	private Map<CWPSharedKey, Integer> cwpSharedUseCounts = new HashMap<>();
	private int nextCwpID = 20000;
	private List<UUID> fellowshipIDs = new ArrayList<>();
	private List<GOTFellowshipClient> fellowshipsClient = new ArrayList<>();
	private List<GOTFellowshipInvite> fellowshipInvites = new ArrayList<>();
	private List<GOTFellowshipClient> fellowshipInvitesClient = new ArrayList<>();
	private UUID chatBoundFellowshipID;
	private boolean structuresBanned = false;
	private GOTPlayerQuestData questData = new GOTPlayerQuestData(this);
	private int siegeActiveTime;
	private boolean teleportedKW;
	private GOTCapes cape;
	private int balance;
	private boolean checkedMenu;
	private boolean askedForJaqen;
	private boolean tableSwitched;

	public GOTPlayerData(UUID uuid) {
		playerUUID = uuid;
		viewingFaction = GOTFaction.NORTH;
		ftSinceTick = GOTLevelData.getWaypointCooldownMax() * 20;
	}

	public void acceptFellowshipInvite(GOTFellowship fs, boolean respectSizeLimit) {
		UUID fsID = fs.getFellowshipID();
		GOTFellowshipInvite existingInvite = null;
		for (GOTFellowshipInvite invite : fellowshipInvites) {
			if (invite.fellowshipID.equals(fsID)) {
				existingInvite = invite;
				break;
			}
		}
		if (existingInvite != null) {
			EntityPlayer entityplayer = getPlayer();
			if (fs.isDisbanded()) {
				rejectFellowshipInvite(fs);
				if (entityplayer != null && !entityplayer.worldObj.isRemote) {
					GOTPacketFellowshipAcceptInviteResult resultPacket = new GOTPacketFellowshipAcceptInviteResult(fs, GOTPacketFellowshipAcceptInviteResult.AcceptInviteResult.DISBANDED);
					GOTPacketHandler.networkWrapper.sendTo((IMessage) resultPacket, (EntityPlayerMP) entityplayer);
				}
			} else {
				int limit = GOTConfig.fellowshipMaxSize;
				if (respectSizeLimit && limit >= 0 && fs.getPlayerCount() >= limit) {
					rejectFellowshipInvite(fs);
					if (entityplayer != null && !entityplayer.worldObj.isRemote) {
						GOTPacketFellowshipAcceptInviteResult resultPacket = new GOTPacketFellowshipAcceptInviteResult(fs, GOTPacketFellowshipAcceptInviteResult.AcceptInviteResult.TOO_LARGE);
						GOTPacketHandler.networkWrapper.sendTo((IMessage) resultPacket, (EntityPlayerMP) entityplayer);
					}
				} else {
					fs.addMember(playerUUID);
					fellowshipInvites.remove(existingInvite);
					markDirty();
					sendFellowshipInviteRemovePacket(fs);
					if (entityplayer != null && !entityplayer.worldObj.isRemote) {
						GOTPacketFellowshipAcceptInviteResult resultPacket = new GOTPacketFellowshipAcceptInviteResult(fs, GOTPacketFellowshipAcceptInviteResult.AcceptInviteResult.JOINED);
						GOTPacketHandler.networkWrapper.sendTo((IMessage) resultPacket, (EntityPlayerMP) entityplayer);
						UUID inviterID = existingInvite.inviterID;
						if (inviterID == null) {
							inviterID = fs.getOwner();
						}
						EntityPlayer inviter = getOtherPlayer(inviterID);
						if (inviter != null) {
							fs.sendNotification(inviter, "got.gui.fellowships.notifyAccept", entityplayer.getCommandSenderName());
						}
					}
				}
			}
		}
	}

	public void addAchievement(GOTAchievement achievement) {
		if (hasAchievement(achievement) || isSiegeActive()) {
			return;
		}
		achievements.add(achievement);
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			boolean canEarn = achievement.canPlayerEarn(entityplayer);
			sendAchievementPacket((EntityPlayerMP) entityplayer, achievement, canEarn);
			if (canEarn) {
				achievement.broadcastEarning(entityplayer);
				List<GOTAchievement> earnedAchievements = getEarnedAchievements(GOTDimension.GAME_OF_THRONES);
				int biomes = 0;
				for (GOTAchievement earnedAchievement : earnedAchievements) {
					if (earnedAchievement.isBiomeAchievement) {
						biomes++;
					}
				}
				if (biomes >= 10) {
					addAchievement(GOTAchievement.travel10);
				}
				if (biomes >= 20) {
					addAchievement(GOTAchievement.travel20);
				}
				if (biomes >= 30) {
					addAchievement(GOTAchievement.travel30);
				}
				if (biomes >= 40) {
					addAchievement(GOTAchievement.travel40);
				}
				if (biomes >= 50) {
					addAchievement(GOTAchievement.travel50);
				}
			}
		}
	}

	public GOTAlignmentBonusMap addAlignment(EntityPlayer entityplayer, GOTAlignmentValues.AlignmentBonus source, GOTFaction faction, double posX, double posY, double posZ) {
		return addAlignment(entityplayer, source, faction, null, posX, posY, posZ);
	}

	public GOTAlignmentBonusMap addAlignment(EntityPlayer entityplayer, GOTAlignmentValues.AlignmentBonus source, GOTFaction faction, Entity entity) {
		return addAlignment(entityplayer, source, faction, null, entity);
	}

	public GOTAlignmentBonusMap addAlignment(EntityPlayer entityplayer, GOTAlignmentValues.AlignmentBonus source, GOTFaction faction, List<GOTFaction> forcedBonusFactions, double posX, double posY, double posZ) {
		float bonus = source.bonus;
		GOTAlignmentBonusMap factionBonusMap = new GOTAlignmentBonusMap();
		float prevMainAlignment = getAlignment(faction);
		float conquestBonus = 0.0F;
		if (source.isKill) {
			List<GOTFaction> killBonuses = faction.getBonusesForKilling();
			for (GOTFaction bonusFaction : killBonuses) {
				if (bonusFaction.isPlayableAlignmentFaction() && (bonusFaction.approvesWarCrimes || !source.isCivilianKill)) {
					if (!source.killByHiredUnit) {
						float mplier = 0.0F;
						if (forcedBonusFactions != null && forcedBonusFactions.contains(bonusFaction)) {
							mplier = 1.0F;
						} else {
							mplier = bonusFaction.getControlZoneAlignmentMultiplier(entityplayer);
						}
						if (mplier > 0.0F) {
							float alignment = getAlignment(bonusFaction);
							float factionBonus = Math.abs(bonus);
							factionBonus *= mplier;
							if (alignment >= bonusFaction.getPledgeAlignment() && !isPledgedTo(bonusFaction)) {
								factionBonus *= 0.5F;
							}
							factionBonus = checkBonusForPledgeEnemyLimit(bonusFaction, factionBonus);
							alignment += factionBonus;
							setAlignment(bonusFaction, alignment);
							factionBonusMap.put(bonusFaction, Float.valueOf(factionBonus));
						}
					}
					if (bonusFaction == getPledgeFaction()) {
						float conq = bonus;
						if (source.killByHiredUnit) {
							conq *= 0.25F;
						}
						conquestBonus = GOTConquestGrid.onConquestKill(entityplayer, bonusFaction, faction, conq);
						getFactionData(bonusFaction).addConquest(Math.abs(conquestBonus));
					}
				}
			}
			List<GOTFaction> killPenalties = faction.getPenaltiesForKilling();
			for (GOTFaction penaltyFaction : killPenalties) {
				if (penaltyFaction.isPlayableAlignmentFaction() && !source.killByHiredUnit) {
					float mplier = 0.0F;
					if (penaltyFaction == faction) {
						mplier = 1.0F;
					} else {
						mplier = penaltyFaction.getControlZoneAlignmentMultiplier(entityplayer);
					}
					if (mplier > 0.0F) {
						float alignment = getAlignment(penaltyFaction);
						float factionPenalty = -Math.abs(bonus);
						factionPenalty *= mplier;
						factionPenalty = GOTAlignmentValues.AlignmentBonus.scalePenalty(factionPenalty, alignment);
						alignment += factionPenalty;
						setAlignment(penaltyFaction, alignment);
						factionBonusMap.put(penaltyFaction, Float.valueOf(factionPenalty));
					}
				}
			}
		} else if (faction.isPlayableAlignmentFaction()) {
			float alignment = getAlignment(faction);
			float factionBonus = bonus;
			if (factionBonus > 0.0F && alignment >= faction.getPledgeAlignment() && !isPledgedTo(faction)) {
				factionBonus *= 0.5F;
			}
			factionBonus = checkBonusForPledgeEnemyLimit(faction, factionBonus);
			alignment += factionBonus;
			setAlignment(faction, alignment);
			factionBonusMap.put(faction, Float.valueOf(factionBonus));
		}
		if (!factionBonusMap.isEmpty() || conquestBonus != 0.0F) {
			sendAlignmentBonusPacket(source, faction, prevMainAlignment, factionBonusMap, conquestBonus, posX, posY, posZ);
		}
		return factionBonusMap;
	}

	public GOTAlignmentBonusMap addAlignment(EntityPlayer entityplayer, GOTAlignmentValues.AlignmentBonus source, GOTFaction faction, List<GOTFaction> forcedBonusFactions, Entity entity) {
		return addAlignment(entityplayer, source, faction, forcedBonusFactions, entity.posX, entity.boundingBox.minY + entity.height * 0.7D, entity.posZ);
	}

	public void addAlignmentFromCommand(GOTFaction faction, float add) {
		float alignment = getAlignment(faction);
		alignment += add;
		setAlignment(faction, alignment);
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
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			GOTCustomWaypointLogger.logCreate(entityplayer, waypoint);
		}
		GOTCustomWaypoint shareCopy = waypoint.createCopyOfShared(playerUUID);
		List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
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

	public void addFellowship(GOTFellowship fs) {
		if (fs.containsPlayer(playerUUID)) {
			UUID fsID = fs.getFellowshipID();
			if (!fellowshipIDs.contains(fsID)) {
				fellowshipIDs.add(fsID);
				markDirty();
				sendFellowshipPacket(fs);
				addSharedCustomWaypointsFromAllIn(fs.getFellowshipID());
			}
		}
	}

	public void addFellowshipInvite(GOTFellowship fs, UUID inviterUUID, String inviterUsername) {
		UUID fsID = fs.getFellowshipID();
		boolean hasInviteAlready = false;
		for (GOTFellowshipInvite invite : fellowshipInvites) {
			if (invite.fellowshipID.equals(fsID)) {
				hasInviteAlready = true;
				break;
			}
		}
		if (!hasInviteAlready) {
			fellowshipInvites.add(new GOTFellowshipInvite(fsID, inviterUUID));
			markDirty();
			sendFellowshipInvitePacket(fs);
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				fs.sendNotification(entityplayer, "got.gui.fellowships.notifyInvite", inviterUsername);
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

	public void addOrUpdateClientFellowship(GOTFellowshipClient fs) {
		UUID fsID = fs.getFellowshipID();
		GOTFellowshipClient inList = null;
		for (GOTFellowshipClient fsInList : fellowshipsClient) {
			if (fsInList.getFellowshipID().equals(fsID)) {
				inList = fsInList;
				break;
			}
		}
		if (inList != null) {
			inList.updateDataFrom(fs);
		} else {
			fellowshipsClient.add(fs);
		}
	}

	public void addOrUpdateClientFellowshipInvite(GOTFellowshipClient fs) {
		UUID fsID = fs.getFellowshipID();
		GOTFellowshipClient inList = null;
		for (GOTFellowshipClient fsInList : fellowshipInvitesClient) {
			if (fsInList.getFellowshipID().equals(fsID)) {
				inList = fsInList;
				break;
			}
		}
		if (inList != null) {
			inList.updateDataFrom(fs);
		} else {
			fellowshipInvitesClient.add(fs);
		}
	}

	public void addOrUpdateSharedCustomWaypoint(GOTCustomWaypoint waypoint) {
		if (!waypoint.isShared()) {
			FMLLog.warning("GOT: Warning! Tried to cache a shared custom waypoint with no owner!");
			return;
		}
		if (waypoint.getSharingPlayerID().equals(playerUUID)) {
			FMLLog.warning("GOT: Warning! Tried to share a custom waypoint to its own player (%s)!", playerUUID.toString());
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
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public int addSharedCustomWaypointsFrom(UUID onlyOneFellowshipID, List<UUID> checkSpecificPlayers) {
		List<UUID> checkFellowshipIDs;
		if (onlyOneFellowshipID != null) {
			checkFellowshipIDs = new ArrayList<>();
			checkFellowshipIDs.add(onlyOneFellowshipID);
		} else {
			checkFellowshipIDs = fellowshipIDs;
		}
		List<UUID> checkFellowPlayerIDs = new ArrayList<>();
		if (checkSpecificPlayers != null) {
			for (UUID player : checkSpecificPlayers) {
				if (!player.equals(playerUUID)) {
					checkFellowPlayerIDs.add(player);
				}
			}
		} else {
			for (UUID fsID : checkFellowshipIDs) {
				GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
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
			List<GOTCustomWaypoint> cwps = pd.getCustomWaypoints();
			for (GOTCustomWaypoint waypoint : cwps) {
				boolean inSharedFellowship = false;
				for (UUID fsID : checkFellowshipIDs) {
					if (waypoint.hasSharedFellowship(fsID)) {
						inSharedFellowship = true;
						break;
					}
				}
				if (inSharedFellowship) {
					addOrUpdateSharedCustomWaypoint(waypoint.createCopyOfShared(player));
				}
			}
		}
		return checkFellowPlayerIDs.size();
	}

	public void addSharedCustomWaypointsFromAllFellowships() {
		addSharedCustomWaypointsFrom(null, null);
	}

	public void addSharedCustomWaypointsFromAllIn(UUID onlyOneFellowshipID) {
		addSharedCustomWaypointsFrom(onlyOneFellowshipID, null);
	}

	public boolean anyMatchingFellowshipNames(String name, boolean client) {
		name = StringUtils.strip(name).toLowerCase();
		if (client) {
			for (GOTFellowshipClient fs : fellowshipsClient) {
				String otherName = fs.getName();
				otherName = StringUtils.strip(otherName).toLowerCase();
				if (name.equals(otherName)) {
					return true;
				}
			}
		} else {
			for (UUID fsID : fellowshipIDs) {
				GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
				if (fs != null) {
					String otherName = fs.getName();
					otherName = StringUtils.strip(otherName).toLowerCase();
					if (name.equals(otherName)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void cancelFastTravel() {
		if (targetFTWaypoint != null) {
			setTargetFTWaypoint(null);
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.motion"));
			}
		}
	}

	public boolean canCreateFellowships(boolean client) {
		int max = getMaxLeadingFellowships();
		int leading = 0;
		if (client) {
			for (GOTFellowshipClient fs : fellowshipsClient) {
				if (fs.isOwned()) {
					leading++;
					if (leading >= max) {
						return false;
					}
				}
			}
		} else {
			for (UUID fsID : fellowshipIDs) {
				GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
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
				for (EntityLiving entityliving : entities) {
					if (entityliving.getAttackTarget() == entityplayer) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public boolean canMakeNewPledge() {
		return pledgeBreakCooldown <= 0;
	}

	public boolean canPledgeTo(GOTFaction fac) {
		if (fac.isPlayableAlignmentFaction()) {
			return hasPledgeAlignment(fac);
		}
		return false;
	}

	public void checkAlignmentAchievements(GOTFaction faction, float prevAlignment) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			float alignment = getAlignment(faction);
			faction.checkAlignmentAchievements(entityplayer, alignment);
		}
	}

	public float checkBonusForPledgeEnemyLimit(GOTFaction fac, float bonus) {
		if (isPledgeEnemyAlignmentLimited(fac)) {
			float limit;
			float alignment = getAlignment(fac);
			limit = getPledgeEnemyAlignmentLimit(fac);
			if (alignment > limit) {
				bonus = 0.0f;
			} else if (alignment + bonus > limit) {
				bonus = limit - alignment;
			}
		}
		return bonus;
	}

	public void checkCustomWaypointsSharedBy(List<UUID> checkSpecificPlayers) {
		List<GOTCustomWaypoint> removes = new ArrayList<>();
		for (GOTCustomWaypoint waypoint : customWaypointsShared) {
			UUID waypointSharingPlayer = waypoint.getSharingPlayerID();
			if (checkSpecificPlayers == null || checkSpecificPlayers.contains(waypointSharingPlayer)) {
				GOTCustomWaypoint wpOriginal = GOTLevelData.getData(waypointSharingPlayer).getCustomWaypointByID(waypoint.getID());
				if (wpOriginal != null) {
					List<UUID> sharedFellowPlayers = wpOriginal.getPlayersInAllSharedFellowships();
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

	public void checkCustomWaypointsSharedFromFellowships() {
		checkCustomWaypointsSharedBy(null);
	}

	public void checkIfStillWaypointSharerForFellowship(GOTFellowship fs) {
		if (!hasAnyWaypointsSharedToFellowship(fs)) {
			fs.markIsWaypointSharer(playerUUID, false);
		}
	}

	public void completeMiniQuest(GOTMiniQuest quest) {
		if (miniQuests.remove(quest)) {
			addMiniQuestCompleted(quest);
			completedMiniquestCount++;
			getFactionData(quest.entityFaction).completeMiniQuest();
			markDirty();
			GOT.proxy.setTrackedQuest(quest);
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTPacketMiniquestRemove packet = new GOTPacketMiniquestRemove(quest, false, true);
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			}
		} else {
			FMLLog.warning("Warning: Attempted to remove a miniquest which does not belong to the player data");
		}
	}

	public void createFellowship(String name, boolean normalCreation) {
		if (normalCreation && (!GOTConfig.enableFellowshipCreation || !canCreateFellowships(false))) {
			return;
		}
		if (!anyMatchingFellowshipNames(name, false)) {
			GOTFellowship fellowship = new GOTFellowship(playerUUID, name);
			fellowship.createAndRegister();
		}
	}

	public void customWaypointAddSharedFellowship(GOTCustomWaypoint waypoint, GOTFellowship fs) {
		UUID fsID = fs.getFellowshipID();
		waypoint.addSharedFellowship(fsID);
		markDirty();
		fs.markIsWaypointSharer(playerUUID, true);
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketShareCWPClient packet = waypoint.getClientAddFellowshipPacket(fsID);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
		GOTCustomWaypoint shareCopy = waypoint.createCopyOfShared(playerUUID);
		for (UUID player : fs.getAllPlayerUUIDs()) {
			if (!player.equals(playerUUID)) {
				GOTLevelData.getData(player).addOrUpdateSharedCustomWaypoint(shareCopy);
			}
		}
	}

	public void customWaypointAddSharedFellowshipClient(GOTCustomWaypoint waypoint, GOTFellowshipClient fs) {
		waypoint.addSharedFellowship(fs.getFellowshipID());
	}

	public void customWaypointRemoveSharedFellowship(GOTCustomWaypoint waypoint, GOTFellowship fs) {
		UUID fsID = fs.getFellowshipID();
		waypoint.removeSharedFellowship(fsID);
		markDirty();
		checkIfStillWaypointSharerForFellowship(fs);
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketShareCWPClient packet = waypoint.getClientRemoveFellowshipPacket(fsID);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
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

	public void customWaypointRemoveSharedFellowshipClient(GOTCustomWaypoint waypoint, UUID fsID) {
		waypoint.removeSharedFellowship(fsID);
	}

	public void disbandFellowship(GOTFellowship fs, String disbanderUsername) {
		if (fs.isOwner(playerUUID)) {
			List<UUID> memberUUIDs = new ArrayList<>(fs.getMemberUUIDs());
			fs.setDisbandedAndRemoveAllMembers();
			removeFellowship(fs);
			for (UUID memberID : memberUUIDs) {
				EntityPlayer member = getOtherPlayer(memberID);
				if (member != null && !member.worldObj.isRemote) {
					fs.sendNotification(member, "got.gui.fellowships.notifyDisband", disbanderUsername);
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

	public boolean doesFactionPreventPledge(GOTFaction pledgeFac, GOTFaction otherFac) {
		return pledgeFac.isMortalEnemy(otherFac);
	}

	public <T extends EntityLiving> T fastTravelEntity(WorldServer world, T entity, int i, int j, int k) {
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

	public void fastTravelTo(GOTAbstractWaypoint waypoint) {
		EntityPlayer player = getPlayer();
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) player;
			WorldServer world = (WorldServer) entityplayer.worldObj;
			int startX = MathHelper.floor_double(entityplayer.posX);
			int startZ = MathHelper.floor_double(entityplayer.posZ);
			double range = 256.0D;
			List<EntityLiving> entities = world.getEntitiesWithinAABB(EntityLiving.class, entityplayer.boundingBox.expand(range, range, range));
			Set<EntityLiving> entitiesToTransport = new HashSet<>();
			for (EntityLiving entity : entities) {
				if (!(entity instanceof GOTEntityDragon)) {
					if (entity instanceof GOTEntityNPC) {
						GOTEntityNPC npc = (GOTEntityNPC) entity;
						if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.shouldFollowPlayer()) {
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
			Set<EntityLiving> removes = new HashSet<>();
			for (EntityLiving entity : entitiesToTransport) {
				Entity rider = entity.riddenByEntity;
				if (rider != null && entitiesToTransport.contains(rider)) {
					removes.add(entity);
				}
			}
			entitiesToTransport.removeAll(removes);
			int i = waypoint.getXCoord();
			int k = waypoint.getZCoord();
			world.theChunkProviderServer.provideChunk(i >> 4, k >> 4);
			int j = waypoint.getYCoord(world, i, k);
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
				GOTCustomWaypointLogger.logTravel((EntityPlayer) entityplayer, (GOTCustomWaypoint) waypoint);
			}
		}
	}

	public List<GOTAchievement> getAchievements() {
		return achievements;
	}

	public List<GOTMiniQuest> getActiveMiniQuests() {
		return selectMiniQuests(new MiniQuestSelector.OptionalActive().setActiveOnly());
	}

	public boolean getAdminHideMap() {
		return adminHideMap;
	}

	public int getAlcoholTolerance() {
		return alcoholTolerance;
	}

	public float getAlignment(GOTFaction faction) {
		if (faction.hasFixedAlignment) {
			return faction.fixedAlignment;
		}
		Float alignment = alignments.get(faction);
		return alignment != null ? alignment : 0.0F;
	}

	public List<GOTAbstractWaypoint> getAllAvailableWaypoints() {
		List<GOTAbstractWaypoint> waypoints = new ArrayList<>(GOTWaypoint.listAllWaypoints());
		waypoints.addAll(getCustomWaypoints());
		waypoints.addAll(customWaypointsShared);
		return waypoints;
	}

	public boolean getAskedForJaqen() {
		return askedForJaqen;
	}

	public int getBalance() {
		return balance;
	}

	public GOTFaction getBrokenPledgeFaction() {
		return brokenPledgeFaction;
	}

	public GOTCapes getCape() {
		return cape;
	}

	public GOTFellowship getChatBoundFellowship() {
		if (chatBoundFellowshipID != null) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(chatBoundFellowshipID);
			if (fs != null) {
				return fs;
			}
		}
		return null;
	}

	public UUID getChatBoundFellowshipID() {
		return chatBoundFellowshipID;
	}

	public boolean getCheckedMenu() {
		return checkedMenu;
	}

	public GOTFellowshipClient getClientFellowshipByID(UUID fsID) {
		for (GOTFellowshipClient fs : fellowshipsClient) {
			if (fs.getFellowshipID().equals(fsID)) {
				return fs;
			}
		}
		return null;
	}

	public GOTFellowshipClient getClientFellowshipByName(String fsName) {
		for (GOTFellowshipClient fs : fellowshipsClient) {
			if (fs.getName().equalsIgnoreCase(fsName)) {
				return fs;
			}
		}
		return null;
	}

	public List<GOTFellowshipClient> getClientFellowshipInvites() {
		return fellowshipInvitesClient;
	}

	public List<GOTFellowshipClient> getClientFellowships() {
		return fellowshipsClient;
	}

	public int getCompletedBountyQuests() {
		return completedBountyQuests;
	}

	public int getCompletedMiniQuestsTotal() {
		return completedMiniquestCount;
	}

	public long getCurrentOnlineTime() {
		return MinecraftServer.getServer().worldServerForDimension(0).getTotalWorldTime();
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

	public ChunkCoordinates getDeathPoint() {
		return deathPoint;
	}

	public List<GOTAchievement> getEarnedAchievements(GOTDimension dimension) {
		List<GOTAchievement> earnedAchievements = new ArrayList<>();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null) {
			for (GOTAchievement achievement : achievements) {
				if (achievement.getDimension() == dimension && achievement.canPlayerEarn(entityplayer)) {
					earnedAchievements.add(achievement);
				}
			}
		}
		return earnedAchievements;
	}

	public boolean getEnableConquestKills() {
		return conquestKills;
	}

	public boolean getEnableHiredDeathMessages() {
		return hiredDeathMessages;
	}

	public GOTFactionData getFactionData(GOTFaction faction) {
		GOTFactionData data = factionDataMap.get(faction);
		if (data == null) {
			data = new GOTFactionData(this, faction);
			factionDataMap.put(faction, data);
		}
		return data;
	}

	public GOTFellowship getFellowshipByName(String fsName) {
		for (UUID fsID : fellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
			if (fs != null && fs.getName().equalsIgnoreCase(fsName)) {
				return fs;
			}
		}
		return null;
	}

	public List<UUID> getFellowshipIDs() {
		return fellowshipIDs;
	}

	public List<GOTFellowship> getFellowships() {
		List<GOTFellowship> fellowships = new ArrayList<>();
		for (UUID fsID : fellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
			if (fs != null) {
				fellowships.add(fs);
			}
		}
		return fellowships;
	}

	public boolean getFemRankOverride() {
		return femRankOverride;
	}

	public boolean getFriendlyFire() {
		return friendlyFire;
	}

	public boolean getHideAlignment() {
		return hideAlignment;
	}

	public boolean getHideMapLocation() {
		return hideOnMap;
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

	public int getMaxLeadingFellowships() {
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
			if (quest.questUUID.equals(id)) {
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

	public List<GOTMiniQuest> getMiniQuestsForEntity(GOTEntityNPC npc, boolean activeOnly) {
		return getMiniQuestsForEntityID(npc.getUniqueID(), activeOnly);
	}

	public List<GOTMiniQuest> getMiniQuestsForEntityID(UUID npcID, boolean activeOnly) {
		MiniQuestSelector.EntityId sel = new MiniQuestSelector.EntityId(npcID);
		if (activeOnly) {
			sel.setActiveOnly();
		}
		return selectMiniQuests(sel);
	}

	public List<GOTMiniQuest> getMiniQuestsForFaction(GOTFaction f, boolean activeOnly) {
		MiniQuestSelector.Faction sel = new MiniQuestSelector.Faction(() -> f);
		if (activeOnly) {
			sel.setActiveOnly();
		}
		return selectMiniQuests(sel);
	}

	public int getNextCwpID() {
		return nextCwpID;
	}

	public EntityPlayer getOtherPlayer(UUID uuid) {
		for (WorldServer worldServer : MinecraftServer.getServer().worldServers) {
			EntityPlayer entityplayer = worldServer.func_152378_a(uuid);
			if (entityplayer != null) {
				return entityplayer;
			}
		}
		return null;
	}

	public EntityPlayer getPlayer() {
		World[] searchWorlds = GOT.proxy.isClient() ? new World[] { GOT.proxy.getClientWorld() } : MinecraftServer.getServer().worldServers;
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

	public UUID getPlayerUUID() {
		return playerUUID;
	}

	public int getPledgeBreakCooldown() {
		return pledgeBreakCooldown;
	}

	public int getPledgeBreakCooldownStart() {
		return pledgeBreakCooldownStart;
	}

	public float getPledgeEnemyAlignmentLimit(GOTFaction fac) {
		return 0.0F;
	}

	public GOTFaction getPledgeFaction() {
		return pledgeFaction;
	}

	public GOTPlayerQuestData getQuestData() {
		return questData;
	}

	public GOTFaction getRegionLastViewedFaction(GOTDimension.DimensionRegion region) {
		GOTFaction fac = prevRegionFactions.get(region);
		if (fac == null) {
			fac = region.factionList.get(0);
			prevRegionFactions.put(region, fac);
		}
		return fac;
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

	public boolean getStructuresBanned() {
		return structuresBanned;
	}

	public boolean getTableSwitched() {
		return tableSwitched;
	}

	public GOTAbstractWaypoint getTargetFTWaypoint() {
		return targetFTWaypoint;
	}

	public boolean getTeleportedKW() {
		return teleportedKW;
	}

	public int getTicksUntilFT() {
		return ticksUntilFT;
	}

	public int getTimeSinceFT() {
		return ftSinceTick;
	}

	public GOTMiniQuest getTrackingMiniQuest() {
		if (trackingMiniQuestID == null) {
			return null;
		}
		return getMiniQuestForID(trackingMiniQuestID, false);
	}

	public GOTFaction getViewingFaction() {
		return viewingFaction;
	}

	public int getWaypointFTTime(GOTAbstractWaypoint wp, EntityPlayer entityplayer) {
		int baseMin = GOTLevelData.getWaypointCooldownMin();
		int baseMax = GOTLevelData.getWaypointCooldownMax();
		int useCount = getWPUseCount(wp);
		double dist = entityplayer.getDistance(wp.getXCoord() + 0.5D, wp.getYCoordSaved(), wp.getZCoord() + 0.5D);
		double time = baseMin;
		double added = (baseMax - baseMin) * Math.pow(0.9D, useCount);
		time += added;
		time *= Math.max(1.0D, dist * 1.2E-5D);
		int seconds = (int) Math.round(time);
		seconds = Math.max(seconds, 0);
		return seconds * 20;
	}

	public int getWPUseCount(GOTAbstractWaypoint wp) {
		if (wp instanceof GOTCustomWaypoint) {
			GOTCustomWaypoint cwp = (GOTCustomWaypoint) wp;
			int ID = cwp.getID();
			if (cwp.isShared()) {
				UUID sharingPlayer = cwp.getSharingPlayerID();
				CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
				if (cwpSharedUseCounts.containsKey(key)) {
					return cwpSharedUseCounts.get(key);
				}
			} else if (cwpUseCounts.containsKey(Integer.valueOf(ID))) {
				return cwpUseCounts.get(Integer.valueOf(ID));
			}
		} else if (wpUseCounts.containsKey(wp)) {
			return wpUseCounts.get(wp);
		}
		return 0;
	}

	public void givePureConquestBonus(EntityPlayer entityplayer, GOTFaction bonusFac, GOTFaction enemyFac, float conq, String title, double posX, double posY, double posZ) {
		conq = GOTConquestGrid.onConquestKill(entityplayer, bonusFac, enemyFac, conq);
		getFactionData(bonusFac).addConquest(Math.abs(conq));
		if (conq != 0.0F) {
			GOTAlignmentValues.AlignmentBonus source = new GOTAlignmentValues.AlignmentBonus(0.0F, title);
			GOTPacketAlignmentBonus packet = new GOTPacketAlignmentBonus(bonusFac, getAlignment(bonusFac), new GOTAlignmentBonusMap(), conq, posX, posY, posZ, source);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public boolean hasAchievement(GOTAchievement achievement) {
		for (GOTAchievement a : achievements) {
			if (a.category == achievement.category && a.ID == achievement.ID) {
				return true;
			}
		}
		return false;
	}

	public boolean hasActiveOrCompleteMQType(Class<? extends GOTMiniQuest> type) {
		List<GOTMiniQuest> quests = getMiniQuests();
		List<GOTMiniQuest> questsComplete = getMiniQuestsCompleted();
		List<GOTMiniQuest> allQuests = new ArrayList<>();
		for (GOTMiniQuest q : quests) {
			if (q.isActive()) {
				allQuests.add(q);
			}
		}
		allQuests.addAll(questsComplete);
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

	public boolean hasAnyWaypointsSharedToFellowship(GOTFellowship fs) {
		for (GOTCustomWaypoint waypoint : customWaypoints) {
			if (waypoint.hasSharedFellowship(fs)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPledgeAlignment(GOTFaction fac) {
		float alignment = getAlignment(fac);
		return alignment >= fac.getPledgeAlignment();
	}

	public void hideOrUnhideSharedCustomWaypoint(GOTCustomWaypoint waypoint, boolean hide) {
		if (!waypoint.isShared()) {
			FMLLog.warning("GOT: Warning! Tried to unlock a shared custom waypoint with no owner!");
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
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void incrementNextCwpID() {
		nextCwpID++;
		markDirty();
	}

	public void incrementWPUseCount(GOTAbstractWaypoint wp) {
		setWPUseCount(wp, getWPUseCount(wp) + 1);
	}

	public void invitePlayerToFellowship(GOTFellowship fs, UUID invitedPlayerUUID, String inviterUsername) {
		if (fs.isOwner(playerUUID) || fs.isAdmin(playerUUID)) {
			GOTLevelData.getData(invitedPlayerUUID).addFellowshipInvite(fs, playerUUID, inviterUsername);
		}
	}

	public boolean isFTRegionUnlocked(GOTWaypoint.Region region) {
		return unlockedFTRegions.contains(region);
	}

	public boolean isPledgedTo(GOTFaction fac) {
		return pledgeFaction == fac;
	}

	public boolean isPledgeEnemyAlignmentLimited(GOTFaction fac) {
		return pledgeFaction != null && doesFactionPreventPledge(pledgeFaction, fac);
	}

	public boolean isSiegeActive() {
		return siegeActiveTime > 0;
	}

	public void leaveFellowship(GOTFellowship fs) {
		if (!fs.isOwner(playerUUID)) {
			fs.removeMember(playerUUID);
			if (fellowshipIDs.contains(fs.getFellowshipID())) {
				removeFellowship(fs);
			}
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				EntityPlayer owner = getOtherPlayer(fs.getOwner());
				if (owner != null) {
					fs.sendNotification(owner, "got.gui.fellowships.notifyLeave", entityplayer.getCommandSenderName());
				}
			}
		}
	}

	public List<String> listAllFellowshipNames() {
		List<String> list = new ArrayList<>();
		for (UUID fsID : fellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
			if (fs != null && fs.containsPlayer(playerUUID)) {
				list.add(fs.getName());
			}
		}
		return list;
	}

	public List<String> listAllLeadingFellowshipNames() {
		List<String> list = new ArrayList<>();
		for (UUID fsID : fellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
			if (fs != null && fs.isOwner(playerUUID)) {
				list.add(fs.getName());
			}
		}
		return list;
	}

	public void load(NBTTagCompound playerData) {
		alignments.clear();
		NBTTagList alignmentTags = playerData.getTagList("AlignmentMap", 10);
		for (int i = 0; i < alignmentTags.tagCount(); i++) {
			NBTTagCompound nbt = alignmentTags.getCompoundTagAt(i);
			GOTFaction faction = GOTFaction.forName(nbt.getString("Faction"));
			if (faction != null) {
				float alignment;
				if (nbt.hasKey("Alignment")) {
					alignment = nbt.getInteger("Alignment");
				} else {
					alignment = nbt.getFloat("AlignF");
				}
				alignments.put(faction, alignment);
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
		hideAlignment = playerData.getBoolean("HideAlignment");
		takenAlignmentRewards.clear();
		NBTTagList takenRewardsTags = playerData.getTagList("TakenAlignmentRewards", 10);
		for (int m = 0; m < takenRewardsTags.tagCount(); m++) {
			NBTTagCompound nbt = takenRewardsTags.getCompoundTagAt(m);
			GOTFaction faction = GOTFaction.forName(nbt.getString("Faction"));
			if (faction != null) {
				takenAlignmentRewards.add(faction);
			}
		}
		pledgeFaction = null;
		if (playerData.hasKey("PledgeFac")) {
			pledgeFaction = GOTFaction.forName(playerData.getString("PledgeFac"));
		}
		pledgeKillCooldown = playerData.getInteger("PledgeKillCD");
		pledgeBreakCooldown = playerData.getInteger("PledgeBreakCD");
		pledgeBreakCooldownStart = playerData.getInteger("PledgeBreakCDStart");
		brokenPledgeFaction = null;
		if (playerData.hasKey("BrokenPledgeFac")) {
			brokenPledgeFaction = GOTFaction.forName(playerData.getString("BrokenPledgeFac"));
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
		if (playerData.hasKey("DeathX") && playerData.hasKey("DeathY") && playerData.hasKey("DeathZ")) {
			deathPoint = new ChunkCoordinates(playerData.getInteger("DeathX"), playerData.getInteger("DeathY"), playerData.getInteger("DeathZ"));
			if (playerData.hasKey("DeathDim")) {
				deathDim = playerData.getInteger("DeathDim");
			} else {
				deathDim = GOTDimension.GAME_OF_THRONES.dimensionID;
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
			GOTBiome[] biomeList = GOTDimension.GAME_OF_THRONES.biomeList;
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
		if (playerData.hasKey("PlayerTitle")) {
			GOTTitle title = GOTTitle.forName(playerData.getString("PlayerTitle"));
			if (title != null) {
				int colorCode = playerData.getInteger("PlayerTitleColor");
				EnumChatFormatting color = GOTTitle.PlayerTitle.colorForID(colorCode);
				playerTitle = new GOTTitle.PlayerTitle(title, color);
			}
		}
		if (playerData.hasKey("FemRankOverride")) {
			femRankOverride = playerData.getBoolean("FemRankOverride");
		}
		if (playerData.hasKey("FTSince")) {
			ftSinceTick = playerData.getInteger("FTSince");
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
			if (sharingPlayer != null) {
				int ID = nbt.getInteger("CustomID");
				CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
				cwpSharedUnlocked.add(key);
			}
		}
		cwpSharedHidden.clear();
		NBTTagList cwpSharedHiddenTags = playerData.getTagList("CWPSharedHidden", 10);
		for (int i8 = 0; i8 < cwpSharedHiddenTags.tagCount(); i8++) {
			NBTTagCompound nbt = cwpSharedHiddenTags.getCompoundTagAt(i8);
			UUID sharingPlayer = UUID.fromString(nbt.getString("SharingPlayer"));
			if (sharingPlayer != null) {
				int ID = nbt.getInteger("CustomID");
				CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
				cwpSharedHidden.add(key);
			}
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
			if (sharingPlayer != null) {
				int ID = nbt.getInteger("CustomID");
				CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
				int count = nbt.getInteger("Count");
				cwpSharedUseCounts.put(key, count);
			}
		}
		nextCwpID = 20000;
		if (playerData.hasKey("NextCWPID")) {
			nextCwpID = playerData.getInteger("NextCWPID");
		}
		fellowshipIDs.clear();
		NBTTagList fellowshipTags = playerData.getTagList("Fellowships", 10);
		for (int i12 = 0; i12 < fellowshipTags.tagCount(); i12++) {
			NBTTagCompound nbt = fellowshipTags.getCompoundTagAt(i12);
			UUID fsID = UUID.fromString(nbt.getString("ID"));
			if (fsID != null) {
				fellowshipIDs.add(fsID);
			}
		}
		fellowshipInvites.clear();
		NBTTagList fellowshipInviteTags = playerData.getTagList("FellowshipInvites", 10);
		for (int i13 = 0; i13 < fellowshipInviteTags.tagCount(); i13++) {
			NBTTagCompound nbt = fellowshipInviteTags.getCompoundTagAt(i13);
			UUID fsID = UUID.fromString(nbt.getString("ID"));
			if (fsID != null) {
				UUID inviterID = null;
				if (nbt.hasKey("InviterID")) {
					inviterID = UUID.fromString(nbt.getString("InviterID"));
				}
				fellowshipInvites.add(new GOTFellowshipInvite(fsID, inviterID));
			}
		}
		chatBoundFellowshipID = null;
		if (playerData.hasKey("ChatBoundFellowship")) {
			UUID fsID = UUID.fromString(playerData.getString("ChatBoundFellowship"));
			if (fsID != null) {
				chatBoundFellowshipID = fsID;
			}
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
				GOTPacketWaypointRegion packet = new GOTPacketWaypointRegion(region, false);
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			}
		}
	}

	public void markDirty() {
		needsSave = true;
	}

	public boolean needsSave() {
		return needsSave;
	}

	public void onPledgeKill(EntityPlayer entityplayer) {
		pledgeKillCooldown += 24000;
		markDirty();
		if (pledgeKillCooldown > 24000) {
			revokePledgeFaction(entityplayer, false);
		} else if (pledgeFaction != null) {
			ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("got.chat.pledgeKillWarn", pledgeFaction.factionName());
			entityplayer.addChatMessage(chatComponentTranslation);
		}
	}

	public void onUpdate(EntityPlayerMP entityplayer, WorldServer world) {
		pdTick++;
		GOTDimension.DimensionRegion currentRegion = viewingFaction.factionRegion;
		GOTDimension currentDim = GOTDimension.getCurrentDimensionWithFallback(world);
		if (currentRegion.getDimension() != currentDim) {
			currentRegion = currentDim.dimensionRegions.get(0);
			setViewingFaction(getRegionLastViewedFaction(currentRegion));
		}
		questData.onUpdate(entityplayer, world);
		if (!isSiegeActive()) {
			runAchievementChecks(entityplayer, world);
		}
		if (!checkedMenu) {
			GOTPacketMenuPrompt packet = new GOTPacketMenuPrompt(GOTPacketMenuPrompt.Type.MENU);
			GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
		}
		if (playerTitle != null && !playerTitle.getTitle().canPlayerUse(entityplayer)) {
			ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("got.chat.loseTitle", playerTitle.getFullTitleComponent(entityplayer));
			entityplayer.addChatMessage(chatComponentTranslation);
			setPlayerTitle(null);
		}
		if (pledgeKillCooldown > 0) {
			pledgeKillCooldown--;
			if (pledgeKillCooldown == 0 || isTimerAutosaveTick()) {
				markDirty();
			}
		}
		if (pledgeBreakCooldown > 0) {
			setPledgeBreakCooldown(pledgeBreakCooldown - 1);
		}
		if (trackingMiniQuestID != null && getTrackingMiniQuest() == null) {
			setTrackingMiniQuest(null);
		}
		List<GOTMiniQuest> activeMiniquests = getActiveMiniQuests();
		for (GOTMiniQuest quest : activeMiniquests) {
			quest.onPlayerTick(entityplayer);
		}
		if (!bountiesPlaced.isEmpty()) {
			for (GOTFaction fac : bountiesPlaced) {
				ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("got.chat.bountyPlaced", fac.factionName());
				chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				entityplayer.addChatMessage(chatComponentTranslation);
			}
			bountiesPlaced.clear();
			markDirty();
		}
		setTimeSinceFT(ftSinceTick + 1);
		if (targetFTWaypoint != null) {
			if (entityplayer.isPlayerSleeping()) {
				entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.inBed"));
				setTargetFTWaypoint(null);
			} else if (ticksUntilFT > 0) {
				int seconds = ticksUntilFT / 20;
				if (ticksUntilFT == ticksUntilFT_max) {
					entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.travelTicksStart", Integer.valueOf(seconds)));
				} else if (ticksUntilFT % 20 == 0 && seconds <= 5) {
					entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.travelTicks", Integer.valueOf(seconds)));
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
				List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, entityplayer.boundingBox.expand(range, range, range));
				for (Object obj : entities) {
					Entity entity = (Entity) obj;
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
			GOTBiome biome = (GOTBiome) world.provider.getBiomeGenForCoords(i, k);
			if (biome.biomeDimension == GOTDimension.GAME_OF_THRONES) {
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

	public void rejectFellowshipInvite(GOTFellowship fs) {
		UUID fsID = fs.getFellowshipID();
		GOTFellowshipInvite existingInvite = null;
		for (GOTFellowshipInvite invite : fellowshipInvites) {
			if (invite.fellowshipID.equals(fsID)) {
				existingInvite = invite;
				break;
			}
		}
		if (existingInvite != null) {
			fellowshipInvites.remove(existingInvite);
			markDirty();
			sendFellowshipInviteRemovePacket(fs);
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

	public void removeClientFellowship(UUID fsID) {
		GOTFellowshipClient inList = null;
		for (GOTFellowshipClient fsInList : fellowshipsClient) {
			if (fsInList.getFellowshipID().equals(fsID)) {
				inList = fsInList;
				break;
			}
		}
		if (inList != null) {
			fellowshipsClient.remove(inList);
		}
	}

	public void removeClientFellowshipInvite(UUID fsID) {
		GOTFellowshipClient inList = null;
		for (GOTFellowshipClient fsInList : fellowshipInvitesClient) {
			if (fsInList.getFellowshipID().equals(fsID)) {
				inList = fsInList;
				break;
			}
		}
		if (inList != null) {
			fellowshipInvitesClient.remove(inList);
		}
	}

	public void removeCustomWaypoint(GOTCustomWaypoint waypoint) {
		if (customWaypoints.remove(waypoint)) {
			markDirty();
			for (UUID fsID : waypoint.getSharedFellowshipIDs()) {
				GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
				if (fs != null) {
					checkIfStillWaypointSharerForFellowship(fs);
				}
			}
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTPacketDeleteCWPClient packet = waypoint.getClientDeletePacket();
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
				GOTCustomWaypointLogger.logDelete(entityplayer, waypoint);
			}
			GOTCustomWaypoint shareCopy = waypoint.createCopyOfShared(playerUUID);
			List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
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

	public void removeFellowship(GOTFellowship fs) {
		if (fs.isDisbanded() || !fs.containsPlayer(playerUUID)) {
			UUID fsID = fs.getFellowshipID();
			if (fellowshipIDs.contains(fsID)) {
				fellowshipIDs.remove(fsID);
				markDirty();
				sendFellowshipRemovePacket(fs);
				unshareFellowshipFromOwnCustomWaypoints(fs);
				checkCustomWaypointsSharedFromFellowships();
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
				GOTPacketMiniquestRemove packet = new GOTPacketMiniquestRemove(quest, quest.isCompleted(), false);
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			}
		} else {
			FMLLog.warning("Warning: Attempted to remove a miniquest which does not belong to the player data");
		}
	}

	public void removePlayerFromFellowship(GOTFellowship fs, UUID player, String removerUsername) {
		if (fs.isOwner(playerUUID) || fs.isAdmin(playerUUID)) {
			fs.removeMember(player);
			EntityPlayer removed = getOtherPlayer(player);
			if (removed != null && !removed.worldObj.isRemote) {
				fs.sendNotification(removed, "got.gui.fellowships.notifyRemove", removerUsername);
			}
		}
	}

	public void removeSharedCustomWaypoint(GOTCustomWaypoint waypoint) {
		if (!waypoint.isShared()) {
			FMLLog.warning("GOT: Warning! Tried to remove a shared custom waypoint with no owner!");
			return;
		}
		GOTCustomWaypoint existing = null;
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
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			}
		} else {
			FMLLog.warning("GOT: Warning! Tried to remove a shared custom waypoint that does not exist!");
		}
	}

	public void renameCustomWaypoint(GOTCustomWaypoint waypoint, String newName) {
		waypoint.rename(newName);
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketRenameCWPClient packet = waypoint.getClientRenamePacket();
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			GOTCustomWaypointLogger.logRename(entityplayer, waypoint);
		}
		GOTCustomWaypoint shareCopy = waypoint.createCopyOfShared(playerUUID);
		List<UUID> sharedPlayers = shareCopy.getPlayersInAllSharedFellowships();
		for (UUID sharedPlayerUUID : sharedPlayers) {
			EntityPlayer sharedPlayer = getOtherPlayer(sharedPlayerUUID);
			if (sharedPlayer != null && !sharedPlayer.worldObj.isRemote) {
				GOTLevelData.getData(sharedPlayerUUID).renameSharedCustomWaypoint(shareCopy, newName);
			}
		}
	}

	public void renameCustomWaypointClient(GOTCustomWaypoint waypoint, String newName) {
		waypoint.rename(newName);
	}

	public void renameFellowship(GOTFellowship fs, String name) {
		if (fs.isOwner(playerUUID)) {
			fs.setName(name);
		}
	}

	public void renameSharedCustomWaypoint(GOTCustomWaypoint waypoint, String newName) {
		if (!waypoint.isShared()) {
			FMLLog.warning("GOT: Warning! Tried to rename a shared custom waypoint with no owner!");
			return;
		}
		waypoint.rename(newName);
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketRenameCWPClient packet = waypoint.getClientRenamePacketShared();
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void revokePledgeFaction(EntityPlayer entityplayer, boolean intentional) {
		GOTFaction wasPledge = pledgeFaction;
		float pledgeLvl = wasPledge.getPledgeAlignment();
		float prevAlign = getAlignment(wasPledge);
		float diff = prevAlign - pledgeLvl;
		float cd = diff / 5000.0F;
		cd = MathHelper.clamp_float(cd, 0.0F, 1.0F);
		int cdTicks = 36000;
		cdTicks += Math.round(cd * 150.0F * 60.0F * 20.0F);
		setPledgeFaction(null);
		setBrokenPledgeFaction(wasPledge);
		setPledgeBreakCooldown(cdTicks);
		World world = entityplayer.worldObj;
		if (!world.isRemote) {
			GOTFactionRank rank = wasPledge.getRank(prevAlign);
			GOTFactionRank rankBelow = wasPledge.getRankBelow(rank);
			GOTFactionRank rankBelow2 = wasPledge.getRankBelow(rankBelow);
			float newAlign = rankBelow2.alignment;
			newAlign = Math.max(newAlign, pledgeLvl / 2.0F);
			float alignPenalty = newAlign - prevAlign;
			if (alignPenalty < 0.0F) {
				GOTAlignmentValues.AlignmentBonus penalty = GOTAlignmentValues.createPledgePenalty(alignPenalty);
				double alignX = 0.0D;
				double alignY = 0.0D;
				double alignZ = 0.0D;
				double lookRange = 2.0D;
				Vec3 posEye = Vec3.createVectorHelper(entityplayer.posX, entityplayer.boundingBox.minY + entityplayer.getEyeHeight(), entityplayer.posZ);
				Vec3 look = entityplayer.getLook(1.0F);
				Vec3 posSight = posEye.addVector(look.xCoord * lookRange, look.yCoord * lookRange, look.zCoord * lookRange);
				MovingObjectPosition mop = world.rayTraceBlocks(posEye, posSight);
				if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					alignX = mop.blockX + 0.5D;
					alignY = mop.blockY + 0.5D;
					alignZ = mop.blockZ + 0.5D;
				} else {
					alignX = posSight.xCoord;
					alignY = posSight.yCoord;
					alignZ = posSight.zCoord;
				}
				addAlignment(entityplayer, penalty, wasPledge, alignX, alignY, alignZ);
			}
			world.playSoundAtEntity(entityplayer, "got:event.unpledge", 1.0F, 1.0F);
			if (intentional) {
				ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("got.chat.unpledge", wasPledge.factionName());
				entityplayer.addChatMessage(chatComponentTranslation);
			} else {
				ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("got.chat.autoUnpledge", wasPledge.factionName());
				entityplayer.addChatMessage(chatComponentTranslation);
			}
			checkAlignmentAchievements(wasPledge, prevAlign);
		}
	}

	public void runAchievementChecks(EntityPlayer entityplayer, World world) {
		int i = MathHelper.floor_double(entityplayer.posX);
		MathHelper.floor_double(entityplayer.boundingBox.minY);
		int k = MathHelper.floor_double(entityplayer.posZ);
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
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
		if (entityplayer.dimension == GOTDimension.GAME_OF_THRONES.dimensionID) {
			addAchievement(GOTAchievement.enterKnownWorld);
		}
		if (entityplayer.inventory.hasItem(GOTRegistry.pouch)) {
			addAchievement(GOTAchievement.getPouch);
		}
		Set<Block> tables = new HashSet();
		int crossbowBolts = 0;
		for (ItemStack item : entityplayer.inventory.mainInventory) {
			if (item != null && item.getItem() instanceof ItemBlock) {
				Block block = Block.getBlockFromItem(item.getItem());
				if (block instanceof GOTBlockCraftingTable) {
					tables.add(block);
				}
			}
			if (item != null && item.getItem() instanceof GOTItemCrossbowBolt) {
				crossbowBolts += item.stackSize;
			}
		}
		if (tables.size() >= 10) {
			addAchievement(GOTAchievement.collectCraftingTables);
		}
		if (crossbowBolts >= 128) {
			addAchievement(GOTAchievement.collectCrossbowBolts);
		}
		if (!hasAchievement(GOTAchievement.hundreds) && pdTick % 20 == 0) {
			int hiredUnits = 0;
			List<GOTEntityNPC> nearbyNPCs = world.getEntitiesWithinAABB(GOTEntityNPC.class, entityplayer.boundingBox.expand(64.0D, 64.0D, 64.0D));
			for (GOTEntityNPC npc : nearbyNPCs) {
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					hiredUnits++;
				}
			}
			if (hiredUnits >= 100) {
				addAchievement(GOTAchievement.hundreds);
			}
		}
		if (!hasAchievement(GOTAchievement.hireGoldenCompany) && pdTick % 20 == 0) {
			int hiredUnits = 0;
			List<GOTEntityGoldenMan> nearbyNPCs = world.getEntitiesWithinAABB(GOTEntityGoldenMan.class, entityplayer.boundingBox.expand(64.0D, 64.0D, 64.0D));
			for (GOTEntityNPC npc : nearbyNPCs) {
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
					hiredUnits++;
				}
			}
			if (hiredUnits >= 10) {
				addAchievement(GOTAchievement.hireGoldenCompany);
			}
		}
		ArmorMaterial fullMaterial;
		ItemArmor.ArmorMaterial material = getFullArmorMaterial(entityplayer);
		if (GOTAchievement.armorAchievements.containsKey(material)) {
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.armorAchievements.get(material));
		}
		fullMaterial = getBodyMaterial(entityplayer);
		if (fullMaterial != null && fullMaterial == GOTMaterial.HAND) {
			addAchievement(GOTAchievement.wearFullHand);
		}
		fullMaterial = getHelmetMaterial(entityplayer);
		if (fullMaterial != null && fullMaterial == GOTMaterial.HELMET) {
			addAchievement(GOTAchievement.wearFullHelmet);
		}
		fullMaterial = getFullArmorMaterialWithoutHelmet(entityplayer);
		if (fullMaterial != null && fullMaterial == GOTMaterial.MOSSOVY) {
			addAchievement(GOTAchievement.wearFullMossovy);
		}
		fullMaterial = getFullArmorMaterialWithoutHelmet(entityplayer);
		if (fullMaterial != null && fullMaterial == GOTMaterial.ICE) {
			addAchievement(GOTAchievement.wearFullWhitewalkers);
		}
	}

	public void save(NBTTagCompound playerData) {
		NBTTagList alignmentTags = new NBTTagList();
		for (Map.Entry<GOTFaction, Float> entry : alignments.entrySet()) {
			GOTFaction faction = entry.getKey();
			float alignment = entry.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Faction", faction.codeName());
			nbt.setFloat("AlignF", alignment);
			alignmentTags.appendTag(nbt);
		}
		playerData.setTag("AlignmentMap", alignmentTags);
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
		playerData.setBoolean("HideAlignment", hideAlignment);
		NBTTagList takenRewardsTags = new NBTTagList();
		for (GOTFaction faction : takenAlignmentRewards) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Faction", faction.codeName());
			takenRewardsTags.appendTag(nbt);
		}
		playerData.setTag("TakenAlignmentRewards", takenRewardsTags);
		if (pledgeFaction != null) {
			playerData.setString("PledgeFac", pledgeFaction.codeName());
		}
		playerData.setInteger("PledgeKillCD", pledgeKillCooldown);
		playerData.setInteger("PledgeBreakCD", pledgeBreakCooldown);
		playerData.setInteger("PledgeBreakCDStart", pledgeBreakCooldownStart);
		if (brokenPledgeFaction != null) {
			playerData.setString("BrokenPledgeFac", brokenPledgeFaction.codeName());
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
			nbt.setString("Category", achievement.category.name());
			nbt.setInteger("ID", achievement.ID);
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
			playerData.setString("PlayerTitle", playerTitle.getTitle().getTitleName());
			playerData.setInteger("PlayerTitleColor", playerTitle.getColor().getFormattingCode());
		}
		playerData.setBoolean("FemRankOverride", femRankOverride);
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
			nbt.setString("SharingPlayer", key.sharingPlayer.toString());
			nbt.setInteger("CustomID", key.waypointID);
			cwpSharedUnlockedTags.appendTag(nbt);
		}
		playerData.setTag("CWPSharedUnlocked", cwpSharedUnlockedTags);
		NBTTagList cwpSharedHiddenTags = new NBTTagList();
		for (CWPSharedKey key : cwpSharedHidden) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("SharingPlayer", key.sharingPlayer.toString());
			nbt.setInteger("CustomID", key.waypointID);
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
			nbt.setString("SharingPlayer", key.sharingPlayer.toString());
			nbt.setInteger("CustomID", key.waypointID);
			nbt.setInteger("Count", count);
			cwpSharedUseTags.appendTag(nbt);
		}
		playerData.setTag("CWPSharedUses", cwpSharedUseTags);
		playerData.setInteger("NextCWPID", nextCwpID);
		NBTTagList fellowshipTags = new NBTTagList();
		for (UUID fsID : fellowshipIDs) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("ID", fsID.toString());
			fellowshipTags.appendTag(nbt);
		}
		playerData.setTag("Fellowships", fellowshipTags);
		NBTTagList fellowshipInviteTags = new NBTTagList();
		for (GOTFellowshipInvite invite : fellowshipInvites) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("ID", invite.fellowshipID.toString());
			if (invite.inviterID != null) {
				nbt.setString("InviterID", invite.inviterID.toString());
			}
			fellowshipInviteTags.appendTag(nbt);
		}
		playerData.setTag("FellowshipInvites", fellowshipInviteTags);
		if (chatBoundFellowshipID != null) {
			playerData.setString("ChatBoundFellowship", chatBoundFellowshipID.toString());
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

	public List<GOTMiniQuest> selectMiniQuests(MiniQuestSelector selector) {
		List<GOTMiniQuest> ret = new ArrayList<>();
		List<GOTMiniQuest> threadSafe = new ArrayList<>(miniQuests);
		for (GOTMiniQuest quest : threadSafe) {
			if (selector.include(quest)) {
				ret.add(quest);
			}
		}
		return ret;
	}

	public void sendAchievementPacket(EntityPlayerMP entityplayer, GOTAchievement achievement, boolean display) {
		GOTPacketAchievement packet = new GOTPacketAchievement(achievement, display);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public void sendAchievementRemovePacket(EntityPlayerMP entityplayer, GOTAchievement achievement) {
		GOTPacketAchievementRemove packet = new GOTPacketAchievementRemove(achievement);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public void sendAlignmentBonusPacket(GOTAlignmentValues.AlignmentBonus source, GOTFaction faction, float prevMainAlignment, GOTAlignmentBonusMap factionMap, float conqBonus, double posX, double posY, double posZ) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null) {
			GOTPacketAlignmentBonus packet = new GOTPacketAlignmentBonus(faction, prevMainAlignment, factionMap, conqBonus, posX, posY, posZ, source);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void sendFellowshipInvitePacket(GOTFellowship fs) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketFellowship packet = new GOTPacketFellowship(this, fs, true);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void sendFellowshipInviteRemovePacket(GOTFellowship fs) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketFellowshipRemove packet = new GOTPacketFellowshipRemove(fs, true);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void sendFellowshipPacket(GOTFellowship fs) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketFellowship packet = new GOTPacketFellowship(this, fs, false);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void sendFellowshipRemovePacket(GOTFellowship fs) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketFellowshipRemove packet = new GOTPacketFellowshipRemove(fs, false);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void sendFTBouncePacket(EntityPlayerMP entityplayer) {
		GOTPacketFTBounceClient packet = new GOTPacketFTBounceClient();
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public void sendFTPacket(EntityPlayerMP entityplayer, GOTAbstractWaypoint waypoint, int startX, int startZ) {
		GOTPacketFTScreen packet = new GOTPacketFTScreen(waypoint, startX, startZ);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public void sendMessageIfNotReceived(GOTGuiMessageTypes message) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			Boolean sent = sentMessageTypes.get(message);
			if (sent == null) {
				sent = false;
				sentMessageTypes.put(message, sent);
			}
			if (!sent.booleanValue()) {
				sentMessageTypes.put(message, true);
				markDirty();
				GOTPacketMessage packet = new GOTPacketMessage(message);
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			}
		}
	}

	public void sendMiniQuestPacket(EntityPlayerMP entityplayer, GOTMiniQuest quest, boolean completed) throws IOException {
		NBTTagCompound nbt = new NBTTagCompound();
		quest.writeToNBT(nbt);
		GOTPacketMiniquest packet = new GOTPacketMiniquest(nbt, completed);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
	}

	public void sendOptionsPacket(int option, boolean flag) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketOptions packet = new GOTPacketOptions(option, flag);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void sendPlayerData(EntityPlayerMP entityplayer) throws IOException {
		NBTTagCompound nbt = new NBTTagCompound();
		save(nbt);
		nbt.removeTag("Achievements");
		nbt.removeTag("MiniQuests");
		nbt.removeTag("MiniQuestsCompleted");
		nbt.removeTag("CustomWaypoints");
		nbt.removeTag("Fellowships");
		nbt.removeTag("FellowshipInvites");
		GOTPacketLoginPlayerData packet = new GOTPacketLoginPlayerData(nbt);
		GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
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
			GOTPacketHandler.networkWrapper.sendTo(cwpPacket, entityplayer);
		}
		for (UUID fsID : fellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
			if (fs != null) {
				sendFellowshipPacket(fs);
				fs.doRetroactiveWaypointSharerCheckIfNeeded();
				checkIfStillWaypointSharerForFellowship(fs);
			}
		}
		Set<GOTFellowshipInvite> staleFellowshipInvites = new HashSet<>();
		for (GOTFellowshipInvite invite : fellowshipInvites) {
			GOTFellowship fs = GOTFellowshipData.getFellowship(invite.fellowshipID);
			if (fs != null) {
				sendFellowshipInvitePacket(fs);
				continue;
			}
			staleFellowshipInvites.add(invite);
		}
		if (!staleFellowshipInvites.isEmpty()) {
			fellowshipInvites.removeAll(staleFellowshipInvites);
			markDirty();
		}
		addSharedCustomWaypointsFromAllFellowships();
	}

	public void setAdminHideMap(boolean flag) {
		adminHideMap = flag;
		markDirty();
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

	public void setAlignment(GOTFaction faction, float alignment) {
		EntityPlayer entityplayer = getPlayer();
		if (faction.isPlayableAlignmentFaction()) {
			float prevAlignment = getAlignment(faction);
			alignments.put(faction, alignment);
			markDirty();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
			}
			checkAlignmentAchievements(faction, prevAlignment);
		}
		if (entityplayer != null && !entityplayer.worldObj.isRemote && pledgeFaction != null && !canPledgeTo(pledgeFaction)) {
			revokePledgeFaction(entityplayer, false);
		}
	}

	public void setAlignmentFromCommand(GOTFaction faction, float set) {
		setAlignment(faction, set);
	}

	public void setAskedForJaqen(boolean flag) {
		askedForJaqen = flag;
		markDirty();
	}

	public void setBalance(int b) {
		balance = b;
		markDirty();
	}

	public void setBrokenPledgeFaction(GOTFaction f) {
		brokenPledgeFaction = f;
		markDirty();
	}

	public void setCape(GOTCapes cape) {
		this.cape = cape;
		markDirty();
	}

	public void setChatBoundFellowship(GOTFellowship fs) {
		setChatBoundFellowshipID(fs.getFellowshipID());
	}

	public void setChatBoundFellowshipID(UUID fsID) {
		chatBoundFellowshipID = fsID;
		markDirty();
	}

	public void setCheckedMenu(boolean flag) {
		if (checkedMenu != flag) {
			checkedMenu = flag;
			markDirty();
		}
	}

	public void setDeathDimension(int dim) {
		deathDim = dim;
		markDirty();
	}

	public void setDeathPoint(int i, int j, int k) {
		deathPoint = new ChunkCoordinates(i, j, k);
		markDirty();
	}

	public void setEnableConquestKills(boolean flag) {
		conquestKills = flag;
		markDirty();
		sendOptionsPacket(5, flag);
	}

	public void setEnableHiredDeathMessages(boolean flag) {
		hiredDeathMessages = flag;
		markDirty();
		sendOptionsPacket(1, flag);
	}

	public void setFellowshipAdmin(GOTFellowship fs, UUID player, boolean flag, String granterUsername) {
		if (fs.isOwner(playerUUID)) {
			fs.setAdmin(player, flag);
			EntityPlayer subjectPlayer = getOtherPlayer(player);
			if (subjectPlayer != null && !subjectPlayer.worldObj.isRemote) {
				if (flag) {
					fs.sendNotification(subjectPlayer, "got.gui.fellowships.notifyOp", granterUsername);
				} else {
					fs.sendNotification(subjectPlayer, "got.gui.fellowships.notifyDeop", granterUsername);
				}
			}
		}
	}

	public void setFellowshipIcon(GOTFellowship fs, ItemStack itemstack) {
		if (fs.isOwner(playerUUID) || fs.isAdmin(playerUUID)) {
			fs.setIcon(itemstack);
		}
	}

	public void setFellowshipPreventHiredFF(GOTFellowship fs, boolean prevent) {
		if (fs.isOwner(playerUUID) || fs.isAdmin(playerUUID)) {
			fs.setPreventHiredFriendlyFire(prevent);
		}
	}

	public void setFellowshipPreventPVP(GOTFellowship fs, boolean prevent) {
		if (fs.isOwner(playerUUID) || fs.isAdmin(playerUUID)) {
			fs.setPreventPVP(prevent);
		}
	}

	public void setFellowshipShowMapLocations(GOTFellowship fs, boolean show) {
		if (fs.isOwner(playerUUID)) {
			fs.setShowMapLocations(show);
		}
	}

	public void setFemRankOverride(boolean flag) {
		femRankOverride = flag;
		markDirty();
		sendOptionsPacket(4, flag);
	}

	public void setFriendlyFire(boolean flag) {
		friendlyFire = flag;
		markDirty();
		sendOptionsPacket(0, flag);
	}

	public void setHideAlignment(boolean flag) {
		hideAlignment = flag;
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
		}
	}

	public void setHideMapLocation(boolean flag) {
		hideOnMap = flag;
		markDirty();
		sendOptionsPacket(3, flag);
	}

	public void setPlayerTitle(GOTTitle.PlayerTitle title) {
		playerTitle = title;
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketTitle packet = new GOTPacketTitle(playerTitle);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
		for (UUID fsID : fellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
			if (fs != null) {
				fs.updateForAllMembers(new FellowshipUpdateType.UpdatePlayerTitle(playerUUID, playerTitle));
			}
		}
	}

	public void setPledgeBreakCooldown(int i) {
		int preCD = pledgeBreakCooldown;
		GOTFaction preBroken = brokenPledgeFaction;
		i = Math.max(0, i);
		pledgeBreakCooldown = i;
		boolean bigChange = (pledgeBreakCooldown == 0 || preCD == 0) && pledgeBreakCooldown != preCD;
		if (pledgeBreakCooldown > pledgeBreakCooldownStart) {
			setPledgeBreakCooldownStart(pledgeBreakCooldown);
			bigChange = true;
		}
		if (pledgeBreakCooldown <= 0 && preBroken != null) {
			setPledgeBreakCooldownStart(0);
			setBrokenPledgeFaction(null);
			bigChange = true;
		}
		if (bigChange || isTimerAutosaveTick()) {
			markDirty();
		}
		if (bigChange || pledgeBreakCooldown % 5 == 0) {
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTPacketBrokenPledge packet = new GOTPacketBrokenPledge(pledgeBreakCooldown, pledgeBreakCooldownStart, brokenPledgeFaction);
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			}
		}
		if (pledgeBreakCooldown == 0 && preCD != pledgeBreakCooldown) {
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				String brokenName = preBroken == null ? StatCollector.translateToLocal("got.gui.factions.pledgeUnknown") : preBroken.factionName();
				ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("got.chat.pledgeBreakCooldown", brokenName);
				entityplayer.addChatMessage(chatComponentTranslation);
			}
		}
	}

	public void setPledgeBreakCooldownStart(int i) {
		pledgeBreakCooldownStart = i;
		markDirty();
	}

	public void setPledgeFaction(GOTFaction fac) {
		pledgeFaction = fac;
		pledgeKillCooldown = 0;
		markDirty();
		if (fac != null) {
			checkAlignmentAchievements(fac, getAlignment(fac));
			addAchievement(GOTAchievement.pledgeService);
		}
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			if (fac != null) {
				World world = entityplayer.worldObj;
				world.playSoundAtEntity(entityplayer, "got:event.pledge", 1.0F, 1.0F);
			}
			GOTPacketPledge packet = new GOTPacketPledge(fac);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void setRegionLastViewedFaction(GOTDimension.DimensionRegion region, GOTFaction fac) {
		if (region.factionList.contains(fac)) {
			prevRegionFactions.put(region, fac);
			markDirty();
		}
	}

	public void setShield(GOTShields gotshield) {
		shield = gotshield;
		markDirty();
	}

	public void setShowCustomWaypoints(boolean flag) {
		showCustomWaypoints = flag;
		markDirty();
	}

	public void setShowHiddenSharedWaypoints(boolean flag) {
		showHiddenSharedWaypoints = flag;
		markDirty();
	}

	public void setShowWaypoints(boolean flag) {
		showWaypoints = flag;
		markDirty();
	}

	public void setSiegeActive(int duration) {
		siegeActiveTime = Math.max(siegeActiveTime, duration);
	}

	public void setStructuresBanned(boolean flag) {
		structuresBanned = flag;
		markDirty();
	}

	public void setTableSwitched(boolean flag) {
		tableSwitched = flag;
		markDirty();
		sendOptionsPacket(9, flag);
	}

	public void setTargetFTWaypoint(GOTAbstractWaypoint wp) {
		targetFTWaypoint = wp;
		markDirty();
		if (wp != null) {
			setTicksUntilFT(ticksUntilFT_max);
		} else {
			setTicksUntilFT(0);
		}
	}

	public void setTeleportedKW(boolean flag) {
		teleportedKW = flag;
		markDirty();
	}

	public void setTicksUntilFT(int i) {
		if (ticksUntilFT != i) {
			ticksUntilFT = i;
			if (ticksUntilFT == ticksUntilFT_max || ticksUntilFT == 0) {
				markDirty();
			}
		}
	}

	public void setTimeSinceFT(int i) {
		setTimeSinceFT(i, false);
	}

	public void setTimeSinceFT(int i, boolean forceUpdate) {
		int preTick = ftSinceTick;
		i = Math.max(0, i);
		ftSinceTick = i;
		boolean bigChange = (ftSinceTick == 0 || preTick == 0) && ftSinceTick != preTick || preTick < 0 && ftSinceTick >= 0;
		if (bigChange || isTimerAutosaveTick() || forceUpdate) {
			markDirty();
		}
		if (bigChange || ftSinceTick % 5 == 0 || forceUpdate) {
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTPacketFTTimer packet = new GOTPacketFTTimer(ftSinceTick);
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			}
		}
	}

	public void setTimeSinceFTWithUpdate(int i) {
		setTimeSinceFT(i, true);
	}

	public void setTrackingMiniQuest(GOTMiniQuest quest) {
		setTrackingMiniQuestID(quest == null ? null : quest.questUUID);
	}

	public void setTrackingMiniQuestID(UUID npcID) {
		trackingMiniQuestID = npcID;
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketMiniquestTrackClient packet = new GOTPacketMiniquestTrackClient(trackingMiniQuestID);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void setUUIDToMount(UUID uuid) {
		uuidToMount = uuid;
		if (uuidToMount != null) {
			uuidToMountTime = 10;
		} else {
			uuidToMountTime = 0;
		}
		markDirty();
	}

	public void setViewingFaction(GOTFaction faction) {
		if (faction != null) {
			viewingFaction = faction;
			markDirty();
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTPacketUpdateViewingFaction packet = new GOTPacketUpdateViewingFaction(viewingFaction);
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			}
		}
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
			GOTPacketWaypointUseCount packet = new GOTPacketWaypointUseCount(wp, count);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
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

	public void transferFellowship(GOTFellowship fs, UUID player, String prevOwnerUsername) {
		if (fs.isOwner(playerUUID)) {
			fs.setOwner(player);
			EntityPlayer newOwner = getOtherPlayer(player);
			if (newOwner != null && !newOwner.worldObj.isRemote) {
				fs.sendNotification(newOwner, "got.gui.fellowships.notifyTransfer", prevOwnerUsername);
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
				GOTPacketWaypointRegion packet = new GOTPacketWaypointRegion(region, true);
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
			}
		}
	}

	public void unlockSharedCustomWaypoint(GOTCustomWaypoint waypoint) {
		if (!waypoint.isShared()) {
			FMLLog.warning("GOT: Warning! Tried to unlock a shared custom waypoint with no owner!");
			return;
		}
		waypoint.setSharedUnlocked();
		CWPSharedKey key = CWPSharedKey.keyFor(waypoint.getSharingPlayerID(), waypoint.getID());
		cwpSharedUnlocked.add(key);
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			GOTPacketCWPSharedUnlockClient packet = waypoint.getClientSharedUnlockPacket();
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void unlockSharedCustomWaypoints(EntityPlayer entityplayer) {
		if (pdTick % 20 == 0 && entityplayer.dimension == GOTDimension.GAME_OF_THRONES.dimensionID) {
			List<GOTCustomWaypoint> unlockWaypoints = new ArrayList<>();
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

	public void unshareFellowshipFromOwnCustomWaypoints(GOTFellowship fs) {
		for (GOTCustomWaypoint waypoint : customWaypoints) {
			if (waypoint.hasSharedFellowship(fs)) {
				customWaypointRemoveSharedFellowship(waypoint, fs);
			}
		}
	}

	public void updateFactionData(GOTFaction faction, GOTFactionData factionData) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			markDirty();
			NBTTagCompound nbt = new NBTTagCompound();
			factionData.save(nbt);
			GOTPacketFactionData packet = new GOTPacketFactionData(faction, nbt);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void updateFastTravelClockFromLastOnlineTime(EntityPlayerMP player, World world) {
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
				ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("got.chat.ft.offlineTick", GOTLevelData.getHMSTime_Ticks(diff), GOTLevelData.getHMSTime_Ticks(ftClockIncrease));
				player.addChatMessage(chatComponentTranslation);
			}
		}
	}

	public void updateFellowship(GOTFellowship fs, FellowshipUpdateType updateType) {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage updatePacket = updateType.createUpdatePacket(this, fs);
			if (updatePacket != null) {
				GOTPacketHandler.networkWrapper.sendTo(updatePacket, (EntityPlayerMP) entityplayer);
			} else {
				GOTLog.logger.error("No associated packet for fellowship update type " + updateType.getClass().getName());
			}
		}
		List<UUID> playersToCheckSharedWaypointsFrom = updateType.getPlayersToCheckSharedWaypointsFrom(fs);
		if (playersToCheckSharedWaypointsFrom != null && !playersToCheckSharedWaypointsFrom.isEmpty()) {
			addSharedCustomWaypointsFrom(fs.getFellowshipID(), playersToCheckSharedWaypointsFrom);
			checkCustomWaypointsSharedBy(playersToCheckSharedWaypointsFrom);
		}
	}

	public void updateMiniQuest(GOTMiniQuest quest) {
		markDirty();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			try {
				sendMiniQuestPacket((EntityPlayerMP) entityplayer, quest, false);
			} catch (IOException e) {
				FMLLog.severe("Error sending miniquest packet to player " + entityplayer.getCommandSenderName());
				e.printStackTrace();
			}
		}
	}

	public boolean useFeminineRanks() {
		if (femRankOverride) {
			return true;
		}
		if (playerTitle != null) {
			GOTTitle title = playerTitle.getTitle();
			return title.isFeminineRank();
		}
		return false;
	}

	public static ArmorMaterial getBodyMaterial(EntityLivingBase entity) {
		ItemStack item = entity.getEquipmentInSlot(3);
		if (item == null || !(item.getItem() instanceof GOTItemArmor)) {
			return null;
		}
		return ((GOTItemArmor) item.getItem()).getArmorMaterial();
	}

	public static ItemArmor.ArmorMaterial getFullArmorMaterial(EntityLivingBase entity) {
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

	public static ArmorMaterial getFullArmorMaterialWithoutHelmet(EntityLivingBase entity) {
		ArmorMaterial material = null;
		for (int i = 1; i <= 3; ++i) {
			ItemStack item = entity.getEquipmentInSlot(i);
			if (item == null || !(item.getItem() instanceof GOTItemArmor)) {
				return null;
			}
			ArmorMaterial itemMaterial = ((GOTItemArmor) item.getItem()).getArmorMaterial();
			if (material != null && itemMaterial != material) {
				return null;
			}
			material = itemMaterial;
		}
		return material;
	}

	public static ArmorMaterial getHelmetMaterial(EntityLivingBase entity) {
		ItemStack item = entity.getEquipmentInSlot(4);
		if (item == null || !(item.getItem() instanceof GOTItemArmor)) {
			return null;
		}
		return ((GOTItemArmor) item.getItem()).getArmorMaterial();
	}

	public static boolean isTimerAutosaveTick() {
		return MinecraftServer.getServer() != null && MinecraftServer.getServer().getTickCounter() % 200 == 0;
	}

	public static class CWPSharedKey extends Pair<UUID, Integer> {
		public UUID sharingPlayer;
		public int waypointID;

		public CWPSharedKey(UUID player, int id) {
			sharingPlayer = player;
			waypointID = id;
		}

		@Override
		public UUID getLeft() {
			return sharingPlayer;
		}

		@Override
		public Integer getRight() {
			return waypointID;
		}

		@Override
		public Integer setValue(Integer value) {
			throw new UnsupportedOperationException();
		}

		public static CWPSharedKey keyFor(UUID player, int id) {
			return new CWPSharedKey(player, id);
		}
	}
}
