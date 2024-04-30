package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.arryn.GOTStructureArrynSettlement;
import got.common.world.structure.westeros.arryn.GOTStructureArrynWatchfort;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeArryn extends GOTBiomeWesteros {
	public GOTBiomeArryn(int i, boolean major) {
		super(i, major);
		decorator.addSettlement(new GOTStructureArrynSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureArrynWatchfort(false), 800);
		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.HILL_TRIBES, GOTEventSpawner.EventChance.COMMON);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.ARRYN_MILITARY, 10).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.ARRYN_CONQUEST, 4).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.HILL_TRIBES_MILITARY, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		Collection<GOTSpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c3);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterArryn;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.ARRYN;
	}
}