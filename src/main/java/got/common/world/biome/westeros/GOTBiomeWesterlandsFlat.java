package got.common.world.biome.westeros;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.westerlands.GOTStructureWesterlandsCity;

public class GOTBiomeWesterlandsFlat extends GOTBiomeWesterlands {
	public GOTBiomeWesterlandsFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] container2 = new SpawnListContainer[1];
		container2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container2);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		SpawnListContainer[] container4 = new SpawnListContainer[1];
		container4[0] = GOTBiomeSpawnList.entry(GOTSpawnList.RIVERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container4);
		SpawnListContainer[] container5 = new SpawnListContainer[1];
		container5[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container5);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureWesterlandsCity village = new GOTStructureWesterlandsCity(this, 0.0f);
		village.affix(GOTWaypoint.Oxcross);
		decorator.affix(village);
		GOTStructureWesterlandsCity castle = new GOTStructureWesterlandsCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Wyndhall);
		castle.affix(GOTWaypoint.Banefort);
		castle.affix(GOTWaypoint.Plumwood);
		castle.affix(GOTWaypoint.Crag);
		castle.affix(GOTWaypoint.Ashemark);
		castle.affix(GOTWaypoint.GoldenTooth, 0, 1);
		castle.affix(GOTWaypoint.Sarsfield, 0, -1);
		castle.affix(GOTWaypoint.Hornvale);
		castle.affix(GOTWaypoint.DeepDen);
		castle.affix(GOTWaypoint.Riverspring);
		castle.affix(GOTWaypoint.Silverhill);
		castle.affix(GOTWaypoint.Greenfield);
		castle.affix(GOTWaypoint.Cornfield);
		castle.affix(GOTWaypoint.Crakehall, -1, 0);
		castle.affix(GOTWaypoint.Faircastle);
		castle.affix(GOTWaypoint.Feastfires);
		castle.affix(GOTWaypoint.Kayce, 1, 0);
		castle.affix(GOTWaypoint.CleganesKeep);
		castle.affix(GOTWaypoint.CasterlyRock, -1, 0);
		decorator.affix(castle);
		GOTStructureWesterlandsCity town = new GOTStructureWesterlandsCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Kayce, 3);
		town.affix(GOTWaypoint.Lannisport, -1, 0, 3);
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