package got.common.world.biome.other;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeRiver extends GOTBiome {
	public GOTBiomeRiver(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
		banditChance = GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return null;
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.OCEAN.getSubregion(biomeName);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return null;
	}
}