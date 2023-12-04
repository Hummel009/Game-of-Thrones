package got.common.world.structure.other;

import got.GOT;
import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureRuins extends GOTStructureBaseSettlement {
	public GOTStructureRuins(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 2;
		fixedSettlementChunkRadius = 2;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance<GOTStructureRuins> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos) {
		return new Instance(this, world, i, k, random, loc, filler, spawnInfos);
	}

	public static class GOTStructureRuinsPart extends GOTStructureBase {
		public GOTStructureRuinsPart(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			int rotation1 = rotation;
			int k2 = k;
			int i2 = i;
			if (!restrictions && usingPlayer != null) {
				rotation1 = usingPlayerRotation();
			}
			switch (rotation1) {
				case 0:
					k2 += 8;
					break;
				case 1:
					i2 -= 8;
					break;
				case 2:
					k2 -= 8;
					break;
				case 3:
					i2 += 8;
					break;
				default:
					break;
			}
			if (restrictions && world.getBlock(i2, world.getTopSolidOrLiquidBlock(i2, k2) - 1, k2) != Blocks.grass) {
				return false;
			}
			int i1;
			int k1;
			int j1;
			for (i1 = i2 - 7; i1 <= i2 + 7; ++i1) {
				for (k1 = k2 - 7; k1 <= k2 + 7; ++k1) {
					j1 = world.getTopSolidOrLiquidBlock(i1, k1);
					Block block = world.getBlock(i1, j1 - 1, k1);
					if (!block.isOpaqueCube()) {
						continue;
					}
					if (random.nextInt(3) == 0) {
						setBlockAndNotifyAdequately(world, i1, j1 - 1, k1, GOTBlocks.brick1, 1);
					}
					if (random.nextInt(3) == 0) {
						if (random.nextInt(3) == 0) {
							placeRandomSlab(world, random, i1, j1, k1);
						} else {
							placeRandomBrick(world, random, i1, j1, k1);
						}
						setGrassToDirt(world, i1, j1 - 1, k1);
					}
					if (!GOT.isOpaque(world, i1, j1, k1) || random.nextInt(4) != 0) {
						continue;
					}
					if (random.nextInt(5) == 0) {
						setBlockAndNotifyAdequately(world, i1, j1 + 1, k1, GOTBlocks.wallStone1, 3);
						placeSkull(world, random, i1, j1 + 2, k1);
						continue;
					}
					if (random.nextInt(3) == 0) {
						placeRandomSlab(world, random, i1, j1 + 1, k1);
						continue;
					}
					placeRandomBrick(world, random, i1, j1 + 1, k1);
				}
			}
			for (i1 = i2 - 7; i1 <= i2 + 7; i1 += 7) {
				block9:
				for (k1 = k2 - 7; k1 <= k2 + 7; k1 += 7) {
					j1 = world.getTopSolidOrLiquidBlock(i1, k1);
					setGrassToDirt(world, i1, j1 - 1, k1);
					int j2 = j1;
					do {
						placeRandomBrick(world, random, i1, j2, k1);
						if (random.nextInt(4) == 0 || j2 > j1 + 4) {
							if (i1 == i2 && k1 == k2) {
								continue block9;
							}
							setBlockAndNotifyAdequately(world, i1, j2 + 1, k1, GOTBlocks.brick1, 5);
							continue block9;
						}
						++j2;
					} while (true);
				}
			}
			return true;
		}

		public void placeRandomBrick(World world, Random random, int i, int j, int k) {
			if (random.nextInt(20) == 0) {
				setBlockAndNotifyAdequately(world, i, j, k, GOTBlocks.brick1, 5);
			} else if (random.nextInt(4) == 0) {
				setBlockAndNotifyAdequately(world, i, j, k, GOTBlocks.brick1, 2 + random.nextInt(2));
			} else {
				setBlockAndNotifyAdequately(world, i, j, k, GOTBlocks.brick1, 1);
			}
		}

		public void placeRandomSlab(World world, Random random, int i, int j, int k) {
			if (random.nextInt(5) == 0 || random.nextInt(4) != 0) {
				setBlockAndNotifyAdequately(world, i, j, k, GOTBlocks.slabSingle1, 3);
			} else {
				setBlockAndNotifyAdequately(world, i, j, k, GOTBlocks.slabSingle1, 4 + random.nextInt(2));
			}
		}

	}

	public static class GOTStructureRuinsTower extends GOTStructureBase {
		public GOTStructureRuinsTower(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			int j4 = j;
			int rotation1 = rotation;
			int k3 = k;
			int i3 = i;
			if (restrictions && world.getBlock(i3, j4 - 1, k3) != Blocks.grass) {
				return false;
			}
			--j4;
			int radius = 4 + random.nextInt(2);
			if (!restrictions && usingPlayer != null) {
				rotation1 = usingPlayerRotation();
				switch (rotation1) {
					case 0:
						k3 += radius;
						break;
					case 1:
						i3 -= radius;
						break;
					case 2:
						k3 -= radius;
						break;
					case 3:
						i3 += radius;
						break;
					default:
						break;
				}
			}
			int sections = 4 + random.nextInt(3);
			int sectionHeight = 4 + random.nextInt(4);
			int wallThresholdMin = radius;
			wallThresholdMin *= wallThresholdMin;
			int wallThresholdMax = radius + 1;
			wallThresholdMax *= wallThresholdMax;
			int maxHeight = (sections - 1) * sectionHeight;
			int i1;
			for (i1 = i3 - radius; i1 <= i3 + radius; ++i1) {
				for (int k12 = k3 - radius; k12 <= k3 + radius; ++k12) {
					int i2 = i1 - i3;
					int k2 = k12 - k3;
					int distSq = i2 * i2 + k2 * k2;
					if (distSq >= wallThresholdMax) {
						continue;
					}
					int j1;
					if (distSq >= wallThresholdMin) {
						for (j1 = j4 - 1; j1 >= 0; --j1) {
							Block block = world.getBlock(i1, j1, k12);
							placeRandomBrick(world, i1, j1, k12);
							if (block == Blocks.grass || block == Blocks.dirt || block == Blocks.stone || !restrictions && block.isOpaqueCube()) {
								break;
							}
						}
						int j2 = j4 + maxHeight;
						for (int j12 = j4; j12 <= j2; ++j12) {
							if (random.nextInt(20) == 0) {
								continue;
							}
							placeRandomBrick(world, i1, j12, k12);
						}
						int j3 = j2 + 1 + random.nextInt(3);
						for (int j13 = j2; j13 <= j3; ++j13) {
							placeRandomBrick(world, i1, j13, k12);
						}
						continue;
					}
					for (j1 = j4 + sectionHeight; j1 <= j4 + maxHeight; j1 += sectionHeight) {
						if (random.nextInt(6) == 0) {
							continue;
						}
						setBlockAndNotifyAdequately(world, i1, j1, k12, GOTBlocks.slabSingle1, 3);
					}
				}
			}
			int k1;
			for (int j1 = j4 + sectionHeight; j1 < j4 + maxHeight; j1 += sectionHeight) {
				for (int j2 = j1 + 2; j2 <= j1 + 3; ++j2) {
					for (int i12 = i3 - 1; i12 <= i3 + 1; ++i12) {
						placeIronBars(world, random, i12, j2, k3 - radius);
						placeIronBars(world, random, i12, j2, k3 + radius);
					}
					for (k1 = k3 - 1; k1 <= k3 + 1; ++k1) {
						placeIronBars(world, random, i3 - radius, j2, k1);
						placeIronBars(world, random, i3 + radius, j2, k1);
					}
				}
			}
			setBlockAndNotifyAdequately(world, i3, j4 + maxHeight, k3, GOTBlocks.slabSingle1, 3);
			setBlockAndNotifyAdequately(world, i3, j4 + maxHeight + 1, k3, GOTBlocks.chestStone, rotation1 + 2);
			GOTChestContents.fillChest(world, random, i3, j4 + maxHeight + 1, k3, GOTChestContents.TREASURE);
			switch (rotation1) {
				case 0:
					for (i1 = i3 - 1; i1 <= i3 + 1; ++i1) {
						int height = j4 + 1 + random.nextInt(3);
						for (int j1 = j4; j1 <= height; ++j1) {
							setBlockAndNotifyAdequately(world, i1, j1, k3 - radius, Blocks.air, 0);
						}
					}
					break;
				case 1:
					for (int k13 = k3 - 1; k13 <= k3 + 1; ++k13) {
						int height = j4 + 1 + random.nextInt(3);
						for (int j1 = j4; j1 <= height; ++j1) {
							setBlockAndNotifyAdequately(world, i3 + radius, j1, k13, Blocks.air, 0);
						}
					}
					break;
				case 2:
					for (i1 = i3 - 1; i1 <= i3 + 1; ++i1) {
						int height = j4 + 1 + random.nextInt(3);
						for (int j1 = j4; j1 <= height; ++j1) {
							setBlockAndNotifyAdequately(world, i1, j1, k3 + radius, Blocks.air, 0);
						}
					}
					break;
				case 3:
					for (int k13 = k3 - 1; k13 <= k3 + 1; ++k13) {
						int height = j4 + 1 + random.nextInt(3);
						for (int j1 = j4; j1 <= height; ++j1) {
							setBlockAndNotifyAdequately(world, i3 - radius, j1, k13, Blocks.air, 0);
						}
					}
					break;
				default:
					break;
			}
			for (int l = 0; l < 16; ++l) {
				int i13 = i3 - random.nextInt(radius * 2) + random.nextInt(radius * 2);
				k1 = k3 - random.nextInt(radius * 2) + random.nextInt(radius * 2);
				int j1 = world.getHeightValue(i13, k1);
				if (world.getBlock(i13, j1 - 1, k1) != Blocks.grass) {
					continue;
				}
				int randomFeature = random.nextInt(4);
				boolean flag = true;
				if (randomFeature == 0) {
					if (!GOT.isOpaque(world, i13, j1, k1)) {
						setBlockAndNotifyAdequately(world, i13, j1, k1, Blocks.air, random.nextBoolean() ? 0 : 5);
					}
				} else {
					int j2;
					for (j2 = j1; j2 < j1 + randomFeature && flag; ++j2) {
						flag = !GOT.isOpaque(world, i13, j2, k1);
					}
					if (flag) {
						for (j2 = j1; j2 < j1 + randomFeature; ++j2) {
							placeRandomBrick(world, i13, j2, k1);
						}
					}
				}
				if (!flag) {
					continue;
				}
				world.getBlock(i13, j1 - 1, k1).onPlantGrow(world, i13, j1 - 1, k1, i13, j1, k1);
			}
			return true;
		}

		public void placeIronBars(World world, Random random, int i, int j, int k) {
			if (random.nextInt(4) == 0) {
				setBlockAndNotifyAdequately(world, i, j, k, Blocks.air, 0);
			} else {
				setBlockAndNotifyAdequately(world, i, j, k, Blocks.iron_bars, 0);
			}
		}

		public void placeRandomBrick(World world, int i, int j, int k) {
			setBlockAndNotifyAdequately(world, i, j, k, GOTBlocks.brick1, 1);
		}
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance<GOTStructureRuins> {
		public Instance(GOTStructureRuins settlement, World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos) {
			super(settlement, world, i, k, random, loc, filler, spawnInfos);
		}

		@Override
		public void addSettlementStructures(Random random) {
			super.addSettlementStructures(random);
			addStructure(new GOTStructureRuinsPart(false), -8, 0, 0, true);
			addStructure(new GOTStructureRuinsPart(false), 8, 0, 0, true);
			addStructure(new GOTStructureRuinsPart(false), -8, -8, 0, true);
			addStructure(new GOTStructureRuinsPart(false), 8, -8, 0, true);
			addStructure(new GOTStructureRuinsPart(false), -8, 8, 0, true);
			addStructure(new GOTStructureRuinsPart(false), 8, 8, 0, true);
			addStructure(new GOTStructureRuinsTower(false), 0, 6, 0, true);
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
