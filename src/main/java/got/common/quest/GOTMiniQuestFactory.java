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
import got.common.item.other.GOTItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.*;

public enum GOTMiniQuestFactory {
	CRIMINAL(true), IBBEN(true), SUMMER(true), SOTHORYOS(true), ASSHAI(true), WILDLING(true), MOSSOVY(true), HOWLAND, BALON, DAENERYS, VARYS, OBERYN, STANNIS, JONSNOW, RENLY, KITRA, BUGAI, TYRION, CERSEI, RAMSAY, SANDOR, MELISANDRA, DORAN, MARGAERY, ELLARYA, ARYA, OLENNA, SAMWELL, LYSA, CATELYN, DAVEN, ARIANNE, MELLARIO, NORTH(true), RIVERLANDS(true), DORNE(true), REACH(true), STORMLANDS(true), IRONBORN(true), WESTERLANDS(true), ARRYN(true), CROWNLANDS(true), DRAGONSTONE(true), GIFT(true), HILLMEN(true), BRAAVOS(true), LORATH(true), NORVOS(true), QOHOR(true), PENTOS(true), LYS(true), MYR(true), TYROSH(true), VOLANTIS(true), GHISCAR(true), QARTH(true), LHAZAR(true), YI_TI(true), DOTHRAKI(true), JOGOS_NHAI(true);

	private static final Map<Class<? extends GOTMiniQuest>, Integer> QUEST_CLASS_WEIGHTS = new HashMap<>();
	private static final Random RANDOM = new Random();

	private final Map<Class<? extends GOTMiniQuest>, List<GOTMiniQuest.QuestFactoryBase<? extends GOTMiniQuest>>> questFactories = new HashMap<>();
	private final List<GOTLore.LoreCategory> loreCategories = new ArrayList<>();
	private final String baseName;

	private boolean noAlignRewardForEnemy;
	private GOTMiniQuestFactory baseSpeechGroup;
	private GOTAchievement questAchievement;
	private GOTFaction alignmentRewardOverride;

	GOTMiniQuestFactory() {
		this(false);
	}

