package got.common.world.map;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class GOTBeziers {
	public static List<GOTBeziers> allBeziers = new ArrayList<>();

	public static List<GOTBeziers> allRoads = new ArrayList<>();
	public static BezierPointDatabase roadPointDatabase = new BezierPointDatabase();

	public static List<GOTBeziers> allWalls = new ArrayList<>();
	public static BezierPointDatabase wallPointDatabase = new BezierPointDatabase();

	public static int id;
	public BezierPoint[] bezierPoints;
	public List<BezierPoint> endpoints = new ArrayList<>();

	public GOTBeziers(BezierPoint... ends) {
		Collections.addAll(endpoints, ends);
	}

	public static boolean isRoadAt(int x, int z) {
		return isRoadNear(x, z, 4) >= 0.0f;
	}

	public static float isRoadNear(int x, int z, int width) {
		double widthSq = width * width;
		float leastSqRatio = -1.0f;
		List<BezierPoint> points = roadPointDatabase.getPointsForCoords(x, z);
		for (BezierPoint point : points) {
			double dx = point.x - x;
			double dz = point.z - z;
			double distSq = dx * dx + dz * dz;
			if (distSq >= widthSq) {
				continue;
			}
			float f = (float) (distSq / widthSq);
			if (leastSqRatio == -1.0f) {
				leastSqRatio = f;
				continue;
			}
			if (f >= leastSqRatio) {
				continue;
			}
			leastSqRatio = f;
		}
		return leastSqRatio;
	}

	public static boolean isWallAt(int x, int z) {
		return isWallNear(x, z, 4) >= 0.0f;
	}

	public static float isWallNear(int x, int z, int width) {
		double widthSq = width * width;
		float leastSqRatio = -1.0f;
		List<BezierPoint> points = wallPointDatabase.getPointsForCoords(x, z);
		for (BezierPoint point : points) {
			double dx = point.x - x;
			double dz = point.z - z;
			double distSq = dx * dx + dz * dz;
			if (distSq >= widthSq) {
				continue;
			}
			float f = (float) (distSq / widthSq);
			if (leastSqRatio == -1.0f) {
				leastSqRatio = f;
				continue;
			}
			if (f >= leastSqRatio) {
				continue;
			}
			leastSqRatio = f;
		}
		return leastSqRatio;
	}

	public static double[] near(GOTWaypoint wp, double x, double y) {
		return new double[]{wp.imgX + x, wp.imgY + y};
	}

	public static void onInit() {
		allRoads.clear();
		allWalls.clear();
		roadPointDatabase = new BezierPointDatabase();
		wallPointDatabase = new BezierPointDatabase();
		registerWall(id++, GOTWaypoint.WestWatch, GOTWaypoint.ShadowTower, GOTWaypoint.SentinelStand, GOTWaypoint.Greyguard, GOTWaypoint.Stonedoor, GOTWaypoint.HoarfrostHill, GOTWaypoint.Icemark, GOTWaypoint.Nightfort, GOTWaypoint.DeepLake, GOTWaypoint.Queensgate, GOTWaypoint.CastleBlack, GOTWaypoint.Oakenshield, GOTWaypoint.Woodswatch, GOTWaypoint.SableHall, GOTWaypoint.Rimegate, GOTWaypoint.LongBarrow, GOTWaypoint.Torches, GOTWaypoint.Greenguard, GOTWaypoint.EastWatch);
		registerWall(id++, GOTWaypoint.Anbei, GOTWaypoint.Jianmen, GOTWaypoint.Anguo, GOTWaypoint.Anjiang, GOTWaypoint.Dingguo, GOTWaypoint.Pinnu, GOTWaypoint.Pingjiang, GOTWaypoint.Wude, GOTWaypoint.Wusheng, GOTWaypoint.Zhenguo, GOTWaypoint.Lungmen, GOTWaypoint.Pingbei);

		registerWall(id++, new double[]{2847, 1273}, new double[]{2820, 1292}, new double[]{2771, 1308}, new double[]{2732, 1308});
		registerWall(id++, new double[]{2732, 1308}, new double[]{2683, 1294}, new double[]{2628, 1294}, new double[]{2588, 1275});
		registerWall(id++, new double[]{2708, 1230}, new double[]{2683, 1244}, new double[]{2656, 1253}, new double[]{2638, 1252});

		registerHiddenRoad(id++, new double[]{559, 544}, new double[]{596, 544});
		registerRoad(id++, GOTWaypoint.Zamettar, near(GOTWaypoint.Zamettar, 0, -1));
		registerRoad(id++, GOTWaypoint.Appleton, near(GOTWaypoint.Appleton, 0, -1));
		registerRoad(id++, GOTWaypoint.Asabhad, near(GOTWaypoint.Asabhad, -1, 0));
		registerRoad(id++, GOTWaypoint.Asabhad, new double[]{3143, 2263}, new double[]{3190, 2276}, GOTWaypoint.SiQo);
		registerRoad(id++, GOTWaypoint.Asshai, near(GOTWaypoint.Asshai, 0, -1));
		registerRoad(id++, GOTWaypoint.Baoji, near(GOTWaypoint.Baoji, 0, 1));
		registerRoad(id++, GOTWaypoint.Baoji, new double[]{3476, 2218}, new double[]{3454, 2212}, GOTWaypoint.Manjin);
		registerRoad(id++, GOTWaypoint.Baoji, new double[]{3554, 2181}, new double[]{3613, 2167}, GOTWaypoint.Lizhao);
		registerRoad(id++, GOTWaypoint.Baoji, new double[]{3570, 2227}, new double[]{3611, 2256}, new double[]{3634, 2281});
		registerRoad(id++, GOTWaypoint.Barrowtown, GOTWaypoint.Goldgrass);
		registerRoad(id++, GOTWaypoint.Bayasabhad, new double[]{3134, 2098}, new double[]{3132, 2128}, GOTWaypoint.Eijiang);
		registerRoad(id++, GOTWaypoint.Bitterbridge, near(GOTWaypoint.Bitterbridge, 0, 1));
		registerRoad(id++, GOTWaypoint.Blackhaven, near(GOTWaypoint.Blackhaven, -1, 0));
		registerRoad(id++, GOTWaypoint.Braavos, new double[]{1174, 1274}, new double[]{1194, 1300}, new double[]{1208, 1335});
		registerRoad(id++, GOTWaypoint.Bronzegate, near(GOTWaypoint.Bronzegate, 1, 0));
		registerRoad(id++, GOTWaypoint.CasterlyRock, GOTWaypoint.Lannisport);
		registerRoad(id++, GOTWaypoint.CasterlyRock, near(GOTWaypoint.CasterlyRock, -1, 0));
		registerRoad(id++, near(GOTWaypoint.CastleBlack, 0, 1), GOTWaypoint.Moletown);
		registerRoad(id++, near(GOTWaypoint.CastleBlack, 0, 1), GOTWaypoint.CastleBlack);
		registerRoad(id++, near(GOTWaypoint.Anjiang, 0, 1), GOTWaypoint.Anjiang);
		registerRoad(id++, near(GOTWaypoint.TraderTown, 1, 0), GOTWaypoint.TraderTown);
		registerRoad(id++, near(GOTWaypoint.Anjiang, 0, 1), near(GOTWaypoint.TraderTown, 1, 0));
		registerRoad(id++, GOTWaypoint.Chroyane, GOTWaypoint.ArNoy, GOTWaypoint.Qohor, GOTWaypoint.VaesKhadokh, GOTWaypoint.VaesGorqoyi, GOTWaypoint.VaesKhewo, GOTWaypoint.VojjorSamvi, GOTWaypoint.Sathar, GOTWaypoint.VaesLeqse, GOTWaypoint.VaesAthjikhari);
		registerRoad(id++, GOTWaypoint.Chroyane, GOTWaypoint.NySar, GOTWaypoint.Norvos);
		registerRoad(id++, GOTWaypoint.Chroyane, GOTWaypoint.Selhorys, GOTWaypoint.Valysar, GOTWaypoint.VolonTherys);
		registerRoad(id++, GOTWaypoint.Crakehall, GOTWaypoint.OldOak);
		registerRoad(id++, GOTWaypoint.Crakehall, near(GOTWaypoint.Crakehall, -1, 0));
		registerRoad(id++, GOTWaypoint.CrossroadsInn, GOTWaypoint.Darry);
		registerRoad(id++, GOTWaypoint.CrossroadsInn, GOTWaypoint.Harroway);
		registerRoad(id++, GOTWaypoint.DarkDell, near(GOTWaypoint.DarkDell, 0, 1));
		registerRoad(id++, GOTWaypoint.Darry, GOTWaypoint.WhiteWalls, GOTWaypoint.Hayford);
		registerRoad(id++, GOTWaypoint.Darry, near(GOTWaypoint.Darry, 1, 0));
		registerRoad(id++, GOTWaypoint.Duskendale, near(GOTWaypoint.Duskendale, -2, 0));
		registerRoad(id++, GOTWaypoint.Eijiang, near(GOTWaypoint.Eijiang, 0, 1));
		registerRoad(id++, GOTWaypoint.FairMarket, near(GOTWaypoint.FairMarket, 4, -4));
		registerRoad(id++, GOTWaypoint.Felwood, near(GOTWaypoint.Felwood, 0, 1));
		registerRoad(id++, GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2);
		registerRoad(id++, GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2);
		registerRoad(id++, GOTWaypoint.FiveForts1, new double[]{3687, 1921}, new double[]{3600, 1973}, GOTWaypoint.Vaibei);
		registerRoad(id++, GOTWaypoint.FiveForts2, GOTWaypoint.FiveForts3);
		registerRoad(id++, GOTWaypoint.FiveForts3, GOTWaypoint.FiveForts4);
		registerRoad(id++, GOTWaypoint.FiveForts4, GOTWaypoint.FiveForts5);
		registerRoad(id++, GOTWaypoint.FiveForts5, new double[]{3797, 2008}, new double[]{3732, 2063}, GOTWaypoint.Yibin);
		registerRoad(id++, GOTWaypoint.GarnetGrove, near(GOTWaypoint.GarnetGrove, -1, 0));
		registerRoad(id++, GOTWaypoint.GarnetGrove, new double[]{379, 2023}, new double[]{397, 2045}, GOTWaypoint.SunHouse);
		registerRoad(id++, GOTWaypoint.GateOfTheMoon, near(GOTWaypoint.GateOfTheMoon, 0, 1));
		registerRoad(id++, GOTWaypoint.GoldenTooth, near(GOTWaypoint.GoldenTooth, 0, 1));
		registerRoad(id++, GOTWaypoint.Goldgrass, near(GOTWaypoint.Goldgrass, 0, 1));
		registerRoad(id++, GOTWaypoint.Goldgrass, near(GOTWaypoint.MoatKailin, -1, 0));
		registerRoad(id++, GOTWaypoint.Harroway, GOTWaypoint.CastleLychester, GOTWaypoint.StoneHedge, GOTWaypoint.Riverrun, GOTWaypoint.GoldenTooth, GOTWaypoint.Sarsfield, GOTWaypoint.Oxcross, GOTWaypoint.CasterlyRock);
		registerRoad(id++, GOTWaypoint.Hayford, GOTWaypoint.KingsLanding);
		registerRoad(id++, GOTWaypoint.Hayford, GOTWaypoint.Rosby, GOTWaypoint.Duskendale, GOTWaypoint.HollardCastle, GOTWaypoint.RooksRest);
		registerRoad(id++, GOTWaypoint.Hayford, near(GOTWaypoint.Hayford, -1, 0));
		registerRoad(id++, GOTWaypoint.Hesh, near(GOTWaypoint.Hesh, 1, 0));
		registerRoad(id++, GOTWaypoint.Highgarden, GOTWaypoint.Nightsong, GOTWaypoint.TowerOfJoy, GOTWaypoint.Kingsgrave, GOTWaypoint.SkyReach);
		registerRoad(id++, GOTWaypoint.Highgarden, GOTWaypoint.Whitegrove, GOTWaypoint.Oldtown);
		registerRoad(id++, GOTWaypoint.Highgarden, near(GOTWaypoint.Highgarden, 0, -1));
		registerRoad(id++, GOTWaypoint.HornHill, near(GOTWaypoint.HornHill, 0, -1));
		registerRoad(id++, GOTWaypoint.Jinqi, near(GOTWaypoint.Jinqi, -1, 0));
		registerRoad(id++, GOTWaypoint.Jinqi, new double[]{3620, 2347}, new double[]{3621, 2315}, new double[]{3634, 2281});
		registerRoad(id++, GOTWaypoint.Jinqi, new double[]{3753, 2449}, new double[]{3810, 2630}, near(GOTWaypoint.Asshai, 0, -1));
		registerRoad(id++, GOTWaypoint.KingsLanding, near(GOTWaypoint.KingsLanding, 2, 0));
		registerRoad(id++, GOTWaypoint.KingsLanding, near(GOTWaypoint.KingsLanding, 5, 28));
		registerRoad(id++, GOTWaypoint.Kingsgrave, near(GOTWaypoint.Kingsgrave, -1, 0));
		registerRoad(id++, GOTWaypoint.Kosrak, near(GOTWaypoint.Kosrak, 1, 0));
		registerRoad(id++, GOTWaypoint.KrazaajHas, GOTWaypoint.VaesMejhah);
		registerRoad(id++, GOTWaypoint.KrazaajHas, new double[]{2430, 1900}, GOTWaypoint.Hesh, GOTWaypoint.Lhazosh, new double[]{2447, 2138}, GOTWaypoint.VaesOrvik);
		registerRoad(id++, GOTWaypoint.Lannisport, near(GOTWaypoint.Lannisport, -1, 0));
		registerRoad(id++, GOTWaypoint.Lannisport, new double[]{371, 1622}, GOTWaypoint.Crakehall);
		registerRoad(id++, GOTWaypoint.Lannisport, new double[]{477, 1572}, new double[]{526, 1598}, new double[]{570, 1625}, new double[]{656, 1606}, new double[]{710, 1633}, GOTWaypoint.KingsLanding);
		registerRoad(id++, GOTWaypoint.Lhazosh, new double[]{2498, 1977}, GOTWaypoint.Kosrak);
		registerRoad(id++, GOTWaypoint.Lhazosh, near(GOTWaypoint.Lhazosh, -1, 0));
		registerRoad(id++, GOTWaypoint.LittleValyria, GOTWaypoint.ValyrianRoad);
		registerRoad(id++, GOTWaypoint.Lizhao, near(GOTWaypoint.Lizhao, 1, 0));
		registerRoad(id++, GOTWaypoint.Maidenpool, GOTWaypoint.Duskendale);
		registerRoad(id++, GOTWaypoint.Manjin, near(GOTWaypoint.Manjin, 1, 0));
		registerRoad(id++, GOTWaypoint.Mantarys, new double[]{1831, 2092}, GOTWaypoint.Oros);
		registerRoad(id++, GOTWaypoint.Meereen, GOTWaypoint.KrazaajHas);
		registerRoad(id++, GOTWaypoint.Meereen, new double[]{2243, 1928}, new double[]{2183, 1928}, GOTWaypoint.Bhorash);
		registerRoad(id++, GOTWaypoint.Meereen, near(GOTWaypoint.Meereen, -9, 55), new double[]{2219, 2124}, GOTWaypoint.Astapor);
		registerRoad(id++, GOTWaypoint.Moletown, new double[]{747, 742}, new double[]{711, 783}, new double[]{672, 826}, near(GOTWaypoint.Winterfell, 0, -1));
		registerRoad(id++, GOTWaypoint.Myr, new double[]{1338, 1874}, GOTWaypoint.Chroyane);
		registerRoad(id++, GOTWaypoint.Nightsong, near(GOTWaypoint.Nightsong, 0, 1));
		registerRoad(id++, GOTWaypoint.OldOak, near(GOTWaypoint.OldOak, -1, 0));
		registerRoad(id++, GOTWaypoint.OldOak, new double[]{438, 1773}, GOTWaypoint.Highgarden);
		registerRoad(id++, GOTWaypoint.Oldtown, near(GOTWaypoint.Oldtown, -1, 0));
		registerRoad(id++, GOTWaypoint.Oldtown, new double[]{393, 1966}, new double[]{377, 1988}, near(GOTWaypoint.ThreeTowers1, 1, 0));
		registerRoad(id++, GOTWaypoint.Pentos, GOTWaypoint.GhoyanDrohe, GOTWaypoint.NySar, GOTWaypoint.ArNoy);
		registerRoad(id++, GOTWaypoint.PortYhos, GOTWaypoint.VaesOrvik);
		registerRoad(id++, GOTWaypoint.RamsGate, near(GOTWaypoint.RamsGate, -1, 0));
		registerRoad(id++, GOTWaypoint.Rhyos, GOTWaypoint.Oros);
		registerRoad(id++, GOTWaypoint.RillwaterCrossing, near(GOTWaypoint.RillwaterCrossing, 0, 1));
		registerRoad(id++, GOTWaypoint.RisvellsCastle, near(GOTWaypoint.RisvellsCastle, 0, 1));
		registerRoad(id++, GOTWaypoint.Riverrun, near(GOTWaypoint.Riverrun, 1, 0));
		registerRoad(id++, GOTWaypoint.RooksRest, near(GOTWaypoint.RooksRest, 0, 1));
		registerRoad(id++, GOTWaypoint.Rosby, near(GOTWaypoint.Rosby, 0, -1));
		registerRoad(id++, GOTWaypoint.SarMell, new double[]{1559, 2045}, GOTWaypoint.Volantis);
		registerRoad(id++, GOTWaypoint.Sarsfield, near(GOTWaypoint.Sarsfield, 0, -1));
		registerRoad(id++, GOTWaypoint.ServinsCastle, near(GOTWaypoint.Dreadfort, -1, 0));
		registerRoad(id++, GOTWaypoint.ServinsCastle, near(GOTWaypoint.ServinsCastle, -1, 0));
		registerRoad(id++, GOTWaypoint.SiQo, near(GOTWaypoint.SiQo, 1, 0));
		registerRoad(id++, GOTWaypoint.SkyReach, near(GOTWaypoint.SkyReach, 0, 1));
		registerRoad(id++, GOTWaypoint.Smithyton, near(GOTWaypoint.Smithyton, 0, 1));
		registerRoad(id++, GOTWaypoint.Starfall, near(GOTWaypoint.Starfall, 0, -1));
		registerRoad(id++, GOTWaypoint.StoneHedge, near(GOTWaypoint.StoneHedge, 0, 1));
		registerRoad(id++, GOTWaypoint.StormsEnd, near(GOTWaypoint.StormsEnd, 0, -1));
		registerRoad(id++, GOTWaypoint.SunHouse, near(GOTWaypoint.SunHouse, 0, -1));
		registerRoad(id++, GOTWaypoint.SunHouse, new double[]{450, 2033}, new double[]{471, 2025}, GOTWaypoint.Starfall);
		registerRoad(id++, near(GOTWaypoint.TheEyrie, 0, 1), GOTWaypoint.GateOfTheMoon, GOTWaypoint.BloodyGate, GOTWaypoint.CrossroadsInn);
		registerRoad(id++, near(GOTWaypoint.TheEyrie, 0, 1), GOTWaypoint.TheEyrie);
		registerRoad(id++, near(GOTWaypoint.BloodyGate, 0, -1), GOTWaypoint.BloodyGate);
		registerRoad(id++, GOTWaypoint.ThreeTowers1, near(GOTWaypoint.ThreeTowers1, 1, 0));
		registerRoad(id++, GOTWaypoint.ThreeTowers2, near(GOTWaypoint.ThreeTowers1, 1, 0));
		registerRoad(id++, GOTWaypoint.ThreeTowers3, near(GOTWaypoint.ThreeTowers1, 1, 0));
		registerRoad(id++, near(GOTWaypoint.ThreeTowers1, 1, 0), new double[]{353, 2005}, GOTWaypoint.GarnetGrove);
		registerRoad(id++, GOTWaypoint.Tiqui, near(GOTWaypoint.Tiqui, 0, -1));
		registerRoad(id++, GOTWaypoint.Tiqui, new double[]{3240, 2114}, new double[]{3193, 2134}, GOTWaypoint.Eijiang);
		registerRoad(id++, GOTWaypoint.Tiqui, new double[]{3374, 2123}, new double[]{3425, 2147}, GOTWaypoint.Manjin);
		registerRoad(id++, GOTWaypoint.TorhensSquare, near(GOTWaypoint.BlackPool, -1, 0));
		registerRoad(id++, GOTWaypoint.TraderTown, near(GOTWaypoint.Tiqui, 1, 0), GOTWaypoint.SiQo, GOTWaypoint.Yin);
		registerRoad(id++, GOTWaypoint.TraderTown, near(GOTWaypoint.TraderTown, 0, -1));
		registerRoad(id++, GOTWaypoint.TraderTown, new double[]{3411, 1920}, new double[]{3473, 1951}, near(GOTWaypoint.Vaibei, -1, 0));
		registerRoad(id++, GOTWaypoint.Tumbleton, GOTWaypoint.Smithyton);
		registerRoad(id++, GOTWaypoint.Tumbleton, near(GOTWaypoint.Tumbleton, 0, -1));
		registerRoad(id++, GOTWaypoint.TwinsLeft, GOTWaypoint.Seagard);
		registerRoad(id++, GOTWaypoint.TwinsRight, GOTWaypoint.TwinsLeft);
		registerRoad(id++, GOTWaypoint.VaesKhadokh, GOTWaypoint.Rathylar, GOTWaypoint.Hornoth, GOTWaypoint.Kyth, GOTWaypoint.Saath);
		registerRoad(id++, GOTWaypoint.VaesMejhah, GOTWaypoint.VaesJini, GOTWaypoint.Samyriana, new double[]{3219, 1895}, GOTWaypoint.TraderTown);
		registerRoad(id++, GOTWaypoint.Vaibei, near(GOTWaypoint.Vaibei, -1, 0));
		registerRoad(id++, GOTWaypoint.Vaibei, near(GOTWaypoint.Vaibei, 0, -1));
		registerRoad(id++, GOTWaypoint.Volantis, GOTWaypoint.LittleValyria, GOTWaypoint.Anogaria, GOTWaypoint.Mantarys, GOTWaypoint.Bhorash);
		registerRoad(id++, GOTWaypoint.VolonTherys, GOTWaypoint.SarMell);
		registerRoad(id++, GOTWaypoint.Whitegrove, near(GOTWaypoint.Whitegrove, -1, 0));
		registerRoad(id++, GOTWaypoint.Whitegrove, near(GOTWaypoint.Whitegrove, -16, 33));
		registerRoad(id++, GOTWaypoint.Wyl, near(GOTWaypoint.Wyl, 0, -1));
		registerRoad(id++, GOTWaypoint.Yibin, GOTWaypoint.Lizhao, new double[]{3679, 2188}, new double[]{3655, 2241}, new double[]{3634, 2281});
		registerRoad(id++, GOTWaypoint.Yibin, near(GOTWaypoint.Yibin, -1, 0));
		registerRoad(id++, GOTWaypoint.Yibin, new double[]{3622, 2031}, new double[]{3560, 2023}, GOTWaypoint.Vaibei);
		registerRoad(id++, GOTWaypoint.Yin, near(GOTWaypoint.Yin, 0, 1));
		registerRoad(id++, GOTWaypoint.Yronwood, near(GOTWaypoint.Yronwood, 1, 0));
		registerRoad(id++, GOTWaypoint.Yronwood, new double[]{680, 1953}, GOTWaypoint.Wyl, GOTWaypoint.Blackhaven, GOTWaypoint.Summerhall, GOTWaypoint.Bronzegate);
		registerRoad(id++, GOTWaypoint.Yunkai, near(GOTWaypoint.Meereen, -9, 55));
		registerRoad(id++, GOTWaypoint.Meereen, near(GOTWaypoint.Meereen, -1, 0.413));
		registerRoad(id++, GOTWaypoint.Yunkai, near(GOTWaypoint.Yunkai, -1, 0.413));
		registerRoad(id++, GOTWaypoint.Astapor, near(GOTWaypoint.Astapor, -1, 0.413));
		registerRoad(id++, GOTWaypoint.Yunnan, near(GOTWaypoint.Yunnan, 1, 0));

		registerRoad(id++, GOTWaypoint.Mantarys, near(GOTWaypoint.Mantarys, -0.413, -1));
		registerRoad(id++, GOTWaypoint.Braavos, near(GOTWaypoint.Braavos, -0.413, -1));
		registerRoad(id++, GOTWaypoint.Norvos, near(GOTWaypoint.Norvos, -0.413, -1));
		registerRoad(id++, GOTWaypoint.Qohor, near(GOTWaypoint.Qohor, -0.413, -1));
		registerRoad(id++, GOTWaypoint.Pentos, near(GOTWaypoint.Pentos, -1, 0.413));
		registerRoad(id++, GOTWaypoint.Selhorys, near(GOTWaypoint.Selhorys, -1, 0.413));
		registerRoad(id++, GOTWaypoint.Valysar, near(GOTWaypoint.Valysar, -1, 0.413));
		registerRoad(id++, GOTWaypoint.VolonTherys, near(GOTWaypoint.VolonTherys, 0.413, 1));
		registerRoad(id++, GOTWaypoint.Volantis, near(GOTWaypoint.Volantis, -1, 0.413));
		registerRoad(id++, GOTWaypoint.LittleValyria, near(GOTWaypoint.LittleValyria, 0.413, 1));
		registerRoad(id++, GOTWaypoint.Myr, near(GOTWaypoint.Myr, -1, 0.413));
		registerRoad(id++, GOTWaypoint.PortYhos, near(GOTWaypoint.PortYhos, 0.413, 1));
		registerRoad(id++, GOTWaypoint.Qarkash, near(GOTWaypoint.Qarkash, 0.413, 1));
		registerRoad(id++, GOTWaypoint.Qarth, near(GOTWaypoint.Qarth, 0.413, 1));

		registerRoad(id++, GOTWaypoint.Zamettar, new double[]{2150, 2793}, GOTWaypoint.Yeen);
		registerRoad(id++, near(GOTWaypoint.BlackPool, -1, 0), near(GOTWaypoint.BlackPool, 1, 0));
		registerRoad(id++, near(GOTWaypoint.BlackPool, 1, 0), near(GOTWaypoint.RamsGate, -1, 0));
		registerRoad(id++, near(GOTWaypoint.Dreadfort, -1, 0), near(GOTWaypoint.Dreadfort, 1, 0));
		registerRoad(id++, near(GOTWaypoint.Dreadfort, 1, 0), new double[]{879, 840}, GOTWaypoint.Karhold);
		registerRoad(id++, near(GOTWaypoint.KingsLanding, 5, 28), GOTWaypoint.Smithyton, GOTWaypoint.Bitterbridge, GOTWaypoint.Appleton, GOTWaypoint.DarkDell, GOTWaypoint.Highgarden);
		registerRoad(id++, near(GOTWaypoint.Maidenpool, -1, 0), near(GOTWaypoint.Maidenpool, 1, 0));
		registerRoad(id++, near(GOTWaypoint.MoatKailin, -1, 0), near(GOTWaypoint.MoatKailin, 1, 0));
		registerRoad(id++, near(GOTWaypoint.MoatKailin, 0, -1), near(GOTWaypoint.MoatKailin, 0, 1));
		registerRoad(id++, near(GOTWaypoint.MoatKailin, 0, 1), new double[]{656, 1217}, near(GOTWaypoint.TwinsRight, 41, -19), GOTWaypoint.TwinsRight);
		registerRoad(id++, near(GOTWaypoint.MoatKailin, 1, 0), new double[]{685, 1110}, near(GOTWaypoint.WhiteHarbour, -9, 0));
		registerRoad(id++, near(GOTWaypoint.RillwaterCrossing, 0, 1), GOTWaypoint.RisvellsCastle, GOTWaypoint.Barrowtown);
		registerRoad(id++, near(GOTWaypoint.Seagard, 0, -1), near(GOTWaypoint.Seagard, 0, 1));
		registerRoad(id++, near(GOTWaypoint.StormsEnd, 0, -1), GOTWaypoint.Bronzegate, GOTWaypoint.Felwood, near(GOTWaypoint.KingsLanding, 5, 28));
		registerRoad(id++, near(GOTWaypoint.TwinsRight, 41, -19), new double[]{655, 1331}, GOTWaypoint.CrossroadsInn);
		registerRoad(id++, near(GOTWaypoint.WhiteHarbour, -9, 0), GOTWaypoint.WhiteHarbour);
		registerRoad(id++, near(GOTWaypoint.Winterfell, 0, -1), near(GOTWaypoint.Winterfell, 0, 1));
		registerRoad(id++, near(GOTWaypoint.Winterfell, 0, 1), GOTWaypoint.ServinsCastle, new double[]{628, 973}, near(GOTWaypoint.MoatKailin, 0, -1));
		registerRoad(id++, new double[]{1251, 1464}, new double[]{1246, 1424}, new double[]{1227, 1388}, new double[]{1208, 1335});
		registerRoad(id++, new double[]{1251, 1464}, new double[]{1267, 1498}, new double[]{1314, 1535}, GOTWaypoint.GhoyanDrohe);
		registerRoad(id++, new double[]{2723, 2245}, GOTWaypoint.Qarkash);
		registerRoad(id++, new double[]{2723, 2245}, new double[]{2687, 2231}, new double[]{2626, 2258}, GOTWaypoint.PortYhos);
		registerRoad(id++, new double[]{2723, 2245}, new double[]{2763, 2273}, new double[]{2834, 2268}, GOTWaypoint.Qarth);
		registerRoad(id++, new double[]{2995, 2293}, new double[]{2972, 2274}, new double[]{2931, 2262}, GOTWaypoint.Qarth);
		registerRoad(id++, new double[]{2995, 2293}, new double[]{3034, 2243}, new double[]{3081, 2240}, GOTWaypoint.Asabhad);
		registerRoad(id++, new double[]{3081, 2240}, new double[]{3088, 2202}, new double[]{3093, 2162}, GOTWaypoint.Eijiang);
		registerRoad(id++, new double[]{3634, 2281}, new double[]{3688, 2321}, new double[]{3756, 2321}, GOTWaypoint.Yunnan);
	}

	public static void registerHiddenRoad(int id, Object... waypoints) {
		ArrayList<BezierPoint> points = new ArrayList<>();
		for (Object obj : waypoints) {
			if (obj instanceof GOTWaypoint) {
				GOTWaypoint wp = (GOTWaypoint) obj;
				points.add(new BezierPoint(wp.getXCoord(), wp.getZCoord(), true, false));
			} else if (obj instanceof double[]) {
				double[] coords = (double[]) obj;
				if (coords.length == 2) {
					points.add(new BezierPoint(GOTWaypoint.mapToWorldX(coords[0]), GOTWaypoint.mapToWorldZ(coords[1]), false, false));
					continue;
				}
				throw new IllegalArgumentException("Coords length must be 2!");
			}
		}
		BezierPoint[] array = points.toArray(new BezierPoint[0]);
		BezierCurves.getSplines(array, false);
	}

	public static void registerRoad(int id, Object... waypoints) {
		ArrayList<BezierPoint> points = new ArrayList<>();
		for (Object obj : waypoints) {
			if (obj instanceof GOTWaypoint) {
				GOTWaypoint wp = (GOTWaypoint) obj;
				points.add(new BezierPoint(wp.getXCoord(), wp.getZCoord(), true, false));
			} else if (obj instanceof double[]) {
				double[] coords = (double[]) obj;
				if (coords.length == 2) {
					points.add(new BezierPoint(GOTWaypoint.mapToWorldX(coords[0]), GOTWaypoint.mapToWorldZ(coords[1]), false, false));
					continue;
				}
				throw new IllegalArgumentException("Coords length must be 2!");
			}
		}
		BezierPoint[] array = points.toArray(new BezierPoint[0]);
		GOTBeziers[] beziers = BezierCurves.getSplines(array, false);
		allRoads.addAll(Arrays.asList(beziers));
		allBeziers.addAll(Arrays.asList(beziers));
	}

	public static void registerWall(int id, Object... waypoints) {
		ArrayList<BezierPoint> points = new ArrayList<>();
		for (Object obj : waypoints) {
			if (obj instanceof GOTWaypoint) {
				GOTWaypoint wp = (GOTWaypoint) obj;
				points.add(new BezierPoint(wp.getXCoord(), wp.getZCoord(), true, true));
			} else if (obj instanceof double[]) {
				double[] coords = (double[]) obj;
				if (coords.length == 2) {
					points.add(new BezierPoint(GOTWaypoint.mapToWorldX(coords[0]), GOTWaypoint.mapToWorldZ(coords[1]), false, true));
					continue;
				}
				throw new IllegalArgumentException("Coords length must be 2!");
			}
		}
		BezierPoint[] array = points.toArray(new BezierPoint[0]);
		GOTBeziers[] beziers = BezierCurves.getSplines(array, true);
		allWalls.addAll(Arrays.asList(beziers));
		allBeziers.addAll(Arrays.asList(beziers));
	}

	public static class BezierCurves {
		public static int bezierLengthFactor = 1;

		public static BezierPoint bezier(BezierPoint a, BezierPoint b, BezierPoint c, BezierPoint d, double t, boolean wall) {
			BezierPoint ab = lerp(a, b, t, wall);
			BezierPoint bc = lerp(b, c, t, wall);
			BezierPoint cd = lerp(c, d, t, wall);
			BezierPoint abbc = lerp(ab, bc, t, wall);
			BezierPoint bccd = lerp(bc, cd, t, wall);
			return lerp(abbc, bccd, t, wall);
		}

		public static double[][] getControlPoints(double[] src) {
			int i;
			int length = src.length - 1;
			double[] p1 = new double[length];
			double[] p2 = new double[length];
			double[] a = new double[length];
			double[] b = new double[length];
			double[] c = new double[length];
			double[] r = new double[length];
			a[0] = 0.0;
			b[0] = 2.0;
			c[0] = 1.0;
			r[0] = src[0] + 2.0 * src[1];
			for (i = 1; i < length - 1; ++i) {
				a[i] = 1.0;
				b[i] = 4.0;
				c[i] = 1.0;
				r[i] = 4.0 * src[i] + 2.0 * src[i + 1];
			}
			a[length - 1] = 2.0;
			b[length - 1] = 7.0;
			c[length - 1] = 0.0;
			r[length - 1] = 8.0 * src[length - 1] + src[length];
			for (i = 1; i < length; ++i) {
				double m = a[i] / b[i - 1];
				b[i] = b[i] - m * c[i - 1];
				r[i] = r[i] - m * r[i - 1];
			}
			p1[length - 1] = r[length - 1] / b[length - 1];
			for (i = length - 2; i >= 0; --i) {
				p1[i] = (r[i] - c[i] * p1[i + 1]) / b[i];
			}
			for (i = 0; i < length - 1; ++i) {
				p2[i] = 2.0 * src[i + 1] - p1[i + 1];
			}
			p2[length - 1] = 0.5 * (src[length] + p1[length - 1]);
			return new double[][]{p1, p2};
		}

		public static GOTBeziers[] getSplines(BezierPoint[] waypoints, boolean wall) {
			if (waypoints.length == 2) {
				BezierPoint p1 = waypoints[0];
				BezierPoint p2 = waypoints[1];
				GOTBeziers bezier = new GOTBeziers(p1, p2);
				double dx = p2.x - p1.x;
				double dz = p2.z - p1.z;
				int bezierLength = (int) Math.round(Math.sqrt(dx * dx + dz * dz));
				int points = bezierLength * bezierLengthFactor;
				bezier.bezierPoints = new BezierPoint[points];
				for (int l = 0; l < points; ++l) {
					BezierPoint point;
					double t = (double) l / points;
					bezier.bezierPoints[l] = point = new BezierPoint(p1.x + dx * t, p1.z + dz * t, false, wall);
					if (wall) {
						wallPointDatabase.add(point);
					} else {
						roadPointDatabase.add(point);
					}
				}
				return new GOTBeziers[]{bezier};
			}
			int length = waypoints.length;
			double[] x = new double[length];
			double[] z = new double[length];
			for (int i = 0; i < length; ++i) {
				x[i] = waypoints[i].x;
				z[i] = waypoints[i].z;
			}
			double[][] controlX = getControlPoints(x);
			double[][] controlZ = getControlPoints(z);
			int controlPoints = controlX[0].length;
			BezierPoint[] controlPoints1 = new BezierPoint[controlPoints];
			BezierPoint[] controlPoints2 = new BezierPoint[controlPoints];
			for (int i = 0; i < controlPoints; ++i) {
				BezierPoint p1 = new BezierPoint(controlX[0][i], controlZ[0][i], false, wall);
				BezierPoint p2 = new BezierPoint(controlX[1][i], controlZ[1][i], false, wall);
				controlPoints1[i] = p1;
				controlPoints2[i] = p2;
			}
			GOTBeziers[] beziers = new GOTBeziers[length - 1];
			for (int i = 0; i < beziers.length; ++i) {
				GOTBeziers bezier;
				BezierPoint p1 = waypoints[i];
				BezierPoint p2 = waypoints[i + 1];
				BezierPoint cp1 = controlPoints1[i];
				BezierPoint cp2 = controlPoints2[i];
				beziers[i] = bezier = new GOTBeziers(p1, p2);
				double dx = p2.x - p1.x;
				double dz = p2.z - p1.z;
				int bezierLength = (int) Math.round(Math.sqrt(dx * dx + dz * dz));
				int points = bezierLength * bezierLengthFactor;
				bezier.bezierPoints = new BezierPoint[points];
				for (int l = 0; l < points; ++l) {
					BezierPoint point;
					double t = (double) l / points;
					bezier.bezierPoints[l] = point = bezier(p1, cp1, cp2, p2, t, wall);
					if (wall) {
						wallPointDatabase.add(point);
					} else {
						roadPointDatabase.add(point);
					}
				}
			}
			return beziers;
		}

		public static BezierPoint lerp(BezierPoint a, BezierPoint b, double t, boolean wall) {
			double x = a.x + (b.x - a.x) * t;
			double z = a.z + (b.z - a.z) * t;
			return new BezierPoint(x, z, false, wall);
		}
	}

	public static class BezierPoint {
		public double x;
		public double z;
		public boolean isWaypoint;
		public boolean isWall;

		public BezierPoint(double i, double j, boolean flag, boolean wall) {
			x = i;
			z = j;
			isWaypoint = flag;
			isWall = wall;
		}
	}

	public static class BezierPointDatabase {
		public static int COORD_LOOKUP_SIZE = 1000;
		public Map<Pair<Integer, Integer>, List<BezierPoint>> pointMap = new HashMap<>();

		public void add(BezierPoint point) {
			int x = (int) Math.round(point.x / 1000.0);
			int z = (int) Math.round(point.z / 1000.0);
			int overlap = 1;
			for (int i = -overlap; i <= overlap; ++i) {
				for (int k = -overlap; k <= overlap; ++k) {
					int xKey = x + i;
					int zKey = z + k;
					getBezierList(xKey, zKey, true).add(point);
				}
			}
		}

		public List<BezierPoint> getBezierList(int xKey, int zKey, boolean addToMap) {
			Pair<Integer, Integer> key = Pair.of(xKey, zKey);
			List<BezierPoint> list = pointMap.get(key);
			if (list == null) {
				list = new ArrayList<>();
				if (addToMap) {
					pointMap.put(key, list);
				}
			}
			return list;
		}

		public List<BezierPoint> getPointsForCoords(int x, int z) {
			int x1 = x / 1000;
			int z1 = z / 1000;
			return getBezierList(x1, z1, false);
		}
	}
}