package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeBraavosForest extends GOTBiomeEssosBase {
	public GOTBiomeBraavosForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupMiderateTrees();

		setupRuinedStructures(false);

		biomeWaypoints = GOTWaypoint.Region.BRAAVOS;
		biomeAchievement = GOTAchievement.enterBraavos;
	}
}