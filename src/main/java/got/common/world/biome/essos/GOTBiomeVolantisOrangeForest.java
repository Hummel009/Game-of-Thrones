package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeVolantisOrangeForest extends GOTBiomeVolantis {
	public GOTBiomeVolantisOrangeForest(int i, boolean major) {
		super(i, major);
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		banditChance = GOTEventSpawner.EventChance.NEVER;
		decorator.setTreesPerChunk(10);
		decorator.setFlowersPerChunk(6);
		decorator.setGrassPerChunk(8);
		decorator.setDoubleGrassPerChunk(2);
		decorator.setWhiteSand(true);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.BEECH_PARTY, 2);
		decorator.addTree(GOTTreeType.OAK, 300);
		decorator.addTree(GOTTreeType.OAK_LARGE, 50);
		decorator.addTree(GOTTreeType.LARCH, 200);
		decorator.addTree(GOTTreeType.BEECH, 100);
		decorator.addTree(GOTTreeType.BEECH_LARGE, 20);
		decorator.addTree(GOTTreeType.ORANGE, 730);
		decorator.addTree(GOTTreeType.ASPEN, 100);
		decorator.addTree(GOTTreeType.ASPEN_LARGE, 20);
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterVolantisOrangeForest;
	}
}