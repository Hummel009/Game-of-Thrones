package got.common.world.biome.other;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeLake extends GOTBiome {
	public GOTBiomeLake(int i, boolean major) {
		super(i, major);
		setMinMaxHeight(-0.5f, 0.2f);
		spawnableCreatureList.clear();
		spawnableGOTAmbientList.clear();
		npcSpawnList.clear();
		decorator.setSandPerChunk(0);
		unreliableChance = GOTEventSpawner.EventChance.NEVER;
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
	public boolean getEnableRiver() {
		return false;
	}
}