package got.common.world.structure;

import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import got.common.world.structure.essos.asshai.*;
import got.common.world.structure.essos.braavos.*;
import got.common.world.structure.essos.dothraki.*;
import got.common.world.structure.essos.ghiscar.*;
import got.common.world.structure.essos.golden.GOTStructureGoldenCompanySettlement;
import got.common.world.structure.essos.golden.GOTStructureGoldenCompanyWatchtower;
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
import got.common.world.structure.essos.yi_ti.*;
import got.common.world.structure.other.*;
import got.common.world.structure.sothoryos.sothoryos.*;
import got.common.world.structure.sothoryos.summer.*;
import got.common.world.structure.westeros.arryn.*;
import got.common.world.structure.westeros.crownlands.*;
import got.common.world.structure.westeros.dorne.*;
import got.common.world.structure.westeros.dragonstone.*;
import got.common.world.structure.westeros.gift.*;
import got.common.world.structure.westeros.hillmen.GOTStructureHillmanFortress;
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
import got.common.world.structure.westeros.wildling.thenn.GOTStructureThennChieftainHouse;
import got.common.world.structure.westeros.wildling.thenn.GOTStructureThennHouse;

public class GOTStructure {
	private GOTStructure() {
	}

	@SuppressWarnings({"UnusedAssignment", "ValueOfIncrementOrDecrementUsed"})
	public static void onInit() {
		int id = 0;
		GOTStructureRegistry.register(id++, GOTStructureBarrow.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureRuinedHouse.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureBurntHouse.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureRottenHouse.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureSmallStoneRuin.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.RuinStone.class, 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.RuinSandstone.class, 9605778);

		GOTStructureRegistry.register(id++, GOTStructureWildlingHouse.class, GOTFaction.WILDLING);
		GOTStructureRegistry.register(id++, GOTStructureWildlingChieftainHouse.class, GOTFaction.WILDLING);

		GOTStructureRegistry.register(id++, GOTStructureThennHouse.class, GOTFaction.WILDLING);
		GOTStructureRegistry.register(id++, GOTStructureThennChieftainHouse.class, GOTFaction.WILDLING);

		GOTStructureRegistry.register(id++, GOTStructureGiftHouseSmall.class, GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftHouse.class, GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftStables.class, GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftSmithy.class, GOTFaction.NIGHT_WATCH);

		GOTStructureRegistry.register(id++, GOTStructureNorthHillmanHouse.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthHillmanChieftainHouse.class, GOTFaction.NORTH);

		GOTStructureRegistry.register(id++, GOTStructureNorthBarn.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthBath.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthFortress.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthHouse.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthHouseLarge.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthHouseSmall.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthSmithy.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthStables.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthTavern.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthTower.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthWatchfort.class, GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthWatchtower.class, GOTFaction.NORTH);

		GOTStructureRegistry.register(id++, GOTStructureIronbornBarn.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornBath.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornFortress.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornHouse.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornHouseLarge.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornHouseSmall.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornSmithy.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornStables.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornTavern.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornTower.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornWatchfort.class, GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornWatchtower.class, GOTFaction.IRONBORN);

		GOTStructureRegistry.register(id++, GOTStructureWesterlandsBarn.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsBath.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsFortress.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsHouse.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsHouseLarge.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsHouseSmall.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsSmithy.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsStables.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsTavern.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsTower.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsWatchfort.class, GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsWatchtower.class, GOTFaction.WESTERLANDS);

		GOTStructureRegistry.register(id++, GOTStructureRiverlandsBarn.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsBath.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsFortress.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsHouse.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsHouseLarge.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsHouseSmall.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsSmithy.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsStables.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsTavern.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsTower.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsWatchfort.class, GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsWatchtower.class, GOTFaction.RIVERLANDS);

		GOTStructureRegistry.register(id++, GOTStructureHillmanFortress.class, GOTFaction.HILL_TRIBES);
		GOTStructureRegistry.register(id++, GOTStructureHillmanHouse.class, GOTFaction.HILL_TRIBES);
		GOTStructureRegistry.register(id++, GOTStructureHillmanTavern.class, GOTFaction.HILL_TRIBES);

		GOTStructureRegistry.register(id++, GOTStructureArrynBarn.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynBath.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynFortress.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynHouse.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynHouseLarge.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynHouseSmall.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynSmithy.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynStables.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynTavern.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynTower.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynWatchfort.class, GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynWatchtower.class, GOTFaction.ARRYN);

		GOTStructureRegistry.register(id++, GOTStructureDragonstoneBarn.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneBath.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneFortress.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneHouse.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneHouseLarge.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneHouseSmall.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneSmithy.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneStables.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneTavern.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneTower.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneWatchfort.class, GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneWatchtower.class, GOTFaction.DRAGONSTONE);

		GOTStructureRegistry.register(id++, GOTStructureCrownlandsBarn.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsBath.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsFortress.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsHouse.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsHouseLarge.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsHouseSmall.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsSmithy.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsStables.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsTavern.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsTower.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsWatchfort.class, GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsWatchtower.class, GOTFaction.CROWNLANDS);

		GOTStructureRegistry.register(id++, GOTStructureStormlandsBarn.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsBath.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsFortress.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsHouse.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsHouseLarge.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsHouseSmall.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsSmithy.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsStables.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsTavern.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsTower.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsWatchfort.class, GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsWatchtower.class, GOTFaction.STORMLANDS);

		GOTStructureRegistry.register(id++, GOTStructureReachBarn.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachBath.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachBrewery.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachFortress.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachHouse.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachHouseLarge.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachHouseSmall.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachSmithy.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachStables.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachTavern.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachTower.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachWatchfort.class, GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachWatchtower.class, GOTFaction.REACH);

		GOTStructureRegistry.register(id++, GOTStructureDorneBarn.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneBath.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneFortress.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneHouse.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneHouseLarge.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneHouseSmall.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneSmithy.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneStables.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneTavern.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneTower.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneWatchfort.class, GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneWatchtower.class, GOTFaction.DORNE);

		GOTStructureRegistry.register(id++, GOTStructureBraavosBarracks.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosBazaar.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosFortress.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosHouse.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosHouseLarge.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosSmithy.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosStables.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosTavern.class, GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosTower.class, GOTFaction.BRAAVOS);

		GOTStructureRegistry.register(id++, GOTStructureVolantisBarracks.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisBazaar.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisFortress.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisHouse.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisHouseLarge.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisSmithy.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisStables.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisTavern.class, GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisTower.class, GOTFaction.VOLANTIS);

		GOTStructureRegistry.register(id++, GOTStructurePentosBarracks.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosBazaar.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosFortress.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosHouse.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosHouseLarge.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosSmithy.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosStables.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosTavern.class, GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosTower.class, GOTFaction.PENTOS);

		GOTStructureRegistry.register(id++, GOTStructureNorvosBarracks.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosBazaar.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosFortress.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosHouse.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosHouseLarge.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosSmithy.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosStables.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosTavern.class, GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosTower.class, GOTFaction.NORVOS);

		GOTStructureRegistry.register(id++, GOTStructureLorathBarracks.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathBazaar.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathFortress.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathHouse.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathHouseLarge.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathSmithy.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathStables.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathTavern.class, GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathTower.class, GOTFaction.LORATH);

		GOTStructureRegistry.register(id++, GOTStructureQohorBarracks.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorBazaar.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorFortress.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorHouse.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorHouseLarge.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorSmithy.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorStables.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorTavern.class, GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorTower.class, GOTFaction.QOHOR);

		GOTStructureRegistry.register(id++, GOTStructureLysBarracks.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysBazaar.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysFortress.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysHouse.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysHouseLarge.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysSmithy.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysStables.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysTavern.class, GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysTower.class, GOTFaction.LYS);

		GOTStructureRegistry.register(id++, GOTStructureMyrBarracks.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrBazaar.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrFortress.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrHouse.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrHouseLarge.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrSmithy.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrStables.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrTavern.class, GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrTower.class, GOTFaction.MYR);

		GOTStructureRegistry.register(id++, GOTStructureTyroshBarracks.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshBazaar.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshFortress.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshHouse.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshHouseLarge.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshSmithy.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshStables.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshTavern.class, GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshTower.class, GOTFaction.TYROSH);

		GOTStructureRegistry.register(id++, GOTStructureGoldenCompanyWatchtower.class, 0xffd700);

		GOTStructureRegistry.register(id++, GOTStructureGhiscarBarracks.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarBazaar.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarFightingPit.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarFortress.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarHouse.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarHouseLarge.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarPyramid.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarSmithy.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarStables.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarTavern.class, GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarTower.class, GOTFaction.GHISCAR);

		GOTStructureRegistry.register(id++, GOTStructureQarthBarracks.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthBazaar.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthFortress.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthHouse.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthHouseLarge.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthSmithy.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthStables.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthTavern.class, GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthTower.class, GOTFaction.QARTH);

		GOTStructureRegistry.register(id++, GOTStructureLhazarAltar.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarBazaar.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarFarm.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarHouse.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarPyramid.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarSmithy.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarTavern.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarTotem.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarTower.class, GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarWarCamp.class, GOTFaction.LHAZAR);

		GOTStructureRegistry.register(id++, GOTStructureDothrakiTent.class, GOTFaction.DOTHRAKI);
		GOTStructureRegistry.register(id++, GOTStructureDothrakiTentLarge.class, GOTFaction.DOTHRAKI);
		GOTStructureRegistry.register(id++, GOTStructureDothrakiChieftainTent.class, GOTFaction.DOTHRAKI);
		GOTStructureRegistry.register(id++, GOTStructureDothrakiShamanTent.class, GOTFaction.DOTHRAKI);

		GOTStructureRegistry.register(id++, GOTStructureIbbenBarn.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenFortress.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenHouse.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenSmithy.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenStables.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenTavern.class, GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenWatchtower.class, GOTFaction.IBBEN);

		GOTStructureRegistry.register(id++, GOTStructureJogosNhaiTent.class, GOTFaction.JOGOS_NHAI);
		GOTStructureRegistry.register(id++, GOTStructureJogosNhaiTentLarge.class, GOTFaction.JOGOS_NHAI);
		GOTStructureRegistry.register(id++, GOTStructureJogosNhaiChieftainTent.class, GOTFaction.JOGOS_NHAI);
		GOTStructureRegistry.register(id++, GOTStructureJogosNhaiShamanTent.class, GOTFaction.JOGOS_NHAI);

		GOTStructureRegistry.register(id++, GOTStructureMossovyBarn.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyFortress.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyHouse.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyHouseLarge.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyHouseSmall.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovySmithy.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyStable.class, GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyTavern.class, GOTFaction.MOSSOVY);

		GOTStructureRegistry.register(id++, GOTStructureYiTiFortress.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiHouse.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiHouseLarge.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiHouseSmall.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiSmithy.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiStables.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTavern.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTavernTown.class, GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTower.class, GOTFaction.YI_TI);

		GOTStructureRegistry.register(id++, GOTStructureAsshaiAltar.class, GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiBiolab.class, GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiFort.class, GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiHouse.class, GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiTavern.class, GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiTower.class, GOTFaction.ASSHAI);

		GOTStructureRegistry.register(id++, GOTStructureSummerFortress.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerHouse.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerHouseRuined.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerSmithy.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerStables.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerTavern.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerTavernRuined.class, GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerTower.class, GOTFaction.SUMMER_ISLANDS);

		GOTStructureRegistry.register(id++, GOTStructureSothoryosHouseLarge.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosHouseSimple.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosHouseStilts.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosPyramid.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosSmithy.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosTemple.class, GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosWatchtower.class, GOTFaction.SOTHORYOS);

		GOTStructureRegistry.register(id++, new GOTStructureWildlingSettlement(GOTBiome.ocean, 1.0f), "WildlingSettle", GOTFaction.WILDLING, (GOTStructureRegistry.ISettlementProperties<GOTStructureWildlingSettlement.Instance>) instance -> instance.setType(GOTStructureWildlingSettlement.Type.DEFAULT));
		GOTStructureRegistry.register(id++, new GOTStructureWildlingSettlement(GOTBiome.ocean, 1.0f), "ThennSettle", GOTFaction.WILDLING, (GOTStructureRegistry.ISettlementProperties<GOTStructureWildlingSettlement.Instance>) instance -> instance.setType(GOTStructureWildlingSettlement.Type.THENN));

		GOTStructureRegistry.register(id++, new GOTStructureGiftSettlement(GOTBiome.ocean, 1.0f), "GiftSettle", GOTFaction.NIGHT_WATCH, (GOTStructureRegistry.ISettlementProperties<GOTStructureGiftSettlement.Instance>) instance -> instance.setType(GOTStructureGiftSettlement.Type.VILLAGE));

		GOTStructureRegistry.register(id++, new GOTStructureNorthSettlement(GOTBiome.ocean, 1.0f), "NorthHillmanSettle", GOTFaction.NORTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorthSettlement.Instance>) instance -> instance.setType(GOTStructureNorthSettlement.Type.HILLMAN));

