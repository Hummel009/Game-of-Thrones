package got.common.world.biome;

import got.common.database.GOTBlocks;
import got.common.world.biome.variant.GOTBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.Random;

public class GOTBiomeGenerator {
	private static final Random TERRAIN_RAND = new Random();
	private static final NoiseGeneratorPerlin NOISE_DIRT = new NoiseGeneratorPerlin(new Random(8359286029006L), 1);
	private static final NoiseGeneratorPerlin NOISE_SAND = new NoiseGeneratorPerlin(new Random(473689270272L), 1);
	private static final NoiseGeneratorPerlin NOISE_RED_SAND = new NoiseGeneratorPerlin(new Random(3528569078920702727L), 1);

	private final GOTBiome biome;

	public GOTBiomeGenerator(GOTBiome biome) {
		this.biome = biome;
	}

	public void generateStoneNoise(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = biome.topBlock;
		int topBlockMeta_pre = biome.getTopBlockMeta();
		Block fillerBlock_pre = biome.fillerBlock;
		int fillerBlockMeta_pre = biome.getFillerBlockMeta();
		double d1 = GOTBiome.BIOME_TERRAIN_NOISE.func_151601_a(i * 0.07, k * 0.07);
		double d2 = GOTBiome.BIOME_TERRAIN_NOISE.func_151601_a(i * 0.4, k * 0.4);
		if (d1 + d2 > 0.5) {
			biome.topBlock = Blocks.stone;
			biome.setTopBlockMeta(0);
			biome.fillerBlock = biome.topBlock;
			biome.setFillerBlockMeta(biome.getTopBlockMeta());
		}
		generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		biome.topBlock = topBlock_pre;
		biome.setTopBlockMeta(topBlockMeta_pre);
		biome.fillerBlock = fillerBlock_pre;
		biome.setFillerBlockMeta(fillerBlockMeta_pre);
	}

	public void generateDirtNoise(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = biome.topBlock;
		int topBlockMeta_pre = biome.getTopBlockMeta();
		Block fillerBlock_pre = biome.fillerBlock;
		int fillerBlockMeta_pre = biome.getFillerBlockMeta();
		double d1 = GOTBiome.BIOME_TERRAIN_NOISE.func_151601_a(i * 0.07, k * 0.07);
		double d2 = GOTBiome.BIOME_TERRAIN_NOISE.func_151601_a(i * 0.4, k * 0.4);
		if (d1 + d2 > 0.5) {
			biome.topBlock = Blocks.dirt;
			biome.setTopBlockMeta(1);
			biome.fillerBlock = biome.topBlock;
			biome.setFillerBlockMeta(biome.getTopBlockMeta());
		}
		generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		biome.topBlock = topBlock_pre;
		biome.setTopBlockMeta(topBlockMeta_pre);
		biome.fillerBlock = fillerBlock_pre;
		biome.setFillerBlockMeta(fillerBlockMeta_pre);
	}

	public void generateDirtSandNoise(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = biome.topBlock;
		int topBlockMeta_pre = biome.getTopBlockMeta();
		Block fillerBlock_pre = biome.fillerBlock;
		int fillerBlockMeta_pre = biome.getFillerBlockMeta();
		double d1 = NOISE_DIRT.func_151601_a(i * 0.09, k * 0.09);
		double d2 = NOISE_DIRT.func_151601_a(i * 0.4, k * 0.4);
		double d3 = NOISE_SAND.func_151601_a(i * 0.09, k * 0.09);
		double d4 = NOISE_SAND.func_151601_a(i * 0.4, k * 0.4);
		if (d3 + d4 > 0.6) {
			biome.topBlock = Blocks.sand;
			biome.setTopBlockMeta(0);
			biome.fillerBlock = biome.topBlock;
			biome.setFillerBlockMeta(biome.getTopBlockMeta());
		} else if (d1 + d2 > 0.2) {
			biome.topBlock = Blocks.dirt;
			biome.setTopBlockMeta(1);
			biome.fillerBlock = biome.topBlock;
			biome.setFillerBlockMeta(biome.getTopBlockMeta());
		}
		generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		biome.topBlock = topBlock_pre;
		biome.setTopBlockMeta(topBlockMeta_pre);
		biome.fillerBlock = fillerBlock_pre;
		biome.setFillerBlockMeta(fillerBlockMeta_pre);
	}

