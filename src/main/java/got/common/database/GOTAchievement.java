package got.common.database;

import java.awt.Color;
import java.util.*;

import got.common.*;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestPickpocket;
import got.common.util.GOTEnumDyeColor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;

public class GOTAchievement {
	public static int id = 1;
	public static Map<ItemArmor.ArmorMaterial, GOTAchievement> armorAchievements = new HashMap<>();
	public static GOTAchievement bannerProtect;
	public static GOTAchievement brewDrinkInBarrel;
	public static GOTAchievement catchButterfly;
	public static GOTAchievement collectCraftingTables;
	public static GOTAchievement collectCrossbowBolts;
	public static GOTAchievement combineSmithScrolls;
	public static GOTAchievement cookKebab;
	public static GOTAchievement craftBomb;
	public static GOTAchievement craftBronze;
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
	public static GOTAchievement enterArryn;
	public static GOTAchievement enterArrynMountains;
	public static GOTAchievement enterArrynTown;
	public static GOTAchievement enterAsshai;
	public static GOTAchievement enterBoneMountains;
	public static GOTAchievement enterBraavos;
	public static GOTAchievement enterCannibalSands;
	public static GOTAchievement enterColdCoast;
	public static GOTAchievement enterCrownlands;
	public static GOTAchievement enterCrownlandsForest;
	public static GOTAchievement enterDorne;
	public static GOTAchievement enterDorneDesert;
	public static GOTAchievement enterDorneMesa;
	public static GOTAchievement enterDorneMountains;
	public static GOTAchievement enterDoshKhalin;
	public static GOTAchievement enterDothrakiSea;
	public static GOTAchievement enterDragonstone;
	public static GOTAchievement enterEssosMountains;
	public static GOTAchievement enterFaces;
	public static GOTAchievement enterFarNorth;
	public static GOTAchievement enterFireField;
	public static GOTAchievement enterFrostfangs;
	public static GOTAchievement enterGhiscar;
	public static GOTAchievement enterGhiscarColony;
	public static GOTAchievement enterGiftNew;
	public static GOTAchievement enterGiftOld;
	public static GOTAchievement enterGOT;
	public static GOTAchievement enterHauntedForest;
	public static GOTAchievement enterHillTribes;
	public static GOTAchievement enterIbben;
	public static GOTAchievement enterIfekevron;
	public static GOTAchievement enterIronborn;
	public static GOTAchievement enterIrontree;
	public static GOTAchievement enterJogos;
	public static GOTAchievement enterJogosDesert;
	public static GOTAchievement enterKingsLanding;
	public static GOTAchievement enterLhazar;
	public static GOTAchievement enterLongSummer;
	public static GOTAchievement enterLorath;
	public static GOTAchievement enterLys;
	public static GOTAchievement enterMercenary;
	public static GOTAchievement enterMossovy;
	public static GOTAchievement enterMossovyMarshes;
	public static GOTAchievement enterMossovySopkas;
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
	public static GOTAchievement enterQarthDesert;
	public static GOTAchievement enterQohor;
	public static GOTAchievement enterQohorForest;
	public static GOTAchievement enterReach;
	public static GOTAchievement enterReachTown;
	public static GOTAchievement enterRedSea;
	public static GOTAchievement enterRiverlands;
	public static GOTAchievement enterShadowLand;
	public static GOTAchievement enterShadowMountains;
	public static GOTAchievement enterSkagos;
	public static GOTAchievement enterSkirlingPass;
	public static GOTAchievement enterSothoryosBushland;
	public static GOTAchievement enterSothoryosDesert;
	public static GOTAchievement enterSothoryosFrost;
	public static GOTAchievement enterSothoryosHell;
	public static GOTAchievement enterSothoryosJungle;
	public static GOTAchievement enterSothoryosKanuka;
	public static GOTAchievement enterSothoryosMangrove;
	public static GOTAchievement enterSothoryosMountains;
	public static GOTAchievement enterSothoryosSavannah;
	public static GOTAchievement enterSothoryosTaiga;
	public static GOTAchievement enterStepstones;
	public static GOTAchievement enterStoneCoast;
	public static GOTAchievement enterStormlands;
	public static GOTAchievement enterSummerIslands;
	public static GOTAchievement enterTarth;
	public static GOTAchievement enterThenn;
	public static GOTAchievement enterTropicalForest;
	public static GOTAchievement enterTyrosh;
	public static GOTAchievement enterUlos;
	public static GOTAchievement enterUlthos;
	public static GOTAchievement enterUlthosDesert;
	public static GOTAchievement enterUlthosMountains;
	public static GOTAchievement enterValyria;
	public static GOTAchievement enterValyriaSea;
	public static GOTAchievement enterValyriaVolcano;
	public static GOTAchievement enterVolantis;
	public static GOTAchievement enterVolantisForest;
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
	public static GOTAchievement craftCopper;
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
			if (!anyAllies) {
				return false;
			}
		}
		return true;
	}

	public GOTAchievement createTitle() {
		return this.createTitle(null);
	}

	public GOTAchievement createTitle(String s) {
		if (achievementTitle != null) {
			throw new IllegalArgumentException("GOT achievement " + getCodeName() + " already has an associated title!");
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

	public GOTTitle getAchievementTitle() {
		return achievementTitle;
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

	public GOTAchievement setBiomeAchievement() {
		isBiomeAchievement = true;
		return this;
	}

	public GOTAchievement setRequiresAlly(GOTFaction... f) {
		allyFactions.addAll(Arrays.asList(f));
		return this;
	}

	public GOTAchievement setRequiresAnyAlly(List<GOTFaction> f) {
		return setRequiresAlly(f.toArray(new GOTFaction[0]));
	}

	public GOTAchievement setRequiresAnyEnemy(List<GOTFaction> f) {
		return setRequiresEnemy(f.toArray(new GOTFaction[0]));
	}

	public GOTAchievement setRequiresEnemy(GOTFaction... f) {
		enemyFactions.addAll(Arrays.asList(f));
		return this;
	}

	public GOTAchievement setSpecial() {
		isSpecial = true;
		return this;
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
			if (!category.name().equals(name)) {
				continue;
			}
			return category;
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
				if (achievement.getCodeName().equalsIgnoreCase(name)) {
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
		killThePolice = new GOTAchievement(Category.KILL, id++, GOTRegistry.westerosDagger, "BANDIT");
		bannerProtect = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.banner, "BANNER_PROTECT");
		brewDrinkInBarrel = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.barrel, "BREW_DRINK_IN_BARREL");
		craftBomb = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.bomb, "CRAFT_BOMB");
		craftWildFire = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.wildFireJar, "CRAFT_WILD_FIRE");
		defeatInvasion = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.gregorCleganeSword, "DEFEAT_INVASION");
		doLegendaryQuest = new GOTAchievement(Category.GENERAL, id++, Blocks.dragon_egg, "DO_LEGENDARY_QUEST");
		doQuest = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.questBook, "DO_QUEST");
		drinkFire = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.mugWildFire, "DRINK_FIRE");
		drinkTermite = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.mugTermiteTequila, "DRINK_TERMITE");
		earnManyCoins = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.coin, "EARN_MANY_COINS");
		enterGOT = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.gregorCleganeSword, "ENTER_GOT");
		factionConquest = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.gregorCleganeSword, "FACTION_CONQUEST");
		findFourLeafClover = new GOTAchievement(Category.GENERAL, id++, new ItemStack(GOTRegistry.clover, 1, 1), "FIND_FOUR_LEAF_CLOVER");
		freeman = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.crowbar, "FREEMAN");
		gainHighAlcoholTolerance = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.mugAle, "GAIN_HIGH_ALCOHOL_TOLERANCE");
		craftBronze = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.bronzeIngot, "GET_BRONZE");
		getConcrete = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.concretePowder.get(GOTEnumDyeColor.LIME), "GET_CONCRETE");
		craftCopper = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.copperIngot, "GET_COPPER");
		getPouch = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.pouch, "GET_POUCH");
		hundreds = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.gregorCleganeSword, "HUNDREDS");
		hireGoldenCompany = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.goldHelmet, "HIRE_GOLDEN_COMPANY");
		killer = new GOTAchievement(Category.KILL, id++, Items.iron_axe, "KILLER");
		killAlliserThorne = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westerosSword, "KILL_ALLISER_THORNE").createTitle();
		killAsshaiArchmag = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.archmagStaff, "KILL_ASSHAI_ARCHMAG").createTitle();
		killBalonGreyjoy = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.euronDagger, "KILL_BALON_GREYJOY");
		killBericDayne = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.dawn, "KILL_BERIC_DAYNE");
		killBericDondarrion = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.bericSword, "KILL_BERIC_DONDARRION").createTitle();
		killBuGai = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.yitiSword, "KILL_BU_GAI").createTitle();
		killCerseiLannister = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.wildFireJar, "KILL_CERSEI_LANNISTER");
		killCraster = new GOTAchievement(Category.LEGENDARY, id++, Items.iron_axe, "KILL_CRASTER").createTitle();
		killDaenerysTargaryen = new GOTAchievement(Category.LEGENDARY, id++, Blocks.dragon_egg, "KILL_DAENERYS_TARGARYEN").createTitle();
		killDavosSeaworth = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.leek, "KILL_DAVOS_SEAWORTH");
		killEuronGreyjoy = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.euronDagger, "KILL_EURON_GREYJOY");
		killGeroldDayne = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.darkstar, "KILL_GEROLD_DAYNE");
		killGiant = new GOTAchievement(Category.KILL, id++, GOTRegistry.club, "KILL_GIANT");
		killGladiator = new GOTAchievement(Category.KILL, id++, GOTRegistry.essosSword, "KILL_GLADIATOR");
		killGregorClegane = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.gregorCleganeSword, "KILL_GREGOR_CLEGANE").createTitle();
		killHodor = new GOTAchievement(Category.LEGENDARY, id++, Items.stick, "KILL_HODOR");
		killHowlandReed = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.reeds, "KILL_HOWLAND_REED");
		killIbben = new GOTAchievement(Category.KILL, id++, GOTRegistry.flintSpear, "KILL_IBBEN");
		killIceSpider = new GOTAchievement(Category.KILL, id++, GOTRegistry.valyrianSword, "KILL_ICE_SPIDER");
		killJaimeLannister = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.jaimeSword, "KILL_JAIME_LANNISTER");
		killJaqenHghar = new GOTAchievement(Category.LEGENDARY, id++, new ItemStack(GOTRegistry.coin, 1, 1), "KILL_JAQEN_HGHAR").createTitle();
		killJeorMormont = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.longclaw, "KILL_JEOR_MORMONT").createTitle();
		killJonSnow = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.longclaw, "KILL_JON_SNOW").createTitle();
		killJorahMormont = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.harpy, "KILL_JORAH_MORMONT");
		killKevanLannister = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.handGold, "KILL_KEVAN_LANNISTER");
		killKhal = new GOTAchievement(Category.KILL, id++, GOTRegistry.lhazarSword, "KILL_KHAL");
		killTugarKhan = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.nomadSword, "KILL_TUGAR_KHAN").createTitle();
		killLancelLannister = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.gregorCleganeSword, "KILL_LANCEL_LANNISTER");
		killLorasTyrell = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.gregorCleganeSword, "KILL_LORAS_TYRELL");
		killMaester = new GOTAchievement(Category.KILL, id++, Items.book, "KILL_MAESTER");
		killMammoth = new GOTAchievement(Category.KILL, id++, GOTRegistry.stoneSpear, "KILL_MAMMOTH");
		killMelisandra = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.lightbringer, "KILL_MELISANDRA");
		killNightKing = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.nightKingSword, "KILL_NIGHT_KING").createTitle();
		killNightWatchGuard = new GOTAchievement(Category.KILL, id++, GOTRegistry.westerosSword, "KILL_NIGHT_WATCH_GUARD");
		killOberynMartell = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.sunspear, "KILL_OBERYN_MARTELL").createTitle();
		killPetyrBaelish = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.baelishDagger, "KILL_PETYR_BAELISH");
		killRamsayBolton = new GOTAchievement(Category.LEGENDARY, id++, Items.bone, "KILL_RAMSAY_BOLTON");
		killRobbStark = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.robbSword, "KILL_ROBB_STARK");
		killRooseBolton = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.boltonDagger, "KILL_ROOSE_BOLTON");
		killSamurai = new GOTAchievement(Category.KILL, id++, GOTRegistry.katana, "KILL_SAMURAI");
		killSandorClegane = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.sandorCleganeSword, "KILL_SANDOR_CLEGANE").createTitle();
		killThennBerserker = new GOTAchievement(Category.KILL, id++, GOTRegistry.wildlingSword, "KILL_THENN_BERSERKER");
		killTheKing = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.aegonHelmet, "KILL_THE_KING").createTitle();
		killTyrion = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.mugRedWine, "KILL_TYRION");
		killTywin = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westkingHelmet, "KILL_TYWIN").createTitle();
		killUlthos = new GOTAchievement(Category.KILL, id++, GOTRegistry.mysteryWeb, "KILL_ULTHOS");
		killUnsullied = new GOTAchievement(Category.KILL, id++, GOTRegistry.essosPike, "KILL_UNSULLIED");
		killVarys = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westerosSword, "KILL_VARYS");
		killVassal = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westerosSword, "KILL_VASSAL");
		killVictarionGreyjoy = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.victarionAxe, "KILL_VICTARION_GREYJOY");
		killWerewolf = new GOTAchievement(Category.KILL, id++, GOTRegistry.mossovySword, "KILL_WEREWOLF");
		killWhiteWalker = new GOTAchievement(Category.KILL, id++, GOTRegistry.valyrianSword, "KILL_WHITE_WALKER");
		killProstitute = new GOTAchievement(Category.KILL, id++, GOTRegistry.ironCrossbow, "KILL_PROSTITUTE");
		killWight = new GOTAchievement(Category.KILL, id++, GOTRegistry.bericSword, "KILL_WIGHT");
		killWightGiant = new GOTAchievement(Category.KILL, id++, GOTRegistry.bericSword, "KILL_WIGHT_GIANT");
		killWitcher = new GOTAchievement(Category.KILL, id++, GOTRegistry.mossovySword, "KILL_WITCHER");
		killYaraGreyjoy = new GOTAchievement(Category.LEGENDARY, id++, Items.iron_axe, "KILL_YARA_GREYJOY");
		killThoros = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westerosSword, "KILL_THOROS");
		killBarristanSelmy = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westerosSword, "KILL_BARRISTAN_SELMY");
		killXaroXhoanDaxos = new GOTAchievement(Category.LEGENDARY, id++, Items.gold_ingot, "KILL_XARO_XHOAN_DAXOS");
		killGendryBaratheon = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.gendryHammer, "KILL_GENDRY_BARATHEON");
		killDoranMartell = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.tyeneDagger, "KILL_DORAN_MARTELL");
		killIllyrioMopatis = new GOTAchievement(Category.LEGENDARY, id++, Items.gold_ingot, "KILL_ILLYRIO_MOPATIS");
		killBenjenStark = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.valyrianSword, "KILL_BENJEN_STARK");
		killBrienneTarth = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.sapphire, "KILL_BRIENNE_TARTH");
		killSansaStark = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.justMaid, "KILL_SANSA_STARK");
		killTormund = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.tormundSword, "KILL_TORMUND");
		killYgritte = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.wildlingDagger, "KILL_YGRITTE");
		killManceRayder = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.wildlingSword, "KILL_MANCE_RAYDER");
		killMaceTyrell = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.wildFire, "KILL_MACE_TYRELL");
		killMargaeryTyrell = new GOTAchievement(Category.LEGENDARY, id++, Blocks.red_flower, "KILL_MARGAERY_TYRELL");
		killEllaryaSand = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.bottlePoison, "KILL_ELLARYA_SAND").createTitle();
		killBryndenTully = new GOTAchievement(Category.LEGENDARY, id++, Items.fish, "KILL_BRYNDEN_TULLY").createTitle();
		killEdmureTully = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.arrowFire, "KILL_EDMURE_TULLY");
		killArdrianCeltigar = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.celtigarAxe, "KILL_ARDRIAN_CELTIGAR").createTitle();
		killKraznysMoNakloz = new GOTAchievement(Category.LEGENDARY, id++, Blocks.dragon_egg, "KILL_KRAZNYS_MO_NAKLOZ");
		killPriest = new GOTAchievement(Category.LEGENDARY, id++, Blocks.fire, "KILL_PRIEST");
		mineValyrian = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.oreValyrian, "MINE_VALYRIAN");
		obama = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.banana, "OBAMA");
		pledgeService = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.gregorCleganeSword, "PLEDGE_SERVICE");
		reforge = new GOTAchievement(Category.GENERAL, id++, Blocks.anvil, "REFORGE");
		shootDownMidges = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.ironCrossbow, "SHOOT_DOWN_MIDGES");
		stealArborGrapes = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.grapeRed, "STEAL_ARBOR_GRAPES");
		tormentTheonGreyjoy = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.boltonDagger, "TORMENT_THEON_GREYJOY");
		trade = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.coin, "TRADE");
		travel10 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL10");
		travel20 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL20");
		travel30 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL30");
		travel40 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL40");
		travel50 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL50");
		unsmelt = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.unsmeltery, "UNSMELT");
		engraveOwnership = new GOTAchievement(Category.GENERAL, id++, Blocks.anvil, "ENGRAVE");
		steal = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.coin, "STEAL");
		enterAlwaysWinter = new GOTAchievement(Category.ENTER, id++, Blocks.ice, "VISIT_ALWAYS_WINTER");
		enterArbor = new GOTAchievement(Category.ENTER, id++, GOTRegistry.grapeRed, "VISIT_ARBOR");
		enterArryn = new GOTAchievement(Category.ENTER, id++, GOTRegistry.arrynHelmet, "VISIT_ARRYN");
		enterArrynMountains = new GOTAchievement(Category.ENTER, id++, new ItemStack(GOTRegistry.rock, 1, 1), "VISIT_ARRYN_MOUNTAINS");
		enterArrynTown = new GOTAchievement(Category.ENTER, id++, GOTRegistry.arrynguardHelmet, "VISIT_ARRYN_TOWN");
		enterAsshai = new GOTAchievement(Category.ENTER, id++, GOTRegistry.asshaiStaff, "VISIT_ASSHAI");
		enterBoneMountains = new GOTAchievement(Category.ENTER, id++, GOTRegistry.boneBlock, "VISIT_BONE_MOUNTAINS");
		enterBraavos = new GOTAchievement(Category.ENTER, id++, GOTRegistry.braavosHelmet, "VISIT_BRAAVOS");
		enterCannibalSands = new GOTAchievement(Category.ENTER, id++, GOTRegistry.quicksand, "VISIT_CANNIBAL_SANDS");
		enterColdCoast = new GOTAchievement(Category.ENTER, id++, GOTRegistry.wildlingPolearm, "VISIT_COLD_COAST");
		enterCrownlandsForest = new GOTAchievement(Category.ENTER, id++, GOTRegistry.mugRedWine, "VISIT_CROWNLANDS_FOREST");
		enterDorne = new GOTAchievement(Category.ENTER, id++, GOTRegistry.dorneHelmet, "VISIT_DORNE");
		enterDorneDesert = new GOTAchievement(Category.ENTER, id++, Blocks.sand, "VISIT_DORNE_DESERT");
		enterDorneMesa = new GOTAchievement(Category.ENTER, id++, Blocks.clay, "VISIT_DORNE_MESA");
		enterDorneMountains = new GOTAchievement(Category.ENTER, id++, new ItemStack(GOTRegistry.rock, 1, 4), "VISIT_DORNE_MOUNTAINS");
		enterDoshKhalin = new GOTAchievement(Category.ENTER, id++, Blocks.stone, "VISIT_DOSH_KHALIN");
		enterDothrakiSea = new GOTAchievement(Category.ENTER, id++, GOTRegistry.nomadSword, "VISIT_DOTHRAKI_SEA");
		enterDragonstone = new GOTAchievement(Category.ENTER, id++, GOTRegistry.obsidianShard, "VISIT_DRAGONSTONE");
		enterEssosMountains = new GOTAchievement(Category.ENTER, id++, Blocks.stone, "VISIT_ESSOS_MOUNTAINS");
		enterFaces = new GOTAchievement(Category.ENTER, id++, new ItemStack(GOTRegistry.leaves9, 1, 2), "VISIT_FACES");
		enterFarNorth = new GOTAchievement(Category.ENTER, id++, Blocks.snow, "VISIT_FAR_NORTH");
		enterFireField = new GOTAchievement(Category.ENTER, id++, Blocks.red_flower, "VISIT_FIRE_FIELD");
		enterFrostfangs = new GOTAchievement(Category.ENTER, id++, Blocks.packed_ice, "VISIT_FROSTFANGS");
		enterGhiscar = new GOTAchievement(Category.ENTER, id++, GOTRegistry.harpy, "VISIT_GHISCAR");
		enterGhiscarColony = new GOTAchievement(Category.ENTER, id++, GOTRegistry.ghiscarHelmet, "VISIT_GHISCAR_COLONY");
		enterGiftNew = new GOTAchievement(Category.ENTER, id++, Blocks.snow, "VISIT_GIFT_NEW");
		enterGiftOld = new GOTAchievement(Category.ENTER, id++, GOTRegistry.brickIce, "VISIT_GIFT_OLD");
		enterHauntedForest = new GOTAchievement(Category.ENTER, id++, GOTRegistry.club, "VISIT_HAUNTED_FOREST");
		enterHillTribes = new GOTAchievement(Category.ENTER, id++, GOTRegistry.trident, "VISIT_HILL_TRIBES");
		enterIbben = new GOTAchievement(Category.ENTER, id++, GOTRegistry.flintDagger, "VISIT_IBBEN");
		enterIfekevron = new GOTAchievement(Category.ENTER, id++, GOTRegistry.flintSpear, "VISIT_IFEKEVRON");
		enterIronborn = new GOTAchievement(Category.ENTER, id++, GOTRegistry.ironbornHelmet, "VISIT_IRONBORN");
		enterIrontree = new GOTAchievement(Category.ENTER, id++, Items.iron_axe, "VISIT_IRONTREE");
		enterJogos = new GOTAchievement(Category.ENTER, id++, GOTRegistry.nomadBow, "VISIT_JOGOS");
		enterJogosDesert = new GOTAchievement(Category.ENTER, id++, GOTRegistry.quicksand, "VISIT_JOGOS_DESERT");
		enterKingsLanding = new GOTAchievement(Category.ENTER, id++, GOTRegistry.crownlandsHelmet, "VISIT_KINGS_LANDING");
		enterLhazar = new GOTAchievement(Category.ENTER, id++, GOTRegistry.lhazarHelmet, "VISIT_LHAZAR");
		enterLongSummer = new GOTAchievement(Category.ENTER, id++, Blocks.fire, "VISIT_LONG_SUMMER");
		enterLorath = new GOTAchievement(Category.ENTER, id++, GOTRegistry.lorathHelmet, "VISIT_LORATH");
		enterLys = new GOTAchievement(Category.ENTER, id++, GOTRegistry.lysHelmet, "VISIT_LYS");
		enterMercenary = new GOTAchievement(Category.ENTER, id++, GOTRegistry.goldHelmet, "VISIT_MERCENARY");
		enterMossovy = new GOTAchievement(Category.ENTER, id++, GOTRegistry.mossovySword, "VISIT_MOSSOVY");
		enterMossovyMarshes = new GOTAchievement(Category.ENTER, id++, GOTRegistry.reeds, "VISIT_MOSSOVY_MARSHES");
		enterMossovySopkas = new GOTAchievement(Category.ENTER, id++, Blocks.stone, "VISIT_MOSSOVY_SOPKAS");
		enterMyr = new GOTAchievement(Category.ENTER, id++, GOTRegistry.myrHelmet, "VISIT_MYR");
		enterNaath = new GOTAchievement(Category.ENTER, id++, GOTRegistry.summerHelmet, "VISIT_NAATH");
		enterNeck = new GOTAchievement(Category.ENTER, id++, GOTRegistry.quagmire, "VISIT_NECK");
		enterNorth = new GOTAchievement(Category.ENTER, id++, GOTRegistry.northHelmet, "VISIT_NORTH");
		enterNorthBarrows = new GOTAchievement(Category.ENTER, id++, GOTRegistry.coin, "VISIT_NORTH_BARROWS");
		enterNorthMountains = new GOTAchievement(Category.ENTER, id++, new ItemStack(GOTRegistry.rock, 1, 1), "VISIT_NORTH_MOUNTAINS");
		enterNorthTown = new GOTAchievement(Category.ENTER, id++, GOTRegistry.northguardHelmet, "VISIT_NORTH_TOWN");
		enterNorthWild = new GOTAchievement(Category.ENTER, id++, GOTRegistry.trident, "VISIT_NORTH_WILD");
		enterNorvos = new GOTAchievement(Category.ENTER, id++, GOTRegistry.norvosHelmet, "VISIT_NORVOS");
		enterPentos = new GOTAchievement(Category.ENTER, id++, GOTRegistry.pentosHelmet, "VISIT_PENTOS");
		enterQarth = new GOTAchievement(Category.ENTER, id++, GOTRegistry.qarthHelmet, "VISIT_QARTH");
		enterQarthDesert = new GOTAchievement(Category.ENTER, id++, GOTRegistry.redSandstone, "VISIT_DORNE_DESERT");
		enterQohor = new GOTAchievement(Category.ENTER, id++, GOTRegistry.qohorHelmet, "VISIT_QOHOR");
		enterQohorForest = new GOTAchievement(Category.ENTER, id++, GOTRegistry.qohorHelmet, "VISIT_QOHOR_FOREST");
		enterReach = new GOTAchievement(Category.ENTER, id++, GOTRegistry.reachHelmet, "VISIT_REACH");
		enterReachTown = new GOTAchievement(Category.ENTER, id++, GOTRegistry.reachguardHelmet, "VISIT_REACH_TOWN");
		enterRedSea = new GOTAchievement(Category.ENTER, id++, GOTRegistry.bronzeSword, "VISIT_RED_SEA");
		enterRiverlands = new GOTAchievement(Category.ENTER, id++, GOTRegistry.riverlandsHelmet, "VISIT_RIVERLANDS");
		enterShadowLand = new GOTAchievement(Category.ENTER, id++, GOTRegistry.asshaiFlower, "VISIT_SHADOW_LAND");
		enterShadowMountains = new GOTAchievement(Category.ENTER, id++, new ItemStack(GOTRegistry.rock, 1, 0), "VISIT_SHADOW_MOUNTAINS");
		enterUlos = new GOTAchievement(Category.ENTER, id++, GOTRegistry.ulthosTorch, "VISIT_ULOS");
		enterSkagos = new GOTAchievement(Category.ENTER, id++, GOTRegistry.trident, "VISIT_SKAGOS");
		enterSkirlingPass = new GOTAchievement(Category.ENTER, id++, GOTRegistry.club, "VISIT_SKIRLING_PASS");
		enterSothoryosBushland = new GOTAchievement(Category.ENTER, id++, GOTRegistry.termite, "VISIT_SOTHORYOS_BUSHLAND");
		enterSothoryosDesert = new GOTAchievement(Category.ENTER, id++, Blocks.sand, "VISIT_SOTHORYOS_DESERT");
		enterSothoryosFrost = new GOTAchievement(Category.ENTER, id++, Blocks.packed_ice, "VISIT_SOTHORYOS_FROST");
		enterSothoryosHell = new GOTAchievement(Category.ENTER, id++, GOTRegistry.sothoryosAmulet, "VISIT_SOTHORYOS_HELL");
		enterSothoryosJungle = new GOTAchievement(Category.ENTER, id++, GOTRegistry.sothoryosHelmet, "VISIT_SOTHORYOS_JUNGLE");
		enterSothoryosKanuka = new GOTAchievement(Category.ENTER, id++, GOTRegistry.sothoryosHelmetGold, "VISIT_SOTHORYOS_KANUKA");
		enterSothoryosMangrove = new GOTAchievement(Category.ENTER, id++, Blocks.water, "VISIT_SOTHORYOS_MANGROVE");
		enterSothoryosMountains = new GOTAchievement(Category.ENTER, id++, new ItemStack(GOTRegistry.rock, 1, 6), "VISIT_SOTHORYOS_MOUNTAINS");
		enterSothoryosSavannah = new GOTAchievement(Category.ENTER, id++, Blocks.grass, "VISIT_SOTHORYOS_SAVANNAH");
		enterSothoryosTaiga = new GOTAchievement(Category.ENTER, id++, GOTRegistry.club, "VISIT_SOTHORYOS_TAIGA");
		enterStepstones = new GOTAchievement(Category.ENTER, id++, GOTRegistry.essosBow, "VISIT_STEPSTONES");
		enterStoneCoast = new GOTAchievement(Category.ENTER, id++, GOTRegistry.westerosDaggerPoisoned, "VISIT_STONE_COAST");
		enterStormlands = new GOTAchievement(Category.ENTER, id++, GOTRegistry.stormlandsHelmet, "VISIT_STORMLANDS");
		enterSummerIslands = new GOTAchievement(Category.ENTER, id++, GOTRegistry.summerHelmet, "VISIT_SUMMER_ISLANDS");
		enterTarth = new GOTAchievement(Category.ENTER, id++, GOTRegistry.whiteSandstone, "VISIT_TARTH");
		enterThenn = new GOTAchievement(Category.ENTER, id++, GOTRegistry.wildlingBattleaxe, "VISIT_THENN");
		enterTropicalForest = new GOTAchievement(Category.ENTER, id++, GOTRegistry.yitiHelmetShogune, "VISIT_TROPICAL_FOREST");
		enterTyrosh = new GOTAchievement(Category.ENTER, id++, GOTRegistry.tyroshHelmet, "VISIT_TYROSH");
		enterUlthos = new GOTAchievement(Category.ENTER, id++, Blocks.web, "VISIT_ULTHOS");
		enterUlthosDesert = new GOTAchievement(Category.ENTER, id++, Blocks.sand, "VISIT_ULTHOS_DESERT");
		enterUlthosMountains = new GOTAchievement(Category.ENTER, id++, new ItemStack(GOTRegistry.rock, 1, 3), "VISIT_ULTHOS_MOUNTAINS");
		enterValyria = new GOTAchievement(Category.ENTER, id++, Blocks.vine, "VISIT_VALYRIA");
		enterValyriaSea = new GOTAchievement(Category.ENTER, id++, Blocks.water, "VISIT_VALYRIA_SEA");
		enterValyriaVolcano = new GOTAchievement(Category.ENTER, id++, Blocks.lava, "VISIT_VALYRIA_VOLCANO");
		enterVolantis = new GOTAchievement(Category.ENTER, id++, GOTRegistry.volantisHelmet, "VISIT_VOLANTIS");
		enterVolantisForest = new GOTAchievement(Category.ENTER, id++, GOTRegistry.orange, "VISIT_VOLANTIS_FOREST");
		enterWesterlands = new GOTAchievement(Category.ENTER, id++, GOTRegistry.westerlandsHelmet, "VISIT_WESTERLANDS");
		enterWesterlandsHills = new GOTAchievement(Category.ENTER, id++, Blocks.gold_ore, "VISIT_WESTERLANDS_HILLS");
		enterWesterlandsTown = new GOTAchievement(Category.ENTER, id++, GOTRegistry.westerlandsguardHelmet, "VISIT_WESTERLANDS_TOWN");
		enterWhisperingWood = new GOTAchievement(Category.ENTER, id++, GOTRegistry.westerosPike, "VISIT_WHISPERING_WOOD");
		enterYeen = new GOTAchievement(Category.ENTER, id++, Blocks.obsidian, "VISIT_YEEN");
		enterYiTi = new GOTAchievement(Category.ENTER, id++, GOTRegistry.yitiHelmet, "VISIT_YI_TI");
		enterYiTiWasteland = new GOTAchievement(Category.ENTER, id++, GOTRegistry.yitiHelmetSamurai, "VISIT_YI_TI_WASTELAND");
		enterCrownlands = new GOTAchievement(Category.ENTER, id++, GOTRegistry.crownlandsHelmet, "VISIT_CROWNLANDS");
		wearFullArryn = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.arrynChestplate, "WEAR_FULL_ARRYN");
		wearFullArrynguard = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.arrynguardChestplate, "WEAR_FULL_ARRYNGUARD");
		wearFullAsshai = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.asshaiChestplate, "WEAR_FULL_ASSHAI");
		wearFullBlackfyre = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.blackfyreChestplate, "WEAR_FULL_BLACKFYRE");
		wearFullBone = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.boneChestplate, "WEAR_FULL_BONE");
		wearFullBraavos = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.braavosChestplate, "WEAR_FULL_BRAAVOS");
		wearFullBronze = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.bronzeChestplate, "WEAR_FULL_BRONZE");
		wearFullCrownlands = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.crownlandsChestplate, "WEAR_FULL_CROWNLANDS");
		wearFullDorne = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.dorneChestplate, "WEAR_FULL_DORNE");
		wearFullDragonstone = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.dragonstoneChestplate, "WEAR_FULL_DRAGONSTONE");
		wearFullFur = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.furChestplate, "WEAR_FULL_FUR");
		wearFullGemsbok = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.gemsbokChestplate, "WEAR_FULL_GEMSBOK");
		wearFullGhiscar = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.ghiscarChestplate, "WEAR_FULL_GHISCAR");
		wearFullGift = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.giftChestplate, "WEAR_FULL_GIFT");
		wearFullGoldencompany = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.goldChestplate, "WEAR_FULL_GOLDENCOMPANY");
		wearFullHand = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.handGold, "WEAR_FULL_HAND");
		wearFullHelmet = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.robertHelmet, "WEAR_FULL_HELMET");
		wearFullHillmen = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.hillmenChestplate, "WEAR_FULL_HILLMEN");
		wearFullIronborn = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.ironbornChestplate, "WEAR_FULL_IRONBORN");
		wearFullKingsguard = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.kingsguardChestplate, "WEAR_FULL_KINGSGUARD");
		wearFullLhazar = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.lhazarChestplate, "WEAR_FULL_LHAZAR");
		wearFullLhazarLion = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.lhazarChestplateLion, "WEAR_FULL_LHAZAR_LION");
		wearFullLorath = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.lorathChestplate, "WEAR_FULL_LORATH");
		wearFullLys = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.lysChestplate, "WEAR_FULL_LYS");
		wearFullMossovy = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.mossovyChestplate, "WEAR_FULL_MOSSOVY");
		wearFullMyr = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.myrChestplate, "WEAR_FULL_MYR");
		wearFullNorth = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.northChestplate, "WEAR_FULL_NORTH");
		wearFullNorthguard = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.northguardChestplate, "WEAR_FULL_NORTHGUARD");
		wearFullNorvos = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.norvosChestplate, "WEAR_FULL_NORVOS");
		wearFullPentos = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.pentosChestplate, "WEAR_FULL_PENTOS");
		wearFullQarth = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.qarthChestplate, "WEAR_FULL_QARTH");
		wearFullQohor = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.qohorChestplate, "WEAR_FULL_QOHOR");
		wearFullReach = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.reachChestplate, "WEAR_FULL_REACH");
		wearFullReachguard = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.reachguardChestplate, "WEAR_FULL_REACHGUARD");
		wearFullRedking = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.redkingChestplate, "WEAR_FULL_REDKING");
		wearFullRiverlands = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.riverlandsChestplate, "WEAR_FULL_RIVERLANDS");
		wearFullRobes = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.robesChestplate, "WEAR_FULL_ROBES");
		wearFullRoyce = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.royceChestplate, "WEAR_FULL_ROYCE");
		wearFullSothoryos = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.sothoryosChestplate, "WEAR_FULL_SOTHORYOS");
		wearFullSothoryosGold = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.sothoryosChestplateGold, "WEAR_FULL_SOTHORYOS_GOLD");
		wearFullStormlands = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.stormlandsChestplate, "WEAR_FULL_STORMLANDS");
		wearFullSummer = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.summerChestplate, "WEAR_FULL_SUMMER");
		wearFullTargaryen = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.targaryenChestplate, "WEAR_FULL_TARGARYEN");
		wearFullTyrosh = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.tyroshChestplate, "WEAR_FULL_TYROSH");
		wearFullUnsullied = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.unsulliedChestplate, "WEAR_FULL_UNSULLIED");
		wearFullValyrian = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.valyrianChestplate, "WEAR_FULL_VALYRIAN");
		wearFullVolantis = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.volantisChestplate, "WEAR_FULL_VOLANTIS");
		wearFullWesterlands = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.westerlandsChestplate, "WEAR_FULL_WESTERLANDS");
		wearFullWesterlandsguard = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.westerlandsguardChestplate, "WEAR_FULL_WESTERLANDSGUARD");
		wearFullWestking = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.westkingChestplate, "WEAR_FULL_WESTKING");
		wearFullWhitewalkers = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.whiteWalkersChestplate, "WEAR_FULL_WHITEWALKERS");
		wearFullYiti = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.yitiChestplate, "WEAR_FULL_YITI");
		wearFullYitiSamurai = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.yitiChestplateSamurai, "WEAR_FULL_YITI_SAMURAI");
		wearFullYitiFrontier = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.yitiChestplateFrontier, "WEAR_FULL_YITI_FRONTIER");
		wearFullDothraki = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.dothrakiChestplate, "WEAR_FULL_DOTHRAKI");
		wearFullJogos = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.jogosChestplate, "WEAR_FULL_JOGOS");

		catchButterfly = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.butterflyJar, "CATCH_BUTTERFLY");
		collectCraftingTables = new GOTAchievement(Category.GENERAL, id++, Blocks.crafting_table, "COLLECT_CRAFTING_TABLES");
		collectCrossbowBolts = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.crossbowBolt, "COLLECT_CROSSBOW_BOLTS");
		combineSmithScrolls = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.smithScroll, "COMBINE_SMITH_SCROLLS");
		cookKebab = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.kebab, "COOK_KEBAB").createTitle("cookKebab");
		craftSaddle = new GOTAchievement(Category.GENERAL, id++, Items.saddle, "CRAFT_SADDLE");
		doMiniquestHunter = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.questBook, "DO_MINIQUEST_HUNTER").createTitle("doMiniquestHunter");
		doMiniquestHunter5 = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.bountyTrophy, "DO_MINIQUEST_HUNTER5").createTitle("doMiniquestHunter5");
		drinkPlantainBrew = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.mugPlantainBrew, "DRINK_PLANTAIN_BREW");
		drinkSkull = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.skullCup, "DRINK_SKULL");
		findPlantain = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.plantain, "FIND_PLANTAIN");
		fishRing = new GOTAchievement(Category.GENERAL, id++, Items.fishing_rod, "FISH_RING").createTitle("fishRing");
		getDrunk = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.mugAle, "GET_DRUNK").createTitle();
		growBaobab = new GOTAchievement(Category.GENERAL, id++, new ItemStack(GOTRegistry.sapling4, 1, 1), "GROW_BAOBAB");
		killBombardier = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.bomb, "KILL_BOMBARDIER");
		killButterfly = new GOTAchievement(Category.GENERAL, id++, Items.iron_sword, "KILL_BUTTERFLY").createTitle("killButterfly");
		killHuntingPlayer = new GOTAchievement(Category.GENERAL, id++, Items.iron_sword, "KILL_HUNTING_PLAYER").createTitle("killHuntingPlayer");
		killLargeMobWithSlingshot = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.sling, "KILL_LARGE_MOB_WITH_SLINGSHOT");
		killThievingBandit = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.leatherHat, "KILL_THIEVING_BANDIT");
		killUsingOnlyPlates = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.plate, "KILL_USING_ONLY_PLATES").createTitle("killPlates");
		killWhileDrunk = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.mugAle, "KILL_WHILE_DRUNK").createTitle("killWhileDrunk");
		lightBeacon = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.beacon, "LIGHT_BEACON");
		marry = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.goldRing, "MARRY");
		mineGlowstone = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.oreGlowstone, "MINE_GLOWSTONE");
		pickpocket = new GOTAchievement(Category.GENERAL, id++, GOTMiniQuestPickpocket.createPickpocketIcon(), "PICKPOCKET").createTitle("pickpocket");
		rideCamel = new GOTAchievement(Category.GENERAL, id++, Items.saddle, "RIDE_CAMEL");
		smeltObsidianShard = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.obsidianShard, "SMELT_OBSIDIAN_SHARD");
		speakToDrunkard = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.mugAle, "SPEAK_TO_DRUNKARD");
		useCrossbow = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.ironCrossbow, "USE_CROSSBOW");
		useSpearFromFar = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.ironSpear, "USE_SPEAR_FROM_FAR");
		useThrowingAxe = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.ironThrowingAxe, "USE_THROWING_AXE");
	}

	public static Comparator<GOTAchievement> sortForDisplay(EntityPlayer entityplayer) {
		return (ach1, ach2) -> {
			if (ach1.isSpecial) {
				if (!ach2.isSpecial) {
					return -1;
				}
				if (ach2.ID < ach1.ID) {
					return 1;
				}
				if (ach2.ID == ach1.ID) {
					return 0;
				}
				if (ach2.ID > ach1.ID) {
					return -1;
				}
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

	public enum Category {
		TITLES(GOTFaction.NIGHT_WATCH), GENERAL(GOTFaction.SOTHORYOS), KILL(GOTFaction.WESTERLANDS), WEAR(GOTFaction.ARRYN), ENTER(GOTFaction.WHITE_WALKER), LEGENDARY(GOTFaction.YI_TI);

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
