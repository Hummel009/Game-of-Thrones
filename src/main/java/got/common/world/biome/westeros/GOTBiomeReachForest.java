package got.common.world.biome.westeros;

import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;

public class GOTBiomeReachForest extends GOTBiomeReach {
	public GOTBiomeReachForest(int i, boolean major) {
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
		decorator.addTree(GOTTreeType.OAK, 500);
		decorator.addTree(GOTTreeType.OAK_TALLER, 5);
		decorator.addTree(GOTTreeType.OAK_GIANT, 2);
		decorator.addTree(GOTTreeType.OAK_LARGE, 100);
		decorator.addTree(GOTTreeType.CHESTNUT, 100);
		decorator.addTree(GOTTreeType.CHESTNUT_LARGE, 50);
		decorator.addTree(GOTTreeType.CHESTNUT_PARTY, 3);
		decorator.addTree(GOTTreeType.HOLLY, 1000);
		decorator.addTree(GOTTreeType.HOLLY_LARGE, 100);
		decorator.addTree(GOTTreeType.BIRCH, 100);
		decorator.addTree(GOTTreeType.BIRCH_LARGE, 50);
		decorator.addTree(GOTTreeType.BIRCH_TALL, 10);
		decorator.addTree(GOTTreeType.BIRCH_PARTY, 2);
		decorator.addTree(GOTTreeType.BIRCH_DEAD, 3);
		decorator.addTree(GOTTreeType.LARCH, 200);
		decorator.addTree(GOTTreeType.ASPEN, 20);
		decorator.addTree(GOTTreeType.ASPEN_LARGE, 5);
		decorator.addTree(GOTTreeType.APPLE, 5);
		decorator.addTree(GOTTreeType.PEAR, 5);
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
	}

}