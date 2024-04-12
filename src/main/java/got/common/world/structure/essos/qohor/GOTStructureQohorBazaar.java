package got.common.world.structure.essos.qohor;

import got.common.database.GOTBlocks;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.entity.essos.qohor.*;
import got.common.world.structure.essos.common.GOTStructureEssosBazaar;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureQohorBazaar extends GOTStructureEssosBazaar {
	private static final Class<? extends GOTStructureBase>[] STALLS = new Class[]{Lumber.class, Mason.class, Fish.class, Baker.class, Goldsmith.class, Farmer.class, Blacksmith.class, Brewer.class, Miner.class, Florist.class, Butcher.class};

	public GOTStructureQohorBazaar(boolean flag) {
		super(flag);
		city = City.QOHOR;
	}

	@Override
	public Class<? extends GOTStructureBase>[] getStallClasses() {
		return STALLS;
	}

	private static class Baker extends GOTStructureBase {
		protected Baker(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
			setBlockAndMetadata(world, -1, 1, 1, GOTBlocks.planks2, 2);
			setBlockAndMetadata(world, 1, 1, 1, GOTBlocks.planks2, 2);
			placePlateItem(world, random, -1, 2, 1, GOTBlocks.ceramicPlate, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
			placePlateItem(world, random, 1, 2, 1, GOTBlocks.ceramicPlate, new ItemStack(GOTItems.oliveBread, 1 + random.nextInt(3)), true);
			placeFlowerPot(world, random.nextBoolean() ? -2 : 2, 2, 0, getRandomFlower(world, random));
			GOTEntityQohorBaker trader = new GOTEntityQohorBaker(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	private static class Blacksmith extends GOTStructureBase {
		protected Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, Blocks.anvil, 3);
			placeWeaponRack(world, -2, 2, 0, 1, new GOTStructureQohorBazaar(false).getRandomWeapon(random));
			placeWeaponRack(world, 2, 2, 0, 3, new GOTStructureQohorBazaar(false).getRandomWeapon(random));
			GOTEntityQohorBlacksmith trader = new GOTEntityQohorBlacksmith(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	private static class Brewer extends GOTStructureBase {
		protected Brewer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTBlocks.stairsCedar, 6);
			setBlockAndMetadata(world, -1, 2, 1, GOTBlocks.barrel, 2);
			setBlockAndMetadata(world, 1, 1, 1, GOTBlocks.stairsCedar, 6);
			setBlockAndMetadata(world, 1, 2, 1, GOTBlocks.barrel, 2);
			placeMug(world, random, -2, 2, 0, 1, GOTFoods.ESSOS_DRINK);
			placeMug(world, random, 2, 2, 0, 1, GOTFoods.ESSOS_DRINK);
			GOTEntityQohorBrewer trader = new GOTEntityQohorBrewer(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	private static class Butcher extends GOTStructureBase {
		protected Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
			placeKebabStand(world, random, 0, 2, 1, GOTBlocks.kebabStand, 3);
			placePlateItem(world, random, -2, 2, 0, GOTBlocks.ceramicPlate, new ItemStack(GOTItems.muttonRaw, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 2, 2, 0, GOTBlocks.ceramicPlate, new ItemStack(GOTItems.camelRaw, 1 + random.nextInt(3), 1), true);
			GOTEntityQohorButcher trader = new GOTEntityQohorButcher(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	private static class Farmer extends GOTStructureBase {
		protected Farmer(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.hay_block, 0);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.hay_block, 0);
			placePlateItem(world, random, -2, 2, 0, GOTBlocks.woodPlate, new ItemStack(GOTItems.orange, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 2, 2, 0, GOTBlocks.woodPlate, new ItemStack(GOTItems.lettuce, 1 + random.nextInt(3), 1), true);
			GOTEntityQohorFarmer trader = new GOTEntityQohorFarmer(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	private static class Fish extends GOTStructureBase {
		protected Fish(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.sponge, 0);
			placePlateItem(world, random, -2, 2, 0, GOTBlocks.ceramicPlate, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 2, 2, 0, GOTBlocks.ceramicPlate, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
			GOTEntityQohorFishmonger trader = new GOTEntityQohorFishmonger(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	private static class Florist extends GOTStructureBase {
		protected Florist(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			placeFlowerPot(world, -2, 2, 0, getRandomFlower(world, random));
			placeFlowerPot(world, 2, 2, 0, getRandomFlower(world, random));
			setBlockAndMetadata(world, -1, 0, 1, Blocks.grass, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTBlocks.doubleFlower, 3);
			setBlockAndMetadata(world, -1, 2, 1, GOTBlocks.doubleFlower, 11);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.grass, 0);
			plantFlower(world, random, 1, 2, 1);
			setBlockAndMetadata(world, 1, 1, 0, Blocks.trapdoor, 12);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.trapdoor, 15);
			GOTEntityQohorFlorist trader = new GOTEntityQohorFlorist(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	private static class Goldsmith extends GOTStructureBase {
		protected Goldsmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, -1, GOTBlocks.goldBars, 0);
			setBlockAndMetadata(world, 1, 1, -1, GOTBlocks.goldBars, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTBlocks.goldBars, 0);
			setBlockAndMetadata(world, 1, 1, 1, GOTBlocks.goldBars, 0);
			setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, -1, GOTBlocks.birdCage, 2);
			setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, 1, GOTBlocks.birdCage, 3);
			GOTEntityQohorGoldsmith trader = new GOTEntityQohorGoldsmith(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	private static class Lumber extends GOTStructureBase {
		protected Lumber(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTBlocks.wood4, 10);
			setBlockAndMetadata(world, 1, 1, 1, GOTBlocks.wood4, 2);
			setBlockAndMetadata(world, 1, 2, 1, GOTBlocks.wood4, 2);
			placeFlowerPot(world, -2, 2, 0, new ItemStack(GOTBlocks.sapling4, 1, 2));
			placeFlowerPot(world, 2, 2, 0, new ItemStack(GOTBlocks.sapling8, 1, 3));
			GOTEntityQohorLumberman trader = new GOTEntityQohorLumberman(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	private static class Mason extends GOTStructureBase {
		protected Mason(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTBlocks.brick1, 1);
			setBlockAndMetadata(world, -1, 2, 1, GOTBlocks.slabSingle1, 3);
			setBlockAndMetadata(world, 1, 1, 1, GOTBlocks.brick1, 5);
			placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(GOTItems.bronzePickaxe));
			GOTEntityQohorMason trader = new GOTEntityQohorMason(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	private static class Miner extends GOTStructureBase {
		protected Miner(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTBlocks.oreTin, 0);
			setBlockAndMetadata(world, -1, 2, 1, GOTBlocks.oreCopper, 0);
			setBlockAndMetadata(world, 1, 1, 1, GOTBlocks.oreCopper, 0);
			placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(GOTItems.bronzePickaxe));
			GOTEntityQohorMiner trader = new GOTEntityQohorMiner(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

}
