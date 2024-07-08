package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeUlthosMarshes extends GOTBiomeUlthosBase implements GOTBiome.Marshes {
	public GOTBiomeUlthosMarshes(int i, boolean major) {
		super(i, major);
		preseter.setupMarshesView();
		preseter.setupMarshesFlora();
		preseter.setupMarshesFauna();

		decorator.addTree(GOTTreeType.WILLOW, 90);
		decorator.addTree(GOTTreeType.WILLOW_WATER, 10);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosMarshes;
	}
}