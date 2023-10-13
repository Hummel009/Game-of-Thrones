package got.common.world.map;

import got.GOT;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import got.common.world.genlayer.GOTGenLayerWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum GOTWaypoint implements GOTAbstractWaypoint {
	ACORN_HALL("AcornHall", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 636, 1468),
	ADAKHAKILEKI("Adakhakileki", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2827, 1857),
	AEGON("Aegon", Region.OCEAN, GOTFaction.UNALIGNED, 20, 3055),
	AK_SHAAR("AkShaar", Region.MOSSOVY, GOTFaction.MOSSOVY, 4664, 1740),
	AMBERLY("Amberly", Region.STORMLANDS, GOTFaction.STORMLANDS, 921, 1810),
	ANBEI("Anbei", Region.YI_TI, GOTFaction.YI_TI, 3257, 1864),
	ANGUO("Anguo", Region.YI_TI, GOTFaction.YI_TI, 3341, 1857),
	ANJIANG("Anjiang", Region.YI_TI, GOTFaction.YI_TI, 3383, 1856),
	ANOGARIA("Anogaria", Region.FREE, GOTFaction.VOLANTIS, 1771, 2014),
	ANTLERS("Antlers", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 789, 1534),
	APPLETON("Appleton", Region.REACH, GOTFaction.REACH, 572, 1743),
	AQUOS_DHAEN("AquosDhaen", Region.VALYRIA, GOTFaction.UNALIGNED, 1906, 2363),
	AR_NOY("ArNoy", Region.FREE, GOTFaction.QOHOR, 1521, 1693),
	ASABHAD("Asabhad", Region.YI_TI, GOTFaction.YI_TI, 3081, 2265),
	ASHEMARK("Ashemark", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 445, 1497),
	ASHFORD("Ashford", Region.REACH, GOTFaction.REACH, 609, 1811),
	ASSHAI("Asshai", Region.ASSHAI, GOTFaction.ASSHAI, 3739, 2819),
	ASTAPOR("Astapor", Region.GHISCAR, GOTFaction.GHISCAR, 2168, 2138),
	ATAAHUA("Ataahua", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1977, 3177),
	ATRANTA("Atranta", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 591, 1459),
	AZHYDAAR("Azhydaar", Region.MOSSOVY, GOTFaction.MOSSOVY, 4033, 1380),
	AZUU_KAN("AzuuKan", Region.MOSSOVY, GOTFaction.MOSSOVY, 4436, 1801),
	BAELISH_KEEP("BaelishKeep", Region.ARRYN, GOTFaction.ARRYN, 855, 1207),
	BANDALLON("Bandallon", Region.REACH, GOTFaction.REACH, 332, 1885),
	BANEFORT("Banefort", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 416, 1407),
	BAOJI("Baoji", Region.YI_TI, GOTFaction.YI_TI, 3499, 2213),
	BARROWTOWN("Barrowtown", Region.NORTH, GOTFaction.NORTH, 519, 1065),
	BARTER_BEACH("BarterBeach", Region.SOTHORYOS, GOTFaction.GHISCAR, 1981, 2708),
	BASHKARUUCHU("Bashkaruuchu", Region.MOSSOVY, GOTFaction.MOSSOVY, 4296, 1559),
	BATARGAS("Batargas", Region.QARTH, GOTFaction.QARTH, 2635, 3147),
	BAYASABHAD("Bayasabhad", Region.JOGOS, GOTFaction.JOGOS, 3098, 2074),
	BHORASH("Bhorash", Region.GHISCAR, GOTFaction.GHISCAR, 2068, 1964),
	BIG_LAKE("BigLake", Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2840, 3904),
	BITTERBRIDGE("Bitterbridge", Region.REACH, GOTFaction.REACH, 652, 1707),
	BLACK_HEART("BlackHeart", Region.STORMLANDS, GOTFaction.STORMLANDS, 870, 1829),
	BLACK_POOL("BlackPool", Region.NORTH, GOTFaction.NORTH, 683, 990),
	BLACKCROWN("Blackcrown", Region.REACH, GOTFaction.REACH, 318, 1974),
	BLACKHAVEN("Blackhaven", Region.STORMLANDS, GOTFaction.STORMLANDS, 725, 1875),
	BLACKMONT("Blackmont", Region.DORNE, GOTFaction.DORNE, 527, 1947),
	BLACKTYDE("Blacktyde", Region.IRONBORN, GOTFaction.IRONBORN, 392, 1282),
	BLOODSTONE("BloodStone", Region.DORNE, GOTFaction.DORNE, 1044, 1924),
	BLOODY_GATE("BloodyGate", Region.ARRYN, GOTFaction.ARRYN, 789, 1406),
	BONETOWN("Bonetown", Region.ASSHAI, GOTFaction.ASSHAI, 3989, 1910),
	BRAAVOS("Braavos", Region.FREE, GOTFaction.BRAAVOS, 1174, 1251),
	BREAKSTONE_HILL("BreakstoneHill", Region.NORTH, GOTFaction.NORTH, 618, 782),
	BRIARWHITE("Briarwhite", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 729, 1577),
	BRIGHTWATER_KEEP("BrightwaterKeep", Region.REACH, GOTFaction.REACH, 391, 1887),
	BROAD_ARCH("BroadArch", Region.STORMLANDS, GOTFaction.STORMLANDS, 913, 1675),
	BRONZEGATE("Bronzegate", Region.STORMLANDS, GOTFaction.STORMLANDS, 879, 1751),
	BROWNHOLLOW("Brownhollow", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 981, 1447),
	CASTAMERE("Castamere", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 439, 1464),
	CASTERLY_ROCK("CasterlyRock", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 375, 1570),
	CASTLE_BLACK("CastleBlack", Region.NORTH, GOTFaction.NIGHT_WATCH, 753, 631),
	CASTLE_LYCHESTER("CastleLychester", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 668, 1444),
	CATFISH_ROCK("CatfishRock", Region.NORTH, GOTFaction.NORTH, 340, 923),
	CENTRAL_FORESTS("CentralForests", Region.ULTHOS, GOTFaction.ULTHOS, 3883, 3680),
	CHANGAN("Changan", Region.YI_TI, GOTFaction.YI_TI, 3215, 2347),
	CHROYANE("Chroyane", Region.FREE, GOTFaction.VOLANTIS, 1479, 1832),
	CIDER_HALL("CiderHall", Region.REACH, GOTFaction.REACH, 557, 1795),
	CLAW_ISLE("ClawIsle", Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 977, 1483),
	CLEGANES_KEEP("CleganesKeep", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 412, 1605),
	COLDMOAT("Coldmoat", Region.REACH, GOTFaction.REACH, 472, 1723),
	COLDWATER_BURN("ColdwaterBurn", Region.ARRYN, GOTFaction.ARRYN, 876, 1258),
	CORNFIELD("Cornfield", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 422, 1653),
	CORPSE_LAKE("CorpseLake", Region.IRONBORN, GOTFaction.IRONBORN, 295, 1329),
	CRAG("Crag", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 413, 1459),
	CRAKEHALL("Crakehall", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 351, 1666),
	CRASTERS_KEEP("CrastersKeep", Region.ICE, GOTFaction.WILDLING, 717, 587),
	CROSSROADS_INN("CrossroadsInn", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 735, 1439),
	CROW_SPIKE_KEEP("CrowSpikeKeep", Region.IRONBORN, GOTFaction.IRONBORN, 266, 1304),
	CROWS_NEST("CrowsNest", Region.STORMLANDS, GOTFaction.STORMLANDS, 851, 1820),
	DARKDELL("DarkDell", Region.REACH, GOTFaction.REACH, 521, 1783),
	DARRY("Darry", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 748, 1453),
	DEAD_HEAD("DeatsHear", Region.STORMLANDS, GOTFaction.STORMLANDS, 770, 1853),
	DEEP_DEN("DeepDen", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 505, 1563),
	DEEP_LAKE("DeepLake", Region.NORTH, GOTFaction.NIGHT_WATCH, 733, 630),
	DEEPDOWN("Deepdown", Region.NORTH, GOTFaction.NORTH, 957, 629),
	DEEPWOOD_MOTTE("DeepwoodMotte", Region.NORTH, GOTFaction.NORTH, 501, 806),
	DINGGUO("Dingguo", Region.YI_TI, GOTFaction.YI_TI, 3425, 1856),
	DOQUU("Doquu", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1235, 2938),
	DOWNDELVING("Downdelving", Region.IRONBORN, GOTFaction.IRONBORN, 268, 1321),
	DRACONYS("Draconys", Region.VALYRIA, GOTFaction.UNALIGNED, 1704, 2333),
	DRAGON_PLACE("DragonPlace", Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2801, 4222),
	DRAGONSTONE("Dragonstone", Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 942, 1545),
	DREADFORT("Dreadfort", Region.NORTH, GOTFaction.NORTH, 814, 871),
	DRIFTMARK("Driftmark", Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 909, 1557),
	DRIFTWOOD_HALL("DriftwoodHall", Region.NORTH, GOTFaction.NORTH, 913, 627),
	DRUMM_CASTLE("DrummCastle", Region.IRONBORN, GOTFaction.IRONBORN, 317, 1319),
	DUNSTONBURY("Dunstonbury", Region.REACH, GOTFaction.REACH, 527, 1806),
	DUSKENDALE("Duskendale", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 832, 1577),
	DYRE_DEN("DyreDen", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 932, 1481),
	EAST_BAY("EastBay", Region.ULTHOS, GOTFaction.ULTHOS, 4045, 3479),
	EAST_COAST("EastCoast", Region.ULTHOS, GOTFaction.ULTHOS, 4764, 2878),
	EAST_WATCH("EastWatch", Region.NORTH, GOTFaction.NIGHT_WATCH, 828, 627),
	EBONHEAD("Ebonhead", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1166, 2930),
	EIJIANG("Eijiang", Region.YI_TI, GOTFaction.YI_TI, 3119, 2150),
	ELYRIA("Elyria", Region.FREE, GOTFaction.VOLANTIS, 1904, 2080),
	EURON("Euron", Region.OCEAN, GOTFaction.UNALIGNED, 3065, 3065, true),
	EVENFALL_HALL("EvenfallHall", Region.STORMLANDS, GOTFaction.STORMLANDS, 967, 1760),
	FAIR_MARKET("FairMarket", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 633, 1378),
	FAIRCASTLE("Faircastle", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 361, 1502),
	FAROS("Faros", Region.YI_TI, GOTFaction.YI_TI, 2832, 2454),
	FAWNTOWN("Fawntown", Region.STORMLANDS, GOTFaction.STORMLANDS, 678, 1804),
	FEASTFIRES("Feastfires", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 311, 1573),
	FELWOOD("Felwood", Region.STORMLANDS, GOTFaction.STORMLANDS, 832, 1725),
	FIST("Fist", Region.ICE, GOTFaction.WILDLING, 661, 538),
	FIVE_FORTS_1("FiveForts1", Region.YI_TI, GOTFaction.YI_TI, 3778, 1920),
	FIVE_FORTS_2("FiveForts2", Region.YI_TI, GOTFaction.YI_TI, 3786, 1931),
	FIVE_FORTS_3("FiveForts3", Region.YI_TI, GOTFaction.YI_TI, 3800, 1941),
	FIVE_FORTS_4("FiveForts4", Region.YI_TI, GOTFaction.YI_TI, 3817, 1943),
	FIVE_FORTS_5("FiveForts5", Region.YI_TI, GOTFaction.YI_TI, 3832, 1939),
	FLINTS_FINGER("FlintsFinger", Region.NORTH, GOTFaction.NORTH, 402, 1173),
	FOURTEEN_FLAMES("FourteenFlames", Region.VALYRIA, GOTFaction.UNALIGNED, 1714, 2287),
	FU_NING("FuNing", Region.YI_TI, GOTFaction.YI_TI, 3642, 2265),
	GALLOWSGREY("Gallowsgrey", Region.STORMLANDS, GOTFaction.STORMLANDS, 929, 1726),
	GARNETGROVE("GarnetGrove", Region.REACH, GOTFaction.REACH, 354, 2013),
	GATE_OF_THE_MOON("GateOfTheMoon", Region.ARRYN, GOTFaction.ARRYN, 825, 1376),
	GHASTON_GREY("GhastonGrey", Region.DORNE, GOTFaction.DORNE, 766, 1946),
	GHOST_HILL("GhostHill", Region.DORNE, GOTFaction.DORNE, 945, 1988),
	GHOYAN_DROHE("GhoyanDrohe", Region.FREE, GOTFaction.PENTOS, 1320, 1602),
	GHOZAI("Ghozai", Region.VALYRIA, GOTFaction.UNALIGNED, 2018, 2146),
	GODSGRACE("Godsgrace", Region.DORNE, GOTFaction.DORNE, 845, 2044),
	GOGOSSOS("Gogossos", Region.SOTHORYOS, GOTFaction.GHISCAR, 2032, 2803),
	GOLDEN_HEAD("GoldenHead", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1355, 2870),
	GOLDEN_TOOTH("GoldenTooth", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 493, 1491),
	GOLDENGROVE("Goldengrove", Region.REACH, GOTFaction.REACH, 493, 1717),
	GOLDENHILL("Goldenhill", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 453, 1446),
	GOLDGRASS("Goldgrass", Region.NORTH, GOTFaction.NORTH, 531, 1067),
	GOROSH("Gorosh", Region.SOTHORYOS, GOTFaction.GHISCAR, 2520, 2698),
	GRANDVIEW("Grandview", Region.STORMLANDS, GOTFaction.STORMLANDS, 794, 1733),
	GRASSY_VALE("GrassyVale", Region.REACH, GOTFaction.REACH, 672, 1751),
	GREENFIELD("Greenfield", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 487, 1642),
	GREENGUARD("Greenguard", Region.NORTH, GOTFaction.NIGHT_WATCH, 822, 628),
	GREENSHIELD("Greenshield", Region.REACH, GOTFaction.REACH, 402, 1800),
	GREENSTONE("Greenstone", Region.STORMLANDS, GOTFaction.STORMLANDS, 960, 1886),
	GREY_GALLOWS("GreyGallows", Region.DORNE, GOTFaction.DORNE, 1085, 1951),
	GREY_GARDEN("GreyGarden", Region.IRONBORN, GOTFaction.IRONBORN, 427, 1307),
	GREY_GLEN("GreyGlen", Region.ARRYN, GOTFaction.ARRYN, 924, 1382),
	GREYGUARD("Greyguard", Region.NORTH, GOTFaction.NIGHT_WATCH, 683, 641),
	GREYIRON_CASTLE("GreyironCastle", Region.IRONBORN, GOTFaction.IRONBORN, 372, 1316),
	GREYWATER_WATCH("GreywaterWatch", Region.NORTH, GOTFaction.NORTH, 629, 1212),
	GRIFFINS_ROOST("GriffinsRoost", Region.STORMLANDS, GOTFaction.STORMLANDS, 864, 1799),
	GRIMSTON("Grimston", Region.REACH, GOTFaction.REACH, 354, 1796),
	GULLTOWN("Gulltown", Region.ARRYN, GOTFaction.ARRYN, 970, 1399),
	HAMMERHAL("Hammerhal", Region.REACH, GOTFaction.REACH, 579, 1650),
	HAMMERHORN("Hammerhorn", Region.IRONBORN, GOTFaction.IRONBORN, 284, 1336),
	HARDHOME("Hardhome", Region.ICE, GOTFaction.WILDLING, 863, 541),
	HARLAW_HALL("HarlawHall", Region.IRONBORN, GOTFaction.IRONBORN, 444, 1328),
	HARRENHAL("Harrenhal", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 715, 1488),
	HARRIDAN_HILL("HarridanHill", Region.IRONBORN, GOTFaction.IRONBORN, 444, 1344),
	HARROWAY("Harroway", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 721, 1444),
	HARVEST_HALL("HarvestHall", Region.STORMLANDS, GOTFaction.STORMLANDS, 643, 1853),
	HAUAURU("Hauauru", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1781, 3209),
	HAYFORD("Hayford", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 761, 1618),
	HAYSTACK_HALL("HaystackHall", Region.STORMLANDS, GOTFaction.STORMLANDS, 886, 1725),
	HEARTS_HOME("HeartsHome", Region.ARRYN, GOTFaction.ARRYN, 843, 1315),
	HELLHOLT("Hellholt", Region.DORNE, GOTFaction.DORNE, 654, 2052),
	HESH("Hesh", Region.LHAZAR, GOTFaction.LHAZAR, 2413, 1959),
	HEWETT_TOWN("HewettTown", Region.REACH, GOTFaction.REACH, 374, 1782),
	HIGH_HERMITAGE("HighHermitage", Region.DORNE, GOTFaction.DORNE, 512, 1982),
	HIGH_TIDE("HighTide", Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 926, 1554),
	HIGHGARDEN("Highgarden", Region.REACH, GOTFaction.REACH, 495, 1816),
	HIGHPOINT("Highpoint", Region.NORTH, GOTFaction.NORTH, 645, 791),
	HIGHTOWER_LITEHOUSE("HightowerLitehouse", Region.REACH, GOTFaction.REACH, 389, 1944),
	HOARE_CASTLE("HoareCastle", Region.IRONBORN, GOTFaction.IRONBORN, 358, 1313),
	HOARE_KEEP("HoareKeep", Region.IRONBORN, GOTFaction.IRONBORN, 301, 1292),
	HOARFROST_HILL("HoarfrostHill", Region.NORTH, GOTFaction.NIGHT_WATCH, 703, 634),
	HOGG_HALL("HoggHall", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 772, 1522),
	HOJDBAATAR("Hojdbaatar", Region.JOGOS, GOTFaction.JOGOS, 3655, 1654),
	HOLLARD_CASTLE("HollardCastle", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 842, 1565),
	HOLLOW_HILL("HollowHill", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 634, 1545),
	HOLYHALL("Holyhall", Region.REACH, GOTFaction.REACH, 493, 1759),
	HONEYHOLT("Honeyholt", Region.REACH, GOTFaction.REACH, 369, 1919),
	HORN_HILL("HornHill", Region.REACH, GOTFaction.REACH, 464, 1894),
	HORNOTH("Hornoth", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1940, 1504),
	HORNVALE("Hornvale", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 491, 1547),
	HORNWOOD("Hornwood", Region.NORTH, GOTFaction.NORTH, 792, 955),
	HUIJI("Huiji", Region.YI_TI, GOTFaction.YI_TI, 3443, 2459),
	HULL("Hull", Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 914, 1548),
	IB_NOR("IbNor", Region.IBBEN, GOTFaction.IBBEN, 2803, 897),
	IB_SAR("IbSar", Region.IBBEN, GOTFaction.IBBEN, 2918, 1161),
	IBBISH("Ibbish", Region.DOTHRAKI, GOTFaction.IBBEN, 2803, 1244),
	ICEMARK("Icemark", Region.NORTH, GOTFaction.NIGHT_WATCH, 713, 632),
	IRON_HOLT("IronHolt", Region.IRONBORN, GOTFaction.IRONBORN, 355, 1359),
	IRONOAKS("IronOak", Region.ARRYN, GOTFaction.ARRYN, 881, 1369),
	IRONRATH("Ironrath", Region.NORTH, GOTFaction.NORTH, 607, 810),
	ISLE_OF_FACES("IsleOfFaces", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 711, 1509),
	ISLE_OF_WHIPS("IsleOfWhips", Region.YI_TI, GOTFaction.YI_TI, 3102, 2435),
	IVY_HALL("IvyHall", Region.REACH, GOTFaction.REACH, 603, 1679),
	JIANMEN("Jianmen", Region.YI_TI, GOTFaction.YI_TI, 3299, 1860),
	JINQI("Jinqi", Region.YI_TI, GOTFaction.YI_TI, 3611, 2377),
	K_DATH("KDath", Region.ASSHAI, GOTFaction.ASSHAI, 3934, 1816),
	KADAR("Kadar", Region.MOSSOVY, GOTFaction.MOSSOVY, 4644, 1272),
	KANDUU_BET("KanduuBet", Region.MOSSOVY, GOTFaction.MOSSOVY, 4878, 1607),
	KARHOLD("Karhold", Region.NORTH, GOTFaction.NORTH, 933, 806),
	KARIMAGAR("Karimagar", Region.QARTH, GOTFaction.QARTH, 2681, 3454),
	KAYAKAYANAYA("Kayakayanaya", Region.JOGOS, GOTFaction.JOGOS, 3069, 1603),
	KAYCE("Kayce", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 321, 1552),
	KINGS_LANDING("KingsLanding", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 771, 1631),
	KINGSGRAVE("Kingsgrave", Region.DORNE, GOTFaction.DORNE, 607, 1935),
	KINGSHOUSE("Kingshouse", Region.NORTH, GOTFaction.NORTH, 977, 667),
	KOHURU("Kohuru", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1576, 3981),
	KOJ("Koj", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1100, 2596),
	KORKUNUCHTUU("Korkunuchtuu", Region.MOSSOVY, GOTFaction.MOSSOVY, 4527, 1122),
	KOSRAK("Kosrak", Region.LHAZAR, GOTFaction.LHAZAR, 2557, 1949),
	KRAZAAJ_HAS("KrazaajHas", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2490, 1846),
	KUJRUK("Kujruk", Region.MOSSOVY, GOTFaction.MOSSOVY, 4269, 1205),
	KUURULGAN("Kuurulgan", Region.MOSSOVY, GOTFaction.MOSSOVY, 4829, 1199),
	KYTH("Kyth", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1911, 1457),
	LANNISPORT("Lannisport", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 375, 1578),
	LAST_HEARTH("LastHearth", Region.NORTH, GOTFaction.NORTH, 776, 738),
	LAST_LAMENT("LastLament", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1112, 2494),
	LEMONWOOD("Lemonwood", Region.DORNE, GOTFaction.DORNE, 929, 2069),
	LENG_MA("LengMa", Region.YI_TI, GOTFaction.YI_TI, 3545, 2543),
	LENG_YI("LengYi", Region.YI_TI, GOTFaction.YI_TI, 3551, 2418),
	LESSER_MORAQ("LesserMoraq", Region.YI_TI, GOTFaction.YI_TI, 2783, 2600),
	LHAZOSH("Lhazosh", Region.LHAZAR, GOTFaction.LHAZAR, 2469, 2029),
	LITTLE_VALYRIA("LittleValyria", Region.FREE, GOTFaction.VOLANTIS, 1679, 2041),
	LIZARD_HEAD("LizardHead", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1422, 2811),
	LIZHAO("Lizhao", Region.YI_TI, GOTFaction.YI_TI, 3670, 2144),
	LONELY_LIGHT("LonelyLight", Region.IRONBORN, GOTFaction.IRONBORN, 41, 1330),
	THE_LONG_BARROW("LongBarrow", Region.NORTH, GOTFaction.NIGHT_WATCH, 803, 632),
	LONGBOW_HALL("LongbowHall", Region.ARRYN, GOTFaction.ARRYN, 932, 1307),
	LONGTABLE("Longtable", Region.REACH, GOTFaction.REACH, 583, 1768),
	LORATH("Lorath", Region.FREE, GOTFaction.LORATH, 1344, 1300),
	LORDSPORT("Lordsport", Region.IRONBORN, GOTFaction.IRONBORN, 356, 1371),
	LOTUS_POINT("LotusPoint", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1123, 2558),
	LUNGMEN("Lungmen", Region.YI_TI, GOTFaction.YI_TI, 3718, 1897),
	LYS("Lys", Region.FREE, GOTFaction.LYS, 1202, 2052),
	MAIDENPOOL("Maidenpool", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 813, 1501),
	MANJIN("Manjin", Region.YI_TI, GOTFaction.YI_TI, 3446, 2192),
	MANTARYS("Mantarys", Region.FREE, GOTFaction.VOLANTIS, 1872, 2010),
	MANTICORE_ISLES("ManticoreIsles", Region.SOTHORYOS, GOTFaction.SOTHORYOS, 3502, 2778),
	MARAHAI("Marahai", Region.YI_TI, GOTFaction.YI_TI, 3272, 2704),
	MARSHES("Marshes", Region.ULTHOS, GOTFaction.ULTHOS, 4578, 3758),
	MATAHAU("Matahau", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1794, 3310),
	MATAO("Matao", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1663, 3561),
	MAUNGA("Maunga", Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2308, 3700),
	MEEREEN("Meereen", Region.GHISCAR, GOTFaction.GHISCAR, 2256, 1951),
	MHYSA_FAER("MhysaFaer", Region.VALYRIA, GOTFaction.UNALIGNED, 1764, 2466),
	MISTWOOD("Mistwood", Region.STORMLANDS, GOTFaction.STORMLANDS, 922, 1862),
	MOAT_KAILIN("MoatKailin", Region.NORTH, GOTFaction.NORTH, 647, 1109),
	MOLETOWN("Moletown", Region.NORTH, GOTFaction.NIGHT_WATCH, 765, 645),
	MORMONTS_KEEP("MormontsKeep", Region.NORTH, GOTFaction.NORTH, 491, 727),
	MORNE("Morne", Region.STORMLANDS, GOTFaction.STORMLANDS, 991, 1767),
	MOROSH("Morosh", Region.DOTHRAKI, GOTFaction.LORATH, 1912, 1278),
	MYR("Myr", Region.FREE, GOTFaction.MYR, 1262, 1842),
	MYRE_CASTLE("MyreCastle", Region.IRONBORN, GOTFaction.IRONBORN, 433, 1359),
	NAATH("Naath", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1718, 2795),
	NAGGA_HILL("NaggaHill", Region.IRONBORN, GOTFaction.IRONBORN, 307, 1316),
	NEFER("Nefer", Region.MOSSOVY, GOTFaction.MOSSOVY, 3680, 1583),
	NEW_BARREL("NewBarrel", Region.REACH, GOTFaction.REACH, 623, 1754),
	NEW_GHIS("NewGhis", Region.GHISCAR, GOTFaction.GHISCAR, 2228, 2430),
	NEW_IBBISH("NewIbbish", Region.DOTHRAKI, GOTFaction.IBBEN, 2692, 1181),
	NGAHERE("Ngahere", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1579, 3830),
	NGARARA("Ngarara", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1707, 3452),
	NIGHT_KING("NightKing", Region.ICE, GOTFaction.WHITE_WALKER, 613, 314, true),
	NIGHTFORT("Nightfort", Region.NORTH, GOTFaction.NIGHT_WATCH, 723, 630),
	NIGHTSONG("Nightsong", Region.STORMLANDS, GOTFaction.STORMLANDS, 588, 1875),
	NINESTARS("Ninestars", Region.ARRYN, GOTFaction.ARRYN, 921, 1358),
	NORTH_FORESTS("NorthForests", Region.ULTHOS, GOTFaction.ULTHOS, 4121, 3090),
	NORVOS("Norvos", Region.FREE, GOTFaction.NORVOS, 1423, 1492),
	NY_SAR("NySar", Region.FREE, GOTFaction.NORVOS, 1433, 1650),
	NYMDUU_OOZ("NymduuOoz", Region.MOSSOVY, GOTFaction.MOSSOVY, 5012, 1485),
	OAKENSHIELD("Oakenshield", Region.NORTH, GOTFaction.NIGHT_WATCH, 763, 632),
	OLD_ANCHOR("OldAnchor", Region.ARRYN, GOTFaction.ARRYN, 949, 1349),
	OLDCASTLE("OldCastle", Region.NORTH, GOTFaction.NORTH, 757, 1138),
	OLD_GHIS("OldGhis", Region.GHISCAR, GOTFaction.GHISCAR, 2194, 2296),
	OLD_OAK("OldOak", Region.REACH, GOTFaction.REACH, 375, 1756),
	OLD_STONES("OldStones", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 615, 1358),
	OLDTOWN("Oldtown", Region.REACH, GOTFaction.REACH, 393, 1948),
	OMBORU("Omboru", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1257, 2699),
	ORKWOOD("Orkwood", Region.IRONBORN, GOTFaction.IRONBORN, 379, 1299),
	OROS("Oros", Region.VALYRIA, GOTFaction.UNALIGNED, 1830, 2262),
	OXCROSS("Oxcross", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 401, 1554),
	PARCHMENTS("Parchments", Region.STORMLANDS, GOTFaction.STORMLANDS, 946, 1702),
	PEARL_PALACE("PearlPalace", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1101, 2609),
	PEBBLE("Pebble", Region.ARRYN, GOTFaction.ARRYN, 884, 1200),
	PEBBLETON("Pebbleton", Region.IRONBORN, GOTFaction.IRONBORN, 331, 1342),
	PENNYTREE("Pennytree", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 606, 1418),
	PENTOS("Pentos", Region.CROWNLANDS, GOTFaction.PENTOS, 1180, 1625),
	PEREKI("Pereki", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1698, 3406),
	PINGBEI("Pingbei", Region.YI_TI, GOTFaction.YI_TI, 3763, 1911),
	PINGJIANG("Pingjiang", Region.YI_TI, GOTFaction.YI_TI, 3527, 1863),
	PINKMAIDEN_CASTLE("PinkmaidenCastle", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 553, 1526),
	PINNU("Pinnu", Region.YI_TI, GOTFaction.YI_TI, 3476, 1858),
	PLANKY_TOWN("PlankyTown", Region.DORNE, GOTFaction.DORNE, 916, 2055),
	PLUMWOOD("Plumwood", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 508, 1438),
	PODDINGFIELD("Poddingfield", Region.STORMLANDS, GOTFaction.STORMLANDS, 676, 1844),
	PORT_MORAQ("PortMoraq", Region.YI_TI, GOTFaction.YI_TI, 3003, 2711),
	PORT_OF_IBBEN("PortOfIbben", Region.IBBEN, GOTFaction.IBBEN, 2725, 1028),
	PORT_YHOS("PortYhos", Region.QARTH, GOTFaction.QARTH, 2557, 2282),
	PYKE("Pyke", Region.IRONBORN, GOTFaction.IRONBORN, 354, 1381),
	QARKASH("Qarkash", Region.QARTH, GOTFaction.QARTH, 2716, 2258),
	QARTH("Qarth", Region.QARTH, GOTFaction.QARTH, 2874, 2294),
	QOHOR("Qohor", Region.FREE, GOTFaction.QOHOR, 1646, 1617),
	QUEENSCROWN("Queenscrown", Region.NORTH, GOTFaction.NIGHT_WATCH, 739, 676),
	QUEENSGATE("Queensgate", Region.NORTH, GOTFaction.NIGHT_WATCH, 743, 630),
	RAENYS("Raenys", Region.OCEAN, GOTFaction.UNALIGNED, 15, 3064),
	RAIN_HOUSE("RainHouse", Region.STORMLANDS, GOTFaction.STORMLANDS, 982, 1821),
	RAMSGATE("RamsGate", Region.NORTH, GOTFaction.NORTH, 857, 1037),
	RAMSEY_TOWER("RamseyTower", Region.NORTH, GOTFaction.NORTH, 809, 937),
	RATHYLAR("Rathylar", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1888, 1568),
	RAUMATI("Raumati", Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2250, 3257),
	RAVENTREE_HALL("RaventreeHall", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 605, 1400),
	RED_FLOWER_VALE("RedFlowerVale", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1310, 2786),
	RED_FORESTS("RedForests", Region.ULTHOS, GOTFaction.ULTHOS, 4182, 3935),
	RED_HAVEN("RedHaven", Region.IRONBORN, GOTFaction.IRONBORN, 339, 1378),
	RED_LAKE("RedLake", Region.REACH, GOTFaction.REACH, 433, 1711),
	REDFORT("Redfort", Region.ARRYN, GOTFaction.ARRYN, 873, 1414),
	RHYOS("Rhyos", Region.VALYRIA, GOTFaction.UNALIGNED, 1737, 2182),
	RILLWATER_CROSSING("RillwaterCrossing", Region.NORTH, GOTFaction.NORTH, 411, 984),
	RIMEGATE("Rimegate", Region.NORTH, GOTFaction.NIGHT_WATCH, 793, 633),
	RING("Ring", Region.REACH, GOTFaction.REACH, 664, 1665),
	RIVERRUN("Riverrun", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 586, 1435),
	RIVERSPRING("Riverspring", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 568, 1579),
	ROOKS_REST("RooksRest", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 863, 1532),
	ROSBY("Rosby", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 804, 1605),
	RUNESTONE("Runestone", Region.ARRYN, GOTFaction.ARRYN, 980, 1373),
	RYAMSPORT("Ryamsport", Region.REACH, GOTFaction.REACH, 307, 2066),
	RYSWELLS_CASTLE("RyswellsCastle", Region.NORTH, GOTFaction.NORTH, 426, 1043),
	SAATH("Saath", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1857, 1338),
	SABLE_HALL("SableHall", Region.NORTH, GOTFaction.NIGHT_WATCH, 783, 633),
	SALTCLIFFE("Saltcliffe", Region.IRONBORN, GOTFaction.IRONBORN, 285, 1370),
	SALTPANS("Saltpans", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 796, 1472),
	SALTSHORE("Saltshore", Region.DORNE, GOTFaction.DORNE, 816, 2083),
	SAMYRIANA("Samyriana", Region.JOGOS, GOTFaction.JOGOS, 3026, 1850),
	SANDSTONE("Sandstone", Region.DORNE, GOTFaction.DORNE, 574, 2044),
	SAR_MELL("SarMell", Region.FREE, GOTFaction.VOLANTIS, 1546, 2042),
	SARHOY("Sarhoy", Region.FREE, GOTFaction.VOLANTIS, 1567, 2091),
	SARSFIELD("Sarsfield", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 443, 1524),
	SATHAR("Sathar", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2270, 1625),
	SEAGARD("Seagard", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 574, 1322),
	SEALSKIN_POINT("SealskinPoint", Region.IRONBORN, GOTFaction.IRONBORN, 288, 1291),
	SEAWORTH_CASTLE("SeaworthCastle", Region.STORMLANDS, GOTFaction.STORMLANDS, 906, 1831),
	SELHORYS("Selhorys", Region.FREE, GOTFaction.VOLANTIS, 1496, 1938),
	SENTINEL_STAND("SentinelStand", Region.NORTH, GOTFaction.NIGHT_WATCH, 673, 645),
	SERVINS_CASTLE("ServinsCastle", Region.NORTH, GOTFaction.NORTH, 638, 910),
	SEVENSTREAMS("Sevenstreams", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 629, 1335),
	SHADOW_TOWER("ShadowTower", Region.NORTH, GOTFaction.NIGHT_WATCH, 664, 649),
	SHANDYSTONE("Shandystone", Region.DORNE, GOTFaction.DORNE, 939, 2044),
	SHARP_POINT("SharpPoint", Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 939, 1596),
	SHATTERSTONE("Shatterstone", Region.IRONBORN, GOTFaction.IRONBORN, 317, 1307),
	SHYLUUN_UUSU("ShyluunUusu", Region.MOSSOVY, GOTFaction.MOSSOVY, 4453, 1425),
	SI_QO("SiQo", Region.YI_TI, GOTFaction.YI_TI, 3269, 2266),
	SILVERHILL("Silverhill", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 487, 1605),
	SISTERTON("Sisterton", Region.ARRYN, GOTFaction.ARRYN, 789, 1176),
	SKANE("Skane", Region.NORTH, GOTFaction.NORTH, 910, 586),
	SKIRLING_PASS("SkirlingPass", Region.ICE, GOTFaction.WILDLING, 578, 543),
	SKY_REACH("SkyReach", Region.DORNE, GOTFaction.DORNE, 616, 1960),
	SMITHYTON("Smithyton", Region.REACH, GOTFaction.REACH, 708, 1700),
	SNAKEWOOD("Snakewood", Region.ARRYN, GOTFaction.ARRYN, 873, 1294),
	SOUTH_POINT("SouthPoint", Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2204, 4132),
	SOUTH_TAIGA("SouthTaiga", Region.ULTHOS, GOTFaction.ULTHOS, 4503, 4289),
	SOUTH_ULTHOS("SouthUlthos", Region.ULTHOS, GOTFaction.ULTHOS, 4701, 3951),
	SOUTHSHIELD("Southshield", Region.REACH, GOTFaction.REACH, 377, 1805),
	SPARR_CASTLE("SparrCastle", Region.IRONBORN, GOTFaction.IRONBORN, 306, 1351),
	SPICETOWN("Spicetown", Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 897, 1556),
	SPIDER("Spider", Region.AMOGUS, GOTFaction.HOSTILE, 4473, 4244, true),
	SPOTTSWOOD("Spottswood", Region.DORNE, GOTFaction.DORNE, 965, 1995),
	STANDFAST("Standfast", Region.REACH, GOTFaction.REACH, 477, 1701),
	STARFALL("Starfall", Region.DORNE, GOTFaction.DORNE, 503, 1999),
	STARFISH_HARBOR("StarfishHarbor", Region.REACH, GOTFaction.REACH, 291, 2054),
	STARPIKE("Starpike", Region.REACH, GOTFaction.REACH, 509, 1871),
	STOKEWORTH("Stokeworth", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 809, 1586),
	STONE_HEDGE("StoneHedge", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 629, 1428),
	STONEDANCE("Stonedance", Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 946, 1621),
	STONEDOOR("Stonedoor", Region.NORTH, GOTFaction.NIGHT_WATCH, 693, 637),
	STONEHELM("Stonehelm", Region.STORMLANDS, GOTFaction.STORMLANDS, 809, 1858),
	STONEHOUSE("Stonehouse", Region.IRONBORN, GOTFaction.IRONBORN, 324, 1313),
	STONETREE_CASTLE("StonetreeCastle", Region.IRONBORN, GOTFaction.IRONBORN, 399, 1341),
	STONEY_SEPT("StoneySept", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 649, 1564),
	STORMS_END("StormsEnd", Region.STORMLANDS, GOTFaction.STORMLANDS, 897, 1784),
	STRONGSONG("Strongsong", Region.ARRYN, GOTFaction.ARRYN, 776, 1314),
	STYGAI("Stygai", Region.ASSHAI, GOTFaction.ASSHAI, 3829, 2729),
	SUMMERHALL("Summerhall", Region.STORMLANDS, GOTFaction.STORMLANDS, 781, 1795),
	SUN_HOUSE("SunHouse", Region.REACH, GOTFaction.REACH, 417, 2060),
	SUNDERLY("Sunderly", Region.IRONBORN, GOTFaction.IRONBORN, 307, 1371),
	SUNSPEAR("Sunspear", Region.DORNE, GOTFaction.DORNE, 977, 2049),
	SUU_NYM("SuuNym", Region.MOSSOVY, GOTFaction.MOSSOVY, 4513, 1519),
	SuudanKorkuu("SuudanKorkuu", Region.MOSSOVY, GOTFaction.MOSSOVY, 4525, 1731),
	SWEET_LOTUS_VALE("SweetLotusVale", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1144, 2871),
	SWEETPORT_SOUND("SweetportSound", Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 924, 1618),
	TAKUTAI("Takutai", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1907, 3235),
	TALL_TREES_TOWN("TallTreesTown", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1159, 2586),
	TARBECK_HALL("TarbeckHall", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 414, 1476),
	TASHTOO("Tashtoo", Region.MOSSOVY, GOTFaction.MOSSOVY, 3763, 1450),
	TAURANGA("Tauranga", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1626, 3695),
	TAWNEY_CASTLE("TawneyCastle", Region.IRONBORN, GOTFaction.IRONBORN, 387, 1312),
	TELYRIA("Telyria", Region.VALYRIA, GOTFaction.UNALIGNED, 1868, 2169),
	TEN_TOWERS("TenTowers", Region.IRONBORN, GOTFaction.IRONBORN, 435, 1317),
	TERIMAN("Teriman", Region.QARTH, GOTFaction.QARTH, 3058, 3654),
	THE_EYRIE("TheEyrie", Region.ARRYN, GOTFaction.ARRYN, 830, 1354),
	THE_PAPS("ThePaps", Region.ARRYN, GOTFaction.ARRYN, 962, 1190),
	THREE_EYED_RAVEN_CAVE("ThreeEyedRavenCave", Region.ICE, GOTFaction.WILDLING, 670, 489),
	THREE_TOWERS("ThreeTowers", Region.REACH, GOTFaction.REACH, 351, 1998),
	TIQUI("Tiqui", Region.YI_TI, GOTFaction.YI_TI, 3308, 2112),
	TOLOS("Tolos", Region.FREE, GOTFaction.VOLANTIS, 1953, 2072),
	TOR("Tor", Region.DORNE, GOTFaction.DORNE, 813, 1980),
	TORCHES("Torches", Region.NORTH, GOTFaction.NIGHT_WATCH, 813, 630),
	TORHENS_SQUARE("TorhensSquare", Region.NORTH, GOTFaction.NORTH, 532, 970),
	TORTURERS_DEEP("TorturersDeep", Region.DORNE, GOTFaction.DORNE, 1082, 1990),
	TOWER_OF_GLIMMERING("TowerOfGlimmering", Region.IRONBORN, GOTFaction.IRONBORN, 413, 1329),
	TOWER_OF_JOY("TowerOfJoy", Region.STORMLANDS, GOTFaction.STORMLANDS, 604, 1909),
	TRADER_TOWN("TraderTown", Region.YI_TI, GOTFaction.YI_TI, 3379, 1865),
	TUDBURY_HOLL("TudburyHoll", Region.STORMLANDS, GOTFaction.STORMLANDS, 875, 1915),
	TUMBLETON("Tumbleton", Region.REACH, GOTFaction.REACH, 698, 1676),
	TURRANI("Turrani", Region.YI_TI, GOTFaction.YI_TI, 3539, 2621),
	TWINS_LEFT("TwinsLeft", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 600, 1289),
	TWINS_RIGHT("TwinsRight", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 605, 1289),
	TYRIA("Tyria", Region.VALYRIA, GOTFaction.UNALIGNED, 1817, 2319),
	TYROSH("Tyrosh", Region.FREE, GOTFaction.TYROSH, 1099, 1880),
	ULOS("Ulos", Region.ASSHAI, GOTFaction.ULTHOS, 4134, 2688),
	UPLANDS("Uplands", Region.REACH, GOTFaction.REACH, 433, 1944),
	VAES_ATHJIKHARI("VaesAthjikhari", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2228, 1524),
	VAES_DIAF("VaesDiaf", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2248, 1776),
	VAES_DOTHRAK("VaesDothrak", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2666, 1525),
	VAES_EFE("VaesEfe", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2607, 1770),
	VAES_GORQOYI("VaesGorqoyi", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1920, 1620),
	VAES_GRADDAKH("VaesGraddakh", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1963, 1314),
	VAES_JINI("VaesJini", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2839, 1822),
	VAES_KHADOKH("VaesKhadokh", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1761, 1595),
	VAES_KHEWO("VaesKhewo", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2027, 1635),
	VAES_LEISI("VaesLeisi", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2442, 1284),
	VAES_LEQSE("VaesLeqse", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2260, 1576),
	VAES_MEJHAH("VaesMejhah", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2570, 1826),
	VAES_ORVIK("VaesOrvik", Region.QARTH, GOTFaction.QARTH, 2574, 2239),
	VAES_QOSAR("VaesQosar", Region.QARTH, GOTFaction.QARTH, 2791, 2205),
	VAES_SHIROSI("VaesShirosi", Region.QARTH, GOTFaction.QARTH, 2638, 2218),
	VAES_TOLORRO("VaesTolorro", Region.QARTH, GOTFaction.QARTH, 2681, 2158),
	VAHAR("Vahar", Region.YI_TI, GOTFaction.YI_TI, 2808, 2533),
	VAIBEI("Vaibei", Region.YI_TI, GOTFaction.YI_TI, 3504, 1978),
	VAITH("Vaith", Region.DORNE, GOTFaction.DORNE, 741, 2044),
	VALYRIAN_ROAD("ValyrianRoad", Region.FREE, GOTFaction.VOLANTIS, 1619, 1991),
	VALYSAR("Valysar", Region.FREE, GOTFaction.VOLANTIS, 1502, 2007),
	VELOS("Velos", Region.VALYRIA, GOTFaction.UNALIGNED, 2028, 2214),
	VICTARION_LANDING("VictarionLanding", Region.IRONBORN, GOTFaction.IRONBORN, 586, 1114, true),
	VINETOWN("Vinetown", Region.REACH, GOTFaction.REACH, 322, 2084),
	VISENYA("Visenya", Region.OCEAN, GOTFaction.UNALIGNED, 26, 3063),
	VOJJOR_SAMVI("VojjorSamvi", Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2155, 1653),
	VOLANTIS("Volantis", Region.FREE, GOTFaction.VOLANTIS, 1569, 2056),
	VOLMARK("Volmark", Region.IRONBORN, GOTFaction.IRONBORN, 423, 1349),
	VOLON_THERYS("VolonTherys", Region.FREE, GOTFaction.VOLANTIS, 1539, 2044),
	VULTURES_ROOST("VulturesRoost", Region.DORNE, GOTFaction.DORNE, 672, 1888),
	WALANO("Walano", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1208, 2609),
	WATER_GARDENS("WaterGardens", Region.DORNE, GOTFaction.DORNE, 963, 2054),
	WAYFARER_REST("WayfarerRest", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 606, 1493),
	WEEPING_TOWN("WeepingTown", Region.STORMLANDS, GOTFaction.STORMLANDS, 904, 1911),
	WEST_WATCH("WestWatch", Region.NORTH, GOTFaction.NIGHT_WATCH, 658, 651),
	WHISPERS("Whispers", Region.CROWNLANDS, GOTFaction.CROWNLANDS, 956, 1506),
	WHITE_HARBOUR("WhiteHarbour", Region.NORTH, GOTFaction.NORTH, 723, 1071),
	WHITE_MOUNTAINS("WhiteMountains", Region.ULTHOS, GOTFaction.ULTHOS, 3664, 3289),
	WHITE_WALLS("WhiteWalls", Region.RIVERLANDS, GOTFaction.RIVERLANDS, 742, 1491),
	WHITE_WOOD("WhiteWood", Region.ICE, GOTFaction.WILDLING, 746, 620),
	WHITEGROVE("Whitegrove", Region.REACH, GOTFaction.REACH, 480, 1860),
	WICKENDEN("Wickenden", Region.ARRYN, GOTFaction.ARRYN, 874, 1472),
	WIDOWS_WATCH("WidowsWatch", Region.NORTH, GOTFaction.NORTH, 963, 1053),
	WINTERFELL("Winterfell", Region.NORTH, GOTFaction.NORTH, 649, 872),
	WITCH_ISLE("WitchIsle", Region.ARRYN, GOTFaction.ARRYN, 1029, 1380),
	WOODSWATCH("Woodswatch", Region.NORTH, GOTFaction.NIGHT_WATCH, 773, 633),
	WUDE("Wude", Region.YI_TI, GOTFaction.YI_TI, 3578, 1869),
	WUSHENG("Wusheng", Region.YI_TI, GOTFaction.YI_TI, 3629, 1877),
	WYL("Wyl", Region.DORNE, GOTFaction.DORNE, 733, 1900),
	WYNDHALL("Wyndhall", Region.WESTERLANDS, GOTFaction.WESTERLANDS, 461, 1417),
	XON("Xon", Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1218, 2894),
	YEEN("Yeen", Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2196, 2833),
	YIBIN("Yibin", Region.YI_TI, GOTFaction.YI_TI, 3648, 2086),
	YIN("Yin", Region.YI_TI, GOTFaction.YI_TI, 3289, 2463),
	YRONWOOD("Yronwood", Region.DORNE, GOTFaction.DORNE, 693, 1964),
	YUNKAI("Yunkai", Region.GHISCAR, GOTFaction.GHISCAR, 2192, 2004),
	YUNNAN("Yunnan", Region.YI_TI, GOTFaction.YI_TI, 3816, 2341),
	ZABHAD("Zabhad", Region.YI_TI, GOTFaction.YI_TI, 3069, 2773),
	ZAMETTAR("Zamettar", Region.GHISCAR, GOTFaction.GHISCAR, 2148, 2725),
	ZHENGUO("Zhenguo", Region.YI_TI, GOTFaction.YI_TI, 3674, 1886);

	public String name;
	public Region region;
	public GOTFaction faction;
	private double imgX;
	private double imgY;
	private int xCoord;
	private int zCoord;
	public boolean isHidden;

	GOTWaypoint(String name, Region r, GOTFaction f, double x, double y) {
		this(name, r, f, x, y, false);
	}

	GOTWaypoint(String name, Region r, GOTFaction f, double x, double y, boolean hide) {
		region = r;
		region.waypoints.add(this);
		faction = f;
		imgX = x;
		imgY = y;
		xCoord = mapToWorldX(x);
		zCoord = mapToWorldZ(y);
		isHidden = hide;
	}

	public static List<GOTAbstractWaypoint> listAllWaypoints() {
		return new ArrayList<>(Arrays.asList(values()));
	}

	public static int mapToWorldR(double r) {
		return (int) Math.round(r * GOTGenLayerWorld.scale);
	}

	public static int mapToWorldX(double x) {
		return (int) Math.round((x - 810.0 + 0.5) * GOTGenLayerWorld.scale);
	}

	public static int mapToWorldZ(double z) {
		return (int) Math.round((z - 730.0 + 0.5) * GOTGenLayerWorld.scale);
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
		return (int) Math.round(r / GOTGenLayerWorld.scale);
	}

	public static int worldToMapX(double x) {
		return (int) Math.round(x / GOTGenLayerWorld.scale - 0.5 + 810.0);
	}

	public static int worldToMapZ(double z) {
		return (int) Math.round(z / GOTGenLayerWorld.scale - 0.5 + 730.0);
	}

	@Override
	public String getCodeName() {
		return name();
	}

	@Override
	public String getDisplayName() {
		return StatCollector.translateToLocal("got.wp." + name);
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

	@Override
	public int getRotation() {
		return 0;
	}

	@Override
	public double getX() {
		return imgX;
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
	public int getXCoord() {
		return xCoord;
	}

	@Override
	public double getY() {
		return imgY;
	}

	@Override
	public int getYCoord(World world, int i, int k) {
		return GOT.getTrueTopBlock(world, i, k);
	}

	@Override
	public int getYCoordSaved() {
		return 64;
	}

	@Override
	public int getZCoord() {
		return zCoord;
	}

	@Override
	public boolean hasPlayerUnlocked(EntityPlayer entityplayer) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		if (pd.isFTRegionUnlocked(region)) {
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

	public boolean isConquered(EntityPlayer entityplayer) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		World world = entityplayer.worldObj;
		GOTConquestZone zone = GOTConquestGrid.getZoneByWorldCoords(xCoord, zCoord);
		GOTFaction pledgeFac = pd.getPledgeFaction();
		return pledgeFac != null && zone.getConquestStrength(pledgeFac, world) >= 500.0f;
	}

	public boolean isConquestUnlockable(EntityPlayer entityplayer) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		World world = entityplayer.worldObj;
		GOTConquestZone zone = GOTConquestGrid.getZoneByWorldCoords(xCoord, zCoord);
		GOTFaction pledgeFac = pd.getPledgeFaction();
		return pledgeFac != null && pledgeFac.isBadRelation(faction) && GOTConquestGrid.getConquestEffectIn(world, zone, pledgeFac) == GOTConquestGrid.ConquestEffective.EFFECTIVE;
	}

	@Override
	public boolean isHidden() {
		return isHidden;
	}

	public boolean isUnlockedByConquest(EntityPlayer entityplayer) {
		return !isCompatibleAlignment(entityplayer) && isConquestUnlockable(entityplayer) && isConquered(entityplayer);
	}

	public enum Region {
		ABOBA, NORTH, ARRYN, ICE, RIVERLANDS, MOSSOVY, DRANGONSTONE, CROWNLANDS, DORNE, DOTHRAKI, IBBEN, IRONBORN, LHAZAR, REACH, OCEAN, STORMLANDS, WESTERLANDS, ASSHAI, GHISCAR, JOGOS, VALYRIA, FREE, QARTH, SOTHORYOS, ULTHOS, YI_TI, SUMMER_ISLANDS, AMOGUS;

		public Collection<GOTWaypoint> waypoints = new ArrayList<>();
	}
}