	GOTMiniQuestFactory(boolean isStandard) {
		if (isStandard) {
			baseName = "standard";
			setAchievement(GOTAchievement.doMiniquest);
		} else {
			baseName = "legendary";
			setAchievement(GOTAchievement.doMiniquestLegendary);
		}
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

	private static GOTFaction getRandomEnemy(GOTFaction owner) {
		ArrayList<GOTFaction> enemies = new ArrayList<>();
		for (GOTFaction fac : GOTFaction.values()) {
			if (owner.isBadRelation(fac) && fac != GOTFaction.WHITE_WALKER && fac != GOTFaction.HOSTILE && fac != GOTFaction.UNALIGNED) {
				enemies.add(fac);
			}
		}
		return enemies.get(RANDOM.nextInt(enemies.size()));
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
		JONSNOW.addQuest(new GOTMiniQuestCollect.QFCollect<>("jonsnow").setCollectItem(new ItemStack(GOTItems.valyrianSword), 1, 1).setHiring());
		SANDOR.addQuest(new GOTMiniQuestCollect.QFCollect<>("sandor").setCollectItem(new ItemStack(Items.cooked_chicken), 1, 10).setHiring());
		DORAN.addQuest(new GOTMiniQuestCollect.QFCollect<>("doran").setCollectItem(new ItemStack(GOTItems.mugPoppyMilk), 1, 10).setRewardFactor(5.0f).setIsLegendary());
		ARYA.addQuest(new GOTMiniQuestCollect.QFCollect<>("arya").setCollectItem(new ItemStack(Items.wooden_sword), 1, 1).setHiring());
		SAMWELL.addQuest(new GOTMiniQuestCollect.QFCollect<>("samwell").setCollectItem(new ItemStack(GOTItems.sothoryosDagger), 1, 1).setRewardFactor(50.0f).setIsLegendary());

		Map<GOTMiniQuestFactory, GOTFaction> kingdoms = new EnumMap<>(GOTMiniQuestFactory.class);
		kingdoms.put(NORTH, GOTFaction.NORTH);
		kingdoms.put(RIVERLANDS, GOTFaction.RIVERLANDS);
		kingdoms.put(WESTERLANDS, GOTFaction.WESTERLANDS);
		kingdoms.put(IRONBORN, GOTFaction.IRONBORN);
		kingdoms.put(ARRYN, GOTFaction.ARRYN);
		kingdoms.put(CROWNLANDS, GOTFaction.CROWNLANDS);
		kingdoms.put(DRAGONSTONE, GOTFaction.DRAGONSTONE);
		kingdoms.put(STORMLANDS, GOTFaction.STORMLANDS);
		kingdoms.put(REACH, GOTFaction.REACH);
		kingdoms.put(DORNE, GOTFaction.DORNE);
		kingdoms.put(GIFT, GOTFaction.NIGHT_WATCH);

		Map<GOTMiniQuestFactory, GOTFaction> cities = new EnumMap<>(GOTMiniQuestFactory.class);
		cities.put(BRAAVOS, GOTFaction.BRAAVOS);
		cities.put(LORATH, GOTFaction.LORATH);
		cities.put(NORVOS, GOTFaction.NORVOS);
		cities.put(QOHOR, GOTFaction.QOHOR);
		cities.put(PENTOS, GOTFaction.PENTOS);
		cities.put(MYR, GOTFaction.MYR);
		cities.put(LYS, GOTFaction.LYS);
		cities.put(TYROSH, GOTFaction.TYROSH);
		cities.put(VOLANTIS, GOTFaction.VOLANTIS);
		cities.put(GHISCAR, GOTFaction.GHISCAR);
		cities.put(QARTH, GOTFaction.QARTH);
		cities.put(LHAZAR, GOTFaction.LHAZAR);

		for (Map.Entry<GOTMiniQuestFactory, GOTFaction> kingdom : kingdoms.entrySet()) {
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.deerCooked), 5, 20).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugAle), 5, 10).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugCider), 5, 10).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugMead), 5, 10).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugRum), 5, 10).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.muttonCooked), 5, 20).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.rabbitCooked), 5, 20).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.bread), 5, 20).setRewardFactor(1.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_beef), 5, 20).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_chicken), 5, 20).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_porkchop), 5, 20).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.log, 1, 0), 20, 50).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.planks, 1, 0), 50, 100).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.planks, 1, 1), 50, 100).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.stonebrick), 50, 100).setRewardFactor(0.5f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.wool, 1, 0), 6, 15).setRewardFactor(1.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.wool, 1, 12), 6, 15).setRewardFactor(1.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.wool, 1, 14), 6, 15).setRewardFactor(1.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.wool, 1, 15), 6, 15).setRewardFactor(1.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood2, 1, 0), 20, 50).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood2, 1, 1), 20, 50).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood4, 1, 0), 20, 50).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood6, 1, 1), 20, 50).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.chisel), 1, 5).setRewardFactor(4.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.ironBattleaxe), 1, 5).setRewardFactor(4.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.ironDagger), 1, 5).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.ironPike), 1, 5).setRewardFactor(4.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.ironThrowingAxe), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.westerosBow), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.westerosDagger), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.westerosDaggerPoisoned), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.westerosHammer), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.westerosHorseArmor), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.westerosPike), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.westerosSpear), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.westerosSword), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.arrow), 20, 50).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.bucket), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_axe), 1, 5).setRewardFactor(4.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_boots), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_chestplate), 1, 5).setRewardFactor(4.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_helmet), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_ingot), 1, 5).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_leggings), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_shovel), 1, 5).setRewardFactor(4.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_sword), 1, 5).setRewardFactor(3.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.shears), 1, 5).setRewardFactor(4.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.cobblestone), 30, 80).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.gold_ore), 10, 5).setRewardFactor(5.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.iron_ore), 10, 20).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.oreCopper), 10, 5).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.oreTin), 10, 5).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.rock, 1, 0), 20, 50).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.rock, 1, 1), 20, 50).setRewardFactor(0.25f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.copperIngot), 1, 5).setRewardFactor(2.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.silverIngot), 1, 5).setRewardFactor(4.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.tinIngot), 1, 5).setRewardFactor(4.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.coal), 20, 50).setRewardFactor(0.5f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.gold_ingot), 1, 5).setRewardFactor(4.0f));
			kingdom.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.leather), 5, 20).setRewardFactor(0.5f));
			kingdom.getKey().addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(kingdom.getValue()), 10, 30));
			kingdom.getKey().addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(kingdom.getValue()), 30, 50));
			kingdom.getKey().addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(kingdom.getValue()), 50, 70));
			kingdom.getKey().addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(kingdom.getValue()), 70, 100));
			kingdom.getKey().addQuest(new GOTMiniQuestBounty.QFBounty<>());
		}
		for (Map.Entry<GOTMiniQuestFactory, GOTFaction> city : cities.entrySet()) {
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.date), 8, 15).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.deerCooked), 5, 10).setRewardFactor(1.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.kebab), 4, 8).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lemon), 4, 12).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lime), 4, 12).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lionCooked), 2, 4).setRewardFactor(4.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugAraq), 3, 5).setRewardFactor(4.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugBananaBeer), 4, 10).setRewardFactor(2.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugCactusLiqueur), 4, 10).setRewardFactor(2.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugCarrotWine), 4, 10).setRewardFactor(2.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugCornLiquor), 4, 10).setRewardFactor(2.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugRum), 4, 10).setRewardFactor(2.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.muttonCooked), 4, 8).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.orange), 4, 12).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.plum), 4, 12).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.rabbitCooked), 5, 10).setRewardFactor(1.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.waterskin), 8, 20).setRewardFactor(0.75f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_beef), 4, 8).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_porkchop), 4, 8).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.chest), 8, 16).setRewardFactor(1.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.wool, 1, 0), 6, 15).setRewardFactor(1.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.wool, 1, 12), 6, 15).setRewardFactor(1.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.wool, 1, 14), 6, 15).setRewardFactor(1.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.wool, 1, 15), 6, 15).setRewardFactor(1.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.brick1, 1, 15), 30, 60).setRewardFactor(0.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.brick3, 1, 13), 30, 60).setRewardFactor(0.75f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.brick6, 1, 6), 30, 60).setRewardFactor(0.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.chestBasket), 5, 10).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.driedReeds), 10, 20).setRewardFactor(0.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.planks2, 1, 11), 60, 120).setRewardFactor(0.2f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.planks2, 1, 2), 60, 120).setRewardFactor(0.125f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.planks2, 1, 2), 60, 120).setRewardFactor(0.2f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.planks3, 1, 3), 60, 120).setRewardFactor(0.2f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.pouch, 1, 0), 3, 5).setRewardFactor(5.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.rope), 5, 12).setRewardFactor(1.1f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.rope), 5, 12).setRewardFactor(1.1f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.slabSingleThatch, 1, 1), 20, 40).setRewardFactor(0.25f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.thatch, 1, 1), 10, 20).setRewardFactor(0.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood4, 1, 2), 30, 60).setRewardFactor(0.25f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.string), 5, 12).setRewardFactor(1.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bottlePoison), 2, 4).setRewardFactor(5.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.essosDagger), 1, 2).setRewardFactor(20.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.essosHammer), 1, 3).setRewardFactor(5.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.essosPike), 1, 1).setRewardFactor(5.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.essosSpear), 1, 3).setRewardFactor(5.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.essosSword), 1, 3).setRewardFactor(5.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.sandstone, 1, 0), 30, 80).setRewardFactor(0.25f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.stone, 1, 0), 30, 80).setRewardFactor(0.25f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.rock, 1, 0), 30, 50).setRewardFactor(0.5f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.gold_ingot), 3, 6).setRewardFactor(4.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_ingot), 4, 8).setRewardFactor(2.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.lava_bucket), 2, 4).setRewardFactor(5.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.leather), 10, 20).setRewardFactor(0.75f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.string), 5, 12).setRewardFactor(1.0f));
			city.getKey().addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.water_bucket), 3, 5).setRewardFactor(5.0f));
			city.getKey().addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(city.getValue()), 10, 30));
			city.getKey().addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(city.getValue()), 30, 50));
			city.getKey().addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(city.getValue()), 50, 70));
			city.getKey().addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(city.getValue()), 70, 100));
			city.getKey().addQuest(new GOTMiniQuestBounty.QFBounty<>());
		}
		ASSHAI.addQuest(new GOTMiniQuestBounty.QFBounty<>());
		ASSHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.asshaiFlower), 6, 15).setRewardFactor(1.0f));
		ASSHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bottlePoison), 6, 15).setRewardFactor(1.0f));

		CRIMINAL.addQuest(new GOTMiniQuestPickpocket.QFPickpocket<>("criminal").setPickpocketNumber(1, 9).setIsLegendary());

		WILDLING.addQuest(new GOTMiniQuestBounty.QFBounty<>());
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_boots), 2, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_chestplate), 2, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_helmet), 2, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_leggings), 2, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.furChestplate), 2, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.furHelmet), 2, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.furBoots), 2, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.furLeggings), 2, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeChestplate), 2, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeHelmet), 2, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeBoots), 2, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeLeggings), 2, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.deerCooked), 4, 8).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.muttonCooked), 4, 8).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_beef), 4, 8).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_chicken), 4, 8).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_porkchop), 4, 8).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeIngot), 3, 10).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.copperIngot), 3, 10).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_ingot), 3, 10).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugEthanol), 2, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeThrowingAxe), 3, 6).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.ironThrowingAxe), 3, 6).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.silverIngot), 3, 6).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.gold_ingot), 3, 6).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.wildlingHammer), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeBattleaxe), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.ironBattleaxe), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.ironPike), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.wildlingPolearm), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.wildlingSword), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeSword), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_sword), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.WILDLING), 10, 30));
		WILDLING.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.WILDLING), 30, 50));
		WILDLING.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.WILDLING), 50, 70));
		WILDLING.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.WILDLING), 70, 100));

		DOTHRAKI.addQuest(new GOTMiniQuestBounty.QFBounty<>());
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 1), 8, 15).setRewardFactor(0.75f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 10), 8, 15).setRewardFactor(0.75f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 11), 8, 15).setRewardFactor(0.75f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 13), 8, 15).setRewardFactor(0.75f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 14), 8, 15).setRewardFactor(0.75f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 3), 8, 15).setRewardFactor(0.75f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 4), 8, 15).setRewardFactor(0.75f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 4), 8, 15).setRewardFactor(0.75f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 5), 8, 15).setRewardFactor(0.75f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.date), 8, 15).setRewardFactor(2.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lemon), 4, 12).setRewardFactor(2.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lime), 4, 12).setRewardFactor(2.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.orange), 4, 12).setRewardFactor(2.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.plum), 4, 12).setRewardFactor(2.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bottlePoison), 2, 4).setRewardFactor(5.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.dothrakiChestplate), 1, 2).setRewardFactor(8.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.dothrakiHelmet), 1, 2).setRewardFactor(8.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.nomadSword), 1, 2).setRewardFactor(8.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.dothrakiBoots), 1, 2).setRewardFactor(8.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.dothrakiLeggings), 1, 2).setRewardFactor(8.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.nomadBattleaxe), 1, 2).setRewardFactor(8.0f));
		DOTHRAKI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.DOTHRAKI), 10, 30));
		DOTHRAKI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.DOTHRAKI), 30, 50));
		DOTHRAKI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.DOTHRAKI), 50, 70));
		DOTHRAKI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.DOTHRAKI), 70, 100));

		HILLMEN.addQuest(new GOTMiniQuestBounty.QFBounty<>());
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.cobblestone), 30, 80).setRewardFactor(0.25f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.log, 1, 0), 30, 80).setRewardFactor(0.25f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.log, 1, 1), 30, 80).setRewardFactor(0.25f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.deerCooked), 3, 8).setRewardFactor(2.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.hillmenBoots), 1, 3).setRewardFactor(10.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.hillmenChestplate), 1, 3).setRewardFactor(10.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.hillmenHelmet), 1, 3).setRewardFactor(10.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.hillmenLeggings), 1, 3).setRewardFactor(10.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugAle), 3, 10).setRewardFactor(2.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugCider), 3, 10).setRewardFactor(2.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugMead), 3, 10).setRewardFactor(2.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugRum), 3, 10).setRewardFactor(2.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.muttonCooked), 3, 8).setRewardFactor(2.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.rabbitCooked), 3, 12).setRewardFactor(2.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.bread), 5, 15).setRewardFactor(1.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.coal), 10, 30).setRewardFactor(0.5f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_beef), 3, 8).setRewardFactor(2.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_chicken), 3, 8).setRewardFactor(2.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_porkchop), 3, 8).setRewardFactor(2.0f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_ingot), 3, 10).setRewardFactor(1.5f));
		HILLMEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.leather), 10, 30).setRewardFactor(0.5f));
		HILLMEN.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.MOSSOVY), 10, 30));
		HILLMEN.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.MOSSOVY), 30, 50));
		HILLMEN.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.MOSSOVY), 50, 70));
		HILLMEN.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.MOSSOVY), 70, 100));

		IBBEN.addQuest(new GOTMiniQuestBounty.QFBounty<>());
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.ibbenBoots), 1, 4).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.ibbenChestplate), 1, 4).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.harpoon), 1, 4).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.ibbenLeggings), 1, 4).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_ingot), 3, 8).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.deerCooked), 3, 8).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.muttonCooked), 3, 8).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.rabbitCooked), 3, 12).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.bread), 5, 15).setRewardFactor(1.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_beef), 3, 8).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_chicken), 3, 8).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_porkchop), 3, 8).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugMead), 3, 20).setRewardFactor(1.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.cobblestone), 30, 80).setRewardFactor(0.25f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.log, 1, 0), 20, 60).setRewardFactor(0.25f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.log, 1, 1), 20, 60).setRewardFactor(0.25f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.planks, 1, 0), 80, 160).setRewardFactor(0.125f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.planks, 1, 1), 80, 160).setRewardFactor(0.125f));
		IBBEN.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.IBBEN), 10, 30));
		IBBEN.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.IBBEN), 30, 50));
		IBBEN.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.IBBEN), 50, 70));
		IBBEN.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.IBBEN), 70, 100));

		JOGOS_NHAI.addQuest(new GOTMiniQuestBounty.QFBounty<>());
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 1), 8, 15).setRewardFactor(0.75f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 10), 8, 15).setRewardFactor(0.75f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 11), 8, 15).setRewardFactor(0.75f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 13), 8, 15).setRewardFactor(0.75f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 14), 8, 15).setRewardFactor(0.75f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 3), 8, 15).setRewardFactor(0.75f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 4), 8, 15).setRewardFactor(0.75f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 4), 8, 15).setRewardFactor(0.75f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.carpet, 5), 8, 15).setRewardFactor(0.75f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.date), 8, 15).setRewardFactor(2.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lemon), 4, 12).setRewardFactor(2.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lime), 4, 12).setRewardFactor(2.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.orange), 4, 12).setRewardFactor(2.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.plum), 4, 12).setRewardFactor(2.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bottlePoison), 2, 4).setRewardFactor(5.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.jogosNhaiChestplate), 1, 2).setRewardFactor(8.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.jogosNhaiHelmet), 1, 2).setRewardFactor(8.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.nomadSword), 1, 2).setRewardFactor(8.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.jogosNhaiBoots), 1, 2).setRewardFactor(8.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.jogosNhaiLeggings), 1, 2).setRewardFactor(8.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.nomadBow), 1, 2).setRewardFactor(8.0f));
		JOGOS_NHAI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.JOGOS_NHAI), 10, 30));
		JOGOS_NHAI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.JOGOS_NHAI), 30, 50));
		JOGOS_NHAI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.JOGOS_NHAI), 50, 70));
		JOGOS_NHAI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.JOGOS_NHAI), 70, 100));

		MOSSOVY.addQuest(new GOTMiniQuestBounty.QFBounty<>());
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.bucket), 1, 4).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugAle), 1, 6).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugCherryLiqueur), 1, 6).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugCider), 1, 6).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugMead), 1, 6).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugPerry), 1, 6).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.appleCrumble), 2, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.appleGreen), 3, 12).setRewardFactor(1.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.cherryPie), 2, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.deerCooked), 5, 20).setRewardFactor(0.75f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugAle), 5, 15).setRewardFactor(1.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugCider), 5, 15).setRewardFactor(1.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.muttonCooked), 5, 20).setRewardFactor(0.75f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.pear), 3, 12).setRewardFactor(1.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.rabbitCooked), 3, 15).setRewardFactor(1.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.rabbitStew), 3, 8).setRewardFactor(2.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.apple), 3, 12).setRewardFactor(1.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.baked_potato), 10, 30).setRewardFactor(0.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.bread), 10, 30).setRewardFactor(0.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_beef), 5, 20).setRewardFactor(0.75f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_chicken), 5, 20).setRewardFactor(0.75f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_porkchop), 5, 20).setRewardFactor(0.75f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.log, 1, 0), 10, 30).setRewardFactor(0.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood2, 1, 1), 10, 30).setRewardFactor(0.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood3, 1, 0), 10, 30).setRewardFactor(0.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood4, 1, 0), 10, 30).setRewardFactor(0.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood6, 1, 1), 10, 30).setRewardFactor(0.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mossovyBoots), 1, 2).setRewardFactor(8.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mossovyChestplate), 1, 2).setRewardFactor(8.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mossovyLeggings), 1, 2).setRewardFactor(8.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mossovySword), 1, 2).setRewardFactor(8.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeAxe), 1, 3).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeHoe), 1, 3).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bronzeShovel), 1, 3).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.chisel), 1, 3).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.bucket), 1, 4).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_axe), 1, 3).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_hoe), 1, 3).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.iron_shovel), 1, 3).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.pipeweed), 20, 40).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.MOSSOVY), 10, 30));
		MOSSOVY.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.MOSSOVY), 30, 50));
		MOSSOVY.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.MOSSOVY), 50, 70));
		MOSSOVY.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.MOSSOVY), 70, 100));

		SOTHORYOS.addQuest(new GOTMiniQuestBounty.QFBounty<>());
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.banana), 4, 6).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bananaBread), 5, 8).setRewardFactor(2.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.banner, 1, GOTItemBanner.BannerType.SOTHORYOS.getBannerID()), 5, 15).setRewardFactor(1.5f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.corn), 6, 12).setRewardFactor(1.5f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.cornBread), 5, 8).setRewardFactor(2.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.cornCooked), 5, 10).setRewardFactor(2.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.dart), 20, 40).setRewardFactor(0.5f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.dartPoisoned), 10, 20).setRewardFactor(1.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mango), 4, 6).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.melonSoup), 3, 8).setRewardFactor(2.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.obsidianShard), 10, 30).setRewardFactor(0.75f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.sothoryosAxe), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.sothoryosBattleaxe), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.sothoryosDagger), 1, 4).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.sothoryosDaggerPoisoned), 1, 3).setRewardFactor(6.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.sothoryosHammer), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.sothoryosPike), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.sothoryosSpear), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.sothoryosSword), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.bread), 5, 8).setRewardFactor(2.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.dye, 1, 3), 8, 20).setRewardFactor(1.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.melon), 10, 20).setRewardFactor(0.75f));
		SOTHORYOS.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.SOTHORYOS), 10, 30));
		SOTHORYOS.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.SOTHORYOS), 30, 50));
		SOTHORYOS.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.SOTHORYOS), 50, 70));
		SOTHORYOS.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.SOTHORYOS), 70, 100));

		SUMMER.addQuest(new GOTMiniQuestBounty.QFBounty<>());
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.water_bucket), 3, 5).setRewardFactor(5.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.log, 1, 0), 20, 60).setRewardFactor(0.25f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.planks, 1, 0), 80, 160).setRewardFactor(0.125f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.sandstone, 1, 0), 30, 80).setRewardFactor(0.25f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.sandstone, 1, 1), 15, 40).setRewardFactor(0.5f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.planks2, 1, 2), 80, 160).setRewardFactor(0.125f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.thatch, 1, 1), 20, 40).setRewardFactor(0.5f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood4, 1, 2), 20, 60).setRewardFactor(0.25f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.rock, 1, 0), 30, 50).setRewardFactor(0.5f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.date), 8, 15).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lemon), 4, 12).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lime), 4, 12).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.orange), 4, 12).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.plum), 4, 12).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.deerCooked), 5, 12).setRewardFactor(1.5f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lemon), 4, 8).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.orange), 4, 8).setRewardFactor(2.5f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.rabbitCooked), 5, 12).setRewardFactor(1.5f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.bread), 5, 8).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bottlePoison), 2, 4).setRewardFactor(5.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.arrow), 20, 40).setRewardFactor(0.5f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.doubleFlower, 1, 3), 5, 15).setRewardFactor(1.5f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.olive), 10, 20).setRewardFactor(1.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.dye, 1, 4), 3, 8).setRewardFactor(3.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.banana), 2, 4).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lemon), 4, 8).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.lionCooked), 3, 6).setRewardFactor(3.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mango), 2, 4).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.orange), 4, 8).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.SUMMER_ISLANDS), 10, 30));
		SUMMER.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.SUMMER_ISLANDS), 30, 50));
		SUMMER.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.SUMMER_ISLANDS), 50, 70));
		SUMMER.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.SUMMER_ISLANDS), 70, 100));

		YI_TI.addQuest(new GOTMiniQuestBounty.QFBounty<>());
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.date), 4, 12).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.pomegranate), 4, 12).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.sapling8, 1, 1), 3, 5).setRewardFactor(3.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.yiTiFlower, 1, 0), 2, 5).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.yiTiFlower, 1, 1), 2, 5).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.yiTiFlower, 1, 2), 2, 5).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.yiTiFlower, 1, 3), 2, 5).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.yiTiFlower, 1, 4), 2, 5).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.deerCooked), 2, 8).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.deerRaw), 5, 10).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugRedWine), 3, 8).setRewardFactor(3.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.mugWhiteWine), 3, 8).setRewardFactor(3.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.muttonCooked), 2, 8).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.oliveBread), 5, 8).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.rabbitRaw), 5, 10).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.raisins), 6, 12).setRewardFactor(1.5f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.whiteBisonHorn), 1, 2).setRewardFactor(10.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.beef), 5, 10).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.bread), 5, 8).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_beef), 2, 8).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.cooked_fished), 2, 8).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.feather), 8, 16).setRewardFactor(1.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Items.leather), 8, 16).setRewardFactor(1.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.bottlePoison), 2, 4).setRewardFactor(5.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.sulfur), 4, 8).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.yiTiBoots), 2, 4).setRewardFactor(4.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.yiTiChestplate), 2, 4).setRewardFactor(4.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.yiTiHelmet), 2, 4).setRewardFactor(4.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.yiTiLeggings), 2, 4).setRewardFactor(4.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.yiTiSpear), 3, 5).setRewardFactor(3.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.yiTiSteelIngot), 5, 10).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.yiTiSword), 3, 5).setRewardFactor(3.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.cobblestone, 1, 0), 40, 100).setRewardFactor(0.25f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(Blocks.log, 1, 0), 30, 60).setRewardFactor(0.25f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.brick5, 1, 11), 40, 100).setRewardFactor(0.2f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.saltpeter), 4, 8).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.sulfur), 4, 8).setRewardFactor(2.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTItems.whiteBisonHorn), 1, 1).setRewardFactor(30.0f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood6, 1, 2), 30, 60).setRewardFactor(0.25f));
		YI_TI.addQuest(new GOTMiniQuestCollect.QFCollect<>().setCollectItem(new ItemStack(GOTBlocks.wood8, 1, 1), 30, 60).setRewardFactor(0.25f));
		YI_TI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.YI_TI), 10, 30));
		YI_TI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.YI_TI), 30, 50));
		YI_TI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.YI_TI), 50, 70));
		YI_TI.addQuest(new GOTMiniQuestKillFaction.QFKillFaction().setKillFaction(getRandomEnemy(GOTFaction.YI_TI), 70, 100));
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

	public GOTFaction checkAlignmentRewardFaction(GOTFaction fac) {
		if (alignmentRewardOverride != null) {
			return alignmentRewardOverride;
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

	public boolean isNoAlignRewardForEnemy() {
		return noAlignRewardForEnemy;
	}

	public void setNoAlignRewardForEnemy(boolean noAlignRewardForEnemy) {
		this.noAlignRewardForEnemy = noAlignRewardForEnemy;
	}

	public Map<Class<? extends GOTMiniQuest>, List<GOTMiniQuest.QuestFactoryBase<? extends GOTMiniQuest>>> getQuestFactories() {
		return questFactories;
	}

	public void setQuestAchievement(GOTAchievement questAchievement) {
		this.questAchievement = questAchievement;
	}

	public void setAlignmentRewardOverride(GOTFaction alignmentRewardOverride) {
		this.alignmentRewardOverride = alignmentRewardOverride;
	}
}