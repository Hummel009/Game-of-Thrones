package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornCity;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornWatchfort;

import java.util.ArrayList;

public class GOTBiomeIronborn extends GOTBiomeWesteros {
	public GOTBiomeIronborn(int i, boolean major) {
		super(i, major);
		decorator.addVillage(new GOTStructureIronbornCity(this, 1.0f));
		decorator.addRandomStructure(new GOTStructureIronbornWatchfort(false), 800);
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterIronborn;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("ironborn");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.IRONBORN;
	}
}