package got.common.world.biome.essos;

import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.spawning.GOTEventSpawner;

import java.util.ArrayList;

public class GOTBiomeEssosMarshes extends GOTBiomeEssos {
	public GOTBiomeEssosMarshes(int i, boolean major) {
		super(i, major);
		setupMarshFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.SWAMP_LOWLAND);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.ACACIA, 300);
		decorator.addTree(GOTTreeType.ACACIA_DEAD, 1);
		decorator.addTree(GOTTreeType.ALMOND, 5);
		decorator.addTree(GOTTreeType.BAOBAB, 20);
		decorator.addTree(GOTTreeType.CEDAR, 300);
		decorator.addTree(GOTTreeType.CEDAR_LARGE, 150);
		decorator.addTree(GOTTreeType.CYPRESS, 500);
		decorator.addTree(GOTTreeType.CYPRESS_LARGE, 50);
		decorator.addTree(GOTTreeType.DATE_PALM, 50);
		decorator.addTree(GOTTreeType.LEMON, 5);
		decorator.addTree(GOTTreeType.LIME, 5);
		decorator.addTree(GOTTreeType.OAK_SWAMP, 400);
		decorator.addTree(GOTTreeType.OLIVE, 5);
		decorator.addTree(GOTTreeType.OLIVE_LARGE, 10);
		decorator.addTree(GOTTreeType.ORANGE, 5);
		decorator.addTree(GOTTreeType.PALM, 500);
		decorator.addTree(GOTTreeType.PLUM, 5);
		decorator.addTree(GOTTreeType.DRAGONBLOOD, 50);
		decorator.addTree(GOTTreeType.DRAGONBLOOD_LARGE, 1);
		decorator.addTree(GOTTreeType.KANUKA, 50);
		variantChance = 1.0f;
		decorator.sandPerChunk = 0;
		decorator.quagmirePerChunk = 1;
		decorator.treesPerChunk = 0;
		decorator.logsPerChunk = 3;
		decorator.flowersPerChunk = 0;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 8;
		decorator.canePerChunk = 10;
		decorator.reedPerChunk = 5;
		decorator.waterlilyPerChunk = 4;
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}
}