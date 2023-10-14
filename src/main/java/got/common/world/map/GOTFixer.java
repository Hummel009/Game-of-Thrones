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

import static got.common.world.map.GOTCoordConverter.toEssosTownGate;

public class GOTFixer {
	public static Map<GOTAbstractWaypoint, GOTStructureBase> structures = new HashMap<>();
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

	public static void initLocations() {
		registerLocation(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.BLOODY_GATE.info(0, -0.5), GOTWaypoint.THE_EYRIE.info(0, -0.4), GOTWaypoint.COLDWATER_BURN, GOTWaypoint.GREY_GLEN, GOTWaypoint.HEARTS_HOME, GOTWaypoint.IRONOAKS, GOTWaypoint.LONGBOW_HALL, GOTWaypoint.NINESTARS, GOTWaypoint.OLD_ANCHOR, GOTWaypoint.PEBBLE, GOTWaypoint.REDFORT, GOTWaypoint.RUNESTONE, GOTWaypoint.SNAKEWOOD, GOTWaypoint.STRONGSONG, GOTWaypoint.THE_PAPS, GOTWaypoint.WICKENDEN, GOTWaypoint.WITCH_ISLE, GOTWaypoint.GATE_OF_THE_MOON.info(0.5, 0, Dir.EAST));
		registerLocation(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureArrynSettlement.Type.TOWN, 6), GOTWaypoint.GULLTOWN, GOTWaypoint.SISTERTON);
		registerLocation(new GOTStructureAsshaiSettlement(GOTBiome.ocean, 0.0f), GOTWaypoint.ASSHAI.info(0, 0.8, Dir.SOUTH));
		registerLocation(new GOTStructureBraavosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureBraavosSettlement.Type.TOWN, 6), GOTWaypoint.BRAAVOS.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.FORT, 3), GOTWaypoint.ANTLERS.info(0.1, -0.5), GOTWaypoint.BROWNHOLLOW, GOTWaypoint.DYRE_DEN, GOTWaypoint.STOKEWORTH.info(-0.4, 0, Dir.WEST), GOTWaypoint.HAYFORD.info(0.1, -0.5), GOTWaypoint.ROOKS_REST.info(0, -0.4), GOTWaypoint.ROSBY.info(-0.5, -0.1, Dir.WEST));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.KINGS_LANDING, 6), GOTWaypoint.KINGS_LANDING.info(0.9, 0, Dir.EAST));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.RED_KEEP, 3), GOTWaypoint.KINGS_LANDING.info(2, 0, Dir.EAST));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.TOWN, 6), GOTWaypoint.DUSKENDALE.info(-0.1, -1.1));
		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.VILLAGE, 6), GOTWaypoint.BRIARWHITE.info(0, -0.7), GOTWaypoint.ROSBY.info(0.7, 0));
		registerLocation(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.BLACKMONT.info(0.5, 0, Dir.EAST), GOTWaypoint.GHOST_HILL, GOTWaypoint.GODSGRACE, GOTWaypoint.HELLHOLT, GOTWaypoint.HIGH_HERMITAGE.info(0.5, 0, Dir.EAST), GOTWaypoint.THE_TOR, GOTWaypoint.VAITH, GOTWaypoint.WATER_GARDENS, GOTWaypoint.SPOTTSWOOD, GOTWaypoint.LEMONWOOD, GOTWaypoint.SALT_SHORE, GOTWaypoint.SANDSTONE, GOTWaypoint.KINGSGRAVE.info(0.5, 0, Dir.EAST), GOTWaypoint.SKYREACH.info(0, 0.5, Dir.SOUTH), GOTWaypoint.STARFALL.info(0, 0.6, Dir.SOUTH), GOTWaypoint.WYL.info(-0.55, 0, Dir.WEST), GOTWaypoint.YRONWOOD.info(0.5, 0, Dir.EAST));
		registerLocation(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDorneSettlement.Type.TOWN, 6), GOTWaypoint.GHASTON_GREY, GOTWaypoint.PLANKY_TOWN, GOTWaypoint.SUNSPEAR);
		registerLocation(new GOTStructureDothrakiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDothrakiSettlement.Type.BIG, 5), GOTWaypoint.HORNOTH.info(0.5, 0), GOTWaypoint.KYTH.info(0.3, -0.4), GOTWaypoint.SATHAR.info(0.5, 0), GOTWaypoint.RATHYLAR.info(0.6, -0.2), GOTWaypoint.SAATH.info(0, -0.5), GOTWaypoint.VAES_ATHJIKHARI.info(0, -0.5), GOTWaypoint.VAES_DIAF, GOTWaypoint.VAES_DOTHRAK, GOTWaypoint.VAES_EFE, GOTWaypoint.VAES_GORQOYI.info(0.3, -0.4), GOTWaypoint.VAES_GRADDAKH, GOTWaypoint.VAES_JINI.info(0, 0.5), GOTWaypoint.VOJJOR_SAMVI.info(0, -0.5), GOTWaypoint.VAES_KHADOKH.info(-0.1, -0.5), GOTWaypoint.VAES_KHEWO.info(0, -0.5), GOTWaypoint.VAES_LEQSE.info(0.5, 0), GOTWaypoint.VAES_MEJHAH.info(0, -0.5), GOTWaypoint.KRAZAAJ_HAS.info(0, -0.6));
		registerLocation(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDragonstoneSettlement.Type.FORT, 3), GOTWaypoint.CLAW_ISLE, GOTWaypoint.DRAGONSTONE, GOTWaypoint.DRIFTMARK, GOTWaypoint.HIGH_TIDE, GOTWaypoint.SHARP_POINT, GOTWaypoint.STONEDANCE, GOTWaypoint.SWEETPORT_SOUND);
		registerLocation(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDragonstoneSettlement.Type.TOWN, 6), GOTWaypoint.HULL);
		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.COLONY, 4), GOTWaypoint.ISLE_OF_WHIPS, GOTWaypoint.BARTER_BEACH, GOTWaypoint.GOGOSSOS, GOTWaypoint.GOROSH, GOTWaypoint.ZAMETTAR.info(0, -0.5, Dir.SOUTH));
		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.PYRAMID, 3), GOTWaypoint.MEEREEN.info(toEssosTownGate(0.0, true), -1.5), GOTWaypoint.ASTAPOR.info(-1.5, toEssosTownGate(0.0, false)), GOTWaypoint.NEW_GHIS.info(-1, 0), GOTWaypoint.YUNKAI.info(-1.5, toEssosTownGate(0.0, false)));
		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.TOWN, 6), GOTWaypoint.MEEREEN.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH), GOTWaypoint.ASTAPOR.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST), GOTWaypoint.NEW_GHIS, GOTWaypoint.YUNKAI.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.ABANDONED, 2), GOTWaypoint.GREENGUARD, GOTWaypoint.THE_TORCHES, GOTWaypoint.THE_LONG_BARROW, GOTWaypoint.RIMEGATE, GOTWaypoint.SABLE_HALL, GOTWaypoint.WOODSWATCH, GOTWaypoint.NIGHTFORT, GOTWaypoint.DEEP_LAKE, GOTWaypoint.OAKENSHIELD, GOTWaypoint.ICEMARK, GOTWaypoint.HOARFROST_HILL, GOTWaypoint.STONEDOOR, GOTWaypoint.GREYGUARD, GOTWaypoint.QUEENSGATE, GOTWaypoint.SENTINEL_STAND);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.CASTLE_BLACK, 2), GOTWaypoint.CASTLE_BLACK);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.EAST_WATCH, 2), GOTWaypoint.EASTWATCH);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.SHADOW_TOWER, 2), GOTWaypoint.SHADOW_TOWER);
		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.VILLAGE, 4), GOTWaypoint.MOLETOWN, GOTWaypoint.QUEENSCROWN);
		registerLocation(new GOTStructureIbbenSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIbbenSettlement.Type.FORT, 6), GOTWaypoint.IB_NOR, GOTWaypoint.IB_SAR, GOTWaypoint.NEW_IBBISH, GOTWaypoint.PORT_OF_IBBEN);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.CAMP, 5), GOTWaypoint.VICTARION_LANDING);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.CATFISH_ROCK, GOTWaypoint.BLACKTYDE, GOTWaypoint.CORPSE_LAKE, GOTWaypoint.CROW_SPIKE_KEEP, GOTWaypoint.DOWNDELVING, GOTWaypoint.DRUMM_CASTLE, GOTWaypoint.GREY_GARDEN, GOTWaypoint.HAMMERHORN, GOTWaypoint.HARLAW_HALL, GOTWaypoint.HARRIDAN_HILL, GOTWaypoint.IRON_HOLT, GOTWaypoint.LONELY_LIGHT, GOTWaypoint.MYRE_CASTLE, GOTWaypoint.ORKWOOD, GOTWaypoint.PYKE, GOTWaypoint.SALTCLIFFE, GOTWaypoint.SEALSKIN_POINT, GOTWaypoint.SHATTERSTONE, GOTWaypoint.SPARR_CASTLE, GOTWaypoint.STONEHOUSE, GOTWaypoint.STONETREE_CASTLE, GOTWaypoint.SUNDERLY, GOTWaypoint.TAWNEY_CASTLE, GOTWaypoint.TEN_TOWERS, GOTWaypoint.VOLMARK);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.TOWN, 6), GOTWaypoint.LORDSPORT, GOTWaypoint.RED_HAVEN);
		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.VILLAGE, 6), GOTWaypoint.PEBBLETON);
		registerLocation(new GOTStructureJogosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureJogosSettlement.Type.BIG, 5), GOTWaypoint.HOJDBAATAR);
		registerLocation(new GOTStructureLhazarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureLhazarSettlement.Type.TOWN, 6), GOTWaypoint.HESH.info(1.2, 0, Dir.WEST), GOTWaypoint.KOSRAK.info(1, 0, Dir.WEST), GOTWaypoint.LHAZOSH.info(1, 0, Dir.WEST));
		registerLocation(new GOTStructureLorathSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureLorathSettlement.Type.TOWN, 6), GOTWaypoint.LORATH, GOTWaypoint.MOROSH);
		registerLocation(new GOTStructureLysSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureLysSettlement.Type.TOWN, 6), GOTWaypoint.LYS);
		registerLocation(new GOTStructureMossovySettlement(GOTBiome.ocean, 0.0f).type(GOTStructureMossovySettlement.Type.VILLAGE, 6), GOTWaypoint.KUURULGAN, GOTWaypoint.SUUDAN_KORKUU, GOTWaypoint.KANDUU_BET, GOTWaypoint.TASHTOO, GOTWaypoint.BASHKARUUCHU, GOTWaypoint.AZUU_KAN, GOTWaypoint.KUJRUK, GOTWaypoint.KORKUNUCHTUU, GOTWaypoint.NYMDUU_OOZ, GOTWaypoint.AZHYDAAR, GOTWaypoint.AK_SHAAR, GOTWaypoint.SUU_NYM, GOTWaypoint.SHYLUUN_UUSU, GOTWaypoint.KADAR, GOTWaypoint.NEFER, GOTWaypoint.K_DATH);
		registerLocation(new GOTStructureMyrSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureMyrSettlement.Type.TOWN, 6), GOTWaypoint.MYR.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.CATFISH_ROCK, GOTWaypoint.BREAKSTONE_HILL, GOTWaypoint.GOLDGRASS.info(0, 0.4, Dir.SOUTH), GOTWaypoint.RYSWELLS_CASTLE.info(-0.5, 0, Dir.WEST), GOTWaypoint.CASTLE_CERWYN.info(-0.4, 0, Dir.WEST), GOTWaypoint.WINTERFELL.info(-0.5, -0.1, Dir.WEST), GOTWaypoint.BLACK_POOL, GOTWaypoint.DEEPWOOD_MOTTE, GOTWaypoint.DREADFORT.info(0, -0.4), GOTWaypoint.FLINTS_FINGER, GOTWaypoint.HIGHPOINT, GOTWaypoint.TORRHENS_SQUARE.info(-0.4, 0, Dir.WEST), GOTWaypoint.WIDOWS_WATCH, GOTWaypoint.HORNWOOD, GOTWaypoint.IRONRATH, GOTWaypoint.KARHOLD.info(0.4, 0, Dir.EAST), GOTWaypoint.LAST_HEARTH, GOTWaypoint.MOAT_KAILIN, GOTWaypoint.MORMONTS_KEEP, GOTWaypoint.OLDCASTLE, GOTWaypoint.RAMSGATE, GOTWaypoint.RILLWATER_CROSSING.info(-0.4, 0, Dir.WEST));
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.HILLMAN, 6), GOTWaypoint.SKANE, GOTWaypoint.DEEPDOWN, GOTWaypoint.DRIFTWOOD_HALL, GOTWaypoint.KINGSHOUSE);
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.SMALL_TOWN, 6), GOTWaypoint.BARROWTOWN);
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.TOWN, 6), GOTWaypoint.WHITE_HARBOUR.info(0.8, 0, Dir.EAST));
		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.VILLAGE, 6), GOTWaypoint.GREYWATER_WATCH);
		registerLocation(new GOTStructureNorvosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorvosSettlement.Type.TOWN, 6), GOTWaypoint.NORVOS.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH));
		registerLocation(new GOTStructurePentosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructurePentosSettlement.Type.TOWN, 6), GOTWaypoint.PENTOS.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));
		registerLocation(new GOTStructurePentosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructurePentosSettlement.Type.VILLAGE, 5), GOTWaypoint.GHOYAN_DROHE.info(0, 0.7));
		registerLocation(new GOTStructureQarthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQarthSettlement.Type.COLONY, 4), GOTWaypoint.TERIMAN, GOTWaypoint.BATARGAS, GOTWaypoint.KARIMAGAR);
		registerLocation(new GOTStructureQarthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQarthSettlement.Type.TOWN, 6), GOTWaypoint.PORT_YHOS.info(toEssosTownGate(0.0, false), 0.5), GOTWaypoint.QARKASH.info(toEssosTownGate(0.0, false), 0.5), GOTWaypoint.QARTH.info(toEssosTownGate(0.0, false), 0.5));
		registerLocation(new GOTStructureQohorSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQohorSettlement.Type.TOWN, 6), GOTWaypoint.QOHOR.info(-0.6, toEssosTownGate(0.0, false), Dir.EAST));
		registerLocation(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.APPLETON.info(0, -0.51), GOTWaypoint.ASHFORD, GOTWaypoint.BANDALLON, GOTWaypoint.GOLDENGROVE, GOTWaypoint.GRASSY_VALE, GOTWaypoint.GREENSHIELD, GOTWaypoint.GRIMSTON, GOTWaypoint.HAMMERHAL.info(0.5, 0, Dir.EAST), GOTWaypoint.RED_LAKE.info(0, 0.5, Dir.SOUTH), GOTWaypoint.RING.info(0, -0.5), GOTWaypoint.SOUTHSHIELD, GOTWaypoint.UPLANDS, GOTWaypoint.HOLYHALL.info(0.5, 0, Dir.EAST), GOTWaypoint.HONEYHOLT, GOTWaypoint.HORN_HILL, GOTWaypoint.IVY_HALL.info(0, 0.5, Dir.SOUTH), GOTWaypoint.LONGTABLE, GOTWaypoint.NEW_BARREL, GOTWaypoint.BLACKCROWN, GOTWaypoint.BRIGHTWATER_KEEP, GOTWaypoint.CIDER_HALL, GOTWaypoint.COLDMOAT.info(0, 0.5, Dir.SOUTH), GOTWaypoint.DARKDELL.info(-0.5, 0, Dir.WEST), GOTWaypoint.DUNSTONBURY, GOTWaypoint.BITTERBRIDGE.info(0, 0.5, Dir.SOUTH), GOTWaypoint.GARNETGROVE.info(-0.5, 0, Dir.WEST), GOTWaypoint.LORD_HEWETTS_TOWN, GOTWaypoint.OLD_OAK.info(0, 0.5, Dir.SOUTH), GOTWaypoint.SUNHOUSE.info(0, 0.5, Dir.SOUTH), GOTWaypoint.WHITEGROVE.info(-0.5, 0, Dir.WEST));
		registerLocation(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureReachSettlement.Type.HIGHGARDEN, 3), GOTWaypoint.HIGHGARDEN.info(0.5, 0, Dir.EAST));
		registerLocation(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureReachSettlement.Type.TOWN, 6), GOTWaypoint.OLDTOWN.info(-1.1, 0, Dir.WEST), GOTWaypoint.APPLETON.info(0.1, 1.1, Dir.SOUTH), GOTWaypoint.ASHFORD, GOTWaypoint.LORD_HEWETTS_TOWN, GOTWaypoint.SMITHYTON.info(0, 0.9, Dir.SOUTH), GOTWaypoint.STARFISH_HARBOR, GOTWaypoint.VINETOWN, GOTWaypoint.RYAMSPORT, GOTWaypoint.TUMBLETON.info(0, -1.0));
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.CROSSROADS, 2), GOTWaypoint.CROSSROADS_INN);
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.DARRY, GOTWaypoint.MAIDENPOOL.info(0, -0.5), GOTWaypoint.PINKMAIDEN_CASTLE, GOTWaypoint.RAVENTREE_HALL, GOTWaypoint.WAYFARERS_REST.info(-0.4, 0, Dir.WEST), GOTWaypoint.ACORN_HALL.info(-0.5, -0.1, Dir.WEST), GOTWaypoint.ATRANTA, GOTWaypoint.RIVERRUN.info(0, -0.4), GOTWaypoint.SEAGARD.info(-0.1, -0.5), GOTWaypoint.STONE_HEDGE.info(0, -0.4));
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.TOWN, 6), GOTWaypoint.HARROWAY.info(0, 0.9, Dir.SOUTH), GOTWaypoint.MAIDENPOOL.info(0, 0.9, Dir.SOUTH), GOTWaypoint.SALTPANS.info(0.8, 0, Dir.EAST), GOTWaypoint.STONEY_SEPT.info(-0.8, 0, Dir.WEST), GOTWaypoint.SEAGARD.info(0, 0.8, Dir.SOUTH));
		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.VILLAGE, 6), GOTWaypoint.FAIRMARKET, GOTWaypoint.PENNYTREE, GOTWaypoint.SEVENSTREAMS);
		registerLocation(new GOTStructureRuins(GOTBiome.ocean, 0.0f), GOTWaypoint.WESTWATCH.info(-0.3, 0), GOTWaypoint.VAES_LEISI.info(0, 0.3), GOTWaypoint.MORNE.info(0, 0.3), GOTWaypoint.NY_SAR.info(0, 0.15), GOTWaypoint.OLD_GHIS.info(0, 0.3), GOTWaypoint.SAR_MELL.info(0, -0.25), GOTWaypoint.SARHOY.info(0, 0.3), GOTWaypoint.SHANDYSTONE.info(0, 0.3), GOTWaypoint.STARPIKE.info(0, 0.3), GOTWaypoint.TELYRIA.info(0, 0.3), GOTWaypoint.TORTURERS_DEEP.info(0, 0.3), GOTWaypoint.VAES_ORVIK.info(0.3, 0), GOTWaypoint.VAES_QOSAR.info(0, 0.3), GOTWaypoint.VAES_SHIROSI.info(0, 0.3), GOTWaypoint.VAES_TOLORRO.info(0, 0.3), GOTWaypoint.VALYRIAN_ROAD.info(-0.2, 0), GOTWaypoint.VELOS.info(0, 0.3), GOTWaypoint.GREY_GALLOWS.info(0, 0.3), GOTWaypoint.BLOODSTONE.info(0, 0.3), GOTWaypoint.ANOGARIA.info(0, 0.2), GOTWaypoint.KAYAKAYANAYA.info(0, 0.3), GOTWaypoint.AEGON.info(0, 0.3), GOTWaypoint.RAENYS.info(0, 0.3), GOTWaypoint.VISENYA.info(0, 0.3), GOTWaypoint.GHOZAI.info(0, 0.3), GOTWaypoint.CHROYANE.info(0.3, 0), GOTWaypoint.FOURTEEN_FLAMES.info(0, 0.3), GOTWaypoint.IBBISH.info(0, 0.3), GOTWaypoint.SAMYRIANA.info(0, 0.3), GOTWaypoint.BHORASH.info(0, 0.2), GOTWaypoint.BAYASABHAD.info(0, 0.3), GOTWaypoint.AR_NOY.info(-0.1, -0.25), GOTWaypoint.ADAKHAKILEKI.info(0, 0.3), GOTWaypoint.CASTLE_LYCHESTER.info(0, -0.3), GOTWaypoint.MHYSA_FAER.info(0, 0.3), GOTWaypoint.AQUOS_DHAEN.info(0, 0.3), GOTWaypoint.DRACONYS.info(0, 0.3), GOTWaypoint.TYRIA.info(0, 0.3), GOTWaypoint.RHYOS.info(0, 0.3), GOTWaypoint.OROS.info(0, 0.2), GOTWaypoint.VULTURES_ROOST.info(0, 0.3), GOTWaypoint.SPICETOWN.info(0, 0.3), GOTWaypoint.CASTAMERE.info(0, 0.3), GOTWaypoint.GOLDENHILL.info(0, 0.3), GOTWaypoint.GREYIRON_CASTLE.info(0, 0.3), GOTWaypoint.HOARE_CASTLE.info(0, 0.3), GOTWaypoint.HOARE_KEEP.info(0, 0.3), GOTWaypoint.HOGG_HALL.info(0.2, -0.2), GOTWaypoint.HOLLARD_CASTLE.info(0.3, 0), GOTWaypoint.OLDSTONES.info(0, 0.3), GOTWaypoint.SUMMERHALL.info(-0.3, 0), GOTWaypoint.TARBECK_HALL.info(0, 0.3), GOTWaypoint.TOWER_OF_JOY.info(-0.3, 0), GOTWaypoint.WHISPERS.info(0, 0.3), GOTWaypoint.WHITEWALLS.info(0.2, -0.2));
		registerLocation(new GOTStructureRuinsBig(GOTBiome.ocean, 0.0f), GOTWaypoint.EAST_BAY.info(0, 0.4), GOTWaypoint.EAST_COAST.info(0, 0.4), GOTWaypoint.NORTH_FORESTS.info(0, 0.4), GOTWaypoint.WHITE_MOUNTAINS.info(0, 0.4), GOTWaypoint.CENTRAL_FORESTS.info(0, 0.4), GOTWaypoint.MARSHES.info(0, 0.4), GOTWaypoint.RED_FORESTS.info(0, 0.4), GOTWaypoint.SOUTH_ULTHOS.info(0, 0.4), GOTWaypoint.SOUTH_TAIGA.info(0, 0.4), GOTWaypoint.BONETOWN.info(0, 0.4), GOTWaypoint.HARRENHAL.info(-0.4, 0), GOTWaypoint.STYGAI.info(0, 0.4), GOTWaypoint.ULOS.info(0, 0.4), GOTWaypoint.YEEN.info(0, 0.4));
		registerLocation(new GOTStructureSothoryosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSothoryosSettlement.Type.PYRAMID, 3), GOTWaypoint.RAUMATI);
		registerLocation(new GOTStructureSothoryosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSothoryosSettlement.Type.VILLAGE, 3), GOTWaypoint.MAUNGA, GOTWaypoint.DRAGON_PLACE, GOTWaypoint.SOUTH_POINT, GOTWaypoint.BIG_LAKE);
		registerLocation(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.CROWS_NEST, GOTWaypoint.GRIFFINS_ROOST, GOTWaypoint.BLACKHAVEN.info(-0.5, 0, Dir.WEST), GOTWaypoint.BRONZEGATE.info(0, -0.5), GOTWaypoint.DEAD_HEAD, GOTWaypoint.EVENFALL_HALL, GOTWaypoint.FAWNTOWN, GOTWaypoint.AMBERLY, GOTWaypoint.BLACK_HEART, GOTWaypoint.BROAD_ARCH, GOTWaypoint.PARCHMENTS.info(0, -0.5), GOTWaypoint.PODDINGFIELD.info(0, -0.5), GOTWaypoint.RAIN_HOUSE, GOTWaypoint.SEAWORTH_CASTLE, GOTWaypoint.STONEHELM, GOTWaypoint.STORMS_END.info(0, 0.5, Dir.SOUTH), GOTWaypoint.TUDBURY_HOLL, GOTWaypoint.GALLOWSGREY.info(0, -0.5), GOTWaypoint.GRANDVIEW.info(-0.5, 0, Dir.WEST), GOTWaypoint.GREENSTONE, GOTWaypoint.HARVEST_HALL.info(0, -0.5), GOTWaypoint.HAYSTACK_HALL.info(0, -0.5), GOTWaypoint.MISTWOOD, GOTWaypoint.FELWOOD.info(0, -0.5), GOTWaypoint.NIGHTSONG.info(0, -0.5));
		registerLocation(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureStormlandsSettlement.Type.TOWN, 6), GOTWaypoint.WEEPING_TOWN);
		registerLocation(new GOTStructureSummerSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSummerSettlement.Type.FORT, 3), GOTWaypoint.HAUAURU, GOTWaypoint.MATAHAU, GOTWaypoint.TAKUTAI, GOTWaypoint.ATAAHUA, GOTWaypoint.PEREKI, GOTWaypoint.NGARARA, GOTWaypoint.TAURANGA, GOTWaypoint.MATAO, GOTWaypoint.NGAHERE, GOTWaypoint.KOHURU);
		registerLocation(new GOTStructureSummerSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSummerSettlement.Type.VILLAGE, 5), GOTWaypoint.DOQUU, GOTWaypoint.EBONHEAD, GOTWaypoint.GOLDEN_HEAD, GOTWaypoint.KOJ, GOTWaypoint.LAST_LAMENT, GOTWaypoint.LIZARD_HEAD, GOTWaypoint.LOTUS_POINT, GOTWaypoint.NAATH, GOTWaypoint.OMBORU, GOTWaypoint.PEARL_PALACE, GOTWaypoint.RED_FLOWER_VALE, GOTWaypoint.SWEET_LOTUS_VALE, GOTWaypoint.TALL_TREES_TOWN, GOTWaypoint.WALANO, GOTWaypoint.XON);
		registerLocation(new GOTStructureTower(GOTBiome.ocean, 0.0f), GOTWaypoint.THREE_TOWERS.info(-0.5, -0.5, Dir.WEST), GOTWaypoint.THREE_TOWERS.info(-0.5, 0, Dir.WEST), GOTWaypoint.THREE_TOWERS.info(-0.5, 0.5, Dir.WEST), GOTWaypoint.TOWER_OF_GLIMMERING, GOTWaypoint.BAELISH_KEEP, GOTWaypoint.HIGHTOWER_LITEHOUSE, GOTWaypoint.RAMSEY_TOWER, GOTWaypoint.STANDFAST, GOTWaypoint.TWINS_LEFT, GOTWaypoint.TWINS_RIGHT);
		registerLocation(new GOTStructureTyroshSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureTyroshSettlement.Type.TOWN, 6), GOTWaypoint.TYROSH);
		registerLocation(new GOTStructureVolantisSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureVolantisSettlement.Type.TOWN, 6), GOTWaypoint.ELYRIA, GOTWaypoint.TOLOS.info(toEssosTownGate(0.0, false), 0.5), GOTWaypoint.LITTLE_VALYRIA.info(toEssosTownGate(0.0, false) + 0.26, 0.6), GOTWaypoint.MANTARYS.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH), GOTWaypoint.SELHORYS.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST), GOTWaypoint.VALYSAR.info(-0.6, toEssosTownGate(0.0, false) + 0.25, Dir.EAST), GOTWaypoint.VOLANTIS.info(toEssosTownGate(0.0, false) - 0.2, 0.6), GOTWaypoint.VOLON_THERYS.info(toEssosTownGate(0.0, false), 0.5));
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.CRAKEHALL.info(-0.5, 0, Dir.WEST), GOTWaypoint.GOLDEN_TOOTH.info(-0.1, -0.5), GOTWaypoint.KAYCE, GOTWaypoint.SARSFIELD.info(-0.1, -0.5), GOTWaypoint.SILVERHILL, GOTWaypoint.WYNDHALL, GOTWaypoint.PLUMWOOD, GOTWaypoint.RIVERSPRING, GOTWaypoint.GREENFIELD, GOTWaypoint.HORNVALE, GOTWaypoint.DEEP_DEN, GOTWaypoint.FAIRCASTLE, GOTWaypoint.FEASTFIRES, GOTWaypoint.CLEGANES_KEEP, GOTWaypoint.CORNFIELD, GOTWaypoint.CRAG, GOTWaypoint.ASHEMARK, GOTWaypoint.BANEFORT);
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.CASTERLY_ROCK, 3), GOTWaypoint.CASTERLY_ROCK.info(-0.4, 0, Dir.WEST));
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.TOWN, 6), GOTWaypoint.KAYCE, GOTWaypoint.LANNISPORT.info(-0.8, 0, Dir.WEST));
		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.VILLAGE, 6), GOTWaypoint.OXCROSS.info(0.2, 0.6));
		registerLocation(new GOTStructureWildlingSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWildlingSettlement.Type.CRASTER, 1), GOTWaypoint.CRASTERS_KEEP);
		registerLocation(new GOTStructureWildlingSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWildlingSettlement.Type.HARDHOME, 7), GOTWaypoint.HARDHOME);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.GATE, 2), GOTWaypoint.JIANMEN, GOTWaypoint.ANGUO, GOTWaypoint.DINGGUO, GOTWaypoint.PINNU, GOTWaypoint.PINGJIANG, GOTWaypoint.WUDE, GOTWaypoint.WUSHENG, GOTWaypoint.ZHENGUO, GOTWaypoint.LUNGMEN);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.GATE_ROAD, 2), GOTWaypoint.ANJIANG);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.TOWER, 9), GOTWaypoint.FIVE_FORTS_1, GOTWaypoint.FIVE_FORTS_2, GOTWaypoint.FIVE_FORTS_3, GOTWaypoint.FIVE_FORTS_4, GOTWaypoint.FIVE_FORTS_5);
		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.TOWN, 7), GOTWaypoint.ASABHAD.info(-1, 0, Dir.WEST), GOTWaypoint.BAOJI.info(0, -1), GOTWaypoint.EIJIANG.info(0, 1, Dir.SOUTH), GOTWaypoint.JINQI.info(-1, 0, Dir.WEST), GOTWaypoint.LIZHAO.info(1, 0, Dir.EAST), GOTWaypoint.MANJIN.info(1.1, -0.2, Dir.EAST), GOTWaypoint.SI_QO.info(1, 0, Dir.EAST), GOTWaypoint.TIQUI.info(0, -1), GOTWaypoint.TRADER_TOWN.info(0, -1), GOTWaypoint.VAIBEI.info(0, -1), GOTWaypoint.YIBIN.info(0, -1), GOTWaypoint.YIN.info(0, 1, Dir.SOUTH), GOTWaypoint.YUNNAN.info(1, 0, Dir.EAST), GOTWaypoint.CHANGAN.info(1, 0, Dir.EAST), GOTWaypoint.FU_NING.info(1.1, 0.1, Dir.EAST), GOTWaypoint.ZABHAD, GOTWaypoint.TURRANI, GOTWaypoint.VAHAR, GOTWaypoint.FAROS, GOTWaypoint.HUIJI, GOTWaypoint.LENG_MA, GOTWaypoint.LENG_YI, GOTWaypoint.LESSER_MORAQ, GOTWaypoint.MARAHAI, GOTWaypoint.PORT_MORAQ);

		for (GOTStructureBaseSettlement location : locations) {
			for (GOTBiome biome : GOTBiome.CONTENT) {
				biome.decorator.addFixedSettlement(location);
			}
		}
	}

	public static void initSpawners() {
		structures.put(GOTWaypoint.ASHEMARK, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityAddamMarbrand(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.ASSHAI.info(0, 0.8), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityAsshaiArchmag(world), world, 0, 1, 0);
			}
		});

		structures.put(GOTWaypoint.ASTAPOR.info(-0.5, toEssosTownGate(0.0, false)), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityKraznysMoNakloz(world), world, -1, 1, 0);
				spawnLegendaryNPC(new GOTEntityMissandei(world), world, -1, 1, -1);
				spawnLegendaryNPC(new GOTEntityGreyWorm(world), world, -1, 1, 1);
			}
		});

		structures.put(GOTWaypoint.BANEFORT, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityQuentenBanefort(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.BARROWTOWN, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBarbreyDustin(world), world, 0, 1, 3);
			}
		});

		structures.put(GOTWaypoint.BLACKTYDE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBaelorBlacktyde(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.BRAAVOS.info(toEssosTownGate(0.0, true), -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityTychoNestoris(world), world, 0, 1, 1);
			}
		});

		structures.put(GOTWaypoint.BRIGHTWATER_KEEP, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGarlanTyrell(world), world, 2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.CASTERLY_ROCK.info(-0.4, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityTywinLannister(world), world, 2, 1, 0);
				spawnLegendaryNPC(new GOTEntityQyburn(world), world, -2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.CLAW_ISLE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityArdrianCeltigar(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.CLEGANES_KEEP, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGregorClegane.GregorCleganeAlive(world), world, 2, 1, 0);
				spawnLegendaryNPC(new GOTEntityPolliver(world), world, -2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.CORNFIELD, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHarysSwyft(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.CRAKEHALL.info(-0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityLyleCrakehall(world), world, 2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.DRAGONSTONE, new Spawner() {
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

		structures.put(GOTWaypoint.DREADFORT.info(0, -0.4), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRamsayBolton(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityRooseBolton(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.DRIFTMARK, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMonfordVelaryon(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityAuraneWaters(world), world, 2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.DRUMM_CASTLE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityDunstanDrumm(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityAndrikTheUnsmilling(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.EURON, new Spawner() {
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

		structures.put(GOTWaypoint.EVENFALL_HALL, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySelwynTarth(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.FAIRCASTLE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySebastonFarman(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.FEASTFIRES, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityForleyPrester(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.GOLDENGROVE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMathisRowan(world), world, 2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.GOLDEN_TOOTH.info(-0.1, -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityLeoLefford(world), world, 2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.GREENSHIELD, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMoribaldChester(world), world, 2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.GREENSTONE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityEldonEstermont(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.GREY_GARDEN, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHarrasHarlaw(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.GREYWATER_WATCH, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHowlandReed(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.GULLTOWN, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGeroldGrafton(world), world, 3, 1, 0);
			}
		});

		structures.put(GOTWaypoint.HAMMERHORN, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGoroldGoodbrother(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.HEARTS_HOME, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityLynCorbray(world), world, 2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.HELLHOLT, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHarmenUller(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.HIGHGARDEN.info(0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMaceTyrell(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityOlennaTyrell(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityMargaeryTyrell(world), world, 2, 1, -2);
				spawnLegendaryNPC(new GOTEntityWillasTyrell(world), world, -2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.HIGH_HERMITAGE.info(0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGeroldDayne(world), world, 2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.HIGHTOWER_LITEHOUSE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityLeytonHightower(world), world, 0, 26, -5);
			}
		});

		structures.put(GOTWaypoint.HOJDBAATAR, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityTugarKhan(world), world, 0, 5, 3);
			}
		});

		structures.put(GOTWaypoint.HOLLOW_HILL, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBericDondarrion.BericDondarrionLife1(world), world, 3, 1, 0);
				spawnLegendaryNPC(new GOTEntityThoros(world), world, 0, 1, 3);
			}
		});

		structures.put(GOTWaypoint.HORN_HILL, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRandyllTarly(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.HORNVALE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityTytosBrax(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.IRONOAKS, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHarroldHardyng(world), world, 0, 1, 2);
				spawnLegendaryNPC(new GOTEntityAnyaWaynwood(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.KARHOLD.info(0.4, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRickardKarstark(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.KINGS_LANDING.info(0.9, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySansaStark(world), world, 0, 1, 5);
				spawnLegendaryNPC(new GOTEntityShae(world), world, 0, 1, 6);
				spawnLegendaryNPC(new GOTEntityYoren(world), world, 0, 1, 4);
			}
		});

		structures.put(GOTWaypoint.LANNISPORT.info(-0.8, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityKevanLannister(world), world, 0, 1, 5);
				spawnLegendaryNPC(new GOTEntityDavenLannister(world), world, 0, 1, -5);
				spawnLegendaryNPC(new GOTEntityAmoryLorch(world), world, 5, 1, 0);
			}
		});

		structures.put(GOTWaypoint.LAST_HEARTH, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityJohnUmber(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.LONELY_LIGHT, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGylbertFarwynd(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.LONGBOW_HALL, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGilwoodHunter(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.LONGTABLE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityOrtonMerryweather(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.LORDSPORT, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityDagmer(world), world, 0, 1, 3);
			}
		});

		structures.put(GOTWaypoint.LYS, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySalladhorSaan(world), world, 0, 1, -1);
			}
		});

		structures.put(GOTWaypoint.MAIDENPOOL.info(0, -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityWilliamMooton(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.MEEREEN.info(toEssosTownGate(0.0, true), -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHizdahrZoLoraq(world), world, -1, 1, -1);
			}
		});

		structures.put(GOTWaypoint.MORMONTS_KEEP, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMaegeMormont(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.MYR.info(-0.5, toEssosTownGate(0.0, false)), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHarryStrickland(world), world, -1, 1, -1);
			}
		});

		structures.put(GOTWaypoint.NAGGAS_HILL, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityAeronGreyjoy(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.NIGHT_KING, new Spawner() {
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
		structures.put(GOTWaypoint.NINESTARS, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntitySymondTempleton(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.NORVOS.info(toEssosTownGate(0.0, true), -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMellario(world), world, 0, 1, 1);
			}
		});

		structures.put(GOTWaypoint.OLDTOWN.info(-1.1, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityEbrose(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.PENTOS.info(-0.5, toEssosTownGate(0.0, false)), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityIllyrioMopatis(world), world, 3, 1, 0);
			}
		});

		structures.put(GOTWaypoint.PINKMAIDEN_CASTLE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityClementPiper(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.PYKE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBalonGreyjoy(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityYaraGreyjoy(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityTheonGreyjoy.TheonGreyjoyNormal(world), world, -2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.QARTH.info(toEssosTownGate(0.0, false), 0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityXaroXhoanDaxos(world), world, 3, 1, 0);
			}
		});

		structures.put(GOTWaypoint.RAVENTREE_HALL, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityTytosBlackwood(world), world, 2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.REDFORT, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHortonRedfort(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.RED_HAVEN, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityErikIronmaker(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.RING.info(0, -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityQuennRoxton(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.RYSWELLS_CASTLE.info(-0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRodrikRyswell(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.RIVERRUN.info(0, -0.4), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBryndenTully(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityEdmureTully(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityHosterTully(world), world, 2, 1, -2);
				spawnLegendaryNPC(new GOTEntityRodrikCassel(world), world, -2, 1, 2);
				spawnLegendaryNPC(new GOTEntityCatelynStark(world), world, 2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.RUNESTONE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityYohnRoyce(world), world, 2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.SANDSTONE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityQuentynQorgyle(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.SEAGARD.info(-0.1, -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityJasonMallister(world), world, 0, 1, 3);
			}
		});

		structures.put(GOTWaypoint.CASTLE_CERWYN.info(-0.4, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityCleyCerwyn(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.SKYREACH.info(0, 0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityFranklynFowler(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.SPIDER, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHummel009(world), world, 0, 1, 0);
			}
		});

		structures.put(GOTWaypoint.STARFALL.info(0, 0.6), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBericDayne(world), world, -2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.STARFISH_HARBOR, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityPaxterRedwyne(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.STONE_HEDGE.info(0, -0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityJonosBracken(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.STONEHELM, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityGulianSwann(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.STORMS_END.info(0, 0.5), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRenlyBaratheon(world), world, 2, 1, 2);
				spawnLegendaryNPC(new GOTEntityLorasTyrell(world), world, -2, 1, -2);
				spawnLegendaryNPC(new GOTEntityBrienneTarth(world), world, -2, 1, 2);
			}
		});

		structures.put(GOTWaypoint.STRONGSONG, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBenedarBelmore(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.SUNSPEAR, new Spawner() {
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

		structures.put(GOTWaypoint.TEN_TOWERS, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRodrikHarlaw(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.THE_EYRIE.info(0, -0.4), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityRobinArryn(world), world, 0, 1, 2);
				spawnLegendaryNPC(new GOTEntityLysaArryn(world), world, -2, 1, 0);
			}
		});

		structures.put(GOTWaypoint.THREE_EYED_RAVEN_CAVE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityThreeEyedRaven(world), world, 0, 2, 5);
			}
		});

		structures.put(GOTWaypoint.TORRHENS_SQUARE.info(-0.4, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityHelmanTallhart(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.TWINS_LEFT, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBlackWalderFrey(world), world, 0, 2, -15);
				spawnLegendaryNPC(new GOTEntityLotharFrey(world), world, 0, 2, -15);
			}
		});

		structures.put(GOTWaypoint.TWINS_RIGHT, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityWalderFrey(world), world, 0, 2, -15);
			}
		});

		structures.put(GOTWaypoint.TYROSH, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityJonConnington(world), world, 0, 1, -1);
				spawnLegendaryNPC(new GOTEntityYoungGriff(world), world, 0, 1, -1);
			}
		});

		structures.put(GOTWaypoint.VAES_EFE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityDaenerysTargaryen(world), world, 0, 5, 3);
				spawnLegendaryNPC(new GOTEntityJorahMormont(world), world, 0, 5, 3);
			}
		});

		structures.put(GOTWaypoint.VOLANTIS.info(toEssosTownGate(0.0, false) - 0.2, 0.6), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMoqorro(world), world, -1, 1, 0);
			}
		});

		structures.put(GOTWaypoint.VOLMARK, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityMaronVolmark(world), world, -2, 1, -2);
			}
		});

		structures.put(GOTWaypoint.WHITE_HARBOUR.info(0.8, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityWymanManderly(world), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.WHITETREE, new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBenjenStark(world).setIsRider(true), world, 0, 1, 5);
			}
		});

		structures.put(GOTWaypoint.WINTERFELL.info(-0.5, -0.1), new Spawner() {
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

		structures.put(GOTWaypoint.YIN.info(0, 1), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityBuGai(world), world, 12, 1, 0);
			}
		});

		structures.put(GOTWaypoint.YRONWOOD.info(0.5, 0), new Spawner() {
			@Override
			public void spawnLegendaryNPC(World world) {
				spawnLegendaryNPC(new GOTEntityQuentynMartell(world), world, 0, 1, 2);
				spawnLegendaryNPC(new GOTEntityAndersYronwood(world), world, 0, 1, 2);
			}
		});

		structures.put(GOTWaypoint.YUNKAI.info(-0.5, toEssosTownGate(0.0, false)), new Spawner() {
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