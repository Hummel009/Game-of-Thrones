package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;
import got.common.world.structure.other.GOTStructureBarrow;

public class GOTBiomeNorthBarrows extends GOTBiomeWesterosBase {
	public GOTBiomeNorthBarrows(int i, boolean major) {
		super(i, major);
		preseter.setupNorthernPlainsView();
		preseter.setupNorthernPlainsFlora();
		preseter.setupNorthernPlainsFauna();
		preseter.setupNorthernTrees(false);

		setupRuinedStructures(false);

		decorator.addStructure(new GOTStructureBarrow(false), 20);

		biomeWaypoints = GOTWaypoint.Region.NORTH;
		biomeAchievement = GOTAchievement.enterNorthBarrows;
	}
}