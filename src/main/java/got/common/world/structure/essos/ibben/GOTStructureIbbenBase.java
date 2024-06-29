package got.common.world.structure.essos.ibben;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public abstract class GOTStructureIbbenBase extends GOTStructureBase {
	protected Block rockBlock;
	protected int rockMeta;
	protected Block rockSlabBlock;
	protected int rockSlabMeta;
	protected Block rockSlabDoubleBlock;
	protected int rockSlabDoubleMeta;
	protected Block rockWallBlock;
	protected int rockWallMeta;
	protected Block brickBlock;
	protected int brickMeta;
	protected Block brickSlabBlock;
	protected int brickSlabMeta;
	protected Block brickStairBlock;
	protected Block brickWallBlock;
	protected int brickWallMeta;
	protected Block brickCarvedBlock;
	protected int brickCarvedMeta;
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
	protected Block plank2Block;
	protected int plank2Meta;
	protected Block plank2SlabBlock;
	protected int plank2SlabMeta;
	protected Block plank2StairBlock;
	protected Block fence2Block;
	protected int fence2Meta;
	protected Block woodBeam2Block;
	protected int woodBeam2Meta;
	protected Block woodBeamIbbenBlock;
	protected int woodBeamIbbenMeta;
	protected Block woodBeamIbbenGoldBlock;
	protected int woodBeamIbbenGoldMeta;
	protected Block roofBlock;
	protected int roofMeta;
	protected Block roofSlabBlock;
	protected int roofSlabMeta;
	protected Block roofStairBlock;
	protected Block barsBlock;
	protected Block bedBlock;
	protected Block gateBlock;
	protected Block plateBlock;
	protected Block carpetBlock;
	protected int carpetMeta;
	protected Block cropBlock;
	protected int cropMeta;
	protected Item seedItem;
	protected GOTItemBanner.BannerType bannerType;

	protected GOTStructureIbbenBase(boolean flag) {
		super(flag);
	}

	protected static Block getRandomCakeBlock(Random random) {
		int i = random.nextInt(3);
		switch (i) {
			case 0:
				return GOTBlocks.appleCrumble;
			case 1:
				return GOTBlocks.cherryPie;
			case 2:
				return GOTBlocks.berryPie;
			default:
				return null;
		}
	}

	protected boolean oneWoodType() {
		return false;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		rockBlock = GOTBlocks.rock;
		rockMeta = 2;
		rockSlabBlock = GOTBlocks.slabSingle2;
		rockSlabMeta = 1;
		rockSlabDoubleBlock = GOTBlocks.slabDouble2;
		rockSlabDoubleMeta = 1;
		rockWallBlock = GOTBlocks.wallStone1;
		rockWallMeta = 8;
		brickBlock = GOTBlocks.brick1;
		brickMeta = 4;
		brickSlabBlock = GOTBlocks.slabSingle1;
		brickSlabMeta = 6;
		brickStairBlock = GOTBlocks.stairsRhyoliteBrick;
		brickWallBlock = GOTBlocks.wallStone1;
		brickWallMeta = 6;
		brickCarvedBlock = GOTBlocks.brick5;
		brickCarvedMeta = 3;
		cobbleBlock = Blocks.cobblestone;
		cobbleMeta = 0;
		int randomWood1 = random.nextInt(6);
		switch (randomWood1) {
			case 0:
				logBlock = GOTBlocks.wood5;
				logMeta = 0;
				plankBlock = GOTBlocks.planks2;
				plankMeta = 4;
				plankSlabBlock = GOTBlocks.woodSlabSingle3;
				plankSlabMeta = 4;
				plankStairBlock = GOTBlocks.stairsPine;
				fenceBlock = GOTBlocks.fence2;
				fenceMeta = 4;
				fenceGateBlock = GOTBlocks.fenceGatePine;
				woodBeamBlock = GOTBlocks.woodBeam5;
				woodBeamMeta = 0;
				doorBlock = GOTBlocks.doorPine;
				break;
			case 1:
				logBlock = GOTBlocks.wood2;
				logMeta = 1;
				plankBlock = GOTBlocks.planks1;
				plankMeta = 9;
				plankSlabBlock = GOTBlocks.woodSlabSingle2;
				plankSlabMeta = 1;
				plankStairBlock = GOTBlocks.stairsBeech;
				fenceBlock = GOTBlocks.fence;
				fenceMeta = 9;
				fenceGateBlock = GOTBlocks.fenceGateBeech;
				woodBeamBlock = GOTBlocks.woodBeam2;
				woodBeamMeta = 1;
				doorBlock = GOTBlocks.doorBeech;
				break;
			case 2:
				logBlock = GOTBlocks.fruitWood;
				logMeta = 0;
				plankBlock = GOTBlocks.planks1;
				plankMeta = 4;
				plankSlabBlock = GOTBlocks.woodSlabSingle1;
				plankSlabMeta = 4;
				plankStairBlock = GOTBlocks.stairsApple;
				fenceBlock = GOTBlocks.fence;
				fenceMeta = 4;
				fenceGateBlock = GOTBlocks.fenceGateApple;
				woodBeamBlock = GOTBlocks.woodBeamFruit;
				woodBeamMeta = 0;
				doorBlock = GOTBlocks.doorApple;
				break;
			default:
				logBlock = Blocks.log;
				logMeta = 0;
				plankBlock = Blocks.planks;
				plankMeta = 0;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 0;
				plankStairBlock = Blocks.oak_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 0;
				fenceGateBlock = Blocks.fence_gate;
				woodBeamBlock = GOTBlocks.woodBeamV1;
				woodBeamMeta = 0;
				doorBlock = Blocks.wooden_door;
				break;
		}

		Block fenceGate2Block;
		int log2Meta;
		Block log2Block;

		int randomWood2 = random.nextInt(4);

		switch (randomWood2) {
			case 0:
				log2Block = GOTBlocks.wood3;
				log2Meta = 1;
				plank2Block = GOTBlocks.planks1;
				plank2Meta = 13;
				plank2SlabBlock = GOTBlocks.woodSlabSingle2;
				plank2SlabMeta = 5;
				plank2StairBlock = GOTBlocks.stairsLarch;
				fence2Block = GOTBlocks.fence;
				fence2Meta = 13;
				fenceGate2Block = GOTBlocks.fenceGateLarch;
				woodBeam2Block = GOTBlocks.woodBeam3;
				break;
			default:
				log2Block = Blocks.log;
				log2Meta = 1;
				plank2Block = Blocks.planks;
				plank2Meta = 1;
				plank2SlabBlock = Blocks.wooden_slab;
				plank2SlabMeta = 1;
				plank2StairBlock = Blocks.spruce_stairs;
				fence2Block = Blocks.fence;
				fence2Meta = 1;
				fenceGate2Block = GOTBlocks.fenceGateSpruce;
				woodBeam2Block = GOTBlocks.woodBeamV1;
				break;
		}
		woodBeam2Meta = 1;
		if (oneWoodType() && random.nextInt(3) == 0) {
			logBlock = log2Block;
			logMeta = log2Meta;
			plankBlock = plank2Block;
			plankMeta = plank2Meta;
			plankSlabBlock = plank2SlabBlock;
			plankSlabMeta = plank2SlabMeta;
			plankStairBlock = plank2StairBlock;
			fenceBlock = fence2Block;
			fenceMeta = fence2Meta;
			fenceGateBlock = fenceGate2Block;
			woodBeamBlock = woodBeam2Block;
			woodBeamMeta = woodBeam2Meta;
		}
		woodBeamIbbenBlock = GOTBlocks.woodBeam1;
		woodBeamIbbenMeta = 0;
		woodBeamIbbenGoldBlock = GOTBlocks.woodBeam1;
		woodBeamIbbenGoldMeta = 1;
		roofBlock = GOTBlocks.thatch;
		roofMeta = 0;
		roofSlabBlock = GOTBlocks.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = GOTBlocks.stairsThatch;
		barsBlock = random.nextBoolean() ? Blocks.iron_bars : GOTBlocks.bronzeBars;
		bedBlock = GOTBlocks.strawBed;
		gateBlock = GOTBlocks.gateWooden;
		plateBlock = random.nextBoolean() ? GOTBlocks.ceramicPlate : GOTBlocks.woodPlate;
		carpetBlock = Blocks.carpet;
		carpetMeta = 13;
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
				case 4:
					cropBlock = GOTBlocks.turnipCrop;
					cropMeta = 7;
					seedItem = GOTItems.turnip;
					break;
			}
		}
		bannerType = GOTItemBanner.BannerType.IBBEN;
	}
}