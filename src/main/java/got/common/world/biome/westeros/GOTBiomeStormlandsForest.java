package got.common.world.biome.westeros;

import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;

public class GOTBiomeStormlandsForest extends GOTBiomeStormlands {
	public GOTBiomeStormlandsForest(int i, boolean major) {
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
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.BEECH_PARTY, 20);
		decorator.addTree(GOTTreeType.OAK, 500);
		decorator.addTree(GOTTreeType.OAK_TALLER, 5);
		decorator.addTree(GOTTreeType.OAK_TALL, 500);
		decorator.addTree(GOTTreeType.OAK_LARGE, 200);
		decorator.addTree(GOTTreeType.BEECH, 500);
		decorator.addTree(GOTTreeType.BEECH_LARGE, 200);
		decorator.addTree(GOTTreeType.SPRUCE, 100);
		decorator.addTree(GOTTreeType.FIR, 100);
		decorator.addTree(GOTTreeType.PINE, 100);
		decorator.addTree(GOTTreeType.MAPLE, 50);
		decorator.addTree(GOTTreeType.MAPLE_LARGE, 20);
		decorator.addTree(GOTTreeType.MAPLE_PARTY, 2);
		decorator.addTree(GOTTreeType.ASPEN, 100);
		decorator.addTree(GOTTreeType.ASPEN_LARGE, 20);
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
	}

}