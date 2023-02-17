package got.common.world.structure.westeros.riverlands;

import java.util.Random;

import com.google.common.math.IntMath;

import got.common.entity.westeros.riverlands.*;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.common.GOTStructureWesterosMarketStall;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class GOTStructureRiverlandsMarketStall extends GOTStructureWesterosMarketStall {
	public static Class[] allStallTypes = { Goldsmith.class, Miner.class, Lumber.class, Mason.class, Brewer.class, Flowers.class, Butcher.class, Fish.class, Farmer.class, Blacksmith.class, Baker.class };

	public GOTStructureRiverlandsMarketStall(boolean flag) {
		super(flag);
		kingdom = Kingdom.RIVERLANDS;
	}

	public static GOTStructureBase getRandomStall(Random random, boolean flag) {
		try {
			Class cls = allStallTypes[random.nextInt(allStallTypes.length)];
			return (GOTStructureBase) cls.getConstructor(Boolean.TYPE).newInstance(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static class Baker extends GOTStructureRiverlandsMarketStall {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsBaker(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			Math.abs(i1);
			int k2 = Math.abs(k1);
			if (k2 % 2 == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 1);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	public static class Blacksmith extends GOTStructureRiverlandsMarketStall {
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsBlacksmith(world);
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

	public static class Brewer extends GOTStructureRiverlandsMarketStall {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsBrewer(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			Math.abs(k1);
			if (i2 % 2 == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
			}
		}
	}

	public static class Butcher extends GOTStructureRiverlandsMarketStall {
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsButcher(world);
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

	public static class Farmer extends GOTStructureRiverlandsMarketStall {
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsFarmer(world);
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

	public static class Fish extends GOTStructureRiverlandsMarketStall {
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsFishmonger(world);
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

	public static class Flowers extends GOTStructureRiverlandsMarketStall {
		public Flowers(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsFlorist(world);
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

	public static class Goldsmith extends GOTStructureRiverlandsMarketStall {
		public Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsGoldsmith(world);
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

	public static class Lumber extends GOTStructureRiverlandsMarketStall {
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsLumberman(world);
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

	public static class Mason extends GOTStructureRiverlandsMarketStall {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsMason(world);
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

	public static class Miner extends GOTStructureRiverlandsMarketStall {
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityRiverlandsMan createTrader(World world) {
			return new GOTEntityRiverlandsMiner(world);
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
