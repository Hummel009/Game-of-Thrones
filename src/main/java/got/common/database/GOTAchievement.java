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
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;

import java.awt.*;
import java.util.List;
import java.util.*;

public class GOTAchievement {
	public static int id = 1;
	public static Map<ItemArmor.ArmorMaterial, GOTAchievement> armorAchievements = new EnumMap<>(ArmorMaterial.class);
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
	public static GOTAchievement enterDoshKhalin;
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
	public static GOTAchievement enterNaath;
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
	public static GOTAchievement enterSkirlingPass;
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
	public static GOTAchievement enterWhisperingWood;
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
	public static GOTAchievement tormentTheonGreyjoy;
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
	public Category category;
	public int ID;
	public ItemStack icon;
	public String name;
	public boolean isBiomeAchievement;
	public boolean isSpecial;
	public GOTTitle achievementTitle;
	public List<GOTFaction> enemyFactions = new ArrayList<>();
	public List<GOTFaction> allyFactions = new ArrayList<>();

	public GOTAchievement(Category c, int i, Block block, String s) {
		this(c, i, new ItemStack(block), s);
	}

	public GOTAchievement(Category c, int i, Item item, String s) {
		this(c, i, new ItemStack(item), s);
	}

	public GOTAchievement(Category c, int i, ItemStack itemstack, String s) {
		category = c;
		ID = i;
		icon = itemstack;
		name = s;
		for (GOTAchievement achievement : category.list) {
			if (achievement.ID != ID) {
				continue;
			}
			throw new IllegalArgumentException("Duplicate ID " + ID + " for GOT achievement category " + category.name());
		}
		category.list.add(this);
		getDimension().allAchievements.add(this);
	}

