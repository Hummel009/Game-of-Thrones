package got.common.world.biome.westeros;

import got.common.world.biome.variant.GOTBiomeVariant;

public class GOTBiomeDorneForest extends GOTBiomeDorne {
	public GOTBiomeDorneForest(int i, boolean major) {
		super(i, major);
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