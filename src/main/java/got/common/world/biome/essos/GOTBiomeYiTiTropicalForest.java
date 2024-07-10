package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.entity.animal.GOTEntityJungleScorpion;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;

public class GOTBiomeYiTiTropicalForest extends GOTBiomeEssosBase {
	public GOTBiomeYiTiTropicalForest(int i, boolean major) {
		super(i, major);
		preseter.setupJungleView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupJungleTrees();

		addSpawnableMonster(GOTEntityJungleScorpion.class, 5, 1, 1);

		setupRuinedStructures(false);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.YI_TI;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterYiTi;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_PAVING;
	}
}