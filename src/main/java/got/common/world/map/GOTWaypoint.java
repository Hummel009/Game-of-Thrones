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
import java.util.List;

public enum GOTWaypoint implements GOTAbstractWaypoint {
	Hauauru(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1781, 3209), Matahau(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1794, 3310), Takutai(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1907, 3235), Ataahua(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1977, 3177), Pereki(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1698, 3406), Ngarara(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1707, 3452), Tauranga(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1626, 3695), Matao(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1663, 3561), Ngahere(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1579, 3830), Kohuru(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1576, 3981), Maunga(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2308, 3700), Raumati(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2250, 3257), DragonPlace(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2801, 4222), SouthPoint(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2204, 4132), BigLake(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2840, 3904), NightKing(Region.ICE, GOTFaction.WHITE_WALKER, 613, 314, true), Spider(Region.AMOGUS, GOTFaction.HOSTILE, 4473, 4244, true), AcornHall(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 636, 1468), SkirlingPass(Region.ICE, GOTFaction.WILDLING, 577, 544), Adakhakileki(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2827, 1857), Aegon(Region.OCEAN, GOTFaction.UNALIGNED, 20, 3055), Amberly(Region.STORMLANDS, GOTFaction.STORMLANDS, 921, 1810), Anogaria(Region.FREE, GOTFaction.VOLANTIS, 1771, 2014), Antlers(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 789, 1534), Appleton(Region.REACH, GOTFaction.REACH, 572, 1743), AquosDhaen(Region.VALYRIA, GOTFaction.UNALIGNED, 1906, 2363), ArNoy(Region.FREE, GOTFaction.QOHOR, 1521, 1693), Asabhad(Region.YI_TI, GOTFaction.YI_TI, 3081, 2265), Ashemark(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 445, 1497), Ashford(Region.REACH, GOTFaction.REACH, 609, 1811), Asshai(Region.ASSHAI, GOTFaction.ASSHAI, 3739, 2819), Astapor(Region.GHISCAR, GOTFaction.GHISCAR, 2168, 2138), Atranta(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 591, 1459), BaelishKeep(Region.ARRYN, GOTFaction.ARRYN, 855, 1207), Bandallon(Region.REACH, GOTFaction.REACH, 332, 1885), Banefort(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 416, 1407), Baoji(Region.YI_TI, GOTFaction.YI_TI, 3499, 2213), Barrowtown(Region.NORTH, GOTFaction.NORTH, 519, 1065), BarterBeach(Region.SOTHORYOS, GOTFaction.GHISCAR, 1981, 2708), Bayasabhad(Region.JOGOS, GOTFaction.JOGOS, 3098, 2074), Bhorash(Region.GHISCAR, GOTFaction.GHISCAR, 2068, 1964), Bitterbridge(Region.REACH, GOTFaction.REACH, 652, 1707), BlackHeart(Region.STORMLANDS, GOTFaction.STORMLANDS, 870, 1829), BlackPool(Region.NORTH, GOTFaction.NORTH, 683, 990), Blackcrown(Region.REACH, GOTFaction.REACH, 318, 1974), Blackhaven(Region.STORMLANDS, GOTFaction.STORMLANDS, 725, 1875), Blackmont(Region.DORNE, GOTFaction.DORNE, 527, 1947), Blacktyde(Region.IRONBORN, GOTFaction.IRONBORN, 392, 1282), BloodStone(Region.DORNE, GOTFaction.DORNE, 1044, 1924), BloodyGate(Region.ARRYN, GOTFaction.ARRYN, 789, 1406), Bonetown(Region.ASSHAI, GOTFaction.ASSHAI, 3989, 1910), Braavos(Region.FREE, GOTFaction.BRAAVOS, 1174, 1251), BreakstoneHill(Region.NORTH, GOTFaction.NORTH, 618, 782), Briarwhite(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 729, 1577), BrightwaterKeep(Region.REACH, GOTFaction.REACH, 391, 1887), BroadArch(Region.STORMLANDS, GOTFaction.STORMLANDS, 913, 1675), Bronzegate(Region.STORMLANDS, GOTFaction.STORMLANDS, 879, 1751), Brownhollow(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 981, 1447), Castamere(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 439, 1464), CasterlyRock(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 375, 1570), CastleLychester(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 668, 1444), CatfishRock(Region.NORTH, GOTFaction.NORTH, 340, 923), Chroyane(Region.FREE, GOTFaction.VOLANTIS, 1479, 1832), ShadowTower(Region.NORTH, GOTFaction.NIGHT_WATCH, 664, 649), SentinelStand(Region.NORTH, GOTFaction.NIGHT_WATCH, 673, 645), Greyguard(Region.NORTH, GOTFaction.NIGHT_WATCH, 683, 641), Stonedoor(Region.NORTH, GOTFaction.NIGHT_WATCH, 693, 637), HoarfrostHill(Region.NORTH, GOTFaction.NIGHT_WATCH, 703, 634), Icemark(Region.NORTH, GOTFaction.NIGHT_WATCH, 713, 632), Nightfort(Region.NORTH, GOTFaction.NIGHT_WATCH, 723, 630), DeepLake(Region.NORTH, GOTFaction.NIGHT_WATCH, 733, 630), Queensgate(Region.NORTH, GOTFaction.NIGHT_WATCH, 743, 630), CastleBlack(Region.NORTH, GOTFaction.NIGHT_WATCH, 753, 631), Oakenshield(Region.NORTH, GOTFaction.NIGHT_WATCH, 763, 632), Woodswatch(Region.NORTH, GOTFaction.NIGHT_WATCH, 773, 633), SableHall(Region.NORTH, GOTFaction.NIGHT_WATCH, 783, 633), Rimegate(Region.NORTH, GOTFaction.NIGHT_WATCH, 793, 633), LongBarrow(Region.NORTH, GOTFaction.NIGHT_WATCH, 803, 632), Torches(Region.NORTH, GOTFaction.NIGHT_WATCH, 813, 630), Anbei(Region.YI_TI, GOTFaction.YI_TI, 3257, 1864), Jianmen(Region.YI_TI, GOTFaction.YI_TI, 3299, 1860), Anguo(Region.YI_TI, GOTFaction.YI_TI, 3341, 1857), Anjiang(Region.YI_TI, GOTFaction.YI_TI, 3383, 1856), Dingguo(Region.YI_TI, GOTFaction.YI_TI, 3425, 1856), Pinnu(Region.YI_TI, GOTFaction.YI_TI, 3476, 1858), Pingjiang(Region.YI_TI, GOTFaction.YI_TI, 3527, 1863), Wude(Region.YI_TI, GOTFaction.YI_TI, 3578, 1869), Wusheng(Region.YI_TI, GOTFaction.YI_TI, 3629, 1877), Zhenguo(Region.YI_TI, GOTFaction.YI_TI, 3674, 1886), Lungmen(Region.YI_TI, GOTFaction.YI_TI, 3718, 1897), Pingbei(Region.YI_TI, GOTFaction.YI_TI, 3763, 1911), Greenguard(Region.NORTH, GOTFaction.NIGHT_WATCH, 822, 628), CiderHall(Region.REACH, GOTFaction.REACH, 557, 1795), ClawIsle(Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 977, 1483), CleganesKeep(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 412, 1605), Coldmoat(Region.REACH, GOTFaction.REACH, 472, 1723), ColdwaterBurn(Region.ARRYN, GOTFaction.ARRYN, 876, 1258), Cornfield(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 422, 1653), CorpseLake(Region.IRONBORN, GOTFaction.IRONBORN, 295, 1329), Crag(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 413, 1459), Crakehall(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 351, 1666), CrossroadsInn(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 735, 1439), CrowSpikeKeep(Region.IRONBORN, GOTFaction.IRONBORN, 266, 1304), CrowsNest(Region.STORMLANDS, GOTFaction.STORMLANDS, 851, 1820), DarkDell(Region.REACH, GOTFaction.REACH, 521, 1783), Darry(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 748, 1453), DeatsHear(Region.STORMLANDS, GOTFaction.STORMLANDS, 770, 1853), DeepDen(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 505, 1563), Deepdown(Region.NORTH, GOTFaction.NORTH, 957, 629), DeepwoodMotte(Region.NORTH, GOTFaction.NORTH, 501, 806), Doquu(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1235, 2938), Downdelving(Region.IRONBORN, GOTFaction.IRONBORN, 268, 1321), Draconys(Region.VALYRIA, GOTFaction.UNALIGNED, 1704, 2333), Dragonstone(Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 942, 1545), Dreadfort(Region.NORTH, GOTFaction.NORTH, 814, 871), Driftmark(Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 909, 1557), DriftwoodHall(Region.NORTH, GOTFaction.NORTH, 913, 627), DrummCastle(Region.IRONBORN, GOTFaction.IRONBORN, 317, 1319), Dunstonbury(Region.REACH, GOTFaction.REACH, 527, 1806), Duskendale(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 832, 1577), DyreDen(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 932, 1481), EastWatch(Region.NORTH, GOTFaction.NIGHT_WATCH, 828, 627), Ebonhead(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1166, 2930), Eijiang(Region.YI_TI, GOTFaction.YI_TI, 3119, 2150), Changan(Region.YI_TI, GOTFaction.YI_TI, 3215, 2347), FuNing(Region.YI_TI, GOTFaction.YI_TI, 3642, 2265), Elyria(Region.FREE, GOTFaction.VOLANTIS, 1904, 2080), Euron(Region.OCEAN, GOTFaction.UNALIGNED, 3065, 3065, true), EvenfallHall(Region.STORMLANDS, GOTFaction.STORMLANDS, 967, 1760), FairMarket(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 633, 1378), Faircastle(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 361, 1502), Faros(Region.YI_TI, GOTFaction.YI_TI, 2832, 2454), Fawntown(Region.STORMLANDS, GOTFaction.STORMLANDS, 678, 1804), Feastfires(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 311, 1573), Felwood(Region.STORMLANDS, GOTFaction.STORMLANDS, 832, 1725), Fist(Region.ICE, GOTFaction.WILDLING, 661, 538), FiveForts1(Region.YI_TI, GOTFaction.YI_TI, 3778, 1920), FiveForts2(Region.YI_TI, GOTFaction.YI_TI, 3786, 1931), FiveForts3(Region.YI_TI, GOTFaction.YI_TI, 3800, 1941), FiveForts4(Region.YI_TI, GOTFaction.YI_TI, 3817, 1943), FiveForts5(Region.YI_TI, GOTFaction.YI_TI, 3832, 1939), FlintsFinger(Region.NORTH, GOTFaction.NORTH, 402, 1173), FourteenFlames(Region.VALYRIA, GOTFaction.UNALIGNED, 1714, 2287), Gallowsgrey(Region.STORMLANDS, GOTFaction.STORMLANDS, 929, 1726), GarnetGrove(Region.REACH, GOTFaction.REACH, 354, 2013), GateOfTheMoon(Region.ARRYN, GOTFaction.ARRYN, 825, 1376), GhastonGrey(Region.DORNE, GOTFaction.DORNE, 766, 1946), GhostHill(Region.DORNE, GOTFaction.DORNE, 945, 1988), GhoyanDrohe(Region.FREE, GOTFaction.PENTOS, 1320, 1602), Ghozai(Region.VALYRIA, GOTFaction.UNALIGNED, 2018, 2146), Godsgrace(Region.DORNE, GOTFaction.DORNE, 845, 2044), Gogossos(Region.SOTHORYOS, GOTFaction.GHISCAR, 2032, 2803), GoldenHead(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1355, 2870), GoldenTooth(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 493, 1491), Goldengrove(Region.REACH, GOTFaction.REACH, 493, 1717), Goldenhill(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 453, 1446), Goldgrass(Region.NORTH, GOTFaction.NORTH, 531, 1067), Gorosh(Region.SOTHORYOS, GOTFaction.GHISCAR, 2520, 2698), Grandview(Region.STORMLANDS, GOTFaction.STORMLANDS, 794, 1733), GrassyVale(Region.REACH, GOTFaction.REACH, 672, 1751), Greenfield(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 487, 1642), Greenshield(Region.REACH, GOTFaction.REACH, 402, 1800), Greenstone(Region.STORMLANDS, GOTFaction.STORMLANDS, 960, 1886), GreyGallows(Region.DORNE, GOTFaction.DORNE, 1085, 1951), GreyGarden(Region.IRONBORN, GOTFaction.IRONBORN, 427, 1307), GreyGlen(Region.ARRYN, GOTFaction.ARRYN, 924, 1382), GreyironCastle(Region.IRONBORN, GOTFaction.IRONBORN, 372, 1316), GreywaterWatch(Region.NORTH, GOTFaction.NORTH, 629, 1212),
	GriffinsRoost(Region.STORMLANDS, GOTFaction.STORMLANDS, 864, 1799), Grimston(Region.REACH, GOTFaction.REACH, 354, 1796), Gulltown(Region.ARRYN, GOTFaction.ARRYN, 970, 1399), Hammerhal(Region.REACH, GOTFaction.REACH, 579, 1650), Hammerhorn(Region.IRONBORN, GOTFaction.IRONBORN, 284, 1336), Hardhome(Region.ICE, GOTFaction.WILDLING, 863, 541), HarlawHall(Region.IRONBORN, GOTFaction.IRONBORN, 444, 1328), Harrenhal(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 715, 1488), HarridanHill(Region.IRONBORN, GOTFaction.IRONBORN, 444, 1344), Harroway(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 721, 1444), HarvestHall(Region.STORMLANDS, GOTFaction.STORMLANDS, 643, 1853), Hayford(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 761, 1618), HaystackHall(Region.STORMLANDS, GOTFaction.STORMLANDS, 886, 1725), HeartsHome(Region.ARRYN, GOTFaction.ARRYN, 843, 1315), Hellholt(Region.DORNE, GOTFaction.DORNE, 654, 2052), Hesh(Region.LHAZAR, GOTFaction.LHAZAR, 2413, 1959), HewettTown(Region.REACH, GOTFaction.REACH, 374, 1782), HighHermitage(Region.DORNE, GOTFaction.DORNE, 512, 1982), Teriman(Region.QARTH, GOTFaction.QARTH, 3058, 3654), Batargas(Region.QARTH, GOTFaction.QARTH, 2635, 3147), Karimagar(Region.QARTH, GOTFaction.QARTH, 2681, 3454), HighTide(Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 926, 1554), Highgarden(Region.REACH, GOTFaction.REACH, 495, 1816), Highpoint(Region.NORTH, GOTFaction.NORTH, 645, 791), HightowerLitehouse(Region.REACH, GOTFaction.REACH, 389, 1944), HoareCastle(Region.IRONBORN, GOTFaction.IRONBORN, 358, 1313), HoareKeep(Region.IRONBORN, GOTFaction.IRONBORN, 301, 1292), HoggHall(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 772, 1522), HollardCastle(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 842, 1565), HollowHill(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 634, 1545), Holyhall(Region.REACH, GOTFaction.REACH, 493, 1759), Honeyholt(Region.REACH, GOTFaction.REACH, 369, 1919), HornHill(Region.REACH, GOTFaction.REACH, 464, 1894), Hornoth(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1940, 1504), Hornvale(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 491, 1547), Hornwood(Region.NORTH, GOTFaction.NORTH, 792, 955), Huiji(Region.YI_TI, GOTFaction.YI_TI, 3443, 2459), Hull(Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 914, 1548), IbNor(Region.IBBEN, GOTFaction.IBBEN, 2803, 897), IbSar(Region.IBBEN, GOTFaction.IBBEN, 2918, 1161), Ibbish(Region.DOTHRAKI, GOTFaction.IBBEN, 2803, 1244), IronHolt(Region.IRONBORN, GOTFaction.IRONBORN, 355, 1359), IronOak(Region.ARRYN, GOTFaction.ARRYN, 881, 1369), Ironrath(Region.NORTH, GOTFaction.NORTH, 607, 810), IsleOfFaces(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 711, 1509), IsleOfWhips(Region.YI_TI, GOTFaction.YI_TI, 3102, 2435), IvyHall(Region.REACH, GOTFaction.REACH, 603, 1679), Jinqi(Region.YI_TI, GOTFaction.YI_TI, 3611, 2377), KDath(Region.ASSHAI, GOTFaction.ASSHAI, 3934, 1816), Karhold(Region.NORTH, GOTFaction.NORTH, 933, 806), Kayakayanaya(Region.JOGOS, GOTFaction.JOGOS, 3069, 1603), Kayce(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 321, 1552), KingsLanding(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 771, 1631), Kingsgrave(Region.DORNE, GOTFaction.DORNE, 607, 1935), Kingshouse(Region.NORTH, GOTFaction.NORTH, 977, 667), Hojdbaatar(Region.JOGOS, GOTFaction.JOGOS, 3655, 1654), Koj(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1100, 2596), Kosrak(Region.LHAZAR, GOTFaction.LHAZAR, 2557, 1949), CrastersKeep(Region.ICE, GOTFaction.WILDLING, 717, 587), KrazaajHas(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2490, 1846), Kyth(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1911, 1457), Lannisport(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 375, 1578), LastHearth(Region.NORTH, GOTFaction.NORTH, 776, 738), LastLament(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1112, 2494), Lemonwood(Region.DORNE, GOTFaction.DORNE, 929, 2069), LengMa(Region.YI_TI, GOTFaction.YI_TI, 3545, 2543), LengYi(Region.YI_TI, GOTFaction.YI_TI, 3551, 2418), LesserMoraq(Region.YI_TI, GOTFaction.YI_TI, 2783, 2600), Lhazosh(Region.LHAZAR, GOTFaction.LHAZAR, 2469, 2029), LittleValyria(Region.FREE, GOTFaction.VOLANTIS, 1679, 2041), LizardHead(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1422, 2811), Lizhao(Region.YI_TI, GOTFaction.YI_TI, 3670, 2144), LonelyLight(Region.IRONBORN, GOTFaction.IRONBORN, 41, 1330), LongbowHall(Region.ARRYN, GOTFaction.ARRYN, 932, 1307), Longtable(Region.REACH, GOTFaction.REACH, 583, 1768), Lorath(Region.FREE, GOTFaction.LORATH, 1344, 1300), Lordsport(Region.IRONBORN, GOTFaction.IRONBORN, 356, 1371), LotusPoint(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1123, 2558), Lys(Region.FREE, GOTFaction.LYS, 1202, 2052), Maidenpool(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 813, 1501), Manjin(Region.YI_TI, GOTFaction.YI_TI, 3446, 2192), Mantarys(Region.FREE, GOTFaction.VOLANTIS, 1872, 2010), ManticoreIsles(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 3502, 2778), Marahai(Region.YI_TI, GOTFaction.YI_TI, 3272, 2704), Meereen(Region.GHISCAR, GOTFaction.GHISCAR, 2256, 1951), MhysaFaer(Region.VALYRIA, GOTFaction.UNALIGNED, 1764, 2466), Mistwood(Region.STORMLANDS, GOTFaction.STORMLANDS, 922, 1862), MoatKailin(Region.NORTH, GOTFaction.NORTH, 647, 1109), MormontsKeep(Region.NORTH, GOTFaction.NORTH, 491, 727), Morne(Region.STORMLANDS, GOTFaction.STORMLANDS, 991, 1767), Morosh(Region.DOTHRAKI, GOTFaction.LORATH, 1912, 1278), Moletown(Region.NORTH, GOTFaction.NIGHT_WATCH, 765, 645), Myr(Region.FREE, GOTFaction.MYR, 1262, 1842), MyreCastle(Region.IRONBORN, GOTFaction.IRONBORN, 433, 1359), Naath(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1718, 2795), NaggaHill(Region.IRONBORN, GOTFaction.IRONBORN, 307, 1316), Nefer(Region.MOSSOVY, GOTFaction.MOSSOVY, 3680, 1583), NewBarrel(Region.REACH, GOTFaction.REACH, 623, 1754), NewGhis(Region.GHISCAR, GOTFaction.GHISCAR, 2228, 2430), NewIbbish(Region.DOTHRAKI, GOTFaction.IBBEN, 2692, 1181), Nightsong(Region.STORMLANDS, GOTFaction.STORMLANDS, 588, 1875), Ninestars(Region.ARRYN, GOTFaction.ARRYN, 921, 1358), Norvos(Region.FREE, GOTFaction.NORVOS, 1423, 1492), NySar(Region.FREE, GOTFaction.NORVOS, 1433, 1650), OldAnchor(Region.ARRYN, GOTFaction.ARRYN, 949, 1349), OldCastle(Region.NORTH, GOTFaction.NORTH, 757, 1138), OldGhis(Region.GHISCAR, GOTFaction.GHISCAR, 2194, 2296), OldOak(Region.REACH, GOTFaction.REACH, 375, 1756), OldStones(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 615, 1358), Oldtown(Region.REACH, GOTFaction.REACH, 393, 1948), Omboru(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1257, 2699), Orkwood(Region.IRONBORN, GOTFaction.IRONBORN, 379, 1299), Oros(Region.VALYRIA, GOTFaction.UNALIGNED, 1830, 2262), Oxcross(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 401, 1554), Parchments(Region.STORMLANDS, GOTFaction.STORMLANDS, 946, 1702), PearlPalace(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1101, 2609), Pebble(Region.ARRYN, GOTFaction.ARRYN, 884, 1200), Pebbleton(Region.IRONBORN, GOTFaction.IRONBORN, 331, 1342), Pennytree(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 606, 1418), Pentos(Region.CROWNLANDS, GOTFaction.PENTOS, 1180, 1625), PinkmaidenCastle(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 553, 1526), PlankyTown(Region.DORNE, GOTFaction.DORNE, 916, 2055), Plumwood(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 508, 1438), Poddingfield(Region.STORMLANDS, GOTFaction.STORMLANDS, 676, 1844), PortMoraq(Region.YI_TI, GOTFaction.YI_TI, 3003, 2711), PortOfIbben(Region.IBBEN, GOTFaction.IBBEN, 2725, 1028), PortYhos(Region.QARTH, GOTFaction.QARTH, 2557, 2282), Pyke(Region.IRONBORN, GOTFaction.IRONBORN, 354, 1381), Qarkash(Region.QARTH, GOTFaction.QARTH, 2716, 2258), Qarth(Region.QARTH, GOTFaction.QARTH, 2874, 2294), Qohor(Region.FREE, GOTFaction.QOHOR, 1646, 1617), Queenscrown(Region.NORTH, GOTFaction.NIGHT_WATCH, 739, 676), Raenys(Region.OCEAN, GOTFaction.UNALIGNED, 15, 3064), RainHouse(Region.STORMLANDS, GOTFaction.STORMLANDS, 982, 1821), RamsGate(Region.NORTH, GOTFaction.NORTH, 857, 1037), RamseyTower(Region.NORTH, GOTFaction.NORTH, 809, 937), Rathylar(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1888, 1568), RaventreeHall(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 605, 1400), RedFlowerVale(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1310, 2786), RedHaven(Region.IRONBORN, GOTFaction.IRONBORN, 339, 1378), RedLake(Region.REACH, GOTFaction.REACH, 433, 1711), Redfort(Region.ARRYN, GOTFaction.ARRYN, 873, 1414), Rhyos(Region.VALYRIA, GOTFaction.UNALIGNED, 1737, 2182), RillwaterCrossing(Region.NORTH, GOTFaction.NORTH, 411, 984), Ring(Region.REACH, GOTFaction.REACH, 664, 1665), RisvellsCastle(Region.NORTH, GOTFaction.NORTH, 426, 1043), Riverrun(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 586, 1435), Riverspring(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 568, 1579), RooksRest(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 863, 1532), Rosby(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 804, 1605), Runestone(Region.ARRYN, GOTFaction.ARRYN, 980, 1373), Ryamsport(Region.REACH, GOTFaction.REACH, 307, 2066), Saath(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1857, 1338), Saltcliffe(Region.IRONBORN, GOTFaction.IRONBORN, 285, 1370), Saltpans(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 796, 1472), Saltshore(Region.DORNE, GOTFaction.DORNE, 816, 2083), Samyriana(Region.JOGOS, GOTFaction.JOGOS, 3026, 1850), Sandstone(Region.DORNE, GOTFaction.DORNE, 574, 2044), SarMell(Region.FREE, GOTFaction.VOLANTIS, 1546, 2042), Sarhoy(Region.FREE, GOTFaction.VOLANTIS, 1567, 2091), Sarsfield(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 443, 1524), Sathar(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2270, 1625), Seagard(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 574, 1322), SealskinPoint(Region.IRONBORN, GOTFaction.IRONBORN, 288, 1291), SeaworthCastle(Region.STORMLANDS, GOTFaction.STORMLANDS, 906, 1831), Selhorys(Region.FREE, GOTFaction.VOLANTIS, 1496, 1938), ServinsCastle(Region.NORTH, GOTFaction.NORTH, 638, 910), VictarionLanding(Region.IRONBORN, GOTFaction.IRONBORN, 586, 1114, true), Sevenstreams(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 629, 1335), Shandystone(Region.DORNE, GOTFaction.DORNE, 939, 2044),
	SharpPoint(Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 939, 1596), Shatterstone(Region.IRONBORN, GOTFaction.IRONBORN, 317, 1307), SiQo(Region.YI_TI, GOTFaction.YI_TI, 3269, 2266), Silverhill(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 487, 1605), Sisterton(Region.ARRYN, GOTFaction.ARRYN, 789, 1176), Skane(Region.NORTH, GOTFaction.NORTH, 910, 586), SkyReach(Region.DORNE, GOTFaction.DORNE, 616, 1960), Smithyton(Region.REACH, GOTFaction.REACH, 708, 1700), Snakewood(Region.ARRYN, GOTFaction.ARRYN, 873, 1294), Southshield(Region.REACH, GOTFaction.REACH, 377, 1805), SparrCastle(Region.IRONBORN, GOTFaction.IRONBORN, 306, 1351), Spicetown(Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 897, 1556), Spottswood(Region.DORNE, GOTFaction.DORNE, 965, 1995), Standfast(Region.REACH, GOTFaction.REACH, 477, 1701), Starfall(Region.DORNE, GOTFaction.DORNE, 503, 1999), StarfishHarbor(Region.REACH, GOTFaction.REACH, 291, 2054), Starpike(Region.REACH, GOTFaction.REACH, 509, 1871), Stokeworth(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 809, 1586), StoneHedge(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 629, 1428), Stonedance(Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 946, 1621), Stonehelm(Region.STORMLANDS, GOTFaction.STORMLANDS, 809, 1858), Stonehouse(Region.IRONBORN, GOTFaction.IRONBORN, 324, 1313), StonetreeCastle(Region.IRONBORN, GOTFaction.IRONBORN, 399, 1341), StoneySept(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 649, 1564), StormsEnd(Region.STORMLANDS, GOTFaction.STORMLANDS, 897, 1784), Strongsong(Region.ARRYN, GOTFaction.ARRYN, 776, 1314), Stygai(Region.ASSHAI, GOTFaction.ASSHAI, 3829, 2729), Summerhall(Region.STORMLANDS, GOTFaction.STORMLANDS, 781, 1795), SunHouse(Region.REACH, GOTFaction.REACH, 417, 2060), Sunderly(Region.IRONBORN, GOTFaction.IRONBORN, 307, 1371), Sunspear(Region.DORNE, GOTFaction.DORNE, 977, 2049), SweetLotusVale(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1144, 2871), SweetportSound(Region.DRANGONSTONE, GOTFaction.DRAGONSTONE, 924, 1618), TallTreesTown(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1159, 2586), TarbeckHall(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 414, 1476), TawneyCastle(Region.IRONBORN, GOTFaction.IRONBORN, 387, 1312), Telyria(Region.VALYRIA, GOTFaction.UNALIGNED, 1868, 2169), TenTowers(Region.IRONBORN, GOTFaction.IRONBORN, 435, 1317), TheEyrie(Region.ARRYN, GOTFaction.ARRYN, 830, 1354), ThePaps(Region.ARRYN, GOTFaction.ARRYN, 962, 1190), ThreeEyedRavenCave(Region.ICE, GOTFaction.WILDLING, 670, 489), ThreeTowers(Region.REACH, GOTFaction.REACH, 351, 1998), Tiqui(Region.YI_TI, GOTFaction.YI_TI, 3308, 2112), Tolos(Region.FREE, GOTFaction.VOLANTIS, 1953, 2072), Tor(Region.DORNE, GOTFaction.DORNE, 813, 1980), TorhensSquare(Region.NORTH, GOTFaction.NORTH, 532, 970), TorturersDeep(Region.DORNE, GOTFaction.DORNE, 1082, 1990), TowerOfGlimmering(Region.IRONBORN, GOTFaction.IRONBORN, 413, 1329), TowerOfJoy(Region.STORMLANDS, GOTFaction.STORMLANDS, 604, 1909), TraderTown(Region.YI_TI, GOTFaction.YI_TI, 3379, 1865), TudburyHoll(Region.STORMLANDS, GOTFaction.STORMLANDS, 875, 1915), Tumbleton(Region.REACH, GOTFaction.REACH, 698, 1676), Turrani(Region.YI_TI, GOTFaction.YI_TI, 3539, 2621), TwinsLeft(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 600, 1289), TwinsRight(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 605, 1289), Tyria(Region.VALYRIA, GOTFaction.UNALIGNED, 1817, 2319), Tyrosh(Region.FREE, GOTFaction.TYROSH, 1099, 1880), Ulos(Region.ASSHAI, GOTFaction.ULTHOS, 4134, 2688), EastCoast(Region.ULTHOS, GOTFaction.ULTHOS, 4764, 2878), NorthForests(Region.ULTHOS, GOTFaction.ULTHOS, 4121, 3090), WhiteMountains(Region.ULTHOS, GOTFaction.ULTHOS, 3664, 3289), CentralForests(Region.ULTHOS, GOTFaction.ULTHOS, 3883, 3680), Marshes(Region.ULTHOS, GOTFaction.ULTHOS, 4578, 3758), RedForests(Region.ULTHOS, GOTFaction.ULTHOS, 4182, 3935), SouthUlthos(Region.ULTHOS, GOTFaction.ULTHOS, 4701, 3951), SouthTaiga(Region.ULTHOS, GOTFaction.ULTHOS, 4503, 4289), EastBay(Region.ULTHOS, GOTFaction.ULTHOS, 4045, 3479), Uplands(Region.REACH, GOTFaction.REACH, 433, 1944), VaesAthjikhari(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2228, 1524), VaesDiaf(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2248, 1776), VaesDothrak(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2666, 1525), VaesEfe(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2607, 1770), VaesGorqoyi(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1920, 1620), VaesGraddakh(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1963, 1314), VaesJini(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2839, 1822), VaesKhadokh(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 1761, 1595), VaesKhewo(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2027, 1635), VaesLeisi(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2442, 1284), VaesLeqse(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2260, 1576), VaesMejhah(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2570, 1826), VaesOrvik(Region.QARTH, GOTFaction.QARTH, 2574, 2239), VaesQosar(Region.QARTH, GOTFaction.QARTH, 2791, 2205), VaesShirosi(Region.QARTH, GOTFaction.QARTH, 2638, 2218), VaesTolorro(Region.QARTH, GOTFaction.QARTH, 2681, 2158), Vahar(Region.YI_TI, GOTFaction.YI_TI, 2808, 2533), Vaibei(Region.YI_TI, GOTFaction.YI_TI, 3504, 1978), Vaith(Region.DORNE, GOTFaction.DORNE, 741, 2044), ValyrianRoad(Region.FREE, GOTFaction.VOLANTIS, 1619, 1991), Valysar(Region.FREE, GOTFaction.VOLANTIS, 1502, 2007), Velos(Region.VALYRIA, GOTFaction.UNALIGNED, 2028, 2214), Vinetown(Region.REACH, GOTFaction.REACH, 322, 2084), Visenya(Region.OCEAN, GOTFaction.UNALIGNED, 26, 3063), VojjorSamvi(Region.DOTHRAKI, GOTFaction.DOTHRAKI, 2155, 1653), Volantis(Region.FREE, GOTFaction.VOLANTIS, 1569, 2056), Volmark(Region.IRONBORN, GOTFaction.IRONBORN, 423, 1349), VolonTherys(Region.FREE, GOTFaction.VOLANTIS, 1539, 2044), VulturesRoost(Region.DORNE, GOTFaction.DORNE, 672, 1888), Walano(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1208, 2609), WaterGardens(Region.DORNE, GOTFaction.DORNE, 963, 2054), WayfarerRest(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 606, 1493), WeepingTown(Region.STORMLANDS, GOTFaction.STORMLANDS, 904, 1911), WestWatch(Region.NORTH, GOTFaction.NIGHT_WATCH, 658, 651), Whispers(Region.CROWNLANDS, GOTFaction.CROWNLANDS, 956, 1506), WhiteHarbour(Region.NORTH, GOTFaction.NORTH, 723, 1071), WhiteWalls(Region.RIVERLANDS, GOTFaction.RIVERLANDS, 742, 1491), WhiteWood(Region.ICE, GOTFaction.WILDLING, 746, 620), Whitegrove(Region.REACH, GOTFaction.REACH, 480, 1860), Wickenden(Region.ARRYN, GOTFaction.ARRYN, 874, 1472), WidowsWatch(Region.NORTH, GOTFaction.NORTH, 963, 1053), Winterfell(Region.NORTH, GOTFaction.NORTH, 649, 872), WitchIsle(Region.ARRYN, GOTFaction.ARRYN, 1029, 1380), Wyl(Region.DORNE, GOTFaction.DORNE, 733, 1900), Wyndhall(Region.WESTERLANDS, GOTFaction.WESTERLANDS, 461, 1417), Xon(Region.SUMMER_ISLANDS, GOTFaction.SUMMER_ISLANDS, 1218, 2894), Yeen(Region.SOTHORYOS, GOTFaction.SOTHORYOS, 2196, 2833), Yibin(Region.YI_TI, GOTFaction.YI_TI, 3648, 2086), Yin(Region.YI_TI, GOTFaction.YI_TI, 3289, 2463), Yronwood(Region.DORNE, GOTFaction.DORNE, 693, 1964), Yunkai(Region.GHISCAR, GOTFaction.GHISCAR, 2192, 2004), Yunnan(Region.YI_TI, GOTFaction.YI_TI, 3816, 2341), Zabhad(Region.YI_TI, GOTFaction.YI_TI, 3069, 2773), Zamettar(Region.GHISCAR, GOTFaction.GHISCAR, 2148, 2725), Tashtoo(Region.MOSSOVY, GOTFaction.MOSSOVY, 3763, 1450), Bashkaruuchu(Region.MOSSOVY, GOTFaction.MOSSOVY, 4296, 1559), Kadar(Region.MOSSOVY, GOTFaction.MOSSOVY, 4644, 1272), AzuuKan(Region.MOSSOVY, GOTFaction.MOSSOVY, 4436, 1801), Kujruk(Region.MOSSOVY, GOTFaction.MOSSOVY, 4269, 1205), Korkunuchtuu(Region.MOSSOVY, GOTFaction.MOSSOVY, 4527, 1122), NymduuOoz(Region.MOSSOVY, GOTFaction.MOSSOVY, 5012, 1485), Azhydaar(Region.MOSSOVY, GOTFaction.MOSSOVY, 4033, 1380), AkShaar(Region.MOSSOVY, GOTFaction.MOSSOVY, 4664, 1740), SuuNym(Region.MOSSOVY, GOTFaction.MOSSOVY, 4513, 1519), Kuurulgan(Region.MOSSOVY, GOTFaction.MOSSOVY, 4829, 1199), KanduuBet(Region.MOSSOVY, GOTFaction.MOSSOVY, 4878, 1607), SuudanKorkuu(Region.MOSSOVY, GOTFaction.MOSSOVY, 4525, 1731), ShyluunUusu(Region.MOSSOVY, GOTFaction.MOSSOVY, 4453, 1425);

	public Region region;
	public GOTFaction faction;
	public double imgX;
	public double imgY;
	public int xCoord;
	public int zCoord;
	public boolean isHidden;

	GOTWaypoint(Region r, GOTFaction f, double x, double y) {
		this(r, f, x, y, false);
	}

	GOTWaypoint(Region r, GOTFaction f, double x, double y, boolean hide) {
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
		return StatCollector.translateToLocal("got.wp." + getCodeName());
	}

	@Override
	public int getID() {
		return ordinal();
	}

	@Override
	public GOTWaypoint getItself() {
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
		return info(shiftX, shiftY, 0);
	}

	public GOTAbstractWaypoint info(double shiftX, double shiftY, int rotation) {
		double shiftedX = imgX + shiftX;
		double shiftedY = imgY + shiftY;
		return new GOTWaypointInfo(this, shiftedX, shiftedY, rotation);
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

		public List<GOTWaypoint> waypoints = new ArrayList<>();
	}
}
