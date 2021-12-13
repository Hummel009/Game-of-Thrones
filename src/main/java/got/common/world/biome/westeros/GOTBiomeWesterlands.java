package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureRuins;
import got.common.world.structure.westeros.westerlands.*;

public class GOTBiomeWesterlands extends GOTBiomeWesteros {

	public GOTBiomeWesterlands(int i, boolean major) {
		super(i, major);
		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		SpawnListContainer[] c2 = new SpawnListContainer[1];
		c2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c2);

		SpawnListContainer[] c3 = new SpawnListContainer[1];
		c3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c3);

		SpawnListContainer[] c4 = new SpawnListContainer[1];
		c4[0] = GOTBiomeSpawnList.entry(GOTSpawnList.RIVERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c4);

		SpawnListContainer[] c5 = new SpawnListContainer[1];
		c5[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c5);

		decorator.affix(new GOTStructureWesterlandsCity(this, 1.0f));

		GOTStructureWesterlandsCity village = new GOTStructureWesterlandsCity(this, 0.0f);
		village.affix(GOTWaypoint.Oxcross);
		decorator.affix(village);

		GOTStructureWesterlandsCity castle = new GOTStructureWesterlandsCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Wyndhall);
		castle.affix(GOTWaypoint.Banefort);
		castle.affix(GOTWaypoint.Plumwood);
		castle.affix(GOTWaypoint.Crag);
		castle.affix(GOTWaypoint.Ashemark);
		castle.affix(GOTWaypoint.GoldenTooth, 0, 1);
		castle.affix(GOTWaypoint.Sarsfield, 0, -1);
		castle.affix(GOTWaypoint.Hornvale);
		castle.affix(GOTWaypoint.DeepDen);
		castle.affix(GOTWaypoint.Riverspring);
		castle.affix(GOTWaypoint.Silverhill);
		castle.affix(GOTWaypoint.Greenfield);
		castle.affix(GOTWaypoint.Cornfield);
		castle.affix(GOTWaypoint.Crakehall, -1, 0);
		castle.affix(GOTWaypoint.Faircastle);
		castle.affix(GOTWaypoint.Feastfires);
		castle.affix(GOTWaypoint.Kayce, 1, 0);
		castle.affix(GOTWaypoint.CleganesKeep);
		decorator.affix(castle);

		GOTStructureWesterlandsCity town = new GOTStructureWesterlandsCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Kayce, 3);
		decorator.affix(town);

		GOTStructureRuins ruins = new GOTStructureRuins(this, 0.0f);
		ruins.affix(GOTWaypoint.TarbeckHall);
		ruins.affix(GOTWaypoint.Castamere);
		ruins.affix(GOTWaypoint.Goldenhill);
		decorator.affix(ruins);

		invasionSpawns.addInvasion(GOTInvasions.DRAGONSTONE, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.STORMLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.RIVERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.NORTH, GOTEventSpawner.EventChance.UNCOMMON);

		decorator.addRandomStructure(new GOTStructureWesterlandsWatchfort(false), 800);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_WESTERLANDS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("westerlands");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.WESTERLANDS;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}
