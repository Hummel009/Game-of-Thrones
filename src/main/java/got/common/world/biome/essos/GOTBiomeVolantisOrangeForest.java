package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeVolantisOrangeForest extends GOTBiomeEssosBase {
	public GOTBiomeVolantisOrangeForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupSouthernTrees(true);

		setupRuinedStructures(false);

		decorator.addTree(GOTTreeType.ORANGE, 1000);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.VOLANTIS;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterVolantis;
	}
}