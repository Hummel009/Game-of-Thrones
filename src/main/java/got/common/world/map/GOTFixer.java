package got.common.world.map;

import got.common.entity.essos.legendary.GOTEntityMissandei;
import got.common.entity.essos.legendary.captain.*;
import got.common.entity.essos.legendary.quest.GOTEntityBuGai;
import got.common.entity.essos.legendary.quest.GOTEntityDaenerysTargaryen;
import got.common.entity.essos.legendary.quest.GOTEntityMellario;
import got.common.entity.essos.legendary.trader.GOTEntityIllyrioMopatis;
import got.common.entity.essos.legendary.trader.GOTEntityMoqorro;
import got.common.entity.essos.legendary.trader.GOTEntityTychoNestoris;
import got.common.entity.essos.legendary.trader.GOTEntityXaroXhoanDaxos;
import got.common.entity.essos.legendary.warrior.*;
import got.common.entity.other.GOTEntityHummel009;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldier;
import got.common.entity.westeros.legendary.captain.*;
import got.common.entity.westeros.legendary.deco.*;
import got.common.entity.westeros.legendary.quest.*;
import got.common.entity.westeros.legendary.reborn.GOTEntityBericDondarrion;
import got.common.entity.westeros.legendary.reborn.GOTEntityGregorClegane;
import got.common.entity.westeros.legendary.reborn.GOTEntityTheonGreyjoy;
import got.common.entity.westeros.legendary.trader.*;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.feature.GOTWorldGenPartyTrees;
import got.common.world.structure.essos.asshai.GOTStructureAsshaiSettlement;
import got.common.world.structure.essos.braavos.GOTStructureBraavosSettlement;
import got.common.world.structure.essos.dothraki.GOTStructureDothrakiSettlement;
import got.common.world.structure.essos.ghiscar.GOTStructureGhiscarSettlement;
import got.common.world.structure.essos.ibben.GOTStructureIbbenSettlement;
import got.common.world.structure.essos.jogos.GOTStructureJogosSettlement;
import got.common.world.structure.essos.lhazar.GOTStructureLhazarSettlement;
import got.common.world.structure.essos.lorath.GOTStructureLorathSettlement;
import got.common.world.structure.essos.lys.GOTStructureLysSettlement;
import got.common.world.structure.essos.mossovy.GOTStructureMossovySettlement;
import got.common.world.structure.essos.myr.GOTStructureMyrSettlement;
import got.common.world.structure.essos.norvos.GOTStructureNorvosSettlement;
import got.common.world.structure.essos.pentos.GOTStructurePentosSettlement;
import got.common.world.structure.essos.qarth.GOTStructureQarthSettlement;
import got.common.world.structure.essos.qohor.GOTStructureQohorSettlement;
import got.common.world.structure.essos.tyrosh.GOTStructureTyroshSettlement;
import got.common.world.structure.essos.volantis.GOTStructureVolantisSettlement;
import got.common.world.structure.essos.yiti.GOTStructureYiTiSettlement;
import got.common.world.structure.other.*;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosSettlement;
import got.common.world.structure.sothoryos.summer.GOTStructureSummerSettlement;
import got.common.world.structure.westeros.arryn.GOTStructureArrynSettlement;
import got.common.world.structure.westeros.crownlands.GOTStructureCrownlandsSettlement;
import got.common.world.structure.westeros.dorne.GOTStructureDorneSettlement;
import got.common.world.structure.westeros.dragonstone.GOTStructureDragonstoneSettlement;
import got.common.world.structure.westeros.gift.GOTStructureGiftSettlement;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornSettlement;
import got.common.world.structure.westeros.north.GOTStructureNorthSettlement;
import got.common.world.structure.westeros.reach.GOTStructureReachSettlement;
import got.common.world.structure.westeros.riverlands.GOTStructureRiverlandsSettlement;
import got.common.world.structure.westeros.stormlands.GOTStructureStormlandsSettlement;
import got.common.world.structure.westeros.westerlands.GOTStructureWesterlandsSettlement;
import got.common.world.structure.westeros.wildling.GOTStructureWildlingSettlement;
import net.minecraft.world.World;

import java.util.*;

public class GOTFixer {
	public static Map<GOTAbstractWaypoint, GOTStructureBase> structures = new HashMap<>();
	public static Collection<GOTStructureBaseSettlement> locations = new HashSet<>();
	public static double essosGateShift = 0.265625;

