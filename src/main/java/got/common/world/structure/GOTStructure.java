package got.common.world.structure;

import got.GOT;
import got.common.faction.GOTFaction;
import got.common.util.DatabaseGenerator;
import got.common.world.biome.GOTBiome;
import got.common.world.structure.essos.asshai.*;
import got.common.world.structure.essos.braavos.*;
import got.common.world.structure.essos.dothraki.*;
import got.common.world.structure.essos.ghiscar.*;
import got.common.world.structure.essos.gold.*;
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
import got.common.world.structure.other.GOTStructureRegistry.IVillageProperties;
import got.common.world.structure.sothoryos.sothoryos.*;
import got.common.world.structure.sothoryos.sothoryos.GOTStructureSothoryosVillage.Instance;
import got.common.world.structure.sothoryos.summer.*;
import got.common.world.structure.westeros.arryn.*;
import got.common.world.structure.westeros.crownlands.*;
import got.common.world.structure.westeros.dorne.*;
import got.common.world.structure.westeros.dragonstone.*;
import got.common.world.structure.westeros.gift.*;
import got.common.world.structure.westeros.hillmen.*;
import got.common.world.structure.westeros.ironborn.*;
import got.common.world.structure.westeros.north.*;
import got.common.world.structure.westeros.north.hillmen.*;
import got.common.world.structure.westeros.reach.*;
import got.common.world.structure.westeros.riverlands.*;
import got.common.world.structure.westeros.stormlands.*;
import got.common.world.structure.westeros.westerlands.*;
import got.common.world.structure.westeros.wildling.*;
import got.common.world.structure.westeros.wildling.thenn.*;

public class GOTStructure {
	public static int id;

