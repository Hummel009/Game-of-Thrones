package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeNeckForest extends GOTBiomeWesterosBase {
	public GOTBiomeNeckForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();

		decorator.addTree(GOTTreeType.WILLOW, 90);
		decorator.addTree(GOTTreeType.WILLOW_WATER, 10);

		decorator.setTreesPerChunk(5);

		setupRuinedStructures(false);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.RIVERLANDS;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterNeck;
	}
}