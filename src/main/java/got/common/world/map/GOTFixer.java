package got.common.world.map;

import java.util.*;

import got.common.entity.essos.legendary.GOTEntityMissandei;
import got.common.entity.essos.legendary.captain.*;
import got.common.entity.essos.legendary.quest.*;
import got.common.entity.essos.legendary.trader.*;
import got.common.entity.essos.legendary.warrior.*;
import got.common.entity.other.GOTEntityHummel009;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldier;
import got.common.entity.westeros.legendary.captain.*;
import got.common.entity.westeros.legendary.deco.*;
import got.common.entity.westeros.legendary.quest.*;
import got.common.entity.westeros.legendary.reborn.*;
import got.common.entity.westeros.legendary.trader.*;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.*;
import got.common.world.structure.essos.asshai.GOTStructureAsshaiCity;
import got.common.world.structure.essos.braavos.GOTStructureBraavosCity;
import got.common.world.structure.essos.dothraki.GOTStructureDothrakiVillage;
import got.common.world.structure.essos.ghiscar.GOTStructureGhiscarCity;
import got.common.world.structure.essos.ibben.GOTStructureIbbenVillage;
import got.common.world.structure.essos.jogos.GOTStructureJogosVillage;
import got.common.world.structure.essos.lhazar.GOTStructureLhazarVillage;
import got.common.world.structure.essos.lorath.GOTStructureLorathCity;
import got.common.world.structure.essos.lys.GOTStructureLysCity;
import got.common.world.structure.essos.mossovy.GOTStructureMossovyVillage;
import got.common.world.structure.essos.myr.GOTStructureMyrCity;
import got.common.world.structure.essos.norvos.GOTStructureNorvosCity;
import got.common.world.structure.essos.pentos.GOTStructurePentosCity;
import got.common.world.structure.essos.qarth.GOTStructureQarthCity;
import got.common.world.structure.essos.qohor.GOTStructureQohorCity;
import got.common.world.structure.essos.tyrosh.GOTStructureTyroshCity;
import got.common.world.structure.essos.volantis.GOTStructureVolantisCity;
import got.common.world.structure.essos.yiti.GOTStructureYiTiCity;
import got.common.world.structure.other.*;
import got.common.world.structure.sothoryos.summer.GOTStructureSummerVillage;
import got.common.world.structure.westeros.arryn.GOTStructureArrynCity;
import got.common.world.structure.westeros.crownlands.GOTStructureCrownlandsCity;
import got.common.world.structure.westeros.dorne.GOTStructureDorneCity;
import got.common.world.structure.westeros.dragonstone.GOTStructureDragonstoneCity;
import got.common.world.structure.westeros.gift.GOTStructureGiftVillage;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornCity;
import got.common.world.structure.westeros.north.GOTStructureNorthCity;
import got.common.world.structure.westeros.north.hillmen.GOTStructureNorthHillmanVillage;
import got.common.world.structure.westeros.reach.GOTStructureReachCity;
import got.common.world.structure.westeros.riverlands.GOTStructureRiverlandsCity;
import got.common.world.structure.westeros.stormlands.GOTStructureStormlandsCity;
import got.common.world.structure.westeros.westerlands.GOTStructureWesterlandsCity;
import got.common.world.structure.westeros.wildling.GOTStructureWildlingVillage;
import net.minecraft.world.World;

public class GOTFixer {
	public static Map<GOTWaypoint, GOTStructureBase> structures = new EnumMap<>(GOTWaypoint.class);
	public static GOTVillageGen[] f = new GOTVillageGen[66];