		GOTStructureRegistry.register(id++, new GOTStructureNorthSettlement(GOTBiome.ocean, 1.0f), "NorthSettle", GOTFaction.NORTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorthSettlement.Instance>) instance -> instance.setType(GOTStructureNorthSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureNorthSettlement(GOTBiome.ocean, 1.0f), "NorthSettleForted", GOTFaction.NORTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorthSettlement.Instance>) instance -> instance.setType(GOTStructureNorthSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureNorthSettlement(GOTBiome.ocean, 1.0f), "NorthSettleLarge", GOTFaction.NORTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorthSettlement.Instance>) instance -> instance.setType(GOTStructureNorthSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureIronbornSettlement(GOTBiome.ocean, 1.0f), "IronbornSettle", GOTFaction.IRONBORN, (GOTStructureRegistry.ISettlementProperties<GOTStructureIronbornSettlement.Instance>) instance -> instance.setType(GOTStructureIronbornSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureIronbornSettlement(GOTBiome.ocean, 1.0f), "IronbornSettleForted", GOTFaction.IRONBORN, (GOTStructureRegistry.ISettlementProperties<GOTStructureIronbornSettlement.Instance>) instance -> instance.setType(GOTStructureIronbornSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureIronbornSettlement(GOTBiome.ocean, 1.0f), "IronbornSettleLarge", GOTFaction.IRONBORN, (GOTStructureRegistry.ISettlementProperties<GOTStructureIronbornSettlement.Instance>) instance -> instance.setType(GOTStructureIronbornSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 1.0f), "WesterlandsSettle", GOTFaction.WESTERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureWesterlandsSettlement.Instance>) instance -> instance.setType(GOTStructureWesterlandsSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 1.0f), "WesterlandsSettleForted", GOTFaction.WESTERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureWesterlandsSettlement.Instance>) instance -> instance.setType(GOTStructureWesterlandsSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsSettlement(GOTBiome.ocean, 1.0f), "WesterlandsSettleLarge", GOTFaction.WESTERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureWesterlandsSettlement.Instance>) instance -> instance.setType(GOTStructureWesterlandsSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 1.0f), "RiverlandsSettle", GOTFaction.RIVERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureRiverlandsSettlement.Instance>) instance -> instance.setType(GOTStructureRiverlandsSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 1.0f), "RiverlandsSettleForted", GOTFaction.RIVERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureRiverlandsSettlement.Instance>) instance -> instance.setType(GOTStructureRiverlandsSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsSettlement(GOTBiome.ocean, 1.0f), "RiverlandsSettleLarge", GOTFaction.RIVERLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureRiverlandsSettlement.Instance>) instance -> instance.setType(GOTStructureRiverlandsSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureHillmanSettlement(GOTBiome.ocean, 1.0f), "HillmanSettle", GOTFaction.HILL_TRIBES, (GOTStructureRegistry.ISettlementProperties<GOTStructureHillmanSettlement.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureArrynSettlement(GOTBiome.ocean, 1.0f), "ArrynSettle", GOTFaction.ARRYN, (GOTStructureRegistry.ISettlementProperties<GOTStructureArrynSettlement.Instance>) instance -> instance.setType(GOTStructureArrynSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureArrynSettlement(GOTBiome.ocean, 1.0f), "ArrynSettleForted", GOTFaction.ARRYN, (GOTStructureRegistry.ISettlementProperties<GOTStructureArrynSettlement.Instance>) instance -> instance.setType(GOTStructureArrynSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureArrynSettlement(GOTBiome.ocean, 1.0f), "ArrynSettleLarge", GOTFaction.ARRYN, (GOTStructureRegistry.ISettlementProperties<GOTStructureArrynSettlement.Instance>) instance -> instance.setType(GOTStructureArrynSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 1.0f), "DragonstoneSettle", GOTFaction.DRAGONSTONE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDragonstoneSettlement.Instance>) instance -> instance.setType(GOTStructureDragonstoneSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 1.0f), "DragonstoneSettleForted", GOTFaction.DRAGONSTONE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDragonstoneSettlement.Instance>) instance -> instance.setType(GOTStructureDragonstoneSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneSettlement(GOTBiome.ocean, 1.0f), "DragonstoneSettleLarge", GOTFaction.DRAGONSTONE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDragonstoneSettlement.Instance>) instance -> instance.setType(GOTStructureDragonstoneSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 1.0f), "CrownlandsSettle", GOTFaction.CROWNLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureCrownlandsSettlement.Instance>) instance -> instance.setType(GOTStructureCrownlandsSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 1.0f), "CrownlandsSettleForted", GOTFaction.CROWNLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureCrownlandsSettlement.Instance>) instance -> instance.setType(GOTStructureCrownlandsSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsSettlement(GOTBiome.ocean, 1.0f), "CrownlandsSettleLarge", GOTFaction.CROWNLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureCrownlandsSettlement.Instance>) instance -> instance.setType(GOTStructureCrownlandsSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureStormlandsSettlement(GOTBiome.ocean, 1.0f), "StormlandsSettle", GOTFaction.STORMLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureStormlandsSettlement.Instance>) instance -> instance.setType(GOTStructureStormlandsSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureStormlandsSettlement(GOTBiome.ocean, 1.0f), "StormlandsSettleForted", GOTFaction.STORMLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureStormlandsSettlement.Instance>) instance -> instance.setType(GOTStructureStormlandsSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureStormlandsSettlement(GOTBiome.ocean, 1.0f), "StormlandsSettleLarge", GOTFaction.STORMLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureStormlandsSettlement.Instance>) instance -> instance.setType(GOTStructureStormlandsSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureReachSettlement(GOTBiome.ocean, 1.0f), "ReachSettle", GOTFaction.REACH, (GOTStructureRegistry.ISettlementProperties<GOTStructureReachSettlement.Instance>) instance -> instance.setType(GOTStructureReachSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureReachSettlement(GOTBiome.ocean, 1.0f), "ReachSettleForted", GOTFaction.REACH, (GOTStructureRegistry.ISettlementProperties<GOTStructureReachSettlement.Instance>) instance -> instance.setType(GOTStructureReachSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureReachSettlement(GOTBiome.ocean, 1.0f), "ReachSettleLarge", GOTFaction.REACH, (GOTStructureRegistry.ISettlementProperties<GOTStructureReachSettlement.Instance>) instance -> instance.setType(GOTStructureReachSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureDorneSettlement(GOTBiome.ocean, 1.0f), "DorneSettle", GOTFaction.DORNE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDorneSettlement.Instance>) instance -> instance.setType(GOTStructureDorneSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureDorneSettlement(GOTBiome.ocean, 1.0f), "DorneSettleForted", GOTFaction.DORNE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDorneSettlement.Instance>) instance -> instance.setType(GOTStructureDorneSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureDorneSettlement(GOTBiome.ocean, 1.0f), "DorneSettleLarge", GOTFaction.DORNE, (GOTStructureRegistry.ISettlementProperties<GOTStructureDorneSettlement.Instance>) instance -> instance.setType(GOTStructureDorneSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureBraavosSettlement(GOTBiome.ocean, 1.0f), "BraavosSettle", GOTFaction.BRAAVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureBraavosSettlement.Instance>) instance -> instance.setType(GOTStructureBraavosSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureBraavosSettlement(GOTBiome.ocean, 1.0f), "BraavosSettleForted", GOTFaction.BRAAVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureBraavosSettlement.Instance>) instance -> instance.setType(GOTStructureBraavosSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureBraavosSettlement(GOTBiome.ocean, 1.0f), "BraavosSettleLarge", GOTFaction.BRAAVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureBraavosSettlement.Instance>) instance -> instance.setType(GOTStructureBraavosSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureVolantisSettlement(GOTBiome.ocean, 1.0f), "VolantisSettle", GOTFaction.VOLANTIS, (GOTStructureRegistry.ISettlementProperties<GOTStructureVolantisSettlement.Instance>) instance -> instance.setType(GOTStructureVolantisSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureVolantisSettlement(GOTBiome.ocean, 1.0f), "VolantisSettleForted", GOTFaction.VOLANTIS, (GOTStructureRegistry.ISettlementProperties<GOTStructureVolantisSettlement.Instance>) instance -> instance.setType(GOTStructureVolantisSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureVolantisSettlement(GOTBiome.ocean, 1.0f), "VolantisSettleLarge", GOTFaction.VOLANTIS, (GOTStructureRegistry.ISettlementProperties<GOTStructureVolantisSettlement.Instance>) instance -> instance.setType(GOTStructureVolantisSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructurePentosSettlement(GOTBiome.ocean, 1.0f), "PentosSettle", GOTFaction.PENTOS, (GOTStructureRegistry.ISettlementProperties<GOTStructurePentosSettlement.Instance>) instance -> instance.setType(GOTStructurePentosSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructurePentosSettlement(GOTBiome.ocean, 1.0f), "PentosSettleForted", GOTFaction.PENTOS, (GOTStructureRegistry.ISettlementProperties<GOTStructurePentosSettlement.Instance>) instance -> instance.setType(GOTStructurePentosSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructurePentosSettlement(GOTBiome.ocean, 1.0f), "PentosSettleLarge", GOTFaction.PENTOS, (GOTStructureRegistry.ISettlementProperties<GOTStructurePentosSettlement.Instance>) instance -> instance.setType(GOTStructurePentosSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureNorvosSettlement(GOTBiome.ocean, 1.0f), "NorvosSettle", GOTFaction.NORVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorvosSettlement.Instance>) instance -> instance.setType(GOTStructureNorvosSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureNorvosSettlement(GOTBiome.ocean, 1.0f), "NorvosSettleForted", GOTFaction.NORVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorvosSettlement.Instance>) instance -> instance.setType(GOTStructureNorvosSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureNorvosSettlement(GOTBiome.ocean, 1.0f), "NorvosSettleLarge", GOTFaction.NORVOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureNorvosSettlement.Instance>) instance -> instance.setType(GOTStructureNorvosSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureLorathSettlement(GOTBiome.ocean, 1.0f), "LorathSettle", GOTFaction.LORATH, (GOTStructureRegistry.ISettlementProperties<GOTStructureLorathSettlement.Instance>) instance -> instance.setType(GOTStructureLorathSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureLorathSettlement(GOTBiome.ocean, 1.0f), "LorathSettleForted", GOTFaction.LORATH, (GOTStructureRegistry.ISettlementProperties<GOTStructureLorathSettlement.Instance>) instance -> instance.setType(GOTStructureLorathSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureLorathSettlement(GOTBiome.ocean, 1.0f), "LorathSettleLarge", GOTFaction.LORATH, (GOTStructureRegistry.ISettlementProperties<GOTStructureLorathSettlement.Instance>) instance -> instance.setType(GOTStructureLorathSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureQohorSettlement(GOTBiome.ocean, 1.0f), "QohorSettle", GOTFaction.QOHOR, (GOTStructureRegistry.ISettlementProperties<GOTStructureQohorSettlement.Instance>) instance -> instance.setType(GOTStructureQohorSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureQohorSettlement(GOTBiome.ocean, 1.0f), "QohorSettleForted", GOTFaction.QOHOR, (GOTStructureRegistry.ISettlementProperties<GOTStructureQohorSettlement.Instance>) instance -> instance.setType(GOTStructureQohorSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureQohorSettlement(GOTBiome.ocean, 1.0f), "QohorSettleLarge", GOTFaction.QOHOR, (GOTStructureRegistry.ISettlementProperties<GOTStructureQohorSettlement.Instance>) instance -> instance.setType(GOTStructureQohorSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureLysSettlement(GOTBiome.ocean, 1.0f), "LysSettle", GOTFaction.LYS, (GOTStructureRegistry.ISettlementProperties<GOTStructureLysSettlement.Instance>) instance -> instance.setType(GOTStructureLysSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureLysSettlement(GOTBiome.ocean, 1.0f), "LysSettleForted", GOTFaction.LYS, (GOTStructureRegistry.ISettlementProperties<GOTStructureLysSettlement.Instance>) instance -> instance.setType(GOTStructureLysSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureLysSettlement(GOTBiome.ocean, 1.0f), "LysSettleLarge", GOTFaction.LYS, (GOTStructureRegistry.ISettlementProperties<GOTStructureLysSettlement.Instance>) instance -> instance.setType(GOTStructureLysSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureMyrSettlement(GOTBiome.ocean, 1.0f), "MyrSettle", GOTFaction.MYR, (GOTStructureRegistry.ISettlementProperties<GOTStructureMyrSettlement.Instance>) instance -> instance.setType(GOTStructureMyrSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureMyrSettlement(GOTBiome.ocean, 1.0f), "MyrSettleForted", GOTFaction.MYR, (GOTStructureRegistry.ISettlementProperties<GOTStructureMyrSettlement.Instance>) instance -> instance.setType(GOTStructureMyrSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureMyrSettlement(GOTBiome.ocean, 1.0f), "MyrSettleLarge", GOTFaction.MYR, (GOTStructureRegistry.ISettlementProperties<GOTStructureMyrSettlement.Instance>) instance -> instance.setType(GOTStructureMyrSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureTyroshSettlement(GOTBiome.ocean, 1.0f), "TyroshSettle", GOTFaction.TYROSH, (GOTStructureRegistry.ISettlementProperties<GOTStructureTyroshSettlement.Instance>) instance -> instance.setType(GOTStructureTyroshSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureTyroshSettlement(GOTBiome.ocean, 1.0f), "TyroshSettleForted", GOTFaction.TYROSH, (GOTStructureRegistry.ISettlementProperties<GOTStructureTyroshSettlement.Instance>) instance -> instance.setType(GOTStructureTyroshSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureTyroshSettlement(GOTBiome.ocean, 1.0f), "TyroshSettleLarge", GOTFaction.TYROSH, (GOTStructureRegistry.ISettlementProperties<GOTStructureTyroshSettlement.Instance>) instance -> instance.setType(GOTStructureTyroshSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureGoldenCompanySettlement(GOTBiome.ocean, 1.0f), "GoldenCompanySettle", 0xffd700, (GOTStructureRegistry.ISettlementProperties<GOTStructureGoldenCompanySettlement.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureGhiscarSettlement(GOTBiome.ocean, 1.0f), "GhiscarSettle", GOTFaction.GHISCAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureGhiscarSettlement.Instance>) instance -> instance.setType(GOTStructureGhiscarSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureGhiscarSettlement(GOTBiome.ocean, 1.0f), "GhiscarSettleForted", GOTFaction.GHISCAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureGhiscarSettlement.Instance>) instance -> instance.setType(GOTStructureGhiscarSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureGhiscarSettlement(GOTBiome.ocean, 1.0f), "GhiscarSettleLarge", GOTFaction.GHISCAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureGhiscarSettlement.Instance>) instance -> instance.setType(GOTStructureGhiscarSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureQarthSettlement(GOTBiome.ocean, 1.0f), "QarthSettle", GOTFaction.QARTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureQarthSettlement.Instance>) instance -> instance.setType(GOTStructureQarthSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureQarthSettlement(GOTBiome.ocean, 1.0f), "QarthSettleForted", GOTFaction.QARTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureQarthSettlement.Instance>) instance -> instance.setType(GOTStructureQarthSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureQarthSettlement(GOTBiome.ocean, 1.0f), "QarthSettleLarge", GOTFaction.QARTH, (GOTStructureRegistry.ISettlementProperties<GOTStructureQarthSettlement.Instance>) instance -> instance.setType(GOTStructureQarthSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureLhazarSettlement(GOTBiome.ocean, 1.0f), "LhazarSettle", GOTFaction.LHAZAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureLhazarSettlement.Instance>) instance -> instance.setType(GOTStructureLhazarSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureLhazarSettlement(GOTBiome.ocean, 1.0f), "LhazarSettleForted", GOTFaction.LHAZAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureLhazarSettlement.Instance>) instance -> instance.setType(GOTStructureLhazarSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureLhazarSettlement(GOTBiome.ocean, 1.0f), "LhazarSettleLarge", GOTFaction.LHAZAR, (GOTStructureRegistry.ISettlementProperties<GOTStructureLhazarSettlement.Instance>) instance -> instance.setType(GOTStructureLhazarSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureDothrakiSettlement(GOTBiome.ocean, 1.0f), "DothrakiSettle", GOTFaction.DOTHRAKI, (GOTStructureRegistry.ISettlementProperties<GOTStructureDothrakiSettlement.Instance>) instance -> instance.setType(GOTStructureDothrakiSettlement.Type.SMALL));
		GOTStructureRegistry.register(id++, new GOTStructureDothrakiSettlement(GOTBiome.ocean, 1.0f), "DothrakiSettleLarge", GOTFaction.DOTHRAKI, (GOTStructureRegistry.ISettlementProperties<GOTStructureDothrakiSettlement.Instance>) instance -> instance.setType(GOTStructureDothrakiSettlement.Type.BIG));

		GOTStructureRegistry.register(id++, new GOTStructureIbbenSettlement(GOTBiome.ocean, 1.0f), "IbbenSettle", GOTFaction.IBBEN, (GOTStructureRegistry.ISettlementProperties<GOTStructureIbbenSettlement.Instance>) instance -> instance.setType(GOTStructureIbbenSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureIbbenSettlement(GOTBiome.ocean, 1.0f), "IbbenSettleForted", GOTFaction.IBBEN, (GOTStructureRegistry.ISettlementProperties<GOTStructureIbbenSettlement.Instance>) instance -> instance.setType(GOTStructureIbbenSettlement.Type.FORT));

		GOTStructureRegistry.register(id++, new GOTStructureJogosNhaiSettlement(GOTBiome.ocean, 1.0f), "JogosNhaiSettle", GOTFaction.JOGOS_NHAI, (GOTStructureRegistry.ISettlementProperties<GOTStructureJogosNhaiSettlement.Instance>) instance -> instance.setType(GOTStructureJogosNhaiSettlement.Type.SMALL));
		GOTStructureRegistry.register(id++, new GOTStructureJogosNhaiSettlement(GOTBiome.ocean, 1.0f), "JogosNhaiSettleLarge", GOTFaction.JOGOS_NHAI, (GOTStructureRegistry.ISettlementProperties<GOTStructureJogosNhaiSettlement.Instance>) instance -> instance.setType(GOTStructureJogosNhaiSettlement.Type.BIG));

		GOTStructureRegistry.register(id++, new GOTStructureMossovySettlement(GOTBiome.ocean, 1.0f), "MossovySettle", GOTFaction.JOGOS_NHAI, (GOTStructureRegistry.ISettlementProperties<GOTStructureMossovySettlement.Instance>) instance -> instance.setType(GOTStructureMossovySettlement.Type.VILLAGE));

		GOTStructureRegistry.register(id++, new GOTStructureYiTiSettlement(GOTBiome.ocean, 1.0f), "YiTiSettle", GOTFaction.YI_TI, (GOTStructureRegistry.ISettlementProperties<GOTStructureYiTiSettlement.Instance>) instance -> instance.setType(GOTStructureYiTiSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureYiTiSettlement(GOTBiome.ocean, 1.0f), "YiTiSettleForted", GOTFaction.YI_TI, (GOTStructureRegistry.ISettlementProperties<GOTStructureYiTiSettlement.Instance>) instance -> instance.setType(GOTStructureYiTiSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureYiTiSettlement(GOTBiome.ocean, 1.0f), "YiTiSettleLarge", GOTFaction.YI_TI, (GOTStructureRegistry.ISettlementProperties<GOTStructureYiTiSettlement.Instance>) instance -> instance.setType(GOTStructureYiTiSettlement.Type.TOWN));

		GOTStructureRegistry.register(id++, new GOTStructureAsshaiSettlement(GOTBiome.ocean, 1.0f), "AsshaiSettle", GOTFaction.ASSHAI, (GOTStructureRegistry.ISettlementProperties<GOTStructureAsshaiSettlement.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureSummerSettlement(GOTBiome.ocean, 1.0f), "SummerSettle", GOTFaction.SUMMER_ISLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureSummerSettlement.Instance>) instance -> instance.setType(GOTStructureSummerSettlement.Type.VILLAGE));
		GOTStructureRegistry.register(id++, new GOTStructureSummerSettlement(GOTBiome.ocean, 1.0f), "SummerSettleForted", GOTFaction.SUMMER_ISLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureSummerSettlement.Instance>) instance -> instance.setType(GOTStructureSummerSettlement.Type.FORT));
		GOTStructureRegistry.register(id++, new GOTStructureSummerSettlement(GOTBiome.ocean, 1.0f), "SummerSettleRuined", GOTFaction.SUMMER_ISLANDS, (GOTStructureRegistry.ISettlementProperties<GOTStructureSummerSettlement.Instance>) instance -> instance.setType(GOTStructureSummerSettlement.Type.RUINED_VILLAGE));

		GOTStructureRegistry.register(id++, new GOTStructureSothoryosSettlement(GOTBiome.ocean, 1.0f), "SothoryosSettle", GOTFaction.SOTHORYOS, (GOTStructureRegistry.ISettlementProperties<GOTStructureSothoryosSettlement.Instance>) instance -> {
		});

		GOTStructureSothoryosPyramidMapgen.register();
		GOTStructureGhiscarPyramidMapgen.register();
	}
}