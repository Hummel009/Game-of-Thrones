package got.common.world.structure;

import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import got.common.world.structure.essos.asshai.*;
import got.common.world.structure.essos.braavos.*;
import got.common.world.structure.essos.dothraki.*;
import got.common.world.structure.essos.ghiscar.*;
import got.common.world.structure.essos.gold.GOTStructureGoldenCamp;
import got.common.world.structure.essos.gold.GOTStructureGoldenCampWatchtower;
import got.common.world.structure.essos.ibben.*;
import got.common.world.structure.essos.jogos.*;
import got.common.world.structure.essos.lhazar.*;
import got.common.world.structure.essos.lorath.*;
import got.common.world.structure.essos.lys.*;
import got.common.world.structure.essos.mossovy.*;
import got.common.world.structure.essos.myr.*;
import got.common.world.structure.essos.norvos.*;
import got.common.world.structure.essos.pentos.*;
import got.common.world.structure.essos.qarth.*;
import got.common.world.structure.essos.qohor.*;
import got.common.world.structure.essos.tyrosh.*;
import got.common.world.structure.essos.volantis.*;
import got.common.world.structure.essos.yiti.*;
import got.common.world.structure.other.*;
import got.common.world.structure.sothoryos.sothoryos.*;
import got.common.world.structure.sothoryos.summer.*;
import got.common.world.structure.westeros.arryn.*;
import got.common.world.structure.westeros.common.GOTStructureWesterosTownTrees;
import got.common.world.structure.westeros.crownlands.*;
import got.common.world.structure.westeros.dorne.*;
import got.common.world.structure.westeros.dragonstone.*;
import got.common.world.structure.westeros.gift.*;
import got.common.world.structure.westeros.hillmen.GOTStructureHillmanFort;
import got.common.world.structure.westeros.hillmen.GOTStructureHillmanHouse;
import got.common.world.structure.westeros.hillmen.GOTStructureHillmanSettlement;
import got.common.world.structure.westeros.hillmen.GOTStructureHillmanTavern;
import got.common.world.structure.westeros.ironborn.*;
import got.common.world.structure.westeros.north.*;
import got.common.world.structure.westeros.north.hillmen.GOTStructureNorthHillmanChieftainHouse;
import got.common.world.structure.westeros.north.hillmen.GOTStructureNorthHillmanHouse;
import got.common.world.structure.westeros.reach.*;
import got.common.world.structure.westeros.riverlands.*;
import got.common.world.structure.westeros.stormlands.*;
import got.common.world.structure.westeros.westerlands.*;
import got.common.world.structure.westeros.wildling.GOTStructureWildlingChieftainHouse;
import got.common.world.structure.westeros.wildling.GOTStructureWildlingHouse;
import got.common.world.structure.westeros.wildling.GOTStructureWildlingSettlement;
import got.common.world.structure.westeros.wildling.thenn.GOTStructureThennHouse;
import got.common.world.structure.westeros.wildling.thenn.GOTStructureThennMagnarHouse;

public class GOTStructure {
	@SuppressWarnings("PublicField")
	public static int id;

	private GOTStructure() {
	}

