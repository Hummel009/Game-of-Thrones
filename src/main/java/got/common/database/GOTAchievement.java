package got.common.database;

import got.common.GOTChatEvents;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
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

@SuppressWarnings({"WeakerAccess", "PublicField", "unused"})
public class GOTAchievement {
	public static final Collection<GOTAchievement> CONTENT = new ArrayList<>();

	public static final Map<ItemArmor.ArmorMaterial, GOTAchievement> ARMOR_ACHIEVEMENTS = new EnumMap<>(ItemArmor.ArmorMaterial.class);

	public static GOTAchievement combineSmithScrolls;
	public static GOTAchievement craftCargocart;
	public static GOTAchievement craftConcretePowder;
	public static GOTAchievement craftPlowcart;
	public static GOTAchievement craftPouch;
	public static GOTAchievement craftSaddle;
	public static GOTAchievement craftWildFire;
	public static GOTAchievement defeatInvasion;
	public static GOTAchievement doMiniquest;
	public static GOTAchievement doMiniquestHunter5;
	public static GOTAchievement doMiniquestHunter;
	public static GOTAchievement doMiniquestLegendary;
	public static GOTAchievement drinkPlantainBrew;
	public static GOTAchievement drinkPoppyMilk;
	public static GOTAchievement drinkShadeEvening;
	public static GOTAchievement drinkTermiteTequila;
	public static GOTAchievement drinkUnsulliedTonic;
	public static GOTAchievement drinkWildFire;
	public static GOTAchievement earnManyCoins;
	public static GOTAchievement engraveOwnership;
	public static GOTAchievement enterAlwaysWinter;
	public static GOTAchievement enterArryn;
	public static GOTAchievement enterArrynMountains;
	public static GOTAchievement enterArrynMountainsFoothills;
	public static GOTAchievement enterArrynTown;
	public static GOTAchievement enterBleedingSea;
	public static GOTAchievement enterBoneMountains;
	public static GOTAchievement enterBraavos;
	public static GOTAchievement enterCannibalSands;
	public static GOTAchievement enterFrozenShore;
	public static GOTAchievement enterCrownlands;
	public static GOTAchievement enterCrownlandsTown;
	public static GOTAchievement enterDisputedLands;
	public static GOTAchievement enterDorne;
	public static GOTAchievement enterDorneDesert;
	public static GOTAchievement enterDorneMesa;
	public static GOTAchievement enterDorneMountains;
	public static GOTAchievement enterDothrakiSea;
	public static GOTAchievement enterDragonstone;
	public static GOTAchievement enterEssosMountains;
	public static GOTAchievement enterFrostfangs;
	public static GOTAchievement enterGhiscar;
	public static GOTAchievement enterGhiscarAstapor;
	public static GOTAchievement enterGhiscarColony;
	public static GOTAchievement enterGhiscarMeereen;
	public static GOTAchievement enterGhiscarNewGhis;
	public static GOTAchievement enterGhiscarYunkai;
	public static GOTAchievement enterGiftNew;
	public static GOTAchievement enterGiftOld;
	public static GOTAchievement enterHauntedForest;
	public static GOTAchievement enterIbben;
	public static GOTAchievement enterIbbenColony;
	public static GOTAchievement enterIbbenMountains;
	public static GOTAchievement enterIbbenTaiga;
	public static GOTAchievement enterIfekevronForest;
	public static GOTAchievement enterIronIslands;
	public static GOTAchievement enterIsleOfFaces;
	public static GOTAchievement enterJogosNhai;
	public static GOTAchievement enterJogosNhaiDesert;
	public static GOTAchievement enterKingswood;
	public static GOTAchievement enterKnownWorld;
	public static GOTAchievement enterLhazar;
	public static GOTAchievement enterLongSummer;
	public static GOTAchievement enterLorath;
	public static GOTAchievement enterLorathMaze;
	public static GOTAchievement enterLys;
	public static GOTAchievement enterMossovy;
	public static GOTAchievement enterMossovyMarshes;
	public static GOTAchievement enterMossovyMountains;
	public static GOTAchievement enterMossovyTaiga;
	public static GOTAchievement enterMyr;
	public static GOTAchievement enterNeck;
	public static GOTAchievement enterNorth;
	public static GOTAchievement enterNorthBarrows;
	public static GOTAchievement enterNorthForestIrontree;
	public static GOTAchievement enterNorthMountains;
	public static GOTAchievement enterNorthTown;
	public static GOTAchievement enterNorthWild;
	public static GOTAchievement enterNorvos;
	public static GOTAchievement enterPentos;
	public static GOTAchievement enterQarth;
	public static GOTAchievement enterQarthColony;
	public static GOTAchievement enterQarthDesert;
	public static GOTAchievement enterQohor;
	public static GOTAchievement enterReach;
	public static GOTAchievement enterReachArbor;
	public static GOTAchievement enterReachFireField;
	public static GOTAchievement enterReachTown;
	public static GOTAchievement enterRiverlands;
	public static GOTAchievement enterShadowLand;
	public static GOTAchievement enterShadowMountains;
	public static GOTAchievement enterShadowTown;
	public static GOTAchievement enterShrykesLand;
	public static GOTAchievement enterSkagos;
	public static GOTAchievement enterSothoryosBushland;
	public static GOTAchievement enterSothoryosDesert;
	public static GOTAchievement enterSothoryosDesertCold;
	public static GOTAchievement enterSothoryosForest;
	public static GOTAchievement enterSothoryosFrost;
	public static GOTAchievement enterSothoryosHell;
	public static GOTAchievement enterSothoryosJungle;
	public static GOTAchievement enterSothoryosMangrove;
	public static GOTAchievement enterSothoryosMountains;
	public static GOTAchievement enterSothoryosSavannah;
	public static GOTAchievement enterSothoryosTaiga;
	public static GOTAchievement enterStepstones;
	public static GOTAchievement enterStoneyShore;
	public static GOTAchievement enterStormlands;
	public static GOTAchievement enterStormlandsTarth;
	public static GOTAchievement enterSummerColony;
	public static GOTAchievement enterSummerIslands;
	public static GOTAchievement enterThennLand;
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
	public static GOTAchievement enterWesterlands;
	public static GOTAchievement enterWesterlandsTown;
	public static GOTAchievement enterWesterosFrost;
	public static GOTAchievement enterYeen;
	public static GOTAchievement enterYiTi;
	public static GOTAchievement enterYiTiBorderZone;
	public static GOTAchievement factionConquest;
	public static GOTAchievement findFourLeafClover;
	public static GOTAchievement fishRing;
	public static GOTAchievement freeman;
	public static GOTAchievement gainHighAlcoholTolerance;
	public static GOTAchievement getBronze;
	public static GOTAchievement getDrunk;
	public static GOTAchievement getIce;
	public static GOTAchievement getValyrianSteel;
	public static GOTAchievement hireGoldenCompany;
	public static GOTAchievement hundreds;
	public static GOTAchievement killAeronGreyjoy;
	public static GOTAchievement killAlliserThorne;
	public static GOTAchievement killAmoryLorch;
	public static GOTAchievement killAreoHotah;
	public static GOTAchievement killAryaStark;
	public static GOTAchievement killAsshaiArchmag;
	public static GOTAchievement killAsshaiShadowbinder;
	public static GOTAchievement killAsshaiSpherebinder;
	public static GOTAchievement killBalonGreyjoy;
	public static GOTAchievement killBarristanSelmy;
	public static GOTAchievement killBarrowWight;
	public static GOTAchievement killBear;
	public static GOTAchievement killBeaver;
	public static GOTAchievement killBenjenStark;
	public static GOTAchievement killBericDondarrion;
	public static GOTAchievement killBlizzard;
	public static GOTAchievement killBranStark;
	public static GOTAchievement killBrienneTarth;
	public static GOTAchievement killBronn;
	public static GOTAchievement killBryndenTully;
	public static GOTAchievement killBuGai;
	public static GOTAchievement killCatelynStark;
	public static GOTAchievement killCerseiLannister;
	public static GOTAchievement killCraster;
	public static GOTAchievement killDaarioNaharis;
	public static GOTAchievement killDaenerysTargaryen;
	public static GOTAchievement killDavosSeaworth;
	public static GOTAchievement killDoranMartell;
	public static GOTAchievement killDragon;
	public static GOTAchievement killEdmureTully;
	public static GOTAchievement killElephant;
	public static GOTAchievement killEllaryaSand;
	public static GOTAchievement killEuronGreyjoy;
	public static GOTAchievement killGarlanTyrell;
	public static GOTAchievement killGendryBaratheon;
	public static GOTAchievement killGhiscarGladiator;
	public static GOTAchievement killGhiscarHarpy;
	public static GOTAchievement killGiant;
	public static GOTAchievement killGregorClegane;
	public static GOTAchievement killGreyWorm;
	public static GOTAchievement killHarryStrickland;
	public static GOTAchievement killHighSepton;
	public static GOTAchievement killHizdahrZoLoraq;
	public static GOTAchievement killHodor;
	public static GOTAchievement killHosterTully;
	public static GOTAchievement killHowlandReed;
	public static GOTAchievement killHuntingPlayer;
	public static GOTAchievement killIceSpider;
	public static GOTAchievement killIfekevron;
	public static GOTAchievement killIllyrioMopatis;
	public static GOTAchievement killIlynPayne;
	public static GOTAchievement killJaimeLannister;
	public static GOTAchievement killJanosSlynt;
	public static GOTAchievement killJaqenHghar;
	public static GOTAchievement killJeorMormont;
	public static GOTAchievement killJoffreyBaratheon;
	public static GOTAchievement killJohnUmber;
	public static GOTAchievement killJonSnow;
	public static GOTAchievement killJorahMormont;
	public static GOTAchievement killKevanLannister;
	public static GOTAchievement killKingsguard;
	public static GOTAchievement killKraznysMoNakloz;
	public static GOTAchievement killLancelLannister;
	public static GOTAchievement killLargeMobWithSlingshot;
	public static GOTAchievement killLegendaryNPC;
	public static GOTAchievement killLorasTyrell;
	public static GOTAchievement killLuwin;
	public static GOTAchievement killLysaArryn;
	public static GOTAchievement killMaceTyrell;
	public static GOTAchievement killMaester;
	public static GOTAchievement killMammoth;
	public static GOTAchievement killManceRayder;
	public static GOTAchievement killManticore;
	public static GOTAchievement killMargaeryTyrell;
	public static GOTAchievement killMelisandra;
	public static GOTAchievement killMerynTrant;
	public static GOTAchievement killMissandei;
	public static GOTAchievement killMoqorro;
	public static GOTAchievement killMossovyWitcher;
	public static GOTAchievement killMyrcellaBaratheon;
	public static GOTAchievement killNPC;
	public static GOTAchievement killNightKing;
	public static GOTAchievement killOberynMartell;
	public static GOTAchievement killOlennaTyrell;
	public static GOTAchievement killOsha;
	public static GOTAchievement killPetyrBaelish;
	public static GOTAchievement killPodrickPayne;
	public static GOTAchievement killPriest;
	public static GOTAchievement killProstitute;
	public static GOTAchievement killQyburn;
	public static GOTAchievement killRamsayBolton;
	public static GOTAchievement killRandyllTarly;
	public static GOTAchievement killRedScorpion;
	public static GOTAchievement killRenlyBaratheon;
	public static GOTAchievement killRickardKarstark;
	public static GOTAchievement killRobbStark;
	public static GOTAchievement killRobinArryn;
	public static GOTAchievement killRooseBolton;
	public static GOTAchievement killSalladhorSaan;
	public static GOTAchievement killSamwellTarly;
	public static GOTAchievement killSandorClegane;
	public static GOTAchievement killSansaStark;
	public static GOTAchievement killSelyseBaratheon;
	public static GOTAchievement killShae;
	public static GOTAchievement killShireenBaratheon;
	public static GOTAchievement killShryke;
	public static GOTAchievement killStannisBaratheon;
	public static GOTAchievement killStoneMan;
	public static GOTAchievement killTermite;
	public static GOTAchievement killTheonGreyjoy;
	public static GOTAchievement killThievingBandit;
	public static GOTAchievement killThoros;
	public static GOTAchievement killThreeEyedRaven;
	public static GOTAchievement killTobhoMott;
	public static GOTAchievement killTommenBaratheon;
	public static GOTAchievement killTormund;
	public static GOTAchievement killTrystaneMartell;
	public static GOTAchievement killTugarKhan;
	public static GOTAchievement killTychoNestoris;
	public static GOTAchievement killTyrionLannister;
	public static GOTAchievement killTywinLannister;
	public static GOTAchievement killUlthosSpider;
	public static GOTAchievement killUnsullied;
	public static GOTAchievement killUsingOnlyPlates;
	public static GOTAchievement killVarys;
	public static GOTAchievement killVictarionGreyjoy;
	public static GOTAchievement killWalderFrey;
	public static GOTAchievement killWalrus;
	public static GOTAchievement killWerewolf;
	public static GOTAchievement killWhileDrunk;
	public static GOTAchievement killWhiteWalker;
	public static GOTAchievement killWight;
	public static GOTAchievement killWightGiant;
	public static GOTAchievement killWillasTyrell;
	public static GOTAchievement killWymanManderly;
	public static GOTAchievement killWyvern;
	public static GOTAchievement killXaroXhoanDaxos;
	public static GOTAchievement killYaraGreyjoy;
	public static GOTAchievement killYgritte;
	public static GOTAchievement killYiTiBombardier;
	public static GOTAchievement killYohnRoyce;
	public static GOTAchievement killYoren;
	public static GOTAchievement killYoungGriff;
	public static GOTAchievement marry;
	public static GOTAchievement pickpocket;
	public static GOTAchievement pledgeService;
	public static GOTAchievement reforge;
	public static GOTAchievement rideCamel;
	public static GOTAchievement rideDragon;
	public static GOTAchievement rideElephant;
	public static GOTAchievement rideIceSpider;
	public static GOTAchievement rideMammoth;
	public static GOTAchievement rideRhino;
	public static GOTAchievement rideUlthosSpider;
	public static GOTAchievement rideWoolyRhino;
	public static GOTAchievement rideZebra;
	public static GOTAchievement stealArborGrapes;
	public static GOTAchievement trade;
	public static GOTAchievement travel100;
	public static GOTAchievement travel20;
	public static GOTAchievement travel40;
	public static GOTAchievement travel60;
	public static GOTAchievement travel80;
	public static GOTAchievement useAlloyForge;
	public static GOTAchievement useAsshaiArchmagStaff;
	public static GOTAchievement useAsshaiShadowbinderStaff;
	public static GOTAchievement useBanner;
	public static GOTAchievement useBarrel;
	public static GOTAchievement useBeacon;
	public static GOTAchievement useBomb;
	public static GOTAchievement useButterflyJar;
	public static GOTAchievement useCrossbow;
	public static GOTAchievement useFirePot;
	public static GOTAchievement useIronBank;
	public static GOTAchievement useKebabStand;
	public static GOTAchievement useLingeringPotion;
	public static GOTAchievement useMillstone;
	public static GOTAchievement useOven;
	public static GOTAchievement useSarbacane;
	public static GOTAchievement useSpear;
	public static GOTAchievement useTermite;
	public static GOTAchievement useThrowingAxe;
	public static GOTAchievement useUnsmeltery;
	public static GOTAchievement wearFullArryn;
	public static GOTAchievement wearFullArrynguard;
	public static GOTAchievement wearFullAsshai;
	public static GOTAchievement wearFullBone;
	public static GOTAchievement wearFullBraavos;
	public static GOTAchievement wearFullBronze;
	public static GOTAchievement wearFullBronzeChainmail;
	public static GOTAchievement wearFullIron;
	public static GOTAchievement wearFullIronChainmail;
	public static GOTAchievement wearFullCrownlands;
	public static GOTAchievement wearFullDorne;
	public static GOTAchievement wearFullDothraki;
	public static GOTAchievement wearFullDragonstone;
	public static GOTAchievement wearFullFur;
	public static GOTAchievement wearFullGhiscar;
	public static GOTAchievement wearFullGift;
	public static GOTAchievement wearFullGoldenCompany;
	public static GOTAchievement wearFullHillmen;
	public static GOTAchievement wearFullIbben;
	public static GOTAchievement wearFullIronborn;
	public static GOTAchievement wearFullJogosNhai;
	public static GOTAchievement wearFullKingsguard;
	public static GOTAchievement wearFullLhazar;
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
	public static GOTAchievement wearFullRiverlands;
	public static GOTAchievement wearFullRobes;
	public static GOTAchievement wearFullSothoryos;
	public static GOTAchievement wearFullStormlands;
	public static GOTAchievement wearFullSummer;
	public static GOTAchievement wearFullTargaryen;
	public static GOTAchievement wearFullTyrosh;
	public static GOTAchievement wearFullUnsullied;
	public static GOTAchievement wearFullValyrian;
	public static GOTAchievement wearFullValyrianChainmail;
	public static GOTAchievement wearFullVolantis;
	public static GOTAchievement wearFullWesterlands;
	public static GOTAchievement wearFullWesterlandsguard;
	public static GOTAchievement wearFullWhiteWalkers;
	public static GOTAchievement wearFullYiti;
	public static GOTAchievement wearFullYitiBombardier;
	public static GOTAchievement wearFullYitiSamurai;
	public static GOTAchievement brandEntity;
	public static GOTAchievement smoke;
	public static GOTAchievement poisonBarrel;
	public static GOTAchievement poisonMug;
	public static GOTAchievement throwSlingIntoWater;
	public static GOTAchievement throwConker;
	public static GOTAchievement wearFullGemsbok;
	public static GOTAchievement wearFullCopper;
	public static GOTAchievement wearFullCopperChainmail;
	public static GOTAchievement wearFullGold;
	public static GOTAchievement wearFullGoldChainmail;
	public static GOTAchievement wearFullAlloySteel;
	public static GOTAchievement wearFullAlloySteelChainmail;

