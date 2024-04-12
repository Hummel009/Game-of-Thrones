package got.common.world.structure.westeros.westerlands;

import com.google.common.math.IntMath;
import got.common.entity.westeros.westerlands.*;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.common.GOTStructureWesterosMarketStall;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTStructureWesterlandsMarketStall extends GOTStructureWesterosMarketStall {
	private static final Class<? extends GOTStructureBase>[] STALLS = new Class[]{Goldsmith.class, Miner.class, Lumber.class, Mason.class, Brewer.class, Flowers.class, Butcher.class, Fish.class, Farmer.class, Blacksmith.class, Baker.class};

	protected GOTStructureWesterlandsMarketStall(boolean flag) {
		super(flag);
		kingdom = Kingdom.WESTERLANDS;
	}

	public static GOTStructureBase getRandomStall(Random random, boolean flag) {
		try {
			Class<? extends GOTStructureBase> cls = STALLS[random.nextInt(STALLS.length)];
			return cls.getConstructor(Boolean.TYPE).newInstance(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static class Baker extends GOTStructureWesterlandsMarketStall {
		protected Baker(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsBaker(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int k2 = Math.abs(k1);
			if (k2 % 2 == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 1);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	private static class Blacksmith extends GOTStructureWesterlandsMarketStall {
		protected Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsBlacksmith(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 == Math.abs(k1)) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
			}
		}
	}

	private static class Brewer extends GOTStructureWesterlandsMarketStall {
		protected Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsBrewer(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 % 2 == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
			}
		}
	}

	private static class Butcher extends GOTStructureWesterlandsMarketStall {
		protected Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsButcher(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if (i2 == 2 || k2 == 2) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 6);
			} else if (i2 == 1 || k2 == 1) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 14);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
			}
		}
	}

	private static class Farmer extends GOTStructureWesterlandsMarketStall {
		protected Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsFarmer(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int k2;
			int i2 = Math.abs(i1);
			if (IntMath.mod(i2 + (k2 = Math.abs(k1)), 2) == 0) {
				if (Integer.signum(i1) != -Integer.signum(k1) && i2 + k2 == 2) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
				} else {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
				}
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	private static class Fish extends GOTStructureWesterlandsMarketStall {
		protected Fish(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsFishmonger(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if (i2 % 2 == 0) {
				if (k2 == 2) {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
				} else {
					setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 3);
				}
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 11);
			}
		}
	}

	private static class Flowers extends GOTStructureWesterlandsMarketStall {
		protected Flowers(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsFlorist(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 == Math.abs(k1)) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
			}
		}
	}

	private static class Goldsmith extends GOTStructureWesterlandsMarketStall {
		protected Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsGoldsmith(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 == Math.abs(k1)) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
			}
		}
	}

	private static class Lumber extends GOTStructureWesterlandsMarketStall {
		protected Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsLumberman(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if ((i2 == 2 || k2 == 2) && IntMath.mod(i2 + k2, 2) == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	private static class Mason extends GOTStructureWesterlandsMarketStall {
		protected Mason(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsMason(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if (i2 == 2 || k2 == 2 || i2 != 1 && k2 != 1) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 8);
			}
		}
	}

	private static class Miner extends GOTStructureWesterlandsMarketStall {
		protected Miner(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityWesterlandsMan createTrader(World world) {
			return new GOTEntityWesterlandsMiner(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (IntMath.mod(i2 + Math.abs(k1), 2) == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 14);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 5);
			}
		}
	}

}
