package got.common.world.biome.sothoryos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.*;
import got.common.world.structure.sothoryos.summer.GOTStructureSummerVillage;

public class GOTBiomeSummerIslands extends GOTBiomeSothoryosJungle {
	public GOTBiomeSummerIslands(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 1;
		npcSpawnList.clear();
		decorator.clearVillages();
		decorator.clearRandomStructures();
		SpawnListContainer[] c0 = new SpawnListContainer[1];
		c0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.SUMMER_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(c0);
		SpawnListContainer[] c1 = new SpawnListContainer[1];
		c1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.GHISCAR_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(c1);
		SpawnListContainer[] c11 = new SpawnListContainer[1];
		c11[0] = GOTBiomeSpawnList.entry(GOTSpawnList.MANTICORE, 1).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(c11);
		GOTStructureSummerVillage village = new GOTStructureSummerVillage(this, 1.0f);
		village.affix(GOTWaypoint.LastLament);
		village.affix(GOTWaypoint.LotusPoint);
		village.affix(GOTWaypoint.PearlPalace);
		village.affix(GOTWaypoint.TallTreesTown);
		village.affix(GOTWaypoint.Ebonhead);
		village.affix(GOTWaypoint.RedFlowerVale);
		village.affix(GOTWaypoint.GoldenHead);
		village.affix(GOTWaypoint.Xon);
		village.affix(GOTWaypoint.Doquu);
		village.affix(GOTWaypoint.SweetLotusVale);
		village.affix(GOTWaypoint.LizardHead);
		village.affix(GOTWaypoint.Omboru);
		village.affix(GOTWaypoint.Koj);
		village.affix(GOTWaypoint.Walano);
		village.affix(GOTWaypoint.Naath);
		decorator.affix(village);
		invasionSpawns.addInvasion(GOTInvasions.GHISCAR, GOTEventSpawner.EventChance.COMMON);
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addRandomStructure(new GOTStructureStoneRuin.SANDSTONE(1, 4), 400);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_SUMMER_ISLANDS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("summerIslands");
	}
}