	public static void addSpecialLocations(World world, Random random, int i, int k) {
		GOTWaypoint[] forts = {GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2, GOTWaypoint.FiveForts3, GOTWaypoint.FiveForts4, GOTWaypoint.FiveForts5};
		for (GOTWaypoint wp : forts) {
			new GOTFiveFortsWall(false, wp).generate(world, random, i, 0, k, 0);
		}
		GOTWorldGenPartyTrees worldGen = ((GOTWorldGenPartyTrees) GOTTreeType.WEIRWOOD.create(false, random)).disableRestrictions();
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Winterfell.info(-0.5, 0, Dir.WEST))) {
			worldGen.generate(world, random, i - 50, world.getTopSolidOrLiquidBlock(i - 50, k), k);
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.WhiteWood)) {
			worldGen.generate(world, random, i, world.getTopSolidOrLiquidBlock(i, k - 15), k - 15);
		}
	}

	public static void initLocations() {
		registerLocation(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.BloodyGate.info(0, -0.5), GOTWaypoint.TheEyrie.info(0, -0.5), GOTWaypoint.ColdwaterBurn, GOTWaypoint.GreyGlen, GOTWaypoint.HeartsHome, GOTWaypoint.IronOak, GOTWaypoint.LongbowHall, GOTWaypoint.Ninestars, GOTWaypoint.OldAnchor, GOTWaypoint.Pebble, GOTWaypoint.Redfort, GOTWaypoint.Runestone, GOTWaypoint.Snakewood, GOTWaypoint.Strongsong, GOTWaypoint.ThePaps, GOTWaypoint.Wickenden, GOTWaypoint.WitchIsle, GOTWaypoint.GateOfTheMoon.info(0.5, 0, Dir.EAST));
		registerLocation(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureArrynSettlement.Type.TOWN, 6), GOTWaypoint.Gulltown, GOTWaypoint.Sisterton);
		registerLocation(new GOTStructureAsshaiSettlement(GOTBiome.ocean, 0.0f), GOTWaypoint.Asshai.info(0, 0.8, Dir.SOUTH));
		registerLocation(new GOTStructureBraavosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureBraavosSettlement.Type.TOWN, 6), GOTWaypoint.Braavos.info(essosGateShift, -0.5, Dir.SOUTH));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.FORT, 3), GOTWaypoint.Antlers.info(0, -0.5), GOTWaypoint.Brownhollow, GOTWaypoint.DyreDen, GOTWaypoint.Stokeworth.info(-0.5, 0, Dir.WEST), GOTWaypoint.Hayford.info(0, -0.5), GOTWaypoint.RooksRest.info(0, -0.5), GOTWaypoint.Rosby.info(-0.55, 0, Dir.WEST));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.KINGS_LANDING, 6), GOTWaypoint.KingsLanding.info(0.9, 0, Dir.EAST));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.RED_KEEP, 3), GOTWaypoint.KingsLanding.info(2, 0, Dir.EAST));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.TOWN, 6), GOTWaypoint.Duskendale.info(-0.1, -1.1));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.VILLAGE, 6), GOTWaypoint.Briarwhite.info(0, -0.7), GOTWaypoint.Rosby.info(0.7, 0));
		registerLocation(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.Blackmont.info(0.5, 0, Dir.EAST), GOTWaypoint.GhostHill, GOTWaypoint.Godsgrace, GOTWaypoint.Hellholt, GOTWaypoint.HighHermitage.info(0.5, 0, Dir.EAST), GOTWaypoint.Tor, GOTWaypoint.Vaith, GOTWaypoint.WaterGardens, GOTWaypoint.Spottswood, GOTWaypoint.Lemonwood, GOTWaypoint.Saltshore, GOTWaypoint.Sandstone, GOTWaypoint.Kingsgrave.info(0.5, 0, Dir.EAST), GOTWaypoint.SkyReach.info(0, 0.5, Dir.SOUTH), GOTWaypoint.Starfall.info(0, 0.6, Dir.SOUTH), GOTWaypoint.Wyl.info(-0.55, 0, Dir.WEST), GOTWaypoint.Yronwood.info(0.5, 0, Dir.EAST));
		registerLocation(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDorneSettlement.Type.TOWN, 6), GOTWaypoint.GhastonGrey, GOTWaypoint.PlankyTown, GOTWaypoint.Sunspear);
		registerLocation(new GOTStructureDothrakiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDothrakiSettlement.Type.BIG, 5), GOTWaypoint.Hornoth.info(0.5, 0), GOTWaypoint.Kyth.info(0.3, -0.4), GOTWaypoint.Sathar.info(0.5, 0), GOTWaypoint.Rathylar.info(0.6, -0.2), GOTWaypoint.Saath.info(0, -0.5), GOTWaypoint.VaesAthjikhari.info(0, -0.5), GOTWaypoint.VaesDiaf, GOTWaypoint.VaesDothrak, GOTWaypoint.VaesEfe, GOTWaypoint.VaesGorqoyi.info(0.3, -0.4), GOTWaypoint.VaesGraddakh, GOTWaypoint.VaesJini.info(0, 0.5), GOTWaypoint.VojjorSamvi.info(0, -0.5), GOTWaypoint.VaesKhadokh.info(-0.1, -0.5), GOTWaypoint.VaesKhewo.info(0, -0.5), GOTWaypoint.VaesLeqse.info(0.5, 0), GOTWaypoint.VaesMejhah.info(0, -0.5), GOTWaypoint.KrazaajHas.info(0, -0.6));
		registerLocation(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDragonstoneSettlement.Type.FORT, 3), GOTWaypoint.ClawIsle, GOTWaypoint.Dragonstone, GOTWaypoint.Driftmark, GOTWaypoint.HighTide, GOTWaypoint.SharpPoint, GOTWaypoint.Stonedance, GOTWaypoint.SweetportSound);
		registerLocation(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDragonstoneSettlement.Type.TOWN, 6), GOTWaypoint.Hull);
		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.COLONY, 4), GOTWaypoint.IsleOfWhips, GOTWaypoint.BarterBeach, GOTWaypoint.Gogossos, GOTWaypoint.Gorosh, GOTWaypoint.Zamettar.info(0, -0.5, Dir.SOUTH));
		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.PYRAMID, 3), GOTWaypoint.Meereen.info(essosGateShift, -1.5), GOTWaypoint.Astapor.info(-1.5, -essosGateShift), GOTWaypoint.NewGhis.info(-1, 0), GOTWaypoint.Yunkai.info(-1.5, -essosGateShift));
		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.TOWN, 6), GOTWaypoint.Meereen.info(essosGateShift, -0.5, Dir.SOUTH), GOTWaypoint.Astapor.info(-0.5, -essosGateShift, Dir.EAST), GOTWaypoint.NewGhis, GOTWaypoint.Yunkai.info(-0.5, -essosGateShift, Dir.EAST));
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.ABANDONED, 2), GOTWaypoint.Greenguard, GOTWaypoint.Torches, GOTWaypoint.LongBarrow, GOTWaypoint.Rimegate, GOTWaypoint.SableHall, GOTWaypoint.Woodswatch, GOTWaypoint.Nightfort, GOTWaypoint.DeepLake, GOTWaypoint.Oakenshield, GOTWaypoint.Icemark, GOTWaypoint.HoarfrostHill, GOTWaypoint.Stonedoor, GOTWaypoint.Greyguard, GOTWaypoint.Queensgate, GOTWaypoint.SentinelStand);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.CASTLE_BLACK, 2), GOTWaypoint.CastleBlack);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.EAST_WATCH, 2), GOTWaypoint.EastWatch);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.SHADOW_TOWER, 2), GOTWaypoint.ShadowTower);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.VILLAGE, 4), GOTWaypoint.Moletown, GOTWaypoint.Queenscrown);
		registerLocation(new GOTStructureIbbenSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIbbenSettlement.Type.FORT, 6), GOTWaypoint.IbNor, GOTWaypoint.IbSar, GOTWaypoint.NewIbbish, GOTWaypoint.PortOfIbben);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.CAMP, 5), GOTWaypoint.VictarionLanding);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.CatfishRock, GOTWaypoint.Blacktyde, GOTWaypoint.CorpseLake, GOTWaypoint.CrowSpikeKeep, GOTWaypoint.Downdelving, GOTWaypoint.DrummCastle, GOTWaypoint.GreyGarden, GOTWaypoint.Hammerhorn, GOTWaypoint.HarlawHall, GOTWaypoint.HarridanHill, GOTWaypoint.IronHolt, GOTWaypoint.LonelyLight, GOTWaypoint.MyreCastle, GOTWaypoint.Orkwood, GOTWaypoint.Pyke, GOTWaypoint.Saltcliffe, GOTWaypoint.SealskinPoint, GOTWaypoint.Shatterstone, GOTWaypoint.SparrCastle, GOTWaypoint.Stonehouse, GOTWaypoint.StonetreeCastle, GOTWaypoint.Sunderly, GOTWaypoint.TawneyCastle, GOTWaypoint.TenTowers, GOTWaypoint.Volmark);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.TOWN, 6), GOTWaypoint.Lordsport, GOTWaypoint.RedHaven);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.VILLAGE, 6), GOTWaypoint.Pebbleton);
		registerLocation(new GOTStructureJogosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureJogosSettlement.Type.BIG, 5), GOTWaypoint.Hojdbaatar);
		registerLocation(new GOTStructureLhazarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureLhazarSettlement.Type.TOWN, 6), GOTWaypoint.Hesh.info(1.2, 0, Dir.WEST), GOTWaypoint.Kosrak.info(1, 0, Dir.WEST), GOTWaypoint.Lhazosh.info(1, 0, Dir.WEST));
		registerLocation(new GOTStructureLorathSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureLorathSettlement.Type.TOWN, 6), GOTWaypoint.Lorath, GOTWaypoint.Morosh);
		registerLocation(new GOTStructureLysSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureLysSettlement.Type.TOWN, 6), GOTWaypoint.Lys);
		registerLocation(new GOTStructureMossovySettlement(GOTBiome.ocean, 0.0f).type(GOTStructureMossovySettlement.Type.VILLAGE, 6), GOTWaypoint.Kuurulgan, GOTWaypoint.SuudanKorkuu, GOTWaypoint.KanduuBet, GOTWaypoint.Tashtoo, GOTWaypoint.Bashkaruuchu, GOTWaypoint.AzuuKan, GOTWaypoint.Kujruk, GOTWaypoint.Korkunuchtuu, GOTWaypoint.NymduuOoz, GOTWaypoint.Azhydaar, GOTWaypoint.AkShaar, GOTWaypoint.SuuNym, GOTWaypoint.ShyluunUusu, GOTWaypoint.Kadar, GOTWaypoint.Nefer, GOTWaypoint.KDath);
		registerLocation(new GOTStructureMyrSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureMyrSettlement.Type.TOWN, 6), GOTWaypoint.Myr.info(-0.5, -essosGateShift, Dir.EAST));
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.CatfishRock, GOTWaypoint.BreakstoneHill, GOTWaypoint.Goldgrass.info(0, 0.5, Dir.SOUTH), GOTWaypoint.RyswellsCastle.info(-0.5, 0, Dir.WEST), GOTWaypoint.ServinsCastle.info(-0.5, 0, Dir.WEST), GOTWaypoint.Winterfell.info(-0.5, 0, Dir.WEST), GOTWaypoint.BlackPool, GOTWaypoint.DeepwoodMotte, GOTWaypoint.Dreadfort.info(0, -0.5), GOTWaypoint.FlintsFinger, GOTWaypoint.Highpoint, GOTWaypoint.TorhensSquare.info(-0.5, 0, Dir.WEST), GOTWaypoint.WidowsWatch, GOTWaypoint.Hornwood, GOTWaypoint.Ironrath, GOTWaypoint.Karhold.info(0.5, 0, Dir.EAST), GOTWaypoint.LastHearth, GOTWaypoint.MoatKailin, GOTWaypoint.MormontsKeep, GOTWaypoint.OldCastle, GOTWaypoint.RamsGate, GOTWaypoint.RillwaterCrossing.info(-0.5, 0, Dir.WEST));
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.HILLMAN, 6), GOTWaypoint.Skane, GOTWaypoint.Deepdown, GOTWaypoint.DriftwoodHall, GOTWaypoint.Kingshouse);
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.SMALL_TOWN, 6), GOTWaypoint.Barrowtown);
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.TOWN, 6), GOTWaypoint.WhiteHarbour.info(0.9, 0, Dir.EAST));
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.VILLAGE, 6), GOTWaypoint.GreywaterWatch);
		registerLocation(new GOTStructureNorvosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorvosSettlement.Type.TOWN, 6), GOTWaypoint.Norvos.info(essosGateShift, -0.5, Dir.SOUTH));
		registerLocation(new GOTStructurePentosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructurePentosSettlement.Type.TOWN, 6), GOTWaypoint.Pentos.info(-0.5, -essosGateShift, Dir.EAST));
		registerLocation(new GOTStructurePentosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructurePentosSettlement.Type.VILLAGE, 5), GOTWaypoint.GhoyanDrohe.info(0, 0.7));
		registerLocation(new GOTStructureQarthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQarthSettlement.Type.COLONY, 4), GOTWaypoint.Teriman, GOTWaypoint.Batargas, GOTWaypoint.Karimagar);
		registerLocation(new GOTStructureQarthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQarthSettlement.Type.TOWN, 6), GOTWaypoint.PortYhos.info(-essosGateShift, 0.5), GOTWaypoint.Qarkash.info(-essosGateShift, 0.5), GOTWaypoint.Qarth.info(-essosGateShift, 0.5));
		registerLocation(new GOTStructureQohorSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQohorSettlement.Type.TOWN, 6), GOTWaypoint.Qohor.info(-0.6, -essosGateShift, Dir.EAST));
		registerLocation(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.Appleton.info(0, -0.51), GOTWaypoint.Ashford, GOTWaypoint.Bandallon, GOTWaypoint.Goldengrove, GOTWaypoint.GrassyVale, GOTWaypoint.Greenshield, GOTWaypoint.Grimston, GOTWaypoint.Hammerhal.info(0.5, 0, Dir.EAST), GOTWaypoint.RedLake.info(0, 0.5, Dir.SOUTH), GOTWaypoint.Ring.info(0, -0.5), GOTWaypoint.Southshield, GOTWaypoint.Uplands, GOTWaypoint.Holyhall.info(0.5, 0, Dir.EAST), GOTWaypoint.Honeyholt, GOTWaypoint.HornHill, GOTWaypoint.IvyHall.info(0, 0.5, Dir.SOUTH), GOTWaypoint.Longtable, GOTWaypoint.NewBarrel, GOTWaypoint.Blackcrown, GOTWaypoint.BrightwaterKeep, GOTWaypoint.CiderHall, GOTWaypoint.Coldmoat.info(0, 0.5, Dir.SOUTH), GOTWaypoint.DarkDell.info(-0.5, 0, Dir.WEST), GOTWaypoint.Dunstonbury, GOTWaypoint.Bitterbridge.info(0, 0.5, Dir.SOUTH), GOTWaypoint.GarnetGrove.info(-0.5, 0, Dir.WEST), GOTWaypoint.HewettTown, GOTWaypoint.OldOak.info(0, 0.5, Dir.SOUTH), GOTWaypoint.SunHouse.info(0, 0.5, Dir.SOUTH), GOTWaypoint.Whitegrove.info(-0.5, 0, Dir.WEST));
		registerLocation(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureReachSettlement.Type.HIGHGARDEN, 3), GOTWaypoint.Highgarden.info(0.5, 0, Dir.EAST));
		registerLocation(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureReachSettlement.Type.TOWN, 6), GOTWaypoint.Oldtown.info(-1.1, 0, Dir.WEST), GOTWaypoint.Appleton.info(0.1, 1.1, Dir.SOUTH), GOTWaypoint.Ashford, GOTWaypoint.HewettTown, GOTWaypoint.Smithyton.info(0, 0.9, Dir.SOUTH), GOTWaypoint.StarfishHarbor, GOTWaypoint.Vinetown, GOTWaypoint.Ryamsport, GOTWaypoint.Tumbleton.info(0, -1.0));
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.CROSSROADS, 2), GOTWaypoint.CrossroadsInn);
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.Darry, GOTWaypoint.Maidenpool.info(0, -0.5), GOTWaypoint.PinkmaidenCastle, GOTWaypoint.RaventreeHall, GOTWaypoint.WayfarerRest.info(-0.5, 0, Dir.WEST), GOTWaypoint.AcornHall.info(-0.5, 0, Dir.WEST), GOTWaypoint.Atranta, GOTWaypoint.Riverrun.info(0, -0.5), GOTWaypoint.Seagard.info(0, -0.7), GOTWaypoint.StoneHedge.info(0, -0.5));
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.TOWN, 6), GOTWaypoint.Harroway.info(0, 0.9, Dir.SOUTH), GOTWaypoint.Maidenpool.info(0, 0.9, Dir.SOUTH), GOTWaypoint.Saltpans.info(0.9, 0, Dir.EAST), GOTWaypoint.StoneySept.info(-0.9, 0, Dir.WEST), GOTWaypoint.Seagard.info(0, 0.9, Dir.SOUTH));
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.VILLAGE, 6), GOTWaypoint.FairMarket, GOTWaypoint.Pennytree, GOTWaypoint.Sevenstreams);
		registerLocation(new GOTStructureRuins(GOTBiome.ocean, 0.0f), GOTWaypoint.WestWatch.info(-0.3, 0), GOTWaypoint.VaesLeisi.info(0, 0.3), GOTWaypoint.Morne.info(0, 0.3), GOTWaypoint.NySar.info(0, 0.15), GOTWaypoint.OldGhis.info(0, 0.3), GOTWaypoint.SarMell.info(0, -0.25), GOTWaypoint.Sarhoy.info(0, 0.3), GOTWaypoint.Shandystone.info(0, 0.3), GOTWaypoint.Starpike.info(0, 0.3), GOTWaypoint.Telyria.info(0, 0.3), GOTWaypoint.TorturersDeep.info(0, 0.3), GOTWaypoint.VaesOrvik.info(0.3, 0), GOTWaypoint.VaesQosar.info(0, 0.3), GOTWaypoint.VaesShirosi.info(0, 0.3), GOTWaypoint.VaesTolorro.info(0, 0.3), GOTWaypoint.ValyrianRoad.info(-0.2, 0), GOTWaypoint.Velos.info(0, 0.3), GOTWaypoint.GreyGallows.info(0, 0.3), GOTWaypoint.BloodStone.info(0, 0.3), GOTWaypoint.Anogaria.info(0, 0.2), GOTWaypoint.Kayakayanaya.info(0, 0.3), GOTWaypoint.Aegon.info(0, 0.3), GOTWaypoint.Raenys.info(0, 0.3), GOTWaypoint.Visenya.info(0, 0.3), GOTWaypoint.Ghozai.info(0, 0.3), GOTWaypoint.Chroyane.info(0.3, 0), GOTWaypoint.FourteenFlames.info(0, 0.3), GOTWaypoint.Ibbish.info(0, 0.3), GOTWaypoint.Samyriana.info(0, 0.3), GOTWaypoint.Bhorash.info(0, 0.2), GOTWaypoint.Bayasabhad.info(0, 0.3), GOTWaypoint.ArNoy.info(-0.1, -0.25), GOTWaypoint.Adakhakileki.info(0, 0.3), GOTWaypoint.CastleLychester.info(0, -0.3), GOTWaypoint.MhysaFaer.info(0, 0.3), GOTWaypoint.AquosDhaen.info(0, 0.3), GOTWaypoint.Draconys.info(0, 0.3), GOTWaypoint.Tyria.info(0, 0.3), GOTWaypoint.Rhyos.info(0, 0.3), GOTWaypoint.Oros.info(0, 0.2), GOTWaypoint.VulturesRoost.info(0, 0.3), GOTWaypoint.Spicetown.info(0, 0.3), GOTWaypoint.Castamere.info(0, 0.3), GOTWaypoint.Goldenhill.info(0, 0.3), GOTWaypoint.GreyironCastle.info(0, 0.3), GOTWaypoint.HoareCastle.info(0, 0.3), GOTWaypoint.HoareKeep.info(0, 0.3), GOTWaypoint.HoggHall.info(0.2, -0.2), GOTWaypoint.HollardCastle.info(0.3, 0), GOTWaypoint.OldStones.info(0, 0.3), GOTWaypoint.Summerhall.info(-0.3, 0), GOTWaypoint.TarbeckHall.info(0, 0.3), GOTWaypoint.TowerOfJoy.info(-0.3, 0), GOTWaypoint.Whispers.info(0, 0.3), GOTWaypoint.WhiteWalls.info(0.2, -0.2));
		registerLocation(new GOTStructureRuinsBig(GOTBiome.ocean, 0.0f), GOTWaypoint.EastBay.info(0, 0.4), GOTWaypoint.EastCoast.info(0, 0.4), GOTWaypoint.NorthForests.info(0, 0.4), GOTWaypoint.WhiteMountains.info(0, 0.4), GOTWaypoint.CentralForests.info(0, 0.4), GOTWaypoint.Marshes.info(0, 0.4), GOTWaypoint.RedForests.info(0, 0.4), GOTWaypoint.SouthUlthos.info(0, 0.4), GOTWaypoint.SouthTaiga.info(0, 0.4), GOTWaypoint.Bonetown.info(0, 0.4), GOTWaypoint.Harrenhal.info(-0.4, 0), GOTWaypoint.Stygai.info(0, 0.4), GOTWaypoint.Ulos.info(0, 0.4), GOTWaypoint.Yeen.info(0, 0.4));
		registerLocation(new GOTStructureSothoryosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSothoryosSettlement.Type.PYRAMID, 3), GOTWaypoint.Raumati);
		registerLocation(new GOTStructureSothoryosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSothoryosSettlement.Type.VILLAGE, 3), GOTWaypoint.Maunga, GOTWaypoint.DragonPlace, GOTWaypoint.SouthPoint, GOTWaypoint.BigLake);
		registerLocation(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.CrowsNest, GOTWaypoint.GriffinsRoost, GOTWaypoint.Blackhaven.info(-0.5, 0, Dir.WEST), GOTWaypoint.Bronzegate.info(0, -0.5), GOTWaypoint.DeatsHear, GOTWaypoint.EvenfallHall, GOTWaypoint.Fawntown, GOTWaypoint.Amberly, GOTWaypoint.BlackHeart, GOTWaypoint.BroadArch, GOTWaypoint.Parchments.info(0, -0.5), GOTWaypoint.Poddingfield.info(0, -0.5), GOTWaypoint.RainHouse, GOTWaypoint.SeaworthCastle, GOTWaypoint.Stonehelm, GOTWaypoint.StormsEnd.info(0, 0.5, Dir.SOUTH), GOTWaypoint.TudburyHoll, GOTWaypoint.Gallowsgrey.info(0, -0.5), GOTWaypoint.Grandview.info(-0.5, 0, Dir.WEST), GOTWaypoint.Greenstone, GOTWaypoint.HarvestHall.info(0, -0.5), GOTWaypoint.HaystackHall.info(0, -0.5), GOTWaypoint.Mistwood, GOTWaypoint.Felwood.info(0, -0.5), GOTWaypoint.Nightsong.info(0, -0.5));
		registerLocation(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureStormlandsSettlement.Type.TOWN, 6), GOTWaypoint.WeepingTown);
		registerLocation(new GOTStructureSummerSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSummerSettlement.Type.FORT, 3), GOTWaypoint.Hauauru, GOTWaypoint.Matahau, GOTWaypoint.Takutai, GOTWaypoint.Ataahua, GOTWaypoint.Pereki, GOTWaypoint.Ngarara, GOTWaypoint.Tauranga, GOTWaypoint.Matao, GOTWaypoint.Ngahere, GOTWaypoint.Kohuru);
		registerLocation(new GOTStructureSummerSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSummerSettlement.Type.VILLAGE, 5), GOTWaypoint.Doquu, GOTWaypoint.Ebonhead, GOTWaypoint.GoldenHead, GOTWaypoint.Koj, GOTWaypoint.LastLament, GOTWaypoint.LizardHead, GOTWaypoint.LotusPoint, GOTWaypoint.Naath, GOTWaypoint.Omboru, GOTWaypoint.PearlPalace, GOTWaypoint.RedFlowerVale, GOTWaypoint.SweetLotusVale, GOTWaypoint.TallTreesTown, GOTWaypoint.Walano, GOTWaypoint.Xon);
		registerLocation(new GOTStructureTower(GOTBiome.ocean, 0.0f), GOTWaypoint.ThreeTowers.info(-0.5, -0.5, Dir.WEST), GOTWaypoint.ThreeTowers.info(-0.5, 0, Dir.WEST), GOTWaypoint.ThreeTowers.info(-0.5, 0.5, Dir.WEST), GOTWaypoint.TowerOfGlimmering, GOTWaypoint.BaelishKeep, GOTWaypoint.HightowerLitehouse, GOTWaypoint.RamseyTower, GOTWaypoint.Standfast, GOTWaypoint.TwinsLeft, GOTWaypoint.TwinsRight);
		registerLocation(new GOTStructureTyroshSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureTyroshSettlement.Type.TOWN, 6), GOTWaypoint.Tyrosh);
		registerLocation(new GOTStructureVolantisSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureVolantisSettlement.Type.TOWN, 6), GOTWaypoint.Elyria, GOTWaypoint.Tolos.info(-essosGateShift, 0.5), GOTWaypoint.LittleValyria.info(-essosGateShift + 0.26, 0.6), GOTWaypoint.Mantarys.info(essosGateShift, -0.5, Dir.SOUTH), GOTWaypoint.Selhorys.info(-0.5, -essosGateShift, Dir.EAST), GOTWaypoint.Valysar.info(-0.6, -essosGateShift + 0.25, Dir.EAST), GOTWaypoint.Volantis.info(-essosGateShift - 0.2, 0.6), GOTWaypoint.VolonTherys.info(-essosGateShift, 0.5));
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.Crakehall.info(-0.5, 0, Dir.WEST), GOTWaypoint.GoldenTooth.info(0, -0.52), GOTWaypoint.Kayce, GOTWaypoint.Sarsfield.info(0, -0.5), GOTWaypoint.Silverhill, GOTWaypoint.Wyndhall, GOTWaypoint.Plumwood, GOTWaypoint.Riverspring, GOTWaypoint.Greenfield, GOTWaypoint.Hornvale, GOTWaypoint.DeepDen, GOTWaypoint.Faircastle, GOTWaypoint.Feastfires, GOTWaypoint.CleganesKeep, GOTWaypoint.Cornfield, GOTWaypoint.Crag, GOTWaypoint.Ashemark, GOTWaypoint.Banefort);
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.CASTERLY_ROCK, 3), GOTWaypoint.CasterlyRock.info(-0.5, 0, Dir.WEST));
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.TOWN, 6), GOTWaypoint.Kayce, GOTWaypoint.Lannisport.info(-0.9, 0, Dir.WEST));
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.VILLAGE, 6), GOTWaypoint.Oxcross.info(0.2, 0.6));
		registerLocation(new GOTStructureWildlingSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWildlingSettlement.Type.CRASTER, 1), GOTWaypoint.CrastersKeep);
		registerLocation(new GOTStructureWildlingSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWildlingSettlement.Type.HARDHOME, 7), GOTWaypoint.Hardhome);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.GATE, 2), GOTWaypoint.Jianmen, GOTWaypoint.Anguo, GOTWaypoint.Dingguo, GOTWaypoint.Pinnu, GOTWaypoint.Pingjiang, GOTWaypoint.Wude, GOTWaypoint.Wusheng, GOTWaypoint.Zhenguo, GOTWaypoint.Lungmen);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.GATE_ROAD, 2), GOTWaypoint.Anjiang);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.TOWER, 9), GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2, GOTWaypoint.FiveForts3, GOTWaypoint.FiveForts4, GOTWaypoint.FiveForts5);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.TOWN, 7), GOTWaypoint.Asabhad.info(-1, 0, Dir.WEST), GOTWaypoint.Baoji.info(0, -1), GOTWaypoint.Eijiang.info(0, 1, Dir.SOUTH), GOTWaypoint.Jinqi.info(-1, 0, Dir.WEST), GOTWaypoint.Lizhao.info(1, 0, Dir.EAST), GOTWaypoint.Manjin.info(1.1, -0.2, Dir.EAST), GOTWaypoint.SiQo.info(1, 0, Dir.EAST), GOTWaypoint.Tiqui.info(0, -1), GOTWaypoint.TraderTown.info(0, -1), GOTWaypoint.Vaibei.info(0, -1), GOTWaypoint.Yibin.info(0, -1), GOTWaypoint.Yin.info(0, 1, Dir.SOUTH), GOTWaypoint.Yunnan.info(1, 0, Dir.EAST), GOTWaypoint.Changan.info(1, 0, Dir.EAST), GOTWaypoint.FuNing.info(1.1, 0.1, Dir.EAST), GOTWaypoint.Zabhad, GOTWaypoint.Turrani, GOTWaypoint.Vahar, GOTWaypoint.Faros, GOTWaypoint.Huiji, GOTWaypoint.LengMa, GOTWaypoint.LengYi, GOTWaypoint.LesserMoraq, GOTWaypoint.Marahai, GOTWaypoint.PortMoraq);

		for (GOTStructureBaseSettlement location : locations) {
			for (GOTBiome biome : GOTBiome.CONTENT) {
				biome.decorator.addFixedSettlement(location);
			}
		}
	}

	public static void initSpawners() {
		structures.put(GOTWaypoint.Ashemark, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityAddamMarbrand(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Asshai.info(0, 0.8), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityAsshaiArchmag(world), world, 0, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Astapor.info(-0.5, -essosGateShift), new Spawner() {
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

		structures.put(GOTWaypoint.Barrowtown, new Spawner() {
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

		structures.put(GOTWaypoint.Braavos.info(essosGateShift, -0.5), new Spawner() {
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

		structures.put(GOTWaypoint.CasterlyRock.info(-0.5, 0), new Spawner() {
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

		structures.put(GOTWaypoint.Crakehall.info(-0.5, 0), new Spawner() {
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

		structures.put(GOTWaypoint.Dreadfort.info(0, -0.5), new Spawner() {
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
				setOriginAndRotation(world, i, j, k, rotation, 0);
				loadStrScan("euron_ship");
				if (!disable) {
					generateStrScan(world, random, 16, 27, -58);
				}
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

		structures.put(GOTWaypoint.GoldenTooth.info(0, -0.52), new Spawner() {
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

		structures.put(GOTWaypoint.Highgarden.info(0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMaceTyrell(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityOlennaTyrell(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityMargaeryTyrell(world), world, 2, 1, -2);
				spawnLegendaryNPC(new GOTEntityWillasTyrell(world), world, -2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.HighHermitage.info(0.5, 0), new Spawner() {
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

		structures.put(GOTWaypoint.Karhold.info(0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRickardKarstark(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.KingsLanding.info(0.9, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySansaStark(world), world, 0, 1, 5);
				spawnLegendaryNPC(new GOTEntityShae(world), world, 0, 1, 6);
				spawnLegendaryNPC(new GOTEntityYoren(world), world, 0, 1, 4);
			}
		});

		structures.put(GOTWaypoint.Lannisport.info(-0.9, 0), new Spawner() {
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

		structures.put(GOTWaypoint.Maidenpool.info(0, -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityWilliamMooton(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Meereen.info(essosGateShift, -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHizdahrZoLoraq(world), world, -1, 1, -1);
			}
		});

		structures.put(GOTWaypoint.MormontsKeep, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMaegeMormont(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Myr.info(-0.5, -essosGateShift), new Spawner() {
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
				setOriginAndRotation(world, i, j, k, rotation, 0);
				loadStrScan("night_king");
				spawnLegendaryNPC(new GOTEntityNightKing(world), world, 0, 10, 0);
				if (!disable) {
					generateStrScan(world, random, 0, 0, 0);
				}
				return true;
			}
		});
		structures.put(GOTWaypoint.Ninestars, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySymondTempleton(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Norvos.info(essosGateShift, -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMellario(world), world, 0, 1, 1);
			}
		});

		structures.put(GOTWaypoint.Oldtown.info(-1.1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityEbrose(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.Pentos.info(-0.5, -essosGateShift), new Spawner() {
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

		structures.put(GOTWaypoint.Qarth.info(-essosGateShift, 0.5), new Spawner() {
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

		structures.put(GOTWaypoint.Ring.info(0, -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityQuennRoxton(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.RyswellsCastle.info(-0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRodrikRyswell(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Riverrun.info(0, -0.5), new Spawner() {
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

		structures.put(GOTWaypoint.Seagard.info(0, -0.7), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityJasonMallister(world), world, 0, 1, 3);
			}
		});

		structures.put(GOTWaypoint.ServinsCastle.info(-0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityCleyCerwyn(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.SkyReach.info(0, 0.5), new Spawner() {
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

		structures.put(GOTWaypoint.Starfall.info(0, 0.6), new Spawner() {
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

		structures.put(GOTWaypoint.StoneHedge.info(0, -0.5), new Spawner() {
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

		structures.put(GOTWaypoint.StormsEnd.info(0, 0.5), new Spawner() {
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

		structures.put(GOTWaypoint.TheEyrie.info(0, -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRobinArryn(world), world, 0, 1, 2);
				spawnLegendaryNPC(new GOTEntityLysaArryn(world), world, -2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.ThreeEyedRavenCave, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityThreeEyedRaven(world), world, 0, 2, 5);
			}
		});

		structures.put(GOTWaypoint.TorhensSquare.info(-0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHelmanTallhart(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.TwinsLeft, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBlackWalderFrey(world), world, 0, 2, -15);
				spawnLegendaryNPC(new GOTEntityLotharFrey(world), world, 0, 2, -15);
			}
		});

		structures.put(GOTWaypoint.TwinsRight, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityWalderFrey(world), world, 0, 2, -15);
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

		structures.put(GOTWaypoint.Volantis.info(-essosGateShift - 0.2, 0.6), new Spawner() {
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

		structures.put(GOTWaypoint.WhiteHarbour.info(0.9, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityWymanManderly(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.WhiteWood, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBenjenStark(world).setIsRider(true), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.Winterfell.info(-0.5, 0), new Spawner() {
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

		structures.put(GOTWaypoint.Yin.info(0, 1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBuGai(world), world, 12, 1, 0);
			}
		});

		structures.put(GOTWaypoint.Yronwood.info(0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityQuentynMartell(world), world, 0, 1, 2);
				spawnLegendaryNPC(new GOTEntityAndersYronwood(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Yunkai.info(-0.5, -essosGateShift), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityDaarioNaharis(world), world, -1, 1, 0);
				spawnLegendaryNPC(new GOTEntityRazdalMoEraz(world), world, -1, 1, 1);
			}
		});
	}

	public static void onInit() {
		initSpawners();
		initLocations();
	}

	public static void registerLocation(GOTStructureBaseSettlement settlement, GOTAbstractWaypoint... wps) {
		settlement.affix(wps);
		locations.add(settlement);
	}

	public enum Dir {
		NORTH, EAST, SOUTH, WEST
	}

	public static class Spawner extends GOTStructureBase {

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(world);
			return true;
		}

		public void spawnLegendaryNPC(World world) {
		}
	}
}