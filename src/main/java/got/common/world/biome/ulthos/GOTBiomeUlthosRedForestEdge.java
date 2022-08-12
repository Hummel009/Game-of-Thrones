package got.common.world.biome.ulthos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;

public class GOTBiomeUlthosRedForestEdge extends GOTBiomeUlthosRedForest {
	public GOTBiomeUlthosRedForestEdge(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 1;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ULTHOS.getSubregion("ulthosRedForestEdge");
	}
}