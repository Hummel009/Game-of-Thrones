package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.reach.GOTStructureReachSettlement;
import got.common.world.structure.westeros.reach.GOTStructureReachWatchfort;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeReach extends GOTBiomeWesterosBase {
	public GOTBiomeReach(int i, boolean major) {
		super(i, major);
		preseter.setupSouthernPlainsView(false);
		preseter.setupSouthernPlainsFlora();
		preseter.setupSouthernPlainsFauna(false);
		preseter.setupSouthernTrees(false);

		setupRuinedStructures(false);

		decorator.addSettlement(new GOTStructureReachSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureReachWatchfort(false), 800);

		invasionSpawns.addInvasion(GOTInvasions.DRAGONSTONE, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.IRONBORN, GOTEventSpawner.EventChance.UNCOMMON);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.REACH_CONQUEST, 4).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.REACH_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.DORNE_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		Collection<GOTSpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c3);

		biomeWaypoints = GOTWaypoint.Region.REACH;
		biomeAchievement = GOTAchievement.enterReach;
	}
}