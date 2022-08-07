package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;

public class GOTBiomeEssosForest extends GOTBiomeEssos {
	public GOTBiomeEssosForest(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 10;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("essosForest");
	}
}
