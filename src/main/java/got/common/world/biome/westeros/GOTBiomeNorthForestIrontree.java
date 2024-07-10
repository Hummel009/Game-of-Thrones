package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeNorthForestIrontree extends GOTBiomeWesterosBase {
	public GOTBiomeNorthForestIrontree(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();

		decorator.addTree(GOTTreeType.REDWOOD, 10000);
		decorator.addTree(GOTTreeType.REDWOOD_2, 10000);
		decorator.addTree(GOTTreeType.REDWOOD_3, 5000);
		decorator.addTree(GOTTreeType.REDWOOD_4, 5000);
		decorator.addTree(GOTTreeType.REDWOOD_5, 2000);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.NORTH;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterNorthForestIrontree;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}
}