	private final Collection<GOTFaction> allyFactions = new ArrayList<>();
	private final Category category;
	private final ItemStack icon;
	private final String name;
	private final int id;

	protected boolean isSpecial;

	private boolean isBiomeAchievement;
	private GOTTitle achievementTitle;

	@SuppressWarnings("WeakerAccess")
	public GOTAchievement(Category c, int i, Item item, String s) {
		this(c, i, new ItemStack(item), s);
	}

	@SuppressWarnings("WeakerAccess")
	public GOTAchievement(Category c, int i, Block block, String s) {
		this(c, i, new ItemStack(block), s);
	}

	@SuppressWarnings("WeakerAccess")
	public GOTAchievement(Category c, int i, ItemStack itemstack, String s) {
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
		category.getList().add(this);
		category.getDimension().getAllAchievements().add(this);
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

	private static GOTAchievement createKillAchievement(int id, String name) {
		return new GOTAchievement(Category.KILL, id, Items.iron_sword, name);
	}

	private static GOTAchievement createLegendaryAchievement(int id, String name) {
		return new GOTAchievement(Category.LEGENDARY, id, GOTItems.iconSword, name);
	}

	private static GOTAchievement createBiomeAchievement(int id, String name) {
		return new GOTAchievement(Category.ENTER, id, Items.filled_map, name).setBiomeAchievement();
	}

	private static GOTAchievement createArmorAchievement(int id, Item item, String name) {
		GOTAchievement achievement = new GOTAchievement(Category.WEAR, id, item, name);
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

	@SuppressWarnings({"UnusedAssignment", "ValueOfIncrementOrDecrementUsed"})
	public static void onInit() {
		int genId = 1;

		enterKnownWorld = new GOTAchievement(Category.GENERAL, genId++, GOTItems.gregorCleganeSword, "ENTER_KNOWN_WORLD");
		freeman = new GOTAchievement(Category.GENERAL, genId++, GOTItems.crowbar, "FREEMAN");

		useCrossbow = new GOTAchievement(Category.GENERAL, genId++, GOTItems.ironCrossbow, "USE_CROSSBOW");
		useSpear = new GOTAchievement(Category.GENERAL, genId++, GOTItems.ironSpear, "USE_SPEAR");
		useThrowingAxe = new GOTAchievement(Category.GENERAL, genId++, GOTItems.ironThrowingAxe, "USE_THROWING_AXE");
		useSarbacane = new GOTAchievement(Category.GENERAL, genId++, GOTItems.sarbacane, "USE_SARBACANE");
		useAsshaiArchmagStaff = new GOTAchievement(Category.GENERAL, genId++, GOTItems.asshaiArchmagStaff, "USE_ASSHAI_ARCHMAG_STAFF");
		useAsshaiShadowbinderStaff = new GOTAchievement(Category.GENERAL, genId++, GOTItems.asshaiShadowbinderStaff, "USE_ASSHAI_SHADOWBINDER_STAFF");
		useTermite = new GOTAchievement(Category.GENERAL, genId++, GOTItems.termite, "USE_TERMITE");
		useFirePot = new GOTAchievement(Category.GENERAL, genId++, GOTItems.firePot, "USE_FIRE_POT");
		useLingeringPotion = new GOTAchievement(Category.GENERAL, genId++, GOTItems.lingeringPotion, "USE_LINGERING_POTION");
		useBomb = new GOTAchievement(Category.GENERAL, genId++, GOTItems.fuse, "USE_BOMB");

		getBronze = new GOTAchievement(Category.GENERAL, genId++, GOTItems.bronzeIngot, "GET_BRONZE");
		getValyrianSteel = new GOTAchievement(Category.GENERAL, genId++, GOTItems.valyrianIngot, "GET_VALYRIAN_STEEL");
		getIce = new GOTAchievement(Category.GENERAL, genId++, GOTItems.ice, "GET_ICE");

		craftConcretePowder = new GOTAchievement(Category.GENERAL, genId++, new ItemStack(GOTBlocks.concretePowder, 1, 13), "CRAFT_CONCRETE_POWDER");
		craftPouch = new GOTAchievement(Category.GENERAL, genId++, GOTItems.pouch, "CRAFT_POUCH");
		craftSaddle = new GOTAchievement(Category.GENERAL, genId++, Items.saddle, "CRAFT_SADDLE");
		craftWildFire = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.wildFireJar, "CRAFT_WILD_FIRE");
		craftCargocart = new GOTAchievement(Category.GENERAL, genId++, GOTItems.cargocart, "CRAFT_CARGOCART");
		craftPlowcart = new GOTAchievement(Category.GENERAL, genId++, GOTItems.plowcart, "CRAFT_PLOWCART");

		useOven = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.oven, "USE_OVEN");
		useAlloyForge = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.alloyForge, "USE_ALLOY_FORGE");
		useUnsmeltery = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.unsmeltery, "USE_UNSMELTERY");
		useMillstone = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.millstone, "USE_MILLSTONE");
		useIronBank = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.ironBank, "USE_IRON_BANK");
		useBarrel = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugEthanol, "USE_BARREL");
		useKebabStand = new GOTAchievement(Category.GENERAL, genId++, GOTItems.shishKebab, "USE_KEBAB_STAND");
		useBeacon = new GOTAchievement(Category.GENERAL, genId++, Items.flint_and_steel, "USE_BEACON");
		useBanner = new GOTAchievement(Category.GENERAL, genId++, GOTItems.banner, "USE_BANNER");
		useButterflyJar = new GOTAchievement(Category.GENERAL, genId++, GOTBlocks.butterflyJar, "USE_BUTTERFLY_JAR");

		throwConker = new GOTAchievement(Category.GENERAL, genId++, GOTItems.chestnut, "THROW_CONKER");
		throwSlingIntoWater = new GOTAchievement(Category.GENERAL, genId++, GOTItems.sling, "THROW_SLING_INTO_WATER");
		poisonMug = new GOTAchievement(Category.GENERAL, genId++, GOTItems.bottlePoison, "POISON_MUG");
		poisonBarrel = new GOTAchievement(Category.GENERAL, genId++, GOTItems.bottlePoison, "POISON_BARREL");
		smoke = new GOTAchievement(Category.GENERAL, genId++, GOTItems.pipe, "SMOKE");
		brandEntity = new GOTAchievement(Category.GENERAL, genId++, GOTItems.brandingIron, "BRAND_ENTITY");
		combineSmithScrolls = new GOTAchievement(Category.GENERAL, genId++, GOTItems.smithScroll, "COMBINE_SMITH_SCROLLS");
		defeatInvasion = new GOTAchievement(Category.GENERAL, genId++, GOTItems.ironCrossbow, "DEFEAT_INVASION");
		earnManyCoins = new GOTAchievement(Category.GENERAL, genId++, GOTItems.coin, "EARN_MANY_COINS");
		engraveOwnership = new GOTAchievement(Category.GENERAL, genId++, Blocks.anvil, "ENGRAVE_OWNERSHIP");
		factionConquest = new GOTAchievement(Category.GENERAL, genId++, GOTItems.gregorCleganeSword, "FACTION_CONQUEST");
		findFourLeafClover = new GOTAchievement(Category.GENERAL, genId++, new ItemStack(GOTBlocks.clover, 1, 1), "FIND_FOUR_LEAF_CLOVER");
		fishRing = new GOTAchievement(Category.GENERAL, genId++, Items.fishing_rod, "FISH_RING");
		gainHighAlcoholTolerance = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugAle, "GAIN_HIGH_ALCOHOL_TOLERANCE");
		getDrunk = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugAle, "GET_DRUNK");
		hundreds = new GOTAchievement(Category.GENERAL, genId++, GOTItems.gregorCleganeSword, "HUNDREDS");
		hireGoldenCompany = new GOTAchievement(Category.GENERAL, genId++, GOTItems.goldenCompanyHelmet, "HIRE_GOLDEN_COMPANY");
		marry = new GOTAchievement(Category.GENERAL, genId++, GOTItems.goldRing, "MARRY");
		pickpocket = new GOTAchievement(Category.GENERAL, genId++, GOTItems.silverRing, "PICKPOCKET");
		pledgeService = new GOTAchievement(Category.GENERAL, genId++, GOTItems.gregorCleganeSword, "PLEDGE_SERVICE");
		reforge = new GOTAchievement(Category.GENERAL, genId++, Blocks.anvil, "REFORGE");
		stealArborGrapes = new GOTAchievement(Category.GENERAL, genId++, GOTItems.grapeRed, "STEAL_ARBOR_GRAPES");
		trade = new GOTAchievement(Category.GENERAL, genId++, GOTItems.coin, "TRADE");

		drinkWildFire = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugWildFire, "DRINK_WILD_FIRE");
		drinkPlantainBrew = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugPlantainBrew, "DRINK_PLANTAIN_BREW");
		drinkTermiteTequila = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugTermiteTequila, "DRINK_TERMITE_TEQUILA");
		drinkPoppyMilk = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugPoppyMilk, "DRINK_POPPY_MILK");
		drinkShadeEvening = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugShadeEvening, "DRINK_SHADE_EVENING");
		drinkUnsulliedTonic = new GOTAchievement(Category.GENERAL, genId++, GOTItems.mugUnsulliedTonic, "DRINK_UNSULLIED_TONIC");

		doMiniquest = new GOTAchievement(Category.GENERAL, genId++, GOTItems.questBook, "DO_MINIQUEST");
		doMiniquestHunter = new GOTAchievement(Category.GENERAL, genId++, GOTItems.questBook, "DO_MINIQUEST_HUNTER");
		doMiniquestHunter5 = new GOTAchievement(Category.GENERAL, genId++, GOTItems.questBook, "DO_MINIQUEST_HUNTER5");
		doMiniquestLegendary = new GOTAchievement(Category.GENERAL, genId++, GOTItems.questBook, "DO_MINIQUEST_LEGENDARY");

		travel20 = new GOTAchievement(Category.GENERAL, genId++, Items.map, "TRAVEL20");
		travel40 = new GOTAchievement(Category.GENERAL, genId++, Items.map, "TRAVEL40");
		travel60 = new GOTAchievement(Category.GENERAL, genId++, Items.map, "TRAVEL60");
		travel80 = new GOTAchievement(Category.GENERAL, genId++, Items.map, "TRAVEL80");
		travel100 = new GOTAchievement(Category.GENERAL, genId++, Items.map, "TRAVEL100");

		rideCamel = new GOTAchievement(Category.GENERAL, genId++, Items.saddle, "RIDE_CAMEL");
		rideDragon = new GOTAchievement(Category.GENERAL, genId++, Items.saddle, "RIDE_DRAGON");
		rideElephant = new GOTAchievement(Category.GENERAL, genId++, Items.saddle, "RIDE_ELEPHANT");
		rideIceSpider = new GOTAchievement(Category.GENERAL, genId++, Items.spider_eye, "RIDE_ICE_SPIDER");
		rideMammoth = new GOTAchievement(Category.GENERAL, genId++, Items.saddle, "RIDE_MAMMOTH");
		rideRhino = new GOTAchievement(Category.GENERAL, genId++, Items.saddle, "RIDE_RHINO");
		rideUlthosSpider = new GOTAchievement(Category.GENERAL, genId++, Items.spider_eye, "RIDE_ULTHOS_SPIDER");
		rideWoolyRhino = new GOTAchievement(Category.GENERAL, genId++, Items.saddle, "RIDE_WOOLY_RHINO");
		rideZebra = new GOTAchievement(Category.GENERAL, genId++, Items.saddle, "RIDE_ZEBRA");

		int killId = 1;

		killNPC = createKillAchievement(killId++, "KILL_NPC");

		killHuntingPlayer = createKillAchievement(killId++, "KILL_HUNTING_PLAYER");
		killLargeMobWithSlingshot = createKillAchievement(killId++, "KILL_LARGE_MOB_WITH_SLINGSHOT");
		killUsingOnlyPlates = createKillAchievement(killId++, "KILL_USING_ONLY_PLATES");
		killThievingBandit = createKillAchievement(killId++, "KILL_THIEVING_BANDIT");
		killWhileDrunk = createKillAchievement(killId++, "KILL_WHILE_DRUNK");

		killAsshaiShadowbinder = createKillAchievement(killId++, "KILL_ASSHAI_SHADOWBINDER");
		killAsshaiSpherebinder = createKillAchievement(killId++, "KILL_ASSHAI_SPHEREBINDER");
		killBarrowWight = createKillAchievement(killId++, "KILL_BARROW_WIGHT");
		killBear = createKillAchievement(killId++, "KILL_BEAR");
		killBeaver = createKillAchievement(killId++, "KILL_BEAVER");
		killBlizzard = createKillAchievement(killId++, "KILL_BLIZZARD");
		killDragon = createKillAchievement(killId++, "KILL_DRAGON");
		killElephant = createKillAchievement(killId++, "KILL_ELEPHANT");
		killGhiscarGladiator = createKillAchievement(killId++, "KILL_GHISCAR_GLADIATOR");
		killGhiscarHarpy = createKillAchievement(killId++, "KILL_GHISCAR_HARPY");
		killGiant = createKillAchievement(killId++, "KILL_GIANT");
		killUlthosSpider = createKillAchievement(killId++, "KILL_ULTHOS_SPIDER");
		killIceSpider = createKillAchievement(killId++, "KILL_ICE_SPIDER");
		killIfekevron = createKillAchievement(killId++, "KILL_IFEKEVRON");
		killKingsguard = createKillAchievement(killId++, "KILL_KINGSGUARD");
		killMaester = createKillAchievement(killId++, "KILL_MAESTER");
		killMammoth = createKillAchievement(killId++, "KILL_MAMMOTH");
		killManticore = createKillAchievement(killId++, "KILL_MANTICORE");
		killMossovyWitcher = createKillAchievement(killId++, "KILL_MOSSOVY_WITCHER");
		killPriest = createKillAchievement(killId++, "KILL_PRIEST");
		killProstitute = createKillAchievement(killId++, "KILL_PROSTITUTE");
		killRedScorpion = createKillAchievement(killId++, "KILL_RED_SCORPION");
		killShryke = createKillAchievement(killId++, "KILL_SHRYKE");
		killStoneMan = createKillAchievement(killId++, "KILL_STONE_MAN");
		killTermite = createKillAchievement(killId++, "KILL_TERMITE");
		killUnsullied = createKillAchievement(killId++, "KILL_UNSULLIED");
		killWalrus = createKillAchievement(killId++, "KILL_WALRUS");
		killWerewolf = createKillAchievement(killId++, "KILL_WEREWOLF");
		killWhiteWalker = createKillAchievement(killId++, "KILL_WHITE_WALKER");
		killWight = createKillAchievement(killId++, "KILL_WIGHT");
		killWightGiant = createKillAchievement(killId++, "KILL_WIGHT_GIANT");
		killWyvern = createKillAchievement(killId++, "KILL_WYVERN");
		killYiTiBombardier = createKillAchievement(killId++, "KILL_YI_TI_BOMBARDIER");

		int legId = 1;

		killLegendaryNPC = createLegendaryAchievement(legId++, "KILL_LEGENDARY_NPC");

		killAeronGreyjoy = createLegendaryAchievement(legId++, "KILL_AERON_GREYJOY");
		killAlliserThorne = createLegendaryAchievement(legId++, "KILL_ALLISER_THORNE");
		killAmoryLorch = createLegendaryAchievement(legId++, "KILL_AMORY_LORCH");
		killAreoHotah = createLegendaryAchievement(legId++, "KILL_AREO_HOTAH");
		killAryaStark = createLegendaryAchievement(legId++, "KILL_ARYA_STARK");
		killAsshaiArchmag = createLegendaryAchievement(legId++, "KILL_ASSHAI_ARCHMAG").createTitle();
		killBalonGreyjoy = createLegendaryAchievement(legId++, "KILL_BALON_GREYJOY");
		killBarristanSelmy = createLegendaryAchievement(legId++, "KILL_BARRISTAN_SELMY");
		killBenjenStark = createLegendaryAchievement(legId++, "KILL_BENJEN_STARK");
		killBericDondarrion = createLegendaryAchievement(legId++, "KILL_BERIC_DONDARRION");
		killBranStark = createLegendaryAchievement(legId++, "KILL_BRAN_STARK");
		killBrienneTarth = createLegendaryAchievement(legId++, "KILL_BRIENNE_TARTH");
		killBronn = createLegendaryAchievement(legId++, "KILL_BRONN");
		killBryndenTully = createLegendaryAchievement(legId++, "KILL_BRYNDEN_TULLY");
		killBuGai = createLegendaryAchievement(legId++, "KILL_BU_GAI");
		killCatelynStark = createLegendaryAchievement(legId++, "KILL_CATELYN_STARK");
		killCerseiLannister = createLegendaryAchievement(legId++, "KILL_CERSEI_LANNISTER");
		killCraster = createLegendaryAchievement(legId++, "KILL_CRASTER");
		killDaarioNaharis = createLegendaryAchievement(legId++, "KILL_DAARIO_NAHARIS");
		killDaenerysTargaryen = createLegendaryAchievement(legId++, "KILL_DAENERYS_TARGARYEN");
		killDavosSeaworth = createLegendaryAchievement(legId++, "KILL_DAVOS_SEAWORTH");
		killDoranMartell = createLegendaryAchievement(legId++, "KILL_DORAN_MARTELL");
		killEdmureTully = createLegendaryAchievement(legId++, "KILL_EDMURE_TULLY");
		killEllaryaSand = createLegendaryAchievement(legId++, "KILL_ELLARYA_SAND");
		killEuronGreyjoy = createLegendaryAchievement(legId++, "KILL_EURON_GREYJOY");
		killGarlanTyrell = createLegendaryAchievement(legId++, "KILL_GARLAN_TYRELL");
		killGendryBaratheon = createLegendaryAchievement(legId++, "KILL_GENDRY_BARATHEON");
		killGregorClegane = createLegendaryAchievement(legId++, "KILL_GREGOR_CLEGANE").createTitle();
		killGreyWorm = createLegendaryAchievement(legId++, "KILL_GREY_WORM");
		killHarryStrickland = createLegendaryAchievement(legId++, "KILL_HARRY_STRICKLAND");
		killHighSepton = createLegendaryAchievement(legId++, "KILL_HIGH_SEPTON");
		killHizdahrZoLoraq = createLegendaryAchievement(legId++, "KILL_HIZDAHR_ZO_LORAQ");
		killHodor = createLegendaryAchievement(legId++, "KILL_HODOR");
		killHosterTully = createLegendaryAchievement(legId++, "KILL_HOSTER_TULLY");
		killHowlandReed = createLegendaryAchievement(legId++, "KILL_HOWLAND_REED");
		killIllyrioMopatis = createLegendaryAchievement(legId++, "KILL_ILLYRIO_MOPATIS");
		killIlynPayne = createLegendaryAchievement(legId++, "KILL_ILYN_PAYNE");
		killJaimeLannister = createLegendaryAchievement(legId++, "KILL_JAIME_LANNISTER");
		killJanosSlynt = createLegendaryAchievement(legId++, "KILL_JANOS_SLYNT");
		killJaqenHghar = createLegendaryAchievement(legId++, "KILL_JAQEN_HGHAR");
		killJeorMormont = createLegendaryAchievement(legId++, "KILL_JEOR_MORMONT");
		killJoffreyBaratheon = createLegendaryAchievement(legId++, "KILL_JOFFREY_BARATHEON");
		killJohnUmber = createLegendaryAchievement(legId++, "KILL_JOHN_UMBER");
		killJonSnow = createLegendaryAchievement(legId++, "KILL_JON_SNOW");
		killJorahMormont = createLegendaryAchievement(legId++, "KILL_JORAH_MORMONT");
		killKevanLannister = createLegendaryAchievement(legId++, "KILL_KEVAN_LANNISTER");
		killKraznysMoNakloz = createLegendaryAchievement(legId++, "KILL_KRAZNYS_MO_NAKLOZ");
		killLancelLannister = createLegendaryAchievement(legId++, "KILL_LANCEL_LANNISTER");
		killLorasTyrell = createLegendaryAchievement(legId++, "KILL_LORAS_TYRELL");
		killLuwin = createLegendaryAchievement(legId++, "KILL_LUWIN");
		killLysaArryn = createLegendaryAchievement(legId++, "KILL_LYSA_ARRYN");
		killMaceTyrell = createLegendaryAchievement(legId++, "KILL_MACE_TYRELL");
		killManceRayder = createLegendaryAchievement(legId++, "KILL_MANCE_RAYDER");
		killMargaeryTyrell = createLegendaryAchievement(legId++, "KILL_MARGAERY_TYRELL");
		killMelisandra = createLegendaryAchievement(legId++, "KILL_MELISANDRA");
		killMerynTrant = createLegendaryAchievement(legId++, "KILL_MERYN_TRANT");
		killMissandei = createLegendaryAchievement(legId++, "KILL_MISSANDEI");
		killMoqorro = createLegendaryAchievement(legId++, "KILL_MOQORRO");
		killMyrcellaBaratheon = createLegendaryAchievement(legId++, "KILL_MYRCELLA_BARATHEON");
		killNightKing = createLegendaryAchievement(legId++, "KILL_NIGHT_KING").createTitle();
		killOberynMartell = createLegendaryAchievement(legId++, "KILL_OBERYN_MARTELL");
		killOlennaTyrell = createLegendaryAchievement(legId++, "KILL_OLENNA_TYRELL");
		killOsha = createLegendaryAchievement(legId++, "KILL_OSHA");
		killPetyrBaelish = createLegendaryAchievement(legId++, "KILL_PETYR_BAELISH");
		killPodrickPayne = createLegendaryAchievement(legId++, "KILL_PODRICK_PAYNE");
		killQyburn = createLegendaryAchievement(legId++, "KILL_QYBURN");
		killRamsayBolton = createLegendaryAchievement(legId++, "KILL_RAMSAY_BOLTON");
		killRandyllTarly = createLegendaryAchievement(legId++, "KILL_RANDYLL_TARLY");
		killRenlyBaratheon = createLegendaryAchievement(legId++, "KILL_RENLY_BARATHEON");
		killRickardKarstark = createLegendaryAchievement(legId++, "KILL_RICKARD_KARSTARK");
		killRobbStark = createLegendaryAchievement(legId++, "KILL_ROBB_STARK");
		killRobinArryn = createLegendaryAchievement(legId++, "KILL_ROBIN_ARRYN");
		killRooseBolton = createLegendaryAchievement(legId++, "KILL_ROOSE_BOLTON");
		killSalladhorSaan = createLegendaryAchievement(legId++, "KILL_SALLADHOR_SAAN");
		killSamwellTarly = createLegendaryAchievement(legId++, "KILL_SAMWELL_TARLY");
		killSandorClegane = createLegendaryAchievement(legId++, "KILL_SANDOR_CLEGANE");
		killSansaStark = createLegendaryAchievement(legId++, "KILL_SANSA_STARK");
		killSelyseBaratheon = createLegendaryAchievement(legId++, "KILL_SELYSE_BARATHEON");
		killShae = createLegendaryAchievement(legId++, "KILL_SHAE");
		killShireenBaratheon = createLegendaryAchievement(legId++, "KILL_SHIREEN_BARATHEON");
		killStannisBaratheon = createLegendaryAchievement(legId++, "KILL_STANNIS_BARATHEON");
		killTheonGreyjoy = createLegendaryAchievement(legId++, "KILL_THEON_GREYJOY");
		killThoros = createLegendaryAchievement(legId++, "KILL_THOROS");
		killThreeEyedRaven = createLegendaryAchievement(legId++, "KILL_THREE_EYED_RAVEN");
		killTobhoMott = createLegendaryAchievement(legId++, "KILL_TOBHO_MOTT");
		killTommenBaratheon = createLegendaryAchievement(legId++, "KILL_TOMMEN_BARATHEON");
		killTormund = createLegendaryAchievement(legId++, "KILL_TORMUND");
		killTrystaneMartell = createLegendaryAchievement(legId++, "KILL_TRYSTANE_MARTELL");
		killTugarKhan = createLegendaryAchievement(legId++, "KILL_TUGAR_KHAN");
		killTychoNestoris = createLegendaryAchievement(legId++, "KILL_TYCHO_NESTORIS");
		killTyrionLannister = createLegendaryAchievement(legId++, "KILL_TYRION_LANNISTER");
		killTywinLannister = createLegendaryAchievement(legId++, "KILL_TYWIN_LANNISTER");
		killVarys = createLegendaryAchievement(legId++, "KILL_VARYS");
		killVictarionGreyjoy = createLegendaryAchievement(legId++, "KILL_VICTARION_GREYJOY");
		killWalderFrey = createLegendaryAchievement(legId++, "KILL_WALDER_FREY");
		killWillasTyrell = createLegendaryAchievement(legId++, "KILL_WILLAS_TYRELL");
		killWymanManderly = createLegendaryAchievement(legId++, "KILL_WYMAN_MANDERLY");
		killXaroXhoanDaxos = createLegendaryAchievement(legId++, "KILL_XARO_XHOAN_DAXOS");
		killYaraGreyjoy = createLegendaryAchievement(legId++, "KILL_YARA_GREYJOY");
		killYgritte = createLegendaryAchievement(legId++, "KILL_YGRITTE");
		killYohnRoyce = createLegendaryAchievement(legId++, "KILL_YOHN_ROYCE");
		killYoren = createLegendaryAchievement(legId++, "KILL_YOREN");
		killYoungGriff = createLegendaryAchievement(legId++, "KILL_YOUNG_GRIFF");

		int entId = 1;

		enterAlwaysWinter = createBiomeAchievement(entId++, "ENTER_ALWAYS_WINTER");
		enterArryn = createBiomeAchievement(entId++, "ENTER_ARRYN");
		enterArrynMountains = createBiomeAchievement(entId++, "ENTER_ARRYN_MOUNTAINS");
		enterArrynMountainsFoothills = createBiomeAchievement(entId++, "ENTER_ARRYN_MOUNTAINS_FOOTHILLS");
		enterArrynTown = createBiomeAchievement(entId++, "ENTER_ARRYN_TOWN");
		enterBleedingSea = createBiomeAchievement(entId++, "ENTER_BLEEDING_SEA");
		enterBoneMountains = createBiomeAchievement(entId++, "ENTER_BONE_MOUNTAINS");
		enterBraavos = createBiomeAchievement(entId++, "ENTER_BRAAVOS");
		enterCannibalSands = createBiomeAchievement(entId++, "ENTER_CANNIBAL_SANDS");
		enterFrozenShore = createBiomeAchievement(entId++, "ENTER_FROZEN_SHORE");
		enterCrownlands = createBiomeAchievement(entId++, "ENTER_CROWNLANDS");
		enterCrownlandsTown = createBiomeAchievement(entId++, "ENTER_CROWNLANDS_TOWN");
		enterDisputedLands = createBiomeAchievement(entId++, "ENTER_DISPUTED_LANDS");
		enterDorne = createBiomeAchievement(entId++, "ENTER_DORNE");
		enterDorneDesert = createBiomeAchievement(entId++, "ENTER_DORNE_DESERT");
		enterDorneMesa = createBiomeAchievement(entId++, "ENTER_DORNE_MESA");
		enterDorneMountains = createBiomeAchievement(entId++, "ENTER_DORNE_MOUNTAINS");
		enterDothrakiSea = createBiomeAchievement(entId++, "ENTER_DOTHRAKI_SEA");
		enterDragonstone = createBiomeAchievement(entId++, "ENTER_DRAGONSTONE");
		enterEssosMountains = createBiomeAchievement(entId++, "ENTER_ESSOS_MOUNTAINS");
		enterFrostfangs = createBiomeAchievement(entId++, "ENTER_FROSTFANGS");
		enterGhiscar = createBiomeAchievement(entId++, "ENTER_GHISCAR");
		enterGhiscarAstapor = createBiomeAchievement(entId++, "ENTER_GHISCAR_ASTAPOR");
		enterGhiscarColony = createBiomeAchievement(entId++, "ENTER_GHISCAR_COLONY");
		enterGhiscarMeereen = createBiomeAchievement(entId++, "ENTER_GHISCAR_MEEREEN");
		enterGhiscarNewGhis = createBiomeAchievement(entId++, "ENTER_GHISCAR_NEW_GHIS");
		enterGhiscarYunkai = createBiomeAchievement(entId++, "ENTER_GHISCAR_YUNKAI");
		enterGiftNew = createBiomeAchievement(entId++, "ENTER_GIFT_NEW");
		enterGiftOld = createBiomeAchievement(entId++, "ENTER_GIFT_OLD");
		enterHauntedForest = createBiomeAchievement(entId++, "ENTER_HAUNTED_FOREST");
		enterIbben = createBiomeAchievement(entId++, "ENTER_IBBEN");
		enterIbbenColony = createBiomeAchievement(entId++, "ENTER_IBBEN_COLONY");
		enterIbbenMountains = createBiomeAchievement(entId++, "ENTER_IBBEN_MOUNTAINS");
		enterIbbenTaiga = createBiomeAchievement(entId++, "ENTER_IBBEN_TAIGA");
		enterIfekevronForest = createBiomeAchievement(entId++, "ENTER_IFEKEVRON_FOREST");
		enterIronIslands = createBiomeAchievement(entId++, "ENTER_IRON_ISLANDS");
		enterIsleOfFaces = createBiomeAchievement(entId++, "ENTER_ISLE_OF_FACES");
		enterJogosNhai = createBiomeAchievement(entId++, "ENTER_JOGOS_NHAI");
		enterJogosNhaiDesert = createBiomeAchievement(entId++, "ENTER_JOGOS_NHAI_DESERT");
		enterKingswood = createBiomeAchievement(entId++, "ENTER_KINGSWOOD");
		enterLhazar = createBiomeAchievement(entId++, "ENTER_LHAZAR");
		enterLongSummer = createBiomeAchievement(entId++, "ENTER_LONG_SUMMER");
		enterLorath = createBiomeAchievement(entId++, "ENTER_LORATH");
		enterLorathMaze = createBiomeAchievement(entId++, "ENTER_LORATH_MAZE");
		enterLys = createBiomeAchievement(entId++, "ENTER_LYS");
		enterMossovy = createBiomeAchievement(entId++, "ENTER_MOSSOVY");
		enterMossovyMarshes = createBiomeAchievement(entId++, "ENTER_MOSSOVY_MARSHES");
		enterMossovyMountains = createBiomeAchievement(entId++, "ENTER_MOSSOVY_MOUNTAINS");
		enterMossovyTaiga = createBiomeAchievement(entId++, "ENTER_MOSSOVY_TAIGA");
		enterMyr = createBiomeAchievement(entId++, "ENTER_MYR");
		enterNeck = createBiomeAchievement(entId++, "ENTER_NECK");
		enterNorth = createBiomeAchievement(entId++, "ENTER_NORTH");
		enterNorthBarrows = createBiomeAchievement(entId++, "ENTER_NORTH_BARROWS");
		enterNorthForestIrontree = createBiomeAchievement(entId++, "ENTER_NORTH_FOREST_IRONTREE");
		enterNorthMountains = createBiomeAchievement(entId++, "ENTER_NORTH_MOUNTAINS");
		enterNorthTown = createBiomeAchievement(entId++, "ENTER_NORTH_TOWN");
		enterNorthWild = createBiomeAchievement(entId++, "ENTER_NORTH_WILD");
		enterNorvos = createBiomeAchievement(entId++, "ENTER_NORVOS");
		enterPentos = createBiomeAchievement(entId++, "ENTER_PENTOS");
		enterQarth = createBiomeAchievement(entId++, "ENTER_QARTH");
		enterQarthColony = createBiomeAchievement(entId++, "ENTER_QARTH_COLONY");
		enterQarthDesert = createBiomeAchievement(entId++, "ENTER_QARTH_DESERT");
		enterQohor = createBiomeAchievement(entId++, "ENTER_QOHOR");
		enterReach = createBiomeAchievement(entId++, "ENTER_REACH");
		enterReachArbor = createBiomeAchievement(entId++, "ENTER_REACH_ARBOR");
		enterReachFireField = createBiomeAchievement(entId++, "ENTER_REACH_FIRE_FIELD");
		enterReachTown = createBiomeAchievement(entId++, "ENTER_REACH_TOWN");
		enterRiverlands = createBiomeAchievement(entId++, "ENTER_RIVERLANDS");
		enterShadowLand = createBiomeAchievement(entId++, "ENTER_SHADOW_LAND");
		enterShadowMountains = createBiomeAchievement(entId++, "ENTER_SHADOW_MOUNTAINS");
		enterShadowTown = createBiomeAchievement(entId++, "ENTER_SHADOW_TOWN");
		enterShrykesLand = createBiomeAchievement(entId++, "ENTER_SHRYKES_LAND");
		enterSkagos = createBiomeAchievement(entId++, "ENTER_SKAGOS");
		enterSothoryosBushland = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_BUSHLAND");
		enterSothoryosDesert = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_DESERT");
		enterSothoryosDesertCold = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_DESERT_COLD");
		enterSothoryosForest = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_FOREST");
		enterSothoryosFrost = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_FROST");
		enterSothoryosHell = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_HELL");
		enterSothoryosJungle = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_JUNGLE");
		enterSothoryosMangrove = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_MANGROVE");
		enterSothoryosMountains = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_MOUNTAINS");
		enterSothoryosSavannah = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_SAVANNAH");
		enterSothoryosTaiga = createBiomeAchievement(entId++, "ENTER_SOTHORYOS_TAIGA");
		enterStepstones = createBiomeAchievement(entId++, "ENTER_STEPSTONES");
		enterStoneyShore = createBiomeAchievement(entId++, "ENTER_STONEY_SHORE");
		enterStormlands = createBiomeAchievement(entId++, "ENTER_STORMLANDS");
		enterStormlandsTarth = createBiomeAchievement(entId++, "ENTER_STORMLANDS_TARTH");
		enterSummerColony = createBiomeAchievement(entId++, "ENTER_SUMMER_COLONY");
		enterSummerIslands = createBiomeAchievement(entId++, "ENTER_SUMMER_ISLANDS");
		enterThennLand = createBiomeAchievement(entId++, "ENTER_THENN_LAND");
		enterTyrosh = createBiomeAchievement(entId++, "ENTER_TYROSH");
		enterUlthos = createBiomeAchievement(entId++, "ENTER_ULTHOS");
		enterUlthosDesert = createBiomeAchievement(entId++, "ENTER_ULTHOS_DESERT");
		enterUlthosDesertCold = createBiomeAchievement(entId++, "ENTER_ULTHOS_DESERT_COLD");
		enterUlthosForest = createBiomeAchievement(entId++, "ENTER_ULTHOS_FOREST");
		enterUlthosFrost = createBiomeAchievement(entId++, "ENTER_ULTHOS_FROST");
		enterUlthosMarshes = createBiomeAchievement(entId++, "ENTER_ULTHOS_MARSHES");
		enterUlthosMountains = createBiomeAchievement(entId++, "ENTER_ULTHOS_MOUNTAINS");
		enterUlthosRedForest = createBiomeAchievement(entId++, "ENTER_ULTHOS_RED_FOREST");
		enterUlthosTaiga = createBiomeAchievement(entId++, "ENTER_ULTHOS_TAIGA");
		enterValyria = createBiomeAchievement(entId++, "ENTER_VALYRIA");
		enterValyriaVolcano = createBiomeAchievement(entId++, "ENTER_VALYRIA_VOLCANO");
		enterVolantis = createBiomeAchievement(entId++, "ENTER_VOLANTIS");
		enterWesterlands = createBiomeAchievement(entId++, "ENTER_WESTERLANDS");
		enterWesterlandsTown = createBiomeAchievement(entId++, "ENTER_WESTERLANDS_TOWN");
		enterWesterosFrost = createBiomeAchievement(entId++, "ENTER_WESTEROS_FROST");
		enterYeen = createBiomeAchievement(entId++, "ENTER_YEEN");
		enterYiTi = createBiomeAchievement(entId++, "ENTER_YI_TI");
		enterYiTiBorderZone = createBiomeAchievement(entId++, "ENTER_YI_TI_BORDER_ZONE");

		int armorId = 1;

		wearFullCopper = createArmorAchievement(armorId++, GOTItems.copperChestplate, "WEAR_FULL_COPPER");
		wearFullCopperChainmail = createArmorAchievement(armorId++, GOTItems.copperChainmailChestplate, "WEAR_FULL_COPPER_CHAINMAIL");
		wearFullBronze = createArmorAchievement(armorId++, GOTItems.bronzeChestplate, "WEAR_FULL_BRONZE");
		wearFullBronzeChainmail = createArmorAchievement(armorId++, GOTItems.bronzeChainmailChestplate, "WEAR_FULL_BRONZE_CHAINMAIL");
		wearFullIron = createArmorAchievement(armorId++, Items.iron_chestplate, "WEAR_FULL_IRON");
		wearFullIronChainmail = createArmorAchievement(armorId++, Items.chainmail_chestplate, "WEAR_FULL_IRON_CHAINMAIL");
		wearFullGold = createArmorAchievement(armorId++, Items.golden_chestplate, "WEAR_FULL_GOLD");
		wearFullGoldChainmail = createArmorAchievement(armorId++, GOTItems.goldChainmailChestplate, "WEAR_FULL_GOLD_CHAINMAIL");
		wearFullAlloySteel = createArmorAchievement(armorId++, GOTItems.alloySteelChestplate, "WEAR_FULL_ALLOY_STEEL");
		wearFullAlloySteelChainmail = createArmorAchievement(armorId++, GOTItems.alloySteelChainmailChestplate, "WEAR_FULL_ALLOY_STEEL_CHAINMAIL");
		wearFullValyrian = createArmorAchievement(armorId++, GOTItems.valyrianChestplate, "WEAR_FULL_VALYRIAN");
		wearFullValyrianChainmail = createArmorAchievement(armorId++, GOTItems.valyrianChainmailChestplate, "WEAR_FULL_VALYRIAN_CHAINMAIL");

		wearFullBone = createArmorAchievement(armorId++, GOTItems.boneChestplate, "WEAR_FULL_BONE");
		wearFullFur = createArmorAchievement(armorId++, GOTItems.furChestplate, "WEAR_FULL_FUR");
		wearFullGemsbok = createArmorAchievement(armorId++, GOTItems.gemsbokChestplate, "WEAR_FULL_GEMSBOK");

		wearFullGift = createArmorAchievement(armorId++, GOTItems.giftChestplate, "WEAR_FULL_GIFT");
		wearFullNorth = createArmorAchievement(armorId++, GOTItems.northChestplate, "WEAR_FULL_NORTH");
		wearFullNorthguard = createArmorAchievement(armorId++, GOTItems.northguardChestplate, "WEAR_FULL_NORTHGUARD");
		wearFullIronborn = createArmorAchievement(armorId++, GOTItems.ironbornChestplate, "WEAR_FULL_IRONBORN");
		wearFullWesterlands = createArmorAchievement(armorId++, GOTItems.westerlandsChestplate, "WEAR_FULL_WESTERLANDS");
		wearFullWesterlandsguard = createArmorAchievement(armorId++, GOTItems.westerlandsguardChestplate, "WEAR_FULL_WESTERLANDSGUARD");
		wearFullRiverlands = createArmorAchievement(armorId++, GOTItems.riverlandsChestplate, "WEAR_FULL_RIVERLANDS");
		wearFullHillmen = createArmorAchievement(armorId++, GOTItems.hillmenChestplate, "WEAR_FULL_HILLMEN");
		wearFullArryn = createArmorAchievement(armorId++, GOTItems.arrynChestplate, "WEAR_FULL_ARRYN");
		wearFullArrynguard = createArmorAchievement(armorId++, GOTItems.arrynguardChestplate, "WEAR_FULL_ARRYNGUARD");
		wearFullDragonstone = createArmorAchievement(armorId++, GOTItems.dragonstoneChestplate, "WEAR_FULL_DRAGONSTONE");
		wearFullCrownlands = createArmorAchievement(armorId++, GOTItems.crownlandsChestplate, "WEAR_FULL_CROWNLANDS");
		wearFullKingsguard = createArmorAchievement(armorId++, GOTItems.kingsguardChestplate, "WEAR_FULL_KINGSGUARD");
		wearFullStormlands = createArmorAchievement(armorId++, GOTItems.stormlandsChestplate, "WEAR_FULL_STORMLANDS");
		wearFullReach = createArmorAchievement(armorId++, GOTItems.reachChestplate, "WEAR_FULL_REACH");
		wearFullReachguard = createArmorAchievement(armorId++, GOTItems.reachguardChestplate, "WEAR_FULL_REACHGUARD");
		wearFullDorne = createArmorAchievement(armorId++, GOTItems.dorneChestplate, "WEAR_FULL_DORNE");

		wearFullBraavos = createArmorAchievement(armorId++, GOTItems.braavosChestplate, "WEAR_FULL_BRAAVOS");
		wearFullVolantis = createArmorAchievement(armorId++, GOTItems.volantisChestplate, "WEAR_FULL_VOLANTIS");
		wearFullPentos = createArmorAchievement(armorId++, GOTItems.pentosChestplate, "WEAR_FULL_PENTOS");
		wearFullNorvos = createArmorAchievement(armorId++, GOTItems.norvosChestplate, "WEAR_FULL_NORVOS");
		wearFullLorath = createArmorAchievement(armorId++, GOTItems.lorathChestplate, "WEAR_FULL_LORATH");
		wearFullQohor = createArmorAchievement(armorId++, GOTItems.qohorChestplate, "WEAR_FULL_QOHOR");
		wearFullMyr = createArmorAchievement(armorId++, GOTItems.myrChestplate, "WEAR_FULL_MYR");
		wearFullLys = createArmorAchievement(armorId++, GOTItems.lysChestplate, "WEAR_FULL_LYS");
		wearFullTyrosh = createArmorAchievement(armorId++, GOTItems.tyroshChestplate, "WEAR_FULL_TYROSH");
		wearFullGoldenCompany = createArmorAchievement(armorId++, GOTItems.goldenCompanyChestplate, "WEAR_FULL_GOLDEN_COMPANY");

		wearFullGhiscar = createArmorAchievement(armorId++, GOTItems.ghiscarChestplate, "WEAR_FULL_GHISCAR");
		wearFullQarth = createArmorAchievement(armorId++, GOTItems.qarthChestplate, "WEAR_FULL_QARTH");
		wearFullLhazar = createArmorAchievement(armorId++, GOTItems.lhazarChestplate, "WEAR_FULL_LHAZAR");
		wearFullDothraki = createArmorAchievement(armorId++, GOTItems.dothrakiChestplate, "WEAR_FULL_DOTHRAKI");
		wearFullIbben = createArmorAchievement(armorId++, GOTItems.ibbenChestplate, "WEAR_FULL_IBBEN");
		wearFullJogosNhai = createArmorAchievement(armorId++, GOTItems.jogosNhaiChestplate, "WEAR_FULL_JOGOS_NHAI");
		wearFullMossovy = createArmorAchievement(armorId++, GOTItems.mossovyChestplate, "WEAR_FULL_MOSSOVY");
		wearFullYiti = createArmorAchievement(armorId++, GOTItems.yiTiChestplate, "WEAR_FULL_YI_TI");
		wearFullYitiBombardier = createArmorAchievement(armorId++, GOTItems.yiTiChestplateBombardier, "WEAR_FULL_YI_TI_BOMBARDIER");
		wearFullYitiSamurai = createArmorAchievement(armorId++, GOTItems.yiTiChestplateSamurai, "WEAR_FULL_YI_TI_SAMURAI");
		wearFullAsshai = createArmorAchievement(armorId++, GOTItems.asshaiChestplate, "WEAR_FULL_ASSHAI");

		wearFullSummer = createArmorAchievement(armorId++, GOTItems.summerChestplate, "WEAR_FULL_SUMMER");
		wearFullSothoryos = createArmorAchievement(armorId++, GOTItems.sothoryosChestplate, "WEAR_FULL_SOTHORYOS");

		wearFullUnsullied = createArmorAchievement(armorId++, GOTItems.unsulliedChestplate, "WEAR_FULL_UNSULLIED");
		wearFullWhiteWalkers = createArmorAchievement(armorId++, GOTItems.whiteWalkersChestplate, "WEAR_FULL_WHITE_WALKERS");

		wearFullRobes = createArmorAchievement(armorId++, GOTItems.robesChestplate, "WEAR_FULL_ROBES");

		wearFullTargaryen = createArmorAchievement(armorId++, GOTItems.targaryenChestplate, "WEAR_FULL_TARGARYEN");
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
		component.getChatStyle().setChatHoverEvent(new HoverEvent(GOTChatEvents.showGotAchievement, new ChatComponentText(category.name() + '$' + id)));
		return component;
	}

	private IChatComponent getChatComponentForEarn(EntityPlayer entityplayer) {
		IChatComponent base = getAchievementChatComponent(entityplayer);
		IChatComponent component = new ChatComponentText("[").appendSibling(base).appendText("]");
		component.setChatStyle(base.getChatStyle());
		return component;
	}

	public String getDescription() {
		return StatCollector.translateToLocal("got.achievement." + name + ".desc");
	}

	public String getTitle(EntityPlayer entityplayer) {
		return StatCollector.translateToLocal(getUntranslatedTitle(entityplayer));
	}

	public String getTitle() {
		return StatCollector.translateToLocal("got.achievement." + name + ".title");
	}

	public String getUntranslatedTitle(EntityPlayer entityplayer) {
		return "got.achievement." + name + ".title";
	}

	public void setRequiresAlly(GOTFaction... f) {
		allyFactions.addAll(Arrays.asList(f));
	}

	public Category getCategory() {
		return category;
	}

	public int getId() {
		return id;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public boolean isBiomeAchievement() {
		return isBiomeAchievement;
	}

	private GOTAchievement setBiomeAchievement() {
		isBiomeAchievement = true;
		return this;
	}

	public void setAchievementTitle(GOTTitle achievementTitle) {
		this.achievementTitle = achievementTitle;
	}

	public enum Category {
		TITLES(GOTFaction.NIGHT_WATCH), GENERAL(GOTFaction.SOTHORYOS), KILL(GOTFaction.WESTERLANDS), WEAR(GOTFaction.ARRYN), ENTER(GOTFaction.WHITE_WALKER), LEGENDARY(GOTFaction.YI_TI);

		private final Collection<GOTAchievement> list = new ArrayList<>();
		private final String codeName;
		private final float[] categoryColors;
		private final GOTDimension dimension;

		private int nextRankAchID = 1000;

		Category(GOTFaction faction) {
			this(faction.getFactionColor());
		}

		Category(int color) {
			this(GOTDimension.GAME_OF_THRONES, color);
		}

		Category(GOTDimension dim, int color) {
			codeName = name();
			categoryColors = new Color(color).getColorComponents(null);
			dimension = dim;
			dimension.getAchievementCategories().add(this);
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

		public Collection<GOTAchievement> getList() {
			return list;
		}

		public GOTDimension getDimension() {
			return dimension;
		}
	}
}