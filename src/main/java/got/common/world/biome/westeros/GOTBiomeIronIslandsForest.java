package got.common.world.biome.westeros;

import got.common.world.biome.variant.GOTBiomeVariant;

public class GOTBiomeIronIslandsForest extends GOTBiomeIronIslands {
	public GOTBiomeIronIslandsForest(int i, boolean major) {
		super(i, major);
		setupStandardForestFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		decorator.setTreesPerChunk(10);
		decorator.setFlowersPerChunk(6);
		decorator.setGrassPerChunk(8);
		decorator.setDoubleGrassPerChunk(2);
		decorator.setWhiteSand(true);
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
	}
}