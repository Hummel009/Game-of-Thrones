package got.common.world.structure.westeros.crownlands;

import com.google.common.math.IntMath;
import got.common.entity.westeros.crownlands.*;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.common.GOTStructureWesterosMarketStall;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTStructureCrownlandsMarketStall extends GOTStructureWesterosMarketStall {
	public static Class<? extends GOTStructureBase>[] allStallTypes = new Class[]{Goldsmith.class, Miner.class, Lumber.class, Mason.class, Brewer.class, Flowers.class, Butcher.class, Fish.class, Farmer.class, Blacksmith.class, Baker.class};

	protected GOTStructureCrownlandsMarketStall(boolean flag) {
		super(flag);
		kingdom = Kingdom.CROWNLANDS;
	}

	public static GOTStructureBase getRandomStall(Random random, boolean flag) {
		try {
			Class<? extends GOTStructureBase> cls = allStallTypes[random.nextInt(allStallTypes.length)];
			return cls.getConstructor(Boolean.TYPE).newInstance(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static class Baker extends GOTStructureCrownlandsMarketStall {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsBaker(world);
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

	public static class Blacksmith extends GOTStructureCrownlandsMarketStall {
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsBlacksmith(world);
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

	public static class Brewer extends GOTStructureCrownlandsMarketStall {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsBrewer(world);
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

	public static class Butcher extends GOTStructureCrownlandsMarketStall {
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsButcher(world);
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

	public static class Farmer extends GOTStructureCrownlandsMarketStall {
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsFarmer(world);
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

	public static class Fish extends GOTStructureCrownlandsMarketStall {
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsFishmonger(world);
		}

		@Override
		public void generateRoof(World world, Random random, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 % 2 == 0) {
				int k2 = Math.abs(k1);
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

	public static class Flowers extends GOTStructureCrownlandsMarketStall {
		public Flowers(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsFlorist(world);
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

	public static class Goldsmith extends GOTStructureCrownlandsMarketStall {
		public Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsGoldsmith(world);
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

	public static class Lumber extends GOTStructureCrownlandsMarketStall {
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsLumberman(world);
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

	public static class Mason extends GOTStructureCrownlandsMarketStall {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsMason(world);
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

	public static class Miner extends GOTStructureCrownlandsMarketStall {
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityCrownlandsMan createTrader(World world) {
			return new GOTEntityCrownlandsMiner(world);
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
