package got.common.quest;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import got.common.GOTLore;
import got.common.database.*;
import got.common.entity.animal.GOTEntityCrocodile;
import got.common.entity.essos.legendary.quest.GOTEntityDaenerysTargaryen;
import got.common.entity.essos.legendary.warrior.GOTEntityTugarKhan;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.legendary.captain.GOTEntityRickardKarstark;
import got.common.entity.westeros.legendary.deco.GOTEntityTommenBaratheon;
import got.common.entity.westeros.legendary.quest.*;
import got.common.entity.westeros.legendary.reborn.GOTEntityTheonGreyjoy;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.faction.GOTFaction;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;

public enum GOTMiniQuestFactory {
	CRIMINAL(true), WESTEROS(true), IBBEN(true), NOMAD(true), SUMMER(true), SOTHORYOS(true), ESSOS(true), ASSHAI(true), WILDLING(true), MOSSOVY(true), HOWLAND, BALON, DAENERYS, VARYS, OBERYN, STANNIS, JONSNOW, RENLY, KITRA, BUGAI, TYRION, CERSEI, RAMSAY, SANDOR, MELISANDRA, DORAN, MARGAERY, ELLARYA, ARYA, OLENNA, SAMWELL, LYSA, CATELYN, DAVEN, ARIANNE, MELLARIO;

	private static Random rand;
	private static Map<Class<? extends GOTMiniQuest>, Integer> questClassWeights;
	static {
		rand = new Random();
		questClassWeights = new HashMap<>();
	}
	private String baseName;
	private GOTMiniQuestFactory baseSpeechGroup;
	private Map<Class<? extends GOTMiniQuest>, List<GOTMiniQuest.QuestFactoryBase>> questFactories = new HashMap<>();
	private GOTAchievement questAchievement;
	private List<GOTLore.LoreCategory> loreCategories = new ArrayList<>();
	private GOTFaction alignmentRewardOverride;

	private boolean noAlignRewardForEnemy = false;

	GOTMiniQuestFactory() {
		baseName = "legendary";
		setAchievement(GOTAchievement.DO_LEGENDARY_QUEST);
	}

	GOTMiniQuestFactory(Boolean isStandart) {
		baseName = "standart";
		setAchievement(GOTAchievement.DO_QUEST);
	}

	private void addQuest(GOTMiniQuest.QuestFactoryBase factory) {
		Class questClass = factory.getQuestClass();
		Class<? extends GOTMiniQuest> registryClass = null;
		for (Class<? extends GOTMiniQuest> c : questClassWeights.keySet()) {
			if (!questClass.equals(c)) {
				continue;
			}
			registryClass = c;
			break;
		}
		if (registryClass == null) {
			for (Class<? extends GOTMiniQuest> c : questClassWeights.keySet()) {
				if (!c.isAssignableFrom(questClass)) {
					continue;
				}
				registryClass = c;
				break;
			}
		}
		if (registryClass == null) {
			throw new IllegalArgumentException("Could not find registered quest class for " + questClass.toString());
		}
		factory.setFactoryGroup(this);
		List<GOTMiniQuest.QuestFactoryBase> list = questFactories.get(registryClass);
		if (list == null) {
			list = new ArrayList<>();
			questFactories.put(registryClass, list);
		}
		list.add(factory);
	}

	public GOTFaction checkAlignmentRewardFaction(GOTFaction fac) {
		if (alignmentRewardOverride != null) {
			return alignmentRewardOverride;
		}
		return fac;
	}

