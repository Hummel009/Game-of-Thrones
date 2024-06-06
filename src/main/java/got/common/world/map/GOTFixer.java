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
	public static final Map<GOTAbstractWaypoint, GOTStructureBaseSettlement> SPAWNERS = new HashMap<>();

	private static final Collection<GOTStructureBaseSettlement> LOCATIONS = new HashSet<>();

	private GOTFixer() {
	}

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
		if (GOTFixedStructures.fixedAt(i, k, GOTWaypoint.EURON)) {
			new EuronShip().generate(world, random, i, 55, k);
		}
	}

	public static void onInit() {
		List<GOTAbstractWaypoint> wps = new ArrayList<>();

		wps.add(GOTWaypoint.BLOODY_GATE.info(0, -0.5));
		wps.add(GOTWaypoint.COLDWATER_BURN);
		wps.add(GOTWaypoint.GREY_GLEN);
		wps.add(GOTWaypoint.OLD_ANCHOR);
		wps.add(GOTWaypoint.PEBBLE);
		wps.add(GOTWaypoint.SNAKEWOOD);
		wps.add(GOTWaypoint.THE_PAPS);
		wps.add(GOTWaypoint.WICKENDEN);
		wps.add(GOTWaypoint.WITCH_ISLE);
		wps.add(GOTWaypoint.GATE_OF_THE_MOON.info(0.5, 0, Dir.EAST));

		registerLocation(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureArrynSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.SISTERTON);

		registerLocation(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureArrynSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.ANTLERS.info(0.1, -0.5));
		wps.add(GOTWaypoint.BROWNHOLLOW);
		wps.add(GOTWaypoint.DYRE_DEN);
		wps.add(GOTWaypoint.STOKEWORTH.info(-0.4, 0, Dir.WEST));
		wps.add(GOTWaypoint.HAYFORD.info(0.1, -0.5));
		wps.add(GOTWaypoint.ROOKS_REST.info(0, -0.4));
		wps.add(GOTWaypoint.ROSBY.info(-0.5, -0.1, Dir.WEST));

		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.KINGS_LANDING.info(2, 0, Dir.EAST));

		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.RED_KEEP, 3), wps);

		wps.add(GOTWaypoint.DUSKENDALE.info(-0.1, -1.1));

		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.BRIARWHITE.info(0, -0.7));
		wps.add(GOTWaypoint.ROSBY.info(0.7, 0));

		registerLocation(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureCrownlandsSettlement.Type.VILLAGE, 6), wps);

		wps.add(GOTWaypoint.BLACKMONT.info(0.5, 0, Dir.EAST));
		wps.add(GOTWaypoint.GHOST_HILL);
		wps.add(GOTWaypoint.GODSGRACE);
		wps.add(GOTWaypoint.THE_TOR);
		wps.add(GOTWaypoint.VAITH);
		wps.add(GOTWaypoint.WATER_GARDENS);
		wps.add(GOTWaypoint.SPOTTSWOOD);
		wps.add(GOTWaypoint.LEMONWOOD);
		wps.add(GOTWaypoint.SALT_SHORE);
		wps.add(GOTWaypoint.KINGSGRAVE.info(0.5, 0, Dir.EAST));
		wps.add(GOTWaypoint.WYL.info(-0.55, 0, Dir.WEST));

		registerLocation(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDorneSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.GHASTON_GREY);
		wps.add(GOTWaypoint.PLANKY_TOWN);

		registerLocation(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDorneSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.HORNOTH.info(0.5, 0));
		wps.add(GOTWaypoint.KYTH.info(0.3, -0.4));
		wps.add(GOTWaypoint.SATHAR.info(0.5, 0));
		wps.add(GOTWaypoint.RATHYLAR.info(0.6, -0.2));
		wps.add(GOTWaypoint.SAATH.info(0, -0.5));
		wps.add(GOTWaypoint.VAES_ATHJIKHARI.info(0, -0.5));
		wps.add(GOTWaypoint.VAES_DIAF);
		wps.add(GOTWaypoint.VAES_DOTHRAK);
		wps.add(GOTWaypoint.VAES_GORQOYI.info(0.3, -0.4));
		wps.add(GOTWaypoint.VAES_GRADDAKH);
		wps.add(GOTWaypoint.VAES_JINI.info(0, 0.5));
		wps.add(GOTWaypoint.VOJJOR_SAMVI.info(0, -0.5));
		wps.add(GOTWaypoint.VAES_KHADOKH.info(-0.1, -0.5));
		wps.add(GOTWaypoint.VAES_KHEWO.info(0, -0.5));
		wps.add(GOTWaypoint.VAES_LEQSE.info(0.5, 0));
		wps.add(GOTWaypoint.VAES_MEJHAH.info(0, -0.5));
		wps.add(GOTWaypoint.KRAZAAJ_HAS.info(0, -0.6));

		registerLocation(new GOTStructureDothrakiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDothrakiSettlement.Type.BIG, 5), wps);

		wps.add(GOTWaypoint.HIGH_TIDE);
		wps.add(GOTWaypoint.SHARP_POINT);
		wps.add(GOTWaypoint.STONEDANCE);
		wps.add(GOTWaypoint.SWEETPORT_SOUND);

		registerLocation(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDragonstoneSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.HULL);

		registerLocation(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureDragonstoneSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.ISLE_OF_WHIPS);
		wps.add(GOTWaypoint.BARTER_BEACH);
		wps.add(GOTWaypoint.GOGOSSOS);
		wps.add(GOTWaypoint.GOROSH);
		wps.add(GOTWaypoint.ZAMETTAR.info(0, -0.5, Dir.SOUTH));

		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.COLONY, 4), wps);

		wps.add(GOTWaypoint.MEEREEN.info(toEssosTownGate(0.0, true), -1.5));
		wps.add(GOTWaypoint.ASTAPOR.info(-1.5, toEssosTownGate(0.0, false)));
		wps.add(GOTWaypoint.NEW_GHIS.info(-1, 0));
		wps.add(GOTWaypoint.YUNKAI.info(-1.5, toEssosTownGate(0.0, false)));

		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.PYRAMID, 3), wps);

		wps.add(GOTWaypoint.BHORASH.info(toEssosTownGate(0.0, false) + 0.26, 0.7));
		wps.add(GOTWaypoint.VELOS);
		wps.add(GOTWaypoint.GHOZAI);
		wps.add(GOTWaypoint.ELYRIA);
		wps.add(GOTWaypoint.TOLOS.info(toEssosTownGate(0.0, false), 0.5));
		wps.add(GOTWaypoint.NEW_GHIS);

		registerLocation(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGhiscarSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.GREENGUARD);
		wps.add(GOTWaypoint.THE_TORCHES);
		wps.add(GOTWaypoint.THE_LONG_BARROW);
		wps.add(GOTWaypoint.RIMEGATE);
		wps.add(GOTWaypoint.SABLE_HALL);
		wps.add(GOTWaypoint.WOODSWATCH);
		wps.add(GOTWaypoint.NIGHTFORT);
		wps.add(GOTWaypoint.DEEP_LAKE);
		wps.add(GOTWaypoint.OAKENSHIELD);
		wps.add(GOTWaypoint.ICEMARK);
		wps.add(GOTWaypoint.HOARFROST_HILL);
		wps.add(GOTWaypoint.STONEDOOR);
		wps.add(GOTWaypoint.GREYGUARD);
		wps.add(GOTWaypoint.QUEENSGATE);
		wps.add(GOTWaypoint.SENTINEL_STAND);

		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.ABANDONED, 2), wps);

		wps.add(GOTWaypoint.CASTLE_BLACK);

		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.CASTLE_BLACK, 2), wps);

		wps.add(GOTWaypoint.EASTWATCH);

		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.EAST_WATCH, 2), wps);

		wps.add(GOTWaypoint.SHADOW_TOWER);

		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.SHADOW_TOWER, 2), wps);

		wps.add(GOTWaypoint.MOLETOWN);
		wps.add(GOTWaypoint.QUEENSCROWN);

		registerLocation(new GOTStructureGiftSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureGiftSettlement.Type.VILLAGE, 4), wps);

		wps.add(GOTWaypoint.IB_NOR);
		wps.add(GOTWaypoint.IB_SAR);
		wps.add(GOTWaypoint.PORT_OF_IBBEN);

		registerLocation(new GOTStructureIbbenSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIbbenSettlement.Type.VILLAGE, 6), wps);

		wps.add(GOTWaypoint.NEW_IBBISH);

		registerLocation(new GOTStructureIbbenSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIbbenSettlement.Type.FORT, 6), wps);

		wps.add(GOTWaypoint.VICTARION_LANDING);

		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.CAMP, 5), wps);

		wps.add(GOTWaypoint.CATFISH_ROCK);
		wps.add(GOTWaypoint.CORPSE_LAKE);
		wps.add(GOTWaypoint.CROW_SPIKE_KEEP);
		wps.add(GOTWaypoint.DOWNDELVING);
		wps.add(GOTWaypoint.HARLAW_HALL);
		wps.add(GOTWaypoint.HARRIDAN_HILL);
		wps.add(GOTWaypoint.IRON_HOLT);
		wps.add(GOTWaypoint.MYRE_CASTLE);
		wps.add(GOTWaypoint.ORKWOOD);
		wps.add(GOTWaypoint.SALTCLIFFE);
		wps.add(GOTWaypoint.SEALSKIN_POINT);
		wps.add(GOTWaypoint.SHATTERSTONE);
		wps.add(GOTWaypoint.SPARR_CASTLE);
		wps.add(GOTWaypoint.STONEHOUSE);
		wps.add(GOTWaypoint.STONETREE_CASTLE);
		wps.add(GOTWaypoint.SUNDERLY);
		wps.add(GOTWaypoint.TAWNEY_CASTLE);

		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.PEBBLETON);

		registerLocation(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureIronbornSettlement.Type.VILLAGE, 6), wps);

		wps.add(GOTWaypoint.HESH.info(1.2, 0, Dir.WEST));
		wps.add(GOTWaypoint.KOSRAK.info(1, 0, Dir.WEST));
		wps.add(GOTWaypoint.LHAZOSH.info(1, 0, Dir.WEST));

		registerLocation(new GOTStructureLhazarSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureLhazarSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.LORATH);
		wps.add(GOTWaypoint.MOROSH);

		registerLocation(new GOTStructureLorathSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureLorathSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.KUURULGAN);
		wps.add(GOTWaypoint.SUUDAN_KORKUU);
		wps.add(GOTWaypoint.KANDUU_BET);
		wps.add(GOTWaypoint.TASHTOO);
		wps.add(GOTWaypoint.BASHKARUUCHU);
		wps.add(GOTWaypoint.AZUU_KAN);
		wps.add(GOTWaypoint.KUJRUK);
		wps.add(GOTWaypoint.KORKUNUCHTUU);
		wps.add(GOTWaypoint.NYMDUU_OOZ);
		wps.add(GOTWaypoint.AZHYDAAR);
		wps.add(GOTWaypoint.AK_SHAAR);
		wps.add(GOTWaypoint.SUU_NYM);
		wps.add(GOTWaypoint.SHYLUUN_UUSU);
		wps.add(GOTWaypoint.KADAR);
		wps.add(GOTWaypoint.NEFER);
		wps.add(GOTWaypoint.K_DATH);

		registerLocation(new GOTStructureMossovySettlement(GOTBiome.ocean, 0.0f).type(GOTStructureMossovySettlement.Type.VILLAGE, 6), wps);

		wps.add(GOTWaypoint.CATFISH_ROCK);
		wps.add(GOTWaypoint.GOLDGRASS.info(0, 0.4, Dir.SOUTH));
		wps.add(GOTWaypoint.BLACK_POOL);
		wps.add(GOTWaypoint.DEEPWOOD_MOTTE);
		wps.add(GOTWaypoint.FLINTS_FINGER);
		wps.add(GOTWaypoint.HIGHPOINT);
		wps.add(GOTWaypoint.WIDOWS_WATCH);
		wps.add(GOTWaypoint.HORNWOOD);
		wps.add(GOTWaypoint.IRONRATH);
		wps.add(GOTWaypoint.MOAT_KAILIN);
		wps.add(GOTWaypoint.OLDCASTLE);
		wps.add(GOTWaypoint.RAMSGATE);
		wps.add(GOTWaypoint.RILLWATER_CROSSING.info(-0.4, 0, Dir.WEST));

		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.BREAKSTONE_HILL);
		wps.add(GOTWaypoint.SKANE);
		wps.add(GOTWaypoint.DEEPDOWN);
		wps.add(GOTWaypoint.DRIFTWOOD_HALL);
		wps.add(GOTWaypoint.KINGSHOUSE);

		registerLocation(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureNorthSettlement.Type.HILLMAN, 6), wps);

		wps.add(GOTWaypoint.GHOYAN_DROHE.info(0, 0.7));

		registerLocation(new GOTStructurePentosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructurePentosSettlement.Type.VILLAGE, 5), wps);

		wps.add(GOTWaypoint.TERIMAN);
		wps.add(GOTWaypoint.BATARGAS);
		wps.add(GOTWaypoint.KARIMAGAR);

		registerLocation(new GOTStructureQarthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQarthSettlement.Type.COLONY, 4), wps);

		wps.add(GOTWaypoint.PORT_YHOS.info(toEssosTownGate(0.0, false), 0.5));
		wps.add(GOTWaypoint.QARKASH.info(toEssosTownGate(0.0, false), 0.5));

		registerLocation(new GOTStructureQarthSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQarthSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.QOHOR.info(-0.6, toEssosTownGate(0.0, false), Dir.EAST));

		registerLocation(new GOTStructureQohorSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureQohorSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.APPLETON.info(0, -0.51));
		wps.add(GOTWaypoint.ASHFORD);
		wps.add(GOTWaypoint.BANDALLON);
		wps.add(GOTWaypoint.GRASSY_VALE);
		wps.add(GOTWaypoint.GRIMSTON);
		wps.add(GOTWaypoint.HAMMERHAL.info(0.5, 0, Dir.EAST));
		wps.add(GOTWaypoint.RED_LAKE.info(0, 0.5, Dir.SOUTH));
		wps.add(GOTWaypoint.SOUTHSHIELD);
		wps.add(GOTWaypoint.UPLANDS);
		wps.add(GOTWaypoint.HOLYHALL.info(0.5, 0, Dir.EAST));
		wps.add(GOTWaypoint.HONEYHOLT);
		wps.add(GOTWaypoint.IVY_HALL.info(0, 0.5, Dir.SOUTH));
		wps.add(GOTWaypoint.NEW_BARREL);
		wps.add(GOTWaypoint.BLACKCROWN);
		wps.add(GOTWaypoint.CIDER_HALL);
		wps.add(GOTWaypoint.COLDMOAT.info(0, 0.5, Dir.SOUTH));
		wps.add(GOTWaypoint.DARKDELL.info(-0.5, 0, Dir.WEST));
		wps.add(GOTWaypoint.DUNSTONBURY);
		wps.add(GOTWaypoint.BITTERBRIDGE.info(0, 0.5, Dir.SOUTH));
		wps.add(GOTWaypoint.GARNETGROVE.info(-0.5, 0, Dir.WEST));
		wps.add(GOTWaypoint.LORD_HEWETTS_TOWN);
		wps.add(GOTWaypoint.OLD_OAK.info(0, 0.5, Dir.SOUTH));
		wps.add(GOTWaypoint.SUNHOUSE.info(0, 0.5, Dir.SOUTH));
		wps.add(GOTWaypoint.WHITEGROVE.info(-0.5, 0, Dir.WEST));

		registerLocation(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureReachSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.APPLETON.info(0.1, 1.1, Dir.SOUTH));
		wps.add(GOTWaypoint.ASHFORD);
		wps.add(GOTWaypoint.LORD_HEWETTS_TOWN);
		wps.add(GOTWaypoint.SMITHYTON.info(0, 0.9, Dir.SOUTH));
		wps.add(GOTWaypoint.VINETOWN);
		wps.add(GOTWaypoint.RYAMSPORT);
		wps.add(GOTWaypoint.TUMBLETON.info(0, -1.0));

		registerLocation(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureReachSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.CROSSROADS_INN);

		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.CROSSROADS, 2), wps);

		wps.add(GOTWaypoint.DARRY);
		wps.add(GOTWaypoint.WAYFARERS_REST.info(-0.4, 0, Dir.WEST));
		wps.add(GOTWaypoint.ACORN_HALL.info(-0.5, -0.1, Dir.WEST));
		wps.add(GOTWaypoint.ATRANTA);

		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.HARROWAY.info(0, 0.9, Dir.SOUTH));
		wps.add(GOTWaypoint.MAIDENPOOL.info(0, 0.9, Dir.SOUTH));
		wps.add(GOTWaypoint.SALTPANS.info(0.8, 0, Dir.EAST));
		wps.add(GOTWaypoint.STONEY_SEPT.info(-0.8, 0, Dir.WEST));
		wps.add(GOTWaypoint.SEAGARD.info(0, 0.8, Dir.SOUTH));

		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.FAIRMARKET);
		wps.add(GOTWaypoint.PENNYTREE);
		wps.add(GOTWaypoint.SEVENSTREAMS);

		registerLocation(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureRiverlandsSettlement.Type.VILLAGE, 6), wps);

		wps.add(GOTWaypoint.WESTWATCH.info(-0.3, 0));
		wps.add(GOTWaypoint.VAES_LEISI.info(0, 0.3));
		wps.add(GOTWaypoint.MORNE.info(0, 0.3));
		wps.add(GOTWaypoint.NY_SAR.info(0, 0.15));
		wps.add(GOTWaypoint.OLD_GHIS.info(0, 0.3));
		wps.add(GOTWaypoint.SAR_MELL.info(0, -0.25));
		wps.add(GOTWaypoint.SARHOY.info(0, 0.3));
		wps.add(GOTWaypoint.SHANDYSTONE.info(0, 0.3));
		wps.add(GOTWaypoint.STARPIKE.info(0, 0.3));
		wps.add(GOTWaypoint.TELYRIA.info(0, 0.3));
		wps.add(GOTWaypoint.TORTURERS_DEEP.info(0, 0.3));
		wps.add(GOTWaypoint.VAES_ORVIK.info(0.3, 0));
		wps.add(GOTWaypoint.VAES_QOSAR.info(0, 0.3));
		wps.add(GOTWaypoint.VAES_SHIROSI.info(0, 0.3));
		wps.add(GOTWaypoint.VAES_TOLORRO.info(0, 0.3));
		wps.add(GOTWaypoint.VALYRIAN_ROAD.info(-0.2, 0));
		wps.add(GOTWaypoint.GREY_GALLOWS.info(0, 0.3));
		wps.add(GOTWaypoint.BLOODSTONE.info(0, 0.3));
		wps.add(GOTWaypoint.KAYAKAYANAYA.info(0, 0.3));
		wps.add(GOTWaypoint.AEGON.info(0, 0.3));
		wps.add(GOTWaypoint.RAENYS.info(0, 0.3));
		wps.add(GOTWaypoint.VISENYA.info(0, 0.3));
		wps.add(GOTWaypoint.CHROYANE.info(0.3, 0));
		wps.add(GOTWaypoint.FOURTEEN_FLAMES.info(0, 0.3));
		wps.add(GOTWaypoint.IBBISH.info(0, 0.3));
		wps.add(GOTWaypoint.SAMYRIANA.info(0, 0.3));
		wps.add(GOTWaypoint.BAYASABHAD.info(0, 0.3));
		wps.add(GOTWaypoint.AR_NOY.info(-0.1, -0.25));
		wps.add(GOTWaypoint.ADAKHAKILEKI.info(0, 0.3));
		wps.add(GOTWaypoint.CASTLE_LYCHESTER.info(0, -0.3));
		wps.add(GOTWaypoint.MHYSA_FAER.info(0, 0.3));
		wps.add(GOTWaypoint.AQUOS_DHAEN.info(0, 0.3));
		wps.add(GOTWaypoint.DRACONYS.info(0, 0.3));
		wps.add(GOTWaypoint.TYRIA.info(0, 0.3));
		wps.add(GOTWaypoint.RHYOS.info(0, 0.3));
		wps.add(GOTWaypoint.OROS.info(0, 0.2));
		wps.add(GOTWaypoint.VULTURES_ROOST.info(0, 0.3));
		wps.add(GOTWaypoint.SPICETOWN.info(0, 0.3));
		wps.add(GOTWaypoint.CASTAMERE.info(0, 0.3));
		wps.add(GOTWaypoint.GOLDENHILL.info(0, 0.3));
		wps.add(GOTWaypoint.GREYIRON_CASTLE.info(0, 0.3));
		wps.add(GOTWaypoint.HOARE_CASTLE.info(0, 0.3));
		wps.add(GOTWaypoint.HOARE_KEEP.info(0, 0.3));
		wps.add(GOTWaypoint.HOGG_HALL.info(0.2, -0.2));
		wps.add(GOTWaypoint.HOLLARD_CASTLE.info(0.3, 0));
		wps.add(GOTWaypoint.OLDSTONES.info(0, 0.3));
		wps.add(GOTWaypoint.SUMMERHALL.info(-0.3, 0));
		wps.add(GOTWaypoint.TARBECK_HALL.info(0, 0.3));
		wps.add(GOTWaypoint.TOWER_OF_JOY.info(-0.3, 0));
		wps.add(GOTWaypoint.WHISPERS.info(0, 0.3));
		wps.add(GOTWaypoint.WHITEWALLS.info(0.2, -0.2));

		registerLocation(new GOTStructureRuins(GOTBiome.ocean, 0.0f), wps);

		wps.add(GOTWaypoint.EAST_BAY.info(0, 0.4));
		wps.add(GOTWaypoint.EAST_COAST.info(0, 0.4));
		wps.add(GOTWaypoint.NORTH_FORESTS.info(0, 0.4));
		wps.add(GOTWaypoint.WHITE_MOUNTAINS.info(0, 0.4));
		wps.add(GOTWaypoint.CENTRAL_FORESTS.info(0, 0.4));
		wps.add(GOTWaypoint.MARSHES.info(0, 0.4));
		wps.add(GOTWaypoint.RED_FORESTS.info(0, 0.4));
		wps.add(GOTWaypoint.SOUTH_ULTHOS.info(0, 0.4));
		wps.add(GOTWaypoint.SOUTH_TAIGA.info(0, 0.4));
		wps.add(GOTWaypoint.BONETOWN.info(0, 0.4));
		wps.add(GOTWaypoint.HARRENHAL.info(-0.4, 0));
		wps.add(GOTWaypoint.STYGAI.info(0, 0.4));
		wps.add(GOTWaypoint.ULOS.info(0, 0.4));
		wps.add(GOTWaypoint.YEEN.info(0, 0.4));

		registerLocation(new GOTStructureRuinsBig(GOTBiome.ocean, 0.0f), wps);

		wps.add(GOTWaypoint.RAUMATI);

		registerLocation(new GOTStructureSothoryosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSothoryosSettlement.Type.PYRAMID, 3), wps);

		wps.add(GOTWaypoint.MAUNGA);
		wps.add(GOTWaypoint.DRAGON_PLACE);
		wps.add(GOTWaypoint.SOUTH_POINT);
		wps.add(GOTWaypoint.BIG_LAKE);

		registerLocation(new GOTStructureSothoryosSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSothoryosSettlement.Type.VILLAGE, 3), wps);

		wps.add(GOTWaypoint.CROWS_NEST);
		wps.add(GOTWaypoint.GRIFFINS_ROOST);
		wps.add(GOTWaypoint.BLACKHAVEN.info(-0.5, 0, Dir.WEST));
		wps.add(GOTWaypoint.BRONZEGATE.info(0, -0.5));
		wps.add(GOTWaypoint.DEAD_HEAD);
		wps.add(GOTWaypoint.FAWNTOWN);
		wps.add(GOTWaypoint.AMBERLY);
		wps.add(GOTWaypoint.BLACK_HEART);
		wps.add(GOTWaypoint.BROAD_ARCH);
		wps.add(GOTWaypoint.PARCHMENTS.info(0, -0.5));
		wps.add(GOTWaypoint.PODDINGFIELD.info(0, -0.5));
		wps.add(GOTWaypoint.RAIN_HOUSE);
		wps.add(GOTWaypoint.SEAWORTH_CASTLE);
		wps.add(GOTWaypoint.TUDBURY_HOLL);
		wps.add(GOTWaypoint.GALLOWSGREY.info(0, -0.5));
		wps.add(GOTWaypoint.GRANDVIEW.info(-0.5, 0, Dir.WEST));
		wps.add(GOTWaypoint.HARVEST_HALL.info(0, -0.5));
		wps.add(GOTWaypoint.HAYSTACK_HALL.info(0, -0.5));
		wps.add(GOTWaypoint.MISTWOOD);
		wps.add(GOTWaypoint.FELWOOD.info(0, -0.5));
		wps.add(GOTWaypoint.NIGHTSONG.info(0, -0.5));

		registerLocation(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureStormlandsSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.WEEPING_TOWN);

		registerLocation(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureStormlandsSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.HAUAURU);
		wps.add(GOTWaypoint.MATAHAU);
		wps.add(GOTWaypoint.TAKUTAI);
		wps.add(GOTWaypoint.ATAAHUA);
		wps.add(GOTWaypoint.PEREKI);
		wps.add(GOTWaypoint.NGARARA);
		wps.add(GOTWaypoint.TAURANGA);
		wps.add(GOTWaypoint.MATAO);
		wps.add(GOTWaypoint.NGAHERE);
		wps.add(GOTWaypoint.KOHURU);

		registerLocation(new GOTStructureSummerSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSummerSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.DOQUU);
		wps.add(GOTWaypoint.EBONHEAD);
		wps.add(GOTWaypoint.GOLDEN_HEAD);
		wps.add(GOTWaypoint.KOJ);
		wps.add(GOTWaypoint.LAST_LAMENT);
		wps.add(GOTWaypoint.LIZARD_HEAD);
		wps.add(GOTWaypoint.LOTUS_POINT);
		wps.add(GOTWaypoint.NAATH);
		wps.add(GOTWaypoint.OMBORU);
		wps.add(GOTWaypoint.PEARL_PALACE);
		wps.add(GOTWaypoint.RED_FLOWER_VALE);
		wps.add(GOTWaypoint.SWEET_LOTUS_VALE);
		wps.add(GOTWaypoint.TALL_TREES_TOWN);
		wps.add(GOTWaypoint.WALANO);
		wps.add(GOTWaypoint.XON);

		registerLocation(new GOTStructureSummerSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureSummerSettlement.Type.VILLAGE, 5), wps);

		wps.add(GOTWaypoint.THREE_TOWERS.info(-0.5, -0.5, Dir.WEST));
		wps.add(GOTWaypoint.THREE_TOWERS.info(-0.5, 0, Dir.WEST));
		wps.add(GOTWaypoint.THREE_TOWERS.info(-0.5, 0.5, Dir.WEST));
		wps.add(GOTWaypoint.TOWER_OF_GLIMMERING);
		wps.add(GOTWaypoint.BAELISH_KEEP);
		wps.add(GOTWaypoint.RAMSEY_TOWER);
		wps.add(GOTWaypoint.STANDFAST);

		registerLocation(new GOTStructureTower(GOTBiome.ocean, 0.0f), wps);

		wps.add(GOTWaypoint.LITTLE_VALYRIA.info(toEssosTownGate(0.0, false) + 0.26, 0.6));
		wps.add(GOTWaypoint.ANOGARIA.info(toEssosTownGate(0.0, false) + 0.26, 0.6));
		wps.add(GOTWaypoint.MANTARYS.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH));
		wps.add(GOTWaypoint.SELHORYS.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));
		wps.add(GOTWaypoint.VALYSAR.info(-0.6, toEssosTownGate(0.0, false) + 0.25, Dir.EAST));
		wps.add(GOTWaypoint.VOLON_THERYS.info(toEssosTownGate(0.0, false), 0.5));

		registerLocation(new GOTStructureVolantisSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureVolantisSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.KAYCE);
		wps.add(GOTWaypoint.SARSFIELD.info(-0.1, -0.5));
		wps.add(GOTWaypoint.SILVERHILL);
		wps.add(GOTWaypoint.WYNDHALL);
		wps.add(GOTWaypoint.PLUMWOOD);
		wps.add(GOTWaypoint.RIVERSPRING);
		wps.add(GOTWaypoint.GREENFIELD);
		wps.add(GOTWaypoint.DEEP_DEN);
		wps.add(GOTWaypoint.CRAG);

		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.FORT, 3), wps);

		wps.add(GOTWaypoint.KAYCE);

		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.TOWN, 6), wps);

		wps.add(GOTWaypoint.OXCROSS.info(0.2, 0.6));

		registerLocation(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWesterlandsSettlement.Type.VILLAGE, 6), wps);

		wps.add(GOTWaypoint.CRASTERS_KEEP);

		registerLocation(new GOTStructureWildlingSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWildlingSettlement.Type.CRASTER, 1), wps);

		wps.add(GOTWaypoint.HARDHOME);

		registerLocation(new GOTStructureWildlingSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureWildlingSettlement.Type.HARDHOME, 7), wps);

		wps.add(GOTWaypoint.JIANMEN);
		wps.add(GOTWaypoint.ANGUO);
		wps.add(GOTWaypoint.DINGGUO);
		wps.add(GOTWaypoint.PINNU);
		wps.add(GOTWaypoint.PINGJIANG);
		wps.add(GOTWaypoint.WUDE);
		wps.add(GOTWaypoint.WUSHENG);
		wps.add(GOTWaypoint.ZHENGUO);
		wps.add(GOTWaypoint.LUNGMEN);

		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.GATE, 2), wps);

		wps.add(GOTWaypoint.ANJIANG);

		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.GATE_ROAD, 2), wps);

		wps.add(GOTWaypoint.FIVE_FORTS_1);
		wps.add(GOTWaypoint.FIVE_FORTS_2);
		wps.add(GOTWaypoint.FIVE_FORTS_3);
		wps.add(GOTWaypoint.FIVE_FORTS_4);
		wps.add(GOTWaypoint.FIVE_FORTS_5);

		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.TOWER, 9), wps);

		wps.add(GOTWaypoint.ASABHAD.info(-1, 0, Dir.WEST));
		wps.add(GOTWaypoint.BAOJI.info(0, -1));
		wps.add(GOTWaypoint.EIJIANG.info(0, 1, Dir.SOUTH));
		wps.add(GOTWaypoint.JINQI.info(-1, 0, Dir.WEST));
		wps.add(GOTWaypoint.LIZHAO.info(1, 0, Dir.EAST));
		wps.add(GOTWaypoint.MANJIN.info(1.1, -0.2, Dir.EAST));
		wps.add(GOTWaypoint.SI_QO.info(1, 0, Dir.EAST));
		wps.add(GOTWaypoint.TIQUI.info(0, -1));
		wps.add(GOTWaypoint.TRADER_TOWN.info(0, -1));
		wps.add(GOTWaypoint.VAIBEI.info(0, -1));
		wps.add(GOTWaypoint.YIBIN.info(0, -1));
		wps.add(GOTWaypoint.YUNNAN.info(1, 0, Dir.EAST));
		wps.add(GOTWaypoint.CHANGAN.info(1, 0, Dir.EAST));
		wps.add(GOTWaypoint.FU_NING.info(1.1, 0.1, Dir.EAST));
		wps.add(GOTWaypoint.ZABHAD);
		wps.add(GOTWaypoint.TURRANI);
		wps.add(GOTWaypoint.VAHAR);
		wps.add(GOTWaypoint.FAROS);
		wps.add(GOTWaypoint.HUIJI);
		wps.add(GOTWaypoint.LENG_MA);
		wps.add(GOTWaypoint.LENG_YI);
		wps.add(GOTWaypoint.LESSER_MORAQ);
		wps.add(GOTWaypoint.MARAHAI);
		wps.add(GOTWaypoint.PORT_MORAQ);

		registerLocation(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f).type(GOTStructureYiTiSettlement.Type.TOWN, 7), wps);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityAddamMarbrand(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.ASHEMARK);

		registerSpawner(new GOTStructureAsshaiSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityAsshaiArchmag(world), 0, 0));
				return spawnInfos;
			}
		}, GOTWaypoint.ASSHAI.info(0, 0.8, Dir.SOUTH));

		registerSpawner(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityKraznysMoNakloz(world), -1, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityMissandei(world), -1, -1));
				spawnInfos.add(new SpawnInfo(new GOTEntityGreyWorm(world), -1, 1));
				return spawnInfos;
			}
		}.type(GOTStructureGhiscarSettlement.Type.TOWN, 6), GOTWaypoint.ASTAPOR.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityQuentenBanefort(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.BANEFORT);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityBarbreyDustin(world), 0, 3));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.SMALL_TOWN, 6), GOTWaypoint.BARROWTOWN);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityBaelorBlacktyde(world), -2, -2));
				return spawnInfos;
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.BLACKTYDE);

		registerSpawner(new GOTStructureBraavosSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityTychoNestoris(world), 0, 1));
				return spawnInfos;
			}
		}.type(GOTStructureBraavosSettlement.Type.TOWN, 6), GOTWaypoint.BRAAVOS.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH));

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityGarlanTyrell(world), 2, -2));
				return spawnInfos;
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.BRIGHTWATER_KEEP);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityTywinLannister(world), 2, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityQyburn(world), -2, 0));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.CASTERLY_ROCK, 3), GOTWaypoint.CASTERLY_ROCK.info(-0.4, 0, Dir.WEST));

		registerSpawner(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityArdrianCeltigar(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureDragonstoneSettlement.Type.FORT, 3), GOTWaypoint.CLAW_ISLE);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityGregorClegane.GregorCleganeAlive(world), 2, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityPolliver(world), -2, 0));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.CLEGANES_KEEP);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityHarysSwyft(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.CORNFIELD);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityLyleCrakehall(world), 2, 2));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.CRAKEHALL.info(-0.5, 0, Dir.WEST));

		registerSpawner(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityStannisBaratheon(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityDavosSeaworth(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityMelisandra(world), -2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityShireenBaratheon(world), 2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntitySelyseBaratheon(world), 0, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityMatthosSeaworth(world), 0, -2));
				return spawnInfos;
			}
		}.type(GOTStructureDragonstoneSettlement.Type.FORT, 3), GOTWaypoint.DRAGONSTONE);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityRamsayBolton(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityRooseBolton(world), -2, -2));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.DREADFORT.info(0, -0.4));

		registerSpawner(new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityMonfordVelaryon(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityAuraneWaters(world), 2, 2));
				return spawnInfos;
			}
		}.type(GOTStructureDragonstoneSettlement.Type.FORT, 3), GOTWaypoint.DRIFTMARK);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityDunstanDrumm(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityAndrikTheUnsmilling(world), -2, -2));
				return spawnInfos;
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.DRUMM_CASTLE);

		registerSpawner(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntitySelwynTarth(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.EVENFALL_HALL);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntitySebastonFarman(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.FAIRCASTLE);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityForleyPrester(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.FEASTFIRES);

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityMathisRowan(world), 2, 2));
				return spawnInfos;
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.GOLDENGROVE);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityLeoLefford(world), 2, 2));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.GOLDEN_TOOTH.info(-0.1, -0.5));

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityMoribaldChester(world), 2, 0));
				return spawnInfos;
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.GREENSHIELD);

		registerSpawner(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityEldonEstermont(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.GREENSTONE);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityHarrasHarlaw(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.GREY_GARDEN);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityHowlandReed(world), 0, 5));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.VILLAGE, 6), GOTWaypoint.GREYWATER_WATCH);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityGeroldGrafton(world), 3, 0));
				return spawnInfos;
			}
		}.type(GOTStructureArrynSettlement.Type.TOWN, 6), GOTWaypoint.GULLTOWN);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityGoroldGoodbrother(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.HAMMERHORN);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityLynCorbray(world), 2, 2));
				return spawnInfos;
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.HEARTS_HOME);

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityHarmenUller(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.HELLHOLT);

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityMaceTyrell(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityOlennaTyrell(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityMargaeryTyrell(world), 2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityWillasTyrell(world), -2, 2));
				return spawnInfos;
			}
		}.type(GOTStructureReachSettlement.Type.HIGHGARDEN, 3), GOTWaypoint.HIGHGARDEN.info(0.5, 0, Dir.EAST));

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityGeroldDayne(world), 2, -2));
				return spawnInfos;
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.HIGH_HERMITAGE.info(0.5, 0, Dir.EAST));

		registerSpawner(new GOTStructureTower(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityLeytonHightower(world), 0, -5));
				return spawnInfos;
			}
		}, GOTWaypoint.HIGHTOWER_LITEHOUSE);

		registerSpawner(new GOTStructureJogosSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityTugarKhan(world), 0, 3));
				return spawnInfos;
			}
		}.type(GOTStructureJogosSettlement.Type.BIG, 5), GOTWaypoint.HOJDBAATAR);

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityBericDondarrion.BericDondarrionLife1(world), 3, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityThoros(world), 0, 3));
				return spawnInfos;
			}
		}, GOTWaypoint.HOLLOW_HILL);

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityRandyllTarly(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.HORN_HILL);

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityTytosBrax(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.FORT, 3), GOTWaypoint.HORNVALE);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityHarroldHardyng(world), 0, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityAnyaWaynwood(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.IRONOAKS);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityRickardKarstark(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.KARHOLD.info(0.4, 0, Dir.EAST));

		registerSpawner(new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntitySansaStark(world), 0, 5));
				spawnInfos.add(new SpawnInfo(new GOTEntityShae(world), 0, 6));
				spawnInfos.add(new SpawnInfo(new GOTEntityYoren(world), 0, 4));
				return spawnInfos;
			}
		}.type(GOTStructureCrownlandsSettlement.Type.KINGS_LANDING, 6), GOTWaypoint.KINGS_LANDING.info(0.9, 0, Dir.EAST));

		registerSpawner(new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityKevanLannister(world), 0, 4));
				spawnInfos.add(new SpawnInfo(new GOTEntityDavenLannister(world), 0, -4));
				spawnInfos.add(new SpawnInfo(new GOTEntityAmoryLorch(world), 4, 0));
				return spawnInfos;
			}
		}.type(GOTStructureWesterlandsSettlement.Type.TOWN, 6), GOTWaypoint.LANNISPORT.info(-0.8, 0, Dir.WEST));

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityJohnUmber(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.LAST_HEARTH);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityGylbertFarwynd(world), -2, -2));
				return spawnInfos;
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.LONELY_LIGHT);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityGilwoodHunter(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.LONGBOW_HALL);

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityOrtonMerryweather(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.LONGTABLE);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityDagmer(world), 0, 3));
				return spawnInfos;
			}
		}.type(GOTStructureIronbornSettlement.Type.TOWN, 6), GOTWaypoint.LORDSPORT);

		registerSpawner(new GOTStructureLysSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntitySalladhorSaan(world), 0, -1));
				return spawnInfos;
			}
		}.type(GOTStructureLysSettlement.Type.TOWN, 6), GOTWaypoint.LYS);

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityWilliamMooton(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.MAIDENPOOL.info(0, -0.5));

		registerSpawner(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityHizdahrZoLoraq(world), -1, -1));
				return spawnInfos;
			}
		}.type(GOTStructureGhiscarSettlement.Type.TOWN, 6), GOTWaypoint.MEEREEN.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH));

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityMaegeMormont(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.MORMONTS_KEEP);

		registerSpawner(new GOTStructureMyrSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityHarryStrickland(world), -1, -1));
				return spawnInfos;
			}
		}.type(GOTStructureMyrSettlement.Type.TOWN, 6), GOTWaypoint.MYR.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityAeronGreyjoy(world), 0, 2));
				return spawnInfos;
			}
		}, GOTWaypoint.NAGGAS_HILL);

		registerSpawner(new GOTStructureEmptySettlement(2) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityNightKing(world), 0, 0));
				return spawnInfos;
			}
		}, GOTWaypoint.NIGHT_KING);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntitySymondTempleton(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.NINESTARS);

		registerSpawner(new GOTStructureNorvosSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityMellario(world), 0, 1));
				return spawnInfos;
			}
		}.type(GOTStructureNorvosSettlement.Type.TOWN, 6), GOTWaypoint.NORVOS.info(toEssosTownGate(0.0, true), -0.5, Dir.SOUTH));

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityEbrose(world), 0, 5));
				return spawnInfos;
			}
		}.type(GOTStructureReachSettlement.Type.TOWN, 6), GOTWaypoint.OLDTOWN.info(-1.1, 0, Dir.WEST));

		registerSpawner(new GOTStructurePentosSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityIllyrioMopatis(world), 3, 0));
				return spawnInfos;
			}
		}.type(GOTStructurePentosSettlement.Type.TOWN, 6), GOTWaypoint.PENTOS.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityClementPiper(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.PINKMAIDEN_CASTLE);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityBalonGreyjoy(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityYaraGreyjoy(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityTheonGreyjoy.TheonGreyjoyNormal(world), -2, 2));
				return spawnInfos;
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.PYKE);

		registerSpawner(new GOTStructureQarthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityXaroXhoanDaxos(world), 3, 0));
				return spawnInfos;
			}
		}.type(GOTStructureQarthSettlement.Type.TOWN, 6), GOTWaypoint.QARTH.info(toEssosTownGate(0.0, false), 0.5));

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityTytosBlackwood(world), 2, 0));
				return spawnInfos;
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.RAVENTREE_HALL);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityHortonRedfort(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.REDFORT);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityErikIronmaker(world), -2, -2));
				return spawnInfos;
			}
		}.type(GOTStructureIronbornSettlement.Type.TOWN, 6), GOTWaypoint.RED_HAVEN);

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityQuennRoxton(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureReachSettlement.Type.FORT, 3), GOTWaypoint.RING.info(0, -0.5));

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityRodrikRyswell(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.RYSWELLS_CASTLE.info(-0.5, 0, Dir.WEST));

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityBryndenTully(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityEdmureTully(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityHosterTully(world), 2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityRodrikCassel(world), -2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityCatelynStark(world), 2, 0));
				return spawnInfos;
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.RIVERRUN.info(0, -0.4));

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityYohnRoyce(world), 2, 0));
				return spawnInfos;
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.RUNESTONE);

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityQuentynQorgyle(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.SANDSTONE);

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityJasonMallister(world), 0, 3));
				return spawnInfos;
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.SEAGARD.info(-0.1, -0.5));

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityCleyCerwyn(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.CASTLE_CERWYN.info(-0.4, 0, Dir.WEST));

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityFranklynFowler(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.SKYREACH.info(0, 0.5, Dir.SOUTH));

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityHummel009(world), 0, 0));
				return spawnInfos;
			}
		}, GOTWaypoint.SPIDER);

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityBericDayne(world), -2, 2));
				return spawnInfos;
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.STARFALL.info(0, 0.6, Dir.SOUTH));

		registerSpawner(new GOTStructureReachSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityPaxterRedwyne(world), 0, 5));
				return spawnInfos;
			}
		}.type(GOTStructureReachSettlement.Type.TOWN, 6), GOTWaypoint.STARFISH_HARBOR);

		registerSpawner(new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityJonosBracken(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureRiverlandsSettlement.Type.FORT, 3), GOTWaypoint.STONE_HEDGE.info(0, -0.4));

		registerSpawner(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityGulianSwann(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.STONEHELM);

		registerSpawner(new GOTStructureStormlandsSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityRenlyBaratheon(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityLorasTyrell(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityBrienneTarth(world), -2, 2));
				return spawnInfos;
			}
		}.type(GOTStructureStormlandsSettlement.Type.FORT, 3), GOTWaypoint.STORMS_END.info(0, 0.5, Dir.SOUTH));

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityBenedarBelmore(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.STRONGSONG);

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityOberynMartell(world), 3, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityDoranMartell(world), 0, 3));
				spawnInfos.add(new SpawnInfo(new GOTEntityEllaryaSand(world), 3, 3));
				spawnInfos.add(new SpawnInfo(new GOTEntityAreoHotah(world), 0, -3));
				spawnInfos.add(new SpawnInfo(new GOTEntityTrystaneMartell(world), -3, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityArianneMartell(world), -3, 3));
				spawnInfos.add(new SpawnInfo(new GOTEntityManfreyMartell(world), -3, -3));
				return spawnInfos;
			}
		}.type(GOTStructureDorneSettlement.Type.TOWN, 6), GOTWaypoint.SUNSPEAR);

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityRodrikHarlaw(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.TEN_TOWERS);

		registerSpawner(new GOTStructureArrynSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityRobinArryn(world), 0, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityLysaArryn(world), -2, 0));
				return spawnInfos;
			}
		}.type(GOTStructureArrynSettlement.Type.FORT, 3), GOTWaypoint.THE_EYRIE.info(0, -0.4));

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityThreeEyedRaven(world), 0, 5));
				return spawnInfos;
			}
		}, GOTWaypoint.THREE_EYED_RAVEN_CAVE);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityHelmanTallhart(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.TORRHENS_SQUARE.info(-0.4, 0, Dir.WEST));

		registerSpawner(new GOTStructureTower(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityBlackWalderFrey(world), 0, -15));
				spawnInfos.add(new SpawnInfo(new GOTEntityLotharFrey(world), 0, -15));
				return spawnInfos;
			}
		}, GOTWaypoint.TWINS_LEFT);

		registerSpawner(new GOTStructureTower(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityWalderFrey(world), 0, -15));
				return spawnInfos;
			}
		}, GOTWaypoint.TWINS_RIGHT);

		registerSpawner(new GOTStructureTyroshSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityJonConnington(world), 0, -1));
				spawnInfos.add(new SpawnInfo(new GOTEntityYoungGriff(world), 0, -1));
				return spawnInfos;
			}
		}.type(GOTStructureTyroshSettlement.Type.TOWN, 6), GOTWaypoint.TYROSH);

		registerSpawner(new GOTStructureDothrakiSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityDaenerysTargaryen(world), 0, 3));
				spawnInfos.add(new SpawnInfo(new GOTEntityJorahMormont(world), 0, 3));
				return spawnInfos;
			}
		}.type(GOTStructureDothrakiSettlement.Type.BIG, 5), GOTWaypoint.VAES_EFE);

		registerSpawner(new GOTStructureVolantisSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityMoqorro(world), -1, 0));
				return spawnInfos;
			}
		}.type(GOTStructureVolantisSettlement.Type.TOWN, 6), GOTWaypoint.VOLANTIS.info(toEssosTownGate(0.0, false) - 0.2, 0.6));

		registerSpawner(new GOTStructureIronbornSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityMaronVolmark(world), -2, -2));
				return spawnInfos;
			}
		}.type(GOTStructureIronbornSettlement.Type.FORT, 3), GOTWaypoint.VOLMARK);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityWymanManderly(world), 0, 5));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.TOWN, 6), GOTWaypoint.WHITE_HARBOUR.info(0.8, 0, Dir.EAST));

		registerSpawner(new GOTStructureEmptySettlement() {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityBenjenStark(world).setIsRider(true), 0, 5));
				return spawnInfos;
			}
		}, GOTWaypoint.WHITETREE);

		registerSpawner(new GOTStructureNorthSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityRobbStark(world), 2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityHodor(world), -2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityAryaStark(world), 2, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityBranStark(world), -2, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityRickonStark(world), 0, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityLuwin(world), 0, -2));
				spawnInfos.add(new SpawnInfo(new GOTEntityOsha(world), 2, 0));
				return spawnInfos;
			}
		}.type(GOTStructureNorthSettlement.Type.FORT, 3), GOTWaypoint.WINTERFELL.info(-0.5, -0.1, Dir.WEST));

		registerSpawner(new GOTStructureYiTiSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityBuGai(world), 12, 0));
				return spawnInfos;
			}
		}.type(GOTStructureYiTiSettlement.Type.TOWN, 7), GOTWaypoint.YIN.info(0, 1, Dir.SOUTH));

		registerSpawner(new GOTStructureDorneSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityQuentynMartell(world), 0, 2));
				spawnInfos.add(new SpawnInfo(new GOTEntityAndersYronwood(world), 0, 2));
				return spawnInfos;
			}
		}.type(GOTStructureDorneSettlement.Type.FORT, 3), GOTWaypoint.YRONWOOD.info(0.5, 0, Dir.EAST));

		registerSpawner(new GOTStructureGhiscarSettlement(GOTBiome.ocean, 0.0f) {
			@Override
			public Collection<SpawnInfo> getLegendaryNPCs(World world) {
				Collection<SpawnInfo> spawnInfos = new ArrayList<>();
				spawnInfos.add(new SpawnInfo(new GOTEntityDaarioNaharis(world), -1, 0));
				spawnInfos.add(new SpawnInfo(new GOTEntityRazdalMoEraz(world), -1, 1));
				return spawnInfos;
			}
		}.type(GOTStructureGhiscarSettlement.Type.TOWN, 6), GOTWaypoint.YUNKAI.info(-0.5, toEssosTownGate(0.0, false), Dir.EAST));

		for (GOTStructureBaseSettlement location : LOCATIONS) {
			for (GOTBiome biome : GOTBiome.CONTENT) {
				biome.getDecorator().addFixedSettlement(location);
			}
		}
		LOCATIONS.clear();
	}

	private static void registerLocation(GOTStructureBaseSettlement settlement, List<GOTAbstractWaypoint> wps) {
		settlement.affix(new ArrayList<>(wps));
		LOCATIONS.add(settlement);
		wps.clear();
	}

	private static void registerSpawner(GOTStructureBaseSettlement spawner, GOTAbstractWaypoint wp) {
		spawner.affix(Collections.singletonList(wp));
		LOCATIONS.add(spawner);
		SPAWNERS.put(wp, spawner);
	}

	public enum Dir {
		NORTH, EAST, SOUTH, WEST
	}

	private static class EuronShip extends GOTStructureBase {
		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			loadStrScan("euron_ship");
			generateStrScan(world, random, 16, 0, -58);
			originY = 73;
			spawnLegendaryNPC(new GOTEntityEuronGreyjoy(world), world, 0, 1, 0);
			for (int l = 0; l < 10; ++l) {
				spawnLegendaryNPC(new GOTEntityIronbornSoldier(world), world, 0, 1, 0);
			}
			return true;
		}
	}

	public static class SpawnInfo {
		private final GOTEntityNPC npc;
		private final int i;
		private final int k;

		@SuppressWarnings("WeakerAccess")
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

		public int getK() {
			return k;
		}
	}
}