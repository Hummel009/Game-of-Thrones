package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.world.feature.GOTTreeType;

public class GOTBiomeMossovyForest extends GOTBiomeMossovy {
	public GOTBiomeMossovyForest(int i, boolean major) {
		super(i, major);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.PINE, 20);
		decorator.treesPerChunk = 10;
		decorator.clearVillages();
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("mossovyForest");
	}
}