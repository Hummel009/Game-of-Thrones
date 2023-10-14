package got.common.world.structure.westeros.gift;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class GOTStructureGiftBase extends GOTStructureBase {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brickCarvedBlock;
	public int brickCarvedMeta;
	public Block cobbleBlock;
	public int cobbleMeta;
	public Block cobbleSlabBlock;
	public int cobbleSlabMeta;
	public Block cobbleStairBlock;
	public Block logBlock;
	public int logMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block fenceGateBlock;
	public Block woodBeamBlock;
	public int woodBeamMeta;
	public Block doorBlock;
	public Block wallBlock;
	public int wallMeta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block barsBlock;
	public Block tableBlock;
	public Block bedBlock;
	public Block plateBlock;
	public Block cropBlock;
	public Block gateBlock;
	public int cropMeta;
	public Item seedItem;
	public GOTItemBanner.BannerType bannerType;
	public boolean isAbandoned;

	protected GOTStructureGiftBase(boolean flag) {
		super(flag);
	}

	public ItemStack getRangerFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.giftHelmet), new ItemStack(GOTItems.giftChestplate), new ItemStack(GOTItems.giftLeggings), new ItemStack(GOTItems.giftBoots), new ItemStack(GOTItems.ironDagger), new ItemStack(GOTItems.bronzeDagger), new ItemStack(Items.bow), new ItemStack(Items.arrow)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		brickBlock = GOTBlocks.cobblebrick;
		brickMeta = 0;
		brickSlabBlock = Blocks.stone_slab;
		brickSlabMeta = 5;
		brickStairBlock = Blocks.stone_brick_stairs;
		brickWallBlock = GOTBlocks.wallStoneV;
		brickWallMeta = 1;
		brickCarvedBlock = GOTBlocks.cobblebrick;
		brickCarvedMeta = 0;
		gateBlock = GOTBlocks.gateIronBars;
		cobbleBlock = Blocks.cobblestone;
		cobbleMeta = 0;
		cobbleSlabBlock = Blocks.stone_slab;
		cobbleSlabMeta = 3;
		cobbleStairBlock = Blocks.stone_stairs;
		logBlock = Blocks.log;
		logMeta = 1;
		plankBlock = Blocks.planks;
		plankMeta = 1;
		plankSlabBlock = Blocks.wooden_slab;
		plankSlabMeta = 1;
		plankStairBlock = Blocks.spruce_stairs;
		fenceBlock = Blocks.fence;
		fenceMeta = 1;
		fenceGateBlock = GOTBlocks.fenceGateSpruce;
		woodBeamBlock = GOTBlocks.woodBeamV1;
		woodBeamMeta = 1;
		doorBlock = GOTBlocks.doorSpruce;
		if (random.nextBoolean()) {
			wallBlock = GOTBlocks.daub;
			wallMeta = 0;
		} else {
			wallBlock = plankBlock;
			wallMeta = plankMeta;
		}
		roofBlock = GOTBlocks.thatch;
		roofMeta = 0;
		roofSlabBlock = GOTBlocks.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = GOTBlocks.stairsThatch;
		barsBlock = random.nextBoolean() ? Blocks.iron_bars : GOTBlocks.bronzeBars;
		tableBlock = GOTBlocks.tableGift;
		bedBlock = GOTBlocks.strawBed;
		plateBlock = random.nextBoolean() ? GOTBlocks.woodPlate : GOTBlocks.ceramicPlate;
		if (random.nextBoolean()) {
			cropBlock = Blocks.wheat;
			cropMeta = 7;
			seedItem = Items.wheat_seeds;
		} else {
			int randomCrop = random.nextInt(5);
			switch (randomCrop) {
				case 0:
					cropBlock = Blocks.carrots;
					cropMeta = 7;
					seedItem = Items.carrot;
					break;
				case 1:
					cropBlock = Blocks.potatoes;
					cropMeta = 7;
					seedItem = Items.potato;
					break;
				case 2:
					cropBlock = GOTBlocks.lettuceCrop;
					cropMeta = 7;
					seedItem = GOTItems.lettuce;
					break;
				case 3:
					cropBlock = GOTBlocks.leekCrop;
					cropMeta = 7;
					seedItem = GOTItems.leek;
					break;
				default:
					cropBlock = GOTBlocks.turnipCrop;
					cropMeta = 7;
					seedItem = GOTItems.turnip;
					break;
			}
		}
		bannerType = GOTItemBanner.BannerType.NIGHT;

		if (isAbandoned) {
			woodBeamBlock = GOTBlocks.rottenLog;
			woodBeamMeta = 0;
			plankBlock = GOTBlocks.planksRotten;
			plankMeta = 0;
			plankSlabBlock = GOTBlocks.rottenSlabSingle;
			plankSlabMeta = 0;
			fenceBlock = GOTBlocks.fenceRotten;
			fenceMeta = 0;
			roofBlock = Blocks.air;
			roofMeta = 0;
			roofSlabBlock = Blocks.air;
			roofSlabMeta = 0;
			plankStairBlock = GOTBlocks.stairsRotten;
			bedBlock = GOTBlocks.furBed;
		}
	}
}
