package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeVolantisForest extends GOTBiomeEssosBase {
	public GOTBiomeVolantisForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupSouthernTrees(true);

		setupRuinedStructures(false);

		biomeWaypoints = GOTWaypoint.Region.VOLANTIS;
		biomeAchievement = GOTAchievement.enterVolantis;
	}
}