package got.common.world.structure;

import got.GOT;
import got.common.faction.GOTFaction;
import got.common.util.DatabaseGenerator;
import got.common.world.biome.GOTBiome;
import got.common.world.structure.essos.asshai.*;
import got.common.world.structure.essos.braavos.*;
import got.common.world.structure.essos.dothraki.*;
import got.common.world.structure.essos.ghiscar.*;
import got.common.world.structure.essos.gold.GOTStructureGoldenCamp;
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
		GOTStructureRegistry.register(id++, GOTStructureBarrow.class, "Barrow", 9605778);
		GOTStructureRegistry.register(id++, GOTStructureRuinedHouse.class, "RuinedHouse", 9605778);
		GOTStructureRegistry.register(id++, GOTStructureBurntHouse.class, "BurntHouse", 9605778);
		GOTStructureRegistry.register(id++, GOTStructureRottenHouse.class, "RottenHouse", 9605778);
		GOTStructureRegistry.register(id++, GOTStructureSmallStoneRuin.class, "SmallStoneRuin", 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.STONE.class, "StoneRuinStandart", 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.YITI.class, "StoneRuinYiTi", 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.ASSHAI.class, "StoneRuinAsshai", 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.SANDSTONE.class, "StoneRuinSandstone", 9605778);
		GOTStructureRegistry.register(id++, GOTStructureStoneRuin.SOTHORYOS.class, "StoneRuinSothoryos", 9605778);

		GOTStructureRegistry.register(id++, GOTStructureWildlingHouse.class, "WildlingHouse", GOTFaction.WILDLING);
		GOTStructureRegistry.register(id++, GOTStructureWildlingChieftainHouse.class, "WildlingChieftainHouse", GOTFaction.WILDLING);

		GOTStructureRegistry.register(id++, GOTStructureThennHouse.class, "ThennHouse", GOTFaction.WILDLING);
		GOTStructureRegistry.register(id++, GOTStructureThennMagnarHouse.class, "ThennMagnarHouse", GOTFaction.WILDLING);

		GOTStructureRegistry.register(id++, GOTStructureGiftHouse.class, "GiftHouse", GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftLodge.class, "GiftLodge", GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftStables.class, "GiftStables", GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftSmithy.class, "GiftSmithy", GOTFaction.NIGHT_WATCH);
		GOTStructureRegistry.register(id++, GOTStructureGiftVillageLight.class, "GiftVillageLight", GOTFaction.NIGHT_WATCH);

		GOTStructureRegistry.register(id++, GOTStructureNorthHillmanHouse.class, "NorthHillmanHouse", GOTFaction.WILDLING);
		GOTStructureRegistry.register(id++, GOTStructureNorthHillmanChieftainHouse.class, "NorthHillmanChieftainHouse", GOTFaction.WILDLING);

		GOTStructureRegistry.register(id++, GOTStructureNorthWatchfort.class, "NorthWatchfort", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthSmithy.class, "NorthSmithy", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthTower.class, "NorthTurret", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthHouse.class, "NorthHouse", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthCottage.class, "NorthCottage", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthStoneHouse.class, "NorthStoneHouse", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthWatchtower.class, "NorthWatchtower", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthStables.class, "NorthStables", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthBarn.class, "NorthBarn", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthFortress.class, "NorthFortress", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthTavern.class, "NorthTavern", GOTFaction.NORTH);
		GOTStructureRegistry.register(id++, GOTStructureNorthBath.class, "NorthBath", GOTFaction.NORTH);

		GOTStructureRegistry.register(id++, GOTStructureIronbornWatchfort.class, "IronbornWatchfort", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornSmithy.class, "IronbornSmithy", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornTower.class, "IronbornTurret", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornHouse.class, "IronbornHouse", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornCottage.class, "IronbornCottage", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornStoneHouse.class, "IronbornStoneHouse", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornWatchtower.class, "IronbornWatchtower", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornStables.class, "IronbornStables", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornBarn.class, "IronbornBarn", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornFortress.class, "IronbornFortress", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornTavern.class, "IronbornTavern", GOTFaction.IRONBORN);
		GOTStructureRegistry.register(id++, GOTStructureIronbornBath.class, "IronbornBath", GOTFaction.IRONBORN);

		GOTStructureRegistry.register(id++, GOTStructureWesterlandsWatchfort.class, "WesterlandsWatchfort", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsSmithy.class, "WesterlandsSmithy", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsTower.class, "WesterlandsTurret", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsHouse.class, "WesterlandsHouse", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsCottage.class, "WesterlandsCottage", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsStoneHouse.class, "WesterlandsStoneHouse", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsWatchtower.class, "WesterlandsWatchtower", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsStables.class, "WesterlandsStables", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsBarn.class, "WesterlandsBarn", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsFortress.class, "WesterlandsFortress", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsTavern.class, "WesterlandsTavern", GOTFaction.WESTERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureWesterlandsBath.class, "WesterlandsBath", GOTFaction.WESTERLANDS);

		GOTStructureRegistry.register(id++, GOTStructureRiverlandsWatchfort.class, "RiverlandsWatchfort", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsSmithy.class, "RiverlandsSmithy", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsTower.class, "RiverlandsTurret", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsHouse.class, "RiverlandsHouse", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsCottage.class, "RiverlandsCottage", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsStoneHouse.class, "RiverlandsStoneHouse", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsWatchtower.class, "RiverlandsWatchtower", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsStables.class, "RiverlandsStables", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsBarn.class, "RiverlandsBarn", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsFortress.class, "RiverlandsFortress", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsTavern.class, "RiverlandsTavern", GOTFaction.RIVERLANDS);
		GOTStructureRegistry.register(id++, GOTStructureRiverlandsBath.class, "RiverlandsBath", GOTFaction.RIVERLANDS);

		GOTStructureRegistry.register(id++, GOTStructureHillmanHouse.class, "HillmenHouse", GOTFaction.HILL_TRIBES);
		GOTStructureRegistry.register(id++, GOTStructureHillmanTavern.class, "HillmenTavern", GOTFaction.HILL_TRIBES);
		GOTStructureRegistry.register(id++, GOTStructureHillmanFort.class, "HillmenHillFort", GOTFaction.HILL_TRIBES);

		GOTStructureRegistry.register(id++, GOTStructureArrynWatchfort.class, "ArrynWatchfort", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynSmithy.class, "ArrynSmithy", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynTower.class, "ArrynTurret", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynHouse.class, "ArrynHouse", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynCottage.class, "ArrynCottage", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynStoneHouse.class, "ArrynStoneHouse", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynWatchtower.class, "ArrynWatchtower", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynStables.class, "ArrynStables", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynBarn.class, "ArrynBarn", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynFortress.class, "ArrynFortress", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynTavern.class, "ArrynTavern", GOTFaction.ARRYN);
		GOTStructureRegistry.register(id++, GOTStructureArrynBath.class, "ArrynBath", GOTFaction.ARRYN);

		GOTStructureRegistry.register(id++, GOTStructureDragonstoneWatchfort.class, "DragonstoneWatchfort", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneSmithy.class, "DragonstoneSmithy", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneTower.class, "DragonstoneTurret", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneHouse.class, "DragonstoneHouse", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneCottage.class, "DragonstoneCottage", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneStoneHouse.class, "DragonstoneStoneHouse", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneWatchtower.class, "DragonstoneWatchtower", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneStables.class, "DragonstoneStables", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneBarn.class, "DragonstoneBarn", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneFortress.class, "DragonstoneFortress", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneTavern.class, "DragonstoneTavern", GOTFaction.DRAGONSTONE);
		GOTStructureRegistry.register(id++, GOTStructureDragonstoneBath.class, "DragonstoneBath", GOTFaction.DRAGONSTONE);

		GOTStructureRegistry.register(id++, GOTStructureCrownlandsWatchfort.class, "CrownlandsWatchfort", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsSmithy.class, "CrownlandsSmithy", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsTower.class, "CrownlandsTurret", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsHouse.class, "CrownlandsHouse", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsCottage.class, "CrownlandsCottage", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsStoneHouse.class, "CrownlandsStoneHouse", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsWatchtower.class, "CrownlandsWatchtower", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsStables.class, "CrownlandsStables", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsBarn.class, "CrownlandsBarn", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsFortress.class, "CrownlandsFortress", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsTavern.class, "CrownlandsTavern", GOTFaction.CROWNLANDS);
		GOTStructureRegistry.register(id++, GOTStructureCrownlandsBath.class, "CrownlandsBath", GOTFaction.CROWNLANDS);

		GOTStructureRegistry.register(id++, GOTStructureStormlandsWatchfort.class, "StormlandsWatchfort", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsSmithy.class, "StormlandsSmithy", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsTower.class, "StormlandsTurret", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsHouse.class, "StormlandsHouse", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsCottage.class, "StormlandsCottage", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsStoneHouse.class, "StormlandsStoneHouse", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsWatchtower.class, "StormlandsWatchtower", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsStables.class, "StormlandsStables", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsBarn.class, "StormlandsBarn", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsFortress.class, "StormlandsFortress", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsTavern.class, "StormlandsTavern", GOTFaction.STORMLANDS);
		GOTStructureRegistry.register(id++, GOTStructureStormlandsBath.class, "StormlandsBath", GOTFaction.STORMLANDS);

		GOTStructureRegistry.register(id++, GOTStructureReachWine.class, "ReachBrewery", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachWatchfort.class, "ReachWatchfort", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachSmithy.class, "ReachSmithy", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachTower.class, "ReachTurret", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachHouse.class, "ReachHouse", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachCottage.class, "ReachCottage", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachStoneHouse.class, "ReachStoneHouse", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachWatchtower.class, "ReachWatchtower", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachStables.class, "ReachStables", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachBarn.class, "ReachBarn", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachFortress.class, "ReachFortress", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachTavern.class, "ReachTavern", GOTFaction.REACH);
		GOTStructureRegistry.register(id++, GOTStructureReachBath.class, "ReachBath", GOTFaction.REACH);

		GOTStructureRegistry.register(id++, GOTStructureDorneWatchfort.class, "DorneWatchfort", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneSmithy.class, "DorneSmithy", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneTower.class, "DorneTurret", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneHouse.class, "DorneHouse", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneCottage.class, "DorneCottage", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneStoneHouse.class, "DorneStoneHouse", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneWatchtower.class, "DorneWatchtower", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneStables.class, "DorneStables", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneBarn.class, "DorneBarn", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneFortress.class, "DorneFortress", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneTavern.class, "DorneTavern", GOTFaction.DORNE);
		GOTStructureRegistry.register(id++, GOTStructureDorneBath.class, "DorneBath", GOTFaction.DORNE);

		GOTStructureRegistry.register(id++, GOTStructureBraavosBarracks.class, "BraavosBarracks", GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosBazaar.class, "BraavosBazaar", GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosFortress.class, "BraavosFortress", GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosHouse.class, "BraavosHouse", GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosMansion.class, "BraavosMansion", GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosSmithy.class, "BraavosSmithy", GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosStables.class, "BraavosStables", GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosTavern.class, "BraavosTavern", GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosTower.class, "BraavosTower", GOTFaction.BRAAVOS);
		GOTStructureRegistry.register(id++, GOTStructureBraavosTraining.class, "BraavosTraining", GOTFaction.BRAAVOS);

		GOTStructureRegistry.register(id++, GOTStructureVolantisBarracks.class, "VolantisBarracks", GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisBazaar.class, "VolantisBazaar", GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisFortress.class, "VolantisFortress", GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisHouse.class, "VolantisHouse", GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisMansion.class, "VolantisMansion", GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisSmithy.class, "VolantisSmithy", GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisStables.class, "VolantisStables", GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisTavern.class, "VolantisTavern", GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisTower.class, "VolantisTower", GOTFaction.VOLANTIS);
		GOTStructureRegistry.register(id++, GOTStructureVolantisTraining.class, "VolantisTraining", GOTFaction.VOLANTIS);

		GOTStructureRegistry.register(id++, GOTStructurePentosBarracks.class, "PentosBarracks", GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosBazaar.class, "PentosBazaar", GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosFortress.class, "PentosFortress", GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosHouse.class, "PentosHouse", GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosMansion.class, "PentosMansion", GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosSmithy.class, "PentosSmithy", GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosStables.class, "PentosStables", GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosTavern.class, "PentosTavern", GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosTower.class, "PentosTower", GOTFaction.PENTOS);
		GOTStructureRegistry.register(id++, GOTStructurePentosTraining.class, "PentosTraining", GOTFaction.PENTOS);

		GOTStructureRegistry.register(id++, GOTStructureNorvosBarracks.class, "NorvosBarracks", GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosBazaar.class, "NorvosBazaar", GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosFortress.class, "NorvosFortress", GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosHouse.class, "NorvosHouse", GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosMansion.class, "NorvosMansion", GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosSmithy.class, "NorvosSmithy", GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosStables.class, "NorvosStables", GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosTavern.class, "NorvosTavern", GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosTower.class, "NorvosTower", GOTFaction.NORVOS);
		GOTStructureRegistry.register(id++, GOTStructureNorvosTraining.class, "NorvosTraining", GOTFaction.NORVOS);

		GOTStructureRegistry.register(id++, GOTStructureLorathBarracks.class, "LorathBarracks", GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathBazaar.class, "LorathBazaar", GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathFortress.class, "LorathFortress", GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathHouse.class, "LorathHouse", GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathMansion.class, "LorathMansion", GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathSmithy.class, "LorathSmithy", GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathStables.class, "LorathStables", GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathTavern.class, "LorathTavern", GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathTower.class, "LorathTower", GOTFaction.LORATH);
		GOTStructureRegistry.register(id++, GOTStructureLorathTraining.class, "LorathTraining", GOTFaction.LORATH);

		GOTStructureRegistry.register(id++, GOTStructureQohorBarracks.class, "QohorBarracks", GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorBazaar.class, "QohorBazaar", GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorFortress.class, "QohorFortress", GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorHouse.class, "QohorHouse", GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorMansion.class, "QohorMansion", GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorSmithy.class, "QohorSmithy", GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorStables.class, "QohorStables", GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorTavern.class, "QohorTavern", GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorTower.class, "QohorTower", GOTFaction.QOHOR);
		GOTStructureRegistry.register(id++, GOTStructureQohorTraining.class, "QohorTraining", GOTFaction.QOHOR);

		GOTStructureRegistry.register(id++, GOTStructureLysBarracks.class, "LysBarracks", GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysBazaar.class, "LysBazaar", GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysFortress.class, "LysFortress", GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysHouse.class, "LysHouse", GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysMansion.class, "LysMansion", GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysSmithy.class, "LysSmithy", GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysStables.class, "LysStables", GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysTavern.class, "LysTavern", GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysTower.class, "LysTower", GOTFaction.LYS);
		GOTStructureRegistry.register(id++, GOTStructureLysTraining.class, "LysTraining", GOTFaction.LYS);

		GOTStructureRegistry.register(id++, GOTStructureMyrBarracks.class, "MyrBarracks", GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrBazaar.class, "MyrBazaar", GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrFortress.class, "MyrFortress", GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrHouse.class, "MyrHouse", GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrMansion.class, "MyrMansion", GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrSmithy.class, "MyrSmithy", GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrStables.class, "MyrStables", GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrTavern.class, "MyrTavern", GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrTower.class, "MyrTower", GOTFaction.MYR);
		GOTStructureRegistry.register(id++, GOTStructureMyrTraining.class, "MyrTraining", GOTFaction.MYR);

		GOTStructureRegistry.register(id++, GOTStructureGoldenCamp.class, "GoldenCamp", 0xffd700);

		GOTStructureRegistry.register(id++, GOTStructureTyroshBarracks.class, "TyroshBarracks", GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshBazaar.class, "TyroshBazaar", GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshFortress.class, "TyroshFortress", GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshHouse.class, "TyroshHouse", GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshMansion.class, "TyroshMansion", GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshSmithy.class, "TyroshSmithy", GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshStables.class, "TyroshStables", GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshTavern.class, "TyroshTavern", GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshTower.class, "TyroshTower", GOTFaction.TYROSH);
		GOTStructureRegistry.register(id++, GOTStructureTyroshTraining.class, "TyroshTraining", GOTFaction.TYROSH);

		GOTStructureRegistry.register(id++, GOTStructureGhiscarPyramid.class, "GhiscarPyramid", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarCamp.class, "GhiscarCamp", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarBarracks.class, "GhiscarBarracks", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarBazaar.class, "GhiscarBazaar", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarFortress.class, "GhiscarFortress", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarHouse.class, "GhiscarHouse", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarMansion.class, "GhiscarMansion", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarSmithy.class, "GhiscarSmithy", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarStables.class, "GhiscarStables", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarTavern.class, "GhiscarTavern", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarTower.class, "GhiscarTower", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarTraining.class, "GhiscarTraining", GOTFaction.GHISCAR);
		GOTStructureRegistry.register(id++, GOTStructureGhiscarFightingPit.class, "GhiscarFightingPit", GOTFaction.GHISCAR);

		GOTStructureRegistry.register(id++, GOTStructureQarthBarracks.class, "QarthBarracks", GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthBazaar.class, "QarthBazaar", GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthFortress.class, "QarthFortress", GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthHouse.class, "QarthHouse", GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthMansion.class, "QarthMansion", GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthSmithy.class, "QarthSmithy", GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthStables.class, "QarthStables", GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthTavern.class, "QarthTavern", GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthTower.class, "QarthTower", GOTFaction.QARTH);
		GOTStructureRegistry.register(id++, GOTStructureQarthTraining.class, "QarthTraining", GOTFaction.QARTH);

		GOTStructureRegistry.register(id++, GOTStructureLhazarWarCamp.class, "LhazarWarCamp", GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarHouse.class, "LhazarHouse", GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarAltar.class, "LhazarAltar", GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarSmithy.class, "LhazarSmithy", GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarBazaar.class, "LhazarBazaar", GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarTotem.class, "LhazarTotem", GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarPyramid.class, "LhazarPyramid", GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarFarm.class, "LhazarFarm", GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarTower.class, "LhazarTower", GOTFaction.LHAZAR);
		GOTStructureRegistry.register(id++, GOTStructureLhazarTavern.class, "LhazarTavern", GOTFaction.LHAZAR);

		GOTStructureRegistry.register(id++, GOTStructureDothrakiTent.class, "DothrakiTent", GOTFaction.DOTHRAKI);
		GOTStructureRegistry.register(id++, GOTStructureDothrakiTentLarge.class, "DothrakiTentLarge", GOTFaction.DOTHRAKI);
		GOTStructureRegistry.register(id++, GOTStructureDothrakiKhalTent.class, "DothrakiKhalTent", GOTFaction.DOTHRAKI);
		GOTStructureRegistry.register(id++, GOTStructureDothrakiKhalinTent.class, "DothrakiKhalinTent", GOTFaction.DOTHRAKI);

		GOTStructureRegistry.register(id++, GOTStructureIbbenTavern.class, "IbbenTavern", GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenWatchtower.class, "IbbenWatchtower", GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenFortress.class, "IbbenFortress", GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenHouse.class, "IbbenHouse", GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenSmithy.class, "IbbenSmithy", GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenStables.class, "IbbenStables", GOTFaction.IBBEN);
		GOTStructureRegistry.register(id++, GOTStructureIbbenBarn.class, "IbbenBarn", GOTFaction.IBBEN);

		GOTStructureRegistry.register(id++, GOTStructureJogosTent.class, "JogosTent", GOTFaction.JOGOS);
		GOTStructureRegistry.register(id++, GOTStructureJogosTentLarge.class, "JogosTentLarge", GOTFaction.JOGOS);
		GOTStructureRegistry.register(id++, GOTStructureJogosChiefTent.class, "JogosChiefTent", GOTFaction.JOGOS);
		GOTStructureRegistry.register(id++, GOTStructureJogosMarketTent.class, "JogosShamanTent", GOTFaction.JOGOS);

		GOTStructureRegistry.register(id++, GOTStructureMossovyBarn.class, "MossovyBarn", GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyCastle.class, "MossovyCastle", GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyHouse.class, "MossovyHouse", GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyInn.class, "MossovyInn", GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyOffice.class, "MossovyOffice", GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyThiefHouse.class, "MossovyThiefHouse", GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovySmithy.class, "MossovySmithy", GOTFaction.MOSSOVY);
		GOTStructureRegistry.register(id++, GOTStructureMossovyStable.class, "MossovyStable", GOTFaction.MOSSOVY);

		GOTStructureRegistry.register(id++, GOTStructureYiTiHouse.class, "YiTiHouse", GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiStables.class, "YiTiStables", GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTownHouse.class, "YiTiTownHouse", GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiLargeTownHouse.class, "YiTiLargeTownHouse", GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiFortress.class, "YiTiFortress", GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTower.class, "YiTiTower", GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiSmithy.class, "YiTiSmithy", GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTavern.class, "YiTiTavern", GOTFaction.YI_TI);
		GOTStructureRegistry.register(id++, GOTStructureYiTiTavernTown.class, "YiTiTavernTown", GOTFaction.YI_TI);

		GOTStructureRegistry.register(id++, GOTStructureAsshaiTower.class, "AsshaiTower", GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiFort.class, "AsshaiFort", GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiLaboratory.class, "AsshaiLaboratory", GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiHouse.class, "AsshaiHouse", GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiTavern.class, "AsshaiTavern", GOTFaction.ASSHAI);
		GOTStructureRegistry.register(id++, GOTStructureAsshaiOblivionAltar.class, "AsshaiOblivionAltar", GOTFaction.ASSHAI);

		GOTStructureRegistry.register(id++, GOTStructureSummerHouse.class, "SummerHouse", GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerSmithy.class, "SummerSmithy", GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerTavern.class, "SummerTavern", GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerTower.class, "SummerTower", GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerFort.class, "SummerFort", GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerStables.class, "SummerStables", GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerHouseRuined.class, "SummerHouseRuined", GOTFaction.SUMMER_ISLANDS);
		GOTStructureRegistry.register(id++, GOTStructureSummerTavernRuined.class, "SummerTavernRuined", GOTFaction.SUMMER_ISLANDS);

		GOTStructureRegistry.register(id++, GOTStructureSothoryosPyramid.class, "SothoryosPyramid", GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosHouseSimple.class, "SothoryosHouseSimple", GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosHouseStilts.class, "SothoryosHouseStilts", GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosWatchtower.class, "SothoryosWatchtower", GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosHouseLarge.class, "SothoryosHouseLarge", GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosChieftainPyramid.class, "SothoryosChieftainPyramid", GOTFaction.SOTHORYOS);
		GOTStructureRegistry.register(id++, GOTStructureSothoryosSmithy.class, "SothoryosSmithy", GOTFaction.SOTHORYOS);

		GOTStructureRegistry.register(id++, new GOTStructureWildlingVillage(GOTBiome.hauntedForest, 1.0f), "WildlingVillage", GOTFaction.WILDLING, new GOTStructureRegistry.IVillageProperties<GOTStructureWildlingVillage.Instance>() {

			@Override
			public void apply(GOTStructureWildlingVillage.Instance instance) {
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureThennVillage(GOTBiome.thenn, 1.0f), "ThennVillage", GOTFaction.WILDLING, new GOTStructureRegistry.IVillageProperties<GOTStructureThennVillage.Instance>() {

			@Override
			public void apply(GOTStructureThennVillage.Instance instance) {
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureGiftVillage(GOTBiome.giftNew, 1.0f), "GiftVillage", GOTFaction.NIGHT_WATCH, new GOTStructureRegistry.IVillageProperties<GOTStructureGiftVillage.Instance>() {

			@Override
			public void apply(GOTStructureGiftVillage.Instance instance) {
			}

		});

		GOTStructureRegistry.register(id++, new GOTStructureNorthHillmanVillage(GOTBiome.skagos, 1.0f), "NorthHillmanVillage", GOTFaction.NORTH, new GOTStructureRegistry.IVillageProperties<GOTStructureNorthHillmanVillage.Instance>() {

			@Override
			public void apply(GOTStructureNorthHillmanVillage.Instance instance) {
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureNorthCity(GOTBiome.north, 1.0f), "NorthVillage", GOTFaction.NORTH, new GOTStructureRegistry.IVillageProperties<GOTStructureNorthCity.Instance>() {

			@Override
			public void apply(GOTStructureNorthCity.Instance instance) {
				instance.villageType = GOTStructureNorthCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureNorthCity(GOTBiome.north, 1.0f), "NorthTown", GOTFaction.NORTH, new GOTStructureRegistry.IVillageProperties<GOTStructureNorthCity.Instance>() {

			@Override
			public void apply(GOTStructureNorthCity.Instance instance) {
				instance.villageType = GOTStructureNorthCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureNorthCity(GOTBiome.north, 1.0f), "NorthFortVillage", GOTFaction.NORTH, new GOTStructureRegistry.IVillageProperties<GOTStructureNorthCity.Instance>() {

			@Override
			public void apply(GOTStructureNorthCity.Instance instance) {
				instance.villageType = GOTStructureNorthCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureIronbornCity(GOTBiome.ironborn, 1.0f), "IronbornCityLittle", GOTFaction.IRONBORN, new GOTStructureRegistry.IVillageProperties<GOTStructureIronbornCity.Instance>() {

			@Override
			public void apply(GOTStructureIronbornCity.Instance instance) {
				instance.villageType = GOTStructureIronbornCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureIronbornCity(GOTBiome.ironborn, 1.0f), "IronbornCityBig", GOTFaction.IRONBORN, new GOTStructureRegistry.IVillageProperties<GOTStructureIronbornCity.Instance>() {

			@Override
			public void apply(GOTStructureIronbornCity.Instance instance) {
				instance.villageType = GOTStructureIronbornCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureIronbornCity(GOTBiome.ironborn, 1.0f), "IronbornCityMedium", GOTFaction.IRONBORN, new GOTStructureRegistry.IVillageProperties<GOTStructureIronbornCity.Instance>() {

			@Override
			public void apply(GOTStructureIronbornCity.Instance instance) {
				instance.villageType = GOTStructureIronbornCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsCity(GOTBiome.westerlands, 1.0f), "WesterlandsCityLittle", GOTFaction.WESTERLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureWesterlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureWesterlandsCity.Instance instance) {
				instance.villageType = GOTStructureWesterlandsCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsCity(GOTBiome.westerlands, 1.0f), "WesterlandsCityBig", GOTFaction.WESTERLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureWesterlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureWesterlandsCity.Instance instance) {
				instance.villageType = GOTStructureWesterlandsCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureWesterlandsCity(GOTBiome.westerlands, 1.0f), "WesterlandsCityMedium", GOTFaction.WESTERLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureWesterlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureWesterlandsCity.Instance instance) {
				instance.villageType = GOTStructureWesterlandsCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsCity(GOTBiome.riverlands, 1.0f), "RiverlandsCityLittle", GOTFaction.RIVERLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureRiverlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureRiverlandsCity.Instance instance) {
				instance.villageType = GOTStructureRiverlandsCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsCity(GOTBiome.riverlands, 1.0f), "RiverlandsCityBig", GOTFaction.RIVERLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureRiverlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureRiverlandsCity.Instance instance) {
				instance.villageType = GOTStructureRiverlandsCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureRiverlandsCity(GOTBiome.riverlands, 1.0f), "RiverlandsCityMedium", GOTFaction.RIVERLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureRiverlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureRiverlandsCity.Instance instance) {
				instance.villageType = GOTStructureRiverlandsCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureHillmanVillage(GOTBiome.sothoryosJungle, 1.0f), "HillmanVillage", GOTFaction.HILL_TRIBES, new GOTStructureRegistry.IVillageProperties<GOTStructureHillmanVillage.Instance>() {

			@Override
			public void apply(GOTStructureHillmanVillage.Instance instance) {
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureArrynCity(GOTBiome.arryn, 1.0f), "ArrynCityLittle", GOTFaction.ARRYN, new GOTStructureRegistry.IVillageProperties<GOTStructureArrynCity.Instance>() {

			@Override
			public void apply(GOTStructureArrynCity.Instance instance) {
				instance.villageType = GOTStructureArrynCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureArrynCity(GOTBiome.arryn, 1.0f), "ArrynCityBig", GOTFaction.ARRYN, new GOTStructureRegistry.IVillageProperties<GOTStructureArrynCity.Instance>() {

			@Override
			public void apply(GOTStructureArrynCity.Instance instance) {
				instance.villageType = GOTStructureArrynCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureArrynCity(GOTBiome.arryn, 1.0f), "ArrynCityMedium", GOTFaction.ARRYN, new GOTStructureRegistry.IVillageProperties<GOTStructureArrynCity.Instance>() {

			@Override
			public void apply(GOTStructureArrynCity.Instance instance) {
				instance.villageType = GOTStructureArrynCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneCity(GOTBiome.dragonstone, 1.0f), "DragonstoneCityLittle", GOTFaction.DRAGONSTONE, new GOTStructureRegistry.IVillageProperties<GOTStructureDragonstoneCity.Instance>() {

			@Override
			public void apply(GOTStructureDragonstoneCity.Instance instance) {
				instance.villageType = GOTStructureDragonstoneCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneCity(GOTBiome.dragonstone, 1.0f), "DragonstoneCityBig", GOTFaction.DRAGONSTONE, new GOTStructureRegistry.IVillageProperties<GOTStructureDragonstoneCity.Instance>() {

			@Override
			public void apply(GOTStructureDragonstoneCity.Instance instance) {
				instance.villageType = GOTStructureDragonstoneCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureDragonstoneCity(GOTBiome.dragonstone, 1.0f), "DragonstoneCityMedium", GOTFaction.DRAGONSTONE, new GOTStructureRegistry.IVillageProperties<GOTStructureDragonstoneCity.Instance>() {

			@Override
			public void apply(GOTStructureDragonstoneCity.Instance instance) {
				instance.villageType = GOTStructureDragonstoneCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsCity(GOTBiome.crownlands, 1.0f), "CrownlandsCityLittle", GOTFaction.CROWNLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureCrownlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureCrownlandsCity.Instance instance) {
				instance.villageType = GOTStructureCrownlandsCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsCity(GOTBiome.crownlands, 1.0f), "CrownlandsCityBig", GOTFaction.CROWNLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureCrownlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureCrownlandsCity.Instance instance) {
				instance.villageType = GOTStructureCrownlandsCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureCrownlandsCity(GOTBiome.crownlands, 1.0f), "CrownlandsCityMedium", GOTFaction.CROWNLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureCrownlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureCrownlandsCity.Instance instance) {
				instance.villageType = GOTStructureCrownlandsCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureStormlandsCity(GOTBiome.stormlands, 1.0f), "StormlandsCityLittle", GOTFaction.STORMLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureStormlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureStormlandsCity.Instance instance) {
				instance.villageType = GOTStructureStormlandsCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureStormlandsCity(GOTBiome.stormlands, 1.0f), "StormlandsCityBig", GOTFaction.STORMLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureStormlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureStormlandsCity.Instance instance) {
				instance.villageType = GOTStructureStormlandsCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureStormlandsCity(GOTBiome.stormlands, 1.0f), "StormlandsCityMedium", GOTFaction.STORMLANDS, new GOTStructureRegistry.IVillageProperties<GOTStructureStormlandsCity.Instance>() {

			@Override
			public void apply(GOTStructureStormlandsCity.Instance instance) {
				instance.villageType = GOTStructureStormlandsCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureReachCity(GOTBiome.reach, 1.0f), "ReachCityLittle", GOTFaction.REACH, new GOTStructureRegistry.IVillageProperties<GOTStructureReachCity.Instance>() {

			@Override
			public void apply(GOTStructureReachCity.Instance instance) {
				instance.villageType = GOTStructureReachCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureReachCity(GOTBiome.reach, 1.0f), "ReachCityBig", GOTFaction.REACH, new GOTStructureRegistry.IVillageProperties<GOTStructureReachCity.Instance>() {

			@Override
			public void apply(GOTStructureReachCity.Instance instance) {
				instance.villageType = GOTStructureReachCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureReachCity(GOTBiome.reach, 1.0f), "ReachCityMedium", GOTFaction.REACH, new GOTStructureRegistry.IVillageProperties<GOTStructureReachCity.Instance>() {

			@Override
			public void apply(GOTStructureReachCity.Instance instance) {
				instance.villageType = GOTStructureReachCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureDorneCity(GOTBiome.dorne, 1.0f), "DorneCityLittle", GOTFaction.DORNE, new GOTStructureRegistry.IVillageProperties<GOTStructureDorneCity.Instance>() {

			@Override
			public void apply(GOTStructureDorneCity.Instance instance) {
				instance.villageType = GOTStructureDorneCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureDorneCity(GOTBiome.dorne, 1.0f), "DorneCityBig", GOTFaction.DORNE, new GOTStructureRegistry.IVillageProperties<GOTStructureDorneCity.Instance>() {

			@Override
			public void apply(GOTStructureDorneCity.Instance instance) {
				instance.villageType = GOTStructureDorneCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureDorneCity(GOTBiome.dorne, 1.0f), "DorneCityMedium", GOTFaction.DORNE, new GOTStructureRegistry.IVillageProperties<GOTStructureDorneCity.Instance>() {

			@Override
			public void apply(GOTStructureDorneCity.Instance instance) {
				instance.villageType = GOTStructureDorneCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureBraavosCity(GOTBiome.braavos, 1.0f), "BraavosCityLittle", GOTFaction.BRAAVOS, new GOTStructureRegistry.IVillageProperties<GOTStructureBraavosCity.Instance>() {

			@Override
			public void apply(GOTStructureBraavosCity.Instance instance) {
				instance.villageType = GOTStructureBraavosCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureBraavosCity(GOTBiome.braavos, 1.0f), "BraavosCityMedium", GOTFaction.BRAAVOS, new GOTStructureRegistry.IVillageProperties<GOTStructureBraavosCity.Instance>() {

			@Override
			public void apply(GOTStructureBraavosCity.Instance instance) {
				instance.villageType = GOTStructureBraavosCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureBraavosCity(GOTBiome.braavos, 1.0f), "BraavosCityBig", GOTFaction.BRAAVOS, new GOTStructureRegistry.IVillageProperties<GOTStructureBraavosCity.Instance>() {

			@Override
			public void apply(GOTStructureBraavosCity.Instance instance) {
				instance.villageType = GOTStructureBraavosCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureVolantisCity(GOTBiome.volantis, 1.0f), "VolantisCityLittle", GOTFaction.VOLANTIS, new GOTStructureRegistry.IVillageProperties<GOTStructureVolantisCity.Instance>() {

			@Override
			public void apply(GOTStructureVolantisCity.Instance instance) {
				instance.villageType = GOTStructureVolantisCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureVolantisCity(GOTBiome.volantis, 1.0f), "VolantisCityMedium", GOTFaction.VOLANTIS, new GOTStructureRegistry.IVillageProperties<GOTStructureVolantisCity.Instance>() {

			@Override
			public void apply(GOTStructureVolantisCity.Instance instance) {
				instance.villageType = GOTStructureVolantisCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureVolantisCity(GOTBiome.volantis, 1.0f), "VolantisCityBig", GOTFaction.VOLANTIS, new GOTStructureRegistry.IVillageProperties<GOTStructureVolantisCity.Instance>() {

			@Override
			public void apply(GOTStructureVolantisCity.Instance instance) {
				instance.villageType = GOTStructureVolantisCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructurePentosCity(GOTBiome.pentos, 1.0f), "PentosCityLittle", GOTFaction.PENTOS, new GOTStructureRegistry.IVillageProperties<GOTStructurePentosCity.Instance>() {

			@Override
			public void apply(GOTStructurePentosCity.Instance instance) {
				instance.villageType = GOTStructurePentosCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructurePentosCity(GOTBiome.pentos, 1.0f), "PentosCityMedium", GOTFaction.PENTOS, new GOTStructureRegistry.IVillageProperties<GOTStructurePentosCity.Instance>() {

			@Override
			public void apply(GOTStructurePentosCity.Instance instance) {
				instance.villageType = GOTStructurePentosCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructurePentosCity(GOTBiome.pentos, 1.0f), "PentosCityBig", GOTFaction.PENTOS, new GOTStructureRegistry.IVillageProperties<GOTStructurePentosCity.Instance>() {

			@Override
			public void apply(GOTStructurePentosCity.Instance instance) {
				instance.villageType = GOTStructurePentosCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureNorvosCity(GOTBiome.norvos, 1.0f), "NorvosCityLittle", GOTFaction.NORVOS, new GOTStructureRegistry.IVillageProperties<GOTStructureNorvosCity.Instance>() {

			@Override
			public void apply(GOTStructureNorvosCity.Instance instance) {
				instance.villageType = GOTStructureNorvosCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureNorvosCity(GOTBiome.norvos, 1.0f), "NorvosCityMedium", GOTFaction.NORVOS, new GOTStructureRegistry.IVillageProperties<GOTStructureNorvosCity.Instance>() {

			@Override
			public void apply(GOTStructureNorvosCity.Instance instance) {
				instance.villageType = GOTStructureNorvosCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureNorvosCity(GOTBiome.norvos, 1.0f), "NorvosCityBig", GOTFaction.NORVOS, new GOTStructureRegistry.IVillageProperties<GOTStructureNorvosCity.Instance>() {

			@Override
			public void apply(GOTStructureNorvosCity.Instance instance) {
				instance.villageType = GOTStructureNorvosCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureLorathCity(GOTBiome.lorath, 1.0f), "LorathCityLittle", GOTFaction.LORATH, new GOTStructureRegistry.IVillageProperties<GOTStructureLorathCity.Instance>() {

			@Override
			public void apply(GOTStructureLorathCity.Instance instance) {
				instance.villageType = GOTStructureLorathCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureLorathCity(GOTBiome.lorath, 1.0f), "LorathCityMedium", GOTFaction.LORATH, new GOTStructureRegistry.IVillageProperties<GOTStructureLorathCity.Instance>() {

			@Override
			public void apply(GOTStructureLorathCity.Instance instance) {
				instance.villageType = GOTStructureLorathCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureLorathCity(GOTBiome.lorath, 1.0f), "LorathCityBig", GOTFaction.LORATH, new GOTStructureRegistry.IVillageProperties<GOTStructureLorathCity.Instance>() {

			@Override
			public void apply(GOTStructureLorathCity.Instance instance) {
				instance.villageType = GOTStructureLorathCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureQohorCity(GOTBiome.qohor, 1.0f), "QohorCityLittle", GOTFaction.QOHOR, new GOTStructureRegistry.IVillageProperties<GOTStructureQohorCity.Instance>() {

			@Override
			public void apply(GOTStructureQohorCity.Instance instance) {
				instance.villageType = GOTStructureQohorCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureQohorCity(GOTBiome.qohor, 1.0f), "QohorCityMedium", GOTFaction.QOHOR, new GOTStructureRegistry.IVillageProperties<GOTStructureQohorCity.Instance>() {

			@Override
			public void apply(GOTStructureQohorCity.Instance instance) {
				instance.villageType = GOTStructureQohorCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureQohorCity(GOTBiome.qohor, 1.0f), "QohorCityBig", GOTFaction.QOHOR, new GOTStructureRegistry.IVillageProperties<GOTStructureQohorCity.Instance>() {

			@Override
			public void apply(GOTStructureQohorCity.Instance instance) {
				instance.villageType = GOTStructureQohorCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureLysCity(GOTBiome.lys, 1.0f), "LysCityLittle", GOTFaction.LYS, new GOTStructureRegistry.IVillageProperties<GOTStructureLysCity.Instance>() {

			@Override
			public void apply(GOTStructureLysCity.Instance instance) {
				instance.villageType = GOTStructureLysCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureLysCity(GOTBiome.lys, 1.0f), "LysCityMedium", GOTFaction.LYS, new GOTStructureRegistry.IVillageProperties<GOTStructureLysCity.Instance>() {

			@Override
			public void apply(GOTStructureLysCity.Instance instance) {
				instance.villageType = GOTStructureLysCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureLysCity(GOTBiome.lys, 1.0f), "LysCityBig", GOTFaction.LYS, new GOTStructureRegistry.IVillageProperties<GOTStructureLysCity.Instance>() {

			@Override
			public void apply(GOTStructureLysCity.Instance instance) {
				instance.villageType = GOTStructureLysCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureMyrCity(GOTBiome.myr, 1.0f), "MyrCityLittle", GOTFaction.MYR, new GOTStructureRegistry.IVillageProperties<GOTStructureMyrCity.Instance>() {

			@Override
			public void apply(GOTStructureMyrCity.Instance instance) {
				instance.villageType = GOTStructureMyrCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureMyrCity(GOTBiome.myr, 1.0f), "MyrCityMedium", GOTFaction.MYR, new GOTStructureRegistry.IVillageProperties<GOTStructureMyrCity.Instance>() {

			@Override
			public void apply(GOTStructureMyrCity.Instance instance) {
				instance.villageType = GOTStructureMyrCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureMyrCity(GOTBiome.myr, 1.0f), "MyrCityBig", GOTFaction.MYR, new GOTStructureRegistry.IVillageProperties<GOTStructureMyrCity.Instance>() {

			@Override
			public void apply(GOTStructureMyrCity.Instance instance) {
				instance.villageType = GOTStructureMyrCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureTyroshCity(GOTBiome.tyrosh, 1.0f), "TyroshCityLittle", GOTFaction.TYROSH, new GOTStructureRegistry.IVillageProperties<GOTStructureTyroshCity.Instance>() {

			@Override
			public void apply(GOTStructureTyroshCity.Instance instance) {
				instance.villageType = GOTStructureTyroshCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureTyroshCity(GOTBiome.tyrosh, 1.0f), "TyroshCityMedium", GOTFaction.TYROSH, new GOTStructureRegistry.IVillageProperties<GOTStructureTyroshCity.Instance>() {

			@Override
			public void apply(GOTStructureTyroshCity.Instance instance) {
				instance.villageType = GOTStructureTyroshCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureTyroshCity(GOTBiome.tyrosh, 1.0f), "TyroshCityBig", GOTFaction.TYROSH, new GOTStructureRegistry.IVillageProperties<GOTStructureTyroshCity.Instance>() {

			@Override
			public void apply(GOTStructureTyroshCity.Instance instance) {
				instance.villageType = GOTStructureTyroshCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureGhiscarCity(GOTBiome.ghiscar, 1.0f), "GhiscarCityLittle", GOTFaction.GHISCAR, new GOTStructureRegistry.IVillageProperties<GOTStructureGhiscarCity.Instance>() {

			@Override
			public void apply(GOTStructureGhiscarCity.Instance instance) {
				instance.villageType = GOTStructureGhiscarCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureGhiscarCity(GOTBiome.ghiscar, 1.0f), "GhiscarCityMedium", GOTFaction.GHISCAR, new GOTStructureRegistry.IVillageProperties<GOTStructureGhiscarCity.Instance>() {

			@Override
			public void apply(GOTStructureGhiscarCity.Instance instance) {
				instance.villageType = GOTStructureGhiscarCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureGhiscarCity(GOTBiome.ghiscar, 1.0f), "GhiscarCityBig", GOTFaction.GHISCAR, new GOTStructureRegistry.IVillageProperties<GOTStructureGhiscarCity.Instance>() {

			@Override
			public void apply(GOTStructureGhiscarCity.Instance instance) {
				instance.villageType = GOTStructureGhiscarCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureQarthCity(GOTBiome.qarth, 1.0f), "QarthCityLittle", GOTFaction.QARTH, new GOTStructureRegistry.IVillageProperties<GOTStructureQarthCity.Instance>() {

			@Override
			public void apply(GOTStructureQarthCity.Instance instance) {
				instance.villageType = GOTStructureQarthCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureQarthCity(GOTBiome.qarth, 1.0f), "QarthCityMedium", GOTFaction.QARTH, new GOTStructureRegistry.IVillageProperties<GOTStructureQarthCity.Instance>() {

			@Override
			public void apply(GOTStructureQarthCity.Instance instance) {
				instance.villageType = GOTStructureQarthCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureQarthCity(GOTBiome.qarth, 1.0f), "QarthCityBig", GOTFaction.QARTH, new GOTStructureRegistry.IVillageProperties<GOTStructureQarthCity.Instance>() {

			@Override
			public void apply(GOTStructureQarthCity.Instance instance) {
				instance.villageType = GOTStructureQarthCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureLhazarVillage(GOTBiome.lhazar, 1.0f), "LhazarCityLittle", GOTFaction.LHAZAR, new IVillageProperties<GOTStructureLhazarVillage.Instance>() {

			@Override
			public void apply(GOTStructureLhazarVillage.Instance instance) {
				instance.villageType = GOTStructureLhazarVillage.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureLhazarVillage(GOTBiome.lhazar, 1.0f), "LhazarCityMedium", GOTFaction.LHAZAR, new IVillageProperties<GOTStructureLhazarVillage.Instance>() {

			@Override
			public void apply(GOTStructureLhazarVillage.Instance instance) {
				instance.villageType = GOTStructureLhazarVillage.VillageType.FORT;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureLhazarVillage(GOTBiome.lhazar, 1.0f), "LhazarCityBig", GOTFaction.LHAZAR, new IVillageProperties<GOTStructureLhazarVillage.Instance>() {

			@Override
			public void apply(GOTStructureLhazarVillage.Instance instance) {
				instance.villageType = GOTStructureLhazarVillage.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureDothrakiVillage(GOTBiome.dothrakiSea, 1.0f), "DothrakiVillageLittle", GOTFaction.DOTHRAKI, new GOTStructureRegistry.IVillageProperties<GOTStructureDothrakiVillage.Instance>() {

			@Override
			public void apply(GOTStructureDothrakiVillage.Instance instance) {
				instance.villageType = GOTStructureDothrakiVillage.VillageType.SMALL;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureDothrakiVillage(GOTBiome.dothrakiSea, 1.0f), "DothrakiVillageBig", GOTFaction.DOTHRAKI, new GOTStructureRegistry.IVillageProperties<GOTStructureDothrakiVillage.Instance>() {

			@Override
			public void apply(GOTStructureDothrakiVillage.Instance instance) {
				instance.villageType = GOTStructureDothrakiVillage.VillageType.BIG;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureIbbenVillage(GOTBiome.ibben, 1.0f), "IbbenVillage", GOTFaction.IBBEN, new IVillageProperties<GOTStructureIbbenVillage.Instance>() {

			@Override
			public void apply(GOTStructureIbbenVillage.Instance instance) {
				instance.villageType = GOTStructureIbbenVillage.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureIbbenVillage(GOTBiome.ibben, 1.0f), "IbbenFortVillage", GOTFaction.IBBEN, new IVillageProperties<GOTStructureIbbenVillage.Instance>() {

			@Override
			public void apply(GOTStructureIbbenVillage.Instance instance) {
				instance.villageType = GOTStructureIbbenVillage.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureJogosVillage(GOTBiome.jogosNhai, 1.0f), "JogosVillageLittle", GOTFaction.JOGOS, new GOTStructureRegistry.IVillageProperties<GOTStructureJogosVillage.Instance>() {

			@Override
			public void apply(GOTStructureJogosVillage.Instance instance) {
				instance.villageType = GOTStructureJogosVillage.VillageType.SMALL;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureJogosVillage(GOTBiome.jogosNhai, 1.0f), "JogosVillageBig", GOTFaction.JOGOS, new GOTStructureRegistry.IVillageProperties<GOTStructureJogosVillage.Instance>() {

			@Override
			public void apply(GOTStructureJogosVillage.Instance instance) {
				instance.villageType = GOTStructureJogosVillage.VillageType.BIG;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureMossovyVillage(GOTBiome.mossovy, 1.0f), "MossovyVillage", GOTFaction.MOSSOVY, new GOTStructureRegistry.IVillageProperties<GOTStructureMossovyVillage.Instance>() {

			@Override
			public void apply(GOTStructureMossovyVillage.Instance instance) {
			}

		});

		GOTStructureRegistry.register(id++, new GOTStructureYiTiCity(GOTBiome.yiTi, 1.0f), "YiTiVillage", GOTFaction.YI_TI, new GOTStructureRegistry.IVillageProperties<GOTStructureYiTiCity.Instance>() {

			@Override
			public void apply(GOTStructureYiTiCity.Instance instance) {
				instance.villageType = GOTStructureYiTiCity.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureYiTiCity(GOTBiome.yiTi, 1.0f), "YiTiTown", GOTFaction.YI_TI, new GOTStructureRegistry.IVillageProperties<GOTStructureYiTiCity.Instance>() {

			@Override
			public void apply(GOTStructureYiTiCity.Instance instance) {
				instance.villageType = GOTStructureYiTiCity.VillageType.TOWN;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureYiTiCity(GOTBiome.yiTi, 1.0f), "YiTiFortVillage", GOTFaction.YI_TI, new GOTStructureRegistry.IVillageProperties<GOTStructureYiTiCity.Instance>() {

			@Override
			public void apply(GOTStructureYiTiCity.Instance instance) {
				instance.villageType = GOTStructureYiTiCity.VillageType.FORT;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureAsshaiCity(GOTBiome.shadowTown, 1.0f), "AsshaiCity", GOTFaction.ASSHAI, new GOTStructureRegistry.IVillageProperties<GOTStructureAsshaiCity.Instance>() {

			@Override
			public void apply(GOTStructureAsshaiCity.Instance instance) {
				instance.villageType = GOTStructureAsshaiCity.VillageType.TOWN;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureSummerVillage(GOTBiome.summerIslands, 1.0f), "SummerVillage", GOTFaction.SUMMER_ISLANDS, new IVillageProperties<GOTStructureSummerVillage.Instance>() {

			@Override
			public void apply(GOTStructureSummerVillage.Instance instance) {
				instance.villageType = GOTStructureSummerVillage.VillageType.VILLAGE;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureSummerVillage(GOTBiome.summerIslands, 1.0f), "SummerFortVillage", GOTFaction.SUMMER_ISLANDS, new IVillageProperties<GOTStructureSummerVillage.Instance>() {

			@Override
			public void apply(GOTStructureSummerVillage.Instance instance) {
				instance.villageType = GOTStructureSummerVillage.VillageType.FORTRESS;
			}
		});
		GOTStructureRegistry.register(id++, new GOTStructureSummerVillage(GOTBiome.summerIslands, 1.0f).setRuined(), "SummerVillageRuined", GOTFaction.SUMMER_ISLANDS, new IVillageProperties<GOTStructureSummerVillage.Instance>() {

			@Override
			public void apply(GOTStructureSummerVillage.Instance instance) {
				instance.villageType = GOTStructureSummerVillage.VillageType.VILLAGE;
			}
		});

		GOTStructureRegistry.register(id++, new GOTStructureSothoryosVillage(GOTBiome.sothoryosJungle, 1.0f), "SothoryosVillage", GOTFaction.SOTHORYOS, new GOTStructureRegistry.IVillageProperties<GOTStructureSothoryosVillage.Instance>() {

			@Override
			public void apply(GOTStructureSothoryosVillage.Instance instance) {
			}
		});
		
		if (GOT.isDevMode) {
			GOTStructureRegistry.register(id++, DatabaseGenerator.class, "DatabaseGenerator", 9605778);
		}
		
		GOTStructureSothoryosPyramidMapgen.register();
		GOTStructureGhiscarPyramidMapgen.register();
	}
}