package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;
import got.common.world.structure.other.GOTStructureBarrow;

public class GOTBiomeNorthBarrows extends GOTBiomeWesterosBase {
	public GOTBiomeNorthBarrows(int i, boolean major) {
		super(i, major);
		preseter.setupColdPlainsView();
		preseter.setupColdPlainsFlora();
		preseter.setupColdPlainsFauna();
		preseter.setupStandardNorthernTrees();

		setupRuinedStructures(false);

		decorator.addStructure(new GOTStructureBarrow(false), 20);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterNorthBarrows;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.NORTH;
	}
}