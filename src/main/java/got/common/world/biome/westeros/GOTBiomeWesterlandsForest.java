package got.common.world.biome.westeros;

import got.common.world.biome.variant.GOTBiomeVariant;

public class GOTBiomeWesterlandsForest extends GOTBiomeWesterlands {
	public GOTBiomeWesterlandsForest(int i, boolean major) {
		super(i, major);
		setupStandardForestFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.CLEARING, 0.2f);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		decorator.treesPerChunk = 10;
		decorator.flowersPerChunk = 6;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		decorator.whiteSand = true;
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
	}

}