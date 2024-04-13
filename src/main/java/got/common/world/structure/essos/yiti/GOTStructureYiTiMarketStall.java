package got.common.world.structure.essos.yiti;

import com.google.common.math.IntMath;
import got.common.database.GOTBlocks;
import got.common.entity.essos.yiti.*;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTStructureYiTiMarketStall extends GOTStructureYiTiBase {
	private static final Class<? extends GOTStructureBase>[] STALLS = new Class[]{Miner.class, Florist.class, Blacksmith.class, Lumber.class, Mason.class, Butcher.class, Brewer.class, Fish.class, Baker.class, Farmer.class, Gold.class};

	protected GOTStructureYiTiMarketStall(boolean flag) {
		super(flag);
	}

	public static GOTStructureBase getRandomStall(Random random, boolean flag) {
		try {
			Class<? extends GOTStructureBase> cls = STALLS[random.nextInt(STALLS.length)];
			return cls.getConstructor(Boolean.TYPE).newInstance(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return new GOTStructureYiTiMarketStall.Gold(flag);
		}
	}

	protected abstract GOTEntityYiTiMan createTrader(World var1);

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -2; i12 <= 2; ++i12) {
				for (int k1 = -2; k1 <= 2; ++k1) {
					j1 = getTopBlock(world, i12, k1) - 1;
					if (!isSurface(world, i12, j1, k1)) {
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
		for (i1 = -2; i1 <= 2; ++i1) {
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
						setBlockAndMetadata(world, i1, j1, k1, woodBeamBlock, woodBeamMeta);
					}
				} else if (i2 == 2 || k2 == 2) {
					setBlockAndMetadata(world, i1, 3, k1, GOTBlocks.reedBars, 0);
				}
				generateRoof(world, i1, 4, k1);
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 1, -2, plankStairBlock, 6);
			setBlockAndMetadata(world, i1, 1, 2, plankStairBlock, 7);
		}
		for (int k1 = -1; k1 <= 1; ++k1) {
			setBlockAndMetadata(world, -2, 1, k1, plankStairBlock, 5);
			setBlockAndMetadata(world, 2, 1, k1, plankStairBlock, 4);
		}
		setBlockAndMetadata(world, -2, 1, 0, fenceGateBlock, 1);
		setBlockAndMetadata(world, -1, 1, 1, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 1, 1, 1, Blocks.chest, 2);
		GOTEntityYiTiMan trader = createTrader(world);
		spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
		return true;
	}

	protected abstract void generateRoof(World var1, int var3, int var4, int var5);

	@SuppressWarnings("WeakerAccess")
	public static class Baker extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiBaker(world);
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
	public static class Blacksmith extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiBlacksmith(world);
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
	public static class Brewer extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiBrewer(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			if (i2 % 2 == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 0);
			}
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Butcher extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiButcher(world);
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
	public static class Farmer extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiFarmer(world);
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
	public static class Fish extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiFishmonger(world);
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
	public static class Florist extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Florist(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiFlorist(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			if (IntMath.mod(i1, 2) == 0 && IntMath.mod(k1, 2) == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Gold extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Gold(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiGoldsmith(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 4);
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Lumber extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiLumberman(world);
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
	public static class Mason extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiMason(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			int i2 = Math.abs(i1);
			int k2 = Math.abs(k1);
			if (i2 == 2 || k2 == 2 || i2 != 1 && k2 != 1) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 7);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 14);
			}
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Miner extends GOTStructureYiTiMarketStall {
		@SuppressWarnings("WeakerAccess")
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public GOTEntityYiTiMan createTrader(World world) {
			return new GOTEntityYiTiMiner(world);
		}

		@Override
		public void generateRoof(World world, int i1, int j1, int k1) {
			if (IntMath.mod(i1, 2) == 0 && IntMath.mod(k1, 2) == 0) {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 15);
			} else {
				setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, 12);
			}
		}
	}
}