package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.fixed.GOTStructureCrossroadsInn;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.riverlands.GOTStructureRiverlandsCity;

public class GOTBiomeRiverlands extends GOTBiomeWesteros {
	public GOTBiomeRiverlands(int i, boolean major) {
		super(i, major);
		SpawnListContainer[] c0 = new SpawnListContainer[2];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.RIVERLANDS_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN);
		c0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.RIVERLANDS_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);

		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);

		SpawnListContainer[] c2 = new SpawnListContainer[1];
		c2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c2);

		SpawnListContainer[] c3 = new SpawnListContainer[1];
		c3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c3);

		decorator.affix(new GOTStructureRiverlandsCity(this, 1.0f));

		GOTStructureRiverlandsCity village = new GOTStructureRiverlandsCity(this, 0.0f);
		village.affix(GOTWaypoint.Sevenstreams);
		village.affix(GOTWaypoint.FairMarket);
		village.affix(GOTWaypoint.Harroway);
		village.affix(GOTWaypoint.Pennytree);
		decorator.affix(village);

		GOTStructureRiverlandsCity castle = new GOTStructureRiverlandsCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Seagard, 0, -1);
		castle.affix(GOTWaypoint.Darry, 1, 0);
		castle.affix(GOTWaypoint.AcornHall);
		castle.affix(GOTWaypoint.Atranta);
		castle.affix(GOTWaypoint.WayfarerRest);
		castle.affix(GOTWaypoint.RaventreeHall);
		castle.affix(GOTWaypoint.PinkmaidenCastle);
		castle.affix(GOTWaypoint.Riverrun, -1, 0);
		castle.affix(GOTWaypoint.StoneHedge, 0, 1);
		castle.affix(GOTWaypoint.Maidenpool, 1, 0);
		decorator.affix(castle);

		GOTStructureRiverlandsCity town = new GOTStructureRiverlandsCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.StoneySept);
		town.affix(GOTWaypoint.Maidenpool, -1, 0, 3);
		town.affix(GOTWaypoint.Saltpans);
		town.affix(GOTWaypoint.Seagard, 0, 1, 2);
		decorator.affix(town);

		GOTStructureTower tower = new GOTStructureTower(this, 0.0f);
		tower.affix(GOTWaypoint.TwinsRight, -2, 0, 3);
		tower.affix(GOTWaypoint.TwinsLeft, 1, 0, 1);
		decorator.affix(tower);

		GOTStructureRuins ruins = new GOTStructureRuins(this, 0.0f);
		ruins.affix(GOTWaypoint.OldStones);
		ruins.affix(GOTWaypoint.WhiteWalls);
		ruins.affix(GOTWaypoint.HoggHall);
		decorator.affix(ruins);

		GOTStructureRuinsBig colossal = new GOTStructureRuinsBig(this, 0.0f);
		colossal.affix(GOTWaypoint.Harrenhal);
		decorator.affix(colossal);

		GOTStructureCrossroadsInn inn = new GOTStructureCrossroadsInn(this, 0.0f);
		inn.affix(GOTWaypoint.CrossroadsInn);
		decorator.affix(inn);

		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.IRONBORN, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.HILL_TRIBES, GOTEventSpawner.EventChance.COMMON);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_RIVERLANDS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("riverlands");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.RIVERLANDS;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

}
