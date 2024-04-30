package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.feature.GOTTreeType;

public class GOTBiomeNorthForestIrontree extends GOTBiomeNorthForest {
	public GOTBiomeNorthForestIrontree(int i, boolean major) {
		super(i, major);
		decorator.clearTrees();
		decorator.clearSettlements();
		decorator.addTree(GOTTreeType.REDWOOD, 10000);
		decorator.addTree(GOTTreeType.REDWOOD_2, 10000);
		decorator.addTree(GOTTreeType.REDWOOD_3, 5000);
		decorator.addTree(GOTTreeType.REDWOOD_4, 5000);
		decorator.addTree(GOTTreeType.REDWOOD_5, 2000);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterNorthForestIrontree;
	}
}