	public static void addSpecialLocations(World world, Random random, int i, int k) {
		GOTWaypoint[] forts = { GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2, GOTWaypoint.FiveForts3, GOTWaypoint.FiveForts4, GOTWaypoint.FiveForts5 };
		for (GOTWaypoint wp : forts) {
			new GOTFiveFortsWall(false, wp).generate(world, random, i, 0, k, 0);
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.WhiteWood) || GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Winterfell)) {
			((GOTWorldGenPartyTrees) GOTTreeType.WEIRWOOD.create(false, random)).disableRestrictions().generate(world, random, i + 50, world.getTopSolidOrLiquidBlock(i + 50, k), k);
		}
	}

	public static void affixWaypointLocations(GOTBiome biome) {
		f[0] = new GOTStructureArrynCity(biome, 0.0f).setIsCastle();
		f[0].affix(GOTWaypoint.BloodyGate.info(0, -1, 0), GOTWaypoint.TheEyrie, GOTWaypoint.ColdwaterBurn, GOTWaypoint.GreyGlen, GOTWaypoint.HeartsHome, GOTWaypoint.IronOak, GOTWaypoint.LongbowHall, GOTWaypoint.Ninestars, GOTWaypoint.OldAnchor, GOTWaypoint.Pebble, GOTWaypoint.Redfort, GOTWaypoint.Runestone, GOTWaypoint.Snakewood, GOTWaypoint.Strongsong, GOTWaypoint.ThePaps, GOTWaypoint.Wickenden, GOTWaypoint.WitchIsle, GOTWaypoint.GateOfTheMoon.info(0, 1, 0));
		f[1] = new GOTStructureArrynCity(biome, 0.0f).setIsTown();
		f[1].affix(GOTWaypoint.Gulltown, GOTWaypoint.Sisterton);
		f[2] = new GOTStructureAsshaiCity(biome, 0.0f);
		f[2].affix(GOTWaypoint.Asshai.info(0, 0, 2));
		f[3] = new GOTStructureBraavosCity(biome, 0.0f).setIsTown();
		f[3].affix(GOTWaypoint.Braavos.info(0, -1, 2));
		f[4] = new GOTStructureGiftVillage(biome, 0.0f).setIsCastleBlack();
		f[4].affix(GOTWaypoint.CastleBlack.info(0, 0, 0));
		f[5] = new GOTStructureWildlingVillage(biome, 0.0f).setIsCraster();
		f[5].affix(GOTWaypoint.CrastersKeep);
		f[6] = new GOTStructureRiverlandsCity(biome, 0.0f).setIsCrossroads();
		f[6].affix(GOTWaypoint.CrossroadsInn);
		f[7] = new GOTStructureCrownlandsCity(biome, 0.0f).setIsKingsLanding();
		f[7].affix(GOTWaypoint.KingsLanding.info(1, 0, 1));
		f[8] = new GOTStructureCrownlandsCity(biome, 0.0f).setIsCastle();
		f[8].affix(GOTWaypoint.Antlers, GOTWaypoint.Brownhollow, GOTWaypoint.DyreDen, GOTWaypoint.Stokeworth, GOTWaypoint.Hayford.info(-1, 0, 0), GOTWaypoint.RooksRest.info(0, -1, 0), GOTWaypoint.Rosby.info(0, -1, 0));
		f[9] = new GOTStructureCrownlandsCity(biome, 0.0f).setIsTown();
		f[9].affix(GOTWaypoint.Duskendale.info(-2, 0, 3));
		f[10] = new GOTStructureCrownlandsCity(biome, 0.0f);
		f[10].affix(GOTWaypoint.Briarwhite);
		f[11] = new GOTStructureDorneCity(biome, 0.0f).setIsCastle();
		f[11].affix(GOTWaypoint.Blackmont, GOTWaypoint.GhostHill, GOTWaypoint.Godsgrace, GOTWaypoint.Hellholt, GOTWaypoint.HighHermitage, GOTWaypoint.Tor, GOTWaypoint.Vaith, GOTWaypoint.WaterGardens, GOTWaypoint.Spottswood, GOTWaypoint.Lemonwood, GOTWaypoint.Saltshore, GOTWaypoint.Sandstone, GOTWaypoint.Kingsgrave.info(-1, 0, 0), GOTWaypoint.SkyReach.info(0, 1, 0), GOTWaypoint.Starfall.info(0, -1, 0), GOTWaypoint.Wyl.info(0, -1, 0), GOTWaypoint.Yronwood.info(1, 0, 0));
		f[12] = new GOTStructureDorneCity(biome, 0.0f).setIsTown();
		f[12].affix(GOTWaypoint.GhastonGrey, GOTWaypoint.PlankyTown, GOTWaypoint.Sunspear);
		f[13] = new GOTStructureDothrakiVillage(biome, 0.0f).setIsBig();
		f[13].affix(GOTWaypoint.Hornoth, GOTWaypoint.Kyth, GOTWaypoint.Sathar, GOTWaypoint.Rathylar, GOTWaypoint.VaesAthjikhari, GOTWaypoint.VaesDiaf, GOTWaypoint.VaesDothrak, GOTWaypoint.VaesEfe, GOTWaypoint.VaesGorqoyi, GOTWaypoint.VaesGraddakh, GOTWaypoint.VaesJini, GOTWaypoint.VaesKhadokh, GOTWaypoint.VaesKhewo, GOTWaypoint.VaesLeisi, GOTWaypoint.VaesLeqse, GOTWaypoint.VaesMejhah);
		f[14] = new GOTStructureDragonstoneCity(biome, 0.0f).setIsCastle();
		f[14].affix(GOTWaypoint.ClawIsle, GOTWaypoint.Dragonstone, GOTWaypoint.Driftmark, GOTWaypoint.HighTide, GOTWaypoint.SharpPoint, GOTWaypoint.Stonedance, GOTWaypoint.SweetportSound);
		f[15] = new GOTStructureDragonstoneCity(biome, 0.0f).setIsTown();
		f[15].affix(GOTWaypoint.Hull);
		f[16] = new GOTStructureGiftVillage(biome, 0.0f).setIsEastWatch();
		f[16].affix(GOTWaypoint.EastWatch);
		f[17] = new GOTStructureYiTiCity(biome, 0.0f).setIsTower();
		f[17].affix(GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2, GOTWaypoint.FiveForts3, GOTWaypoint.FiveForts4, GOTWaypoint.FiveForts5.info(0, 0, 3));
		f[18] = new GOTStructureGhiscarCity(biome, 0.0f).setIsColony();
		f[18].affix(GOTWaypoint.IsleOfWhips, GOTWaypoint.BarterBeach, GOTWaypoint.Gogossos, GOTWaypoint.Gorosh, GOTWaypoint.Zamettar.info(0, -1, 2));
		f[19] = new GOTStructureGhiscarCity(biome, 0.0f).setIsTown();
		f[19].affix(GOTWaypoint.Astapor.info(-1, 0, 1), GOTWaypoint.Meereen.info(0, -1, 2), GOTWaypoint.NewGhis.info(0, 0, 0), GOTWaypoint.Yunkai.info(-1, 0, 1));
		f[20] = new GOTStructureGhiscarCity(biome, 0.0f).setIsPyramidShiftX();
		f[20].affix(GOTWaypoint.Astapor.info(-2, 0, 0), GOTWaypoint.NewGhis.info(-2, 0, 0), GOTWaypoint.Yunkai.info(-2, 0, 0));
		f[21] = new GOTStructureGhiscarCity(biome, 0.0f).setIsPyramidShiftZ();
		f[21].affix(GOTWaypoint.Meereen.info(0, -2, 0));
		f[22] = new GOTStructureGiftVillage(biome, 0.0f);
		f[22].affix(GOTWaypoint.Moletown, GOTWaypoint.Queenscrown);
		f[23] = new GOTStructureWildlingVillage(biome, 0.0f).setIsHardhome();
		f[23].affix(GOTWaypoint.Hardhome);
		f[24] = new GOTStructureIbbenVillage(biome, 0.0f);
		f[24].affix(GOTWaypoint.IbNor, GOTWaypoint.IbSar, GOTWaypoint.NewIbbish, GOTWaypoint.PortOfIbben);
		f[25] = new GOTStructureIronbornCity(biome, 0.0f).setIsCastle();
		f[25].affix(GOTWaypoint.CatfishRock.info(0, 1, 0), GOTWaypoint.Blackhaven.info(0, 0, 0), GOTWaypoint.Blacktyde, GOTWaypoint.CorpseLake, GOTWaypoint.CrowSpikeKeep, GOTWaypoint.Downdelving, GOTWaypoint.DrummCastle, GOTWaypoint.GreyGarden, GOTWaypoint.Hammerhorn, GOTWaypoint.HarlawHall, GOTWaypoint.HarridanHill, GOTWaypoint.IronHolt, GOTWaypoint.LonelyLight, GOTWaypoint.MyreCastle, GOTWaypoint.Orkwood, GOTWaypoint.Pyke, GOTWaypoint.Saltcliffe, GOTWaypoint.SealskinPoint, GOTWaypoint.Shatterstone, GOTWaypoint.SparrCastle, GOTWaypoint.Stonehouse, GOTWaypoint.StonetreeCastle, GOTWaypoint.Sunderly, GOTWaypoint.TawneyCastle, GOTWaypoint.TenTowers, GOTWaypoint.Volmark);
		f[26] = new GOTStructureIronbornCity(biome, 0.0f).setIsTown();
		f[26].affix(GOTWaypoint.Lordsport, GOTWaypoint.RedHaven);
		f[27] = new GOTStructureIronbornCity(biome, 0.0f);
		f[27].affix(GOTWaypoint.Pebbleton);
		f[28] = new GOTStructureJogosVillage(biome, 0.0f).setIsBig();
		f[28].affix(GOTWaypoint.Hojdbaatar);
		f[29] = new GOTStructureLhazarVillage(biome, 0.0f).setIsTown();
		f[29].affix(GOTWaypoint.Hesh.info(1, 0, 3), GOTWaypoint.Kosrak.info(1, 0, 1), GOTWaypoint.Lhazosh.info(-1, 0, 1));
		f[30] = new GOTStructureLorathCity(biome, 0.0f).setIsTown();
		f[30].affix(GOTWaypoint.Lorath, GOTWaypoint.Morosh);
		f[31] = new GOTStructureLysCity(biome, 0.0f).setIsTown();
		f[31].affix(GOTWaypoint.Lys);
		f[33] = new GOTStructureMossovyVillage(biome, 0.0f);
		f[33].affix(GOTWaypoint.Tashtoo, GOTWaypoint.Bashkaruuchu, GOTWaypoint.AzuuKan, GOTWaypoint.Kujruk, GOTWaypoint.Korkunuchtuu, GOTWaypoint.NymduuOoz, GOTWaypoint.Azhydaar, GOTWaypoint.AkShaar, GOTWaypoint.SuuNym, GOTWaypoint.ShyluunUusu, GOTWaypoint.Kadar, GOTWaypoint.Nefer, GOTWaypoint.KDath);
		f[34] = new GOTStructureMyrCity(biome, 0.0f).setIsTown();
		f[34].affix(GOTWaypoint.Myr.info(-1, 0, 1));
		f[35] = new GOTStructureNorthCity(biome, 0.0f).setIsCastle();
		f[35].affix(GOTWaypoint.CatfishRock.info(0, 0, 0), GOTWaypoint.BreakstoneHill, GOTWaypoint.Goldgrass.info(0, 1, 0), GOTWaypoint.RisvellsCastle.info(0, 1, 0), GOTWaypoint.ServinsCastle.info(-1, 0, 0), GOTWaypoint.Winterfell.info(0, 0, 1), GOTWaypoint.BlackPool, GOTWaypoint.DeepwoodMotte, GOTWaypoint.Dreadfort, GOTWaypoint.FlintsFinger, GOTWaypoint.Highpoint, GOTWaypoint.TorhensSquare, GOTWaypoint.WidowsWatch, GOTWaypoint.Hornwood, GOTWaypoint.Ironrath, GOTWaypoint.Karhold, GOTWaypoint.LastHearth, GOTWaypoint.MoatKailin, GOTWaypoint.MormontsKeep, GOTWaypoint.OldCastle, GOTWaypoint.RamsGate, GOTWaypoint.RillwaterCrossing);
		f[36] = new GOTStructureNorthCity(biome, 0.0f).setIsSmallTown();
		f[36].affix(GOTWaypoint.Barrowtown.info(0, 1, 2));
		f[37] = new GOTStructureNorthCity(biome, 0.0f).setIsTown();
		f[37].affix(GOTWaypoint.WhiteHarbour.info(0, 0, 1));
		f[38] = new GOTStructureNorthHillmanVillage(biome, 0.0f);
		f[38].affix(GOTWaypoint.Skane, GOTWaypoint.Deepdown, GOTWaypoint.DriftwoodHall, GOTWaypoint.Kingshouse);
		f[39] = new GOTStructureNorthCity(biome, 0.0f);
		f[39].affix(GOTWaypoint.GreywaterWatch);
		f[40] = new GOTStructureNorvosCity(biome, 0.0f).setIsTown();
		f[40].affix(GOTWaypoint.Norvos.info(0, -1, 2));
		f[41] = new GOTStructurePentosCity(biome, 0.0f).setIsTown();
		f[41].affix(GOTWaypoint.Pentos.info(-1, 0, 1));
		f[42] = new GOTStructureQarthCity(biome, 0.0f).setIsTown();
		f[42].affix(GOTWaypoint.PortYhos.info(0, 1, 0), GOTWaypoint.Qarkash.info(0, 0, 0), GOTWaypoint.Qarth.info(0, 1, 0));
		f[43] = new GOTStructureQohorCity(biome, 0.0f).setIsTown();
		f[43].affix(GOTWaypoint.Qohor.info(0, -1, 2));
		f[44] = new GOTStructureReachCity(biome, 0.0f).setIsCastle();
		f[44].affix(GOTWaypoint.Highgarden.info(0, -1, 0), GOTWaypoint.Appleton.info(0, -1, 0), GOTWaypoint.Ashford.info(0, 1, 0), GOTWaypoint.Bandallon, GOTWaypoint.Goldengrove, GOTWaypoint.GrassyVale, GOTWaypoint.Greenshield, GOTWaypoint.Grimston, GOTWaypoint.Hammerhal, GOTWaypoint.RedLake, GOTWaypoint.Ring, GOTWaypoint.Southshield, GOTWaypoint.Uplands, GOTWaypoint.Holyhall, GOTWaypoint.Honeyholt, GOTWaypoint.HornHill, GOTWaypoint.IvyHall, GOTWaypoint.Longtable, GOTWaypoint.NewBarrel, GOTWaypoint.Blackcrown, GOTWaypoint.BrightwaterKeep, GOTWaypoint.CiderHall, GOTWaypoint.Coldmoat, GOTWaypoint.DarkDell, GOTWaypoint.Dunstonbury, GOTWaypoint.Bitterbridge.info(0, 1, 0), GOTWaypoint.GarnetGrove.info(-1, 0, 0), GOTWaypoint.HewettTown.info(0, -1, 0), GOTWaypoint.OldOak.info(-1, 0, 0), GOTWaypoint.SunHouse.info(0, -1, 0), GOTWaypoint.Whitegrove.info(-1, 0, 0));
		f[45] = new GOTStructureReachCity(biome, 0.0f).setIsTown();
		f[45].affix(GOTWaypoint.Oldtown.info(-1, 0, 3), GOTWaypoint.Appleton.info(0, 1, 2), GOTWaypoint.Ashford.info(0, -1, 0), GOTWaypoint.HewettTown.info(0, 1, 0), GOTWaypoint.Smithyton.info(0, 1, 2), GOTWaypoint.StarfishHarbor, GOTWaypoint.Vinetown, GOTWaypoint.Ryamsport, GOTWaypoint.Tumbleton.info(0, -1, 0));
		f[46] = new GOTStructureCrownlandsCity(biome, 0.0f).setIsRedKeep();
		f[46].affix(GOTWaypoint.KingsLanding.info(2, 0, 1));
		f[47] = new GOTStructureRiverlandsCity(biome, 0.0f).setIsCastle();
		f[47].affix(GOTWaypoint.Darry.info(1, 0, 0), GOTWaypoint.Maidenpool.info(1, 0, 0), GOTWaypoint.PinkmaidenCastle, GOTWaypoint.RaventreeHall, GOTWaypoint.WayfarerRest, GOTWaypoint.AcornHall, GOTWaypoint.Atranta, GOTWaypoint.Riverrun.info(1, 0, 0), GOTWaypoint.Seagard.info(0, -1, 0), GOTWaypoint.StoneHedge.info(0, 1, 0));
		f[48] = new GOTStructureRiverlandsCity(biome, 0.0f).setIsTown();
		f[48].affix(GOTWaypoint.Maidenpool.info(-1, 0, 3), GOTWaypoint.Saltpans, GOTWaypoint.StoneySept, GOTWaypoint.Seagard.info(0, 1, 2));
		f[49] = new GOTStructureRiverlandsCity(biome, 0.0f);
		f[49].affix(GOTWaypoint.FairMarket, GOTWaypoint.Harroway, GOTWaypoint.Pennytree, GOTWaypoint.Sevenstreams);
		f[50] = new GOTStructureRuins(biome, 0.0f);
		f[50].affix(GOTWaypoint.WestWatch, GOTWaypoint.KrazaajHas, GOTWaypoint.Morne, GOTWaypoint.NySar, GOTWaypoint.OldGhis, GOTWaypoint.SarMell, GOTWaypoint.Sarhoy, GOTWaypoint.Shandystone, GOTWaypoint.Starpike, GOTWaypoint.Telyria, GOTWaypoint.TorturersDeep, GOTWaypoint.VaesOrvik, GOTWaypoint.VaesQosar, GOTWaypoint.VaesShirosi, GOTWaypoint.VaesTolorro, GOTWaypoint.ValyrianRoad, GOTWaypoint.Velos, GOTWaypoint.VojjorSamvi, GOTWaypoint.GreyGallows, GOTWaypoint.BloodStone, GOTWaypoint.Anogaria, GOTWaypoint.KrazaajHas, GOTWaypoint.Kayakayanaya, GOTWaypoint.Aegon, GOTWaypoint.Raenys, GOTWaypoint.Visenya, GOTWaypoint.Ghozai, GOTWaypoint.Chroyane, GOTWaypoint.GhoyanDrohe, GOTWaypoint.FourteenFlames, GOTWaypoint.Ibbish, GOTWaypoint.Samyriana, GOTWaypoint.Bhorash, GOTWaypoint.Bayasabhad, GOTWaypoint.ArNoy, GOTWaypoint.Adakhakileki, GOTWaypoint.CastleLychester, GOTWaypoint.MhysaFaer, GOTWaypoint.AquosDhaen, GOTWaypoint.Draconys, GOTWaypoint.Tyria, GOTWaypoint.Rhyos, GOTWaypoint.Oros, GOTWaypoint.VulturesRoost, GOTWaypoint.Spicetown, GOTWaypoint.Castamere, GOTWaypoint.Goldenhill, GOTWaypoint.GreyironCastle, GOTWaypoint.HoareCastle, GOTWaypoint.HoareKeep, GOTWaypoint.HoggHall, GOTWaypoint.HollardCastle, GOTWaypoint.OldStones, GOTWaypoint.Summerhall, GOTWaypoint.TarbeckHall, GOTWaypoint.TowerOfJoy, GOTWaypoint.Whispers, GOTWaypoint.WhiteWalls);
		f[51] = new GOTStructureRuinsBig(biome, 0.0f);
		f[51].affix(GOTWaypoint.Bonetown, GOTWaypoint.Harrenhal, GOTWaypoint.Stygai, GOTWaypoint.Ulos, GOTWaypoint.Ulthos, GOTWaypoint.Yeen);
		f[52] = new GOTStructureGiftVillage(biome, 0.0f).setIsShadowTower();
		f[52].affix(GOTWaypoint.ShadowTower);
		f[53] = new GOTStructureStormlandsCity(biome, 0.0f).setIsCastle();
		f[53].affix(GOTWaypoint.CrowsNest, GOTWaypoint.GriffinsRoost, GOTWaypoint.Blackhaven.info(-1, 0, 0), GOTWaypoint.Bronzegate.info(1, 0, 0), GOTWaypoint.DeatsHear, GOTWaypoint.EvenfallHall, GOTWaypoint.Fawntown, GOTWaypoint.Amberly, GOTWaypoint.BlackHeart, GOTWaypoint.BroadArch, GOTWaypoint.Parchments, GOTWaypoint.Poddingfield, GOTWaypoint.RainHouse, GOTWaypoint.SeaworthCastle, GOTWaypoint.Stonehelm, GOTWaypoint.StormsEnd, GOTWaypoint.TudburyHoll, GOTWaypoint.Gallowsgrey, GOTWaypoint.Grandview, GOTWaypoint.Greenstone, GOTWaypoint.HarvestHall, GOTWaypoint.HaystackHall, GOTWaypoint.Mistwood, GOTWaypoint.Felwood.info(0, 1, 0), GOTWaypoint.Nightsong.info(0, 1, 0));
		f[54] = new GOTStructureStormlandsCity(biome, 0.0f).setIsTown();
		f[54].affix(GOTWaypoint.WeepingTown);
		f[55] = new GOTStructureSummerVillage(biome, 0.0f);
		f[55].affix(GOTWaypoint.Doquu, GOTWaypoint.Ebonhead, GOTWaypoint.GoldenHead, GOTWaypoint.Koj, GOTWaypoint.LastLament, GOTWaypoint.LizardHead, GOTWaypoint.LotusPoint, GOTWaypoint.Naath, GOTWaypoint.Omboru, GOTWaypoint.PearlPalace, GOTWaypoint.RedFlowerVale, GOTWaypoint.SweetLotusVale, GOTWaypoint.TallTreesTown, GOTWaypoint.Walano, GOTWaypoint.Xon);
		f[56] = new GOTStructureTower(biome, 0.0f);
		f[56].affix(GOTWaypoint.ThreeTowers1.info(0, 0, 1), GOTWaypoint.ThreeTowers2.info(0, 0, 1), GOTWaypoint.ThreeTowers3.info(0, 0, 1), GOTWaypoint.TowerOfGlimmering, GOTWaypoint.BaelishKeep, GOTWaypoint.HightowerLitehouse, GOTWaypoint.RamseyTower, GOTWaypoint.Standfast, GOTWaypoint.TwinsLeft.info(0, 0, 2), GOTWaypoint.TwinsRight);
		f[57] = new GOTStructureTyroshCity(biome, 0.0f).setIsTown();
		f[57].affix(GOTWaypoint.Tyrosh);
		f[58] = new GOTStructureIronbornCity(biome, 0.0f).setIsCamp();
		f[58].affix(GOTWaypoint.VictarionLanding);
		f[59] = new GOTStructureVolantisCity(biome, 0.0f).setIsTown();
		f[59].affix(GOTWaypoint.Elyria, GOTWaypoint.Tolos, GOTWaypoint.LittleValyria.info(0, 1, 0), GOTWaypoint.Mantarys.info(0, -1, 2), GOTWaypoint.Selhorys.info(-1, 0, 1), GOTWaypoint.Valysar.info(-1, 0, 1), GOTWaypoint.Volantis.info(-1, 0, 1), GOTWaypoint.VolonTherys.info(0, 1, 0));
		f[60] = new GOTStructureYiTiCity(biome, 0.0f).setIsTown();
		f[60].affix(GOTWaypoint.Asabhad.info(-1, 0, 0), GOTWaypoint.Baoji.info(0, 1, 2), GOTWaypoint.Eijiang.info(0, 1, 2), GOTWaypoint.Jinqi.info(-1, 0, 3), GOTWaypoint.Lizhao.info(1, 0, 0), GOTWaypoint.Manjin.info(1, 0, 3), GOTWaypoint.SiQo.info(1, 0, 0), GOTWaypoint.Tiqui.info(0, -1, 0), GOTWaypoint.TraderTown.info(0, -1, 0), GOTWaypoint.Vaibei.info(0, -1, 0), GOTWaypoint.Yibin.info(-1, 0, 3), GOTWaypoint.Yin.info(0, 1, 2), GOTWaypoint.Yunnan.info(1, 0, 1), GOTWaypoint.Zabhad, GOTWaypoint.Turrani, GOTWaypoint.Vahar, GOTWaypoint.Faros, GOTWaypoint.Huiji, GOTWaypoint.LengMa, GOTWaypoint.LengYi, GOTWaypoint.LesserMoraq, GOTWaypoint.Marahai, GOTWaypoint.PortMoraq);
		f[61] = new GOTStructureWesterlandsCity(biome, 0.0f).setIsCastle();
		f[61].affix(GOTWaypoint.CasterlyRock.info(-1, 0, 0), GOTWaypoint.Crakehall.info(-1, 0, 0), GOTWaypoint.GoldenTooth.info(0, 1, 0), GOTWaypoint.Kayce.info(1, 0, 0), GOTWaypoint.Sarsfield.info(0, -1, 0), GOTWaypoint.Silverhill, GOTWaypoint.Wyndhall, GOTWaypoint.Plumwood, GOTWaypoint.Riverspring, GOTWaypoint.Greenfield, GOTWaypoint.Hornvale, GOTWaypoint.DeepDen, GOTWaypoint.Faircastle, GOTWaypoint.Feastfires, GOTWaypoint.CleganesKeep, GOTWaypoint.Cornfield, GOTWaypoint.Crag, GOTWaypoint.Ashemark, GOTWaypoint.Banefort);
		f[62] = new GOTStructureWesterlandsCity(biome, 0.0f).setIsTown();
		f[62].affix(GOTWaypoint.Kayce.info(0, 0, 3), GOTWaypoint.Lannisport.info(-1, 0, 3));
		f[63] = new GOTStructureWesterlandsCity(biome, 0.0f);
		f[63].affix(GOTWaypoint.Oxcross);
		f[64] = new GOTStructureGiftVillage(biome, 0.0f).setIsAbandoned();
		f[64].affix(GOTWaypoint.Greenguard, GOTWaypoint.Torches, GOTWaypoint.LongBarrow, GOTWaypoint.Rimegate, GOTWaypoint.SableHall, GOTWaypoint.Woodswatch, GOTWaypoint.Nightfort, GOTWaypoint.DeepLake, GOTWaypoint.Oakenshield, GOTWaypoint.Icemark, GOTWaypoint.HoarfrostHill, GOTWaypoint.Stonedoor, GOTWaypoint.Greyguard, GOTWaypoint.Queensgate, GOTWaypoint.SentinelStand);
		f[65] = new GOTStructureYiTiCity(biome, 0.0f).setIsWall(false);
		f[65].affix(GOTWaypoint.Jianmen, GOTWaypoint.Anguo, GOTWaypoint.Dingguo, GOTWaypoint.Pinnu, GOTWaypoint.Pingjiang, GOTWaypoint.Wude, GOTWaypoint.Wusheng, GOTWaypoint.Zhenguo, GOTWaypoint.Lungmen);
		f[32] = new GOTStructureYiTiCity(biome, 0.0f).setIsWall(true);
		f[32].affix(GOTWaypoint.Anjiang);

		for (GOTVillageGen element : f) {
			biome.decorator.addFixedVillage(element);
		}
	}

	public static void onInit() {
		structures.put(GOTWaypoint.Ashemark, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityAddamMarbrand(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Asshai, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityAsshaiArchmag(world), world, 0, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Astapor.shift(-1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityKraznysMoNakloz(world), world, -1, 1, 0);
				spawnLegendaryNPC(new GOTEntityMissandei(world), world, -1, 1, -1);
				spawnLegendaryNPC(new GOTEntityGreyWorm(world), world, -1, 1, 1);
			}
		});

		structures.put(GOTWaypoint.Banefort, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityQuentenBanefort(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Barrowtown.shift(0, 1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBarbreyDustin(world), world, 0, 1, 3);
			}
		});

		structures.put(GOTWaypoint.Blacktyde, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBaelorBlacktyde(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.Braavos.shift(0, -1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityTychoNestoris(world), world, 0, 1, 1);
			}
		});

		structures.put(GOTWaypoint.BrightwaterKeep, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGarlanTyrell(world), world, 2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.CasterlyRock.shift(-1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityTywinLannister(world), world, 2, 1, 0);
				spawnLegendaryNPC(new GOTEntityQyburn(world), world, -2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.ClawIsle, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityArdrianCeltigar(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.CleganesKeep, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGregorClegane.GregorCleganeAlive(world), world, 2, 1, 0);
				spawnLegendaryNPC(new GOTEntityPolliver(world), world, -2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Cornfield, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHarysSwyft(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Crakehall.shift(-1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityLyleCrakehall(world), world, 2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Dragonstone, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityStannisBaratheon(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityDavosSeaworth(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityMelisandra(world), world, -2, 1, 2);
				spawnLegendaryNPC(new GOTEntityShireenBaratheon(world), world, 2, 1, -2);
				spawnLegendaryNPC(new GOTEntitySelyseBaratheon(world), world, 0, 1, 2);
				spawnLegendaryNPC(new GOTEntityMatthosSeaworth(world), world, 0, 1, -2);
			}
		});

		structures.put(GOTWaypoint.Dreadfort, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRamsayBolton(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityRooseBolton(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.Driftmark, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMonfordVelaryon(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityAuraneWaters(world), world, 2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.DrummCastle, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityDunstanDrumm(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityAndrikTheUnsmilling(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.Euron, new Spawner() {
			@Override
			public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
				this.setOriginAndRotation(world, i, j, k, rotation, 0);
				loadStrScan("euron_ship");
				generateStrScan(world, random, 16, 27, -58);
				for (int l = 0; l < 10; ++l) {
					spawnLegendaryNPC(new GOTEntityIronbornSoldier(world), world, 0, 1, 0);
				}
				spawnLegendaryNPC(new GOTEntityEuronGreyjoy(world), world, 0, 1, 0);
				return true;
			}
		});
		structures.put(GOTWaypoint.EvenfallHall, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySelwynTarth(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Faircastle, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySebastonFarman(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Feastfires, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityForleyPrester(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Goldengrove, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMathisRowan(world), world, 2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.GoldenTooth.shift(0, 1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityLeoLefford(world), world, 2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Greenshield, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMoribaldChester(world), world, 2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Greenstone, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityEldonEstermont(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.GreyGarden, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHarrasHarlaw(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.GreywaterWatch, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHowlandReed(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.Gulltown, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGeroldGrafton(world), world, 3, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Hammerhorn, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGoroldGoodbrother(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.HeartsHome, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityLynCorbray(world), world, 2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Hellholt, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHarmenUller(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Highgarden.shift(0, -1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMaceTyrell(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityOlennaTyrell(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityMargaeryTyrell(world), world, 2, 1, -2);
				spawnLegendaryNPC(new GOTEntityWillasTyrell(world), world, -2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.HighHermitage, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGeroldDayne(world), world, 2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.HightowerLitehouse, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityLeytonHightower(world), world, 0, 26, -5);
			}
		});

		structures.put(GOTWaypoint.Hojdbaatar, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityTugarKhan(world), world, 0, 5, 3);
			}
		});

		structures.put(GOTWaypoint.HollowHill, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBericDondarrion.BericDondarrionLife1(world), world, 3, 1, 0);
				spawnLegendaryNPC(new GOTEntityThoros(world), world, 0, 1, 3);
			}
		});

		structures.put(GOTWaypoint.HornHill, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRandyllTarly(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Hornvale, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityTytosBrax(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.IronOak, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHarroldHardyng(world), world, 0, 1, 2);
				spawnLegendaryNPC(new GOTEntityAnyaWaynwood(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Karhold, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRickardKarstark(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.KingsLanding.shift(1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySansaStark(world), world, 0, 1, 5);
				spawnLegendaryNPC(new GOTEntityShae(world), world, 0, 1, 6);
				spawnLegendaryNPC(new GOTEntityYoren(world), world, 0, 1, 4);
			}
		});

		structures.put(GOTWaypoint.Lannisport.shift(-1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityKevanLannister(world), world, 0, 1, 5);
				spawnLegendaryNPC(new GOTEntityDavenLannister(world), world, 0, 1, -5);
				spawnLegendaryNPC(new GOTEntityAmoryLorch(world), world, 5, 1, 0);
			}
		});

		structures.put(GOTWaypoint.LastHearth, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityJohnUmber(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.LonelyLight, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGylbertFarwynd(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.LongbowHall, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGilwoodHunter(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Longtable, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityOrtonMerryweather(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Lordsport, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityDagmer(world), world, 0, 1, 3);
			}
		});

		structures.put(GOTWaypoint.Lys, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySalladhorSaan(world), world, 0, 1, -1);
			}
		});

		structures.put(GOTWaypoint.Maidenpool.shift(1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityWilliamMooton(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Meereen.shift(0, -1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHizdahrZoLoraq(world), world, 0, 1, 1);
			}
		});

		structures.put(GOTWaypoint.MormontsKeep, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMaegeMormont(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Myr.shift(-1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHarryStrickland(world), world, -1, 1, -1);
			}
		});

		structures.put(GOTWaypoint.NaggaHill, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityAeronGreyjoy(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.NightKing, new Spawner() {
			@Override
			public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
				this.setOriginAndRotation(world, i, j, k, rotation, 0);
				loadStrScan("night_king");
				spawnLegendaryNPC(new GOTEntityNightKing(world), world, 0, 10, 0);
				generateStrScan(world, random, 0, 0, 0);
				return true;
			}
		});
		structures.put(GOTWaypoint.Ninestars, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySymondTempleton(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Norvos.shift(0, -1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMellario(world), world, 0, 1, 1);
			}
		});

		structures.put(GOTWaypoint.Oldtown.shift(-1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityEbrose(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.Pentos.shift(-1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityIllyrioMopatis(world), world, 3, 1, 0);
			}
		});

		structures.put(GOTWaypoint.PinkmaidenCastle, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityClementPiper(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Pyke, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBalonGreyjoy(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityYaraGreyjoy(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityTheonGreyjoy.TheonGreyjoyNormal(world), world, -2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Qarth.shift(0, 1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityXaroXhoanDaxos(world), world, 3, 1, 0);
			}
		});

		structures.put(GOTWaypoint.RaventreeHall, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityTytosBlackwood(world), world, 2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Redfort, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHortonRedfort(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.RedHaven, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityErikIronmaker(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.Ring, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityQuennRoxton(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.RisvellsCastle.shift(0, 1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRodrikRyswell(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Riverrun.shift(1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBryndenTully(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityEdmureTully(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityHosterTully(world), world, 2, 1, -2);
				spawnLegendaryNPC(new GOTEntityRodrikCassel(world), world, -2, 1, 2);
				spawnLegendaryNPC(new GOTEntityCatelynStark(world), world, 2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Runestone, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityYohnRoyce(world), world, 2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Sandstone, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityQuentynQorgyle(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Seagard.shift(0, -1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityJasonMallister(world), world, 0, 1, 3);
			}
		});

		structures.put(GOTWaypoint.ServinsCastle.shift(-1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityCleyCerwyn(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.SkyReach.shift(0, 1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityFranklynFowler(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Spider, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHummel009(world), world, 0, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Starfall.shift(0, -1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBericDayne(world), world, -2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.StarfishHarbor, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityPaxterRedwyne(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.StoneHedge.shift(0, 1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityJonosBracken(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Stonehelm, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGulianSwann(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.StormsEnd, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRenlyBaratheon(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityLorasTyrell(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityBrienneTarth(world), world, -2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Strongsong, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBenedarBelmore(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Sunspear, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityOberynMartell(world), world, 3, 1, 0);
				spawnLegendaryNPC(new GOTEntityDoranMartell(world), world, 0, 1, 3);
				spawnLegendaryNPC(new GOTEntityEllaryaSand(world), world, 3, 1, 3);
				spawnLegendaryNPC(new GOTEntityAreoHotah(world), world, 0, 1, -3);
				spawnLegendaryNPC(new GOTEntityTrystaneMartell(world), world, -3, 1, 0);
				spawnLegendaryNPC(new GOTEntityArianneMartell(world), world, -3, 1, 3);
				spawnLegendaryNPC(new GOTEntityManfreyMartell(world), world, -3, 1, -3);
			}
		});

		structures.put(GOTWaypoint.TenTowers, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRodrikHarlaw(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.TheEyrie, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRobinArryn(world), world, 0, 1, 2);
				spawnLegendaryNPC(new GOTEntityLysaArryn(world), world, -2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.ThreeEyedRavenCave, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityThreeEyedRaven(world), world, 0, 1, 0);
			}
		});

		structures.put(GOTWaypoint.TorhensSquare, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHelmanTallhart(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.TwinsLeft, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBlackWalderFrey(world), world, 0, 1, -15);
				spawnLegendaryNPC(new GOTEntityLotharFrey(world), world, 0, 1, -15);
			}
		});

		structures.put(GOTWaypoint.TwinsRight, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityWalderFrey(world), world, 0, 1, 15);
			}
		});

		structures.put(GOTWaypoint.Tyrosh, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityJonConnington(world), world, 0, 1, -1);
				spawnLegendaryNPC(new GOTEntityYoungGriff(world), world, 0, 1, -1);
			}
		});

		structures.put(GOTWaypoint.VaesEfe, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityDaenerysTargaryen(world), world, 0, 5, 3);
				spawnLegendaryNPC(new GOTEntityJorahMormont(world), world, 0, 5, 3);
			}
		});

		structures.put(GOTWaypoint.Volantis.shift(-1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMoqorro(world), world, -1, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Volmark, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMaronVolmark(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.WhiteHarbour, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityWymanManderly(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.WhiteWood, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBenjenStark(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.Winterfell, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRobbStark(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityHodor(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityAryaStark(world), world, 2, 1, -2);
				spawnLegendaryNPC(new GOTEntityBranStark(world), world, -2, 1, 2);
				spawnLegendaryNPC(new GOTEntityRickonStark(world), world, 0, 1, 2);
				spawnLegendaryNPC(new GOTEntityLuwin(world), world, 0, 1, -2);
				spawnLegendaryNPC(new GOTEntityOsha(world), world, 2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Yin.shift(0, 1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBuGai(world), world, 12, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Yronwood.shift(1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityQuentynMartell(world), world, 0, 1, 2);
				spawnLegendaryNPC(new GOTEntityAndersYronwood(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Yunkai.shift(-1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityDaarioNaharis(world), world, -1, 1, 0);
				spawnLegendaryNPC(new GOTEntityRazdalMoEraz(world), world, -1, 1, 1);
			}
		});
	}

	public static class Spawner extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(world);
			return true;
		}

		public void spawnLegendaryNPC(World world) {
		}
	}
}