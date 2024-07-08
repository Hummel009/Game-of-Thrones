package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTSpawnListContainer;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.ArrayList;
import java.util.Collection;

public class GOTBiomeTropicalForest extends GOTBiomeEssos {
	public GOTBiomeTropicalForest(int i, boolean major) {
		super(i, major);
		setupJungleFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.CLEARING, 0.2f);
		decorator.setTreesPerChunk(20);
		decorator.setFlowersPerChunk(4);
		decorator.setDoubleFlowersPerChunk(4);
		decorator.setGrassPerChunk(15);
		decorator.setDoubleGrassPerChunk(10);
		decorator.setCanePerChunk(5);
		decorator.setCornPerChunk(10);
		decorator.setLogsPerChunk(0);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.JUNGLE, 1000);
		decorator.addTree(GOTTreeType.JUNGLE_LARGE, 500);
		decorator.addTree(GOTTreeType.MAHOGANY, 500);
		decorator.addTree(GOTTreeType.MANGO, 20);
		decorator.addTree(GOTTreeType.BANANA, 50);
		decorator.addGem(new WorldGenMinable(GOTBlocks.oreGem, 4, 8, Blocks.stone), 3.0f, 0, 48);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.JUNGLE_SCORPION, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterTropicalForest;
	}
}