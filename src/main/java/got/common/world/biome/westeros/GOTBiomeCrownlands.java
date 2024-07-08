package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.crownlands.GOTStructureCrownlandsSettlement;
import got.common.world.structure.westeros.crownlands.GOTStructureCrownlandsWatchfort;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeCrownlands extends GOTBiomeWesterosBase {
	public GOTBiomeCrownlands(int i, boolean major) {
		super(i, major);
		preseter.setupPlainsView();
		preseter.setupPlainsFlora();
		preseter.setupPlainsFauna();
		preseter.setupStandardMiderateTrees();

		setupRuinedStructures(false);

		decorator.addSettlement(new GOTStructureCrownlandsSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureCrownlandsWatchfort(false), 800);

		invasionSpawns.addInvasion(GOTInvasions.DRAGONSTONE, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.IRONBORN, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.STORMLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.RIVERLANDS, GOTEventSpawner.EventChance.UNCOMMON);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROWNLANDS_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(5).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 4).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(5).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);
		Collection<GOTSpawnListContainer> c3 = new ArrayList<>();
		c3.add(GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c3);
		Collection<GOTSpawnListContainer> c4 = new ArrayList<>();
		c4.add(GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c4);
		Collection<GOTSpawnListContainer> c5 = new ArrayList<>();
		c5.add(GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c5);
		Collection<GOTSpawnListContainer> c6 = new ArrayList<>();
		c6.add(GOTBiomeSpawnList.entry(GOTSpawnList.RIVERLANDS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c6);
		Collection<GOTSpawnListContainer> c7 = new ArrayList<>();
		c7.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c7);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterCrownlands;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.CROWNLANDS;
	}
}