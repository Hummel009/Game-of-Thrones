package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeWesterlandsForest extends GOTBiomeWesterosBase {
	public GOTBiomeWesterlandsForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupStandardMiderateTrees();

		setupRuinedStructures(false);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterWesterlands;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.WESTERLANDS;
	}
}