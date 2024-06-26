package got.common.world.map;

import got.common.database.GOTBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public abstract class GOTBezierType {
	public static final GOTBezierType WALL_IBBEN = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes = {new BezierBlock(GOTBlocks.woodBeamV1, 1), new BezierBlock(GOTBlocks.wood4, 2), new BezierBlock(GOTBlocks.woodBeam4, 2)};
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static final GOTBezierType WALL_ICE = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes = {new BezierBlock(GOTBlocks.brickIce, 0), new BezierBlock(GOTBlocks.brickIce, 0), new BezierBlock(GOTBlocks.brickIce, 0), new BezierBlock(GOTBlocks.brickIce, 0), new BezierBlock(Blocks.packed_ice, 0), new BezierBlock(Blocks.packed_ice, 0), new BezierBlock(Blocks.snow, 0)};
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static final GOTBezierType WALL_YI_TI = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			return new BezierBlock(GOTBlocks.cobblebrick, 0);
		}
	};

	public static final GOTBezierType PATH_ASSHAI = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new BezierBlock(GOTBlocks.slabSingleDirt, 3);
			}
			return new BezierBlock(GOTBlocks.asshaiDirt, 0);
		}
	};

	public static final GOTBezierType PATH_COBBLE = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(Blocks.stone_slab, 5), new BezierBlock(Blocks.stone_slab, 3), new BezierBlock(Blocks.stone_slab, 3), new BezierBlock(GOTBlocks.slabSingleV, 4)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(Blocks.stonebrick, 0), new BezierBlock(Blocks.cobblestone, 0), new BezierBlock(Blocks.cobblestone, 0), new BezierBlock(Blocks.mossy_cobblestone, 0)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static final GOTBezierType PATH_DIRTY = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTBlocks.slabSingleDirt, 1), new BezierBlock(GOTBlocks.slabSingleDirt, 0), new BezierBlock(GOTBlocks.slabSingleGravel, 0)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTBlocks.dirtPath, 0), new BezierBlock(Blocks.dirt, 1), new BezierBlock(Blocks.gravel, 0)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static final GOTBezierType PATH_PAVING = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new BezierBlock(GOTBlocks.slabSingleDirt, 5);
			}
			return new BezierBlock(GOTBlocks.dirtPath, 2);
		}
	};

	public static final GOTBezierType PATH_SANDY = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTBlocks.slabSingleDirt, 0), new BezierBlock(GOTBlocks.slabSingleDirt, 1), new BezierBlock(GOTBlocks.slabSingleSand, 0), new BezierBlock(GOTBlocks.slabSingle4, 0), new BezierBlock(GOTBlocks.slabSingle7, 1), new BezierBlock(GOTBlocks.slabSingle4, 7)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(Blocks.dirt, 1), new BezierBlock(GOTBlocks.dirtPath, 0), new BezierBlock(top ? Blocks.sand : Blocks.sandstone, 0), new BezierBlock(GOTBlocks.brick1, 15), new BezierBlock(GOTBlocks.brick3, 11), new BezierBlock(GOTBlocks.pillar1, 5)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static final GOTBezierType PATH_SNOWY = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			return new BezierBlock(Blocks.snow, 0);
		}
	};

	public static final GOTBezierType PATH_SOTHORYOS = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTBlocks.slabSingle8, 0), new BezierBlock(GOTBlocks.slabSingle8, 1), new BezierBlock(GOTBlocks.slabSingle8, 2), new BezierBlock(GOTBlocks.slabSingle8, rand.nextBoolean() ? 1 : 2)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTBlocks.brick4, 0), new BezierBlock(GOTBlocks.brick4, 1), new BezierBlock(GOTBlocks.brick4, 2), new BezierBlock(GOTBlocks.brick4, rand.nextBoolean() ? 1 : 2)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static final GOTBezierType TOWN_ASSHAI = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTBlocks.slabSingleDirt, 3)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTBlocks.basaltGravel, 0), new BezierBlock(GOTBlocks.asshaiDirt, 0)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static final GOTBezierType TOWN_YI_TI = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTBlocks.slabSingle12, 0), new BezierBlock(GOTBlocks.slabSingle12, 0), new BezierBlock(GOTBlocks.slabSingle12, 0), new BezierBlock(GOTBlocks.slabSingle12, 1), new BezierBlock(GOTBlocks.slabSingle12, 2)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTBlocks.brick5, 11), new BezierBlock(GOTBlocks.brick5, 11), new BezierBlock(GOTBlocks.brick5, 11), new BezierBlock(GOTBlocks.brick5, 13), new BezierBlock(GOTBlocks.brick5, 14)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	@SuppressWarnings("WeakerAccess")
	protected GOTBezierType() {
	}

	public abstract BezierBlock getBlock(Random var1, BiomeGenBase var2, boolean var3, boolean var4);

	public float getRepair() {
		return 1.0f;
	}

	public boolean hasFlowers() {
		return false;
	}

	public GOTBezierType setHasFlowers(boolean flag) {
		GOTBezierType baseRoad = this;
		return new CustomBezierType(baseRoad, flag);
	}

	@SuppressWarnings("AbstractClassWithOnlyOneDirectInheritor")
	public abstract static class BridgeType {
		public static final BridgeType DEFAULT = new BridgeType() {

			@Override
			public BezierBlock getBlock(boolean slab) {
				if (slab) {
					return new BezierBlock(Blocks.wooden_slab, 0);
				}
				return new BezierBlock(Blocks.planks, 0);
			}

			@Override
			public BezierBlock getEdge() {
				return new BezierBlock(GOTBlocks.woodBeamV1, 0);
			}

			@Override
			public BezierBlock getFence() {
				return new BezierBlock(Blocks.fence, 0);
			}
		};

		abstract BezierBlock getBlock(boolean var2);

		abstract BezierBlock getEdge();

		abstract BezierBlock getFence();
	}

	public static class BezierBlock {
		private final Block block;
		private final int meta;

		@SuppressWarnings("WeakerAccess")
		public BezierBlock(Block b, int i) {
			block = b;
			meta = i;
		}

		public Block getBlock() {
			return block;
		}

		public int getMeta() {
			return meta;
		}
	}

	private static class CustomBezierType extends GOTBezierType {
		private final GOTBezierType baseRoad;
		private final boolean flag;

		private CustomBezierType(GOTBezierType baseRoad, boolean flag) {
			this.baseRoad = baseRoad;
			this.flag = flag;
		}

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			return baseRoad.getBlock(rand, biome, top, slab);
		}

		@Override
		public float getRepair() {
			return baseRoad.getRepair();
		}

		@Override
		public boolean hasFlowers() {
			return flag;
		}
	}
}