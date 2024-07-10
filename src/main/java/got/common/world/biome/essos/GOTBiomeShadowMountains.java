package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTWorldGenAsshaiMoss;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class GOTBiomeShadowMountains extends GOTBiomeShadowLand implements GOTBiome.Mountains {
	private static final NoiseGeneratorPerlin NOISE_DIRT = new NoiseGeneratorPerlin(new Random(389502092662L), 1);
	private static final NoiseGeneratorPerlin NOISE_GRAVEL = new NoiseGeneratorPerlin(new Random(1379468206L), 1);

	public GOTBiomeShadowMountains(int i, boolean major) {
		super(i, major);
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.MOUNTAIN, 1.0f);

		decorator.setGrassPerChunk(1);

		decorator.setBiomeGemFactor(decorator.getBiomeGemFactor() * 2.0f);
		decorator.setBiomeOreFactor(decorator.getBiomeOreFactor() * 2.0f);
		decorator.addOre(new WorldGenMinable(GOTBlocks.oreGlowstone, 4), 8.0f, 0, 48);
		decorator.addOre(new WorldGenMinable(GOTBlocks.oreCobalt, 5), 5.0f, 0, 32);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterShadowMountains;
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int j1;
		int i1;
		int k1;
		super.decorate(world, random, i, k);
		int l;
		int k12;
		int i12;
		int j12;
		if (random.nextInt(60) == 0) {
			for (l = 0; l < 8; ++l) {
				i12 = i + random.nextInt(16) + 8;
				k12 = k + random.nextInt(16) + 8;
				j12 = world.getHeightValue(i12, k12);
				decorator.genTree(world, random, i12, j12, k12);
			}
		}
		if (decorator.getGrassPerChunk() > 0) {
			if (random.nextInt(20) == 0) {
				for (l = 0; l < 6; ++l) {
					i12 = i + random.nextInt(6) + 8;
					if (world.isAirBlock(i12, j12 = world.getHeightValue(i12, k12 = k + random.nextInt(6) + 8), k12) || !GOTBlocks.asshaiThorn.canBlockStay(world, i12, j12, k12)) {
						world.setBlock(i12, j12, k12, GOTBlocks.asshaiThorn, 0, 2);
					}
				}
			}
			if (random.nextInt(20) == 0 && world.isAirBlock(i1 = i + random.nextInt(16) + 8, j1 = world.getHeightValue(i1, k1 = k + random.nextInt(16) + 8), k1) && GOTBlocks.asshaiMoss.canBlockStay(world, i1, j1, k1)) {
				new GOTWorldGenAsshaiMoss().generate(world, random, i1, j1, k1);
			}
		}
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = topBlock;
		int topBlockMeta_pre = topBlockMeta;
		Block fillerBlock_pre = fillerBlock;
		int fillerBlockMeta_pre = fillerBlockMeta;
		double d1 = NOISE_DIRT.func_151601_a(i * 0.08, k * 0.08);
		double d2 = NOISE_DIRT.func_151601_a(i * 0.4, k * 0.4);
		double d3 = NOISE_GRAVEL.func_151601_a(i * 0.08, k * 0.08);
		if (d3 + NOISE_GRAVEL.func_151601_a(i * 0.4, k * 0.4) > 0.8) {
			topBlock = GOTBlocks.basaltGravel;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		} else if (d1 + d2 > 0.5) {
			topBlock = GOTBlocks.asshaiDirt;
			topBlockMeta = 0;
			fillerBlock = topBlock;
			fillerBlockMeta = topBlockMeta;
		}
		super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		topBlock = topBlock_pre;
		topBlockMeta = topBlockMeta_pre;
		fillerBlock = fillerBlock_pre;
		fillerBlockMeta = fillerBlockMeta_pre;
	}

	@Override
	public void generateMountainTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, int xzIndex, int ySize, int height, int rockDepth, GOTBiomeVariant variant) {
		for (int j = ySize - 1; j >= 0; --j) {
			int index = xzIndex * ySize + j;
			Block block = blocks[index];
			if (block == Blocks.stone) {
				blocks[index] = GOTBlocks.rock;
				meta[index] = 0;
			}
		}
	}

	@Override
	public GrassBlockAndMeta getRandomGrass(Random random) {
		return new GrassBlockAndMeta(GOTBlocks.asshaiGrass, 0);
	}
}