	public void generateDirtSandRedSandNoise(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = biome.topBlock;
		int topBlockMeta_pre = biome.getTopBlockMeta();
		Block fillerBlock_pre = biome.fillerBlock;
		int fillerBlockMeta_pre = biome.getFillerBlockMeta();
		double d1 = NOISE_DIRT.func_151601_a(i * 0.002, k * 0.002);
		double d2 = NOISE_DIRT.func_151601_a(i * 0.07, k * 0.07);
		double d3 = NOISE_DIRT.func_151601_a(i * 0.25, k * 0.25);
		double d4 = NOISE_SAND.func_151601_a(i * 0.002, k * 0.002);
		double d5 = NOISE_SAND.func_151601_a(i * 0.07, k * 0.07);
		double d6 = NOISE_SAND.func_151601_a(i * 0.25, k * 0.25);
		double d7 = NOISE_RED_SAND.func_151601_a(i * 0.002, k * 0.002);
		if (d7 + NOISE_RED_SAND.func_151601_a(i * 0.07, k * 0.07) + NOISE_RED_SAND.func_151601_a(i * 0.25, k * 0.25) > 0.9) {
			biome.topBlock = Blocks.sand;
			biome.setTopBlockMeta(1);
			biome.fillerBlock = biome.topBlock;
			biome.setFillerBlockMeta(biome.getTopBlockMeta());
		} else if (d4 + d5 + d6 > 1.2) {
			biome.topBlock = Blocks.sand;
			biome.setTopBlockMeta(0);
			biome.fillerBlock = biome.topBlock;
			biome.setFillerBlockMeta(biome.getTopBlockMeta());
		} else if (d1 + d2 + d3 > 0.4) {
			biome.topBlock = Blocks.dirt;
			biome.setTopBlockMeta(1);
		}
		generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		biome.topBlock = topBlock_pre;
		biome.setTopBlockMeta(topBlockMeta_pre);
		biome.fillerBlock = fillerBlock_pre;
		biome.setFillerBlockMeta(fillerBlockMeta_pre);
	}

