package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeNeck extends GOTBiomeWesteros {
	public GOTBiomeNeck(int i, boolean major) {
		super(i, major);
		setupMarshFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.SWAMP_LOWLAND, 1.0f);
		variantChance = 1.0f;
		decorator.setSandPerChunk(0);
		decorator.setClayPerChunk(0);
		decorator.setQuagmirePerChunk(1);
		decorator.setTreesPerChunk(0);
		decorator.setLogsPerChunk(2);
		decorator.setGrassPerChunk(8);
		decorator.setDoubleGrassPerChunk(8);
		decorator.setFlowersPerChunk(0);
		decorator.setReedPerChunk(2);
		decorator.setDryReedChance(1.0f);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.WILLOW, 90);
		decorator.addTree(GOTTreeType.WILLOW_WATER, 10);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(CONQUEST_SPAWN / 2));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterNeck;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.RIVERLANDS;
	}

	@Override
	public int spawnCountMultiplier() {
		return 4;
	}
}