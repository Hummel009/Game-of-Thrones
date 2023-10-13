package got.common.world.biome.essos;

import java.util.ArrayList;
import java.util.Collection;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.structure.essos.yiti.GOTStructureYiTiSettlement;
import got.common.world.structure.other.GOTStructureBurntHouse;
import got.common.world.structure.other.GOTStructureRottenHouse;
import got.common.world.structure.other.GOTStructureRuinedHouse;
import got.common.world.structure.other.GOTStructureStoneRuin;

public class GOTBiomeYiTi extends GOTBiomeEssos {
	public GOTBiomeYiTi(int i, boolean major) {
		super(i, major);
		setupStandardPlainsFauna();
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
		decorator.clearStructures();
		decorator.addStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addStructure(new GOTStructureStoneRuin.RuinYiTi(1, 4), 400);
		decorator.addSettlement(new GOTStructureYiTiSettlement(this, 1.0f));
		invasionSpawns.addInvasion(GOTInvasions.JOGOS, GOTEventSpawner.EventChance.UNCOMMON);
		Collection<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.YITI_CONQUEST, 1).setSpawnChance(GOTBiome.SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.YITI_MILITARY, 9).setSpawnChance(GOTBiome.SPAWN));
		npcSpawnList.newFactionList(8).add(c0);
		Collection<SpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.JOGOS_MILITARY, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		Collection<SpawnListContainer> c2 = new ArrayList<>();
		c2.add(GOTBiomeSpawnList.entry(GOTSpawnList.MANTICORE, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN / 4));
		npcSpawnList.newFactionList(2).add(c2);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterYiTi;
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.YI_TI;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_PAVING;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WALL_YITI;
	}

}