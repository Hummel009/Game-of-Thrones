package got.common.world.biome.westeros;

import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;

public class GOTBiomeReachForest extends GOTBiomeReach {
	public GOTBiomeReachForest(int i, boolean major) {
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
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK, 500);
		decorator.addTree(GOTTreeType.OAK_TALLER, 5);
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