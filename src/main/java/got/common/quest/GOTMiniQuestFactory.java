package got.common.quest;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTLore;
import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.entity.animal.GOTEntityCrocodile;
import got.common.entity.essos.legendary.quest.GOTEntityDaenerysTargaryen;
import got.common.entity.essos.legendary.warrior.GOTEntityTugarKhan;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.legendary.captain.GOTEntityRickardKarstark;
import got.common.entity.westeros.legendary.deco.GOTEntityTommenBaratheon;
import got.common.entity.westeros.legendary.quest.*;
import got.common.entity.westeros.legendary.reborn.GOTEntityTheonGreyjoy;
import got.common.entity.westeros.legendary.warrior.GOTEntityEuronGreyjoy;
import got.common.entity.westeros.legendary.warrior.GOTEntityGeroldDayne;
import got.common.entity.westeros.legendary.warrior.GOTEntityJoffreyBaratheon;
import got.common.entity.westeros.legendary.warrior.GOTEntityTywinLannister;
import got.common.faction.GOTFaction;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.*;

public enum GOTMiniQuestFactory {
	CRIMINAL(GOTFaction.NEUTRAL),
	IBBEN(GOTFaction.IBBEN),
	SUMMER_ISLANDS(GOTFaction.SUMMER_ISLANDS),
	SOTHORYOS(GOTFaction.SOTHORYOS),
	ASSHAI(GOTFaction.ASSHAI),
	WILDLING(GOTFaction.WILDLING),
	MOSSOVY(GOTFaction.MOSSOVY),
	NORTH(GOTFaction.NORTH),
	RIVERLANDS(GOTFaction.RIVERLANDS),
	DORNE(GOTFaction.DORNE),
	REACH(GOTFaction.REACH),
	STORMLANDS(GOTFaction.STORMLANDS),
	IRONBORN(GOTFaction.IRONBORN),
	WESTERLANDS(GOTFaction.WESTERLANDS),
	ARRYN(GOTFaction.ARRYN),
	CROWNLANDS(GOTFaction.CROWNLANDS),
	DRAGONSTONE(GOTFaction.DRAGONSTONE),
	GIFT(GOTFaction.NIGHT_WATCH),
	HILL_TRIBES(GOTFaction.HILL_TRIBES),
	BRAAVOS(GOTFaction.BRAAVOS),
	LORATH(GOTFaction.LORATH),
	NORVOS(GOTFaction.NORVOS),
	QOHOR(GOTFaction.QOHOR),
	PENTOS(GOTFaction.PENTOS),
	LYS(GOTFaction.LYS),
	MYR(GOTFaction.MYR),
	TYROSH(GOTFaction.TYROSH),
	VOLANTIS(GOTFaction.VOLANTIS),
	GHISCAR(GOTFaction.GHISCAR),
	QARTH(GOTFaction.QARTH),
	LHAZAR(GOTFaction.LHAZAR),
	YI_TI(GOTFaction.YI_TI),
	DOTHRAKI(GOTFaction.DOTHRAKI),
	JOGOS_NHAI(GOTFaction.JOGOS_NHAI),
	HOWLAND,
	BALON,
	DAENERYS,
	VARYS,
	OBERYN,
	STANNIS,
	JON_SNOW,
	RENLY,
	KITRA,
	BUGAI,
	TYRION,
	CERSEI,
	RAMSAY,
	SANDOR,
	MELISANDRA,
	DORAN,
	MARGAERY,
	ELLARYA,
	ARYA,
	OLENNA,
	SAMWELL,
	LYSA,
	CATELYN,
	DAVEN,
	ARIANNE,
	MELLARIO;

	private static final Map<Class<? extends GOTMiniQuest>, Integer> QUEST_CLASS_WEIGHTS = new HashMap<>();
	private static final Random RANDOM = new Random();

	private final Map<Class<? extends GOTMiniQuest>, List<GOTMiniQuest.QuestFactoryBase<? extends GOTMiniQuest>>> questFactories = new HashMap<>();
	private final List<GOTLore.LoreCategory> loreCategories = new ArrayList<>();
	private final String baseName;
	private final GOTFaction faction;
	private boolean noRepRewardForEnemy;
	private GOTMiniQuestFactory baseSpeechGroup;
	private GOTAchievement questAchievement;
	private GOTFaction reputationRewardOverride;

