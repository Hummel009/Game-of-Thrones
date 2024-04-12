package got.common.world.structure.westeros.hillmen;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTStructureHillmanBase extends GOTStructureBase {
	protected Block floorBlock;
	protected int floorMeta;
	protected Block woodBlock;
	protected int woodMeta;
	protected Block plankBlock;
	protected int plankMeta;
	protected Block plankSlabBlock;
	protected int plankSlabMeta;
	protected Block plankStairBlock;
	protected Block fenceBlock;
	protected int fenceMeta;
	protected Block fenceGateBlock;
	protected Block doorBlock;
	protected Block roofBlock;
	protected int roofMeta;
	protected Block roofSlabBlock;
	protected int roofSlabMeta;
	protected Block roofStairBlock;
	protected Block barsBlock;
	protected int barsMeta;
	protected Block bedBlock;

	protected GOTStructureHillmanBase(boolean flag) {
		super(flag);
	}

	protected ItemStack getRandomHillmanWeapon(Random random) {
		ItemStack[] items = {new ItemStack(Items.iron_sword), new ItemStack(GOTItems.ironSpear), new ItemStack(GOTItems.ironDagger), new ItemStack(Items.stone_sword), new ItemStack(GOTItems.stoneSpear), new ItemStack(GOTItems.club), new ItemStack(GOTItems.trident)};
		return items[random.nextInt(items.length)].copy();
	}

	protected void placeHillmanItemFrame(World world, Random random, int i, int j, int k, int direction) {
		ItemStack[] items = {new ItemStack(Items.bone), new ItemStack(GOTItems.fur), new ItemStack(Items.flint), new ItemStack(Items.iron_sword), new ItemStack(Items.stone_sword), new ItemStack(GOTItems.ironSpear), new ItemStack(GOTItems.stoneSpear), new ItemStack(Items.bow), new ItemStack(Items.arrow), new ItemStack(GOTItems.mug), new ItemStack(GOTItems.skullCup)};
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
			default:
				floorBlock = Blocks.stained_hardened_clay;
				floorMeta = 15;
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
			fenceGateBlock = GOTBlocks.fenceGateSpruce;
			doorBlock = GOTBlocks.doorSpruce;
		} else if (random.nextBoolean()) {
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
		} else {
			woodBlock = GOTBlocks.wood5;
			woodMeta = 0;
			plankBlock = GOTBlocks.planks2;
			plankMeta = 4;
			plankSlabBlock = GOTBlocks.woodSlabSingle3;
			plankSlabMeta = 4;
			plankStairBlock = GOTBlocks.stairsPine;
			fenceBlock = GOTBlocks.fence2;
			fenceMeta = 4;
			fenceGateBlock = GOTBlocks.fenceGatePine;
			doorBlock = GOTBlocks.doorPine;
		}
		roofBlock = GOTBlocks.thatch;
		roofMeta = 0;
		roofSlabBlock = GOTBlocks.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = GOTBlocks.stairsThatch;
		if (random.nextBoolean()) {
			barsBlock = Blocks.iron_bars;
		} else {
			barsBlock = GOTBlocks.bronzeBars;
		}
		barsMeta = 0;
		bedBlock = random.nextBoolean() ? GOTBlocks.furBed : GOTBlocks.strawBed;
	}
}
