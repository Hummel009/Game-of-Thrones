package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeNorthForest extends GOTBiomeWesterosBase {
	public GOTBiomeNorthForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupNorthernTrees(false);

		setupRuinedStructures(false);

		biomeWaypoints = GOTWaypoint.Region.NORTH;
		biomeAchievement = GOTAchievement.enterNorth;
	}
}