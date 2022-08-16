package got.common.world.biome.essos;

import java.util.ArrayList;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.essos.yiti.GOTStructureYiTiCity;
import got.common.world.structure.other.*;

public class GOTBiomeYiTi extends GOTBiomeEssos {

	public GOTBiomeYiTi(int i, boolean major) {
		super(i, major);
		setupStandartPlainsFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.FLOWERS);
		addBiomeVariant(GOTBiomeVariant.FOREST);
		addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		addBiomeVariant(GOTBiomeVariant.FOREST_CHERRY);
		addBiomeVariant(GOTBiomeVariant.FOREST_LEMON);
		addBiomeVariant(GOTBiomeVariant.FOREST_LIME);
		addBiomeVariant(GOTBiomeVariant.FOREST_CYPRESS);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.CHERRY, 500);
		decorator.addTree(GOTTreeType.POMEGRANATE, 200);
		decorator.addTree(GOTTreeType.LEMON, 100);
		decorator.addTree(GOTTreeType.LIME, 100);
		decorator.clearRandomStructures();
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addRandomStructure(new GOTStructureStoneRuin.RuinYiTi(1, 4), 400);
		decorator.addVillage(new GOTStructureYiTiCity(this, 1.0f));
		invasionSpawns.addInvasion(GOTInvasions.JOGOS, GOTEventSpawner.EventChance.UNCOMMON);
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.YITI_CONQUEST, 4).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.YITI_MILITARY, 10).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(8).add(c0);
		ArrayList<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.JOGOS_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		ArrayList<SpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.MANTICORE, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(2).add(c2);
	}

	@Override
	public boolean disableNoise() {
		return true;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterYiTi;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("yiTi");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.YI_TI;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PAVING;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.COBBLEBRICK;
	}

	@Override
	public int getWallTop() {
		return 90;
	}
}