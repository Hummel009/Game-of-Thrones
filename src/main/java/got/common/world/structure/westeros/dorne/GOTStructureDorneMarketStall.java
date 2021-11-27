package got.common.world.structure.westeros.dorne;

import java.util.Random;

import com.google.common.math.IntMath;

import got.common.database.GOTRegistry;
import got.common.entity.westeros.dorne.*;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class GOTStructureDorneMarketStall extends GOTStructureDorneBase {
	public static Class[] allStallTypes = { Greengrocer.class, Lumber.class, Mason.class, Brewer.class, Flowers.class, Butcher.class, Fish.class, Farmer.class, Blacksmith.class, Baker.class };

	public GOTStructureDorneMarketStall(boolean flag) {
		super(flag);
	}

	public abstract GOTEntityDorneMan createTrader(World var1);

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -2; i1 <= 2; ++i1) {
				for (int k1 = -2; k1 <= 2; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -2; i1 <= 2; ++i1) {
			for (int k1 = -2; k1 <= 2; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
				if (i2 == 2 && k2 == 2) {
					for (j1 = 1; j1 <= 3; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, fenceBlock, fenceMeta);
					}
				} else if (i2 == 2 || k2 == 2) {
					setBlockAndMetadata(world, i1, 1, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i1, 3, k1, fenceBlock, fenceMeta);
				}
				generateRoof(world, random, i1, 4, k1);
			}
		}
		setBlockAndMetadata(world, -2, 1, 0, fenceGateBlock, 1);
		setBlockAndMetadata(world, -1, 1, -1, GOTRegistry.tableDorne, 0);
		setBlockAndMetadata(world, 1, 1, -1, Blocks.chest, 3);
		GOTEntityDorneMan trader = createTrader(world);
		spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
		return true;
	}

	public abstract void generateRoof(World var1, Random var2, int var3, int var4, int var5);

	public static GOTStructureBase getRandomStall(Random random, boolean flag) {
		try {
			Class cls = allStallTypes[random.nextInt(allStallTypes.length)];
			return (GOTStructureBase) cls.getConstructor(Boolean.TYPE).newInstance(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static class Baker extends GOTStructureDorneMarketStall {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneBaker(world);
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

	public static class Blacksmith extends GOTStructureDorneMarketStall {
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneBlacksmith(world);
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

	public static class Brewer extends GOTStructureDorneMarketStall {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneBrewer(world);
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

	public static class Butcher extends GOTStructureDorneMarketStall {
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneButcher(world);
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

	public static class Farmer extends GOTStructureDorneMarketStall {
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneFarmer(world);
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

	public static class Fish extends GOTStructureDorneMarketStall {
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneFishmonger(world);
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

	public static class Flowers extends GOTStructureDorneMarketStall {
		public Flowers(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneFlorist(world);
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

	public static class Goldsmith extends GOTStructureDorneMarketStall {
		public Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneGoldsmith(world);
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

	public static class Greengrocer extends GOTStructureDorneMarketStall {
		public Greengrocer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneGreengrocer(world);
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

	public static class Lumber extends GOTStructureDorneMarketStall {
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneLumberman(world);
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

	public static class Mason extends GOTStructureDorneMarketStall {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityDorneMan createTrader(World world) {
			return new GOTEntityDorneMason(world);
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

}
