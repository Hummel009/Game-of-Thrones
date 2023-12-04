package got.common.world.map;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static got.common.world.map.GOTCoordConverter.*;

public class GOTBeziers {
	public static Collection<GOTBeziers> allBeziers = new ArrayList<>();

	public static BezierPointDatabase linkerPointDatabase = new BezierPointDatabase();
	public static BezierPointDatabase roadPointDatabase = new BezierPointDatabase();
	public static BezierPointDatabase wallPointDatabase = new BezierPointDatabase();

	public static int id;

	private BezierPoint[] bezierPoints;
	private Collection<BezierPoint> endpoints = new ArrayList<>();

	public GOTBeziers(BezierPoint... ends) {
		Collections.addAll(endpoints, ends);
	}

	public static boolean isBezierAt(int x, int z, Type type) {
		return isBezierNear(x, z, 4, type) >= 0.0f;
	}

	public static float isBezierNear(int x, int z, int width, Type type) {
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
		}
		float leastSqRatio = -1.0f;
		double widthSq = width * width;
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

	public static void onInit() {
		allBeziers.clear();

		roadPointDatabase = new BezierPointDatabase();
		wallPointDatabase = new BezierPointDatabase();
		linkerPointDatabase = new BezierPointDatabase();

		registerBezier(Type.WALL, GOTWaypoint.WESTWATCH, GOTWaypoint.SHADOW_TOWER, GOTWaypoint.SENTINEL_STAND, GOTWaypoint.GREYGUARD, GOTWaypoint.STONEDOOR, GOTWaypoint.HOARFROST_HILL, GOTWaypoint.ICEMARK, GOTWaypoint.NIGHTFORT, GOTWaypoint.DEEP_LAKE, GOTWaypoint.QUEENSGATE, GOTWaypoint.CASTLE_BLACK, GOTWaypoint.OAKENSHIELD, GOTWaypoint.WOODSWATCH, GOTWaypoint.SABLE_HALL, GOTWaypoint.RIMEGATE, GOTWaypoint.THE_LONG_BARROW, GOTWaypoint.THE_TORCHES, GOTWaypoint.GREENGUARD, GOTWaypoint.EASTWATCH);
		registerBezier(Type.WALL, GOTWaypoint.ANBEI, GOTWaypoint.JIANMEN, GOTWaypoint.ANGUO, GOTWaypoint.ANJIANG, GOTWaypoint.DINGGUO, GOTWaypoint.PINNU, GOTWaypoint.PINGJIANG, GOTWaypoint.WUDE, GOTWaypoint.WUSHENG, GOTWaypoint.ZHENGUO, GOTWaypoint.LUNGMEN, GOTWaypoint.PINGBEI);

		registerBezier(Type.WALL, new double[]{2847, 1273}, new double[]{2820, 1292}, new double[]{2771, 1308}, new double[]{2732, 1308});
		registerBezier(Type.WALL, new double[]{2732, 1308}, new double[]{2683, 1294}, new double[]{2628, 1294}, new double[]{2588, 1275});
		registerBezier(Type.WALL, new double[]{2708, 1230}, new double[]{2683, 1244}, new double[]{2656, 1253}, new double[]{2638, 1252});

		registerBezier(Type.ROAD, new double[]{559, 544}, GOTWaypoint.SKIRLING_PASS, new double[]{596, 544});

		/* NORTH */

		registerBezier(Type.ROAD, GOTWaypoint.CASTLE_BLACK, new double[]{745, 732}, new double[]{694, 804}, GOTWaypoint.WINTERFELL);
		registerBezier(Type.ROAD, GOTWaypoint.WINTERFELL, new double[]{642, 891}, GOTWaypoint.CASTLE_CERWYN);
		registerBezier(Type.ROAD, GOTWaypoint.CASTLE_CERWYN, new double[]{696, 899}, new double[]{765, 872}, GOTWaypoint.DREADFORT);
		registerBezier(Type.ROAD, GOTWaypoint.DREADFORT, new double[]{855, 865}, new double[]{893, 828}, GOTWaypoint.KARHOLD);
		registerBezier(Type.ROAD, GOTWaypoint.CASTLE_CERWYN, new double[]{632, 976}, new double[]{644, 1043}, GOTWaypoint.MOAT_KAILIN.info(0, -0.5));
		registerBezier(Type.ROAD, GOTWaypoint.TORRHENS_SQUARE, new double[]{553, 996}, new double[]{539, 1037}, GOTWaypoint.GOLDGRASS);
		registerBezier(Type.ROAD, GOTWaypoint.RILLWATER_CROSSING, new double[]{422, 989}, new double[]{419, 1013}, GOTWaypoint.RYSWELLS_CASTLE);
		registerBezier(Type.ROAD, GOTWaypoint.RYSWELLS_CASTLE, new double[]{447, 1064}, new double[]{496, 1066}, GOTWaypoint.GOLDGRASS);
		registerBezier(Type.ROAD, GOTWaypoint.GOLDGRASS, new double[]{606, 1081}, new double[]{626, 1103}, GOTWaypoint.MOAT_KAILIN.info(-0.5, 0));
		registerBezier(Type.ROAD, GOTWaypoint.MOAT_KAILIN.info(0.5, 0), new double[]{672, 1101}, new double[]{707, 1072}, GOTWaypoint.WHITE_HARBOUR);
		double[] northRiverlandsCrossroads = {655, 1257};
		registerBezier(Type.ROAD, GOTWaypoint.MOAT_KAILIN.info(0, 0.5), new double[]{649, 1136}, new double[]{656, 1179}, northRiverlandsCrossroads);
		registerBezier(Type.ROAD, GOTWaypoint.MOAT_KAILIN.info(-0.5, 0), GOTWaypoint.MOAT_KAILIN.info(0.5, 0));
		registerBezier(Type.ROAD, GOTWaypoint.MOAT_KAILIN.info(0, -0.5), GOTWaypoint.MOAT_KAILIN.info(0, 0.5));

		/* ARRYN */
		registerBezier(Type.ROAD, GOTWaypoint.CROSSROADS_INN, new double[]{754, 1430}, new double[]{771, 1417}, GOTWaypoint.BLOODY_GATE);
		registerBezier(Type.ROAD, GOTWaypoint.BLOODY_GATE, new double[]{804, 1397}, new double[]{817, 1388}, GOTWaypoint.GATE_OF_THE_MOON);
		registerBezier(Type.ROAD, GOTWaypoint.GATE_OF_THE_MOON, new double[]{827, 1369}, new double[]{828, 1361}, GOTWaypoint.THE_EYRIE);

		/* RIVERLANDS */

		registerBezier(Type.ROAD, northRiverlandsCrossroads, new double[]{638, 1268}, new double[]{623, 1282}, GOTWaypoint.TWINS_RIGHT);
		registerBezier(Type.ROAD, GOTWaypoint.TWINS_RIGHT, GOTWaypoint.TWINS_LEFT);
		registerBezier(Type.ROAD, GOTWaypoint.TWINS_LEFT, new double[]{590, 1296}, new double[]{583, 1311}, GOTWaypoint.SEAGARD);
		double[] northRiverlandsBridge = {713, 1400};
		registerBezier(Type.ROAD, northRiverlandsCrossroads, new double[]{664, 1310}, new double[]{683, 1360}, northRiverlandsBridge);
		registerBezier(Type.ROAD, northRiverlandsBridge, new double[]{721, 1412}, new double[]{723, 1428}, GOTWaypoint.CROSSROADS_INN);
		registerBezier(Type.ROAD, GOTWaypoint.CROSSROADS_INN, new double[]{758, 1446}, new double[]{776, 1461}, GOTWaypoint.SALTPANS);
		double[] antiCrossroadsInn = {732, 1447};
		registerBezier(Type.ROAD, GOTWaypoint.CROSSROADS_INN, antiCrossroadsInn);
		registerBezier(Type.ROAD, antiCrossroadsInn, new double[]{730, 1463}, new double[]{735, 1479}, GOTWaypoint.WHITEWALLS);
		registerBezier(Type.ROAD, GOTWaypoint.WHITEWALLS, new double[]{726, 1490}, GOTWaypoint.HARRENHAL);
		registerBezier(Type.ROAD, GOTWaypoint.WHITEWALLS, new double[]{751, 1503}, new double[]{761, 1513}, GOTWaypoint.HOGG_HALL);
		registerBezier(Type.ROAD, GOTWaypoint.HOGG_HALL, GOTWaypoint.ANTLERS);
		registerBezier(Type.ROAD, antiCrossroadsInn, GOTWaypoint.HARROWAY);
		registerBezier(Type.ROAD, GOTWaypoint.HARROWAY, new double[]{703, 1443}, new double[]{685, 1446}, GOTWaypoint.CASTLE_LYCHESTER);
		registerBezier(Type.ROAD, GOTWaypoint.CASTLE_LYCHESTER, new double[]{657, 1450}, new double[]{644, 1458}, GOTWaypoint.ACORN_HALL);
		registerBezier(Type.ROAD, GOTWaypoint.ACORN_HALL, new double[]{628, 1478}, GOTWaypoint.WAYFARERS_REST);
		registerBezier(Type.ROAD, GOTWaypoint.CASTLE_LYCHESTER, new double[]{655, 1439}, new double[]{642, 1430}, GOTWaypoint.STONE_HEDGE);
		registerBezier(Type.ROAD, GOTWaypoint.STONE_HEDGE, new double[]{615, 1431}, new double[]{601, 1434}, GOTWaypoint.RIVERRUN);
		double[] hillsExit = {519, 1476};
		registerBezier(Type.ROAD, GOTWaypoint.RIVERRUN, new double[]{565, 1444}, new double[]{541, 1462}, hillsExit);
		registerBezier(Type.ROAD, GOTWaypoint.STONEY_SEPT, new double[]{691, 1565}, GOTWaypoint.BRIARWHITE);

		/* CROWNLANDS */
		registerBezier(Type.ROAD, GOTWaypoint.BRIARWHITE, new double[]{740, 1588}, new double[]{748, 1606}, GOTWaypoint.HAYFORD);
		registerBezier(Type.ROAD, GOTWaypoint.HAYFORD, new double[]{767, 1624}, GOTWaypoint.KINGS_LANDING);
		registerBezier(Type.ROAD, GOTWaypoint.ANTLERS, new double[]{799, 1547}, new double[]{809, 1569}, GOTWaypoint.STOKEWORTH);
		registerBezier(Type.ROAD, GOTWaypoint.STOKEWORTH, new double[]{818, 1584}, new double[]{826, 1581}, GOTWaypoint.DUSKENDALE);
		registerBezier(Type.ROAD, GOTWaypoint.DUSKENDALE, new double[]{837, 1573}, new double[]{840, 1569}, GOTWaypoint.HOLLARD_CASTLE);
		registerBezier(Type.ROAD, GOTWaypoint.HOLLARD_CASTLE, new double[]{848, 1552}, new double[]{855, 1542}, GOTWaypoint.ROOKS_REST);
		registerBezier(Type.ROAD, GOTWaypoint.STOKEWORTH, new double[]{807, 1597}, GOTWaypoint.ROSBY);
		registerBezier(Type.ROAD, GOTWaypoint.ROSBY, new double[]{793, 1614}, new double[]{778, 1617}, GOTWaypoint.KINGS_LANDING);

		/* WESTERLANDS */

		registerBezier(Type.ROAD, hillsExit, new double[]{510, 1480}, new double[]{501, 1485}, GOTWaypoint.GOLDEN_TOOTH);
		registerBezier(Type.ROAD, GOTWaypoint.GOLDEN_TOOTH, new double[]{476, 1504}, new double[]{460, 1513}, GOTWaypoint.SARSFIELD);
		registerBezier(Type.ROAD, GOTWaypoint.SARSFIELD, GOTWaypoint.OXCROSS);
		registerBezier(Type.ROAD, GOTWaypoint.OXCROSS, GOTWaypoint.CASTERLY_ROCK);
		registerBezier(Type.ROAD, GOTWaypoint.CASTERLY_ROCK, GOTWaypoint.LANNISPORT);
		registerBezier(Type.ROAD, GOTWaypoint.LANNISPORT, new double[]{371, 1607}, new double[]{366, 1639}, GOTWaypoint.CRAKEHALL);
		double[] westerlandsCrossroads = {363, 1716};
		registerBezier(Type.ROAD, GOTWaypoint.CRAKEHALL, new double[]{349, 1681}, new double[]{359, 1700}, westerlandsCrossroads);
		double[] westerlandsHillsHalfway = {489, 1577};
		registerBezier(Type.ROAD, GOTWaypoint.LANNISPORT, new double[]{410, 1572}, new double[]{453, 1569}, westerlandsHillsHalfway);
		double[] reachCrossroads = {574, 1623};
		registerBezier(Type.ROAD, westerlandsHillsHalfway, new double[]{518, 1592}, new double[]{543, 1612}, reachCrossroads);

		/* REACH */

		registerBezier(Type.ROAD, westerlandsCrossroads, new double[]{365, 1730}, new double[]{371, 1743}, GOTWaypoint.OLD_OAK);
		registerBezier(Type.ROAD, reachCrossroads, GOTWaypoint.HAMMERHAL, GOTWaypoint.IVY_HALL);
		registerBezier(Type.ROAD, reachCrossroads, new double[]{640, 1627}, new double[]{705, 1625}, GOTWaypoint.KINGS_LANDING);
		registerBezier(Type.ROAD, GOTWaypoint.SMITHYTON, new double[]{707, 1689}, new double[]{704, 1680}, GOTWaypoint.TUMBLETON);
		registerBezier(Type.ROAD, GOTWaypoint.TUMBLETON, new double[]{687, 1673}, new double[]{673, 1673}, GOTWaypoint.RING);
		registerBezier(Type.ROAD, GOTWaypoint.SMITHYTON, GOTWaypoint.BITTERBRIDGE);
		registerBezier(Type.ROAD, GOTWaypoint.BITTERBRIDGE, new double[]{625, 1714}, new double[]{596, 1727}, GOTWaypoint.APPLETON);
		registerBezier(Type.ROAD, GOTWaypoint.APPLETON, new double[]{537, 1768}, GOTWaypoint.DARKDELL);
		registerBezier(Type.ROAD, GOTWaypoint.DARKDELL, new double[]{512, 1794}, new double[]{501, 1803}, GOTWaypoint.HIGHGARDEN);
		registerBezier(Type.ROAD, GOTWaypoint.DARKDELL, new double[]{512, 1772}, new double[]{498, 1768}, GOTWaypoint.HOLYHALL);
		registerBezier(Type.ROAD, GOTWaypoint.HOLYHALL, new double[]{491, 1745}, new double[]{485, 1730}, GOTWaypoint.COLDMOAT);
		registerBezier(Type.ROAD, GOTWaypoint.COLDMOAT, new double[]{459, 1720}, new double[]{446, 1714}, GOTWaypoint.RED_LAKE);
		registerBezier(Type.ROAD, GOTWaypoint.RED_LAKE, new double[]{410, 1711}, new double[]{387, 1713}, westerlandsCrossroads);
		registerBezier(Type.ROAD, GOTWaypoint.HIGHGARDEN, new double[]{490, 1830}, new double[]{487, 1844}, GOTWaypoint.WHITEGROVE);
		registerBezier(Type.ROAD, GOTWaypoint.WHITEGROVE, new double[]{459, 1887}, new double[]{419, 1915}, GOTWaypoint.OLDTOWN);
		registerBezier(Type.ROAD, GOTWaypoint.OLDTOWN, new double[]{383, 1968}, new double[]{376, 1983}, GOTWaypoint.THREE_TOWERS);
		registerBezier(Type.ROAD, GOTWaypoint.THREE_TOWERS, new double[]{354, 2006}, GOTWaypoint.GARNETGROVE);
		registerBezier(Type.ROAD, GOTWaypoint.GARNETGROVE, new double[]{378, 2026}, new double[]{394, 2046}, GOTWaypoint.SUNHOUSE);
		registerBezier(Type.ROAD, GOTWaypoint.SUNHOUSE, new double[]{449, 2034}, new double[]{475, 2022}, GOTWaypoint.STARFALL);
		registerBezier(Type.ROAD, GOTWaypoint.WHITEGROVE, new double[]{521, 1857}, new double[]{550, 1870}, GOTWaypoint.NIGHTSONG);
		double[] kingswoodCrossroads = {775, 1666};
		registerBezier(Type.ROAD, GOTWaypoint.SMITHYTON, new double[]{732, 1692}, new double[]{752, 1677}, kingswoodCrossroads);

		/* DORNE */
		registerBezier(Type.ROAD, GOTWaypoint.STARFALL, new double[]{507, 1994}, new double[]{510, 1987}, GOTWaypoint.HIGH_HERMITAGE);
		registerBezier(Type.ROAD, GOTWaypoint.HIGH_HERMITAGE, new double[]{516, 1966}, new double[]{520, 1955}, GOTWaypoint.BLACKMONT);
		registerBezier(Type.ROAD, GOTWaypoint.YRONWOOD, new double[]{688, 1954}, new double[]{715, 1921}, GOTWaypoint.WYL);
		registerBezier(Type.ROAD, GOTWaypoint.WYL, new double[]{734, 1894}, new double[]{726, 1884}, GOTWaypoint.BLACKHAVEN);

		/* STORMLANDS */

		registerBezier(Type.ROAD, GOTWaypoint.NIGHTSONG, new double[]{607, 1870}, new double[]{625, 1861}, GOTWaypoint.HARVEST_HALL);
		registerBezier(Type.ROAD, GOTWaypoint.HARVEST_HALL, new double[]{654, 1849}, new double[]{665, 1846}, GOTWaypoint.PODDINGFIELD);
		double[] stormlandsCrossroads = {741, 1840};
		registerBezier(Type.ROAD, GOTWaypoint.PODDINGFIELD, new double[]{698, 1842}, new double[]{719, 1841}, stormlandsCrossroads);
		registerBezier(Type.ROAD, GOTWaypoint.NIGHTSONG, new double[]{593, 1886}, new double[]{601, 1898}, GOTWaypoint.TOWER_OF_JOY);
		registerBezier(Type.ROAD, GOTWaypoint.TOWER_OF_JOY, GOTWaypoint.KINGSGRAVE, GOTWaypoint.SKYREACH);
		registerBezier(Type.ROAD, GOTWaypoint.BLACKHAVEN, new double[]{729, 1861}, new double[]{735, 1851}, stormlandsCrossroads);
		registerBezier(Type.ROAD, stormlandsCrossroads, new double[]{755, 1824}, new double[]{769, 1811}, GOTWaypoint.SUMMERHALL);
		registerBezier(Type.ROAD, GOTWaypoint.KINGS_LANDING, kingswoodCrossroads, GOTWaypoint.GRANDVIEW, GOTWaypoint.SUMMERHALL);
		registerBezier(Type.ROAD, GOTWaypoint.GRANDVIEW, GOTWaypoint.FELWOOD, GOTWaypoint.HAYSTACK_HALL, GOTWaypoint.GALLOWSGREY, GOTWaypoint.PARCHMENTS);
		registerBezier(Type.ROAD, GOTWaypoint.FELWOOD, new double[]{854, 1745}, GOTWaypoint.BRONZEGATE, GOTWaypoint.STORMS_END);

		/* SOTHORYOS */
		double[] sothoryosHalfway = {2176, 2793};

		registerBezier(Type.ROAD, GOTWaypoint.ZAMETTAR, new double[]{2164, 2749}, new double[]{2166, 2770}, sothoryosHalfway);
		registerBezier(Type.ROAD, sothoryosHalfway, new double[]{2189, 2807}, new double[]{2195, 2819}, GOTWaypoint.YEEN);

		/* GHISCAR & LHAZAR */

		registerBezier(Type.ROAD, GOTWaypoint.LHAZOSH, new double[]{2474, 2056}, new double[]{2462, 2111}, new double[]{2463, 2153}, new double[]{2483, 2183}, new double[]{2522, 2203}, GOTWaypoint.VAES_ORVIK);
		registerBezier(Type.ROAD, GOTWaypoint.LHAZOSH, new double[]{2492, 1993}, new double[]{2528, 1976}, GOTWaypoint.KOSRAK);
		double[] giscarLhazarCrossroads = {2373, 1919};
		registerBezier(Type.ROAD, GOTWaypoint.MEEREEN, new double[]{2297, 1945}, new double[]{2334, 1930}, giscarLhazarCrossroads);
		registerBezier(Type.ROAD, GOTWaypoint.ASTAPOR, new double[]{2190, 2130}, new double[]{2196, 2111}, new double[]{2203, 2090}, new double[]{2206, 2059}, new double[]{2196, 2028}, GOTWaypoint.YUNKAI);
		registerBezier(Type.ROAD, GOTWaypoint.YUNKAI, new double[]{2224, 1993}, new double[]{2236, 1970}, GOTWaypoint.MEEREEN.info(-0.5, 0));
		registerBezier(Type.ROAD, GOTWaypoint.MEEREEN.info(-0.5, 0), new double[]{2230, 1933}, new double[]{2158, 1937}, GOTWaypoint.BHORASH);
		registerBezier(Type.ROAD, GOTWaypoint.MEEREEN, GOTWaypoint.MEEREEN.info(-0.5, 0));

		/* DOTHRAKI */
		registerBezier(Type.ROAD, GOTWaypoint.VAES_JINI, new double[]{2916, 1827}, new double[]{2956, 1844}, GOTWaypoint.SAMYRIANA);
		registerBezier(Type.ROAD, GOTWaypoint.VAES_MEJHAH, new double[]{2662, 1819}, new double[]{2749, 1823}, GOTWaypoint.VAES_JINI);
		registerBezier(Type.ROAD, GOTWaypoint.KRAZAAJ_HAS, new double[]{2518, 1838}, new double[]{2544, 1832}, GOTWaypoint.VAES_MEJHAH);
		registerBezier(Type.ROAD, giscarLhazarCrossroads, new double[]{2413, 1902}, new double[]{2449, 1867}, GOTWaypoint.KRAZAAJ_HAS);
		registerBezier(Type.ROAD, giscarLhazarCrossroads, GOTWaypoint.HESH, GOTWaypoint.LHAZOSH);
		registerBezier(Type.ROAD, GOTWaypoint.VAES_ATHJIKHARI, new double[]{2237, 1543}, new double[]{2255, 1559}, GOTWaypoint.VAES_LEQSE);
		registerBezier(Type.ROAD, GOTWaypoint.VAES_LEQSE, new double[]{2263, 1592}, new double[]{2272, 1611}, GOTWaypoint.SATHAR);
		registerBezier(Type.ROAD, GOTWaypoint.SATHAR, new double[]{2251, 1639}, new double[]{2199, 1649}, GOTWaypoint.VOJJOR_SAMVI);
		registerBezier(Type.ROAD, GOTWaypoint.VOJJOR_SAMVI, new double[]{2111, 1653}, new double[]{2068, 1642}, GOTWaypoint.VAES_KHEWO);
		registerBezier(Type.ROAD, GOTWaypoint.VAES_KHEWO, new double[]{1991, 1631}, new double[]{1953, 1630}, GOTWaypoint.VAES_GORQOYI);
		registerBezier(Type.ROAD, GOTWaypoint.SAATH, new double[]{1879, 1373}, new double[]{1887, 1423}, GOTWaypoint.KYTH);
		registerBezier(Type.ROAD, GOTWaypoint.KYTH, new double[]{1925, 1469}, new double[]{1936, 1487}, GOTWaypoint.HORNOTH);
		registerBezier(Type.ROAD, GOTWaypoint.HORNOTH, new double[]{1933, 1521}, new double[]{1902, 1543}, GOTWaypoint.RATHYLAR);
		registerBezier(Type.ROAD, GOTWaypoint.VAES_GORQOYI, new double[]{1907, 1604}, new double[]{1903, 1584}, GOTWaypoint.RATHYLAR);
		registerBezier(Type.ROAD, GOTWaypoint.VAES_KHADOKH, new double[]{1796, 1580}, new double[]{1846, 1576}, GOTWaypoint.RATHYLAR);

		/* QARTH */
		double[] boneMountP1 = {2978, 2279};
		double[] qarthYiTiAfterMount = {3078, 2240};

		registerBezier(Type.ROAD, boneMountP1, new double[]{3002, 2284}, new double[]{3036, 2247}, qarthYiTiAfterMount);
		double[] qarthYiTiBeforeMount = {2928, 2260};
		registerBezier(Type.ROAD, qarthYiTiBeforeMount, new double[]{2948, 2260}, new double[]{2965, 2267}, boneMountP1);
		registerBezier(Type.ROAD, GOTWaypoint.QARTH, new double[]{2884, 2288}, new double[]{2901, 2268}, qarthYiTiBeforeMount);
		registerBezier(Type.ROAD, GOTWaypoint.QARKASH, new double[]{2735, 2265}, new double[]{2766, 2288}, new double[]{2807, 2285}, new double[]{2840, 2275}, new double[]{2864, 2290}, GOTWaypoint.QARTH);
		registerBezier(Type.ROAD, GOTWaypoint.PORT_YHOS, new double[]{2622, 2260}, new double[]{2648, 2243}, new double[]{2665, 2234}, new double[]{2685, 2232}, new double[]{2710, 2239}, GOTWaypoint.QARKASH);
		registerBezier(Type.ROAD, GOTWaypoint.VAES_ORVIK, new double[]{2571, 2255}, new double[]{2563, 2268}, GOTWaypoint.PORT_YHOS);

		/* FREE CITIES */

		registerBezier(Type.ROAD, GOTWaypoint.MANTARYS, new double[]{1864, 2058}, new double[]{1843, 2089}, new double[]{1832, 2136}, new double[]{1830, 2184}, new double[]{1835, 2219}, GOTWaypoint.OROS);
		double[] unhabitedCrossroads = {1956, 2026};
		registerBezier(Type.ROAD, GOTWaypoint.BHORASH, new double[]{2024, 1981}, new double[]{1988, 2001}, unhabitedCrossroads);
		registerBezier(Type.ROAD, unhabitedCrossroads, new double[]{1958, 2043}, new double[]{1951, 2058}, GOTWaypoint.TOLOS);
		registerBezier(Type.ROAD, unhabitedCrossroads, new double[]{1942, 2030}, new double[]{1913, 2027}, GOTWaypoint.MANTARYS);
		registerBezier(Type.ROAD, GOTWaypoint.MANTARYS, new double[]{1839, 2004}, new double[]{1802, 2007}, GOTWaypoint.ANOGARIA);
		registerBezier(Type.ROAD, GOTWaypoint.ANOGARIA, new double[]{1738, 2022}, new double[]{1710, 2030}, GOTWaypoint.LITTLE_VALYRIA);
		registerBezier(Type.ROAD, GOTWaypoint.QOHOR, new double[]{1666, 1606}, new double[]{1732, 1603}, GOTWaypoint.VAES_KHADOKH);
		registerBezier(Type.ROAD, GOTWaypoint.QOHOR, new double[]{1625, 1637}, new double[]{1562, 1661}, GOTWaypoint.AR_NOY);
		registerBezier(Type.ROAD, GOTWaypoint.CHROYANE, new double[]{1437, 1844}, new double[]{1403, 1861}, new double[]{1354, 1868}, new double[]{1315, 1864}, new double[]{1286, 1853}, GOTWaypoint.MYR);
		registerBezier(Type.ROAD, GOTWaypoint.LITTLE_VALYRIA, new double[]{1661, 2022}, GOTWaypoint.VALYRIAN_ROAD);
		registerBezier(Type.ROAD, GOTWaypoint.LITTLE_VALYRIA, new double[]{1638, 2054}, new double[]{1589, 2063}, GOTWaypoint.VOLANTIS);
		registerBezier(Type.ROAD, GOTWaypoint.VOLANTIS, new double[]{1562, 2049}, new double[]{1557, 2045}, GOTWaypoint.SAR_MELL);
		registerBezier(Type.ROAD, GOTWaypoint.SAR_MELL, GOTWaypoint.VOLON_THERYS);
		registerBezier(Type.ROAD, GOTWaypoint.VOLON_THERYS, new double[]{1519, 2032}, new double[]{1508, 2021}, GOTWaypoint.VALYSAR);
		registerBezier(Type.ROAD, GOTWaypoint.VALYSAR, new double[]{1496, 1988}, new double[]{1492, 1963}, GOTWaypoint.SELHORYS);
		registerBezier(Type.ROAD, GOTWaypoint.SELHORYS, new double[]{1501, 1916}, new double[]{1491, 1897}, new double[]{1486, 1879}, new double[]{1484, 1862}, new double[]{1484, 1846}, GOTWaypoint.CHROYANE);
		registerBezier(Type.ROAD, GOTWaypoint.CHROYANE, new double[]{1499, 1802}, new double[]{1505, 1730}, GOTWaypoint.AR_NOY);
		registerBezier(Type.ROAD, GOTWaypoint.AR_NOY, new double[]{1479, 1682}, new double[]{1453, 1660}, GOTWaypoint.NY_SAR);
		double[] norvosHalfway = {1423, 1552};
		registerBezier(Type.ROAD, GOTWaypoint.NY_SAR, new double[]{1430, 1617}, new double[]{1417, 1583}, norvosHalfway);
		registerBezier(Type.ROAD, norvosHalfway, new double[]{1425, 1528}, new double[]{1417, 1510}, GOTWaypoint.NORVOS);
		registerBezier(Type.ROAD, GOTWaypoint.NY_SAR, new double[]{1391, 1635}, new double[]{1356, 1612}, GOTWaypoint.GHOYAN_DROHE);
		registerBezier(Type.ROAD, GOTWaypoint.GHOYAN_DROHE, new double[]{1262, 1604}, GOTWaypoint.PENTOS);
		double[] braavosHalfway1 = {1250, 1451};
		registerBezier(Type.ROAD, GOTWaypoint.GHOYAN_DROHE, new double[]{1315, 1539}, new double[]{1263, 1493}, braavosHalfway1);
		double[] braavosHalfway2 = {1210, 1339};
		registerBezier(Type.ROAD, braavosHalfway1, new double[]{1246, 1421}, new double[]{1223, 1376}, braavosHalfway2);
		registerBezier(Type.ROAD, braavosHalfway2, new double[]{1197, 1304}, new double[]{1175, 1270}, GOTWaypoint.BRAAVOS);

		registerBezier(Type.ROAD, GOTWaypoint.FIVE_FORTS_1, GOTWaypoint.FIVE_FORTS_2);
		registerBezier(Type.ROAD, GOTWaypoint.FIVE_FORTS_2, GOTWaypoint.FIVE_FORTS_3);
		registerBezier(Type.ROAD, GOTWaypoint.FIVE_FORTS_3, GOTWaypoint.FIVE_FORTS_4);
		registerBezier(Type.ROAD, GOTWaypoint.FIVE_FORTS_4, GOTWaypoint.FIVE_FORTS_5);
		registerBezier(Type.ROAD, qarthYiTiAfterMount, new double[]{3092, 2210}, new double[]{3098, 2164}, GOTWaypoint.EIJIANG);
		registerBezier(Type.ROAD, qarthYiTiAfterMount, new double[]{3083, 2244}, new double[]{3086, 2255}, GOTWaypoint.ASABHAD);
		registerBezier(Type.ROAD, GOTWaypoint.EIJIANG, new double[]{3130, 2131}, new double[]{3133, 2100}, GOTWaypoint.BAYASABHAD);
		registerBezier(Type.ROAD, GOTWaypoint.CHANGAN, new double[]{3212, 2373}, new double[]{3223, 2391}, new double[]{3235, 2406}, new double[]{3256, 2425}, new double[]{3277, 2438}, GOTWaypoint.YIN);
		registerBezier(Type.ROAD, GOTWaypoint.CHANGAN, new double[]{3180, 2355}, new double[]{3158, 2330}, new double[]{3132, 2316}, new double[]{3111, 2299}, new double[]{3100, 2270}, GOTWaypoint.ASABHAD);
		registerBezier(Type.ROAD, GOTWaypoint.SI_QO, new double[]{3259, 2291}, new double[]{3227, 2311}, GOTWaypoint.CHANGAN);
		double[] asshaiCrossroads = {3729, 2423};
		registerBezier(Type.ROAD, asshaiCrossroads, new double[]{3757, 2401}, new double[]{3783, 2361}, GOTWaypoint.YUNNAN);
		double[] asshaiCrossroadsHalfway = {3674, 2408};
		registerBezier(Type.ROAD, asshaiCrossroadsHalfway, new double[]{3695, 2420}, asshaiCrossroads, new double[]{3762, 2448}, new double[]{3791, 2494}, new double[]{3807, 2552}, new double[]{3811, 2608}, new double[]{3803, 2687}, new double[]{3770, 2754}, GOTWaypoint.ASSHAI);
		registerBezier(Type.ROAD, GOTWaypoint.JINQI, new double[]{3630, 2391}, new double[]{3658, 2398}, asshaiCrossroadsHalfway);
		registerBezier(Type.ROAD, GOTWaypoint.EIJIANG, new double[]{3175, 2141}, new double[]{3246, 2117}, GOTWaypoint.TIQUI);
		registerBezier(Type.ROAD, GOTWaypoint.SI_QO, new double[]{3276, 2218}, new double[]{3299, 2178}, GOTWaypoint.TIQUI.info(-1, 0));
		registerBezier(Type.ROAD, GOTWaypoint.TIQUI.info(-1, 0), new double[]{3316, 2024}, new double[]{3348, 1940}, GOTWaypoint.TRADER_TOWN);
		registerBezier(Type.ROAD, GOTWaypoint.SAMYRIANA, new double[]{3144, 1860}, new double[]{3249, 1873}, GOTWaypoint.TRADER_TOWN);
		registerBezier(Type.ROAD, GOTWaypoint.TRADER_TOWN, new double[]{3382, 1864}, new double[]{3383, 1862}, GOTWaypoint.ANJIANG);
		registerBezier(Type.ROAD, GOTWaypoint.TRADER_TOWN, new double[]{3398, 1914}, new double[]{3457, 1960}, GOTWaypoint.VAIBEI);
		registerBezier(Type.ROAD, GOTWaypoint.VAIBEI, new double[]{3553, 2008}, new double[]{3578, 2068}, GOTWaypoint.YIBIN);
		registerBezier(Type.ROAD, GOTWaypoint.YIBIN, new double[]{3732, 2071}, new double[]{3811, 2011}, GOTWaypoint.FIVE_FORTS_5);
		registerBezier(Type.ROAD, GOTWaypoint.YIBIN, new double[]{3650, 2108}, new double[]{3663, 2125}, GOTWaypoint.LIZHAO);
		registerBezier(Type.ROAD, GOTWaypoint.LIZHAO, new double[]{3672, 2184}, new double[]{3663, 2220}, GOTWaypoint.FU_NING);
		registerBezier(Type.ROAD, GOTWaypoint.FU_NING, new double[]{3623, 2308}, new double[]{3620, 2341}, GOTWaypoint.JINQI);
		registerBezier(Type.ROAD, GOTWaypoint.FU_NING, new double[]{3595, 2246}, new double[]{3561, 2222}, GOTWaypoint.BAOJI);
		registerBezier(Type.ROAD, GOTWaypoint.BAOJI, new double[]{3481, 2212}, new double[]{3458, 2206}, GOTWaypoint.MANJIN);
		registerBezier(Type.ROAD, GOTWaypoint.TIQUI, new double[]{3390, 2123}, new double[]{3432, 2160}, GOTWaypoint.MANJIN);

		/* LINKERS */
		registerLinker(GOTWaypoint.SEAGARD.info(-0.1, toWesterosCastle(-0.5)), false);
		registerLinker(GOTWaypoint.SEAGARD.info(0, toWesterosTown(0.8)));
		registerLinker(GOTWaypoint.APPLETON.info(0, toWesterosCastle(-0.51)));
		registerLinker(GOTWaypoint.APPLETON.info(0.1, toWesterosTown(1.1)));
		registerLinker(GOTWaypoint.WINTERFELL.info(toWesterosCastle(-0.5), -0.1), true);
		registerLinker(GOTWaypoint.CASTERLY_ROCK.info(toWesterosCastle(-0.4), 0));
		registerLinker(GOTWaypoint.CRAKEHALL.info(toWesterosCastle(-0.5), 0));
		registerLinker(GOTWaypoint.LANNISPORT.info(toWesterosTown(-0.8), 0));
		registerLinker(GOTWaypoint.CASTLE_CERWYN.info(toWesterosCastle(-0.4), 0));
		registerLinker(GOTWaypoint.DREADFORT.info(0, toWesterosCastle(-0.4)));
		registerLinker(GOTWaypoint.GOLDGRASS.info(0, toWesterosCastle(0.4)));
		registerLinker(GOTWaypoint.KARHOLD.info(toWesterosCastle(0.4), 0));
		registerLinker(GOTWaypoint.WHITE_HARBOUR.info(toWesterosTown(0.8), 0));
		registerLinker(GOTWaypoint.TORRHENS_SQUARE.info(toWesterosCastle(-0.4), 0));
		registerLinker(GOTWaypoint.RILLWATER_CROSSING.info(toWesterosCastle(-0.4), 0));
		registerLinker(GOTWaypoint.RYSWELLS_CASTLE.info(toWesterosCastle(-0.5), 0));
		registerLinker(GOTWaypoint.SALTPANS.info(toWesterosTown(0.8), 0));
		registerLinker(GOTWaypoint.HARROWAY.info(0, toWesterosTown(0.9)));
		registerLinker(GOTWaypoint.STONE_HEDGE.info(0, toWesterosCastle(-0.4)));
		registerLinker(GOTWaypoint.ACORN_HALL.info(toWesterosCastle(-0.5), -0.1), true);
		registerLinker(GOTWaypoint.WAYFARERS_REST.info(toWesterosCastle(-0.4), 0));
		registerLinker(GOTWaypoint.RIVERRUN.info(0, toWesterosCastle(-0.4)));
		registerLinker(GOTWaypoint.STONEY_SEPT.info(toWesterosTown(-0.8), 0));
		registerLinker(GOTWaypoint.BLOODY_GATE.info(0, toWesterosCastle(-0.5)));
		registerLinker(GOTWaypoint.THE_EYRIE.info(0, toWesterosCastle(-0.4)));
		registerLinker(GOTWaypoint.GATE_OF_THE_MOON.info(toWesterosCastle(0.5), 0));
		registerLinker(GOTWaypoint.HAYFORD.info(0.1, toWesterosCastle(-0.5)), false);
		registerLinker(GOTWaypoint.ROOKS_REST.info(0, toWesterosCastle(-0.4)));
		registerLinker(GOTWaypoint.ANTLERS.info(0.1, toWesterosCastle(-0.5)), false);
		registerLinker(GOTWaypoint.STOKEWORTH.info(toWesterosCastle(-0.4), 0));
		registerLinker(GOTWaypoint.ROSBY.info(toWesterosCastle(-0.5), -0.1), true);
		registerLinker(GOTWaypoint.DUSKENDALE.info(-0.1, toWesterosTown(-0.9) - 0.2), false);
		registerLinker(GOTWaypoint.KINGS_LANDING.info(1.6953125, 0));
		registerLinker(GOTWaypoint.GOLDEN_TOOTH.info(-0.1, toWesterosCastle(-0.5)), false);
		registerLinker(GOTWaypoint.SARSFIELD.info(-0.1, toWesterosCastle(-0.5)), false);
		registerLinker(GOTWaypoint.OLD_OAK.info(0, toWesterosCastle(0.5)));
		registerLinker(GOTWaypoint.RED_LAKE.info(0, toWesterosCastle(0.5)));
		registerLinker(GOTWaypoint.COLDMOAT.info(0, toWesterosCastle(0.5)));
		registerLinker(GOTWaypoint.BITTERBRIDGE.info(0, toWesterosCastle(0.5)));
		registerLinker(GOTWaypoint.IVY_HALL.info(0, toWesterosCastle(0.5)));
		registerLinker(GOTWaypoint.SUNHOUSE.info(0, toWesterosCastle(0.5)));
		registerLinker(GOTWaypoint.RING.info(0, toWesterosCastle(-0.5)));
		registerLinker(GOTWaypoint.TUMBLETON.info(0, toWesterosTown(-0.9) - 0.1));
		registerLinker(GOTWaypoint.WHITEGROVE.info(toWesterosCastle(-0.5), 0));
		registerLinker(GOTWaypoint.GARNETGROVE.info(toWesterosCastle(-0.5), 0));
		registerLinker(GOTWaypoint.DARKDELL.info(toWesterosCastle(-0.5), 0));
		registerLinker(GOTWaypoint.HAMMERHAL.info(toWesterosCastle(0.5), 0));
		registerLinker(GOTWaypoint.HOLYHALL.info(toWesterosCastle(0.5), 0));
		registerLinker(GOTWaypoint.HIGHGARDEN.info(0.5, 0));
		registerLinker(GOTWaypoint.SMITHYTON.info(0, toWesterosTown(0.9)));
		registerLinker(GOTWaypoint.OLDTOWN.info(toWesterosTown(-0.9) - 0.2, 0));
		registerLinker(GOTWaypoint.THREE_TOWERS.info(-0.5, -0.5));
		registerLinker(GOTWaypoint.THREE_TOWERS.info(-0.5, 0));
		registerLinker(GOTWaypoint.THREE_TOWERS.info(-0.5, 0.5));
		registerLinker(GOTWaypoint.GRANDVIEW.info(toWesterosCastle(-0.5), 0));
		registerLinker(GOTWaypoint.BLACKHAVEN.info(toWesterosCastle(-0.5), 0));
		registerLinker(GOTWaypoint.FELWOOD.info(0, toWesterosCastle(-0.5)));
		registerLinker(GOTWaypoint.HAYSTACK_HALL.info(0, toWesterosCastle(-0.5)));
		registerLinker(GOTWaypoint.GALLOWSGREY.info(0, toWesterosCastle(-0.5)));
		registerLinker(GOTWaypoint.PARCHMENTS.info(0, toWesterosCastle(-0.5)));
		registerLinker(GOTWaypoint.BRONZEGATE.info(0, toWesterosCastle(-0.5)));
		registerLinker(GOTWaypoint.PODDINGFIELD.info(0, toWesterosCastle(-0.5)));
		registerLinker(GOTWaypoint.HARVEST_HALL.info(0, toWesterosCastle(-0.5)));
		registerLinker(GOTWaypoint.NIGHTSONG.info(0, toWesterosCastle(-0.5)));
		registerLinker(GOTWaypoint.STORMS_END.info(0, toWesterosCastle(0.5)));
		registerLinker(GOTWaypoint.SKYREACH.info(0, toWesterosCastle(0.5)));
		registerLinker(GOTWaypoint.STARFALL.info(0, toWesterosCastle(0.5) + 0.1));
		registerLinker(GOTWaypoint.HIGH_HERMITAGE.info(toWesterosCastle(0.5), 0));
		registerLinker(GOTWaypoint.BLACKMONT.info(toWesterosCastle(0.5), 0));
		registerLinker(GOTWaypoint.KINGSGRAVE.info(toWesterosCastle(0.5), 0));
		registerLinker(GOTWaypoint.YRONWOOD.info(toWesterosCastle(0.5), 0));
		registerLinker(GOTWaypoint.WYL.info(toWesterosCastle(-0.5) - 0.05, 0));
		registerLinker(GOTWaypoint.ZAMETTAR.info(0, -0.125));

		registerLinker(GOTWaypoint.BRAAVOS.info(0, toEssosTown(-0.5)));
		registerLinker(GOTWaypoint.NORVOS.info(0, toEssosTown(-0.5)));
		registerLinker(GOTWaypoint.MANTARYS.info(0, toEssosTown(-0.5)));
		registerLinker(GOTWaypoint.VOLON_THERYS.info(0, toEssosTown(0.5)));
		registerLinker(GOTWaypoint.LITTLE_VALYRIA.info(0.26, toEssosTown(0.5) + 0.1));
		registerLinker(GOTWaypoint.TOLOS.info(0, toEssosTown(0.5)));
		registerLinker(GOTWaypoint.MEEREEN.info(0, toEssosTown(-0.5)));
		registerLinker(GOTWaypoint.PORT_YHOS.info(0, toEssosTown(0.5)));
		registerLinker(GOTWaypoint.QARKASH.info(0, toEssosTown(0.5)));
		registerLinker(GOTWaypoint.QARTH.info(0, toEssosTown(0.5)));
		registerLinker(GOTWaypoint.VOLANTIS.info(-0.2, toEssosTown(0.5) + 0.1));
		registerLinker(GOTWaypoint.PENTOS.info(toEssosTown(-0.5), 0));
		registerLinker(GOTWaypoint.MYR.info(toEssosTown(-0.5), 0));
		registerLinker(GOTWaypoint.QOHOR.info(toEssosTown(-0.5) - 0.1, 0));
		registerLinker(GOTWaypoint.SELHORYS.info(toEssosTown(-0.5), 0));
		registerLinker(GOTWaypoint.VALYSAR.info(toEssosTown(-0.5) - 0.1, 0.25));
		registerLinker(GOTWaypoint.YUNKAI.info(toEssosTown(-0.5), 0));
		registerLinker(GOTWaypoint.ASTAPOR.info(toEssosTown(-0.5), 0));

		registerLinker(GOTWaypoint.KOSRAK.info(1, 0));
		registerLinker(GOTWaypoint.LHAZOSH.info(1, 0));
		registerLinker(GOTWaypoint.HESH.info(1, 0));
		registerLinker(GOTWaypoint.ASSHAI.info(0, toWesterosTown(0.9) - 0.1));

		registerLinker(GOTWaypoint.TRADER_TOWN.info(0, toYiTiTown(-1.0)));
		registerLinker(GOTWaypoint.YIBIN.info(0, toYiTiTown(-1.0)));
		registerLinker(GOTWaypoint.VAIBEI.info(0, toYiTiTown(-1.0)));
		registerLinker(GOTWaypoint.BAOJI.info(0, toYiTiTown(-1.0)));
		registerLinker(GOTWaypoint.EIJIANG.info(0, toYiTiTown(1.0)));
		registerLinker(GOTWaypoint.YIN.info(0, toYiTiTown(1.0)));
		registerLinker(GOTWaypoint.JINQI.info(toYiTiTown(-1.0), 0));
		registerLinker(GOTWaypoint.ASABHAD.info(toYiTiTown(-1.0), 0));
		registerLinker(GOTWaypoint.SI_QO.info(toYiTiTown(1.0), 0));
		registerLinker(GOTWaypoint.YUNNAN.info(toYiTiTown(1.0), 0));
		registerLinker(GOTWaypoint.MANJIN.info(toYiTiTown(1.0) + 0.1, -0.2));
		registerLinker(GOTWaypoint.LIZHAO.info(toYiTiTown(1.0), 0));
		registerLinker(GOTWaypoint.TIQUI.info(0, toYiTiTown(-1.0)));
		registerLinker(GOTWaypoint.CHANGAN.info(toYiTiTown(1.0), 0));
		registerLinker(GOTWaypoint.FU_NING.info(toYiTiTown(1.0) + 0.1, 0.1));
	}

