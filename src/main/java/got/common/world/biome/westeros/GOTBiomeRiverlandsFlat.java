package got.common.world.biome.westeros;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.fixed.GOTStructureCrossroadsInn;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureTower;
import got.common.world.structure.westeros.riverlands.GOTStructureRiverlandsCity;

public class GOTBiomeRiverlandsFlat extends GOTBiomeRiverlands {
	public GOTBiomeRiverlandsFlat(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		decorator.clearVillages();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.RIVERLANDS_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.RIVERLANDS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.IRONBORN_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		SpawnListContainer[] container5 = new SpawnListContainer[1];
		container5[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container5);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureRiverlandsCity village = new GOTStructureRiverlandsCity(this, 0.0f);
		village.affix(GOTWaypoint.Sevenstreams);
		village.affix(GOTWaypoint.FairMarket);
		village.affix(GOTWaypoint.Harroway);
		village.affix(GOTWaypoint.Pennytree);
		decorator.addVillage(village);
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
		decorator.addVillage(castle);
		GOTStructureRiverlandsCity town = new GOTStructureRiverlandsCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.StoneySept);
		town.affix(GOTWaypoint.Maidenpool, -1, 0, 3);
		town.affix(GOTWaypoint.Saltpans);
		town.affix(GOTWaypoint.Seagard, 0, 1, 2);
		decorator.addVillage(town);
		GOTStructureTower tower = new GOTStructureTower(this, 0.0f);
		tower.affix(GOTWaypoint.TwinsRight, -2, 0, 3);
		tower.affix(GOTWaypoint.TwinsLeft, 1, 0, 1);
		decorator.addVillage(tower);
		GOTStructureCrossroadsInn inn = new GOTStructureCrossroadsInn(this, 0.0f);
		inn.affix(GOTWaypoint.CrossroadsInn);
		decorator.addVillage(inn);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}