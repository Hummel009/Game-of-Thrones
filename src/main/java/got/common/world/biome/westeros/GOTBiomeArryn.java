package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureTower;
import got.common.world.structure.westeros.arryn.GOTStructureArrynCity;

public class GOTBiomeArryn extends GOTBiomeWesteros {
	public GOTBiomeArryn(int i, boolean major) {
		super(i, major);
		decorator.affix(new GOTStructureArrynCity(this, 1.0f));
		GOTStructureTower towerGen = new GOTStructureTower(this, 0.0f);
		towerGen.affix(GOTWaypoint.BaelishKeep);
		decorator.affix(towerGen);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.HILL_TRIBES_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] container2 = new SpawnListContainer[1];
		container2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container2);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.HILL_TRIBES, GOTEventSpawner.EventChance.COMMON);
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.ARRYN_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.ARRYN_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureArrynCity castle = new GOTStructureArrynCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Pebble);
		castle.affix(GOTWaypoint.ThePaps);
		castle.affix(GOTWaypoint.ColdwaterBurn);
		castle.affix(GOTWaypoint.Snakewood);
		castle.affix(GOTWaypoint.HeartsHome);
		castle.affix(GOTWaypoint.Strongsong);
		castle.affix(GOTWaypoint.LongbowHall);
		castle.affix(GOTWaypoint.OldAnchor);
		castle.affix(GOTWaypoint.Ninestars);
		castle.affix(GOTWaypoint.IronOak);
		castle.affix(GOTWaypoint.Runestone);
		castle.affix(GOTWaypoint.GreyGlen);
		castle.affix(GOTWaypoint.Redfort);
		castle.affix(GOTWaypoint.GateOfTheMoon, 0, 1);
		castle.affix(GOTWaypoint.WitchIsle);
		castle.affix(GOTWaypoint.Wickenden);
		decorator.affix(castle);
		GOTStructureArrynCity town = new GOTStructureArrynCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.Sisterton);
		town.affix(GOTWaypoint.Gulltown);
		decorator.affix(town);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_ARRYN;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("arryn");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ARRYN;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}
