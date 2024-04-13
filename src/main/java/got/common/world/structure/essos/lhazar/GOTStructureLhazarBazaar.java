package got.common.world.structure.essos.lhazar;

import got.common.database.GOTBlocks;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.entity.animal.GOTEntityBird;
import got.common.entity.essos.lhazar.*;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GOTStructureLhazarBazaar extends GOTStructureLhazarBase {
	private static final Class<? extends GOTStructureBase>[] STALLS = new Class[]{Mason.class, Butcher.class, Brewer.class, Fish.class, Baker.class, Miner.class, Goldsmith.class, Lumber.class, Blacksmith.class, Farmer.class};

	public GOTStructureLhazarBazaar(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -17; i1 <= 17; ++i1) {
				for (int k1 = -12; k1 <= 8; ++k1) {
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
					if (maxHeight - minHeight <= 12) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -17; i1 <= 17; ++i1) {
			for (int k1 = -12; k1 <= 8; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 >= 5 && i2 <= 9 && k2 >= 10) {
					for (j1 = 1; j1 <= 5; ++j1) {
						setAir(world, i1, j1, k1);
					}
				}
				if ((k1 < -6 || k1 > -5 || i2 > 4) && (k1 != -5 || i2 < 10 || i2 > 12) && (k2 != 4 || i2 > 14) && (k2 < 2 || k2 > 3 || i2 > 15) && (k2 > 1 || i2 > 16) && (k1 != 5 || i2 > 12) && (k1 != 6 || i2 > 9) && (k1 != 7 || i2 > 4)) {
					continue;
				}
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("lhazar_bazaar");
		addBlockMetaAliasOption("BRICK", brickBlock, brickMeta);
		addBlockMetaAliasOption("BRICK", GOTBlocks.brick3, 11);
		addBlockMetaAliasOption("BRICK", Blocks.sandstone, 0);
		addBlockAliasOption("BRICK_STAIR", brickStairBlock);
		addBlockAliasOption("BRICK_STAIR", GOTBlocks.stairsSandstoneBrickCracked);
		addBlockAliasOption("BRICK_STAIR", Blocks.sandstone_stairs);
		addBlockMetaAliasOption("BRICK_WALL", brickWallBlock, brickWallMeta);
		addBlockMetaAliasOption("BRICK_WALL", GOTBlocks.wallStone3, 3);
		addBlockMetaAliasOption("BRICK_WALL", GOTBlocks.wallStoneV, 4);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM2", beam2Block, beam2Meta);
		associateBlockMetaAlias("BEAM2|4", beam2Block, beam2Meta | 4);
		associateBlockMetaAlias("BEAM2|8", beam2Block, beam2Meta | 8);
		addBlockMetaAliasOption("GROUND", Blocks.sand, 0);
		addBlockMetaAliasOption("GROUND", Blocks.dirt, 1);
		addBlockMetaAliasOption("GROUND", GOTBlocks.dirtPath, 0);
		associateBlockMetaAlias("WOOL", Blocks.wool, 14);
		associateBlockMetaAlias("CARPET", Blocks.carpet, 14);
		associateBlockMetaAlias("WOOL2", Blocks.wool, 15);
		associateBlockMetaAlias("CARPET2", Blocks.carpet, 15);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 0, 0);
		placeAnimalJar(world, -5, 4, -2, GOTBlocks.birdCageWood, 0, new GOTEntityBird(world));
		placeAnimalJar(world, -7, 5, 0, GOTBlocks.birdCageWood, 0, new GOTEntityBird(world));
		placeWallBanner(world, -3, 4, -7, GOTItemBanner.BannerType.LHAZAR, 2);
		placeWallBanner(world, 3, 4, -7, GOTItemBanner.BannerType.LHAZAR, 2);
		placeWallBanner(world, -7, 10, -8, GOTItemBanner.BannerType.LHAZAR, 2);
		placeWallBanner(world, -7, 10, -6, GOTItemBanner.BannerType.LHAZAR, 0);
		placeWallBanner(world, -8, 10, -7, GOTItemBanner.BannerType.LHAZAR, 3);
		placeWallBanner(world, -6, 10, -7, GOTItemBanner.BannerType.LHAZAR, 1);
		placeWallBanner(world, 7, 10, -8, GOTItemBanner.BannerType.LHAZAR, 2);
		placeWallBanner(world, 7, 10, -6, GOTItemBanner.BannerType.LHAZAR, 0);
		placeWallBanner(world, 6, 10, -7, GOTItemBanner.BannerType.LHAZAR, 3);
		placeWallBanner(world, 8, 10, -7, GOTItemBanner.BannerType.LHAZAR, 1);
		for (int i1 : new int[]{-7, 7}) {
			j1 = 1;
			int k1 = -11;
			GOTEntityLhazarWarrior guard = new GOTEntityLhazarWarrior(world);
			guard.spawnRidingHorse = false;
			spawnNPCAndSetHome(guard, world, i1, j1, k1, 4);
		}
		List<Class<? extends GOTStructureBase>> stallClasses = Arrays.asList(Arrays.copyOf(STALLS, STALLS.length));
		Collections.shuffle(stallClasses, random);
		try {
			GOTStructureBase stall0 = stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall1 = stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall2 = stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall3 = stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall4 = stallClasses.get(4).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			generateSubstructure(stall0, world, random, -9, 1, 0, 3);
			generateSubstructure(stall1, world, random, -5, 1, 5, 1);
			generateSubstructure(stall2, world, random, 0, 1, 6, 1);
			generateSubstructure(stall3, world, random, 8, 1, 2, 3);
			generateSubstructure(stall4, world, random, 11, 1, -2, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@SuppressWarnings("WeakerAccess")
	public static class Baker extends GOTStructureBase {
		@SuppressWarnings("WeakerAccess")
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 2, Blocks.furnace, 2);
			setBlockAndMetadata(world, 1, 1, 2, GOTBlocks.chestBasket, 2);
			placePlateItem(world, random, 1, 2, 0, GOTBlocks.ceramicPlate, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
			setBlockAndMetadata(world, 3, 2, 2, GOTBlocks.bananaCake, 0);
			placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(GOTItems.rollingPin));
			GOTEntityLhazarBaker trader = new GOTEntityLhazarBaker(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Blacksmith extends GOTStructureBase {
		@SuppressWarnings("WeakerAccess")
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 2, Blocks.anvil, 3);
			placeArmorStand(world, 1, 1, 2, 0, new ItemStack[]{null, new ItemStack(GOTItems.lhazarChestplate), null, null});
			placeWeaponRack(world, 0, 2, 2, 1, new GOTStructureLhazarBazaar(false).getRandomlhazarWeapon(random));
			placeWeaponRack(world, 3, 2, 2, 3, new GOTStructureLhazarBazaar(false).getRandomlhazarWeapon(random));
			GOTEntityLhazarBlacksmith trader = new GOTEntityLhazarBlacksmith(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Brewer extends GOTStructureBase {
		@SuppressWarnings("WeakerAccess")
		public Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 1, 1, 1, GOTBlocks.barrel, 3);
			placeMug(world, random, 1, 2, 0, 0, GOTFoods.NOMAD_DRINK);
			placeMug(world, random, 0, 2, 2, 3, GOTFoods.NOMAD_DRINK);
			placeMug(world, random, 3, 2, 1, 1, GOTFoods.NOMAD_DRINK);
			placeFlowerPot(world, 2, 2, 3, getRandomFlower(world, random));
			GOTEntityLhazarBrewer trader = new GOTEntityLhazarBrewer(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Butcher extends GOTStructureBase {
		@SuppressWarnings("WeakerAccess")
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			placePlateItem(world, random, 1, 2, 0, GOTBlocks.woodPlate, new ItemStack(GOTItems.rabbitRaw, 1 + random.nextInt(3)), true);
			placePlateItem(world, random, 0, 2, 2, GOTBlocks.woodPlate, new ItemStack(GOTItems.camelRaw, 1 + random.nextInt(3)), true);
			placePlateItem(world, random, 3, 2, 1, GOTBlocks.woodPlate, new ItemStack(GOTItems.muttonRaw, 1 + random.nextInt(3)), true);
			placeSkull(world, random, 2, 2, 3);
			GOTEntityLhazarButcher trader = new GOTEntityLhazarButcher(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Farmer extends GOTStructureBase {
		@SuppressWarnings("WeakerAccess")
		public Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 2, Blocks.cauldron, 3);
			setBlockAndMetadata(world, 1, 2, 3, Blocks.hay_block, 0);
			placePlateItem(world, random, 3, 2, 1, GOTBlocks.woodPlate, new ItemStack(GOTItems.orange, 1 + random.nextInt(3)), true);
			placeFlowerPot(world, 0, 2, 2, getRandomFlower(world, random));
			GOTEntityLhazarFarmer trader = new GOTEntityLhazarFarmer(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Fish extends GOTStructureBase {
		@SuppressWarnings("WeakerAccess")
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 2, Blocks.cauldron, 3);
			placePlateItem(world, random, 1, 2, 0, GOTBlocks.woodPlate, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 0, 2, 2, GOTBlocks.woodPlate, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
			placePlateItem(world, random, 3, 2, 1, GOTBlocks.woodPlate, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placeWeaponRack(world, 1, 2, 3, 0, new ItemStack(Items.fishing_rod));
			GOTEntityLhazarFishmonger trader = new GOTEntityLhazarFishmonger(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Goldsmith extends GOTStructureBase {
		@SuppressWarnings("WeakerAccess")
		public Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 2, 2, GOTBlocks.birdCage, 3);
			setBlockAndMetadata(world, 2, 3, 2, GOTBlocks.goldBars, 0);
			placeFlowerPot(world, 0, 2, 1, getRandomFlower(world, random));
			GOTEntityLhazarGoldsmith trader = new GOTEntityLhazarGoldsmith(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Lumber extends GOTStructureBase {
		@SuppressWarnings("WeakerAccess")
		public Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 2, GOTBlocks.wood8, 3);
			setBlockAndMetadata(world, 2, 2, 2, GOTBlocks.wood8, 3);
			placeFlowerPot(world, 0, 2, 2, new ItemStack(Blocks.sapling, 1, 4));
			placeFlowerPot(world, 3, 2, 1, new ItemStack(GOTBlocks.sapling8, 1, 3));
			GOTEntityLhazarLumberman trader = new GOTEntityLhazarLumberman(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Mason extends GOTStructureBase {
		@SuppressWarnings("WeakerAccess")
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 2, GOTBlocks.brick1, 15);
			setBlockAndMetadata(world, 2, 2, 2, GOTBlocks.brick3, 13);
			placeFlowerPot(world, 0, 2, 2, getRandomFlower(world, random));
			placeWeaponRack(world, 3, 2, 2, 3, new ItemStack(GOTItems.bronzePickaxe));
			GOTEntityLhazarMason trader = new GOTEntityLhazarMason(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static class Miner extends GOTStructureBase {
		@SuppressWarnings("WeakerAccess")
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 1, 1, 2, GOTBlocks.chestBasket, 2);
			setBlockAndMetadata(world, 2, 1, 2, GOTBlocks.oreTin, 0);
			setBlockAndMetadata(world, 2, 2, 2, GOTBlocks.oreCopper, 0);
			placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(GOTItems.bronzePickaxe));
			GOTEntityLhazarMiner trader = new GOTEntityLhazarMiner(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}
}