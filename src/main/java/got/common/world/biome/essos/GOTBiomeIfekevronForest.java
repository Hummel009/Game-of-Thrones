package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.entity.other.GOTEntityIfekevron;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeIfekevronForest extends GOTBiomeEssosBase {
	public GOTBiomeIfekevronForest(int i, boolean major) {
		super(i, major);
		preseter.setupJungleView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupJungleTrees();

		addSpawnableMonster(GOTEntityIfekevron.class, 5, 1, 1);

		setupRuinedStructures(false);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.DOTHRAKI;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterIfekevronForest;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WALL_IBBEN;
	}
}