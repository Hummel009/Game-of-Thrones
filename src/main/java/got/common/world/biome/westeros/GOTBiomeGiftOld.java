package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.fixed.*;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.gift.GOTStructureGiftVillage;

public class GOTBiomeGiftOld extends GOTBiomeWesteros {
	public GOTBiomeGiftOld(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		clearBiomeVariants();
		decorator.clearTrees();
		decorator.treesPerChunk = -1;
		SpawnListContainer[] container0 = new SpawnListContainer[1];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.GIFT_GUARDIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		SpawnListContainer[] container2 = new SpawnListContainer[2];
		container2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		container2[1] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container2);
		invasionSpawns.addInvasion(GOTInvasions.THENN, GOTEventSpawner.EventChance.RARE);
		invasionSpawns.addInvasion(GOTInvasions.WILDLING, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.GIANT, GOTEventSpawner.EventChance.RARE);
		
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		
		GOTStructureGiftVillage village = new GOTStructureGiftVillage(this, 0.0f);
		village.affix(GOTWaypoint.Moletown);
		decorator.affix(village);
		GOTStructureCastleBlack bc = new GOTStructureCastleBlack(this, 0.0f);
		bc.affix(GOTWaypoint.CastleBlack);
		decorator.affix(bc);
		GOTStructureShadowTower st = new GOTStructureShadowTower(this, 0.0f);
		st.affix(GOTWaypoint.ShadowTower);
		decorator.affix(st);
		GOTStructureEastWatch ew = new GOTStructureEastWatch(this, 0.0f);
		ew.affix(GOTWaypoint.EastWatch);
		decorator.affix(ew);
		GOTStructureWallGate wall = new GOTStructureWallGate(this, 0.0f);
		wall.affix(GOTWaypoint.CastleBlack, 0, -1);
		decorator.affix(wall);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_GIFT_OLD;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("giftOld");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.NORTH;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public int getWallTop() {
		return 150;
	}
}
