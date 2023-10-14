package got.common.quest;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTDate;
import got.common.GOTDimension;
import got.common.GOTLore;
import got.common.GOTPlayerData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.essos.qohor.GOTEntityQohorBlacksmith;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTHiredNPCInfo;
import got.common.entity.other.GOTUnitTradeEntry;
import got.common.faction.GOTAlignmentBonusMap;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import got.common.item.other.GOTItemCoin;
import got.common.item.other.GOTItemModifierTemplate;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.List;
import java.util.*;

public abstract class GOTMiniQuest {
	public static Map<String, Class<? extends GOTMiniQuest>> nameToQuestMapping = new HashMap<>();
	public static Map<Class<? extends GOTMiniQuest>, String> questToNameMapping = new HashMap<>();
	public static int MAX_MINIQUESTS_PER_FACTION = 5;
	public static double RENDER_HEAD_DISTANCE = 12.0;
	public static float defaultRewardFactor = 1.0f;

	static {
		registerQuestType("Collect", GOTMiniQuestCollect.class);
		registerQuestType("KillFaction", GOTMiniQuestKillFaction.class);
		registerQuestType("KillEntity", GOTMiniQuestKillEntity.class);
		registerQuestType("Bounty", GOTMiniQuestBounty.class);
		registerQuestType("Welcome", GOTMiniQuestWelcome.class);
		registerQuestType("Pickpocket", GOTMiniQuestPickpocket.class);
	}

	public GOTMiniQuestFactory questGroup;
	public GOTPlayerData playerData;
	public UUID questUUID;
	public UUID entityUUID;
	public String entityName;
	public String entityNameFull;
	public GOTFaction entityFaction;
	public int questColor;
	public int dateGiven;
	public GOTBiome biomeGiven;
	public float rewardFactor = 1.0f;
	public boolean willHire;
	public float hiringAlignment;
	public List<ItemStack> rewardItemTable = new ArrayList<>();
	public boolean completed;
	public int dateCompleted;
	public boolean isLegendary;
	public int coinsRewarded;
	public float alignmentRewarded;
	public boolean wasHired;
	public Collection<ItemStack> itemsRewarded = new ArrayList<>();
	public boolean entityDead;
	public Pair<ChunkCoordinates, Integer> lastLocation;
	public String speechBankStart;
	public String speechBankProgress;
	public String speechBankComplete;
	public String speechBankTooMany;
	public String quoteStart;

	public String quoteComplete;

	public List<String> quotesStages = new ArrayList<>();

	protected GOTMiniQuest(GOTPlayerData pd) {
		playerData = pd;
		questUUID = UUID.randomUUID();
	}

	public static GOTMiniQuest loadQuestFromNBT(NBTTagCompound nbt, GOTPlayerData playerData) {
		String questTypeName = nbt.getString("QuestType");
		Class<? extends GOTMiniQuest> questType = nameToQuestMapping.get(questTypeName);
		if (questType == null) {
			FMLLog.severe("Could not instantiate miniquest of type " + questTypeName);
			return null;
		}
		GOTMiniQuest quest = newQuestInstance(questType, playerData);
		if (quest != null) {
			quest.readFromNBT(nbt);
			if (quest.isValidQuest()) {
				return quest;
			}
			FMLLog.severe("Loaded an invalid GOT miniquest " + quest.speechBankStart);
		}
		return null;
	}

