package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeKingswoodSouth extends GOTBiomeWesterosBase {
	public GOTBiomeKingswoodSouth(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupStandardSouthernTrees(false);

		setupRuinedStructures(false);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterKingswood;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.STORMLANDS;
	}
}