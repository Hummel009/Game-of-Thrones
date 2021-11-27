package got.common.world.structure.westeros.crownlands.red;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class GOTStructureRedFortWall extends GOTStructureRedBase {
	public boolean isRight;

	public GOTStructureRedFortWall(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		this.setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		int xMin = -8;
		int xMax = 6;
		if (isRight) {
			xMin = -6;
			xMax = 8;
		}
		for (int i1 = xMin; i1 <= xMax; ++i1) {
			int j1;
			boolean pillar;
			int i2 = Math.abs(i1);
			int k1 = 0;
			findSurface(world, i1, k1);
			pillar = i2 % 3 == 0;
			if (pillar) {
				for (j1 = 4; (j1 >= 1 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, pillar2Block, pillar2Meta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
			} else {
				for (j1 = 3; (j1 >= 1 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				setBlockAndMetadata(world, i1, 4, k1, brickStairBlock, 6);
			}
			setBlockAndMetadata(world, i1, 5, k1, brick2WallBlock, brick2WallMeta);
			if (pillar) {
				setBlockAndMetadata(world, i1, 6, k1, Blocks.torch, 5);
			}
			setBlockAndMetadata(world, i1, 4, 1, rockSlabBlock, rockSlabMeta | 8);
		}
		return true;
	}

	public static class Left extends GOTStructureRedFortWall {
		public Left(boolean flag) {
			super(flag);
			isRight = false;
		}
	}

	public static class Right extends GOTStructureRedFortWall {
		public Right(boolean flag) {
			super(flag);
			isRight = true;
		}
	}

}