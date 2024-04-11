package got.common.util;

import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Random;

public class GOTMazeGenerator {
	private final int xSize;
	private final int zSize;

	private short[][] mazeFlags;

	private int startX = -1;
	private int startZ = -1;
	private int endX = -1;
	private int endZ = -1;

	public GOTMazeGenerator(int x, int z) {
		xSize = x;
		zSize = z;
		setupMaze();
	}

	public void clear(int x, int z) {
		setFlag(x, z, (short) 1, true);
	}

	public void exclude(int x, int z) {
		setFlag(x, z, (short) 2, true);
	}

	public void generate(Random random) {
		ArrayList<MazePos> positions = new ArrayList<>();
		Dir lastDir = null;
		clear(startX, startZ);
		positions.add(new MazePos(startX, startZ));
		while (!positions.isEmpty()) {
			int maxIndex = positions.size() - 1;
			float branchingness = 0.2f;
			int randPosIndex = MathHelper.getRandomIntegerInRange(random, (int) (maxIndex * (1.0f - branchingness)), maxIndex);
			MazePos pos = positions.get(randPosIndex);
			ArrayList<Dir> validDirs = new ArrayList<>();
			block1:
			for (Dir dir : Dir.values()) {
				for (int l = 1; l <= 2; ++l) {
					int x = pos.getPosX() + dir.getDirX() * l;
					int z = pos.getPosZ() + dir.getDirZ() * l;
					if (x < 0 || x >= xSize || z < 0 || z >= zSize || isPath(x, z) || getFlag(x, z, (short) 2)) {
						continue block1;
					}
				}
				validDirs.add(dir);
			}
			if (validDirs.isEmpty()) {
				positions.remove(randPosIndex);
				lastDir = null;
			} else {
				Dir dir;
				float windyness = 0.3f;
				if (lastDir != null && validDirs.contains(lastDir) && random.nextFloat() >= windyness) {
					dir = lastDir;
				} else {
					dir = validDirs.get(random.nextInt(validDirs.size()));
				}
				int x = pos.getPosX();
				int z = pos.getPosZ();
				if (getFlag(x, z, (short) 4)) {
					setFlag(x, z, (short) 4, false);
				}
				for (int l = 0; l < 2; ++l) {
					clear(x += dir.getDirX(), z += dir.getDirZ());
				}
				if (!getFlag(x, z, (short) 4)) {
					setFlag(x, z, (short) 4, true);
				}
				positions.add(new MazePos(x, z));
				lastDir = dir;
			}
		}
	}

	public int[] getEnd() {
		return new int[]{endX, endZ};
	}

	private boolean getFlag(int x, int z, short flag) {
		return (mazeFlags[x][z] & flag) == flag;
	}

	public boolean isDeadEnd(int x, int z) {
		return getFlag(x, z, (short) 4);
	}

	public boolean isPath(int x, int z) {
		return getFlag(x, z, (short) 1);
	}

	public void selectOuterEndpoint(Random random) {
		int startXHalf = startX / (xSize / 2);
		int startZHalf = startZ / (zSize / 2);
		int wx = 0;
		int wz = 0;
		do {
			ArrayList<MazePos> positions = new ArrayList<>();
			for (int x = 0; x < xSize; ++x) {
				for (int z = 0; z < zSize; ++z) {
					boolean outer = x == wx || x == xSize - 1 - wx || z == wz || z == zSize - 1 - wz;
					if (outer && isPath(x, z)) {
						int xHalf = x / (xSize / 2);
						int zHalf = z / (zSize / 2);
						if (startXHalf != xHalf && startZHalf != zHalf) {
							positions.add(new MazePos(x, z));
						}
					}
				}
			}
			if (positions.isEmpty()) {
				continue;
			}
			MazePos pos = positions.get(random.nextInt(positions.size()));
			endX = pos.getPosX();
			endZ = pos.getPosZ();
			return;
		} while (++wx <= xSize / 2 + 1 && ++wz <= zSize / 2 + 1);
	}

	private void setFlag(int x, int z, short flag, boolean val) {
		short[] arrs = mazeFlags[x];
		if (val) {
			arrs[z] = (short) (arrs[z] | flag);
		} else {
			arrs[z] = (short) (arrs[z] & ~flag);
		}
	}

	public void setStart(int x, int z) {
		startX = x;
		startZ = z;
	}

	private void setupMaze() {
		mazeFlags = new short[xSize][zSize];
	}

	public int getSizeZ() {
		return zSize;
	}

	public int getSizeX() {
		return xSize;
	}

	public enum Dir {
		XNEG(-1, 0), XPOS(1, 0), ZNEG(0, -1), ZPOS(0, 1);

		private final int xDir;
		private final int zDir;

		Dir(int x, int z) {
			xDir = x;
			zDir = z;
		}

		private int getDirX() {
			return xDir;
		}

		private int getDirZ() {
			return zDir;
		}
	}

	private static class MazePos {
		private final int xPos;
		private final int zPos;

		private MazePos(int x, int z) {
			xPos = x;
			zPos = z;
		}

		private int getPosX() {
			return xPos;
		}

		private int getPosZ() {
			return zPos;
		}
	}
}