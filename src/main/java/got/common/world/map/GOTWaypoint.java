package got.common.world.map;

import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import got.common.world.genlayer.GOTGenLayerWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.*;

public enum GOTWaypoint implements GOTAbstractWaypoint {
	ACORN_HALL(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 636, 1468),
	ADAKHAKILEKI(Region.ESSOS_SEPARATOR, GOTFaction.UNALIGNED, 2827, 1857),
	AEGON(Region.OCEAN, GOTFaction.UNALIGNED, 20, 3055),
	AK_SHAAR(Region.MOSSOVY, GOTFaction.MOSSOVY, 4664, 1740),
	AMBERLY(Region.STORMLANDS, GOTFaction.STORMLANDS, 921, 1810),
	ANBEI(Region.YI_TI, GOTFaction.YI_TI, 3257, 1864),
	ANGUO(Region.YI_TI, GOTFaction.YI_TI, 3341, 1857),
	ANJIANG(Region.YI_TI, GOTFaction.YI_TI, 3383, 1856),
	ANOGARIA(Arrays.asList(Region.VOLANTIS, Region.VALYRIA), GOTFaction.VOLANTIS, 1771, 2014),
	ANTLERS(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 789, 1534),
	APPLETON(Region.REACH, GOTFaction.REACH, 572, 1743),
	AQUOS_DHAEN(Region.VALYRIA, GOTFaction.UNALIGNED, 1906, 2378),
	AR_NOY(Region.QOHOR, GOTFaction.QOHOR, 1521, 1693),
	ASABHAD(Region.YI_TI, GOTFaction.YI_TI, 3081, 2265),
	ASHEMARK(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 445, 1497),
	ASHFORD(Region.REACH, GOTFaction.REACH, 609, 1811),
	ASSHAI(Arrays.asList(Region.ASSHAI, Region.ULTHOS), GOTFaction.ASSHAI, 3739, 2819),
	ASTAPOR(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY), GOTFaction.GHISCAR, 2168, 2138),
	ATAAHUA(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1977, 3177),
	ATRANTA(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 591, 1459),
	AZHYDAAR(Region.MOSSOVY, GOTFaction.MOSSOVY, 4033, 1380),
	AZUU_KAN(Region.MOSSOVY, GOTFaction.MOSSOVY, 4436, 1801),
	BAELISH_KEEP(Region.ARRYN, GOTFaction.ARRYN, 855, 1207),
	BANDALLON(Region.REACH, GOTFaction.REACH, 332, 1885),
	BANEFORT(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 416, 1407),
	BAOJI(Region.YI_TI, GOTFaction.YI_TI, 3499, 2213),
	BARROWTOWN(Region.NORTH, GOTFaction.NORTH, 519, 1065),
	BARTER_BEACH(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY, Region.SOTHORYOS), GOTFaction.GHISCAR, 1981, 2708),
	BASHKARUUCHU(Region.MOSSOVY, GOTFaction.MOSSOVY, 4296, 1559),
	BATARGAS(Arrays.asList(Region.QARTH, Region.QARTH_COLONY, Region.SOTHORYOS), GOTFaction.QARTH, 2635, 3147),
	BAYASABHAD(Region.ESSOS_SEPARATOR, GOTFaction.UNALIGNED, 3098, 2074),
	BHORASH(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY), GOTFaction.GHISCAR, 2068, 1964),
	BIG_LAKE(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2840, 3904),
	BITTERBRIDGE(Region.REACH, GOTFaction.REACH, 652, 1707),
	BLACKCROWN(Region.REACH, GOTFaction.REACH, 318, 1974),
	BLACKHAVEN(Region.STORMLANDS, GOTFaction.STORMLANDS, 725, 1875),
	BLACKMONT(Region.DORNE, GOTFaction.DORNE, 527, 1947),
	BLACKTYDE(Region.IRONBORN, GOTFaction.IRONBORN, 392, 1282),
	BLACK_HEART(Region.STORMLANDS, GOTFaction.STORMLANDS, 870, 1829),
	BLACK_POOL(Region.NORTH, GOTFaction.NORTH, 683, 990),
	BLOODSTONE(Arrays.asList(Region.DORNE, Region.SOUTHERN_FREE_CITIES), GOTFaction.UNALIGNED, 1044, 1924),
	BLOODY_GATE(Region.ARRYN, GOTFaction.ARRYN, 789, 1406),
	BONETOWN(Region.ASSHAI, GOTFaction.ASSHAI, 3989, 1910),
	BRAAVOS(Region.BRAAVOS, GOTFaction.BRAAVOS, 1174, 1251),
	BREAKSTONE_HILL(Region.NORTH, GOTFaction.NORTH, 618, 782),
	BRIARWHITE(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 729, 1577),
	BRIGHTWATER_KEEP(Region.REACH, GOTFaction.REACH, 391, 1887),
	BROAD_ARCH(Region.STORMLANDS, GOTFaction.STORMLANDS, 913, 1675),
	BRONZEGATE(Region.STORMLANDS, GOTFaction.STORMLANDS, 879, 1751),
	BROWNHOLLOW(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 981, 1447),
	CASTAMERE(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 439, 1464),
	CASTERLY_ROCK(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 375, 1570),
	CASTLE_BLACK(Region.NORTH, GOTFaction.NIGHT_WATCH, 753, 631),
	CASTLE_CERWYN(Region.NORTH, GOTFaction.NORTH, 638, 910),
	CASTLE_LYCHESTER(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 668, 1444),
	CATFISH_ROCK(Region.NORTH, GOTFaction.NORTH, 340, 923),
	CENTRAL_FORESTS(Region.ULTHOS, GOTFaction.ULTHOS, 3883, 3680),
	CHANGAN(Region.YI_TI, GOTFaction.YI_TI, 3215, 2347),
	CHROYANE(Region.VOLANTIS, GOTFaction.VOLANTIS, 1479, 1832),
	CIDER_HALL(Region.REACH, GOTFaction.REACH, 557, 1795),
	CLAW_ISLE(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 977, 1483),
	CLEGANES_KEEP(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 412, 1605),
	COLDMOAT(Region.REACH, GOTFaction.REACH, 472, 1723),
	COLDWATER_BURN(Region.ARRYN, GOTFaction.ARRYN, 876, 1258),
	CORNFIELD(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 422, 1653),
	CORPSE_LAKE(Region.IRONBORN, GOTFaction.IRONBORN, 295, 1329),
	CRAG(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 413, 1459),
	CRAKEHALL(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 351, 1666),
	CRASTERS_KEEP(Region.BEYOND_WALL, GOTFaction.WILDLING, 717, 587),
	CROSSROADS_INN(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 735, 1439),
	CROWS_NEST(Region.STORMLANDS, GOTFaction.STORMLANDS, 851, 1820),
	CROW_SPIKE_KEEP(Region.IRONBORN, GOTFaction.IRONBORN, 266, 1304),
	DARKDELL(Region.REACH, GOTFaction.REACH, 521, 1783),
	DARRY(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 748, 1453),
	DEAD_HEAD(Region.STORMLANDS, GOTFaction.STORMLANDS, 770, 1853),
	DEEPDOWN(Region.NORTH, GOTFaction.NORTH, 957, 629),
	DEEPWOOD_MOTTE(Region.NORTH, GOTFaction.NORTH, 501, 806),
	DEEP_DEN(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 505, 1563),
	DEEP_LAKE(Region.NORTH, GOTFaction.NIGHT_WATCH, 733, 630),
	DINGGUO(Region.YI_TI, GOTFaction.YI_TI, 3425, 1856),
	DOQUU(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1235, 2938),
	DOWNDELVING(Region.IRONBORN, GOTFaction.IRONBORN, 268, 1321),
	DRACONYS(Region.VALYRIA, GOTFaction.UNALIGNED, 1707, 2331),
	DRAGONSTONE(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.DRAGONSTONE, 942, 1545),
	DRAGON_PLACE(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2801, 4222),
	DREADFORT(Region.NORTH, GOTFaction.NORTH, 814, 871),
	DRIFTMARK(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.DRAGONSTONE, 909, 1557),
	DRIFTWOOD_HALL(Region.NORTH, GOTFaction.NORTH, 913, 627),
	DRUMM_CASTLE(Region.IRONBORN, GOTFaction.IRONBORN, 317, 1319),
	DUNSTONBURY(Region.REACH, GOTFaction.REACH, 527, 1806),
	DUSKENDALE(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 832, 1577),
	DYRE_DEN(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 932, 1481),
	EASTWATCH(Region.NORTH, GOTFaction.NIGHT_WATCH, 828, 627),
	EAST_BAY(Region.ULTHOS, GOTFaction.ULTHOS, 4045, 3479),
	EAST_COAST(Region.ULTHOS, GOTFaction.ULTHOS, 4764, 2878),
	EBONHEAD(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1166, 2930),
	EIJIANG(Region.YI_TI, GOTFaction.YI_TI, 3119, 2150),
	ELYRIA(Arrays.asList(Region.GHISCAR, Region.VALYRIA), GOTFaction.GHISCAR, 1904, 2080),
	EURON(Region.OCEAN, GOTFaction.UNALIGNED, 3065, 3065, true),
	EVENFALL_HALL(Region.STORMLANDS, GOTFaction.STORMLANDS, 967, 1760),
	FAIRCASTLE(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 361, 1502),
	FAIRMARKET(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 633, 1378),
	FAROS(Region.YI_TI, GOTFaction.YI_TI, 2832, 2454),
	FAWNTOWN(Region.STORMLANDS, GOTFaction.STORMLANDS, 678, 1804),
	FEASTFIRES(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 311, 1573),
	FELWOOD(Region.STORMLANDS, GOTFaction.STORMLANDS, 832, 1725),
	FIST(Region.BEYOND_WALL, GOTFaction.WILDLING, 661, 538),
	FIVE_FORTS_1(Region.YI_TI, GOTFaction.YI_TI, 3778, 1920),
	FIVE_FORTS_2(Region.YI_TI, GOTFaction.YI_TI, 3786, 1931),
	FIVE_FORTS_3(Region.YI_TI, GOTFaction.YI_TI, 3800, 1941),
	FIVE_FORTS_4(Region.YI_TI, GOTFaction.YI_TI, 3817, 1943),
	FIVE_FORTS_5(Region.YI_TI, GOTFaction.YI_TI, 3832, 1939),
	FLINTS_FINGER(Arrays.asList(Region.NORTH, Region.IRONBORN), GOTFaction.NORTH, 402, 1173),
	FOURTEEN_FLAMES(Region.VALYRIA, GOTFaction.UNALIGNED, 1714, 2284),
	FU_NING(Region.YI_TI, GOTFaction.YI_TI, 3642, 2265),
	GALLOWSGREY(Region.STORMLANDS, GOTFaction.STORMLANDS, 929, 1726),
	GARNETGROVE(Region.REACH, GOTFaction.REACH, 354, 2013),
	GATE_OF_THE_MOON(Region.ARRYN, GOTFaction.ARRYN, 825, 1376),
	GHASTON_GREY(Region.DORNE, GOTFaction.DORNE, 766, 1946),
	GHOST_HILL(Region.DORNE, GOTFaction.DORNE, 945, 1988),
	GHOYAN_DROHE(Region.PENTOS, GOTFaction.PENTOS, 1320, 1602),
	GHOZAI(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY), GOTFaction.GHISCAR, 2018, 2146),
	GODSGRACE(Region.DORNE, GOTFaction.DORNE, 845, 2044),
	GOGOSSOS(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY, Region.SOTHORYOS), GOTFaction.GHISCAR, 2032, 2803),
	GOLDENGROVE(Region.REACH, GOTFaction.REACH, 493, 1717),
	GOLDENHILL(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 453, 1446),
	GOLDEN_HEAD(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1355, 2870),
	GOLDEN_TOOTH(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 493, 1491),
	GOLDGRASS(Region.NORTH, GOTFaction.NORTH, 531, 1067),
	GOROSH(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY, Region.SOTHORYOS), GOTFaction.GHISCAR, 2520, 2698),
	GRANDVIEW(Region.STORMLANDS, GOTFaction.STORMLANDS, 794, 1733),
	GRASSY_VALE(Region.REACH, GOTFaction.REACH, 672, 1751),
	GREENFIELD(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 487, 1642),
	GREENGUARD(Region.NORTH, GOTFaction.NIGHT_WATCH, 822, 628),
	GREENSHIELD(Region.REACH, GOTFaction.REACH, 402, 1800),
	GREENSTONE(Region.STORMLANDS, GOTFaction.STORMLANDS, 960, 1886),
	GREYGUARD(Region.NORTH, GOTFaction.NIGHT_WATCH, 683, 641),
	GREYIRON_CASTLE(Region.IRONBORN, GOTFaction.IRONBORN, 372, 1316),
	GREYWATER_WATCH(Arrays.asList(Region.RIVERLANDS, Region.NORTH), GOTFaction.NORTH, 629, 1212),
	GREY_GALLOWS(Arrays.asList(Region.DORNE, Region.SOUTHERN_FREE_CITIES), GOTFaction.UNALIGNED, 1085, 1951),
	GREY_GARDEN(Region.IRONBORN, GOTFaction.IRONBORN, 427, 1307),
	GREY_GLEN(Region.ARRYN, GOTFaction.ARRYN, 924, 1382),
	GRIFFINS_ROOST(Region.STORMLANDS, GOTFaction.STORMLANDS, 864, 1799),
	GRIMSTON(Region.REACH, GOTFaction.REACH, 354, 1796),
	GULLTOWN(Region.ARRYN, GOTFaction.ARRYN, 970, 1399),
	HAMMERHAL(Region.REACH, GOTFaction.REACH, 579, 1650),
	HAMMERHORN(Region.IRONBORN, GOTFaction.IRONBORN, 284, 1336),
	HARDHOME(Region.BEYOND_WALL, GOTFaction.WILDLING, 863, 541),
	HARLAW_HALL(Region.IRONBORN, GOTFaction.IRONBORN, 444, 1328),
	HARRENHAL(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 715, 1488),
	HARRIDAN_HILL(Region.IRONBORN, GOTFaction.IRONBORN, 444, 1344),
	HARROWAY(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 721, 1444),
	HARVEST_HALL(Region.STORMLANDS, GOTFaction.STORMLANDS, 643, 1853),
	HAUAURU(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1781, 3209),
	HAYFORD(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 761, 1618),
	HAYSTACK_HALL(Region.STORMLANDS, GOTFaction.STORMLANDS, 886, 1725),
	HEARTS_HOME(Region.ARRYN, GOTFaction.ARRYN, 843, 1315),
	HELLHOLT(Region.DORNE, GOTFaction.DORNE, 654, 2052),
	HESH(Region.LHAZAR, GOTFaction.LHAZAR, 2413, 1959),
	HIGHGARDEN(Region.REACH, GOTFaction.REACH, 495, 1816),
	HIGHPOINT(Region.NORTH, GOTFaction.NORTH, 645, 791),
	HIGHTOWER_LITEHOUSE(Region.REACH, GOTFaction.REACH, 389, 1944),
	HIGH_HERMITAGE(Region.DORNE, GOTFaction.DORNE, 512, 1982),
	HIGH_TIDE(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.DRAGONSTONE, 926, 1554),
	HOARE_CASTLE(Region.IRONBORN, GOTFaction.IRONBORN, 358, 1313),
	HOARE_KEEP(Region.IRONBORN, GOTFaction.IRONBORN, 301, 1292),
	HOARFROST_HILL(Region.NORTH, GOTFaction.NIGHT_WATCH, 703, 634),
	HOGG_HALL(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 772, 1522),
	HOJDBAATAR(Region.JOGOS, GOTFaction.JOGOS, 3655, 1654),
	HOLLARD_CASTLE(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 842, 1565),
	HOLLOW_HILL(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 634, 1545),
	HOLYHALL(Region.REACH, GOTFaction.REACH, 493, 1759),
	HONEYHOLT(Region.REACH, GOTFaction.REACH, 369, 1919),
	HORNOTH(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 1940, 1504),
	HORNVALE(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 491, 1547),
	HORNWOOD(Region.NORTH, GOTFaction.NORTH, 792, 955),
	HORN_HILL(Region.REACH, GOTFaction.REACH, 464, 1894),
	HUIJI(Region.YI_TI, GOTFaction.YI_TI, 3443, 2459),
	HULL(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.DRAGONSTONE, 914, 1548),
	IBBISH(Arrays.asList(Region.DOTHRAKI, Region.IBBEN_COLONY, Region.IBBEN), GOTFaction.IBBEN, 2803, 1244),
	IB_NOR(Arrays.asList(Region.IBBEN_COLONY, Region.IBBEN), GOTFaction.IBBEN, 2803, 897),
	IB_SAR(Arrays.asList(Region.IBBEN_COLONY, Region.IBBEN), GOTFaction.IBBEN, 2918, 1161),
	ICEMARK(Region.NORTH, GOTFaction.NIGHT_WATCH, 713, 632),
	IRONOAKS(Region.ARRYN, GOTFaction.ARRYN, 881, 1369),
	IRONRATH(Region.NORTH, GOTFaction.NORTH, 607, 810),
	IRON_HOLT(Region.IRONBORN, GOTFaction.IRONBORN, 355, 1359),
	ISLE_OF_FACES(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 711, 1509),
	ISLE_OF_WHIPS(Region.YI_TI, GOTFaction.YI_TI, 3102, 2435),
	IVY_HALL(Region.REACH, GOTFaction.REACH, 603, 1679),
	JIANMEN(Region.YI_TI, GOTFaction.YI_TI, 3299, 1860),
	JINQI(Region.YI_TI, GOTFaction.YI_TI, 3611, 2377),
	KADAR(Region.MOSSOVY, GOTFaction.MOSSOVY, 4644, 1272),
	KANDUU_BET(Region.MOSSOVY, GOTFaction.MOSSOVY, 4878, 1607),
	KARHOLD(Region.NORTH, GOTFaction.NORTH, 933, 806),
	KARIMAGAR(Arrays.asList(Region.QARTH, Region.QARTH_COLONY, Region.SOTHORYOS), GOTFaction.QARTH, 2681, 3454),
	KAYAKAYANAYA(Arrays.asList(Region.ESSOS_SEPARATOR, Region.JOGOS), GOTFaction.UNALIGNED, 3069, 1603),
	KAYCE(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 321, 1552),
	KINGSGRAVE(Region.DORNE, GOTFaction.DORNE, 607, 1935),
	KINGSHOUSE(Region.NORTH, GOTFaction.NORTH, 977, 667),
	KINGS_LANDING(Arrays.asList(Region.KINGS_LANDING, Region.CROWNLANDS, Region.PENTOS), GOTFaction.CROWNLANDS, 771, 1631),
	KOHURU(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1576, 3981),
	KOJ(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1100, 2596),
	KORKUNUCHTUU(Region.MOSSOVY, GOTFaction.MOSSOVY, 4527, 1122),
	KOSRAK(Region.LHAZAR, GOTFaction.LHAZAR, 2557, 1949),
	KRAZAAJ_HAS(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 2490, 1846),
	KUJRUK(Region.MOSSOVY, GOTFaction.MOSSOVY, 4269, 1205),
	KUURULGAN(Region.MOSSOVY, GOTFaction.MOSSOVY, 4829, 1199),
	KYTH(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 1911, 1457),
	K_DATH(Region.MOSSOVY, GOTFaction.MOSSOVY, 3908, 1794),
	LANNISPORT(Arrays.asList(Region.WESTERLANDS, Region.IRONBORN), GOTFaction.WESTERLANDS, 375, 1578),
	LAST_HEARTH(Region.NORTH, GOTFaction.NORTH, 776, 738),
	LAST_LAMENT(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1112, 2494),
	LEMONWOOD(Region.DORNE, GOTFaction.DORNE, 929, 2069),
	LENG_MA(Region.YI_TI, GOTFaction.YI_TI, 3545, 2543),
	LENG_YI(Region.YI_TI, GOTFaction.YI_TI, 3551, 2418),
	LESSER_MORAQ(Region.YI_TI, GOTFaction.YI_TI, 2783, 2600),
	LHAZOSH(Region.LHAZAR, GOTFaction.LHAZAR, 2469, 2029),
	LITTLE_VALYRIA(Region.VOLANTIS, GOTFaction.VOLANTIS, 1679, 2041),
	LIZARD_HEAD(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1422, 2811),
	LIZHAO(Region.YI_TI, GOTFaction.YI_TI, 3670, 2144),
	LONELY_LIGHT(Region.IRONBORN, GOTFaction.IRONBORN, 41, 1330),
	LONGBOW_HALL(Region.ARRYN, GOTFaction.ARRYN, 932, 1307),
	LONGTABLE(Region.REACH, GOTFaction.REACH, 583, 1768),
	LORATH(Region.LORATH, GOTFaction.LORATH, 1344, 1300),
	LORDSPORT(Arrays.asList(Region.IRONBORN, Region.NORTH, Region.RIVERLANDS, Region.WESTERLANDS), GOTFaction.IRONBORN, 356, 1371),
	LORD_HEWETTS_TOWN(Region.REACH, GOTFaction.REACH, 374, 1782),
	LOTUS_POINT(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1123, 2558),
	LUNGMEN(Region.YI_TI, GOTFaction.YI_TI, 3718, 1897),
	LYS(Region.SOUTHERN_FREE_CITIES, GOTFaction.LYS, 1202, 2052),
	MAIDENPOOL(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 813, 1501),
	MANJIN(Region.YI_TI, GOTFaction.YI_TI, 3446, 2192),
	MANTARYS(Arrays.asList(Region.VOLANTIS, Region.VALYRIA), GOTFaction.VOLANTIS, 1872, 2010),
	MANTICORE_ISLES(Region.YI_TI, GOTFaction.YI_TI, 3502, 2778),
	MARAHAI(Region.YI_TI, GOTFaction.YI_TI, 3272, 2704),
	MARSHES(Region.ULTHOS, GOTFaction.ULTHOS, 4578, 3758),
	MATAHAU(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1794, 3310),
	MATAO(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1663, 3561),
	MAUNGA(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2308, 3700),
	MEEREEN(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY), GOTFaction.GHISCAR, 2256, 1951),
	MHYSA_FAER(Region.VALYRIA, GOTFaction.UNALIGNED, 1761, 2472),
	MISTWOOD(Region.STORMLANDS, GOTFaction.STORMLANDS, 922, 1862),
	MOAT_KAILIN(Region.NORTH, GOTFaction.NORTH, 647, 1109),
	MOLETOWN(Region.NORTH, GOTFaction.NIGHT_WATCH, 765, 645),
	MORMONTS_KEEP(Region.NORTH, GOTFaction.NORTH, 491, 727),
	MORNE(Region.STORMLANDS, GOTFaction.STORMLANDS, 991, 1767),
	MOROSH(Arrays.asList(Region.LORATH, Region.DOTHRAKI), GOTFaction.LORATH, 1912, 1278),
	MYR(Region.SOUTHERN_FREE_CITIES, GOTFaction.MYR, 1262, 1842),
	MYRE_CASTLE(Region.IRONBORN, GOTFaction.IRONBORN, 433, 1359),
	NAATH(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY, Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1718, 2795),
	NAGGAS_HILL(Region.IRONBORN, GOTFaction.IRONBORN, 307, 1316),
	NEFER(Region.MOSSOVY, GOTFaction.MOSSOVY, 3680, 1583),
	NEW_BARREL(Region.REACH, GOTFaction.REACH, 623, 1754),
	NEW_GHIS(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY), GOTFaction.GHISCAR, 2228, 2430),
	NEW_IBBISH(Arrays.asList(Region.DOTHRAKI, Region.IBBEN_COLONY, Region.IBBEN), GOTFaction.IBBEN, 2692, 1181),
	NGAHERE(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1579, 3830),
	NGARARA(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1707, 3452),
	NIGHTFORT(Region.NORTH, GOTFaction.NIGHT_WATCH, 723, 630),
	NIGHTSONG(Region.STORMLANDS, GOTFaction.STORMLANDS, 588, 1875),
	NIGHT_KING(Region.BEYOND_WALL, GOTFaction.WHITE_WALKER, 613, 314, true),
	NINESTARS(Region.ARRYN, GOTFaction.ARRYN, 921, 1358),
	NORTH_FORESTS(Region.ULTHOS, GOTFaction.ULTHOS, 4121, 3090),
	NORVOS(Region.NORVOS, GOTFaction.NORVOS, 1423, 1492),
	NYMDUU_OOZ(Region.MOSSOVY, GOTFaction.MOSSOVY, 5012, 1485),
	NY_SAR(Region.NORVOS, GOTFaction.NORVOS, 1433, 1650),
	OAKENSHIELD(Region.NORTH, GOTFaction.NIGHT_WATCH, 763, 632),
	OLDCASTLE(Region.NORTH, GOTFaction.NORTH, 757, 1138),
	OLDSTONES(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 615, 1358),
	OLDTOWN(Region.REACH, GOTFaction.REACH, 393, 1948),
	OLD_ANCHOR(Region.ARRYN, GOTFaction.ARRYN, 949, 1349),
	OLD_GHIS(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY), GOTFaction.GHISCAR, 2194, 2296),
	OLD_OAK(Region.REACH, GOTFaction.REACH, 375, 1756),
	OMBORU(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1257, 2699),
	ORKWOOD(Region.IRONBORN, GOTFaction.IRONBORN, 379, 1299),
	OROS(Region.VALYRIA, GOTFaction.UNALIGNED, 1842, 2250),
	OXCROSS(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 401, 1554),
	PARCHMENTS(Region.STORMLANDS, GOTFaction.STORMLANDS, 946, 1702),
	PEARL_PALACE(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1101, 2609),
	PEBBLE(Region.ARRYN, GOTFaction.ARRYN, 884, 1200),
	PEBBLETON(Region.IRONBORN, GOTFaction.IRONBORN, 331, 1342),
	PENNYTREE(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 606, 1418),
	PENTOS(Arrays.asList(Region.KINGS_LANDING, Region.PENTOS), GOTFaction.PENTOS, 1180, 1625),
	PEREKI(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1698, 3406),
	PINGBEI(Region.YI_TI, GOTFaction.YI_TI, 3763, 1911),
	PINGJIANG(Region.YI_TI, GOTFaction.YI_TI, 3527, 1863),
	PINKMAIDEN_CASTLE(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 553, 1526),
	PINNU(Region.YI_TI, GOTFaction.YI_TI, 3476, 1858),
	PLANKY_TOWN(Region.DORNE, GOTFaction.DORNE, 916, 2055),
	PLUMWOOD(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 508, 1438),
	PODDINGFIELD(Region.STORMLANDS, GOTFaction.STORMLANDS, 676, 1844),
	PORT_MORAQ(Region.YI_TI, GOTFaction.YI_TI, 3003, 2711),
	PORT_OF_IBBEN(Arrays.asList(Region.IBBEN_COLONY, Region.IBBEN), GOTFaction.IBBEN, 2725, 1028),
	PORT_YHOS(Arrays.asList(Region.QARTH, Region.QARTH_COLONY), GOTFaction.QARTH, 2557, 2282),
	PYKE(Region.IRONBORN, GOTFaction.IRONBORN, 354, 1381),
	QARKASH(Arrays.asList(Region.QARTH, Region.QARTH_COLONY), GOTFaction.QARTH, 2716, 2258),
	QARTH(Arrays.asList(Region.QARTH, Region.QARTH_COLONY), GOTFaction.QARTH, 2874, 2294),
	QOHOR(Region.QOHOR, GOTFaction.QOHOR, 1646, 1617),
	QUEENSCROWN(Region.NORTH, GOTFaction.NIGHT_WATCH, 739, 676),
	QUEENSGATE(Region.NORTH, GOTFaction.NIGHT_WATCH, 743, 630),
	RAENYS(Region.OCEAN, GOTFaction.UNALIGNED, 15, 3064),
	RAIN_HOUSE(Region.STORMLANDS, GOTFaction.STORMLANDS, 982, 1821),
	RAMSEY_TOWER(Region.NORTH, GOTFaction.NORTH, 809, 937),
	RAMSGATE(Region.NORTH, GOTFaction.NORTH, 857, 1037),
	RATHYLAR(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 1888, 1568),
	RAUMATI(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2250, 3257),
	RAVENTREE_HALL(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 605, 1400),
	REDFORT(Region.ARRYN, GOTFaction.ARRYN, 873, 1414),
	RED_FLOWER_VALE(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1310, 2786),
	RED_FORESTS(Region.ULTHOS, GOTFaction.ULTHOS, 4182, 3935),
	RED_HAVEN(Region.IRONBORN, GOTFaction.IRONBORN, 339, 1378),
	RED_LAKE(Region.REACH, GOTFaction.REACH, 433, 1711),
	RHYOS(Region.VALYRIA, GOTFaction.UNALIGNED, 1737, 2182),
	RILLWATER_CROSSING(Region.NORTH, GOTFaction.NORTH, 411, 984),
	RIMEGATE(Region.NORTH, GOTFaction.NIGHT_WATCH, 793, 633),
	RING(Region.REACH, GOTFaction.REACH, 664, 1665),
	RIVERRUN(Arrays.asList(Region.WESTERLANDS, Region.RIVERLANDS), GOTFaction.RIVERLANDS, 586, 1435),
	RIVERSPRING(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 568, 1579),
	ROOKS_REST(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 863, 1532),
	ROSBY(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 804, 1605),
	RUNESTONE(Region.ARRYN, GOTFaction.ARRYN, 980, 1373),
	RYAMSPORT(Region.REACH, GOTFaction.REACH, 307, 2066),
	RYSWELLS_CASTLE(Region.NORTH, GOTFaction.NORTH, 426, 1043),
	SAATH(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 1857, 1338),
	SABLE_HALL(Region.NORTH, GOTFaction.NIGHT_WATCH, 783, 633),
	SALTCLIFFE(Region.IRONBORN, GOTFaction.IRONBORN, 285, 1370),
	SALTPANS(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 796, 1472),
	SALT_SHORE(Region.DORNE, GOTFaction.DORNE, 816, 2083),
	SAMYRIANA(Region.ESSOS_SEPARATOR, GOTFaction.UNALIGNED, 3026, 1850),
	SANDSTONE(Region.DORNE, GOTFaction.DORNE, 574, 2044),
	SARHOY(Region.VOLANTIS, GOTFaction.VOLANTIS, 1567, 2091),
	SARSFIELD(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 443, 1524),
	SAR_MELL(Region.VOLANTIS, GOTFaction.VOLANTIS, 1546, 2042),
	SATHAR(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 2270, 1625),
	SEAGARD(Arrays.asList(Region.RIVERLANDS, Region.IRONBORN, Region.NORTH), GOTFaction.RIVERLANDS, 574, 1322),
	SEALSKIN_POINT(Region.IRONBORN, GOTFaction.IRONBORN, 288, 1291),
	SEAWORTH_CASTLE(Region.STORMLANDS, GOTFaction.STORMLANDS, 906, 1831),
	SELHORYS(Region.VOLANTIS, GOTFaction.VOLANTIS, 1496, 1938),
	SENTINEL_STAND(Region.NORTH, GOTFaction.NIGHT_WATCH, 673, 645),
	SEVENSTREAMS(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 629, 1335),
	SHADOW_TOWER(Region.NORTH, GOTFaction.NIGHT_WATCH, 664, 649),
	SHANDYSTONE(Region.DORNE, GOTFaction.DORNE, 939, 2044),
	SHARP_POINT(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.DRAGONSTONE, 939, 1596),
	SHATTERSTONE(Region.IRONBORN, GOTFaction.IRONBORN, 317, 1307),
	SHYLUUN_UUSU(Region.MOSSOVY, GOTFaction.MOSSOVY, 4453, 1425),
	SILVERHILL(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 487, 1605),
	SISTERTON(Region.ARRYN, GOTFaction.ARRYN, 789, 1176),
	SI_QO(Region.YI_TI, GOTFaction.YI_TI, 3269, 2266),
	SKANE(Region.NORTH, GOTFaction.NORTH, 910, 586),
	SKIRLING_PASS(Region.BEYOND_WALL, GOTFaction.WILDLING, 578, 543),
	SKYREACH(Region.DORNE, GOTFaction.DORNE, 616, 1960),
	SMITHYTON(Region.REACH, GOTFaction.REACH, 708, 1700),
	SNAKEWOOD(Region.ARRYN, GOTFaction.ARRYN, 873, 1294),
	SOUTHSHIELD(Region.REACH, GOTFaction.REACH, 377, 1805),
	SOUTH_POINT(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SOTHORYOS, 2204, 4132),
	SOUTH_TAIGA(Region.ULTHOS, GOTFaction.ULTHOS, 4503, 4289),
	SOUTH_ULTHOS(Region.ULTHOS, GOTFaction.ULTHOS, 4701, 3951),
	SPARR_CASTLE(Region.IRONBORN, GOTFaction.IRONBORN, 306, 1351),
	SPICETOWN(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.DRAGONSTONE, 897, 1556),
	SPIDER(Region.HIDDEN, GOTFaction.HOSTILE, 4473, 4244, true),
	SPOTTSWOOD(Region.DORNE, GOTFaction.DORNE, 965, 1995),
	STANDFAST(Region.REACH, GOTFaction.REACH, 477, 1701),
	STARFALL(Region.DORNE, GOTFaction.DORNE, 503, 1999),
	STARFISH_HARBOR(Region.REACH, GOTFaction.REACH, 291, 2054),
	STARPIKE(Region.REACH, GOTFaction.REACH, 509, 1871),
	STOKEWORTH(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 809, 1586),
	STONEDANCE(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.DRAGONSTONE, 946, 1621),
	STONEDOOR(Region.NORTH, GOTFaction.NIGHT_WATCH, 693, 637),
	STONEHELM(Region.STORMLANDS, GOTFaction.STORMLANDS, 809, 1858),
	STONEHOUSE(Region.IRONBORN, GOTFaction.IRONBORN, 324, 1313),
	STONETREE_CASTLE(Region.IRONBORN, GOTFaction.IRONBORN, 399, 1341),
	STONEY_SEPT(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 649, 1564),
	STONE_HEDGE(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 629, 1428),
	STORMS_END(Region.STORMLANDS, GOTFaction.STORMLANDS, 897, 1784),
	STRONGSONG(Region.ARRYN, GOTFaction.ARRYN, 776, 1314),
	STYGAI(Region.ASSHAI, GOTFaction.ASSHAI, 3829, 2729),
	SUMMERHALL(Region.STORMLANDS, GOTFaction.STORMLANDS, 781, 1795),
	SUNDERLY(Region.IRONBORN, GOTFaction.IRONBORN, 307, 1371),
	SUNHOUSE(Region.REACH, GOTFaction.REACH, 417, 2060),
	SUNSPEAR(Region.DORNE, GOTFaction.DORNE, 977, 2049),
	SUUDAN_KORKUU(Region.MOSSOVY, GOTFaction.MOSSOVY, 4525, 1731),
	SUU_NYM(Region.MOSSOVY, GOTFaction.MOSSOVY, 4513, 1519),
	SWEETPORT_SOUND(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.DRAGONSTONE, 924, 1618),
	SWEET_LOTUS_VALE(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1144, 2871),
	TAKUTAI(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1907, 3235),
	TALL_TREES_TOWN(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1159, 2586),
	TARBECK_HALL(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 414, 1476),
	TASHTOO(Arrays.asList(Region.MOSSOVY, Region.IBBEN, Region.IBBEN_COLONY), GOTFaction.MOSSOVY, 3763, 1450),
	TAURANGA(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY, Region.SOTHORYOS), GOTFaction.SUMMER_ISLANDS, 1626, 3695),
	TAWNEY_CASTLE(Region.IRONBORN, GOTFaction.IRONBORN, 387, 1312),
	TELYRIA(Region.VALYRIA, GOTFaction.UNALIGNED, 1877, 2168),
	TEN_TOWERS(Region.IRONBORN, GOTFaction.IRONBORN, 435, 1317),
	TERIMAN(Arrays.asList(Region.QARTH, Region.QARTH_COLONY, Region.SOTHORYOS), GOTFaction.QARTH, 3058, 3654),
	THE_EYRIE(Region.ARRYN, GOTFaction.ARRYN, 830, 1354),
	THE_LONG_BARROW(Region.NORTH, GOTFaction.NIGHT_WATCH, 803, 632),
	THE_PAPS(Region.ARRYN, GOTFaction.ARRYN, 962, 1190),
	THE_TOR(Region.DORNE, GOTFaction.DORNE, 813, 1980),
	THE_TORCHES(Region.NORTH, GOTFaction.NIGHT_WATCH, 813, 630),
	THREE_EYED_RAVEN_CAVE(Region.BEYOND_WALL, GOTFaction.WILDLING, 670, 489),
	THREE_TOWERS(Region.REACH, GOTFaction.REACH, 351, 1998),
	TIQUI(Region.YI_TI, GOTFaction.YI_TI, 3308, 2112),
	TOLOS(Arrays.asList(Region.GHISCAR, Region.VALYRIA), GOTFaction.GHISCAR, 1953, 2072),
	TORRHENS_SQUARE(Region.NORTH, GOTFaction.NORTH, 532, 970),
	TORTURERS_DEEP(Arrays.asList(Region.DORNE, Region.SOUTHERN_FREE_CITIES), GOTFaction.UNALIGNED, 1082, 1990),
	TOWER_OF_GLIMMERING(Region.IRONBORN, GOTFaction.IRONBORN, 413, 1329),
	TOWER_OF_JOY(Region.STORMLANDS, GOTFaction.STORMLANDS, 604, 1909),
	TRADER_TOWN(Region.YI_TI, GOTFaction.YI_TI, 3379, 1865),
	TUDBURY_HOLL(Region.STORMLANDS, GOTFaction.STORMLANDS, 875, 1915),
	TUMBLETON(Region.REACH, GOTFaction.REACH, 698, 1676),
	TURRANI(Region.YI_TI, GOTFaction.YI_TI, 3539, 2621),
	TWINS_LEFT(Arrays.asList(Region.RIVERLANDS, Region.NORTH), GOTFaction.RIVERLANDS, 600, 1289),
	TWINS_RIGHT(Arrays.asList(Region.RIVERLANDS, Region.NORTH), GOTFaction.RIVERLANDS, 605, 1289),
	TYRIA(Region.VALYRIA, GOTFaction.UNALIGNED, 1819, 2303),
	TYROSH(Region.SOUTHERN_FREE_CITIES, GOTFaction.TYROSH, 1099, 1880),
	ULOS(Arrays.asList(Region.ASSHAI, Region.ULTHOS), GOTFaction.ULTHOS, 4134, 2688),
	UPLANDS(Region.REACH, GOTFaction.REACH, 433, 1944),
	VAES_ATHJIKHARI(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 2228, 1524),
	VAES_DIAF(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 2248, 1776),
	VAES_DOTHRAK(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 2666, 1525),
	VAES_EFE(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 2607, 1770),
	VAES_GORQOYI(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 1920, 1620),
	VAES_GRADDAKH(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 1963, 1314),
	VAES_JINI(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI, Region.ESSOS_SEPARATOR), GOTFaction.UNALIGNED, 2839, 1822),
	VAES_KHADOKH(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 1761, 1595),
	VAES_KHEWO(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 2027, 1635),
	VAES_LEISI(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI, Region.IBBEN), GOTFaction.IBBEN, 2442, 1284),
	VAES_LEQSE(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 2260, 1576),
	VAES_MEJHAH(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 2570, 1826),
	VAES_ORVIK(Arrays.asList(Region.QARTH, Region.QARTH_COLONY), GOTFaction.QARTH, 2574, 2239),
	VAES_QOSAR(Arrays.asList(Region.QARTH, Region.QARTH_COLONY), GOTFaction.QARTH, 2791, 2205),
	VAES_SHIROSI(Arrays.asList(Region.QARTH, Region.QARTH_COLONY), GOTFaction.QARTH, 2638, 2218),
	VAES_TOLORRO(Arrays.asList(Region.QARTH, Region.QARTH_COLONY), GOTFaction.QARTH, 2681, 2158),
	VAHAR(Region.YI_TI, GOTFaction.YI_TI, 2808, 2533),
	VAIBEI(Region.YI_TI, GOTFaction.YI_TI, 3504, 1978),
	VAITH(Region.DORNE, GOTFaction.DORNE, 741, 2044),
	VALYRIAN_ROAD(Region.VOLANTIS, GOTFaction.VOLANTIS, 1619, 1991),
	VALYSAR(Region.VOLANTIS, GOTFaction.VOLANTIS, 1502, 2007),
	VELOS(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY), GOTFaction.GHISCAR, 2028, 2214),
	VICTARION_LANDING(Region.IRONBORN, GOTFaction.IRONBORN, 586, 1114, true),
	VINETOWN(Region.REACH, GOTFaction.REACH, 322, 2084),
	VISENYA(Region.OCEAN, GOTFaction.UNALIGNED, 26, 3063),
	VOJJOR_SAMVI(Arrays.asList(Region.IBBEN_COLONY, Region.DOTHRAKI), GOTFaction.DOTHRAKI, 2155, 1653),
	VOLANTIS(Region.VOLANTIS, GOTFaction.VOLANTIS, 1569, 2056),
	VOLMARK(Region.IRONBORN, GOTFaction.IRONBORN, 423, 1349),
	VOLON_THERYS(Region.VOLANTIS, GOTFaction.VOLANTIS, 1539, 2044),
	VULTURES_ROOST(Region.DORNE, GOTFaction.DORNE, 672, 1888),
	WALANO(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1208, 2609),
	WATER_GARDENS(Region.DORNE, GOTFaction.DORNE, 963, 2054),
	WAYFARERS_REST(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 606, 1493),
	WEEPING_TOWN(Region.STORMLANDS, GOTFaction.STORMLANDS, 904, 1911),
	WESTWATCH(Region.NORTH, GOTFaction.NIGHT_WATCH, 658, 651),
	WHISPERS(Arrays.asList(Region.CROWNLANDS, Region.KINGS_LANDING), GOTFaction.CROWNLANDS, 956, 1506),
	WHITEGROVE(Region.REACH, GOTFaction.REACH, 480, 1860),
	WHITETREE(Region.BEYOND_WALL, GOTFaction.WILDLING, 746, 620),
	WHITEWALLS(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 742, 1491),
	WHITE_HARBOUR(Region.NORTH, GOTFaction.NORTH, 723, 1071),
	WHITE_MOUNTAINS(Region.ULTHOS, GOTFaction.ULTHOS, 3664, 3289),
	WICKENDEN(Region.ARRYN, GOTFaction.ARRYN, 874, 1472),
	WIDOWS_WATCH(Region.NORTH, GOTFaction.NORTH, 963, 1053),
	WINTERFELL(Region.NORTH, GOTFaction.NORTH, 649, 872),
	WITCH_ISLE(Region.ARRYN, GOTFaction.ARRYN, 1029, 1380),
	WOODSWATCH(Region.NORTH, GOTFaction.NIGHT_WATCH, 773, 633),
	WUDE(Region.YI_TI, GOTFaction.YI_TI, 3578, 1869),
	WUSHENG(Region.YI_TI, GOTFaction.YI_TI, 3629, 1877),
	WYL(Region.DORNE, GOTFaction.DORNE, 733, 1900),
	WYNDHALL(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 461, 1417),
	XON(Arrays.asList(Region.SUMMER, Region.SUMMER_COLONY), GOTFaction.SUMMER_ISLANDS, 1218, 2894),
	YEEN(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2196, 2833),
	YIBIN(Region.YI_TI, GOTFaction.YI_TI, 3648, 2086),
	YIN(Region.YI_TI, GOTFaction.YI_TI, 3289, 2463),
	YRONWOOD(Region.DORNE, GOTFaction.DORNE, 693, 1964),
	YUNKAI(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY), GOTFaction.GHISCAR, 2192, 2004),
	YUNNAN(Arrays.asList(Region.ASSHAI, Region.YI_TI), GOTFaction.YI_TI, 3816, 2341),
	ZABHAD(Region.YI_TI, GOTFaction.YI_TI, 3069, 2773),
	ZAMETTAR(Arrays.asList(Region.GHISCAR, Region.GHISCAR_COLONY, Region.SOTHORYOS), GOTFaction.GHISCAR, 2148, 2725),
	ZHENGUO(Region.YI_TI, GOTFaction.YI_TI, 3674, 1886);

	private final List<Region> regions;
	private final double imgX;
	private final double imgY;
	private final int coordX;
	private final int coordZ;

	private GOTFaction faction;
	private boolean isHidden;

	GOTWaypoint(List<Region> regions, GOTFaction f, double x, double y) {
		this(regions, f, x, y, false);
	}

	GOTWaypoint(Region r, GOTFaction f, double x, double y) {
		this(Collections.singletonList(r), f, x, y, false);
	}

	GOTWaypoint(Region r, GOTFaction f, double x, double y, boolean hide) {
		this(Collections.singletonList(r), f, x, y, hide);
	}

	GOTWaypoint(List<Region> r, GOTFaction f, double x, double y, boolean hide) {
		regions = r;
		for (Region region : regions) {
			region.getWaypoints().add(this);
		}
		faction = f;
		imgX = x;
		imgY = y;
		coordX = mapToWorldX(x);
		coordZ = mapToWorldZ(y);
		isHidden = hide;
	}

	public static List<GOTAbstractWaypoint> listAllWaypoints() {
		return new ArrayList<>(Arrays.asList(values()));
	}

	public static int mapToWorldR(double r) {
		return (int) Math.round(r * GOTGenLayerWorld.SCALE);
	}

	public static int mapToWorldX(double x) {
		return (int) Math.round((x - GOTGenLayerWorld.ORIGIN_X + 0.5) * GOTGenLayerWorld.SCALE);
	}

	public static int mapToWorldZ(double z) {
		return (int) Math.round((z - GOTGenLayerWorld.ORIGIN_Z + 0.5) * GOTGenLayerWorld.SCALE);
	}

	public static Region regionForID(int id) {
		for (Region waypointRegion : Region.values()) {
			if (waypointRegion.ordinal() != id) {
				continue;
			}
			return waypointRegion;
		}
		return null;
	}

	public static Region regionForName(String name) {
		for (Region waypointRegion : Region.values()) {
			if (!waypointRegion.name().equals(name)) {
				continue;
			}
			return waypointRegion;
		}
		return null;
	}

	public static GOTWaypoint waypointForName(String name) {
		for (GOTWaypoint wp : values()) {
			if (!wp.getCodeName().equals(name)) {
				continue;
			}
			return wp;
		}
		return null;
	}

	public static int worldToMapR(double r) {
		return (int) Math.round(r / GOTGenLayerWorld.SCALE);
	}

	public static int worldToMapX(double x) {
		return (int) Math.round(x / GOTGenLayerWorld.SCALE - 0.5 + GOTGenLayerWorld.ORIGIN_X);
	}

	public static int worldToMapZ(double z) {
		return (int) Math.round(z / GOTGenLayerWorld.SCALE - 0.5 + GOTGenLayerWorld.ORIGIN_Z);
	}

	@Override
	public String getCodeName() {
		return name();
	}

	@Override
	public String getDisplayName() {
		return StatCollector.translateToLocal("got.wp." + getCodeName());
	}

	public GOTFaction getFaction() {
		return faction;
	}

	public void setFaction(GOTFaction faction) {
		this.faction = faction;
	}

	@Override
	public int getID() {
		return ordinal();
	}

	@Override
	public GOTWaypoint getInstance() {
		return this;
	}

	@Override
	public GOTAbstractWaypoint.WaypointLockState getLockState(EntityPlayer entityplayer) {
		if (hasPlayerUnlocked(entityplayer)) {
			return isUnlockedByConquest(entityplayer) ? GOTAbstractWaypoint.WaypointLockState.STANDARD_UNLOCKED_CONQUEST : GOTAbstractWaypoint.WaypointLockState.STANDARD_UNLOCKED;
		}
		return GOTAbstractWaypoint.WaypointLockState.STANDARD_LOCKED;
	}

	@Override
	public String getLoreText(EntityPlayer entityplayer) {
		return StatCollector.translateToLocal("got.wp." + getCodeName() + ".info");
	}

	public List<Region> getRegions() {
		return regions;
	}

	@Override
	public int getRotation() {
		return 0;
	}

	@Override
	public double getShiftX() {
		return 0;
	}

	@Override
	public double getShiftY() {
		return 0;
	}

	@Override
	public double getImgX() {
		return imgX;
	}

	@Override
	public int getCoordX() {
		return coordX;
	}

	@Override
	public double getImgY() {
		return imgY;
	}

	@Override
	public int getCoordY(World world, int i, int k) {
		return GOT.getTrueTopBlock(world, i, k);
	}

	@Override
	public int getCoordYSaved() {
		return 64;
	}

	@Override
	public int getCoordZ() {
		return coordZ;
	}

	@Override
	public boolean hasPlayerUnlocked(EntityPlayer entityplayer) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		if (pd.isFTRegionUnlocked(regions)) {
			if (isCompatibleAlignment(entityplayer)) {
				return true;
			}
			if (isConquestUnlockable(entityplayer)) {
				return isConquered(entityplayer);
			}
		}
		return false;
	}

	public GOTAbstractWaypoint info(double shiftX, double shiftY) {
		return info(shiftX, shiftY, GOTFixer.Dir.NORTH);
	}

	public GOTAbstractWaypoint info(double shiftX, double shiftY, GOTFixer.Dir direction) {
		return new GOTWaypointInfo(this, shiftX, shiftY, direction.ordinal());
	}

	public boolean isCompatibleAlignment(EntityPlayer entityplayer) {
		if (faction == GOTFaction.UNALIGNED) {
			return true;
		}
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		return pd.getAlignment(faction) >= 0.0f;
	}

	private boolean isConquered(EntityPlayer entityplayer) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		World world = entityplayer.worldObj;
		GOTConquestZone zone = GOTConquestGrid.getZoneByWorldCoords(coordX, coordZ);
		GOTFaction pledgeFac = pd.getPledgeFaction();
		return pledgeFac != null && zone.getConquestStrength(pledgeFac, world) >= 500.0f;
	}

	public boolean isConquestUnlockable(EntityPlayer entityplayer) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		World world = entityplayer.worldObj;
		GOTConquestZone zone = GOTConquestGrid.getZoneByWorldCoords(coordX, coordZ);
		GOTFaction pledgeFac = pd.getPledgeFaction();
		return pledgeFac != null && pledgeFac.isBadRelation(faction) && GOTConquestGrid.getConquestEffectIn(world, zone, pledgeFac) == GOTConquestGrid.ConquestEffective.EFFECTIVE;
	}

	@Override
	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean hidden) {
		isHidden = hidden;
	}

	private boolean isUnlockedByConquest(EntityPlayer entityplayer) {
		return !isCompatibleAlignment(entityplayer) && isConquestUnlockable(entityplayer) && isConquered(entityplayer);
	}

	@SuppressWarnings("InnerClassFieldHidesOuterClassField")
	public enum Region {
		ARRYN,
		ASSHAI,
		BEYOND_WALL,
		BRAAVOS,
		CROWNLANDS,
		DORNE,
		DOTHRAKI,
		ESSOS_SEPARATOR,
		GHISCAR,
		GHISCAR_COLONY,
		HIDDEN,
		IBBEN,
		IBBEN_COLONY,
		IRONBORN,
		JOGOS,
		KINGS_LANDING,
		LHAZAR,
		LORATH,
		MOSSOVY,
		NORTH,
		NORVOS,
		OCEAN,
		PENTOS,
		QARTH,
		QARTH_COLONY,
		QOHOR,
		REACH,
		RIVERLANDS,
		SOTHORYOS,
		SOUTHERN_FREE_CITIES,
		STORMLANDS,
		SUMMER,
		SUMMER_COLONY,
		ULTHOS,
		VALYRIA,
		VOLANTIS,
		WESTERLANDS,
		YI_TI;

		private final Collection<GOTWaypoint> waypoints = new ArrayList<>();

		private Collection<GOTWaypoint> getWaypoints() {
			return waypoints;
		}
	}
}