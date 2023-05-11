package got.common.world.structure.essos.asshai;

import got.common.database.GOTRegistry;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureAsshaiAltar extends GOTStructureAsshaiBase {
	public GOTStructureAsshaiAltar(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int k1;
		int k12;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k1 = -5; k1 <= 5; ++k1) {
				j1 = 0;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					placeRandomBrick(world, random, i1, j1, k1);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 <= 4 && k2 <= 4) {
					placeRandomBrick(world, random, i1, 1, k1);
				}
				if (i2 > 3 || k2 > 3) {
					continue;
				}
				if (random.nextInt(10) == 0) {
					setBlockAndMetadata(world, i1, 2, k1, GOTRegistry.brick1, 0);
					continue;
				}
				placeRandomBrick(world, random, i1, 2, k1);
			}
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			placeRandomStairs(world, random, i1, 1, -5, 2);
			placeRandomStairs(world, random, i1, 1, 5, 3);
		}
		for (k12 = -5; k12 <= 5; ++k12) {
			placeRandomStairs(world, random, -5, 1, k12, 1);
			placeRandomStairs(world, random, 5, 1, k12, 0);
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			placeRandomStairs(world, random, i1, 2, -4, 2);
			placeRandomStairs(world, random, i1, 2, 4, 3);
		}
		for (k12 = -4; k12 <= 4; ++k12) {
			placeRandomStairs(world, random, -4, 2, k12, 1);
			placeRandomStairs(world, random, 4, 2, k12, 0);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			placeRandomStairs(world, random, i1, 3, -1, 2);
			placeRandomStairs(world, random, i1, 3, 1, 3);
		}
		for (k12 = -1; k12 <= 1; ++k12) {
			placeRandomStairs(world, random, -1, 3, k12, 1);
			placeRandomStairs(world, random, 1, 3, k12, 0);
		}
		placeRandomBrick(world, random, 0, 3, 0);
		setBlockAndMetadata(world, 0, 4, 0, GOTRegistry.tableAsshai, 0);
		for (int x = -4; x <= 3; x += 7) {
			for (int z = -4; z <= 3; z += 7) {
				for (int i12 = x; i12 <= x + 1; ++i12) {
					for (int k13 = z; k13 <= z + 1; ++k13) {
						for (int j12 = 2; j12 <= 5; ++j12) {
							placeRandomBrick(world, random, i12, j12, k13);
						}
					}
				}
			}
		}
		setBlockAndMetadata(world, -4, 6, -4, GOTRegistry.wallStone1, 0);
		setBlockAndMetadata(world, -4, 7, -4, GOTRegistry.asshaiTorch, 5);
		setBlockAndMetadata(world, 4, 6, -4, GOTRegistry.wallStone1, 0);
		setBlockAndMetadata(world, 4, 7, -4, GOTRegistry.asshaiTorch, 5);
		setBlockAndMetadata(world, -4, 6, 4, GOTRegistry.wallStone1, 0);
		setBlockAndMetadata(world, -4, 7, 4, GOTRegistry.asshaiTorch, 5);
		setBlockAndMetadata(world, 4, 6, 4, GOTRegistry.wallStone1, 0);
		setBlockAndMetadata(world, 4, 7, 4, GOTRegistry.asshaiTorch, 5);
		for (i1 = -4; i1 <= 4; i1 += 8) {
			placeRandomStairs(world, random, i1, 6, -3, 2);
			placeRandomStairs(world, random, i1, 6, -2, 7);
			setBlockAndMetadata(world, i1, 7, -2, GOTRegistry.brick1, 0);
			placeRandomStairs(world, random, i1, 8, -2, 2);
			placeRandomStairs(world, random, i1, 8, -1, 7);
			placeRandomStairs(world, random, i1, 6, 3, 3);
			placeRandomStairs(world, random, i1, 6, 2, 6);
			setBlockAndMetadata(world, i1, 7, 2, GOTRegistry.brick1, 0);
			placeRandomStairs(world, random, i1, 8, 2, 3);
			placeRandomStairs(world, random, i1, 8, 1, 6);
		}
		for (k12 = -4; k12 <= 4; k12 += 8) {
			placeRandomStairs(world, random, -3, 6, k12, 1);
			placeRandomStairs(world, random, -2, 6, k12, 4);
			setBlockAndMetadata(world, -2, 7, k12, GOTRegistry.brick1, 0);
			placeRandomStairs(world, random, -2, 8, k12, 1);
			placeRandomStairs(world, random, -1, 8, k12, 4);
			placeRandomStairs(world, random, 3, 6, k12, 0);
			placeRandomStairs(world, random, 2, 6, k12, 5);
			setBlockAndMetadata(world, 2, 7, k12, GOTRegistry.brick1, 0);
			placeRandomStairs(world, random, 2, 8, k12, 0);
			placeRandomStairs(world, random, 1, 8, k12, 5);
		}
		return true;
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(4) == 0) {
			setBlockAndMetadata(world, i, j, k, GOTRegistry.brick1, 7);
		} else {
			setBlockAndMetadata(world, i, j, k, GOTRegistry.brick1, 0);
		}
	}

	public void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
		if (random.nextInt(4) == 0) {
			setBlockAndMetadata(world, i, j, k, GOTRegistry.stairsBasaltBrickCracked, meta);
		} else {
			setBlockAndMetadata(world, i, j, k, GOTRegistry.stairsBasaltBrick, meta);
		}
	}
}