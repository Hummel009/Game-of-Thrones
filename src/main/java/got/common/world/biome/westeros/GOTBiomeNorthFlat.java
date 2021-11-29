package got.common.world.biome.westeros;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.north.GOTStructureNorthCity;

public class GOTBiomeNorthFlat extends GOTBiomeNorth {
	public GOTBiomeNorthFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] container2 = new SpawnListContainer[1];
		container2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container2);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		SpawnListContainer[] container4 = new SpawnListContainer[2];
		container4[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		container4[1] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container4);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureNorthCity castle = new GOTStructureNorthCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.MormontsKeep);
		castle.affix(GOTWaypoint.DeepwoodMotte);
		castle.affix(GOTWaypoint.Karhold);
		castle.affix(GOTWaypoint.Winterfell, 1);
		castle.affix(GOTWaypoint.LastHearth);
		castle.affix(GOTWaypoint.Dreadfort);
		castle.affix(GOTWaypoint.DeepwoodMotte);
		castle.affix(GOTWaypoint.Hornwood);
		castle.affix(GOTWaypoint.BlackPool);
		castle.affix(GOTWaypoint.RamsGate);
		castle.affix(GOTWaypoint.WidowsWatch);
		castle.affix(GOTWaypoint.OldCastle);
		castle.affix(GOTWaypoint.ServinsCastle, -1, 0);
		castle.affix(GOTWaypoint.Ironrath);
		castle.affix(GOTWaypoint.Highpoint);
		castle.affix(GOTWaypoint.TorhensSquare);
		castle.affix(GOTWaypoint.RisvellsCastle, 0, 1);
		castle.affix(GOTWaypoint.RillwaterCrossing);
		castle.affix(GOTWaypoint.FlintsFinger);
		decorator.affix(castle);
		GOTStructureNorthCity town = new GOTStructureNorthCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.WhiteHarbour, 1);
		decorator.affix(town);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public boolean hasDomesticAnimals() {
		return true;
	}
}