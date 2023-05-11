package got.common.world.structure.essos.ibben;

import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

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

	public GOTStructureIbbenBase(boolean flag) {
		super(flag);
	}

	public Block getRandomCakeBlock(Random random) {
		int i = random.nextInt(3);
		switch (i) {
			case 0:
				return GOTRegistry.appleCrumble;
			case 1:
				return GOTRegistry.cherryPie;
			case 2:
				return GOTRegistry.berryPie;
			default:
				break;
		}
		return null;
	}

	public ItemStack getRandomWeapon(Random random) {
		ItemStack[] items = {new ItemStack(GOTRegistry.stoneSpear), new ItemStack(GOTRegistry.ironSpear), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.ironDaggerPoisoned)};
		return items[random.nextInt(items.length)].copy();
	}

	public boolean oneWoodType() {
		return false;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		rockBlock = GOTRegistry.rock;
		rockMeta = 2;
		rockSlabBlock = GOTRegistry.slabSingle2;
		rockSlabMeta = 1;
		rockSlabDoubleBlock = GOTRegistry.slabDouble2;
		rockSlabDoubleMeta = 1;
		rockStairBlock = GOTRegistry.stairsRhyolite;
		rockWallBlock = GOTRegistry.wallStone1;
		rockWallMeta = 8;
		brickBlock = GOTRegistry.brick1;
		brickMeta = 4;
		brickSlabBlock = GOTRegistry.slabSingle1;
		brickSlabMeta = 6;
		brickStairBlock = GOTRegistry.stairsRhyoliteBrick;
		brickWallBlock = GOTRegistry.wallStone1;
		brickWallMeta = 6;
		brickCarvedBlock = GOTRegistry.brick5;
		brickCarvedMeta = 3;
		pillarBlock = GOTRegistry.pillar1;
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
				woodBeamBlock = GOTRegistry.woodBeamV1;
				woodBeamMeta = 0;
				doorBlock = Blocks.wooden_door;
				break;
			case 3:
				logBlock = GOTRegistry.wood2;
				logMeta = 1;
				plankBlock = GOTRegistry.planks1;
				plankMeta = 9;
				plankSlabBlock = GOTRegistry.woodSlabSingle2;
				plankSlabMeta = 1;
				plankStairBlock = GOTRegistry.stairsBeech;
				fenceBlock = GOTRegistry.fence;
				fenceMeta = 9;
				fenceGateBlock = GOTRegistry.fenceGateBeech;
				woodBeamBlock = GOTRegistry.woodBeam2;
				woodBeamMeta = 1;
				doorBlock = GOTRegistry.doorBeech;
				break;
			case 4:
				logBlock = GOTRegistry.fruitWood;
				logMeta = 0;
				plankBlock = GOTRegistry.planks1;
				plankMeta = 4;
				plankSlabBlock = GOTRegistry.woodSlabSingle1;
				plankSlabMeta = 4;
				plankStairBlock = GOTRegistry.stairsApple;
				fenceBlock = GOTRegistry.fence;
				fenceMeta = 4;
				fenceGateBlock = GOTRegistry.fenceGateApple;
				woodBeamBlock = GOTRegistry.woodBeamFruit;
				woodBeamMeta = 0;
				doorBlock = GOTRegistry.doorApple;
				break;
			case 5:
				logBlock = GOTRegistry.wood5;
				logMeta = 0;
				plankBlock = GOTRegistry.planks2;
				plankMeta = 4;
				plankSlabBlock = GOTRegistry.woodSlabSingle3;
				plankSlabMeta = 4;
				plankStairBlock = GOTRegistry.stairsPine;
				fenceBlock = GOTRegistry.fence2;
				fenceMeta = 4;
				fenceGateBlock = GOTRegistry.fenceGatePine;
				woodBeamBlock = GOTRegistry.woodBeam5;
				woodBeamMeta = 0;
				doorBlock = GOTRegistry.doorPine;
				break;
			default:
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
			fenceGate2Block = GOTRegistry.fenceGateSpruce;
			woodBeam2Block = GOTRegistry.woodBeamV1;
			woodBeam2Meta = 1;
		} else if (randomWood2 == 3) {
			log2Block = GOTRegistry.wood3;
			log2Meta = 1;
			plank2Block = GOTRegistry.planks1;
			plank2Meta = 13;
			plank2SlabBlock = GOTRegistry.woodSlabSingle2;
			plank2SlabMeta = 5;
			plank2StairBlock = GOTRegistry.stairsLarch;
			fence2Block = GOTRegistry.fence;
			fence2Meta = 13;
			fenceGate2Block = GOTRegistry.fenceGateLarch;
			woodBeam2Block = GOTRegistry.woodBeam3;
			woodBeam2Meta = 1;
		}
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
		woodBeamRohanBlock = GOTRegistry.woodBeamS;
		woodBeamRohanMeta = 0;
		woodBeamRohanGoldBlock = GOTRegistry.woodBeamS;
		woodBeamRohanGoldMeta = 1;
		roofBlock = GOTRegistry.thatch;
		roofMeta = 0;
		roofSlabBlock = GOTRegistry.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = GOTRegistry.stairsThatch;
		barsBlock = random.nextBoolean() ? Blocks.iron_bars : GOTRegistry.bronzeBars;
		bedBlock = GOTRegistry.strawBed;
		gateBlock = GOTRegistry.gateWooden;
		plateBlock = random.nextBoolean() ? GOTRegistry.ceramicPlateBlock : GOTRegistry.woodPlateBlock;
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
		bannerType = GOTItemBanner.BannerType.IBBEN;
	}
}