	GOTMiniQuestFactory() {
		faction = GOTFaction.NEUTRAL;
		baseName = "legendary";
		setAchievement(GOTAchievement.doMiniquestLegendary);
	}

	GOTMiniQuestFactory(GOTFaction fac) {
		faction = fac;
		baseName = "standard";
		setAchievement(GOTAchievement.doMiniquest);
	}

	public static GOTMiniQuestFactory forName(String name) {
		for (GOTMiniQuestFactory group : values()) {
			if (group.baseName.equals(name)) {
				return group;
			}
		}
		return null;
	}

	private static int getQuestClassWeight(Class<? extends GOTMiniQuest> questClass) {
		Integer i = QUEST_CLASS_WEIGHTS.get(questClass);
		if (i == null) {
			throw new RuntimeException("Encountered a registered quest class " + questClass.toString() + " which is not assigned a weight");
		}
		return i;
	}

	private static int getTotalQuestClassWeight(GOTMiniQuestFactory factory) {
		Collection<Class<? extends GOTMiniQuest>> registeredQuestTypes = new HashSet<>();
		for (Map.Entry<Class<? extends GOTMiniQuest>, List<GOTMiniQuest.QuestFactoryBase<? extends GOTMiniQuest>>> entry : factory.questFactories.entrySet()) {
			Class<? extends GOTMiniQuest> questType = entry.getKey();
			registeredQuestTypes.add(questType);
		}
		int totalWeight = 0;
		for (Class<? extends GOTMiniQuest> c : registeredQuestTypes) {
			totalWeight += getQuestClassWeight(c);
		}
		return totalWeight;
	}

