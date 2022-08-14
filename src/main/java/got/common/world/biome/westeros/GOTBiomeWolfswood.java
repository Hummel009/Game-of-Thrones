package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeWolfswood extends GOTBiomeNorthForest {
	public GOTBiomeWolfswood(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 6;
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("wolfswood");
	}
}