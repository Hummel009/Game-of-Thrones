package got.common.entity.other;

import java.util.*;

import com.google.common.base.Predicate;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.*;
import got.common.network.*;
import got.common.quest.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

public class GOTEntityQuestInfo {
	public static int maxOfferTime = 24000;
	public GOTEntityNPC theNPC;
	public GOTMiniQuest miniquestOffer;
	public int offerTime = 0;
	public int offerChance;
	public float minAlignment;
	public Map<UUID, GOTMiniQuest> playerSpecificOffers = new HashMap<>();
	public List<EntityPlayer> openOfferPlayers = new ArrayList<>();
	public Map<UUID, Boolean> playerPacketCache = new HashMap<>();
	public boolean clientIsOffering;
	public int clientOfferColor;
	public List<UUID> activeQuestPlayers = new ArrayList<>();
	public Predicate<EntityPlayer> bountyHelpPredicate;
	public Predicate<EntityPlayer> bountyHelpConsumer;
	public MiniQuestSelector.BountyActiveAnyFaction activeBountySelector;

	public GOTEntityQuestInfo(GOTEntityNPC npc) {
		theNPC = npc;
		offerChance = 20000;
		minAlignment = 0.0f;
		bountyHelpPredicate = player -> theNPC.getRNG().nextInt(3) == 0;
		bountyHelpConsumer = player -> true;
		activeBountySelector = new MiniQuestSelector.BountyActiveFaction(() -> theNPC.getFaction());
	}

	public void addActiveQuestPlayer(EntityPlayer entityplayer) {
		activeQuestPlayers.add(entityplayer.getUniqueID());
	}

	public void addOpenOfferPlayer(EntityPlayer entityplayer) {
		openOfferPlayers.add(entityplayer);
	}

	public boolean anyActiveQuestPlayers() {
		return !activeQuestPlayers.isEmpty();
	}

	public boolean anyOpenOfferPlayers() {
		return !openOfferPlayers.isEmpty();
	}

	public boolean canGenerateQuests() {
		if (!GOTConfig.allowMiniquests || theNPC.isChild() || theNPC.isDrunkard()) {
			return false;
		}
		return !theNPC.isTrader() && !theNPC.isTraderEscort && !theNPC.hiredNPCInfo.isActive;
	}

	public boolean canOfferQuestsTo(EntityPlayer entityplayer) {
		if (canGenerateQuests() && theNPC.isFriendlyAndAligned(entityplayer) && theNPC.getAttackTarget() == null) {
			float alignment = GOTLevelData.getData(entityplayer).getAlignment(theNPC.getFaction());
			return alignment >= minAlignment;
		}
		return false;
	}

	public void clearMiniQuestOffer() {
		setMiniQuestOffer(null, 0);
	}

	public void clearPlayerSpecificOffer(EntityPlayer entityplayer) {
		playerSpecificOffers.remove(entityplayer.getUniqueID());
	}

	public GOTMiniQuest generateRandomMiniQuest() {
		int tries = 8;
		for (int l = 0; l < tries; ++l) {
			GOTMiniQuest quest = theNPC.createMiniQuest();
			if (quest == null) {
				continue;
			}
			if (quest.isValidQuest()) {
				return quest;
			}
			FMLLog.severe("Created an invalid GOT miniquest " + quest.speechBankStart);
		}
		return null;
	}

	public GOTMiniQuest getOfferFor(EntityPlayer entityplayer) {
		return this.getOfferFor(entityplayer, null);
	}

	public GOTMiniQuest getOfferFor(EntityPlayer entityplayer, boolean[] isSpecific) {
		UUID id = entityplayer.getUniqueID();
		if (playerSpecificOffers.containsKey(id)) {
			if (isSpecific != null) {
				isSpecific[0] = true;
			}
			return playerSpecificOffers.get(id);
		}
		if (isSpecific != null) {
			isSpecific[0] = false;
		}
		return miniquestOffer;
	}

