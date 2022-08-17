package got.common.world.biome.essos;

import java.util.ArrayList;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeTropicalForest extends GOTBiomeEssos {
	public GOTBiomeTropicalForest(int i, boolean major) {
		super(i, major);
		setupJungleFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.CLEARING, 0.2f);
		decorator.treesPerChunk = 20;
		decorator.flowersPerChunk = 4;
		decorator.doubleFlowersPerChunk = 4;
		decorator.grassPerChunk = 15;
		decorator.doubleGrassPerChunk = 10;
		decorator.canePerChunk = 5;
		decorator.cornPerChunk = 10;
		decorator.logsPerChunk = 0;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.JUNGLE, 1000);
		decorator.addTree(GOTTreeType.JUNGLE_LARGE, 500);
		decorator.addTree(GOTTreeType.MAHOGANY, 500);
		decorator.addTree(GOTTreeType.JUNGLE_SHRUB, 1000);
		decorator.addTree(GOTTreeType.MANGO, 20);
		decorator.addTree(GOTTreeType.BANANA, 50);
		decorator.addGem(new WorldGenMinable(GOTRegistry.oreGem, 4, 8, Blocks.stone), 3.0f, 0, 48);
		ArrayList<SpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.JUNGLE_SCORPION, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
	}

	@Override
	public boolean disableNoise() {
		return true;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterTropicalForest;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("tropicalForest");
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}