	public static void onInit() {
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

		GOTStructureRegistry.register(id++, GOTStructureGoldenWatchtower.class, 0xffd700);

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

		GOTStructureRegistry.register(id++, new GOTStructureWildlingVillage(GOTBiome.hauntedForest, 1.0f), "WildlingVillage", GOTFaction.WILDLING, (IVillageProperties<GOTStructureWildlingVillage.Instance>) instance -> instance.villageType = GOTStructureWildlingVillage.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureThennVillage(GOTBiome.thenn, 1.0f), "ThennVillage", GOTFaction.WILDLING, (IVillageProperties<GOTStructureThennVillage.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureGiftVillage(GOTBiome.giftNew, 1.0f), "GiftVillage", GOTFaction.NIGHT_WATCH, (IVillageProperties<GOTStructureGiftVillage.Instance>) instance -> instance.villageType = GOTStructureGiftVillage.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureNorthHillmanVillage(GOTBiome.skagos, 1.0f), "NorthHillmanVillage", GOTFaction.NORTH, (IVillageProperties<GOTStructureNorthHillmanVillage.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureNorthCity(GOTBiome.north, 1.0f), "NorthVillage", GOTFaction.NORTH, (IVillageProperties<GOTStructureNorthCity.Instance>) instance -> instance.villageType = GOTStructureNorthCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureNorthCity(GOTBiome.north, 1.0f), "NorthTown", GOTFaction.NORTH, (IVillageProperties<GOTStructureNorthCity.Instance>) instance -> instance.villageType = GOTStructureNorthCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureNorthCity(GOTBiome.north, 1.0f), "NorthFortVillage", GOTFaction.NORTH, (IVillageProperties<GOTStructureNorthCity.Instance>) instance -> instance.villageType = GOTStructureNorthCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureIronbornCity(GOTBiome.ironborn, 1.0f), "IronbornCityLittle", GOTFaction.IRONBORN, (IVillageProperties<GOTStructureIronbornCity.Instance>) instance -> instance.villageType = GOTStructureIronbornCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureIronbornCity(GOTBiome.ironborn, 1.0f), "IronbornCityBig", GOTFaction.IRONBORN, (IVillageProperties<GOTStructureIronbornCity.Instance>) instance -> instance.villageType = GOTStructureIronbornCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureIronbornCity(GOTBiome.ironborn, 1.0f), "IronbornCityMedium", GOTFaction.IRONBORN, (IVillageProperties<GOTStructureIronbornCity.Instance>) instance -> instance.villageType = GOTStructureIronbornCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsCity(GOTBiome.westerlands, 1.0f), "WesterlandsCityLittle", GOTFaction.WESTERLANDS, (IVillageProperties<GOTStructureWesterlandsCity.Instance>) instance -> instance.villageType = GOTStructureWesterlandsCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsCity(GOTBiome.westerlands, 1.0f), "WesterlandsCityBig", GOTFaction.WESTERLANDS, (IVillageProperties<GOTStructureWesterlandsCity.Instance>) instance -> instance.villageType = GOTStructureWesterlandsCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsCity(GOTBiome.westerlands, 1.0f), "WesterlandsCityMedium", GOTFaction.WESTERLANDS, (IVillageProperties<GOTStructureWesterlandsCity.Instance>) instance -> instance.villageType = GOTStructureWesterlandsCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsCity(GOTBiome.riverlands, 1.0f), "RiverlandsCityLittle", GOTFaction.RIVERLANDS, (IVillageProperties<GOTStructureRiverlandsCity.Instance>) instance -> instance.villageType = GOTStructureRiverlandsCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsCity(GOTBiome.riverlands, 1.0f), "RiverlandsCityBig", GOTFaction.RIVERLANDS, (IVillageProperties<GOTStructureRiverlandsCity.Instance>) instance -> instance.villageType = GOTStructureRiverlandsCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsCity(GOTBiome.riverlands, 1.0f), "RiverlandsCityMedium", GOTFaction.RIVERLANDS, (IVillageProperties<GOTStructureRiverlandsCity.Instance>) instance -> instance.villageType = GOTStructureRiverlandsCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureHillmanVillage(GOTBiome.sothoryosJungle, 1.0f), "HillmanVillage", GOTFaction.HILL_TRIBES, (IVillageProperties<GOTStructureHillmanVillage.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureArrynCity(GOTBiome.arryn, 1.0f), "ArrynCityLittle", GOTFaction.ARRYN, (IVillageProperties<GOTStructureArrynCity.Instance>) instance -> instance.villageType = GOTStructureArrynCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureArrynCity(GOTBiome.arryn, 1.0f), "ArrynCityBig", GOTFaction.ARRYN, (IVillageProperties<GOTStructureArrynCity.Instance>) instance -> instance.villageType = GOTStructureArrynCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureArrynCity(GOTBiome.arryn, 1.0f), "ArrynCityMedium", GOTFaction.ARRYN, (IVillageProperties<GOTStructureArrynCity.Instance>) instance -> instance.villageType = GOTStructureArrynCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneCity(GOTBiome.dragonstone, 1.0f), "DragonstoneCityLittle", GOTFaction.DRAGONSTONE, (IVillageProperties<GOTStructureDragonstoneCity.Instance>) instance -> instance.villageType = GOTStructureDragonstoneCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneCity(GOTBiome.dragonstone, 1.0f), "DragonstoneCityBig", GOTFaction.DRAGONSTONE, (IVillageProperties<GOTStructureDragonstoneCity.Instance>) instance -> instance.villageType = GOTStructureDragonstoneCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneCity(GOTBiome.dragonstone, 1.0f), "DragonstoneCityMedium", GOTFaction.DRAGONSTONE, (IVillageProperties<GOTStructureDragonstoneCity.Instance>) instance -> instance.villageType = GOTStructureDragonstoneCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsCity(GOTBiome.crownlands, 1.0f), "CrownlandsCityLittle", GOTFaction.CROWNLANDS, (IVillageProperties<GOTStructureCrownlandsCity.Instance>) instance -> instance.villageType = GOTStructureCrownlandsCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsCity(GOTBiome.crownlands, 1.0f), "CrownlandsCityBig", GOTFaction.CROWNLANDS, (IVillageProperties<GOTStructureCrownlandsCity.Instance>) instance -> instance.villageType = GOTStructureCrownlandsCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsCity(GOTBiome.crownlands, 1.0f), "CrownlandsCityMedium", GOTFaction.CROWNLANDS, (IVillageProperties<GOTStructureCrownlandsCity.Instance>) instance -> instance.villageType = GOTStructureCrownlandsCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureStormlandsCity(GOTBiome.stormlands, 1.0f), "StormlandsCityLittle", GOTFaction.STORMLANDS, (IVillageProperties<GOTStructureStormlandsCity.Instance>) instance -> instance.villageType = GOTStructureStormlandsCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureStormlandsCity(GOTBiome.stormlands, 1.0f), "StormlandsCityBig", GOTFaction.STORMLANDS, (IVillageProperties<GOTStructureStormlandsCity.Instance>) instance -> instance.villageType = GOTStructureStormlandsCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureStormlandsCity(GOTBiome.stormlands, 1.0f), "StormlandsCityMedium", GOTFaction.STORMLANDS, (IVillageProperties<GOTStructureStormlandsCity.Instance>) instance -> instance.villageType = GOTStructureStormlandsCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureReachCity(GOTBiome.reach, 1.0f), "ReachCityLittle", GOTFaction.REACH, (IVillageProperties<GOTStructureReachCity.Instance>) instance -> instance.villageType = GOTStructureReachCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureReachCity(GOTBiome.reach, 1.0f), "ReachCityBig", GOTFaction.REACH, (IVillageProperties<GOTStructureReachCity.Instance>) instance -> instance.villageType = GOTStructureReachCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureReachCity(GOTBiome.reach, 1.0f), "ReachCityMedium", GOTFaction.REACH, (IVillageProperties<GOTStructureReachCity.Instance>) instance -> instance.villageType = GOTStructureReachCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureDorneCity(GOTBiome.dorne, 1.0f), "DorneCityLittle", GOTFaction.DORNE, (IVillageProperties<GOTStructureDorneCity.Instance>) instance -> instance.villageType = GOTStructureDorneCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureDorneCity(GOTBiome.dorne, 1.0f), "DorneCityBig", GOTFaction.DORNE, (IVillageProperties<GOTStructureDorneCity.Instance>) instance -> instance.villageType = GOTStructureDorneCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureDorneCity(GOTBiome.dorne, 1.0f), "DorneCityMedium", GOTFaction.DORNE, (IVillageProperties<GOTStructureDorneCity.Instance>) instance -> instance.villageType = GOTStructureDorneCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureBraavosCity(GOTBiome.braavos, 1.0f), "BraavosCityLittle", GOTFaction.BRAAVOS, (IVillageProperties<GOTStructureBraavosCity.Instance>) instance -> instance.villageType = GOTStructureBraavosCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureBraavosCity(GOTBiome.braavos, 1.0f), "BraavosCityMedium", GOTFaction.BRAAVOS, (IVillageProperties<GOTStructureBraavosCity.Instance>) instance -> instance.villageType = GOTStructureBraavosCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureBraavosCity(GOTBiome.braavos, 1.0f), "BraavosCityBig", GOTFaction.BRAAVOS, (IVillageProperties<GOTStructureBraavosCity.Instance>) instance -> instance.villageType = GOTStructureBraavosCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureVolantisCity(GOTBiome.volantis, 1.0f), "VolantisCityLittle", GOTFaction.VOLANTIS, (IVillageProperties<GOTStructureVolantisCity.Instance>) instance -> instance.villageType = GOTStructureVolantisCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureVolantisCity(GOTBiome.volantis, 1.0f), "VolantisCityMedium", GOTFaction.VOLANTIS, (IVillageProperties<GOTStructureVolantisCity.Instance>) instance -> instance.villageType = GOTStructureVolantisCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureVolantisCity(GOTBiome.volantis, 1.0f), "VolantisCityBig", GOTFaction.VOLANTIS, (IVillageProperties<GOTStructureVolantisCity.Instance>) instance -> instance.villageType = GOTStructureVolantisCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructurePentosCity(GOTBiome.pentos, 1.0f), "PentosCityLittle", GOTFaction.PENTOS, (IVillageProperties<GOTStructurePentosCity.Instance>) instance -> instance.villageType = GOTStructurePentosCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructurePentosCity(GOTBiome.pentos, 1.0f), "PentosCityMedium", GOTFaction.PENTOS, (IVillageProperties<GOTStructurePentosCity.Instance>) instance -> instance.villageType = GOTStructurePentosCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructurePentosCity(GOTBiome.pentos, 1.0f), "PentosCityBig", GOTFaction.PENTOS, (IVillageProperties<GOTStructurePentosCity.Instance>) instance -> instance.villageType = GOTStructurePentosCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureNorvosCity(GOTBiome.norvos, 1.0f), "NorvosCityLittle", GOTFaction.NORVOS, (IVillageProperties<GOTStructureNorvosCity.Instance>) instance -> instance.villageType = GOTStructureNorvosCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureNorvosCity(GOTBiome.norvos, 1.0f), "NorvosCityMedium", GOTFaction.NORVOS, (IVillageProperties<GOTStructureNorvosCity.Instance>) instance -> instance.villageType = GOTStructureNorvosCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureNorvosCity(GOTBiome.norvos, 1.0f), "NorvosCityBig", GOTFaction.NORVOS, (IVillageProperties<GOTStructureNorvosCity.Instance>) instance -> instance.villageType = GOTStructureNorvosCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureLorathCity(GOTBiome.lorath, 1.0f), "LorathCityLittle", GOTFaction.LORATH, (IVillageProperties<GOTStructureLorathCity.Instance>) instance -> instance.villageType = GOTStructureLorathCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureLorathCity(GOTBiome.lorath, 1.0f), "LorathCityMedium", GOTFaction.LORATH, (IVillageProperties<GOTStructureLorathCity.Instance>) instance -> instance.villageType = GOTStructureLorathCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureLorathCity(GOTBiome.lorath, 1.0f), "LorathCityBig", GOTFaction.LORATH, (IVillageProperties<GOTStructureLorathCity.Instance>) instance -> instance.villageType = GOTStructureLorathCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureQohorCity(GOTBiome.qohor, 1.0f), "QohorCityLittle", GOTFaction.QOHOR, (IVillageProperties<GOTStructureQohorCity.Instance>) instance -> instance.villageType = GOTStructureQohorCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureQohorCity(GOTBiome.qohor, 1.0f), "QohorCityMedium", GOTFaction.QOHOR, (IVillageProperties<GOTStructureQohorCity.Instance>) instance -> instance.villageType = GOTStructureQohorCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureQohorCity(GOTBiome.qohor, 1.0f), "QohorCityBig", GOTFaction.QOHOR, (IVillageProperties<GOTStructureQohorCity.Instance>) instance -> instance.villageType = GOTStructureQohorCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureLysCity(GOTBiome.lys, 1.0f), "LysCityLittle", GOTFaction.LYS, (IVillageProperties<GOTStructureLysCity.Instance>) instance -> instance.villageType = GOTStructureLysCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureLysCity(GOTBiome.lys, 1.0f), "LysCityMedium", GOTFaction.LYS, (IVillageProperties<GOTStructureLysCity.Instance>) instance -> instance.villageType = GOTStructureLysCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureLysCity(GOTBiome.lys, 1.0f), "LysCityBig", GOTFaction.LYS, (IVillageProperties<GOTStructureLysCity.Instance>) instance -> instance.villageType = GOTStructureLysCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureMyrCity(GOTBiome.myr, 1.0f), "MyrCityLittle", GOTFaction.MYR, (IVillageProperties<GOTStructureMyrCity.Instance>) instance -> instance.villageType = GOTStructureMyrCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureMyrCity(GOTBiome.myr, 1.0f), "MyrCityMedium", GOTFaction.MYR, (IVillageProperties<GOTStructureMyrCity.Instance>) instance -> instance.villageType = GOTStructureMyrCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureMyrCity(GOTBiome.myr, 1.0f), "MyrCityBig", GOTFaction.MYR, (IVillageProperties<GOTStructureMyrCity.Instance>) instance -> instance.villageType = GOTStructureMyrCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureTyroshCity(GOTBiome.tyrosh, 1.0f), "TyroshCityLittle", GOTFaction.TYROSH, (IVillageProperties<GOTStructureTyroshCity.Instance>) instance -> instance.villageType = GOTStructureTyroshCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureTyroshCity(GOTBiome.tyrosh, 1.0f), "TyroshCityMedium", GOTFaction.TYROSH, (IVillageProperties<GOTStructureTyroshCity.Instance>) instance -> instance.villageType = GOTStructureTyroshCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureTyroshCity(GOTBiome.tyrosh, 1.0f), "TyroshCityBig", GOTFaction.TYROSH, (IVillageProperties<GOTStructureTyroshCity.Instance>) instance -> instance.villageType = GOTStructureTyroshCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureGoldenCamp(GOTBiome.essos, 1.0f), "GoldenCamp", 0xffd700, (IVillageProperties<GOTStructureGoldenCamp.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureGhiscarCity(GOTBiome.ghiscar, 1.0f), "GhiscarCityLittle", GOTFaction.GHISCAR, (IVillageProperties<GOTStructureGhiscarCity.Instance>) instance -> instance.villageType = GOTStructureGhiscarCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureGhiscarCity(GOTBiome.ghiscar, 1.0f), "GhiscarCityMedium", GOTFaction.GHISCAR, (IVillageProperties<GOTStructureGhiscarCity.Instance>) instance -> instance.villageType = GOTStructureGhiscarCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureGhiscarCity(GOTBiome.ghiscar, 1.0f), "GhiscarCityBig", GOTFaction.GHISCAR, (IVillageProperties<GOTStructureGhiscarCity.Instance>) instance -> instance.villageType = GOTStructureGhiscarCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureQarthCity(GOTBiome.qarth, 1.0f), "QarthCityLittle", GOTFaction.QARTH, (IVillageProperties<GOTStructureQarthCity.Instance>) instance -> instance.villageType = GOTStructureQarthCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureQarthCity(GOTBiome.qarth, 1.0f), "QarthCityMedium", GOTFaction.QARTH, (IVillageProperties<GOTStructureQarthCity.Instance>) instance -> instance.villageType = GOTStructureQarthCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureQarthCity(GOTBiome.qarth, 1.0f), "QarthCityBig", GOTFaction.QARTH, (IVillageProperties<GOTStructureQarthCity.Instance>) instance -> instance.villageType = GOTStructureQarthCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureLhazarVillage(GOTBiome.lhazar, 1.0f), "LhazarCityLittle", GOTFaction.LHAZAR, (IVillageProperties<GOTStructureLhazarVillage.Instance>) instance -> instance.villageType = GOTStructureLhazarVillage.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureLhazarVillage(GOTBiome.lhazar, 1.0f), "LhazarCityMedium", GOTFaction.LHAZAR, (IVillageProperties<GOTStructureLhazarVillage.Instance>) instance -> instance.villageType = GOTStructureLhazarVillage.VillageType.FORT);
		GOTStructureRegistry.register(id++, new GOTStructureLhazarVillage(GOTBiome.lhazar, 1.0f), "LhazarCityBig", GOTFaction.LHAZAR, (IVillageProperties<GOTStructureLhazarVillage.Instance>) instance -> instance.villageType = GOTStructureLhazarVillage.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureDothrakiVillage(GOTBiome.dothrakiSea, 1.0f), "DothrakiVillageLittle", GOTFaction.DOTHRAKI, (IVillageProperties<GOTStructureDothrakiVillage.Instance>) instance -> instance.villageType = GOTStructureDothrakiVillage.VillageType.SMALL);
		GOTStructureRegistry.register(id++, new GOTStructureDothrakiVillage(GOTBiome.dothrakiSea, 1.0f), "DothrakiVillageBig", GOTFaction.DOTHRAKI, (IVillageProperties<GOTStructureDothrakiVillage.Instance>) instance -> instance.villageType = GOTStructureDothrakiVillage.VillageType.BIG);

		GOTStructureRegistry.register(id++, new GOTStructureIbbenVillage(GOTBiome.ibben, 1.0f), "IbbenVillage", GOTFaction.IBBEN, (IVillageProperties<GOTStructureIbbenVillage.Instance>) instance -> instance.villageType = GOTStructureIbbenVillage.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureIbbenVillage(GOTBiome.ibben, 1.0f), "IbbenFortVillage", GOTFaction.IBBEN, (IVillageProperties<GOTStructureIbbenVillage.Instance>) instance -> instance.villageType = GOTStructureIbbenVillage.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureJogosVillage(GOTBiome.jogosNhai, 1.0f), "JogosVillageLittle", GOTFaction.JOGOS, (IVillageProperties<GOTStructureJogosVillage.Instance>) instance -> instance.villageType = GOTStructureJogosVillage.VillageType.SMALL);
		GOTStructureRegistry.register(id++, new GOTStructureJogosVillage(GOTBiome.jogosNhai, 1.0f), "JogosVillageBig", GOTFaction.JOGOS, (IVillageProperties<GOTStructureJogosVillage.Instance>) instance -> instance.villageType = GOTStructureJogosVillage.VillageType.BIG);

		GOTStructureRegistry.register(id++, new GOTStructureMossovyVillage(GOTBiome.mossovy, 1.0f), "MossovyVillage", GOTFaction.MOSSOVY, (IVillageProperties<GOTStructureMossovyVillage.Instance>) instance -> {
		});

		GOTStructureRegistry.register(id++, new GOTStructureYiTiCity(GOTBiome.yiTi, 1.0f), "YiTiVillage", GOTFaction.YI_TI, (IVillageProperties<GOTStructureYiTiCity.Instance>) instance -> instance.villageType = GOTStructureYiTiCity.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureYiTiCity(GOTBiome.yiTi, 1.0f), "YiTiTown", GOTFaction.YI_TI, (IVillageProperties<GOTStructureYiTiCity.Instance>) instance -> instance.villageType = GOTStructureYiTiCity.VillageType.TOWN);
		GOTStructureRegistry.register(id++, new GOTStructureYiTiCity(GOTBiome.yiTi, 1.0f), "YiTiFortVillage", GOTFaction.YI_TI, (IVillageProperties<GOTStructureYiTiCity.Instance>) instance -> instance.villageType = GOTStructureYiTiCity.VillageType.FORT);

		GOTStructureRegistry.register(id++, new GOTStructureAsshaiCity(GOTBiome.shadowTown, 1.0f), "AsshaiCity", GOTFaction.ASSHAI, (IVillageProperties<GOTStructureAsshaiCity.Instance>) instance -> instance.villageType = GOTStructureAsshaiCity.VillageType.TOWN);

		GOTStructureRegistry.register(id++, new GOTStructureSummerVillage(GOTBiome.summerIslands, 1.0f), "SummerVillage", GOTFaction.SUMMER_ISLANDS, (IVillageProperties<GOTStructureSummerVillage.Instance>) instance -> instance.villageType = GOTStructureSummerVillage.VillageType.VILLAGE);
		GOTStructureRegistry.register(id++, new GOTStructureSummerVillage(GOTBiome.summerIslands, 1.0f), "SummerFortVillage", GOTFaction.SUMMER_ISLANDS, (IVillageProperties<GOTStructureSummerVillage.Instance>) instance -> instance.villageType = GOTStructureSummerVillage.VillageType.FORTRESS);
		GOTStructureRegistry.register(id++, new GOTStructureSummerVillage(GOTBiome.summerIslands, 1.0f).setRuined(), "SummerVillageRuined", GOTFaction.SUMMER_ISLANDS, (IVillageProperties<GOTStructureSummerVillage.Instance>) instance -> instance.villageType = GOTStructureSummerVillage.VillageType.VILLAGE);

		GOTStructureRegistry.register(id++, new GOTStructureSothoryosVillage(GOTBiome.sothoryosJungle, 1.0f), "SothoryosVillage", GOTFaction.SOTHORYOS, (IVillageProperties<Instance>) instance -> {
		});

		if (GOT.isDevMode) {
			GOTStructureRegistry.register(id++, DatabaseGenerator.class, 9605778);
		}

		GOTStructureSothoryosPyramidMapgen.register();
		GOTStructureGhiscarPyramidMapgen.register();
	}
}