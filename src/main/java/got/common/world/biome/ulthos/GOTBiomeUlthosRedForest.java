package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeUlthosRedForest extends GOTBiomeUlthosBase {
	public GOTBiomeUlthosRedForest(int i, boolean major) {
		super(i, major);
		preseter.setupForestView();
		preseter.setupForestFlora();
		preseter.setupForestFauna();

		decorator.clearTrees();
		setupForestTrees();
		decorator.addTree(GOTTreeType.ULTHOS_RED_OAK, 1000);
		decorator.addTree(GOTTreeType.ULTHOS_RED_OAK_LARGE, 50);
		decorator.addTree(GOTTreeType.ULTHOS_GREEN_OAK, 15);
		decorator.addTree(GOTTreeType.ULTHOS_GREEN_OAK_LARGE, 10);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.ULTHOS, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_MILITARY, 4).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosRedForest;
	}
}