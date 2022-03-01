package got.common;

import java.io.IOException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.GOT;
import got.common.command.GOTCommandAdminHideMap;
import got.common.database.*;
import got.common.database.GOTTitle.PlayerTitle;
import got.common.entity.dragon.GOTEntityDragon;
import got.common.entity.essos.gold.GOTEntityGoldenMan;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.*;
import got.common.fellowship.*;
import got.common.item.other.GOTItemArmor;
import got.common.network.*;
import got.common.quest.*;
import got.common.util.GOTLog;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTPlayerData {
	private static int ticksUntilFT_max = 200;
	private ChunkCoordinates deathPoint;
	private GOTAbstractWaypoint targetFTWaypoint;
	private GOTBiome lastBiome;
	private GOTCapes cape;
	private GOTFaction brokenPledgeFaction = null;
	private GOTFaction pledgeFaction;
	private GOTFaction viewingFaction;
	private GOTPlayerQuestData questData = new GOTPlayerQuestData(this);
	private GOTShields shield;
	private GOTWaypoint lastWaypoint;
	private List<GOTAchievement> achievements = new ArrayList<>();
	private List<GOTCustomWaypoint> customWaypoints = new ArrayList<>();
	private List<GOTCustomWaypoint> customWaypointsShared = new ArrayList<>();
	private List<GOTFaction> bountiesPlaced = new ArrayList<>();
	private List<GOTFellowshipClient> fellowshipInvitesClient = new ArrayList<>();
	private List<GOTFellowshipClient> fellowshipsClient = new ArrayList<>();
	private List<GOTFellowshipInvite> fellowshipInvites = new ArrayList<>();
	private List<GOTMiniQuest> miniQuests = new ArrayList<>();
	private List<GOTMiniQuest> miniQuestsCompleted = new ArrayList<>();
	private List<UUID> fellowshipIDs = new ArrayList<>();
	private Map<CWPSharedKey, Integer> cwpSharedUseCounts = new HashMap<>();
	private Map<GOTDimension.DimensionRegion, GOTFaction> prevRegionFactions = new EnumMap<>(GOTDimension.DimensionRegion.class);
	private Map<GOTFaction, Float> alignments = new EnumMap<>(GOTFaction.class);
	private Map<GOTFaction, GOTFactionData> factionDataMap = new EnumMap<>(GOTFaction.class);
	private Map<GOTGuiMessageTypes, Boolean> sentMessageTypes = new EnumMap<>(GOTGuiMessageTypes.class);
	private Map<GOTWaypoint, Integer> wpUseCounts = new EnumMap<>(GOTWaypoint.class);
	private Map<Integer, Integer> cwpUseCounts = new HashMap<>();
	private PlayerTitle playerTitle;
	private Set<CWPSharedKey> cwpSharedHidden = new HashSet<>();
	private Set<CWPSharedKey> cwpSharedUnlocked = new HashSet<>();
	private Set<GOTFaction> takenAlignmentRewards = new HashSet<>();
	private Set<GOTWaypoint.Region> unlockedFTRegions = new HashSet<>();
	private UUID chatBoundFellowshipID;
	private UUID playerUUID;
	private UUID trackingMiniQuestID;
	private UUID uuidToMount;
	private boolean adminHideMap;
	private boolean askedForJaqen;
	private boolean checkedMenu;
	private boolean conquestKills = true;
	private boolean friendlyFire;
	private boolean hideAlignment;
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
	private int balance = 0;
	private int completedBountyQuests;
	private int completedMiniquestCount;
	private int deathDim;
	private int ftSinceTick;
	private int nextCwpID = 20000;
	private int pdTick = 0;
	private int pledgeBreakCooldown;
	private int pledgeBreakCooldownStart;
	private int pledgeKillCooldown = 0;
	private int siegeActiveTime;
	private int ticksUntilFT;
	private int uuidToMountTime;
	private long lastOnlineTime = -1L;
	private boolean femRankOverride = false;

	public GOTPlayerData(UUID uuid) {
		playerUUID = uuid;
		viewingFaction = GOTFaction.NORTH;
		ftSinceTick = GOTLevelData.getWaypointCooldownMax() * 20;
	}

	public void acceptFellowshipInvite(GOTFellowship fs, boolean respectSizeLimit) {
		UUID fsID = fs.getFellowshipID();
		GOTFellowshipInvite existingInvite = null;
		for (GOTFellowshipInvite invite : fellowshipInvites) {
			if (!invite.fellowshipID.equals(fsID)) {
				continue;
			}
			existingInvite = invite;
			break;
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
						EntityPlayer inviter;
						GOTPacketFellowshipAcceptInviteResult resultPacket = new GOTPacketFellowshipAcceptInviteResult(fs, GOTPacketFellowshipAcceptInviteResult.AcceptInviteResult.JOINED);
						GOTPacketHandler.networkWrapper.sendTo((IMessage) resultPacket, (EntityPlayerMP) entityplayer);
						UUID inviterID = existingInvite.inviterID;
						if (inviterID == null) {
							inviterID = fs.getOwner();
						}
						inviter = getOtherPlayer(inviterID);
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
						++biomes;
					}
				}
				if (biomes >= 10) {
					addAchievement(GOTAchievement.TRAVEL10);
				}
				if (biomes >= 20) {
					addAchievement(GOTAchievement.TRAVEL20);
				}
				if (biomes >= 30) {
					addAchievement(GOTAchievement.TRAVEL30);
				}
				if (biomes >= 40) {
					addAchievement(GOTAchievement.TRAVEL40);
				}
				if (biomes >= 50) {
					addAchievement(GOTAchievement.TRAVEL50);
				}
			}
		}
	}

	public GOTAlignmentBonusMap addAlignment(EntityPlayer entityplayer, GOTAlignmentValues.AlignmentBonus source, GOTFaction faction, double posX, double posY, double posZ) {
		return this.addAlignment(entityplayer, source, faction, null, posX, posY, posZ);
	}

	public GOTAlignmentBonusMap addAlignment(EntityPlayer entityplayer, GOTAlignmentValues.AlignmentBonus source, GOTFaction faction, Entity entity) {
		return this.addAlignment(entityplayer, source, faction, null, entity);
	}

	public GOTAlignmentBonusMap addAlignment(EntityPlayer entityplayer, GOTAlignmentValues.AlignmentBonus source, GOTFaction faction, List<GOTFaction> forcedBonusFactions, double posX, double posY, double posZ) {
		float bonus = source.bonus;
		GOTAlignmentBonusMap factionBonusMap = new GOTAlignmentBonusMap();
		float prevMainAlignment = getAlignment(faction);
		float conquestBonus = 0.0f;
		if (source.isKill) {
			List<GOTFaction> killBonuses = faction.getBonusesForKilling();
			for (GOTFaction bonusFaction : killBonuses) {
				if (!bonusFaction.isPlayableAlignmentFaction() || !bonusFaction.isViolent && source.isCivilianKill) {
					continue;
				}
				if (!source.killByHiredUnit) {
					float mplier;
					mplier = forcedBonusFactions != null && forcedBonusFactions.contains(bonusFaction) ? 1.0f : bonusFaction.getControlZoneAlignmentMultiplier(entityplayer);
					if (mplier > 0.0f) {
						float alignment = getAlignment(bonusFaction);
						float factionBonus = Math.abs(bonus);
						factionBonus *= mplier;
						if (alignment >= bonusFaction.getPledgeAlignment() && !isPledgedTo(bonusFaction)) {
							factionBonus *= 0.5f;
						}
						factionBonus = checkBonusForPledgeEnemyLimit(bonusFaction, factionBonus);
						setAlignment(bonusFaction, alignment += factionBonus);
						factionBonusMap.put(bonusFaction, factionBonus);
					}
				}
				if (bonusFaction != getPledgeFaction()) {
					continue;
				}
				float conq = bonus;
				if (source.killByHiredUnit) {
					conq *= 0.25f;
				}
				conquestBonus = GOTConquestGrid.onConquestKill(entityplayer, bonusFaction, faction, conq);
				getFactionData(bonusFaction).addConquest(Math.abs(conquestBonus));
			}
			List<GOTFaction> killPenalties = faction.getPenaltiesForKilling();
			for (GOTFaction penaltyFaction : killPenalties) {
				if (!penaltyFaction.isPlayableAlignmentFaction() || source.killByHiredUnit) {
					continue;
				}
				float mplier;
				mplier = penaltyFaction == faction ? 1.0f : penaltyFaction.getControlZoneAlignmentMultiplier(entityplayer);
				if (mplier <= 0.0f) {
					continue;
				}
				float alignment = getAlignment(penaltyFaction);
				float factionPenalty = -Math.abs(bonus);
				factionPenalty *= mplier;
				factionPenalty = GOTAlignmentValues.AlignmentBonus.scalePenalty(factionPenalty, alignment);
				if (source.isRoyalOrder) {
					setAlignment(penaltyFaction, alignment += 0);
					setAlignment(source.faction, alignment += factionPenalty);
				} else {
					setAlignment(penaltyFaction, alignment += factionPenalty);
				}
				factionBonusMap.put(penaltyFaction, factionPenalty);
			}
		} else if (faction.isPlayableAlignmentFaction()) {
			float alignment = getAlignment(faction);
			float factionBonus = bonus;
			if (factionBonus > 0.0f && alignment >= faction.getPledgeAlignment() && !isPledgedTo(faction)) {
				factionBonus *= 0.5f;
			}
			factionBonus = checkBonusForPledgeEnemyLimit(faction, factionBonus);
			setAlignment(faction, alignment += factionBonus);
			factionBonusMap.put(faction, factionBonus);
		}
		if (!factionBonusMap.isEmpty() || conquestBonus != 0.0f) {
			sendAlignmentBonusPacket(source, faction, prevMainAlignment, factionBonusMap, conquestBonus, posX, posY, posZ);
		}
		return factionBonusMap;
	}

	public GOTAlignmentBonusMap addAlignment(EntityPlayer entityplayer, GOTAlignmentValues.AlignmentBonus source, GOTFaction faction, List<GOTFaction> forcedBonusFactions, Entity entity) {
		return this.addAlignment(entityplayer, source, faction, forcedBonusFactions, entity.posX, entity.boundingBox.minY + entity.height * 0.7, entity.posZ);
	}

	public void addAlignmentFromCommand(GOTFaction faction, float add) {
		float alignment = getAlignment(faction);
		alignment += add;
		setAlignment(faction, alignment);
	}

	public void addCompletedBountyQuest() {
		++completedBountyQuests;
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
			if (sharedPlayer == null || sharedPlayer.worldObj.isRemote) {
				continue;
			}
			GOTLevelData.getData(sharedPlayerUUID).addOrUpdateSharedCustomWaypoint(shareCopy);
		}
	}

	public void addCustomWaypointClient(GOTCustomWaypoint waypoint) {
		customWaypoints.add(waypoint);
	}

	public void addFellowship(GOTFellowship fs) {
		UUID fsID;
		if (fs.containsPlayer(playerUUID) && !fellowshipIDs.contains(fsID = fs.getFellowshipID())) {
			fellowshipIDs.add(fsID);
			markDirty();
			sendFellowshipPacket(fs);
			addSharedCustomWaypointsFromAllIn(fs.getFellowshipID());
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
			if (!fsInList.getFellowshipID().equals(fsID)) {
				continue;
			}
			inList = fsInList;
			break;
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
			if (!fsInList.getFellowshipID().equals(fsID)) {
				continue;
			}
			inList = fsInList;
			break;
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

	private int addSharedCustomWaypointsFrom(UUID onlyOneFellowshipID, List<UUID> checkSpecificPlayers) {
		List<UUID> checkFellowshipIDs;
		if (onlyOneFellowshipID != null) {
			checkFellowshipIDs = new ArrayList<>();
			checkFellowshipIDs.add(onlyOneFellowshipID);
		} else {
			checkFellowshipIDs = fellowshipIDs;
		}
		ArrayList<UUID> checkFellowPlayerIDs = new ArrayList<>();
		if (checkSpecificPlayers != null) {
			for (UUID player : checkSpecificPlayers) {
				if (player.equals(playerUUID)) {
					continue;
				}
				checkFellowPlayerIDs.add(player);
			}
		} else {
			for (UUID fsID : checkFellowshipIDs) {
				GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
				if (fs == null) {
					continue;
				}
				Set<UUID> playerIDs = fs.getWaypointSharerUUIDs();
				for (UUID player : playerIDs) {
					if (player.equals(playerUUID) || checkFellowPlayerIDs.contains(player)) {
						continue;
					}
					checkFellowPlayerIDs.add(player);
				}
			}
		}
		for (UUID player : checkFellowPlayerIDs) {
			GOTPlayerData pd = GOTLevelData.getData(player);
			List<GOTCustomWaypoint> cwps = pd.getCustomWaypoints();
			for (GOTCustomWaypoint waypoint : cwps) {
				boolean inSharedFellowship = false;
				for (UUID fsID : checkFellowshipIDs) {
					if (!waypoint.hasSharedFellowship(fsID)) {
						continue;
					}
					inSharedFellowship = true;
					break;
				}
				if (!inSharedFellowship) {
					continue;
				}
				addOrUpdateSharedCustomWaypoint(waypoint.createCopyOfShared(player));
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
				if (!name.equals(otherName = StringUtils.strip(otherName).toLowerCase())) {
					continue;
				}
				return true;
			}
		} else {
			for (UUID fsID : fellowshipIDs) {
				GOTFellowship fs = GOTFellowshipData.getFellowship(fsID);
				if (fs == null || fs.isDisbanded()) {
					continue;
				}
				String otherName = fs.getName();
				if (!name.equals(otherName = StringUtils.strip(otherName).toLowerCase())) {
					continue;
				}
				return true;
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
				if (!fs.isOwned() || ++leading < max) {
					continue;
				}
				return false;
			}
		} else {
			for (UUID fsID : fellowshipIDs) {
				GOTFellowship fs = GOTFellowshipData.getFellowship(fsID);
				if (fs == null || !fs.isOwner(playerUUID) || ++leading < max) {
					continue;
				}
				return false;
			}
		}
		return leading < max;
	}

	public boolean canFastTravel() {
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null) {
			World world = entityplayer.worldObj;
			if (!entityplayer.capabilities.isCreativeMode) {
				double range = 16.0;
				List entities = world.getEntitiesWithinAABB(EntityLiving.class, entityplayer.boundingBox.expand(range, range, range));
				for (Object element : entities) {
					EntityLiving entityliving = (EntityLiving) element;
					if (entityliving.getAttackTarget() != entityplayer) {
						continue;
					}
					return false;
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
		ArrayList<GOTCustomWaypoint> removes = new ArrayList<>();
		for (GOTCustomWaypoint waypoint : customWaypointsShared) {
			GOTCustomWaypoint wpOriginal;
			UUID waypointSharingPlayer = waypoint.getSharingPlayerID();
			if (checkSpecificPlayers != null && !checkSpecificPlayers.contains(waypointSharingPlayer) || (wpOriginal = GOTLevelData.getData(waypointSharingPlayer).getCustomWaypointByID(waypoint.getID())) == null || wpOriginal.getPlayersInAllSharedFellowships().contains(playerUUID)) {
				continue;
			}
			removes.add(waypoint);
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
			++completedMiniquestCount;
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
		if (normalCreation && !canCreateFellowships(false)) {
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
			if (player.equals(playerUUID)) {
				continue;
			}
			GOTLevelData.getData(player).addOrUpdateSharedCustomWaypoint(shareCopy);
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
			if (player.equals(playerUUID)) {
				continue;
			}
			GOTPlayerData pd = GOTLevelData.getData(player);
			pd.addOrUpdateSharedCustomWaypoint(shareCopy);
			pd.checkCustomWaypointsSharedBy(ImmutableList.of(playerUUID));
		}
	}

	public void customWaypointRemoveSharedFellowshipClient(GOTCustomWaypoint waypoint, UUID fsID) {
		waypoint.removeSharedFellowship(fsID);
	}

	public void disbandFellowship(GOTFellowship fs, String disbanderUsername) {
		if (fs.isOwner(playerUUID)) {
			ArrayList<UUID> memberUUIDs = new ArrayList<>(fs.getMemberUUIDs());
			fs.setDisbandedAndRemoveAllMembers();
			removeFellowship(fs);
			getPlayer();
			for (UUID memberID : memberUUIDs) {
				EntityPlayer member = getOtherPlayer(memberID);
				if (member == null || member.worldObj.isRemote) {
					continue;
				}
				fs.sendNotification(member, "got.gui.fellowships.notifyDisband", disbanderUsername);
			}
		}
	}

	public void distributeMQEvent(GOTMiniQuestEvent event) {
		for (GOTMiniQuest quest : miniQuests) {
			if (!quest.isActive()) {
				continue;
			}
			quest.handleEvent(event);
		}
	}

	public boolean doesFactionPreventPledge(GOTFaction pledgeFac, GOTFaction otherFac) {
		return pledgeFac.isMortalEnemy(otherFac);
	}

	public boolean doFactionsDrain(GOTFaction fac1, GOTFaction fac2) {
		return fac1.isMortalEnemy(fac2);
	}

	public <T extends EntityLiving> T fastTravelEntity(WorldServer world, T entity, int i, int j, int k) {
		String entityID = EntityList.getEntityString(entity);
		NBTTagCompound nbt = new NBTTagCompound();
		entity.writeToNBT(nbt);
		entity.setDead();
		EntityLiving newEntity = (EntityLiving) EntityList.createEntityByName(entityID, world);
		newEntity.readFromNBT(nbt);
		newEntity.setLocationAndAngles(i + 0.5, j, k + 0.5, newEntity.rotationYaw, newEntity.rotationPitch);
		newEntity.fallDistance = 0.0f;
		newEntity.getNavigator().clearPathEntity();
		newEntity.setAttackTarget(null);
		world.spawnEntityInWorld(newEntity);
		world.updateEntityWithOptionalForce(newEntity, false);
		return (T) newEntity;
	}

	public void fastTravelTo(GOTAbstractWaypoint waypoint) {
		EntityPlayer player = getPlayer();
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) player;
			WorldServer world = (WorldServer) entityplayer.worldObj;
			int startX = MathHelper.floor_double(entityplayer.posX);
			int startZ = MathHelper.floor_double(entityplayer.posZ);
			double range = 256.0;
			List<EntityLiving> entities = world.getEntitiesWithinAABB(EntityLiving.class, entityplayer.boundingBox.expand(range, range, range));
			HashSet<EntityLiving> entitiesToTransport = new HashSet<>();
			for (EntityLiving entity : entities) {
				EntityTameable pet;
				if (entity instanceof GOTEntityNPC) {
					GOTEntityNPC npc = (GOTEntityNPC) entity;
					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() == entityplayer && npc.hiredNPCInfo.shouldFollowPlayer()) {
						entitiesToTransport.add(npc);
						continue;
					}
				}
				if (entity instanceof EntityTameable && !(entity instanceof GOTEntityDragon) && (pet = (EntityTameable) entity).getOwner() == entityplayer && !pet.isSitting()) {
					entitiesToTransport.add(pet);
					continue;
				}
				if (!entity.getLeashed() || entity.getLeashedToEntity() != entityplayer) {
					continue;
				}
				entitiesToTransport.add(entity);
			}
			HashSet<EntityLiving> removes = new HashSet<>();
			for (EntityLiving entity : entitiesToTransport) {
				Entity rider = entity.riddenByEntity;
				if (rider == null || !entitiesToTransport.contains(rider)) {
					continue;
				}
				removes.add(entity);
			}
			entitiesToTransport.removeAll(removes);
			int i = waypoint.getXCoord();
			int k = waypoint.getZCoord();
			world.theChunkProviderServer.provideChunk(i >> 4, k >> 4);
			int j = waypoint.getYCoord(world, i, k);
			Entity playerMount = entityplayer.ridingEntity;
			entityplayer.mountEntity(null);
			entityplayer.setPositionAndUpdate(i + 0.5, j, k + 0.5);
			entityplayer.fallDistance = 0.0f;
			if (playerMount instanceof EntityLiving) {
				playerMount = this.fastTravelEntity(world, (EntityLiving) playerMount, i, j, k);
			}
			if (playerMount != null) {
				setUUIDToMount(playerMount.getUniqueID());
			}
			for (EntityLiving entity : entitiesToTransport) {
				Entity mount = entity.ridingEntity;
				entity.mountEntity(null);
				entity = this.fastTravelEntity(world, entity, i, j, k);
				if (!(mount instanceof EntityLiving)) {
					continue;
				}
				mount = this.fastTravelEntity(world, (EntityLiving) mount, i, j, k);
				entity.mountEntity(mount);
			}
			sendFTPacket(entityplayer, waypoint, startX, startZ);
			setTimeSinceFT(0);
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
		return alignment != null ? alignment : 0.0f;
	}

	public List<GOTAbstractWaypoint> getAllAvailableWaypoints() {
		ArrayList<GOTAbstractWaypoint> waypoints = new ArrayList<>(GOTWaypoint.listAllWaypoints());
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
		GOTFellowship fs;
		if (chatBoundFellowshipID != null && (fs = GOTFellowshipData.getActiveFellowship(chatBoundFellowshipID)) != null) {
			return fs;
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
			if (!fs.getFellowshipID().equals(fsID)) {
				continue;
			}
			return fs;
		}
		return null;
	}

	public GOTFellowshipClient getClientFellowshipByName(String fsName) {
		for (GOTFellowshipClient fs : fellowshipsClient) {
			if (!fs.getName().equalsIgnoreCase(fsName)) {
				continue;
			}
			return fs;
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
			if (waypoint.getID() != ID) {
				continue;
			}
			return waypoint;
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
		ArrayList<GOTAchievement> earnedAchievements = new ArrayList<>();
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null) {
			for (GOTAchievement achievement : achievements) {
				if (achievement.getDimension() != dimension || !achievement.canPlayerEarn(entityplayer)) {
					continue;
				}
				earnedAchievements.add(achievement);
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

	public List<GOTFaction> getFactionsPreventingPledgeTo(GOTFaction fac) {
		ArrayList<GOTFaction> enemies = new ArrayList<>();
		for (GOTFaction otherFac : GOTFaction.values()) {
			if (!otherFac.isPlayableAlignmentFaction() || !doesFactionPreventPledge(fac, otherFac) || getAlignment(otherFac) <= 0.0f) {
				continue;
			}
			enemies.add(otherFac);
		}
		return enemies;
	}

	public GOTFellowship getFellowshipByName(String fsName) {
		for (UUID fsID : fellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getFellowship(fsID);
			if (fs == null || !fs.getName().equalsIgnoreCase(fsName)) {
				continue;
			}
			return fs;
		}
		return null;
	}

	public List<UUID> getFellowshipIDs() {
		return fellowshipIDs;
	}

	public List<GOTFellowship> getFellowships() {
		ArrayList<GOTFellowship> fellowships = new ArrayList<>();
		for (UUID fsID : fellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getActiveFellowship(fsID);
			if (fs == null) {
				continue;
			}
			fellowships.add(fs);
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
		ArrayList<GOTMiniQuest> threadSafe = completed ? new ArrayList<>(miniQuestsCompleted) : new ArrayList<>(miniQuests);
		for (GOTMiniQuest quest : threadSafe) {
			if (!quest.questUUID.equals(id)) {
				continue;
			}
			return quest;
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
		for (WorldServer world : MinecraftServer.getServer().worldServers) {
			EntityPlayer entityplayer = world.func_152378_a(uuid);
			if (entityplayer == null) {
				continue;
			}
			return entityplayer;
		}
		return null;
	}

	public EntityPlayer getPlayer() {
		World[] searchWorlds = GOT.proxy.isClient() ? new World[] { GOT.proxy.getClientWorld() } : MinecraftServer.getServer().worldServers;
		for (World world : searchWorlds) {
			EntityPlayer entityplayer = world.func_152378_a(playerUUID);
			if (entityplayer == null) {
				continue;
			}
			return entityplayer;
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
		return 0.0f;
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
			if (!waypoint.getSharingPlayerID().equals(owner) || waypoint.getID() != id) {
				continue;
			}
			return waypoint;
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

	public boolean getTeleportedME() {
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
		double dist = entityplayer.getDistance(wp.getXCoord() + 0.5, wp.getYCoordSaved(), wp.getZCoord() + 0.5);
		double time = baseMin;
		double added = (baseMax - baseMin) * Math.pow(0.9, useCount);
		time += added;
		int seconds = (int) Math.round(time *= Math.max(1.0, dist * 1.2E-5));
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
			} else if (cwpUseCounts.containsKey(ID)) {
				return cwpUseCounts.get(ID);
			}
		} else if (wpUseCounts.containsKey(wp)) {
			return wpUseCounts.get(wp);
		}
		return 0;
	}

	public void givePureConquestBonus(EntityPlayer entityplayer, GOTFaction bonusFac, GOTFaction enemyFac, float conq, String title, double posX, double posY, double posZ) {
		conq = GOTConquestGrid.onConquestKill(entityplayer, bonusFac, enemyFac, conq);
		getFactionData(bonusFac).addConquest(Math.abs(conq));
		if (conq != 0.0f) {
			GOTAlignmentValues.AlignmentBonus source = new GOTAlignmentValues.AlignmentBonus(0.0f, title);
			GOTPacketAlignmentBonus packet = new GOTPacketAlignmentBonus(bonusFac, getAlignment(bonusFac), new GOTAlignmentBonusMap(), conq, posX, posY, posZ, source);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public boolean hasAchievement(GOTAchievement achievement) {
		for (GOTAchievement a : achievements) {
			if (a.category != achievement.category || a.ID != achievement.ID) {
				continue;
			}
			return true;
		}
		return false;
	}

	public boolean hasActiveOrCompleteMQType(Class<? extends GOTMiniQuest> type) {
		List<GOTMiniQuest> quests = getMiniQuests();
		List<GOTMiniQuest> questsComplete = getMiniQuestsCompleted();
		ArrayList<GOTMiniQuest> allQuests = new ArrayList<>();
		for (GOTMiniQuest q : quests) {
			if (!q.isActive()) {
				continue;
			}
			allQuests.add(q);
		}
		allQuests.addAll(questsComplete);
		for (GOTMiniQuest q : allQuests) {
			if (!type.isAssignableFrom(q.getClass())) {
				continue;
			}
			return true;
		}
		return false;
	}

	public boolean hasAnyJHQuest() {
		return hasActiveOrCompleteMQType(GOTMiniQuestWelcome.class);
	}

	public boolean hasAnyWaypointsSharedToFellowship(GOTFellowship fs) {
		for (GOTCustomWaypoint waypoint : customWaypoints) {
			if (!waypoint.hasSharedFellowship(fs)) {
				continue;
			}
			return true;
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
		++nextCwpID;
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

	public GOTMaterial isPlayerWearingFull(EntityPlayer entityplayer) {
		GOTMaterial fullMaterial = null;
		for (ItemStack itemstack : entityplayer.inventory.armorInventory) {
			if (itemstack != null && itemstack.getItem() instanceof GOTItemArmor) {
				GOTItemArmor armor = (GOTItemArmor) itemstack.getItem();
				GOTMaterial thisMaterial = armor.getGOTArmorMaterial();
				if (fullMaterial == null) {
					fullMaterial = thisMaterial;
					continue;
				}
				if (fullMaterial == thisMaterial) {
					continue;
				}
			}
			return null;
		}
		return fullMaterial;
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
			EntityPlayer entityplayer;
			EntityPlayer owner;
			fs.removeMember(playerUUID);
			if (fellowshipIDs.contains(fs.getFellowshipID())) {
				removeFellowship(fs);
			}
			entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote && (owner = getOtherPlayer(fs.getOwner())) != null) {
				fs.sendNotification(owner, "got.gui.fellowships.notifyLeave", entityplayer.getCommandSenderName());
			}
		}
	}

	public List<String> listAllFellowshipNames() {
		ArrayList<String> list = new ArrayList<>();
		for (UUID fsID : fellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getFellowship(fsID);
			if (fs == null || !fs.containsPlayer(playerUUID)) {
				continue;
			}
			list.add(fs.getName());
		}
		return list;
	}

	public List<String> listAllLeadingFellowshipNames() {
		ArrayList<String> list = new ArrayList<>();
		for (UUID fsID : fellowshipIDs) {
			GOTFellowship fs = GOTFellowshipData.getFellowship(fsID);
			if (fs == null || !fs.isOwner(playerUUID)) {
				continue;
			}
			list.add(fs.getName());
		}
		return list;
	}

	public void load(NBTTagCompound playerData) {
		GOTShields savedShield;
		GOTCapes savedCape;
		UUID fsID;
		GOTTitle title;
		GOTFaction cur;
		GOTFaction faction;
		alignments.clear();
		NBTTagList alignmentTags = playerData.getTagList("AlignmentMap", 10);
		for (int i = 0; i < alignmentTags.tagCount(); ++i) {
			float alignment;
			NBTTagCompound nbt = alignmentTags.getCompoundTagAt(i);
			GOTFaction faction2 = GOTFaction.forName(nbt.getString("Faction"));
			if (faction2 == null) {
				continue;
			}
			if (nbt.hasKey("Alignment")) {
				alignment = nbt.getInteger("Alignment");
			} else {
				alignment = nbt.getFloat("AlignF");
			}
			alignments.put(faction2, alignment);
		}
		if (playerData.hasKey("TableSwitched")) {
			tableSwitched = playerData.getBoolean("TableSwitched");
		}
		factionDataMap.clear();
		NBTTagList factionDataTags = playerData.getTagList("FactionData", 10);
		for (int i = 0; i < factionDataTags.tagCount(); ++i) {
			NBTTagCompound nbt = factionDataTags.getCompoundTagAt(i);
			GOTFaction faction3 = GOTFaction.forName(nbt.getString("Faction"));
			if (faction3 == null) {
				continue;
			}
			GOTFactionData data = new GOTFactionData(this, faction3);
			data.load(nbt);
			factionDataMap.put(faction3, data);
		}
		balance = playerData.getInteger("Balance");
		checkedMenu = playerData.getBoolean("CheckedMenu");
		askedForJaqen = playerData.getBoolean("AskedForJaqen");
		if (playerData.hasKey("CurrentFaction") && (cur = GOTFaction.forName(playerData.getString("CurrentFaction"))) != null) {
			viewingFaction = cur;
		}
		prevRegionFactions.clear();
		NBTTagList prevRegionFactionTags = playerData.getTagList("PrevRegionFactions", 10);
		for (int i = 0; i < prevRegionFactionTags.tagCount(); ++i) {
			NBTTagCompound nbt = prevRegionFactionTags.getCompoundTagAt(i);
			GOTDimension.DimensionRegion region = GOTDimension.DimensionRegion.forName(nbt.getString("Region"));
			faction = GOTFaction.forName(nbt.getString("Faction"));
			if (region == null || faction == null) {
				continue;
			}
			prevRegionFactions.put(region, faction);
		}
		hideAlignment = playerData.getBoolean("HideAlignment");
		takenAlignmentRewards.clear();
		NBTTagList takenRewardsTags = playerData.getTagList("TakenAlignmentRewards", 10);
		for (int i = 0; i < takenRewardsTags.tagCount(); ++i) {
			NBTTagCompound nbt = takenRewardsTags.getCompoundTagAt(i);
			faction = GOTFaction.forName(nbt.getString("Faction"));
			if (faction == null) {
				continue;
			}
			takenAlignmentRewards.add(faction);
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
		for (int i = 0; i < achievementTags.tagCount(); ++i) {
			NBTTagCompound nbt = achievementTags.getCompoundTagAt(i);
			String category = nbt.getString("Category");
			int ID = nbt.getInteger("ID");
			GOTAchievement achievement = GOTAchievement.achievementForCategoryAndID(GOTAchievement.categoryForName(category), ID);
			if (achievement == null || achievements.contains(achievement)) {
				continue;
			}
			achievements.add(achievement);
		}
		shield = null;
		if (playerData.hasKey("Shield") && (savedShield = GOTShields.shieldForName(playerData.getString("Shield"))) != null) {
			shield = savedShield;
		}
		cape = null;
		if (playerData.hasKey("Cape") && (savedCape = GOTCapes.capeForName(playerData.getString("Cape"))) != null) {
			cape = savedCape;
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
			deathDim = playerData.hasKey("DeathDim") ? playerData.getInteger("DeathDim") : GOTDimension.GAME_OF_THRONES.dimensionID;
		}
		alcoholTolerance = playerData.getInteger("Alcohol");
		miniQuests.clear();
		NBTTagList miniquestTags = playerData.getTagList("MiniQuests", 10);
		for (int i = 0; i < miniquestTags.tagCount(); ++i) {
			NBTTagCompound nbt = miniquestTags.getCompoundTagAt(i);
			GOTMiniQuest quest = GOTMiniQuest.loadQuestFromNBT(nbt, this);
			if (quest == null) {
				continue;
			}
			miniQuests.add(quest);
		}
		miniQuestsCompleted.clear();
		NBTTagList miniquestCompletedTags = playerData.getTagList("MiniQuestsCompleted", 10);
		for (int i = 0; i < miniquestCompletedTags.tagCount(); ++i) {
			NBTTagCompound nbt = miniquestCompletedTags.getCompoundTagAt(i);
			GOTMiniQuest quest = GOTMiniQuest.loadQuestFromNBT(nbt, this);
			if (quest == null) {
				continue;
			}
			miniQuestsCompleted.add(quest);
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
		for (int i = 0; i < bountyTags.tagCount(); ++i) {
			String fName = bountyTags.getStringTagAt(i);
			GOTFaction fac = GOTFaction.forName(fName);
			if (fac == null) {
				continue;
			}
			bountiesPlaced.add(fac);
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
		for (int i = 0; i < sentMessageTags.tagCount(); ++i) {
			NBTTagCompound nbt = sentMessageTags.getCompoundTagAt(i);
			GOTGuiMessageTypes message = GOTGuiMessageTypes.forSaveName(nbt.getString("Message"));
			if (message == null) {
				continue;
			}
			boolean sent = nbt.getBoolean("Sent");
			sentMessageTypes.put(message, sent);
		}
		playerTitle = null;
		if (playerData.hasKey("PlayerTitle") && (title = GOTTitle.forName(playerData.getString("PlayerTitle"))) != null) {
			int colorCode = playerData.getInteger("PlayerTitleColor");
			EnumChatFormatting color = GOTTitle.PlayerTitle.colorForID(colorCode);
			playerTitle = new GOTTitle.PlayerTitle(title, color);
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
		for (int i = 0; i < unlockedFTRegionTags.tagCount(); ++i) {
			NBTTagCompound nbt = unlockedFTRegionTags.getCompoundTagAt(i);
			String regionName = nbt.getString("Name");
			GOTWaypoint.Region region = GOTWaypoint.regionForName(regionName);
			if (region == null) {
				continue;
			}
			unlockedFTRegions.add(region);
		}
		customWaypoints.clear();
		NBTTagList customWaypointTags = playerData.getTagList("CustomWaypoints", 10);
		for (int i = 0; i < customWaypointTags.tagCount(); ++i) {
			NBTTagCompound nbt = customWaypointTags.getCompoundTagAt(i);
			GOTCustomWaypoint waypoint = GOTCustomWaypoint.readFromNBT(nbt, this);
			customWaypoints.add(waypoint);
		}
		cwpSharedUnlocked.clear();
		NBTTagList cwpSharedUnlockedTags = playerData.getTagList("CWPSharedUnlocked", 10);
		for (int i = 0; i < cwpSharedUnlockedTags.tagCount(); ++i) {
			NBTTagCompound nbt = cwpSharedUnlockedTags.getCompoundTagAt(i);
			UUID sharingPlayer = UUID.fromString(nbt.getString("SharingPlayer"));
			if (sharingPlayer == null) {
				continue;
			}
			int ID = nbt.getInteger("CustomID");
			CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
			cwpSharedUnlocked.add(key);
		}
		cwpSharedHidden.clear();
		NBTTagList cwpSharedHiddenTags = playerData.getTagList("CWPSharedHidden", 10);
		for (int i = 0; i < cwpSharedHiddenTags.tagCount(); ++i) {
			NBTTagCompound nbt = cwpSharedHiddenTags.getCompoundTagAt(i);
			UUID sharingPlayer = UUID.fromString(nbt.getString("SharingPlayer"));
			if (sharingPlayer == null) {
				continue;
			}
			int ID = nbt.getInteger("CustomID");
			CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
			cwpSharedHidden.add(key);
		}
		wpUseCounts.clear();
		NBTTagList wpCooldownTags = playerData.getTagList("WPUses", 10);
		for (int i = 0; i < wpCooldownTags.tagCount(); ++i) {
			NBTTagCompound nbt = wpCooldownTags.getCompoundTagAt(i);
			String name = nbt.getString("WPName");
			int count = nbt.getInteger("Count");
			GOTWaypoint wp = GOTWaypoint.waypointForName(name);
			if (wp == null) {
				continue;
			}
			wpUseCounts.put(wp, count);
		}
		cwpUseCounts.clear();
		NBTTagList cwpCooldownTags = playerData.getTagList("CWPUses", 10);
		for (int i = 0; i < cwpCooldownTags.tagCount(); ++i) {
			NBTTagCompound nbt = cwpCooldownTags.getCompoundTagAt(i);
			int ID = nbt.getInteger("CustomID");
			int count = nbt.getInteger("Count");
			cwpUseCounts.put(ID, count);
		}
		cwpSharedUseCounts.clear();
		NBTTagList cwpSharedCooldownTags = playerData.getTagList("CWPSharedUses", 10);
		for (int i = 0; i < cwpSharedCooldownTags.tagCount(); ++i) {
			NBTTagCompound nbt = cwpSharedCooldownTags.getCompoundTagAt(i);
			UUID sharingPlayer = UUID.fromString(nbt.getString("SharingPlayer"));
			if (sharingPlayer == null) {
				continue;
			}
			int ID = nbt.getInteger("CustomID");
			CWPSharedKey key = CWPSharedKey.keyFor(sharingPlayer, ID);
			int count = nbt.getInteger("Count");
			cwpSharedUseCounts.put(key, count);
		}
		nextCwpID = 20000;
		if (playerData.hasKey("NextCWPID")) {
			nextCwpID = playerData.getInteger("NextCWPID");
		}
		fellowshipIDs.clear();
		NBTTagList fellowshipTags = playerData.getTagList("Fellowships", 10);
		for (int i = 0; i < fellowshipTags.tagCount(); ++i) {
			NBTTagCompound nbt = fellowshipTags.getCompoundTagAt(i);
			UUID fsID2 = UUID.fromString(nbt.getString("ID"));
			if (fsID2 == null) {
				continue;
			}
			fellowshipIDs.add(fsID2);
		}
		fellowshipInvites.clear();
		NBTTagList fellowshipInviteTags = playerData.getTagList("FellowshipInvites", 10);
		for (int i = 0; i < fellowshipInviteTags.tagCount(); ++i) {
			NBTTagCompound nbt = fellowshipInviteTags.getCompoundTagAt(i);
			UUID fsID3 = UUID.fromString(nbt.getString("ID"));
			if (fsID3 == null) {
				continue;
			}
			UUID inviterID = null;
			if (nbt.hasKey("InviterID")) {
				inviterID = UUID.fromString(nbt.getString("InviterID"));
			}
			fellowshipInvites.add(new GOTFellowshipInvite(fsID3, inviterID));
		}
		chatBoundFellowshipID = null;
		if (playerData.hasKey("ChatBoundFellowship") && (fsID = UUID.fromString(playerData.getString("ChatBoundFellowship"))) != null) {
			chatBoundFellowshipID = fsID;
		}
		structuresBanned = playerData.getBoolean("StructuresBanned");
		teleportedKW = playerData.getBoolean("TeleportedME");
		if (playerData.hasKey("QuestData")) {
			NBTTagCompound questNBT = playerData.getCompoundTag("QuestData");
			questData.load(questNBT);
		}
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
			ChatComponentTranslation msg = new ChatComponentTranslation("got.chat.pledgeKillWarn", pledgeFaction.factionName());
			entityplayer.addChatMessage(msg);
		}
	}

	public void onUpdate(EntityPlayerMP entityplayer, WorldServer world) {
		++pdTick;
		GOTDimension.DimensionRegion currentRegion = viewingFaction.factionRegion;
		GOTDimension currentDim = GOTDimension.getCurrentDimensionWithFallback(world);
		if (currentRegion.getDimension() != currentDim) {
			currentRegion = currentDim.dimensionRegions.get(0);
			setViewingFaction(getRegionLastViewedFaction(currentRegion));
		}
		questData.onUpdate(entityplayer, world);
		if (!isSiegeActive()) {
			searchForBiomeWaypoints(entityplayer, world);
			runAchievementChecks(entityplayer, world);
		}
		if (!checkedMenu) {
			GOTPacketMenuPrompt packet = new GOTPacketMenuPrompt(GOTPacketMenuPrompt.Type.MENU);
			GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
		}
		if (playerTitle != null && !playerTitle.getTitle().canPlayerUse(entityplayer)) {
			ChatComponentTranslation msg = new ChatComponentTranslation("got.chat.loseTitle", playerTitle.getFullTitleComponent(entityplayer));
			entityplayer.addChatMessage(msg);
			setPlayerTitle(null);
		}
		if (pledgeKillCooldown > 0) {
			--pledgeKillCooldown;
			if (pledgeKillCooldown == 0 || GOTPlayerData.isTimerAutosaveTick()) {
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
				ChatComponentTranslation msg = new ChatComponentTranslation("got.chat.bountyPlaced", fac.factionName());
				msg.getChatStyle().setColor(EnumChatFormatting.YELLOW);
				entityplayer.addChatMessage(msg);
			}
			bountiesPlaced.clear();
			markDirty();
		}
		this.setTimeSinceFT(ftSinceTick + 1);
		if (targetFTWaypoint != null) {
			if (entityplayer.isPlayerSleeping()) {
				entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.inBed"));
				setTargetFTWaypoint(null);
			} else if (ticksUntilFT > 0) {
				int seconds = ticksUntilFT / 20;
				if (ticksUntilFT == ticksUntilFT_max) {
					entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.travelTicksStart", seconds));
				} else if (ticksUntilFT % 20 == 0 && seconds <= 5) {
					entityplayer.addChatMessage(new ChatComponentTranslation("got.fastTravel.travelTicks", seconds));
				}
				--ticksUntilFT;
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
				--uuidToMountTime;
			} else {
				double range = 32.0;
				List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, entityplayer.boundingBox.expand(range, range, range));
				for (Object obj : entities) {
					Entity entity = (Entity) obj;
					if (!entity.getUniqueID().equals(uuidToMount)) {
						continue;
					}
					entityplayer.mountEntity(entity);
					break;
				}
				setUUIDToMount(null);
			}
		}
		if (pdTick % 24000 == 0 && alcoholTolerance > 0) {
			--alcoholTolerance;
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
			--siegeActiveTime;
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
			if (!invite.fellowshipID.equals(fsID)) {
				continue;
			}
			existingInvite = invite;
			break;
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
			if (!fsInList.getFellowshipID().equals(fsID)) {
				continue;
			}
			inList = fsInList;
			break;
		}
		if (inList != null) {
			fellowshipsClient.remove(inList);
		}
	}

	public void removeClientFellowshipInvite(UUID fsID) {
		GOTFellowshipClient inList = null;
		for (GOTFellowshipClient fsInList : fellowshipInvitesClient) {
			if (!fsInList.getFellowshipID().equals(fsID)) {
				continue;
			}
			inList = fsInList;
			break;
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
				if (fs == null) {
					continue;
				}
				checkIfStillWaypointSharerForFellowship(fs);
			}
			EntityPlayer entityplayer = getPlayer();
			if (entityplayer != null && !entityplayer.worldObj.isRemote) {
				GOTPacketDeleteCWPClient packet = waypoint.getClientDeletePacket();
				GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
				GOTCustomWaypointLogger.logDelete(entityplayer, waypoint);
			}
		}
	}

	public void removeCustomWaypointClient(GOTCustomWaypoint waypoint) {
		customWaypoints.remove(waypoint);
	}

	public void removeFellowship(GOTFellowship fs) {
		UUID fsID;
		if (!fs.containsPlayer(playerUUID) && fellowshipIDs.contains(fsID = fs.getFellowshipID())) {
			fellowshipIDs.remove(fsID);
			markDirty();
			sendFellowshipRemovePacket(fs);
			unshareFellowshipFromOwnCustomWaypoints(fs);
			checkCustomWaypointsSharedFromFellowships();
		}
	}

	public void removeMiniQuest(GOTMiniQuest quest, boolean completed) {
		List<GOTMiniQuest> removeList = completed ? miniQuestsCompleted : miniQuests;
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
		GOTCustomWaypoint existing;
		existing = customWaypointsShared.contains(waypoint) ? waypoint : getSharedCustomWaypointByID(waypoint.getSharingPlayerID(), waypoint.getID());
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
			if (sharedPlayer == null || sharedPlayer.worldObj.isRemote) {
				continue;
			}
			GOTLevelData.getData(sharedPlayerUUID).renameSharedCustomWaypoint(shareCopy, newName);
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
		float cd = diff / 5000.0f;
		cd = MathHelper.clamp_float(cd, 0.0f, 1.0f);
		int cdTicks = 36000;
		setPledgeFaction(null);
		setBrokenPledgeFaction(wasPledge);
		setPledgeBreakCooldown(cdTicks += Math.round(cd * 150.0f * 60.0f * 20.0f));
		World world = entityplayer.worldObj;
		if (!world.isRemote) {
			ChatComponentTranslation msg;
			GOTFactionRank rank = wasPledge.getRank(prevAlign);
			GOTFactionRank rankBelow = wasPledge.getRankBelow(rank);
			GOTFactionRank rankBelow2 = wasPledge.getRankBelow(rankBelow);
			float newAlign = rankBelow2.alignment;
			float alignPenalty = (newAlign = Math.max(newAlign, pledgeLvl / 2.0f)) - prevAlign;
			if (alignPenalty < 0.0f) {
				GOTAlignmentValues.AlignmentBonus penalty = GOTAlignmentValues.createPledgePenalty(alignPenalty);
				double alignX = 0.0;
				double alignY = 0.0;
				double alignZ = 0.0;
				double lookRange = 2.0;
				Vec3 posEye = Vec3.createVectorHelper(entityplayer.posX, entityplayer.boundingBox.minY + entityplayer.getEyeHeight(), entityplayer.posZ);
				Vec3 look = entityplayer.getLook(1.0f);
				Vec3 posSight = posEye.addVector(look.xCoord * lookRange, look.yCoord * lookRange, look.zCoord * lookRange);
				MovingObjectPosition mop = world.rayTraceBlocks(posEye, posSight);
				if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					alignX = mop.blockX + 0.5;
					alignY = mop.blockY + 0.5;
					alignZ = mop.blockZ + 0.5;
				} else {
					alignX = posSight.xCoord;
					alignY = posSight.yCoord;
					alignZ = posSight.zCoord;
				}
				this.addAlignment(entityplayer, penalty, wasPledge, alignX, alignY, alignZ);
			}
			world.playSoundAtEntity(entityplayer, "got:event.unpledge", 1.0f, 1.0f);
			if (intentional) {
				msg = new ChatComponentTranslation("got.chat.unpledge", wasPledge.factionName());
			} else {
				msg = new ChatComponentTranslation("got.chat.autoUnpledge", wasPledge.factionName());
			}
			entityplayer.addChatMessage(msg);
			checkAlignmentAchievements(wasPledge, prevAlign);
		}
	}

	public void runAchievementChecks(EntityPlayer entityplayer, World world) {
		GOTMaterial fullMaterial;
		int i = MathHelper.floor_double(entityplayer.posX);
		MathHelper.floor_double(entityplayer.boundingBox.minY);
		int k = MathHelper.floor_double(entityplayer.posZ);
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		ItemArmor.ArmorMaterial material = getFullArmorMaterial(entityplayer);
		if (biome instanceof GOTBiome) {
			GOTBiome gotbiome = (GOTBiome) biome;
			GOTAchievement ach = gotbiome.getBiomeAchievement();
			if (ach != null) {
				addAchievement(ach);
			}
		}
		if (GOTAchievement.armorAchievements.containsKey(material)) {
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.armorAchievements.get(material));
		}
		if (entityplayer.dimension == GOTDimension.GAME_OF_THRONES.dimensionID) {
			addAchievement(GOTAchievement.ENTER_GOT);
		}
		if (entityplayer.inventory.hasItem(GOTRegistry.pouch)) {
			addAchievement(GOTAchievement.GET_POUCH);
		}
		if (!hasAchievement(GOTAchievement.HIRE_GOLDEN_COMPANY) && pdTick % 20 == 0) {
			int hiredUnits = 0;
			List<GOTEntityGoldenMan> nearbyNPCs = world.getEntitiesWithinAABB(GOTEntityGoldenMan.class, entityplayer.boundingBox.expand(64.0, 64.0, 64.0));
			for (GOTEntityNPC npc : nearbyNPCs) {
				if (!npc.hiredNPCInfo.isActive || npc.hiredNPCInfo.getHiringPlayer() != entityplayer) {
					continue;
				}
				++hiredUnits;
			}
			if (hiredUnits >= 10) {
				addAchievement(GOTAchievement.HIRE_GOLDEN_COMPANY);
			}
		}
		if (!hasAchievement(GOTAchievement.HUNDREDS) && pdTick % 20 == 0) {
			int hiredUnits = 0;
			List<GOTEntityNPC> nearbyNPCs = world.getEntitiesWithinAABB(GOTEntityNPC.class, entityplayer.boundingBox.expand(64.0, 64.0, 64.0));
			for (GOTEntityNPC npc : nearbyNPCs) {
				if (!npc.hiredNPCInfo.isActive || npc.hiredNPCInfo.getHiringPlayer() != entityplayer) {
					continue;
				}
				++hiredUnits;
			}
			if (hiredUnits >= 100) {
				addAchievement(GOTAchievement.HUNDREDS);
			}
		}
		fullMaterial = getBodyMaterial(entityplayer);
		if (fullMaterial != null && fullMaterial == GOTMaterial.HAND) {
			addAchievement(GOTAchievement.WEAR_FULL_HAND);
		}
		fullMaterial = getHelmetMaterial(entityplayer);
		if (fullMaterial != null && fullMaterial == GOTMaterial.HELMET) {
			addAchievement(GOTAchievement.WEAR_FULL_HELMET);
		}
		fullMaterial = getFullArmorMaterialWithoutHelmet(entityplayer);
		if (fullMaterial != null && fullMaterial == GOTMaterial.MOSSOVY) {
			addAchievement(GOTAchievement.WEAR_FULL_MOSSOVY);
		}
		fullMaterial = getFullArmorMaterialWithoutHelmet(entityplayer);
		if (fullMaterial != null && fullMaterial == GOTMaterial.ICE) {
			addAchievement(GOTAchievement.WEAR_FULL_WHITEWALKERS);
		}
	}

	public void save(NBTTagCompound playerData) {
		NBTTagList alignmentTags = new NBTTagList();
		for (Map.Entry<GOTFaction, Float> entry : alignments.entrySet()) {
			GOTFaction faction = entry.getKey();
			float alignment = entry.getValue();
			NBTTagCompound nbt6 = new NBTTagCompound();
			nbt6.setString("Faction", faction.codeName());
			nbt6.setFloat("AlignF", alignment);
			alignmentTags.appendTag(nbt6);
		}
		playerData.setTag("AlignmentMap", alignmentTags);
		NBTTagList factionDataTags = new NBTTagList();
		for (Map.Entry<GOTFaction, GOTFactionData> entry : factionDataMap.entrySet()) {
			GOTFaction faction = entry.getKey();
			GOTFactionData data = entry.getValue();
			NBTTagCompound nbt4 = new NBTTagCompound();
			nbt4.setString("Faction", faction.codeName());
			data.save(nbt4);
			factionDataTags.appendTag(nbt4);
		}
		playerData.setTag("FactionData", factionDataTags);
		playerData.setString("CurrentFaction", viewingFaction.codeName());
		NBTTagList prevRegionFactionTags = new NBTTagList();
		for (Map.Entry<GOTDimension.DimensionRegion, GOTFaction> entry : prevRegionFactions.entrySet()) {
			GOTDimension.DimensionRegion region = entry.getKey();
			GOTFaction faction = entry.getValue();
			NBTTagCompound nbt3 = new NBTTagCompound();
			nbt3.setString("Region", region.codeName());
			nbt3.setString("Faction", faction.codeName());
			prevRegionFactionTags.appendTag(nbt3);
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
		if (cape != null) {
			playerData.setString("Cape", cape.name());
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
			NBTTagCompound nbt7 = new NBTTagCompound();
			quest.writeToNBT(nbt7);
			miniquestTags.appendTag(nbt7);
		}
		playerData.setTag("MiniQuests", miniquestTags);
		NBTTagList miniquestCompletedTags = new NBTTagList();
		for (GOTMiniQuest quest : miniQuestsCompleted) {
			NBTTagCompound nbt8 = new NBTTagCompound();
			quest.writeToNBT(nbt8);
			miniquestCompletedTags.appendTag(nbt8);
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
			NBTTagCompound nbt9 = new NBTTagCompound();
			nbt9.setString("Name", region.name());
			unlockedFTRegionTags.appendTag(nbt9);
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
			NBTTagCompound nbt10 = new NBTTagCompound();
			nbt10.setString("SharingPlayer", key.sharingPlayer.toString());
			nbt10.setInteger("CustomID", key.waypointID);
			cwpSharedUnlockedTags.appendTag(nbt10);
		}
		playerData.setTag("CWPSharedUnlocked", cwpSharedUnlockedTags);
		NBTTagList cwpSharedHiddenTags = new NBTTagList();
		for (CWPSharedKey key : cwpSharedHidden) {
			NBTTagCompound nbt11 = new NBTTagCompound();
			nbt11.setString("SharingPlayer", key.sharingPlayer.toString());
			nbt11.setInteger("CustomID", key.waypointID);
			cwpSharedHiddenTags.appendTag(nbt11);
		}
		playerData.setTag("CWPSharedHidden", cwpSharedHiddenTags);
		NBTTagList wpUseTags = new NBTTagList();
		for (Map.Entry<GOTWaypoint, Integer> e : wpUseCounts.entrySet()) {
			GOTAbstractWaypoint wp = e.getKey();
			int count = e.getValue();
			NBTTagCompound nbt12 = new NBTTagCompound();
			nbt12.setString("WPName", wp.getCodeName());
			nbt12.setInteger("Count", count);
			wpUseTags.appendTag(nbt12);
		}
		playerData.setTag("WPUses", wpUseTags);
		NBTTagList cwpUseTags = new NBTTagList();
		for (Map.Entry<Integer, Integer> e : cwpUseCounts.entrySet()) {
			int ID = e.getKey();
			int count = e.getValue();
			NBTTagCompound nbt5 = new NBTTagCompound();
			nbt5.setInteger("CustomID", ID);
			nbt5.setInteger("Count", count);
			cwpUseTags.appendTag(nbt5);
		}
		playerData.setTag("CWPUses", cwpUseTags);
		NBTTagList cwpSharedUseTags = new NBTTagList();
		for (Map.Entry<CWPSharedKey, Integer> e : cwpSharedUseCounts.entrySet()) {
			CWPSharedKey key = e.getKey();
			int count = e.getValue();
			NBTTagCompound nbt2 = new NBTTagCompound();
			nbt2.setString("SharingPlayer", key.sharingPlayer.toString());
			nbt2.setInteger("CustomID", key.waypointID);
			nbt2.setInteger("Count", count);
			cwpSharedUseTags.appendTag(nbt2);
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
		playerData.setBoolean("TeleportedME", teleportedKW);
		playerData.setBoolean("AskedForJaqen", askedForJaqen);
		playerData.setBoolean("TableSwitched", tableSwitched);
		playerData.setInteger("Balance", balance);
		NBTTagCompound questNBT = new NBTTagCompound();
		questData.save(questNBT);
		playerData.setTag("QuestData", questNBT);
		playerData.setBoolean("CheckedMenu", checkedMenu);
		needsSave = false;
	}

	public void searchForBiomeWaypoints(EntityPlayer entityplayer, World world) {
		int i = MathHelper.floor_double(entityplayer.posX);
		MathHelper.floor_double(entityplayer.boundingBox.minY);
		int k = MathHelper.floor_double(entityplayer.posZ);
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		if (biome instanceof GOTBiome) {
			Region biomeRegion;
			GOTBiome GOTbiome = (GOTBiome) biome;
			biomeRegion = GOTbiome.getBiomeWaypoints();
			if (biomeRegion != null) {
				unlockFTRegion(biomeRegion);
			}
		}
	}

	public List<GOTMiniQuest> selectMiniQuests(MiniQuestSelector selector) {
		ArrayList<GOTMiniQuest> ret = new ArrayList<>();
		ArrayList<GOTMiniQuest> threadSafe = new ArrayList<>(miniQuests);
		for (GOTMiniQuest quest : threadSafe) {
			if (!selector.include(quest)) {
				continue;
			}
			ret.add(quest);
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
			if (fs == null) {
				continue;
			}
			sendFellowshipPacket(fs);
			fs.doRetroactiveWaypointSharerCheckIfNeeded();
			checkIfStillWaypointSharerForFellowship(fs);
		}
		HashSet<GOTFellowshipInvite> staleFellowshipInvites = new HashSet<>();
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
		EntityPlayer entityplayer;
		alcoholTolerance = i;
		markDirty();
		if (alcoholTolerance >= 250 && (entityplayer = getPlayer()) != null && !entityplayer.worldObj.isRemote) {
			addAchievement(GOTAchievement.GAIN_HIGH_ALCOHOL_TOLERANCE);
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
			if (fs == null) {
				continue;
			}
			fs.updateForAllMembers(new FellowshipUpdateType.UpdatePlayerTitle(playerUUID, playerTitle));
		}
	}

	public void setPledgeBreakCooldown(int i) {
		boolean bigChange;
		EntityPlayer entityplayer;
		int preCD = pledgeBreakCooldown;
		GOTFaction preBroken = brokenPledgeFaction;
		pledgeBreakCooldown = i;
		bigChange = (pledgeBreakCooldown == 0 || preCD == 0) && pledgeBreakCooldown != preCD;
		if (pledgeBreakCooldown > pledgeBreakCooldownStart) {
			setPledgeBreakCooldownStart(pledgeBreakCooldown);
			bigChange = true;
		}
		if (pledgeBreakCooldown <= 0 && preBroken != null) {
			setPledgeBreakCooldownStart(0);
			setBrokenPledgeFaction(null);
			bigChange = true;
		}
		if (bigChange || GOTPlayerData.isTimerAutosaveTick()) {
			markDirty();
		}
		if ((bigChange || pledgeBreakCooldown % 5 == 0) && (entityplayer = getPlayer()) != null && !entityplayer.worldObj.isRemote) {
			GOTPacketBrokenPledge packet = new GOTPacketBrokenPledge(pledgeBreakCooldown, pledgeBreakCooldownStart, brokenPledgeFaction);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
		if (pledgeBreakCooldown == 0 && preCD != pledgeBreakCooldown && (entityplayer = getPlayer()) != null && !entityplayer.worldObj.isRemote) {
			String brokenName = preBroken == null ? StatCollector.translateToLocal("got.gui.factions.pledgeUnknown") : preBroken.factionName();
			ChatComponentTranslation msg = new ChatComponentTranslation("got.chat.pledgeBreakCooldown", brokenName);
			entityplayer.addChatMessage(msg);
		}
	}

	public void setPledgeBreakCooldownStart(int i) {
		pledgeBreakCooldownStart = i;
		markDirty();
	}

	public void setPledgeFaction(GOTFaction fac) {
		EntityPlayer entityplayer;
		pledgeFaction = fac;
		pledgeKillCooldown = 0;
		markDirty();
		if (fac != null) {
			checkAlignmentAchievements(fac, getAlignment(fac));
			addAchievement(GOTAchievement.PLEDGE_SERVICE);
		}
		entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			if (fac != null) {
				World world = entityplayer.worldObj;
				world.playSoundAtEntity(entityplayer, "got:event.pledge", 1.0f, 1.0f);
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

	public void setTeleportedME(boolean flag) {
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
		this.setTimeSinceFT(i, false);
	}

	public void setTimeSinceFT(int i, boolean forceUpdate) {
		boolean bigChange;
		EntityPlayer entityplayer;
		int preTick = ftSinceTick;
		ftSinceTick = i = Math.max(0, i);
		bigChange = (ftSinceTick == 0 || preTick == 0) && ftSinceTick != preTick || preTick < 0 && ftSinceTick >= 0;
		if (bigChange || GOTPlayerData.isTimerAutosaveTick() || forceUpdate) {
			markDirty();
		}
		if ((bigChange || ftSinceTick % 5 == 0 || forceUpdate) && (entityplayer = getPlayer()) != null && !entityplayer.worldObj.isRemote) {
			GOTPacketFTTimer packet = new GOTPacketFTTimer(ftSinceTick);
			GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		}
	}

	public void setTimeSinceFTWithUpdate(int i) {
		this.setTimeSinceFT(i, true);
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
		uuidToMountTime = uuidToMount != null ? 10 : 0;
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
			ArrayList<GOTCustomWaypoint> unlockWaypoints = new ArrayList<>();
			for (GOTCustomWaypoint waypoint : customWaypointsShared) {
				if (!waypoint.isShared() || waypoint.isSharedUnlocked() || !waypoint.canUnlockShared(entityplayer)) {
					continue;
				}
				unlockWaypoints.add(waypoint);
			}
			for (GOTCustomWaypoint waypoint : unlockWaypoints) {
				unlockSharedCustomWaypoint(waypoint);
			}
		}
	}

	public void unshareFellowshipFromOwnCustomWaypoints(GOTFellowship fs) {
		for (GOTCustomWaypoint waypoint : customWaypoints) {
			if (!waypoint.hasSharedFellowship(fs)) {
				continue;
			}
			customWaypointRemoveSharedFellowship(waypoint, fs);
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
		int ftClockIncrease;
		if (lastOnlineTime <= 0L) {
			return;
		}
		MinecraftServer.getServer();
		ftClockIncrease = (int) ((int) (getCurrentOnlineTime() - lastOnlineTime) * 0.1);
		if (ftClockIncrease > 0) {
			setTimeSinceFTWithUpdate(ftSinceTick + ftClockIncrease);
		}
	}

	public void updateFellowship(GOTFellowship fs, FellowshipUpdateType updateType) {
		List<UUID> playersToCheckSharedWaypointsFrom;
		EntityPlayer entityplayer = getPlayer();
		if (entityplayer != null && !entityplayer.worldObj.isRemote) {
			IMessage updatePacket = updateType.createUpdatePacket(this, fs);
			if (updatePacket != null) {
				GOTPacketHandler.networkWrapper.sendTo(updatePacket, (EntityPlayerMP) entityplayer);
			} else {
				GOTLog.logger.error("No associated packet for fellowship update type " + updateType.getClass().getName());
			}
		}
		playersToCheckSharedWaypointsFrom = updateType.getPlayersToCheckSharedWaypointsFrom(fs);
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

	public static GOTMaterial getBodyMaterial(EntityLivingBase entity) {
		ItemStack item = entity.getEquipmentInSlot(3);
		if (item == null || !(item.getItem() instanceof GOTItemArmor)) {
			return null;
		}
		return ((GOTItemArmor) item.getItem()).getGOTArmorMaterial();
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

	public static GOTMaterial getFullArmorMaterialWithoutHelmet(EntityLivingBase entity) {
		GOTMaterial material = null;
		for (int i = 1; i <= 3; ++i) {
			ItemStack item = entity.getEquipmentInSlot(i);
			if (item == null || !(item.getItem() instanceof GOTItemArmor)) {
				return null;
			}
			GOTMaterial itemMaterial = ((GOTItemArmor) item.getItem()).getGOTArmorMaterial();
			if (material != null && itemMaterial != material) {
				return null;
			}
			material = itemMaterial;
		}
		return material;
	}

	public static GOTMaterial getHelmetMaterial(EntityLivingBase entity) {
		ItemStack item = entity.getEquipmentInSlot(4);
		if (item == null || !(item.getItem() instanceof GOTItemArmor)) {
			return null;
		}
		return ((GOTItemArmor) item.getItem()).getGOTArmorMaterial();
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