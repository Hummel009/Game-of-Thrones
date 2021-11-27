package got.common.world.structure.westeros.dorne;

import java.util.Random;

import com.google.common.math.IntMath;

import net.minecraft.world.World;

public class GOTStructureDorneTownWall extends GOTStructureDorneBase {
	public int xMin;

	public int xMax;

	public int xMinInner;

	public int xMaxInner;

	public GOTStructureDorneTownWall(boolean flag, int x0, int x1) {
		this(flag, x0, x1, x0, x1);
	}

	public GOTStructureDorneTownWall(boolean flag, int x0, int x1, int xi0, int xi1) {
		super(flag);
		xMin = x0;
		xMax = x1;
		xMinInner = xi0;
		xMaxInner = xi1;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		for (int i1 = xMin; i1 <= xMax; i1++) {
			Math.abs(i1);
			int k1 = 0;
			findSurface(world, i1, k1);
			int j1;
			for (j1 = 1; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; j1--) {
				setBlockAndMetadata(world, i1, j1, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
				setGrassToDirt(world, i1, j1 - 1, k1);
			}
			for (j1 = 2; j1 <= 3; j1++) {
				setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, i1, 4, k1, brick2Block, brick2Meta);
			int i3 = IntMath.mod(i1, 4);
			if (i3 == 2) {
				setBlockAndMetadata(world, i1, 5, k1, rockWallBlock, rockWallMeta);
			} else {
				setBlockAndMetadata(world, i1, 5, k1, brickBlock, brickMeta);
				switch (i3) {
				case 3:
					setBlockAndMetadata(world, i1, 6, k1, brickStairBlock, 1);
					break;
				case 0:
					setBlockAndMetadata(world, i1, 6, k1, brickBlock, brickMeta);
					break;
				case 1:
					setBlockAndMetadata(world, i1, 6, k1, brickStairBlock, 0);
					break;
				default:
					break;
				}
			}
			if (i1 >= xMinInner && i1 <= xMaxInner) {
				for (k1 = 1; k1 <= 1; k1++) {
					for (int m = 4; (m >= 0 || !isOpaque(world, i1, m, k1)) && getY(m) >= 0; m--) {
						setBlockAndMetadata(world, i1, m, k1, brickBlock, brickMeta);
						setGrassToDirt(world, i1, m - 1, k1);
					}
				}
			}
		}
		return true;
	}

	public static GOTStructureDorneTownWall Centre(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -5, 5);
	}

	public static GOTStructureDorneTownWall Left(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -9, 6);
	}

	public static GOTStructureDorneTownWall LeftEnd(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -6, 6, -5, 6);
	}

	public static GOTStructureDorneTownWall LeftEndShort(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -5, 6);
	}

	public static GOTStructureDorneTownWall Right(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -6, 9);
	}

	public static GOTStructureDorneTownWall RightEnd(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -6, 6, -6, 5);
	}

	public static GOTStructureDorneTownWall RightEndShort(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -6, 5);
	}
}
