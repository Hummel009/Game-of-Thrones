package got.common.world.biome.other;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeLake extends GOTBiome {
	public GOTBiomeLake(int i, boolean major) {
		super(i, major);
		setMinMaxHeight(-0.5f, 0.2f);
		spawnableCreatureList.clear();
		spawnableGOTAmbientList.clear();
		npcSpawnList.clear();
		decorator.setSandPerChunk(0);
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

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return null;
	}

	@Override
	public int getWallTop() {
		return 0;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}