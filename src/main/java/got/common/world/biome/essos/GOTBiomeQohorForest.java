package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeQohorForest extends GOTBiomeEssosBase {
	public GOTBiomeQohorForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupMiderateTrees();

		setupRuinedStructures(false);

		decorator.addTree(GOTTreeType.CATALPA, 1000);
		decorator.addTree(GOTTreeType.CATALPA_BOUGHS, 100);
		decorator.addTree(GOTTreeType.CATALPA_PARTY, 1);

		biomeWaypoints = GOTWaypoint.Region.QOHOR;
		biomeAchievement = GOTAchievement.enterQohor;
	}
}