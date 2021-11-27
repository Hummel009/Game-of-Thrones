package got.common.world.biome.other;

import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.world.biome.GOTBiome;

public class GOTBiomeRiver extends GOTBiome {
	public GOTBiomeRiver(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return null;
	}

	@Override
	public boolean isRiver() {
		return true;
	}
}
