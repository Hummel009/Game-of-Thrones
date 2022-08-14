package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;

public class GOTBiomeWesterosForest extends GOTBiomeWesteros {
	public GOTBiomeWesterosForest(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		setupStandartForestFauna();
		enablePodzol = false;
		decorator.treesPerChunk = 10;
		decorator.flowersPerChunk = 6;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		decorator.whiteSand = true;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("westerosForest");
	}
}