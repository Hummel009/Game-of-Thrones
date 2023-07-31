package got.common.faction;

import got.GOT;
import got.common.GOTAchievementRank;
import got.common.GOTDimension;
import got.common.GOTDimension.DimensionRegion;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.database.GOTAchievement;
import got.common.entity.other.GOTNPCSelectForInfluence;
import got.common.faction.GOTFactionRelations.Relation;
import got.common.item.other.GOTItemBanner;
import got.common.world.GOTWorldProvider;
import got.common.world.map.GOTWaypoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.awt.*;
import java.util.List;
import java.util.*;

public enum GOTFaction {
	WHITE_WALKER(0x8ddaf8, DimensionRegion.WESTEROS, new GOTMapRegion(550, 550, 500)), WILDLING(0x749987, DimensionRegion.WESTEROS, new GOTMapRegion(615, 520, 200)), NIGHT_WATCH(0x2A2A2A, DimensionRegion.WESTEROS, new GOTMapRegion(750, 670, 150)), NORTH(0xD4CFB7, DimensionRegion.WESTEROS, new GOTMapRegion(670, 930, 400)), IRONBORN(0x4b483a, DimensionRegion.WESTEROS, new GOTMapRegion(349, 1323, 129)), WESTERLANDS(0x7c0a02, DimensionRegion.WESTEROS, new GOTMapRegion(485, 1540, 200)), RIVERLANDS(0x146f69, DimensionRegion.WESTEROS, new GOTMapRegion(675, 1437, 215)), HILL_TRIBES(0x573C2F, DimensionRegion.WESTEROS, new GOTMapRegion(842, 1329, 176)), ARRYN(0x2D3257, DimensionRegion.WESTEROS, new GOTMapRegion(842, 1329, 176)), DRAGONSTONE(0x6A6A6A, DimensionRegion.WESTEROS, new GOTMapRegion(923, 1549, 40)), CROWNLANDS(0xDBAB25, DimensionRegion.WESTEROS, new GOTMapRegion(876, 1566, 168)), STORMLANDS(0x014634, DimensionRegion.WESTEROS, new GOTMapRegion(820, 1865, 218)), REACH(0x617646, DimensionRegion.WESTEROS, new GOTMapRegion(500, 1820, 293)), DORNE(0xF57820, DimensionRegion.WESTEROS, new GOTMapRegion(717, 2011, 300)), BRAAVOS(0x4A0C0C, DimensionRegion.WEST_ESSOS, new GOTMapRegion(1221, 1351, 137)), VOLANTIS(0x4C3A5F, DimensionRegion.WEST_ESSOS, new GOTMapRegion(1553, 1928, 210)), PENTOS(0x13425F, DimensionRegion.WEST_ESSOS, new GOTMapRegion(1234, 1566, 172)), NORVOS(0x2D4D2F, DimensionRegion.WEST_ESSOS, new GOTMapRegion(1437, 1468, 201)), LORATH(0xE5E5E5, DimensionRegion.WEST_ESSOS, new GOTMapRegion(1379, 1354, 119)), MYR(0x3F3F3F, DimensionRegion.WEST_ESSOS, new GOTMapRegion(1325, 1797, 223)), LYS(0x3D3023, DimensionRegion.WEST_ESSOS, new GOTMapRegion(1204, 2053, 65)), QOHOR(0x053246, DimensionRegion.WEST_ESSOS, new GOTMapRegion(1590, 1594, 214)), TYROSH(0x2E2E2E, DimensionRegion.WEST_ESSOS, new GOTMapRegion(1110, 1876, 52)), GHISCAR(0xAB7731, DimensionRegion.WEST_ESSOS, new GOTMapRegion(2115, 2180, 347)), QARTH(0x701010, DimensionRegion.WEST_ESSOS, new GOTMapRegion(2750, 2277, 221)), LHAZAR(0x9E4B1E, DimensionRegion.WEST_ESSOS, new GOTMapRegion(2510, 1910, 175)), DOTHRAKI(0x77551F, DimensionRegion.WEST_ESSOS, new GOTMapRegion(2270, 1670, 600)), IBBEN(0x4E3A26, DimensionRegion.EAST_ESSOS, new GOTMapRegion(2761, 1052, 252)), JOGOS(0x985916, DimensionRegion.EAST_ESSOS, new GOTMapRegion(3369, 1651, 460)), MOSSOVY(0x4B5C42, DimensionRegion.EAST_ESSOS, new GOTMapRegion(4056, 1480, 400)), YI_TI(0xC29033, DimensionRegion.EAST_ESSOS, new GOTMapRegion(3350, 2200, 350)), ASSHAI(0x3C353F, DimensionRegion.EAST_ESSOS, new GOTMapRegion(4098, 2331, 644)), SOTHORYOS(0x5E6A18, DimensionRegion.OTHER, new GOTMapRegion(2375, 3540, 1084)), SUMMER_ISLANDS(0x911A17, DimensionRegion.OTHER, new GOTMapRegion(1228, 2716, 310)), ULTHOS(0x343A2C, DimensionRegion.OTHER, new GOTMapRegion(4100, 3517, 1109)), HOSTILE(true, -1), UNALIGNED(false, 0);

