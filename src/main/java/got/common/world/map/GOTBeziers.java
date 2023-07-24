package got.common.world.map;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class GOTBeziers {
	public static List<GOTBeziers> allBeziers = new ArrayList<>();

	public static BezierPointDatabase linkerPointDatabase = new BezierPointDatabase();
	public static BezierPointDatabase roadPointDatabase = new BezierPointDatabase();
	public static BezierPointDatabase wallPointDatabase = new BezierPointDatabase();

	public static int id;
	public BezierPoint[] bezierPoints;
	public List<BezierPoint> endpoints = new ArrayList<>();

	public GOTBeziers(BezierPoint... ends) {
		Collections.addAll(endpoints, ends);
	}

	public static float isBezierNear(int x, int z, int width, Type type) {
		double widthSq = width * width;
		float leastSqRatio = -1.0f;
		List<BezierPoint> points = null;
		switch (type) {
			case ROAD:
				points = roadPointDatabase.getPointsForCoords(x, z);
				break;
			case WALL:
				points = wallPointDatabase.getPointsForCoords(x, z);
				break;
			case LINKER:
				points = linkerPointDatabase.getPointsForCoords(x, z);
				break;
			default:
				break;
		}
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

	public static boolean isBezierAt(int x, int z, Type type) {
		return isBezierNear(x, z, 4, type) >= 0.0f;
	}

	public static void onInit() {
		allBeziers.clear();

		roadPointDatabase = new BezierPointDatabase();
		wallPointDatabase = new BezierPointDatabase();
		linkerPointDatabase = new BezierPointDatabase();

		registerWall(id++, GOTWaypoint.WestWatch, GOTWaypoint.ShadowTower, GOTWaypoint.SentinelStand, GOTWaypoint.Greyguard, GOTWaypoint.Stonedoor, GOTWaypoint.HoarfrostHill, GOTWaypoint.Icemark, GOTWaypoint.Nightfort, GOTWaypoint.DeepLake, GOTWaypoint.Queensgate, GOTWaypoint.CastleBlack, GOTWaypoint.Oakenshield, GOTWaypoint.Woodswatch, GOTWaypoint.SableHall, GOTWaypoint.Rimegate, GOTWaypoint.LongBarrow, GOTWaypoint.Torches, GOTWaypoint.Greenguard, GOTWaypoint.EastWatch);
		registerWall(id++, GOTWaypoint.Anbei, GOTWaypoint.Jianmen, GOTWaypoint.Anguo, GOTWaypoint.Anjiang, GOTWaypoint.Dingguo, GOTWaypoint.Pinnu, GOTWaypoint.Pingjiang, GOTWaypoint.Wude, GOTWaypoint.Wusheng, GOTWaypoint.Zhenguo, GOTWaypoint.Lungmen, GOTWaypoint.Pingbei);

		registerWall(id++, new double[]{2847, 1273}, new double[]{2820, 1292}, new double[]{2771, 1308}, new double[]{2732, 1308});
		registerWall(id++, new double[]{2732, 1308}, new double[]{2683, 1294}, new double[]{2628, 1294}, new double[]{2588, 1275});
		registerWall(id++, new double[]{2708, 1230}, new double[]{2683, 1244}, new double[]{2656, 1253}, new double[]{2638, 1252});

		registerRoad(id++, new double[]{559, 544}, GOTWaypoint.SkirlingPass, new double[]{596, 544});

		/* NORTH */
		double[] northRiverlandsCrossroads = {655, 1257};

		registerRoad(id++, GOTWaypoint.CastleBlack, new double[]{745, 732}, new double[]{694, 804}, GOTWaypoint.Winterfell);
		registerRoad(id++, GOTWaypoint.Winterfell, new double[]{642, 891}, GOTWaypoint.ServinsCastle);
		registerRoad(id++, GOTWaypoint.ServinsCastle, new double[]{696, 899}, new double[]{765, 872}, GOTWaypoint.Dreadfort);
		registerRoad(id++, GOTWaypoint.Dreadfort, new double[]{855, 865}, new double[]{893, 828}, GOTWaypoint.Karhold);
		registerRoad(id++, GOTWaypoint.ServinsCastle, new double[]{632, 976}, new double[]{644, 1043}, GOTWaypoint.MoatKailin.info(0, -0.5));
		registerRoad(id++, GOTWaypoint.TorhensSquare, new double[]{553, 996}, new double[]{539, 1037}, GOTWaypoint.Goldgrass);
		registerRoad(id++, GOTWaypoint.RillwaterCrossing, new double[]{422, 989}, new double[]{419, 1013}, GOTWaypoint.RyswellsCastle);
		registerRoad(id++, GOTWaypoint.RyswellsCastle, new double[]{447, 1064}, new double[]{496, 1066}, GOTWaypoint.Goldgrass);
		registerRoad(id++, GOTWaypoint.Goldgrass, new double[]{606, 1081}, new double[]{626, 1103}, GOTWaypoint.MoatKailin.info(-0.5, 0));
		registerRoad(id++, GOTWaypoint.MoatKailin.info(0.5, 0), new double[]{672, 1101}, new double[]{707, 1072}, GOTWaypoint.WhiteHarbour);
		registerRoad(id++, GOTWaypoint.MoatKailin.info(0, 0.5), new double[]{649, 1136}, new double[]{656, 1179}, northRiverlandsCrossroads);
		registerRoad(id++, GOTWaypoint.MoatKailin.info(-0.5, 0), GOTWaypoint.MoatKailin.info(0.5, 0));
		registerRoad(id++, GOTWaypoint.MoatKailin.info(0, -0.5), GOTWaypoint.MoatKailin.info(0, 0.5));

		/* ARRYN */
		registerRoad(id++, GOTWaypoint.CrossroadsInn, new double[]{754, 1430}, new double[]{771, 1417}, GOTWaypoint.BloodyGate);
		registerRoad(id++, GOTWaypoint.BloodyGate, new double[]{804, 1397}, new double[]{817, 1388}, GOTWaypoint.GateOfTheMoon);
		registerRoad(id++, GOTWaypoint.GateOfTheMoon, new double[]{827, 1369}, new double[]{828, 1361}, GOTWaypoint.TheEyrie);

		/* RIVERLANDS */
		double[] northRiverlandsBridge = {713, 1400};
		double[] antiCrossroadsInn = {732, 1447};
		double[] hillsExit = {519, 1476};

		registerRoad(id++, northRiverlandsCrossroads, new double[]{638, 1268}, new double[]{623, 1282}, GOTWaypoint.TwinsRight);
		registerRoad(id++, GOTWaypoint.TwinsRight, GOTWaypoint.TwinsLeft);
		registerRoad(id++, GOTWaypoint.TwinsLeft, new double[]{590, 1296}, new double[]{583, 1311}, GOTWaypoint.Seagard);
		registerRoad(id++, northRiverlandsCrossroads, new double[]{664, 1310}, new double[]{683, 1360}, northRiverlandsBridge);
		registerRoad(id++, northRiverlandsBridge, new double[]{721, 1412}, new double[]{723, 1428}, GOTWaypoint.CrossroadsInn);
		registerRoad(id++, GOTWaypoint.CrossroadsInn, new double[]{758, 1446}, new double[]{776, 1461}, GOTWaypoint.Saltpans);
		registerRoad(id++, GOTWaypoint.CrossroadsInn, antiCrossroadsInn);
		registerRoad(id++, antiCrossroadsInn, new double[]{730, 1463}, new double[]{735, 1479}, GOTWaypoint.WhiteWalls);
		registerRoad(id++, GOTWaypoint.WhiteWalls, new double[]{726, 1490}, GOTWaypoint.Harrenhal);
		registerRoad(id++, GOTWaypoint.WhiteWalls, new double[]{751, 1503}, new double[]{761, 1513}, GOTWaypoint.HoggHall);
		registerRoad(id++, GOTWaypoint.HoggHall, GOTWaypoint.Antlers);
		registerRoad(id++, antiCrossroadsInn, GOTWaypoint.Harroway);
		registerRoad(id++, GOTWaypoint.Harroway, new double[]{703, 1443}, new double[]{685, 1446}, GOTWaypoint.CastleLychester);
		registerRoad(id++, GOTWaypoint.CastleLychester, new double[]{657, 1450}, new double[]{644, 1458}, GOTWaypoint.AcornHall);
		registerRoad(id++, GOTWaypoint.AcornHall, new double[]{628, 1478}, GOTWaypoint.WayfarerRest);
		registerRoad(id++, GOTWaypoint.CastleLychester, new double[]{655, 1439}, new double[]{642, 1430}, GOTWaypoint.StoneHedge);
		registerRoad(id++, GOTWaypoint.StoneHedge, new double[]{615, 1431}, new double[]{601, 1434}, GOTWaypoint.Riverrun);
		registerRoad(id++, GOTWaypoint.Riverrun, new double[]{565, 1444}, new double[]{541, 1462}, hillsExit);
		registerRoad(id++, GOTWaypoint.StoneySept, new double[]{691, 1565}, GOTWaypoint.Briarwhite);

		/* CROWNLANDS */
		registerRoad(id++, GOTWaypoint.Briarwhite, new double[]{740, 1588}, new double[]{748, 1606}, GOTWaypoint.Hayford);
		registerRoad(id++, GOTWaypoint.Hayford, new double[]{767, 1624}, GOTWaypoint.KingsLanding);
		registerRoad(id++, GOTWaypoint.Antlers, new double[]{799, 1547}, new double[]{809, 1569}, GOTWaypoint.Stokeworth);
		registerRoad(id++, GOTWaypoint.Stokeworth, new double[]{818, 1584}, new double[]{826, 1581}, GOTWaypoint.Duskendale);
		registerRoad(id++, GOTWaypoint.Duskendale, new double[]{837, 1573}, new double[]{840, 1569}, GOTWaypoint.HollardCastle);
		registerRoad(id++, GOTWaypoint.HollardCastle, new double[]{848, 1552}, new double[]{855, 1542}, GOTWaypoint.RooksRest);
		registerRoad(id++, GOTWaypoint.Stokeworth, new double[]{807, 1597}, GOTWaypoint.Rosby);
		registerRoad(id++, GOTWaypoint.Rosby, new double[]{793, 1614}, new double[]{778, 1617}, GOTWaypoint.KingsLanding);

		/* WESTERLANDS */
		double[] westerlandsCrossroads = {363, 1716};
		double[] westerlandsHillsHalfway = {489, 1577};
		double[] reachCrossroads = {574, 1623};

		registerRoad(id++, hillsExit, new double[]{510, 1480}, new double[]{501, 1485}, GOTWaypoint.GoldenTooth);
		registerRoad(id++, GOTWaypoint.GoldenTooth, new double[]{476, 1504}, new double[]{460, 1513}, GOTWaypoint.Sarsfield);
		registerRoad(id++, GOTWaypoint.Sarsfield, GOTWaypoint.Oxcross);
		registerRoad(id++, GOTWaypoint.Oxcross, GOTWaypoint.CasterlyRock);
		registerRoad(id++, GOTWaypoint.CasterlyRock, GOTWaypoint.Lannisport);
		registerRoad(id++, GOTWaypoint.Lannisport, new double[]{371, 1607}, new double[]{366, 1639}, GOTWaypoint.Crakehall);
		registerRoad(id++, GOTWaypoint.Crakehall, new double[]{349, 1681}, new double[]{359, 1700}, westerlandsCrossroads);
		registerRoad(id++, GOTWaypoint.Lannisport, new double[]{410, 1572}, new double[]{453, 1569}, westerlandsHillsHalfway);
		registerRoad(id++, westerlandsHillsHalfway, new double[]{518, 1592}, new double[]{543, 1612}, reachCrossroads);

		/* REACH */
		double[] kingswoodCrossroads = {775, 1666};

		registerRoad(id++, westerlandsCrossroads, new double[]{365, 1730}, new double[]{371, 1743}, GOTWaypoint.OldOak);
		registerRoad(id++, reachCrossroads, GOTWaypoint.Hammerhal, GOTWaypoint.IvyHall);
		registerRoad(id++, reachCrossroads, new double[]{640, 1627}, new double[]{705, 1625}, GOTWaypoint.KingsLanding);
		registerRoad(id++, GOTWaypoint.Smithyton, new double[]{707, 1689}, new double[]{704, 1680}, GOTWaypoint.Tumbleton);
		registerRoad(id++, GOTWaypoint.Tumbleton, new double[]{687, 1673}, new double[]{673, 1673}, GOTWaypoint.Ring);
		registerRoad(id++, GOTWaypoint.Smithyton, GOTWaypoint.Bitterbridge);
		registerRoad(id++, GOTWaypoint.Bitterbridge, new double[]{625, 1714}, new double[]{596, 1727}, GOTWaypoint.Appleton);
		registerRoad(id++, GOTWaypoint.Appleton, new double[]{537, 1768}, GOTWaypoint.DarkDell);
		registerRoad(id++, GOTWaypoint.DarkDell, new double[]{512, 1794}, new double[]{501, 1803}, GOTWaypoint.Highgarden);
		registerRoad(id++, GOTWaypoint.DarkDell, new double[]{512, 1772}, new double[]{498, 1768}, GOTWaypoint.Holyhall);
		registerRoad(id++, GOTWaypoint.Holyhall, new double[]{491, 1745}, new double[]{485, 1730}, GOTWaypoint.Coldmoat);
		registerRoad(id++, GOTWaypoint.Coldmoat, new double[]{459, 1720}, new double[]{446, 1714}, GOTWaypoint.RedLake);
		registerRoad(id++, GOTWaypoint.RedLake, new double[]{410, 1711}, new double[]{387, 1713}, westerlandsCrossroads);
		registerRoad(id++, GOTWaypoint.Highgarden, new double[]{490, 1830}, new double[]{487, 1844}, GOTWaypoint.Whitegrove);
		registerRoad(id++, GOTWaypoint.Whitegrove, new double[]{459, 1887}, new double[]{419, 1915}, GOTWaypoint.Oldtown);
		registerRoad(id++, GOTWaypoint.Oldtown, new double[]{383, 1968}, new double[]{376, 1983}, GOTWaypoint.ThreeTowers);
		registerRoad(id++, GOTWaypoint.ThreeTowers, new double[]{354, 2006}, GOTWaypoint.GarnetGrove);
		registerRoad(id++, GOTWaypoint.GarnetGrove, new double[]{378, 2026}, new double[]{394, 2046}, GOTWaypoint.SunHouse);
		registerRoad(id++, GOTWaypoint.SunHouse, new double[]{449, 2034}, new double[]{475, 2022}, GOTWaypoint.Starfall);
		registerRoad(id++, GOTWaypoint.Whitegrove, new double[]{521, 1857}, new double[]{550, 1870}, GOTWaypoint.Nightsong);
		registerRoad(id++, GOTWaypoint.Smithyton, new double[]{732, 1692}, new double[]{752, 1677}, kingswoodCrossroads);

		/* DORNE */
		registerRoad(id++, GOTWaypoint.Starfall, new double[]{507, 1994}, new double[]{510, 1987}, GOTWaypoint.HighHermitage);
		registerRoad(id++, GOTWaypoint.HighHermitage, new double[]{516, 1966}, new double[]{520, 1955}, GOTWaypoint.Blackmont);
		registerRoad(id++, GOTWaypoint.Yronwood, new double[]{688, 1954}, new double[]{715, 1921}, GOTWaypoint.Wyl);
		registerRoad(id++, GOTWaypoint.Wyl, new double[]{734, 1894}, new double[]{726, 1884}, GOTWaypoint.Blackhaven);

		/* STORMLANDS */
		double[] stormlandsCrossroads = {741, 1840};

		registerRoad(id++, GOTWaypoint.Nightsong, new double[]{607, 1870}, new double[]{625, 1861}, GOTWaypoint.HarvestHall);
		registerRoad(id++, GOTWaypoint.HarvestHall, new double[]{654, 1849}, new double[]{665, 1846}, GOTWaypoint.Poddingfield);
		registerRoad(id++, GOTWaypoint.Poddingfield, new double[]{698, 1842}, new double[]{719, 1841}, stormlandsCrossroads);
		registerRoad(id++, GOTWaypoint.Nightsong, new double[]{593, 1886}, new double[]{601, 1898}, GOTWaypoint.TowerOfJoy);
		registerRoad(id++, GOTWaypoint.TowerOfJoy, GOTWaypoint.Kingsgrave, GOTWaypoint.SkyReach);
		registerRoad(id++, GOTWaypoint.Blackhaven, new double[]{729, 1861}, new double[]{735, 1851}, stormlandsCrossroads);
		registerRoad(id++, stormlandsCrossroads, new double[]{755, 1824}, new double[]{769, 1811}, GOTWaypoint.Summerhall);
		registerRoad(id++, GOTWaypoint.KingsLanding, kingswoodCrossroads, GOTWaypoint.Grandview, GOTWaypoint.Summerhall);
		registerRoad(id++, GOTWaypoint.Grandview, GOTWaypoint.Felwood, GOTWaypoint.HaystackHall, GOTWaypoint.Gallowsgrey, GOTWaypoint.Parchments);
		registerRoad(id++, GOTWaypoint.Felwood, new double[]{854, 1745}, GOTWaypoint.Bronzegate, GOTWaypoint.StormsEnd);

		/* SOTHORYOS */
		double[] sothoryosHalfway = {2176, 2793};

		registerRoad(id++, GOTWaypoint.Zamettar, new double[]{2164, 2749}, new double[]{2166, 2770}, sothoryosHalfway);
		registerRoad(id++, sothoryosHalfway, new double[]{2189, 2807}, new double[]{2195, 2819}, GOTWaypoint.Yeen);

		/* GHISCAR & LHAZAR */
		double[] giscarLhazarCrossroads = {2373, 1919};

		registerRoad(id++, GOTWaypoint.Lhazosh, new double[]{2474, 2056}, new double[]{2462, 2111}, new double[]{2463, 2153}, new double[]{2483, 2183}, new double[]{2522, 2203}, GOTWaypoint.VaesOrvik);
		registerRoad(id++, GOTWaypoint.Lhazosh, new double[]{2492, 1993}, new double[]{2528, 1976}, GOTWaypoint.Kosrak);
		registerRoad(id++, GOTWaypoint.Meereen, new double[]{2297, 1945}, new double[]{2334, 1930}, giscarLhazarCrossroads);
		registerRoad(id++, GOTWaypoint.Astapor, new double[]{2190, 2130}, new double[]{2196, 2111}, new double[]{2203, 2090}, new double[]{2206, 2059}, new double[]{2196, 2028}, GOTWaypoint.Yunkai);
		registerRoad(id++, GOTWaypoint.Yunkai, new double[]{2224, 1993}, new double[]{2236, 1970}, GOTWaypoint.Meereen.info(-0.5, 0));
		registerRoad(id++, GOTWaypoint.Meereen.info(-0.5, 0), new double[]{2230, 1933}, new double[]{2158, 1937}, GOTWaypoint.Bhorash);
		registerRoad(id++, GOTWaypoint.Meereen, GOTWaypoint.Meereen.info(-0.5, 0));

		/* DOTHRAKI */
		registerRoad(id++, GOTWaypoint.VaesJini, new double[]{2916, 1827}, new double[]{2956, 1844}, GOTWaypoint.Samyriana);
		registerRoad(id++, GOTWaypoint.VaesMejhah, new double[]{2662, 1819}, new double[]{2749, 1823}, GOTWaypoint.VaesJini);
		registerRoad(id++, GOTWaypoint.KrazaajHas, new double[]{2518, 1838}, new double[]{2544, 1832}, GOTWaypoint.VaesMejhah);
		registerRoad(id++, giscarLhazarCrossroads, new double[]{2413, 1902}, new double[]{2449, 1867}, GOTWaypoint.KrazaajHas);
		registerRoad(id++, giscarLhazarCrossroads, GOTWaypoint.Hesh, GOTWaypoint.Lhazosh);
		registerRoad(id++, GOTWaypoint.VaesAthjikhari, new double[]{2237, 1543}, new double[]{2255, 1559}, GOTWaypoint.VaesLeqse);
		registerRoad(id++, GOTWaypoint.VaesLeqse, new double[]{2263, 1592}, new double[]{2272, 1611}, GOTWaypoint.Sathar);
		registerRoad(id++, GOTWaypoint.Sathar, new double[]{2251, 1639}, new double[]{2199, 1649}, GOTWaypoint.VojjorSamvi);
		registerRoad(id++, GOTWaypoint.VojjorSamvi, new double[]{2111, 1653}, new double[]{2068, 1642}, GOTWaypoint.VaesKhewo);
		registerRoad(id++, GOTWaypoint.VaesKhewo, new double[]{1991, 1631}, new double[]{1953, 1630}, GOTWaypoint.VaesGorqoyi);
		registerRoad(id++, GOTWaypoint.Saath, new double[]{1879, 1373}, new double[]{1887, 1423}, GOTWaypoint.Kyth);
		registerRoad(id++, GOTWaypoint.Kyth, new double[]{1925, 1469}, new double[]{1936, 1487}, GOTWaypoint.Hornoth);
		registerRoad(id++, GOTWaypoint.Hornoth, new double[]{1933, 1521}, new double[]{1902, 1543}, GOTWaypoint.Rathylar);
		registerRoad(id++, GOTWaypoint.VaesGorqoyi, new double[]{1907, 1604}, new double[]{1903, 1584}, GOTWaypoint.Rathylar);
		registerRoad(id++, GOTWaypoint.VaesKhadokh, new double[]{1796, 1580}, new double[]{1846, 1576}, GOTWaypoint.Rathylar);

		/* QARTH */
		double[] boneMountP1 = {2978, 2279};
		double[] qarthYiTiBeforeMount = {2928, 2260};
		double[] qarthYiTiAfterMount = {3078, 2240};

		registerRoad(id++, boneMountP1, new double[]{3002, 2284}, new double[]{3036, 2247}, qarthYiTiAfterMount);
		registerRoad(id++, qarthYiTiBeforeMount, new double[]{2948, 2260}, new double[]{2965, 2267}, boneMountP1);
		registerRoad(id++, GOTWaypoint.Qarth, new double[]{2884, 2288}, new double[]{2901, 2268}, qarthYiTiBeforeMount);
		registerRoad(id++, GOTWaypoint.Qarkash, new double[]{2735, 2265}, new double[]{2766, 2288}, new double[]{2807, 2285}, new double[]{2840, 2275}, new double[]{2864, 2290}, GOTWaypoint.Qarth);
		registerRoad(id++, GOTWaypoint.PortYhos, new double[]{2622, 2260}, new double[]{2648, 2243}, new double[]{2665, 2234}, new double[]{2685, 2232}, new double[]{2710, 2239}, GOTWaypoint.Qarkash);
		registerRoad(id++, GOTWaypoint.VaesOrvik, new double[]{2571, 2255}, new double[]{2563, 2268}, GOTWaypoint.PortYhos);

		/* FREE CITIES */
		double[] unhabitedCrossroads = {1956, 2026};
		double[] norvosHalfway = {1423, 1552};
		double[] braavosHalfway1 = {1250, 1451};
		double[] braavosHalfway2 = {1210, 1339};

		registerRoad(id++, GOTWaypoint.Mantarys, new double[]{1864, 2058}, new double[]{1843, 2089}, new double[]{1832, 2136}, new double[]{1830, 2184}, new double[]{1835, 2219}, GOTWaypoint.Oros);
		registerRoad(id++, GOTWaypoint.Bhorash, new double[]{2024, 1981}, new double[]{1988, 2001}, unhabitedCrossroads);
		registerRoad(id++, unhabitedCrossroads, new double[]{1958, 2043}, new double[]{1951, 2058}, GOTWaypoint.Tolos);
		registerRoad(id++, unhabitedCrossroads, new double[]{1942, 2030}, new double[]{1913, 2027}, GOTWaypoint.Mantarys);
		registerRoad(id++, GOTWaypoint.Mantarys, new double[]{1839, 2004}, new double[]{1802, 2007}, GOTWaypoint.Anogaria);
		registerRoad(id++, GOTWaypoint.Anogaria, new double[]{1738, 2022}, new double[]{1710, 2030}, GOTWaypoint.LittleValyria);
		registerRoad(id++, GOTWaypoint.Qohor, new double[]{1666, 1606}, new double[]{1732, 1603}, GOTWaypoint.VaesKhadokh);
		registerRoad(id++, GOTWaypoint.Qohor, new double[]{1625, 1637}, new double[]{1562, 1661}, GOTWaypoint.ArNoy);
		registerRoad(id++, GOTWaypoint.Chroyane, new double[]{1437, 1844}, new double[]{1403, 1861}, new double[]{1354, 1868}, new double[]{1315, 1864}, new double[]{1286, 1853}, GOTWaypoint.Myr);
		registerRoad(id++, GOTWaypoint.LittleValyria, new double[]{1661, 2022}, GOTWaypoint.ValyrianRoad);
		registerRoad(id++, GOTWaypoint.LittleValyria, new double[]{1638, 2054}, new double[]{1589, 2063}, GOTWaypoint.Volantis);
		registerRoad(id++, GOTWaypoint.Volantis, new double[]{1562, 2049}, new double[]{1557, 2045}, GOTWaypoint.SarMell);
		registerRoad(id++, GOTWaypoint.SarMell, GOTWaypoint.VolonTherys);
		registerRoad(id++, GOTWaypoint.VolonTherys, new double[]{1519, 2032}, new double[]{1508, 2021}, GOTWaypoint.Valysar);
		registerRoad(id++, GOTWaypoint.Valysar, new double[]{1496, 1988}, new double[]{1492, 1963}, GOTWaypoint.Selhorys);
		registerRoad(id++, GOTWaypoint.Selhorys, new double[]{1501, 1916}, new double[]{1491, 1897}, new double[]{1486, 1879}, new double[]{1484, 1862}, new double[]{1484, 1846}, GOTWaypoint.Chroyane);
		registerRoad(id++, GOTWaypoint.Chroyane, new double[]{1499, 1802}, new double[]{1505, 1730}, GOTWaypoint.ArNoy);
		registerRoad(id++, GOTWaypoint.ArNoy, new double[]{1479, 1682}, new double[]{1453, 1660}, GOTWaypoint.NySar);
		registerRoad(id++, GOTWaypoint.NySar, new double[]{1430, 1617}, new double[]{1417, 1583}, norvosHalfway);
		registerRoad(id++, norvosHalfway, new double[]{1425, 1528}, new double[]{1417, 1510}, GOTWaypoint.Norvos);
		registerRoad(id++, GOTWaypoint.NySar, new double[]{1391, 1635}, new double[]{1356, 1612}, GOTWaypoint.GhoyanDrohe);
		registerRoad(id++, GOTWaypoint.GhoyanDrohe, new double[]{1262, 1604}, GOTWaypoint.Pentos);
		registerRoad(id++, GOTWaypoint.GhoyanDrohe, new double[]{1315, 1539}, new double[]{1263, 1493}, braavosHalfway1);
		registerRoad(id++, braavosHalfway1, new double[]{1246, 1421}, new double[]{1223, 1376}, braavosHalfway2);
		registerRoad(id++, braavosHalfway2, new double[]{1197, 1304}, new double[]{1175, 1270}, GOTWaypoint.Braavos);

		double[] asshaiCrossroadsHalfway = {3674, 2408};
		double[] asshaiCrossroads = {3729, 2423};

		registerRoad(id++, GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2);
		registerRoad(id++, GOTWaypoint.FiveForts2, GOTWaypoint.FiveForts3);
		registerRoad(id++, GOTWaypoint.FiveForts3, GOTWaypoint.FiveForts4);
		registerRoad(id++, GOTWaypoint.FiveForts4, GOTWaypoint.FiveForts5);
		registerRoad(id++, qarthYiTiAfterMount, new double[]{3092, 2210}, new double[]{3098, 2164}, GOTWaypoint.Eijiang);
		registerRoad(id++, qarthYiTiAfterMount, new double[]{3083, 2244}, new double[]{3086, 2255}, GOTWaypoint.Asabhad);
		registerRoad(id++, GOTWaypoint.Eijiang, new double[]{3130, 2131}, new double[]{3133, 2100}, GOTWaypoint.Bayasabhad);
		registerRoad(id++, GOTWaypoint.Changan, new double[]{3212, 2373}, new double[]{3223, 2391}, new double[]{3235, 2406}, new double[]{3256, 2425}, new double[]{3277, 2438}, GOTWaypoint.Yin);
		registerRoad(id++, GOTWaypoint.Changan, new double[]{3180, 2355}, new double[]{3158, 2330}, new double[]{3132, 2316}, new double[]{3111, 2299}, new double[]{3100, 2270}, GOTWaypoint.Asabhad);
		registerRoad(id++, GOTWaypoint.SiQo, new double[]{3259, 2291}, new double[]{3227, 2311}, GOTWaypoint.Changan);
		registerRoad(id++, asshaiCrossroads, new double[]{3757, 2401}, new double[]{3783, 2361}, GOTWaypoint.Yunnan);
		registerRoad(id++, asshaiCrossroadsHalfway, new double[]{3695, 2420}, asshaiCrossroads, new double[]{3762, 2448}, new double[]{3791, 2494}, new double[]{3807, 2552}, new double[]{3811, 2608}, new double[]{3803, 2687}, new double[]{3770, 2754}, GOTWaypoint.Asshai);
		registerRoad(id++, GOTWaypoint.Jinqi, new double[]{3630, 2391}, new double[]{3658, 2398}, asshaiCrossroadsHalfway);
		registerRoad(id++, GOTWaypoint.Eijiang, new double[]{3175, 2141}, new double[]{3246, 2117}, GOTWaypoint.Tiqui);
		registerRoad(id++, GOTWaypoint.SiQo, new double[]{3276, 2218}, new double[]{3299, 2178}, GOTWaypoint.Tiqui.info(-1, 0));
		registerRoad(id++, GOTWaypoint.Tiqui.info(-1, 0), new double[]{3316, 2024}, new double[]{3348, 1940}, GOTWaypoint.TraderTown);
		registerRoad(id++, GOTWaypoint.Samyriana, new double[]{3144, 1860}, new double[]{3249, 1873}, GOTWaypoint.TraderTown);
		registerRoad(id++, GOTWaypoint.TraderTown, new double[]{3382, 1864}, new double[]{3383, 1862}, GOTWaypoint.Anjiang);
		registerRoad(id++, GOTWaypoint.TraderTown, new double[]{3398, 1914}, new double[]{3457, 1960}, GOTWaypoint.Vaibei);
		registerRoad(id++, GOTWaypoint.Vaibei, new double[]{3553, 2008}, new double[]{3578, 2068}, GOTWaypoint.Yibin);
		registerRoad(id++, GOTWaypoint.Yibin, new double[]{3732, 2071}, new double[]{3811, 2011}, GOTWaypoint.FiveForts5);
		registerRoad(id++, GOTWaypoint.Yibin, new double[]{3650, 2108}, new double[]{3663, 2125}, GOTWaypoint.Lizhao);
		registerRoad(id++, GOTWaypoint.Lizhao, new double[]{3672, 2184}, new double[]{3663, 2220}, GOTWaypoint.FuNing);
		registerRoad(id++, GOTWaypoint.FuNing, new double[]{3623, 2308}, new double[]{3620, 2341}, GOTWaypoint.Jinqi);
		registerRoad(id++, GOTWaypoint.FuNing, new double[]{3595, 2246}, new double[]{3561, 2222}, GOTWaypoint.Baoji);
		registerRoad(id++, GOTWaypoint.Baoji, new double[]{3481, 2212}, new double[]{3458, 2206}, GOTWaypoint.Manjin);
		registerRoad(id++, GOTWaypoint.Tiqui, new double[]{3390, 2123}, new double[]{3432, 2160}, GOTWaypoint.Manjin);

		/* LINKERS */
		double westerosCastle = 0.1953125;
		double westerosTown = 0.2453125;

		registerLinker(id++, GOTWaypoint.Seagard.info(0, -westerosCastle - 0.2), GOTWaypoint.Seagard.info(0, westerosTown));
		registerLinker(id++, GOTWaypoint.Appleton, GOTWaypoint.Appleton.info(0, -westerosCastle - 0.01));
		registerLinker(id++, GOTWaypoint.Appleton, GOTWaypoint.Appleton.info(0.1, westerosTown + 0.2));
		registerLinker(id++, GOTWaypoint.Winterfell, GOTWaypoint.Winterfell.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.CasterlyRock, GOTWaypoint.CasterlyRock.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Crakehall, GOTWaypoint.Crakehall.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Lannisport, GOTWaypoint.Lannisport.info(-westerosTown, 0));
		registerLinker(id++, GOTWaypoint.ServinsCastle, GOTWaypoint.ServinsCastle.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Dreadfort, GOTWaypoint.Dreadfort.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.Goldgrass, GOTWaypoint.Goldgrass.info(0, westerosCastle));
		registerLinker(id++, GOTWaypoint.Karhold, GOTWaypoint.Karhold.info(westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.WhiteHarbour, GOTWaypoint.WhiteHarbour.info(westerosTown, 0));
		registerLinker(id++, GOTWaypoint.TorhensSquare, GOTWaypoint.TorhensSquare.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.RillwaterCrossing, GOTWaypoint.RillwaterCrossing.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.RyswellsCastle, GOTWaypoint.RyswellsCastle.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Saltpans, GOTWaypoint.Saltpans.info(westerosTown, 0));
		registerLinker(id++, GOTWaypoint.Harroway, GOTWaypoint.Harroway.info(0, westerosTown));
		registerLinker(id++, GOTWaypoint.StoneHedge, GOTWaypoint.StoneHedge.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.AcornHall, GOTWaypoint.AcornHall.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.WayfarerRest, GOTWaypoint.WayfarerRest.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Riverrun, GOTWaypoint.Riverrun.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.StoneySept, GOTWaypoint.StoneySept.info(-westerosTown, 0));
		registerLinker(id++, GOTWaypoint.BloodyGate, GOTWaypoint.BloodyGate.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.TheEyrie, GOTWaypoint.TheEyrie.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.GateOfTheMoon, GOTWaypoint.GateOfTheMoon.info(westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Hayford, GOTWaypoint.Hayford.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.RooksRest, GOTWaypoint.RooksRest.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.Antlers, GOTWaypoint.Antlers.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.Stokeworth, GOTWaypoint.Stokeworth.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Rosby, GOTWaypoint.Rosby.info(-westerosCastle - 0.05, 0));
		registerLinker(id++, GOTWaypoint.Duskendale, GOTWaypoint.Duskendale.info(-0.1, -westerosTown - 0.2));
		registerLinker(id++, GOTWaypoint.KingsLanding, GOTWaypoint.KingsLanding.info(1.6953125, 0));
		registerLinker(id++, GOTWaypoint.GoldenTooth, GOTWaypoint.GoldenTooth.info(0, -westerosCastle - 0.02));
		registerLinker(id++, GOTWaypoint.Sarsfield, GOTWaypoint.Sarsfield.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.OldOak, GOTWaypoint.OldOak.info(0, westerosCastle));
		registerLinker(id++, GOTWaypoint.RedLake, GOTWaypoint.RedLake.info(0, westerosCastle));
		registerLinker(id++, GOTWaypoint.Coldmoat, GOTWaypoint.Coldmoat.info(0, westerosCastle));
		registerLinker(id++, GOTWaypoint.Bitterbridge, GOTWaypoint.Bitterbridge.info(0, westerosCastle));
		registerLinker(id++, GOTWaypoint.IvyHall, GOTWaypoint.IvyHall.info(0, westerosCastle));
		registerLinker(id++, GOTWaypoint.SunHouse, GOTWaypoint.SunHouse.info(0, westerosCastle));
		registerLinker(id++, GOTWaypoint.Ring, GOTWaypoint.Ring.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.Tumbleton, GOTWaypoint.Tumbleton.info(0, -westerosTown - 0.1));
		registerLinker(id++, GOTWaypoint.Whitegrove, GOTWaypoint.Whitegrove.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.GarnetGrove, GOTWaypoint.GarnetGrove.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.DarkDell, GOTWaypoint.DarkDell.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Hammerhal, GOTWaypoint.Hammerhal.info(westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Holyhall, GOTWaypoint.Holyhall.info(westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Highgarden, GOTWaypoint.Highgarden.info(0.5, 0));
		registerLinker(id++, GOTWaypoint.Smithyton, GOTWaypoint.Smithyton.info(0, westerosTown));
		registerLinker(id++, GOTWaypoint.Oldtown, GOTWaypoint.Oldtown.info(-westerosTown - 0.2, 0));
		registerLinker(id++, GOTWaypoint.ThreeTowers, GOTWaypoint.ThreeTowers.info(-0.5, -0.5));
		registerLinker(id++, GOTWaypoint.ThreeTowers, GOTWaypoint.ThreeTowers.info(-0.5, 0));
		registerLinker(id++, GOTWaypoint.ThreeTowers, GOTWaypoint.ThreeTowers.info(-0.5, 0.5));
		registerLinker(id++, GOTWaypoint.Grandview, GOTWaypoint.Grandview.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Blackhaven, GOTWaypoint.Blackhaven.info(-westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Felwood, GOTWaypoint.Felwood.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.HaystackHall, GOTWaypoint.HaystackHall.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.Gallowsgrey, GOTWaypoint.Gallowsgrey.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.Parchments, GOTWaypoint.Parchments.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.Bronzegate, GOTWaypoint.Bronzegate.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.Poddingfield, GOTWaypoint.Poddingfield.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.HarvestHall, GOTWaypoint.HarvestHall.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.Nightsong, GOTWaypoint.Nightsong.info(0, -westerosCastle));
		registerLinker(id++, GOTWaypoint.StormsEnd, GOTWaypoint.StormsEnd.info(0, westerosCastle));
		registerLinker(id++, GOTWaypoint.SkyReach, GOTWaypoint.SkyReach.info(0, westerosCastle));
		registerLinker(id++, GOTWaypoint.Starfall, GOTWaypoint.Starfall.info(0, westerosCastle + 0.1));
		registerLinker(id++, GOTWaypoint.HighHermitage, GOTWaypoint.HighHermitage.info(westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Blackmont, GOTWaypoint.Blackmont.info(westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Kingsgrave, GOTWaypoint.Kingsgrave.info(westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Yronwood, GOTWaypoint.Yronwood.info(westerosCastle, 0));
		registerLinker(id++, GOTWaypoint.Wyl, GOTWaypoint.Wyl.info(-westerosCastle - 0.05, 0));
		registerLinker(id++, GOTWaypoint.Zamettar, GOTWaypoint.Zamettar.info(0, -0.125));

		double essosTown = 0.1484375;

		registerLinker(id++, GOTWaypoint.Braavos, GOTWaypoint.Braavos.info(0, -essosTown));
		registerLinker(id++, GOTWaypoint.Norvos, GOTWaypoint.Norvos.info(0, -essosTown));
		registerLinker(id++, GOTWaypoint.Mantarys, GOTWaypoint.Mantarys.info(0, -essosTown));
		registerLinker(id++, GOTWaypoint.VolonTherys, GOTWaypoint.VolonTherys.info(0, essosTown));
		registerLinker(id++, GOTWaypoint.LittleValyria, GOTWaypoint.LittleValyria.info(0.26, essosTown + 0.1));
		registerLinker(id++, GOTWaypoint.Tolos, GOTWaypoint.Tolos.info(0, essosTown));
		registerLinker(id++, GOTWaypoint.Meereen, GOTWaypoint.Meereen.info(0, -essosTown));
		registerLinker(id++, GOTWaypoint.PortYhos, GOTWaypoint.PortYhos.info(0, essosTown));
		registerLinker(id++, GOTWaypoint.Qarkash, GOTWaypoint.Qarkash.info(0, essosTown));
		registerLinker(id++, GOTWaypoint.Qarth, GOTWaypoint.Qarth.info(0, essosTown));
		registerLinker(id++, GOTWaypoint.Volantis, GOTWaypoint.Volantis.info(-0.2, essosTown + 0.1));
		registerLinker(id++, GOTWaypoint.Pentos, GOTWaypoint.Pentos.info(-essosTown, 0));
		registerLinker(id++, GOTWaypoint.Myr, GOTWaypoint.Myr.info(-essosTown, 0));
		registerLinker(id++, GOTWaypoint.Qohor, GOTWaypoint.Qohor.info(-essosTown - 0.1, 0));
		registerLinker(id++, GOTWaypoint.Selhorys, GOTWaypoint.Selhorys.info(-essosTown, 0));
		registerLinker(id++, GOTWaypoint.Valysar, GOTWaypoint.Valysar.info(-essosTown - 0.1, 0.25));
		registerLinker(id++, GOTWaypoint.Yunkai, GOTWaypoint.Yunkai.info(-essosTown, 0));
		registerLinker(id++, GOTWaypoint.Astapor, GOTWaypoint.Astapor.info(-essosTown, 0));

		registerLinker(id++, GOTWaypoint.Kosrak, GOTWaypoint.Kosrak.info(1, 0));
		registerLinker(id++, GOTWaypoint.Lhazosh, GOTWaypoint.Lhazosh.info(1, 0));
		registerLinker(id++, GOTWaypoint.Hesh, GOTWaypoint.Hesh.info(1, 0));
		registerLinker(id++, GOTWaypoint.Asshai, GOTWaypoint.Asshai.info(0, westerosTown - 0.1));

		double yitiTown = 0.2734375;

		registerLinker(id++, GOTWaypoint.TraderTown, GOTWaypoint.TraderTown.info(0, -yitiTown));
		registerLinker(id++, GOTWaypoint.Yibin, GOTWaypoint.Yibin.info(0, -yitiTown));
		registerLinker(id++, GOTWaypoint.Vaibei, GOTWaypoint.Vaibei.info(0, -yitiTown));
		registerLinker(id++, GOTWaypoint.Baoji, GOTWaypoint.Baoji.info(0, -yitiTown));
		registerLinker(id++, GOTWaypoint.Eijiang, GOTWaypoint.Eijiang.info(0, yitiTown));
		registerLinker(id++, GOTWaypoint.Yin, GOTWaypoint.Yin.info(0, yitiTown));
		registerLinker(id++, GOTWaypoint.Jinqi, GOTWaypoint.Jinqi.info(-yitiTown, 0));
		registerLinker(id++, GOTWaypoint.Asabhad, GOTWaypoint.Asabhad.info(-yitiTown, 0));
		registerLinker(id++, GOTWaypoint.SiQo, GOTWaypoint.SiQo.info(yitiTown, 0));
		registerLinker(id++, GOTWaypoint.Yunnan, GOTWaypoint.Yunnan.info(yitiTown, 0));
		registerLinker(id++, GOTWaypoint.Manjin, GOTWaypoint.Manjin.info(yitiTown + 0.1, -0.2));
		registerLinker(id++, GOTWaypoint.Lizhao, GOTWaypoint.Lizhao.info(yitiTown, 0));
		registerLinker(id++, GOTWaypoint.Tiqui, GOTWaypoint.Tiqui.info(0, -yitiTown));
		registerLinker(id++, GOTWaypoint.Changan, GOTWaypoint.Changan.info(yitiTown, 0));
		registerLinker(id++, GOTWaypoint.FuNing, GOTWaypoint.FuNing.info(yitiTown + 0.1, 0.1));
	}

	public enum Type {
		ROAD, WALL, LINKER
	}

	public static void registerLinker(int id, Object... waypoints) {
		ArrayList<BezierPoint> points = new ArrayList<>();
		for (Object obj : waypoints) {
			if (obj instanceof GOTAbstractWaypoint) {
				GOTAbstractWaypoint wp = (GOTAbstractWaypoint) obj;
				points.add(new BezierPoint(wp.getXCoord(), wp.getZCoord(), true));
			} else if (obj instanceof double[]) {
				double[] coords = (double[]) obj;
				if (coords.length == 2) {
					points.add(new BezierPoint(GOTWaypoint.mapToWorldX(coords[0]), GOTWaypoint.mapToWorldZ(coords[1]), false));
					continue;
				}
				throw new IllegalArgumentException("Coords length must be 2!");
			}
		}
		BezierPoint[] array = points.toArray(new BezierPoint[0]);
		BezierCurves.getSplines(array, Type.LINKER);
	}

	public static void registerRoad(int id, Object... waypoints) {
		ArrayList<BezierPoint> points = new ArrayList<>();
		for (Object obj : waypoints) {
			if (obj instanceof GOTAbstractWaypoint) {
				GOTAbstractWaypoint wp = (GOTAbstractWaypoint) obj;
				points.add(new BezierPoint(wp.getXCoord(), wp.getZCoord(), true));
			} else if (obj instanceof double[]) {
				double[] coords = (double[]) obj;
				if (coords.length == 2) {
					points.add(new BezierPoint(GOTWaypoint.mapToWorldX(coords[0]), GOTWaypoint.mapToWorldZ(coords[1]), false));
					continue;
				}
				throw new IllegalArgumentException("Coords length must be 2!");
			}
		}
		BezierPoint[] array = points.toArray(new BezierPoint[0]);
		GOTBeziers[] beziers = BezierCurves.getSplines(array, Type.ROAD);
		allBeziers.addAll(Arrays.asList(beziers));
	}

	public static void registerWall(int id, Object... waypoints) {
		ArrayList<BezierPoint> points = new ArrayList<>();
		for (Object obj : waypoints) {
			if (obj instanceof GOTAbstractWaypoint) {
				GOTAbstractWaypoint wp = (GOTAbstractWaypoint) obj;
				points.add(new BezierPoint(wp.getXCoord(), wp.getZCoord(), true));
			} else if (obj instanceof double[]) {
				double[] coords = (double[]) obj;
				if (coords.length == 2) {
					points.add(new BezierPoint(GOTWaypoint.mapToWorldX(coords[0]), GOTWaypoint.mapToWorldZ(coords[1]), false));
					continue;
				}
				throw new IllegalArgumentException("Coords length must be 2!");
			}
		}
		BezierPoint[] array = points.toArray(new BezierPoint[0]);
		GOTBeziers[] beziers = BezierCurves.getSplines(array, Type.WALL);
		allBeziers.addAll(Arrays.asList(beziers));
	}

	public static class BezierCurves {
		public static int bezierLengthFactor = 1;

		public static BezierPoint bezier(BezierPoint a, BezierPoint b, BezierPoint c, BezierPoint d, double t) {
			BezierPoint ab = lerp(a, b, t);
			BezierPoint bc = lerp(b, c, t);
			BezierPoint cd = lerp(c, d, t);
			BezierPoint abbc = lerp(ab, bc, t);
			BezierPoint bccd = lerp(bc, cd, t);
			return lerp(abbc, bccd, t);
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

		public static GOTBeziers[] getSplines(BezierPoint[] waypoints, Type type) {
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
					bezier.bezierPoints[l] = point = new BezierPoint(p1.x + dx * t, p1.z + dz * t, false);
					switch (type) {
						case ROAD:
							roadPointDatabase.add(point);
							break;
						case WALL:
							wallPointDatabase.add(point);
							break;
						case LINKER:
							linkerPointDatabase.add(point);
							break;
						default:
							break;
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
				BezierPoint p1 = new BezierPoint(controlX[0][i], controlZ[0][i], false);
				BezierPoint p2 = new BezierPoint(controlX[1][i], controlZ[1][i], false);
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
					bezier.bezierPoints[l] = point = bezier(p1, cp1, cp2, p2, t);
					switch (type) {
						case ROAD:
							roadPointDatabase.add(point);
							break;
						case WALL:
							wallPointDatabase.add(point);
							break;
						case LINKER:
							linkerPointDatabase.add(point);
							break;
						default:
							break;
					}
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