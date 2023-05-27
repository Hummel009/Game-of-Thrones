package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.hillmen.GOTStructureHillmanSettlement;

import java.util.ArrayList;

public class GOTBiomeArrynMountainsFoothills extends GOTBiomeWesteros {
	public GOTBiomeArrynMountainsFoothills(int i, boolean major) {
		super(i, major);
		setupStandardForestFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.CLEARING, 0.2f);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		decorator.treesPerChunk = 2;
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.addSettlement(new GOTStructureHillmanSettlement(this, 1.0f));
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.HILL_TRIBES_CIVILIAN, 4).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.HILL_TRIBES_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.ARRYN_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		ArrayList<SpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterHillTribes;
	}


	@Override
	public Region getBiomeWaypoints() {
		return Region.ARRYN;
	}

	@Override
	public float getTreeIncreaseChance() {
		return 0.75f;
	}

}
