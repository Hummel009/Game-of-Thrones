package got.common.world.biome.other;

import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeLake extends GOTBiome {
	public GOTBiomeLake(int i, boolean major) {
		super(i, major);
		setMinMaxHeight(-0.5f, 0.2f);
		spawnableCreatureList.clear();
		spawnableGOTAmbientList.clear();
		npcSpawnList.clear();
		decorator.sandPerChunk = 0;
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return null;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

}
