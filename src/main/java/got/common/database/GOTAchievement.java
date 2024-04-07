package got.common.database;

import got.common.GOTChatEvents;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestPickpocket;
import got.common.util.GOTEnumDyeColor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;

import java.awt.*;
import java.util.List;
import java.util.*;

@SuppressWarnings({"PublicField", "WeakerAccess"})
public class GOTAchievement {
	public static final Set<GOTAchievement> CONTENT = new HashSet<>();

	public static final Map<ItemArmor.ArmorMaterial, GOTAchievement> ARMOR_ACHIEVEMENTS = new EnumMap<>(ItemArmor.ArmorMaterial.class);

	public static GOTAchievement bannerProtect;
	public static GOTAchievement brewDrinkInBarrel;
	public static GOTAchievement catchButterfly;
	public static GOTAchievement collectCraftingTables;
	public static GOTAchievement collectCrossbowBolts;
	public static GOTAchievement combineSmithScrolls;
	public static GOTAchievement cookKebab;
	public static GOTAchievement craftBomb;
	public static GOTAchievement craftBronze;
	public static GOTAchievement craftCopper;
	public static GOTAchievement craftSaddle;
	public static GOTAchievement craftWildFire;
	public static GOTAchievement defeatInvasion;
	public static GOTAchievement doLegendaryQuest;
	public static GOTAchievement doMiniquestHunter5;
	public static GOTAchievement doMiniquestHunter;
	public static GOTAchievement doQuest;
	public static GOTAchievement drinkFire;
	public static GOTAchievement drinkPlantainBrew;
	public static GOTAchievement drinkSkull;
	public static GOTAchievement drinkTermite;
	public static GOTAchievement earnManyCoins;
	public static GOTAchievement engraveOwnership;
	public static GOTAchievement enterAlwaysWinter;
	public static GOTAchievement enterArbor;
	public static GOTAchievement enterArrynTown;
	public static GOTAchievement enterArrynVale;
	public static GOTAchievement enterAsshai;
	public static GOTAchievement enterBleedingSea;
	public static GOTAchievement enterBoneMountains;
	public static GOTAchievement enterBraavos;
	public static GOTAchievement enterCannibalSands;
	public static GOTAchievement enterColdCoast;
	public static GOTAchievement enterCrownlands;
	public static GOTAchievement enterDisputedLands;
	public static GOTAchievement enterDorne;
	public static GOTAchievement enterDorneDesert;
	public static GOTAchievement enterDorneMesa;
	public static GOTAchievement enterDorneMountains;
	public static GOTAchievement enterDothrakiSea;
	public static GOTAchievement enterDragonstone;
	public static GOTAchievement enterEssosMountains;
	public static GOTAchievement enterFireField;
	public static GOTAchievement enterFrostfangs;
	public static GOTAchievement enterGhiscar;
	public static GOTAchievement enterGhiscarColony;
	public static GOTAchievement enterGiftNew;
	public static GOTAchievement enterGiftOld;
	public static GOTAchievement enterHauntedForest;
	public static GOTAchievement enterIbben;
	public static GOTAchievement enterIfekevronForest;
	public static GOTAchievement enterIronIslands;
	public static GOTAchievement enterIrontreeForest;
	public static GOTAchievement enterIsleOfFaces;
	public static GOTAchievement enterJogosNhai;
	public static GOTAchievement enterJogosNhaiDesert;
	public static GOTAchievement enterKingsLanding;
	public static GOTAchievement enterKingswood;
	public static GOTAchievement enterKnownWorld;
	public static GOTAchievement enterLhazar;
	public static GOTAchievement enterLongSummer;
	public static GOTAchievement enterLorath;
	public static GOTAchievement enterLys;
	public static GOTAchievement enterMoonMountains;
	public static GOTAchievement enterMoonMountainsFoothills;
	public static GOTAchievement enterMossovy;
	public static GOTAchievement enterMossovyMarshes;
	public static GOTAchievement enterMossovyMountains;
	public static GOTAchievement enterMyr;
	public static GOTAchievement enterNeck;
	public static GOTAchievement enterNorth;
	public static GOTAchievement enterNorthBarrows;
	public static GOTAchievement enterNorthMountains;
	public static GOTAchievement enterNorthTown;
	public static GOTAchievement enterNorthWild;
	public static GOTAchievement enterNorvos;
	public static GOTAchievement enterPentos;
	public static GOTAchievement enterQarth;
	public static GOTAchievement enterQarthColony;
	public static GOTAchievement enterQarthDesert;
	public static GOTAchievement enterQohor;
	public static GOTAchievement enterQohorForest;
	public static GOTAchievement enterReach;
	public static GOTAchievement enterReachTown;
	public static GOTAchievement enterRiverlands;
	public static GOTAchievement enterShadowLand;
	public static GOTAchievement enterShadowMountains;
	public static GOTAchievement enterShrykesLand;
	public static GOTAchievement enterSkagos;
	public static GOTAchievement enterSnowyWasteland;
	public static GOTAchievement enterSothoryosBushland;
	public static GOTAchievement enterSothoryosDesert;
	public static GOTAchievement enterSothoryosForest;
	public static GOTAchievement enterSothoryosFrost;
	public static GOTAchievement enterSothoryosHell;
	public static GOTAchievement enterSothoryosJungle;
	public static GOTAchievement enterSothoryosMangrove;
	public static GOTAchievement enterSothoryosMountains;
	public static GOTAchievement enterSothoryosSavannah;
	public static GOTAchievement enterSothoryosTaiga;
	public static GOTAchievement enterStepstones;
	public static GOTAchievement enterStoneCoast;
	public static GOTAchievement enterStormlands;
	public static GOTAchievement enterSummerColony;
	public static GOTAchievement enterSummerIslands;
	public static GOTAchievement enterTarth;
	public static GOTAchievement enterThennLand;
	public static GOTAchievement enterTropicalForest;
	public static GOTAchievement enterTyrosh;
	public static GOTAchievement enterUlthos;
	public static GOTAchievement enterUlthosDesert;
	public static GOTAchievement enterUlthosDesertCold;
	public static GOTAchievement enterUlthosForest;
	public static GOTAchievement enterUlthosFrost;
	public static GOTAchievement enterUlthosMarshes;
	public static GOTAchievement enterUlthosMountains;
	public static GOTAchievement enterUlthosRedForest;
	public static GOTAchievement enterUlthosTaiga;
	public static GOTAchievement enterValyria;
	public static GOTAchievement enterValyriaVolcano;
	public static GOTAchievement enterVolantis;
	public static GOTAchievement enterVolantisOrangeForest;
	public static GOTAchievement enterWesterlands;
	public static GOTAchievement enterWesterlandsHills;
	public static GOTAchievement enterWesterlandsTown;
	public static GOTAchievement enterYeen;
	public static GOTAchievement enterYiTi;
	public static GOTAchievement enterYiTiWasteland;
	public static GOTAchievement factionConquest;
	public static GOTAchievement findFourLeafClover;
	public static GOTAchievement findPlantain;
	public static GOTAchievement fishRing;
	public static GOTAchievement freeman;
	public static GOTAchievement gainHighAlcoholTolerance;
	public static GOTAchievement getConcrete;
	public static GOTAchievement getDrunk;
	public static GOTAchievement getPouch;
	public static GOTAchievement growBaobab;
	public static GOTAchievement hireGoldenCompany;
	public static GOTAchievement hundreds;
	public static GOTAchievement killAlliserThorne;
	public static GOTAchievement killArdrianCeltigar;
	public static GOTAchievement killAsshaiArchmag;
	public static GOTAchievement killBalonGreyjoy;
	public static GOTAchievement killBarristanSelmy;
	public static GOTAchievement killBeaver;
	public static GOTAchievement killBenjenStark;
	public static GOTAchievement killBericDayne;
	public static GOTAchievement killBericDondarrion;
	public static GOTAchievement killBombardier;
	public static GOTAchievement killBrienneTarth;
	public static GOTAchievement killBryndenTully;
	public static GOTAchievement killBuGai;
	public static GOTAchievement killButterfly;
	public static GOTAchievement killCerseiLannister;
	public static GOTAchievement killCraster;
	public static GOTAchievement killDaenerysTargaryen;
	public static GOTAchievement killDavosSeaworth;
	public static GOTAchievement killDoranMartell;
	public static GOTAchievement killEdmureTully;
	public static GOTAchievement killEllaryaSand;
	public static GOTAchievement killEuronGreyjoy;
	public static GOTAchievement killGendryBaratheon;
	public static GOTAchievement killGeroldDayne;
	public static GOTAchievement killGiant;
	public static GOTAchievement killGladiator;
	public static GOTAchievement killGregorClegane;
	public static GOTAchievement killHodor;
	public static GOTAchievement killHowlandReed;
	public static GOTAchievement killHuntingPlayer;
	public static GOTAchievement killIbben;
	public static GOTAchievement killIceSpider;
	public static GOTAchievement killIllyrioMopatis;
	public static GOTAchievement killJaimeLannister;
	public static GOTAchievement killJaqenHghar;
	public static GOTAchievement killJeorMormont;
	public static GOTAchievement killJonSnow;
	public static GOTAchievement killJorahMormont;
	public static GOTAchievement killKevanLannister;
	public static GOTAchievement killKhal;
	public static GOTAchievement killKraznysMoNakloz;
	public static GOTAchievement killLancelLannister;
	public static GOTAchievement killLargeMobWithSlingshot;
	public static GOTAchievement killLorasTyrell;
	public static GOTAchievement killMaceTyrell;
	public static GOTAchievement killMaester;
	public static GOTAchievement killMammoth;
	public static GOTAchievement killManceRayder;
	public static GOTAchievement killMargaeryTyrell;
	public static GOTAchievement killMelisandra;
	public static GOTAchievement killNightKing;
	public static GOTAchievement killNightWatchGuard;
	public static GOTAchievement killOberynMartell;
	public static GOTAchievement killPetyrBaelish;
	public static GOTAchievement killPriest;
	public static GOTAchievement killProstitute;
	public static GOTAchievement killRamsayBolton;
	public static GOTAchievement killRobbStark;
	public static GOTAchievement killRooseBolton;
	public static GOTAchievement killSamurai;
	public static GOTAchievement killSandorClegane;
	public static GOTAchievement killSansaStark;
	public static GOTAchievement killShryke;
	public static GOTAchievement killTheKing;
	public static GOTAchievement killThePolice;
	public static GOTAchievement killThennBerserker;
	public static GOTAchievement killTheonGreyjoy;
	public static GOTAchievement killThievingBandit;
	public static GOTAchievement killThoros;
	public static GOTAchievement killTormund;
	public static GOTAchievement killTugarKhan;
	public static GOTAchievement killTyrion;
	public static GOTAchievement killTywin;
	public static GOTAchievement killUlthos;
	public static GOTAchievement killUnsullied;
	public static GOTAchievement killUsingOnlyPlates;
	public static GOTAchievement killVarys;
	public static GOTAchievement killVassal;
	public static GOTAchievement killVictarionGreyjoy;
	public static GOTAchievement killWerewolf;
	public static GOTAchievement killWhileDrunk;
	public static GOTAchievement killWhiteWalker;
	public static GOTAchievement killWight;
	public static GOTAchievement killWightGiant;
	public static GOTAchievement killWitcher;
	public static GOTAchievement killXaroXhoanDaxos;
	public static GOTAchievement killYaraGreyjoy;
	public static GOTAchievement killYgritte;
	public static GOTAchievement killer;
	public static GOTAchievement lightBeacon;
	public static GOTAchievement marry;
	public static GOTAchievement mineGlowstone;
	public static GOTAchievement mineValyrian;
	public static GOTAchievement obama;
	public static GOTAchievement pickpocket;
	public static GOTAchievement pledgeService;
	public static GOTAchievement reforge;
	public static GOTAchievement rideCamel;
	public static GOTAchievement shootDownMidges;
	public static GOTAchievement smeltObsidianShard;
	public static GOTAchievement speakToDrunkard;
	public static GOTAchievement steal;
	public static GOTAchievement stealArborGrapes;
	public static GOTAchievement trade;
	public static GOTAchievement travel10;
	public static GOTAchievement travel20;
	public static GOTAchievement travel30;
	public static GOTAchievement travel40;
	public static GOTAchievement travel50;
	public static GOTAchievement unsmelt;
	public static GOTAchievement useCrossbow;
	public static GOTAchievement useSpearFromFar;
	public static GOTAchievement useThrowingAxe;
	public static GOTAchievement wearFullArryn;
	public static GOTAchievement wearFullArrynguard;
	public static GOTAchievement wearFullAsshai;
	public static GOTAchievement wearFullBlackfyre;
	public static GOTAchievement wearFullBone;
	public static GOTAchievement wearFullBraavos;
	public static GOTAchievement wearFullBronze;
	public static GOTAchievement wearFullCrownlands;
	public static GOTAchievement wearFullDorne;
	public static GOTAchievement wearFullDothraki;
	public static GOTAchievement wearFullDragonstone;
	public static GOTAchievement wearFullFur;
	public static GOTAchievement wearFullGemsbok;
	public static GOTAchievement wearFullGhiscar;
	public static GOTAchievement wearFullGift;
	public static GOTAchievement wearFullGoldencompany;
	public static GOTAchievement wearFullHand;
	public static GOTAchievement wearFullHelmet;
	public static GOTAchievement wearFullHillmen;
	public static GOTAchievement wearFullIronborn;
	public static GOTAchievement wearFullJogos;
	public static GOTAchievement wearFullKingsguard;
	public static GOTAchievement wearFullLhazar;
	public static GOTAchievement wearFullLhazarLion;
	public static GOTAchievement wearFullLorath;
	public static GOTAchievement wearFullLys;
	public static GOTAchievement wearFullMossovy;
	public static GOTAchievement wearFullMyr;
	public static GOTAchievement wearFullNorth;
	public static GOTAchievement wearFullNorthguard;
	public static GOTAchievement wearFullNorvos;
	public static GOTAchievement wearFullPentos;
	public static GOTAchievement wearFullQarth;
	public static GOTAchievement wearFullQohor;
	public static GOTAchievement wearFullReach;
	public static GOTAchievement wearFullReachguard;
	public static GOTAchievement wearFullRedking;
	public static GOTAchievement wearFullRiverlands;
	public static GOTAchievement wearFullRobes;
	public static GOTAchievement wearFullRoyce;
	public static GOTAchievement wearFullSothoryos;
	public static GOTAchievement wearFullSothoryosGold;
	public static GOTAchievement wearFullStormlands;
	public static GOTAchievement wearFullSummer;
	public static GOTAchievement wearFullTargaryen;
	public static GOTAchievement wearFullTyrosh;
	public static GOTAchievement wearFullUnsullied;
	public static GOTAchievement wearFullValyrian;
	public static GOTAchievement wearFullVolantis;
	public static GOTAchievement wearFullWesterlands;
	public static GOTAchievement wearFullWesterlandsguard;
	public static GOTAchievement wearFullWestking;
	public static GOTAchievement wearFullWhitewalkers;
	public static GOTAchievement wearFullYiti;
	public static GOTAchievement wearFullYitiFrontier;
	public static GOTAchievement wearFullYitiSamurai;

