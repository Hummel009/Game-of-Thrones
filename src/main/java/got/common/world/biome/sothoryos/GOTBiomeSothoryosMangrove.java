package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTSpawnList;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;

import java.util.ArrayList;

public class GOTBiomeSothoryosMangrove extends GOTBiomeSothoryosJungle {
	public GOTBiomeSothoryosMangrove(int i, boolean major) {
		super(i, major);
		setupMarshFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.SWAMP_LOWLAND);
		variantChance = 1.0f;
		decorator.sandPerChunk = 0;
		decorator.quagmirePerChunk = 1;
		decorator.treesPerChunk = 40;
		decorator.logsPerChunk = 2;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 8;
		decorator.flowersPerChunk = 0;
		decorator.canePerChunk = 10;
		decorator.reedPerChunk = 2;
		decorator.dryReedChance = 1.0f;
		decorator.clearSettlements();
		decorator.clearStructures();
		npcSpawnList.clear();
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.JUNGLE, 1000);
		decorator.addTree(GOTTreeType.JUNGLE_LARGE, 500);
		decorator.addTree(GOTTreeType.MAHOGANY, 500);
		decorator.addTree(GOTTreeType.MANGROVE, 500);
		decorator.addTree(GOTTreeType.JUNGLE_SHRUB, 1000);
		decorator.addTree(GOTTreeType.MANGO, 20);
		decorator.addTree(GOTTreeType.BANANA, 50);
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.CROCODILE, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN / 2));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosMangrove;
	}

}