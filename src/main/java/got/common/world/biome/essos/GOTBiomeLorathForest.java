package got.common.world.biome.essos;

import got.common.world.biome.variant.GOTBiomeVariant;

public class GOTBiomeLorathForest extends GOTBiomeLorath {
	public GOTBiomeLorathForest(int i, boolean major) {
		super(i, major);
		setupStandardForestFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.CLEARING, 0.2f);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		decorator.treesPerChunk = 10;
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
	}

}