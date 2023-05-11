package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.westeros.westerlands.GOTStructureWesterlandsCity;
import got.common.world.structure.westeros.westerlands.GOTStructureWesterlandsWatchfort;

import java.util.ArrayList;

public class GOTBiomeWesterlands extends GOTBiomeWesteros {

	public GOTBiomeWesterlands(int i, boolean major) {
		super(i, major);
		decorator.addTree(GOTTreeType.ARAMANT, 5);
		decorator.addVillage(new GOTStructureWesterlandsCity(this, 1.0f));
		decorator.addRandomStructure(new GOTStructureWesterlandsWatchfort(false), 800);
		invasionSpawns.addInvasion(GOTInvasions.DRAGONSTONE, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.STORMLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.RIVERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.NORTH, GOTEventSpawner.EventChance.UNCOMMON);
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		ArrayList<SpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		ArrayList<SpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c3);
		ArrayList<SpawnListContainer> c4 = new ArrayList<>();
		c4.add(GOTBiomeSpawnList.entry(GOTSpawnList.RIVERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c4);
		ArrayList<SpawnListContainer> c5 = new ArrayList<>();
		c5.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c5);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterWesterlands;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("westerlands");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.WESTERLANDS;
	}
}