package got.common.entity.other.info;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.entity.other.GOTEntityNPC;
import got.common.network.GOTPacketHandler;
import got.common.network.GOTPacketMiniquestOffer;
import got.common.network.GOTPacketNPCIsOfferingQuest;
import got.common.quest.GOTMiniQuest;
import got.common.quest.GOTMiniQuestBounty;
import got.common.quest.GOTMiniQuestFactory;
import got.common.quest.GOTMiniQuestSelector;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

import java.util.*;
import java.util.function.Predicate;

public class GOTQuestInfo {
	private final GOTEntityNPC theNPC;

	private final Map<UUID, GOTMiniQuest> playerSpecificOffers = new HashMap<>();
	private final Map<UUID, Boolean> playerPacketCache = new HashMap<>();
	private final Predicate<EntityPlayer> bountyHelpPredicate;
	private final Predicate<EntityPlayer> bountyHelpConsumer;
	private final Collection<EntityPlayer> openOfferPlayers = new ArrayList<>();
	private final Collection<UUID> activeQuestPlayers = new ArrayList<>();
	private final GOTMiniQuestSelector.BountyActiveAnyFaction activeBountySelector;

	private GOTMiniQuest miniquestOffer;

	private boolean clientIsOffering;

	private int offerTime;
	private int offerChance;
	private int clientOfferColor;

	private float minAlignment;

	public GOTQuestInfo(GOTEntityNPC npc) {
		theNPC = npc;
		offerChance = 20000;
		minAlignment = 0.0f;
		bountyHelpPredicate = player -> theNPC.getRNG().nextInt(3) == 0;
		bountyHelpConsumer = player -> true;
		activeBountySelector = new GOTMiniQuestSelector.BountyActiveFaction(theNPC::getFaction);
	}

	public void addActiveQuestPlayer(EntityPlayer entityplayer) {
		activeQuestPlayers.add(entityplayer.getUniqueID());
	}

	private void addOpenOfferPlayer(EntityPlayer entityplayer) {
		openOfferPlayers.add(entityplayer);
	}

	public boolean anyActiveQuestPlayers() {
		return !activeQuestPlayers.isEmpty();
	}

	public boolean anyOpenOfferPlayers() {
		return !openOfferPlayers.isEmpty();
	}

	private boolean canGenerateQuests() {
		return GOTConfig.allowMiniquests && !theNPC.isChild() && !theNPC.isDrunkard() && !theNPC.isTrader() && !theNPC.getHireableInfo().isActive();
	}

	private boolean canOfferQuestsTo(EntityPlayer entityplayer) {
		if (canGenerateQuests() && theNPC.isFriendlyAndAligned(entityplayer) && theNPC.getAttackTarget() == null) {
			float alignment = GOTLevelData.getData(entityplayer).getAlignment(theNPC.getFaction());
			return alignment >= minAlignment;
		}
		return false;
	}

	private void clearMiniQuestOffer() {
		setMiniQuestOffer(null, 0);
	}

	private void clearPlayerSpecificOffer(EntityPlayer entityplayer) {
		playerSpecificOffers.remove(entityplayer.getUniqueID());
	}

	private GOTMiniQuest generateRandomMiniQuest() {
		int tries = 8;
		for (int l = 0; l < tries; ++l) {
			GOTMiniQuestFactory questFactory = theNPC.getMiniQuestFactory();
			if (questFactory == null) {
				continue;
			}
			GOTMiniQuest quest = questFactory.createQuest(theNPC);
			if (quest.isValidQuest()) {
				return quest;
			}
			FMLLog.severe("Created an invalid GOT miniquest " + quest.getSpeechBankStart());
		}
		return null;
	}

	public GOTMiniQuest getOfferFor(EntityPlayer entityplayer) {
		return getOfferFor(entityplayer, null);
	}

