package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeKingswoodNorth extends GOTBiomeWesterosBase {
	public GOTBiomeKingswoodNorth(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupMiderateTrees();

		setupRuinedStructures(false);

		biomeWaypoints = GOTWaypoint.Region.CROWNLANDS;
		biomeAchievement = GOTAchievement.enterKingswood;
	}
}