	public static Random factionRand = new Random();
	public static int CONTROL_ZONE_EXTRA_RANGE = 50;
	public GOTDimension factionDimension;
	public GOTDimension.DimensionRegion factionRegion;
	public Color factionColor;
	public Map<Float, float[]> facRGBCache = new HashMap<>();
	public Collection<GOTItemBanner.BannerType> factionBanners = new ArrayList<>();
	public boolean allowPlayer;
	public boolean allowEntityRegistry;
	public boolean hasFixedAlignment;
	public int fixedAlignment;
	public List<GOTFactionRank> ranksSortedDescending = new ArrayList<>();
	public GOTFactionRank pledgeRank;
	public GOTAchievement.Category achieveCategory;
	public GOTMapRegion factionMapInfo;
	public List<GOTControlZone> controlZones = new ArrayList<>();
	public boolean isolationist;
	public int eggColor;
	public boolean approvesWarCrimes;
	public List<String> legacyAliases = new ArrayList<>();

	GOTFaction(boolean registry, int alignment) {
		this(0, null, null, false, registry, alignment, null);
	}

	GOTFaction(int color, GOTDimension dim, GOTDimension.DimensionRegion region, boolean player, boolean registry, int alignment, GOTMapRegion mapInfo) {
		allowPlayer = player;
		eggColor = color;
		allowEntityRegistry = registry;
		factionColor = new Color(color);
		factionDimension = dim;
		if (factionDimension != null) {
			factionDimension.factionList.add(this);
		}
		factionRegion = region;
		if (factionRegion != null) {
			factionRegion.factionList.add(this);
			if (factionRegion.getDimension() != factionDimension) {
				throw new IllegalArgumentException("Faction dimension region must agree with faction dimension!");
			}
		}
		if (alignment != Integer.MIN_VALUE) {
			setFixedAlignment(alignment);
		}
		if (mapInfo != null) {
			factionMapInfo = mapInfo;
		}
	}

	GOTFaction(int color, GOTDimension dim, GOTDimension.DimensionRegion region, GOTMapRegion mapInfo) {
		this(color, dim, region, true, true, Integer.MIN_VALUE, mapInfo);
	}

	GOTFaction(int color, GOTDimension.DimensionRegion region, GOTMapRegion mapInfo) {
		this(color, GOTDimension.GAME_OF_THRONES, region, mapInfo);
	}

	public static boolean controlZonesEnabled(World world) {
		return GOTLevelData.enableAlignmentZones() && world.getWorldInfo().getTerrainType() != GOT.worldTypeGOTClassic;
	}

	public static GOTFaction forID(int ID) {
		for (GOTFaction f : values()) {
			if (f.ordinal() == ID) {
				return f;
			}
		}
		return null;
	}

	public static GOTFaction forName(String name) {
		for (GOTFaction f : values()) {
			if (f.matchesNameOrAlias(name)) {
				return f;
			}
		}
		return null;
	}

	public static List<String> getPlayableAlignmentFactionNames() {
		List<GOTFaction> factions = getPlayableAlignmentFactions();
		List<String> names = new ArrayList<>();
		for (GOTFaction f : factions) {
			names.add(f.codeName());
		}
		return names;
	}

	public static List<GOTFaction> getPlayableAlignmentFactions() {
		List<GOTFaction> factions = new ArrayList<>();
		for (GOTFaction f : values()) {
			if (f.isPlayableAlignmentFaction()) {
				factions.add(f);
			}
		}
		return factions;
	}

