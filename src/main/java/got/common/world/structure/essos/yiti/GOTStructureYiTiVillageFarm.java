package got.common.world.structure.essos.yiti;

import got.common.database.GOTBlocks;
import got.common.entity.essos.yiti.GOTEntityYiTiFarmer;
import got.common.entity.essos.yiti.GOTEntityYiTiFarmhand;
import got.common.world.feature.GOTTreeType;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.ArrayList;
import java.util.Random;

public abstract class GOTStructureYiTiVillageFarm extends GOTStructureYiTiBase {
	protected GOTStructureYiTiVillageFarm(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -5; i1 <= 5; ++i1) {
				for (int k1 = -5; k1 <= 5; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 4) {
						continue;
					}
					return false;
				}
			}
		}
		if (restrictions) {
			int highestHeight = 0;
			for (int i1 = -6; i1 <= 6; ++i1) {
				for (int k1 = -6; k1 <= 6; ++k1) {
					int j12;
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					if ((i2 != 6 || k2 != 0) && (k2 != 6 || i2 != 0) || !isSurface(world, i1, j12 = getTopBlock(world, i1, k1) - 1, k1) || j12 <= highestHeight) {
						continue;
					}
					highestHeight = j12;
				}
			}
			originY = getY(highestHeight);
		}
		for (int i1 = -5; i1 <= 5; ++i1) {
			for (int k1 = -5; k1 <= 5; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 10; ++j1) {
					setAir(world, i1, j1, k1);
				}
				if (i2 == 5 && k2 == 5) {
					setBlockAndMetadata(world, i1, 1, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 2, k1, brickSlabBlock, brickSlabMeta);
					continue;
				}
				if (i2 == 5 || k2 == 5) {
					setBlockAndMetadata(world, i1, 1, k1, fenceBlock, fenceMeta);
					if (i2 == 2 || k2 == 2) {
						setBlockAndMetadata(world, i1, 1, k1, brickBlock, brickMeta);
						setBlockAndMetadata(world, i1, 2, k1, brickBlock, brickMeta);
						setBlockAndMetadata(world, i1, 3, k1, brickWallBlock, brickWallMeta);
						setBlockAndMetadata(world, i1, 4, k1, Blocks.torch, 5);
					}
					if (i2 != 0 && k2 != 0) {
						continue;
					}
					setAir(world, i1, 1, k1);
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
			}
		}
		return true;
	}

	public static class Animals extends GOTStructureYiTiVillageFarm {
		public Animals(boolean flag) {
			super(flag);
		}

		public static EntityAnimal getRandomAnimal(World world, Random random) {
			int animal = random.nextInt(4);
			switch (animal) {
				case 0:
					return new EntityCow(world);
				case 1:
					return new EntityPig(world);
				case 2:
					return new EntitySheep(world);
				default:
					return new EntityChicken(world);
			}
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			int i1;
			if (!super.generate(world, random, i, j, k, rotation)) {
				return false;
			}
			for (i1 = -1; i1 <= 1; ++i1) {
				setBlockAndMetadata(world, i1, 1, -5, fenceGateBlock, 0);
				setBlockAndMetadata(world, i1, 1, 5, fenceGateBlock, 2);
			}
			for (int k1 = -1; k1 <= 1; ++k1) {
				setBlockAndMetadata(world, -5, 1, k1, fenceGateBlock, 1);
				setBlockAndMetadata(world, 5, 1, k1, fenceGateBlock, 3);
			}
			for (i1 = -1; i1 <= 1; ++i1) {
				for (int k1 = -1; k1 <= 1; ++k1) {
					if (random.nextInt(3) != 0) {
						continue;
					}
					int j1 = 1;
					int j2 = 1;
					if (i1 == 0 && k1 == 0 && random.nextBoolean()) {
						++j2;
					}
					for (int j3 = j1; j3 <= j2; ++j3) {
						setBlockAndMetadata(world, i1, j3, k1, Blocks.hay_block, 0);
					}
				}
			}
			int animals = 4 + random.nextInt(5);
			for (int l = 0; l < animals; ++l) {
				EntityAnimal animal = getRandomAnimal(world, random);
				int i12 = 3 * (random.nextBoolean() ? 1 : -1);
				int k1 = 3 * (random.nextBoolean() ? 1 : -1);
				spawnNPCAndSetHome(animal, world, i12, 1, k1, 0);
				assert animal != null;
				animal.detachHome();
			}
			return true;
		}
	}

	public static class Crops extends GOTStructureYiTiVillageFarm {
		public Crops(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			if (!super.generate(world, random, i, j, k, rotation)) {
				return false;
			}
			for (int i1 = -4; i1 <= 4; ++i1) {
				for (int k1 = -4; k1 <= 4; ++k1) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					if (i2 <= 2 && k2 <= 2) {
						if (i2 == 0 && k2 == 0) {
							setBlockAndMetadata(world, i1, 0, k1, Blocks.water, 0);
							setBlockAndMetadata(world, i1, 1, k1, brickBlock, brickMeta);
							setBlockAndMetadata(world, i1, 2, k1, Blocks.hay_block, 0);
							setBlockAndMetadata(world, i1, 3, k1, fenceBlock, fenceMeta);
							setBlockAndMetadata(world, i1, 4, k1, Blocks.hay_block, 0);
							setBlockAndMetadata(world, i1, 5, k1, Blocks.pumpkin, 2);
							continue;
						}
						setBlockAndMetadata(world, i1, 0, k1, Blocks.farmland, 7);
						setBlockAndMetadata(world, i1, 1, k1, cropBlock, cropMeta);
						continue;
					}
					setBlockAndMetadata(world, i1, 0, k1, GOTBlocks.dirtPath, 0);
				}
			}
			setBlockAndMetadata(world, 0, 1, -5, fenceGateBlock, 0);
			setBlockAndMetadata(world, 0, 1, 5, fenceGateBlock, 2);
			setBlockAndMetadata(world, -5, 1, 0, fenceGateBlock, 1);
			setBlockAndMetadata(world, 5, 1, 0, fenceGateBlock, 3);
			int farmhands = 1 + random.nextInt(2);
			for (int l = 0; l < farmhands; ++l) {
				GOTEntityYiTiFarmhand farmhand = new GOTEntityYiTiFarmhand(world);
				spawnNPCAndSetHome(farmhand, world, 0, 1, -1, 8);
				farmhand.seedsItem = seedItem;
			}
			if (random.nextInt(3) == 0) {
				GOTEntityYiTiFarmer farmer = new GOTEntityYiTiFarmer(world);
				spawnNPCAndSetHome(farmer, world, 0, 1, -1, 8);
			}
			return true;
		}
	}

	public static class Tree extends GOTStructureYiTiVillageFarm {
		public Tree(boolean flag) {
			super(flag);
		}

		public static GOTTreeType getRandomTree(Random random) {
			ArrayList<GOTTreeType> treeList = new ArrayList<>();
			treeList.add(GOTTreeType.BEECH);
			treeList.add(GOTTreeType.BEECH_LARGE);
			treeList.add(GOTTreeType.MAPLE);
			treeList.add(GOTTreeType.MAPLE_LARGE);
			treeList.add(GOTTreeType.CYPRESS);
			treeList.add(GOTTreeType.ALMOND);
			treeList.add(GOTTreeType.OLIVE);
			treeList.add(GOTTreeType.DATE_PALM);
			treeList.add(GOTTreeType.POMEGRANATE);
			return treeList.get(random.nextInt(treeList.size()));
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			int i1;
			if (!super.generate(world, random, i, j, k, rotation)) {
				return false;
			}
			for (i1 = -5; i1 <= 5; ++i1) {
				for (int k1 = -5; k1 <= 5; ++k1) {
					int i2 = Math.abs(i1);
					int k2 = Math.abs(k1);
					if (i2 != 5 || k2 != 5) {
						continue;
					}
					setBlockAndMetadata(world, i1, 2, k1, brickWallBlock, brickWallMeta);
					setBlockAndMetadata(world, i1, 3, k1, GOTBlocks.leaves6, 6);
				}
			}
			for (int l = 0; l < 16; ++l) {
				GOTTreeType tree = getRandomTree(random);
				WorldGenAbstractTree treeGen = tree.create(notifyChanges, random);
				if (treeGen == null) {
					continue;
				}
				int i12 = 0;
				int j1 = 1;
				int k1 = 0;
				if (treeGen.generate(world, random, getX(i12, k1), getY(j1), getZ(i12, k1))) {
					break;
				}
			}
			for (i1 = -4; i1 <= 4; ++i1) {
				for (int k1 = -4; k1 <= 4; ++k1) {
					int j1 = 1;
					if (isOpaque(world, i1, j1, k1) || random.nextInt(8) != 0) {
						continue;
					}
					plantFlower(world, random, i1, j1, k1);
				}
			}
			return true;
		}
	}

}
