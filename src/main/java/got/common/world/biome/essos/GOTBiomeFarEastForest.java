package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;

public class GOTBiomeFarEastForest extends GOTBiomeFarEast {
	public GOTBiomeFarEastForest(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 10;
		decorator.clearVillages();
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("farEastForest");
	}
}