	public static void registerBezier(Type type, Object... waypoints) {
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
		GOTBeziers[] beziers = BezierCurves.getSplines(array, type);
		if (type != Type.LINKER) {
			allBeziers.addAll(Arrays.asList(beziers));
		}
		id++;
	}

	public static void registerLinker(GOTAbstractWaypoint to) {
		registerBezier(Type.LINKER, to.getInstance(), to);
	}

	public static void registerLinker(GOTAbstractWaypoint to, boolean xAxis) {
		GOTWaypoint from = to.getInstance();
		double fromX = from.getX();
		double fromY = from.getY();
		double shiftX = to.getShiftX();
		double shiftY = to.getShiftY();
		double toX = to.getX();
		double toY = to.getY();
		if (xAxis) {
			double halfway = Math.min(Math.abs(shiftX / 2.0), 0.1) * (fromX < toX ? -1 : 1);
			registerBezier(Type.LINKER, from, from.info(shiftX + halfway, shiftY));
			registerBezier(Type.LINKER, from.info(shiftX + halfway, shiftY), to);
		} else {
			double halfway = Math.min(Math.abs(shiftY / 2.0), 0.1) * (fromY < toY ? -1 : 1);
			registerBezier(Type.LINKER, from, from.info(shiftX, shiftY + halfway));
			registerBezier(Type.LINKER, from.info(shiftX, shiftY + halfway), to);
		}
	}

