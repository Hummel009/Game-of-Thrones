package got.common.world.map;

import java.util.Random;

import com.google.common.math.IntMath;

import got.GOT;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBezierGenerator {
	public static boolean generateBezier(World world, Random rand, int i, int k, GOTBiome biome, Block[] blocks, byte[] metadata, double[] heightNoise) {
		int chunkX = i & 0xF;
		int chunkZ = k & 0xF;
		int xzIndex = chunkX * 16 + chunkZ;
		int ySize = blocks.length / 256;
		GOTBezierType roadType = biome.getRoadBlock();
		GOTBezierType.BridgeType bridgeType = biome.getBridgeBlock();
		GOTBezierType wallType = biome.getWallBlock();
		int wallTop = biome.getWallTop();
		boolean disableLocations = world.getWorldInfo().getTerrainType() == GOT.worldTypeGOTEmpty;

		boolean wallAt = GOTBeziers.isBezierAt(i, k, GOTBeziers.Type.WALL);
		if (wallAt) {
			int index;
			int j;
			for (j = wallTop; j > 62; --j) {
				index = xzIndex * ySize + j;
				boolean isTop = j == wallTop;
				GOTBezierType.BezierBlock wallblock = wallType.getBlock(rand, biome, isTop, false);
				blocks[index] = wallblock.getBlock();
				metadata[index] = (byte) wallblock.getMeta();
			}
			return true;
		}

		boolean roadAt = GOTBeziers.isBezierAt(i, k, GOTBeziers.Type.ROAD);
		boolean linkerAt = GOTBeziers.isBezierAt(i, k, GOTBeziers.Type.LINKER) && !disableLocations;
		if (roadAt || linkerAt) {
			int index;
			int j;
			int indexLower;
			int roadTop = 0;
			int bridgeBase = 0;
			boolean bridge = false;
			boolean bridgeSlab = false;
			for (j = ySize - 1; j > 0; --j) {
				index = xzIndex * ySize + j;
				Block block = blocks[index];
				if (block.isOpaqueCube()) {
					roadTop = j;
					break;
				}
				if (!block.getMaterial().isLiquid()) {
					continue;
				}
				bridgeBase = roadTop = j + 1;
				int maxBridgeTop = j + 6;
				float bridgeHeight = 0.0f;
				for (int j1 = j - 1; j1 > 0 && blocks[xzIndex * ySize + j1].getMaterial().isLiquid(); --j1) {
					bridgeHeight += 0.5f;
				}
				int bridgeHeightInt = (int) Math.floor(bridgeHeight);
				roadTop += bridgeHeightInt;
				roadTop = Math.min(roadTop, maxBridgeTop);
				if (roadTop >= maxBridgeTop) {
					bridgeSlab = true;
				} else {
					float bridgeHeightR = bridgeHeight - bridgeHeightInt;
					if (bridgeHeightR < 0.5f) {
						bridgeSlab = true;
					}
				}
				bridge = true;
				break;
			}
			if (bridge) {
				GOTBezierType.BezierBlock bridgeBlock = bridgeType.getBlock(rand, false);
				GOTBezierType.BezierBlock bridgeBlockSlab = bridgeType.getBlock(rand, true);
				GOTBezierType.BezierBlock bridgeEdge = bridgeType.getEdge(rand);
				GOTBezierType.BezierBlock bridgeFence = bridgeType.getFence(rand);
				boolean fence = isFenceAt(i, k);
				int index2 = xzIndex * ySize + roadTop;
				if (fence) {
					boolean pillar = isPillarAt(i, k);
					if (pillar) {
						int pillarIndex;
						for (int j2 = roadTop + 4; j2 > 0 && !blocks[pillarIndex = xzIndex * ySize + j2].isOpaqueCube(); --j2) {
							if (j2 >= roadTop + 4) {
								blocks[pillarIndex] = bridgeFence.getBlock();
								metadata[pillarIndex] = (byte) bridgeFence.getMeta();
								continue;
							}
							if (j2 >= roadTop + 3) {
								blocks[pillarIndex] = bridgeBlock.getBlock();
								metadata[pillarIndex] = (byte) bridgeBlock.getMeta();
								continue;
							}
							blocks[pillarIndex] = bridgeEdge.getBlock();
							metadata[pillarIndex] = (byte) bridgeEdge.getMeta();
						}
					} else {
						int support;
						blocks[index2] = bridgeEdge.getBlock();
						metadata[index2] = (byte) bridgeEdge.getMeta();
						int indexUpper = index2 + 1;
						blocks[indexUpper] = bridgeFence.getBlock();
						metadata[indexUpper] = (byte) bridgeFence.getMeta();
						if (roadTop > bridgeBase) {
							int indexLower2 = index2 - 1;
							blocks[indexLower2] = bridgeEdge.getBlock();
							metadata[indexLower2] = (byte) bridgeEdge.getMeta();
						}
						support = bridgeBase + 2;
						if (roadTop - 1 > support) {
							int indexSupport = xzIndex * ySize + support;
							blocks[indexSupport] = bridgeFence.getBlock();
							metadata[indexSupport] = (byte) bridgeFence.getMeta();
						}
					}
				} else {
					if (bridgeSlab) {
						blocks[index2] = bridgeBlockSlab.getBlock();
						metadata[index2] = (byte) bridgeBlockSlab.getMeta();
					} else {
						blocks[index2] = bridgeBlock.getBlock();
						metadata[index2] = (byte) bridgeBlock.getMeta();
					}
					if (roadTop > bridgeBase) {
						indexLower = index2 - 1;
						blocks[indexLower] = bridgeBlock.getBlock();
						metadata[indexLower] = (byte) bridgeBlock.getMeta();
					}
				}
			} else {
				for (j = roadTop; j > roadTop - 4 && j > 0; --j) {
					index = xzIndex * ySize + j;
					float repair = roadType.getRepair();
					if (rand.nextFloat() >= repair) {
						continue;
					}
					boolean isTop = j == roadTop;
					boolean isSlab = false;
					if (isTop && j >= 63) {
						double avgNoise = (heightNoise[index] + heightNoise[index + 1]) / 2.0;
						isSlab = avgNoise < 0.0;
					}
					GOTBezierType.BezierBlock roadblock = roadType.getBlock(rand, biome, isTop, isSlab);
					blocks[index] = roadblock.getBlock();
					metadata[index] = (byte) roadblock.getMeta();
				}
			}
			return true;
		}
		if (roadType.hasFlowers()) {
			int index;
			int i1;
			int roadTop = 0;
			for (int j = ySize - 1; j > 0; --j) {
				index = xzIndex * ySize + j;
				Block block = blocks[index];
				if (!block.isOpaqueCube()) {
					continue;
				}
				roadTop = j;
				break;
			}
			boolean adjRoad = false;
			block5: for (i1 = -2; i1 <= 2; ++i1) {
				for (int k1 = -2; k1 <= 2; ++k1) {
					if (i1 == 0 && k1 == 0 || !GOTBeziers.isBezierAt(i + i1, k + k1, GOTBeziers.Type.ROAD)) {
						continue;
					}
					adjRoad = true;
					break block5;
				}
			}
			if (adjRoad) {
				index = xzIndex * ySize + roadTop + 1;
				BiomeGenBase.FlowerEntry flower = biome.getRandomFlower(rand);
				blocks[index] = flower.block;
				metadata[index] = (byte) flower.metadata;
			} else {
				block7: for (i1 = -3; i1 <= 3; ++i1) {
					for (int k1 = -3; k1 <= 3; ++k1) {
						if (Math.abs(i1) <= 2 && Math.abs(k1) <= 2 || !GOTBeziers.isBezierAt(i + i1, k + k1, GOTBeziers.Type.ROAD)) {
							continue;
						}
						adjRoad = true;
						break block7;
					}
				}
				if (adjRoad) {
					index = xzIndex * ySize + roadTop + 1;
					blocks[index] = Blocks.leaves;
					metadata[index] = 4;
				}
			}
			return true;
		}
		return false;
	}

	public static boolean isBridgeEdgePillar(int i, int k) {
		return GOTBeziers.isBezierAt(i, k, GOTBeziers.Type.ROAD) && isFenceAt(i, k) && isPillarAt(i, k);
	}

	public static boolean isFenceAt(int i, int k) {
		for (int i1 = -1; i1 <= 1; ++i1) {
			for (int k1 = -1; k1 <= 1; ++k1) {
				if (i1 == 0 && k1 == 0 || GOTBeziers.isBezierAt(i + i1, k + k1, GOTBeziers.Type.ROAD)) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public static boolean isPillarAt(int i, int k) {
		int pRange = 8;
		int xmod = IntMath.mod(i, pRange);
		if (IntMath.mod(xmod + IntMath.mod(k, pRange), pRange) == 0) {
			return !isBridgeEdgePillar(i + 1, k - 1) && !isBridgeEdgePillar(i + 1, k + 1);
		}
		return false;
	}
}