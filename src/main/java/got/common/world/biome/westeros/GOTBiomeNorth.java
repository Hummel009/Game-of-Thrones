package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.north.GOTStructureNorthSettlement;
import got.common.world.structure.westeros.north.GOTStructureNorthWatchfort;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeNorth extends GOTBiomeWesterosBase {
	public GOTBiomeNorth(int i, boolean major) {
		super(i, major);
		preseter.setupNorthernPlainsView();
		preseter.setupNorthernPlainsFlora();
		preseter.setupNorthernPlainsFauna();
		preseter.setupNorthernTrees(false);

		setupRuinedStructures(false);

		decorator.addStructure(new GOTStructureNorthWatchfort(false), 800);
		decorator.addSettlement(new GOTStructureNorthSettlement(this, 1.0f));

		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.IRONBORN, GOTEventSpawner.EventChance.UNCOMMON);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 4).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		Collection<GOTSpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c3);
		Collection<GOTSpawnListContainer> c4 = new ArrayList<>();
		c4.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 10).setSpawnChance(CONQUEST_SPAWN));
		c4.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c4);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.NORTH;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterNorth;
	}
}