	private Collection<GOTFaction> allyFactions = new ArrayList<>();

	private Category category;
	private int id;
	private ItemStack icon;
	private String name;
	private boolean isBiomeAchievement;
	private boolean isSpecial;
	private GOTTitle achievementTitle;

	private GOTAchievement(Category c, int i, Block block, String s) {
		this(c, i, new ItemStack(block), s);
	}

	public GOTAchievement(Category c, int i, Item item, String s) {
		this(c, i, new ItemStack(item), s);
	}

	private GOTAchievement(Category c, int i, ItemStack itemstack, String s) {
		category = c;
		id = i;
		icon = itemstack;
		name = s;
		for (GOTAchievement achievement : category.getList()) {
			if (achievement.id != id) {
				continue;
			}
			throw new IllegalArgumentException("Duplicate ID " + id + " for GOT achievement category " + category.name());
		}
		category.addAchievement(this);
		getDimension().allAchievements.add(this);
		CONTENT.add(this);
	}

	public static GOTAchievement achievementForCategoryAndID(Category category, int ID) {
		if (category == null) {
			return null;
		}
		for (GOTAchievement achievement : category.getList()) {
			if (achievement.id != ID) {
				continue;
			}
			return achievement;
		}
		return null;
	}

	public static Category categoryForName(String name) {
		for (Category category : Category.values()) {
			if (category.name().equals(name)) {
				return category;
			}
		}
		return null;
	}

