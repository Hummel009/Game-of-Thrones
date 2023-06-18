package got.common.world.structure.essos.norvos;

import got.common.database.GOTBlocks;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.entity.essos.norvos.*;
import got.common.world.structure.essos.common.GOTStructureEssosBazaar;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureNorvosBazaar extends GOTStructureEssosBazaar {
	public static Class<? extends GOTStructureBase>[] stalls = new Class[]{Lumber.class, Mason.class, Fish.class, Baker.class, Goldsmith.class, Farmer.class, Blacksmith.class, Brewer.class, Miner.class, Florist.class, Butcher.class};

	public GOTStructureNorvosBazaar(boolean flag) {
		super(flag);
		city = City.NORVOS;
	}

	@Override
	public Class<? extends GOTStructureBase>[] getStallClasses() {
		return stalls;
	}

	public static class Baker extends GOTStructureBase {
		public Baker(boolean flag) {
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
			GOTEntityNorvosBaker trader = new GOTEntityNorvosBaker(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Blacksmith extends GOTStructureBase {
		public Blacksmith(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, Blocks.anvil, 3);
			placeWeaponRack(world, -2, 2, 0, 1, new GOTStructureNorvosBazaar(false).getRandomWeapon(random));
			placeWeaponRack(world, 2, 2, 0, 3, new GOTStructureNorvosBazaar(false).getRandomWeapon(random));
			GOTEntityNorvosBlacksmith trader = new GOTEntityNorvosBlacksmith(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Brewer extends GOTStructureBase {
		public Brewer(boolean flag) {
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
			GOTEntityNorvosBrewer trader = new GOTEntityNorvosBrewer(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Butcher extends GOTStructureBase {
		public Butcher(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
			placeKebabStand(world, random, 0, 2, 1, GOTBlocks.kebabStand, 3);
			placePlateItem(world, random, -2, 2, 0, GOTBlocks.ceramicPlate, new ItemStack(GOTItems.muttonRaw, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 2, 2, 0, GOTBlocks.ceramicPlate, new ItemStack(GOTItems.camelRaw, 1 + random.nextInt(3), 1), true);
			GOTEntityNorvosButcher trader = new GOTEntityNorvosButcher(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Farmer extends GOTStructureBase {
		public Farmer(boolean flag) {
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
			GOTEntityNorvosFarmer trader = new GOTEntityNorvosFarmer(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Fish extends GOTStructureBase {
		public Fish(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.sponge, 0);
			placePlateItem(world, random, -2, 2, 0, GOTBlocks.ceramicPlate, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 2, 2, 0, GOTBlocks.ceramicPlate, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
			GOTEntityNorvosFishmonger trader = new GOTEntityNorvosFishmonger(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Florist extends GOTStructureBase {
		public Florist(boolean flag) {
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
			GOTEntityNorvosFlorist trader = new GOTEntityNorvosFlorist(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Goldsmith extends GOTStructureBase {
		public Goldsmith(boolean flag) {
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
			GOTEntityNorvosGoldsmith trader = new GOTEntityNorvosGoldsmith(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Lumber extends GOTStructureBase {
		public Lumber(boolean flag) {
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
			GOTEntityNorvosLumberman trader = new GOTEntityNorvosLumberman(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Mason extends GOTStructureBase {
		public Mason(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTBlocks.brick1, 1);
			setBlockAndMetadata(world, -1, 2, 1, GOTBlocks.slabSingle1, 3);
			setBlockAndMetadata(world, 1, 1, 1, GOTBlocks.brick1, 5);
			placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(GOTItems.bronzePickaxe));
			GOTEntityNorvosMason trader = new GOTEntityNorvosMason(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

	public static class Miner extends GOTStructureBase {
		public Miner(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTBlocks.oreTin, 0);
			setBlockAndMetadata(world, -1, 2, 1, GOTBlocks.oreCopper, 0);
			setBlockAndMetadata(world, 1, 1, 1, GOTBlocks.oreCopper, 0);
			placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(GOTItems.bronzePickaxe));
			GOTEntityNorvosMiner trader = new GOTEntityNorvosMiner(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

}
