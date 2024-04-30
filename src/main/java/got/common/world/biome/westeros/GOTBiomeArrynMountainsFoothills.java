package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.hillmen.GOTStructureHillmanSettlement;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeArrynMountainsFoothills extends GOTBiomeWesteros {
	public GOTBiomeArrynMountainsFoothills(int i, boolean major) {
		super(i, major);
		setupStandardForestFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		decorator.setTreesPerChunk(2);
		decorator.setGrassPerChunk(6);
		decorator.setDoubleGrassPerChunk(1);
		decorator.setFlowersPerChunk(3);
		decorator.setDoubleFlowersPerChunk(1);
		decorator.addSettlement(new GOTStructureHillmanSettlement(this, 1.0f));
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.HILL_TRIBES_CIVILIAN, 4).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.HILL_TRIBES_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.ARRYN_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterArrynMountainsFoothills;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.ARRYN;
	}
}