	private static GOTAchievement createArmorAchievement(GOTAchievement.Category category, int id, Item item, String name) {
		GOTAchievement achievement = new GOTAchievement(category, id, item, name);
		ARMOR_ACHIEVEMENTS.put(((ItemArmor) item).getArmorMaterial(), achievement);
		return achievement;
	}

	public static GOTAchievement findByName(String name) {
		for (Category category : Category.values()) {
			for (GOTAchievement achievement : category.getList()) {
				if (achievement.name.equalsIgnoreCase(name)) {
					return achievement;
				}
			}
		}
		return null;
	}

	public static List<GOTAchievement> getAllAchievements() {
		List<GOTAchievement> list = new ArrayList<>();
		for (Category category : Category.values()) {
			list.addAll(category.getList());
		}
		return list;
	}

	public static void onInit() {
		int genId = 1;
		enterKnownWorld = new GOTAchievement(Category.GENERAL, genId++, GOTItems.gregorCleganeSword, "ENTER_GOT");
		freeman = new GOTAchievement(Category.GENERAL, genId++, GOTItems.crowbar, "FREEMAN");

		bannerProtect = new GOTAchievement(Category.GENERAL, genId++, GOTItems.banner, "BANNER_PROTECT");
		brewDrinkInBarrel = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.barrel, "BREW_DRINK_IN_BARREL");
		catchButterfly = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.butterflyJar, "CATCH_BUTTERFLY");
		collectCraftingTables = new GOTAchievement(Category.GENERAL, genId++, Blocks.crafting_table, "COLLECT_CRAFTING_TABLES");
		collectCrossbowBolts = new GOTAchievement(Category.GENERAL, genId++, GOTItems.crossbowBolt, "COLLECT_CROSSBOW_BOLTS");
		combineSmithScrolls = new GOTAchievement(Category.GENERAL, genId++, GOTItems.smithScroll, "COMBINE_SMITH_SCROLLS");
		cookKebab = new GOTAchievement(Category.GENERAL, genId++, GOTItems.kebab, "COOK_KEBAB");
		craftBomb = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.bomb, "CRAFT_BOMB");
		craftBronze = new GOTAchievement(Category.GENERAL, genId++, GOTItems.bronzeIngot, "GET_BRONZE");
		craftCopper = new GOTAchievement(Category.GENERAL, genId++, GOTItems.copperIngot, "GET_COPPER");
		craftSaddle = new GOTAchievement(Category.GENERAL, genId++, Items.saddle, "CRAFT_SADDLE");
		craftWildFire = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.wildFireJar, "CRAFT_WILD_FIRE");
		defeatInvasion = new GOTAchievement(Category.GENERAL, genId++, GOTItems.gregorCleganeSword, "DEFEAT_INVASION");
		doLegendaryQuest = new GOTAchievement(Category.GENERAL, genId++, Blocks.dragon_egg, "DO_LEGENDARY_QUEST");
		doMiniquestHunter = new GOTAchievement(Category.GENERAL, genId++, GOTItems.questBook, "DO_MINIQUEST_HUNTER");
		doMiniquestHunter5 = new GOTAchievement(Category.GENERAL, genId++, GOTItems.bountyTrophy, "DO_MINIQUEST_HUNTER5");
		doQuest = new GOTAchievement(Category.GENERAL, genId++, GOTItems.questBook, "DO_QUEST");
		drinkFire = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugWildFire, "DRINK_FIRE");
		drinkPlantainBrew = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugPlantainBrew, "DRINK_PLANTAIN_BREW");
		drinkSkull = new GOTAchievement(Category.GENERAL, genId++, GOTItems.skullCup, "DRINK_SKULL");
		drinkTermite = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugTermiteTequila, "DRINK_TERMITE");
		earnManyCoins = new GOTAchievement(Category.GENERAL, genId++, GOTItems.coin, "EARN_MANY_COINS");
		engraveOwnership = new GOTAchievement(Category.GENERAL, genId++, Blocks.anvil, "ENGRAVE");
		factionConquest = new GOTAchievement(Category.GENERAL, genId++, GOTItems.gregorCleganeSword, "FACTION_CONQUEST");
		findFourLeafClover = new GOTAchievement(Category.GENERAL, genId++, new ItemStack(GOTBlocks.clover, 1, 1), "FIND_FOUR_LEAF_CLOVER");
		findPlantain = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.plantain, "FIND_PLANTAIN");
		fishRing = new GOTAchievement(Category.GENERAL, genId++, Items.fishing_rod, "FISH_RING");
		gainHighAlcoholTolerance = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugAle, "GAIN_HIGH_ALCOHOL_TOLERANCE");
		getConcrete = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.CONCRETE_POWDER.get(GOTEnumDyeColor.LIME), "GET_CONCRETE");
		getDrunk = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugAle, "GET_DRUNK");
		getPouch = new GOTAchievement(Category.GENERAL, genId++, GOTItems.pouch, "GET_POUCH");
		growBaobab = new GOTAchievement(Category.GENERAL, genId++, new ItemStack(GOTBlocks.sapling4, 1, 1), "GROW_BAOBAB");
		hireGoldenCompany = new GOTAchievement(Category.GENERAL, genId++, GOTItems.goldHelmet, "HIRE_GOLDEN_COMPANY");
		hundreds = new GOTAchievement(Category.GENERAL, genId++, GOTItems.gregorCleganeSword, "HUNDREDS");
		lightBeacon = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.beacon, "LIGHT_BEACON");
		marry = new GOTAchievement(Category.GENERAL, genId++, GOTItems.goldRing, "MARRY");
		mineGlowstone = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.oreGlowstone, "MINE_GLOWSTONE");
		mineValyrian = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.oreValyrian, "MINE_VALYRIAN");
		obama = new GOTAchievement(Category.GENERAL, genId++, GOTItems.banana, "OBAMA");
		pickpocket = new GOTAchievement(Category.GENERAL, genId++, GOTMiniQuestPickpocket.createPickpocketIcon(), "PICKPOCKET");
		pledgeService = new GOTAchievement(Category.GENERAL, genId++, GOTItems.gregorCleganeSword, "PLEDGE_SERVICE");
		reforge = new GOTAchievement(Category.GENERAL, genId++, Blocks.anvil, "REFORGE");
		rideCamel = new GOTAchievement(Category.GENERAL, genId++, Items.saddle, "RIDE_CAMEL");
		shootDownMidges = new GOTAchievement(Category.GENERAL, genId++, GOTItems.ironCrossbow, "SHOOT_DOWN_MIDGES");
		smeltObsidianShard = new GOTAchievement(Category.GENERAL, genId++, GOTItems.obsidianShard, "SMELT_OBSIDIAN_SHARD");
		speakToDrunkard = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugAle, "SPEAK_TO_DRUNKARD");
		steal = new GOTAchievement(Category.GENERAL, genId++, GOTItems.coin, "STEAL");
		stealArborGrapes = new GOTAchievement(Category.GENERAL, genId++, GOTItems.grapeRed, "STEAL_ARBOR_GRAPES");
		trade = new GOTAchievement(Category.GENERAL, genId++, GOTItems.coin, "TRADE");
		travel10 = new GOTAchievement(Category.GENERAL, genId++, Items.map, "TRAVEL10");
		travel20 = new GOTAchievement(Category.GENERAL, genId++, Items.map, "TRAVEL20");
		travel30 = new GOTAchievement(Category.GENERAL, genId++, Items.map, "TRAVEL30");
		travel40 = new GOTAchievement(Category.GENERAL, genId++, Items.map, "TRAVEL40");
		travel50 = new GOTAchievement(Category.GENERAL, genId++, Items.map, "TRAVEL50");
		unsmelt = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.unsmeltery, "UNSMELT");
		useCrossbow = new GOTAchievement(Category.GENERAL, genId++, GOTItems.ironCrossbow, "USE_CROSSBOW");
		useSpearFromFar = new GOTAchievement(Category.GENERAL, genId++, GOTItems.ironSpear, "USE_SPEAR_FROM_FAR");
		useThrowingAxe = new GOTAchievement(Category.GENERAL, genId++, GOTItems.ironThrowingAxe, "USE_THROWING_AXE");

		int killId = 1;
		killer = new GOTAchievement(Category.KILL, killId++, Items.iron_axe, "KILLER");

		killBeaver = new GOTAchievement(Category.KILL, genId++, GOTItems.beaverTail, "KILL_BEAVER");
		killBombardier = new GOTAchievement(Category.KILL, killId++, GOTBlocks.bomb, "KILL_BOMBARDIER");
		killButterfly = new GOTAchievement(Category.KILL, killId++, Items.iron_sword, "KILL_BUTTERFLY");
		killGiant = new GOTAchievement(Category.KILL, killId++, GOTItems.club, "KILL_GIANT");
		killGladiator = new GOTAchievement(Category.KILL, killId++, GOTItems.essosSword, "KILL_GLADIATOR");
		killHuntingPlayer = new GOTAchievement(Category.KILL, killId++, Items.iron_sword, "KILL_HUNTING_PLAYER");
		killIbben = new GOTAchievement(Category.KILL, killId++, GOTItems.flintSpear, "KILL_IBBEN");
		killIceSpider = new GOTAchievement(Category.KILL, killId++, GOTItems.valyrianSword, "KILL_ICE_SPIDER");
		killKhal = new GOTAchievement(Category.KILL, killId++, GOTItems.lhazarSword, "KILL_KHAL");
		killLargeMobWithSlingshot = new GOTAchievement(Category.KILL, killId++, GOTItems.sling, "KILL_LARGE_MOB_WITH_SLINGSHOT");
		killMaester = new GOTAchievement(Category.KILL, killId++, Items.book, "KILL_MAESTER");
		killMammoth = new GOTAchievement(Category.KILL, killId++, GOTItems.stoneSpear, "KILL_MAMMOTH");
		killPriest = new GOTAchievement(Category.KILL, killId++, Blocks.fire, "KILL_PRIEST");
		killProstitute = new GOTAchievement(Category.KILL, killId++, GOTItems.ironCrossbow, "KILL_PROSTITUTE");
		killSamurai = new GOTAchievement(Category.KILL, killId++, GOTItems.katana, "KILL_SAMURAI");
		killShryke = new GOTAchievement(Category.KILL, killId++, GOTItems.bottlePoison, "KILL_SHRYKE");
		killThePolice = new GOTAchievement(Category.KILL, killId++, GOTItems.westerosDagger, "BANDIT");
		killThievingBandit = new GOTAchievement(Category.KILL, killId++, GOTItems.leatherHat, "KILL_THIEVING_BANDIT");
		killUlthos = new GOTAchievement(Category.KILL, killId++, GOTItems.mysteryWeb, "KILL_ULTHOS");
		killUnsullied = new GOTAchievement(Category.KILL, killId++, GOTItems.essosPike, "KILL_UNSULLIED");
		killUsingOnlyPlates = new GOTAchievement(Category.KILL, killId++, GOTItems.plate, "KILL_USING_ONLY_PLATES");
		killWerewolf = new GOTAchievement(Category.KILL, killId++, GOTItems.mossovySword, "KILL_WEREWOLF");
		killWhileDrunk = new GOTAchievement(Category.KILL, killId++, GOTItems.mugAle, "KILL_WHILE_DRUNK");
		killWhiteWalker = new GOTAchievement(Category.KILL, killId++, GOTItems.valyrianSword, "KILL_WHITE_WALKER");
		killWight = new GOTAchievement(Category.KILL, killId++, GOTItems.bericSword, "KILL_WIGHT");
		killWightGiant = new GOTAchievement(Category.KILL, killId++, GOTItems.bericSword, "KILL_WIGHT_GIANT");
		killWitcher = new GOTAchievement(Category.KILL, killId++, GOTItems.mossovySword, "KILL_WITCHER");

		int legId = 1;
		killAlliserThorne = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.westerosSword, "KILL_ALLISER_THORNE").createTitle();
		killArdrianCeltigar = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.celtigarAxe, "KILL_ARDRIAN_CELTIGAR").createTitle();
		killAsshaiArchmag = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.archmagStaff, "KILL_ASSHAI_ARCHMAG").createTitle();
		killBalonGreyjoy = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.euronDagger, "KILL_BALON_GREYJOY");
		killBarristanSelmy = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.westerosSword, "KILL_BARRISTAN_SELMY");
		killBenjenStark = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.valyrianSword, "KILL_BENJEN_STARK");
		killBericDayne = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.dawn, "KILL_BERIC_DAYNE");
		killBericDondarrion = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.bericSword, "KILL_BERIC_DONDARRION").createTitle();
		killBrienneTarth = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.sapphire, "KILL_BRIENNE_TARTH");
		killBryndenTully = new GOTAchievement(Category.LEGENDARY, legId++, Items.fish, "KILL_BRYNDEN_TULLY").createTitle();
		killBuGai = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.yitiSword, "KILL_BU_GAI").createTitle();
		killCerseiLannister = new GOTAchievement(Category.LEGENDARY, legId++, GOTBlocks.wildFireJar, "KILL_CERSEI_LANNISTER");
		killCraster = new GOTAchievement(Category.LEGENDARY, legId++, Items.iron_axe, "KILL_CRASTER").createTitle();
		killDaenerysTargaryen = new GOTAchievement(Category.LEGENDARY, legId++, Blocks.dragon_egg, "KILL_DAENERYS_TARGARYEN").createTitle();
		killDavosSeaworth = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.leek, "KILL_DAVOS_SEAWORTH");
		killDoranMartell = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.tyeneDagger, "KILL_DORAN_MARTELL");
		killEdmureTully = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.arrowFire, "KILL_EDMURE_TULLY");
		killEllaryaSand = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.bottlePoison, "KILL_ELLARYA_SAND").createTitle();
		killEuronGreyjoy = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.euronDagger, "KILL_EURON_GREYJOY");
		killGendryBaratheon = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.gendryHammer, "KILL_GENDRY_BARATHEON");
		killGeroldDayne = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.darkstar, "KILL_GEROLD_DAYNE");
		killGregorClegane = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.gregorCleganeSword, "KILL_GREGOR_CLEGANE").createTitle();
		killHodor = new GOTAchievement(Category.LEGENDARY, legId++, Items.stick, "KILL_HODOR");
		killHowlandReed = new GOTAchievement(Category.LEGENDARY, legId++, GOTBlocks.reeds, "KILL_HOWLAND_REED");
		killIllyrioMopatis = new GOTAchievement(Category.LEGENDARY, legId++, Items.gold_ingot, "KILL_ILLYRIO_MOPATIS");
		killJaimeLannister = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.jaimeSword, "KILL_JAIME_LANNISTER");
		killJaqenHghar = new GOTAchievement(Category.LEGENDARY, legId++, new ItemStack(GOTItems.coin, 1, 1), "KILL_JAQEN_HGHAR").createTitle();
		killJeorMormont = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.longclaw, "KILL_JEOR_MORMONT").createTitle();
		killJonSnow = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.longclaw, "KILL_JON_SNOW").createTitle();
		killJorahMormont = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.harpy, "KILL_JORAH_MORMONT");
		killKevanLannister = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.handGold, "KILL_KEVAN_LANNISTER");
		killKraznysMoNakloz = new GOTAchievement(Category.LEGENDARY, legId++, Blocks.dragon_egg, "KILL_KRAZNYS_MO_NAKLOZ");
		killLancelLannister = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.gregorCleganeSword, "KILL_LANCEL_LANNISTER");
		killLorasTyrell = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.gregorCleganeSword, "KILL_LORAS_TYRELL");
		killMaceTyrell = new GOTAchievement(Category.LEGENDARY, legId++, GOTBlocks.wildFire, "KILL_MACE_TYRELL");
		killManceRayder = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.wildlingSword, "KILL_MANCE_RAYDER");
		killMargaeryTyrell = new GOTAchievement(Category.LEGENDARY, legId++, Blocks.red_flower, "KILL_MARGAERY_TYRELL");
		killMelisandra = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.lightbringer, "KILL_MELISANDRA");
		killNightKing = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.nightKingSword, "KILL_NIGHT_KING").createTitle();
		killNightWatchGuard = new GOTAchievement(Category.KILL, legId++, GOTItems.westerosSword, "KILL_NIGHT_WATCH_GUARD");
		killOberynMartell = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.sunspear, "KILL_OBERYN_MARTELL").createTitle();
		killPetyrBaelish = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.baelishDagger, "KILL_PETYR_BAELISH");
		killRamsayBolton = new GOTAchievement(Category.LEGENDARY, legId++, Items.bone, "KILL_RAMSAY_BOLTON");
		killRobbStark = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.robbSword, "KILL_ROBB_STARK");
		killRooseBolton = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.boltonDagger, "KILL_ROOSE_BOLTON");
		killSandorClegane = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.sandorCleganeSword, "KILL_SANDOR_CLEGANE").createTitle();
		killSansaStark = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.justMaid, "KILL_SANSA_STARK");
		killTheKing = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.aegonHelmet, "KILL_THE_KING").createTitle();
		killThennBerserker = new GOTAchievement(Category.KILL, legId++, GOTItems.wildlingSword, "KILL_THENN_BERSERKER");
		killTheonGreyjoy = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.boltonDagger, "KILL_THEON_GREYJOY");
		killThoros = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.westerosSword, "KILL_THOROS");
		killTormund = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.tormundSword, "KILL_TORMUND");
		killTugarKhan = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.nomadSword, "KILL_TUGAR_KHAN").createTitle();
		killTyrion = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.mugRedWine, "KILL_TYRION");
		killTywin = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.westkingHelmet, "KILL_TYWIN").createTitle();
		killVarys = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.westerosSword, "KILL_VARYS");
		killVassal = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.westerosSword, "KILL_VASSAL");
		killVictarionGreyjoy = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.victarionAxe, "KILL_VICTARION_GREYJOY");
		killXaroXhoanDaxos = new GOTAchievement(Category.LEGENDARY, legId++, Items.gold_ingot, "KILL_XARO_XHOAN_DAXOS");
		killYaraGreyjoy = new GOTAchievement(Category.LEGENDARY, legId++, Items.iron_axe, "KILL_YARA_GREYJOY");
		killYgritte = new GOTAchievement(Category.LEGENDARY, legId++, GOTItems.wildlingDagger, "KILL_YGRITTE");

		int visId = 1;
		enterAlwaysWinter = new GOTAchievement(Category.VISIT, visId++, Blocks.ice, "VISIT_ALWAYS_WINTER").setBiomeAchievement();
		enterArbor = new GOTAchievement(Category.VISIT, visId++, GOTItems.grapeRed, "VISIT_ARBOR").setBiomeAchievement();
		enterArrynTown = new GOTAchievement(Category.VISIT, visId++, GOTItems.arrynguardHelmet, "VISIT_ARRYN_TOWN").setBiomeAchievement();
		enterArrynVale = new GOTAchievement(Category.VISIT, visId++, GOTItems.arrynHelmet, "VISIT_ARRYN").setBiomeAchievement();
		enterAsshai = new GOTAchievement(Category.VISIT, visId++, GOTItems.asshaiStaff, "VISIT_ASSHAI").setBiomeAchievement();
		enterBleedingSea = new GOTAchievement(Category.VISIT, visId++, GOTItems.bronzeSword, "VISIT_RED_SEA").setBiomeAchievement();
		enterBoneMountains = new GOTAchievement(Category.VISIT, visId++, GOTBlocks.boneBlock, "VISIT_BONE_MOUNTAINS").setBiomeAchievement();
		enterBraavos = new GOTAchievement(Category.VISIT, visId++, GOTItems.braavosHelmet, "VISIT_BRAAVOS").setBiomeAchievement();
		enterCannibalSands = new GOTAchievement(Category.VISIT, visId++, GOTBlocks.quicksand, "VISIT_CANNIBAL_SANDS").setBiomeAchievement();
		enterColdCoast = new GOTAchievement(Category.VISIT, visId++, GOTItems.wildlingPolearm, "VISIT_COLD_COAST").setBiomeAchievement();
		enterCrownlands = new GOTAchievement(Category.VISIT, visId++, GOTItems.crownlandsHelmet, "VISIT_CROWNLANDS").setBiomeAchievement();
		enterDisputedLands = new GOTAchievement(Category.VISIT, visId++, GOTItems.goldHelmet, "VISIT_MERCENARY").setBiomeAchievement();
		enterDorne = new GOTAchievement(Category.VISIT, visId++, GOTItems.dorneHelmet, "VISIT_DORNE").setBiomeAchievement();
		enterDorneDesert = new GOTAchievement(Category.VISIT, visId++, Blocks.sand, "VISIT_DORNE_DESERT").setBiomeAchievement();
		enterDorneMesa = new GOTAchievement(Category.VISIT, visId++, Blocks.clay, "VISIT_DORNE_MESA").setBiomeAchievement();
		enterDorneMountains = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.rock, 1, 4), "VISIT_DORNE_MOUNTAINS").setBiomeAchievement();
		enterDothrakiSea = new GOTAchievement(Category.VISIT, visId++, GOTItems.nomadSword, "VISIT_DOTHRAKI_SEA").setBiomeAchievement();
		enterDragonstone = new GOTAchievement(Category.VISIT, visId++, GOTItems.obsidianShard, "VISIT_DRAGONSTONE").setBiomeAchievement();
		enterEssosMountains = new GOTAchievement(Category.VISIT, visId++, Blocks.stone, "VISIT_ESSOS_MOUNTAINS").setBiomeAchievement();
		enterFireField = new GOTAchievement(Category.VISIT, visId++, Blocks.red_flower, "VISIT_FIRE_FIELD").setBiomeAchievement();
		enterFrostfangs = new GOTAchievement(Category.VISIT, visId++, Blocks.packed_ice, "VISIT_FROSTFANGS").setBiomeAchievement();
		enterGhiscar = new GOTAchievement(Category.VISIT, visId++, GOTItems.harpy, "VISIT_GHISCAR").setBiomeAchievement();
		enterGhiscarColony = new GOTAchievement(Category.VISIT, visId++, GOTItems.ghiscarHelmet, "VISIT_GHISCAR_COLONY").setBiomeAchievement();
		enterGiftNew = new GOTAchievement(Category.VISIT, visId++, Blocks.snow, "VISIT_GIFT_NEW").setBiomeAchievement();
		enterGiftOld = new GOTAchievement(Category.VISIT, visId++, GOTBlocks.brickIce, "VISIT_GIFT_OLD").setBiomeAchievement();
		enterHauntedForest = new GOTAchievement(Category.VISIT, visId++, GOTItems.club, "VISIT_HAUNTED_FOREST").setBiomeAchievement();
		enterIbben = new GOTAchievement(Category.VISIT, visId++, GOTItems.flintDagger, "VISIT_IBBEN").setBiomeAchievement();
		enterIfekevronForest = new GOTAchievement(Category.VISIT, visId++, GOTItems.flintSpear, "VISIT_IFEKEVRON").setBiomeAchievement();
		enterIronIslands = new GOTAchievement(Category.VISIT, visId++, GOTItems.ironbornHelmet, "VISIT_IRONBORN").setBiomeAchievement();
		enterIrontreeForest = new GOTAchievement(Category.VISIT, visId++, Items.iron_axe, "VISIT_IRONTREE").setBiomeAchievement();
		enterIsleOfFaces = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.leaves9, 1, 2), "VISIT_FACES").setBiomeAchievement();
		enterJogosNhai = new GOTAchievement(Category.VISIT, visId++, GOTItems.nomadBow, "VISIT_JOGOS").setBiomeAchievement();
		enterJogosNhaiDesert = new GOTAchievement(Category.VISIT, visId++, GOTBlocks.quicksand, "VISIT_JOGOS_DESERT").setBiomeAchievement();
		enterKingsLanding = new GOTAchievement(Category.VISIT, visId++, GOTItems.crownlandsHelmet, "VISIT_KINGS_LANDING").setBiomeAchievement();
		enterKingswood = new GOTAchievement(Category.VISIT, visId++, GOTItems.mugRedWine, "VISIT_CROWNLANDS_FOREST").setBiomeAchievement();
		enterLhazar = new GOTAchievement(Category.VISIT, visId++, GOTItems.lhazarHelmet, "VISIT_LHAZAR").setBiomeAchievement();
		enterLongSummer = new GOTAchievement(Category.VISIT, visId++, Blocks.fire, "VISIT_LONG_SUMMER").setBiomeAchievement();
		enterLorath = new GOTAchievement(Category.VISIT, visId++, GOTItems.lorathHelmet, "VISIT_LORATH").setBiomeAchievement();
		enterLys = new GOTAchievement(Category.VISIT, visId++, GOTItems.lysHelmet, "VISIT_LYS").setBiomeAchievement();
		enterMoonMountains = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.rock, 1, 1), "VISIT_ARRYN_MOUNTAINS").setBiomeAchievement();
		enterMoonMountainsFoothills = new GOTAchievement(Category.VISIT, visId++, GOTItems.trident, "VISIT_HILL_TRIBES").setBiomeAchievement();
		enterMossovy = new GOTAchievement(Category.VISIT, visId++, GOTItems.mossovySword, "VISIT_MOSSOVY").setBiomeAchievement();
		enterMossovyMarshes = new GOTAchievement(Category.VISIT, visId++, GOTBlocks.reeds, "VISIT_MOSSOVY_MARSHES").setBiomeAchievement();
		enterMossovyMountains = new GOTAchievement(Category.VISIT, visId++, Blocks.stone, "VISIT_MOSSOVY_SOPKAS").setBiomeAchievement();
		enterMyr = new GOTAchievement(Category.VISIT, visId++, GOTItems.myrHelmet, "VISIT_MYR").setBiomeAchievement();
		enterNeck = new GOTAchievement(Category.VISIT, visId++, GOTBlocks.quagmire, "VISIT_NECK").setBiomeAchievement();
		enterNorth = new GOTAchievement(Category.VISIT, visId++, GOTItems.northHelmet, "VISIT_NORTH").setBiomeAchievement();
		enterNorthBarrows = new GOTAchievement(Category.VISIT, visId++, GOTItems.coin, "VISIT_NORTH_BARROWS").setBiomeAchievement();
		enterNorthMountains = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.rock, 1, 1), "VISIT_NORTH_MOUNTAINS").setBiomeAchievement();
		enterNorthTown = new GOTAchievement(Category.VISIT, visId++, GOTItems.northguardHelmet, "VISIT_NORTH_TOWN").setBiomeAchievement();
		enterNorthWild = new GOTAchievement(Category.VISIT, visId++, GOTItems.trident, "VISIT_NORTH_WILD").setBiomeAchievement();
		enterNorvos = new GOTAchievement(Category.VISIT, visId++, GOTItems.norvosHelmet, "VISIT_NORVOS").setBiomeAchievement();
		enterPentos = new GOTAchievement(Category.VISIT, visId++, GOTItems.pentosHelmet, "VISIT_PENTOS").setBiomeAchievement();
		enterQarth = new GOTAchievement(Category.VISIT, visId++, GOTItems.qarthHelmet, "VISIT_QARTH").setBiomeAchievement();
		enterQarthColony = new GOTAchievement(Category.VISIT, visId++, GOTItems.qarthHelmet, "VISIT_QARTH_COLONY").setBiomeAchievement();
		enterQarthDesert = new GOTAchievement(Category.VISIT, visId++, GOTBlocks.redSandstone, "VISIT_DORNE_DESERT").setBiomeAchievement();
		enterQohor = new GOTAchievement(Category.VISIT, visId++, GOTItems.qohorHelmet, "VISIT_QOHOR").setBiomeAchievement();
		enterQohorForest = new GOTAchievement(Category.VISIT, visId++, GOTItems.qohorHelmet, "VISIT_QOHOR_FOREST").setBiomeAchievement();
		enterReach = new GOTAchievement(Category.VISIT, visId++, GOTItems.reachHelmet, "VISIT_REACH").setBiomeAchievement();
		enterReachTown = new GOTAchievement(Category.VISIT, visId++, GOTItems.reachguardHelmet, "VISIT_REACH_TOWN").setBiomeAchievement();
		enterRiverlands = new GOTAchievement(Category.VISIT, visId++, GOTItems.riverlandsHelmet, "VISIT_RIVERLANDS").setBiomeAchievement();
		enterShadowLand = new GOTAchievement(Category.VISIT, visId++, GOTBlocks.asshaiFlower, "VISIT_SHADOW_LAND").setBiomeAchievement();
		enterShadowMountains = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.rock, 1, 0), "VISIT_SHADOW_MOUNTAINS").setBiomeAchievement();
		enterShrykesLand = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.deadMarshPlant), "VISIT_SHRYKES_LAND").setBiomeAchievement();
		enterSkagos = new GOTAchievement(Category.VISIT, visId++, GOTItems.trident, "VISIT_SKAGOS").setBiomeAchievement();
		enterSnowyWasteland = new GOTAchievement(Category.VISIT, visId++, Blocks.snow, "VISIT_FAR_NORTH").setBiomeAchievement();
		enterSothoryosBushland = new GOTAchievement(Category.VISIT, visId++, GOTItems.termite, "VISIT_SOTHORYOS_BUSHLAND").setBiomeAchievement();
		enterSothoryosDesert = new GOTAchievement(Category.VISIT, visId++, Blocks.sand, "VISIT_SOTHORYOS_DESERT").setBiomeAchievement();
		enterSothoryosForest = new GOTAchievement(Category.VISIT, visId++, GOTItems.sothoryosHelmetGold, "VISIT_SOTHORYOS_KANUKA").setBiomeAchievement();
		enterSothoryosFrost = new GOTAchievement(Category.VISIT, visId++, Blocks.packed_ice, "VISIT_SOTHORYOS_FROST").setBiomeAchievement();
		enterSothoryosHell = new GOTAchievement(Category.VISIT, visId++, GOTItems.sothoryosAmulet, "VISIT_SOTHORYOS_HELL").setBiomeAchievement();
		enterSothoryosJungle = new GOTAchievement(Category.VISIT, visId++, GOTItems.sothoryosHelmet, "VISIT_SOTHORYOS_JUNGLE").setBiomeAchievement();
		enterSothoryosMangrove = new GOTAchievement(Category.VISIT, visId++, Blocks.water, "VISIT_SOTHORYOS_MANGROVE").setBiomeAchievement();
		enterSothoryosMountains = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.rock, 1, 6), "VISIT_SOTHORYOS_MOUNTAINS").setBiomeAchievement();
		enterSothoryosSavannah = new GOTAchievement(Category.VISIT, visId++, Blocks.grass, "VISIT_SOTHORYOS_SAVANNAH").setBiomeAchievement();
		enterSothoryosTaiga = new GOTAchievement(Category.VISIT, visId++, GOTItems.club, "VISIT_SOTHORYOS_TAIGA").setBiomeAchievement();
		enterStepstones = new GOTAchievement(Category.VISIT, visId++, GOTItems.essosBow, "VISIT_STEPSTONES").setBiomeAchievement();
		enterStoneCoast = new GOTAchievement(Category.VISIT, visId++, GOTItems.westerosDaggerPoisoned, "VISIT_STONE_COAST").setBiomeAchievement();
		enterStormlands = new GOTAchievement(Category.VISIT, visId++, GOTItems.stormlandsHelmet, "VISIT_STORMLANDS").setBiomeAchievement();
		enterSummerColony = new GOTAchievement(Category.VISIT, visId++, GOTItems.summerHelmet, "VISIT_SUMMER_COLONY").setBiomeAchievement();
		enterSummerIslands = new GOTAchievement(Category.VISIT, visId++, GOTItems.summerHelmet, "VISIT_SUMMER_ISLANDS").setBiomeAchievement();
		enterTarth = new GOTAchievement(Category.VISIT, visId++, GOTBlocks.whiteSandstone, "VISIT_TARTH").setBiomeAchievement();
		enterThennLand = new GOTAchievement(Category.VISIT, visId++, GOTItems.wildlingBattleaxe, "VISIT_THENN").setBiomeAchievement();
		enterTropicalForest = new GOTAchievement(Category.VISIT, visId++, GOTItems.yitiHelmetShogune, "VISIT_TROPICAL_FOREST").setBiomeAchievement();
		enterTyrosh = new GOTAchievement(Category.VISIT, visId++, GOTItems.tyroshHelmet, "VISIT_TYROSH").setBiomeAchievement();
		enterUlthos = new GOTAchievement(Category.VISIT, visId++, Blocks.web, "VISIT_ULTHOS").setBiomeAchievement();
		enterUlthosDesert = new GOTAchievement(Category.VISIT, visId++, Blocks.sand, "VISIT_ULTHOS_DESERT").setBiomeAchievement();
		enterUlthosDesertCold = new GOTAchievement(Category.VISIT, visId++, new ItemStack(Blocks.sand), "VISIT_ULTHOS_DESERT_COLD").setBiomeAchievement();
		enterUlthosForest = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.wood1, 1, 2), "VISIT_ULTHOS_FOREST").setBiomeAchievement();
		enterUlthosFrost = new GOTAchievement(Category.VISIT, visId++, new ItemStack(Blocks.snow), "VISIT_ULTHOS_FROST").setBiomeAchievement();
		enterUlthosMarshes = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.deadMarshPlant), "VISIT_ULTHOS_MARSHES").setBiomeAchievement();
		enterUlthosMountains = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.rock, 1, 3), "VISIT_ULTHOS_MOUNTAINS").setBiomeAchievement();
		enterUlthosRedForest = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.wood1), "VISIT_ULTHOS_RED_FOREST").setBiomeAchievement();
		enterUlthosTaiga = new GOTAchievement(Category.VISIT, visId++, new ItemStack(GOTBlocks.wood1, 1, 0), "VISIT_ULTHOS_TAIGA").setBiomeAchievement();
		enterValyria = new GOTAchievement(Category.VISIT, visId++, Blocks.vine, "VISIT_VALYRIA").setBiomeAchievement();
		enterValyriaVolcano = new GOTAchievement(Category.VISIT, visId++, Blocks.lava, "VISIT_VALYRIA_VOLCANO").setBiomeAchievement();
		enterVolantis = new GOTAchievement(Category.VISIT, visId++, GOTItems.volantisHelmet, "VISIT_VOLANTIS").setBiomeAchievement();
		enterVolantisOrangeForest = new GOTAchievement(Category.VISIT, visId++, GOTItems.orange, "VISIT_VOLANTIS_FOREST").setBiomeAchievement();
		enterWesterlands = new GOTAchievement(Category.VISIT, visId++, GOTItems.westerlandsHelmet, "VISIT_WESTERLANDS").setBiomeAchievement();
		enterWesterlandsHills = new GOTAchievement(Category.VISIT, visId++, Blocks.gold_ore, "VISIT_WESTERLANDS_HILLS").setBiomeAchievement();
		enterWesterlandsTown = new GOTAchievement(Category.VISIT, visId++, GOTItems.westerlandsguardHelmet, "VISIT_WESTERLANDS_TOWN").setBiomeAchievement();
		enterYeen = new GOTAchievement(Category.VISIT, visId++, Blocks.obsidian, "VISIT_YEEN").setBiomeAchievement();
		enterYiTi = new GOTAchievement(Category.VISIT, visId++, GOTItems.yitiHelmet, "VISIT_YI_TI").setBiomeAchievement();
		enterYiTiWasteland = new GOTAchievement(Category.VISIT, visId++, GOTItems.yitiHelmetSamurai, "VISIT_YI_TI_WASTELAND").setBiomeAchievement();

		int wearId = 1;
		wearFullArrynguard = createArmorAchievement(Category.WEAR, wearId++, GOTItems.arrynguardChestplate, "WEAR_FULL_ARRYNGUARD");
		wearFullAsshai = createArmorAchievement(Category.WEAR, wearId++, GOTItems.asshaiChestplate, "WEAR_FULL_ASSHAI");
		wearFullBlackfyre = createArmorAchievement(Category.WEAR, wearId++, GOTItems.blackfyreChestplate, "WEAR_FULL_BLACKFYRE");
		wearFullBone = createArmorAchievement(Category.WEAR, wearId++, GOTItems.boneChestplate, "WEAR_FULL_BONE");
		wearFullBraavos = createArmorAchievement(Category.WEAR, wearId++, GOTItems.braavosChestplate, "WEAR_FULL_BRAAVOS");
		wearFullBronze = createArmorAchievement(Category.WEAR, wearId++, GOTItems.bronzeChestplate, "WEAR_FULL_BRONZE");
		wearFullCrownlands = createArmorAchievement(Category.WEAR, wearId++, GOTItems.crownlandsChestplate, "WEAR_FULL_CROWNLANDS");
		wearFullDorne = createArmorAchievement(Category.WEAR, wearId++, GOTItems.dorneChestplate, "WEAR_FULL_DORNE");
		wearFullDothraki = createArmorAchievement(Category.WEAR, wearId++, GOTItems.dothrakiChestplate, "WEAR_FULL_DOTHRAKI");
		wearFullDragonstone = createArmorAchievement(Category.WEAR, wearId++, GOTItems.dragonstoneChestplate, "WEAR_FULL_DRAGONSTONE");
		wearFullFur = createArmorAchievement(Category.WEAR, wearId++, GOTItems.furChestplate, "WEAR_FULL_FUR");
		wearFullGemsbok = createArmorAchievement(Category.WEAR, wearId++, GOTItems.gemsbokChestplate, "WEAR_FULL_GEMSBOK");
		wearFullGhiscar = createArmorAchievement(Category.WEAR, wearId++, GOTItems.ghiscarChestplate, "WEAR_FULL_GHISCAR");
		wearFullGift = createArmorAchievement(Category.WEAR, wearId++, GOTItems.giftChestplate, "WEAR_FULL_GIFT");
		wearFullGoldencompany = createArmorAchievement(Category.WEAR, wearId++, GOTItems.goldChestplate, "WEAR_FULL_GOLDENCOMPANY");
		wearFullHand = createArmorAchievement(Category.WEAR, wearId++, GOTItems.handGold, "WEAR_FULL_HAND");
		wearFullHelmet = createArmorAchievement(Category.WEAR, wearId++, GOTItems.robertHelmet, "WEAR_FULL_HELMET");
		wearFullHillmen = createArmorAchievement(Category.WEAR, wearId++, GOTItems.hillmenChestplate, "WEAR_FULL_HILLMEN");
		wearFullIronborn = createArmorAchievement(Category.WEAR, wearId++, GOTItems.ironbornChestplate, "WEAR_FULL_IRONBORN");
		wearFullJogos = createArmorAchievement(Category.WEAR, wearId++, GOTItems.jogosChestplate, "WEAR_FULL_JOGOS");
		wearFullKingsguard = createArmorAchievement(Category.WEAR, wearId++, GOTItems.kingsguardChestplate, "WEAR_FULL_KINGSGUARD");
		wearFullLhazar = createArmorAchievement(Category.WEAR, wearId++, GOTItems.lhazarChestplate, "WEAR_FULL_LHAZAR");
		wearFullLhazarLion = createArmorAchievement(Category.WEAR, wearId++, GOTItems.lhazarChestplateLion, "WEAR_FULL_LHAZAR_LION");
		wearFullLorath = createArmorAchievement(Category.WEAR, wearId++, GOTItems.lorathChestplate, "WEAR_FULL_LORATH");
		wearFullLys = createArmorAchievement(Category.WEAR, wearId++, GOTItems.lysChestplate, "WEAR_FULL_LYS");
		wearFullMossovy = createArmorAchievement(Category.WEAR, wearId++, GOTItems.mossovyChestplate, "WEAR_FULL_MOSSOVY");
		wearFullMyr = createArmorAchievement(Category.WEAR, wearId++, GOTItems.myrChestplate, "WEAR_FULL_MYR");
		wearFullNorth = createArmorAchievement(Category.WEAR, wearId++, GOTItems.northChestplate, "WEAR_FULL_NORTH");
		wearFullNorthguard = createArmorAchievement(Category.WEAR, wearId++, GOTItems.northguardChestplate, "WEAR_FULL_NORTHGUARD");
		wearFullNorvos = createArmorAchievement(Category.WEAR, wearId++, GOTItems.norvosChestplate, "WEAR_FULL_NORVOS");
		wearFullPentos = createArmorAchievement(Category.WEAR, wearId++, GOTItems.pentosChestplate, "WEAR_FULL_PENTOS");
		wearFullQarth = createArmorAchievement(Category.WEAR, wearId++, GOTItems.qarthChestplate, "WEAR_FULL_QARTH");
		wearFullQohor = createArmorAchievement(Category.WEAR, wearId++, GOTItems.qohorChestplate, "WEAR_FULL_QOHOR");
		wearFullReach = createArmorAchievement(Category.WEAR, wearId++, GOTItems.reachChestplate, "WEAR_FULL_REACH");
		wearFullReachguard = createArmorAchievement(Category.WEAR, wearId++, GOTItems.reachguardChestplate, "WEAR_FULL_REACHGUARD");
		wearFullRedking = createArmorAchievement(Category.WEAR, wearId++, GOTItems.redkingChestplate, "WEAR_FULL_REDKING");
		wearFullRiverlands = createArmorAchievement(Category.WEAR, wearId++, GOTItems.riverlandsChestplate, "WEAR_FULL_RIVERLANDS");
		wearFullRobes = createArmorAchievement(Category.WEAR, wearId++, GOTItems.robesChestplate, "WEAR_FULL_ROBES");
		wearFullRoyce = createArmorAchievement(Category.WEAR, wearId++, GOTItems.royceChestplate, "WEAR_FULL_ROYCE");
		wearFullSothoryos = createArmorAchievement(Category.WEAR, wearId++, GOTItems.sothoryosChestplate, "WEAR_FULL_SOTHORYOS");
		wearFullSothoryosGold = createArmorAchievement(Category.WEAR, wearId++, GOTItems.sothoryosChestplateGold, "WEAR_FULL_SOTHORYOS_GOLD");
		wearFullStormlands = createArmorAchievement(Category.WEAR, wearId++, GOTItems.stormlandsChestplate, "WEAR_FULL_STORMLANDS");
		wearFullSummer = createArmorAchievement(Category.WEAR, wearId++, GOTItems.summerChestplate, "WEAR_FULL_SUMMER");
		wearFullTargaryen = createArmorAchievement(Category.WEAR, wearId++, GOTItems.targaryenChestplate, "WEAR_FULL_TARGARYEN");
		wearFullTyrosh = createArmorAchievement(Category.WEAR, wearId++, GOTItems.tyroshChestplate, "WEAR_FULL_TYROSH");
		wearFullUnsullied = createArmorAchievement(Category.WEAR, wearId++, GOTItems.unsulliedChestplate, "WEAR_FULL_UNSULLIED");
		wearFullValyrian = createArmorAchievement(Category.WEAR, wearId++, GOTItems.valyrianChestplate, "WEAR_FULL_VALYRIAN");
		wearFullVolantis = createArmorAchievement(Category.WEAR, wearId++, GOTItems.volantisChestplate, "WEAR_FULL_VOLANTIS");
		wearFullWesterlands = createArmorAchievement(Category.WEAR, wearId++, GOTItems.westerlandsChestplate, "WEAR_FULL_WESTERLANDS");
		wearFullWesterlandsguard = createArmorAchievement(Category.WEAR, wearId++, GOTItems.westerlandsguardChestplate, "WEAR_FULL_WESTERLANDSGUARD");
		wearFullWestking = createArmorAchievement(Category.WEAR, wearId++, GOTItems.westkingChestplate, "WEAR_FULL_WESTKING");
		wearFullWhitewalkers = createArmorAchievement(Category.WEAR, wearId++, GOTItems.whiteWalkersChestplate, "WEAR_FULL_WHITEWALKERS");
		wearFullYiti = createArmorAchievement(Category.WEAR, wearId++, GOTItems.yitiChestplate, "WEAR_FULL_YITI");
		wearFullYitiFrontier = createArmorAchievement(Category.WEAR, wearId++, GOTItems.yitiChestplateFrontier, "WEAR_FULL_YITI_FRONTIER");
		wearFullYitiSamurai = createArmorAchievement(Category.WEAR, wearId++, GOTItems.yitiChestplateSamurai, "WEAR_FULL_YITI_SAMURAI");
		wearFullArryn = createArmorAchievement(Category.WEAR, wearId++, GOTItems.arrynChestplate, "WEAR_FULL_ARRYN");
	}

	public static Comparator<GOTAchievement> sortForDisplay(EntityPlayer entityplayer) {
		return (ach1, ach2) -> {
			if (ach1.isSpecial) {
				if (!ach2.isSpecial) {
					return -1;
				}
				return Integer.compare(ach1.id, ach2.id);
			}
			if (ach2.isSpecial) {
				return 1;
			}
			if (ach1.isBiomeAchievement) {
				if (ach2.isBiomeAchievement) {
					return ach1.getTitle(entityplayer).compareTo(ach2.getTitle(entityplayer));
				}
				return -1;
			}
			if (!ach2.isBiomeAchievement) {
				return ach1.getTitle(entityplayer).compareTo(ach2.getTitle(entityplayer));
			}
			return 1;
		};
	}

	public void broadcastEarning(EntityPlayer entityplayer) {
		IChatComponent earnName = getChatComponentForEarn(entityplayer);
		IChatComponent msg = new ChatComponentTranslation("got.chat.achievement", entityplayer.func_145748_c_(), earnName);
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(msg);
	}

	public boolean canPlayerEarn(EntityPlayer entityplayer) {
		float alignment;
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		if (!allyFactions.isEmpty()) {
			boolean anyAllies = false;
			for (GOTFaction f : allyFactions) {
				alignment = playerData.getAlignment(f);
				if (alignment < 0.0f) {
					continue;
				}
				anyAllies = true;
			}
			return anyAllies;
		}
		return true;
	}

	private GOTAchievement createTitle() {
		return createTitle(null);
	}

	private GOTAchievement createTitle(String s) {
		if (achievementTitle != null) {
			throw new IllegalArgumentException("GOT achievement " + name + " already has an associated title!");
		}
		achievementTitle = new GOTTitle(s, this);
		return this;
	}

	public IChatComponent getAchievementChatComponent(EntityPlayer entityplayer) {
		ChatComponentTranslation component = new ChatComponentTranslation(getUntranslatedTitle(entityplayer)).createCopy();
		component.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		component.getChatStyle().setChatHoverEvent(new HoverEvent(GOTChatEvents.SHOW_GOT_ACHIEVEMENT, new ChatComponentText(category.name() + '$' + id)));
		return component;
	}

	private IChatComponent getChatComponentForEarn(EntityPlayer entityplayer) {
		IChatComponent base = getAchievementChatComponent(entityplayer);
		IChatComponent component = new ChatComponentText("[").appendSibling(base).appendText("]");
		component.setChatStyle(base.getChatStyle());
		return component;
	}

	public String getCodeName() {
		return name;
	}

	public String getDescription(EntityPlayer entityplayer) {
		return StatCollector.translateToLocal("got.achievement." + name + ".desc");
	}

	public GOTDimension getDimension() {
		return category.getDimension();
	}

	public String getTitle(EntityPlayer entityplayer) {
		return StatCollector.translateToLocal(getUntranslatedTitle(entityplayer));
	}

	public String getUntranslatedTitle(EntityPlayer entityplayer) {
		return "got.achievement." + name + ".title";
	}

	public void setRequiresAlly(GOTFaction... f) {
		allyFactions.addAll(Arrays.asList(f));
	}

	protected void setSpecial() {
		isSpecial = true;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBiomeAchievement() {
		return isBiomeAchievement;
	}

	public GOTAchievement setBiomeAchievement() {
		isBiomeAchievement = true;
		return this;
	}

	public boolean isSpecial() {
		return isSpecial;
	}

	public void setSpecial(boolean special) {
		isSpecial = special;
	}

	public GOTTitle getAchievementTitle() {
		return achievementTitle;
	}

	public void setAchievementTitle(GOTTitle achievementTitle) {
		this.achievementTitle = achievementTitle;
	}

	public enum Category {
		TITLES(GOTFaction.NIGHT_WATCH), GENERAL(GOTFaction.SOTHORYOS), KILL(GOTFaction.WESTERLANDS), WEAR(GOTFaction.ARRYN), VISIT(GOTFaction.WHITE_WALKER), LEGENDARY(GOTFaction.YI_TI);

		private final Collection<GOTAchievement> list = new ArrayList<>();
		private final String codeName;
		private final float[] categoryColors;
		private final GOTDimension dimension;

		private int nextRankAchID = 1000;

		Category(GOTDimension dim, int color) {
			codeName = name();
			categoryColors = new Color(color).getColorComponents(null);
			dimension = dim;
			dimension.achievementCategories.add(this);
		}

		Category(GOTFaction faction) {
			this(faction.getFactionColor());
		}

		Category(int color) {
			this(GOTDimension.GAME_OF_THRONES, color);
		}

		private String codeName() {
			return codeName;
		}

		public float[] getCategoryRGB() {
			return categoryColors;
		}

		public String getDisplayName() {
			return StatCollector.translateToLocal("got.achievement.category." + codeName());
		}

		public int getNextRankAchID() {
			++nextRankAchID;
			return nextRankAchID;
		}

		public void addAchievement(GOTAchievement achievement) {
			list.add(achievement);
		}

		public Collection<GOTAchievement> getList() {
			return new ArrayList<>(list);
		}

		public GOTDimension getDimension() {
			return dimension;
		}
	}
}