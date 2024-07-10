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

		setupRuinedStructures(false);

		decorator.clearTrees();
		decorator.addTree(GOTTreeType.CATALPA, 1000);
		decorator.addTree(GOTTreeType.CATALPA_BOUGHS, 250);
		decorator.addTree(GOTTreeType.PINE, 400);
		decorator.addTree(GOTTreeType.SPRUCE, 300);
		decorator.addTree(GOTTreeType.FIR, 300);
		decorator.addTree(GOTTreeType.DARK_OAK, 100);
		decorator.addTree(GOTTreeType.CATALPA_PARTY, 1);
		decorator.addTree(GOTTreeType.DARK_OAK_PARTY, 1);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.QOHOR;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterQohor;
	}
}