package got.common.world.biome.westeros;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.fixed.*;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.westeros.gift.GOTStructureGiftVillage;

public class GOTBiomeGiftOldFlat extends GOTBiomeGiftOld {
	public GOTBiomeGiftOldFlat(int i, boolean major) {
		super(i, major);
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.GIFT_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.GIFT_GUARDIAN, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		SpawnListContainer[] container2 = new SpawnListContainer[2];
		container2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		container2[1] = GOTBiomeSpawnList.entry(GOTSpawnList.WILDING_GIANT, 1).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container2);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureGiftVillage village = new GOTStructureGiftVillage(this, 0.0f);
		village.affix(GOTWaypoint.Moletown);
		decorator.addVillage(village);
		GOTStructureCastleBlack bc = new GOTStructureCastleBlack(this, 0.0f);
		bc.affix(GOTWaypoint.CastleBlack);
		decorator.addVillage(bc);
		GOTStructureShadowTower st = new GOTStructureShadowTower(this, 0.0f);
		st.affix(GOTWaypoint.ShadowTower);
		decorator.addVillage(st);
		GOTStructureEastWatch ew = new GOTStructureEastWatch(this, 0.0f);
		ew.affix(GOTWaypoint.EastWatch);
		decorator.addVillage(ew);
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}
}