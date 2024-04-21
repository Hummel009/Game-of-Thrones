package got.common.world.biome.ulthos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeUlthosMarshes extends GOTBiomeUlthosForest implements GOTBiome.Marshes {
	public GOTBiomeUlthosMarshes(int i, boolean major) {
		super(i, major);
		setupMarshFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.SWAMP_LOWLAND, 1.0f);
		variantChance = 1.0f;
		decorator.setSandPerChunk(0);
		decorator.setQuagmirePerChunk(1);
		decorator.setTreesPerChunk(0);
		decorator.setLogsPerChunk(2);
		decorator.setGrassPerChunk(8);
		decorator.setDoubleGrassPerChunk(8);
		decorator.setFlowersPerChunk(0);
		decorator.setCanePerChunk(10);
		decorator.setReedPerChunk(2);
		decorator.setDryReedChance(1.0f);
		npcSpawnList.clear();
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosMarshes;
	}

	@Override
	public int spawnCountMultiplier() {
		return 4;
	}
}