package got.common.world.biome.essos;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.pentos.GOTStructurePentosCity;

public class GOTBiomePentosFlat extends GOTBiomePentos {
	public GOTBiomePentosFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.PENTOS_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.PENTOS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.BRAAVOS_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructurePentosCity town = new GOTStructurePentosCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Pentos, -1, 0, 1);
		decorator.affix(town);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}