	private GOTMiniQuest getOfferFor(EntityPlayer entityplayer, boolean[] isSpecific) {
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
				if (quest.getEntityUUID().equals(theNPC.getUniqueID()) || !quest.onInteractOther(entityplayer, theNPC)) {
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
			GOTMiniQuest offer = getOfferFor(entityplayer);
			if (offer != null && offer.isValidQuest() && offer.canPlayerAccept(entityplayer)) {
				List<GOTMiniQuest> questsForFaction = playerData.getMiniQuestsForFaction(theNPC.getFaction(), true);
				if (questsForFaction.size() < GOTMiniQuest.MAX_MINIQUESTS_PER_FACTION) {
					sendMiniquestOffer(entityplayer, offer);
					return true;
				}
				theNPC.sendSpeechBank(entityplayer, offer.getSpeechBankTooMany(), offer);
				return true;
			}
			GOTMiniQuestFactory bountyHelpSpeechDir = theNPC.getMiniQuestFactory();
			if (bountyHelpSpeechDir != null && bountyHelpPredicate.test(entityplayer) && !(bountyQuests = playerData.selectMiniQuests(activeBountySelector)).isEmpty()) {
				GOTWaypoint lastWP;
				GOTMiniQuestBounty bQuest = (GOTMiniQuestBounty) bountyQuests.get(theNPC.getRNG().nextInt(bountyQuests.size()));
				UUID targetID = bQuest.getTargetID();
				String objective = bQuest.getTargetName();
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
					String speechBank = "miniquest/" + bountyHelpSpeechDir.getBaseName() + "/_bountyHelp_" + helpType.getSpeechName();
					theNPC.sendSpeechBank(entityplayer, speechBank, location, objective);
					bountyHelpConsumer.test(entityplayer);
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

	private void pruneActiveQuestPlayers() {
		if (!activeQuestPlayers.isEmpty()) {
			Collection<UUID> removes = new HashSet<>();
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
			activeQuestPlayers.add(player2);
		}
		if (nbt.hasKey("NPCMiniQuestPlayer")) {
			String playerString = nbt.getString("NPCMiniQuestPlayer");
			UUID player = UUID.fromString(playerString);
			activeQuestPlayers.add(player);
		}
	}

	public void receiveData(GOTPacketNPCIsOfferingQuest packet) {
		clientIsOffering = packet.isOffering();
		clientOfferColor = packet.getOfferColor();
	}

	public void receiveOfferResponse(EntityPlayer entityplayer, boolean accept) {
		removeOpenOfferPlayer(entityplayer);
		if (accept) {
			boolean[] container = new boolean[1];
			GOTMiniQuest quest = getOfferFor(entityplayer, container);
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

	private void removeActiveQuestPlayer(EntityPlayer entityplayer) {
		activeQuestPlayers.remove(entityplayer.getUniqueID());
	}

	private void removeOpenOfferPlayer(EntityPlayer entityplayer) {
		openOfferPlayers.remove(entityplayer);
	}

	public void sendData(EntityPlayerMP entityplayer) {
		GOTMiniQuest questOffer = getOfferFor(entityplayer);
		boolean isOffering = questOffer != null && canOfferQuestsTo(entityplayer);
		int color = questOffer != null ? questOffer.getQuestColor() : 0;
		boolean prevOffering = false;
		UUID uuid = entityplayer.getUniqueID();
		if (playerPacketCache.containsKey(uuid)) {
			prevOffering = playerPacketCache.get(uuid);
		}
		playerPacketCache.put(uuid, isOffering);
		if (isOffering != prevOffering) {
			IMessage packet = new GOTPacketNPCIsOfferingQuest(theNPC.getEntityId(), isOffering, color);
			GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, entityplayer);
		}
	}

	private void sendDataToAllWatchers() {
		int x = MathHelper.floor_double(theNPC.posX) >> 4;
		int z = MathHelper.floor_double(theNPC.posZ) >> 4;
		PlayerManager playermanager = ((WorldServer) theNPC.worldObj).getPlayerManager();
		List<EntityPlayer> players = theNPC.worldObj.playerEntities;
		for (EntityPlayer obj : players) {
			if (playermanager.isPlayerWatchingChunk((EntityPlayerMP) obj, x, z)) {
				sendData((EntityPlayerMP) obj);
			}
		}
	}

	private void sendMiniquestOffer(EntityPlayer entityplayer, GOTMiniQuest quest) {
		NBTTagCompound nbt = new NBTTagCompound();
		quest.writeToNBT(nbt);
		IMessage packet = new GOTPacketMiniquestOffer(theNPC.getEntityId(), nbt);
		GOTPacketHandler.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) entityplayer);
		addOpenOfferPlayer(entityplayer);
	}

	private void setMiniQuestOffer(GOTMiniQuest quest, int time) {
		miniquestOffer = quest;
		offerTime = time;
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

	public void setOfferChance(int offerChance) {
		this.offerChance = offerChance;
	}

	public boolean isClientIsOffering() {
		return clientIsOffering;
	}

	public int getClientOfferColor() {
		return clientOfferColor;
	}

	public void setMinAlignment(float minAlignment) {
		this.minAlignment = minAlignment;
	}
}