package got.common.world.structure.other;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.entity.westeros.GOTEntityMaester;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.structure.westeros.common.GOTStructureWesterosBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureTower extends GOTStructureBaseSettlement {
	public GOTStructureTower(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 2;
		fixedSettlementChunkRadius = 2;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance<GOTStructureTower> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos) {
		return new Instance(this, world, i, k, random, loc, filler, spawnInfos);
	}

	public static class GOTStructureTowerBase extends GOTStructureWesterosBase {
		public GOTStructureTowerBase(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			int i1;
			int j1;
			int step;
			int k1;
			int i12;
			int k12;
			int distSq;
			int i13;
			int k13;
			int j12;
			int k14;
			int j2;
			int j13;
			int radius = 6;
			int radiusPlusOne = radius + 1;
			setOriginAndRotation(world, i, j, k, rotation, radiusPlusOne);
			int sections = 6;
			int sectionHeight = 6;
			int topHeight = sections * sectionHeight;
			double radiusD = radius - 0.5;
			double radiusDPlusOne = radiusD + 1.0;
			int wallThresholdMin = (int) (radiusD * radiusD);
			int wallThresholdMax = (int) (radiusDPlusOne * radiusDPlusOne);
			if (restrictions) {
				int minHeight = 0;
				int maxHeight = 0;
				for (i13 = -radiusPlusOne; i13 <= radiusPlusOne; ++i13) {
					for (k13 = -radiusPlusOne; k13 <= radiusPlusOne; ++k13) {
						int distSq2 = i13 * i13 + k13 * k13;
						if (distSq2 >= wallThresholdMax) {
							continue;
						}
						int j14 = getTopBlock(world, i13, k13) - 1;
						Block block = getBlock(world, i13, j14, k13);
						if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
							return false;
						}
						if (j14 < minHeight) {
							minHeight = j14;
						}
						if (j14 > maxHeight) {
							maxHeight = j14;
						}
						if (maxHeight - minHeight <= 16) {
							continue;
						}
						return false;
					}
				}
			}
			for (i1 = -radius; i1 <= radius; ++i1) {
				for (k1 = -radius; k1 <= radius; ++k1) {
					distSq = i1 * i1 + k1 * k1;
					if (distSq >= wallThresholdMax) {
						continue;
					}
					for (j13 = 0; (j13 == 0 || !isOpaque(world, i1, j13, k1)) && getY(j13) >= 0; --j13) {
						if (distSq >= wallThresholdMin) {
							placeRandomBrick(world, random, i1, j13, k1);
						} else {
							setBlockAndMetadata(world, i1, j13, k1, GOTBlocks.brick1, 1);
						}
						setGrassToDirt(world, i1, j13 - 1, k1);
					}
				}
			}
			for (int l = 0; l < sections; ++l) {
				int step2;
				int sectionBase = l * sectionHeight;
				for (j1 = sectionBase + 1; j1 <= sectionBase + sectionHeight; ++j1) {
					for (i12 = -radius; i12 <= radius; ++i12) {
						for (int k15 = -radius; k15 <= radius; ++k15) {
							int distSq3 = i12 * i12 + k15 * k15;
							if (distSq3 >= wallThresholdMax) {
								continue;
							}
							if (distSq3 >= wallThresholdMin) {
								placeRandomBrick(world, random, i12, j1, k15);
								continue;
							}
							if (j1 == sectionBase + sectionHeight) {
								setBlockAndMetadata(world, i12, j1, k15, GOTBlocks.brick1, 1);
								continue;
							}
							setAir(world, i12, j1, k15);
						}
					}
				}
				for (j1 = sectionBase + 2; j1 <= sectionBase + 3; ++j1) {
					for (k13 = -1; k13 <= 1; ++k13) {
						setBlockAndMetadata(world, -radius, j1, k13, Blocks.iron_bars, 0);
						setBlockAndMetadata(world, radius, j1, k13, Blocks.iron_bars, 0);
					}
					for (i12 = -1; i12 <= 1; ++i12) {
						setBlockAndMetadata(world, i12, j1, -radius, Blocks.iron_bars, 0);
					}
				}
				if (l > 0) {
					setAir(world, 0, sectionBase, 0);
					for (i13 = -1; i13 <= 1; ++i13) {
						for (k13 = -1; k13 <= 1; ++k13) {
							int i2 = Math.abs(i13);
							int k2 = Math.abs(k13);
							if (i2 == 1 || k2 == 1) {
								setBlockAndMetadata(world, i13, sectionBase + 1, k13, GOTBlocks.wallStone1, 3);
							}
							if (i2 != 1 || k2 != 1) {
								continue;
							}
							setBlockAndMetadata(world, i13, sectionBase + 2, k13, GOTBlocks.wallStone1, 3);
						}
					}
				} else {
					for (i13 = -1; i13 <= 1; ++i13) {
						for (j13 = sectionBase + 1; j13 <= sectionBase + 3; ++j13) {
							setAir(world, i13, j13, -radius);
						}
						setBlockAndMetadata(world, i13, sectionBase, -radius, GOTBlocks.brick1, 1);
					}
					placeRandomStairs(world, random, -1, sectionBase + 3, -radius, 4);
					placeRandomStairs(world, random, 1, sectionBase + 3, -radius, 5);
					for (i13 = -5; i13 <= 5; ++i13) {
						setBlockAndMetadata(world, i13, sectionBase, 0, GOTBlocks.brick1, 1);
					}
					for (k14 = -6; k14 <= 3; ++k14) {
						setBlockAndMetadata(world, 0, sectionBase, k14, GOTBlocks.brick1, 1);
					}
					setBlockAndMetadata(world, 0, sectionBase + 1, 0, GOTBlocks.brick1, 1);
					setBlockAndMetadata(world, 0, sectionBase + 2, 0, GOTBlocks.wallStone1, 3);
				}
				for (j1 = sectionBase + 1; j1 <= sectionBase + 5; ++j1) {
					setBlockAndMetadata(world, -2, j1, -5, GOTBlocks.pillar1, 6);
					setBlockAndMetadata(world, 2, j1, -5, GOTBlocks.pillar1, 6);
					setBlockAndMetadata(world, 5, j1, -2, GOTBlocks.pillar1, 6);
					setBlockAndMetadata(world, 5, j1, 2, GOTBlocks.pillar1, 6);
					setBlockAndMetadata(world, -3, j1, 4, GOTBlocks.pillar1, 6);
					setBlockAndMetadata(world, 3, j1, 4, GOTBlocks.pillar1, 6);
					setBlockAndMetadata(world, -5, j1, -2, GOTBlocks.pillar1, 6);
					setBlockAndMetadata(world, -5, j1, 2, GOTBlocks.pillar1, 6);
				}
				setBlockAndMetadata(world, -3, sectionBase + 4, 3, Blocks.torch, 4);
				setBlockAndMetadata(world, 3, sectionBase + 4, 3, Blocks.torch, 4);
				setBlockAndMetadata(world, 4, sectionBase + 4, -2, Blocks.torch, 1);
				setBlockAndMetadata(world, 4, sectionBase + 4, 2, Blocks.torch, 1);
				setBlockAndMetadata(world, -2, sectionBase + 4, -4, Blocks.torch, 3);
				setBlockAndMetadata(world, 2, sectionBase + 4, -4, Blocks.torch, 3);
				setBlockAndMetadata(world, -4, sectionBase + 4, -2, Blocks.torch, 2);
				setBlockAndMetadata(world, -4, sectionBase + 4, 2, Blocks.torch, 2);
				setBlockAndMetadata(world, -3, sectionBase + 5, 3, GOTBlocks.stairsAndesiteBrick, 6);
				setBlockAndMetadata(world, 3, sectionBase + 5, 3, GOTBlocks.stairsAndesiteBrick, 6);
				setBlockAndMetadata(world, 4, sectionBase + 5, -2, GOTBlocks.stairsAndesiteBrick, 5);
				setBlockAndMetadata(world, 5, sectionBase + 5, -1, GOTBlocks.stairsAndesiteBrick, 7);
				setBlockAndMetadata(world, 5, sectionBase + 5, 1, GOTBlocks.stairsAndesiteBrick, 6);
				setBlockAndMetadata(world, 4, sectionBase + 5, 2, GOTBlocks.stairsAndesiteBrick, 5);
				setBlockAndMetadata(world, -2, sectionBase + 5, -4, GOTBlocks.stairsAndesiteBrick, 7);
				setBlockAndMetadata(world, -1, sectionBase + 5, -5, GOTBlocks.stairsAndesiteBrick, 4);
				setBlockAndMetadata(world, 1, sectionBase + 5, -5, GOTBlocks.stairsAndesiteBrick, 5);
				setBlockAndMetadata(world, 2, sectionBase + 5, -4, GOTBlocks.stairsAndesiteBrick, 7);
				setBlockAndMetadata(world, -4, sectionBase + 5, -2, GOTBlocks.stairsAndesiteBrick, 4);
				setBlockAndMetadata(world, -5, sectionBase + 5, -1, GOTBlocks.stairsAndesiteBrick, 7);
				setBlockAndMetadata(world, -5, sectionBase + 5, 1, GOTBlocks.stairsAndesiteBrick, 6);
				setBlockAndMetadata(world, -4, sectionBase + 5, 2, GOTBlocks.stairsAndesiteBrick, 4);
				for (step2 = 0; step2 <= 2; ++step2) {
					setBlockAndMetadata(world, 1 - step2, sectionBase + 1 + step2, 4, GOTBlocks.stairsAndesiteBrick, 0);
					for (j13 = sectionBase + 1; j13 <= sectionBase + step2; ++j13) {
						setBlockAndMetadata(world, 1 - step2, j13, 4, GOTBlocks.brick1, 1);
					}
				}
				for (k14 = 4; k14 <= 5; ++k14) {
					for (j13 = sectionBase + 1; j13 <= sectionBase + 3; ++j13) {
						setBlockAndMetadata(world, -2, j13, k14, GOTBlocks.brick1, 1);
					}
				}
				for (i13 = -2; i13 <= 0; ++i13) {
					setAir(world, i13, sectionBase + sectionHeight, 5);
				}
				for (step2 = 0; step2 <= 2; ++step2) {
					setBlockAndMetadata(world, -1 + step2, sectionBase + 4 + step2, 5, GOTBlocks.stairsAndesiteBrick, 1);
					setBlockAndMetadata(world, -1 + step2, sectionBase + 3 + step2, 5, GOTBlocks.brick1, 1);
					setBlockAndMetadata(world, -1 + step2, sectionBase + 2 + step2, 5, GOTBlocks.stairsAndesiteBrick, 4);
				}
				setBlockAndMetadata(world, 2, sectionBase + 5, 5, GOTBlocks.stairsAndesiteBrick, 4);
			}
			placeChest(world, random, -1, 1, 5, GOTBlocks.chestStone, 0, GOTChestContents.TREASURE);
			for (k12 = -3; k12 <= 3; k12 += 6) {
				for (step = 0; step <= 3; ++step) {
					placeBrickSupports(world, random, -9 + step, k12);
					placeBrickSupports(world, random, 9 - step, k12);
					placeRandomStairs(world, random, -9 + step, 1 + step * 2, k12, 1);
					placeRandomStairs(world, random, 9 - step, 1 + step * 2, k12, 0);
					for (j1 = 1; j1 <= step * 2; ++j1) {
						placeRandomBrick(world, random, -9 + step, j1, k12);
						placeRandomBrick(world, random, 9 - step, j1, k12);
					}
				}
			}
			for (i1 = -3; i1 <= 3; i1 += 6) {
				for (step = 0; step <= 3; ++step) {
					placeBrickSupports(world, random, i1, -9 + step);
					placeBrickSupports(world, random, i1, 9 - step);
					placeRandomStairs(world, random, i1, 1 + step * 2, -9 + step, 2);
					placeRandomStairs(world, random, i1, 1 + step * 2, 9 - step, 3);
					for (j1 = 1; j1 <= step * 2; ++j1) {
						placeRandomBrick(world, random, i1, j1, -9 + step);
						placeRandomBrick(world, random, i1, j1, 9 - step);
					}
				}
			}
			for (i1 = -radius; i1 <= radius; ++i1) {
				for (k1 = -radius; k1 <= radius; ++k1) {
					distSq = i1 * i1 + k1 * k1;
					if (distSq >= wallThresholdMax || distSq < (int) Math.pow(radiusD - 0.25, 2.0)) {
						continue;
					}
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					setBlockAndMetadata(world, i1, topHeight + 1, k1, GOTBlocks.wallStone1, 3);
					if (i2 < 3 || k2 < 3) {
						continue;
					}
					setBlockAndMetadata(world, i1, topHeight + 2, k1, GOTBlocks.wallStone1, 3);
					if (i2 != 4 || k2 != 4) {
						continue;
					}
					setBlockAndMetadata(world, i1, topHeight + 3, k1, GOTBlocks.wallStone1, 3);
					setBlockAndMetadata(world, i1, topHeight + 4, k1, GOTBlocks.wallStone1, 3);
					setBlockAndMetadata(world, i1, topHeight + 5, k1, Blocks.torch, 5);
				}
			}
			setAir(world, -2, topHeight + 1, 5);
			for (i1 = -2; i1 <= 2; i1 += 4) {
				for (step = 0; step <= 4; ++step) {
					j1 = topHeight + 1 + step * 2;
					k13 = -9 + step;
					placeRandomStairs(world, random, i1, j1 - 2, k13, 7);
					for (j2 = j1 - 1; j2 <= j1 + 1; ++j2) {
						placeRandomBrick(world, random, i1, j2, k13);
					}
					placeRandomStairs(world, random, i1, j1 + 2, k13, 2);
					k13 = 9 - step;
					placeRandomStairs(world, random, i1, j1 - 2, k13, 6);
					for (j2 = j1 - 1; j2 <= j1 + 1; ++j2) {
						placeRandomBrick(world, random, i1, j2, k13);
					}
					placeRandomStairs(world, random, i1, j1 + 2, k13, 3);
				}
				for (j12 = topHeight - 4; j12 <= topHeight + 2; ++j12) {
					for (k14 = -9; k14 <= -8; ++k14) {
						placeRandomBrick(world, random, i1, j12, k14);
					}
					for (k14 = 8; k14 <= 9; ++k14) {
						placeRandomBrick(world, random, i1, j12, k14);
					}
				}
				placeRandomBrick(world, random, i1, topHeight - 1, -7);
				placeRandomBrick(world, random, i1, topHeight, -7);
				setBlockAndMetadata(world, i1, topHeight + 1, -7, GOTBlocks.wallStone1, 3);
				placeRandomBrick(world, random, i1, topHeight - 1, 7);
				placeRandomBrick(world, random, i1, topHeight, 7);
				setBlockAndMetadata(world, i1, topHeight + 1, 7, GOTBlocks.wallStone1, 3);
				placeRandomStairs(world, random, i1, topHeight - 4, -9, 6);
				placeRandomStairs(world, random, i1, topHeight - 5, -8, 6);
				placeRandomStairs(world, random, i1, topHeight - 4, 9, 7);
				placeRandomStairs(world, random, i1, topHeight - 5, 8, 7);
			}
			for (k12 = -2; k12 <= 2; k12 += 4) {
				for (step = 0; step <= 4; ++step) {
					j1 = topHeight + 1 + step * 2;
					i12 = -9 + step;
					placeRandomStairs(world, random, i12, j1 - 2, k12, 4);
					for (j2 = j1 - 1; j2 <= j1 + 1; ++j2) {
						placeRandomBrick(world, random, i12, j2, k12);
					}
					placeRandomStairs(world, random, i12, j1 + 2, k12, 1);
					i12 = 9 - step;
					placeRandomStairs(world, random, i12, j1 - 2, k12, 5);
					for (j2 = j1 - 1; j2 <= j1 + 1; ++j2) {
						placeRandomBrick(world, random, i12, j2, k12);
					}
					placeRandomStairs(world, random, i12, j1 + 2, k12, 0);
				}
				for (j12 = topHeight - 4; j12 <= topHeight + 2; ++j12) {
					for (i13 = -9; i13 <= -8; ++i13) {
						placeRandomBrick(world, random, i13, j12, k12);
					}
					for (i13 = 8; i13 <= 9; ++i13) {
						placeRandomBrick(world, random, i13, j12, k12);
					}
				}
				placeRandomBrick(world, random, -7, topHeight - 1, k12);
				placeRandomBrick(world, random, -7, topHeight, k12);
				setBlockAndMetadata(world, -7, topHeight + 1, k12, GOTBlocks.wallStone1, 3);
				placeRandomBrick(world, random, 7, topHeight - 1, k12);
				placeRandomBrick(world, random, 7, topHeight, k12);
				setBlockAndMetadata(world, 7, topHeight + 1, k12, GOTBlocks.wallStone1, 3);
				placeRandomStairs(world, random, -9, topHeight - 4, k12, 5);
				placeRandomStairs(world, random, -8, topHeight - 5, k12, 5);
				placeRandomStairs(world, random, 9, topHeight - 4, k12, 4);
				placeRandomStairs(world, random, 8, topHeight - 5, k12, 4);
			}
			spawnNPCAndSetHome(new GOTEntityMaester(world), world, 0, topHeight + 1, 0, 16);
			setBlockAndMetadata(world, 0, topHeight + 1, -4, GOTBlocks.commandTable, 0);
			return true;
		}

		public void placeBrickSupports(World world, Random random, int i, int k) {
			int j = 0;
			while (!isOpaque(world, i, j, k) && getY(j) >= 0) {
				placeRandomBrick(world, random, i, j, k);
				setGrassToDirt(world, i, j - 1, k);
				--j;
			}
		}

		public void placeRandomBrick(World world, Random random, int i, int j, int k) {
			if (random.nextInt(4) == 0) {
				setBlockAndMetadata(world, i, j, k, GOTBlocks.brick1, 3);
			} else {
				setBlockAndMetadata(world, i, j, k, GOTBlocks.brick1, 1);
			}
		}

		public void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
			if (random.nextInt(6) == 0) {
				setBlockAndMetadata(world, i, j, k, GOTBlocks.stairsAndesiteBrickCracked, meta);
			} else {
				setBlockAndMetadata(world, i, j, k, GOTBlocks.stairsAndesiteBrick, meta);
			}
		}
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance<GOTStructureTower> {
		public Instance(GOTStructureTower settlement, World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos) {
			super(settlement, world, i, k, random, loc, filler, spawnInfos);
		}

		@Override
		public void addSettlementStructures(Random random) {
			super.addSettlementStructures(random);
			addStructure(new GOTStructureTowerBase(false), 0, -7, 2, true);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupSettlementProperties(Random random) {
		}
	}

}
