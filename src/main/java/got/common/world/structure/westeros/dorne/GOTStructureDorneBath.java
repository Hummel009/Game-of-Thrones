package got.common.world.structure.westeros.dorne;

import java.util.Random;

import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.dorne.GOTEntityDorneMan;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureDorneBath extends GOTStructureDorneBase {
	public GOTStructureDorneBath(boolean flag) {
		super(flag);
	}

	public GOTEntityNPC createBather(World world) {
		return new GOTEntityDorneMan(world);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k2;
		int i1;
		int i2;
		int k1;
		int k12;
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 10);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i1 = -11; i1 <= 11; ++i1) {
				for (k1 = -9; k1 <= 9; ++k1) {
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
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i13 = -11; i13 <= 11; ++i13) {
			for (int k122 = -9; k122 <= 9; ++k122) {
				i2 = Math.abs(i13);
				k2 = Math.abs(k122);
				for (j1 = 0; (j1 >= -1 || !isOpaque(world, i13, j1, k122)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i13, j1, k122, brickBlock, brickMeta);
					setGrassToDirt(world, i13, j1 - 1, k122);
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i13, j1, k122);
				}
				if (i2 > 6 || k2 > 4 || i2 + k2 > 8) {
					continue;
				}
				setBlockAndMetadata(world, i13, 0, k122, Blocks.water, 0);
			}
		}
		for (int i14 = -10; i14 <= 10; ++i14) {
			for (k12 = -8; k12 <= 8; ++k12) {
				i2 = Math.abs(i14);
				k2 = Math.abs(k12);
				if (i2 == 10 && k2 % 4 == 0 || k2 == 8 && i2 % 4 == 2) {
					for (j1 = 1; j1 <= 4; ++j1) {
						setBlockAndMetadata(world, i14, j1, k12, pillarBlock, pillarMeta);
					}
					setBlockAndMetadata(world, i14 - 1, 1, k12, brickStairBlock, 1);
					setBlockAndMetadata(world, i14 + 1, 1, k12, brickStairBlock, 0);
					setBlockAndMetadata(world, i14, 1, k12 - 1, brickStairBlock, 2);
					setBlockAndMetadata(world, i14, 1, k12 + 1, brickStairBlock, 3);
					setBlockAndMetadata(world, i14 - 1, 4, k12, brickStairBlock, 5);
					setBlockAndMetadata(world, i14 + 1, 4, k12, brickStairBlock, 4);
					setBlockAndMetadata(world, i14, 4, k12 - 1, brickStairBlock, 6);
					setBlockAndMetadata(world, i14, 4, k12 + 1, brickStairBlock, 7);
				}
				if (i2 != 10 && k2 != 8) {
					continue;
				}
				setBlockAndMetadata(world, i14, 5, k12, brickBlock, brickMeta);
			}
		}
		int[] i14 = { -6, 6 };
		k12 = i14.length;
		for (i2 = 0; i2 < k12; ++i2) {
			int i15 = i14[i2];
			for (int k13 : new int[] { -4, 4 }) {
				for (int j12 = 1; j12 <= 7; ++j12) {
					setBlockAndMetadata(world, i15, j12, k13, pillarBlock, pillarMeta);
				}
				setBlockAndMetadata(world, i15 - 1, 1, k13, brickStairBlock, 1);
				setBlockAndMetadata(world, i15 + 1, 1, k13, brickStairBlock, 0);
				setBlockAndMetadata(world, i15, 1, k13 - 1, brickStairBlock, 2);
				setBlockAndMetadata(world, i15, 1, k13 + 1, brickStairBlock, 3);
				setBlockAndMetadata(world, i15 - 1, 7, k13, brickStairBlock, 5);
				setBlockAndMetadata(world, i15 + 1, 7, k13, brickStairBlock, 4);
				setBlockAndMetadata(world, i15, 7, k13 - 1, brickStairBlock, 6);
				setBlockAndMetadata(world, i15, 7, k13 + 1, brickStairBlock, 7);
				setBlockAndMetadata(world, i15 - 1, 4, k13, Blocks.torch, 1);
				setBlockAndMetadata(world, i15 + 1, 4, k13, Blocks.torch, 2);
				setBlockAndMetadata(world, i15, 4, k13 - 1, Blocks.torch, 4);
				setBlockAndMetadata(world, i15, 4, k13 + 1, Blocks.torch, 3);
			}
		}
		for (int step = 0; step <= 3; ++step) {
			int j13 = 5 + step;
			i1 = 11 - step;
			k1 = 9 - step;
			for (int i22 = -i1; i22 <= i1; ++i22) {
				setBlockAndMetadata(world, i22, j13, -k1, brick2StairBlock, 2);
				setBlockAndMetadata(world, i22, j13, k1, brick2StairBlock, 3);
			}
			for (int k22 = -k1 + 1; k22 <= k1 - 1; ++k22) {
				setBlockAndMetadata(world, -i1, j13, k22, brick2StairBlock, 1);
				setBlockAndMetadata(world, i1, j13, k22, brick2StairBlock, 0);
			}
			if (step < 2) {
				continue;
			}
			for (int i22 = -i1 + 1; i22 <= i1 - 1; ++i22) {
				setBlockAndMetadata(world, i22, j13 - 1, -k1, brick2StairBlock, 7);
				setBlockAndMetadata(world, i22, j13 - 1, k1, brick2StairBlock, 6);
			}
			for (int k22 = -k1; k22 <= k1; ++k22) {
				setBlockAndMetadata(world, -i1, j13 - 1, k22, brick2StairBlock, 4);
				setBlockAndMetadata(world, i1, j13 - 1, k22, brick2StairBlock, 5);
			}
		}
		for (int i12 = -7; i12 <= 7; ++i12) {
			for (k12 = -5; k12 <= 5; ++k12) {
				setBlockAndMetadata(world, i12, 8, k12, brick2Block, brick2Meta);
			}
		}
		for (int i12 = -9; i12 <= 9; ++i12) {
			for (k12 = -7; k12 <= 7; ++k12) {
				i2 = Math.abs(i12);
				k2 = Math.abs(k12);
				if ((i2 != 9 || k2 % 4 != 0) && (k2 != 7 || i2 % 4 != 2)) {
					continue;
				}
				for (int j14 = 5; j14 <= 6; ++j14) {
					setBlockAndMetadata(world, i12, j14, k12, brickBlock, brickMeta);
				}
			}
		}
		int bathers = 2 + random.nextInt(4);
		for (int l = 0; l < bathers; ++l) {
			GOTEntityNPC man = createBather(world);
			spawnNPCAndSetHome(man, world, 0, 0, 0, 16);
		}
		return true;
	}
}
