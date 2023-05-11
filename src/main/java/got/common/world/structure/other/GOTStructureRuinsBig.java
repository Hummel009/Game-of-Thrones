package got.common.world.structure.other;

import got.common.database.GOTRegistry;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureRuinsBig extends GOTVillageGen {
	public GOTStructureRuinsBig(GOTBiome biome, float f) {
		super(biome);
		gridScale = 10;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 3;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class GOTStructureHarrenhal extends GOTStructureBase {
		public GOTStructureHarrenhal() {
			super(false);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			int j2;
			int j1;
			setOriginAndRotation(world, i, j, k, 2, 0);
			originY = 0;
			int radius = 8;
			int baseHeight = 40;
			int portalHeight = 10;
			int portalBase = portalHeight - 2 - 1;
			for (int i1 = -radius; i1 <= radius; ++i1) {
				for (int k1 = -radius; k1 <= radius; ++k1) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					int maxHeight = 100 + random.nextInt(10);
					for (int j12 = baseHeight; j12 <= maxHeight; ++j12) {
						if (i2 == radius || k2 == radius || j12 == baseHeight || j12 >= maxHeight - 10) {
							setBlockAndMetadata(world, i1, j12, k1, GOTRegistry.brick2, 11);
							continue;
						}
						setAir(world, i1, j12, k1);
					}
					if (i2 >= radius || k2 >= radius || random.nextInt(16) != 0) {
						continue;
					}
					int height = 1 + random.nextInt(2);
					for (j1 = baseHeight; j1 <= baseHeight + height; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.brick2, 11);
					}
				}
			}
			for (int l = 0; l < 40; ++l) {
				int i1 = -random.nextInt(radius * 3) + random.nextInt(radius * 3);
				int k1 = -random.nextInt(radius * 3) + random.nextInt(radius * 3);
				int width = 1 + random.nextInt(3);
				int height = width * 4 + random.nextInt(4);
				for (int i2 = i1 - width; i2 <= i1 + width; ++i2) {
					for (int k2 = k1 - width; k2 <= k1 + width; ++k2) {
						int base = getTopBlock(world, i2, k2);
						int top = base + height - random.nextInt(3);
						for (j2 = base; j2 < top; ++j2) {
							setBlockAndMetadata(world, i2, j2, k2, GOTRegistry.brick2, 11);
						}
					}
				}
			}
			int entranceX = -radius;
			int entranceZ = -radius;
			int entranceY = 80;
			int entranceSize = 6;
			int entranceSizeExtra = entranceSize + 3;
			for (int i1 = entranceX - entranceSize; i1 <= entranceX + entranceSize; ++i1) {
				for (j1 = entranceY - entranceSize; j1 <= entranceY + entranceSize; ++j1) {
					for (int k1 = entranceZ - entranceSize; k1 <= entranceZ + entranceSize; ++k1) {
						int i2 = i1 - entranceX;
						j2 = j1 - entranceY;
						int k2 = k1 - entranceZ;
						float dist = i2 * i2 + j2 * j2 + k2 * k2;
						if (dist >= entranceSize * entranceSize && (dist >= entranceSizeExtra * entranceSizeExtra || random.nextInt(6) != 0)) {
							continue;
						}
						setAir(world, i1, j1, k1);
					}
				}
			}
			int stairX = entranceX + 1;
			int stairY = entranceY - entranceSize - 1;
			int stairZ = entranceZ + 1;
			int stairDirection = 2;
			do {
				setBlockAndMetadata(world, stairX, stairY, stairZ, GOTRegistry.brick2, 11);
				if (stairY <= baseHeight) {
					break;
				}
				if (stairDirection == 0) {
					--stairY;
					if (getBlock(world, stairX, stairY, stairZ + 1).isOpaqueCube()) {
						stairDirection = 1;
					}
				}
				if (stairDirection == 1 && getBlock(world, stairX - 1, stairY, stairZ).isOpaqueCube()) {
					stairDirection = 2;
				}
				if (stairDirection == 2 && getBlock(world, stairX, stairY, stairZ - 1).isOpaqueCube()) {
					stairDirection = 3;
				}
				if (stairDirection == 3 && getBlock(world, stairX + 1, stairY, stairZ).isOpaqueCube()) {
					stairDirection = 0;
				}
				if (stairDirection == 0) {
					++stairZ;
				}
				if (stairDirection == 1) {
					--stairX;
				}
				if (stairDirection == 2) {
					--stairZ;
				}
				if (stairDirection != 3) {
					continue;
				}
				++stairX;
			} while (true);
			for (int i1 = -2; i1 <= 2; ++i1) {
				for (int k1 = -2; k1 <= 2; ++k1) {
					int j13;
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					for (int j14 = portalBase; j14 <= baseHeight + 1; ++j14) {
						if (i2 < 2 && k2 < 2) {
							if (j14 == portalBase) {
								setBlockAndMetadata(world, i1, j14, k1, GOTRegistry.brick2, 11);
								continue;
							}
							if (j14 < portalHeight) {
								setAir(world, i1, j14, k1);
								continue;
							}
							if (j14 == portalHeight && i2 <= 1 && k2 <= 1) {
								setBlockAndMetadata(world, i1, j14, k1, Blocks.water, 1);
								continue;
							}
							setAir(world, i1, j14, k1);
							continue;
						}
						setBlockAndMetadata(world, i1, j14, k1, GOTRegistry.brick2, 11);
					}
					if (i2 != 2 || k2 != 2) {
						continue;
					}
					int min = baseHeight + 2;
					int max = min + 2 + random.nextInt(2);
					for (j13 = min; j13 <= max; ++j13) {
						setBlockAndMetadata(world, i1, j13, k1, GOTRegistry.brick2, 11);
					}
					setBlockAndMetadata(world, i1, max + 1, k1, GOTRegistry.brick2, 11);
					min = max + 2;
					max = min + 2;
					for (j13 = min; j13 <= max; ++j13) {
						setBlockAndMetadata(world, i1, j13, k1, GOTRegistry.brick2, 11);
					}
				}
			}
			return true;
		}
	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureRuinsBig village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			addStructure(new GOTStructureHarrenhal(), 0, 0, 0, true);
			addStructure(new GOTStructureHarrenhal(), -15, -15, 0, true);
			addStructure(new GOTStructureHarrenhal(), -15, 15, 0, true);
			addStructure(new GOTStructureHarrenhal(), 15, -15, 0, true);
			addStructure(new GOTStructureHarrenhal(), 15, 15, 0, true);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupVillageProperties(Random random) {
		}
	}

}
