package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeIfekevronForest extends GOTBiomeEssosBase {
	public GOTBiomeIfekevronForest(int i, boolean major) {
		super(i, major);
		preseter.setupJungleView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();
		preseter.setupJungleTrees();

		setupRuinedStructures(false);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.IFEKEVRON, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
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