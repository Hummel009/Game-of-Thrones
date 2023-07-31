package got.common.world.structure.westeros.stormlands;

import com.google.common.math.IntMath;
import got.common.entity.westeros.stormlands.*;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.common.GOTStructureWesterosMarketStall;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTStructureStormlandsMarketStall extends GOTStructureWesterosMarketStall {
	public static Class<? extends GOTStructureBase>[] allStallTypes = new Class[]{Goldsmith.class, Miner.class, Lumber.class, Mason.class, Brewer.class, Flowers.class, Butcher.class, Fish.class, Farmer.class, Blacksmith.class, Baker.class};

	protected GOTStructureStormlandsMarketStall(boolean flag) {
		super(flag);
		kingdom = Kingdom.STORMLANDS;
	}

	public static GOTStructureBase getRandomStall(Random random, boolean flag) {
		try {
			Class<? extends GOTStructureBase> cls = allStallTypes[random.nextInt(allStallTypes.length)];
			return cls.getConstructor(Boolean.TYPE).newInstance(flag);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static class Baker extends GOTStructureStormlandsMarketStall {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsBaker(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int k2 = Math.abs(k1);
			if (k2 % 2 == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 1);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	public static class Blacksmith extends GOTStructureStormlandsMarketStall {
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsBlacksmith(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 == Math.abs(k1)) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
			}
		}
	}

	public static class Brewer extends GOTStructureStormlandsMarketStall {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsBrewer(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 % 2 == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
			}
		}
	}

	public static class Butcher extends GOTStructureStormlandsMarketStall {
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsButcher(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
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

	public static class Farmer extends GOTStructureStormlandsMarketStall {
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsFarmer(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
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

	public static class Fish extends GOTStructureStormlandsMarketStall {
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsFishmonger(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
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

	public static class Flowers extends GOTStructureStormlandsMarketStall {
		public Flowers(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsFlorist(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 == Math.abs(k1)) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
			}
		}
	}

	public static class Goldsmith extends GOTStructureStormlandsMarketStall {
		public Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsGoldsmith(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 == Math.abs(k1)) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
			}
		}
	}

	public static class Lumber extends GOTStructureStormlandsMarketStall {
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsLumberman(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if ((i2 == 2 || k2 == 2) && IntMath.mod(i2 + k2, 2) == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 13);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	public static class Mason extends GOTStructureStormlandsMarketStall {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsMason(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if (i2 == 2 || k2 == 2 || i2 != 1 && k2 != 1) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 8);
			}
		}
	}

	public static class Miner extends GOTStructureStormlandsMarketStall {
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityStormlandsMan createTrader(World world) {
			return new GOTEntityStormlandsMiner(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (IntMath.mod(i2 + Math.abs(k1), 2) == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 14);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 5);
			}
		}
	}

}
