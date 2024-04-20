package got.common.world.structure.westeros.gift;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class GOTStructureGiftBase extends GOTStructureBase {
	protected Block brickBlock;
	protected int brickMeta;
	protected Block brickStairBlock;
	protected Block brickWallBlock;
	protected int brickWallMeta;
	protected Block cobbleBlock;
	protected int cobbleMeta;
	protected Block logBlock;
	protected int logMeta;
	protected Block plankBlock;
	protected int plankMeta;
	protected Block plankSlabBlock;
	protected int plankSlabMeta;
	protected Block plankStairBlock;
	protected Block fenceBlock;
	protected int fenceMeta;
	protected Block fenceGateBlock;
	protected Block woodBeamBlock;
	protected int woodBeamMeta;
	protected Block doorBlock;
	protected Block wallBlock;
	protected int wallMeta;
	protected Block roofBlock;
	protected int roofMeta;
	protected Block roofSlabBlock;
	protected int roofSlabMeta;
	protected Block roofStairBlock;
	protected Block barsBlock;
	protected Block tableBlock;
	protected Block bedBlock;
	protected Block plateBlock;
	protected Block cropBlock;
	protected Block gateBlock;
	protected int cropMeta;
	protected GOTItemBanner.BannerType bannerType;
	protected boolean isAbandoned;

	protected GOTStructureGiftBase(boolean flag) {
		super(flag);
	}

	protected ItemStack getRangerFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.giftHelmet), new ItemStack(GOTItems.giftChestplate), new ItemStack(GOTItems.giftLeggings), new ItemStack(GOTItems.giftBoots), new ItemStack(GOTItems.ironDagger), new ItemStack(GOTItems.bronzeDagger), new ItemStack(Items.bow), new ItemStack(Items.arrow)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		brickBlock = GOTBlocks.cobblebrick;
		brickMeta = 0;
		brickStairBlock = Blocks.stone_brick_stairs;
		brickWallBlock = GOTBlocks.wallStoneV;
		brickWallMeta = 1;
		gateBlock = GOTBlocks.gateIronBars;
		cobbleBlock = Blocks.cobblestone;
		cobbleMeta = 0;
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
		} else {
			int randomCrop = random.nextInt(5);
			switch (randomCrop) {
				case 0:
					cropBlock = Blocks.carrots;
					cropMeta = 7;
					break;
				case 1:
					cropBlock = Blocks.potatoes;
					cropMeta = 7;
					break;
				case 2:
					cropBlock = GOTBlocks.lettuceCrop;
					cropMeta = 7;
					break;
				case 3:
					cropBlock = GOTBlocks.leekCrop;
					cropMeta = 7;
					break;
				default:
					cropBlock = GOTBlocks.turnipCrop;
					cropMeta = 7;
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