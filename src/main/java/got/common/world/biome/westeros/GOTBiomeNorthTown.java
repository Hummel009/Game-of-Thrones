package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;

public class GOTBiomeNorthTown extends GOTBiomeNorth {
	public GOTBiomeNorthTown(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		decorator.clearVillages();

		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_GUARDIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		SpawnListContainer[] c2 = new SpawnListContainer[1];
		c2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c2);

		SpawnListContainer[] c3 = new SpawnListContainer[1];
		c3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c3);

		SpawnListContainer[] c4 = new SpawnListContainer[2];
		c4[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		c4[1] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c4);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_NORTH_TOWN;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("northTown");
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PAVING;
	}

	@Override
	public boolean hasDomesticAnimals() {
		return true;
	}
}