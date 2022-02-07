package got.common.util;

import java.util.*;

import net.minecraft.util.MathHelper;

public class GOTMazeGenerator {
	private int xSize;
	private int zSize;
	private short[][] mazeFlags;
	private int startX = -1;
	private int startZ = -1;
	private int endX = -1;
	private int endZ = -1;
	private float windyness = 0.3f;
	private float branchingness = 0.2f;

	public GOTMazeGenerator(int x, int z) {
		setxSize(x);
		setzSize(z);
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
			int randPosIndex = MathHelper.getRandomIntegerInRange(random, (int) (maxIndex * (1.0f - branchingness)), maxIndex);
			MazePos pos = positions.get(randPosIndex);
			ArrayList<Dir> validDirs = new ArrayList<>();
			block1: for (Dir dir : Dir.values()) {
				for (int l = 1; l <= 2; ++l) {
					int x = pos.xPos + dir.xDir * l;
					int z = pos.zPos + dir.zDir * l;
					if (x < 0 || x >= getxSize() || z < 0 || z >= getzSize() || isPath(x, z) || getFlag(x, z, (short) 2)) {
						continue block1;
					}
				}
				validDirs.add(dir);
			}
			if (!validDirs.isEmpty()) {
				Dir dir = lastDir != null && validDirs.contains(lastDir) && random.nextFloat() >= windyness ? lastDir : (Dir) validDirs.get(random.nextInt(validDirs.size()));
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
				continue;
			}
			positions.remove(randPosIndex);
			lastDir = null;
		}
	}

	public int[] getEnd() {
		return new int[] { endX, endZ };
	}

	private boolean getFlag(int x, int z, short flag) {
		return (mazeFlags[x][z] & flag) == flag;
	}

	public int getxSize() {
		return xSize;
	}

	public int getzSize() {
		return zSize;
	}

	public boolean isDeadEnd(int x, int z) {
		return getFlag(x, z, (short) 4);
	}

	public boolean isPath(int x, int z) {
		return getFlag(x, z, (short) 1);
	}

	public void selectOuterEndpoint(Random random) {
		int startXHalf = startX / (getxSize() / 2);
		int startZHalf = startZ / (getzSize() / 2);
		int wx = 0;
		int wz = 0;
		do {
			ArrayList<MazePos> positions = new ArrayList<>();
			for (int x = 0; x < getxSize(); ++x) {
				for (int z = 0; z < getzSize(); ++z) {
					boolean outer;
					outer = x == 0 + wx || x == getxSize() - 1 - wx || z == 0 + wz || z == getzSize() - 1 - wz;
					if (!outer || !isPath(x, z)) {
						continue;
					}
					int xHalf = x / (getxSize() / 2);
					int zHalf = z / (getzSize() / 2);
					if (startXHalf == xHalf || startZHalf == zHalf) {
						continue;
					}
					positions.add(new MazePos(x, z));
				}
			}
			if (positions.isEmpty()) {
				continue;
			}
			MazePos pos = positions.get(random.nextInt(positions.size()));
			endX = pos.xPos;
			endZ = pos.zPos;
			return;
		} while (++wx <= getxSize() / 2 + 1 && ++wz <= getzSize() / 2 + 1);
	}

	private void setFlag(int x, int z, short flag, boolean val) {
		if (val) {
			short[] arrs = mazeFlags[x];
			int n = z;
			arrs[n] = (short) (arrs[n] | flag);
		} else {
			short[] arrs = mazeFlags[x];
			int n = z;
			arrs[n] = (short) (arrs[n] & ~flag);
		}
	}

	public void setStart(int x, int z) {
		startX = x;
		startZ = z;
	}

	private void setupMaze() {
		mazeFlags = new short[getxSize()][getzSize()];
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public void setzSize(int zSize) {
		this.zSize = zSize;
	}

	private enum Dir {
		XNEG(-1, 0), XPOS(1, 0), ZNEG(0, -1), ZPOS(0, 1);

		private int xDir;
		private int zDir;

		Dir(int x, int z) {
			xDir = x;
			zDir = z;
		}
	}

	private static class MazePos {
		private int xPos;
		private int zPos;

		private MazePos(int x, int z) {
			xPos = x;
			zPos = z;
		}
	}

}
