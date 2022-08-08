package got.common.world.structure.sothoryos.summer;

import java.util.*;

import got.common.database.*;
import got.common.entity.animal.*;
import got.common.entity.sothoryos.summer.*;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureSummerMarket extends GOTStructureSummerBase {
	public static Class[] stalls = { Goldsmith.class, Florist.class, Brewer.class, Fish.class, Butcher.class, Baker.class, Lumber.class, Miner.class, Mason.class, Blacksmith.class, Farmer.class };

	public GOTStructureSummerMarket(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j2;
		int i1;
		int i12;
		int k12;
		int k1;
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i13 = -9; i13 <= 9; ++i13) {
				for (int k13 = -9; k13 <= 9; ++k13) {
					j1 = getTopBlock(world, i13, k13) - 1;
					if (!isSurface(world, i13, j1, k13)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 12) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i14 = -8; i14 <= 8; ++i14) {
			for (int k14 = -8; k14 <= 8; ++k14) {
				int i2 = Math.abs(i14);
				int k2 = Math.abs(k14);
				if ((i2 > 6 || k2 > 6) && (i2 != 7 || k2 > 4) && (k2 != 7 || i2 > 4) && (i2 != 8 || k2 > 1) && (k2 != 8 || i2 > 1)) {
					continue;
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i14, j1, k14);
				}
				j1 = -1;
				while (!isOpaque(world, i14, j1, k14) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i14, j1, k14, plank2Block, plank2Meta);
					setGrassToDirt(world, i14, j1 - 1, k14);
					--j1;
				}
			}
		}
		loadStrScan("summer_market");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("WOOD|12", woodBlock, woodMeta | 0xC);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		generateStrScan(world, random, 0, 1, 0);
		placeWallBanner(world, 0, 5, -2, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, 0, 5, 2, GOTItemBanner.BannerType.SUMMER, 0);
		placeWallBanner(world, -2, 5, 0, GOTItemBanner.BannerType.SUMMER, 3);
		placeWallBanner(world, 2, 5, 0, GOTItemBanner.BannerType.SUMMER, 1);
		spawnItemFrame(world, 2, 2, -3, 3, getFramedItem(random));
		spawnItemFrame(world, -2, 2, 3, 1, getFramedItem(random));
		placeWeaponRack(world, -3, 2, 1, 6, getRandomWeapon(random));
		placeArmorStand(world, 2, 1, -2, 2, new ItemStack[] { new ItemStack(GOTRegistry.summerHelmet), null, null, null });
		placeFlowerPot(world, -2, 2, 2, getRandomFlower(world, random));
		placeAnimalJar(world, 2, 1, 1, GOTRegistry.butterflyJar, 0, new GOTEntityButterfly(world));
		placeAnimalJar(world, -3, 1, -1, GOTRegistry.birdCageWood, 0, new GOTEntityBird(world));
		placeAnimalJar(world, -2, 3, -2, GOTRegistry.birdCage, 0, new GOTEntityBird(world));
		placeAnimalJar(world, 6, 3, 1, GOTRegistry.birdCage, 0, new GOTEntityBird(world));
		this.placeSkull(world, random, 2, 4, -5);
		ArrayList<Class> stallClasses = new ArrayList<>(Arrays.asList(stalls));
		while (stallClasses.size() > 4) {
			stallClasses.remove(random.nextInt(stallClasses.size()));
		}
		try {
			GOTStructureBase stall0 = (GOTStructureBase) stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall1 = (GOTStructureBase) stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall2 = (GOTStructureBase) stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall3 = (GOTStructureBase) stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			generateSubstructure(stall0, world, random, 2, 1, 2, 0);
			generateSubstructure(stall1, world, random, 2, 1, -2, 1);
			generateSubstructure(stall2, world, random, -2, 1, -2, 2);
			generateSubstructure(stall3, world, random, -2, 1, 2, 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			int j12;
			for (int step = 0; step < 12 && !isOpaque(world, i1, j12 = 0 - step, k12 = -9 - step); ++step) {
				setBlockAndMetadata(world, i1, j12, k12, plank2StairBlock, 2);
				setGrassToDirt(world, i1, j12 - 1, k12);
				j2 = j12 - 1;
				while (!isOpaque(world, i1, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k12, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j2 - 1, k12);
					--j2;
				}
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			int j13;
			for (int step = 0; step < 12 && !isOpaque(world, i1, j13 = 0 - step, k12 = 9 + step); ++step) {
				setBlockAndMetadata(world, i1, j13, k12, plank2StairBlock, 3);
				setGrassToDirt(world, i1, j13 - 1, k12);
				j2 = j13 - 1;
				while (!isOpaque(world, i1, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k12, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j2 - 1, k12);
					--j2;
				}
			}
		}
		for (k1 = -1; k1 <= 1; ++k1) {
			int j14;
			for (int step = 0; step < 12 && !isOpaque(world, i12 = -9 - step, j14 = 0 - step, k1); ++step) {
				setBlockAndMetadata(world, i12, j14, k1, plank2StairBlock, 1);
				setGrassToDirt(world, i12, j14 - 1, k1);
				j2 = j14 - 1;
				while (!isOpaque(world, i12, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i12, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (k1 = -1; k1 <= 1; ++k1) {
			int j15;
			for (int step = 0; step < 12 && !isOpaque(world, i12 = 9 + step, j15 = 0 - step, k1); ++step) {
				setBlockAndMetadata(world, i12, j15, k1, plank2StairBlock, 0);
				setGrassToDirt(world, i12, j15 - 1, k1);
				j2 = j15 - 1;
				while (!isOpaque(world, i12, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i12, j2 - 1, k1);
					--j2;
				}
			}
		}
		return true;
	}

	public static class Baker extends GOTStructureBase {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			placeFlowerPot(world, 2, 2, 0, getRandomFlower(world, random));
			placePlateItem(world, random, 2, 2, 0, GOTRegistry.woodPlateBlock, new ItemStack(GOTRegistry.oliveBread, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 0, 2, 2, GOTRegistry.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3), 0), true);
			setBlockAndMetadata(world, 0, 2, 4, GOTRegistry.lemonCake, 0);
			setBlockAndMetadata(world, 3, 1, 3, GOTRegistry.woodSlabSingle4, 15);
			setBlockAndMetadata(world, 3, 2, 3, GOTRegistry.marzipanBlock, 0);
			placeWeaponRack(world, 2, 2, 4, 7, new ItemStack(GOTRegistry.rollingPin));
			GOTEntitySummerBaker trader = new GOTEntitySummerBaker(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}
	}

	public static class Blacksmith extends GOTStructureBase {
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			placeWeaponRack(world, 3, 2, 0, 2, new GOTStructureSummerMarket(false).getRandomWeapon(random));
			placeWeaponRack(world, 0, 2, 4, 3, new GOTStructureSummerMarket(false).getRandomWeapon(random));
			placeFlowerPot(world, 0, 2, 2, getRandomFlower(world, random));
			setBlockAndMetadata(world, 3, 1, 3, Blocks.anvil, 1);
			placeArmorStand(world, 4, 1, 2, 0, new ItemStack[] { new ItemStack(GOTRegistry.summerHelmet), new ItemStack(GOTRegistry.summerChestplate), null, null });
			placeArmorStand(world, 2, 1, 4, 1, null);
			GOTEntitySummerBlacksmith trader = new GOTEntitySummerBlacksmith(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}
	}

	public static class Brewer extends GOTStructureBase {
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			this.placeMug(world, random, 3, 2, 0, 0, GOTFoods.SOTHORYOS_DRINK);
			this.placeMug(world, random, 0, 2, 2, 1, GOTFoods.SOTHORYOS_DRINK);
			setBlockAndMetadata(world, 0, 2, 4, GOTRegistry.barrel, 4);
			setBlockAndMetadata(world, 3, 1, 3, GOTRegistry.woodSlabSingle4, 15);
			setBlockAndMetadata(world, 3, 2, 3, GOTRegistry.barrel, 2);
			GOTEntitySummerBrewer trader = new GOTEntitySummerBrewer(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}
	}

	public static class Butcher extends GOTStructureBase {
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			placePlateItem(world, random, 2, 2, 0, GOTRegistry.ceramicPlateBlock, new ItemStack(GOTRegistry.camelRaw, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 0, 2, 2, GOTRegistry.woodPlateBlock, new ItemStack(GOTRegistry.kebab, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 0, 2, 4, GOTRegistry.woodPlateBlock, new ItemStack(GOTRegistry.kebab, 1 + random.nextInt(3), 0), true);
			setBlockAndMetadata(world, 3, 1, 3, Blocks.furnace, 2);
			placeKebabStand(world, random, 3, 2, 3, GOTRegistry.kebabStand, 2);
			setBlockAndMetadata(world, 2, 3, 3, GOTRegistry.kebabBlock, 0);
			setBlockAndMetadata(world, 2, 4, 3, GOTRegistry.fence2, 2);
			setBlockAndMetadata(world, 2, 5, 3, GOTRegistry.fence2, 2);
			GOTEntitySummerButcher trader = new GOTEntitySummerButcher(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}
	}

	public static class Farmer extends GOTStructureBase {
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 4, Blocks.hay_block, 0);
			setBlockAndMetadata(world, 3, 1, 3, Blocks.hay_block, 0);
			setBlockAndMetadata(world, 3, 1, 2, GOTRegistry.berryBush, 9);
			setBlockAndMetadata(world, 4, 1, 2, GOTRegistry.berryBush, 9);
			placePlateItem(world, random, 3, 2, 0, GOTRegistry.woodPlateBlock, getRandomFarmFood(random), true);
			placePlateItem(world, random, 0, 2, 2, GOTRegistry.woodPlateBlock, getRandomFarmFood(random), true);
			placePlateItem(world, random, 0, 2, 4, GOTRegistry.woodPlateBlock, getRandomFarmFood(random), true);
			GOTEntitySummerFarmer trader = new GOTEntitySummerFarmer(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}

		public ItemStack getRandomFarmFood(Random random) {
			ItemStack[] items = { new ItemStack(GOTRegistry.orange), new ItemStack(GOTRegistry.lemon), new ItemStack(GOTRegistry.lime), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(GOTRegistry.lettuce), new ItemStack(GOTRegistry.turnip) };
			ItemStack ret = items[random.nextInt(items.length)].copy();
			ret.stackSize = 1 + random.nextInt(3);
			return ret;
		}
	}

	public static class Fish extends GOTStructureBase {
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			placePlateItem(world, random, 2, 2, 0, GOTRegistry.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
			placePlateItem(world, random, 0, 2, 3, GOTRegistry.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placeFlowerPot(world, 0, 2, 4, getRandomFlower(world, random));
			setBlockAndMetadata(world, 3, 1, 3, GOTRegistry.woodSlabSingle4, 15);
			placePlateItem(world, random, 3, 2, 3, GOTRegistry.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			setBlockAndMetadata(world, 2, 1, 4, Blocks.cauldron, 3);
			placeWeaponRack(world, 4, 2, 2, 6, new ItemStack(Items.fishing_rod));
			GOTEntitySummerFishmonger trader = new GOTEntitySummerFishmonger(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}
	}

	public static class Florist extends GOTStructureBase {
		public Florist(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 4, Blocks.hay_block, 0);
			setBlockAndMetadata(world, 3, 1, 3, Blocks.hay_block, 0);
			setBlockAndMetadata(world, 3, 1, 2, GOTRegistry.essosFlower, 9);
			setBlockAndMetadata(world, 4, 1, 2, GOTRegistry.yitiFlower, 9);
			GOTEntitySummerFlorist trader = new GOTEntitySummerFlorist(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}
	}

	public static class Goldsmith extends GOTStructureBase {
		public Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			placeWeaponRack(world, 2, 2, 0, 2, new ItemStack(Items.iron_pickaxe));
			placeWeaponRack(world, 0, 2, 3, 3, new ItemStack(Items.iron_shovel));
			setBlockAndMetadata(world, 4, 1, 2, GOTRegistry.oreCopper, 0);
			setBlockAndMetadata(world, 2, 1, 3, GOTRegistry.oreCopper, 0);
			setBlockAndMetadata(world, 3, 1, 3, GOTRegistry.oreTin, 0);
			setBlockAndMetadata(world, 3, 2, 3, GOTRegistry.oreCopper, 0);
			setBlockAndMetadata(world, 2, 1, 4, GOTRegistry.oreTin, 0);
			GOTEntitySummerGoldsmith trader = new GOTEntitySummerGoldsmith(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}
	}

	public static class Lumber extends GOTStructureBase {
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			placeFlowerPot(world, 2, 2, 0, new ItemStack(GOTRegistry.sapling4, 1, 2));
			placeFlowerPot(world, 0, 2, 2, new ItemStack(GOTRegistry.sapling8, 1, 3));
			placeFlowerPot(world, 0, 2, 4, new ItemStack(GOTRegistry.sapling7, 1, 3));
			setBlockAndMetadata(world, 3, 1, 3, GOTRegistry.wood8, 3);
			setBlockAndMetadata(world, 3, 2, 3, GOTRegistry.wood8, 3);
			setBlockAndMetadata(world, 2, 1, 4, GOTRegistry.wood6, 3);
			setBlockAndMetadata(world, 2, 1, 3, GOTRegistry.wood6, 11);
			setBlockAndMetadata(world, 4, 1, 2, GOTRegistry.woodBeam8, 11);
			placeWeaponRack(world, 2, 2, 4, 7, new ItemStack(GOTRegistry.bronzeAxe));
			GOTEntitySummerLumberman trader = new GOTEntitySummerLumberman(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}
	}

	public static class Mason extends GOTStructureBase {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			placeFlowerPot(world, 2, 2, 0, getRandomFlower(world, random));
			placeWeaponRack(world, 0, 2, 3, 3, new ItemStack(GOTRegistry.bronzePickaxe));
			setBlockAndMetadata(world, 4, 1, 2, Blocks.sandstone, 0);
			setBlockAndMetadata(world, 2, 1, 3, Blocks.sandstone, 0);
			setBlockAndMetadata(world, 3, 1, 3, GOTRegistry.redSandstone, 0);
			setBlockAndMetadata(world, 3, 2, 3, GOTRegistry.redSandstone, 0);
			setBlockAndMetadata(world, 2, 1, 4, GOTRegistry.redSandstone, 0);
			GOTEntitySummerMason trader = new GOTEntitySummerMason(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}
	}

	public static class Miner extends GOTStructureBase {
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			placeWeaponRack(world, 2, 2, 0, 2, new ItemStack(GOTRegistry.bronzePickaxe));
			placeWeaponRack(world, 0, 2, 3, 3, new ItemStack(GOTRegistry.bronzeShovel));
			setBlockAndMetadata(world, 4, 1, 2, GOTRegistry.oreCopper, 0);
			setBlockAndMetadata(world, 2, 1, 3, GOTRegistry.oreCopper, 0);
			setBlockAndMetadata(world, 3, 1, 3, GOTRegistry.oreTin, 0);
			setBlockAndMetadata(world, 3, 2, 3, GOTRegistry.oreCopper, 0);
			setBlockAndMetadata(world, 2, 1, 4, GOTRegistry.oreTin, 0);
			GOTEntitySummerMiner trader = new GOTEntitySummerMiner(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
			return true;
		}
	}

}
