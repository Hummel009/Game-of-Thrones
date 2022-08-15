package got.common.world.biome.ulthos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;

public class GOTBiomeUlthosForestEdge extends GOTBiomeUlthosForest {
	public GOTBiomeUlthosForestEdge(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 2;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ULTHOS.getSubregion("ulthosForestEdge");
	}
}
