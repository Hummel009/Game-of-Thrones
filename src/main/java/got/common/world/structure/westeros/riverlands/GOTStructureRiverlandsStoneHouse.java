package got.common.world.structure.westeros.riverlands;

import java.util.Random;

import got.common.database.*;
import got.common.entity.westeros.riverlands.GOTEntityRiverlandsMan;
import got.common.item.other.GOTItemBanner;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureRiverlandsStoneHouse extends GOTStructureRiverlandsBase {
	public GOTStructureRiverlandsStoneHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int j1;
		int j12;
		int i1;
		int k12;
		int j13;
		this.setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -5; i12 <= 5; ++i12) {
				for (int k132 = -7; k132 <= 6; ++k132) {
					j13 = getTopBlock(world, i12, k132) - 1;
					if (!isSurface(world, i12, j13, k132)) {
						return false;
					}
					if (j13 < minHeight) {
						minHeight = j13;
					}
					if (j13 > maxHeight) {
						maxHeight = j13;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -5; i1 <= 4; ++i1) {
			for (int k14 = -7; k14 <= 5; ++k14) {
				int i2 = Math.abs(i1);
				Math.abs(k14);
				if (i1 == -5) {
					for (int j132 = 1; j132 <= 8; ++j132) {
						setAir(world, i1, j132, k14);
					}
					setBlockAndMetadata(world, i1, 0, k14, Blocks.grass, 0);
					j13 = -1;
					while (!isOpaque(world, i1, j13, k14) && getY(j13) >= 0) {
						setBlockAndMetadata(world, i1, j13, k14, Blocks.dirt, 0);
						setGrassToDirt(world, i1, j13 - 1, k14);
						--j13;
					}
					continue;
				}
				for (j13 = 0; (j13 == 0 || !isOpaque(world, i1, j13, k14)) && getY(j13) >= 0; --j13) {
					setBlockAndMetadata(world, i1, j13, k14, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j13 - 1, k14);
				}
				if (k14 >= -4) {
					if ((k14 == -4 || k14 == 5) && i2 == 4) {
						for (j13 = 1; j13 <= 7; ++j13) {
							setBlockAndMetadata(world, i1, j13, k14, pillarBlock, pillarMeta);
						}
					} else if (k14 == -4 || k14 == 5 || i2 == 4) {
						for (j13 = 1; j13 <= 7; ++j13) {
							setBlockAndMetadata(world, i1, j13, k14, brickBlock, brickMeta);
						}
					} else if (k14 >= -3 && k14 <= 4 && i2 <= 3) {
						for (j13 = 1; j13 <= 3; ++j13) {
							setAir(world, i1, j13, k14);
						}
						setBlockAndMetadata(world, i1, 4, k14, plankBlock, plankMeta);
						for (j13 = 5; j13 <= 9; ++j13) {
							setAir(world, i1, j13, k14);
						}
					}
				}
				if (k14 > -5) {
					continue;
				}
				if (k14 == -7) {
					if (i2 == 4 || i2 == 2) {
						for (j13 = 1; j13 <= 3; ++j13) {
							setBlockAndMetadata(world, i1, j13, k14, pillarBlock, pillarMeta);
						}
						continue;
					}
					for (j13 = 1; j13 <= 3; ++j13) {
						setAir(world, i1, j13, k14);
					}
					continue;
				}
				if (i2 == 4) {
					setBlockAndMetadata(world, i1, 1, k14, brickBlock, brickMeta);
					placeFlowerPot(world, i1, 2, k14, getRandomFlower(world, random));
					setBlockAndMetadata(world, i1, 3, k14, fenceBlock, fenceMeta);
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k14, plankBlock, plankMeta);
				for (j13 = 1; j13 <= 3; ++j13) {
					setAir(world, i1, j13, k14);
				}
			}
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			int j2;
			int i2 = Math.abs(i1);
			for (int step = 0; step <= 2; ++step) {
				int j14 = 8 + step;
				setBlockAndMetadata(world, i1, j14, -5 + step, brick2StairBlock, 2);
				setBlockAndMetadata(world, i1, j14, -4 + step, brick2Block, brick2Meta);
				setBlockAndMetadata(world, i1, j14, -3 + step, brick2StairBlock, 7);
				setBlockAndMetadata(world, i1, j14, 6 - step, brick2StairBlock, 3);
				setBlockAndMetadata(world, i1, j14, 5 - step, brick2Block, brick2Meta);
				setBlockAndMetadata(world, i1, j14, 4 - step, brick2StairBlock, 6);
				if (i2 != 4) {
					continue;
				}
				for (j2 = 8; j2 <= j14 - 1; ++j2) {
					setBlockAndMetadata(world, i1, j2, -5 + step, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, j2, 6 - step, brickBlock, brickMeta);
				}
			}
			for (int k15 = -2; k15 <= 3; ++k15) {
				int j14 = 10;
				setBlockAndMetadata(world, i1, j14, k15, brick2Block, brick2Meta);
				if (i2 != 4) {
					continue;
				}
				for (j2 = 8; j2 <= j14 - 1; ++j2) {
					setBlockAndMetadata(world, i1, j2, k15, brickBlock, brickMeta);
				}
			}
		}
		for (i1 = -5; i1 <= -3; ++i1) {
			for (k12 = -1; k12 <= 2; ++k12) {
				for (j12 = 1; j12 <= 11; ++j12) {
					setBlockAndMetadata(world, i1, j12, k12, brickBlock, brickMeta);
				}
				setGrassToDirt(world, i1, 0, k12);
			}
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			setBlockAndMetadata(world, i1, 4, -7, brick2SlabBlock, brick2SlabMeta);
			setBlockAndMetadata(world, i1, 4, -6, brick2Block, brick2Meta);
			setBlockAndMetadata(world, i1, 4, -5, brick2Block, brick2Meta);
			setBlockAndMetadata(world, i1, 5, -5, brick2SlabBlock, brick2SlabMeta);
		}
		setBlockAndMetadata(world, 0, 3, -6, GOTRegistry.chandelier, 2);
		setBlockAndMetadata(world, 0, 1, -4, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -4, doorBlock, 8);
		setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 2, -3, Blocks.torch, 3);
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k12 = -1; k12 <= 2; ++k12) {
				setBlockAndMetadata(world, i1, 1, k12, Blocks.carpet, 15);
			}
		}
		if (random.nextInt(4) == 0) {
			this.placeChest(world, random, 0, 0, 1, GOTRegistry.chestStone, 2, GOTChestContents.WESTEROS);
		}
		setBlockAndMetadata(world, 3, 2, 4, Blocks.torch, 1);
		setBlockAndMetadata(world, 0, 3, 1, GOTRegistry.chandelier, 2);
		for (k1 = 0; k1 <= 1; ++k1) {
			setBlockAndMetadata(world, -3, 1, k1, Blocks.iron_bars, 0);
			setBlockAndMetadata(world, -3, 2, k1, Blocks.furnace, 4);
			setBlockAndMetadata(world, -4, 0, k1, GOTRegistry.hearth, 0);
			setBlockAndMetadata(world, -4, 1, k1, Blocks.fire, 0);
			for (j1 = 2; j1 <= 10; ++j1) {
				setAir(world, -4, j1, k1);
			}
		}
		for (k1 = -3; k1 <= -2; ++k1) {
			setBlockAndMetadata(world, -3, 1, k1, plankBlock, plankMeta);
			this.placeMug(world, random, -3, 2, k1, 3, GOTFoods.WESTEROS_DRINK);
			setBlockAndMetadata(world, -3, 3, k1, plankStairBlock, 4);
		}
		for (k1 = 3; k1 <= 4; ++k1) {
			setBlockAndMetadata(world, -3, 3, k1, plankStairBlock, 4);
		}
		setBlockAndMetadata(world, -3, 1, 3, Blocks.cauldron, 3);
		setBlockAndMetadata(world, -3, 1, 4, plankBlock, plankMeta);
		placePlateWithCertainty(world, random, -3, 2, 4, plateBlock, GOTFoods.WESTEROS);
		setBlockAndMetadata(world, -2, 1, 4, Blocks.crafting_table, 0);
		for (k1 = 0; k1 <= 3; ++k1) {
			setAir(world, 3, 4, k1);
		}
		for (int step = 0; step <= 3; ++step) {
			setBlockAndMetadata(world, 3, 1 + step, 2 - step, plankStairBlock, 3);
		}
		setBlockAndMetadata(world, 3, 1, 1, plankBlock, plankMeta);
		setBlockAndMetadata(world, 3, 1, 0, plankBlock, plankMeta);
		setBlockAndMetadata(world, 3, 2, 0, plankStairBlock, 6);
		this.placeChest(world, random, 3, 1, -1, 5, GOTChestContents.WESTEROS);
		setBlockAndMetadata(world, 3, 1, -2, tableBlock, 0);
		setBlockAndMetadata(world, 3, 1, -3, plankBlock, plankMeta);
		setBlockAndMetadata(world, 3, 2, -3, fenceBlock, fenceMeta);
		for (k1 = -3; k1 <= -1; ++k1) {
			setBlockAndMetadata(world, 3, 3, k1, plankBlock, plankMeta);
		}
		spawnItemFrame(world, 3, 3, -1, 3, new ItemStack(Items.clock));
		for (int j15 = 1; j15 <= 3; ++j15) {
			setBlockAndMetadata(world, 0, j15, 5, pillarBlock, pillarMeta);
		}
		placeWallBanner(world, 0, 3, 5, GOTItemBanner.BannerType.TULLY, 2);
		for (int i13 : new int[] { -3, 1 }) {
			setBlockAndMetadata(world, i13, 2, 5, brickStairBlock, 0);
			setBlockAndMetadata(world, i13, 3, 5, brickStairBlock, 4);
			setBlockAndMetadata(world, i13 + 1, 2, 5, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i13 + 1, 3, 5, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i13 + 2, 2, 5, brickStairBlock, 1);
			setBlockAndMetadata(world, i13 + 2, 3, 5, brickStairBlock, 5);
		}
		for (int k13 : new int[] { -4, 5 }) {
			for (int i14 : new int[] { -3, 1 }) {
				setBlockAndMetadata(world, i14, 6, k13, brickStairBlock, 0);
				setBlockAndMetadata(world, i14, 7, k13, brickStairBlock, 4);
				setBlockAndMetadata(world, i14 + 1, 6, k13, brickWallBlock, brickWallMeta);
				setBlockAndMetadata(world, i14 + 1, 7, k13, brickWallBlock, brickWallMeta);
				setBlockAndMetadata(world, i14 + 1, 8, k13, brickBlock, brickMeta);
				setBlockAndMetadata(world, i14 + 2, 6, k13, brickStairBlock, 1);
				setBlockAndMetadata(world, i14 + 2, 7, k13, brickStairBlock, 5);
			}
			setBlockAndMetadata(world, 0, 6, k13, GOTRegistry.brick1, 5);
		}
		setBlockAndMetadata(world, -2, 5, 0, plankBlock, plankMeta);
		setBlockAndMetadata(world, -2, 6, 0, GOTRegistry.plateBlock, 0);
		setBlockAndMetadata(world, -2, 5, 1, plankBlock, plankMeta);
		this.placeMug(world, random, -2, 6, 1, 3, GOTFoods.WESTEROS_DRINK);
		int[] j15 = { -1, 2 };
		j1 = j15.length;
		for (j12 = 0; j12 < j1; ++j12) {
			int k13;
			k13 = j15[j12];
			setBlockAndMetadata(world, -2, 5, k13, bedBlock, 11);
			setBlockAndMetadata(world, -1, 5, k13, bedBlock, 3);
			spawnItemFrame(world, -3, 7, k13, 1, getFramedItem(random));
		}
		for (int k122 = 0; k122 <= 1; ++k122) {
			for (j1 = 7; j1 <= 8; ++j1) {
				setBlockAndMetadata(world, -3, j1, k122, pillarBlock, pillarMeta);
			}
		}
		this.placeChest(world, random, -3, 5, -3, 4, GOTChestContents.WESTEROS);
		setBlockAndMetadata(world, -3, 5, -2, plankBlock, plankMeta);
		setBlockAndMetadata(world, -3, 5, 3, plankBlock, plankMeta);
		this.placeChest(world, random, -3, 5, 4, 4, GOTChestContents.WESTEROS);
		setBlockAndMetadata(world, 0, 9, -2, brick2Block, brick2Meta);
		setBlockAndMetadata(world, 0, 8, -2, GOTRegistry.chandelier, 2);
		setBlockAndMetadata(world, 0, 9, 3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, 0, 8, 3, GOTRegistry.chandelier, 2);
		setBlockAndMetadata(world, -3, 7, -2, Blocks.torch, 2);
		setBlockAndMetadata(world, -3, 7, 3, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 7, -2, Blocks.torch, 1);
		setBlockAndMetadata(world, 3, 7, 3, Blocks.torch, 1);
		for (int k14 = -1; k14 <= 2; ++k14) {
			setBlockAndMetadata(world, -5, 12, k14, brickStairBlock, 1);
			setBlockAndMetadata(world, -3, 12, k14, brickStairBlock, 0);
		}
		setBlockAndMetadata(world, -4, 12, -1, brickStairBlock, 2);
		setBlockAndMetadata(world, -4, 12, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 12, 1, brickBlock, brickMeta);
		setBlockAndMetadata(world, -4, 12, 2, brickStairBlock, 3);
		setBlockAndMetadata(world, -4, 13, 0, brickWallBlock, brickWallMeta);
		setBlockAndMetadata(world, -4, 13, 1, brickWallBlock, brickWallMeta);
		GOTEntityRiverlandsMan male = new GOTEntityRiverlandsMan(world);
		male.familyInfo.setMale(true);
		male.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(male, world, 0, 1, 0, 16);
		GOTEntityRiverlandsMan female = new GOTEntityRiverlandsMan(world);
		female.familyInfo.setMale(false);
		female.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(female, world, 0, 1, 0, 16);
		GOTEntityRiverlandsMan child = new GOTEntityRiverlandsMan(world);
		child.familyInfo.setMale(random.nextBoolean());
		child.familyInfo.setChild();
		spawnNPCAndSetHome(child, world, 0, 1, 0, 16);
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (random.nextBoolean()) {
			doorBlock = GOTRegistry.doorAramant;
		}
		bedBlock = Blocks.bed;
		plateBlock = random.nextBoolean() ? GOTRegistry.plateBlock : GOTRegistry.ceramicPlateBlock;
	}
}