	public static void onInit() {
		GOTStructureRegistry.register(id++, GOTStructureWesterosTownTrees.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureBarrow.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureRuinedHouse.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureBurntHouse.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureRottenHouse.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureSmallStoneRuin.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.RuinStone.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.RuinYiTi.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.RuinAsshai.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.RuinSandstone.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.RuinSothoryos.class, 9605778);

		GOTStructureRegistry.register(id++, GOTStructureWildlingHouse.class, GOTFaction.WILDLING);
		GOTStructureRegistry.register(id++, GOTStructureWildlingChieftainHouse.class, GOTFaction.WILDLING);

		GOTStructureRegistry.register(id++, GOTStructureThennHouse.class, GOTFaction.WILDLING);
		GOTStructureRegistry.register(id++, GOTStructureThennMagnarHouse.class, GOTFaction.WILDLING);

		GOTStructureRegistry.register(id++, GOTStructureGiftHouse.class, GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftLodge.class, GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftStables.class, GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftSmithy.class, GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftVillageLight.class, GOTFaction.NIGHT_WATCH);

		GOTStructureRegistry.register(id++, GOTStructureNorthHillmanHouse.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthHillmanChieftainHouse.class, GOTFaction.NORTH);

		GOTStructureRegistry.register(id++, GOTStructureNorthWatchfort.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthSmithy.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthTower.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthHouse.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthCottage.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthStoneHouse.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthWatchtower.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthStables.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthBarn.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthFortress.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthTavern.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthBath.class, GOTFaction.NORTH);

		GOTStructureRegistry.register(id++, GOTStructureIronbornBarn.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornBath.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornCottage.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornFortress.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornHouse.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornSmithy.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornStables.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornStoneHouse.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornTavern.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornTower.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornWatchfort.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornWatchtower.class, GOTFaction.IRONBORN);

		GOTStructureRegistry.register(id++, GOTStructureWesterlandsWatchfort.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsSmithy.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsTower.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsHouse.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsCottage.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsStoneHouse.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsWatchtower.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsStables.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsBarn.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsFortress.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsTavern.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsBath.class, GOTFaction.WESTERLANDS);

		GOTStructureRegistry.register(id++, GOTStructureRiverlandsWatchfort.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsSmithy.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsTower.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsHouse.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsCottage.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsStoneHouse.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsWatchtower.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsStables.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsBarn.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsFortress.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsTavern.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsBath.class, GOTFaction.RIVERLANDS);

		GOTStructureRegistry.register(id++, GOTStructureHillmanHouse.class, GOTFaction.HILL_TRIBES);
		GOTStructureRegistry.register(id++, GOTStructureHillmanTavern.class, GOTFaction.HILL_TRIBES);
		GOTStructureRegistry.register(id++, GOTStructureHillmanFort.class, GOTFaction.HILL_TRIBES);

		GOTStructureRegistry.register(id++, GOTStructureArrynWatchfort.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynSmithy.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynTower.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynHouse.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynCottage.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynStoneHouse.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynWatchtower.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynStables.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynBarn.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynFortress.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynTavern.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynBath.class, GOTFaction.ARRYN);

		GOTStructureRegistry.register(id++, GOTStructureDragonstoneWatchfort.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneSmithy.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneTower.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneHouse.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneCottage.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneStoneHouse.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneWatchtower.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneStables.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneBarn.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneFortress.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneTavern.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneBath.class, GOTFaction.DRAGONSTONE);

		GOTStructureRegistry.register(id++, GOTStructureCrownlandsWatchfort.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsSmithy.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsTower.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsHouse.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsCottage.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsStoneHouse.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsWatchtower.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsStables.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsBarn.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsFortress.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsTavern.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsBath.class, GOTFaction.CROWNLANDS);

		GOTStructureRegistry.register(id++, GOTStructureStormlandsWatchfort.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsSmithy.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsTower.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsHouse.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsCottage.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsStoneHouse.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsWatchtower.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsStables.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsBarn.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsFortress.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsTavern.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsBath.class, GOTFaction.STORMLANDS);

		GOTStructureRegistry.register(id++, GOTStructureReachBrewery.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachWatchfort.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachSmithy.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachTower.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachHouse.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachCottage.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachStoneHouse.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachWatchtower.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachStables.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachBarn.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachFortress.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachTavern.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachBath.class, GOTFaction.REACH);

		GOTStructureRegistry.register(id++, GOTStructureDorneWatchfort.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneSmithy.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneTower.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneHouse.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneCottage.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneStoneHouse.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneWatchtower.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneStables.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneBarn.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneFortress.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneTavern.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneBath.class, GOTFaction.DORNE);

		GOTStructureRegistry.register(id++, GOTStructureBraavosBarracks.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosBazaar.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosFortress.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosHouse.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosMansion.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosSmithy.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosStables.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosTavern.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosTower.class, GOTFaction.BRAAVOS);

		GOTStructureRegistry.register(id++, GOTStructureVolantisBarracks.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisBazaar.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisFortress.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisHouse.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisMansion.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisSmithy.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisStables.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisTavern.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisTower.class, GOTFaction.VOLANTIS);

		GOTStructureRegistry.register(id++, GOTStructurePentosBarracks.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosBazaar.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosFortress.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosHouse.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosMansion.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosSmithy.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosStables.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosTavern.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosTower.class, GOTFaction.PENTOS);

		GOTStructureRegistry.register(id++, GOTStructureNorvosBarracks.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosBazaar.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosFortress.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosHouse.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosMansion.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosSmithy.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosStables.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosTavern.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosTower.class, GOTFaction.NORVOS);

		GOTStructureRegistry.register(id++, GOTStructureLorathBarracks.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathBazaar.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathFortress.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathHouse.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathMansion.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathSmithy.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathStables.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathTavern.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathTower.class, GOTFaction.LORATH);

		GOTStructureRegistry.register(id++, GOTStructureQohorBarracks.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorBazaar.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorFortress.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorHouse.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorMansion.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorSmithy.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorStables.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorTavern.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorTower.class, GOTFaction.QOHOR);

		GOTStructureRegistry.register(id++, GOTStructureLysBarracks.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysBazaar.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysFortress.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysHouse.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysMansion.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysSmithy.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysStables.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysTavern.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysTower.class, GOTFaction.LYS);

		GOTStructureRegistry.register(id++, GOTStructureMyrBarracks.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrBazaar.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrFortress.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrHouse.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrMansion.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrSmithy.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrStables.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrTavern.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrTower.class, GOTFaction.MYR);

		GOTStructureRegistry.register(id++, GOTStructureTyroshBarracks.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshBazaar.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshFortress.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshHouse.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshMansion.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshSmithy.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshStables.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshTavern.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshTower.class, GOTFaction.TYROSH);

		GOTStructureRegistry.register(id++, GOTStructureGoldenCampWatchtower.class, 0xffd700);

		GOTStructureRegistry.register(id++, GOTStructureGhiscarPyramid.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarBarracks.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarBazaar.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarFortress.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarHouse.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarMansion.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarSmithy.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarStables.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarTavern.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarTower.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarFightingPit.class, GOTFaction.GHISCAR);

		GOTStructureRegistry.register(id++, GOTStructureQarthBarracks.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthBazaar.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthFortress.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthHouse.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthMansion.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthSmithy.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthStables.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthTavern.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthTower.class, GOTFaction.QARTH);

		GOTStructureRegistry.register(id++, GOTStructureLhazarWarCamp.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarHouse.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarAltar.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarSmithy.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarBazaar.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarTotem.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarPyramid.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarFarm.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarTower.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarTavern.class, GOTFaction.LHAZAR);

		GOTStructureRegistry.register(id++, GOTStructureDothrakiTent.class, GOTFaction.DOTHRAKI);
		GOTStructureRegistry.register(id++, GOTStructureDothrakiTentLarge.class, GOTFaction.DOTHRAKI);
		GOTStructureRegistry.register(id++, GOTStructureDothrakiKhalTent.class, GOTFaction.DOTHRAKI);
		GOTStructureRegistry.register(id++, GOTStructureDothrakiKhalinTent.class, GOTFaction.DOTHRAKI);

		GOTStructureRegistry.register(id++, GOTStructureIbbenTavern.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenWatchtower.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenFortress.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenHouse.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenSmithy.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenStables.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenBarn.class, GOTFaction.IBBEN);

		GOTStructureRegistry.register(id++, GOTStructureJogosTent.class, GOTFaction.JOGOS);
		GOTStructureRegistry.register(id++, GOTStructureJogosTentLarge.class, GOTFaction.JOGOS);
		GOTStructureRegistry.register(id++, GOTStructureJogosChiefTent.class, GOTFaction.JOGOS);
		GOTStructureRegistry.register(id++, GOTStructureJogosShamanTent.class, GOTFaction.JOGOS);

		GOTStructureRegistry.register(id++, GOTStructureMossovyBarn.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyCastle.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyHouse.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyInn.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyOffice.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyTrampHouse.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovySmithy.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyStable.class, GOTFaction.MOSSOVY);

		GOTStructureRegistry.register(id++, GOTStructureYiTiHouse.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiStables.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTownHouse.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiLargeTownHouse.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiFortress.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTower.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiSmithy.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTavern.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTavernTown.class, GOTFaction.YI_TI);

		GOTStructureRegistry.register(id++, GOTStructureAsshaiTower.class, GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiFort.class, GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiHouse.class, GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiTavern.class, GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiSpiderPit.class, GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiAltar.class, GOTFaction.ASSHAI);

		GOTStructureRegistry.register(id++, GOTStructureSummerHouse.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerSmithy.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerTavern.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerTower.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerFort.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerStables.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerHouseRuined.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerTavernRuined.class, GOTFaction.SUMMER_ISLANDS);

		GOTStructureRegistry.register(id++, GOTStructureSothoryosPyramid.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosHouseSimple.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosHouseStilts.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosWatchtower.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosHouseLarge.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosChieftainPyramid.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosSmithy.class, GOTFaction.SOTHORYOS);

		GOTStructureRegistry.register(id++, new GOTStructureWildlingSettlement(GOTBiome.hauntedForest, 1.0f), "WildlingVillage", GOTFaction.WILDLING, (GOTStructureRegistry.ISettlementProperties<GOTStructureWildlingSettlement.Instance>) instance -> instance.type = GOTStructureWildlingSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureWildlingSettlement(GOTBiome.thennLand, 1.0f), "ThennVillage", GOTFaction.WILDLING, (GOTStructureRegistry.ISettlementProperties<GOTStructureWildlingSettlement.Instance>) instance -> instance.type = GOTStructureWildlingSettlement.Type.THENN);

		GOTStructureRegistry.register(id++, new GOTStructureGiftSettlement(GOTBiome.giftNew, 1.0f), "GiftVillage", GOTFaction.NIGHT_WATCH, (GOTStructureRegistry.ISettlementProperties<GOTStructureGiftSettlement.Instance>) instance -> instance.type = GOTStructureGiftSettlement.Type.VILLAGE);

		GOTStructureRegistry.register(id++, new GOTStructureNorthSettlement(GOTBiome.skagos, 1.0f), "NorthHillmanVillage", GOTFaction.NORTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorthSettlement.Instance>) instance -> instance.type = GOTStructureNorthSettlement.Type.HILLMAN);
		GOTStructureRegistry.register(id++, new GOTStructureNorthSettlement(GOTBiome.north, 1.0f), "NorthVillage", GOTFaction.NORTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorthSettlement.Instance>) instance -> instance.type = GOTStructureNorthSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureNorthSettlement(GOTBiome.north, 1.0f), "NorthTown", GOTFaction.NORTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorthSettlement.Instance>) instance -> instance.type = GOTStructureNorthSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureNorthSettlement(GOTBiome.north, 1.0f), "NorthFortVillage", GOTFaction.NORTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorthSettlement.Instance>) instance -> instance.type = GOTStructureNorthSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureIronbornSettlement(GOTBiome.ironborn, 1.0f), "IronbornCityLittle", GOTFaction.IRONBORN, (GOTStructureRegistry.ISettlementProperties<GOTStructureIronbornSettlement.Instance>) instance -> instance.type = GOTStructureIronbornSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureIronbornSettlement(GOTBiome.ironborn, 1.0f), "IronbornCityBig", GOTFaction.IRONBORN, (GOTStructureRegistry.ISettlementProperties<GOTStructureIronbornSettlement.Instance>) instance -> instance.type = GOTStructureIronbornSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureIronbornSettlement(GOTBiome.ironborn, 1.0f), "IronbornCityMedium", GOTFaction.IRONBORN, (GOTStructureRegistry.ISettlementProperties<GOTStructureIronbornSettlement.Instance>) instance -> instance.type = GOTStructureIronbornSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsSettlement(GOTBiome.westerlands, 1.0f), "WesterlandsCityLittle", GOTFaction.WESTERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureWesterlandsSettlement.Instance>) instance -> instance.type = GOTStructureWesterlandsSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsSettlement(GOTBiome.westerlands, 1.0f), "WesterlandsCityBig", GOTFaction.WESTERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureWesterlandsSettlement.Instance>) instance -> instance.type = GOTStructureWesterlandsSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsSettlement(GOTBiome.westerlands, 1.0f), "WesterlandsCityMedium", GOTFaction.WESTERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureWesterlandsSettlement.Instance>) instance -> instance.type = GOTStructureWesterlandsSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsSettlement(GOTBiome.riverlands, 1.0f), "RiverlandsCityLittle", GOTFaction.RIVERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureRiverlandsSettlement.Instance>) instance -> instance.type = GOTStructureRiverlandsSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsSettlement(GOTBiome.riverlands, 1.0f), "RiverlandsCityBig", GOTFaction.RIVERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureRiverlandsSettlement.Instance>) instance -> instance.type = GOTStructureRiverlandsSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsSettlement(GOTBiome.riverlands, 1.0f), "RiverlandsCityMedium", GOTFaction.RIVERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureRiverlandsSettlement.Instance>) instance -> instance.type = GOTStructureRiverlandsSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureHillmanSettlement(GOTBiome.sothoryosJungle, 1.0f), "HillmanVillage", GOTFaction.HILL_TRIBES, (GOTStructureRegistry.ISettlementProperties<GOTStructureHillmanSettlement.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureArrynSettlement(GOTBiome.arryn, 1.0f), "ArrynCityLittle", GOTFaction.ARRYN, (GOTStructureRegistry.ISettlementProperties<GOTStructureArrynSettlement.Instance>) instance -> instance.type = GOTStructureArrynSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureArrynSettlement(GOTBiome.arryn, 1.0f), "ArrynCityBig", GOTFaction.ARRYN, (GOTStructureRegistry.ISettlementProperties<GOTStructureArrynSettlement.Instance>) instance -> instance.type = GOTStructureArrynSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureArrynSettlement(GOTBiome.arryn, 1.0f), "ArrynCityMedium", GOTFaction.ARRYN, (GOTStructureRegistry.ISettlementProperties<GOTStructureArrynSettlement.Instance>) instance -> instance.type = GOTStructureArrynSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneSettlement(GOTBiome.dragonstone, 1.0f), "DragonstoneCityLittle", GOTFaction.DRAGONSTONE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDragonstoneSettlement.Instance>) instance -> instance.type = GOTStructureDragonstoneSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneSettlement(GOTBiome.dragonstone, 1.0f), "DragonstoneCityBig", GOTFaction.DRAGONSTONE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDragonstoneSettlement.Instance>) instance -> instance.type = GOTStructureDragonstoneSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneSettlement(GOTBiome.dragonstone, 1.0f), "DragonstoneCityMedium", GOTFaction.DRAGONSTONE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDragonstoneSettlement.Instance>) instance -> instance.type = GOTStructureDragonstoneSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsSettlement(GOTBiome.crownlands, 1.0f), "CrownlandsCityLittle", GOTFaction.CROWNLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureCrownlandsSettlement.Instance>) instance -> instance.type = GOTStructureCrownlandsSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsSettlement(GOTBiome.crownlands, 1.0f), "CrownlandsCityBig", GOTFaction.CROWNLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureCrownlandsSettlement.Instance>) instance -> instance.type = GOTStructureCrownlandsSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsSettlement(GOTBiome.crownlands, 1.0f), "CrownlandsCityMedium", GOTFaction.CROWNLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureCrownlandsSettlement.Instance>) instance -> instance.type = GOTStructureCrownlandsSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureStormlandsSettlement(GOTBiome.stormlands, 1.0f), "StormlandsCityLittle", GOTFaction.STORMLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureStormlandsSettlement.Instance>) instance -> instance.type = GOTStructureStormlandsSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureStormlandsSettlement(GOTBiome.stormlands, 1.0f), "StormlandsCityBig", GOTFaction.STORMLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureStormlandsSettlement.Instance>) instance -> instance.type = GOTStructureStormlandsSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureStormlandsSettlement(GOTBiome.stormlands, 1.0f), "StormlandsCityMedium", GOTFaction.STORMLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureStormlandsSettlement.Instance>) instance -> instance.type = GOTStructureStormlandsSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureReachSettlement(GOTBiome.reach, 1.0f), "ReachCityLittle", GOTFaction.REACH, (GOTStructureRegistry.ISettlementProperties<GOTStructureReachSettlement.Instance>) instance -> instance.type = GOTStructureReachSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureReachSettlement(GOTBiome.reach, 1.0f), "ReachCityBig", GOTFaction.REACH, (GOTStructureRegistry.ISettlementProperties<GOTStructureReachSettlement.Instance>) instance -> instance.type = GOTStructureReachSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureReachSettlement(GOTBiome.reach, 1.0f), "ReachCityMedium", GOTFaction.REACH, (GOTStructureRegistry.ISettlementProperties<GOTStructureReachSettlement.Instance>) instance -> instance.type = GOTStructureReachSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureDorneSettlement(GOTBiome.dorne, 1.0f), "DorneCityLittle", GOTFaction.DORNE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDorneSettlement.Instance>) instance -> instance.type = GOTStructureDorneSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureDorneSettlement(GOTBiome.dorne, 1.0f), "DorneCityBig", GOTFaction.DORNE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDorneSettlement.Instance>) instance -> instance.type = GOTStructureDorneSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureDorneSettlement(GOTBiome.dorne, 1.0f), "DorneCityMedium", GOTFaction.DORNE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDorneSettlement.Instance>) instance -> instance.type = GOTStructureDorneSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureBraavosSettlement(GOTBiome.braavos, 1.0f), "BraavosCityLittle", GOTFaction.BRAAVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureBraavosSettlement.Instance>) instance -> instance.type = GOTStructureBraavosSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureBraavosSettlement(GOTBiome.braavos, 1.0f), "BraavosCityMedium", GOTFaction.BRAAVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureBraavosSettlement.Instance>) instance -> instance.type = GOTStructureBraavosSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureBraavosSettlement(GOTBiome.braavos, 1.0f), "BraavosCityBig", GOTFaction.BRAAVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureBraavosSettlement.Instance>) instance -> instance.type = GOTStructureBraavosSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureVolantisSettlement(GOTBiome.volantis, 1.0f), "VolantisCityLittle", GOTFaction.VOLANTIS, (GOTStructureRegistry.ISettlementProperties<GOTStructureVolantisSettlement.Instance>) instance -> instance.type = GOTStructureVolantisSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureVolantisSettlement(GOTBiome.volantis, 1.0f), "VolantisCityMedium", GOTFaction.VOLANTIS, (GOTStructureRegistry.ISettlementProperties<GOTStructureVolantisSettlement.Instance>) instance -> instance.type = GOTStructureVolantisSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureVolantisSettlement(GOTBiome.volantis, 1.0f), "VolantisCityBig", GOTFaction.VOLANTIS, (GOTStructureRegistry.ISettlementProperties<GOTStructureVolantisSettlement.Instance>) instance -> instance.type = GOTStructureVolantisSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructurePentosSettlement(GOTBiome.pentos, 1.0f), "PentosCityLittle", GOTFaction.PENTOS, (GOTStructureRegistry.ISettlementProperties<GOTStructurePentosSettlement.Instance>) instance -> instance.type = GOTStructurePentosSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructurePentosSettlement(GOTBiome.pentos, 1.0f), "PentosCityMedium", GOTFaction.PENTOS, (GOTStructureRegistry.ISettlementProperties<GOTStructurePentosSettlement.Instance>) instance -> instance.type = GOTStructurePentosSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructurePentosSettlement(GOTBiome.pentos, 1.0f), "PentosCityBig", GOTFaction.PENTOS, (GOTStructureRegistry.ISettlementProperties<GOTStructurePentosSettlement.Instance>) instance -> instance.type = GOTStructurePentosSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureNorvosSettlement(GOTBiome.norvos, 1.0f), "NorvosCityLittle", GOTFaction.NORVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorvosSettlement.Instance>) instance -> instance.type = GOTStructureNorvosSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureNorvosSettlement(GOTBiome.norvos, 1.0f), "NorvosCityMedium", GOTFaction.NORVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorvosSettlement.Instance>) instance -> instance.type = GOTStructureNorvosSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureNorvosSettlement(GOTBiome.norvos, 1.0f), "NorvosCityBig", GOTFaction.NORVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorvosSettlement.Instance>) instance -> instance.type = GOTStructureNorvosSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureLorathSettlement(GOTBiome.lorath, 1.0f), "LorathCityLittle", GOTFaction.LORATH, (GOTStructureRegistry.ISettlementProperties<GOTStructureLorathSettlement.Instance>) instance -> instance.type = GOTStructureLorathSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureLorathSettlement(GOTBiome.lorath, 1.0f), "LorathCityMedium", GOTFaction.LORATH, (GOTStructureRegistry.ISettlementProperties<GOTStructureLorathSettlement.Instance>) instance -> instance.type = GOTStructureLorathSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureLorathSettlement(GOTBiome.lorath, 1.0f), "LorathCityBig", GOTFaction.LORATH, (GOTStructureRegistry.ISettlementProperties<GOTStructureLorathSettlement.Instance>) instance -> instance.type = GOTStructureLorathSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureQohorSettlement(GOTBiome.qohor, 1.0f), "QohorCityLittle", GOTFaction.QOHOR, (GOTStructureRegistry.ISettlementProperties<GOTStructureQohorSettlement.Instance>) instance -> instance.type = GOTStructureQohorSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureQohorSettlement(GOTBiome.qohor, 1.0f), "QohorCityMedium", GOTFaction.QOHOR, (GOTStructureRegistry.ISettlementProperties<GOTStructureQohorSettlement.Instance>) instance -> instance.type = GOTStructureQohorSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureQohorSettlement(GOTBiome.qohor, 1.0f), "QohorCityBig", GOTFaction.QOHOR, (GOTStructureRegistry.ISettlementProperties<GOTStructureQohorSettlement.Instance>) instance -> instance.type = GOTStructureQohorSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureLysSettlement(GOTBiome.lys, 1.0f), "LysCityLittle", GOTFaction.LYS, (GOTStructureRegistry.ISettlementProperties<GOTStructureLysSettlement.Instance>) instance -> instance.type = GOTStructureLysSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureLysSettlement(GOTBiome.lys, 1.0f), "LysCityMedium", GOTFaction.LYS, (GOTStructureRegistry.ISettlementProperties<GOTStructureLysSettlement.Instance>) instance -> instance.type = GOTStructureLysSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureLysSettlement(GOTBiome.lys, 1.0f), "LysCityBig", GOTFaction.LYS, (GOTStructureRegistry.ISettlementProperties<GOTStructureLysSettlement.Instance>) instance -> instance.type = GOTStructureLysSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureMyrSettlement(GOTBiome.myr, 1.0f), "MyrCityLittle", GOTFaction.MYR, (GOTStructureRegistry.ISettlementProperties<GOTStructureMyrSettlement.Instance>) instance -> instance.type = GOTStructureMyrSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureMyrSettlement(GOTBiome.myr, 1.0f), "MyrCityMedium", GOTFaction.MYR, (GOTStructureRegistry.ISettlementProperties<GOTStructureMyrSettlement.Instance>) instance -> instance.type = GOTStructureMyrSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureMyrSettlement(GOTBiome.myr, 1.0f), "MyrCityBig", GOTFaction.MYR, (GOTStructureRegistry.ISettlementProperties<GOTStructureMyrSettlement.Instance>) instance -> instance.type = GOTStructureMyrSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureTyroshSettlement(GOTBiome.tyrosh, 1.0f), "TyroshCityLittle", GOTFaction.TYROSH, (GOTStructureRegistry.ISettlementProperties<GOTStructureTyroshSettlement.Instance>) instance -> instance.type = GOTStructureTyroshSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureTyroshSettlement(GOTBiome.tyrosh, 1.0f), "TyroshCityMedium", GOTFaction.TYROSH, (GOTStructureRegistry.ISettlementProperties<GOTStructureTyroshSettlement.Instance>) instance -> instance.type = GOTStructureTyroshSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureTyroshSettlement(GOTBiome.tyrosh, 1.0f), "TyroshCityBig", GOTFaction.TYROSH, (GOTStructureRegistry.ISettlementProperties<GOTStructureTyroshSettlement.Instance>) instance -> instance.type = GOTStructureTyroshSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureGoldenCamp(GOTBiome.essos, 1.0f), "GoldenCamp", 0xffd700, (GOTStructureRegistry.ISettlementProperties<GOTStructureGoldenCamp.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureGhiscarSettlement(GOTBiome.ghiscar, 1.0f), "GhiscarCityLittle", GOTFaction.GHISCAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureGhiscarSettlement.Instance>) instance -> instance.type = GOTStructureGhiscarSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureGhiscarSettlement(GOTBiome.ghiscar, 1.0f), "GhiscarCityMedium", GOTFaction.GHISCAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureGhiscarSettlement.Instance>) instance -> instance.type = GOTStructureGhiscarSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureGhiscarSettlement(GOTBiome.ghiscar, 1.0f), "GhiscarCityBig", GOTFaction.GHISCAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureGhiscarSettlement.Instance>) instance -> instance.type = GOTStructureGhiscarSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureQarthSettlement(GOTBiome.qarth, 1.0f), "QarthCityLittle", GOTFaction.QARTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureQarthSettlement.Instance>) instance -> instance.type = GOTStructureQarthSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureQarthSettlement(GOTBiome.qarth, 1.0f), "QarthCityMedium", GOTFaction.QARTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureQarthSettlement.Instance>) instance -> instance.type = GOTStructureQarthSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureQarthSettlement(GOTBiome.qarth, 1.0f), "QarthCityBig", GOTFaction.QARTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureQarthSettlement.Instance>) instance -> instance.type = GOTStructureQarthSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureLhazarSettlement(GOTBiome.lhazar, 1.0f), "LhazarCityLittle", GOTFaction.LHAZAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureLhazarSettlement.Instance>) instance -> instance.type = GOTStructureLhazarSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureLhazarSettlement(GOTBiome.lhazar, 1.0f), "LhazarCityMedium", GOTFaction.LHAZAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureLhazarSettlement.Instance>) instance -> instance.type = GOTStructureLhazarSettlement.Type.FORT);
		GOTStructureRegistry.register(id++, new GOTStructureLhazarSettlement(GOTBiome.lhazar, 1.0f), "LhazarCityBig", GOTFaction.LHAZAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureLhazarSettlement.Instance>) instance -> instance.type = GOTStructureLhazarSettlement.Type.TOWN);

		GOTStructureRegistry.register(id++, new GOTStructureDothrakiSettlement(GOTBiome.dothrakiSea, 1.0f), "DothrakiVillageLittle", GOTFaction.DOTHRAKI, (GOTStructureRegistry.ISettlementProperties<GOTStructureDothrakiSettlement.Instance>) instance -> instance.type = GOTStructureDothrakiSettlement.Type.SMALL);
		GOTStructureRegistry.register(id++, new GOTStructureDothrakiSettlement(GOTBiome.dothrakiSea, 1.0f), "DothrakiVillageBig", GOTFaction.DOTHRAKI, (GOTStructureRegistry.ISettlementProperties<GOTStructureDothrakiSettlement.Instance>) instance -> instance.type = GOTStructureDothrakiSettlement.Type.BIG);

		GOTStructureRegistry.register(id++, new GOTStructureIbbenSettlement(GOTBiome.ibben, 1.0f), "IbbenVillage", GOTFaction.IBBEN, (GOTStructureRegistry.ISettlementProperties<GOTStructureIbbenSettlement.Instance>) instance -> instance.type = GOTStructureIbbenSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureIbbenSettlement(GOTBiome.ibben, 1.0f), "IbbenFortVillage", GOTFaction.IBBEN, (GOTStructureRegistry.ISettlementProperties<GOTStructureIbbenSettlement.Instance>) instance -> instance.type = GOTStructureIbbenSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureJogosSettlement(GOTBiome.jogosNhai, 1.0f), "JogosVillageLittle", GOTFaction.JOGOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureJogosSettlement.Instance>) instance -> instance.type = GOTStructureJogosSettlement.Type.SMALL);
		GOTStructureRegistry.register(id++, new GOTStructureJogosSettlement(GOTBiome.jogosNhai, 1.0f), "JogosVillageBig", GOTFaction.JOGOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureJogosSettlement.Instance>) instance -> instance.type = GOTStructureJogosSettlement.Type.BIG);

		GOTStructureRegistry.register(id++, new GOTStructureMossovySettlement(GOTBiome.mossovy, 1.0f), "MossovyVillage", GOTFaction.MOSSOVY, (GOTStructureRegistry.ISettlementProperties<GOTStructureMossovySettlement.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureYiTiSettlement(GOTBiome.yiTi, 1.0f), "YiTiVillage", GOTFaction.YI_TI, (GOTStructureRegistry.ISettlementProperties<GOTStructureYiTiSettlement.Instance>) instance -> instance.type = GOTStructureYiTiSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureYiTiSettlement(GOTBiome.yiTi, 1.0f), "YiTiTown", GOTFaction.YI_TI, (GOTStructureRegistry.ISettlementProperties<GOTStructureYiTiSettlement.Instance>) instance -> instance.type = GOTStructureYiTiSettlement.Type.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureYiTiSettlement(GOTBiome.yiTi, 1.0f), "YiTiFortVillage", GOTFaction.YI_TI, (GOTStructureRegistry.ISettlementProperties<GOTStructureYiTiSettlement.Instance>) instance -> instance.type = GOTStructureYiTiSettlement.Type.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureAsshaiSettlement(GOTBiome.shadowTown, 1.0f), "AsshaiCity", GOTFaction.ASSHAI, (GOTStructureRegistry.ISettlementProperties<GOTStructureAsshaiSettlement.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureSummerSettlement(GOTBiome.summerIslands, 1.0f), "SummerVillage", GOTFaction.SUMMER_ISLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureSummerSettlement.Instance>) instance -> instance.type = GOTStructureSummerSettlement.Type.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureSummerSettlement(GOTBiome.summerIslands, 1.0f), "SummerFortVillage", GOTFaction.SUMMER_ISLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureSummerSettlement.Instance>) instance -> instance.type = GOTStructureSummerSettlement.Type.FORT);
		GOTStructureRegistry.register(id++, new GOTStructureSummerSettlement(GOTBiome.summerIslands, 1.0f), "SummerVillageRuined", GOTFaction.SUMMER_ISLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureSummerSettlement.Instance>) instance -> instance.type = GOTStructureSummerSettlement.Type.RUINED_VILLAGE);

		GOTStructureRegistry.register(id++, new GOTStructureSothoryosSettlement(GOTBiome.sothoryosJungle, 1.0f), "SothoryosVillage", GOTFaction.SOTHORYOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureSothoryosSettlement.Instance>) instance -> {
		});

		GOTStructureSothoryosPyramidMapgen.register();
		GOTStructureGhiscarPyramidMapgen.register();
	}
}