	public BezierPoint[] getBezierPoints() {
		return bezierPoints;
	}

	public Collection<BezierPoint> getEndpoints() {
		return endpoints;
	}

	public enum Type {
		ROAD, WALL, LINKER
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
			int length = src.length - 1;
			double[] a = new double[length];
			a[0] = 0.0;
			double[] b = new double[length];
			b[0] = 2.0;
			double[] c = new double[length];
			c[0] = 1.0;
			double[] r = new double[length];
			r[0] = src[0] + 2.0 * src[1];
			int i;
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
				b[i] -= m * c[i - 1];
				r[i] -= m * r[i - 1];
			}
			double[] p1 = new double[length];
			p1[length - 1] = r[length - 1] / b[length - 1];
			for (i = length - 2; i >= 0; --i) {
				p1[i] = (r[i] - c[i] * p1[i + 1]) / b[i];
			}
			double[] p2 = new double[length];
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
					bezier.getBezierPoints()[l] = point = new BezierPoint(p1.x + dx * t, p1.z + dz * t, false);
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
					bezier.getBezierPoints()[l] = point = bezier(p1, cp1, cp2, p2, t);
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
		private double x;
		private double z;
		private boolean isWaypoint;

		public BezierPoint(double i, double j, boolean flag) {
			x = i;
			z = j;
			isWaypoint = flag;
		}

		public double getX() {
			return x;
		}

		public double getZ() {
			return z;
		}

		public boolean isWaypoint() {
			return isWaypoint;
		}
	}

	public static class BezierPointDatabase {
		public static int COORD_LOOKUP_SIZE = 1000;
		private Map<Pair<Integer, Integer>, List<BezierPoint>> pointMap = new HashMap<>();

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