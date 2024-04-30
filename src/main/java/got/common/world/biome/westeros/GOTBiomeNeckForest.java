package got.common.world.biome.westeros;

import got.common.world.biome.variant.GOTBiomeVariant;

public class GOTBiomeNeckForest extends GOTBiomeNeck {
	public GOTBiomeNeckForest(int i, boolean major) {
		super(i, major);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		decorator.setTreesPerChunk(5);
	}
}