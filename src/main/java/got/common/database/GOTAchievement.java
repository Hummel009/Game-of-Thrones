package got.common.database;

import java.awt.Color;
import java.util.*;

import got.common.*;
import got.common.faction.GOTFaction;
import got.common.util.GOTEnumDyeColor;
import got.common.world.biome.GOTBiome;
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
	public static GOTAchievement BANDIT;
	public static GOTAchievement BANNER_PROTECT;
	public static GOTAchievement BREW_DRINK_IN_BARREL;
	public static GOTAchievement CRAFT_BOMB;
	public static GOTAchievement CRAFT_WILD_FIRE;
	public static GOTAchievement DEFEAT_INVASION;
	public static GOTAchievement DO_LEGENDARY_QUEST;
	public static GOTAchievement DO_QUEST;
	public static GOTAchievement STEAL;
	public static GOTAchievement DRINK_FIRE;
	public static GOTAchievement DRINK_TERMITE;
	public static GOTAchievement EARN_MANY_COINS;
	public static GOTAchievement ENTER_GOT;
	public static GOTAchievement FACTION_CONQUEST;
	public static GOTAchievement FIND_FOUR_LEAF_CLOVER;
	public static GOTAchievement FREEMAN;
	public static GOTAchievement GAIN_HIGH_ALCOHOL_TOLERANCE;
	public static GOTAchievement GET_BRONZE;
	public static GOTAchievement GET_CONCRETE;
	public static GOTAchievement GET_COPPER;
	public static GOTAchievement GET_POUCH;
	public static GOTAchievement HUNDREDS;
	public static GOTAchievement KILLER;
	public static GOTAchievement KILL_ALLISER_THORNE;
	public static GOTAchievement KILL_ASSHAI_ARCHMAG;
	public static GOTAchievement KILL_BALON_GREYJOY;
	public static GOTAchievement KILL_BERIC_DAYNE;
	public static GOTAchievement KILL_BERIC_DONDARRION;
	public static GOTAchievement KILL_BU_GAI;
	public static GOTAchievement KILL_CERSEI_LANNISTER;
	public static GOTAchievement KILL_CRASTER;
	public static GOTAchievement KILL_DAENERYS_TARGARYEN;
	public static GOTAchievement KILL_DAVOS_SEAWORTH;
	public static GOTAchievement KILL_EURON_GREYJOY;
	public static GOTAchievement KILL_GEROLD_DAYNE;
	public static GOTAchievement KILL_GIANT;
	public static GOTAchievement KILL_GLADIATOR;
	public static GOTAchievement KILL_GREGOR_CLEGANE;
	public static GOTAchievement KILL_HODOR;
	public static GOTAchievement KILL_HOWLAND_REED;
	public static GOTAchievement KILL_IBBEN;
	public static GOTAchievement KILL_ICE_SPIDER;
	public static GOTAchievement KILL_JAIME_LANNISTER;
	public static GOTAchievement KILL_JAQEN_HGHAR;
	public static GOTAchievement KILL_JEOR_MORMONT;
	public static GOTAchievement KILL_JON_SNOW;
	public static GOTAchievement KILL_JORAH_MORMONT;
	public static GOTAchievement KILL_KEVAN_LANNISTER;
	public static GOTAchievement KILL_KHAL;
	public static GOTAchievement KILL_KITRA_JUVAIN;
	public static GOTAchievement KILL_LANCEL_LANNISTER;
	public static GOTAchievement KILL_LORAS_TYRELL;
	public static GOTAchievement KILL_MAESTER;
	public static GOTAchievement KILL_MAMMOTH;
	public static GOTAchievement KILL_MELISANDRA;
	public static GOTAchievement KILL_NIGHT_KING;
	public static GOTAchievement KILL_NIGHT_WATCH_GUARD;
	public static GOTAchievement KILL_OBERYN_MARTELL;
	public static GOTAchievement KILL_PETYR_BAELISH;
	public static GOTAchievement KILL_RAMSAY_BOLTON;
	public static GOTAchievement KILL_ROBB_STARK;
	public static GOTAchievement KILL_ROOSE_BOLTON;
	public static GOTAchievement KILL_SAMURAI;
	public static GOTAchievement KILL_SANDOR_CLEGANE;
	public static GOTAchievement KILL_THENN_BERSERKER;
	public static GOTAchievement KILL_THE_KING;
	public static GOTAchievement KILL_TYRION;
	public static GOTAchievement KILL_TYWIN;
	public static GOTAchievement KILL_ULTHOS;
	public static GOTAchievement KILL_UNSULLIED;
	public static GOTAchievement KILL_VARYS;
	public static GOTAchievement KILL_VASSAL;
	public static GOTAchievement KILL_VICTARION_GREYJOY;
	public static GOTAchievement KILL_WEREWOLF;
	public static GOTAchievement KILL_WHITE_WALKER;
	public static GOTAchievement KILL_WHORE;
	public static GOTAchievement KILL_WIGHT;
	public static GOTAchievement KILL_WIGHT_GIANT;
	public static GOTAchievement KILL_WITCHER;
	public static GOTAchievement KILL_YARA_GREYJOY;
	public static GOTAchievement KILL_PRIEST;
	public static GOTAchievement MINE_VALYRIAN;
	public static GOTAchievement OBAMA;
	public static GOTAchievement PLEDGE_SERVICE;
	public static GOTAchievement REFORGE;
	public static GOTAchievement ENGRAVE;
	public static GOTAchievement SHOOT_DOWN_MIDGES;
	public static GOTAchievement STEAL_ARBOR_GRAPES;
	public static GOTAchievement TORMENT_THEON_GREYJOY;
	public static GOTAchievement TRADE;
	public static GOTAchievement TRAVEL10;
	public static GOTAchievement TRAVEL20;
	public static GOTAchievement TRAVEL30;
	public static GOTAchievement TRAVEL40;
	public static GOTAchievement TRAVEL50;
	public static GOTAchievement UNSMELT;
	public static GOTAchievement VISIT_ALWAYS_WINTER;
	public static GOTAchievement VISIT_ARBOR;
	public static GOTAchievement VISIT_ARRYN;
	public static GOTAchievement VISIT_ARRYN_MOUNTAINS;
	public static GOTAchievement VISIT_ARRYN_TOWN;
	public static GOTAchievement VISIT_ASSHAI;
	public static GOTAchievement VISIT_BONE_MOUNTAINS;
	public static GOTAchievement VISIT_BRAAVOS;
	public static GOTAchievement VISIT_CANNIBAL_SANDS;
	public static GOTAchievement VISIT_COLD_COAST;
	public static GOTAchievement VISIT_CROWNLANDS;
	public static GOTAchievement VISIT_CROWNLANDS_FOREST;
	public static GOTAchievement VISIT_DORNE;
	public static GOTAchievement VISIT_DORNE_DESERT;
	public static GOTAchievement VISIT_DORNE_MESA;
	public static GOTAchievement VISIT_DORNE_MOUNTAINS;
	public static GOTAchievement VISIT_DOSH_KHALIN;
	public static GOTAchievement VISIT_DOTHRAKI_SEA;
	public static GOTAchievement VISIT_DRAGONSTONE;
	public static GOTAchievement VISIT_ESSOS_MOUNTAINS;
	public static GOTAchievement VISIT_FACES;
	public static GOTAchievement VISIT_FAR_NORTH;
	public static GOTAchievement VISIT_FIRE_FIELD;
	public static GOTAchievement VISIT_FROSTFANGS;
	public static GOTAchievement VISIT_GHISCAR;
	public static GOTAchievement VISIT_GHISCAR_COLONY;
	public static GOTAchievement VISIT_GIFT_NEW;
	public static GOTAchievement VISIT_GIFT_OLD;
	public static GOTAchievement VISIT_HAUNTED_FOREST;
	public static GOTAchievement VISIT_HILL_TRIBES;
	public static GOTAchievement VISIT_IBBEN;
	public static GOTAchievement VISIT_IFEKEVRON;
	public static GOTAchievement VISIT_IRONBORN;
	public static GOTAchievement VISIT_IRONTREE;
	public static GOTAchievement VISIT_JOGOS;
	public static GOTAchievement VISIT_JOGOS_DESERT;
	public static GOTAchievement VISIT_KINGS_LANDING;
	public static GOTAchievement VISIT_LHAZAR;
	public static GOTAchievement VISIT_LONG_SUMMER;
	public static GOTAchievement VISIT_LORATH;
	public static GOTAchievement VISIT_LYS;
	public static GOTAchievement VISIT_MERCENARY;
	public static GOTAchievement VISIT_MOSSOVY;
	public static GOTAchievement VISIT_MOSSOVY_MARSHES;
	public static GOTAchievement VISIT_MOSSOVY_SOPKAS;
	public static GOTAchievement VISIT_MYR;
	public static GOTAchievement VISIT_NAATH;
	public static GOTAchievement VISIT_NECK;
	public static GOTAchievement VISIT_NORTH;
	public static GOTAchievement VISIT_NORTH_BARROWS;
	public static GOTAchievement VISIT_NORTH_MOUNTAINS;
	public static GOTAchievement VISIT_NORTH_TOWN;
	public static GOTAchievement VISIT_NORTH_WILD;
	public static GOTAchievement VISIT_NORVOS;
	public static GOTAchievement VISIT_PENTOS;
	public static GOTAchievement VISIT_QARTH;
	public static GOTAchievement VISIT_QARTH_DESERT;
	public static GOTAchievement VISIT_QOHOR;
	public static GOTAchievement VISIT_QOHOR_FOREST;
	public static GOTAchievement VISIT_REACH;
	public static GOTAchievement VISIT_REACH_TOWN;
	public static GOTAchievement VISIT_RED_SEA;
	public static GOTAchievement VISIT_RIVERLANDS;
	public static GOTAchievement VISIT_SHADOW_LAND;
	public static GOTAchievement VISIT_SHADOW_MOUNTAINS;
	public static GOTAchievement VISIT_ULOS;
	public static GOTAchievement VISIT_SKAGOS;
	public static GOTAchievement VISIT_SKIRLING_PASS;
	public static GOTAchievement VISIT_SOTHORYOS_BUSHLAND;
	public static GOTAchievement VISIT_SOTHORYOS_DESERT;
	public static GOTAchievement VISIT_SOTHORYOS_FROST;
	public static GOTAchievement VISIT_SOTHORYOS_HELL;
	public static GOTAchievement VISIT_SOTHORYOS_JUNGLE;
	public static GOTAchievement VISIT_SOTHORYOS_KANUKA;
	public static GOTAchievement VISIT_SOTHORYOS_MANGROVE;
	public static GOTAchievement VISIT_SOTHORYOS_MOUNTAINS;
	public static GOTAchievement VISIT_SOTHORYOS_SAVANNAH;
	public static GOTAchievement VISIT_SOTHORYOS_TAIGA;
	public static GOTAchievement VISIT_STEPSTONES;
	public static GOTAchievement VISIT_STONE_COAST;
	public static GOTAchievement VISIT_STORMLANDS;
	public static GOTAchievement VISIT_SUMMER_ISLANDS;
	public static GOTAchievement VISIT_TARTH;
	public static GOTAchievement VISIT_THENN;
	public static GOTAchievement VISIT_TROPICAL_FOREST;
	public static GOTAchievement VISIT_TYROSH;
	public static GOTAchievement VISIT_ULTHOS;
	public static GOTAchievement VISIT_ULTHOS_DESERT;
	public static GOTAchievement VISIT_ULTHOS_MOUNTAINS;
	public static GOTAchievement VISIT_VALYRIA;
	public static GOTAchievement VISIT_VALYRIA_SEA;
	public static GOTAchievement VISIT_VALYRIA_VOLCANO;
	public static GOTAchievement VISIT_VOLANTIS;
	public static GOTAchievement VISIT_VOLANTIS_FOREST;
	public static GOTAchievement VISIT_WESTERLANDS;
	public static GOTAchievement VISIT_WESTERLANDS_HILLS;
	public static GOTAchievement VISIT_WESTERLANDS_TOWN;
	public static GOTAchievement VISIT_WHISPERING_WOOD;
	public static GOTAchievement VISIT_YEEN;
	public static GOTAchievement VISIT_YI_TI;
	public static GOTAchievement VISIT_YI_TI_WASTELAND;
	public static GOTAchievement WEAR_FULL_ARRYN;
	public static GOTAchievement WEAR_FULL_ARRYNGUARD;
	public static GOTAchievement WEAR_FULL_ASSHAI;
	public static GOTAchievement WEAR_FULL_BLACKFYRE;
	public static GOTAchievement WEAR_FULL_BONE;
	public static GOTAchievement WEAR_FULL_BRAAVOS;
	public static GOTAchievement WEAR_FULL_BRONZE;
	public static GOTAchievement WEAR_FULL_CROWNLANDS;
	public static GOTAchievement WEAR_FULL_DORNE;
	public static GOTAchievement WEAR_FULL_DRAGONSTONE;
	public static GOTAchievement WEAR_FULL_FUR;
	public static GOTAchievement WEAR_FULL_GEMSBOK;
	public static GOTAchievement WEAR_FULL_GHISCAR;
	public static GOTAchievement WEAR_FULL_GIFT;
	public static GOTAchievement WEAR_FULL_GOLDENCOMPANY;
	public static GOTAchievement WEAR_FULL_HAND;
	public static GOTAchievement WEAR_FULL_HELMET;
	public static GOTAchievement WEAR_FULL_HILLMEN;
	public static GOTAchievement WEAR_FULL_IRONBORN;
	public static GOTAchievement WEAR_FULL_KINGSGUARD;
	public static GOTAchievement WEAR_FULL_LHAZAR;
	public static GOTAchievement WEAR_FULL_LHAZAR_LION;
	public static GOTAchievement WEAR_FULL_LORATH;
	public static GOTAchievement WEAR_FULL_LYS;
	public static GOTAchievement WEAR_FULL_MOSSOVY;
	public static GOTAchievement WEAR_FULL_MYR;
	public static GOTAchievement WEAR_FULL_NORTH;
	public static GOTAchievement WEAR_FULL_NORTHGUARD;
	public static GOTAchievement WEAR_FULL_NORVOS;
	public static GOTAchievement WEAR_FULL_PENTOS;
	public static GOTAchievement WEAR_FULL_QARTH;
	public static GOTAchievement WEAR_FULL_QOHOR;
	public static GOTAchievement WEAR_FULL_REACH;
	public static GOTAchievement WEAR_FULL_REACHGUARD;
	public static GOTAchievement WEAR_FULL_REDKING;
	public static GOTAchievement WEAR_FULL_RIVERLANDS;
	public static GOTAchievement WEAR_FULL_ROBES;
	public static GOTAchievement WEAR_FULL_ROYCE;
	public static GOTAchievement WEAR_FULL_SOTHORYOS;
	public static GOTAchievement WEAR_FULL_SOTHORYOS_GOLD;
	public static GOTAchievement WEAR_FULL_STORMLANDS;
	public static GOTAchievement WEAR_FULL_SUMMER;
	public static GOTAchievement WEAR_FULL_TARGARYEN;
	public static GOTAchievement WEAR_FULL_TYROSH;
	public static GOTAchievement WEAR_FULL_UNSULLIED;
	public static GOTAchievement WEAR_FULL_VALYRIAN;
	public static GOTAchievement WEAR_FULL_VOLANTIS;
	public static GOTAchievement WEAR_FULL_WESTERLANDS;
	public static GOTAchievement WEAR_FULL_WESTERLANDSGUARD;
	public static GOTAchievement WEAR_FULL_WESTKING;
	public static GOTAchievement WEAR_FULL_WHITEWALKERS;
	public static GOTAchievement WEAR_FULL_YITI;
	public static GOTAchievement WEAR_FULL_YITI_SAMURAI;
	public static GOTAchievement WEAR_FULL_YITI_FRONTIER;
	public static GOTAchievement WEAR_FULL_NOMAD;
	public static GOTAchievement KILL_THOROS;
	public static GOTAchievement KILL_BARRISTAN_SELMY;
	public static GOTAchievement KILL_XARO_XHOAN_DAXOS;
	public static GOTAchievement KILL_GENDRY_BARATHEON;
	public static GOTAchievement KILL_DORAN_MARTELL;
	public static GOTAchievement KILL_ILLYRIO_MOPATIS;
	public static GOTAchievement KILL_BENJEN_STARK;
	public static GOTAchievement KILL_BRIENNE_TARTH;
	public static GOTAchievement KILL_SANSA_STARK;
	public static GOTAchievement KILL_TORMUND;
	public static GOTAchievement KILL_YGRITTE;
	public static GOTAchievement KILL_MANCE_RAYDER;
	public static GOTAchievement KILL_MACE_TYRELL;
	public static GOTAchievement KILL_MARGAERY_TYRELL;
	public static GOTAchievement KILL_ELLARYA_SAND;
	public static GOTAchievement KILL_BRYNDEN_TULLY;
	public static GOTAchievement KILL_EDMURE_TULLY;
	public static GOTAchievement KILL_ARDRIAN_CELTIGAR;
	public static GOTAchievement KILL_KRAZNYS_MO_NAKLOZ;
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

		getDimension().getDimensionName();
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
		ChatComponentTranslation component = new ChatComponentTranslation(getTitle(entityplayer)).createCopy();
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
		return StatCollector.translateToLocal(getUntranslatedTitle());
	}

	public String getUntranslatedTitle() {
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

	public GOTAchievement setRequiresAlly(List<GOTFaction> f) {
		return this.setRequiresAlly(f.toArray(new GOTFaction[0]));
	}

	public GOTAchievement setRequiresEnemy(GOTFaction... f) {
		enemyFactions.addAll(Arrays.asList(f));
		return this;
	}

	public GOTAchievement setRequiresEnemy(List<GOTFaction> f) {
		return this.setRequiresEnemy(f.toArray(new GOTFaction[0]));
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
		if (!(item instanceof ItemArmor)) {
			throw new IllegalArgumentException("Invalid armor achievement item, name: " + name + " for GOT achievement category " + category);
		}
		GOTAchievement achievement = new GOTAchievement(category, id, item, name);
		armorAchievements.put(((ItemArmor) item).getArmorMaterial(), achievement);
		return achievement;
	}

	public static GOTAchievement findByName(String name) {
		for (Category category : Category.values()) {
			for (GOTAchievement achievement : category.list) {
				if (!achievement.getCodeName().equalsIgnoreCase(name)) {
					continue;
				}
				return achievement;
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
		BANDIT = new GOTAchievement(Category.KILL, id++, GOTRegistry.westerosDagger, "BANDIT");
		BANNER_PROTECT = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.banner, "BANNER_PROTECT");
		BREW_DRINK_IN_BARREL = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.barrel, "BREW_DRINK_IN_BARREL");
		CRAFT_BOMB = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.bomb, "CRAFT_BOMB");
		CRAFT_WILD_FIRE = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.wildFireJar, "CRAFT_WILD_FIRE");
		DEFEAT_INVASION = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.gregorCleganeSword, "DEFEAT_INVASION");
		DO_LEGENDARY_QUEST = new GOTAchievement(Category.GENERAL, id++, Blocks.dragon_egg, "DO_LEGENDARY_QUEST");
		DO_QUEST = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.questBook, "DO_QUEST");
		DRINK_FIRE = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.mugWildFire, "DRINK_FIRE");
		DRINK_TERMITE = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.mugTermiteTequila, "DRINK_TERMITE");
		EARN_MANY_COINS = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.coin, "EARN_MANY_COINS");
		ENTER_GOT = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.gregorCleganeSword, "ENTER_GOT");
		FACTION_CONQUEST = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.gregorCleganeSword, "FACTION_CONQUEST");
		FIND_FOUR_LEAF_CLOVER = new GOTAchievement(Category.GENERAL, id++, new ItemStack(GOTRegistry.clover, 1, 1), "FIND_FOUR_LEAF_CLOVER");
		FREEMAN = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.crowbar, "FREEMAN");
		GAIN_HIGH_ALCOHOL_TOLERANCE = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.mugAle, "GAIN_HIGH_ALCOHOL_TOLERANCE");
		GET_BRONZE = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.bronzeIngot, "GET_BRONZE");
		GET_CONCRETE = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.concretePowder.get(GOTEnumDyeColor.LIME), "GET_CONCRETE");
		GET_COPPER = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.copperIngot, "GET_COPPER");
		GET_POUCH = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.pouch, "GET_POUCH");
		HUNDREDS = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.gregorCleganeSword, "HUNDREDS");
		KILLER = new GOTAchievement(Category.KILL, id++, Items.iron_axe, "KILLER");
		KILL_ALLISER_THORNE = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westerosSword, "KILL_ALLISER_THORNE").createTitle();
		KILL_ASSHAI_ARCHMAG = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.archmagStaff, "KILL_ASSHAI_ARCHMAG").createTitle();
		KILL_BALON_GREYJOY = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.euronDagger, "KILL_BALON_GREYJOY");
		KILL_BERIC_DAYNE = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.dawn, "KILL_BERIC_DAYNE");
		KILL_BERIC_DONDARRION = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.bericSword, "KILL_BERIC_DONDARRION").createTitle();
		KILL_BU_GAI = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.yitiSword, "KILL_BU_GAI").createTitle();
		KILL_CERSEI_LANNISTER = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.wildFireJar, "KILL_CERSEI_LANNISTER");
		KILL_CRASTER = new GOTAchievement(Category.LEGENDARY, id++, Items.iron_axe, "KILL_CRASTER").createTitle();
		KILL_DAENERYS_TARGARYEN = new GOTAchievement(Category.LEGENDARY, id++, Blocks.dragon_egg, "KILL_DAENERYS_TARGARYEN").createTitle();
		KILL_DAVOS_SEAWORTH = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.leek, "KILL_DAVOS_SEAWORTH");
		KILL_EURON_GREYJOY = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.euronDagger, "KILL_EURON_GREYJOY");
		KILL_GEROLD_DAYNE = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.darkstar, "KILL_GEROLD_DAYNE");
		KILL_GIANT = new GOTAchievement(Category.KILL, id++, GOTRegistry.club, "KILL_GIANT");
		KILL_GLADIATOR = new GOTAchievement(Category.KILL, id++, GOTRegistry.essosSword, "KILL_GLADIATOR");
		KILL_GREGOR_CLEGANE = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.gregorCleganeSword, "KILL_GREGOR_CLEGANE").createTitle();
		KILL_HODOR = new GOTAchievement(Category.LEGENDARY, id++, Items.stick, "KILL_HODOR");
		KILL_HOWLAND_REED = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.reeds, "KILL_HOWLAND_REED");
		KILL_IBBEN = new GOTAchievement(Category.KILL, id++, GOTRegistry.flintSpear, "KILL_IBBEN");
		KILL_ICE_SPIDER = new GOTAchievement(Category.KILL, id++, GOTRegistry.valyrianSword, "KILL_ICE_SPIDER");
		KILL_JAIME_LANNISTER = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.jaimeSword, "KILL_JAIME_LANNISTER");
		KILL_JAQEN_HGHAR = new GOTAchievement(Category.LEGENDARY, id++, new ItemStack(GOTRegistry.coin, 1, 1), "KILL_JAQEN_HGHAR").createTitle();
		KILL_JEOR_MORMONT = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.longclaw, "KILL_JEOR_MORMONT").createTitle();
		KILL_JON_SNOW = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.longclaw, "KILL_JON_SNOW").createTitle();
		KILL_JORAH_MORMONT = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.harpy, "KILL_JORAH_MORMONT");
		KILL_KEVAN_LANNISTER = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.handGold, "KILL_KEVAN_LANNISTER");
		KILL_KHAL = new GOTAchievement(Category.KILL, id++, GOTRegistry.lhazarSword, "KILL_KHAL");
		KILL_KITRA_JUVAIN = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.nomadSword, "KILL_KITRA_JUVAIN").createTitle();
		KILL_LANCEL_LANNISTER = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.gregorCleganeSword, "KILL_LANCEL_LANNISTER");
		KILL_LORAS_TYRELL = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.gregorCleganeSword, "KILL_LORAS_TYRELL");
		KILL_MAESTER = new GOTAchievement(Category.KILL, id++, Items.book, "KILL_MAESTER");
		KILL_MAMMOTH = new GOTAchievement(Category.KILL, id++, GOTRegistry.stoneSpear, "KILL_MAMMOTH");
		KILL_MELISANDRA = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.lightbringer, "KILL_MELISANDRA");
		KILL_NIGHT_KING = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.nightKingSword, "KILL_NIGHT_KING").createTitle();
		KILL_NIGHT_WATCH_GUARD = new GOTAchievement(Category.KILL, id++, GOTRegistry.westerosSword, "KILL_NIGHT_WATCH_GUARD");
		KILL_OBERYN_MARTELL = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.sunspear, "KILL_OBERYN_MARTELL").createTitle();
		KILL_PETYR_BAELISH = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.baelishDagger, "KILL_PETYR_BAELISH");
		KILL_RAMSAY_BOLTON = new GOTAchievement(Category.LEGENDARY, id++, Items.bone, "KILL_RAMSAY_BOLTON");
		KILL_ROBB_STARK = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.robbSword, "KILL_ROBB_STARK");
		KILL_ROOSE_BOLTON = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.boltonDagger, "KILL_ROOSE_BOLTON");
		KILL_SAMURAI = new GOTAchievement(Category.KILL, id++, GOTRegistry.katana, "KILL_SAMURAI");
		KILL_SANDOR_CLEGANE = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.sandorCleganeSword, "KILL_SANDOR_CLEGANE").createTitle();
		KILL_THENN_BERSERKER = new GOTAchievement(Category.KILL, id++, GOTRegistry.wildlingSword, "KILL_THENN_BERSERKER");
		KILL_THE_KING = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.aegonHelmet, "KILL_THE_KING").createTitle();
		KILL_TYRION = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.mugRedWine, "KILL_TYRION");
		KILL_TYWIN = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westkingHelmet, "KILL_TYWIN").createTitle();
		KILL_ULTHOS = new GOTAchievement(Category.KILL, id++, GOTRegistry.mysteryWeb, "KILL_ULTHOS");
		KILL_UNSULLIED = new GOTAchievement(Category.KILL, id++, GOTRegistry.essosPike, "KILL_UNSULLIED");
		KILL_VARYS = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westerosSword, "KILL_VARYS");
		KILL_VASSAL = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westerosSword, "KILL_VASSAL");
		KILL_VICTARION_GREYJOY = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.victarionAxe, "KILL_VICTARION_GREYJOY");
		KILL_WEREWOLF = new GOTAchievement(Category.KILL, id++, GOTRegistry.mossovySword, "KILL_WEREWOLF");
		KILL_WHITE_WALKER = new GOTAchievement(Category.KILL, id++, GOTRegistry.valyrianSword, "KILL_WHITE_WALKER");
		KILL_WHORE = new GOTAchievement(Category.KILL, id++, GOTRegistry.ironCrossbow, "KILL_WHORE");
		KILL_WIGHT = new GOTAchievement(Category.KILL, id++, GOTRegistry.bericSword, "KILL_WIGHT");
		KILL_WIGHT_GIANT = new GOTAchievement(Category.KILL, id++, GOTRegistry.bericSword, "KILL_WIGHT_GIANT");
		KILL_WITCHER = new GOTAchievement(Category.KILL, id++, GOTRegistry.mossovySword, "KILL_WITCHER");
		KILL_YARA_GREYJOY = new GOTAchievement(Category.LEGENDARY, id++, Items.iron_axe, "KILL_YARA_GREYJOY");
		KILL_THOROS = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westerosSword, "KILL_THOROS");
		KILL_BARRISTAN_SELMY = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.westerosSword, "KILL_BARRISTAN_SELMY");
		KILL_XARO_XHOAN_DAXOS = new GOTAchievement(Category.LEGENDARY, id++, Items.gold_ingot, "KILL_XARO_XHOAN_DAXOS");
		KILL_GENDRY_BARATHEON = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.gendryHammer, "KILL_GENDRY_BARATHEON");
		KILL_DORAN_MARTELL = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.tyeneDagger, "KILL_DORAN_MARTELL");
		KILL_ILLYRIO_MOPATIS = new GOTAchievement(Category.LEGENDARY, id++, Items.gold_ingot, "KILL_ILLYRIO_MOPATIS");
		KILL_BENJEN_STARK = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.valyrianSword, "KILL_BENJEN_STARK");
		KILL_BRIENNE_TARTH = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.sapphire, "KILL_BRIENNE_TARTH");
		KILL_SANSA_STARK = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.justMaid, "KILL_SANSA_STARK");
		KILL_TORMUND = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.tormundSword, "KILL_TORMUND");
		KILL_YGRITTE = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.wildlingDagger, "KILL_YGRITTE");
		KILL_MANCE_RAYDER = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.wildlingSword, "KILL_MANCE_RAYDER");
		KILL_MACE_TYRELL = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.wildFire, "KILL_MACE_TYRELL");
		KILL_MARGAERY_TYRELL = new GOTAchievement(Category.LEGENDARY, id++, Blocks.red_flower, "KILL_MARGAERY_TYRELL");
		KILL_ELLARYA_SAND = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.bottlePoison, "KILL_ELLARYA_SAND").createTitle();
		KILL_BRYNDEN_TULLY = new GOTAchievement(Category.LEGENDARY, id++, Items.fish, "KILL_BRYNDEN_TULLY").createTitle();
		KILL_EDMURE_TULLY = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.arrowFire, "KILL_EDMURE_TULLY");
		KILL_ARDRIAN_CELTIGAR = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.celtigarAxe, "KILL_ARDRIAN_CELTIGAR").createTitle();
		KILL_KRAZNYS_MO_NAKLOZ = new GOTAchievement(Category.LEGENDARY, id++, Blocks.dragon_egg, "KILL_KRAZNYS_MO_NAKLOZ");
		KILL_PRIEST = new GOTAchievement(Category.LEGENDARY, id++, Blocks.fire, "KILL_PRIEST");
		MINE_VALYRIAN = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.oreValyrian, "MINE_VALYRIAN");
		OBAMA = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.banana, "OBAMA");
		PLEDGE_SERVICE = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.gregorCleganeSword, "PLEDGE_SERVICE");
		REFORGE = new GOTAchievement(Category.GENERAL, id++, Blocks.anvil, "REFORGE");
		SHOOT_DOWN_MIDGES = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.ironCrossbow, "SHOOT_DOWN_MIDGES");
		STEAL_ARBOR_GRAPES = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.grapeRed, "STEAL_ARBOR_GRAPES");
		TORMENT_THEON_GREYJOY = new GOTAchievement(Category.LEGENDARY, id++, GOTRegistry.boltonDagger, "TORMENT_THEON_GREYJOY");
		TRADE = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.coin, "TRADE");
		TRAVEL10 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL10");
		TRAVEL20 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL20");
		TRAVEL30 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL30");
		TRAVEL40 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL40");
		TRAVEL50 = new GOTAchievement(Category.GENERAL, id++, Items.map, "TRAVEL50");
		UNSMELT = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.unsmeltery, "UNSMELT");
		ENGRAVE = new GOTAchievement(Category.GENERAL, id++, Blocks.anvil, "ENGRAVE");
		STEAL = new GOTAchievement(Category.GENERAL, id++, GOTRegistry.coin, "STEAL");
		VISIT_ALWAYS_WINTER = new GOTAchievement(Category.VISIT, id++, Blocks.ice, "VISIT_ALWAYS_WINTER");
		VISIT_ARBOR = new GOTAchievement(Category.VISIT, id++, GOTRegistry.grapeRed, "VISIT_ARBOR");
		VISIT_ARRYN = new GOTAchievement(Category.VISIT, id++, GOTRegistry.arrynHelmet, "VISIT_ARRYN");
		VISIT_ARRYN_MOUNTAINS = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTRegistry.rock, 1, 1), "VISIT_ARRYN_MOUNTAINS");
		VISIT_ARRYN_TOWN = new GOTAchievement(Category.VISIT, id++, GOTRegistry.arrynguardHelmet, "VISIT_ARRYN_TOWN");
		VISIT_ASSHAI = new GOTAchievement(Category.VISIT, id++, GOTRegistry.asshaiStaff, "VISIT_ASSHAI");
		VISIT_BONE_MOUNTAINS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.boneBlock, "VISIT_BONE_MOUNTAINS");
		VISIT_BRAAVOS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.braavosHelmet, "VISIT_BRAAVOS");
		VISIT_CANNIBAL_SANDS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.quicksand, "VISIT_CANNIBAL_SANDS");
		VISIT_COLD_COAST = new GOTAchievement(Category.VISIT, id++, GOTRegistry.wildlingPolearm, "VISIT_COLD_COAST");
		VISIT_CROWNLANDS_FOREST = new GOTAchievement(Category.VISIT, id++, GOTRegistry.mugRedWine, "VISIT_CROWNLANDS_FOREST");
		VISIT_DORNE = new GOTAchievement(Category.VISIT, id++, GOTRegistry.dorneHelmet, "VISIT_DORNE");
		VISIT_DORNE_DESERT = new GOTAchievement(Category.VISIT, id++, Blocks.sand, "VISIT_DORNE_DESERT");
		VISIT_DORNE_MESA = new GOTAchievement(Category.VISIT, id++, Blocks.clay, "VISIT_DORNE_MESA");
		VISIT_DORNE_MOUNTAINS = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTRegistry.rock, 1, 4), "VISIT_DORNE_MOUNTAINS");
		VISIT_DOSH_KHALIN = new GOTAchievement(Category.VISIT, id++, Blocks.stone, "VISIT_DOSH_KHALIN");
		VISIT_DOTHRAKI_SEA = new GOTAchievement(Category.VISIT, id++, GOTRegistry.nomadSword, "VISIT_DOTHRAKI_SEA");
		VISIT_DRAGONSTONE = new GOTAchievement(Category.VISIT, id++, GOTRegistry.obsidianShard, "VISIT_DRAGONSTONE");
		VISIT_ESSOS_MOUNTAINS = new GOTAchievement(Category.VISIT, id++, Blocks.stone, "VISIT_ESSOS_MOUNTAINS");
		VISIT_FACES = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTRegistry.leaves9, 1, 2), "VISIT_FACES");
		VISIT_FAR_NORTH = new GOTAchievement(Category.VISIT, id++, Blocks.snow, "VISIT_FAR_NORTH");
		VISIT_FIRE_FIELD = new GOTAchievement(Category.VISIT, id++, Blocks.red_flower, "VISIT_FIRE_FIELD");
		VISIT_FROSTFANGS = new GOTAchievement(Category.VISIT, id++, Blocks.packed_ice, "VISIT_FROSTFANGS");
		VISIT_GHISCAR = new GOTAchievement(Category.VISIT, id++, GOTRegistry.harpy, "VISIT_GHISCAR");
		VISIT_GHISCAR_COLONY = new GOTAchievement(Category.VISIT, id++, GOTRegistry.ghiscarHelmet, "VISIT_GHISCAR_COLONY");
		VISIT_GIFT_NEW = new GOTAchievement(Category.VISIT, id++, Blocks.snow, "VISIT_GIFT_NEW");
		VISIT_GIFT_OLD = new GOTAchievement(Category.VISIT, id++, GOTRegistry.brickIce, "VISIT_GIFT_OLD");
		VISIT_HAUNTED_FOREST = new GOTAchievement(Category.VISIT, id++, GOTRegistry.club, "VISIT_HAUNTED_FOREST");
		VISIT_HILL_TRIBES = new GOTAchievement(Category.VISIT, id++, GOTRegistry.trident, "VISIT_HILL_TRIBES");
		VISIT_IBBEN = new GOTAchievement(Category.VISIT, id++, GOTRegistry.flintDagger, "VISIT_IBBEN");
		VISIT_IFEKEVRON = new GOTAchievement(Category.VISIT, id++, GOTRegistry.flintSpear, "VISIT_IFEKEVRON");
		VISIT_IRONBORN = new GOTAchievement(Category.VISIT, id++, GOTRegistry.ironbornHelmet, "VISIT_IRONBORN");
		VISIT_IRONTREE = new GOTAchievement(Category.VISIT, id++, Items.iron_axe, "VISIT_IRONTREE");
		VISIT_JOGOS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.nomadBow, "VISIT_JOGOS");
		VISIT_JOGOS_DESERT = new GOTAchievement(Category.VISIT, id++, GOTRegistry.quicksand, "VISIT_JOGOS_DESERT");
		VISIT_KINGS_LANDING = new GOTAchievement(Category.VISIT, id++, GOTRegistry.crownlandsHelmet, "VISIT_KINGS_LANDING");
		VISIT_LHAZAR = new GOTAchievement(Category.VISIT, id++, GOTRegistry.lhazarHelmet, "VISIT_LHAZAR");
		VISIT_LONG_SUMMER = new GOTAchievement(Category.VISIT, id++, Blocks.fire, "VISIT_LONG_SUMMER");
		VISIT_LORATH = new GOTAchievement(Category.VISIT, id++, GOTRegistry.lorathHelmet, "VISIT_LORATH");
		VISIT_LYS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.lysHelmet, "VISIT_LYS");
		VISIT_MERCENARY = new GOTAchievement(Category.VISIT, id++, GOTRegistry.goldHelmet, "VISIT_MERCENARY");
		VISIT_MOSSOVY = new GOTAchievement(Category.VISIT, id++, GOTRegistry.mossovySword, "VISIT_MOSSOVY");
		VISIT_MOSSOVY_MARSHES = new GOTAchievement(Category.VISIT, id++, GOTRegistry.reeds, "VISIT_MOSSOVY_MARSHES");
		VISIT_MOSSOVY_SOPKAS = new GOTAchievement(Category.VISIT, id++, Blocks.stone, "VISIT_MOSSOVY_SOPKAS");
		VISIT_MYR = new GOTAchievement(Category.VISIT, id++, GOTRegistry.myrHelmet, "VISIT_MYR");
		VISIT_NAATH = new GOTAchievement(Category.VISIT, id++, GOTRegistry.summerHelmet, "VISIT_NAATH");
		VISIT_NECK = new GOTAchievement(Category.VISIT, id++, GOTRegistry.quagmire, "VISIT_NECK");
		VISIT_NORTH = new GOTAchievement(Category.VISIT, id++, GOTRegistry.northHelmet, "VISIT_NORTH");
		VISIT_NORTH_BARROWS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.coin, "VISIT_NORTH_BARROWS");
		VISIT_NORTH_MOUNTAINS = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTRegistry.rock, 1, 1), "VISIT_NORTH_MOUNTAINS");
		VISIT_NORTH_TOWN = new GOTAchievement(Category.VISIT, id++, GOTRegistry.northguardHelmet, "VISIT_NORTH_TOWN");
		VISIT_NORTH_WILD = new GOTAchievement(Category.VISIT, id++, GOTRegistry.trident, "VISIT_NORTH_WILD");
		VISIT_NORVOS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.norvosHelmet, "VISIT_NORVOS");
		VISIT_PENTOS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.pentosHelmet, "VISIT_PENTOS");
		VISIT_QARTH = new GOTAchievement(Category.VISIT, id++, GOTRegistry.qarthHelmet, "VISIT_QARTH");
		VISIT_QARTH_DESERT = new GOTAchievement(Category.VISIT, id++, GOTRegistry.redSandstone, "VISIT_DORNE_DESERT");
		VISIT_QOHOR = new GOTAchievement(Category.VISIT, id++, GOTRegistry.qohorHelmet, "VISIT_QOHOR");
		VISIT_QOHOR_FOREST = new GOTAchievement(Category.VISIT, id++, GOTRegistry.qohorHelmet, "VISIT_QOHOR_FOREST");
		VISIT_REACH = new GOTAchievement(Category.VISIT, id++, GOTRegistry.reachHelmet, "VISIT_REACH");
		VISIT_REACH_TOWN = new GOTAchievement(Category.VISIT, id++, GOTRegistry.reachguardHelmet, "VISIT_REACH_TOWN");
		VISIT_RED_SEA = new GOTAchievement(Category.VISIT, id++, GOTRegistry.bronzeSword, "VISIT_RED_SEA");
		VISIT_RIVERLANDS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.riverlandsHelmet, "VISIT_RIVERLANDS");
		VISIT_SHADOW_LAND = new GOTAchievement(Category.VISIT, id++, GOTRegistry.asshaiFlower, "VISIT_SHADOW_LAND");
		VISIT_SHADOW_MOUNTAINS = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTRegistry.rock, 1, 0), "VISIT_SHADOW_MOUNTAINS");
		VISIT_ULOS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.ulthosTorch, "VISIT_ULOS");
		VISIT_SKAGOS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.trident, "VISIT_SKAGOS");
		VISIT_SKIRLING_PASS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.club, "VISIT_SKIRLING_PASS");
		VISIT_SOTHORYOS_BUSHLAND = new GOTAchievement(Category.VISIT, id++, GOTRegistry.termite, "VISIT_SOTHORYOS_BUSHLAND");
		VISIT_SOTHORYOS_DESERT = new GOTAchievement(Category.VISIT, id++, Blocks.sand, "VISIT_SOTHORYOS_DESERT");
		VISIT_SOTHORYOS_FROST = new GOTAchievement(Category.VISIT, id++, Blocks.packed_ice, "VISIT_SOTHORYOS_FROST");
		VISIT_SOTHORYOS_HELL = new GOTAchievement(Category.VISIT, id++, GOTRegistry.sothoryosAmulet, "VISIT_SOTHORYOS_HELL");
		VISIT_SOTHORYOS_JUNGLE = new GOTAchievement(Category.VISIT, id++, GOTRegistry.sothoryosHelmet, "VISIT_SOTHORYOS_JUNGLE");
		VISIT_SOTHORYOS_KANUKA = new GOTAchievement(Category.VISIT, id++, GOTRegistry.sothoryosHelmetGold, "VISIT_SOTHORYOS_KANUKA");
		VISIT_SOTHORYOS_MANGROVE = new GOTAchievement(Category.VISIT, id++, Blocks.water, "VISIT_SOTHORYOS_MANGROVE");
		VISIT_SOTHORYOS_MOUNTAINS = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTRegistry.rock, 1, 6), "VISIT_SOTHORYOS_MOUNTAINS");
		VISIT_SOTHORYOS_SAVANNAH = new GOTAchievement(Category.VISIT, id++, Blocks.grass, "VISIT_SOTHORYOS_SAVANNAH");
		VISIT_SOTHORYOS_TAIGA = new GOTAchievement(Category.VISIT, id++, GOTRegistry.club, "VISIT_SOTHORYOS_TAIGA");
		VISIT_STEPSTONES = new GOTAchievement(Category.VISIT, id++, GOTRegistry.essosBow, "VISIT_STEPSTONES");
		VISIT_STONE_COAST = new GOTAchievement(Category.VISIT, id++, GOTRegistry.westerosDaggerPoisoned, "VISIT_STONE_COAST");
		VISIT_STORMLANDS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.stormlandsHelmet, "VISIT_STORMLANDS");
		VISIT_SUMMER_ISLANDS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.summerHelmet, "VISIT_SUMMER_ISLANDS");
		VISIT_TARTH = new GOTAchievement(Category.VISIT, id++, GOTRegistry.whiteSandstone, "VISIT_TARTH");
		VISIT_THENN = new GOTAchievement(Category.VISIT, id++, GOTRegistry.wildlingBattleaxe, "VISIT_THENN");
		VISIT_TROPICAL_FOREST = new GOTAchievement(Category.VISIT, id++, GOTRegistry.yitiHelmetShogune, "VISIT_TROPICAL_FOREST");
		VISIT_TYROSH = new GOTAchievement(Category.VISIT, id++, GOTRegistry.tyroshHelmet, "VISIT_TYROSH");
		VISIT_ULTHOS = new GOTAchievement(Category.VISIT, id++, Blocks.web, "VISIT_ULTHOS");
		VISIT_ULTHOS_DESERT = new GOTAchievement(Category.VISIT, id++, Blocks.sand, "VISIT_ULTHOS_DESERT");
		VISIT_ULTHOS_MOUNTAINS = new GOTAchievement(Category.VISIT, id++, new ItemStack(GOTRegistry.rock, 1, 3), "VISIT_ULTHOS_MOUNTAINS");
		VISIT_VALYRIA = new GOTAchievement(Category.VISIT, id++, Blocks.vine, "VISIT_VALYRIA");
		VISIT_VALYRIA_SEA = new GOTAchievement(Category.VISIT, id++, Blocks.water, "VISIT_VALYRIA_SEA");
		VISIT_VALYRIA_VOLCANO = new GOTAchievement(Category.VISIT, id++, Blocks.lava, "VISIT_VALYRIA_VOLCANO");
		VISIT_VOLANTIS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.volantisHelmet, "VISIT_VOLANTIS");
		VISIT_VOLANTIS_FOREST = new GOTAchievement(Category.VISIT, id++, GOTRegistry.orange, "VISIT_VOLANTIS_FOREST");
		VISIT_WESTERLANDS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.westerlandsHelmet, "VISIT_WESTERLANDS");
		VISIT_WESTERLANDS_HILLS = new GOTAchievement(Category.VISIT, id++, Blocks.gold_ore, "VISIT_WESTERLANDS_HILLS");
		VISIT_WESTERLANDS_TOWN = new GOTAchievement(Category.VISIT, id++, GOTRegistry.westerlandsguardHelmet, "VISIT_WESTERLANDS_TOWN");
		VISIT_WHISPERING_WOOD = new GOTAchievement(Category.VISIT, id++, GOTRegistry.westerosPike, "VISIT_WHISPERING_WOOD");
		VISIT_YEEN = new GOTAchievement(Category.VISIT, id++, Blocks.obsidian, "VISIT_YEEN");
		VISIT_YI_TI = new GOTAchievement(Category.VISIT, id++, GOTRegistry.yitiHelmet, "VISIT_YI_TI");
		VISIT_YI_TI_WASTELAND = new GOTAchievement(Category.VISIT, id++, GOTRegistry.yitiHelmetSamurai, "VISIT_YI_TI_WASTELAND");
		VISIT_CROWNLANDS = new GOTAchievement(Category.VISIT, id++, GOTRegistry.crownlandsHelmet, "VISIT_CROWNLANDS");
		WEAR_FULL_ARRYN = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.arrynChestplate, "WEAR_FULL_ARRYN");
		WEAR_FULL_ARRYNGUARD = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.arrynguardChestplate, "WEAR_FULL_ARRYNGUARD");
		WEAR_FULL_ASSHAI = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.asshaiChestplate, "WEAR_FULL_ASSHAI");
		WEAR_FULL_BLACKFYRE = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.blackfyreChestplate, "WEAR_FULL_BLACKFYRE");
		WEAR_FULL_BONE = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.boneChestplate, "WEAR_FULL_BONE");
		WEAR_FULL_BRAAVOS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.braavosChestplate, "WEAR_FULL_BRAAVOS");
		WEAR_FULL_BRONZE = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.bronzeChestplate, "WEAR_FULL_BRONZE");
		WEAR_FULL_CROWNLANDS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.crownlandsChestplate, "WEAR_FULL_CROWNLANDS");
		WEAR_FULL_DORNE = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.dorneChestplate, "WEAR_FULL_DORNE");
		WEAR_FULL_DRAGONSTONE = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.dragonstoneChestplate, "WEAR_FULL_DRAGONSTONE");
		WEAR_FULL_FUR = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.furChestplate, "WEAR_FULL_FUR");
		WEAR_FULL_GEMSBOK = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.gemsbokChestplate, "WEAR_FULL_GEMSBOK");
		WEAR_FULL_GHISCAR = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.ghiscarChestplate, "WEAR_FULL_GHISCAR");
		WEAR_FULL_GIFT = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.giftChestplate, "WEAR_FULL_GIFT");
		WEAR_FULL_GOLDENCOMPANY = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.goldChestplate, "WEAR_FULL_GOLDENCOMPANY");
		WEAR_FULL_HAND = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.handGold, "WEAR_FULL_HAND");
		WEAR_FULL_HELMET = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.robertHelmet, "WEAR_FULL_HELMET");
		WEAR_FULL_HILLMEN = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.hillmenChestplate, "WEAR_FULL_HILLMEN");
		WEAR_FULL_IRONBORN = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.ironbornChestplate, "WEAR_FULL_IRONBORN");
		WEAR_FULL_KINGSGUARD = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.kingsguardChestplate, "WEAR_FULL_KINGSGUARD");
		WEAR_FULL_LHAZAR = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.lhazarChestplate, "WEAR_FULL_LHAZAR");
		WEAR_FULL_LHAZAR_LION = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.lhazarChestplateLion, "WEAR_FULL_LHAZAR_LION");
		WEAR_FULL_LORATH = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.lorathChestplate, "WEAR_FULL_LORATH");
		WEAR_FULL_LYS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.lysChestplate, "WEAR_FULL_LYS");
		WEAR_FULL_MOSSOVY = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.mossovyChestplate, "WEAR_FULL_MOSSOVY");
		WEAR_FULL_MYR = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.myrChestplate, "WEAR_FULL_MYR");
		WEAR_FULL_NORTH = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.northChestplate, "WEAR_FULL_NORTH");
		WEAR_FULL_NORTHGUARD = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.northguardChestplate, "WEAR_FULL_NORTHGUARD");
		WEAR_FULL_NORVOS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.norvosChestplate, "WEAR_FULL_NORVOS");
		WEAR_FULL_PENTOS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.pentosChestplate, "WEAR_FULL_PENTOS");
		WEAR_FULL_QARTH = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.qarthChestplate, "WEAR_FULL_QARTH");
		WEAR_FULL_QOHOR = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.qohorChestplate, "WEAR_FULL_QOHOR");
		WEAR_FULL_REACH = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.reachChestplate, "WEAR_FULL_REACH");
		WEAR_FULL_REACHGUARD = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.reachguardChestplate, "WEAR_FULL_REACHGUARD");
		WEAR_FULL_REDKING = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.redkingChestplate, "WEAR_FULL_REDKING");
		WEAR_FULL_RIVERLANDS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.riverlandsChestplate, "WEAR_FULL_RIVERLANDS");
		WEAR_FULL_ROBES = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.robesChestplate, "WEAR_FULL_ROBES");
		WEAR_FULL_ROYCE = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.royceChestplate, "WEAR_FULL_ROYCE");
		WEAR_FULL_SOTHORYOS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.sothoryosChestplate, "WEAR_FULL_SOTHORYOS");
		WEAR_FULL_SOTHORYOS_GOLD = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.sothoryosChestplateGold, "WEAR_FULL_SOTHORYOS_GOLD");
		WEAR_FULL_STORMLANDS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.stormlandsChestplate, "WEAR_FULL_STORMLANDS");
		WEAR_FULL_SUMMER = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.summerChestplate, "WEAR_FULL_SUMMER");
		WEAR_FULL_TARGARYEN = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.targaryenChestplate, "WEAR_FULL_TARGARYEN");
		WEAR_FULL_TYROSH = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.tyroshChestplate, "WEAR_FULL_TYROSH");
		WEAR_FULL_UNSULLIED = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.unsulliedChestplate, "WEAR_FULL_UNSULLIED");
		WEAR_FULL_VALYRIAN = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.valyrianChestplate, "WEAR_FULL_VALYRIAN");
		WEAR_FULL_VOLANTIS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.volantisChestplate, "WEAR_FULL_VOLANTIS");
		WEAR_FULL_WESTERLANDS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.westerlandsChestplate, "WEAR_FULL_WESTERLANDS");
		WEAR_FULL_WESTERLANDSGUARD = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.westerlandsguardChestplate, "WEAR_FULL_WESTERLANDSGUARD");
		WEAR_FULL_WESTKING = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.westkingChestplate, "WEAR_FULL_WESTKING");
		WEAR_FULL_WHITEWALKERS = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.whiteWalkersChestplate, "WEAR_FULL_WHITEWALKERS");
		WEAR_FULL_YITI = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.yitiChestplate, "WEAR_FULL_YITI");
		WEAR_FULL_YITI_SAMURAI = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.yitiChestplateSamurai, "WEAR_FULL_YITI_SAMURAI");
		WEAR_FULL_YITI_FRONTIER = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.yitiChestplateFrontier, "WEAR_FULL_YITI_FRONTIER");
		WEAR_FULL_NOMAD = GOTAchievement.createArmorAchievement(Category.WEAR, id++, GOTRegistry.nomadChestplate, "WEAR_FULL_NOMAD");
	}

	public static Comparator<GOTAchievement> sortForDisplay(EntityPlayer entityplayer) {
		return new Comparator<GOTAchievement>() {

			@Override
			public int compare(GOTAchievement ach1, GOTAchievement ach2) {
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
			}
		};
	}

	public enum Category {
		TITLES(GOTFaction.NIGHT_WATCH), GENERAL(GOTFaction.SOTHORYOS), KILL(GOTFaction.WESTERLANDS), WEAR(GOTFaction.ARRYN), VISIT(GOTFaction.WHITE_WALKER), LEGENDARY(GOTFaction.YI_TI);

		public String codeName;
		public float[] categoryColors;
		public GOTDimension dimension;
		public List<GOTAchievement> list = new ArrayList<>();
		public int nextRankAchID = 1000;

		Category(GOTBiome biome) {
			this(biome.color);
		}

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
