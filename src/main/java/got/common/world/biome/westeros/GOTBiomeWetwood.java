package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.world.feature.GOTTreeType;

public class GOTBiomeWetwood extends GOTBiomeNeck {
	public GOTBiomeWetwood(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 5;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.WILLOW, 100);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("wetwood");
	}
}
