package got.common.world.map;

import got.common.database.GOTBlocks;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTFiveFortsWall extends GOTStructureBase {
	private final int centreX;
	private final int centreZ;

	public GOTFiveFortsWall(boolean flag, GOTAbstractWaypoint waypoint) {
		super(flag);
		centreX = waypoint.getCoordX();
		centreZ = waypoint.getCoordZ();
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		double wallThick = 0.5;
		if (isPosInWall(i + 8, k + 8) < wallThick * 3.0) {
			for (int i1 = i; i1 <= i + 15; ++i1) {
				block1:
				for (int k1 = k; k1 <= k + 15; ++k1) {
					double circleDist = isPosInWall(i1, k1);
					if (circleDist >= 0.08) {
						continue;
					}
					float roadNear = GOTBeziers.isBezierNear(i1, k1, 9, GOTBeziers.Type.ROAD);
					boolean gate = roadNear >= 0.0f;
					boolean fences = false;
					boolean wallEdge = circleDist > 0.025;
					int wallTop = 100;
					for (int j1 = wallTop; j1 > 0; --j1) {
						if (fences) {
							setBlockAndMetadata(world, i1, j1, k1, GOTBlocks.fence, 3);
						} else {
							setBlockAndMetadata(world, i1, j1, k1, GOTBlocks.brick1, 0);
							if (wallEdge && j1 == wallTop) {
								setBlockAndMetadata(world, i1, wallTop + 1, k1, GOTBlocks.wallStone1, 1);
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
						int gateBottom = 77;
						if (fences) {
							if (j1 != gateBottom) {
								continue;
							}
							continue block1;
						}
						int gateTop = wallTop - 18;
						int lerpGateTop = gateBottom + Math.round((gateTop - gateBottom) * MathHelper.sqrt_float(1.0f - roadNear));
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

	private double isPosInWall(int i, int k) {
		int dx = i - centreX;
		int dz = k - centreZ;
		int distSq = dx * dx + dz * dz;
		int radius = 100;
		int radiusSq = radius * radius;
		return Math.abs(distSq / (1.5 * radiusSq) - 1.0);
	}

	@Override
	public int rotateMeta(Block block, int meta) {
		return meta;
	}
}