package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornSettlement;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornWatchfort;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeIronIslands extends GOTBiomeWesterosBase {
	public GOTBiomeIronIslands(int i, boolean major) {
		super(i, major);
		preseter.setupPlainsView();
		preseter.setupPlainsFlora();
		preseter.setupPlainsFauna();
		preseter.setupStandardNorthernTrees();

		setupRuinedStructures(false);

		decorator.addSettlement(new GOTStructureIronbornSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureIronbornWatchfort(false), 800);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 4).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterIronIslands;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.IRONBORN;
	}
}