package got.common.world.structure.essos.lhazar;

import got.common.database.GOTFoods;
import got.common.database.GOTRegistry;
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
	public static Class[] stalls = {Mason.class, Butcher.class, Brewer.class, Fish.class, Baker.class, Miner.class, Goldsmith.class, Lumber.class, Blacksmith.class, Farmer.class};

	public GOTStructureLhazarBazaar(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 8);
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
				if (i2 >= 5 && i2 <= 9 && k2 >= 10 && k2 <= 12) {
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
		addBlockMetaAliasOption("BRICK", 6, brickBlock, brickMeta);
		addBlockMetaAliasOption("BRICK", 2, GOTRegistry.brick3, 11);
		addBlockMetaAliasOption("BRICK", 8, Blocks.sandstone, 0);
		addBlockAliasOption("BRICK_STAIR", 6, brickStairBlock);
		addBlockAliasOption("BRICK_STAIR", 2, GOTRegistry.stairsSandstoneBrickCracked);
		addBlockAliasOption("BRICK_STAIR", 8, Blocks.sandstone_stairs);
		addBlockMetaAliasOption("BRICK_WALL", 6, brickWallBlock, brickWallMeta);
		addBlockMetaAliasOption("BRICK_WALL", 2, GOTRegistry.wallStone3, 3);
		addBlockMetaAliasOption("BRICK_WALL", 8, GOTRegistry.wallStoneV, 4);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM2", beam2Block, beam2Meta);
		associateBlockMetaAlias("BEAM2|4", beam2Block, beam2Meta | 4);
		associateBlockMetaAlias("BEAM2|8", beam2Block, beam2Meta | 8);
		addBlockMetaAliasOption("GROUND", 10, Blocks.sand, 0);
		addBlockMetaAliasOption("GROUND", 3, Blocks.dirt, 1);
		addBlockMetaAliasOption("GROUND", 2, GOTRegistry.dirtPath, 0);
		associateBlockMetaAlias("WOOL", Blocks.wool, 14);
		associateBlockMetaAlias("CARPET", Blocks.carpet, 14);
		associateBlockMetaAlias("WOOL2", Blocks.wool, 15);
		associateBlockMetaAlias("CARPET2", Blocks.carpet, 15);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 0, 0);
		placeAnimalJar(world, -5, 4, -2, GOTRegistry.birdCageWood, 0, new GOTEntityBird(world));
		placeAnimalJar(world, -7, 5, 0, GOTRegistry.birdCageWood, 0, new GOTEntityBird(world));
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
		List<Class> stallClasses = Arrays.asList(Arrays.copyOf(stalls, stalls.length));
		Collections.shuffle(stallClasses, random);
		try {
			GOTStructureBase stall0 = (GOTStructureBase) stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall1 = (GOTStructureBase) stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall2 = (GOTStructureBase) stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall3 = (GOTStructureBase) stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall4 = (GOTStructureBase) stallClasses.get(4).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
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

	public static class Baker extends GOTStructureBase {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 2, Blocks.furnace, 2);
			setBlockAndMetadata(world, 1, 1, 2, GOTRegistry.chestBasket, 2);
			placePlateItem(world, random, 1, 2, 0, GOTRegistry.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
			setBlockAndMetadata(world, 3, 2, 2, GOTRegistry.bananaCake, 0);
			placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(GOTRegistry.rollingPin));
			GOTEntityLhazarBaker trader = new GOTEntityLhazarBaker(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 1, 2, Blocks.anvil, 3);
			placeArmorStand(world, 1, 1, 2, 0, new ItemStack[]{null, new ItemStack(GOTRegistry.lhazarChestplate), null, null});
			placeWeaponRack(world, 0, 2, 2, 1, new GOTStructureLhazarBazaar(false).getRandomlhazarWeapon(random));
			placeWeaponRack(world, 3, 2, 2, 3, new GOTStructureLhazarBazaar(false).getRandomlhazarWeapon(random));
			GOTEntityLhazarBlacksmith trader = new GOTEntityLhazarBlacksmith(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 1, 1, 1, GOTRegistry.barrel, 3);
			this.placeMug(world, random, 1, 2, 0, 0, GOTFoods.NOMAD_DRINK);
			this.placeMug(world, random, 0, 2, 2, 3, GOTFoods.NOMAD_DRINK);
			this.placeMug(world, random, 3, 2, 1, 1, GOTFoods.NOMAD_DRINK);
			placeFlowerPot(world, 2, 2, 3, getRandomFlower(world, random));
			GOTEntityLhazarBrewer trader = new GOTEntityLhazarBrewer(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			placePlateItem(world, random, 1, 2, 0, GOTRegistry.woodPlateBlock, new ItemStack(GOTRegistry.rabbitRaw, 1 + random.nextInt(3)), true);
			placePlateItem(world, random, 0, 2, 2, GOTRegistry.woodPlateBlock, new ItemStack(GOTRegistry.camelRaw, 1 + random.nextInt(3)), true);
			placePlateItem(world, random, 3, 2, 1, GOTRegistry.woodPlateBlock, new ItemStack(GOTRegistry.muttonRaw, 1 + random.nextInt(3)), true);
			this.placeSkull(world, random, 2, 2, 3);
			GOTEntityLhazarButcher trader = new GOTEntityLhazarButcher(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 1, 2, Blocks.cauldron, 3);
			setBlockAndMetadata(world, 1, 2, 3, Blocks.hay_block, 0);
			placePlateItem(world, random, 3, 2, 1, GOTRegistry.woodPlateBlock, new ItemStack(GOTRegistry.orange, 1 + random.nextInt(3)), true);
			placeFlowerPot(world, 0, 2, 2, getRandomFlower(world, random));
			GOTEntityLhazarFarmer trader = new GOTEntityLhazarFarmer(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

	public static class Fish extends GOTStructureBase {
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 2, 1, 2, Blocks.cauldron, 3);
			placePlateItem(world, random, 1, 2, 0, GOTRegistry.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 0, 2, 2, GOTRegistry.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
			placePlateItem(world, random, 3, 2, 1, GOTRegistry.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placeWeaponRack(world, 1, 2, 3, 0, new ItemStack(Items.fishing_rod));
			GOTEntityLhazarFishmonger trader = new GOTEntityLhazarFishmonger(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 2, 2, GOTRegistry.birdCage, 3);
			setBlockAndMetadata(world, 2, 3, 2, GOTRegistry.goldBars, 0);
			placeFlowerPot(world, 0, 2, 1, getRandomFlower(world, random));
			GOTEntityLhazarGoldsmith trader = new GOTEntityLhazarGoldsmith(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 1, 2, GOTRegistry.wood8, 3);
			setBlockAndMetadata(world, 2, 2, 2, GOTRegistry.wood8, 3);
			placeFlowerPot(world, 0, 2, 2, new ItemStack(Blocks.sapling, 1, 4));
			placeFlowerPot(world, 3, 2, 1, new ItemStack(GOTRegistry.sapling8, 1, 3));
			GOTEntityLhazarLumberman trader = new GOTEntityLhazarLumberman(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 2, 1, 2, GOTRegistry.brick1, 15);
			setBlockAndMetadata(world, 2, 2, 2, GOTRegistry.brick3, 13);
			placeFlowerPot(world, 0, 2, 2, getRandomFlower(world, random));
			placeWeaponRack(world, 3, 2, 2, 3, new ItemStack(GOTRegistry.bronzePickaxe));
			GOTEntityLhazarMason trader = new GOTEntityLhazarMason(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
			setBlockAndMetadata(world, 1, 1, 2, GOTRegistry.chestBasket, 2);
			setBlockAndMetadata(world, 2, 1, 2, GOTRegistry.oreTin, 0);
			setBlockAndMetadata(world, 2, 2, 2, GOTRegistry.oreCopper, 0);
			placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(GOTRegistry.bronzePickaxe));
			GOTEntityLhazarMiner trader = new GOTEntityLhazarMiner(world);
			spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
			return true;
		}
	}

}
