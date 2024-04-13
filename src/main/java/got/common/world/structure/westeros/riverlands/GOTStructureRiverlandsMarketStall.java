package got.common.world.structure.westeros.riverlands;

import com.google.common.math.IntMath;
import got.common.entity.westeros.riverlands.*;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.common.GOTStructureWesterosMarketStall;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTStructureRiverlandsMarketStall extends GOTStructureWesterosMarketStall {
	private static final Class<? extends GOTStructureBase>[] STALLS = new Class[]{Goldsmith.class, Miner.class, Lumber.class, Mason.class, Brewer.class, Flowers.class, Butcher.class, Fish.class, Farmer.class, Blacksmith.class, Baker.class};

	protected GOTStructureRiverlandsMarketStall(boolean flag) {
		super(flag);
		kingdom = Kingdom.RIVERLANDS;
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

	@SuppressWarnings("WeakerAccess")
	public static class Baker extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsBaker(world);
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

	@SuppressWarnings("WeakerAccess")
	public static class Blacksmith extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsBlacksmith(world);
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

	@SuppressWarnings("WeakerAccess")
	public static class Brewer extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsBrewer(world);
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

	@SuppressWarnings("WeakerAccess")
	public static class Butcher extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsButcher(world);
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

	@SuppressWarnings("WeakerAccess")
	public static class Farmer extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsFarmer(world);
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

	@SuppressWarnings("WeakerAccess")
	public static class Fish extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsFishmonger(world);
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

	@SuppressWarnings("WeakerAccess")
	public static class Flowers extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Flowers(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsFlorist(world);
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

	@SuppressWarnings("WeakerAccess")
	public static class Goldsmith extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsGoldsmith(world);
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

	@SuppressWarnings("WeakerAccess")
	public static class Lumber extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsLumberman(world);
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

	@SuppressWarnings("WeakerAccess")
	public static class Mason extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsMason(world);
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

	@SuppressWarnings("WeakerAccess")
	public static class Miner extends GOTStructureRiverlandsMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsMiner(world);
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