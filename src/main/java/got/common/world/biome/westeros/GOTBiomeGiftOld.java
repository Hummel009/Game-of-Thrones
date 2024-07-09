package got.common.world.biome.westeros;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.westeros.gift.GOTStructureGiftSettlement;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeGiftOld extends GOTBiomeWesterosBase {
	public GOTBiomeGiftOld(int i, boolean major) {
		super(i, major);
		preseter.setupNorthernPlainsView();
		preseter.setupNorthernPlainsFlora();
		preseter.setupNorthernPlainsFauna();
		preseter.setupNorthernTrees(false);

		decorator.setDoubleFlowersPerChunk(0);

		spawnableGOTAmbientList.clear();

		setupRuinedStructures(false);

		invasionSpawns.addInvasion(GOTInvasions.THENN, GOTEventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(GOTInvasions.WILDLING, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.GIANT, GOTEventSpawner.EventChance.RARE);

		decorator.addSettlement(new GOTStructureGiftSettlement(this, 1.0f));

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.GIFT_GUARDIAN, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<GOTSpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 10).setSpawnChance(CONQUEST_SPAWN));
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c2);

		biomeWaypoints = GOTWaypoint.Region.NORTH;
		biomeAchievement = GOTAchievement.enterGiftOld;
	}
}