	public void generateDesertNoise(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		Block topBlock_pre = biome.topBlock;
		int topBlockMeta_pre = biome.getTopBlockMeta();
		Block fillerBlock_pre = biome.fillerBlock;
		int fillerBlockMeta_pre = biome.getFillerBlockMeta();
		double d1 = GOTBiome.BIOME_TERRAIN_NOISE.func_151601_a(i * 0.07, k * 0.07);
		double d2 = GOTBiome.BIOME_TERRAIN_NOISE.func_151601_a(i * 0.4, k * 0.4);
		d2 *= 0.6;
		if (d1 + d2 > 0.7) {
			biome.topBlock = Blocks.grass;
			biome.setTopBlockMeta(0);
			biome.fillerBlock = Blocks.dirt;
			biome.setFillerBlockMeta(0);
		} else if (d1 + d2 > 0.2) {
			biome.topBlock = Blocks.dirt;
			biome.setTopBlockMeta(1);
			biome.fillerBlock = biome.topBlock;
			biome.setFillerBlockMeta(biome.getTopBlockMeta());
		}
		generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
		biome.topBlock = topBlock_pre;
		biome.setTopBlockMeta(topBlockMeta_pre);
		biome.fillerBlock = fillerBlock_pre;
		biome.setFillerBlockMeta(fillerBlockMeta_pre);
	}

	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
		int seaLevel = 63;
		int fillerDepthBase = (int) (stoneNoise / 4.0 + 5.0 + random.nextDouble() * 0.25);
		int fillerDepth = -1;
		Block top = biome.topBlock;
		byte topMeta = (byte) biome.getTopBlockMeta();
		Block filler = biome.fillerBlock;
		byte fillerMeta = (byte) biome.getFillerBlockMeta();
		if (biome.isEnableRocky() && height >= 90) {
			float hFactor = (height - 90) / 10.0f;
			float thresh = 1.2f - hFactor * 0.2f;
			thresh = Math.max(thresh, 0.0f);
			double d12 = GOTBiome.BIOME_TERRAIN_NOISE.func_151601_a(i * 0.03, k * 0.03);
			if (d12 + GOTBiome.BIOME_TERRAIN_NOISE.func_151601_a(i * 0.3, k * 0.3) > thresh) {
				if (random.nextInt(5) == 0) {
					top = Blocks.gravel;
				} else {
					top = Blocks.stone;
				}
				topMeta = 0;
				filler = Blocks.stone;
				fillerMeta = 0;
			}
		}
		boolean podzol = false;
		if (biome.topBlock == Blocks.grass) {
			float trees = biome.getDecorator().getTreesPerChunk() + 0.1f;
			trees = Math.max(trees, variant.getTreeFactor() * 0.5f);
			if (trees >= 1.0f) {
				float thresh = 0.8f;
				thresh -= trees * 0.15f;
				thresh = Math.max(thresh, 0.0f);
				double d = 0.06;
				double randNoise = GOTBiome.BIOME_TERRAIN_NOISE.func_151601_a(i * d, k * d);
				if (randNoise > thresh) {
					podzol = true;
				}
			}
		}
		if (podzol) {
			TERRAIN_RAND.setSeed(world.getSeed());
			TERRAIN_RAND.setSeed(TERRAIN_RAND.nextLong() + i * 4668095025L + k * 1387590552L ^ world.getSeed());
			float pdzRand = TERRAIN_RAND.nextFloat();
			if (pdzRand < 0.35f) {
				top = Blocks.dirt;
				topMeta = 2;
			} else if (pdzRand < 0.5f) {
				top = Blocks.dirt;
				topMeta = 1;
			} else if (pdzRand < 0.51f) {
				top = Blocks.gravel;
				topMeta = 0;
			}
		}
		if (variant.isHasMarsh() && GOTBiomeVariant.MARSH_NOISE.func_151601_a(i * 0.1, k * 0.1) > -0.1) {
			for (int j = ySize - 1; j >= 0; --j) {
				int index = xzIndex * ySize + j;
				if (blocks[index] == null || blocks[index].getMaterial() != Material.air) {
					if (j != seaLevel - 1 || blocks[index] == Blocks.water) {
						break;
					}
					blocks[index] = Blocks.water;
					break;
				}
			}
		}
		for (int j = ySize - 1; j >= 0; --j) {
			int index = xzIndex * ySize + j;
			if (j <= random.nextInt(5)) {
				blocks[index] = Blocks.bedrock;
			} else {
				Block block = blocks[index];
				if (block == Blocks.air) {
					fillerDepth = -1;
				} else if (block == Blocks.stone) {
					if (fillerDepth == -1) {
						if (fillerDepthBase <= 0) {
							top = Blocks.air;
							topMeta = 0;
							filler = Blocks.stone;
							fillerMeta = 0;
						} else if (j >= seaLevel - 4 && j <= seaLevel + 1) {
							top = biome.topBlock;
							topMeta = (byte) biome.getTopBlockMeta();
							filler = biome.fillerBlock;
							fillerMeta = (byte) biome.getFillerBlockMeta();
						}
						if (j < seaLevel && top == Blocks.air) {
							top = Blocks.water;
							topMeta = 0;
						}
						fillerDepth = fillerDepthBase;
						if (j >= seaLevel - 1) {
							blocks[index] = top;
							meta[index] = topMeta;
						} else {
							blocks[index] = filler;
							meta[index] = fillerMeta;
						}
					} else if (fillerDepth > 0) {
						blocks[index] = filler;
						meta[index] = fillerMeta;
						--fillerDepth;
						if (fillerDepth == 0) {
							boolean sand = false;
							if (filler == Blocks.sand) {
								if (fillerMeta == 1) {
									filler = GOTBlocks.redSandstone;
								} else {
									filler = Blocks.sandstone;
								}
								fillerMeta = 0;
								sand = true;
							}
							if (filler == GOTBlocks.whiteSand) {
								filler = GOTBlocks.whiteSandstone;
								fillerMeta = 0;
								sand = true;
							}
							if (sand) {
								fillerDepth = 10 + random.nextInt(4);
							}
						}
						if (fillerDepth == 0 && biome.fillerBlock != GOTBlocks.rock && filler == biome.fillerBlock) {
							fillerDepth = 6 + random.nextInt(3);
							filler = Blocks.stone;
							fillerMeta = 0;
						}
					}
				}
			}
		}
		int rockDepth = (int) (stoneNoise * 6.0 + 2.0 + random.nextDouble() * 0.25);
		if (biome instanceof GOTBiome.Mountains) {
			((GOTBiome.Mountains) biome).generateMountainTerrain(world, random, blocks, meta, i, k, xzIndex, ySize, height, rockDepth, variant);
		}
		variant.generateVariantTerrain(blocks, meta, i, k);
	}
}