	public static <Q extends GOTMiniQuest> Q newQuestInstance(Class<Q> questType, GOTPlayerData playerData) {
		try {
			return questType.getConstructor(GOTPlayerData.class).newInstance(playerData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void registerQuestType(String name, Class<? extends GOTMiniQuest> questType) {
		nameToQuestMapping.put(name, questType);
		questToNameMapping.put(questType, name);
	}

	public boolean anyRewardsGiven() {
		return alignmentRewarded > 0.0f || coinsRewarded > 0 || !itemsRewarded.isEmpty();
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
		completed = true;
		dateCompleted = GOTDate.AegonCalendar.currentDay;
		Random rand = npc.getRNG();
		List<ItemStack> dropItems = new ArrayList<>();
		float alignment = getAlignmentBonus();
		if (alignment != 0.0f) {
			alignment *= MathHelper.randomFloatClamp(rand, 0.75f, 1.25f);
			alignment = Math.max(alignment, 1.0f);
			GOTAlignmentValues.AlignmentBonus bonus = GOTAlignmentValues.createMiniquestBonus(alignment);
			GOTFaction rewardFaction = getAlignmentRewardFaction();
			if (!questGroup.isNoAlignRewardForEnemy() || playerData.getAlignment(rewardFaction) >= 0.0f) {
				GOTAlignmentBonusMap alignmentMap = playerData.addAlignment(entityplayer, bonus, rewardFaction, npc);
				alignmentRewarded = alignmentMap.get(rewardFaction);
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
			coinsRewarded = coins = Math.max(coins, 1);
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
					dropItems.add(new ItemStack(GOTItems.coin, 64, l));
				}
				dropItems.add(new ItemStack(GOTItems.coin, numCoins, l));
			}
		}
		if (!rewardItemTable.isEmpty()) {
			ItemStack item = rewardItemTable.get(rand.nextInt(rewardItemTable.size()));
			dropItems.add(item.copy());
			itemsRewarded.add(item.copy());
		}
		if (canRewardVariousExtraItems()) {
			GOTLore lore;
			if (rand.nextInt(10) == 0 && questGroup != null && !questGroup.getLoreCategories().isEmpty() && (lore = GOTLore.getMultiRandomLore(questGroup.getLoreCategories(), rand, true)) != null) {
				ItemStack loreBook = lore.createLoreBook(rand);
				dropItems.add(loreBook.copy());
				itemsRewarded.add(loreBook.copy());
			}
			if (rand.nextInt(15) == 0) {
				ItemStack modItem = GOTItemModifierTemplate.getRandomCommonTemplate(rand);
				dropItems.add(modItem.copy());
				itemsRewarded.add(modItem.copy());
			}
			if (npc instanceof GOTEntityQohorBlacksmith && rand.nextInt(10) == 0) {
				ItemStack mithrilBook = new ItemStack(GOTItems.valyrianBook);
				dropItems.add(mithrilBook.copy());
				itemsRewarded.add(mithrilBook.copy());
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
				itemsRewarded.add(pouchCopy);
			}
			npc.dropItemList(dropItems);
		}
		if (willHire) {
			GOTUnitTradeEntry tradeEntry = new GOTUnitTradeEntry(npc.getClass(), 0, hiringAlignment);
			tradeEntry.setTask(GOTHiredNPCInfo.Task.WARRIOR);
			npc.hiredNPCInfo.hireUnit(entityplayer, false, entityFaction, tradeEntry, null, npc.ridingEntity);
			wasHired = true;
		}
		if (isLegendary) {
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

	public GOTFaction getAlignmentRewardFaction() {
		return questGroup.checkAlignmentRewardFaction(entityFaction);
	}

	public abstract int getCoinBonus();

	public abstract float getCompletionFactor();

	public String getFactionSubtitle() {
		if (entityFaction.isPlayableAlignmentFaction()) {
			return entityFaction.factionName();
		}
		return "";
	}

	public ChunkCoordinates getLastLocation() {
		if (lastLocation != null) {
			return lastLocation.getLeft();
		}
		return null;
	}

	public abstract String getObjectiveInSpeech();

	public GOTPlayerData getPlayerData() {
		return playerData;
	}

	public void setPlayerData(GOTPlayerData pd) {
		playerData = pd;
	}

	public abstract String getProgressedObjectiveInSpeech();

	public int getQuestColor() {
		return questColor;
	}

	public float[] getQuestColorComponents() {
		return new Color(questColor).getColorComponents(null);
	}

	public String getQuestFailure() {
		return StatCollector.translateToLocalFormatted("got.gui.redBook.mq.diary.dead", entityName);
	}

	public String getQuestFailureShorthand() {
		return StatCollector.translateToLocal("got.gui.redBook.mq.dead");
	}

	public abstract ItemStack getQuestIcon();

	public abstract String getQuestObjective();

	public abstract String getQuestProgress();

	public abstract String getQuestProgressShorthand();

	public void handleEvent(GOTMiniQuestEvent event) {
	}

	public boolean isActive() {
		return !completed && !isFailed();
	}

	public boolean isCompleted() {
		return completed;
	}

	public boolean isFailed() {
		return entityDead;
	}

	public boolean isValidQuest() {
		return entityUUID != null && entityFaction != null;
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
		ItemStack item;
		GOTMiniQuestFactory factory;
		NBTTagList itemTags;
		String recovery;
		isLegendary = nbt.getBoolean("Legendary");
		if (nbt.hasKey("QuestGroup") && (factory = GOTMiniQuestFactory.forName(nbt.getString("QuestGroup"))) != null) {
			questGroup = factory;
		}
		if (nbt.hasKey("QuestUUID")) {
			String questUUIDString = nbt.getString("QuestUUID");
			questUUID = UUID.fromString(questUUIDString);
		}
		entityUUID = nbt.hasKey("UUIDMost") && nbt.hasKey("UUIDLeast") ? new UUID(nbt.getLong("UUIDMost"), nbt.getLong("UUIDLeast")) : UUID.fromString(nbt.getString("EntityUUID"));
		entityName = nbt.getString("Owner");
		entityNameFull = nbt.hasKey("OwnerFull") ? nbt.getString("OwnerFull") : entityName;
		entityFaction = GOTFaction.forName(nbt.getString("Faction"));
		questColor = nbt.hasKey("Color") ? nbt.getInteger("Color") : entityFaction.getFactionColor();
		dateGiven = nbt.getInteger("DateGiven");
		if (nbt.hasKey("BiomeID")) {
			int biomeID = nbt.getByte("BiomeID") & 0xFF;
			String biomeDimName = nbt.getString("BiomeDim");
			GOTDimension biomeDim = GOTDimension.forName(biomeDimName);
			if (biomeDim != null) {
				biomeGiven = biomeDim.biomeList[biomeID];
			}
		}
		rewardFactor = nbt.hasKey("RewardFactor") ? nbt.getFloat("RewardFactor") : 1.0f;
		willHire = nbt.getBoolean("WillHire");
		hiringAlignment = nbt.hasKey("HiringAlignment") ? nbt.getInteger("HiringAlignment") : nbt.getFloat("HiringAlignF");
		rewardItemTable.clear();
		if (nbt.hasKey("RewardItemTable")) {
			itemTags = nbt.getTagList("RewardItemTable", 10);
			for (int l = 0; l < itemTags.tagCount(); ++l) {
				itemData = itemTags.getCompoundTagAt(l);
				item = ItemStack.loadItemStackFromNBT(itemData);
				if (item == null) {
					continue;
				}
				rewardItemTable.add(item);
			}
		}
		completed = nbt.getBoolean("Completed");
		dateCompleted = nbt.getInteger("DateCompleted");
		coinsRewarded = nbt.getShort("CoinReward");
		alignmentRewarded = nbt.hasKey("AlignmentReward") ? nbt.getShort("AlignmentReward") : nbt.getFloat("AlignRewardF");
		wasHired = nbt.getBoolean("WasHired");
		itemsRewarded.clear();
		if (nbt.hasKey("ItemRewards")) {
			itemTags = nbt.getTagList("ItemRewards", 10);
			for (int l = 0; l < itemTags.tagCount(); ++l) {
				itemData = itemTags.getCompoundTagAt(l);
				item = ItemStack.loadItemStackFromNBT(itemData);
				if (item == null) {
					continue;
				}
				itemsRewarded.add(item);
			}
		}
		entityDead = nbt.getBoolean("OwnerDead");
		if (nbt.hasKey("Dimension")) {
			ChunkCoordinates coords = new ChunkCoordinates(nbt.getInteger("XPos"), nbt.getInteger("YPos"), nbt.getInteger("ZPos"));
			int dimension = nbt.getInteger("Dimension");
			lastLocation = Pair.of(coords, dimension);
		}
		speechBankStart = nbt.getString("SpeechStart");
		speechBankProgress = nbt.getString("SpeechProgress");
		speechBankComplete = nbt.getString("SpeechComplete");
		speechBankTooMany = nbt.getString("SpeechTooMany");
		quoteStart = nbt.getString("QuoteStart");
		quoteComplete = nbt.getString("QuoteComplete");
		quotesStages.clear();
		if (nbt.hasKey("QuotesStages")) {
			NBTTagList stageTags = nbt.getTagList("QuotesStages", 8);
			for (int l = 0; l < stageTags.tagCount(); ++l) {
				String s = stageTags.getStringTagAt(l);
				quotesStages.add(s);
			}
		}
		if (questGroup == null && (recovery = speechBankStart) != null) {
			GOTMiniQuestFactory factory2;
			int i1 = recovery.indexOf('/');
			int i2 = recovery.indexOf('/', i1 + 1);
			if (i1 >= 0 && i2 >= 0 && (factory2 = GOTMiniQuestFactory.forName(recovery.substring(i1 + 1, i2))) != null) {
				questGroup = factory2;
			}
		}
	}

	public void sendCompletedSpeech(EntityPlayer entityplayer, GOTEntityNPC npc) {
		sendQuoteSpeech(entityplayer, npc, quoteComplete);
	}

	public void sendProgressSpeechbank(EntityPlayer entityplayer, GOTEntityNPC npc) {
		npc.sendSpeechBank(entityplayer, speechBankProgress, this);
	}

	public void sendQuoteSpeech(EntityPlayer entityplayer, GOTEntityNPC npc, String quote) {
		GOTSpeech.sendSpeech(entityplayer, npc, GOTSpeech.formatSpeech(quote, entityplayer, null, getObjectiveInSpeech()));
		npc.markNPCSpoken();
	}

	public void setEntityDead() {
		entityDead = true;
		updateQuest();
	}

	public void setNPCInfo(GOTEntityNPC npc) {
		entityUUID = npc.getUniqueID();
		entityName = npc.getNPCName();
		entityNameFull = npc.getCommandSenderName();
		entityFaction = npc.getFaction();
		questColor = npc.getMiniquestColor();
	}

	public boolean shouldRandomiseCoinReward() {
		return true;
	}

	public void start(EntityPlayer entityplayer, GOTEntityNPC npc) {
		setNPCInfo(npc);
		dateGiven = GOTDate.AegonCalendar.currentDay;
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
		boolean sendUpdate;
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
		nbt.setString("QuestType", questToNameMapping.get(getClass()));
		if (questGroup != null) {
			nbt.setString("QuestGroup", questGroup.getBaseName());
		}
		nbt.setString("QuestUUID", questUUID.toString());
		nbt.setString("EntityUUID", entityUUID.toString());
		nbt.setString("Owner", entityName);
		nbt.setString("OwnerFull", entityNameFull);
		nbt.setString("Faction", entityFaction.codeName());
		nbt.setInteger("Color", questColor);
		nbt.setInteger("DateGiven", dateGiven);
		if (biomeGiven != null) {
			nbt.setByte("BiomeID", (byte) biomeGiven.biomeID);
			nbt.setString("BiomeDim", biomeGiven.getBiomeDimension().dimensionName);
		}
		nbt.setFloat("RewardFactor", rewardFactor);
		nbt.setBoolean("WillHire", willHire);
		nbt.setFloat("HiringAlignF", hiringAlignment);
		if (!rewardItemTable.isEmpty()) {
			itemTags = new NBTTagList();
			for (ItemStack item : rewardItemTable) {
				itemData = new NBTTagCompound();
				item.writeToNBT(itemData);
				itemTags.appendTag(itemData);
			}
			nbt.setTag("RewardItemTable", itemTags);
		}
		nbt.setBoolean("Completed", completed);
		nbt.setInteger("DateCompleted", dateCompleted);
		nbt.setShort("CoinReward", (short) coinsRewarded);
		nbt.setFloat("AlignRewardF", alignmentRewarded);
		nbt.setBoolean("WasHired", wasHired);
		if (!itemsRewarded.isEmpty()) {
			itemTags = new NBTTagList();
			for (ItemStack item : itemsRewarded) {
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
		nbt.setBoolean("Legendary", isLegendary);
		nbt.setString("SpeechStart", speechBankStart);
		nbt.setString("SpeechProgress", speechBankProgress);
		nbt.setString("SpeechComplete", speechBankComplete);
		nbt.setString("SpeechTooMany", speechBankTooMany);
		nbt.setString("QuoteStart", quoteStart);
		nbt.setString("QuoteComplete", quoteComplete);
		if (!quotesStages.isEmpty()) {
			NBTTagList stageTags = new NBTTagList();
			for (String s : quotesStages) {
				stageTags.appendTag(new NBTTagString(s));
			}
			nbt.setTag("QuotesStages", stageTags);
		}
	}

	public abstract static class QuestFactoryBase<Q extends GOTMiniQuest> {
		public GOTMiniQuestFactory questFactoryGroup;
		public String questName;
		public float rewardFactor = 1.0f;
		public boolean willHire;
		public float hiringAlignment;
		public boolean isLegendary;
		public List<ItemStack> rewardItems;

		protected QuestFactoryBase(String name) {
			questName = name;
		}

		public Q createQuest(GOTEntityNPC npc, Random rand) {
			Q quest = newQuestInstance(getQuestClass(), null);
			if (quest != null) {
				quest.questGroup = questFactoryGroup;
				String pathName = "miniquest/" + questFactoryGroup.getBaseName() + "/";
				String pathNameBaseSpeech = "miniquest/" + questFactoryGroup.getBaseSpeechGroup().getBaseName() + "/";
				String questPathName = pathName + questName + "_";
				quest.speechBankStart = questPathName + "start";
				quest.speechBankProgress = questPathName + "progress";
				quest.speechBankComplete = questPathName + "complete";
				quest.speechBankTooMany = pathNameBaseSpeech + "_tooMany";
				quest.isLegendary = isLegendary;
				quest.quoteStart = GOTSpeech.getRandomSpeech(quest.speechBankStart);
				quest.quoteComplete = GOTSpeech.getRandomSpeech(quest.speechBankComplete);
				quest.setNPCInfo(npc);
				quest.rewardFactor = rewardFactor;
				quest.willHire = willHire;
				quest.hiringAlignment = hiringAlignment;
				if (rewardItems != null) {
					quest.rewardItemTable.addAll(rewardItems);
				}
				return quest;
			}
			return null;
		}

		public GOTMiniQuestFactory getFactoryGroup() {
			return questFactoryGroup;
		}

		public void setFactoryGroup(GOTMiniQuestFactory factory) {
			questFactoryGroup = factory;
		}

		public abstract Class<Q> getQuestClass();

		public QuestFactoryBase<Q> setHiring() {
			willHire = true;
			hiringAlignment = 100.0F;
			return this;
		}

		public QuestFactoryBase<Q> setHiring(float f) {
			willHire = true;
			hiringAlignment = f;
			return this;
		}

		public QuestFactoryBase<Q> setIsLegendary() {
			isLegendary = true;
			return this;
		}

		public QuestFactoryBase<Q> setRewardFactor(float f) {
			rewardFactor = f;
			return this;
		}

		public QuestFactoryBase<Q> setRewardItems(ItemStack[] items) {
			rewardItems = Arrays.asList(items);
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
				return q1.entityName.compareTo(q2.entityName);
			}
			return Integer.compare(q1.entityFaction.ordinal(), q2.entityFaction.ordinal());
		}
	}
}