package got.common.world.structure.westeros.ironborn;

import com.google.common.math.IntMath;
import got.common.entity.westeros.ironborn.*;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.common.GOTStructureWesterosMarketStall;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTStructureIronbornMarketStall extends GOTStructureWesterosMarketStall {
	private static final Class<? extends GOTStructureBase>[] STALLS = new Class[]{Goldsmith.class, Miner.class, Lumber.class, Mason.class, Brewer.class, Flowers.class, Butcher.class, Fish.class, Farmer.class, Blacksmith.class, Baker.class};

	protected GOTStructureIronbornMarketStall(boolean flag) {
		super(flag);
		kingdom = Kingdom.IRONBORN;
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Baker extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornBaker(world);
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Blacksmith extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornBlacksmith(world);
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Brewer extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornBrewer(world);
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Butcher extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornButcher(world);
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Farmer extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornFarmer(world);
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Fish extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornFishmonger(world);
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Flowers extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Flowers(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornFlorist(world);
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Goldsmith extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornGoldsmith(world);
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Lumber extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornLumberman(world);
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Mason extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornMason(world);
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

	@SuppressWarnings({"WeakerAccess", "unused"})
	public static class Miner extends GOTStructureIronbornMarketStall {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityIronbornMan createTrader(World world) {
			return new GOTEntityIronbornMiner(world);
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