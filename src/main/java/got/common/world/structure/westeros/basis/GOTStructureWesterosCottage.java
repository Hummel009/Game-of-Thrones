package got.common.world.structure.westeros.basis;

import java.util.Random;

import got.common.database.*;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureWesterosCottage extends GOTStructureWesterosBase {
	public GOTStructureWesterosCottage(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int j1;
		int j12;
		int j13;
		int i12;
		int k12;
		int i13;
		int k132;
		int l;
		this.setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i14 = -6; i14 <= 6; ++i14) {
				for (k132 = -7; k132 <= 10; ++k132) {
					j12 = getTopBlock(world, i14, k132) - 1;
					if (!isSurface(world, i14, j12, k132)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		for (i12 = -5; i12 <= 5; ++i12) {
			for (k12 = -5; k12 <= 5; ++k12) {
				int i2 = Math.abs(i12);
				int k2 = Math.abs(k12);
				if (i2 == 5 && k2 == 5) {
					for (j12 = 3; (j12 >= 0 || !isOpaque(world, i12, j12, k12)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i12, j12, k12, woodBeamBlock, woodBeamMeta);
						setGrassToDirt(world, i12, j12 - 1, k12);
					}
					continue;
				}
				if (i2 == 5 || k2 == 5) {
					for (j12 = 1; (j12 >= 0 || !isOpaque(world, i12, j12, k12)) && getY(j12) >= 0; --j12) {
						setBlockAndMetadata(world, i12, j12, k12, brickBlock, brickMeta);
						setGrassToDirt(world, i12, j12 - 1, k12);
					}
					setBlockAndMetadata(world, i12, 2, k12, wallBlock, wallMeta);
					setBlockAndMetadata(world, i12, 3, k12, wallBlock, wallMeta);
					continue;
				}
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i12, j12, k12)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i12, j12, k12, rockBlock, rockMeta);
					setGrassToDirt(world, i12, j12 - 1, k12);
				}
				for (j12 = 1; j12 <= 7; ++j12) {
					setAir(world, i12, j12, k12);
				}
				if (random.nextInt(3) != 0) {
					setBlockAndMetadata(world, i12, 1, k12, GOTRegistry.thatchFloor, 0);
				}
				if (i2 != 4 || k2 != 4) {
					continue;
				}
				for (j12 = 1; j12 <= 4; ++j12) {
					setBlockAndMetadata(world, i12, j12, k12, woodBeamBlock, woodBeamMeta);
				}
			}
		}
		for (i12 = -5; i12 <= 5; ++i12) {
			for (k12 = -7; k12 <= -6; ++k12) {
				for (j13 = 0; (j13 >= 0 || !isOpaque(world, i12, j13, k12)) && getY(j13) >= 0; --j13) {
					setBlockAndMetadata(world, i12, j13, k12, GOTRegistry.dirtPath, 0);
					setGrassToDirt(world, i12, j13 - 1, k12);
				}
				for (j13 = 1; j13 <= 8; ++j13) {
					setAir(world, i12, j13, k12);
				}
			}
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			for (k12 = 6; k12 <= 10; ++k12) {
				if (k12 == 10 && Math.abs(i12) >= 3) {
					continue;
				}
				for (j13 = 0; (j13 >= 0 || !isOpaque(world, i12, j13, k12)) && getY(j13) >= 0; --j13) {
					setBlockAndMetadata(world, i12, j13, k12, GOTRegistry.dirtPath, 0);
					setGrassToDirt(world, i12, j13 - 1, k12);
				}
				for (j13 = 1; j13 <= 8; ++j13) {
					setAir(world, i12, j13, k12);
				}
			}
		}
		for (int i15 : new int[] { -5, 5 }) {
			setBlockAndMetadata(world, i15, 2, -3, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15, 2, -2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15, 2, 0, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15, 2, 2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i15, 2, 3, fenceBlock, fenceMeta);
		}
		for (int k1321 : new int[] { -5, 5 }) {
			int i16;
			for (i16 = -1; i16 <= 1; ++i16) {
				for (int j14 = 2; j14 <= 3; ++j14) {
					setBlockAndMetadata(world, i16, j14, k1321, brickBlock, brickMeta);
				}
			}
			for (i16 = -4; i16 <= 4; ++i16) {
				setBlockAndMetadata(world, i16, 4, k1321, woodBeamBlock, woodBeamMeta | 4);
			}
		}
		for (int i17 = -3; i17 <= 3; ++i17) {
			if (Math.abs(i17) <= 1) {
				continue;
			}
			setBlockAndMetadata(world, i17, 2, -5, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i17, 3, -5, wallBlock, wallMeta);
			setBlockAndMetadata(world, i17, 2, 5, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i17, 3, 5, wallBlock, wallMeta);
		}
		setBlockAndMetadata(world, 0, 0, -5, rockBlock, rockMeta);
		setBlockAndMetadata(world, 0, 1, -5, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -5, doorBlock, 8);
		setBlockAndMetadata(world, 0, 3, -6, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 0, 5, rockBlock, rockMeta);
		setBlockAndMetadata(world, 0, 1, 5, doorBlock, 3);
		setBlockAndMetadata(world, 0, 2, 5, doorBlock, 8);
		setBlockAndMetadata(world, 0, 3, 6, Blocks.torch, 3);
		int[] i17 = { -5, 5 };
		k12 = i17.length;
		for (j13 = 0; j13 < k12; ++j13) {
			k132 = i17[j13];
			for (int l2 = 0; l2 <= 2; ++l2) {
				for (int i18 = -3 + l2; i18 <= 3 - l2; ++i18) {
					setBlockAndMetadata(world, i18, 5 + l2, k132, wallBlock, wallMeta);
				}
			}
		}
		setBlockAndMetadata(world, 0, 5, -5, wallBlock, wallMeta);
		setBlockAndMetadata(world, 0, 6, -5, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 7, -5, wallBlock, wallMeta);
		setBlockAndMetadata(world, 0, 5, 5, wallBlock, wallMeta);
		setBlockAndMetadata(world, 0, 6, 5, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 7, 5, wallBlock, wallMeta);
		for (k1 = -6; k1 <= 6; ++k1) {
			for (l = 0; l <= 5; ++l) {
				setBlockAndMetadata(world, -6 + l, 3 + l, k1, roofStairBlock, 1);
				setBlockAndMetadata(world, 6 - l, 3 + l, k1, roofStairBlock, 0);
			}
			setBlockAndMetadata(world, 0, 8, k1, roofBlock, roofMeta);
			setBlockAndMetadata(world, 0, 9, k1, roofSlabBlock, roofSlabMeta);
			if (Math.abs(k1) != 6) {
				continue;
			}
			for (l = 0; l <= 4; ++l) {
				setBlockAndMetadata(world, -5 + l, 3 + l, k1, roofStairBlock, 4);
				setBlockAndMetadata(world, 5 - l, 3 + l, k1, roofStairBlock, 5);
			}
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			setBlockAndMetadata(world, i1, 4, 0, woodBeamBlock, woodBeamMeta | 4);
		}
		for (k1 = -4; k1 <= 4; ++k1) {
			setBlockAndMetadata(world, 0, 4, k1, woodBeamBlock, woodBeamMeta | 8);
		}
		for (j1 = 1; j1 <= 7; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, woodBeamBlock, woodBeamMeta);
		}
		setBlockAndMetadata(world, -1, 3, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 1, 3, 0, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 3, -1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 3, 1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -4, 3, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 4, 3, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 0, 3, -4, Blocks.torch, 3);
		setBlockAndMetadata(world, 0, 3, 4, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 5, -4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 6, -4, Blocks.torch, 5);
		setBlockAndMetadata(world, 0, 5, 4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 6, 4, Blocks.torch, 5);
		setBlockAndMetadata(world, -2, 1, -4, bedBlock, 3);
		setBlockAndMetadata(world, -3, 1, -4, bedBlock, 11);
		setBlockAndMetadata(world, 2, 1, -4, bedBlock, 1);
		setBlockAndMetadata(world, 3, 1, -4, bedBlock, 9);
		setBlockAndMetadata(world, -4, 1, -2, bedBlock, 2);
		setBlockAndMetadata(world, -4, 1, -3, bedBlock, 10);
		setBlockAndMetadata(world, 4, 1, -2, bedBlock, 2);
		setBlockAndMetadata(world, 4, 1, -3, bedBlock, 10);
		setBlockAndMetadata(world, -4, 1, 0, Blocks.furnace, 4);
		setBlockAndMetadata(world, -4, 1, 1, rockSlabDoubleBlock, rockSlabDoubleMeta);
		placePlateWithCertainty(world, random, -4, 2, 1, plateBlock, GOTFoods.WESTEROS);
		setBlockAndMetadata(world, -4, 1, 2, Blocks.cauldron, 3);
		setBlockAndMetadata(world, -4, 1, 3, rockSlabDoubleBlock, rockSlabDoubleMeta);
		this.placeMug(world, random, -4, 2, 3, 3, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, -3, 1, 4, rockSlabDoubleBlock, rockSlabDoubleMeta);
		placeFlowerPot(world, -3, 2, 4, getRandomFlower(world, random));
		setBlockAndMetadata(world, -2, 1, 4, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 4, 1, 0, tableBlock, 0);
		this.placeChest(world, random, 4, 1, 1, 5, GOTChestContents.WESTEROS);
		this.placeChest(world, random, 4, 1, 2, 5, GOTChestContents.WESTEROS);
		setBlockAndMetadata(world, 4, 1, 3, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 3, 1, 4, rockSlabDoubleBlock, rockSlabDoubleMeta);
		placeFlowerPot(world, 3, 2, 4, getRandomFlower(world, random));
		setBlockAndMetadata(world, 2, 1, 4, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -5, 1, -6, GOTRegistry.reedBars, 0);
		for (i1 = -5; i1 <= -3; ++i1) {
			setBlockAndMetadata(world, i1, 1, -7, GOTRegistry.reedBars, 0);
		}
		placeFlowerPot(world, -4, 1, -6, getRandomFlower(world, random));
		placeFlowerPot(world, -3, 1, -6, getRandomFlower(world, random));
		placeFlowerPot(world, 2, 1, -6, getRandomFlower(world, random));
		setBlockAndMetadata(world, 3, 1, -6, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 4, 1, -6, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 5, 1, -6, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 4, 2, -6, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 4, 1, -7, Blocks.hay_block, 0);
		for (j1 = 1; j1 <= 2; ++j1) {
			for (k12 = 6; k12 <= 9; ++k12) {
				setBlockAndMetadata(world, -4, j1, k12, GOTRegistry.reedBars, 0);
				setBlockAndMetadata(world, 4, j1, k12, GOTRegistry.reedBars, 0);
			}
			setBlockAndMetadata(world, -3, j1, 9, GOTRegistry.reedBars, 0);
			setBlockAndMetadata(world, -2, j1, 9, GOTRegistry.reedBars, 0);
			setBlockAndMetadata(world, 2, j1, 9, GOTRegistry.reedBars, 0);
			setBlockAndMetadata(world, 3, j1, 9, GOTRegistry.reedBars, 0);
			for (i13 = -2; i13 <= 2; ++i13) {
				setBlockAndMetadata(world, i13, j1, 10, GOTRegistry.reedBars, 0);
			}
		}
		int[] j15 = { -2, 1 };
		i13 = j15.length;
		for (j13 = 0; j13 < i13; ++j13) {
			int i15;
			for (int i2 = i15 = j15[j13]; i2 <= i15 + 1; ++i2) {
				for (int k14 = 7; k14 <= 8; ++k14) {
					setBlockAndMetadata(world, i2, 0, k14, Blocks.farmland, 7);
					setBlockAndMetadata(world, i2, 1, k14, cropBlock, cropMeta);
				}
			}
		}
		setBlockAndMetadata(world, 0, -1, 9, Blocks.dirt, 0);
		setGrassToDirt(world, 0, -2, 9);
		setBlockAndMetadata(world, 0, 0, 9, Blocks.water, 0);
		setBlockAndMetadata(world, 0, 1, 9, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 2, 9, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 3, 9, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 0, 4, 9, Blocks.pumpkin, 0);
		GOTEntityNPC male = getMan(world);
		male.familyInfo.setMale(true);
		male.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(male, world, 0, 1, -1, 16);
		GOTEntityNPC female = getMan(world);
		female.familyInfo.setMale(false);
		female.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(female, world, 0, 1, -1, 16);
		GOTEntityNPC child = getMan(world);
		child.familyInfo.setMale(random.nextBoolean());
		child.familyInfo.setChild();
		spawnNPCAndSetHome(child, world, 0, 1, -1, 16);
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		wallBlock = GOTRegistry.daub;
		wallMeta = 0;
		if (random.nextInt(3) == 0) {
			roofBlock = brick2Block;
			roofMeta = brick2Meta;
			roofSlabBlock = brick2SlabBlock;
			roofSlabMeta = brick2SlabMeta;
			roofStairBlock = brick2StairBlock;
			bedBlock = Blocks.bed;
		}
	}
}
