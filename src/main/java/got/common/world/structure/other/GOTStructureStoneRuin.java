package got.common.world.structure.other;

import java.util.Random;

import got.common.database.GOTRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class GOTStructureStoneRuin extends GOTStructureBase {
	public int minWidth;
	public int maxWidth;

	public GOTStructureStoneRuin(int i, int j) {
		super(false);
		minWidth = i;
		maxWidth = j;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		boolean generateColumn;
		this.setOriginAndRotation(world, i, j, k, rotation, 0);
		int width = MathHelper.getRandomIntegerInRange(random, minWidth, maxWidth);
		generateColumn = random.nextInt(3) > 0;
		if (generateColumn) {
			int minHeight = 0;
			int maxHeight = 0;
			int columnX = 0 - width / 2;
			int columnZ = 0 - width / 2;
			if (restrictions) {
				block0: for (int i1 = columnX; i1 < columnX + width; ++i1) {
					for (int k1 = columnZ; k1 < columnZ + width; ++k1) {
						int j1 = getTopBlock(world, i1, k1);
						if (j1 < minHeight) {
							minHeight = j1;
						}
						if (j1 > maxHeight) {
							maxHeight = j1;
						}
						if (maxHeight - minHeight > 8) {
							generateColumn = false;
							break block0;
						}
						if (isSurface(world, i1, j1 - 1, k1)) {
							continue;
						}
						generateColumn = false;
						break block0;
					}
				}
			}
			if (generateColumn) {
				int baseHeight = 4 + random.nextInt(4) + random.nextInt(width * 3);
				for (int i1 = columnX; i1 < columnX + width; ++i1) {
					for (int k1 = columnZ; k1 < columnZ + width; ++k1) {
						for (int j1 = (int) (baseHeight * (1.0f + random.nextFloat())); j1 >= minHeight; --j1) {
							placeRandomBrick(world, random, i1, j1, k1);
							setGrassToDirt(world, i1, j1 - 1, k1);
						}
					}
				}
			}
		}
		int radius = width * 2;
		int ruinParts = 2 + random.nextInt(4) + random.nextInt(width * 3);
		for (int l = 0; l < ruinParts; ++l) {
			int i1 = MathHelper.getRandomIntegerInRange(random, -radius * 2, radius * 2);
			int k1 = MathHelper.getRandomIntegerInRange(random, -radius * 2, radius * 2);
			int j1 = getTopBlock(world, i1, k1);
			if (restrictions && !isSurface(world, i1, j1 - 1, k1)) {
				continue;
			}
			int randomFeature = random.nextInt(4);
			boolean flag = true;
			if (randomFeature == 0) {
				if (!isOpaque(world, i1, j1, k1)) {
					placeRandomSlab(world, random, i1, j1, k1);
				}
			} else {
				int j2;
				for (j2 = j1; j2 < j1 + randomFeature && flag; ++j2) {
					flag = !isOpaque(world, i1, j2, k1);
				}
				if (flag) {
					for (j2 = j1; j2 < j1 + randomFeature; ++j2) {
						placeRandomBrick(world, random, i1, j2, k1);
					}
				}
			}
			if (!flag) {
				continue;
			}
			setGrassToDirt(world, i1, j1 - 1, k1);
		}
		return true;
	}

	protected abstract void placeRandomBrick(World var1, Random var2, int var3, int var4, int var5);

	protected abstract void placeRandomSlab(World var1, Random var2, int var3, int var4, int var5);

	public static class ASSHAI extends GOTStructureStoneRuin {
		public ASSHAI(int i, int j) {
			super(i, j);
		}

		@Override
		protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
			int l = random.nextInt(2);
			switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.brick1, 0);
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.brick1, 7);
			}
			}
		}

		@Override
		protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
			int l = random.nextInt(2);
			switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle1, 1);
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle2, 2);
			}
			}
		}
	}

	public static class SANDSTONE extends GOTStructureStoneRuin {
		public SANDSTONE(int i, int j) {
			super(i, j);
		}

		@Override
		protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
			int l = random.nextInt(2);
			switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.brick1, 15);
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.brick3, 11);
			}
			}
		}

		@Override
		protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
			int l = random.nextInt(2);
			switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle4, 0);
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle7, 1);
			}
			}
		}
	}

	public static class SOTHORYOS extends GOTStructureStoneRuin {
		public SOTHORYOS(int i, int j) {
			super(i, j);
		}

		@Override
		protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
			int l = random.nextInt(3);
			switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.brick4, 0);
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.brick4, 1);
				break;
			}
			case 2: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.brick4, 2);
			}
			}
		}

		@Override
		protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
			if (random.nextInt(4) == 0) {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle8, 5);
			} else {
				int l = random.nextInt(3);
				switch (l) {
				case 0: {
					setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle8, 0);
					break;
				}
				case 1: {
					setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle8, 1);
					break;
				}
				case 2: {
					setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle8, 2);
				}
				}
			}
		}
	}

	public static class STONE extends GOTStructureStoneRuin {
		public STONE(int i, int j) {
			super(i, j);
		}

		@Override
		protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
			int l = random.nextInt(3);
			switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 1);
				break;
			}
			case 2: {
				setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 2);
			}
			}
		}

		@Override
		protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
			if (random.nextInt(4) == 0) {
				setBlockAndMetadata(world, i, j, k, Blocks.stone_slab, 0);
			} else {
				int l = random.nextInt(3);
				switch (l) {
				case 0: {
					setBlockAndMetadata(world, i, j, k, Blocks.stone_slab, 5);
					break;
				}
				case 1: {
					setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingleV, 0);
					break;
				}
				case 2: {
					setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingleV, 1);
				}
				}
			}
		}
	}

	public static class YITI extends GOTStructureStoneRuin {
		public YITI(int i, int j) {
			super(i, j);
		}

		@Override
		protected void placeRandomBrick(World world, Random random, int i, int j, int k) {
			int l = random.nextInt(3);
			switch (l) {
			case 0: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.brick5, 11);
				break;
			}
			case 1: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.brick5, 13);
				break;
			}
			case 2: {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.brick5, 14);
			}
			}
		}

		@Override
		protected void placeRandomSlab(World world, Random random, int i, int j, int k) {
			if (random.nextInt(4) == 0) {
				setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle12, 4);
			} else {
				int l = random.nextInt(3);
				switch (l) {
				case 0: {
					setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle12, 0);
					break;
				}
				case 1: {
					setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle12, 1);
					break;
				}
				case 2: {
					setBlockAndMetadata(world, i, j, k, GOTRegistry.slabSingle12, 2);
				}
				}
			}
		}
	}

}
