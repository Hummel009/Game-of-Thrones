package got.common.world.biome.westeros;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.dragonstone.GOTStructureDragonstoneCity;

public class GOTBiomeDragonstoneFlat extends GOTBiomeDragonstone {
	public GOTBiomeDragonstoneFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] container2 = new SpawnListContainer[1];
		container2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container2);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.REACH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureDragonstoneCity castle = new GOTStructureDragonstoneCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Dragonstone);
		castle.affix(GOTWaypoint.HighTide);
		castle.affix(GOTWaypoint.Driftmark);
		castle.affix(GOTWaypoint.SharpPoint);
		castle.affix(GOTWaypoint.Stonedance);
		castle.affix(GOTWaypoint.SweetportSound);
		castle.affix(GOTWaypoint.ClawIsle);
		decorator.affix(castle);
		GOTStructureDragonstoneCity town = new GOTStructureDragonstoneCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Hull);
		decorator.affix(town);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}