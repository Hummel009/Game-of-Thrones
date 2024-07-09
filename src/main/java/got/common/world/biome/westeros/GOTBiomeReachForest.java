package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeReachForest extends GOTBiomeWesterosBase {
	public GOTBiomeReachForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupSouthernTrees(false);

		setupRuinedStructures(false);

		biomeWaypoints = GOTWaypoint.Region.REACH;
		biomeAchievement = GOTAchievement.enterReach;
	}
}