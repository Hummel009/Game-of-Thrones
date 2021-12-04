package got.common.world.map;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

public class GOTBezier {
	public static List<GOTBezier> allBeziers = new ArrayList<>();
	public static BezierPointDatabase bezierPointDatabase = new BezierPointDatabase();
	public static int beziers = allBeziers.size();
	public static int id = 0;
	public static boolean isWall = false;
	public BezierPoint[] bezierPoints;
	public List<BezierPoint> endpoints = new ArrayList<>();
	public String bezierName;

	public GOTBezier(String name, BezierPoint... ends) {
		bezierName = name;
		Collections.addAll(endpoints, ends);
	}

	public String getDisplayName() {
		return null;
	}

	public static int[] ingameXZ(int ingameX, int ingameZ) {
		return new int[] { (int) ((ingameX / 128) + 809.5D), (int) ((ingameZ / 128) + 729.5D) };
	}

	public static boolean isBezierAt(int x, int z) {
		return GOTBezier.isBezierNear(x, z, 4) >= 0.0f;
	}

	public static boolean isWall() {
		return isWall;
	}

	public static float isBezierNear(int x, int z, int width) {
		double widthSq = width * width;
		float leastSqRatio = -1.0f;
		List<BezierPoint> points = bezierPointDatabase.getPointsForCoords(x, z);
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

	public static int[] near(GOTWaypoint wp, int x, int y) {
		return new int[] { wp.imgX + x, wp.imgY + y };
	}

	public static void onInit() {
		allBeziers.clear();
		bezierPointDatabase = new BezierPointDatabase();
		GOTBezier.registerRoad(id++, GOTWaypoint.Appleton, near(GOTWaypoint.Appleton, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Asabhad, near(GOTWaypoint.Asabhad, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Asabhad, new int[] { 3143, 2263 }, new int[] { 3190, 2276 }, GOTWaypoint.SiQo);
		GOTBezier.registerRoad(id++, GOTWaypoint.Asshai, near(GOTWaypoint.Asshai, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Baoji, near(GOTWaypoint.Baoji, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Baoji, new int[] { 3476, 2218 }, new int[] { 3454, 2212 }, GOTWaypoint.Manjin);
		GOTBezier.registerRoad(id++, GOTWaypoint.Baoji, new int[] { 3554, 2181 }, new int[] { 3613, 2167 }, GOTWaypoint.Lizhao);
		GOTBezier.registerRoad(id++, GOTWaypoint.Baoji, new int[] { 3570, 2227 }, new int[] { 3611, 2256 }, new int[] { 3634, 2281 });
		GOTBezier.registerRoad(id++, GOTWaypoint.Barrowtown, GOTWaypoint.Goldgrass);
		GOTBezier.registerRoad(id++, GOTWaypoint.Bayasabhad, new int[] { 3134, 2098 }, new int[] { 3132, 2128 }, GOTWaypoint.Eijiang);
		GOTBezier.registerRoad(id++, GOTWaypoint.Bitterbridge, near(GOTWaypoint.Bitterbridge, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Blackhaven, near(GOTWaypoint.Blackhaven, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Braavos, new int[] { 1174, 1274 }, new int[] { 1194, 1300 }, new int[] { 1208, 1335 });
		GOTBezier.registerRoad(id++, GOTWaypoint.Bronzegate, near(GOTWaypoint.Bronzegate, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.CasterlyRock, GOTWaypoint.Lannisport);
		GOTBezier.registerRoad(id++, GOTWaypoint.CasterlyRock, near(GOTWaypoint.CasterlyRock, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.CastleBlack, GOTWaypoint.Moletown);
		GOTBezier.registerRoad(id++, GOTWaypoint.Chroyane, GOTWaypoint.ArNoy, GOTWaypoint.Qohor, GOTWaypoint.VaesKhadokh, GOTWaypoint.VaesGorqoyi, GOTWaypoint.VaesKhewo, GOTWaypoint.VojjorSamvi, GOTWaypoint.Sathar, GOTWaypoint.VaesLeqse, GOTWaypoint.VaesAthjikhari);
		GOTBezier.registerRoad(id++, GOTWaypoint.Chroyane, GOTWaypoint.NySar, GOTWaypoint.Norvos);
		GOTBezier.registerRoad(id++, GOTWaypoint.Chroyane, GOTWaypoint.Selhorys, GOTWaypoint.Valysar, GOTWaypoint.VolonTherys);
		GOTBezier.registerRoad(id++, GOTWaypoint.Crakehall, GOTWaypoint.OldOak);
		GOTBezier.registerRoad(id++, GOTWaypoint.Crakehall, near(GOTWaypoint.Crakehall, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.CrossroadsInn, GOTWaypoint.Darry);
		GOTBezier.registerRoad(id++, GOTWaypoint.CrossroadsInn, GOTWaypoint.Harroway);
		GOTBezier.registerRoad(id++, GOTWaypoint.DarkDell, near(GOTWaypoint.DarkDell, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Darry, GOTWaypoint.WhiteWalls, GOTWaypoint.Hayford);
		GOTBezier.registerRoad(id++, GOTWaypoint.Darry, near(GOTWaypoint.Darry, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Duskendale, near(GOTWaypoint.Duskendale, -2, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Eijiang, near(GOTWaypoint.Eijiang, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.FairMarket, near(GOTWaypoint.FairMarket, 4, -4));
		GOTBezier.registerRoad(id++, GOTWaypoint.Felwood, near(GOTWaypoint.Felwood, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2);
		GOTBezier.registerRoad(id++, GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2);
		GOTBezier.registerRoad(id++, GOTWaypoint.FiveForts1, new int[] { 3687, 1921 }, new int[] { 3600, 1973 }, GOTWaypoint.Vaibei);
		GOTBezier.registerRoad(id++, GOTWaypoint.FiveForts2, GOTWaypoint.FiveForts3);
		GOTBezier.registerRoad(id++, GOTWaypoint.FiveForts3, GOTWaypoint.FiveForts4);
		GOTBezier.registerRoad(id++, GOTWaypoint.FiveForts4, GOTWaypoint.FiveForts5);
		GOTBezier.registerRoad(id++, GOTWaypoint.FiveForts5, new int[] { 3797, 2008 }, new int[] { 3732, 2063 }, GOTWaypoint.Yibin);
		GOTBezier.registerRoad(id++, GOTWaypoint.GarnetGrove, near(GOTWaypoint.GarnetGrove, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.GarnetGrove, new int[] { 379, 2023 }, new int[] { 397, 2045 }, GOTWaypoint.SunHouse);
		GOTBezier.registerRoad(id++, GOTWaypoint.GateOfTheMoon, near(GOTWaypoint.GateOfTheMoon, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.GoldenTooth, near(GOTWaypoint.GoldenTooth, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Goldgrass, near(GOTWaypoint.Goldgrass, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Goldgrass, near(GOTWaypoint.MoatKailin, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Harroway, GOTWaypoint.CastleLychester, GOTWaypoint.StoneHedge, GOTWaypoint.Riverrun, GOTWaypoint.GoldenTooth, GOTWaypoint.Sarsfield, GOTWaypoint.Oxcross, GOTWaypoint.CasterlyRock);
		GOTBezier.registerRoad(id++, GOTWaypoint.Hayford, GOTWaypoint.KingsLanding);
		GOTBezier.registerRoad(id++, GOTWaypoint.Hayford, GOTWaypoint.Rosby, GOTWaypoint.Duskendale, GOTWaypoint.HollardCastle, GOTWaypoint.RooksRest);
		GOTBezier.registerRoad(id++, GOTWaypoint.Hayford, near(GOTWaypoint.Hayford, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Hesh, near(GOTWaypoint.Hesh, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Highgarden, GOTWaypoint.Nightsong, GOTWaypoint.TowerOfJoy, GOTWaypoint.Kingsgrave, GOTWaypoint.SkyReach);
		GOTBezier.registerRoad(id++, GOTWaypoint.Highgarden, GOTWaypoint.Whitegrove, GOTWaypoint.Oldtown);
		GOTBezier.registerRoad(id++, GOTWaypoint.Highgarden, near(GOTWaypoint.Highgarden, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.HornHill, near(GOTWaypoint.HornHill, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Jinqi, near(GOTWaypoint.Jinqi, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Jinqi, new int[] { 3620, 2347 }, new int[] { 3621, 2315 }, new int[] { 3634, 2281 });
		GOTBezier.registerRoad(id++, GOTWaypoint.Jinqi, new int[] { 3753, 2449 }, new int[] { 3810, 2630 }, near(GOTWaypoint.Asshai, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.KingsLanding, near(GOTWaypoint.KingsLanding, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.KingsLanding, near(GOTWaypoint.KingsLanding, 5, 28));
		GOTBezier.registerRoad(id++, GOTWaypoint.Kingsgrave, near(GOTWaypoint.Kingsgrave, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Kosrak, near(GOTWaypoint.Kosrak, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.KrazaajHas, GOTWaypoint.VaesMejhah);
		GOTBezier.registerRoad(id++, GOTWaypoint.KrazaajHas, ingameXZ(207427, 149867), GOTWaypoint.Hesh, GOTWaypoint.Lhazosh, new int[] { 2447, 2138 }, GOTWaypoint.VaesOrvik);
		GOTBezier.registerRoad(id++, GOTWaypoint.Lannisport, near(GOTWaypoint.Lannisport, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Lannisport, new int[] { 371, 1622 }, GOTWaypoint.Crakehall);
		GOTBezier.registerRoad(id++, GOTWaypoint.Lannisport, new int[] { 477, 1572 }, new int[] { 526, 1598 }, new int[] { 570, 1625 }, new int[] { 656, 1606 }, new int[] { 710, 1633 }, GOTWaypoint.KingsLanding);
		GOTBezier.registerRoad(id++, GOTWaypoint.Lhazosh, ingameXZ(216131, 159723), GOTWaypoint.Kosrak);
		GOTBezier.registerRoad(id++, GOTWaypoint.Lhazosh, near(GOTWaypoint.Lhazosh, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.LittleValyria, GOTWaypoint.ValyrianRoad);
		GOTBezier.registerRoad(id++, GOTWaypoint.Lizhao, near(GOTWaypoint.Lizhao, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Maidenpool, GOTWaypoint.Duskendale);
		GOTBezier.registerRoad(id++, GOTWaypoint.Manjin, near(GOTWaypoint.Manjin, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Mantarys, new int[] { 1831, 2092 }, GOTWaypoint.Oros);
		GOTBezier.registerRoad(id++, GOTWaypoint.Meereen, GOTWaypoint.KrazaajHas);
		GOTBezier.registerRoad(id++, GOTWaypoint.Meereen, ingameXZ(183429, 153350), ingameXZ(175749, 153350), GOTWaypoint.Bhorash);
		GOTBezier.registerRoad(id++, GOTWaypoint.Meereen, near(GOTWaypoint.Meereen, -9, 55), new int[] { 2219, 2124 }, GOTWaypoint.Astapor);
		GOTBezier.registerRoad(id++, GOTWaypoint.Moletown, new int[] { 747, 742 }, new int[] { 711, 783 }, new int[] { 672, 826 }, near(GOTWaypoint.Winterfell, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Myr, ingameXZ(67636, 146432), GOTWaypoint.Chroyane);
		GOTBezier.registerRoad(id++, GOTWaypoint.Nightsong, near(GOTWaypoint.Nightsong, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.OldOak, near(GOTWaypoint.OldOak, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.OldOak, new int[] { 438, 1773 }, GOTWaypoint.Highgarden);
		GOTBezier.registerRoad(id++, GOTWaypoint.Oldtown, near(GOTWaypoint.Oldtown, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Oldtown, new int[] { 393, 1966 }, new int[] { 377, 1988 }, GOTWaypoint.ThreeTowers);
		GOTBezier.registerRoad(id++, GOTWaypoint.Pentos, GOTWaypoint.GhoyanDrohe, GOTWaypoint.NySar, GOTWaypoint.ArNoy);
		GOTBezier.registerRoad(id++, GOTWaypoint.PortYhos, GOTWaypoint.VaesOrvik);
		GOTBezier.registerRoad(id++, GOTWaypoint.RamsGate, near(GOTWaypoint.RamsGate, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Rhyos, GOTWaypoint.Oros);
		GOTBezier.registerRoad(id++, GOTWaypoint.RillwaterCrossing, near(GOTWaypoint.RillwaterCrossing, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.RisvellsCastle, near(GOTWaypoint.RisvellsCastle, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Riverrun, near(GOTWaypoint.Riverrun, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.RooksRest, near(GOTWaypoint.RooksRest, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Rosby, near(GOTWaypoint.Rosby, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.SarMell, new int[] { 1559, 2045 }, GOTWaypoint.Volantis);
		GOTBezier.registerRoad(id++, GOTWaypoint.Sarsfield, near(GOTWaypoint.Sarsfield, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.ServinsCastle, near(GOTWaypoint.Dreadfort, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.ServinsCastle, near(GOTWaypoint.ServinsCastle, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.SiQo, near(GOTWaypoint.SiQo, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.SkyReach, near(GOTWaypoint.SkyReach, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Smithyton, near(GOTWaypoint.Smithyton, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Starfall, near(GOTWaypoint.Starfall, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.StoneHedge, near(GOTWaypoint.StoneHedge, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.StormsEnd, near(GOTWaypoint.StormsEnd, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.SunHouse, near(GOTWaypoint.SunHouse, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.SunHouse, new int[] { 450, 2033 }, new int[] { 471, 2025 }, GOTWaypoint.Starfall);
		GOTBezier.registerRoad(id++, GOTWaypoint.TheEyrie, GOTWaypoint.GateOfTheMoon, GOTWaypoint.BloodyGate, GOTWaypoint.CrossroadsInn);
		GOTBezier.registerRoad(id++, GOTWaypoint.ThreeTowers, near(GOTWaypoint.ThreeTowers, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.ThreeTowers, new int[] { 353, 2005 }, GOTWaypoint.GarnetGrove);
		GOTBezier.registerRoad(id++, GOTWaypoint.Tiqui, near(GOTWaypoint.Tiqui, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Tiqui, new int[] { 3240, 2114 }, new int[] { 3193, 2134 }, GOTWaypoint.Eijiang);
		GOTBezier.registerRoad(id++, GOTWaypoint.Tiqui, new int[] { 3374, 2123 }, new int[] { 3425, 2147 }, GOTWaypoint.Manjin);
		GOTBezier.registerRoad(id++, GOTWaypoint.TorhensSquare, near(GOTWaypoint.BlackPool, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.TraderTown, near(GOTWaypoint.Tiqui, 1, 0), GOTWaypoint.SiQo, GOTWaypoint.Yin);
		GOTBezier.registerRoad(id++, GOTWaypoint.TraderTown, near(GOTWaypoint.TraderTown, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.TraderTown, new int[] { 3411, 1920 }, new int[] { 3473, 1951 }, near(GOTWaypoint.Vaibei, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Tumbleton, GOTWaypoint.Smithyton);
		GOTBezier.registerRoad(id++, GOTWaypoint.Tumbleton, near(GOTWaypoint.Tumbleton, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.TwinsLeft, GOTWaypoint.Seagard);
		GOTBezier.registerRoad(id++, GOTWaypoint.TwinsRight, GOTWaypoint.TwinsLeft);
		GOTBezier.registerRoad(id++, GOTWaypoint.VaesKhadokh, GOTWaypoint.Rathylar, GOTWaypoint.Hornoth, GOTWaypoint.Kyth, GOTWaypoint.Saath);
		GOTBezier.registerRoad(id++, GOTWaypoint.VaesMejhah, GOTWaypoint.VaesJini, GOTWaypoint.Samyriana, new int[] { 3219, 1895 }, GOTWaypoint.TraderTown);
		GOTBezier.registerRoad(id++, GOTWaypoint.Vaibei, near(GOTWaypoint.Vaibei, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Vaibei, near(GOTWaypoint.Vaibei, 0, -1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Volantis, GOTWaypoint.LittleValyria, GOTWaypoint.Anogaria, GOTWaypoint.Mantarys, GOTWaypoint.Bhorash);
		GOTBezier.registerRoad(id++, GOTWaypoint.VolonTherys, GOTWaypoint.SarMell);
		GOTBezier.registerRoad(id++, GOTWaypoint.Whitegrove, near(GOTWaypoint.Whitegrove, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Whitegrove, near(GOTWaypoint.Whitegrove, -16, 33));
		GOTBezier.registerRoad(id++, GOTWaypoint.Wyl, near(GOTWaypoint.Wyl, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Yibin, GOTWaypoint.Lizhao, new int[] { 3679, 2188 }, new int[] { 3655, 2241 }, new int[] { 3634, 2281 });
		GOTBezier.registerRoad(id++, GOTWaypoint.Yibin, near(GOTWaypoint.Yibin, -1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Yibin, new int[] { 3622, 2031 }, new int[] { 3560, 2023 }, GOTWaypoint.Vaibei);
		GOTBezier.registerRoad(id++, GOTWaypoint.Yin, near(GOTWaypoint.Yin, 0, 1));
		GOTBezier.registerRoad(id++, GOTWaypoint.Yronwood, near(GOTWaypoint.Yronwood, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Yronwood, new int[] { 680, 1953 }, GOTWaypoint.Wyl, GOTWaypoint.Blackhaven, GOTWaypoint.Summerhall, GOTWaypoint.Bronzegate);
		GOTBezier.registerRoad(id++, GOTWaypoint.Yunkai, near(GOTWaypoint.Meereen, -9, 55));
		GOTBezier.registerRoad(id++, GOTWaypoint.Yunnan, near(GOTWaypoint.Yunnan, 1, 0));
		GOTBezier.registerRoad(id++, GOTWaypoint.Zamettar, new int[] { 2150, 2793 }, GOTWaypoint.Yeen);
		GOTBezier.registerRoad(id++, near(GOTWaypoint.BlackPool, -1, 0), near(GOTWaypoint.BlackPool, 1, 0));
		GOTBezier.registerRoad(id++, near(GOTWaypoint.BlackPool, 1, 0), near(GOTWaypoint.RamsGate, -1, 0));
		GOTBezier.registerRoad(id++, near(GOTWaypoint.Dreadfort, -1, 0), near(GOTWaypoint.Dreadfort, 1, 0));
		GOTBezier.registerRoad(id++, near(GOTWaypoint.Dreadfort, 1, 0), new int[] { 879, 840 }, GOTWaypoint.Karhold);
		GOTBezier.registerRoad(id++, near(GOTWaypoint.KingsLanding, 5, 28), GOTWaypoint.Smithyton, GOTWaypoint.Bitterbridge, GOTWaypoint.Appleton, GOTWaypoint.DarkDell, GOTWaypoint.Highgarden);
		GOTBezier.registerRoad(id++, near(GOTWaypoint.Maidenpool, -1, 0), near(GOTWaypoint.Maidenpool, 1, 0));
		GOTBezier.registerRoad(id++, near(GOTWaypoint.MoatKailin, -1, 0), near(GOTWaypoint.MoatKailin, 1, 0));
		GOTBezier.registerRoad(id++, near(GOTWaypoint.MoatKailin, 0, -1), near(GOTWaypoint.MoatKailin, 0, 1));
		GOTBezier.registerRoad(id++, near(GOTWaypoint.MoatKailin, 0, 1), new int[] { 656, 1217 }, near(GOTWaypoint.TwinsRight, 41, -19), GOTWaypoint.TwinsRight);
		GOTBezier.registerRoad(id++, near(GOTWaypoint.MoatKailin, 1, 0), new int[] { 685, 1110 }, near(GOTWaypoint.WhiteHarbour, -9, 0));
		GOTBezier.registerRoad(id++, near(GOTWaypoint.RillwaterCrossing, 0, 1), GOTWaypoint.RisvellsCastle, GOTWaypoint.Barrowtown);
		GOTBezier.registerRoad(id++, near(GOTWaypoint.Seagard, 0, -1), near(GOTWaypoint.Seagard, 0, 1));
		GOTBezier.registerRoad(id++, near(GOTWaypoint.StormsEnd, 0, -1), GOTWaypoint.Bronzegate, GOTWaypoint.Felwood, near(GOTWaypoint.KingsLanding, 5, 28));
		GOTBezier.registerRoad(id++, near(GOTWaypoint.TwinsRight, 41, -19), new int[] { 655, 1331 }, GOTWaypoint.CrossroadsInn);
		GOTBezier.registerRoad(id++, near(GOTWaypoint.WhiteHarbour, -9, 0), GOTWaypoint.WhiteHarbour);
		GOTBezier.registerRoad(id++, near(GOTWaypoint.Winterfell, 0, -1), near(GOTWaypoint.Winterfell, 0, 1));
		GOTBezier.registerRoad(id++, near(GOTWaypoint.Winterfell, 0, 1), GOTWaypoint.ServinsCastle, new int[] { 628, 973 }, near(GOTWaypoint.MoatKailin, 0, -1));
		GOTBezier.registerRoad(id++, new int[] { 1251, 1464 }, new int[] { 1246, 1424 }, new int[] { 1227, 1388 }, new int[] { 1208, 1335 });
		GOTBezier.registerRoad(id++, new int[] { 1251, 1464 }, new int[] { 1267, 1498 }, new int[] { 1314, 1535 }, GOTWaypoint.GhoyanDrohe);
		GOTBezier.registerRoad(id++, new int[] { 2723, 2245 }, GOTWaypoint.Qarkash);
		GOTBezier.registerRoad(id++, new int[] { 2723, 2245 }, new int[] { 2687, 2231 }, new int[] { 2626, 2258 }, GOTWaypoint.PortYhos);
		GOTBezier.registerRoad(id++, new int[] { 2723, 2245 }, new int[] { 2763, 2273 }, new int[] { 2834, 2268 }, GOTWaypoint.Qarth);
		GOTBezier.registerRoad(id++, new int[] { 2995, 2293 }, new int[] { 2972, 2274 }, new int[] { 2931, 2262 }, GOTWaypoint.Qarth);
		GOTBezier.registerRoad(id++, new int[] { 2995, 2293 }, new int[] { 3034, 2243 }, new int[] { 3081, 2240 }, GOTWaypoint.Asabhad);
		GOTBezier.registerRoad(id++, new int[] { 3081, 2240 }, new int[] { 3088, 2202 }, new int[] { 3093, 2162 }, GOTWaypoint.Eijiang);
		GOTBezier.registerRoad(id++, new int[] { 3634, 2281 }, new int[] { 3688, 2321 }, new int[] { 3756, 2321 }, GOTWaypoint.Yunnan);
		GOTBezier.registerWall(id++, GOTWaypoint.WestWatch, near(GOTWaypoint.CastleBlack, 0, -1), near(GOTWaypoint.EastWatch, 2, 0));
		GOTBezier.registerWall(id++, new int[] { 2659, 1218 }, new int[] { 2676, 1220 }, new int[] { 2693, 1218 });
		GOTBezier.registerWall(id++, new int[] { 3257, 1864 }, new int[] { 3425, 1856 }, new int[] { 3629, 1877 }, new int[] { 3763, 1911 });
		GOTBezier.registerWall(id++, new int[] { 3028, 1843 }, new int[] { 3097, 1857 }, new int[] { 3179, 1863 }, new int[] { 3257, 1864 });
	}

	public static void registerWall(int id, Object... waypoints) {
		isWall = true;
		ArrayList<BezierPoint> points = new ArrayList<>();
		for (Object obj : waypoints) {
			if (obj instanceof GOTWaypoint) {
				GOTWaypoint wp = (GOTWaypoint) obj;
				points.add(new BezierPoint(wp.getXCoord(), wp.getZCoord(), true));
				continue;
			}
			if (obj instanceof int[]) {
				int[] coords = (int[]) obj;
				if (coords.length == 2) {
					points.add(new BezierPoint(GOTWaypoint.mapToWorldX(coords[0]), GOTWaypoint.mapToWorldZ(coords[1]), false));
					continue;
				}
				throw new IllegalArgumentException("Coords length must be 2!");
			}
			throw new IllegalArgumentException("Wrong wall parameter!");
		}
		BezierPoint[] array = points.toArray(new BezierPoint[0]);
		GOTBezier[] beziers = BezierCurves.getSplines("null", array);
	}

	public static void registerRoad(int id, Object... waypoints) {
		isWall = false;
		ArrayList<BezierPoint> points = new ArrayList<>();
		for (Object obj : waypoints) {
			if (obj instanceof GOTWaypoint) {
				GOTWaypoint wp = (GOTWaypoint) obj;
				points.add(new BezierPoint(wp.getXCoord(), wp.getZCoord(), true));
				continue;
			}
			if (obj instanceof int[]) {
				int[] coords = (int[]) obj;
				if (coords.length == 2) {
					points.add(new BezierPoint(GOTWaypoint.mapToWorldX(coords[0]), GOTWaypoint.mapToWorldZ(coords[1]), false));
					continue;
				}
				throw new IllegalArgumentException("Coords length must be 2!");
			}
			throw new IllegalArgumentException("Wrong road parameter!");
		}
		BezierPoint[] array = points.toArray(new BezierPoint[0]);
		GOTBezier[] beziers = BezierCurves.getSplines("null", array);
		allBeziers.addAll(Arrays.asList(beziers));
	}

	public static class BezierCurves {
		public static int bezierLengthFactor = 1;

		public static BezierPoint bezier(BezierPoint a, BezierPoint b, BezierPoint c, BezierPoint d, double t) {
			BezierPoint ab = BezierCurves.lerp(a, b, t);
			BezierPoint bc = BezierCurves.lerp(b, c, t);
			BezierPoint cd = BezierCurves.lerp(c, d, t);
			BezierPoint abbc = BezierCurves.lerp(ab, bc, t);
			BezierPoint bccd = BezierCurves.lerp(bc, cd, t);
			return BezierCurves.lerp(abbc, bccd, t);
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
			return new double[][] { p1, p2 };
		}

		public static GOTBezier[] getSplines(String name, BezierPoint[] waypoints) {
			if (waypoints.length == 2) {
				BezierPoint p1 = waypoints[0];
				BezierPoint p2 = waypoints[1];
				GOTBezier bezier = new GOTBezier(name, p1, p2);
				double dx = p2.x - p1.x;
				double dz = p2.z - p1.z;
				int bezierLength = (int) Math.round(Math.sqrt(dx * dx + dz * dz));
				int points = bezierLength * bezierLengthFactor;
				bezier.bezierPoints = new BezierPoint[points];
				for (int l = 0; l < points; ++l) {
					BezierPoint point;
					double t = (double) l / (double) points;
					bezier.bezierPoints[l] = point = new BezierPoint(p1.x + dx * t, p1.z + dz * t, false);
					bezierPointDatabase.add(point);
				}
				return new GOTBezier[] { bezier };
			}
			int length = waypoints.length;
			double[] x = new double[length];
			double[] z = new double[length];
			for (int i = 0; i < length; ++i) {
				x[i] = waypoints[i].x;
				z[i] = waypoints[i].z;
			}
			double[][] controlX = BezierCurves.getControlPoints(x);
			double[][] controlZ = BezierCurves.getControlPoints(z);
			int controlPoints = controlX[0].length;
			BezierPoint[] controlPoints1 = new BezierPoint[controlPoints];
			BezierPoint[] controlPoints2 = new BezierPoint[controlPoints];
			for (int i = 0; i < controlPoints; ++i) {
				BezierPoint p1 = new BezierPoint(controlX[0][i], controlZ[0][i], false);
				BezierPoint p2 = new BezierPoint(controlX[1][i], controlZ[1][i], false);
				controlPoints1[i] = p1;
				controlPoints2[i] = p2;
			}
			GOTBezier[] beziers = new GOTBezier[length - 1];
			for (int i = 0; i < beziers.length; ++i) {
				GOTBezier bezier;
				BezierPoint p1 = waypoints[i];
				BezierPoint p2 = waypoints[i + 1];
				BezierPoint cp1 = controlPoints1[i];
				BezierPoint cp2 = controlPoints2[i];
				beziers[i] = bezier = new GOTBezier(name, p1, p2);
				double dx = p2.x - p1.x;
				double dz = p2.z - p1.z;
				int bezierLength = (int) Math.round(Math.sqrt(dx * dx + dz * dz));
				int points = bezierLength * bezierLengthFactor;
				bezier.bezierPoints = new BezierPoint[points];
				for (int l = 0; l < points; ++l) {
					BezierPoint point;
					double t = (double) l / (double) points;
					bezier.bezierPoints[l] = point = BezierCurves.bezier(p1, cp1, cp2, p2, t);
					bezierPointDatabase.add(point);
				}
			}
			return beziers;
		}

		public static BezierPoint lerp(BezierPoint a, BezierPoint b, double t) {
			double x = a.x + (b.x - a.x) * t;
			double z = a.z + (b.z - a.z) * t;
			return new BezierPoint(x, z, false);
		}
	}

	public static class BezierPoint {
		public double x;
		public double z;
		public boolean isWaypoint;

		public BezierPoint(double i, double j, boolean flag) {
			x = i;
			z = j;
			isWaypoint = flag;
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

		public List<BezierPoint> getPointsForCoords(int x, int z) {
			int x1 = x / 1000;
			int z1 = z / 1000;
			return getBezierList(x1, z1, false);
		}

		public List<BezierPoint> getBezierList(int xKey, int zKey, boolean addToMap) {
			Pair key = Pair.of((Object) xKey, (Object) zKey);
			List<BezierPoint> list = pointMap.get(key);
			if (list == null) {
				list = new ArrayList<>();
				if (addToMap) {
					pointMap.put(key, list);
				}
			}
			return list;
		}
	}

}
