package got.common.world.biome.ulthos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;

public class GOTBiomeUlthosMarshesForest extends GOTBiomeUlthosMarshes {
	public GOTBiomeUlthosMarshesForest(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 5;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ULTHOS.getSubregion("ulthosMarshesForest");
	}
}
