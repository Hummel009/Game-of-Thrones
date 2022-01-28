package got.common.world.structure.essos.yiti;

import java.util.Random;

import got.common.database.*;
import got.common.entity.essos.yiti.GOTEntityLengMan;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureLengHouseStilts extends GOTStructureYiTiHouse {
	public GOTStructureLengHouseStilts(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k2;
		int i1;
		int j1;
		int k1;
		int i2;
		int i12;
		this.setOriginAndRotation(world, i, j, k, rotation, 5);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			int range = 5;
			for (int i11 = -range; i11 <= range; ++i11) {
				for (int k11 = -range; k11 <= range; ++k11) {
					int j11 = getTopBlock(world, i11, k11) - 1;
					if (!isSurface(world, i11, j11, k11)) {
						return false;
					}
					if (j11 < minHeight) {
						minHeight = j11;
					}
					if (j11 > maxHeight) {
						maxHeight = j11;
					}
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		bedBlock = GOTRegistry.strawBed;
		for (i12 = -3; i12 <= 3; ++i12) {
			for (k1 = -3; k1 <= 3; ++k1) {
				for (j1 = 3; j1 <= 7; ++j1) {
					setAir(world, i12, j1, k1);
				}
			}
		}
		placeStilt(world, -3, -3, false);
		placeStilt(world, 3, -3, false);
		placeStilt(world, -3, 3, false);
		placeStilt(world, 3, 3, false);
		placeStilt(world, 0, -4, true);
		for (i12 = -3; i12 <= 3; ++i12) {
			for (k1 = -3; k1 <= 3; ++k1) {
				i2 = Math.abs(i12);
				k2 = Math.abs(k1);
				if (i2 == 3 && k2 == 3) {
					for (j1 = 3; j1 <= 7; ++j1) {
						setBlockAndMetadata(world, i12, j1, k1, logBlock, logMeta);
					}
				}
				if (i2 == 3 && k2 <= 2) {
					setBlockAndMetadata(world, i12, 3, k1, logBlock, logMeta | 8);
					setBlockAndMetadata(world, i12, 4, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i12, 5, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i12, 6, k1, logBlock, logMeta | 8);
				}
				if (k2 == 3 && i2 <= 2) {
					setBlockAndMetadata(world, i12, 3, k1, logBlock, logMeta | 4);
					setBlockAndMetadata(world, i12, 4, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i12, 5, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i12, 6, k1, logBlock, logMeta | 4);
				}
				if (i2 <= 2 && k2 <= 2) {
					setBlockAndMetadata(world, i12, 3, k1, plankBlock, plankMeta);
					if (random.nextInt(3) == 0) {
						setBlockAndMetadata(world, i12, 4, k1, GOTRegistry.thatchFloor, 0);
					}
				}
				if (i12 == -3 && k2 == 1) {
					setBlockAndMetadata(world, i12, 4, k1, plankStairBlock, 1);
					setBlockAndMetadata(world, i12, 5, k1, fenceBlock, fenceMeta);
				}
				if (i12 == 3 && k2 == 1) {
					setBlockAndMetadata(world, i12, 4, k1, plankStairBlock, 0);
					setBlockAndMetadata(world, i12, 5, k1, fenceBlock, fenceMeta);
				}
				if (k1 != 3 || i2 != 1) {
					continue;
				}
				setBlockAndMetadata(world, i12, 4, k1, plankStairBlock, 3);
				setBlockAndMetadata(world, i12, 5, k1, fenceBlock, fenceMeta);
			}
		}
		int[] i13 = { 3, 6 };
		k1 = i13.length;
		for (i2 = 0; i2 < k1; ++i2) {
			int j12 = i13[i2];
			setBlockAndMetadata(world, -4, j12, -3, logBlock, logMeta | 4);
			setBlockAndMetadata(world, 4, j12, -3, logBlock, logMeta | 4);
			setBlockAndMetadata(world, -4, j12, 3, logBlock, logMeta | 4);
			setBlockAndMetadata(world, 4, j12, 3, logBlock, logMeta | 4);
			setBlockAndMetadata(world, -3, j12, -4, logBlock, logMeta | 8);
			setBlockAndMetadata(world, -3, j12, 4, logBlock, logMeta | 8);
			setBlockAndMetadata(world, 3, j12, -4, logBlock, logMeta | 8);
			setBlockAndMetadata(world, 3, j12, 4, logBlock, logMeta | 8);
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -4; k1 <= 4; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 == 4 && k2 == 2 || k2 == 4 && i2 == 2) {
					setBlockAndMetadata(world, i1, 6, k1, fenceBlock, fenceMeta);
				}
				if (i2 == 4 && k2 <= 3 || k2 == 4 && i2 <= 3) {
					setBlockAndMetadata(world, i1, 7, k1, thatchSlabBlock, thatchSlabMeta);
				}
				if (i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
					setBlockAndMetadata(world, i1, 7, k1, thatchBlock, thatchMeta);
				}
				if (i2 == 2 && k2 <= 2 || k2 == 2 && i2 <= 2) {
					setBlockAndMetadata(world, i1, 8, k1, thatchSlabBlock, thatchSlabMeta);
					if (i2 == 2 && k2 == 2) {
						setBlockAndMetadata(world, i1, 7, k1, thatchSlabBlock, thatchSlabMeta | 8);
					} else {
						setBlockAndMetadata(world, i1, 7, k1, fenceBlock, fenceMeta);
					}
				}
				if (i2 == 1 && k2 <= 1 || k2 == 1 && i2 <= 1) {
					setBlockAndMetadata(world, i1, 8, k1, thatchSlabBlock, thatchSlabMeta | 8);
				}
				if (i2 != 0 || k2 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 9, k1, thatchSlabBlock, thatchSlabMeta);
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 3, -4, plankBlock, plankMeta);
			if (i1 == 0) {
				continue;
			}
			setBlockAndMetadata(world, i1, 3, -5, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, i1, 4, -5, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -2, 4, -4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 4, -4, fenceBlock, fenceMeta);
		setAir(world, 0, 4, -3);
		setAir(world, 0, 5, -3);
		setBlockAndMetadata(world, 0, 6, -2, Blocks.torch, 3);
		setBlockAndMetadata(world, -2, 6, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 0, 6, 2, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 6, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 4, -2, logBlock, logMeta);
		this.placeChest(world, random, -2, 5, -2, 3, GOTChestContents.YI_TI);
		setBlockAndMetadata(world, 2, 4, -2, logBlock, logMeta);
		this.placeBarrel(world, random, 2, 5, -2, 3, GOTFoods.YITI_DRINK);
		for (int i14 : new int[] { -2, 2 }) {
			setBlockAndMetadata(world, i14, 4, 1, bedBlock, 0);
			setBlockAndMetadata(world, i14, 4, 2, bedBlock, 8);
		}
		setBlockAndMetadata(world, -1, 4, 2, GOTRegistry.tableYiTi, 0);
		setBlockAndMetadata(world, 0, 4, 2, logBlock, logMeta);
		this.placeMug(world, random, 0, 5, 2, 0, GOTFoods.YITI_DRINK);
		setBlockAndMetadata(world, 1, 4, 2, Blocks.crafting_table, 0);
		placeFuseTorch(world, -1, 5, -5);
		placeFuseTorch(world, 1, 5, -5);
		GOTEntityLengMan male = new GOTEntityLengMan(world);
		male.familyInfo.setMale(true);
		male.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(male, world, 0, 4, 0, 2);
		GOTEntityLengMan female = new GOTEntityLengMan(world);
		female.familyInfo.setMale(false);
		female.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(female, world, 0, 4, 0, 2);
		GOTEntityLengMan child = new GOTEntityLengMan(world);
		child.familyInfo.setMale(random.nextBoolean());
		child.familyInfo.setChild();
		spawnNPCAndSetHome(child, world, 0, 4, 0, 2);
		return true;
	}

	public void placeStilt(World world, int i, int k, boolean ladder) {
		for (int j = 3; (j == 3 || !isOpaque(world, i, j, k)) && getY(j) >= 0; --j) {
			setBlockAndMetadata(world, i, j, k, logBlock, logMeta);
			setGrassToDirt(world, i, j - 1, k);
			if (!ladder) {
				continue;
			}
			setBlockAndMetadata(world, i, j, k - 1, Blocks.ladder, 2);
		}
	}
	
	public void placeFuseTorch(World world, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, GOTRegistry.fuse, 0);
		setBlockAndMetadata(world, i, j + 1, k, GOTRegistry.fuse, 1);
	}
}
