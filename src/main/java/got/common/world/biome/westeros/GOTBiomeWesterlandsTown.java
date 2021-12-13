package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.westerlands.GOTStructureWesterlandsCity;

public class GOTBiomeWesterlandsTown extends GOTBiomeWesterlands {
	public GOTBiomeWesterlandsTown(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		decorator.clearVillages();
		decorator.clearRandomStructures();

		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_GUARDIAN, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		SpawnListContainer[] c2 = new SpawnListContainer[1];
		c2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c2);

		SpawnListContainer[] c3 = new SpawnListContainer[1];
		c3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c3);

		SpawnListContainer[] c4 = new SpawnListContainer[1];
		c4[0] = GOTBiomeSpawnList.entry(GOTSpawnList.RIVERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c4);

		SpawnListContainer[] c5 = new SpawnListContainer[1];
		c5[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c5);

		GOTStructureWesterlandsCity town = new GOTStructureWesterlandsCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Lannisport, -1, 0, 3);
		decorator.affix(town);

		GOTStructureWesterlandsCity castle = new GOTStructureWesterlandsCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.CasterlyRock, -1, 0);
		decorator.affix(castle);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_WESTERLANDS_TOWN;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("westerlandsTown");
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