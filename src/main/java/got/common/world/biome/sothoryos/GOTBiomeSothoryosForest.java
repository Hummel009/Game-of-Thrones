package got.common.world.biome.sothoryos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.GOTWorldChunkManager;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GOTBiomeSothoryosForest extends GOTBiomeSothoryosBushland {
	public static NoiseGeneratorPerlin noisePaths1 = new NoiseGeneratorPerlin(new Random(22L), 1);
	public static NoiseGeneratorPerlin noisePaths2 = new NoiseGeneratorPerlin(new Random(11L), 1);

	public GOTBiomeSothoryosForest(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 10;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.grassPerChunk = 4;
		decorator.doubleGrassPerChunk = 1;
		decorator.cornPerChunk = 10;
		npcSpawnList.clear();
		invasionSpawns.clearInvasions();
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		if (isForest()) {
			GOTBiomeVariant variant = ((GOTWorldChunkManager) world.getWorldChunkManager()).getBiomeVariantAt(i + 8, k + 8);
			int grasses = 12;
			grasses = Math.round(grasses * variant.grassFactor);
			for (int l = 0; l < grasses; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int j1 = random.nextInt(128);
				int k1 = k + random.nextInt(16) + 8;
				if (world.getHeightValue(i1, k1) <= 75) {
					continue;
				}
				WorldGenerator grassGen = getRandomWorldGenForGrass(random);
				grassGen.generate(world, random, i1, j1, k1);
			}
			int doubleGrasses = 4;
			doubleGrasses = Math.round(doubleGrasses * variant.grassFactor);
			for (int l = 0; l < doubleGrasses; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int j1 = random.nextInt(128);
				int k1 = k + random.nextInt(16) + 8;
				if (world.getHeightValue(i1, k1) <= 75) {
					continue;
				}
				WorldGenerator grassGen = getRandomWorldGenForDoubleGrass();
				grassGen.generate(world, random, i1, j1, k1);
			}
		}
	}

	@Override
	public boolean enableTermite() {
		return false;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		if (isForest()) {
			double d1 = noisePaths1.func_151601_a(i * 0.008, k * 0.008);
			double d2 = noisePaths2.func_151601_a(i * 0.008, k * 0.008);
			if (d1 > 0.0 && d1 < 0.1 || d2 > 0.0 && d2 < 0.1) {
				topBlock = GOTBlocks.dirtPath;
				topBlockMeta = 1;
			}
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		if (isForest()) {
			topBlock = topBlock_pre;
			topBlockMeta = topBlockMeta_pre;
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterSothoryosForest;
	}

	@Override
	public GOTBiome.GrassBlockAndMeta getRandomGrass(Random random) {
		if (isForest() && random.nextInt(5) != 0) {
			return new GOTBiome.GrassBlockAndMeta(Blocks.tallgrass, 2);
		}
		return super.getRandomGrass(random);
	}

	@Override
	public WorldGenerator getRandomWorldGenForDoubleGrass() {
		if (isForest()) {
			WorldGenDoublePlant generator = new WorldGenDoublePlant();
			generator.func_150548_a(3);
			return generator;
		}
		return super.getRandomWorldGenForDoubleGrass();
	}

	public boolean isForest() {
		return true;
	}
}