package got.common.world.structure.essos.mossovy;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;

public abstract class GOTStructureMossovyBase extends GOTStructureBase {
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

	public GOTStructureMossovyBase(boolean flag) {
		super(flag);
	}

	public ItemStack getRangerFramedItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.mossovyChestplate), new ItemStack(GOTRegistry.mossovyLeggings), new ItemStack(GOTRegistry.mossovyBoots), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.bronzeDagger), new ItemStack(Items.bow), new ItemStack(Items.arrow) };
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		brickBlock = GOTRegistry.cobblebrick;
		brickMeta = 0;
		brickSlabBlock = Blocks.stone_slab;
		brickSlabMeta = 5;
		brickStairBlock = Blocks.stone_brick_stairs;
		brickWallBlock = GOTRegistry.wallStoneV;
		brickWallMeta = 1;
		brickCarvedBlock = GOTRegistry.cobblebrick;
		brickCarvedMeta = 0;
		gateBlock = GOTRegistry.gateIronBars;
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
		fenceGateBlock = GOTRegistry.fenceGateSpruce;
		woodBeamBlock = GOTRegistry.woodBeamV1;
		woodBeamMeta = 1;
		doorBlock = GOTRegistry.doorSpruce;
		if (random.nextBoolean()) {
			wallBlock = GOTRegistry.daub;
			wallMeta = 0;
		} else {
			wallBlock = plankBlock;
			wallMeta = plankMeta;
		}
		roofBlock = GOTRegistry.thatch;
		roofMeta = 0;
		roofSlabBlock = GOTRegistry.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = GOTRegistry.stairsThatch;
		barsBlock = random.nextBoolean() ? Blocks.iron_bars : GOTRegistry.bronzeBars;
		tableBlock = GOTRegistry.tableMossovy;
		bedBlock = GOTRegistry.strawBed;
		plateBlock = random.nextBoolean() ? GOTRegistry.woodPlateBlock : GOTRegistry.ceramicPlateBlock;
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
				cropBlock = GOTRegistry.lettuceCrop;
				cropMeta = 7;
				seedItem = GOTRegistry.lettuce;
				break;
			case 3:
				cropBlock = GOTRegistry.leekCrop;
				cropMeta = 7;
				seedItem = GOTRegistry.leek;
				break;
			case 4:
				cropBlock = GOTRegistry.turnipCrop;
				cropMeta = 7;
				seedItem = GOTRegistry.turnip;
				break;
			default:
				break;
			}
		}
		bannerType = GOTItemBanner.BannerType.MOSSOVY;
	}
}
