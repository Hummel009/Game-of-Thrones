package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeStormlandsForest extends GOTBiomeWesterosBase {
	public GOTBiomeStormlandsForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupSouthernTrees(false);

		setupRuinedStructures(false);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.REACH;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterReach;
	}
}