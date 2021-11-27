package got.common.world.structure.westeros.hillmen;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class GOTStructureHillmanBase extends GOTStructureBase {
	public Block floorBlock;
	public int floorMeta;
	public Block woodBlock;
	public int woodMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block fenceGateBlock;
	public Block doorBlock;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block barsBlock;
	public int barsMeta;
	public Block bedBlock;

	public GOTStructureHillmanBase(boolean flag) {
		super(flag);
	}

	public ItemStack getRandomHillmanWeapon(Random random) {
		ItemStack[] items = { new ItemStack(Items.iron_sword), new ItemStack(GOTRegistry.ironSpear), new ItemStack(GOTRegistry.ironDagger), new ItemStack(Items.stone_sword), new ItemStack(GOTRegistry.stoneSpear), new ItemStack(GOTRegistry.club), new ItemStack(GOTRegistry.trident) };
		return items[random.nextInt(items.length)].copy();
	}

	public void placeHillmanItemFrame(World world, Random random, int i, int j, int k, int direction) {
		ItemStack[] items = { new ItemStack(Items.bone), new ItemStack(GOTRegistry.fur), new ItemStack(Items.flint), new ItemStack(Items.iron_sword), new ItemStack(Items.stone_sword), new ItemStack(GOTRegistry.ironSpear), new ItemStack(GOTRegistry.stoneSpear), new ItemStack(Items.bow), new ItemStack(Items.arrow), new ItemStack(GOTRegistry.mug), new ItemStack(GOTRegistry.skullCup) };
		ItemStack item = items[random.nextInt(items.length)].copy();
		spawnItemFrame(world, i, j, k, direction, item);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		int randomFloor = random.nextInt(5);
		switch (randomFloor) {
		case 0:
			floorBlock = Blocks.cobblestone;
			floorMeta = 0;
			break;
		case 1:
			floorBlock = Blocks.hardened_clay;
			floorMeta = 0;
			break;
		case 2:
			floorBlock = Blocks.stained_hardened_clay;
			floorMeta = 7;
			break;
		case 3:
			floorBlock = Blocks.stained_hardened_clay;
			floorMeta = 12;
			break;
		case 4:
			floorBlock = Blocks.stained_hardened_clay;
			floorMeta = 15;
			break;
		default:
			break;
		}
		if (random.nextBoolean()) {
			woodBlock = Blocks.log;
			woodMeta = 1;
			plankBlock = Blocks.planks;
			plankMeta = 1;
			plankSlabBlock = Blocks.wooden_slab;
			plankSlabMeta = 1;
			plankStairBlock = Blocks.spruce_stairs;
			fenceBlock = Blocks.fence;
			fenceMeta = 1;
			fenceGateBlock = GOTRegistry.fenceGateSpruce;
			doorBlock = GOTRegistry.doorSpruce;
		} else {
			int randomWood = random.nextInt(2);
			if (randomWood == 0) {
				woodBlock = Blocks.log;
				woodMeta = 0;
				plankBlock = Blocks.planks;
				plankMeta = 0;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 0;
				plankStairBlock = Blocks.oak_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 0;
				fenceGateBlock = Blocks.fence_gate;
				doorBlock = Blocks.wooden_door;
			} else if (randomWood == 1) {
				woodBlock = GOTRegistry.wood5;
				woodMeta = 0;
				plankBlock = GOTRegistry.planks2;
				plankMeta = 4;
				plankSlabBlock = GOTRegistry.woodSlabSingle3;
				plankSlabMeta = 4;
				plankStairBlock = GOTRegistry.stairsPine;
				fenceBlock = GOTRegistry.fence2;
				fenceMeta = 4;
				fenceGateBlock = GOTRegistry.fenceGatePine;
				doorBlock = GOTRegistry.doorPine;
			}
		}
		roofBlock = GOTRegistry.thatch;
		roofMeta = 0;
		roofSlabBlock = GOTRegistry.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = GOTRegistry.stairsThatch;
		if (random.nextBoolean()) {
			barsBlock = Blocks.iron_bars;
		} else {
			barsBlock = GOTRegistry.bronzeBars;
		}
		barsMeta = 0;
		bedBlock = random.nextBoolean() ? GOTRegistry.furBed : GOTRegistry.strawBed;
	}
}
