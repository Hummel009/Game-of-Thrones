package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeUlthosForest extends GOTBiomeUlthos {
	public GOTBiomeUlthosForest(int i, boolean major) {
		super(i, major);
		setupStandardForestFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.FLOWERS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		decorator.setTreesPerChunk(8);
		decorator.setLogsPerChunk(3);
		decorator.setFlowersPerChunk(2);
		decorator.setDoubleFlowersPerChunk(1);
		decorator.setGrassPerChunk(12);
		decorator.setDoubleGrassPerChunk(6);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.ULTHOS, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.SOTHORYOS_MILITARY, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosForest;
	}

	@Override
	public boolean isBushland() {
		return false;
	}
}