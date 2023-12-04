package got.common.util;

import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Random;

public class GOTMazeGenerator {
	public static short FLAG_PATH = 1;
	public static short FLAG_EXCLUDE = 2;
	public static short FLAG_DEADEND = 4;
	public int xSize;
	public int zSize;
	public short[][] mazeFlags;
	public int startX = -1;
	public int startZ = -1;
	public int endX = -1;
	public int endZ = -1;
	public float windyness = 0.3f;
	public float branchingness = 0.2f;

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
		clear(startX, startZ);
		ArrayList<MazePos> positions = new ArrayList<>();
		positions.add(new MazePos(startX, startZ));
		Dir lastDir = null;
		while (!positions.isEmpty()) {
			int maxIndex = positions.size() - 1;
			int randPosIndex = MathHelper.getRandomIntegerInRange(random, (int) (maxIndex * (1.0f - branchingness)), maxIndex);
			MazePos pos = positions.get(randPosIndex);
			ArrayList<Dir> validDirs = new ArrayList<>();
			block1:
			for (Dir dir : Dir.values()) {
				for (int l = 1; l <= 2; ++l) {
					int x = pos.xPos + dir.xDir * l;
					int z = pos.zPos + dir.zDir * l;
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
				if (lastDir != null && validDirs.contains(lastDir) && random.nextFloat() >= windyness) {
					dir = lastDir;
				} else {
					dir = validDirs.get(random.nextInt(validDirs.size()));
				}
				int x = pos.xPos;
				int z = pos.zPos;
				if (getFlag(x, z, (short) 4)) {
					setFlag(x, z, (short) 4, false);
				}
				for (int l = 0; l < 2; ++l) {
					clear(x += dir.xDir, z += dir.zDir);
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

	public boolean getFlag(int x, int z, short flag) {
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
			endX = pos.xPos;
			endZ = pos.zPos;
			return;
		} while (++wx <= xSize / 2 + 1 && ++wz <= zSize / 2 + 1);
	}

	public void setFlag(int x, int z, short flag, boolean val) {
		if (val) {
			short[] arrs = mazeFlags[x];
			arrs[z] |= flag;
		} else {
			short[] arrs = mazeFlags[x];
			arrs[z] &= (short) ~flag;
		}
	}

	public void setStart(int x, int z) {
		startX = x;
		startZ = z;
	}

	public void setupMaze() {
		mazeFlags = new short[xSize][zSize];
	}

	public enum Dir {
		XNEG(-1, 0), XPOS(1, 0), ZNEG(0, -1), ZPOS(0, 1);

		public int xDir;
		public int zDir;

		Dir(int x, int z) {
			xDir = x;
			zDir = z;
		}
	}

	public static class MazePos {
		public int xPos;
		public int zPos;

		public MazePos(int x, int z) {
			xPos = x;
			zPos = z;
		}
	}

}