	public boolean interact(EntityPlayer entityplayer) {
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		List<GOTMiniQuest> thisNPCQuests = playerData.getMiniQuestsForEntity(theNPC, true);
		if (thisNPCQuests.isEmpty()) {
			for (GOTMiniQuest quest : playerData.getActiveMiniQuests()) {
				if (quest.entityUUID.equals(theNPC.getUniqueID()) || !quest.onInteractOther(entityplayer, theNPC)) {
					continue;
				}
				return true;
			}
		}
		if (canOfferQuestsTo(entityplayer)) {
			List<GOTMiniQuest> bountyQuests;
			if (!thisNPCQuests.isEmpty()) {
				GOTMiniQuest activeQuest = thisNPCQuests.get(0);
				activeQuest.onInteract(entityplayer, theNPC);
				if (activeQuest.isCompleted()) {
					removeActiveQuestPlayer(entityplayer);
				} else {
					playerData.setTrackingMiniQuest(activeQuest);
				}
				return true;
			}
			GOTMiniQuest offer = this.getOfferFor(entityplayer);
			if (offer != null && offer.isValidQuest() && offer.canPlayerAccept(entityplayer)) {
				List<GOTMiniQuest> questsForFaction = playerData.getMiniQuestsForFaction(theNPC.getFaction(), true);
				if (questsForFaction.size() < GOTMiniQuest.MAX_MINIQUESTS_PER_FACTION) {
					sendMiniquestOffer(entityplayer, offer);
					return true;
				}
				theNPC.sendSpeechBank(entityplayer, offer.speechBankTooMany, offer);
				return true;
			}
			GOTMiniQuestFactory bountyHelpSpeechDir = theNPC.getBountyHelpSpeechDir();
			if (bountyHelpSpeechDir != null && bountyHelpPredicate.apply(entityplayer) && !(bountyQuests = playerData.selectMiniQuests(activeBountySelector)).isEmpty()) {
				GOTWaypoint lastWP;
				GOTMiniQuestBounty bQuest = (GOTMiniQuestBounty) bountyQuests.get(theNPC.getRNG().nextInt(bountyQuests.size()));
				UUID targetID = bQuest.targetID;
				String objective = bQuest.targetName;
				GOTPlayerData targetData = GOTLevelData.getData(targetID);
				GOTMiniQuestBounty.BountyHelp helpType = GOTMiniQuestBounty.BountyHelp.getRandomHelpType(theNPC.getRNG());
				String location = null;
				if (helpType == GOTMiniQuestBounty.BountyHelp.BIOME) {
					GOTBiome lastBiome = targetData.getLastKnownBiome();
					if (lastBiome != null) {
						location = lastBiome.getBiomeDisplayName();
					}
				} else if (helpType == GOTMiniQuestBounty.BountyHelp.WAYPOINT && (lastWP = targetData.getLastKnownWaypoint()) != null) {
					location = lastWP.getDisplayName();
				}
				if (location != null) {
					String speechBank = "miniquest/" + bountyHelpSpeechDir.getBaseName() + "/_bountyHelp_" + helpType.speechName;
					theNPC.sendSpeechBank(entityplayer, speechBank, location, objective);
					bountyHelpConsumer.apply(entityplayer);
					return true;
				}
			}
		}
		return false;
	}

	public void onDeath() {
		if (!theNPC.worldObj.isRemote && !activeQuestPlayers.isEmpty()) {
			for (UUID player : activeQuestPlayers) {
				List<GOTMiniQuest> playerQuests = GOTLevelData.getData(player).getMiniQuestsForEntity(theNPC, true);
				for (GOTMiniQuest quest : playerQuests) {
					if (!quest.isActive()) {
						continue;
					}
					quest.setEntityDead();
				}
			}
		}
	}

