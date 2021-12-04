package got.common.world.fixed;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.world.map.*;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTStructureFiveFortsWall extends GOTStructureBase {
	public int centreX;
	public int centreZ;
	public int radius = 100;
	public int radiusSq = radius * radius;
	public double wallThick = 0.5;
	public int wallTop = 100;
	public int gateBottom = 77;
	public int gateTop = wallTop - 18;

	public GOTStructureFiveFortsWall(boolean flag, GOTWaypoint waypoint) {
		super(flag);
		centreX = waypoint.getXCoord();
		centreZ = waypoint.getZCoord();
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		if (isPosInWall(i + 8, k + 8) < wallThick * 3.0) {
			for (int i1 = i; i1 <= i + 15; ++i1) {
				block1: for (int k1 = k; k1 <= k + 15; ++k1) {
					double circleDist = isPosInWall(i1, k1);
					if (circleDist >= 0.08) {
						continue;
					}
					float bezierNear = GOTBezier.isBezierNear(i1, k1, 9);
					boolean gate = bezierNear >= 0.0f;
					boolean fences = false;
					boolean wallEdge = circleDist > 0.025;
					for (int j1 = wallTop; j1 > 0; --j1) {
						if (fences) {
							setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.fence, 3);
						} else {
							setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.brick2, 11);
							if (wallEdge && j1 == wallTop) {
								setBlockAndMetadata(world, i1, j1 + 1, k1, GOTRegistry.wallStone2, 10);
							}
						}
						Block below = getBlock(world, i1, j1 - 1, k1);
						setGrassToDirt(world, i1, j1 - 1, k1);
						if (below == Blocks.grass || below == Blocks.dirt || below == Blocks.stone) {
							continue block1;
						}
						if (!gate) {
							continue;
						}
						if (fences) {
							if (j1 != gateBottom) {
								continue;
							}
							continue block1;
						}
						int lerpGateTop = gateBottom + Math.round((gateTop - gateBottom) * MathHelper.sqrt_float(1.0f - bezierNear));
						if (j1 != lerpGateTop) {
							continue;
						}
						if (circleDist <= 0.025) {
							continue block1;
						}
						fences = true;
					}
				}
			}
		}
		return true;
	}

	@Override
	public int getX(int x, int z) {
		return x;
	}

	@Override
	public int getY(int y) {
		return y;
	}

	@Override
	public int getZ(int x, int z) {
		return z;
	}

	public double isPosInWall(int i, int k) {
		int dx = i - centreX;
		int dz = k - centreZ;
		int distSq = dx * dx + dz * dz;
		return Math.abs(distSq / (1.5 * radiusSq) - 1.0);
	}

	@Override
	public int rotateMeta(Block block, int meta) {
		return meta;
	}
}