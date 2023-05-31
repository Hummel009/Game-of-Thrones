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
	public static Set<GOTStructureBaseSettlement> locations = new HashSet<>();

	public static void addSpecialLocations(World world, Random random, int i, int k) {
		GOTWaypoint[] forts = {GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2, GOTWaypoint.FiveForts3, GOTWaypoint.FiveForts4, GOTWaypoint.FiveForts5};
		for (GOTWaypoint wp : forts) {
			new GOTFiveFortsWall(false, wp).generate(world, random, i, 0, k, 0);
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.WhiteWood) || GOTFixedStructures.fixedAt(i, k, GOTWaypoint.Winterfell)) {
			((GOTWorldGenPartyTrees) GOTTreeType.WEIRWOOD.create(false, random)).disableRestrictions().generate(world, random, i + 50, world.getTopSolidOrLiquidBlock(i + 50, k), k);
		}
	}

	public static void registerLocation(GOTStructureBaseSettlement settlement, GOTAbstractWaypoint... wps) {
		settlement.affix(wps);
		locations.add(settlement);
	}

	public static void addWaypointLocations(GOTBiome biome) {
		locations.clear();
		registerLocation(new GOTStructureArrynSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.BloodyGate.info(0, -0.5), GOTWaypoint.TheEyrie.info(0, -0.5), GOTWaypoint.ColdwaterBurn, GOTWaypoint.GreyGlen, GOTWaypoint.HeartsHome, GOTWaypoint.IronOak, GOTWaypoint.LongbowHall, GOTWaypoint.Ninestars, GOTWaypoint.OldAnchor, GOTWaypoint.Pebble, GOTWaypoint.Redfort, GOTWaypoint.Runestone, GOTWaypoint.Snakewood, GOTWaypoint.Strongsong, GOTWaypoint.ThePaps, GOTWaypoint.Wickenden, GOTWaypoint.WitchIsle, GOTWaypoint.GateOfTheMoon.info(-0.6, 0));
		registerLocation(new GOTStructureArrynSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Gulltown, GOTWaypoint.Sisterton);
		registerLocation(new GOTStructureAsshaiSettlement(biome, 0.0f), GOTWaypoint.Asshai.info(0, 0, 2));
		registerLocation(new GOTStructureBraavosSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Braavos.info(0, -1, 2));
		registerLocation(new GOTStructureCrownlandsSettlement(biome, 0.0f), GOTWaypoint.Briarwhite, GOTWaypoint.Rosby.info(0, 0.7));
		registerLocation(new GOTStructureCrownlandsSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.Antlers, GOTWaypoint.Brownhollow, GOTWaypoint.DyreDen, GOTWaypoint.Stokeworth, GOTWaypoint.Hayford.info(-0.5, 0), GOTWaypoint.RooksRest.info(0, -0.5), GOTWaypoint.Rosby.info(0, -0.5));
		registerLocation(new GOTStructureCrownlandsSettlement(biome, 0.0f).setIsKingsLanding(), GOTWaypoint.KingsLanding.info(0.9, 0, 1));
		registerLocation(new GOTStructureCrownlandsSettlement(biome, 0.0f).setIsRedKeep(), GOTWaypoint.KingsLanding.info(2, 0, 1));
		registerLocation(new GOTStructureCrownlandsSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Duskendale.info(-1.3, 0, 3));
		registerLocation(new GOTStructureDorneSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.Blackmont, GOTWaypoint.GhostHill, GOTWaypoint.Godsgrace, GOTWaypoint.Hellholt, GOTWaypoint.HighHermitage, GOTWaypoint.Tor, GOTWaypoint.Vaith, GOTWaypoint.WaterGardens, GOTWaypoint.Spottswood, GOTWaypoint.Lemonwood, GOTWaypoint.Saltshore, GOTWaypoint.Sandstone, GOTWaypoint.Kingsgrave.info(-0.5, 0), GOTWaypoint.SkyReach.info(0, 0.5), GOTWaypoint.Starfall.info(0, -0.5), GOTWaypoint.Wyl.info(-0.5, 0), GOTWaypoint.Yronwood.info(0.5, 0));
		registerLocation(new GOTStructureDorneSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.GhastonGrey, GOTWaypoint.PlankyTown, GOTWaypoint.Sunspear);
		registerLocation(new GOTStructureDothrakiSettlement(biome, 0.0f).setIsBig(), GOTWaypoint.Hornoth.info(0.5, 0), GOTWaypoint.Kyth.info(0.5, 0), GOTWaypoint.Sathar.info(0, 0.6), GOTWaypoint.Rathylar.info(0, -0.6), GOTWaypoint.Saath.info(0, -0.5), GOTWaypoint.VaesAthjikhari.info(0, -0.5), GOTWaypoint.VaesDiaf, GOTWaypoint.VaesDothrak, GOTWaypoint.VaesEfe, GOTWaypoint.VaesGorqoyi.info(0, -0.5), GOTWaypoint.VaesGraddakh, GOTWaypoint.VaesJini.info(0, -0.5), GOTWaypoint.VojjorSamvi.info(0, -0.5), GOTWaypoint.VaesKhadokh.info(0, -0.5), GOTWaypoint.VaesKhewo.info(0, -0.5), GOTWaypoint.VaesLeqse.info(0.6, 0), GOTWaypoint.VaesMejhah.info(0, -0.5), GOTWaypoint.KrazaajHas.info(0, -0.5));
		registerLocation(new GOTStructureDragonstoneSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.ClawIsle, GOTWaypoint.Dragonstone, GOTWaypoint.Driftmark, GOTWaypoint.HighTide, GOTWaypoint.SharpPoint, GOTWaypoint.Stonedance, GOTWaypoint.SweetportSound);
		registerLocation(new GOTStructureDragonstoneSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Hull);
		registerLocation(new GOTStructureGhiscarSettlement(biome, 0.0f).setIsColony(), GOTWaypoint.IsleOfWhips, GOTWaypoint.BarterBeach, GOTWaypoint.Gogossos, GOTWaypoint.Gorosh, GOTWaypoint.Zamettar.info(0, -1, 2));
		registerLocation(new GOTStructureGhiscarSettlement(biome, 0.0f).setIsPyramidShiftX(), GOTWaypoint.Meereen.info(-2, 0, 0), GOTWaypoint.Astapor.info(-2, 0, 0), GOTWaypoint.NewGhis.info(-1, 0, 0), GOTWaypoint.Yunkai.info(-2, 0, 0));
		registerLocation(new GOTStructureGhiscarSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Meereen.info(-1, 0, 1), GOTWaypoint.Astapor.info(-1, 0, 1), GOTWaypoint.NewGhis.info(0, 0, 1), GOTWaypoint.Yunkai.info(-1, 0, 1));
		registerLocation(new GOTStructureGiftSettlement(biome, 0.0f), GOTWaypoint.Moletown.info(0.5, 0, 3), GOTWaypoint.Queenscrown);
		registerLocation(new GOTStructureGiftSettlement(biome, 0.0f).setIsAbandoned(), GOTWaypoint.Greenguard, GOTWaypoint.Torches, GOTWaypoint.LongBarrow, GOTWaypoint.Rimegate, GOTWaypoint.SableHall, GOTWaypoint.Woodswatch, GOTWaypoint.Nightfort, GOTWaypoint.DeepLake, GOTWaypoint.Oakenshield, GOTWaypoint.Icemark, GOTWaypoint.HoarfrostHill, GOTWaypoint.Stonedoor, GOTWaypoint.Greyguard, GOTWaypoint.Queensgate, GOTWaypoint.SentinelStand);
		registerLocation(new GOTStructureGiftSettlement(biome, 0.0f).setIsCastleBlack(), GOTWaypoint.CastleBlack);
		registerLocation(new GOTStructureGiftSettlement(biome, 0.0f).setIsEastWatch(), GOTWaypoint.EastWatch);
		registerLocation(new GOTStructureGiftSettlement(biome, 0.0f).setIsShadowTower(), GOTWaypoint.ShadowTower);
		registerLocation(new GOTStructureIbbenSettlement(biome, 0.0f), GOTWaypoint.IbNor, GOTWaypoint.IbSar, GOTWaypoint.NewIbbish, GOTWaypoint.PortOfIbben);
		registerLocation(new GOTStructureIronbornSettlement(biome, 0.0f), GOTWaypoint.Pebbleton);
		registerLocation(new GOTStructureIronbornSettlement(biome, 0.0f).setIsCamp(), GOTWaypoint.VictarionLanding);
		registerLocation(new GOTStructureIronbornSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.CatfishRock.info(0, 1, 0), GOTWaypoint.Blacktyde, GOTWaypoint.CorpseLake, GOTWaypoint.CrowSpikeKeep, GOTWaypoint.Downdelving, GOTWaypoint.DrummCastle, GOTWaypoint.GreyGarden, GOTWaypoint.Hammerhorn, GOTWaypoint.HarlawHall, GOTWaypoint.HarridanHill, GOTWaypoint.IronHolt, GOTWaypoint.LonelyLight, GOTWaypoint.MyreCastle, GOTWaypoint.Orkwood, GOTWaypoint.Pyke, GOTWaypoint.Saltcliffe, GOTWaypoint.SealskinPoint, GOTWaypoint.Shatterstone, GOTWaypoint.SparrCastle, GOTWaypoint.Stonehouse, GOTWaypoint.StonetreeCastle, GOTWaypoint.Sunderly, GOTWaypoint.TawneyCastle, GOTWaypoint.TenTowers, GOTWaypoint.Volmark);
		registerLocation(new GOTStructureIronbornSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Lordsport, GOTWaypoint.RedHaven);
		registerLocation(new GOTStructureJogosSettlement(biome, 0.0f).setIsBig(), GOTWaypoint.Hojdbaatar);
		registerLocation(new GOTStructureLhazarSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Hesh.info(1, 0, 3), GOTWaypoint.Kosrak.info(1, 0, 3), GOTWaypoint.Lhazosh.info(-1, 0, 1));
		registerLocation(new GOTStructureLorathSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Lorath, GOTWaypoint.Morosh);
		registerLocation(new GOTStructureLysSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Lys);
		registerLocation(new GOTStructureMossovySettlement(biome, 0.0f), GOTWaypoint.Kuurulgan, GOTWaypoint.SuudanKorkuu, GOTWaypoint.KanduuBet, GOTWaypoint.Tashtoo, GOTWaypoint.Bashkaruuchu, GOTWaypoint.AzuuKan, GOTWaypoint.Kujruk, GOTWaypoint.Korkunuchtuu, GOTWaypoint.NymduuOoz, GOTWaypoint.Azhydaar, GOTWaypoint.AkShaar, GOTWaypoint.SuuNym, GOTWaypoint.ShyluunUusu, GOTWaypoint.Kadar, GOTWaypoint.Nefer, GOTWaypoint.KDath);
		registerLocation(new GOTStructureMyrSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Myr.info(-1, 0, 1));
		registerLocation(new GOTStructureNorthSettlement(biome, 0.0f), GOTWaypoint.GreywaterWatch);
		registerLocation(new GOTStructureNorthSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.CatfishRock, GOTWaypoint.BreakstoneHill, GOTWaypoint.Goldgrass.info(0, 0.5), GOTWaypoint.RisvellsCastle.info(0, 0.5), GOTWaypoint.ServinsCastle.info(-0.5, 0, 0), GOTWaypoint.Winterfell.info(0, 0, 1), GOTWaypoint.BlackPool.info(0, 0.5), GOTWaypoint.DeepwoodMotte, GOTWaypoint.Dreadfort.info(0, 0.5), GOTWaypoint.FlintsFinger, GOTWaypoint.Highpoint, GOTWaypoint.TorhensSquare.info(-0.5, 0), GOTWaypoint.WidowsWatch, GOTWaypoint.Hornwood, GOTWaypoint.Ironrath, GOTWaypoint.Karhold.info(0.5, 0), GOTWaypoint.LastHearth, GOTWaypoint.MoatKailin, GOTWaypoint.MormontsKeep, GOTWaypoint.OldCastle, GOTWaypoint.RamsGate.info(0.5, 0), GOTWaypoint.RillwaterCrossing.info(0, -0.5));
		registerLocation(new GOTStructureNorthSettlement(biome, 0.0f).setIsHillman(), GOTWaypoint.Skane, GOTWaypoint.Deepdown, GOTWaypoint.DriftwoodHall, GOTWaypoint.Kingshouse);
		registerLocation(new GOTStructureNorthSettlement(biome, 0.0f).setIsSmallTown(), GOTWaypoint.Barrowtown.info(0, 1, 2));
		registerLocation(new GOTStructureNorthSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.WhiteHarbour.info(0.9, 0, 1));
		registerLocation(new GOTStructureNorvosSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Norvos.info(0, -1, 2));
		registerLocation(new GOTStructurePentosSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Pentos.info(-1, 0, 1));
		registerLocation(new GOTStructureQarthSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.Teriman, GOTWaypoint.Batargas, GOTWaypoint.Karimagar);
		registerLocation(new GOTStructureQarthSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.PortYhos.info(0, 1, 0), GOTWaypoint.Qarkash.info(0, 1, 0), GOTWaypoint.Qarth.info(0, 1, 0));
		registerLocation(new GOTStructureQohorSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Qohor.info(0, -1, 2));
		registerLocation(new GOTStructureReachSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.Highgarden.info(0, -1, 0), GOTWaypoint.Appleton.info(0, -0.5), GOTWaypoint.Ashford.info(0, 1, 0), GOTWaypoint.Bandallon, GOTWaypoint.Goldengrove, GOTWaypoint.GrassyVale, GOTWaypoint.Greenshield, GOTWaypoint.Grimston, GOTWaypoint.Hammerhal, GOTWaypoint.RedLake, GOTWaypoint.Ring, GOTWaypoint.Southshield, GOTWaypoint.Uplands, GOTWaypoint.Holyhall, GOTWaypoint.Honeyholt, GOTWaypoint.HornHill.info(0, 0.5), GOTWaypoint.IvyHall, GOTWaypoint.Longtable, GOTWaypoint.NewBarrel, GOTWaypoint.Blackcrown, GOTWaypoint.BrightwaterKeep, GOTWaypoint.CiderHall, GOTWaypoint.Coldmoat, GOTWaypoint.DarkDell.info(0, 0.6), GOTWaypoint.Dunstonbury, GOTWaypoint.Bitterbridge.info(0, 0.5), GOTWaypoint.GarnetGrove.info(-0.5, 0), GOTWaypoint.HewettTown.info(0, -1, 0), GOTWaypoint.OldOak.info(-0.5, 0), GOTWaypoint.SunHouse.info(0, 0.5), GOTWaypoint.Whitegrove.info(-0.5, 0));
		registerLocation(new GOTStructureReachSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Oldtown.info(-0.9, 0, 3), GOTWaypoint.Appleton.info(0, 1.2, 2), GOTWaypoint.Ashford.info(0, -1, 0), GOTWaypoint.HewettTown.info(0, 1, 0), GOTWaypoint.Smithyton.info(0, 0.9, 2), GOTWaypoint.StarfishHarbor, GOTWaypoint.Vinetown, GOTWaypoint.Ryamsport, GOTWaypoint.Tumbleton.info(0, -0.9));
		registerLocation(new GOTStructureRiverlandsSettlement(biome, 0.0f), GOTWaypoint.FairMarket, GOTWaypoint.Harroway.info(0, 0.6), GOTWaypoint.Pennytree, GOTWaypoint.Sevenstreams);
		registerLocation(new GOTStructureRiverlandsSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.Darry.info(0.5, 0), GOTWaypoint.Maidenpool.info(0.5, 0), GOTWaypoint.PinkmaidenCastle, GOTWaypoint.RaventreeHall, GOTWaypoint.WayfarerRest, GOTWaypoint.AcornHall, GOTWaypoint.Atranta, GOTWaypoint.Riverrun.info(0, -0.5), GOTWaypoint.Seagard.info(0, -0.7), GOTWaypoint.StoneHedge.info(0, 0.5));
		registerLocation(new GOTStructureRiverlandsSettlement(biome, 0.0f).setIsCrossroads(), GOTWaypoint.CrossroadsInn);
		registerLocation(new GOTStructureRiverlandsSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Maidenpool.info(-0.9, 0, 3), GOTWaypoint.Saltpans, GOTWaypoint.StoneySept, GOTWaypoint.Seagard.info(0, 0.9, 2));
		registerLocation(new GOTStructureRuins(biome, 0.0f), GOTWaypoint.WestWatch, GOTWaypoint.VaesLeisi, GOTWaypoint.Morne, GOTWaypoint.NySar.info(0.3, 0), GOTWaypoint.OldGhis, GOTWaypoint.SarMell.info(0, -0.3), GOTWaypoint.Sarhoy, GOTWaypoint.Shandystone, GOTWaypoint.Starpike, GOTWaypoint.Telyria, GOTWaypoint.TorturersDeep, GOTWaypoint.VaesOrvik.info(0.3, 0), GOTWaypoint.VaesQosar, GOTWaypoint.VaesShirosi, GOTWaypoint.VaesTolorro, GOTWaypoint.ValyrianRoad.info(0, -0.3), GOTWaypoint.Velos, GOTWaypoint.GreyGallows, GOTWaypoint.BloodStone, GOTWaypoint.Anogaria.info(0, -0.3), GOTWaypoint.Kayakayanaya, GOTWaypoint.Aegon, GOTWaypoint.Raenys, GOTWaypoint.Visenya, GOTWaypoint.Ghozai, GOTWaypoint.Chroyane.info(0.3, 0), GOTWaypoint.GhoyanDrohe.info(0, 0.3), GOTWaypoint.FourteenFlames, GOTWaypoint.Ibbish, GOTWaypoint.Samyriana.info(0, -0.3), GOTWaypoint.Bhorash.info(0, -0.3), GOTWaypoint.Bayasabhad.info(0, -0.3), GOTWaypoint.ArNoy.info(0.3, 0), GOTWaypoint.Adakhakileki, GOTWaypoint.CastleLychester.info(0, -0.3), GOTWaypoint.MhysaFaer, GOTWaypoint.AquosDhaen, GOTWaypoint.Draconys, GOTWaypoint.Tyria, GOTWaypoint.Rhyos, GOTWaypoint.Oros, GOTWaypoint.VulturesRoost, GOTWaypoint.Spicetown, GOTWaypoint.Castamere, GOTWaypoint.Goldenhill, GOTWaypoint.GreyironCastle, GOTWaypoint.HoareCastle, GOTWaypoint.HoareKeep, GOTWaypoint.HoggHall, GOTWaypoint.HollardCastle.info(-0.3, 0), GOTWaypoint.OldStones, GOTWaypoint.Summerhall.info(0.3, 0), GOTWaypoint.TarbeckHall, GOTWaypoint.TowerOfJoy.info(0.3, 0), GOTWaypoint.Whispers, GOTWaypoint.WhiteWalls.info(0.3, 0));
		registerLocation(new GOTStructureRuinsBig(biome, 0.0f), GOTWaypoint.EastBay, GOTWaypoint.EastCoast, GOTWaypoint.NorthForests, GOTWaypoint.WhiteMountains, GOTWaypoint.CentralForests, GOTWaypoint.Marshes, GOTWaypoint.RedForests, GOTWaypoint.SouthUlthos, GOTWaypoint.SouthTaiga, GOTWaypoint.Bonetown, GOTWaypoint.Harrenhal, GOTWaypoint.Stygai, GOTWaypoint.Ulos, GOTWaypoint.Yeen.info(0.3, 0.3));
		registerLocation(new GOTStructureSothoryosSettlement(biome, 0.0f), GOTWaypoint.Maunga, GOTWaypoint.DragonPlace, GOTWaypoint.SouthPoint, GOTWaypoint.BigLake);
		registerLocation(new GOTStructureSothoryosSettlement(biome, 0.0f).setIsPyramid(), GOTWaypoint.Raumati);
		registerLocation(new GOTStructureStormlandsSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.CrowsNest, GOTWaypoint.GriffinsRoost, GOTWaypoint.Blackhaven.info(-0.5, 0), GOTWaypoint.Bronzegate.info(0, 0.6), GOTWaypoint.DeatsHear, GOTWaypoint.EvenfallHall, GOTWaypoint.Fawntown, GOTWaypoint.Amberly, GOTWaypoint.BlackHeart, GOTWaypoint.BroadArch, GOTWaypoint.Parchments, GOTWaypoint.Poddingfield, GOTWaypoint.RainHouse, GOTWaypoint.SeaworthCastle, GOTWaypoint.Stonehelm, GOTWaypoint.StormsEnd.info(0, 0.5), GOTWaypoint.TudburyHoll, GOTWaypoint.Gallowsgrey, GOTWaypoint.Grandview, GOTWaypoint.Greenstone, GOTWaypoint.HarvestHall, GOTWaypoint.HaystackHall, GOTWaypoint.Mistwood, GOTWaypoint.Felwood.info(0, 0.6), GOTWaypoint.Nightsong.info(0, 0.6));
		registerLocation(new GOTStructureStormlandsSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.WeepingTown);
		registerLocation(new GOTStructureSummerSettlement(biome, 0.0f), GOTWaypoint.Hauauru, GOTWaypoint.Matahau, GOTWaypoint.Takutai, GOTWaypoint.Ataahua, GOTWaypoint.Pereki, GOTWaypoint.Ngarara, GOTWaypoint.Tauranga, GOTWaypoint.Matao, GOTWaypoint.Ngahere, GOTWaypoint.Kohuru, GOTWaypoint.Doquu, GOTWaypoint.Ebonhead, GOTWaypoint.GoldenHead, GOTWaypoint.Koj, GOTWaypoint.LastLament, GOTWaypoint.LizardHead, GOTWaypoint.LotusPoint, GOTWaypoint.Naath, GOTWaypoint.Omboru, GOTWaypoint.PearlPalace, GOTWaypoint.RedFlowerVale, GOTWaypoint.SweetLotusVale, GOTWaypoint.TallTreesTown, GOTWaypoint.Walano, GOTWaypoint.Xon);
		registerLocation(new GOTStructureTower(biome, 0.0f), GOTWaypoint.ThreeTowers.info(-0.5, 0, 1), GOTWaypoint.ThreeTowers.info(-0.5, -0.5, 1), GOTWaypoint.ThreeTowers.info(-0.5, 0.5, 1), GOTWaypoint.TowerOfGlimmering, GOTWaypoint.BaelishKeep, GOTWaypoint.HightowerLitehouse, GOTWaypoint.RamseyTower, GOTWaypoint.Standfast, GOTWaypoint.TwinsLeft.info(0, 0, 2), GOTWaypoint.TwinsRight);
		registerLocation(new GOTStructureTyroshSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Tyrosh);
		registerLocation(new GOTStructureVolantisSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Elyria, GOTWaypoint.Tolos, GOTWaypoint.LittleValyria.info(0, 1, 0), GOTWaypoint.Mantarys.info(0, -1, 2), GOTWaypoint.Selhorys.info(-1, 0, 1), GOTWaypoint.Valysar.info(-1, 0, 1), GOTWaypoint.Volantis.info(-1, 0, 1), GOTWaypoint.VolonTherys.info(0, 1, 0));
		registerLocation(new GOTStructureWesterlandsSettlement(biome, 0.0f), GOTWaypoint.Oxcross.info(-0.2, 0.6));
		registerLocation(new GOTStructureWesterlandsSettlement(biome, 0.0f).setIsCastle(), GOTWaypoint.CasterlyRock.info(-0.5, 0), GOTWaypoint.Crakehall.info(-0.5, 0), GOTWaypoint.GoldenTooth.info(0, 0.5), GOTWaypoint.Kayce.info(1, 0, 0), GOTWaypoint.Sarsfield.info(0, -0.5), GOTWaypoint.Silverhill, GOTWaypoint.Wyndhall, GOTWaypoint.Plumwood, GOTWaypoint.Riverspring, GOTWaypoint.Greenfield, GOTWaypoint.Hornvale, GOTWaypoint.DeepDen, GOTWaypoint.Faircastle, GOTWaypoint.Feastfires, GOTWaypoint.CleganesKeep, GOTWaypoint.Cornfield, GOTWaypoint.Crag, GOTWaypoint.Ashemark, GOTWaypoint.Banefort);
		registerLocation(new GOTStructureWesterlandsSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Kayce.info(0, 0, 3), GOTWaypoint.Lannisport.info(-0.9, 0, 3));
		registerLocation(new GOTStructureWildlingSettlement(biome, 0.0f).setIsCraster(), GOTWaypoint.CrastersKeep);
		registerLocation(new GOTStructureWildlingSettlement(biome, 0.0f).setIsHardhome(), GOTWaypoint.Hardhome);
		registerLocation(new GOTStructureYiTiSettlement(biome, 0.0f).setIsTower(), GOTWaypoint.FiveForts1, GOTWaypoint.FiveForts2, GOTWaypoint.FiveForts3, GOTWaypoint.FiveForts4, GOTWaypoint.FiveForts5.info(0, 0, 3));
		registerLocation(new GOTStructureYiTiSettlement(biome, 0.0f).setIsTown(), GOTWaypoint.Asabhad.info(-1, 0, 3), GOTWaypoint.Baoji.info(0, 1, 2), GOTWaypoint.Eijiang.info(0, 1, 2), GOTWaypoint.Jinqi.info(-1, 0, 3), GOTWaypoint.Lizhao.info(1.1, 0, 1), GOTWaypoint.Manjin.info(1, 0, 1), GOTWaypoint.SiQo.info(1, 0, 1), GOTWaypoint.Tiqui.info(0, -1, 0), GOTWaypoint.TraderTown.info(0, -1, 0), GOTWaypoint.Vaibei.info(0, -1, 0), GOTWaypoint.Yibin.info(-1, 0, 3), GOTWaypoint.Yin.info(0, 1, 2), GOTWaypoint.Yunnan.info(1, 0, 1), GOTWaypoint.Zabhad, GOTWaypoint.Turrani, GOTWaypoint.Vahar, GOTWaypoint.Faros, GOTWaypoint.Huiji, GOTWaypoint.LengMa, GOTWaypoint.LengYi, GOTWaypoint.LesserMoraq, GOTWaypoint.Marahai, GOTWaypoint.PortMoraq);
		registerLocation(new GOTStructureYiTiSettlement(biome, 0.0f).setIsWall(false), GOTWaypoint.Jianmen, GOTWaypoint.Anguo, GOTWaypoint.Dingguo, GOTWaypoint.Pinnu, GOTWaypoint.Pingjiang, GOTWaypoint.Wude, GOTWaypoint.Wusheng, GOTWaypoint.Zhenguo, GOTWaypoint.Lungmen);
		registerLocation(new GOTStructureYiTiSettlement(biome, 0.0f).setIsWall(true), GOTWaypoint.Anjiang);

		for (GOTStructureBaseSettlement element : locations) {
			biome.decorator.addFixedSettlement(element);
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

		structures.put(GOTWaypoint.Astapor.info(-1, 0), new Spawner() {
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

		structures.put(GOTWaypoint.Barrowtown.info(0, 1), new Spawner() {
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

		structures.put(GOTWaypoint.Braavos.info(0, -1), new Spawner() {
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

		structures.put(GOTWaypoint.Dreadfort.info(0, 0.5), new Spawner() {
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

		structures.put(GOTWaypoint.GoldenTooth.info(0, 0.5), new Spawner() {
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

		structures.put(GOTWaypoint.Highgarden.info(0, -1), new Spawner() {
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

		structures.put(GOTWaypoint.HornHill.info(0, 0.5), new Spawner() {
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

		structures.put(GOTWaypoint.Maidenpool.info(0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityWilliamMooton(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.Meereen.info(-1, 0), new Spawner() {
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

		structures.put(GOTWaypoint.Myr.info(-1, 0), new Spawner() {
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

		structures.put(GOTWaypoint.Norvos.info(0, -1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMellario(world), world, 0, 1, 1);
			}
		});

		structures.put(GOTWaypoint.Oldtown.info(-0.9, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityEbrose(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.Pentos.info(-1, 0), new Spawner() {
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

		structures.put(GOTWaypoint.Qarth.info(0, 1), new Spawner() {
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

		structures.put(GOTWaypoint.RisvellsCastle.info(0, 0.5), new Spawner() {
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

		structures.put(GOTWaypoint.Starfall.info(0, -0.5), new Spawner() {
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

		structures.put(GOTWaypoint.StoneHedge.info(0, 0.5), new Spawner() {
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
				spawnLegendaryNPC(new GOTEntityThreeEyedRaven(world), world, 0, 1, 0);
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

		structures.put(GOTWaypoint.Volantis.info(-1, 0), new Spawner() {
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

		structures.put(GOTWaypoint.Yunkai.info(-1, 0), new Spawner() {
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
			setOriginAndRotation(world, i, j, k, rotation, 0);
			spawnLegendaryNPC(world);
			return true;
		}

		public void spawnLegendaryNPC(World world) {
		}
	}
}