	public static GOTAchievement achievementForCategoryAndID(Category category, int ID) {
		if (category == null) {
			return null;
		}
		for (GOTAchievement achievement : category.list) {
			if (achievement.ID != ID) {
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

	public static GOTAchievement createArmorAchievement(GOTAchievement.Category category, int id, Item item, String name) {
		GOTAchievement achievement = new GOTAchievement(category, id, item, name);
		armorAchievements.put(((ItemArmor) item).getArmorMaterial(), achievement);
		return achievement;
	}

	public static GOTAchievement findByName(String name) {
		for (Category category : Category.values()) {
			for (GOTAchievement achievement : category.list) {
				if (achievement.name.equalsIgnoreCase(name)) {
					return achievement;
				}
			}
		}
		return null;
	}

	public static List<GOTAchievement> getAllAchievements() {
		ArrayList<GOTAchievement> list = new ArrayList<>();
		for (Category category : Category.values()) {
			list.addAll(category.list);
		}
		return list;
	}

	public static void onInit() {
		killThePolice = new GOTAchievement(Category.KILL, id++, GOTItems.westerosDagger, "BANDIT");
		bannerProtect = new GOTAchievement(Category.GENERAL, id++, GOTItems.banner, "BANNER_PROTECT");
		brewDrinkInBarrel = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.barrel, "BREW_DRINK_IN_BARREL");
		craftBomb = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.bomb, "CRAFT_BOMB");
		craftWildFire = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.wildFireJar, "CRAFT_WILD_FIRE");
		defeatInvasion = new GOTAchievement(Category.GENERAL, id++, GOTItems.gregorCleganeSword, "DEFEAT_INVASION");
		doLegendaryQuest = new GOTAchievement(Category.GENERAL, id++, Blocks.dragon_egg, "DO_LEGENDARY_QUEST");
		doQuest = new GOTAchievement(Category.GENERAL, id++, GOTItems.questBook, "DO_QUEST");
		drinkFire = new GOTAchievement(Category.GENERAL, id++, GOTItems.mugWildFire, "DRINK_FIRE");
		drinkTermite = new GOTAchievement(Category.GENERAL, id++, GOTItems.mugTermiteTequila, "DRINK_TERMITE");
		earnManyCoins = new GOTAchievement(Category.GENERAL, id++, GOTItems.coin, "EARN_MANY_COINS");
		enterKnownWorld = new GOTAchievement(Category.GENERAL, id++, GOTItems.gregorCleganeSword, "ENTER_GOT");
		factionConquest = new GOTAchievement(Category.GENERAL, id++, GOTItems.gregorCleganeSword, "FACTION_CONQUEST");
		findFourLeafClover = new GOTAchievement(Category.GENERAL, id++, new ItemStack(GOTBlocks.clover, 1, 1), "FIND_FOUR_LEAF_CLOVER");
		freeman = new GOTAchievement(Category.GENERAL, id++, GOTItems.crowbar, "FREEMAN");
		gainHighAlcoholTolerance = new GOTAchievement(Category.GENERAL, id++, GOTItems.mugAle, "GAIN_HIGH_ALCOHOL_TOLERANCE");
		craftBronze = new GOTAchievement(Category.GENERAL, id++, GOTItems.bronzeIngot, "GET_BRONZE");
		getConcrete = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.concretePowder.get(GOTEnumDyeColor.LIME), "GET_CONCRETE");
		craftCopper = new GOTAchievement(Category.GENERAL, id++, GOTItems.copperIngot, "GET_COPPER");
		getPouch = new GOTAchievement(Category.GENERAL, id++, GOTItems.pouch, "GET_POUCH");
		hundreds = new GOTAchievement(Category.GENERAL, id++, GOTItems.gregorCleganeSword, "HUNDREDS");
		hireGoldenCompany = new GOTAchievement(Category.GENERAL, id++, GOTItems.goldHelmet, "HIRE_GOLDEN_COMPANY");
		killer = new GOTAchievement(Category.KILL, id++, Items.iron_axe, "KILLER");
		killAlliserThorne = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.westerosSword, "KILL_ALLISER_THORNE").createTitle();
		killAsshaiArchmag = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.archmagStaff, "KILL_ASSHAI_ARCHMAG").createTitle();
		killBalonGreyjoy = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.euronDagger, "KILL_BALON_GREYJOY");
		killBericDayne = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.dawn, "KILL_BERIC_DAYNE");
		killBericDondarrion = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.bericSword, "KILL_BERIC_DONDARRION").createTitle();
		killBuGai = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.yitiSword, "KILL_BU_GAI").createTitle();
		killCerseiLannister = new GOTAchievement(Category.LEGENDARY, id++, GOTBlocks.wildFireJar, "KILL_CERSEI_LANNISTER");
		killCraster = new GOTAchievement(Category.LEGENDARY, id++, Items.iron_axe, "KILL_CRASTER").createTitle();
		killDaenerysTargaryen = new GOTAchievement(Category.LEGENDARY, id++, Blocks.dragon_egg, "KILL_DAENERYS_TARGARYEN").createTitle();
		killDavosSeaworth = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.leek, "KILL_DAVOS_SEAWORTH");
		killEuronGreyjoy = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.euronDagger, "KILL_EURON_GREYJOY");
		killGeroldDayne = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.darkstar, "KILL_GEROLD_DAYNE");
		killGiant = new GOTAchievement(Category.KILL, id++, GOTItems.club, "KILL_GIANT");
		killGladiator = new GOTAchievement(Category.KILL, id++, GOTItems.essosSword, "KILL_GLADIATOR");
		killGregorClegane = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.gregorCleganeSword, "KILL_GREGOR_CLEGANE").createTitle();
		killHodor = new GOTAchievement(Category.LEGENDARY, id++, Items.stick, "KILL_HODOR");
		killHowlandReed = new GOTAchievement(Category.LEGENDARY, id++, GOTBlocks.reeds, "KILL_HOWLAND_REED");
		killIbben = new GOTAchievement(Category.KILL, id++, GOTItems.flintSpear, "KILL_IBBEN");
		killIceSpider = new GOTAchievement(Category.KILL, id++, GOTItems.valyrianSword, "KILL_ICE_SPIDER");
		killJaimeLannister = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.jaimeSword, "KILL_JAIME_LANNISTER");
		killJaqenHghar = new GOTAchievement(Category.LEGENDARY, id++, new ItemStack(GOTItems.coin, 1, 1), "KILL_JAQEN_HGHAR").createTitle();
		killJeorMormont = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.longclaw, "KILL_JEOR_MORMONT").createTitle();
		killJonSnow = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.longclaw, "KILL_JON_SNOW").createTitle();
		killJorahMormont = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.harpy, "KILL_JORAH_MORMONT");
		killKevanLannister = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.handGold, "KILL_KEVAN_LANNISTER");
		killKhal = new GOTAchievement(Category.KILL, id++, GOTItems.lhazarSword, "KILL_KHAL");
		killTugarKhan = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.nomadSword, "KILL_TUGAR_KHAN").createTitle();
		killLancelLannister = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.gregorCleganeSword, "KILL_LANCEL_LANNISTER");
		killLorasTyrell = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.gregorCleganeSword, "KILL_LORAS_TYRELL");
		killMaester = new GOTAchievement(Category.KILL, id++, Items.book, "KILL_MAESTER");
		killMammoth = new GOTAchievement(Category.KILL, id++, GOTItems.stoneSpear, "KILL_MAMMOTH");
		killShryke = new GOTAchievement(Category.KILL, id++, GOTItems.bottlePoison, "KILL_SHRYKE");
		killMelisandra = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.lightbringer, "KILL_MELISANDRA");
		killNightKing = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.nightKingSword, "KILL_NIGHT_KING").createTitle();
		killNightWatchGuard = new GOTAchievement(Category.KILL, id++, GOTItems.westerosSword, "KILL_NIGHT_WATCH_GUARD");
		killOberynMartell = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.sunspear, "KILL_OBERYN_MARTELL").createTitle();
		killPetyrBaelish = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.baelishDagger, "KILL_PETYR_BAELISH");
		killRamsayBolton = new GOTAchievement(Category.LEGENDARY, id++, Items.bone, "KILL_RAMSAY_BOLTON");
		killRobbStark = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.robbSword, "KILL_ROBB_STARK");
		killRooseBolton = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.boltonDagger, "KILL_ROOSE_BOLTON");
		killSamurai = new GOTAchievement(Category.KILL, id++, GOTItems.katana, "KILL_SAMURAI");
		killSandorClegane = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.sandorCleganeSword, "KILL_SANDOR_CLEGANE").createTitle();
		killThennBerserker = new GOTAchievement(Category.KILL, id++, GOTItems.wildlingSword, "KILL_THENN_BERSERKER");
		killTheKing = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.aegonHelmet, "KILL_THE_KING").createTitle();
		killTyrion = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.mugRedWine, "KILL_TYRION");
		killTywin = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.westkingHelmet, "KILL_TYWIN").createTitle();
		killUlthos = new GOTAchievement(Category.KILL, id++, GOTItems.mysteryWeb, "KILL_ULTHOS");
		killUnsullied = new GOTAchievement(Category.KILL, id++, GOTItems.essosPike, "KILL_UNSULLIED");
		killVarys = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.westerosSword, "KILL_VARYS");
		killVassal = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.westerosSword, "KILL_VASSAL");
		killVictarionGreyjoy = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.victarionAxe, "KILL_VICTARION_GREYJOY");
		killWerewolf = new GOTAchievement(Category.KILL, id++, GOTItems.mossovySword, "KILL_WEREWOLF");
		killWhiteWalker = new GOTAchievement(Category.KILL, id++, GOTItems.valyrianSword, "KILL_WHITE_WALKER");
		killProstitute = new GOTAchievement(Category.KILL, id++, GOTItems.ironCrossbow, "KILL_PROSTITUTE");
		killWight = new GOTAchievement(Category.KILL, id++, GOTItems.bericSword, "KILL_WIGHT");
		killWightGiant = new GOTAchievement(Category.KILL, id++, GOTItems.bericSword, "KILL_WIGHT_GIANT");
		killWitcher = new GOTAchievement(Category.KILL, id++, GOTItems.mossovySword, "KILL_WITCHER");
		killYaraGreyjoy = new GOTAchievement(Category.LEGENDARY, id++, Items.iron_axe, "KILL_YARA_GREYJOY");
		killThoros = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.westerosSword, "KILL_THOROS");
		killBarristanSelmy = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.westerosSword, "KILL_BARRISTAN_SELMY");
		killXaroXhoanDaxos = new GOTAchievement(Category.LEGENDARY, id++, Items.gold_ingot, "KILL_XARO_XHOAN_DAXOS");
		killGendryBaratheon = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.gendryHammer, "KILL_GENDRY_BARATHEON");
		killDoranMartell = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.tyeneDagger, "KILL_DORAN_MARTELL");
		killIllyrioMopatis = new GOTAchievement(Category.LEGENDARY, id++, Items.gold_ingot, "KILL_ILLYRIO_MOPATIS");
		killBenjenStark = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.valyrianSword, "KILL_BENJEN_STARK");
		killBrienneTarth = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.sapphire, "KILL_BRIENNE_TARTH");
		killSansaStark = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.justMaid, "KILL_SANSA_STARK");
		killTormund = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.tormundSword, "KILL_TORMUND");
		killYgritte = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.wildlingDagger, "KILL_YGRITTE");
		killManceRayder = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.wildlingSword, "KILL_MANCE_RAYDER");
		killMaceTyrell = new GOTAchievement(Category.LEGENDARY, id++, GOTBlocks.wildFire, "KILL_MACE_TYRELL");
		killMargaeryTyrell = new GOTAchievement(Category.LEGENDARY, id++, Blocks.red_flower, "KILL_MARGAERY_TYRELL");
		killEllaryaSand = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.bottlePoison, "KILL_ELLARYA_SAND").createTitle();
		killBryndenTully = new GOTAchievement(Category.LEGENDARY, id++, Items.fish, "KILL_BRYNDEN_TULLY").createTitle();
		killEdmureTully = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.arrowFire, "KILL_EDMURE_TULLY");
		killArdrianCeltigar = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.celtigarAxe, "KILL_ARDRIAN_CELTIGAR").createTitle();
		killKraznysMoNakloz = new GOTAchievement(Category.LEGENDARY, id++, Blocks.dragon_egg, "KILL_KRAZNYS_MO_NAKLOZ");
		killPriest = new GOTAchievement(Category.LEGENDARY, id++, Blocks.fire, "KILL_PRIEST");
		mineValyrian = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.oreValyrian, "MINE_VALYRIAN");
		obama = new GOTAchievement(Category.GENERAL, id++, GOTItems.banana, "OBAMA");
		pledgeService = new GOTAchievement(Category.GENERAL, id++, GOTItems.gregorCleganeSword, "PLEDGE_SERVICE");
		reforge = new GOTAchievement(Category.GENERAL, id++, Blocks.anvil, "REFORGE");
		shootDownMidges = new GOTAchievement(Category.GENERAL, id++, GOTItems.ironCrossbow, "SHOOT_DOWN_MIDGES");
		stealArborGrapes = new GOTAchievement(Category.GENERAL, id++, GOTItems.grapeRed, "STEAL_ARBOR_GRAPES");
		tormentTheonGreyjoy = new GOTAchievement(Category.LEGENDARY, id++, GOTItems.boltonDagger, "TORMENT_THEON_GREYJOY");
		trade = new GOTAchievement(Category.GENERAL, id++, GOTItems.coin, "TRADE");
		travel10 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL10");
		travel20 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL20");
		travel30 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL30");
		travel40 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL40");
		travel50 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL50");
		unsmelt = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.unsmeltery, "UNSMELT");
		engraveOwnership = new GOTAchievement(Category.GENERAL, id++, Blocks.anvil, "ENGRAVE");
		steal = new GOTAchievement(Category.GENERAL, id++, GOTItems.coin, "STEAL");
		enterAlwaysWinter = new GOTAchievement(Category.VISIT, id++, Blocks.ice, "VISIT_ALWAYS_WINTER");
		enterArbor = new GOTAchievement(Category.VISIT, id++, GOTItems.grapeRed, "VISIT_ARBOR");
		enterArrynVale = new GOTAchievement(Category.VISIT, id++, GOTItems.arrynHelmet, "VISIT_ARRYN");
		enterMoonMountains = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.rock, 1, 1), "VISIT_ARRYN_MOUNTAINS");
		enterArrynTown = new GOTAchievement(Category.VISIT, id++, GOTItems.arrynguardHelmet, "VISIT_ARRYN_TOWN");
		enterAsshai = new GOTAchievement(Category.VISIT, id++, GOTItems.asshaiStaff, "VISIT_ASSHAI");
		enterBoneMountains = new GOTAchievement(Category.VISIT, id++, GOTBlocks.boneBlock, "VISIT_BONE_MOUNTAINS");
		enterBraavos = new GOTAchievement(Category.VISIT, id++, GOTItems.braavosHelmet, "VISIT_BRAAVOS");
		enterCannibalSands = new GOTAchievement(Category.VISIT, id++, GOTBlocks.quicksand, "VISIT_CANNIBAL_SANDS");
		enterColdCoast = new GOTAchievement(Category.VISIT, id++, GOTItems.wildlingPolearm, "VISIT_COLD_COAST");
		enterKingswood = new GOTAchievement(Category.VISIT, id++, GOTItems.mugRedWine, "VISIT_CROWNLANDS_FOREST");
		enterDorne = new GOTAchievement(Category.VISIT, id++, GOTItems.dorneHelmet, "VISIT_DORNE");
		enterDorneDesert = new GOTAchievement(Category.VISIT, id++, Blocks.sand, "VISIT_DORNE_DESERT");
		enterDorneMesa = new GOTAchievement(Category.VISIT, id++, Blocks.clay, "VISIT_DORNE_MESA");
		enterDorneMountains = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.rock, 1, 4), "VISIT_DORNE_MOUNTAINS");
		enterDoshKhalin = new GOTAchievement(Category.VISIT, id++, Blocks.stone, "VISIT_DOSH_KHALIN");
		enterDothrakiSea = new GOTAchievement(Category.VISIT, id++, GOTItems.nomadSword, "VISIT_DOTHRAKI_SEA");
		enterDragonstone = new GOTAchievement(Category.VISIT, id++, GOTItems.obsidianShard, "VISIT_DRAGONSTONE");
		enterEssosMountains = new GOTAchievement(Category.VISIT, id++, Blocks.stone, "VISIT_ESSOS_MOUNTAINS");
		enterIsleOfFaces = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.leaves9, 1, 2), "VISIT_FACES");
		enterSnowyWasteland = new GOTAchievement(Category.VISIT, id++, Blocks.snow, "VISIT_FAR_NORTH");
		enterFireField = new GOTAchievement(Category.VISIT, id++, Blocks.red_flower, "VISIT_FIRE_FIELD");
		enterFrostfangs = new GOTAchievement(Category.VISIT, id++, Blocks.packed_ice, "VISIT_FROSTFANGS");
		enterGhiscar = new GOTAchievement(Category.VISIT, id++, GOTItems.harpy, "VISIT_GHISCAR");
		enterGhiscarColony = new GOTAchievement(Category.VISIT, id++, GOTItems.ghiscarHelmet, "VISIT_GHISCAR_COLONY");
		enterGiftNew = new GOTAchievement(Category.VISIT, id++, Blocks.snow, "VISIT_GIFT_NEW");
		enterGiftOld = new GOTAchievement(Category.VISIT, id++, GOTBlocks.brickIce, "VISIT_GIFT_OLD");
		enterHauntedForest = new GOTAchievement(Category.VISIT, id++, GOTItems.club, "VISIT_HAUNTED_FOREST");
		enterMoonMountainsFoothills = new GOTAchievement(Category.VISIT, id++, GOTItems.trident, "VISIT_HILL_TRIBES");
		enterIbben = new GOTAchievement(Category.VISIT, id++, GOTItems.flintDagger, "VISIT_IBBEN");
		enterIfekevronForest = new GOTAchievement(Category.VISIT, id++, GOTItems.flintSpear, "VISIT_IFEKEVRON");
		enterIronIslands = new GOTAchievement(Category.VISIT, id++, GOTItems.ironbornHelmet, "VISIT_IRONBORN");
		enterIrontreeForest = new GOTAchievement(Category.VISIT, id++, Items.iron_axe, "VISIT_IRONTREE");
		enterJogosNhai = new GOTAchievement(Category.VISIT, id++, GOTItems.nomadBow, "VISIT_JOGOS");
		enterJogosNhaiDesert = new GOTAchievement(Category.VISIT, id++, GOTBlocks.quicksand, "VISIT_JOGOS_DESERT");
		enterKingsLanding = new GOTAchievement(Category.VISIT, id++, GOTItems.crownlandsHelmet, "VISIT_KINGS_LANDING");
		enterLhazar = new GOTAchievement(Category.VISIT, id++, GOTItems.lhazarHelmet, "VISIT_LHAZAR");
		enterLongSummer = new GOTAchievement(Category.VISIT, id++, Blocks.fire, "VISIT_LONG_SUMMER");
		enterLorath = new GOTAchievement(Category.VISIT, id++, GOTItems.lorathHelmet, "VISIT_LORATH");
		enterLys = new GOTAchievement(Category.VISIT, id++, GOTItems.lysHelmet, "VISIT_LYS");
		enterDisputedLands = new GOTAchievement(Category.VISIT, id++, GOTItems.goldHelmet, "VISIT_MERCENARY");
		enterMossovy = new GOTAchievement(Category.VISIT, id++, GOTItems.mossovySword, "VISIT_MOSSOVY");
		enterMossovyMarshes = new GOTAchievement(Category.VISIT, id++, GOTBlocks.reeds, "VISIT_MOSSOVY_MARSHES");
		enterMossovyMountains = new GOTAchievement(Category.VISIT, id++, Blocks.stone, "VISIT_MOSSOVY_SOPKAS");
		enterMyr = new GOTAchievement(Category.VISIT, id++, GOTItems.myrHelmet, "VISIT_MYR");
		enterNaath = new GOTAchievement(Category.VISIT, id++, GOTItems.summerHelmet, "VISIT_NAATH");
		enterNeck = new GOTAchievement(Category.VISIT, id++, GOTBlocks.quagmire, "VISIT_NECK");
		enterNorth = new GOTAchievement(Category.VISIT, id++, GOTItems.northHelmet, "VISIT_NORTH");
		enterNorthBarrows = new GOTAchievement(Category.VISIT, id++, GOTItems.coin, "VISIT_NORTH_BARROWS");
		enterNorthMountains = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.rock, 1, 1), "VISIT_NORTH_MOUNTAINS");
		enterNorthTown = new GOTAchievement(Category.VISIT, id++, GOTItems.northguardHelmet, "VISIT_NORTH_TOWN");
		enterNorthWild = new GOTAchievement(Category.VISIT, id++, GOTItems.trident, "VISIT_NORTH_WILD");
		enterNorvos = new GOTAchievement(Category.VISIT, id++, GOTItems.norvosHelmet, "VISIT_NORVOS");
		enterPentos = new GOTAchievement(Category.VISIT, id++, GOTItems.pentosHelmet, "VISIT_PENTOS");
		enterQarth = new GOTAchievement(Category.VISIT, id++, GOTItems.qarthHelmet, "VISIT_QARTH");
		enterQarthDesert = new GOTAchievement(Category.VISIT, id++, GOTBlocks.redSandstone, "VISIT_DORNE_DESERT");
		enterQohor = new GOTAchievement(Category.VISIT, id++, GOTItems.qohorHelmet, "VISIT_QOHOR");
		enterQohorForest = new GOTAchievement(Category.VISIT, id++, GOTItems.qohorHelmet, "VISIT_QOHOR_FOREST");
		enterReach = new GOTAchievement(Category.VISIT, id++, GOTItems.reachHelmet, "VISIT_REACH");
		enterReachTown = new GOTAchievement(Category.VISIT, id++, GOTItems.reachguardHelmet, "VISIT_REACH_TOWN");
		enterBleedingSea = new GOTAchievement(Category.VISIT, id++, GOTItems.bronzeSword, "VISIT_RED_SEA");
		enterRiverlands = new GOTAchievement(Category.VISIT, id++, GOTItems.riverlandsHelmet, "VISIT_RIVERLANDS");
		enterShadowLand = new GOTAchievement(Category.VISIT, id++, GOTBlocks.asshaiFlower, "VISIT_SHADOW_LAND");
		enterShadowMountains = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.rock, 1, 0), "VISIT_SHADOW_MOUNTAINS");
		enterSkagos = new GOTAchievement(Category.VISIT, id++, GOTItems.trident, "VISIT_SKAGOS");
		enterSkirlingPass = new GOTAchievement(Category.VISIT, id++, GOTItems.club, "VISIT_SKIRLING_PASS");
		enterSothoryosBushland = new GOTAchievement(Category.VISIT, id++, GOTItems.termite, "VISIT_SOTHORYOS_BUSHLAND");
		enterSothoryosDesert = new GOTAchievement(Category.VISIT, id++, Blocks.sand, "VISIT_SOTHORYOS_DESERT");
		enterSothoryosFrost = new GOTAchievement(Category.VISIT, id++, Blocks.packed_ice, "VISIT_SOTHORYOS_FROST");
		enterSothoryosHell = new GOTAchievement(Category.VISIT, id++, GOTItems.sothoryosAmulet, "VISIT_SOTHORYOS_HELL");
		enterSothoryosJungle = new GOTAchievement(Category.VISIT, id++, GOTItems.sothoryosHelmet, "VISIT_SOTHORYOS_JUNGLE");
		enterSothoryosForest = new GOTAchievement(Category.VISIT, id++, GOTItems.sothoryosHelmetGold, "VISIT_SOTHORYOS_KANUKA");
		enterSothoryosMangrove = new GOTAchievement(Category.VISIT, id++, Blocks.water, "VISIT_SOTHORYOS_MANGROVE");
		enterSothoryosMountains = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.rock, 1, 6), "VISIT_SOTHORYOS_MOUNTAINS");
		enterSothoryosSavannah = new GOTAchievement(Category.VISIT, id++, Blocks.grass, "VISIT_SOTHORYOS_SAVANNAH");
		enterSothoryosTaiga = new GOTAchievement(Category.VISIT, id++, GOTItems.club, "VISIT_SOTHORYOS_TAIGA");
		enterStepstones = new GOTAchievement(Category.VISIT, id++, GOTItems.essosBow, "VISIT_STEPSTONES");
		enterStoneCoast = new GOTAchievement(Category.VISIT, id++, GOTItems.westerosDaggerPoisoned, "VISIT_STONE_COAST");
		enterStormlands = new GOTAchievement(Category.VISIT, id++, GOTItems.stormlandsHelmet, "VISIT_STORMLANDS");
		enterSummerIslands = new GOTAchievement(Category.VISIT, id++, GOTItems.summerHelmet, "VISIT_SUMMER_ISLANDS");
		enterTarth = new GOTAchievement(Category.VISIT, id++, GOTBlocks.whiteSandstone, "VISIT_TARTH");
		enterThennLand = new GOTAchievement(Category.VISIT, id++, GOTItems.wildlingBattleaxe, "VISIT_THENN");
		enterTropicalForest = new GOTAchievement(Category.VISIT, id++, GOTItems.yitiHelmetShogune, "VISIT_TROPICAL_FOREST");
		enterTyrosh = new GOTAchievement(Category.VISIT, id++, GOTItems.tyroshHelmet, "VISIT_TYROSH");
		enterUlthos = new GOTAchievement(Category.VISIT, id++, Blocks.web, "VISIT_ULTHOS");
		enterUlthosDesert = new GOTAchievement(Category.VISIT, id++, Blocks.sand, "VISIT_ULTHOS_DESERT");
		enterUlthosMountains = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.rock, 1, 3), "VISIT_ULTHOS_MOUNTAINS");
		enterValyria = new GOTAchievement(Category.VISIT, id++, Blocks.vine, "VISIT_VALYRIA");
		enterShrykesLand = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.deadMarshPlant), "VISIT_SHRYKES_LAND");
		enterValyriaVolcano = new GOTAchievement(Category.VISIT, id++, Blocks.lava, "VISIT_VALYRIA_VOLCANO");
		enterVolantis = new GOTAchievement(Category.VISIT, id++, GOTItems.volantisHelmet, "VISIT_VOLANTIS");
		enterVolantisOrangeForest = new GOTAchievement(Category.VISIT, id++, GOTItems.orange, "VISIT_VOLANTIS_FOREST");
		enterWesterlands = new GOTAchievement(Category.VISIT, id++, GOTItems.westerlandsHelmet, "VISIT_WESTERLANDS");
		enterWesterlandsHills = new GOTAchievement(Category.VISIT, id++, Blocks.gold_ore, "VISIT_WESTERLANDS_HILLS");
		enterWesterlandsTown = new GOTAchievement(Category.VISIT, id++, GOTItems.westerlandsguardHelmet, "VISIT_WESTERLANDS_TOWN");
		enterWhisperingWood = new GOTAchievement(Category.VISIT, id++, GOTItems.westerosPike, "VISIT_WHISPERING_WOOD");
		enterYeen = new GOTAchievement(Category.VISIT, id++, Blocks.obsidian, "VISIT_YEEN");
		enterYiTi = new GOTAchievement(Category.VISIT, id++, GOTItems.yitiHelmet, "VISIT_YI_TI");
		enterYiTiWasteland = new GOTAchievement(Category.VISIT, id++, GOTItems.yitiHelmetSamurai, "VISIT_YI_TI_WASTELAND");
		enterCrownlands = new GOTAchievement(Category.VISIT, id++, GOTItems.crownlandsHelmet, "VISIT_CROWNLANDS");
		enterQarthColony = new GOTAchievement(Category.VISIT, id++, GOTItems.qarthHelmet, "VISIT_QARTH_COLONY");
		enterUlthosDesertCold = new GOTAchievement(Category.VISIT, id++, new ItemStack(Blocks.sand), "VISIT_ULTHOS_DESERT_COLD");
		enterUlthosForest = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.wood1, 1, 2), "VISIT_ULTHOS_FOREST");
		enterUlthosFrost = new GOTAchievement(Category.VISIT, id++, new ItemStack(Blocks.snow), "VISIT_ULTHOS_FROST");
		enterUlthosTaiga = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.wood1, 1, 0), "VISIT_ULTHOS_TAIGA");
		enterSummerColony = new GOTAchievement(Category.VISIT, id++, GOTItems.summerHelmet, "VISIT_SUMMER_COLONY");
		enterUlthosMarshes = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.deadMarshPlant), "VISIT_ULTHOS_MARSHES");
		enterUlthosRedForest = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTBlocks.wood1), "VISIT_ULTHOS_RED_FOREST");
		wearFullArryn = createArmorAchievement(Category.WEAR, id++, GOTItems.arrynChestplate, "WEAR_FULL_ARRYN");
		wearFullArrynguard = createArmorAchievement(Category.WEAR, id++, GOTItems.arrynguardChestplate, "WEAR_FULL_ARRYNGUARD");
		wearFullAsshai = createArmorAchievement(Category.WEAR, id++, GOTItems.asshaiChestplate, "WEAR_FULL_ASSHAI");
		wearFullBlackfyre = createArmorAchievement(Category.WEAR, id++, GOTItems.blackfyreChestplate, "WEAR_FULL_BLACKFYRE");
		wearFullBone = createArmorAchievement(Category.WEAR, id++, GOTItems.boneChestplate, "WEAR_FULL_BONE");
		wearFullBraavos = createArmorAchievement(Category.WEAR, id++, GOTItems.braavosChestplate, "WEAR_FULL_BRAAVOS");
		wearFullBronze = createArmorAchievement(Category.WEAR, id++, GOTItems.bronzeChestplate, "WEAR_FULL_BRONZE");
		wearFullCrownlands = createArmorAchievement(Category.WEAR, id++, GOTItems.crownlandsChestplate, "WEAR_FULL_CROWNLANDS");
		wearFullDorne = createArmorAchievement(Category.WEAR, id++, GOTItems.dorneChestplate, "WEAR_FULL_DORNE");
		wearFullDragonstone = createArmorAchievement(Category.WEAR, id++, GOTItems.dragonstoneChestplate, "WEAR_FULL_DRAGONSTONE");
		wearFullFur = createArmorAchievement(Category.WEAR, id++, GOTItems.furChestplate, "WEAR_FULL_FUR");
		wearFullGemsbok = createArmorAchievement(Category.WEAR, id++, GOTItems.gemsbokChestplate, "WEAR_FULL_GEMSBOK");
		wearFullGhiscar = createArmorAchievement(Category.WEAR, id++, GOTItems.ghiscarChestplate, "WEAR_FULL_GHISCAR");
		wearFullGift = createArmorAchievement(Category.WEAR, id++, GOTItems.giftChestplate, "WEAR_FULL_GIFT");
		wearFullGoldencompany = createArmorAchievement(Category.WEAR, id++, GOTItems.goldChestplate, "WEAR_FULL_GOLDENCOMPANY");
		wearFullHand = createArmorAchievement(Category.WEAR, id++, GOTItems.handGold, "WEAR_FULL_HAND");
		wearFullHelmet = createArmorAchievement(Category.WEAR, id++, GOTItems.robertHelmet, "WEAR_FULL_HELMET");
		wearFullHillmen = createArmorAchievement(Category.WEAR, id++, GOTItems.hillmenChestplate, "WEAR_FULL_HILLMEN");
		wearFullIronborn = createArmorAchievement(Category.WEAR, id++, GOTItems.ironbornChestplate, "WEAR_FULL_IRONBORN");
		wearFullKingsguard = createArmorAchievement(Category.WEAR, id++, GOTItems.kingsguardChestplate, "WEAR_FULL_KINGSGUARD");
		wearFullLhazar = createArmorAchievement(Category.WEAR, id++, GOTItems.lhazarChestplate, "WEAR_FULL_LHAZAR");
		wearFullLhazarLion = createArmorAchievement(Category.WEAR, id++, GOTItems.lhazarChestplateLion, "WEAR_FULL_LHAZAR_LION");
		wearFullLorath = createArmorAchievement(Category.WEAR, id++, GOTItems.lorathChestplate, "WEAR_FULL_LORATH");
		wearFullLys = createArmorAchievement(Category.WEAR, id++, GOTItems.lysChestplate, "WEAR_FULL_LYS");
		wearFullMossovy = createArmorAchievement(Category.WEAR, id++, GOTItems.mossovyChestplate, "WEAR_FULL_MOSSOVY");
		wearFullMyr = createArmorAchievement(Category.WEAR, id++, GOTItems.myrChestplate, "WEAR_FULL_MYR");
		wearFullNorth = createArmorAchievement(Category.WEAR, id++, GOTItems.northChestplate, "WEAR_FULL_NORTH");
		wearFullNorthguard = createArmorAchievement(Category.WEAR, id++, GOTItems.northguardChestplate, "WEAR_FULL_NORTHGUARD");
		wearFullNorvos = createArmorAchievement(Category.WEAR, id++, GOTItems.norvosChestplate, "WEAR_FULL_NORVOS");
		wearFullPentos = createArmorAchievement(Category.WEAR, id++, GOTItems.pentosChestplate, "WEAR_FULL_PENTOS");
		wearFullQarth = createArmorAchievement(Category.WEAR, id++, GOTItems.qarthChestplate, "WEAR_FULL_QARTH");
		wearFullQohor = createArmorAchievement(Category.WEAR, id++, GOTItems.qohorChestplate, "WEAR_FULL_QOHOR");
		wearFullReach = createArmorAchievement(Category.WEAR, id++, GOTItems.reachChestplate, "WEAR_FULL_REACH");
		wearFullReachguard = createArmorAchievement(Category.WEAR, id++, GOTItems.reachguardChestplate, "WEAR_FULL_REACHGUARD");
		wearFullRedking = createArmorAchievement(Category.WEAR, id++, GOTItems.redkingChestplate, "WEAR_FULL_REDKING");
		wearFullRiverlands = createArmorAchievement(Category.WEAR, id++, GOTItems.riverlandsChestplate, "WEAR_FULL_RIVERLANDS");
		wearFullRobes = createArmorAchievement(Category.WEAR, id++, GOTItems.robesChestplate, "WEAR_FULL_ROBES");
		wearFullRoyce = createArmorAchievement(Category.WEAR, id++, GOTItems.royceChestplate, "WEAR_FULL_ROYCE");
		wearFullSothoryos = createArmorAchievement(Category.WEAR, id++, GOTItems.sothoryosChestplate, "WEAR_FULL_SOTHORYOS");
		wearFullSothoryosGold = createArmorAchievement(Category.WEAR, id++, GOTItems.sothoryosChestplateGold, "WEAR_FULL_SOTHORYOS_GOLD");
		wearFullStormlands = createArmorAchievement(Category.WEAR, id++, GOTItems.stormlandsChestplate, "WEAR_FULL_STORMLANDS");
		wearFullSummer = createArmorAchievement(Category.WEAR, id++, GOTItems.summerChestplate, "WEAR_FULL_SUMMER");
		wearFullTargaryen = createArmorAchievement(Category.WEAR, id++, GOTItems.targaryenChestplate, "WEAR_FULL_TARGARYEN");
		wearFullTyrosh = createArmorAchievement(Category.WEAR, id++, GOTItems.tyroshChestplate, "WEAR_FULL_TYROSH");
		wearFullUnsullied = createArmorAchievement(Category.WEAR, id++, GOTItems.unsulliedChestplate, "WEAR_FULL_UNSULLIED");
		wearFullValyrian = createArmorAchievement(Category.WEAR, id++, GOTItems.valyrianChestplate, "WEAR_FULL_VALYRIAN");
		wearFullVolantis = createArmorAchievement(Category.WEAR, id++, GOTItems.volantisChestplate, "WEAR_FULL_VOLANTIS");
		wearFullWesterlands = createArmorAchievement(Category.WEAR, id++, GOTItems.westerlandsChestplate, "WEAR_FULL_WESTERLANDS");
		wearFullWesterlandsguard = createArmorAchievement(Category.WEAR, id++, GOTItems.westerlandsguardChestplate, "WEAR_FULL_WESTERLANDSGUARD");
		wearFullWestking = createArmorAchievement(Category.WEAR, id++, GOTItems.westkingChestplate, "WEAR_FULL_WESTKING");
		wearFullWhitewalkers = createArmorAchievement(Category.WEAR, id++, GOTItems.whiteWalkersChestplate, "WEAR_FULL_WHITEWALKERS");
		wearFullYiti = createArmorAchievement(Category.WEAR, id++, GOTItems.yitiChestplate, "WEAR_FULL_YITI");
		wearFullYitiSamurai = createArmorAchievement(Category.WEAR, id++, GOTItems.yitiChestplateSamurai, "WEAR_FULL_YITI_SAMURAI");
		wearFullYitiFrontier = createArmorAchievement(Category.WEAR, id++, GOTItems.yitiChestplateFrontier, "WEAR_FULL_YITI_FRONTIER");
		wearFullDothraki = createArmorAchievement(Category.WEAR, id++, GOTItems.dothrakiChestplate, "WEAR_FULL_DOTHRAKI");
		wearFullJogos = createArmorAchievement(Category.WEAR, id++, GOTItems.jogosChestplate, "WEAR_FULL_JOGOS");

		catchButterfly = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.butterflyJar, "CATCH_BUTTERFLY");
		collectCraftingTables = new GOTAchievement(Category.GENERAL, id++, Blocks.crafting_table, "COLLECT_CRAFTING_TABLES");
		collectCrossbowBolts = new GOTAchievement(Category.GENERAL, id++, GOTItems.crossbowBolt, "COLLECT_CROSSBOW_BOLTS");
		combineSmithScrolls = new GOTAchievement(Category.GENERAL, id++, GOTItems.smithScroll, "COMBINE_SMITH_SCROLLS");
		cookKebab = new GOTAchievement(Category.GENERAL, id++, GOTItems.kebab, "COOK_KEBAB");
		craftSaddle = new GOTAchievement(Category.GENERAL, id++, Items.saddle, "CRAFT_SADDLE");
		doMiniquestHunter = new GOTAchievement(Category.GENERAL, id++, GOTItems.questBook, "DO_MINIQUEST_HUNTER");
		doMiniquestHunter5 = new GOTAchievement(Category.GENERAL, id++, GOTItems.bountyTrophy, "DO_MINIQUEST_HUNTER5");
		drinkPlantainBrew = new GOTAchievement(Category.GENERAL, id++, GOTItems.mugPlantainBrew, "DRINK_PLANTAIN_BREW");
		drinkSkull = new GOTAchievement(Category.GENERAL, id++, GOTItems.skullCup, "DRINK_SKULL");
		findPlantain = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.plantain, "FIND_PLANTAIN");
		fishRing = new GOTAchievement(Category.GENERAL, id++, Items.fishing_rod, "FISH_RING");
		getDrunk = new GOTAchievement(Category.GENERAL, id++, GOTItems.mugAle, "GET_DRUNK");
		growBaobab = new GOTAchievement(Category.GENERAL, id++, new ItemStack(GOTBlocks.sapling4, 1, 1), "GROW_BAOBAB");
		killBombardier = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.bomb, "KILL_BOMBARDIER");
		killButterfly = new GOTAchievement(Category.GENERAL, id++, Items.iron_sword, "KILL_BUTTERFLY");
		killHuntingPlayer = new GOTAchievement(Category.GENERAL, id++, Items.iron_sword, "KILL_HUNTING_PLAYER");
		killLargeMobWithSlingshot = new GOTAchievement(Category.GENERAL, id++, GOTItems.sling, "KILL_LARGE_MOB_WITH_SLINGSHOT");
		killThievingBandit = new GOTAchievement(Category.GENERAL, id++, GOTItems.leatherHat, "KILL_THIEVING_BANDIT");
		killUsingOnlyPlates = new GOTAchievement(Category.GENERAL, id++, GOTItems.plate, "KILL_USING_ONLY_PLATES");
		killWhileDrunk = new GOTAchievement(Category.GENERAL, id++, GOTItems.mugAle, "KILL_WHILE_DRUNK");
		lightBeacon = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.beacon, "LIGHT_BEACON");
		marry = new GOTAchievement(Category.GENERAL, id++, GOTItems.goldRing, "MARRY");
		mineGlowstone = new GOTAchievement(Category.GENERAL, id++, GOTBlocks.oreGlowstone, "MINE_GLOWSTONE");
		pickpocket = new GOTAchievement(Category.GENERAL, id++, GOTMiniQuestPickpocket.createPickpocketIcon(), "PICKPOCKET");
		rideCamel = new GOTAchievement(Category.GENERAL, id++, Items.saddle, "RIDE_CAMEL");
		smeltObsidianShard = new GOTAchievement(Category.GENERAL, id++, GOTItems.obsidianShard, "SMELT_OBSIDIAN_SHARD");
		speakToDrunkard = new GOTAchievement(Category.GENERAL, id++, GOTItems.mugAle, "SPEAK_TO_DRUNKARD");
		useCrossbow = new GOTAchievement(Category.GENERAL, id++, GOTItems.ironCrossbow, "USE_CROSSBOW");
		useSpearFromFar = new GOTAchievement(Category.GENERAL, id++, GOTItems.ironSpear, "USE_SPEAR_FROM_FAR");
		useThrowingAxe = new GOTAchievement(Category.GENERAL, id++, GOTItems.ironThrowingAxe, "USE_THROWING_AXE");
	}

	public static Comparator<GOTAchievement> sortForDisplay(EntityPlayer entityplayer) {
		return (ach1, ach2) -> {
			if (ach1.isSpecial) {
				if (!ach2.isSpecial) {
					return -1;
				}
				return Integer.compare(ach1.ID, ach2.ID);
			} else if (ach2.isSpecial) {
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
		ChatComponentTranslation msg = new ChatComponentTranslation("got.chat.achievement", entityplayer.func_145748_c_(), earnName);
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(msg);
	}

	public boolean canPlayerEarn(EntityPlayer entityplayer) {
		float alignment;
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		if (!enemyFactions.isEmpty()) {
			boolean anyEnemies = false;
			for (GOTFaction f : enemyFactions) {
				alignment = playerData.getAlignment(f);
				if (alignment > 0.0f) {
					continue;
				}
				anyEnemies = true;
			}
			if (!anyEnemies) {
				return false;
			}
		}
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

	public GOTAchievement createTitle() {
		return createTitle(null);
	}

	public GOTAchievement createTitle(String s) {
		if (achievementTitle != null) {
			throw new IllegalArgumentException("GOT achievement " + name + " already has an associated title!");
		}
		achievementTitle = new GOTTitle(s, this);
		return this;
	}

	public IChatComponent getAchievementChatComponent(EntityPlayer entityplayer) {
		ChatComponentTranslation component = new ChatComponentTranslation(getUntranslatedTitle(entityplayer)).createCopy();
		component.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		component.getChatStyle().setChatHoverEvent(new HoverEvent(GOTChatEvents.SHOW_GOT_ACHIEVEMENT, new ChatComponentText(category.name() + "$" + ID)));
		return component;
	}

	public IChatComponent getChatComponentForEarn(EntityPlayer entityplayer) {
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
		return category.dimension;
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

	public GOTAchievement setRequiresAnyEnemy(List<GOTFaction> f) {
		return setRequiresEnemy(f.toArray(new GOTFaction[0]));
	}

	public GOTAchievement setRequiresEnemy(GOTFaction... f) {
		enemyFactions.addAll(Arrays.asList(f));
		return this;
	}

	public void setSpecial() {
		isSpecial = true;
	}

	public enum Category {
		TITLES(GOTFaction.NIGHT_WATCH), GENERAL(GOTFaction.SOTHORYOS), KILL(GOTFaction.WESTERLANDS), WEAR(GOTFaction.ARRYN), VISIT(GOTFaction.WHITE_WALKER), LEGENDARY(GOTFaction.YI_TI);

		public String codeName;
		public float[] categoryColors;
		public GOTDimension dimension;
		public List<GOTAchievement> list = new ArrayList<>();
		public int nextRankAchID = 1000;

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

		public String codeName() {
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
	}

}