	public void onUpdate() {
		if (!theNPC.worldObj.isRemote) {
			if (miniquestOffer == null) {
				if (canGenerateQuests() && theNPC.getRNG().nextInt(offerChance) == 0) {
					miniquestOffer = generateRandomMiniQuest();
					if (miniquestOffer != null) {
						offerTime = 24000;
					}
				}
			} else if (!miniquestOffer.isValidQuest() || !canGenerateQuests()) {
				clearMiniQuestOffer();
			} else if (!anyOpenOfferPlayers()) {
				if (offerTime > 0) {
					--offerTime;
				} else {
					clearMiniQuestOffer();
				}
			}
			if (theNPC.ticksExisted % 10 == 0) {
				pruneActiveQuestPlayers();
			}
			if (theNPC.ticksExisted % 10 == 0) {
				sendDataToAllWatchers();
			}
		}
	}

	public void pruneActiveQuestPlayers() {
		if (!activeQuestPlayers.isEmpty()) {
			HashSet<UUID> removes = new HashSet<>();
			for (UUID player : activeQuestPlayers) {
				List<GOTMiniQuest> playerQuests = GOTLevelData.getData(player).getMiniQuestsForEntity(theNPC, true);
				if (playerQuests.isEmpty()) {
					removes.add(player);
					continue;
				}
				for (GOTMiniQuest quest : playerQuests) {
					quest.updateLocation(theNPC);
				}
			}
			activeQuestPlayers.removeAll(removes);
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		int i;
		UUID player;
		if (nbt.hasKey("MQOffer", 10)) {
			NBTTagCompound questData = nbt.getCompoundTag("MQOffer");
			miniquestOffer = GOTMiniQuest.loadQuestFromNBT(questData, null);
		}
		offerTime = nbt.getInteger("MQOfferTime");
		playerSpecificOffers.clear();
		if (nbt.hasKey("MQSpecificOffers")) {
			NBTTagList specificTags = nbt.getTagList("MQSpecificOffers", 10);
			for (i = 0; i < specificTags.tagCount(); ++i) {
				NBTTagCompound offerData = specificTags.getCompoundTagAt(i);
				try {
					UUID playerID = UUID.fromString(offerData.getString("OfferPlayerID"));
					GOTMiniQuest offer = GOTMiniQuest.loadQuestFromNBT(offerData, null);
					if (offer == null || !offer.isValidQuest()) {
						continue;
					}
					playerSpecificOffers.put(playerID, offer);
					continue;
				} catch (Exception e) {
					FMLLog.warning("Error loading NPC player-specific miniquest offer");
					e.printStackTrace();
				}
			}
		}
		activeQuestPlayers.clear();
		NBTTagList activeQuestTags = nbt.getTagList("ActiveQuestPlayers", 8);
		for (i = 0; i < activeQuestTags.tagCount(); ++i) {
			String s = activeQuestTags.getStringTagAt(i);
			UUID player2 = UUID.fromString(s);
			if (player2 == null) {
				continue;
			}
			activeQuestPlayers.add(player2);
		}
		if (nbt.hasKey("NPCMiniQuestPlayer") && (player = UUID.fromString(nbt.getString("NPCMiniQuestPlayer"))) != null) {
			activeQuestPlayers.add(player);
		}
	}

	public void receiveData(GOTPacketNPCIsOfferingQuest packet) {
		clientIsOffering = packet.offering;
		clientOfferColor = packet.offerColor;
	}

	public void receiveOfferResponse(EntityPlayer entityplayer, boolean accept) {
		removeOpenOfferPlayer(entityplayer);
		if (accept) {
			boolean[] container = new boolean[1];
			GOTMiniQuest quest = this.getOfferFor(entityplayer, container);
			boolean isSpecific = container[0];
			if (quest != null && quest.isValidQuest() && canOfferQuestsTo(entityplayer)) {
				quest.setPlayerData(GOTLevelData.getData(entityplayer));
				quest.start(entityplayer, theNPC);
				if (isSpecific) {
					clearPlayerSpecificOffer(entityplayer);
				} else {
					clearMiniQuestOffer();
				}
			}
		}
	}

	public void removeActiveQuestPlayer(EntityPlayer entityplayer) {
		activeQuestPlayers.remove(entityplayer.getUniqueID());
	}

	public void removeOpenOfferPlayer(EntityPlayer entityplayer) {
		openOfferPlayers.remove(entityplayer);
	}

	public void sendData(EntityPlayerMP entityplayer) {
		GOTMiniQuest questOffer = this.getOfferFor(entityplayer);
		boolean isOffering = questOffer != null && canOfferQuestsTo(entityplayer);
		int color = questOffer != null ? questOffer.getQuestColor() : 0;
		boolean prevOffering = false;
		UUID uuid = entityplayer.getUniqueID();
		if (playerPacketCache.containsKey(uuid)) {
			prevOffering = playerPacketCache.get(uuid);
		}
		playerPacketCache.put(uuid, isOffering);
		if (isOffering != prevOffering) {
			GOTPacketNPCIsOfferingQuest packet = new GOTPacketNPCIsOfferingQuest(theNPC.getEntityId(), isOffering, color);
			GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
		}
	}

	public void sendDataToAllWatchers() {
		int x = MathHelper.floor_double(theNPC.posX) >> 4;
		int z = MathHelper.floor_double(theNPC.posZ) >> 4;
		PlayerManager playermanager = ((WorldServer) theNPC.worldObj).getPlayerManager();
		List players = theNPC.worldObj.playerEntities;
		for (Object obj : players) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) obj;
			if (!playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
				continue;
			}
			sendData(entityplayer);
		}
	}

	public void sendMiniquestOffer(EntityPlayer entityplayer, GOTMiniQuest quest) {
		NBTTagCompound nbt = new NBTTagCompound();
		quest.writeToNBT(nbt);
		GOTPacketMiniquestOffer packet = new GOTPacketMiniquestOffer(theNPC.getEntityId(), nbt);
		GOTPacketHandler.networkWrapper.sendTo((IMessage) packet, (EntityPlayerMP) entityplayer);
		addOpenOfferPlayer(entityplayer);
	}

	public void setActiveBountySelector(MiniQuestSelector.BountyActiveAnyFaction sel) {
		activeBountySelector = sel;
	}

	public void setBountyHelpConsumer(Predicate<EntityPlayer> predicate) {
		bountyHelpConsumer = predicate;
	}

	public void setBountyHelpPredicate(Predicate<EntityPlayer> predicate) {
		bountyHelpPredicate = predicate;
	}

	public void setIsLegendaryQuest() {
		offerChance = 1;
		minAlignment = 100;
	}

	public void setMinAlignment(float f) {
		minAlignment = f;
	}

	public void setMiniQuestOffer(GOTMiniQuest quest, int time) {
		miniquestOffer = quest;
		offerTime = time;
	}

	public void setOfferChance(int i) {
		offerChance = i;
	}

	public void setPlayerSpecificOffer(EntityPlayer entityplayer, GOTMiniQuest quest) {
		playerSpecificOffers.put(entityplayer.getUniqueID(), quest);
	}

	public void writeToNBT(NBTTagCompound nbt) {
		if (miniquestOffer != null) {
			NBTTagCompound questData = new NBTTagCompound();
			miniquestOffer.writeToNBT(questData);
			nbt.setTag("MQOffer", questData);
		}
		nbt.setInteger("MQOfferTime", offerTime);
		NBTTagList specificTags = new NBTTagList();
		for (Map.Entry<UUID, GOTMiniQuest> e : playerSpecificOffers.entrySet()) {
			UUID playerID = e.getKey();
			GOTMiniQuest offer = e.getValue();
			NBTTagCompound offerData = new NBTTagCompound();
			offerData.setString("OfferPlayerID", playerID.toString());
			offer.writeToNBT(offerData);
			specificTags.appendTag(offerData);
		}
		nbt.setTag("MQSpecificOffers", specificTags);
		NBTTagList activeQuestTags = new NBTTagList();
		for (UUID player : activeQuestPlayers) {
			String s = player.toString();
			activeQuestTags.appendTag(new NBTTagString(s));
		}
		nbt.setTag("ActiveQuestPlayers", activeQuestTags);
	}

}