package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeIbbenColonyForest extends GOTBiomeEssosBase {
	public GOTBiomeIbbenColonyForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupMiderateTrees();

		setupRuinedStructures(false);

		biomeWaypoints = GOTWaypoint.Region.IBBEN_COLONY;
		biomeAchievement = GOTAchievement.enterIbbenColony;
		wallBlock = GOTBezierType.WALL_IBBEN;
	}
}