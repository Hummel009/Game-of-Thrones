package got.common.world.map;

import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public abstract class GOTBezierType {
	public static GOTBezierType WALL_IBBEN = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes = {new BezierBlock(GOTRegistry.woodBeamV1, 1), new BezierBlock(GOTRegistry.wood4, 2), new BezierBlock(GOTRegistry.woodBeam4, 2)};
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static GOTBezierType WALL_ICE = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes = {new BezierBlock(GOTRegistry.brickIce, 0), new BezierBlock(GOTRegistry.brickIce, 0), new BezierBlock(GOTRegistry.brickIce, 0), new BezierBlock(GOTRegistry.brickIce, 0), new BezierBlock(Blocks.packed_ice, 0), new BezierBlock(Blocks.packed_ice, 0), new BezierBlock(Blocks.snow, 0)};
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static GOTBezierType WALL_YITI = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			return new BezierBlock(GOTRegistry.cobblebrick, 0);
		}
	};

	public static GOTBezierType PATH_ASSHAI = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new BezierBlock(GOTRegistry.slabSingleDirt, 3);
			}
			return new BezierBlock(GOTRegistry.asshaiDirt, 0);
		}
	};

	public static GOTBezierType PATH_COBBLE = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(Blocks.stone_slab, 5), new BezierBlock(Blocks.stone_slab, 3), new BezierBlock(Blocks.stone_slab, 3), new BezierBlock(GOTRegistry.slabSingleV, 4)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(Blocks.stonebrick, 0), new BezierBlock(Blocks.cobblestone, 0), new BezierBlock(Blocks.cobblestone, 0), new BezierBlock(Blocks.mossy_cobblestone, 0)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static GOTBezierType PATH_DIRTY = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTRegistry.slabSingleDirt, 1), new BezierBlock(GOTRegistry.slabSingleDirt, 0), new BezierBlock(GOTRegistry.slabSingleGravel, 0)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTRegistry.dirtPath, 0), new BezierBlock(Blocks.dirt, 1), new BezierBlock(Blocks.gravel, 0)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static GOTBezierType PATH_PAVING = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new BezierBlock(GOTRegistry.slabSingleDirt, 5);
			}
			return new BezierBlock(GOTRegistry.dirtPath, 2);
		}
	};

	public static GOTBezierType PATH_SANDY = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTRegistry.slabSingleDirt, 0), new BezierBlock(GOTRegistry.slabSingleDirt, 1), new BezierBlock(GOTRegistry.slabSingleSand, 0), new BezierBlock(GOTRegistry.slabSingle4, 0), new BezierBlock(GOTRegistry.slabSingle7, 1), new BezierBlock(GOTRegistry.slabSingle4, 7)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(Blocks.dirt, 1), new BezierBlock(GOTRegistry.dirtPath, 0), top ? new BezierBlock(Blocks.sand, 0) : new BezierBlock(Blocks.sandstone, 0), new BezierBlock(GOTRegistry.brick1, 15), new BezierBlock(GOTRegistry.brick3, 11), new BezierBlock(GOTRegistry.pillar1, 5)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static GOTBezierType PATH_SNOWY = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			return new BezierBlock(Blocks.snow, 0);
		}
	};

	public static GOTBezierType PATH_SOTHORYOS = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTRegistry.slabSingle8, 0), new BezierBlock(GOTRegistry.slabSingle8, 1), new BezierBlock(GOTRegistry.slabSingle8, 2), new BezierBlock(GOTRegistry.slabSingle8, rand.nextBoolean() ? 1 : 2)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTRegistry.brick4, 0), new BezierBlock(GOTRegistry.brick4, 1), new BezierBlock(GOTRegistry.brick4, 2), new BezierBlock(GOTRegistry.brick4, rand.nextBoolean() ? 1 : 2)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static GOTBezierType TOWN_ASSHAI = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTRegistry.slabSingleDirt, 1)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTRegistry.basaltGravel, 0), new BezierBlock(GOTRegistry.asshaiDirt, 0)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

	public static GOTBezierType TOWN_YITI = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			BezierBlock[] blockTypes;
			if (slab) {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTRegistry.slabSingle12, 0), new BezierBlock(GOTRegistry.slabSingle12, 0), new BezierBlock(GOTRegistry.slabSingle12, 0), new BezierBlock(GOTRegistry.slabSingle12, 1), new BezierBlock(GOTRegistry.slabSingle12, 2)};
			} else {
				blockTypes = new BezierBlock[]{new BezierBlock(GOTRegistry.brick5, 11), new BezierBlock(GOTRegistry.brick5, 11), new BezierBlock(GOTRegistry.brick5, 11), new BezierBlock(GOTRegistry.brick5, 13), new BezierBlock(GOTRegistry.brick5, 14)};
			}
			return blockTypes[rand.nextInt(blockTypes.length)];
		}
	};

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
		return new GOTBezierType() {

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
		};
	}

	public static class BezierBlock {
		public Block block;
		public int meta;

		public BezierBlock(Block b, int i) {
			block = b;
			meta = i;
		}
	}

	public abstract static class BridgeType {
		public static BridgeType DEFAULT = new BridgeType() {

			@Override
			public BezierBlock getBlock(Random rand, boolean slab) {
				if (slab) {
					return new BezierBlock(Blocks.wooden_slab, 0);
				}
				return new BezierBlock(Blocks.planks, 0);
			}

			@Override
			public BezierBlock getEdge(Random rand) {
				return new BezierBlock(GOTRegistry.woodBeamV1, 0);
			}

			@Override
			public BezierBlock getFence(Random rand) {
				return new BezierBlock(Blocks.fence, 0);
			}
		};
		public static BridgeType CHARRED = new BridgeType() {

			@Override
			public BezierBlock getBlock(Random rand, boolean slab) {
				if (slab) {
					return new BezierBlock(GOTRegistry.woodSlabSingle1, 3);
				}
				return new BezierBlock(GOTRegistry.planks1, 3);
			}

			@Override
			public BezierBlock getEdge(Random rand) {
				return new BezierBlock(GOTRegistry.woodBeam1, 3);
			}

			@Override
			public BezierBlock getFence(Random rand) {
				return new BezierBlock(GOTRegistry.fence, 3);
			}
		};

		protected BridgeType() {
		}

		public abstract BezierBlock getBlock(Random var1, boolean var2);

		public abstract BezierBlock getEdge(Random var1);

		public abstract BezierBlock getFence(Random var1);

	}

}
