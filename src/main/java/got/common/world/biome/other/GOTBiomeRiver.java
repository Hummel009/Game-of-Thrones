package got.common.world.biome.other;

import got.client.sound.GOTMusicRegion;
import got.client.sound.GOTMusicRegion.Sub;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeRiver extends GOTBiome {
	public GOTBiomeRiver(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.OCEAN.getSubregion(biomeName);
	}

	@Override
	public boolean isRiver() {
		return true;
	}
}
