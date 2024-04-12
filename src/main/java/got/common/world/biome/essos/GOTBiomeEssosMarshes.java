package got.common.world.biome.essos;

import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeEssosMarshes extends GOTBiomeEssos {
	public GOTBiomeEssosMarshes(int i, boolean major) {
		super(i, major);
		setupMarshFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.SWAMP_LOWLAND, 1.0f);
		unreliableChance = GOTEventSpawner.EventChance.NEVER;
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
		decorator.setSandPerChunk(0);
		decorator.setQuagmirePerChunk(1);
		decorator.setTreesPerChunk(0);
		decorator.setLogsPerChunk(3);
		decorator.setFlowersPerChunk(0);
		decorator.setGrassPerChunk(8);
		decorator.setDoubleGrassPerChunk(8);
		decorator.setCanePerChunk(10);
		decorator.setReedPerChunk(5);
		decorator.setWaterlilyPerChunk(4);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(CONQUEST_SPAWN / 2));
		npcSpawnList.newFactionList(10).add(c0);
	}
}