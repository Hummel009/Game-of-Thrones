package got.common.world.map;

import java.util.Random;

import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class GOTBezierType {
	public static GOTBezierType WOOD = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			return new BezierBlock(Blocks.planks, 1);
		}
	};
	public static GOTBezierType COBBLEBRICK = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			return new BezierBlock(GOTRegistry.cobblebrick, 0);
		}
	};

	public static GOTBezierType ICE = new GOTBezierType() {
		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			return new BezierBlock(GOTRegistry.brickIce, 0);
		}
	};

	public static GOTBezierType PAVING = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new BezierBlock(GOTRegistry.slabSingleDirt, 5);
			}
			return new BezierBlock(GOTRegistry.dirtPath, 2);
		}
	};

	public static GOTBezierType PATH_DIRTY = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				float f = rand.nextFloat();
				if (f < 0.5f) {
					return new BezierBlock(GOTRegistry.slabSingleDirt, 1);
				}
				if (f < 0.8f) {
					return new BezierBlock(GOTRegistry.slabSingleDirt, 0);
				}
				return new BezierBlock(GOTRegistry.slabSingleGravel, 0);
			}
			if (top) {
				float f = rand.nextFloat();
				if (f < 0.5f) {
					return new BezierBlock(GOTRegistry.dirtPath, 0);
				}
				if (f < 0.8f) {
					return new BezierBlock(Blocks.dirt, 1);
				}
				return new BezierBlock(Blocks.gravel, 0);
			}
			return new BezierBlock(GOTRegistry.dirtPath, 0);
		}
	};

	public static GOTBezierType VALYRIA = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new BezierBlock(Blocks.stone_slab, 3);
			}
			return new BezierBlock(Blocks.cobblestone, 0);
		}
	};

	public static GOTBezierType PATH_SANDY = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				float f = rand.nextFloat();
				if (f < 0.17f) {
					return new BezierBlock(GOTRegistry.slabSingleDirt, 0);
				}
				if (f < 0.33f) {
					return new BezierBlock(GOTRegistry.slabSingleDirt, 1);
				}
				if (f < 0.5f) {
					return new BezierBlock(GOTRegistry.slabSingleSand, 0);
				}
				if (f < 0.67f) {
					return new BezierBlock(GOTRegistry.slabSingle4, 0);
				}
				if (f < 0.83f) {
					return new BezierBlock(GOTRegistry.slabSingle7, 1);
				}
				return new BezierBlock(GOTRegistry.slabSingle4, 7);
			}
			float f = rand.nextFloat();
			if (f < 0.17f) {
				return new BezierBlock(Blocks.dirt, 1);
			}
			if (f < 0.33f) {
				return new BezierBlock(GOTRegistry.dirtPath, 0);
			}
			if (f < 0.5f) {
				return top ? new BezierBlock(Blocks.sand, 0) : new BezierBlock(Blocks.sandstone, 0);
			}
			if (f < 0.67f) {
				return new BezierBlock(GOTRegistry.brick1, 15);
			}
			if (f < 0.83f) {
				return new BezierBlock(GOTRegistry.brick3, 11);
			}
			return new BezierBlock(GOTRegistry.pillar1, 5);
		}
	};

	public static GOTBezierType SOTHORYOS = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				if (rand.nextInt(4) == 0) {
					if (rand.nextBoolean()) {
						return new BezierBlock(GOTRegistry.slabSingle8, 1);
					}
					return new BezierBlock(GOTRegistry.slabSingle8, 2);
				}
				return new BezierBlock(GOTRegistry.slabSingle8, 0);
			}
			if (rand.nextInt(4) == 0) {
				if (rand.nextBoolean()) {
					return new BezierBlock(GOTRegistry.brick4, 1);
				}
				return new BezierBlock(GOTRegistry.brick4, 2);
			}
			return new BezierBlock(GOTRegistry.brick4, 0);
		}
	};

	public static GOTBezierType ASSHAI = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				return new BezierBlock(GOTRegistry.slabSingleDirt, 3);
			}
			return new BezierBlock(GOTRegistry.asshaiDirt, 0);
		}
	};

	public static GOTBezierType YITI = new GOTBezierType() {

		@Override
		public BezierBlock getBlock(Random rand, BiomeGenBase biome, boolean top, boolean slab) {
			if (slab) {
				if (rand.nextInt(8) == 0) {
					return rand.nextBoolean() ? new BezierBlock(GOTRegistry.slabSingle12, 1) : new BezierBlock(GOTRegistry.slabSingle12, 2);
				}
				return new BezierBlock(GOTRegistry.slabSingle12, 0);
			}
			if (rand.nextInt(8) == 0) {
				return rand.nextBoolean() ? new BezierBlock(GOTRegistry.brick5, 13) : new BezierBlock(GOTRegistry.brick5, 14);
			}
			return new BezierBlock(GOTRegistry.brick5, 11);
		}
	};

	public GOTBezierType() {
	}

	public abstract BezierBlock getBlock(Random var1, BiomeGenBase var2, boolean var3, boolean var4);

	public float getRepair() {
		return 1.0f;
	}

	public boolean hasFlowers() {
		return false;
	}

	public static class BezierBlock {
		public Block block;
		public int meta;

		public BezierBlock(Block b, int i) {
			block = b;
			meta = i;
		}
	}

	public static abstract class BridgeType {
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

		public BridgeType() {
		}

		public abstract BezierBlock getBlock(Random var1, boolean var2);

		public abstract BezierBlock getEdge(Random var1);

		public abstract BezierBlock getFence(Random var1);

	}

}
