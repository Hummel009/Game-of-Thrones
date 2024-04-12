package got.common.world.map;

import got.common.util.GOTCrashHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenRavine;

import java.util.Random;

public class GOTMapGenRavine extends MapGenRavine {
	private final float[] ravineNoise = new float[1024];

	@Override
	public void digBlock(Block[] data, int index, int i, int j, int k, int chunkX, int chunkZ, boolean topBlock) {
		BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(worldObj, i + chunkX * 16, k + chunkZ * 16);
		Block top = biome.topBlock;
		Block filler = biome.fillerBlock;
		Block block = data[index];
		if (GOTMapGenCaves.isTerrainBlock(block, biome)) {
			if (j < 10) {
				data[index] = Blocks.flowing_lava;
			} else {
				data[index] = Blocks.air;
				if (topBlock && data[index - 1] == filler) {
					data[index - 1] = top;
				}
			}
		}
	}

	@Override
	public void func_151538_a(World world, int i, int k, int chunkX, int chunkZ, Block[] blocks) {
		GOTCrashHandler.getBiomeGenForCoords(worldObj, chunkX * 16, chunkZ * 16);
		if (rand.nextBoolean()) {
			super.func_151538_a(world, i, k, chunkX, chunkZ, blocks);
		}
	}

	@Override
	public void func_151540_a(long seed, int chunkX, int chunkZ, Block[] blocks, double d, double d1, double d2, float f, float ravineAngle, float f2, int intPar1, int intPar2, double increase) {
		int intPar21 = intPar2;
		double d3 = d;
		double d15 = d1;
		double d21 = d2;
		float f21 = f2;
		float ravineAngle1 = ravineAngle;
		int intPar11 = intPar1;
		Random random = new Random(seed);
		double chunkCentreX = chunkX * 16 + 8;
		double chunkCentreZ = chunkZ * 16 + 8;
		float f3 = 0.0f;
		float f4 = 0.0f;
		if (intPar21 <= 0) {
			int j1 = range * 16 - 16;
			intPar21 = j1 - random.nextInt(j1 / 4);
		}
		boolean flag = false;
		if (intPar11 == -1) {
			intPar11 = intPar21 / 2;
			flag = true;
		}
		float f5 = 1.0f;
		for (int k1 = 0; k1 < 256; ++k1) {
			if (k1 == 0 || random.nextInt(3) == 0) {
				f5 = 1.0f + random.nextFloat() * random.nextFloat() * 1.0f;
			}
			ravineNoise[k1] = f5 * f5;
		}
		while (intPar11 < intPar21) {
			double d6 = 1.5 + MathHelper.sin(intPar11 * 3.1415927f / intPar21) * f * 1.0f;
			double d7 = d6 * increase;
			d6 *= random.nextFloat() * 0.25 + 0.75;
			d7 *= random.nextFloat() * 0.25 + 0.75;
			float f6 = MathHelper.cos(f21);
			float f7 = MathHelper.sin(f21);
			d3 += MathHelper.cos(ravineAngle1) * f6;
			d15 += f7;
			d21 += MathHelper.sin(ravineAngle1) * f6;
			f21 *= 0.7f;
			f21 += f4 * 0.05f;
			ravineAngle1 += f3 * 0.05f;
			f4 *= 0.8f;
			f3 *= 0.5f;
			f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0f;
			f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0f;
			if (flag || random.nextInt(4) != 0) {
				double d8 = d3 - chunkCentreX;
				double d9 = d21 - chunkCentreZ;
				double d10 = intPar21 - intPar11;
				double d11 = f + 2.0f + 16.0f;
				if (d8 * d8 + d9 * d9 - d10 * d10 > d11 * d11) {
					return;
				}
				if (d3 >= chunkCentreX - 16.0 - d6 * 2.0 && d21 >= chunkCentreZ - 16.0 - d6 * 2.0 && d3 <= chunkCentreX + 16.0 + d6 * 2.0 && d21 <= chunkCentreZ + 16.0 + d6 * 2.0) {
					int i1;
					int xMin = MathHelper.floor_double(d3 - d6) - chunkX * 16 - 1;
					int xMax = MathHelper.floor_double(d3 + d6) - chunkX * 16 + 1;
					int yMin = MathHelper.floor_double(d15 - d7) - 1;
					int yMax = MathHelper.floor_double(d15 + d7) + 1;
					int zMin = MathHelper.floor_double(d21 - d6) - chunkZ * 16 - 1;
					int zMax = MathHelper.floor_double(d21 + d6) - chunkZ * 16 + 1;
					xMin = Math.max(xMin, 0);
					xMax = Math.min(xMax, 16);
					yMin = Math.max(yMin, 1);
					yMax = Math.min(yMax, 120);
					zMin = Math.max(zMin, 0);
					zMax = Math.min(zMax, 16);
					boolean isWater = false;
					block2:
					for (i1 = xMin; i1 < xMax; ++i1) {
						for (int k1 = zMin; k1 < zMax; ++k1) {
							for (int j1 = yMax + 1; j1 >= yMin - 1; --j1) {
								int blockIndex = (i1 * 16 + k1) * 256 + j1;
								if (j1 >= 256) {
									continue;
								}
								if (isOceanBlock(blocks, blockIndex, i1, j1, k1, chunkX, chunkZ)) {
									isWater = true;
								}
								if (j1 != yMin - 1 && i1 != xMin && i1 != xMax - 1 && k1 != zMin && k1 != zMax - 1) {
									j1 = yMin;
								}
								if (isWater) {
									break block2;
								}
							}
						}
					}
					if (!isWater) {
						for (i1 = xMin; i1 < xMax; ++i1) {
							double d12 = (i1 + chunkX * 16 + 0.5 - d3) / d6;
							for (int k1 = zMin; k1 < zMax; ++k1) {
								double d13 = (k1 + chunkZ * 16 + 0.5 - d21) / d6;
								int blockIndex = (i1 * 16 + k1) * 256 + yMax;
								boolean topBlock = false;
								if (d12 * d12 + d13 * d13 >= 1.0) {
									continue;
								}
								for (int j1 = yMax - 1; j1 >= yMin; --j1) {
									double d14 = (j1 + 0.5 - d15) / d7;
									if ((d12 * d12 + d13 * d13) * ravineNoise[j1] + d14 * d14 / 6.0 < 1.0) {
										if (isTopBlock(blocks, blockIndex, i1, k1, chunkX, chunkZ)) {
											topBlock = true;
										}
										digBlock(blocks, blockIndex, i1, j1, k1, chunkX, chunkZ, topBlock);
									}
									--blockIndex;
								}
							}
						}
						if (flag) {
							break;
						}
					}
				}
			}
			++intPar11;
		}
	}

	private boolean isTopBlock(Block[] data, int index, int i, int k, int chunkX, int chunkZ) {
		BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(worldObj, i + chunkX * 16, k + chunkZ * 16);
		return data[index] == biome.topBlock;
	}
}