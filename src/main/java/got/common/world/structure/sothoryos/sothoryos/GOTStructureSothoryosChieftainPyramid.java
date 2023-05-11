package got.common.world.structure.sothoryos.sothoryos;

import got.common.database.GOTRegistry;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosChieftain;
import got.common.item.other.GOTItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSothoryosChieftainPyramid extends GOTStructureSothoryosHouse {
	public GOTStructureSothoryosChieftainPyramid(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int j1;
		int k2;
		int i12;
		int i1;
		int k12;
		int j12;
		int i2;
		if (!super.generate(world, random, i, j, k, rotation)) {
			return false;
		}
		for (i12 = -10; i12 <= 10; ++i12) {
			for (k1 = -10; k1 <= 10; ++k1) {
				layFoundation(world, i12, k1);
				for (j1 = 1; j1 <= 14; ++j1) {
					setAir(world, i12, j1, k1);
				}
			}
		}
		for (i12 = -10; i12 <= 10; ++i12) {
			for (k1 = -10; k1 <= 10; ++k1) {
				i2 = Math.abs(i12);
				k2 = Math.abs(k1);
				if (i2 >= 8 || k2 >= 8) {
					setBlockAndMetadata(world, i12, 0, k1, brickBlock, brickMeta);
					if (k1 < 0 && i12 == 0) {
						setBlockAndMetadata(world, i12, 0, k1, GOTRegistry.brick4, 4);
					}
					if (i2 > 9 || k2 > 9 || i2 != 9 && k2 != 9) {
						continue;
					}
					setBlockAndMetadata(world, i12, 0, k1, GOTRegistry.brick4, 4);
					continue;
				}
				for (j1 = 1; j1 <= 4; ++j1) {
					setBlockAndMetadata(world, i12, j1, k1, brickBlock, brickMeta);
				}
				if (i2 <= 1 && k1 <= 0) {
					int step = 4 - (-3 - k1);
					if (step < 0 || step > 4) {
						continue;
					}
					for (j12 = step + 1; j12 <= 4; ++j12) {
						setAir(world, i12, j12, k1);
					}
					if (step == 0) {
						setBlockAndMetadata(world, -1, step, k1, brickBlock, brickMeta);
						setBlockAndMetadata(world, 0, step, k1, GOTRegistry.brick4, 4);
						setBlockAndMetadata(world, 1, step, k1, brickBlock, brickMeta);
						continue;
					}
					if (step > 4) {
						continue;
					}
					setBlockAndMetadata(world, -1, step, k1, brickStairBlock, 2);
					setBlockAndMetadata(world, 0, step, k1, GOTRegistry.stairsSothoryosBrickObsidian, 2);
					setBlockAndMetadata(world, 1, step, k1, brickStairBlock, 2);
					continue;
				}
				if ((i2 != 7 || k1 % 2 != 0) && (k2 != 7 || i12 % 2 != 0)) {
					continue;
				}
				setBlockAndMetadata(world, i12, 1, k1, brickWallBlock, brickWallMeta);
				placeSothoryosTorch(world, i12, 2, k1);
			}
		}
		for (int k13 = -2; k13 <= 4; ++k13) {
			setBlockAndMetadata(world, 0, 4, k13, GOTRegistry.brick4, 4);
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			setBlockAndMetadata(world, i12, 4, 0, GOTRegistry.brick4, 4);
		}
		for (int i13 : new int[]{-5, 5}) {
			for (int k14 : new int[]{-5, 5}) {
				for (int i22 = i13 - 1; i22 <= i13 + 1; ++i22) {
					for (int k22 = k14 - 1; k22 <= k14 + 1; ++k22) {
						int i3 = Math.abs(i22 - i13);
						int k3 = Math.abs(k22 - k14);
						if (i3 == 1 && k3 == 1) {
							setBlockAndMetadata(world, i22, 5, k22, brickSlabBlock, brickSlabMeta);
							continue;
						}
						if (i3 == 1 || k3 == 1) {
							setBlockAndMetadata(world, i22, 5, k22, brickBlock, brickMeta);
							continue;
						}
						setBlockAndMetadata(world, i22, 5, k22, GOTRegistry.hearth, 0);
						setBlockAndMetadata(world, i22, 6, k22, Blocks.fire, 0);
					}
				}
			}
		}
		for (int i13 : new int[]{-3, 3}) {
			setBlockAndMetadata(world, i13, 5, -6, brickWallBlock, brickWallMeta);
			for (int j13 = 5; j13 <= 7; ++j13) {
				for (int k15 = -5; k15 <= -3; ++k15) {
					setBlockAndMetadata(world, i13, j13, k15, brickBlock, brickMeta);
				}
				setBlockAndMetadata(world, i13, j13, -1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, j13, 1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, j13, 3, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, i13, 5, 4, brickBlock, brickMeta);
			setBlockAndMetadata(world, i13, 7, 0, brickSlabBlock, brickSlabMeta | 8);
			setBlockAndMetadata(world, i13, 5, -2, brickStairBlock, 3);
			setBlockAndMetadata(world, i13, 7, -2, brickStairBlock, 7);
			setBlockAndMetadata(world, i13, 5, 2, brickStairBlock, 2);
			setBlockAndMetadata(world, i13, 7, 2, brickStairBlock, 6);
			for (int k16 = -4; k16 <= 4; ++k16) {
				if (k16 == 0 || Math.abs(k16) == 3) {
					setBlockAndMetadata(world, i13, 8, k16, brickBlock, brickMeta);
					continue;
				}
				setBlockAndMetadata(world, i13, 8, k16, brickSlabBlock, brickSlabMeta);
			}
		}
		int[] k13 = {-4, 4};
		j12 = k13.length;
		for (int i3 = 0; i3 < j12; ++i3) {
			int i13;
			i13 = k13[i3];
			for (int j14 = 5; j14 <= 7; ++j14) {
				setBlockAndMetadata(world, i13, j14, -4, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, j14, -2, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, j14, 2, brickBlock, brickMeta);
				setBlockAndMetadata(world, i13, j14, 4, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, i13, 5, -3, brickBlock, brickMeta);
			setBlockAndMetadata(world, i13, 5, 3, brickBlock, brickMeta);
			setBlockAndMetadata(world, i13, 7, -1, brickSlabBlock, brickSlabMeta | 8);
			setBlockAndMetadata(world, i13, 7, 1, brickSlabBlock, brickSlabMeta | 8);
			for (int k17 = -4; k17 <= 4; ++k17) {
				if (Math.abs(k17) == 4) {
					setBlockAndMetadata(world, i13, 8, k17, brickBlock, brickMeta);
					continue;
				}
				setBlockAndMetadata(world, i13, 8, k17, brickSlabBlock, brickSlabMeta);
			}
		}
		int[] i14 = {-2, 2};
		k1 = i14.length;
		for (i2 = 0; i2 < k1; ++i2) {
			int i13 = i14[i2];
			setBlockAndMetadata(world, i13, 5, -6, brickWallBlock, brickWallMeta);
			placeSothoryosTorch(world, i13, 6, -6);
			setBlockAndMetadata(world, i13, 5, -5, brickBlock, brickMeta);
			setBlockAndMetadata(world, i13, 6, -5, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, i13, 8, -4, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, i13, 8, -3, brickSlabBlock, brickSlabMeta);
			placeArmorStand(world, i13, 5, 2, 0, new ItemStack[]{new ItemStack(GOTRegistry.sothoryosHelmet), new ItemStack(GOTRegistry.sothoryosChestplate), new ItemStack(GOTRegistry.sothoryosLeggings), new ItemStack(GOTRegistry.sothoryosBoots)});
		}
		for (j12 = 5; j12 <= 7; ++j12) {
			setBlockAndMetadata(world, -2, j12, 4, brickBlock, brickMeta);
			setBlockAndMetadata(world, 2, j12, 4, brickBlock, brickMeta);
			setBlockAndMetadata(world, -1, j12, 3, brickBlock, brickMeta);
			setBlockAndMetadata(world, 1, j12, 3, brickBlock, brickMeta);
		}
		setBlockAndMetadata(world, 0, 7, 3, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, -2, 5, 3, brickStairBlock, 0);
		setBlockAndMetadata(world, -2, 7, 3, brickStairBlock, 4);
		setBlockAndMetadata(world, 2, 5, 3, brickStairBlock, 1);
		setBlockAndMetadata(world, 2, 7, 3, brickStairBlock, 5);
		setBlockAndMetadata(world, -1, 7, 4, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, 1, 7, 4, brickSlabBlock, brickSlabMeta | 8);
		for (i1 = -2; i1 <= 2; ++i1) {
			setBlockAndMetadata(world, i1, 8, 3, brickSlabBlock, brickSlabMeta);
			setBlockAndMetadata(world, i1, 8, 4, brickSlabBlock, brickSlabMeta);
		}
		setBlockAndMetadata(world, 0, 8, 3, brickBlock, brickMeta);
		for (k12 = -3; k12 <= 3; ++k12) {
			setBlockAndMetadata(world, -7, 5, k12, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 7, 5, k12, brickWallBlock, brickWallMeta);
		}
		for (i1 = -6; i1 <= -5; ++i1) {
			setBlockAndMetadata(world, i1, 5, -3, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i1, 5, 3, brickWallBlock, brickWallMeta);
		}
		for (i1 = 5; i1 <= 6; ++i1) {
			setBlockAndMetadata(world, i1, 5, -3, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i1, 5, 3, brickWallBlock, brickWallMeta);
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 5, 7, brickWallBlock, brickWallMeta);
		}
		for (k12 = 5; k12 <= 6; ++k12) {
			setBlockAndMetadata(world, -3, 5, k12, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 3, 5, k12, brickWallBlock, brickWallMeta);
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				for (j12 = 8; j12 <= 9; ++j12) {
					setBlockAndMetadata(world, i1, j12, k1, brickBlock, brickMeta);
				}
				if (i2 == 2 && k2 == 2) {
					setBlockAndMetadata(world, i1, 10, k1, brickSlabBlock, brickSlabMeta);
				} else {
					setBlockAndMetadata(world, i1, 10, k1, brickBlock, brickMeta);
				}
				if (i2 == 1 && k2 == 1) {
					for (j12 = 11; j12 <= 12; ++j12) {
						setBlockAndMetadata(world, i1, j12, k1, brickWallBlock, brickWallMeta);
					}
				}
				if (i2 == 0 && k2 == 0) {
					setBlockAndMetadata(world, i1, 10, k1, GOTRegistry.hearth, 0);
					setBlockAndMetadata(world, i1, 11, k1, Blocks.fire, 0);
				}
				if (i2 > 1 || k2 > 1) {
					continue;
				}
				setBlockAndMetadata(world, i1, 13, k1, GOTRegistry.brick4, 3);
				if (k1 == -1) {
					setBlockAndMetadata(world, i1, 14, k1, GOTRegistry.stairsSothoryosBrickGold, 2);
					continue;
				}
				if (k1 == 1) {
					setBlockAndMetadata(world, i1, 14, k1, GOTRegistry.stairsSothoryosBrickGold, 3);
					continue;
				}
				if (i1 == -1) {
					setBlockAndMetadata(world, i1, 14, k1, GOTRegistry.stairsSothoryosBrickGold, 1);
					continue;
				}
				if (i1 == 1) {
					setBlockAndMetadata(world, i1, 14, k1, GOTRegistry.stairsSothoryosBrickGold, 0);
					continue;
				}
				setBlockAndMetadata(world, i1, 14, k1, GOTRegistry.brick4, 3);
				setBlockAndMetadata(world, i1, 15, k1, GOTRegistry.wallStone4, 3);
				placeSothoryosTorch(world, i1, 16, k1);
			}
		}
		setBlockAndMetadata(world, -1, 8, -3, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 8, -3, brickSlabBlock, brickSlabMeta | 8);
		setBlockAndMetadata(world, 0, 9, -3, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, 1, 8, -3, brickBlock, brickMeta);
		placeWallBanner(world, -1, 8, -3, GOTItemBanner.BannerType.SOTHORYOS, 2);
		placeWallBanner(world, 1, 8, -3, GOTItemBanner.BannerType.SOTHORYOS, 2);
		placeWallBanner(world, -3, 7, -3, GOTItemBanner.BannerType.SOTHORYOS, 1);
		placeWallBanner(world, 3, 7, -3, GOTItemBanner.BannerType.SOTHORYOS, 3);
		setBlockAndMetadata(world, -2, 6, -1, Blocks.torch, 2);
		setBlockAndMetadata(world, -2, 6, 1, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 6, -1, Blocks.torch, 1);
		setBlockAndMetadata(world, 2, 6, 1, Blocks.torch, 1);
		spawnItemFrame(world, -1, 6, 3, 2, new ItemStack(GOTRegistry.sarbacaneTrap));
		spawnItemFrame(world, 1, 6, 3, 2, new ItemStack(GOTRegistry.sothoryosDagger));
		GOTEntitySothoryosChieftain chieftain = new GOTEntitySothoryosChieftain(world);
		spawnNPCAndSetHome(chieftain, world, 0, 5, 0, 8);
		return true;
	}

	@Override
	public int getOffset() {
		return 11;
	}

	@Override
	public boolean useStoneBrick() {
		return true;
	}
}
