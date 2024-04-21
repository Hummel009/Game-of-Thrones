package got.common.faction;

import got.GOT;
import got.common.GOTAchievementRank;
import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.entity.other.GOTNPCSelectByFaction;
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
	WHITE_WALKER(0x8ddaf8, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(550, 550, 500)), WILDLING(0x749987, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(615, 520, 200)), NIGHT_WATCH(0x2A2A2A, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(750, 670, 150)), NORTH(0xD4CFB7, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(670, 930, 400)), IRONBORN(0x4b483a, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(349, 1323, 129)), WESTERLANDS(0x7c0a02, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(485, 1540, 200)), RIVERLANDS(0x146f69, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(675, 1437, 215)), HILL_TRIBES(0x573C2F, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(842, 1329, 176)), ARRYN(0x2D3257, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(842, 1329, 176)), DRAGONSTONE(0x6A6A6A, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(923, 1549, 40)), CROWNLANDS(0xDBAB25, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(876, 1566, 168)), STORMLANDS(0x014634, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(820, 1865, 218)), REACH(0x617646, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(500, 1820, 293)), DORNE(0xF57820, GOTDimension.DimensionRegion.WESTEROS, new GOTMapRegion(717, 2011, 300)), BRAAVOS(0x4A0C0C, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(1221, 1351, 137)), VOLANTIS(0x4C3A5F, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(1553, 1928, 210)), PENTOS(0x13425F, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(1234, 1566, 172)), NORVOS(0x2D4D2F, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(1437, 1468, 201)), LORATH(0xE5E5E5, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(1379, 1354, 119)), MYR(0x3F3F3F, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(1325, 1797, 223)), LYS(0x3D3023, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(1204, 2053, 65)), QOHOR(0x053246, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(1590, 1594, 214)), TYROSH(0x2E2E2E, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(1110, 1876, 52)), GHISCAR(0xAB7731, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(2115, 2180, 347)), QARTH(0x701010, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(2750, 2277, 221)), LHAZAR(0x9E4B1E, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(2510, 1910, 175)), DOTHRAKI(0x77551F, GOTDimension.DimensionRegion.WEST_ESSOS, new GOTMapRegion(2270, 1670, 600)), IBBEN(0x4E3A26, GOTDimension.DimensionRegion.EAST_ESSOS, new GOTMapRegion(2761, 1052, 252)), JOGOS(0x985916, GOTDimension.DimensionRegion.EAST_ESSOS, new GOTMapRegion(3369, 1651, 460)), MOSSOVY(0x4B5C42, GOTDimension.DimensionRegion.EAST_ESSOS, new GOTMapRegion(4056, 1480, 400)), YI_TI(0xC29033, GOTDimension.DimensionRegion.EAST_ESSOS, new GOTMapRegion(3350, 2200, 350)), ASSHAI(0x3C353F, GOTDimension.DimensionRegion.EAST_ESSOS, new GOTMapRegion(4098, 2331, 644)), SOTHORYOS(0x5E6A18, GOTDimension.DimensionRegion.OTHER, new GOTMapRegion(2375, 3540, 1084)), SUMMER_ISLANDS(0x911A17, GOTDimension.DimensionRegion.OTHER, new GOTMapRegion(1228, 2716, 310)), ULTHOS(0x343A2C, GOTDimension.DimensionRegion.OTHER, new GOTMapRegion(4100, 3517, 1109)), HOSTILE(true, -1), UNALIGNED(false, 0);

	private final Collection<GOTItemBanner.BannerType> factionBanners = new ArrayList<>();
	private final List<GOTFactionRank> ranksSortedDescending = new ArrayList<>();
	private final List<GOTControlZone> controlZones = new ArrayList<>();
	private final Map<Float, float[]> facRGBCache = new HashMap<>();
	private final Color factionColor;
	private final boolean allowEntityRegistry;
	private final int eggColor;

	private GOTDimension.DimensionRegion factionRegion;
	private GOTDimension factionDimension;
	private GOTMapRegion factionMapInfo;
	private GOTFactionRank pledgeRank;
	private boolean allowPlayer;
	private boolean hasFixedAlignment;
	private boolean approvesWarCrimes;
	private int fixedAlignment;

	GOTFaction(boolean registry, int alignment) {
		this(0, null, null, false, registry, alignment, null);
	}

	GOTFaction(int color, GOTDimension.DimensionRegion region, GOTMapRegion mapInfo) {
		this(color, GOTDimension.GAME_OF_THRONES, region, mapInfo);
	}

	GOTFaction(int color, GOTDimension dim, GOTDimension.DimensionRegion region, GOTMapRegion mapInfo) {
		this(color, dim, region, true, true, Integer.MIN_VALUE, mapInfo);
	}

	GOTFaction(int color, GOTDimension dim, GOTDimension.DimensionRegion region, boolean player, boolean registry, int alignment, GOTMapRegion mapInfo) {
		allowPlayer = player;
		eggColor = color;
		allowEntityRegistry = registry;
		factionColor = new Color(color);
		factionDimension = dim;
		if (factionDimension != null) {
			factionDimension.getFactionList().add(this);
		}
		factionRegion = region;
		if (factionRegion != null) {
			factionRegion.getFactionList().add(this);
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

	public static boolean controlZonesEnabled(World world) {
		return GOTLevelData.isEnableAlignmentZones() && world.getWorldInfo().getTerrainType() != GOT.worldTypeGOTClassic;
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
			if (f.matchesName(name)) {
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
				GOTFactionRelations.setRelations(f, WHITE_WALKER, GOTFactionRelations.Relation.MORTAL_ENEMY);
			}
		}
		GOTFactionRelations.setRelations(ARRYN, CROWNLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(ARRYN, HILL_TRIBES, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(ARRYN, NORTH, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(ARRYN, RIVERLANDS, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(ARRYN, WESTERLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(ASSHAI, JOGOS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(ASSHAI, MOSSOVY, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(ASSHAI, QARTH, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(ASSHAI, ULTHOS, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(ASSHAI, YI_TI, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(BRAAVOS, LORATH, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(BRAAVOS, LYS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(BRAAVOS, MYR, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(BRAAVOS, NORVOS, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(BRAAVOS, PENTOS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(BRAAVOS, QOHOR, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(BRAAVOS, TYROSH, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(BRAAVOS, VOLANTIS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, DORNE, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, DRAGONSTONE, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, HILL_TRIBES, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(CROWNLANDS, NORTH, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, RIVERLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, STORMLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(CROWNLANDS, WESTERLANDS, GOTFactionRelations.Relation.ALLY);
		GOTFactionRelations.setRelations(DORNE, WESTERLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(DRAGONSTONE, REACH, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(DRAGONSTONE, STORMLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(DRAGONSTONE, WESTERLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(GHISCAR, QARTH, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(GHISCAR, SOTHORYOS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(GHISCAR, SUMMER_ISLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(GHISCAR, VOLANTIS, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(IBBEN, DOTHRAKI, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(IBBEN, JOGOS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(IBBEN, LORATH, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(IBBEN, MOSSOVY, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(IBBEN, NORVOS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(IRONBORN, NORTH, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(IRONBORN, RIVERLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(IRONBORN, WESTERLANDS, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(JOGOS, DOTHRAKI, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(JOGOS, MOSSOVY, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(JOGOS, YI_TI, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(LHAZAR, DOTHRAKI, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(LHAZAR, GHISCAR, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(LORATH, NORVOS, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(LORATH, QOHOR, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(LYS, MYR, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(LYS, TYROSH, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(LYS, VOLANTIS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(MOSSOVY, YI_TI, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(MYR, TYROSH, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(MYR, VOLANTIS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(NIGHT_WATCH, DRAGONSTONE, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(NIGHT_WATCH, NORTH, GOTFactionRelations.Relation.ALLY);
		GOTFactionRelations.setRelations(NIGHT_WATCH, WILDLING, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(NORTH, RIVERLANDS, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(NORTH, WESTERLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(NORTH, WILDLING, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(NORVOS, QOHOR, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(NORVOS, VOLANTIS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(PENTOS, MYR, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(PENTOS, VOLANTIS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(QARTH, SOTHORYOS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(QARTH, YI_TI, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(QOHOR, DOTHRAKI, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(QOHOR, VOLANTIS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(REACH, STORMLANDS, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(RIVERLANDS, HILL_TRIBES, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(RIVERLANDS, WESTERLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(SOTHORYOS, SUMMER_ISLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(SOTHORYOS, ULTHOS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(STORMLANDS, WESTERLANDS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(SUMMER_ISLANDS, DORNE, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(SUMMER_ISLANDS, LYS, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(SUMMER_ISLANDS, TYROSH, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(SUMMER_ISLANDS, VOLANTIS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(TYROSH, VOLANTIS, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(ULTHOS, ASSHAI, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(VOLANTIS, DOTHRAKI, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(VOLANTIS, TYROSH, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(WESTERLANDS, HILL_TRIBES, GOTFactionRelations.Relation.FRIEND);
		GOTFactionRelations.setRelations(WILDLING, DRAGONSTONE, GOTFactionRelations.Relation.ENEMY);
		GOTFactionRelations.setRelations(WILDLING, IBBEN, GOTFactionRelations.Relation.FRIEND);

		ARRYN.controlZones.add(new GOTControlZone(851, 1335, 191));
		ASSHAI.controlZones.add(new GOTControlZone(3914, 2135, 765));
		ASSHAI.controlZones.add(new GOTControlZone(4466, 1677, 660));
		ASSHAI.controlZones.add(new GOTControlZone(4604, 2283, 210));
		ASSHAI.controlZones.add(new GOTControlZone(4940, 1442, 300));
		ASSHAI.approvesWarCrimes = true;
		BRAAVOS.controlZones.add(new GOTControlZone(1297, 1708, 543));
		CROWNLANDS.controlZones.add(new GOTControlZone(703, 1416, 550));
		CROWNLANDS.approvesWarCrimes = true;
		DORNE.controlZones.add(new GOTControlZone(718, 1867, 334));
		DOTHRAKI.controlZones.add(new GOTControlZone(1828, 1694, 454));
		DOTHRAKI.controlZones.add(new GOTControlZone(2177, 1661, 394));
		DOTHRAKI.controlZones.add(new GOTControlZone(2642, 1995, 325));
		DOTHRAKI.controlZones.add(new GOTControlZone(2648, 1586, 429));
		DOTHRAKI.approvesWarCrimes = true;
		DRAGONSTONE.controlZones.add(new GOTControlZone(703, 1416, 550));
		DRAGONSTONE.controlZones.add(new GOTControlZone(764, 608, 133));
		DRAGONSTONE.approvesWarCrimes = true;
		GHISCAR.controlZones.add(new GOTControlZone(1069, 1952, 94));
		GHISCAR.controlZones.add(new GOTControlZone(2144, 2375, 530));
		GHISCAR.controlZones.add(new GOTControlZone(GOTWaypoint.BARTER_BEACH, 200));
		GHISCAR.controlZones.add(new GOTControlZone(GOTWaypoint.GOGOSSOS, 200));
		GHISCAR.controlZones.add(new GOTControlZone(GOTWaypoint.GOROSH, 200));
		GHISCAR.controlZones.add(new GOTControlZone(GOTWaypoint.NEW_GHIS, 200));
		GHISCAR.controlZones.add(new GOTControlZone(GOTWaypoint.ZAMETTAR, 200));
		GHISCAR.approvesWarCrimes = true;
		HILL_TRIBES.controlZones.add(new GOTControlZone(851, 1335, 191));
		HILL_TRIBES.approvesWarCrimes = true;
		HOSTILE.approvesWarCrimes = true;
		IBBEN.controlZones.add(new GOTControlZone(2038, 1332, 180));
		IBBEN.controlZones.add(new GOTControlZone(2400, 1168, 355));
		IBBEN.controlZones.add(new GOTControlZone(2879, 1099, 404));
		IBBEN.controlZones.add(new GOTControlZone(3441, 1272, 345));
		IBBEN.controlZones.add(new GOTControlZone(3849, 1337, 345));
		IRONBORN.controlZones.add(new GOTControlZone(1069, 1952, 94));
		IRONBORN.controlZones.add(new GOTControlZone(513, 1102, 348));
		IRONBORN.controlZones.add(new GOTControlZone(623, 1519, 280));
		IRONBORN.approvesWarCrimes = true;
		JOGOS.controlZones.add(new GOTControlZone(3088, 2508, 430));
		JOGOS.controlZones.add(new GOTControlZone(3809, 1955, 1005));
		JOGOS.approvesWarCrimes = true;
		LHAZAR.controlZones.add(new GOTControlZone(2507, 2002, 269));
		LORATH.controlZones.add(new GOTControlZone(1297, 1708, 543));
		LORATH.controlZones.add(new GOTControlZone(1905, 1307, 112));
		LYS.controlZones.add(new GOTControlZone(1297, 1708, 543));
		MOSSOVY.controlZones.add(new GOTControlZone(3914, 2135, 765));
		MOSSOVY.controlZones.add(new GOTControlZone(4466, 1677, 660));
		MOSSOVY.controlZones.add(new GOTControlZone(4604, 2283, 210));
		MOSSOVY.controlZones.add(new GOTControlZone(4940, 1442, 300));
		MYR.controlZones.add(new GOTControlZone(1297, 1708, 543));
		NIGHT_WATCH.controlZones.add(new GOTControlZone(757, 573, 267));
		NORTH.controlZones.add(new GOTControlZone(749, 1111, 605));
		NORVOS.controlZones.add(new GOTControlZone(1297, 1708, 543));
		PENTOS.controlZones.add(new GOTControlZone(1297, 1708, 543));
		QARTH.controlZones.add(new GOTControlZone(2144, 2375, 530));
		QARTH.controlZones.add(new GOTControlZone(GOTWaypoint.BATARGAS, 200));
		QARTH.controlZones.add(new GOTControlZone(GOTWaypoint.KARIMAGAR, 200));
		QARTH.controlZones.add(new GOTControlZone(GOTWaypoint.TERIMAN, 200));
		QOHOR.controlZones.add(new GOTControlZone(1297, 1708, 543));
		REACH.controlZones.add(new GOTControlZone(401, 1939, 183));
		REACH.controlZones.add(new GOTControlZone(619, 1624, 300));
		RIVERLANDS.controlZones.add(new GOTControlZone(703, 1416, 550));
		SOTHORYOS.controlZones.add(new GOTControlZone(2264, 3542, 1050));
		SOTHORYOS.controlZones.add(new GOTControlZone(3352, 3555, 570));
		STORMLANDS.controlZones.add(new GOTControlZone(687, 1600, 404));
		SUMMER_ISLANDS.controlZones.add(new GOTControlZone(1714, 2516, 777));
		SUMMER_ISLANDS.controlZones.add(new GOTControlZone(GOTWaypoint.ATAAHUA, 200));
		SUMMER_ISLANDS.controlZones.add(new GOTControlZone(GOTWaypoint.HAUAURU, 200));
		SUMMER_ISLANDS.controlZones.add(new GOTControlZone(GOTWaypoint.KOHURU, 200));
		SUMMER_ISLANDS.controlZones.add(new GOTControlZone(GOTWaypoint.MATAHAU, 200));
		SUMMER_ISLANDS.controlZones.add(new GOTControlZone(GOTWaypoint.MATAO, 200));
		SUMMER_ISLANDS.controlZones.add(new GOTControlZone(GOTWaypoint.NGAHERE, 200));
		SUMMER_ISLANDS.controlZones.add(new GOTControlZone(GOTWaypoint.NGARARA, 200));
		SUMMER_ISLANDS.controlZones.add(new GOTControlZone(GOTWaypoint.PEREKI, 200));
		SUMMER_ISLANDS.controlZones.add(new GOTControlZone(GOTWaypoint.TAURANGA, 200));
		TYROSH.controlZones.add(new GOTControlZone(1297, 1708, 543));
		ULTHOS.controlZones.add(new GOTControlZone(3175, 3599, 570));
		ULTHOS.controlZones.add(new GOTControlZone(4100, 3517, 1109));
		ULTHOS.approvesWarCrimes = true;
		VOLANTIS.controlZones.add(new GOTControlZone(1297, 1708, 543));
		VOLANTIS.controlZones.add(new GOTControlZone(1904, 2146, 554));
		VOLANTIS.approvesWarCrimes = true;
		WESTERLANDS.controlZones.add(new GOTControlZone(703, 1416, 550));
		WESTERLANDS.approvesWarCrimes = true;
		WHITE_WALKER.controlZones.add(new GOTControlZone(360, 297, 240));
		WHITE_WALKER.controlZones.add(new GOTControlZone(494, 551, 206));
		WHITE_WALKER.controlZones.add(new GOTControlZone(546, 245, 191));
		WHITE_WALKER.controlZones.add(new GOTControlZone(667, 239, 186));
		WHITE_WALKER.controlZones.add(new GOTControlZone(696, 504, 207));
		WHITE_WALKER.controlZones.add(new GOTControlZone(757, 573, 267));
		WHITE_WALKER.approvesWarCrimes = true;
		WILDLING.controlZones.add(new GOTControlZone(757, 573, 267));
		WILDLING.approvesWarCrimes = true;
		YI_TI.controlZones.add(new GOTControlZone(3088, 2508, 430));
		YI_TI.controlZones.add(new GOTControlZone(3809, 1955, 1005));
		for (GOTFaction fac : values()) {
			if (fac != WHITE_WALKER && fac != UNALIGNED && fac != HOSTILE) {
				fac.addRank(10.0f, "guest").makeTitle().makeAchievement();
				fac.addRank(50.0f, "friend").makeTitle().makeAchievement();
				fac.addRank(100.0f, "defender").setPledgeRank().makeTitle().makeAchievement();
				fac.addRank(500.0f, "hero").makeTitle().makeAchievement();
				fac.addRank(1000.0f, "leader").makeTitle().makeAchievement();
			}
		}
		WHITE_WALKER.addSpecialRank(1000.0f, "king").setPledgeRank().makeTitle().makeAchievement();
	}

	private GOTFactionRank addRank(float alignment, String name) {
		GOTFactionRank rank = new GOTFactionRank(this, alignment, name);
		ranksSortedDescending.add(rank);
		Collections.sort(ranksSortedDescending);
		return rank;
	}

	private GOTFactionRank addSpecialRank(float alignment, String name) {
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
			int cxMin = zone.getCoordX() - zone.getRadiusCoord();
			int cxMax = zone.getCoordX() + zone.getRadiusCoord();
			int czMin = zone.getCoordZ() - zone.getRadiusCoord();
			int czMax = zone.getCoordZ() + zone.getRadiusCoord();
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

	public void checkAlignmentAchievements(EntityPlayer entityplayer) {
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

	public double distanceToNearestControlZoneInRange(World world, double x, double z, int mapRange) {
		double closestDist = -1.0;
		if (isFactionDimension(world)) {
			int coordRange = GOTWaypoint.mapToWorldR(mapRange);
			for (GOTControlZone zone : controlZones) {
				double dx = x - zone.getCoordX();
				double dz = z - zone.getCoordZ();
				double dSq = dx * dx + dz * dz;
				double dToEdge = Math.sqrt(dSq) - zone.getRadiusCoord();
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
			if (f == this || !f.isPlayableAlignmentFaction() || GOTFactionRelations.getRelations(this, f) != GOTFactionRelations.Relation.ALLY) {
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
		if (isFactionDimension(entityplayer.worldObj) && (dist = distanceToNearestControlZoneInRange(entityplayer.worldObj, entityplayer.posX, entityplayer.posZ, reducedRange = 50)) >= 0.0) {
			double mapDist = GOTWaypoint.worldToMapR(dist);
			float frac = (float) mapDist / reducedRange;
			float mplier = 1.0f - frac;
			return MathHelper.clamp_float(mplier, 0.0f, 1.0f);
		}
		return 0.0f;
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

	public List<GOTFaction> getOthersOfRelation(GOTFactionRelations.Relation rel) {
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
			return pledgeRank.getAlignment();
		}
		return 0.0f;
	}

	public GOTFactionRank getPledgeRank() {
		return pledgeRank;
	}

	public void setPledgeRank(GOTFactionRank rank) {
		if (rank.getFaction() != this) {
			throw new IllegalArgumentException("Incompatible faction!");
		}
		if (pledgeRank != null) {
			throw new IllegalArgumentException("Faction already has a pledge rank!");
		}
		pledgeRank = rank;
	}

	public GOTFactionRank getRank(float alignment) {
		for (GOTFactionRank rank : ranksSortedDescending) {
			if (rank.isDummyRank() || alignment < rank.getAlignment()) {
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

	private boolean inControlZone(World world, double x, double y, double z) {
		if (inDefinedControlZone(world, x, z)) {
			return true;
		}
		double nearbyRange = 24.0;
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(x, y, z, x, y, z).expand(nearbyRange, nearbyRange, nearbyRange);
		List<EntityLivingBase> nearbyNPCs = world.selectEntitiesWithinAABB(EntityLivingBase.class, aabb, new GOTNPCSelectByFaction(this));
		return !nearbyNPCs.isEmpty();
	}

	public boolean inDefinedControlZone(EntityPlayer entityplayer) {
		return inDefinedControlZone(entityplayer, 0);
	}

	public boolean inDefinedControlZone(EntityPlayer entityplayer, int extraMapRange) {
		return inDefinedControlZone(entityplayer.worldObj, entityplayer.posX, entityplayer.posZ, extraMapRange);
	}

	private boolean inDefinedControlZone(World world, double x, double z) {
		return inDefinedControlZone(world, x, z, 0);
	}

	public boolean inDefinedControlZone(World world, double x, double z, int extraMapRange) {
		if (isFactionDimension(world)) {
			if (!controlZonesEnabled(world)) {
				return true;
			}
			for (GOTControlZone zone : controlZones) {
				if (!zone.inZone(x, z, extraMapRange)) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public boolean isAlly(GOTFaction other) {
		GOTFactionRelations.Relation rel = GOTFactionRelations.getRelations(this, other);
		return rel == GOTFactionRelations.Relation.ALLY;
	}

	public boolean isBadRelation(GOTFaction other) {
		GOTFactionRelations.Relation rel = GOTFactionRelations.getRelations(this, other);
		return rel == GOTFactionRelations.Relation.ENEMY || rel == GOTFactionRelations.Relation.MORTAL_ENEMY;
	}

	private boolean isFactionDimension(World world) {
		return world.provider instanceof GOTWorldProvider && factionDimension == GOTDimension.GAME_OF_THRONES;
	}

	public boolean isGoodRelation(GOTFaction other) {
		GOTFactionRelations.Relation rel = GOTFactionRelations.getRelations(this, other);
		return rel == GOTFactionRelations.Relation.ALLY || rel == GOTFactionRelations.Relation.FRIEND;
	}

	public boolean isMortalEnemy(GOTFaction other) {
		GOTFactionRelations.Relation rel = GOTFactionRelations.getRelations(this, other);
		return rel == GOTFactionRelations.Relation.MORTAL_ENEMY;
	}

	public boolean isNeutral(GOTFaction other) {
		return GOTFactionRelations.getRelations(this, other) == GOTFactionRelations.Relation.NEUTRAL;
	}

	public boolean isPlayableAlignmentFaction() {
		return allowPlayer && !hasFixedAlignment;
	}

	private boolean matchesName(String name) {
		return codeName().equals(name);
	}

	private String untranslatedFactionName() {
		return "got.faction." + codeName() + ".name";
	}

	public int getFixedAlignment() {
		return fixedAlignment;
	}

	public void setFixedAlignment(int alignment) {
		hasFixedAlignment = true;
		fixedAlignment = alignment;
	}

	public GOTDimension getFactionDimension() {
		return factionDimension;
	}

	public void setFactionDimension(GOTDimension factionDimension) {
		this.factionDimension = factionDimension;
	}

	public GOTDimension.DimensionRegion getFactionRegion() {
		return factionRegion;
	}

	public void setFactionRegion(GOTDimension.DimensionRegion factionRegion) {
		this.factionRegion = factionRegion;
	}

	public boolean isAllowPlayer() {
		return allowPlayer;
	}

	public void setAllowPlayer(boolean allowPlayer) {
		this.allowPlayer = allowPlayer;
	}

	public boolean isApprovesWarCrimes() {
		return approvesWarCrimes;
	}

	public GOTMapRegion getFactionMapInfo() {
		return factionMapInfo;
	}

	public boolean isHasFixedAlignment() {
		return hasFixedAlignment;
	}

	public int getEggColor() {
		return eggColor;
	}

	public boolean isAllowEntityRegistry() {
		return allowEntityRegistry;
	}

	public List<GOTFactionRank> getRanksSortedDescending() {
		return ranksSortedDescending;
	}

	public Collection<GOTItemBanner.BannerType> getFactionBanners() {
		return factionBanners;
	}
}