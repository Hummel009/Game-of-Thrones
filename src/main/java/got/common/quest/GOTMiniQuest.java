package got.common.quest;

import java.awt.Color;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

import cpw.mods.fml.common.FMLLog;
import got.common.*;
import got.common.database.*;
import got.common.entity.essos.qohor.GOTEntityQohorBlacksmith;
import got.common.entity.other.*;
import got.common.faction.*;
import got.common.item.other.*;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class GOTMiniQuest {
	private static Map<String, Class<? extends GOTMiniQuest>> nameToQuestMapping = new HashMap<>();
	private static Map<Class<? extends GOTMiniQuest>, String> questToNameMapping = new HashMap<>();
	private static int MAX_MINIQUESTS_PER_FACTION;
	private static double RENDER_HEAD_DISTANCE;
	static {
		GOTMiniQuest.registerQuestType("Collect", GOTMiniQuestCollect.class);
		GOTMiniQuest.registerQuestType("KillFaction", GOTMiniQuestKillFaction.class);
		GOTMiniQuest.registerQuestType("KillEntity", GOTMiniQuestKillEntity.class);
		GOTMiniQuest.registerQuestType("Bounty", GOTMiniQuestBounty.class);
		GOTMiniQuest.registerQuestType("Welcome", GOTMiniQuestWelcome.class);
		GOTMiniQuest.registerQuestType("Pickpocket", GOTMiniQuestPickpocket.class);
		setMaxQuestsPerFac(5);
		setRenderHeadDistance(12.0);
	}
	public GOTMiniQuestFactory questGroup;
	public GOTPlayerData playerData;
	private UUID questUUID;
	private UUID entityUUID;
	private String entityName;
	private String entityNameFull;
	public GOTFaction entityFaction;
	private int questColor;
	private int dateGiven;
	public GOTBiome biomeGiven;
	private float rewardFactor = 1.0f;
	private boolean willHire = false;
	private float hiringAlignment;
	private List<ItemStack> rewardItemTable = new ArrayList<>();
	private boolean completed;
	private int dateCompleted;
	private boolean isLegendary = false;
	private int coinsRewarded;
	private float alignmentRewarded;
	private boolean wasHired;
	private List<ItemStack> itemsRewarded = new ArrayList<>();
	private boolean entityDead;
	private Pair<ChunkCoordinates, Integer> lastLocation;
	private String speechBankStart;
	private String speechBankProgress;
	private String speechBankComplete;
	private String speechBankTooMany;
	private String quoteStart;

	private String quoteComplete;

	private List<String> quotesStages = new ArrayList<>();

	public GOTMiniQuest(GOTPlayerData pd) {
		playerData = pd;
		setQuestUUID(UUID.randomUUID());
	}

	public boolean anyRewardsGiven() {
		return getAlignmentRewarded() > 0.0f || getCoinsRewarded() > 0 || !getItemsRewarded().isEmpty();
	}

	public boolean canPlayerAccept(EntityPlayer entityplayer) {
		return true;
	}

	public boolean canRewardVariousExtraItems() {
		return true;
	}

	public void complete(EntityPlayer entityplayer, GOTEntityNPC npc) {
		int coins;
		GOTAchievement achievement;
		setCompleted(true);
		setDateCompleted(GOTDate.AegonCalendar.getCurrentDay());
		Random rand = npc.getRNG();
		ArrayList<ItemStack> dropItems = new ArrayList<>();
		float alignment = getAlignmentBonus();
		if (alignment != 0.0f) {
			alignment *= MathHelper.randomFloatClamp(rand, 0.75f, 1.25f);
			alignment = Math.max(alignment, 1.0f);
			GOTAlignmentValues.AlignmentBonus bonus = GOTAlignmentValues.createMiniquestBonus(alignment);
			GOTFaction rewardFaction = getAlignmentRewardFaction();
			if (!questGroup.isNoAlignRewardForEnemy() || playerData.getAlignment(rewardFaction) >= 0.0f) {
				GOTAlignmentBonusMap alignmentMap = playerData.addAlignment(entityplayer, bonus, rewardFaction, npc);
				setAlignmentRewarded(alignmentMap.get(rewardFaction));
			}
		}
		coins = getCoinBonus();
		if (coins != 0) {
			if (shouldRandomiseCoinReward()) {
				coins = Math.round(coins * MathHelper.randomFloatClamp(rand, 0.75f, 1.25f));
				if (rand.nextInt(12) == 0) {
					coins *= MathHelper.getRandomIntegerInRange(rand, 2, 5);
				}
			}
			setCoinsRewarded(coins = Math.max(coins, 1));
			int coinsRemain = coins;
			for (int l = GOTItemCoin.values.length - 1; l >= 0; --l) {
				int coinValue = GOTItemCoin.values[l];
				if (coinsRemain < coinValue) {
					continue;
				}
				int numCoins = coinsRemain / coinValue;
				coinsRemain -= numCoins * coinValue;
				while (numCoins > 64) {
					numCoins -= 64;
					dropItems.add(new ItemStack(GOTRegistry.coin, 64, l));
				}
				dropItems.add(new ItemStack(GOTRegistry.coin, numCoins, l));
			}
		}
		if (!getRewardItemTable().isEmpty()) {
			ItemStack item = getRewardItemTable().get(rand.nextInt(getRewardItemTable().size()));
			dropItems.add(item.copy());
			getItemsRewarded().add(item.copy());
		}
		if (canRewardVariousExtraItems()) {
			GOTLore lore;
			if (rand.nextInt(10) == 0 && questGroup != null && !questGroup.getLoreCategories().isEmpty() && (lore = GOTLore.getMultiRandomLore(questGroup.getLoreCategories(), rand, true)) != null) {
				ItemStack loreBook = lore.createLoreBook(rand);
				dropItems.add(loreBook.copy());
				getItemsRewarded().add(loreBook.copy());
			}
			if (rand.nextInt(15) == 0) {
				ItemStack modItem = GOTItemModifierTemplate.getRandomCommonTemplate(rand);
				dropItems.add(modItem.copy());
				getItemsRewarded().add(modItem.copy());
			}
			if (npc instanceof GOTEntityQohorBlacksmith && rand.nextInt(10) == 0) {
				ItemStack mithrilBook = new ItemStack(GOTRegistry.valyrianBook);
				dropItems.add(mithrilBook.copy());
				getItemsRewarded().add(mithrilBook.copy());
			}
		}
		if (!dropItems.isEmpty()) {
			boolean givePouch;
			givePouch = canRewardVariousExtraItems() && rand.nextInt(10) == 0;
			if (givePouch) {
				ItemStack pouch = npc.createNPCPouchDrop();
				npc.fillPouchFromListAndRetainUnfilled(pouch, dropItems);
				npc.entityDropItem(pouch, 0.0f);
				ItemStack pouchCopy = pouch.copy();
				pouchCopy.setTagCompound(null);
				getItemsRewarded().add(pouchCopy);
			}
			npc.dropItemList(dropItems);
		}
		if (isWillHire()) {
			GOTUnitTradeEntry tradeEntry = new GOTUnitTradeEntry(npc.getClass(), 0, getHiringAlignment());
			tradeEntry.setTask(GOTHiredNPCInfo.Task.WARRIOR);
			npc.hiredNPCInfo.hireUnit(entityplayer, false, entityFaction, tradeEntry, null, npc.ridingEntity);
			setWasHired(true);
		}
		if (isLegendary()) {
			npc.hiredNPCInfo.isActive = true;
		}
		updateQuest();
		playerData.completeMiniQuest(this);
		sendCompletedSpeech(entityplayer, npc);
		if (questGroup != null && (achievement = questGroup.getAchievement()) != null) {
			playerData.addAchievement(achievement);
		}
	}

	public abstract float getAlignmentBonus();

	public float getAlignmentRewarded() {
		return alignmentRewarded;
	}

	public GOTFaction getAlignmentRewardFaction() {
		return questGroup.checkAlignmentRewardFaction(entityFaction);
	}

	public abstract int getCoinBonus();

	public int getCoinsRewarded() {
		return coinsRewarded;
	}

	public abstract float getCompletionFactor();

	public int getDateCompleted() {
		return dateCompleted;
	}

	public int getDateGiven() {
		return dateGiven;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getEntityNameFull() {
		return entityNameFull;
	}

	public UUID getEntityUUID() {
		return entityUUID;
	}

	public String getFactionSubtitle() {
		if (entityFaction.isPlayableAlignmentFaction()) {
			return entityFaction.factionName();
		}
		return "";
	}

	public float getHiringAlignment() {
		return hiringAlignment;
	}

	public List<ItemStack> getItemsRewarded() {
		return itemsRewarded;
	}

	public ChunkCoordinates getLastLocation() {
		return lastLocation == null ? null : (ChunkCoordinates) lastLocation.getLeft();
	}

	public abstract String getObjectiveInSpeech();

	public GOTPlayerData getPlayerData() {
		return playerData;
	}

	public abstract String getProgressedObjectiveInSpeech();

	public int getQuestColor() {
		return questColor;
	}

	public float[] getQuestColorComponents() {
		return new Color(getQuestColor()).getColorComponents(null);
	}

	public String getQuestFailure() {
		return StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.dead", getEntityName());
	}

	public String getQuestFailureShorthand() {
		return StatCollector.translateToLocal("got.gui.redBook.mq.dead");
	}

	public abstract ItemStack getQuestIcon();

	public abstract String getQuestObjective();

	public abstract String getQuestProgress();

	public abstract String getQuestProgressShorthand();

	public UUID getQuestUUID() {
		return questUUID;
	}

	public String getQuoteComplete() {
		return quoteComplete;
	}

	public List<String> getQuotesStages() {
		return quotesStages;
	}

	public String getQuoteStart() {
		return quoteStart;
	}

	public float getRewardFactor() {
		return rewardFactor;
	}

	public List<ItemStack> getRewardItemTable() {
		return rewardItemTable;
	}

	public String getSpeechBankComplete() {
		return speechBankComplete;
	}

	public String getSpeechBankProgress() {
		return speechBankProgress;
	}

	public String getSpeechBankStart() {
		return speechBankStart;
	}

	public String getSpeechBankTooMany() {
		return speechBankTooMany;
	}

	public void handleEvent(GOTMiniQuestEvent event) {
	}

	public boolean isActive() {
		return !isCompleted() && !isFailed();
	}

	public boolean isCompleted() {
		return completed;
	}

	public boolean isFailed() {
		return entityDead;
	}

	public boolean isLegendary() {
		return isLegendary;
	}

	public boolean isValidQuest() {
		return getEntityUUID() != null && entityFaction != null;
	}

	public boolean isWasHired() {
		return wasHired;
	}

	public boolean isWillHire() {
		return willHire;
	}

	public void onInteract(EntityPlayer entityplayer, GOTEntityNPC npc) {
	}

	public boolean onInteractOther(EntityPlayer entityplayer, GOTEntityNPC npc) {
		return false;
	}

	public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
	}

	public void onKilledByPlayer(EntityPlayer entityplayer, EntityPlayer killer) {
	}

	public void onPlayerTick(EntityPlayer entityplayer) {
	}

	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound itemData;
		UUID u;
		ItemStack item;
		GOTMiniQuestFactory factory;
		NBTTagList itemTags;
		String recovery;
		setLegendary(nbt.getBoolean("Legendary"));
		if (nbt.hasKey("QuestGroup") && (factory = GOTMiniQuestFactory.forName(nbt.getString("QuestGroup"))) != null) {
			questGroup = factory;
		}
		if (nbt.hasKey("QuestUUID") && (u = UUID.fromString(nbt.getString("QuestUUID"))) != null) {
			setQuestUUID(u);
		}
		setEntityUUID(nbt.hasKey("UUIDMost") && nbt.hasKey("UUIDLeast") ? new UUID(nbt.getLong("UUIDMost"), nbt.getLong("UUIDLeast")) : UUID.fromString(nbt.getString("EntityUUID")));
		setEntityName(nbt.getString("Owner"));
		setEntityNameFull(nbt.hasKey("OwnerFull") ? nbt.getString("OwnerFull") : getEntityName());
		entityFaction = GOTFaction.forName(nbt.getString("Faction"));
		questColor = nbt.hasKey("Color") ? nbt.getInteger("Color") : entityFaction.getFactionColor();
		setDateGiven(nbt.getInteger("DateGiven"));
		if (nbt.hasKey("BiomeID")) {
			int biomeID = nbt.getByte("BiomeID") & 0xFF;
			String biomeDimName = nbt.getString("BiomeDim");
			GOTDimension biomeDim = GOTDimension.forName(biomeDimName);
			if (biomeDim != null) {
				biomeGiven = biomeDim.getBiomeList()[biomeID];
			}
		}
		setRewardFactor(nbt.hasKey("RewardFactor") ? nbt.getFloat("RewardFactor") : 1.0f);
		setWillHire(nbt.getBoolean("WillHire"));
		setHiringAlignment(nbt.hasKey("HiringAlignment") ? (float) nbt.getInteger("HiringAlignment") : nbt.getFloat("HiringAlignF"));
		getRewardItemTable().clear();
		if (nbt.hasKey("RewardItemTable")) {
			itemTags = nbt.getTagList("RewardItemTable", 10);
			for (int l = 0; l < itemTags.tagCount(); ++l) {
				itemData = itemTags.getCompoundTagAt(l);
				item = ItemStack.loadItemStackFromNBT(itemData);
				if (item == null) {
					continue;
				}
				getRewardItemTable().add(item);
			}
		}
		setCompleted(nbt.getBoolean("Completed"));
		setDateCompleted(nbt.getInteger("DateCompleted"));
		setCoinsRewarded(nbt.getShort("CoinReward"));
		setAlignmentRewarded(nbt.hasKey("AlignmentReward") ? (float) nbt.getShort("AlignmentReward") : nbt.getFloat("AlignRewardF"));
		setWasHired(nbt.getBoolean("WasHired"));
		getItemsRewarded().clear();
		if (nbt.hasKey("ItemRewards")) {
			itemTags = nbt.getTagList("ItemRewards", 10);
			for (int l = 0; l < itemTags.tagCount(); ++l) {
				itemData = itemTags.getCompoundTagAt(l);
				item = ItemStack.loadItemStackFromNBT(itemData);
				if (item == null) {
					continue;
				}
				getItemsRewarded().add(item);
			}
		}
		entityDead = nbt.getBoolean("OwnerDead");
		if (nbt.hasKey("Dimension")) {
			ChunkCoordinates coords = new ChunkCoordinates(nbt.getInteger("XPos"), nbt.getInteger("YPos"), nbt.getInteger("ZPos"));
			int dimension = nbt.getInteger("Dimension");
			lastLocation = Pair.of(coords, dimension);
		}
		setSpeechBankStart(nbt.getString("SpeechStart"));
		setSpeechBankProgress(nbt.getString("SpeechProgress"));
		setSpeechBankComplete(nbt.getString("SpeechComplete"));
		setSpeechBankTooMany(nbt.getString("SpeechTooMany"));
		setQuoteStart(nbt.getString("QuoteStart"));
		setQuoteComplete(nbt.getString("QuoteComplete"));
		getQuotesStages().clear();
		if (nbt.hasKey("QuotesStages")) {
			NBTTagList stageTags = nbt.getTagList("QuotesStages", 8);
			for (int l = 0; l < stageTags.tagCount(); ++l) {
				String s = stageTags.getStringTagAt(l);
				getQuotesStages().add(s);
			}
		}
		if (questGroup == null && (recovery = getSpeechBankStart()) != null) {
			GOTMiniQuestFactory factory2;
			int i1 = recovery.indexOf("/", 0);
			int i2 = recovery.indexOf("/", i1 + 1);
			if (i1 >= 0 && i2 >= 0 && (factory2 = GOTMiniQuestFactory.forName(recovery = recovery.substring(i1 + 1, i2))) != null) {
				questGroup = factory2;
			}
		}
	}

	public void sendCompletedSpeech(EntityPlayer entityplayer, GOTEntityNPC npc) {
		sendQuoteSpeech(entityplayer, npc, getQuoteComplete());
	}

	public void sendProgressSpeechbank(EntityPlayer entityplayer, GOTEntityNPC npc) {
		npc.sendSpeechBank(entityplayer, getSpeechBankProgress(), this);
	}

	public void sendQuoteSpeech(EntityPlayer entityplayer, GOTEntityNPC npc, String quote) {
		GOTSpeech.sendSpeech(entityplayer, npc, GOTSpeech.formatSpeech(quote, entityplayer, null, getObjectiveInSpeech()));
		npc.markNPCSpoken();
	}

	public void setAlignmentRewarded(float alignmentRewarded) {
		this.alignmentRewarded = alignmentRewarded;
	}

	public void setCoinsRewarded(int coinsRewarded) {
		this.coinsRewarded = coinsRewarded;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void setDateCompleted(int dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public void setDateGiven(int dateGiven) {
		this.dateGiven = dateGiven;
	}

	public void setEntityDead() {
		entityDead = true;
		updateQuest();
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setEntityNameFull(String entityNameFull) {
		this.entityNameFull = entityNameFull;
	}

	public void setEntityUUID(UUID entityUUID) {
		this.entityUUID = entityUUID;
	}

	public void setHiringAlignment(float hiringAlignment) {
		this.hiringAlignment = hiringAlignment;
	}

	public void setItemsRewarded(List<ItemStack> itemsRewarded) {
		this.itemsRewarded = itemsRewarded;
	}

	public void setLegendary(boolean isLegendary) {
		this.isLegendary = isLegendary;
	}

	public void setNPCInfo(GOTEntityNPC npc) {
		setEntityUUID(npc.getUniqueID());
		setEntityName(npc.getNPCName());
		setEntityNameFull(npc.getCommandSenderName());
		entityFaction = npc.getFaction();
		questColor = npc.getMiniquestColor();
	}

	public void setPlayerData(GOTPlayerData pd) {
		playerData = pd;
	}

	public void setQuestUUID(UUID questUUID) {
		this.questUUID = questUUID;
	}

	public void setQuoteComplete(String quoteComplete) {
		this.quoteComplete = quoteComplete;
	}

	public void setQuotesStages(List<String> quotesStages) {
		this.quotesStages = quotesStages;
	}

	public void setQuoteStart(String quoteStart) {
		this.quoteStart = quoteStart;
	}

	public void setRewardFactor(float rewardFactor) {
		this.rewardFactor = rewardFactor;
	}

	public void setRewardItemTable(List<ItemStack> rewardItemTable) {
		this.rewardItemTable = rewardItemTable;
	}

	public void setSpeechBankComplete(String speechBankComplete) {
		this.speechBankComplete = speechBankComplete;
	}

	public void setSpeechBankProgress(String speechBankProgress) {
		this.speechBankProgress = speechBankProgress;
	}

	public void setSpeechBankStart(String speechBankStart) {
		this.speechBankStart = speechBankStart;
	}

	public void setSpeechBankTooMany(String speechBankTooMany) {
		this.speechBankTooMany = speechBankTooMany;
	}

	public void setWasHired(boolean wasHired) {
		this.wasHired = wasHired;
	}

	public void setWillHire(boolean willHire) {
		this.willHire = willHire;
	}

	public boolean shouldRandomiseCoinReward() {
		return true;
	}

	public void start(EntityPlayer entityplayer, GOTEntityNPC npc) {
		setNPCInfo(npc);
		setDateGiven(GOTDate.AegonCalendar.getCurrentDay());
		int i = MathHelper.floor_double(entityplayer.posX);
		int k = MathHelper.floor_double(entityplayer.posZ);
		BiomeGenBase biome = entityplayer.worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof GOTBiome) {
			biomeGiven = (GOTBiome) biome;
		}
		playerData.addMiniQuest(this);
		npc.questInfo.addActiveQuestPlayer(entityplayer);
		playerData.setTrackingMiniQuest(this);
	}

	public void updateLocation(GOTEntityNPC npc) {
		int i = MathHelper.floor_double(npc.posX);
		int j = MathHelper.floor_double(npc.posY);
		int k = MathHelper.floor_double(npc.posZ);
		ChunkCoordinates coords = new ChunkCoordinates(i, j, k);
		int dim = npc.dimension;
		ChunkCoordinates prevCoords = null;
		if (lastLocation != null) {
			prevCoords = lastLocation.getLeft();
		}
		lastLocation = Pair.of(coords, dim);
		boolean sendUpdate = false;
		if (prevCoords == null) {
			sendUpdate = true;
		} else {
			sendUpdate = coords.getDistanceSquaredToChunkCoordinates(prevCoords) > 256.0;
		}
		if (sendUpdate) {
			updateQuest();
		}
	}

	public void updateQuest() {
		playerData.updateMiniQuest(this);
	}

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList itemTags;
		NBTTagCompound itemData;
		nbt.setString("QuestType", questToNameMapping.get(this.getClass()));
		if (questGroup != null) {
			nbt.setString("QuestGroup", questGroup.getBaseName());
		}
		nbt.setString("QuestUUID", getQuestUUID().toString());
		nbt.setString("EntityUUID", getEntityUUID().toString());
		nbt.setString("Owner", getEntityName());
		nbt.setString("OwnerFull", getEntityNameFull());
		nbt.setString("Faction", entityFaction.codeName());
		nbt.setInteger("Color", questColor);
		nbt.setInteger("DateGiven", getDateGiven());
		if (biomeGiven != null) {
			nbt.setByte("BiomeID", (byte) biomeGiven.biomeID);
			nbt.setString("BiomeDim", biomeGiven.biomeDimension.getDimensionName());
		}
		nbt.setFloat("RewardFactor", getRewardFactor());
		nbt.setBoolean("WillHire", isWillHire());
		nbt.setFloat("HiringAlignF", getHiringAlignment());
		if (!getRewardItemTable().isEmpty()) {
			itemTags = new NBTTagList();
			for (ItemStack item : getRewardItemTable()) {
				itemData = new NBTTagCompound();
				item.writeToNBT(itemData);
				itemTags.appendTag(itemData);
			}
			nbt.setTag("RewardItemTable", itemTags);
		}
		nbt.setBoolean("Completed", isCompleted());
		nbt.setInteger("DateCompleted", getDateCompleted());
		nbt.setShort("CoinReward", (short) getCoinsRewarded());
		nbt.setFloat("AlignRewardF", getAlignmentRewarded());
		nbt.setBoolean("WasHired", isWasHired());
		if (!getItemsRewarded().isEmpty()) {
			itemTags = new NBTTagList();
			for (ItemStack item : getItemsRewarded()) {
				itemData = new NBTTagCompound();
				item.writeToNBT(itemData);
				itemTags.appendTag(itemData);
			}
			nbt.setTag("ItemRewards", itemTags);
		}
		nbt.setBoolean("OwnerDead", entityDead);
		if (lastLocation != null) {
			ChunkCoordinates coords = lastLocation.getLeft();
			nbt.setInteger("XPos", coords.posX);
			nbt.setInteger("YPos", coords.posY);
			nbt.setInteger("ZPos", coords.posZ);
			nbt.setInteger("Dimension", lastLocation.getRight());
		}
		nbt.setBoolean("Legendary", isLegendary());
		nbt.setString("SpeechStart", getSpeechBankStart());
		nbt.setString("SpeechProgress", getSpeechBankProgress());
		nbt.setString("SpeechComplete", getSpeechBankComplete());
		nbt.setString("SpeechTooMany", getSpeechBankTooMany());
		nbt.setString("QuoteStart", getQuoteStart());
		nbt.setString("QuoteComplete", getQuoteComplete());
		if (!getQuotesStages().isEmpty()) {
			NBTTagList stageTags = new NBTTagList();
			for (String s : getQuotesStages()) {
				stageTags.appendTag(new NBTTagString(s));
			}
			nbt.setTag("QuotesStages", stageTags);
		}
	}

	public static int getMaxQuestsPerFac() {
		return MAX_MINIQUESTS_PER_FACTION;
	}

	public static double getRenderHeadDistance() {
		return RENDER_HEAD_DISTANCE;
	}

	public static GOTMiniQuest loadQuestFromNBT(NBTTagCompound nbt, GOTPlayerData playerData) {
		String questTypeName = nbt.getString("QuestType");
		Class<? extends GOTMiniQuest> questType = nameToQuestMapping.get(questTypeName);
		if (questType == null) {
			FMLLog.severe("Could not instantiate miniquest of type " + questTypeName);
			return null;
		}
		GOTMiniQuest quest = GOTMiniQuest.newQuestInstance(questType, playerData);
		quest.readFromNBT(nbt);
		if (quest.isValidQuest()) {
			return quest;
		}
		FMLLog.severe("Loaded an invalid GOT miniquest " + quest.getSpeechBankStart());
		return null;
	}

	private static <Q extends GOTMiniQuest> Q newQuestInstance(Class<Q> questType, GOTPlayerData playerData) {
		try {
			GOTMiniQuest quest = questType.getConstructor(GOTPlayerData.class).newInstance(playerData);
			return (Q) quest;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void registerQuestType(String name, Class<? extends GOTMiniQuest> questType) {
		nameToQuestMapping.put(name, questType);
		questToNameMapping.put(questType, name);
	}

	public static void setMaxQuestsPerFac(int mAX_MINIQUESTS_PER_FACTION) {
		MAX_MINIQUESTS_PER_FACTION = mAX_MINIQUESTS_PER_FACTION;
	}

	public static void setRenderHeadDistance(double rENDER_HEAD_DISTANCE) {
		RENDER_HEAD_DISTANCE = rENDER_HEAD_DISTANCE;
	}

	public abstract static class QuestFactoryBase<Q extends GOTMiniQuest> {
		public GOTMiniQuestFactory questFactoryGroup;
		private String questName;
		private float rewardFactor = 1.0f;
		private boolean willHire = false;
		private float hiringAlignment;
		private boolean isLegendary = false;
		private List<ItemStack> rewardItems;

		public QuestFactoryBase(String name) {
			this.questName = name;
		}

		public Q createQuest(GOTEntityNPC npc, Random rand) {
			GOTMiniQuest quest = GOTMiniQuest.newQuestInstance(this.getQuestClass(), null);
			quest.questGroup = this.getFactoryGroup();
			String pathName = "miniquest/" + this.getFactoryGroup().getBaseName() + "/";
			String pathNameBaseSpeech = "miniquest/" + this.getFactoryGroup().getBaseSpeechGroup().getBaseName() + "/";
			String questPathName = pathName + this.questName + "_";
			quest.setSpeechBankStart(questPathName + "start");
			quest.setSpeechBankProgress(questPathName + "progress");
			quest.setSpeechBankComplete(questPathName + "complete");
			quest.setSpeechBankTooMany(pathNameBaseSpeech + "_tooMany");
			quest.setLegendary(this.isLegendary);
			quest.setQuoteStart(GOTSpeech.getRandomSpeech(quest.getSpeechBankStart()));
			quest.setQuoteComplete(GOTSpeech.getRandomSpeech(quest.getSpeechBankComplete()));
			quest.setNPCInfo(npc);
			quest.setRewardFactor(this.rewardFactor);
			quest.setWillHire(this.willHire);
			quest.setHiringAlignment(this.hiringAlignment);
			if (this.rewardItems != null) {
				quest.getRewardItemTable().addAll(this.rewardItems);
			}
			return (Q) quest;
		}

		public GOTMiniQuestFactory getFactoryGroup() {
			return this.questFactoryGroup;
		}

		public abstract Class<Q> getQuestClass();

		public void setFactoryGroup(GOTMiniQuestFactory factory) {
			this.questFactoryGroup = factory;
		}

		public QuestFactoryBase setHiring() {
			this.willHire = true;
			this.hiringAlignment = 100.0F;
			return this;
		}

		public QuestFactoryBase setIsLegendary() {
			this.isLegendary = true;
			return this;
		}

		public QuestFactoryBase setRewardFactor(float f) {
			this.rewardFactor = f;
			return this;
		}
	}

	public static class SorterAlphabetical implements Comparator<GOTMiniQuest> {
		@Override
		public int compare(GOTMiniQuest q1, GOTMiniQuest q2) {
			if (!q2.isActive() && q1.isActive()) {
				return 1;
			}
			if (!q1.isActive() && q2.isActive()) {
				return -1;
			}
			if (q1.entityFaction == q2.entityFaction) {
				return q1.getEntityName().compareTo(q2.getEntityName());
			}
			return Integer.compare(q1.entityFaction.ordinal(), q2.entityFaction.ordinal());
		}
	}
}