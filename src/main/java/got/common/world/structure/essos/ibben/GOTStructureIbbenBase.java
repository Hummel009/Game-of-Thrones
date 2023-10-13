package got.common.world.structure.essos.ibben;

import java.util.Random;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class GOTStructureIbbenBase extends GOTStructureBase {
	public Block rockBlock;
	public int rockMeta;
	public Block rockSlabBlock;
	public int rockSlabMeta;
	public Block rockSlabDoubleBlock;
	public int rockSlabDoubleMeta;
	public Block rockStairBlock;
	public Block rockWallBlock;
	public int rockWallMeta;
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brickCarvedBlock;
	public int brickCarvedMeta;
	public Block pillarBlock;
	public int pillarMeta;
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
	public Block log2Block;
	public int log2Meta;
	public Block plank2Block;
	public int plank2Meta;
	public Block plank2SlabBlock;
	public int plank2SlabMeta;
	public Block plank2StairBlock;
	public Block fence2Block;
	public int fence2Meta;
	public Block fenceGate2Block;
	public Block woodBeam2Block;
	public int woodBeam2Meta;
	public Block woodBeamRohanBlock;
	public int woodBeamRohanMeta;
	public Block woodBeamRohanGoldBlock;
	public int woodBeamRohanGoldMeta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block barsBlock;
	public Block bedBlock;
	public Block gateBlock;
	public Block plateBlock;
	public Block carpetBlock;
	public int carpetMeta;
	public Block cropBlock;
	public int cropMeta;
	public Item seedItem;
	public GOTItemBanner.BannerType bannerType;

	protected GOTStructureIbbenBase(boolean flag) {
		super(flag);
	}

	public Block getRandomCakeBlock(Random random) {
		int i = random.nextInt(3);
		switch (i) {
			case 0:
				return GOTBlocks.appleCrumble;
			case 1:
				return GOTBlocks.cherryPie;
			default:
				return GOTBlocks.berryPie;
		}
	}

	public ItemStack getRandomWeapon(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.stoneSpear), new ItemStack(GOTItems.ironSpear), new ItemStack(GOTItems.ironDagger), new ItemStack(GOTItems.ironDaggerPoisoned)};
		return items[random.nextInt(items.length)].copy();
	}

	public boolean oneWoodType() {
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
		rockStairBlock = GOTBlocks.stairsRhyolite;
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
		pillarBlock = GOTBlocks.pillar1;
		pillarMeta = 8;
		cobbleBlock = Blocks.cobblestone;
		cobbleMeta = 0;
		cobbleSlabBlock = Blocks.stone_slab;
		cobbleSlabMeta = 3;
		cobbleStairBlock = Blocks.stone_stairs;
		int randomWood = random.nextInt(6);
		switch (randomWood) {
			case 0:
			case 1:
			case 2:
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
			case 3:
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
			case 4:
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
		}
		int randomWood2 = random.nextInt(4);
		if (randomWood2 == 0 || randomWood2 == 1 || randomWood2 == 2) {
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
		} else {
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
		woodBeamRohanBlock = GOTBlocks.woodBeamS;
		woodBeamRohanMeta = 0;
		woodBeamRohanGoldBlock = GOTBlocks.woodBeamS;
		woodBeamRohanGoldMeta = 1;
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
				default:
					cropBlock = GOTBlocks.turnipCrop;
					cropMeta = 7;
					seedItem = GOTItems.turnip;
					break;
			}
		}
		bannerType = GOTItemBanner.BannerType.IBBEN;
	}
}