	public static void onInit() {
		for (GOTFaction f : values()) {
			if (f.allowPlayer && f != WHITE_WALKER) {
				GOTFactionRelations.setRelations(f, WHITE_WALKER, Relation.MORTAL_ENEMY);
			}
		}
		GOTFactionRelations.setRelations(ARRYN, CROWNLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(ARRYN, HILL_TRIBES, Relation.ENEMY);
		GOTFactionRelations.setRelations(ARRYN, NORTH, Relation.FRIEND);
		GOTFactionRelations.setRelations(ARRYN, RIVERLANDS, Relation.FRIEND);
		GOTFactionRelations.setRelations(ARRYN, WESTERLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(ASSHAI, JOGOS, Relation.ENEMY);
		GOTFactionRelations.setRelations(ASSHAI, MOSSOVY, Relation.ENEMY);
		GOTFactionRelations.setRelations(ASSHAI, QARTH, Relation.FRIEND);
		GOTFactionRelations.setRelations(ASSHAI, ULTHOS, Relation.FRIEND);
		GOTFactionRelations.setRelations(ASSHAI, YI_TI, Relation.FRIEND);
		GOTFactionRelations.setRelations(BRAAVOS, LORATH, Relation.FRIEND);
		GOTFactionRelations.setRelations(BRAAVOS, LYS, Relation.ENEMY);
		GOTFactionRelations.setRelations(BRAAVOS, MYR, Relation.ENEMY);
		GOTFactionRelations.setRelations(BRAAVOS, NORVOS, Relation.FRIEND);
		GOTFactionRelations.setRelations(BRAAVOS, PENTOS, Relation.ENEMY);
		GOTFactionRelations.setRelations(BRAAVOS, QOHOR, Relation.FRIEND);
		GOTFactionRelations.setRelations(BRAAVOS, TYROSH, Relation.ENEMY);
		GOTFactionRelations.setRelations(BRAAVOS, VOLANTIS, Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, DORNE, Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, DRAGONSTONE, Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, HILL_TRIBES, Relation.FRIEND);
		GOTFactionRelations.setRelations(CROWNLANDS, NORTH, Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, RIVERLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, STORMLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, WESTERLANDS, Relation.ALLY);
		GOTFactionRelations.setRelations(DORNE, WESTERLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(DRAGONSTONE, REACH, Relation.ENEMY);
		GOTFactionRelations.setRelations(DRAGONSTONE, STORMLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(DRAGONSTONE, WESTERLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(GHISCAR, QARTH, Relation.FRIEND);
		GOTFactionRelations.setRelations(GHISCAR, SOTHORYOS, Relation.ENEMY);
		GOTFactionRelations.setRelations(GHISCAR, SUMMER_ISLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(GHISCAR, VOLANTIS, Relation.FRIEND);
		GOTFactionRelations.setRelations(IBBEN, DOTHRAKI, Relation.ENEMY);
		GOTFactionRelations.setRelations(IBBEN, JOGOS, Relation.ENEMY);
		GOTFactionRelations.setRelations(IBBEN, LORATH, Relation.ENEMY);
		GOTFactionRelations.setRelations(IBBEN, MOSSOVY, Relation.FRIEND);
		GOTFactionRelations.setRelations(IBBEN, NORVOS, Relation.ENEMY);
		GOTFactionRelations.setRelations(IRONBORN, NORTH, Relation.ENEMY);
		GOTFactionRelations.setRelations(IRONBORN, RIVERLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(IRONBORN, WESTERLANDS, Relation.FRIEND);
		GOTFactionRelations.setRelations(JOGOS, DOTHRAKI, Relation.FRIEND);
		GOTFactionRelations.setRelations(JOGOS, MOSSOVY, Relation.ENEMY);
		GOTFactionRelations.setRelations(JOGOS, YI_TI, Relation.ENEMY);
		GOTFactionRelations.setRelations(LHAZAR, DOTHRAKI, Relation.ENEMY);
		GOTFactionRelations.setRelations(LHAZAR, GHISCAR, Relation.FRIEND);
		GOTFactionRelations.setRelations(LORATH, NORVOS, Relation.FRIEND);
		GOTFactionRelations.setRelations(LORATH, QOHOR, Relation.FRIEND);
		GOTFactionRelations.setRelations(LYS, MYR, Relation.ENEMY);
		GOTFactionRelations.setRelations(LYS, TYROSH, Relation.ENEMY);
		GOTFactionRelations.setRelations(LYS, VOLANTIS, Relation.ENEMY);
		GOTFactionRelations.setRelations(MOSSOVY, YI_TI, Relation.ENEMY);
		GOTFactionRelations.setRelations(MYR, TYROSH, Relation.ENEMY);
		GOTFactionRelations.setRelations(MYR, VOLANTIS, Relation.ENEMY);
		GOTFactionRelations.setRelations(NIGHT_WATCH, DRAGONSTONE, Relation.FRIEND);
		GOTFactionRelations.setRelations(NIGHT_WATCH, NORTH, Relation.ALLY);
		GOTFactionRelations.setRelations(NIGHT_WATCH, WILDLING, Relation.ENEMY);
		GOTFactionRelations.setRelations(NORTH, RIVERLANDS, Relation.FRIEND);
		GOTFactionRelations.setRelations(NORTH, WESTERLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(NORTH, WILDLING, Relation.ENEMY);
		GOTFactionRelations.setRelations(NORVOS, QOHOR, Relation.FRIEND);
		GOTFactionRelations.setRelations(NORVOS, VOLANTIS, Relation.ENEMY);
		GOTFactionRelations.setRelations(PENTOS, MYR, Relation.FRIEND);
		GOTFactionRelations.setRelations(PENTOS, VOLANTIS, Relation.ENEMY);
		GOTFactionRelations.setRelations(QARTH, SOTHORYOS, Relation.ENEMY);
		GOTFactionRelations.setRelations(QARTH, YI_TI, Relation.FRIEND);
		GOTFactionRelations.setRelations(QOHOR, DOTHRAKI, Relation.ENEMY);
		GOTFactionRelations.setRelations(QOHOR, VOLANTIS, Relation.ENEMY);
		GOTFactionRelations.setRelations(REACH, STORMLANDS, Relation.FRIEND);
		GOTFactionRelations.setRelations(RIVERLANDS, HILL_TRIBES, Relation.ENEMY);
		GOTFactionRelations.setRelations(RIVERLANDS, WESTERLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(SOTHORYOS, SUMMER_ISLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(SOTHORYOS, ULTHOS, Relation.ENEMY);
		GOTFactionRelations.setRelations(STORMLANDS, WESTERLANDS, Relation.ENEMY);
		GOTFactionRelations.setRelations(SUMMER_ISLANDS, DORNE, Relation.FRIEND);
		GOTFactionRelations.setRelations(SUMMER_ISLANDS, LYS, Relation.FRIEND);
		GOTFactionRelations.setRelations(SUMMER_ISLANDS, TYROSH, Relation.FRIEND);
		GOTFactionRelations.setRelations(SUMMER_ISLANDS, VOLANTIS, Relation.ENEMY);
		GOTFactionRelations.setRelations(TYROSH, VOLANTIS, Relation.ENEMY);
		GOTFactionRelations.setRelations(ULTHOS, ASSHAI, Relation.FRIEND);
		GOTFactionRelations.setRelations(VOLANTIS, DOTHRAKI, Relation.ENEMY);
		GOTFactionRelations.setRelations(VOLANTIS, TYROSH, Relation.ENEMY);
		GOTFactionRelations.setRelations(WESTERLANDS, HILL_TRIBES, Relation.FRIEND);
		GOTFactionRelations.setRelations(WILDLING, DRAGONSTONE, Relation.ENEMY);
		GOTFactionRelations.setRelations(WILDLING, IBBEN, Relation.FRIEND);

		ARRYN.addControlZone(new GOTControlZone(851, 1335, 191));
		ASSHAI.addControlZone(new GOTControlZone(3914, 2135, 765));
		ASSHAI.addControlZone(new GOTControlZone(4466, 1677, 660));
		ASSHAI.addControlZone(new GOTControlZone(4604, 2283, 210));
		ASSHAI.addControlZone(new GOTControlZone(4940, 1442, 300));
		ASSHAI.approvesWarCrimes = true;
		BRAAVOS.addControlZone(new GOTControlZone(1297, 1708, 543));
		CROWNLANDS.addControlZone(new GOTControlZone(703, 1416, 550));
		CROWNLANDS.approvesWarCrimes = true;
		DORNE.addControlZone(new GOTControlZone(718, 1867, 334));
		DOTHRAKI.addControlZone(new GOTControlZone(1828, 1694, 454));
		DOTHRAKI.addControlZone(new GOTControlZone(2177, 1661, 394));
		DOTHRAKI.addControlZone(new GOTControlZone(2642, 1995, 325));
		DOTHRAKI.addControlZone(new GOTControlZone(2648, 1586, 429));
		DOTHRAKI.approvesWarCrimes = true;
		DRAGONSTONE.addControlZone(new GOTControlZone(703, 1416, 550));
		DRAGONSTONE.addControlZone(new GOTControlZone(764, 608, 133));
		DRAGONSTONE.approvesWarCrimes = true;
		GHISCAR.addControlZone(new GOTControlZone(1069, 1952, 94));
		GHISCAR.addControlZone(new GOTControlZone(2144, 2375, 530));
		GHISCAR.addControlZone(new GOTControlZone(GOTWaypoint.BarterBeach, 200));
		GHISCAR.addControlZone(new GOTControlZone(GOTWaypoint.Gogossos, 200));
		GHISCAR.addControlZone(new GOTControlZone(GOTWaypoint.Gorosh, 200));
		GHISCAR.addControlZone(new GOTControlZone(GOTWaypoint.NewGhis, 200));
		GHISCAR.addControlZone(new GOTControlZone(GOTWaypoint.Zamettar, 200));
		GHISCAR.approvesWarCrimes = true;
		HILL_TRIBES.addControlZone(new GOTControlZone(851, 1335, 191));
		HILL_TRIBES.approvesWarCrimes = true;
		HOSTILE.approvesWarCrimes = true;
		IBBEN.addControlZone(new GOTControlZone(2038, 1332, 180));
		IBBEN.addControlZone(new GOTControlZone(2400, 1168, 355));
		IBBEN.addControlZone(new GOTControlZone(2879, 1099, 404));
		IBBEN.addControlZone(new GOTControlZone(3441, 1272, 345));
		IBBEN.addControlZone(new GOTControlZone(3849, 1337, 345));
		IRONBORN.addControlZone(new GOTControlZone(1069, 1952, 94));
		IRONBORN.addControlZone(new GOTControlZone(513, 1102, 348));
		IRONBORN.addControlZone(new GOTControlZone(623, 1519, 280));
		IRONBORN.approvesWarCrimes = true;
		JOGOS.addControlZone(new GOTControlZone(3088, 2508, 430));
		JOGOS.addControlZone(new GOTControlZone(3809, 1955, 1005));
		JOGOS.approvesWarCrimes = true;
		LHAZAR.addControlZone(new GOTControlZone(2507, 2002, 269));
		LORATH.addControlZone(new GOTControlZone(1297, 1708, 543));
		LORATH.addControlZone(new GOTControlZone(1905, 1307, 112));
		LYS.addControlZone(new GOTControlZone(1297, 1708, 543));
		MOSSOVY.addControlZone(new GOTControlZone(3914, 2135, 765));
		MOSSOVY.addControlZone(new GOTControlZone(4466, 1677, 660));
		MOSSOVY.addControlZone(new GOTControlZone(4604, 2283, 210));
		MOSSOVY.addControlZone(new GOTControlZone(4940, 1442, 300));
		MYR.addControlZone(new GOTControlZone(1297, 1708, 543));
		NIGHT_WATCH.addControlZone(new GOTControlZone(757, 573, 267));
		NORTH.addControlZone(new GOTControlZone(749, 1111, 605));
		NORVOS.addControlZone(new GOTControlZone(1297, 1708, 543));
		PENTOS.addControlZone(new GOTControlZone(1297, 1708, 543));
		QARTH.addControlZone(new GOTControlZone(2144, 2375, 530));
		QARTH.addControlZone(new GOTControlZone(GOTWaypoint.Batargas, 200));
		QARTH.addControlZone(new GOTControlZone(GOTWaypoint.Karimagar, 200));
		QARTH.addControlZone(new GOTControlZone(GOTWaypoint.Teriman, 200));
		QOHOR.addControlZone(new GOTControlZone(1297, 1708, 543));
		REACH.addControlZone(new GOTControlZone(401, 1939, 183));
		REACH.addControlZone(new GOTControlZone(619, 1624, 300));
		RIVERLANDS.addControlZone(new GOTControlZone(703, 1416, 550));
		SOTHORYOS.addControlZone(new GOTControlZone(2264, 3542, 1050));
		SOTHORYOS.addControlZone(new GOTControlZone(3352, 3555, 570));
		STORMLANDS.addControlZone(new GOTControlZone(687, 1600, 404));
		SUMMER_ISLANDS.addControlZone(new GOTControlZone(1714, 2516, 777));
		SUMMER_ISLANDS.addControlZone(new GOTControlZone(GOTWaypoint.Ataahua, 200));
		SUMMER_ISLANDS.addControlZone(new GOTControlZone(GOTWaypoint.Hauauru, 200));
		SUMMER_ISLANDS.addControlZone(new GOTControlZone(GOTWaypoint.Kohuru, 200));
		SUMMER_ISLANDS.addControlZone(new GOTControlZone(GOTWaypoint.Matahau, 200));
		SUMMER_ISLANDS.addControlZone(new GOTControlZone(GOTWaypoint.Matao, 200));
		SUMMER_ISLANDS.addControlZone(new GOTControlZone(GOTWaypoint.Ngahere, 200));
		SUMMER_ISLANDS.addControlZone(new GOTControlZone(GOTWaypoint.Ngarara, 200));
		SUMMER_ISLANDS.addControlZone(new GOTControlZone(GOTWaypoint.Pereki, 200));
		SUMMER_ISLANDS.addControlZone(new GOTControlZone(GOTWaypoint.Tauranga, 200));
		TYROSH.addControlZone(new GOTControlZone(1297, 1708, 543));
		ULTHOS.addControlZone(new GOTControlZone(3175, 3599, 570));
		ULTHOS.addControlZone(new GOTControlZone(4100, 3517, 1109));
		ULTHOS.approvesWarCrimes = true;
		VOLANTIS.addControlZone(new GOTControlZone(1297, 1708, 543));
		VOLANTIS.addControlZone(new GOTControlZone(1904, 2146, 554));
		VOLANTIS.approvesWarCrimes = true;
		WESTERLANDS.addControlZone(new GOTControlZone(703, 1416, 550));
		WESTERLANDS.approvesWarCrimes = true;
		WHITE_WALKER.addControlZone(new GOTControlZone(360, 297, 240));
		WHITE_WALKER.addControlZone(new GOTControlZone(494, 551, 206));
		WHITE_WALKER.addControlZone(new GOTControlZone(546, 245, 191));
		WHITE_WALKER.addControlZone(new GOTControlZone(667, 239, 186));
		WHITE_WALKER.addControlZone(new GOTControlZone(696, 504, 207));
		WHITE_WALKER.addControlZone(new GOTControlZone(757, 573, 267));
		WHITE_WALKER.approvesWarCrimes = true;
		WILDLING.addControlZone(new GOTControlZone(757, 573, 267));
		WILDLING.approvesWarCrimes = true;
		YI_TI.addControlZone(new GOTControlZone(3088, 2508, 430));
		YI_TI.addControlZone(new GOTControlZone(3809, 1955, 1005));
		for (GOTFaction fac : values()) {
			if (fac != WHITE_WALKER && fac != UNALIGNED && fac != HOSTILE) {
				fac.addRank(10.0f, "guest").makeTitle().makeAchievement(); // �����
				fac.addRank(50.0f, "friend").makeTitle().makeAchievement(); // ����
				fac.addRank(100.0f, "defender").setPledgeRank().makeTitle().makeAchievement(); // ��������
				fac.addRank(500.0f, "hero").makeTitle().makeAchievement(); // �����
				fac.addRank(1000.0f, "leader").makeTitle().makeAchievement(); // �����
			}
		}
		WHITE_WALKER.addSpecialRank(1000.0f, "king").setPledgeRank().makeTitle().makeAchievement();
	}

	public void addControlZone(GOTControlZone zone) {
		controlZones.add(zone);
	}

	public GOTFactionRank addRank(float alignment, String name) {
		GOTFactionRank rank = new GOTFactionRank(this, alignment, name);
		ranksSortedDescending.add(rank);
		Collections.sort(ranksSortedDescending);
		return rank;
	}

	public GOTFactionRank addSpecialRank(float alignment, String name) {
		GOTFactionRank rank = new GOTFactionRank(this, alignment, name, false);
		ranksSortedDescending.add(rank);
		Collections.sort(ranksSortedDescending);
		return rank;
	}

	public int[] calculateFullControlZoneWorldBorders() {
		int xMin = 0;
		int xMax = 0;
		int zMin = 0;
		int zMax = 0;
		boolean first = true;
		for (GOTControlZone zone : controlZones) {
			int cxMin = zone.xCoord - zone.radiusCoord;
			int cxMax = zone.xCoord + zone.radiusCoord;
			int czMin = zone.zCoord - zone.radiusCoord;
			int czMax = zone.zCoord + zone.radiusCoord;
			if (first) {
				xMin = cxMin;
				xMax = cxMax;
				zMin = czMin;
				zMax = czMax;
				first = false;
				continue;
			}
			xMin = Math.min(xMin, cxMin);
			xMax = Math.max(xMax, cxMax);
			zMin = Math.min(zMin, czMin);
			zMax = Math.max(zMax, czMax);
		}
		return new int[]{xMin, xMax, zMin, zMax};
	}

	public void checkAlignmentAchievements(EntityPlayer entityplayer, float alignment) {
		GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
		for (GOTFactionRank rank : ranksSortedDescending) {
			GOTAchievementRank rankAch = rank.getRankAchievement();
			if (rankAch == null || !rankAch.isPlayerRequiredRank(entityplayer)) {
				continue;
			}
			playerData.addAchievement(rankAch);
		}
	}

	public String codeName() {
		return name();
	}

	public double distanceToNearestControlZoneInRange(World world, double d, double d1, double d2, int mapRange) {
		double closestDist = -1.0;
		if (isFactionDimension(world)) {
			int coordRange = GOTWaypoint.mapToWorldR(mapRange);
			for (GOTControlZone zone : controlZones) {
				double dx = d - zone.xCoord;
				double dz = d2 - zone.zCoord;
				double dSq = dx * dx + dz * dz;
				double dToEdge = Math.sqrt(dSq) - zone.radiusCoord;
				if (dToEdge > coordRange || closestDist >= 0.0 && dToEdge >= closestDist) {
					continue;
				}
				closestDist = dToEdge;
			}
		}
		return closestDist;
	}

	public String factionEntityName() {
		return StatCollector.translateToLocal("got.faction." + codeName() + ".entity");
	}

	public String factionName() {
		return StatCollector.translateToLocal(untranslatedFactionName());
	}

	public String factionSubtitle() {
		return StatCollector.translateToLocal("got.faction." + codeName() + ".subtitle");
	}

	public List<GOTFaction> getBonusesForKilling() {
		List<GOTFaction> list = new ArrayList<>();
		for (GOTFaction f : values()) {
			if (f == this || !isBadRelation(f)) {
				continue;
			}
			list.add(f);
		}
		return list;
	}

	public List<GOTFaction> getConquestBoostRelations() {
		List<GOTFaction> list = new ArrayList<>();
		for (GOTFaction f : values()) {
			if (f == this || !f.isPlayableAlignmentFaction() || GOTFactionRelations.getRelations(this, f) != Relation.ALLY) {
				continue;
			}
			list.add(f);
		}
		return list;
	}

	public float getControlZoneAlignmentMultiplier(EntityPlayer entityplayer) {
		int reducedRange;
		double dist;
		if (inControlZone(entityplayer)) {
			return 1.0f;
		}
		if (isFactionDimension(entityplayer.worldObj) && (dist = distanceToNearestControlZoneInRange(entityplayer.worldObj, entityplayer.posX, entityplayer.boundingBox.minY, entityplayer.posZ, reducedRange = getControlZoneReducedRange())) >= 0.0) {
			double mapDist = GOTWaypoint.worldToMapR(dist);
			float frac = (float) mapDist / reducedRange;
			float mplier = 1.0f - frac;
			return MathHelper.clamp_float(mplier, 0.0f, 1.0f);
		}
		return 0.0f;
	}

	public int getControlZoneReducedRange() {
		if (isolationist) {
			return 0;
		}
		return 50;
	}

	public List<GOTControlZone> getControlZones() {
		return controlZones;
	}

	public int getFactionColor() {
		return factionColor.getRGB();
	}

	public float[] getFactionRGB() {
		return getFactionRGB_MinBrightness(0.0f);
	}

	public float[] getFactionRGB_MinBrightness(float minBrightness) {
		float[] rgb = facRGBCache.get(minBrightness);
		if (rgb == null) {
			float[] hsb = Color.RGBtoHSB(factionColor.getRed(), factionColor.getGreen(), factionColor.getBlue(), null);
			hsb[2] = Math.max(hsb[2], minBrightness);
			int alteredColor = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
			rgb = new Color(alteredColor).getColorComponents(null);
			facRGBCache.put(minBrightness, rgb);
		}
		return rgb;
	}

	public GOTFactionRank getFirstRank() {
		if (ranksSortedDescending.isEmpty()) {
			return GOTFactionRank.RANK_NEUTRAL;
		}
		return ranksSortedDescending.get(ranksSortedDescending.size() - 1);
	}

	public List<GOTFaction> getOthersOfRelation(Relation rel) {
		List<GOTFaction> list = new ArrayList<>();
		for (GOTFaction f : values()) {
			if (f == this || !f.isPlayableAlignmentFaction() || GOTFactionRelations.getRelations(this, f) != rel) {
				continue;
			}
			list.add(f);
		}
		return list;
	}

	public List<GOTFaction> getPenaltiesForKilling() {
		List<GOTFaction> list = new ArrayList<>();
		list.add(this);
		for (GOTFaction f : values()) {
			if (f == this || !isGoodRelation(f)) {
				continue;
			}
			list.add(f);
		}
		return list;
	}

	public float getPledgeAlignment() {
		if (pledgeRank != null) {
			return pledgeRank.alignment;
		}
		return 0.0f;
	}

	public GOTFactionRank getPledgeRank() {
		return pledgeRank;
	}

	public void setPledgeRank(GOTFactionRank rank) {
		if (rank.fac != this) {
			throw new IllegalArgumentException("Incompatible faction!");
		}
		if (pledgeRank != null) {
			throw new IllegalArgumentException("Faction already has a pledge rank!");
		}
		pledgeRank = rank;
	}

	public GOTFactionRank getRank(float alignment) {
		for (GOTFactionRank rank : ranksSortedDescending) {
			if (rank.isDummyRank() || alignment < rank.alignment) {
				continue;
			}
			return rank;
		}
		if (alignment >= 0.0f) {
			return GOTFactionRank.RANK_NEUTRAL;
		}
		return GOTFactionRank.RANK_ENEMY;
	}

	public GOTFactionRank getRank(GOTPlayerData pd) {
		float alignment = pd.getAlignment(this);
		return getRank(alignment);
	}

	public GOTFactionRank getRankAbove(GOTFactionRank curRank) {
		return getRankNAbove(curRank, 1);
	}

	public GOTFactionRank getRankBelow(GOTFactionRank curRank) {
		return getRankNAbove(curRank, -1);
	}

	public GOTFactionRank getRankNAbove(GOTFactionRank curRank, int n) {
		if (ranksSortedDescending.isEmpty() || curRank == null) {
			return GOTFactionRank.RANK_NEUTRAL;
		}
		int index = -1;
		if (curRank.isDummyRank()) {
			index = ranksSortedDescending.size();
		} else if (ranksSortedDescending.contains(curRank)) {
			index = ranksSortedDescending.indexOf(curRank);
		}
		if (index >= 0) {
			index -= n;
			if (index < 0) {
				return ranksSortedDescending.get(0);
			}
			if (index > ranksSortedDescending.size() - 1) {
				return GOTFactionRank.RANK_NEUTRAL;
			}
			return ranksSortedDescending.get(index);
		}
		return GOTFactionRank.RANK_NEUTRAL;
	}

	public boolean inControlZone(EntityPlayer entityplayer) {
		return inControlZone(entityplayer.worldObj, entityplayer.posX, entityplayer.boundingBox.minY, entityplayer.posZ);
	}

	public boolean inControlZone(World world, double d, double d1, double d2) {
		if (inDefinedControlZone(world, d, d1, d2)) {
			return true;
		}
		double nearbyRange = 24.0;
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(d, d1, d2, d, d1, d2).expand(nearbyRange, nearbyRange, nearbyRange);
		List<EntityLivingBase> nearbyNPCs = world.selectEntitiesWithinAABB(EntityLivingBase.class, aabb, new GOTNPCSelectForInfluence(this));
		return !nearbyNPCs.isEmpty();
	}

	public boolean inDefinedControlZone(EntityPlayer entityplayer) {
		return inDefinedControlZone(entityplayer, 0);
	}

	public boolean inDefinedControlZone(EntityPlayer entityplayer, int extraMapRange) {
		return inDefinedControlZone(entityplayer.worldObj, entityplayer.posX, entityplayer.boundingBox.minY, entityplayer.posZ, extraMapRange);
	}

	public boolean inDefinedControlZone(World world, double d, double d1, double d2) {
		return inDefinedControlZone(world, d, d1, d2, 0);
	}

	public boolean inDefinedControlZone(World world, double d, double d1, double d2, int extraMapRange) {
		if (isFactionDimension(world)) {
			if (!controlZonesEnabled(world)) {
				return true;
			}
			for (GOTControlZone zone : controlZones) {
				if (!zone.inZone(d, d1, d2, extraMapRange)) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public boolean isAlly(GOTFaction other) {
		Relation rel = GOTFactionRelations.getRelations(this, other);
		return rel == Relation.ALLY;
	}

	public boolean isBadRelation(GOTFaction other) {
		Relation rel = GOTFactionRelations.getRelations(this, other);
		return rel == Relation.ENEMY || rel == Relation.MORTAL_ENEMY;
	}

	public boolean isFactionDimension(World world) {
		return world.provider instanceof GOTWorldProvider && ((GOTWorldProvider) world.provider).getGOTDimension() == factionDimension;
	}

	public boolean isGoodRelation(GOTFaction other) {
		Relation rel = GOTFactionRelations.getRelations(this, other);
		return rel == Relation.ALLY || rel == Relation.FRIEND;
	}

	public boolean isMortalEnemy(GOTFaction other) {
		Relation rel = GOTFactionRelations.getRelations(this, other);
		return rel == Relation.MORTAL_ENEMY;
	}

	public boolean isNeutral(GOTFaction other) {
		return GOTFactionRelations.getRelations(this, other) == Relation.NEUTRAL;
	}

	public boolean isPlayableAlignmentFaction() {
		return allowPlayer && !hasFixedAlignment;
	}

	public List<String> listAliases() {
		return new ArrayList<>(legacyAliases);
	}

	public boolean matchesNameOrAlias(String name) {
		if (codeName().equals(name)) {
			return true;
		}
		for (String alias : legacyAliases) {
			if (alias.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public void setAchieveCategory(GOTAchievement.Category cat) {
		achieveCategory = cat;
	}

	public void setFixedAlignment(int alignment) {
		hasFixedAlignment = true;
		fixedAlignment = alignment;
	}

	public boolean sharesControlZoneWith(GOTFaction other) {
		return sharesControlZoneWith(other, 0);
	}

	public boolean sharesControlZoneWith(GOTFaction other, int extraMapRadius) {
		if (other.factionDimension == factionDimension) {
			for (GOTControlZone zone : controlZones) {
				for (GOTControlZone otherZone : other.controlZones) {
					if (zone.intersectsWith(otherZone, extraMapRadius)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public String untranslatedFactionName() {
		return "got.faction." + codeName() + ".name";
	}
}