	private static void registerQuestCollect(GOTMiniQuestFactory factory, ItemStack itemstack, int min, int max, float f) {
		factory.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(itemstack, min, max).setRewardFactor(f));
	}

	public static void onInit() {
		registerQuestClass(GOTMiniQuestCollect.class, 5);
		registerQuestClass(GOTMiniQuestKill.class, 2);
		registerQuestClass(GOTMiniQuestBounty.class, 2);
		registerQuestClass(GOTMiniQuestPickpocket.class, 1);

		BALON.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("balon").setKillEntity(GOTEntityEuronGreyjoy.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		VARYS.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("varys").setKillEntity(GOTEntityDaenerysTargaryen.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		OBERYN.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("oberyn").setKillEntity(GOTEntityGeroldDayne.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		STANNIS.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("stannis").setKillEntity(GOTEntityRenlyBaratheon.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		RENLY.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("renly").setKillEntity(GOTEntityStannisBaratheon.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		BUGAI.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("bugai").setKillEntity(GOTEntityTugarKhan.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		MARGAERY.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("margaery").setKillEntity(GOTEntityCerseiLannister.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		HOWLAND.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("howland").setKillEntity(GOTEntityCrocodile.class, 50, 50).setRewardFactor(50.0f).setIsLegendary());
		OLENNA.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("olenna").setKillEntity(GOTEntityJoffreyBaratheon.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		ELLARYA.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("ellarya").setKillEntity(GOTEntityTywinLannister.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		LYSA.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("lysa").setKillEntity(GOTEntityTyrionLannister.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		CATELYN.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("catelyn").setKillEntity(GOTEntityTheonGreyjoy.TheonGreyjoyNormal.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		DAVEN.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("daven").setKillEntity(GOTEntityRickardKarstark.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		ARIANNE.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("arianne").setKillEntity(GOTEntityTommenBaratheon.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		MELLARIO.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("mellario").setKillEntity(GOTEntityDoranMartell.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		MELISANDRA.addQuest(new GOTMiniQuestCollect.QFCollect<>("melisandra").setCollectItem(new ItemStack(GOTItems.bloodOfTrueKings), 1, 1).setRewardFactor(100.0f).setIsLegendary());
		TYRION.addQuest(new GOTMiniQuestCollect.QFCollect<>("tyrion").setCollectItem(new ItemStack(GOTItems.mugRedWine), 1, 10).setRewardFactor(5.0f).setIsLegendary());
		CERSEI.addQuest(new GOTMiniQuestCollect.QFCollect<>("cersei").setCollectItem(new ItemStack(GOTBlocks.wildFireJar), 1, 10).setRewardFactor(0.0f).setIsLegendary());
		RAMSAY.addQuest(new GOTMiniQuestCollect.QFCollect<>("ramsay").setCollectItem(new ItemStack(GOTItems.brandingIron), 1, 1).setRewardFactor(5.0f).setIsLegendary());
		DAENERYS.addQuest(new GOTMiniQuestCollect.QFCollect<>("daenerys").setCollectItem(new ItemStack(Blocks.dragon_egg), 3, 3).setRewardFactor(100.0f).setIsLegendary());
		JON_SNOW.addQuest(new GOTMiniQuestCollect.QFCollect<>("jonsnow").setCollectItem(new ItemStack(GOTItems.valyrianSword), 1, 1).setHiring());
		SANDOR.addQuest(new GOTMiniQuestCollect.QFCollect<>("sandor").setCollectItem(new ItemStack(Items.cooked_chicken), 1, 10).setHiring());
		DORAN.addQuest(new GOTMiniQuestCollect.QFCollect<>("doran").setCollectItem(new ItemStack(GOTItems.mugPoppyMilk), 1, 10).setRewardFactor(5.0f).setIsLegendary());
		ARYA.addQuest(new GOTMiniQuestCollect.QFCollect<>("arya").setCollectItem(new ItemStack(Items.wooden_sword), 1, 1).setHiring());
		SAMWELL.addQuest(new GOTMiniQuestCollect.QFCollect<>("samwell").setCollectItem(new ItemStack(GOTItems.obsidianDagger), 1, 1).setRewardFactor(50.0f).setIsLegendary());

		CRIMINAL.addQuest(new GOTMiniQuestPickpocket.QFPickpocket<>("criminal").setPickpocketNumber(1, 9).setIsLegendary());

		for (GOTMiniQuestFactory factory : values()) {
			if (factory.faction != GOTFaction.NEUTRAL) {
				factory.addQuest(new GOTMiniQuestBounty.QFBounty<>());

				for (GOTFaction potentialEnemy : GOTFaction.values()) {
					if (factory.faction != potentialEnemy && factory.faction.isBadRelation(potentialEnemy) && factory.faction != GOTFaction.WHITE_WALKER && factory.faction != GOTFaction.HOSTILE && potentialEnemy != GOTFaction.WHITE_WALKER && potentialEnemy != GOTFaction.HOSTILE && potentialEnemy != GOTFaction.NEUTRAL) {
						factory.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(potentialEnemy, 10, 30));
						factory.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(potentialEnemy, 30, 50));
						factory.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(potentialEnemy, 50, 70));
						factory.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(potentialEnemy, 70, 100));
					}
				}

				registerQuestCollect(factory, new ItemStack(Blocks.chest), 8, 16, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.chestSandstone), 8, 16, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.chestStone), 8, 16, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.chestBasket), 8, 16, 1.0f);

				registerQuestCollect(factory, new ItemStack(Blocks.hardened_clay), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(Blocks.cobblestone), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(Blocks.sandstone), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(Blocks.stone), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.redSandstone), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.whiteSandstone), 20, 50, 0.25f);

				for (int i = 0; i <= 6; i++) {
					registerQuestCollect(factory, new ItemStack(GOTBlocks.rock, 1, i), 20, 50, 0.25f);
				}

				for (int i = 0; i <= 15; i++) {
					registerQuestCollect(factory, new ItemStack(GOTBlocks.concretePowder, 1, i), 20, 50, 0.25f);
				}

				registerQuestCollect(factory, new ItemStack(Blocks.log, 1, 0), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(Blocks.log, 1, 1), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(Blocks.log, 1, 2), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(Blocks.log2, 1, 1), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood2, 1, 1), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood2, 1, 2), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood3, 1, 0), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood3, 1, 1), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood4, 1, 0), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood4, 1, 2), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood4, 1, 3), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood5, 1, 0), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood6, 1, 1), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood6, 1, 2), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood6, 1, 3), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood7, 1, 0), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood7, 1, 2), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood7, 1, 3), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood8, 1, 0), 20, 50, 0.25f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.wood8, 1, 2), 20, 50, 0.25f);

				for (int i = 0; i <= 15; i++) {
					registerQuestCollect(factory, new ItemStack(Blocks.wool, 1, i), 6, 15, 0.5f);
				}

				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeChainmailBoots), 1, 1, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeChainmailChestplate), 1, 1, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeChainmailHelmet), 1, 1, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeChainmailLeggings), 1, 1, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeBoots), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeChestplate), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeHelmet), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeLeggings), 1, 1, 2.0f);

				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeAxe), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeBattleaxe), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeCrossbow), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeDagger), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeDaggerPoisoned), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeHammer), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeMattock), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeScimitar), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeSpear), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeSword), 1, 1, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeThrowingAxe), 1, 1, 2.0f);

				registerQuestCollect(factory, new ItemStack(Items.chainmail_boots), 1, 1, 2.5f);
				registerQuestCollect(factory, new ItemStack(Items.chainmail_leggings), 1, 1, 2.5f);
				registerQuestCollect(factory, new ItemStack(Items.chainmail_chestplate), 1, 1, 2.5f);
				registerQuestCollect(factory, new ItemStack(Items.chainmail_helmet), 1, 1, 2.5f);
				registerQuestCollect(factory, new ItemStack(Items.iron_boots), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(Items.iron_chestplate), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(Items.iron_helmet), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(Items.iron_leggings), 1, 1, 3.0f);

				registerQuestCollect(factory, new ItemStack(Items.iron_axe), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ironBattleaxe), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ironCrossbow), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ironDagger), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ironDaggerPoisoned), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ironHammer), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ironMattock), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ironScimitar), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ironSpear), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(Items.iron_sword), 1, 1, 3.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ironThrowingAxe), 1, 1, 3.0f);

				registerQuestCollect(factory, new ItemStack(Items.arrow), 2, 5, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.crossbowBolt), 2, 5, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.bow), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.longbow), 1, 1, 1.0f);

				registerQuestCollect(factory, new ItemStack(GOTItems.goldRing), 1, 1, 5.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.silverRing), 1, 1, 4.0f);

				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeIngot), 1, 2, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.bronzeRing), 1, 2, 2.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.copperRing), 1, 2, 2.0f);

				registerQuestCollect(factory, new ItemStack(Items.iron_ingot), 1, 2, 30);

				registerQuestCollect(factory, new ItemStack(Items.bucket), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.glass_bottle), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.milk_bucket), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.water_bucket), 1, 1, 1.0f);

				registerQuestCollect(factory, new ItemStack(Items.flint_and_steel), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.saddle), 1, 1, 1.0f);

				registerQuestCollect(factory, new ItemStack(Items.flint), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.coal), 1, 4, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.string), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.leather), 1, 2, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.feather), 1, 2, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.stick), 1, 8, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.fur), 1, 3, 1.0f);

				registerQuestCollect(factory, new ItemStack(GOTBlocks.rope), 5, 12, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.leatherHat), 1, 1, 1.0f);

				registerQuestCollect(factory, new ItemStack(Items.wheat), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(Items.wheat_seeds), 1, 6, 1.0f);

				registerQuestCollect(factory, new ItemStack(GOTItems.waterskin), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.mug), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ceramicMug), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.clayMug), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.plate), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.ceramicPlate), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.clayPlate), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.gobletBronze), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.gobletCopper), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.gobletWood), 1, 3, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.horn), 1, 2, 1.0f);

				registerQuestCollect(factory, new ItemStack(GOTItems.mugAle), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.mugCider), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.mugCornLiquor), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.mugMapleBeer), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.mugPerry), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.mugPlumKvass), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.mugRedWine), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.mugSourMilk), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.mugVodka), 1, 1, 1.0f);
				registerQuestCollect(factory, new ItemStack(GOTItems.mugWhiteWine), 1, 1, 1.0f);

				registerQuestCollect(factory, new ItemStack(GOTItems.beaverCooked), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.deerCooked), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.muttonCooked), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.rabbitCooked), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.cooked_beef), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.cooked_chicken), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.cooked_fished), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.cooked_porkchop), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.carrot), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.potato), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.apple), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTBlocks.cornStalk), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.appleGreen), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.cherry), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.corn), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.grapeRed), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.grapeWhite), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.pear), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.plum), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.fish, 1, 0), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.fish, 1, 1), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.fish, 1, 2), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.fish, 1, 3), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(GOTItems.cornBread), 1, 3, 1.5f);
				registerQuestCollect(factory, new ItemStack(Items.bread), 1, 3, 1.5f);
			}
		}
	}

	private static void registerQuestClass(Class<? extends GOTMiniQuest> questClass, int weight) {
		QUEST_CLASS_WEIGHTS.put(questClass, weight);
	}

	private void addQuest(GOTMiniQuest.QuestFactoryBase<? extends GOTMiniQuest> factory) {
		Class<?> questClass = factory.getQuestClass();
		Class<? extends GOTMiniQuest> registryClass = null;
		for (Class<? extends GOTMiniQuest> c : QUEST_CLASS_WEIGHTS.keySet()) {
			if (questClass == c) {
				registryClass = c;
				break;
			}
		}
		if (registryClass == null) {
			for (Class<? extends GOTMiniQuest> c : QUEST_CLASS_WEIGHTS.keySet()) {
				if (c.isAssignableFrom(questClass)) {
					registryClass = c;
					break;
				}
			}
		}
		if (registryClass == null) {
			throw new IllegalArgumentException("Could not find registered quest class for " + questClass.toString());
		}
		factory.setFactoryGroup(this);
		questFactories.computeIfAbsent(registryClass, k -> new ArrayList<>()).add(factory);
	}

	public GOTFaction checkReputationRewardFaction(GOTFaction fac) {
		if (reputationRewardOverride != null) {
			return reputationRewardOverride;
		}
		return fac;
	}

	public GOTMiniQuest createQuest(GOTEntityNPC npc) {
		int totalWeight = getTotalQuestClassWeight(this);
		if (totalWeight <= 0) {
			FMLLog.warning("Hummel009: No quests registered for %s!", baseName);
			return null;
		}
		int i = RANDOM.nextInt(totalWeight);
		List<GOTMiniQuest.QuestFactoryBase<? extends GOTMiniQuest>> chosenFactoryList = null;
		for (Map.Entry<Class<? extends GOTMiniQuest>, List<GOTMiniQuest.QuestFactoryBase<? extends GOTMiniQuest>>> next : questFactories.entrySet()) {
			chosenFactoryList = next.getValue();
			i -= getQuestClassWeight(next.getKey());
			if (i < 0) {
				break;
			}
		}
		GOTMiniQuest.QuestFactoryBase<? extends GOTMiniQuest> factory = chosenFactoryList.get(RANDOM.nextInt(chosenFactoryList.size()));
		GOTMiniQuest quest = factory.createQuest(npc, RANDOM);
		if (quest != null) {
			quest.setQuestGroup(this);
		}
		return quest;
	}

	public GOTAchievement getAchievement() {
		return questAchievement;
	}

	private void setAchievement(GOTAchievement a) {
		if (questAchievement != null) {
			throw new IllegalArgumentException("Miniquest achievement is already registered");
		}
		questAchievement = a;
	}

	public String getBaseName() {
		return baseName;
	}

	public GOTMiniQuestFactory getBaseSpeechGroup() {
		if (baseSpeechGroup != null) {
			return baseSpeechGroup;
		}
		return this;
	}

	public void setBaseSpeechGroup(GOTMiniQuestFactory baseSpeechGroup) {
		this.baseSpeechGroup = baseSpeechGroup;
	}

	public List<GOTLore.LoreCategory> getLoreCategories() {
		return loreCategories;
	}

	public boolean isNoRepRewardForEnemy() {
		return noRepRewardForEnemy;
	}

	public void setNoRepRewardForEnemy(boolean noRepRewardForEnemy) {
		this.noRepRewardForEnemy = noRepRewardForEnemy;
	}

	public Map<Class<? extends GOTMiniQuest>, List<GOTMiniQuest.QuestFactoryBase<? extends GOTMiniQuest>>> getQuestFactories() {
		return questFactories;
	}

	public void setQuestAchievement(GOTAchievement questAchievement) {
		this.questAchievement = questAchievement;
	}

	public void setReputationRewardOverride(GOTFaction reputationRewardOverride) {
		this.reputationRewardOverride = reputationRewardOverride;
	}
}