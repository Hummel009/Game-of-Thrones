package got.common.world.structure.westeros.crownlands;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;

public abstract class GOTStructureCrownlandsBase extends GOTStructureBase {
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
	public Block brickMossyBlock;
	public int brickMossyMeta;
	public Block brickMossySlabBlock;
	public int brickMossySlabMeta;
	public Block brickMossyStairBlock;
	public Block brickMossyWallBlock;
	public int brickMossyWallMeta;
	public Block brickCrackedBlock;
	public int brickCrackedMeta;
	public Block brickCrackedSlabBlock;
	public int brickCrackedSlabMeta;
	public Block brickCrackedStairBlock;
	public Block brickCrackedWallBlock;
	public int brickCrackedWallMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block brick2Block;
	public int brick2Meta;
	public Block brick2SlabBlock;
	public int brick2SlabMeta;
	public Block brick2StairBlock;
	public Block brick2WallBlock;
	public int brick2WallMeta;
	public Block pillar2Block;
	public int pillar2Meta;
	public Block cobbleBlock;
	public int cobbleMeta;
	public Block cobbleSlabBlock;
	public int cobbleSlabMeta;
	public Block cobbleStairBlock;
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
	public Block bedBlock;
	public Block gateBlock;
	public Block plateBlock;
	public Block cropBlock;
	public int cropMeta;
	public Item seedItem;
	public Block tableBlock;

	public GOTStructureCrownlandsBase(boolean flag) {
		super(flag);
	}

	public ItemStack getFramedItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.westerosDagger), new ItemStack(GOTRegistry.westerosSpear), new ItemStack(GOTRegistry.westerosBow), new ItemStack(Items.arrow), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.ironPike), new ItemStack(GOTRegistry.ironCrossbow), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing) };
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		tableBlock = GOTRegistry.tableCrownlands;
		rockBlock = GOTRegistry.rock;
		rockMeta = 1;
		rockSlabBlock = GOTRegistry.slabSingle1;
		rockSlabMeta = 2;
		rockSlabDoubleBlock = GOTRegistry.slabDouble1;
		rockSlabDoubleMeta = 2;
		rockStairBlock = GOTRegistry.stairsAndesite;
		rockWallBlock = GOTRegistry.wallStone1;
		rockWallMeta = 2;
		brickBlock = GOTRegistry.brick1;
		brickMeta = 1;
		brickSlabBlock = GOTRegistry.slabSingle1;
		brickSlabMeta = 3;
		brickStairBlock = GOTRegistry.stairsAndesiteBrick;
		brickWallBlock = GOTRegistry.wallStone1;
		brickWallMeta = 3;
		brickMossyBlock = GOTRegistry.brick1;
		brickMossyMeta = 2;
		brickMossySlabBlock = GOTRegistry.slabSingle1;
		brickMossySlabMeta = 4;
		brickMossyStairBlock = GOTRegistry.stairsAndesiteBrickMossy;
		brickMossyWallBlock = GOTRegistry.wallStone1;
		brickMossyWallMeta = 4;
		brickCrackedBlock = GOTRegistry.brick1;
		brickCrackedMeta = 3;
		brickCrackedSlabBlock = GOTRegistry.slabSingle1;
		brickCrackedSlabMeta = 5;
		brickCrackedStairBlock = GOTRegistry.stairsAndesiteBrickCracked;
		brickCrackedWallBlock = GOTRegistry.wallStone1;
		brickCrackedWallMeta = 5;
		pillarBlock = GOTRegistry.pillar1;
		pillarMeta = 6;
		brick2Block = GOTRegistry.brick2;
		brick2Meta = 11;
		brick2SlabBlock = GOTRegistry.slabSingle5;
		brick2SlabMeta = 3;
		brick2StairBlock = GOTRegistry.stairsBasaltWesterosBrick;
		brick2WallBlock = GOTRegistry.wallStone2;
		brick2WallMeta = 10;
		pillar2Block = GOTRegistry.pillar1;
		pillar2Meta = 9;
		cobbleBlock = Blocks.cobblestone;
		cobbleMeta = 0;
		cobbleSlabBlock = Blocks.stone_slab;
		cobbleSlabMeta = 3;
		cobbleStairBlock = Blocks.stone_stairs;
		int randomWood = random.nextInt(7);
		switch (randomWood) {
		case 0:
		case 1:
		case 2:
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
			plankBlock = GOTRegistry.planks2;
			plankMeta = 2;
			plankSlabBlock = GOTRegistry.woodSlabSingle3;
			plankSlabMeta = 2;
			plankStairBlock = GOTRegistry.stairsCedar;
			fenceBlock = GOTRegistry.fence2;
			fenceMeta = 2;
			fenceGateBlock = GOTRegistry.fenceGateCedar;
			woodBeamBlock = GOTRegistry.woodBeam4;
			woodBeamMeta = 2;
			doorBlock = GOTRegistry.doorCedar;
			break;
		case 5:
			plankBlock = GOTRegistry.planks1;
			plankMeta = 8;
			plankSlabBlock = GOTRegistry.woodSlabSingle2;
			plankSlabMeta = 0;
			plankStairBlock = GOTRegistry.stairsAramant;
			fenceBlock = GOTRegistry.fence;
			fenceMeta = 8;
			fenceGateBlock = GOTRegistry.fenceGateAramant;
			woodBeamBlock = GOTRegistry.woodBeam2;
			woodBeamMeta = 0;
			doorBlock = GOTRegistry.doorAramant;
			break;
		case 6:
			plankBlock = Blocks.planks;
			plankMeta = 2;
			plankSlabBlock = Blocks.wooden_slab;
			plankSlabMeta = 2;
			plankStairBlock = Blocks.birch_stairs;
			fenceBlock = Blocks.fence;
			fenceMeta = 2;
			fenceGateBlock = GOTRegistry.fenceGateBirch;
			woodBeamBlock = GOTRegistry.woodBeamV1;
			woodBeamMeta = 2;
			doorBlock = GOTRegistry.doorBirch;
			break;
		default:
			break;
		}
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
		barsBlock = Blocks.iron_bars;
		bedBlock = GOTRegistry.strawBed;
		gateBlock = GOTRegistry.gateWesteros;
		plateBlock = GOTRegistry.ceramicPlateBlock;
		if (random.nextBoolean()) {
			cropBlock = Blocks.wheat;
			cropMeta = 7;
			seedItem = Items.wheat_seeds;
		} else {
			int randomCrop = random.nextInt(6);
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
				cropBlock = GOTRegistry.cornStalk;
				cropMeta = 0;
				seedItem = Item.getItemFromBlock(GOTRegistry.cornStalk);
				break;
			case 4:
				cropBlock = GOTRegistry.leekCrop;
				cropMeta = 7;
				seedItem = GOTRegistry.leek;
				break;
			case 5:
				cropBlock = GOTRegistry.turnipCrop;
				cropMeta = 7;
				seedItem = GOTRegistry.turnip;
				break;
			default:
				break;
			}
		}
	}
}