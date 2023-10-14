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
import got.common.entity.other.GOTEntityNPC;
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

import static got.common.world.map.GOTCoordConverter.toEssosTownGate;

public class GOTFixer {
	public static Map<GOTAbstractWaypoint, GOTStructureBaseSettlement> spawners = new HashMap<>();
	public static Collection<GOTStructureBaseSettlement> locations = new HashSet<>();

	public static void addSpecialLocations(World world, Random random, int i, int k) {
		GOTWaypoint[] forts = {GOTWaypoint.FIVE_FORTS_1, GOTWaypoint.FIVE_FORTS_2, GOTWaypoint.FIVE_FORTS_3, GOTWaypoint.FIVE_FORTS_4, GOTWaypoint.FIVE_FORTS_5};
		for (GOTWaypoint wp : forts) {
			new GOTFiveFortsWall(false, wp).generate(world, random, i, 0, k, 0);
		}
		GOTWorldGenPartyTrees worldGen = ((GOTWorldGenPartyTrees) GOTTreeType.WEIRWOOD.create(false, random)).disableRestrictions();
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.WINTERFELL.info(-0.5, -0.1, Dir.WEST))) {
			worldGen.generate(world, random, i - 50, world.getTopSolidOrLiquidBlock(i - 50, k), k);
		}
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.WHITETREE)) {
			worldGen.generate(world, random, i, world.getTopSolidOrLiquidBlock(i, k - 15), k - 15);
		}
	}

	public static void onInit() {
		registerLocation(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.BLOODY_GATE.info(0, -0.5), GOTWaypoint.COLDWATER_BURN, GOTWaypoint.GREY_GLEN, GOTWaypoint.OLD_ANCHOR, GOTWaypoint.PEBBLE, GOTWaypoint.SNAKEWOOD, GOTWaypoint.THE_PAPS, GOTWaypoint.WICKENDEN, GOTWaypoint.WITCH_ISLE, GOTWaypoint.GATE_OF_THE_MOON.info(0.5, 0, Dir.EAST));
		registerLocation(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureArrynSettlement.Type.TOWN, 6), GOTWaypoint.SISTERTON);
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.FORT, 3), GOTWaypoint.ANTLERS.info(0.1, -0.5), GOTWaypoint.BROWNHOLLOW, GOTWaypoint.DYRE_DEN, GOTWaypoint.STOKEWORTH.info(-0.4, 0, Dir.WEST), GOTWaypoint.HAYFORD.info(0.1, -0.5), GOTWaypoint.ROOKS_REST.info(0, -0.4), GOTWaypoint.ROSBY.info(-0.5, -0.1, Dir.WEST));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.RED_KEEP, 3), GOTWaypoint.KINGS_LANDING.info(2, 0, Dir.EAST));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.TOWN, 6), GOTWaypoint.DUSKENDALE.info(-0.1, -1.1));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.VILLAGE, 6), GOTWaypoint.BRIARWHITE.info(0, -0.7), GOTWaypoint.ROSBY.info(0.7, 0));
		registerLocation(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.BLACKMONT.info(0.5, 0, Dir.EAST), GOTWaypoint.GHOST_HILL, GOTWaypoint.GODSGRACE, GOTWaypoint.THE_TOR, GOTWaypoint.VAITH, GOTWaypoint.WATER_GARDENS, GOTWaypoint.SPOTTSWOOD, GOTWaypoint.LEMONWOOD, GOTWaypoint.SALT_SHORE, GOTWaypoint.KINGSGRAVE.info(0.5, 0, Dir.EAST), GOTWaypoint.WYL.info(-0.55, 0, Dir.WEST));
		registerLocation(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDorneSettlement.Type.TOWN, 6), GOTWaypoint.GHASTON_GREY, GOTWaypoint.PLANKY_TOWN);
		registerLocation(new GOTStructureDothrakiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDothrakiSettlement.Type.BIG, 5), GOTWaypoint.HORNOTH.info(0.5, 0), GOTWaypoint.KYTH.info(0.3, -0.4), GOTWaypoint.SATHAR.info(0.5, 0), GOTWaypoint.RATHYLAR.info(0.6, -0.2), GOTWaypoint.SAATH.info(0, -0.5), GOTWaypoint.VAES_ATHJIKHARI.info(0, -0.5), GOTWaypoint.VAES_DIAF, GOTWaypoint.VAES_DOTHRAK, GOTWaypoint.VAES_GORQOYI.info(0.3, -0.4), GOTWaypoint.VAES_GRADDAKH, GOTWaypoint.VAES_JINI.info(0, 0.5), GOTWaypoint.VOJJOR_SAMVI.info(0, -0.5), GOTWaypoint.VAES_KHADOKH.info(-0.1, -0.5), GOTWaypoint.VAES_KHEWO.info(0, -0.5), GOTWaypoint.VAES_LEQSE.info(0.5, 0), GOTWaypoint.VAES_MEJHAH.info(0, -0.5), GOTWaypoint.KRAZAAJ_HAS.info(0, -0.6));
		registerLocation(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDragonstoneSettlement.Type.FORT, 3), GOTWaypoint.HIGH_TIDE, GOTWaypoint.SHARP_POINT, GOTWaypoint.STONEDANCE, GOTWaypoint.SWEETPORT_SOUND);
		registerLocation(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDragonstoneSettlement.Type.TOWN, 6), GOTWaypoint.HULL);
		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.COLONY, 4), GOTWaypoint.ISLE_OF_WHIPS, GOTWaypoint.BARTER_BEACH, GOTWaypoint.GOGOSSOS, GOTWaypoint.GOROSH, GOTWaypoint.ZAMETTAR.info(0, -0.5, Dir.SOUTH));
		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.PYRAMID, 3), GOTWaypoint.MEEREEN.info(toEssosTownGate(0.0, true), -1.5), GOTWaypoint.ASTAPOR.info(-1.5, toEssosTownGate(0.0, false)), GOTWaypoint.NEW_GHIS.info(-1, 0), GOTWaypoint.YUNKAI.info(-1.5, toEssosTownGate(0.0, false)));
		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.TOWN, 6), GOTWaypoint.NEW_GHIS);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.ABANDONED, 2), GOTWaypoint.GREENGUARD, GOTWaypoint.THE_TORCHES, GOTWaypoint.THE_LONG_BARROW, GOTWaypoint.RIMEGATE, GOTWaypoint.SABLE_HALL, GOTWaypoint.WOODSWATCH, GOTWaypoint.NIGHTFORT, GOTWaypoint.DEEP_LAKE, GOTWaypoint.OAKENSHIELD, GOTWaypoint.ICEMARK, GOTWaypoint.HOARFROST_HILL, GOTWaypoint.STONEDOOR, GOTWaypoint.GREYGUARD, GOTWaypoint.QUEENSGATE, GOTWaypoint.SENTINEL_STAND);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.CASTLE_BLACK, 2), GOTWaypoint.CASTLE_BLACK);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.EAST_WATCH, 2), GOTWaypoint.EASTWATCH);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.SHADOW_TOWER, 2), GOTWaypoint.SHADOW_TOWER);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.VILLAGE, 4), GOTWaypoint.MOLETOWN, GOTWaypoint.QUEENSCROWN);
		registerLocation(new GOTStructureIbbenSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIbbenSettlement.Type.FORT, 6), GOTWaypoint.IB_NOR, GOTWaypoint.IB_SAR, GOTWaypoint.NEW_IBBISH, GOTWaypoint.PORT_OF_IBBEN);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.CAMP, 5), GOTWaypoint.VICTARION_LANDING);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.CATFISH_ROCK, GOTWaypoint.CORPSE_LAKE, GOTWaypoint.CROW_SPIKE_KEEP, GOTWaypoint.DOWNDELVING, GOTWaypoint.HARLAW_HALL, GOTWaypoint.HARRIDAN_HILL, GOTWaypoint.IRON_HOLT, GOTWaypoint.MYRE_CASTLE, GOTWaypoint.ORKWOOD, GOTWaypoint.SALTCLIFFE, GOTWaypoint.SEALSKIN_POINT, GOTWaypoint.SHATTERSTONE, GOTWaypoint.SPARR_CASTLE, GOTWaypoint.STONEHOUSE, GOTWaypoint.STONETREE_CASTLE, GOTWaypoint.SUNDERLY, GOTWaypoint.TAWNEY_CASTLE);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.VILLAGE, 6), GOTWaypoint.PEBBLETON);
		registerLocation(new GOTStructureLhazarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureLhazarSettlement.Type.TOWN, 6), GOTWaypoint.HESH.info(1.2, 0, Dir.WEST), GOTWaypoint.KOSRAK.info(1, 0, Dir.WEST), GOTWaypoint.LHAZOSH.info(1, 0, Dir.WEST));
		registerLocation(new GOTStructureLorathSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureLorathSettlement.Type.TOWN, 6), GOTWaypoint.LORATH, GOTWaypoint.MOROSH);
		registerLocation(new GOTStructureMossovySettlement(GOTBiome.ocean, 0.0f).type(GOTStructureMossovySettlement.Type.VILLAGE, 6), GOTWaypoint.KUURULGAN, GOTWaypoint.SUUDAN_KORKUU, GOTWaypoint.KANDUU_BET, GOTWaypoint.TASHTOO, GOTWaypoint.BASHKARUUCHU, GOTWaypoint.AZUU_KAN, GOTWaypoint.KUJRUK, GOTWaypoint.KORKUNUCHTUU, GOTWaypoint.NYMDUU_OOZ, GOTWaypoint.AZHYDAAR, GOTWaypoint.AK_SHAAR, GOTWaypoint.SUU_NYM, GOTWaypoint.SHYLUUN_UUSU, GOTWaypoint.KADAR, GOTWaypoint.NEFER, GOTWaypoint.K_DATH);
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.CATFISH_ROCK, GOTWaypoint.BREAKSTONE_HILL, GOTWaypoint.GOLDGRASS.info(0, 0.4, Dir.SOUTH), GOTWaypoint.BLACK_POOL, GOTWaypoint.DEEPWOOD_MOTTE, GOTWaypoint.FLINTS_FINGER, GOTWaypoint.HIGHPOINT, GOTWaypoint.WIDOWS_WATCH, GOTWaypoint.HORNWOOD, GOTWaypoint.IRONRATH, GOTWaypoint.MOAT_KAILIN, GOTWaypoint.OLDCASTLE, GOTWaypoint.RAMSGATE, GOTWaypoint.RILLWATER_CROSSING.info(-0.4, 0, Dir.WEST));
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.HILLMAN, 6), GOTWaypoint.SKANE, GOTWaypoint.DEEPDOWN, GOTWaypoint.DRIFTWOOD_HALL, GOTWaypoint.KINGSHOUSE);
		registerLocation(new GOTStructurePentosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructurePentosSettlement.Type.VILLAGE, 5), GOTWaypoint.GHOYAN_DROHE.info(0, 0.7));
		registerLocation(new GOTStructureQarthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQarthSettlement.Type.COLONY, 4), GOTWaypoint.TERIMAN, GOTWaypoint.BATARGAS, GOTWaypoint.KARIMAGAR);
		registerLocation(new GOTStructureQarthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQarthSettlement.Type.TOWN, 6), GOTWaypoint.PORT_YHOS.info(toEssosTownGate(0.0, false), 0.5), GOTWaypoint.QARKASH.info(toEssosTownGate(0.0, false), 0.5));
		registerLocation(new GOTStructureQohorSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQohorSettlement.Type.TOWN, 6), GOTWaypoint.QOHOR.info(-0.6, toEssosTownGate(0.0, false), Dir.EAST));
		registerLocation(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.APPLETON.info(0, -0.51), GOTWaypoint.ASHFORD, GOTWaypoint.BANDALLON, GOTWaypoint.GRASSY_VALE, GOTWaypoint.GRIMSTON, GOTWaypoint.HAMMERHAL.info(0.5, 0, Dir.EAST), GOTWaypoint.RED_LAKE.info(0, 0.5, Dir.SOUTH), GOTWaypoint.SOUTHSHIELD, GOTWaypoint.UPLANDS, GOTWaypoint.HOLYHALL.info(0.5, 0, Dir.EAST), GOTWaypoint.HONEYHOLT, GOTWaypoint.IVY_HALL.info(0, 0.5, Dir.SOUTH), GOTWaypoint.NEW_BARREL, GOTWaypoint.BLACKCROWN, GOTWaypoint.CIDER_HALL, GOTWaypoint.COLDMOAT.info(0, 0.5, Dir.SOUTH), GOTWaypoint.DARKDELL.info(-0.5, 0, Dir.WEST), GOTWaypoint.DUNSTONBURY, GOTWaypoint.BITTERBRIDGE.info(0, 0.5, Dir.SOUTH), GOTWaypoint.GARNETGROVE.info(-0.5, 0, Dir.WEST), GOTWaypoint.LORD_HEWETTS_TOWN, GOTWaypoint.OLD_OAK.info(0, 0.5, Dir.SOUTH), GOTWaypoint.SUNHOUSE.info(0, 0.5, Dir.SOUTH), GOTWaypoint.WHITEGROVE.info(-0.5, 0, Dir.WEST));
		registerLocation(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureReachSettlement.Type.TOWN, 6), GOTWaypoint.APPLETON.info(0.1, 1.1, Dir.SOUTH), GOTWaypoint.ASHFORD, GOTWaypoint.LORD_HEWETTS_TOWN, GOTWaypoint.SMITHYTON.info(0, 0.9, Dir.SOUTH), GOTWaypoint.VINETOWN, GOTWaypoint.RYAMSPORT, GOTWaypoint.TUMBLETON.info(0, -1.0));
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.CROSSROADS, 2), GOTWaypoint.CROSSROADS_INN);
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.DARRY, GOTWaypoint.WAYFARERS_REST.info(-0.4, 0, Dir.WEST), GOTWaypoint.ACORN_HALL.info(-0.5, -0.1, Dir.WEST), GOTWaypoint.ATRANTA);
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.TOWN, 6), GOTWaypoint.HARROWAY.info(0, 0.9, Dir.SOUTH), GOTWaypoint.MAIDENPOOL.info(0, 0.9, Dir.SOUTH), GOTWaypoint.SALTPANS.info(0.8, 0, Dir.EAST), GOTWaypoint.STONEY_SEPT.info(-0.8, 0, Dir.WEST), GOTWaypoint.SEAGARD.info(0, 0.8, Dir.SOUTH));
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.VILLAGE, 6), GOTWaypoint.FAIRMARKET, GOTWaypoint.PENNYTREE, GOTWaypoint.SEVENSTREAMS);
		registerLocation(new GOTStructureRuins(GOTBiome.ocean, 0.0f), GOTWaypoint.WESTWATCH.info(-0.3, 0), GOTWaypoint.VAES_LEISI.info(0, 0.3), GOTWaypoint.MORNE.info(0, 0.3), GOTWaypoint.NY_SAR.info(0, 0.15), GOTWaypoint.OLD_GHIS.info(0, 0.3), GOTWaypoint.SAR_MELL.info(0, -0.25), GOTWaypoint.SARHOY.info(0, 0.3), GOTWaypoint.SHANDYSTONE.info(0, 0.3), GOTWaypoint.STARPIKE.info(0, 0.3), GOTWaypoint.TELYRIA.info(0, 0.3), GOTWaypoint.TORTURERS_DEEP.info(0, 0.3), GOTWaypoint.VAES_ORVIK.info(0.3, 0), GOTWaypoint.VAES_QOSAR.info(0, 0.3), GOTWaypoint.VAES_SHIROSI.info(0, 0.3), GOTWaypoint.VAES_TOLORRO.info(0, 0.3), GOTWaypoint.VALYRIAN_ROAD.info(-0.2, 0), GOTWaypoint.VELOS.info(0, 0.3), GOTWaypoint.GREY_GALLOWS.info(0, 0.3), GOTWaypoint.BLOODSTONE.info(0, 0.3), GOTWaypoint.ANOGARIA.info(0, 0.2), GOTWaypoint.KAYAKAYANAYA.info(0, 0.3), GOTWaypoint.AEGON.info(0, 0.3), GOTWaypoint.RAENYS.info(0, 0.3), GOTWaypoint.VISENYA.info(0, 0.3), GOTWaypoint.GHOZAI.info(0, 0.3), GOTWaypoint.CHROYANE.info(0.3, 0), GOTWaypoint.FOURTEEN_FLAMES.info(0, 0.3), GOTWaypoint.IBBISH.info(0, 0.3), GOTWaypoint.SAMYRIANA.info(0, 0.3), GOTWaypoint.BHORASH.info(0, 0.2), GOTWaypoint.BAYASABHAD.info(0, 0.3), GOTWaypoint.AR_NOY.info(-0.1, -0.25), GOTWaypoint.ADAKHAKILEKI.info(0, 0.3), GOTWaypoint.CASTLE_LYCHESTER.info(0, -0.3), GOTWaypoint.MHYSA_FAER.info(0, 0.3), GOTWaypoint.AQUOS_DHAEN.info(0, 0.3), GOTWaypoint.DRACONYS.info(0, 0.3), GOTWaypoint.TYRIA.info(0, 0.3), GOTWaypoint.RHYOS.info(0, 0.3), GOTWaypoint.OROS.info(0, 0.2), GOTWaypoint.VULTURES_ROOST.info(0, 0.3), GOTWaypoint.SPICETOWN.info(0, 0.3), GOTWaypoint.CASTAMERE.info(0, 0.3), GOTWaypoint.GOLDENHILL.info(0, 0.3), GOTWaypoint.GREYIRON_CASTLE.info(0, 0.3), GOTWaypoint.HOARE_CASTLE.info(0, 0.3), GOTWaypoint.HOARE_KEEP.info(0, 0.3), GOTWaypoint.HOGG_HALL.info(0.2, -0.2), GOTWaypoint.HOLLARD_CASTLE.info(0.3, 0), GOTWaypoint.OLDSTONES.info(0, 0.3), GOTWaypoint.SUMMERHALL.info(-0.3, 0), GOTWaypoint.TARBECK_HALL.info(0, 0.3), GOTWaypoint.TOWER_OF_JOY.info(-0.3, 0), GOTWaypoint.WHISPERS.info(0, 0.3), GOTWaypoint.WHITEWALLS.info(0.2, -0.2));
		registerLocation(new GOTStructureRuinsBig(GOTBiome.ocean, 0.0f), GOTWaypoint.EAST_BAY.info(0, 0.4), GOTWaypoint.EAST_COAST.info(0, 0.4), GOTWaypoint.NORTH_FORESTS.info(0, 0.4), GOTWaypoint.WHITE_MOUNTAINS.info(0, 0.4), GOTWaypoint.CENTRAL_FORESTS.info(0, 0.4), GOTWaypoint.MARSHES.info(0, 0.4), GOTWaypoint.RED_FORESTS.info(0, 0.4), GOTWaypoint.SOUTH_ULTHOS.info(0, 0.4), GOTWaypoint.SOUTH_TAIGA.info(0, 0.4), GOTWaypoint.BONETOWN.info(0, 0.4), GOTWaypoint.HARRENHAL.info(-0.4, 0), GOTWaypoint.STYGAI.info(0, 0.4), GOTWaypoint.ULOS.info(0, 0.4), GOTWaypoint.YEEN.info(0, 0.4));
		registerLocation(new GOTStructureSothoryosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSothoryosSettlement.Type.PYRAMID, 3), GOTWaypoint.RAUMATI);
		registerLocation(new GOTStructureSothoryosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSothoryosSettlement.Type.VILLAGE, 3), GOTWaypoint.MAUNGA, GOTWaypoint.DRAGON_PLACE, GOTWaypoint.SOUTH_POINT, GOTWaypoint.BIG_LAKE);
		registerLocation(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.CROWS_NEST, GOTWaypoint.GRIFFINS_ROOST, GOTWaypoint.BLACKHAVEN.info(-0.5, 0, Dir.WEST), GOTWaypoint.BRONZEGATE.info(0, -0.5), GOTWaypoint.DEAD_HEAD, GOTWaypoint.FAWNTOWN, GOTWaypoint.AMBERLY, GOTWaypoint.BLACK_HEART, GOTWaypoint.BROAD_ARCH, GOTWaypoint.PARCHMENTS.info(0, -0.5), GOTWaypoint.PODDINGFIELD.info(0, -0.5), GOTWaypoint.RAIN_HOUSE, GOTWaypoint.SEAWORTH_CASTLE, GOTWaypoint.TUDBURY_HOLL, GOTWaypoint.GALLOWSGREY.info(0, -0.5), GOTWaypoint.GRANDVIEW.info(-0.5, 0, Dir.WEST), GOTWaypoint.HARVEST_HALL.info(0, -0.5), GOTWaypoint.HAYSTACK_HALL.info(0, -0.5), GOTWaypoint.MISTWOOD, GOTWaypoint.FELWOOD.info(0, -0.5), GOTWaypoint.NIGHTSONG.info(0, -0.5));
		registerLocation(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureStormlandsSettlement.Type.TOWN, 6), GOTWaypoint.WEEPING_TOWN);
		registerLocation(new GOTStructureSummerSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSummerSettlement.Type.FORT, 3), GOTWaypoint.HAUAURU, GOTWaypoint.MATAHAU, GOTWaypoint.TAKUTAI, GOTWaypoint.ATAAHUA, GOTWaypoint.PEREKI, GOTWaypoint.NGARARA, GOTWaypoint.TAURANGA, GOTWaypoint.MATAO, GOTWaypoint.NGAHERE, GOTWaypoint.KOHURU);
		registerLocation(new GOTStructureSummerSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSummerSettlement.Type.VILLAGE, 5), GOTWaypoint.DOQUU, GOTWaypoint.EBONHEAD, GOTWaypoint.GOLDEN_HEAD, GOTWaypoint.KOJ, GOTWaypoint.LAST_LAMENT, GOTWaypoint.LIZARD_HEAD, GOTWaypoint.LOTUS_POINT, GOTWaypoint.NAATH, GOTWaypoint.OMBORU, GOTWaypoint.PEARL_PALACE, GOTWaypoint.RED_FLOWER_VALE, GOTWaypoint.SWEET_LOTUS_VALE, GOTWaypoint.TALL_TREES_TOWN, GOTWaypoint.WALANO, GOTWaypoint.XON);
		registerLocation(new GOTStructureTower(GOTBiome.ocean, 0.0f), GOTWaypoint.THREE_TOWERS.info(-0.5, -0.5, Dir.WEST), GOTWaypoint.THREE_TOWERS.info(-0.5, 0, Dir.WEST), GOTWaypoint.THREE_TOWERS.info(-0.5, 0.5, Dir.WEST), GOTWaypoint.TOWER_OF_GLIMMERING, GOTWaypoint.BAELISH_KEEP, GOTWaypoint.RAMSEY_TOWER, GOTWaypoint.STANDFAST);
		registerLocation(new GOTStructureVolantisSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureVolantisSettlement.Type.TOWN, 6), GOTWaypoint.ELYRIA, GOTWaypoint.TOLOS.info(toEssosTownGate(0.0, false), 0.5), GOTWaypoint.LITTLE_VALYRIA.info(toEssosTownGate(0.0, false) + 0.26, 0.6), GOTWaypoint.MANTARYS.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH), GOTWaypoint.SELHORYS.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST), GOTWaypoint.VALYSAR.info(-0.6, toEssosTownGate(0.0, false) + 0.25, Dir.EAST), GOTWaypoint.VOLON_THERYS.info(toEssosTownGate(0.0, false), 0.5));
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.KAYCE, GOTWaypoint.SARSFIELD.info(-0.1, -0.5), GOTWaypoint.SILVERHILL, GOTWaypoint.WYNDHALL, GOTWaypoint.PLUMWOOD, GOTWaypoint.RIVERSPRING, GOTWaypoint.GREENFIELD, GOTWaypoint.DEEP_DEN, GOTWaypoint.CRAG);
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.TOWN, 6), GOTWaypoint.KAYCE);
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.VILLAGE, 6), GOTWaypoint.OXCROSS.info(0.2, 0.6));
		registerLocation(new GOTStructureWildlingSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWildlingSettlement.Type.CRASTER, 1), GOTWaypoint.CRASTERS_KEEP);
		registerLocation(new GOTStructureWildlingSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWildlingSettlement.Type.HARDHOME, 7), GOTWaypoint.HARDHOME);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.GATE, 2), GOTWaypoint.JIANMEN, GOTWaypoint.ANGUO, GOTWaypoint.DINGGUO, GOTWaypoint.PINNU, GOTWaypoint.PINGJIANG, GOTWaypoint.WUDE, GOTWaypoint.WUSHENG, GOTWaypoint.ZHENGUO, GOTWaypoint.LUNGMEN);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.GATE_ROAD, 2), GOTWaypoint.ANJIANG);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.TOWER, 9), GOTWaypoint.FIVE_FORTS_1, GOTWaypoint.FIVE_FORTS_2, GOTWaypoint.FIVE_FORTS_3, GOTWaypoint.FIVE_FORTS_4, GOTWaypoint.FIVE_FORTS_5);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.TOWN, 7), GOTWaypoint.ASABHAD.info(-1, 0, Dir.WEST), GOTWaypoint.BAOJI.info(0, -1), GOTWaypoint.EIJIANG.info(0, 1, Dir.SOUTH), GOTWaypoint.JINQI.info(-1, 0, Dir.WEST), GOTWaypoint.LIZHAO.info(1, 0, Dir.EAST), GOTWaypoint.MANJIN.info(1.1, -0.2, Dir.EAST), GOTWaypoint.SI_QO.info(1, 0, Dir.EAST), GOTWaypoint.TIQUI.info(0, -1), GOTWaypoint.TRADER_TOWN.info(0, -1), GOTWaypoint.VAIBEI.info(0, -1), GOTWaypoint.YIBIN.info(0, -1), GOTWaypoint.YUNNAN.info(1, 0, Dir.EAST), GOTWaypoint.CHANGAN.info(1, 0, Dir.EAST), GOTWaypoint.FU_NING.info(1.1, 0.1, Dir.EAST), GOTWaypoint.ZABHAD, GOTWaypoint.TURRANI, GOTWaypoint.VAHAR, GOTWaypoint.FAROS, GOTWaypoint.HUIJI, GOTWaypoint.LENG_MA, GOTWaypoint.LENG_YI, GOTWaypoint.LESSER_MORAQ, GOTWaypoint.MARAHAI, GOTWaypoint.PORT_MORAQ);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityAddamMarbrand(world), 0, 2));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.ASHEMARK);

		registerSpawner(new GOTStructureAsshaiSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityAsshaiArchmag(world), 0, 0));
			}
		}, GOTWaypoint.ASSHAI.info(0, 0.8, Dir.SOUTH));

		registerSpawner(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityKraznysMoNakloz(world), -1, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityMissandei(world), -1, -1));
				spawnInfos.add(new SpawnInfo(new GOTEntityGreyWorm(world), -1, 1));
			}
		}.type(GOTStructureGhiscarSettlement.Type.TOWN, 6), GOTWaypoint.ASTAPOR.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityQuentenBanefort(world), 0, 2));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.BANEFORT);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityBarbreyDustin(world), 0, 3));
			}
		}.type(GOTStructureNorthSettlement.Type.SMALL_TOWN, 6), GOTWaypoint.BARROWTOWN);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityBaelorBlacktyde(world), -2, -2));
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.BLACKTYDE);

		registerSpawner(new GOTStructureBraavosSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityTychoNestoris(world), 0, 1));
			}
		}.type(GOTStructureBraavosSettlement.Type.TOWN, 6), GOTWaypoint.BRAAVOS.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH));

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityGarlanTyrell(world), 2, -2));
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.BRIGHTWATER_KEEP);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityTywinLannister(world), 2, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityQyburn(world), -2, 0));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.CASTERLY_ROCK, 3), GOTWaypoint.CASTERLY_ROCK.info(-0.4, 0, Dir.WEST));

		registerSpawner(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityArdrianCeltigar(world), 0, 2));
			}
		}.type(GOTStructureDragonstoneSettlement.Type.FORT, 3), GOTWaypoint.CLAW_ISLE);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityGregorClegane.GregorCleganeAlive(world), 2, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityPolliver(world), -2, 0));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.CLEGANES_KEEP);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityHarysSwyft(world), 0, 2));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.CORNFIELD);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityLyleCrakehall(world), 2, 2));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.CRAKEHALL.info(-0.5, 0, Dir.WEST));

		registerSpawner(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityStannisBaratheon(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityDavosSeaworth(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityMelisandra(world), -2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityShireenBaratheon(world), 2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntitySelyseBaratheon(world), 0, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityMatthosSeaworth(world), 0, -2));
			}
		}.type(GOTStructureDragonstoneSettlement.Type.FORT, 3), GOTWaypoint.DRAGONSTONE);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityRamsayBolton(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityRooseBolton(world), -2, -2));
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.DREADFORT.info(0, -0.4));

		registerSpawner(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityMonfordVelaryon(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityAuraneWaters(world), 2, 2));
			}
		}.type(GOTStructureDragonstoneSettlement.Type.FORT, 3), GOTWaypoint.DRIFTMARK);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityDunstanDrumm(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityAndrikTheUnsmilling(world), -2, -2));
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.DRUMM_CASTLE);

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityEuronGreyjoy(world), 0, 0));
			}

			@Override
			public GOTStructureBase getSpecialStructure() {
				return new GOTStructureBase() {
					@Override
					public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
						setOriginAndRotation(world, i, j, k, rotation, 0);
						loadStrScan("euron_ship");
						generateStrScan(world, random, 16, 27, -58);
						for (int l = 0; l < 10; ++l) {
							spawnLegendaryNPC(new GOTEntityIronbornSoldier(world), world, 0, 1, 0);
						}
						spawnLegendaryNPC(new GOTEntityEuronGreyjoy(world), world, 0, 1, 0);
						return true;
					}
				};
			}
		}, GOTWaypoint.EURON);

		registerSpawner(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntitySelwynTarth(world), 0, 2));
			}
		}.type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.EVENFALL_HALL);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntitySebastonFarman(world), 0, 2));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.FAIRCASTLE);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityForleyPrester(world), 0, 2));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.FEASTFIRES);

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityMathisRowan(world), 2, 2));
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.GOLDENGROVE);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityLeoLefford(world), 2, 2));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.GOLDEN_TOOTH.info(-0.1, -0.5));

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityMoribaldChester(world), 2, 0));
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.GREENSHIELD);

		registerSpawner(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityEldonEstermont(world), 0, 2));
			}
		}.type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.GREENSTONE);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityHarrasHarlaw(world), 0, 2));
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.GREY_GARDEN);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityHowlandReed(world), 0, 5));
			}
		}.type(GOTStructureNorthSettlement.Type.VILLAGE, 6), GOTWaypoint.GREYWATER_WATCH);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityGeroldGrafton(world), 3, 0));
			}
		}.type(GOTStructureArrynSettlement.Type.TOWN, 6), GOTWaypoint.GULLTOWN);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityGoroldGoodbrother(world), 0, 2));
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.HAMMERHORN);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityLynCorbray(world), 2, 2));
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.HEARTS_HOME);

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityHarmenUller(world), 0, 2));
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.HELLHOLT);

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityMaceTyrell(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityOlennaTyrell(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityMargaeryTyrell(world), 2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityWillasTyrell(world), -2, 2));
			}
		}.type(GOTStructureReachSettlement.Type.HIGHGARDEN, 3), GOTWaypoint.HIGHGARDEN.info(0.5, 0, Dir.EAST));

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityGeroldDayne(world), 2, -2));
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.HIGH_HERMITAGE.info(0.5, 0, Dir.EAST));

		registerSpawner(new GOTStructureTower(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityLeytonHightower(world), 0, -5));
			}
		}, GOTWaypoint.HIGHTOWER_LITEHOUSE);

		registerSpawner(new GOTStructureJogosSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityTugarKhan(world), 0, 3));
			}
		}.type(GOTStructureJogosSettlement.Type.BIG, 5), GOTWaypoint.HOJDBAATAR);

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityBericDondarrion.BericDondarrionLife1(world), 3, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityThoros(world), 0, 3));
			}
		}, GOTWaypoint.HOLLOW_HILL);

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityRandyllTarly(world), 0, 2));
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.HORN_HILL);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityTytosBrax(world), 0, 2));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.HORNVALE);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityHarroldHardyng(world), 0, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityAnyaWaynwood(world), 0, 2));
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.IRONOAKS);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityRickardKarstark(world), 0, 2));
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.KARHOLD.info(0.4, 0, Dir.EAST));

		registerSpawner(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntitySansaStark(world), 0, 5));
				spawnInfos.add(new SpawnInfo(new GOTEntityShae(world), 0, 6));
				spawnInfos.add(new SpawnInfo(new GOTEntityYoren(world), 0, 4));
			}
		}.type(GOTStructureCrownlandsSettlement.Type.KINGS_LANDING, 6), GOTWaypoint.KINGS_LANDING.info(0.9, 0, Dir.EAST));

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityKevanLannister(world), 0, 4));
				spawnInfos.add(new SpawnInfo(new GOTEntityDavenLannister(world), 0, -4));
				spawnInfos.add(new SpawnInfo(new GOTEntityAmoryLorch(world), 4, 0));
			}
		}.type(GOTStructureWesterlandsSettlement.Type.TOWN, 6), GOTWaypoint.LANNISPORT.info(-0.8, 0, Dir.WEST));

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityJohnUmber(world), 0, 2));
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.LAST_HEARTH);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityGylbertFarwynd(world), -2, -2));
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.LONELY_LIGHT);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityGilwoodHunter(world), 0, 2));
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.LONGBOW_HALL);

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityOrtonMerryweather(world), 0, 2));
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.LONGTABLE);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityDagmer(world), 0, 3));
			}
		}.type(GOTStructureIronbornSettlement.Type.TOWN, 6), GOTWaypoint.LORDSPORT);

		registerSpawner(new GOTStructureLysSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntitySalladhorSaan(world), 0, -1));
			}
		}.type(GOTStructureLysSettlement.Type.TOWN, 6), GOTWaypoint.LYS);

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityWilliamMooton(world), 0, 2));
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.MAIDENPOOL.info(0, -0.5));

		registerSpawner(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityHizdahrZoLoraq(world), -1, -1));
			}
		}.type(GOTStructureGhiscarSettlement.Type.TOWN, 6), GOTWaypoint.MEEREEN.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH));

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityMaegeMormont(world), 0, 2));
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.MORMONTS_KEEP);

		registerSpawner(new GOTStructureMyrSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityHarryStrickland(world), -1, -1));
			}
		}.type(GOTStructureMyrSettlement.Type.TOWN, 6), GOTWaypoint.MYR.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityAeronGreyjoy(world), 0, 2));
			}
		}, GOTWaypoint.NAGGAS_HILL);

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityNightKing(world), 0, 0));
			}

			@Override
			public GOTStructureBase getSpecialStructure() {
				return new GOTStructureBase() {
					@Override
					public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
						setOriginAndRotation(world, i, j, k, rotation, 0);
						loadStrScan("night_king");
						spawnLegendaryNPC(new GOTEntityNightKing(world), world, 0, 2, 0);
						generateStrScan(world, random, 0, 0, 0);
						return true;
					}
				};
			}
		}, GOTWaypoint.NIGHT_KING);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntitySymondTempleton(world), 0, 2));
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.NINESTARS);

		registerSpawner(new GOTStructureNorvosSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityMellario(world), 0, 1));
			}
		}.type(GOTStructureNorvosSettlement.Type.TOWN, 6), GOTWaypoint.NORVOS.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH));

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityEbrose(world), 0, 5));
			}
		}.type(GOTStructureReachSettlement.Type.TOWN, 6), GOTWaypoint.OLDTOWN.info(-1.1, 0, Dir.WEST));

		registerSpawner(new GOTStructurePentosSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityIllyrioMopatis(world), 3, 0));
			}
		}.type(GOTStructurePentosSettlement.Type.TOWN, 6), GOTWaypoint.PENTOS.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityClementPiper(world), 0, 2));
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.PINKMAIDEN_CASTLE);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityBalonGreyjoy(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityYaraGreyjoy(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityTheonGreyjoy.TheonGreyjoyNormal(world), -2, 2));
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.PYKE);

		registerSpawner(new GOTStructureQarthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityXaroXhoanDaxos(world), 3, 0));
			}
		}.type(GOTStructureQarthSettlement.Type.TOWN, 6), GOTWaypoint.QARTH.info(toEssosTownGate(0.0, false), 0.5));

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityTytosBlackwood(world), 2, 0));
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.RAVENTREE_HALL);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityHortonRedfort(world), 0, 2));
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.REDFORT);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityErikIronmaker(world), -2, -2));
			}
		}.type(GOTStructureIronbornSettlement.Type.TOWN, 6), GOTWaypoint.RED_HAVEN);

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityQuennRoxton(world), 0, 2));
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.RING.info(0, -0.5));

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityRodrikRyswell(world), 0, 2));
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.RYSWELLS_CASTLE.info(-0.5, 0, Dir.WEST));

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityBryndenTully(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityEdmureTully(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityHosterTully(world), 2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityRodrikCassel(world), -2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityCatelynStark(world), 2, 0));
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.RIVERRUN.info(0, -0.4));

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityYohnRoyce(world), 2, 0));
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.RUNESTONE);

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityQuentynQorgyle(world), 0, 2));
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.SANDSTONE);

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityJasonMallister(world), 0, 3));
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.SEAGARD.info(-0.1, -0.5));

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityCleyCerwyn(world), 0, 2));
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.CASTLE_CERWYN.info(-0.4, 0, Dir.WEST));

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityFranklynFowler(world), 0, 2));
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.SKYREACH.info(0, 0.5, Dir.SOUTH));

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityHummel009(world), 0, 0));
			}
		}, GOTWaypoint.SPIDER);

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityBericDayne(world), -2, 2));
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.STARFALL.info(0, 0.6, Dir.SOUTH));

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityPaxterRedwyne(world), 0, 5));
			}
		}.type(GOTStructureReachSettlement.Type.TOWN, 6), GOTWaypoint.STARFISH_HARBOR);

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityJonosBracken(world), 0, 2));
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.STONE_HEDGE.info(0, -0.4));

		registerSpawner(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityGulianSwann(world), 0, 2));
			}
		}.type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.STONEHELM);

		registerSpawner(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityRenlyBaratheon(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityLorasTyrell(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityBrienneTarth(world), -2, 2));
			}
		}.type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.STORMS_END.info(0, 0.5, Dir.SOUTH));

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityBenedarBelmore(world), 0, 2));
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.STRONGSONG);

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityOberynMartell(world), 3, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityDoranMartell(world), 0, 3));
				spawnInfos.add(new SpawnInfo(new GOTEntityEllaryaSand(world), 3, 3));
				spawnInfos.add(new SpawnInfo(new GOTEntityAreoHotah(world), 0, -3));
				spawnInfos.add(new SpawnInfo(new GOTEntityTrystaneMartell(world), -3, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityArianneMartell(world), -3, 3));
				spawnInfos.add(new SpawnInfo(new GOTEntityManfreyMartell(world), -3, -3));
			}
		}.type(GOTStructureDorneSettlement.Type.TOWN, 6), GOTWaypoint.SUNSPEAR);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityRodrikHarlaw(world), 0, 2));
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.TEN_TOWERS);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityRobinArryn(world), 0, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityLysaArryn(world), -2, 0));
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.THE_EYRIE.info(0, -0.4));

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityThreeEyedRaven(world), 0, 5));
			}
		}, GOTWaypoint.THREE_EYED_RAVEN_CAVE);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityHelmanTallhart(world), 0, 2));
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.TORRHENS_SQUARE.info(-0.4, 0, Dir.WEST));

		registerSpawner(new GOTStructureTower(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityBlackWalderFrey(world), 0, -15));
				spawnInfos.add(new SpawnInfo(new GOTEntityLotharFrey(world), 0, -15));
			}
		}, GOTWaypoint.TWINS_LEFT);

		registerSpawner(new GOTStructureTower(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityWalderFrey(world), 0, -15));
			}
		}, GOTWaypoint.TWINS_RIGHT);

		registerSpawner(new GOTStructureTyroshSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityJonConnington(world), 0, -1));
				spawnInfos.add(new SpawnInfo(new GOTEntityYoungGriff(world), 0, -1));
			}
		}.type(GOTStructureTyroshSettlement.Type.TOWN, 6), GOTWaypoint.TYROSH);

		registerSpawner(new GOTStructureDothrakiSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityDaenerysTargaryen(world), 0, 3));
				spawnInfos.add(new SpawnInfo(new GOTEntityJorahMormont(world), 0, 3));
			}
		}.type(GOTStructureDothrakiSettlement.Type.BIG, 5), GOTWaypoint.VAES_EFE);

		registerSpawner(new GOTStructureVolantisSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityMoqorro(world), -1, 0));
			}
		}.type(GOTStructureVolantisSettlement.Type.TOWN, 6), GOTWaypoint.VOLANTIS.info(toEssosTownGate(0.0, false) - 0.2, 0.6));

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityMaronVolmark(world), -2, -2));
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.VOLMARK);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityWymanManderly(world), 0, 5));
			}
		}.type(GOTStructureNorthSettlement.Type.TOWN, 6), GOTWaypoint.WHITE_HARBOUR.info(0.8, 0, Dir.EAST));

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityBenjenStark(world).setIsRider(true), 0, 5));
			}
		}, GOTWaypoint.WHITETREE);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityRobbStark(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityHodor(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityAryaStark(world), 2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityBranStark(world), -2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityRickonStark(world), 0, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityLuwin(world), 0, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityOsha(world), 2, 0));
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.WINTERFELL.info(-0.5, -0.1, Dir.WEST));

		registerSpawner(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityBuGai(world), 12, 0));
			}
		}.type(GOTStructureYiTiSettlement.Type.TOWN, 7), GOTWaypoint.YIN.info(0, 1, Dir.SOUTH));

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityQuentynMartell(world), 0, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityAndersYronwood(world), 0, 2));
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.YRONWOOD.info(0.5, 0, Dir.EAST));

		registerSpawner(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public void addLegendaryNPCs(World world) {
				spawnInfos.add(new SpawnInfo(new GOTEntityDaarioNaharis(world), -1, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityRazdalMoEraz(world), -1, 1));
			}
		}.type(GOTStructureGhiscarSettlement.Type.TOWN, 6), GOTWaypoint.YUNKAI.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));

		for (GOTStructureBaseSettlement location : locations) {
			for (GOTBiome biome : GOTBiome.CONTENT) {
				biome.decorator.addFixedSettlement(location);
			}
		}

		locations.clear();
	}

	public static void registerLocation(GOTStructureBaseSettlement settlement, GOTAbstractWaypoint... wps) {
		settlement.affix(wps);
		locations.add(settlement);
	}

	public static void registerSpawner(GOTStructureBaseSettlement spawner, GOTAbstractWaypoint wp) {
		spawner.affix(wp);
		locations.add(spawner);
		spawners.put(wp, spawner);
	}

	public enum Dir {
		NORTH, EAST, SOUTH, WEST
	}

	public static class SpawnInfo {
		private GOTEntityNPC npc;
		private int i;
		private int k;

		public SpawnInfo(GOTEntityNPC npc, int i, int k) {
			this.npc = npc;
			this.i = i;
			this.k = k;
		}

		public GOTEntityNPC getNPC() {
			return npc;
		}

		public int getI() {
			return i;
		}

		public int getJ() {
			return 6;
		}

		public int getK() {
			return k;
		}
	}
}