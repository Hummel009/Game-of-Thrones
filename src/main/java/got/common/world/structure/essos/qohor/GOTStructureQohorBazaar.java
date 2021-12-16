package got.common.world.structure.essos.qohor;

import java.util.*;

import got.common.database.*;
import got.common.entity.animal.*;
import got.common.entity.essos.qohor.*;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureQohorBazaar extends GOTStructureQohorBase {
	public static Class[] stalls = { Lumber.class, Mason.class, Fish.class, Baker.class, Goldsmith.class, Farmer.class, Blacksmith.class, Brewer.class, Miner.class, Florist.class, Butcher.class };

	public GOTStructureQohorBazaar(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		this.setOriginAndRotation(world, i, j, k, rotation, 10);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -13; i1 <= 13; ++i1) {
				for (int k1 = -9; k1 <= 9; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
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
		for (int i1 = -13; i1 <= 13; ++i1) {
			for (int k1 = -9; k1 <= 9; ++k1) {
				int j1;
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
				j1 = -1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, stoneBlock, stoneMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
			}
		}
		loadStrScan("essos_bazaar");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockMetaAlias("BRICK2_SLAB", brick2SlabBlock, brick2SlabMeta);
		associateBlockMetaAlias("BRICK2_SLAB_INV", brick2SlabBlock, brick2SlabMeta | 8);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		generateStrScan(world, random, 0, 0, 0);
		placeAnimalJar(world, -3, 1, -7, GOTRegistry.butterflyJar, 0, new GOTEntityButterfly(world));
		placeAnimalJar(world, 11, 1, -1, GOTRegistry.birdCageWood, 0, null);
		placeAnimalJar(world, 3, 1, 7, GOTRegistry.birdCage, 0, new GOTEntityBird(world));
		placeAnimalJar(world, -9, 3, 0, GOTRegistry.birdCageWood, 0, new GOTEntityBird(world));
		placeAnimalJar(world, 4, 3, 3, GOTRegistry.birdCageWood, 0, new GOTEntityBird(world));
		ArrayList<Class> stallClasses = new ArrayList<>(Arrays.asList(getStallClasses()));
		while (stallClasses.size() > 6) {
			stallClasses.remove(random.nextInt(stallClasses.size()));
		}
		try {
			GOTStructureBase stall0 = (GOTStructureBase) stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall1 = (GOTStructureBase) stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall2 = (GOTStructureBase) stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall3 = (GOTStructureBase) stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall4 = (GOTStructureBase) stallClasses.get(4).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			GOTStructureBase stall5 = (GOTStructureBase) stallClasses.get(5).getConstructor(Boolean.TYPE).newInstance(notifyChanges);
			generateSubstructure(stall0, world, random, -8, 1, -4, 2);
			generateSubstructure(stall1, world, random, 0, 1, -4, 2);
			generateSubstructure(stall2, world, random, 8, 1, -4, 2);
			generateSubstructure(stall3, world, random, -8, 1, 4, 0);
			generateSubstructure(stall4, world, random, 0, 1, 4, 0);
			generateSubstructure(stall5, world, random, 8, 1, 4, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public Class[] getStallClasses() {
		return stalls;
	}

	public static class Baker extends GOTStructureBase {
		public Baker(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
			setBlockAndMetadata(world, -1, 1, 1, GOTRegistry.planks2, 2);
			setBlockAndMetadata(world, 1, 1, 1, GOTRegistry.planks2, 2);
			placePlateItem(world, random, -1, 2, 1, GOTRegistry.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
			placePlateItem(world, random, 1, 2, 1, GOTRegistry.ceramicPlateBlock, new ItemStack(GOTRegistry.oliveBread, 1 + random.nextInt(3)), true);
			placeFlowerPot(world, random.nextBoolean() ? -2 : 2, 2, 0, getRandomFlower(world, random));
			GOTEntityQohorBaker trader = new GOTEntityQohorBaker(world);
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
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, Blocks.anvil, 3);
			placeWeaponRack(world, -2, 2, 0, 1, new GOTStructureQohorBazaar(false).getRandomWeapon(random));
			placeWeaponRack(world, 2, 2, 0, 3, new GOTStructureQohorBazaar(false).getRandomWeapon(random));
			GOTEntityQohorBlacksmith trader = new GOTEntityQohorBlacksmith(world);
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
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTRegistry.stairsCedar, 6);
			setBlockAndMetadata(world, -1, 2, 1, GOTRegistry.barrel, 2);
			setBlockAndMetadata(world, 1, 1, 1, GOTRegistry.stairsCedar, 6);
			setBlockAndMetadata(world, 1, 2, 1, GOTRegistry.barrel, 2);
			this.placeMug(world, random, -2, 2, 0, 1, GOTFoods.ESSOS_DRINK);
			this.placeMug(world, random, 2, 2, 0, 1, GOTFoods.ESSOS_DRINK);
			GOTEntityQohorBrewer trader = new GOTEntityQohorBrewer(world);
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
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
			placeKebabStand(world, random, 0, 2, 1, GOTRegistry.kebabStand, 3);
			placePlateItem(world, random, -2, 2, 0, GOTRegistry.ceramicPlateBlock, new ItemStack(GOTRegistry.muttonRaw, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 2, 2, 0, GOTRegistry.ceramicPlateBlock, new ItemStack(GOTRegistry.camelRaw, 1 + random.nextInt(3), 1), true);
			GOTEntityQohorButcher trader = new GOTEntityQohorButcher(world);
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
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.hay_block, 0);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.hay_block, 0);
			placePlateItem(world, random, -2, 2, 0, GOTRegistry.woodPlateBlock, new ItemStack(GOTRegistry.orange, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 2, 2, 0, GOTRegistry.woodPlateBlock, new ItemStack(GOTRegistry.lettuce, 1 + random.nextInt(3), 1), true);
			GOTEntityQohorFarmer trader = new GOTEntityQohorFarmer(world);
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
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.cauldron, 3);
			setBlockAndMetadata(world, -1, 1, -1, Blocks.sponge, 0);
			placePlateItem(world, random, -2, 2, 0, GOTRegistry.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
			placePlateItem(world, random, 2, 2, 0, GOTRegistry.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
			GOTEntityQohorFishmonger trader = new GOTEntityQohorFishmonger(world);
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
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			placeFlowerPot(world, -2, 2, 0, getRandomFlower(world, random));
			placeFlowerPot(world, 2, 2, 0, getRandomFlower(world, random));
			setBlockAndMetadata(world, -1, 0, 1, Blocks.grass, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTRegistry.doubleFlower, 3);
			setBlockAndMetadata(world, -1, 2, 1, GOTRegistry.doubleFlower, 11);
			setBlockAndMetadata(world, 1, 1, 1, Blocks.grass, 0);
			plantFlower(world, random, 1, 2, 1);
			setBlockAndMetadata(world, 1, 1, 0, Blocks.trapdoor, 12);
			setBlockAndMetadata(world, 0, 1, 1, Blocks.trapdoor, 15);
			GOTEntityQohorFlorist trader = new GOTEntityQohorFlorist(world);
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
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, -1, GOTRegistry.goldBars, 0);
			setBlockAndMetadata(world, 1, 1, -1, GOTRegistry.goldBars, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTRegistry.goldBars, 0);
			setBlockAndMetadata(world, 1, 1, 1, GOTRegistry.goldBars, 0);
			setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, -1, GOTRegistry.birdCage, 2);
			setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, 1, GOTRegistry.birdCage, 3);
			GOTEntityQohorGoldsmith trader = new GOTEntityQohorGoldsmith(world);
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
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTRegistry.wood4, 10);
			setBlockAndMetadata(world, 1, 1, 1, GOTRegistry.wood4, 2);
			setBlockAndMetadata(world, 1, 2, 1, GOTRegistry.wood4, 2);
			placeFlowerPot(world, -2, 2, 0, new ItemStack(GOTRegistry.sapling4, 1, 2));
			placeFlowerPot(world, 2, 2, 0, new ItemStack(GOTRegistry.sapling8, 1, 3));
			GOTEntityQohorLumberman trader = new GOTEntityQohorLumberman(world);
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
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTRegistry.brick1, 15);
			setBlockAndMetadata(world, -1, 2, 1, GOTRegistry.slabSingle4, 0);
			setBlockAndMetadata(world, 1, 1, 1, GOTRegistry.brick3, 13);
			placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(GOTRegistry.bronzePickaxe));
			GOTEntityQohorMason trader = new GOTEntityQohorMason(world);
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
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setBlockAndMetadata(world, -1, 1, 1, GOTRegistry.oreTin, 0);
			setBlockAndMetadata(world, -1, 2, 1, GOTRegistry.oreCopper, 0);
			setBlockAndMetadata(world, 1, 1, 1, GOTRegistry.oreCopper, 0);
			placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(GOTRegistry.bronzePickaxe));
			GOTEntityQohorMiner trader = new GOTEntityQohorMiner(world);
			spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
			return true;
		}
	}

}
