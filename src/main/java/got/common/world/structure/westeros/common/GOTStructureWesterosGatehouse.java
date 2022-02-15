package got.common.world.structure.westeros.common;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureWesterosGatehouse extends GOTStructureWesterosBase {
	public GOTStructureWesterosGatehouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int i2;
		int k1;
		int j1;
		int k2;
		int i22;
		int k12;
		int k13;
		int i1;
		int i122;
		int j12;
		int i13;
		int i14;
		int j13;
		this.setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i15 = -6; i15 <= 6; ++i15) {
				for (int k14 = -5; k14 <= 5; ++k14) {
					j12 = getTopBlock(world, i15, k14) - 1;
					if (!isSurface(world, i15, j12, k14)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (i14 = -3; i14 <= 3; ++i14) {
			for (k13 = -3; k13 <= 3; ++k13) {
				i2 = Math.abs(i14);
				k2 = Math.abs(k13);
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i14, j12, k13)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i14, j12, k13, cobbleBlock, cobbleMeta);
					setGrassToDirt(world, i14, j12 - 1, k13);
				}
				for (j12 = 1; j12 <= 14; ++j12) {
					setAir(world, i14, j12, k13);
				}
				if (i2 == 3 && k2 == 3) {
					for (j12 = 1; j12 <= 10; ++j12) {
						setBlockAndMetadata(world, i14, j12, k13, pillarBlock, pillarMeta);
					}
				} else {
					if (i2 == 3) {
						for (j12 = 1; j12 <= 6; ++j12) {
							setBlockAndMetadata(world, i14, j12, k13, brickBlock, brickMeta);
						}
					}
					setBlockAndMetadata(world, i14, 7, k13, brickBlock, brickMeta);
				}
				if (i2 > 3 || k2 > 3) {
					continue;
				}
				if (i2 == 3 || k2 == 3) {
					setBlockAndMetadata(world, i14, 11, k13, rockSlabDoubleBlock, rockSlabDoubleMeta);
					continue;
				}
				setBlockAndMetadata(world, i14, 11, k13, rockSlabBlock, rockSlabMeta | 8);
			}
		}
		for (i14 = -2; i14 <= 2; ++i14) {
			setBlockAndMetadata(world, i14, 0, -1, cobbleStairBlock, 3);
			setBlockAndMetadata(world, i14, 0, 1, cobbleStairBlock, 2);
			setBlockAndMetadata(world, i14, -1, 0, cobbleBlock, cobbleMeta);
			setGrassToDirt(world, i14, -2, 0);
			setAir(world, i14, 0, 0);
		}
		for (i14 = -2; i14 <= 2; ++i14) {
			for (j13 = 1; j13 <= 7; ++j13) {
				if (j13 > 6 && i14 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i14, j13, -1, gateBlock, 2);
				setBlockAndMetadata(world, i14, j13, 1, GOTRegistry.gateIronBars, 2);
			}
		}
		for (int k14 : new int[] { -3, 3 }) {
			setBlockAndMetadata(world, -2, 6, k14, brickStairBlock, 4);
			setBlockAndMetadata(world, 2, 6, k14, brickStairBlock, 5);
			setBlockAndMetadata(world, -2, 5, k14, Blocks.torch, 2);
			setBlockAndMetadata(world, 2, 5, k14, Blocks.torch, 1);
			for (int i16 = -2; i16 <= 2; ++i16) {
				int i23 = Math.abs(i16);
				setBlockAndMetadata(world, i16, 8, k14, rockSlabDoubleBlock, rockSlabDoubleMeta);
				if (i23 % 2 == 0) {
					setBlockAndMetadata(world, i16, 9, k14, GOTRegistry.gateIronBars, 2);
				} else {
					setBlockAndMetadata(world, i16, 9, k14, brickBlock, brickMeta);
				}
				if (i23 == 0) {
					setBlockAndMetadata(world, i16, 10, k14, GOTRegistry.brick1, 5);
					continue;
				}
				setBlockAndMetadata(world, i16, 10, k14, brickBlock, brickMeta);
			}
		}
		int[] i17 = { -3, 3 };
		j13 = i17.length;
		for (i2 = 0; i2 < j13; ++i2) {
			i122 = i17[i2];
			for (int k15 : new int[] { -2, 2 }) {
				setBlockAndMetadata(world, i122, 8, k15, rockSlabDoubleBlock, rockSlabDoubleMeta);
				setBlockAndMetadata(world, i122, 9, k15, brickBlock, brickMeta);
				setBlockAndMetadata(world, i122, 10, k15, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, i122, 10, -1, brickStairBlock, 7);
			setBlockAndMetadata(world, i122, 10, 1, brickStairBlock, 6);
		}
		for (i13 = -3; i13 <= 3; ++i13) {
			setBlockAndMetadata(world, i13, 11, -4, brickStairBlock, 6);
			setBlockAndMetadata(world, i13, 11, 4, brickStairBlock, 7);
		}
		for (int k16 = -3; k16 <= 3; ++k16) {
			setBlockAndMetadata(world, -4, 11, k16, brickStairBlock, 5);
			setBlockAndMetadata(world, 4, 11, k16, brickStairBlock, 4);
		}
		for (i13 = -4; i13 <= 4; ++i13) {
			for (k13 = -4; k13 <= 4; ++k13) {
				i2 = Math.abs(i13);
				k2 = Math.abs(k13);
				if (i2 > 3 && k2 > 3 || i2 != 4 && k2 != 4) {
					continue;
				}
				if ((i2 + k2) % 2 == 1) {
					setBlockAndMetadata(world, i13, 12, k13, brickBlock, brickMeta);
					setBlockAndMetadata(world, i13, 13, k13, brickSlabBlock, brickSlabMeta);
					continue;
				}
				setBlockAndMetadata(world, i13, 12, k13, brickWallBlock, brickWallMeta);
			}
		}
		setBlockAndMetadata(world, 0, 8, -1, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 8, 0, fenceBlock, fenceMeta);
		setAir(world, 0, 7, 0);
		setBlockAndMetadata(world, 0, 8, 1, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 9, -1, Blocks.lever, 14);
		setBlockAndMetadata(world, 0, 9, 1, Blocks.lever, 14);
		for (int i1221 : new int[] { -1, 1 }) {
			for (j12 = 8; j12 <= 11; ++j12) {
				setBlockAndMetadata(world, i1221, j12, 2, Blocks.ladder, 2);
			}
		}
		setBlockAndMetadata(world, -2, 10, -2, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 10, -2, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 10, 2, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 10, 2, Blocks.torch, 1);
		placeWallBanner(world, 1, 10, -3, banner, 0);
		placeWallBanner(world, -1, 10, -3, banner, 0);
		setBlockAndMetadata(world, -3, 12, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 3, 12, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, -3, 12, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, 3, 12, 3, Blocks.torch, 4);
		placeWallBanner(world, -3, 7, -3, banner, 2);
		placeWallBanner(world, 3, 7, -3, banner, 2);
		placeWallBanner(world, 3, 7, 3, banner, 0);
		placeWallBanner(world, -3, 7, 3, banner, 0);

		for (i1 = -5; i1 <= 5; ++i1) {
			i22 = Math.abs(i1);
			if (i22 >= 4) {
				for (k12 = -1; k12 <= 1; ++k12) {
					for (j1 = 4; (j1 >= 0 || !isOpaque(world, i1, j1, k12)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i1, j1, k12, brickBlock, brickMeta);
						setGrassToDirt(world, i1, j1 - 1, k12);
					}
				}
				k12 = -2;
				for (j1 = 7; (j1 >= 0 || !isOpaque(world, i1, j1, k12)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k12);
				}
				setBlockAndMetadata(world, i1, 4, k12, brick2Block, brick2Meta);
				setBlockAndMetadata(world, i1, 8, k12, rockWallBlock, rockWallMeta);
			}
			if (i22 == 5) {
				k12 = -3;
				for (j1 = 7; (j1 >= 0 || !isOpaque(world, i1, j1, k12)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k12, pillarBlock, pillarMeta);
					setGrassToDirt(world, i1, j1 - 1, k12);
				}
				setBlockAndMetadata(world, i1, 8, k12, rockWallBlock, rockWallMeta);
			}
			if (i22 != 4) {
				continue;
			}
			k12 = -3;
			setBlockAndMetadata(world, i1, 5, k12, brickStairBlock, 6);
			setBlockAndMetadata(world, i1, 6, k12, rockWallBlock, rockWallMeta);
		}
		for (k1 = -1; k1 <= 1; ++k1) {
			setBlockAndMetadata(world, -3, 7, k1, brickStairBlock, 1);
			setBlockAndMetadata(world, -4, 6, k1, brickStairBlock, 1);
			setBlockAndMetadata(world, -5, 5, k1, brickStairBlock, 1);
			setBlockAndMetadata(world, -4, 5, k1, brickBlock, brickMeta);
			setBlockAndMetadata(world, 3, 7, k1, brickStairBlock, 0);
			setBlockAndMetadata(world, 4, 6, k1, brickStairBlock, 0);
			setBlockAndMetadata(world, 5, 5, k1, brickStairBlock, 0);
			setBlockAndMetadata(world, 4, 5, k1, brickBlock, brickMeta);
		}
		for (i1 = -8; i1 <= 8; ++i1) {
			i22 = Math.abs(i1);
			if (i22 < 6) {
				continue;
			}
			for (k12 = 0; k12 <= 1; ++k12) {
				for (j1 = 4; (j1 >= 0 || !isOpaque(world, i1, j1, k12)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k12);
				}
			}
		}
		for (k1 = 0; k1 <= 1; ++k1) {
			int j2;
			int step;
			int maxStep = 12;
			for (step = 0; step < maxStep && !isOpaque(world, i122 = -9 - step, j12 = 4 - step, k1); ++step) {
				setBlockAndMetadata(world, i122, j12, k1, brickStairBlock, 1);
				setGrassToDirt(world, i122, j12 - 1, k1);
				j2 = j12 - 1;
				while (!isOpaque(world, i122, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i122, j2, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i122, j2 - 1, k1);
					--j2;
				}
			}
			for (step = 0; step < maxStep && !isOpaque(world, i122 = 9 + step, j12 = 4 - step, k1); ++step) {
				setBlockAndMetadata(world, i122, j12, k1, brickStairBlock, 0);
				setGrassToDirt(world, i122, j12 - 1, k1);
				j2 = j12 - 1;
				while (!isOpaque(world, i122, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i122, j2, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i122, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (i1 = -9; i1 <= 9; ++i1) {
			i22 = Math.abs(i1);
			if (i22 == 5 || i22 == 8) {
				setBlockAndMetadata(world, i1, 3, 1, GOTRegistry.brick1, 5);
				continue;
			}
			if (i22 < 4) {
				continue;
			}
			setBlockAndMetadata(world, i1, 3, 1, brickStairBlock, 7);
		}
		for (int i1221 : new int[] { -1, 1 }) {
			j12 = 8;
			int k17 = 0;
			GOTEntityNPC levyman = getSoldier(world);
			levyman.spawnRidingHorse = false;
			spawnNPCAndSetHome(levyman, world, i1221, j12, k17, 8);
		}
		return true;
	}
}