	public GOTMiniQuest createQuest(GOTEntityNPC npc) {
		int totalWeight = GOTMiniQuestFactory.getTotalQuestClassWeight(this);
		if (totalWeight <= 0) {
			FMLLog.warning("GOT: No quests registered for %s!", baseName);
			return null;
		}
		int i = rand.nextInt(totalWeight);
		Iterator<Map.Entry<Class<? extends GOTMiniQuest>, List<GOTMiniQuest.QuestFactoryBase>>> iterator = questFactories.entrySet().iterator();
		List<GOTMiniQuest.QuestFactoryBase> chosenFactoryList = null;
		while (iterator.hasNext()) {
			Map.Entry<Class<? extends GOTMiniQuest>, List<GOTMiniQuest.QuestFactoryBase>> next = iterator.next();
			chosenFactoryList = next.getValue();
			i -= GOTMiniQuestFactory.getQuestClassWeight(next.getKey());
			if (i >= 0) {
				continue;
			}
		}
		GOTMiniQuest.QuestFactoryBase factory = chosenFactoryList.get(rand.nextInt(chosenFactoryList.size()));
		GOTMiniQuest quest = factory.createQuest(npc, rand);
		if (quest != null) {
			quest.setQuestGroup(this);
		}
		return quest;
	}

	public GOTAchievement getAchievement() {
		return questAchievement;
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

	public List<GOTLore.LoreCategory> getLoreCategories() {
		return loreCategories;
	}

	public boolean isNoAlignRewardForEnemy() {
		return noAlignRewardForEnemy;
	}

	private void setAchievement(GOTAchievement a) {
		if (questAchievement != null) {
			throw new IllegalArgumentException("Miniquest achievement is already registered");
		}
		questAchievement = a;
	}

	public GOTMiniQuestFactory setAlignmentRewardOverride(GOTFaction fac) {
		alignmentRewardOverride = fac;
		return this;
	}

	public GOTMiniQuestFactory setNoAlignRewardForEnemy() {
		noAlignRewardForEnemy = true;
		return this;
	}

	public static GOTMiniQuestFactory forName(String name) {
		for (GOTMiniQuestFactory group : GOTMiniQuestFactory.values()) {
			if (!group.getBaseName().equals(name)) {
				continue;
			}
			return group;
		}
		return null;
	}

	private static int getQuestClassWeight(Class<? extends GOTMiniQuest> questClass) {
		Integer i = questClassWeights.get(questClass);
		if (i == null) {
			throw new RuntimeException("Encountered a registered quest class " + questClass.toString() + " which is not assigned a weight");
		}
		return i;
	}

	private static int getTotalQuestClassWeight(GOTMiniQuestFactory factory) {
		HashSet<Class<? extends GOTMiniQuest>> registeredQuestTypes = new HashSet<>();
		for (Map.Entry<Class<? extends GOTMiniQuest>, List<GOTMiniQuest.QuestFactoryBase>> entry : factory.questFactories.entrySet()) {
			Class<? extends GOTMiniQuest> questType = entry.getKey();
			registeredQuestTypes.add(questType);
		}
		int totalWeight = 0;
		for (Class c : registeredQuestTypes) {
			totalWeight += GOTMiniQuestFactory.getQuestClassWeight(c);
		}
		return totalWeight;
	}

	public static void onInit() {
		GOTMiniQuestFactory.registerQuestClass(GOTMiniQuestCollect.class, 10);
		GOTMiniQuestFactory.registerQuestClass(GOTMiniQuestPickpocket.class, 6);
		GOTMiniQuestFactory.registerQuestClass(GOTMiniQuestKill.class, 8);
		GOTMiniQuestFactory.registerQuestClass(GOTMiniQuestBounty.class, 4);
		BALON.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("balon").setKillEntity(GOTEntityEuronGreyjoy.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		VARYS.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("varys").setKillEntity(GOTEntityDaenerysTargaryen.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		OBERYN.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("oberyn").setKillEntity(GOTEntityGeroldDayne.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		STANNIS.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("stannis").setKillEntity(GOTEntityRenlyBaratheon.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		RENLY.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("renly").setKillEntity(GOTEntityStannisBaratheon.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		BUGAI.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("bugai").setKillEntity(GOTEntityTugarKhan.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		MARGAERY.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("margaery").setKillEntity(GOTEntityCerseiLannister.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		HOWLAND.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("howland").setKillEntity(GOTEntityCrocodile.class, 50, 50).setRewardFactor(50.0f).setIsLegendary());
		OLENNA.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("olenna").setKillEntity(GOTEntityJoffreyBaratheon.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		MELISANDRA.addQuest(new GOTMiniQuestCollect.QFCollect("melisandra").setCollectItem(new ItemStack(GOTRegistry.bloodOfTrueKings), 1, 1).setRewardFactor(100.0f).setIsLegendary());
		TYRION.addQuest(new GOTMiniQuestCollect.QFCollect("tyrion").setCollectItem(new ItemStack(GOTRegistry.mugRedWine), 1, 10).setRewardFactor(5.0f).setIsLegendary());
		CERSEI.addQuest(new GOTMiniQuestCollect.QFCollect("cersei").setCollectItem(new ItemStack(GOTRegistry.wildFireJar), 1, 10).setRewardFactor(0.0f).setIsLegendary());
		RAMSAY.addQuest(new GOTMiniQuestCollect.QFCollect("ramsay").setCollectItem(new ItemStack(GOTRegistry.brandingIron), 1, 1).setRewardFactor(5.0f).setIsLegendary());
		DAENERYS.addQuest(new GOTMiniQuestCollect.QFCollect("daenerys").setCollectItem(new ItemStack(Blocks.dragon_egg), 3, 3).setRewardFactor(100.0f).setIsLegendary());
		JONSNOW.addQuest(new GOTMiniQuestCollect.QFCollect("jonsnow").setCollectItem(new ItemStack(GOTRegistry.valyrianSword), 1, 1).setHiring());
		SANDOR.addQuest(new GOTMiniQuestCollect.QFCollect("sandor").setCollectItem(new ItemStack(Items.cooked_chicken), 1, 10).setHiring());
		DORAN.addQuest(new GOTMiniQuestCollect.QFCollect("doran").setCollectItem(new ItemStack(GOTRegistry.mugPoppyMilk), 1, 10).setRewardFactor(5.0f).setIsLegendary());
		ELLARYA.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("ellarya").setKillEntity(GOTEntityTywinLannister.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		ARYA.addQuest(new GOTMiniQuestCollect.QFCollect("arya").setCollectItem(new ItemStack(Items.wooden_sword), 1, 1).setHiring());
		SAMWELL.addQuest(new GOTMiniQuestCollect.QFCollect("samwell").setCollectItem(new ItemStack(GOTRegistry.sothoryosDagger), 1, 1).setRewardFactor(50.0f).setIsLegendary());
		LYSA.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("lysa").setKillEntity(GOTEntityTyrionLannister.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		CATELYN.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("catelyn").setKillEntity(GOTEntityTheonGreyjoy.Normal.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		DAVEN.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("daven").setKillEntity(GOTEntityRickardKarstark.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		ARIANNE.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("arianne").setKillEntity(GOTEntityTommenBaratheon.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());
		MELLARIO.addQuest(new GOTMiniQuestKillEntity.QFKillEntity("mellario").setKillEntity(GOTEntityDoranMartell.class, 1, 1).setRewardFactor(100.0f).setIsLegendary());

		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(Blocks.planks, 1, 0), 50, 100).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(Blocks.planks, 1, 1), 50, 100).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(Blocks.stonebrick), 50, 100).setRewardFactor(0.5f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(Blocks.log, 1, 0), 20, 50).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood2, 1, 0), 20, 50).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood2, 1, 1), 20, 50).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood4, 1, 0), 20, 50).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood6, 1, 1), 20, 50).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(GOTRegistry.rock, 1, 0), 20, 50).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(GOTRegistry.rock, 1, 1), 20, 50).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.cobblestone), 30, 80).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.iron_ore), 10, 20).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(GOTRegistry.oreCopper), 10, 5).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(GOTRegistry.oreTin), 10, 5).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.gold_ore), 10, 5).setRewardFactor(5.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.copperIngot), 1, 5).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.silverIngot), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.tinIngot), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.tinIngot), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(Items.coal), 20, 50).setRewardFactor(0.5f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(Items.gold_ingot), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(Items.leather), 5, 20).setRewardFactor(0.5f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeBattleaxe), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeBoots), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeChestplate), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeHelmet), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeIngot), 1, 5).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeLeggings), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeSword), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeThrowingAxe), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironBattleaxe), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironDagger), 1, 5).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironPike), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironThrowingAxe), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.westerosBow), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.westerosDagger), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.westerosDaggerPoisoned), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.westerosHammer), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.westerosHorseArmor), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.westerosLance), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.westerosPike), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.westerosSpear), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.westerosSword), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_boots), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_chestplate), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_helmet), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_ingot), 1, 5).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_leggings), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_sword), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.arrow), 20, 50).setRewardFactor(0.25f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.bronzeHoe), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_axe), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.bronzeAxe), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_shovel), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.bronzeShovel), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.chisel), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.shears), 1, 5).setRewardFactor(4.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.bucket), 1, 5).setRewardFactor(3.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugAle), 5, 10).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugCider), 5, 10).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugMead), 5, 10).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugRum), 5, 10).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.deerCooked), 5, 20).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.muttonCooked), 5, 20).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.rabbitCooked), 5, 20).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(Items.bread), 5, 20).setRewardFactor(1.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(Items.cooked_beef), 5, 20).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(Items.cooked_chicken), 5, 20).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(Items.cooked_porkchop), 5, 20).setRewardFactor(2.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 0), 6, 15).setRewardFactor(1.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 12), 6, 15).setRewardFactor(1.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 14), 6, 15).setRewardFactor(1.0f));
		WESTEROS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 15), 6, 15).setRewardFactor(1.0f));

		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(GOTRegistry.planks2, 1, 11), 60, 120).setRewardFactor(0.2f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(GOTRegistry.planks2, 1, 2), 60, 120).setRewardFactor(0.2f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(GOTRegistry.planks2, 1, 2), 80, 160).setRewardFactor(0.125f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(GOTRegistry.planks3, 1, 3), 60, 120).setRewardFactor(0.2f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(GOTRegistry.planks3, 1, 4), 60, 120).setRewardFactor(0.125f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood4, 1, 2), 20, 60).setRewardFactor(0.25f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood4, 1, 2), 30, 60).setRewardFactor(0.25f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood6, 1, 0), 40, 80).setRewardFactor(0.25f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood8, 1, 3), 30, 60).setRewardFactor(0.25f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood9, 1, 0), 30, 60).setRewardFactor(0.25f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.sandstone, 1, 0), 30, 80).setRewardFactor(0.25f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.sandstone, 1, 1), 15, 40).setRewardFactor(0.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.stone, 1, 0), 30, 80).setRewardFactor(0.25f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.iron_ore), 10, 20).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(GOTRegistry.oreCopper), 10, 5).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(GOTRegistry.oreTin), 10, 5).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.gold_ore), 10, 5).setRewardFactor(5.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.copperIngot), 1, 5).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.silverIngot), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.tinIngot), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.tinIngot), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(Items.coal), 20, 50).setRewardFactor(0.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(Items.gold_ingot), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(Items.leather), 5, 20).setRewardFactor(0.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeBattleaxe), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeBoots), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeChestplate), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeHelmet), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeIngot), 1, 5).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeLeggings), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeSword), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeThrowingAxe), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironBattleaxe), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironDagger), 1, 5).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironPike), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironThrowingAxe), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_boots), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_chestplate), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_helmet), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_ingot), 1, 5).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_leggings), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_sword), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.arrow), 20, 50).setRewardFactor(0.25f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.bronzeHoe), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_axe), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.bronzeAxe), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_shovel), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.bronzeShovel), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.chisel), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.shears), 1, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.bucket), 1, 5).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.banana), 2, 4).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.plum), 4, 12).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mango), 2, 4).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.olive), 10, 20).setRewardFactor(1.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.orange), 4, 12).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugAraq), 3, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugBananaBeer), 4, 10).setRewardFactor(2.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugCactusLiqueur), 4, 10).setRewardFactor(2.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugCarrotWine), 4, 10).setRewardFactor(2.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugCornLiquor), 4, 10).setRewardFactor(2.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.mugRum), 4, 10).setRewardFactor(2.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(Items.bread), 5, 20).setRewardFactor(1.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.kebab), 4, 8).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.lemon), 1, 100).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(Blocks.melon_block), 1, 10).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(Items.melon), 1, 10).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.lime), 4, 12).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.lionCooked), 2, 4).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.rabbitCooked), 5, 12).setRewardFactor(1.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.rhinoCooked), 4, 6).setRewardFactor(3.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.muttonCooked), 4, 8).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.date), 3, 5).setRewardFactor(4.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.date), 8, 15).setRewardFactor(2.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.deerCooked), 5, 10).setRewardFactor(1.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectBartender").setCollectItem(new ItemStack(GOTRegistry.deerCooked), 5, 12).setRewardFactor(1.5f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 0), 6, 15).setRewardFactor(1.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 12), 6, 15).setRewardFactor(1.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 14), 6, 15).setRewardFactor(1.0f));
		ESSOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 15), 6, 15).setRewardFactor(1.0f));

		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.banana), 2, 4).setRewardFactor(4.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.plum), 4, 12).setRewardFactor(2.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.mango), 2, 4).setRewardFactor(4.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.olive), 10, 20).setRewardFactor(1.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.orange), 4, 12).setRewardFactor(2.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.mugAraq), 3, 5).setRewardFactor(4.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.mugBananaBeer), 4, 10).setRewardFactor(2.5f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.mugCactusLiqueur), 4, 10).setRewardFactor(2.5f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.mugCarrotWine), 4, 10).setRewardFactor(2.5f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.mugCornLiquor), 4, 10).setRewardFactor(2.5f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.mugLemonLiqueur), 2, 6).setRewardFactor(4.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.mugOrangeJuice), 2, 6).setRewardFactor(4.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.mugRum), 4, 10).setRewardFactor(2.5f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(Items.bread), 5, 20).setRewardFactor(1.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.kebab), 4, 8).setRewardFactor(2.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.lemon), 1, 100).setRewardFactor(2.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(Blocks.melon_block), 1, 10).setRewardFactor(2.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(Items.melon), 1, 10).setRewardFactor(2.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.lime), 4, 12).setRewardFactor(2.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.lionCooked), 2, 4).setRewardFactor(4.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.rabbitCooked), 5, 12).setRewardFactor(1.5f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.rhinoCooked), 4, 6).setRewardFactor(3.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.muttonCooked), 4, 8).setRewardFactor(2.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.date), 3, 5).setRewardFactor(4.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.date), 8, 15).setRewardFactor(2.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.deerCooked), 5, 10).setRewardFactor(1.5f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.deerCooked), 5, 12).setRewardFactor(1.5f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.nomadBattleaxe), 1, 5).setRewardFactor(3.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.nomadBow), 1, 5).setRewardFactor(3.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.nomadSpear), 1, 5).setRewardFactor(3.0f));
		NOMAD.addQuest(new GOTMiniQuestCollect.QFCollect("collectNomad").setCollectItem(new ItemStack(GOTRegistry.nomadSword), 1, 5).setRewardFactor(3.0f));

		ASSHAI.addQuest(new GOTMiniQuestCollect.QFCollect("collectAsshai").setCollectItem(new ItemStack(GOTRegistry.bottlePoison), 6, 15).setRewardFactor(1.0f));
		ASSHAI.addQuest(new GOTMiniQuestCollect.QFCollect("collectAsshai").setCollectItem(new ItemStack(GOTRegistry.asshaiFlower), 6, 15).setRewardFactor(1.0f));

		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeBattleaxe), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeBoots), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeChestplate), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeHelmet), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeIngot), 1, 5).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeLeggings), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeSword), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeThrowingAxe), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.ironBattleaxe), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.ironDagger), 1, 5).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.ironPike), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.ironThrowingAxe), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.wildlingAxe), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.wildlingBattleaxe), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.wildlingDagger), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.wildlingDaggerPoisoned), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.wildlingHammer), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.wildlingPolearm), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.wildlingSpear), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.wildlingSword), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.iron_boots), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.iron_chestplate), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.iron_helmet), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.iron_ingot), 1, 5).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.iron_leggings), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.iron_sword), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.arrow), 20, 50).setRewardFactor(0.25f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeHoe), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.iron_axe), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeAxe), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.iron_shovel), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.bronzeShovel), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.chisel), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.shears), 1, 5).setRewardFactor(4.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.bucket), 1, 5).setRewardFactor(3.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.deerCooked), 5, 20).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.muttonCooked), 5, 20).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(GOTRegistry.rabbitCooked), 5, 20).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.bread), 5, 20).setRewardFactor(1.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.cooked_beef), 5, 20).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.cooked_chicken), 5, 20).setRewardFactor(2.0f));
		WILDLING.addQuest(new GOTMiniQuestCollect.QFCollect("collectWildling").setCollectItem(new ItemStack(Items.cooked_porkchop), 5, 20).setRewardFactor(2.0f));

		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.flintSpear), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.flintDagger), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeBattleaxe), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeBoots), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeChestplate), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeHelmet), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeIngot), 1, 5).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeLeggings), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeSword), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeThrowingAxe), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.ironBattleaxe), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.ironDagger), 1, 5).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.ironPike), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.ironThrowingAxe), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.iron_boots), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.iron_chestplate), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.iron_helmet), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.iron_ingot), 1, 5).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.iron_leggings), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.iron_sword), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.arrow), 20, 50).setRewardFactor(0.25f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeHoe), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.iron_axe), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeAxe), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.iron_shovel), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.bronzeShovel), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.chisel), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.shears), 1, 5).setRewardFactor(4.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.bucket), 1, 5).setRewardFactor(3.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.deerCooked), 5, 20).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.muttonCooked), 5, 20).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(GOTRegistry.rabbitCooked), 5, 20).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.bread), 5, 20).setRewardFactor(1.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.cooked_beef), 5, 20).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.cooked_chicken), 5, 20).setRewardFactor(2.0f));
		IBBEN.addQuest(new GOTMiniQuestCollect.QFCollect("collectIbben").setCollectItem(new ItemStack(Items.cooked_porkchop), 5, 20).setRewardFactor(2.0f));

		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.copperIngot), 1, 5).setRewardFactor(2.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.silverIngot), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.tinIngot), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.tinIngot), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.coal), 20, 50).setRewardFactor(0.5f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.gold_ingot), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.leather), 5, 20).setRewardFactor(0.5f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(2.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.bronzeHoe), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.iron_axe), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.bronzeAxe), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.iron_shovel), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.bronzeShovel), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.chisel), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.shears), 1, 5).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.bucket), 1, 5).setRewardFactor(3.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.sothoryosSword), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.sothoryosDagger), 1, 4).setRewardFactor(4.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.sothoryosDaggerPoisoned), 1, 3).setRewardFactor(6.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.sothoryosAxe), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.sothoryosSpear), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.sothoryosHammer), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.sothoryosBattleaxe), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.sothoryosPike), 1, 4).setRewardFactor(5.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.obsidianShard), 10, 30).setRewardFactor(0.75f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Blocks.wool, 1, 0), 6, 15).setRewardFactor(1.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Blocks.wool, 1, 12), 6, 15).setRewardFactor(1.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Blocks.wool, 1, 14), 6, 15).setRewardFactor(1.0f));
		SOTHORYOS.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Blocks.wool, 1, 15), 6, 15).setRewardFactor(1.0f));

		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.copperIngot), 1, 5).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.silverIngot), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.tinIngot), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.tinIngot), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.coal), 20, 50).setRewardFactor(0.5f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.gold_ingot), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.leather), 5, 20).setRewardFactor(0.5f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(2.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.bronzeHoe), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.iron_axe), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.bronzeAxe), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.iron_shovel), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.bronzeShovel), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.chisel), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.shears), 1, 5).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Items.bucket), 1, 5).setRewardFactor(3.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.summerBoots), 1, 4).setRewardFactor(5.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.summerChestplate), 1, 4).setRewardFactor(4.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.summerDagger), 1, 3).setRewardFactor(6.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.summerDaggerPoisoned), 1, 4).setRewardFactor(5.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.summerHelmet), 1, 4).setRewardFactor(5.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.summerLeggings), 1, 4).setRewardFactor(5.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.summerPike), 1, 4).setRewardFactor(5.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.summerSpear), 1, 4).setRewardFactor(5.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(GOTRegistry.summerSword), 10, 30).setRewardFactor(0.75f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Blocks.wool, 1, 0), 6, 15).setRewardFactor(1.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Blocks.wool, 1, 12), 6, 15).setRewardFactor(1.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Blocks.wool, 1, 14), 6, 15).setRewardFactor(1.0f));
		SUMMER.addQuest(new GOTMiniQuestCollect.QFCollect("collectSothoryos").setCollectItem(new ItemStack(Blocks.wool, 1, 15), 6, 15).setRewardFactor(1.0f));

		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(Blocks.planks, 1, 0), 50, 100).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(Blocks.planks, 1, 1), 50, 100).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectMaterials").setCollectItem(new ItemStack(Blocks.stonebrick), 50, 100).setRewardFactor(0.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(Blocks.log, 1, 0), 20, 50).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood2, 1, 0), 20, 50).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood2, 1, 1), 20, 50).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood4, 1, 0), 20, 50).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectWood").setCollectItem(new ItemStack(GOTRegistry.wood6, 1, 1), 20, 50).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(GOTRegistry.rock, 1, 0), 20, 50).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(GOTRegistry.rock, 1, 1), 20, 50).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.cobblestone), 30, 80).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.iron_ore), 10, 20).setRewardFactor(2.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(GOTRegistry.oreCopper), 10, 5).setRewardFactor(2.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(GOTRegistry.oreTin), 10, 5).setRewardFactor(2.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectMineral").setCollectItem(new ItemStack(Blocks.gold_ore), 10, 5).setRewardFactor(5.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.copperIngot), 1, 5).setRewardFactor(2.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.silverIngot), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.tinIngot), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(GOTRegistry.tinIngot), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(Items.coal), 20, 50).setRewardFactor(0.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(Items.gold_ingot), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectSmith").setCollectItem(new ItemStack(Items.leather), 5, 20).setRewardFactor(0.5f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeBattleaxe), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeBoots), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeChestplate), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeHelmet), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeIngot), 1, 5).setRewardFactor(2.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeLeggings), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeSword), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.bronzeThrowingAxe), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironBattleaxe), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironDagger), 1, 5).setRewardFactor(2.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironPike), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(GOTRegistry.ironThrowingAxe), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_boots), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_chestplate), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_helmet), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_ingot), 1, 5).setRewardFactor(2.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_leggings), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.iron_sword), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectEquipment").setCollectItem(new ItemStack(Items.arrow), 20, 50).setRewardFactor(0.25f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(2.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_hoe), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.bronzeHoe), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_axe), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.bronzeAxe), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.iron_shovel), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.bronzeShovel), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(GOTRegistry.chisel), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.shears), 1, 5).setRewardFactor(4.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectTool").setCollectItem(new ItemStack(Items.bucket), 1, 5).setRewardFactor(3.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 0), 6, 15).setRewardFactor(1.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 12), 6, 15).setRewardFactor(1.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 14), 6, 15).setRewardFactor(1.0f));
		MOSSOVY.addQuest(new GOTMiniQuestCollect.QFCollect("collectWool").setCollectItem(new ItemStack(Blocks.wool, 1, 15), 6, 15).setRewardFactor(1.0f));

		CRIMINAL.addQuest(new GOTMiniQuestPickpocket.QFPickpocket("criminal").setPickpocketNumber(1, 9).setIsLegendary());
	}

	private static void registerQuestClass(Class<? extends GOTMiniQuest> questClass, int weight) {
		questClassWeights.put(questClass, weight);
	}
}
