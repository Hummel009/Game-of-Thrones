package got.common.world.structure.essos.mossovy;

import java.util.Random;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class GOTStructureMossovyBase extends GOTStructureBase {
	public Block woodBeamBlock;
	public int woodBeamMeta;
	public Block brickBlock;
	public int brickMeta;
	public Block brick2Block;
	public int brick2Meta;
	public Block brick2SlabBlock;
	public int brick2SlabMeta;
	public Block brick2StairBlock;
	public Block brick2WallBlock;
	public int brick2WallMeta;
	public Block floorBlock;
	public int floorMeta;
	public Block stoneWallBlock;
	public int stoneWallMeta;
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
	public Block trapdoorBlock;
	public Block beamBlock;
	public int beamMeta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block carpetBlock;
	public int carpetMeta;
	public Block bedBlock;
	public Block tableBlock;

	protected GOTStructureMossovyBase(boolean flag) {
		super(flag);
	}

	public ItemStack getRandomBreeWeapon(Random random) {
		ItemStack[] items = { new ItemStack(Items.iron_sword), new ItemStack(GOTItems.ironDagger), new ItemStack(GOTItems.ironPike), new ItemStack(GOTItems.rollingPin) };
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomTavernItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTItems.rollingPin), new ItemStack(GOTItems.mug), new ItemStack(GOTItems.ceramicMug), new ItemStack(Items.bow), new ItemStack(Items.wooden_axe), new ItemStack(Items.fishing_rod), new ItemStack(Items.feather) };
		return items[random.nextInt(items.length)].copy();
	}

	public void placeRandomFloor(World world, Random random, int i, int j, int k) {
		float randFloor = random.nextFloat();
		if (randFloor < 0.25f) {
			setBlockAndMetadata(world, i, j, k, Blocks.grass, 0);
		} else if (randFloor < 0.5f) {
			setBlockAndMetadata(world, i, j, k, Blocks.dirt, 1);
		} else if (randFloor < 0.75f) {
			setBlockAndMetadata(world, i, j, k, Blocks.gravel, 0);
		} else {
			setBlockAndMetadata(world, i, j, k, GOTBlocks.dirtPath, 0);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		brickBlock = GOTBlocks.cobblebrick;
		brickMeta = 0;
		brick2Block = Blocks.stonebrick;
		brick2Meta = 0;
		brick2SlabBlock = Blocks.stone_slab;
		brick2SlabMeta = 5;
		brick2StairBlock = Blocks.stone_brick_stairs;
		brick2WallBlock = GOTBlocks.wallStoneV;
		brick2WallMeta = 1;
		floorBlock = Blocks.cobblestone;
		floorMeta = 0;
		stoneWallBlock = Blocks.cobblestone_wall;
		stoneWallMeta = 0;
		woodBeamBlock = GOTBlocks.woodBeamV1;
		woodBeamMeta = 1;
		int randomWood = random.nextInt(7);
		switch (randomWood) {
		case 0:
			plankBlock = Blocks.planks;
			plankMeta = 0;
			plankSlabBlock = Blocks.wooden_slab;
			plankSlabMeta = 0;
			plankStairBlock = Blocks.oak_stairs;
			fenceBlock = Blocks.fence;
			fenceMeta = 0;
			fenceGateBlock = Blocks.fence_gate;
			beamBlock = GOTBlocks.woodBeamV1;
			beamMeta = 0;
			break;
		case 1:
			plankBlock = GOTBlocks.planks1;
			plankMeta = 9;
			plankSlabBlock = GOTBlocks.woodSlabSingle2;
			plankSlabMeta = 1;
			plankStairBlock = GOTBlocks.stairsBeech;
			fenceBlock = GOTBlocks.fence;
			fenceMeta = 9;
			fenceGateBlock = GOTBlocks.fenceGateBeech;
			beamBlock = GOTBlocks.woodBeam2;
			beamMeta = 1;
			break;
		case 2:
			plankBlock = Blocks.planks;
			plankMeta = 2;
			plankSlabBlock = Blocks.wooden_slab;
			plankSlabMeta = 2;
			plankStairBlock = Blocks.birch_stairs;
			fenceBlock = Blocks.fence;
			fenceMeta = 2;
			fenceGateBlock = GOTBlocks.fenceGateBirch;
			beamBlock = GOTBlocks.woodBeamV1;
			beamMeta = 2;
			break;
		case 3:
			plankBlock = Blocks.planks;
			plankMeta = 1;
			plankSlabBlock = Blocks.wooden_slab;
			plankSlabMeta = 1;
			plankStairBlock = Blocks.spruce_stairs;
			fenceBlock = Blocks.fence;
			fenceMeta = 1;
			fenceGateBlock = GOTBlocks.fenceGateSpruce;
			beamBlock = GOTBlocks.woodBeamV1;
			beamMeta = 1;
			break;
		case 4:
			woodBlock = GOTBlocks.wood4;
			woodMeta = 0;
			plankBlock = GOTBlocks.planks2;
			plankMeta = 0;
			plankSlabBlock = GOTBlocks.woodSlabSingle3;
			plankSlabMeta = 0;
			plankStairBlock = GOTBlocks.stairsChestnut;
			fenceBlock = GOTBlocks.fence2;
			fenceMeta = 0;
			fenceGateBlock = GOTBlocks.fenceGateChestnut;
			beamBlock = GOTBlocks.woodBeam4;
			beamMeta = 0;
			break;
		case 5:
			woodBlock = GOTBlocks.wood3;
			woodMeta = 0;
			plankBlock = GOTBlocks.planks1;
			plankMeta = 12;
			plankSlabBlock = GOTBlocks.woodSlabSingle2;
			plankSlabMeta = 4;
			plankStairBlock = GOTBlocks.stairsMaple;
			fenceBlock = GOTBlocks.fence;
			fenceMeta = 12;
			fenceGateBlock = GOTBlocks.fenceGateMaple;
			beamBlock = GOTBlocks.woodBeam3;
			beamMeta = 0;
			break;
		case 6:
			woodBlock = GOTBlocks.wood7;
			woodMeta = 0;
			plankBlock = GOTBlocks.planks2;
			plankMeta = 12;
			plankSlabBlock = GOTBlocks.woodSlabSingle4;
			plankSlabMeta = 4;
			plankStairBlock = GOTBlocks.stairsAspen;
			fenceBlock = GOTBlocks.fence2;
			fenceMeta = 12;
			fenceGateBlock = GOTBlocks.fenceGateAspen;
			beamBlock = GOTBlocks.woodBeam7;
			beamMeta = 0;
			break;
		default:
			break;
		}
		doorBlock = GOTBlocks.doorBeech;
		trapdoorBlock = GOTBlocks.trapdoorBeech;
		roofBlock = GOTBlocks.thatch;
		roofMeta = 0;
		roofSlabBlock = GOTBlocks.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = GOTBlocks.stairsThatch;
		carpetBlock = Blocks.carpet;
		carpetMeta = 12;
		bedBlock = GOTBlocks.strawBed;
		tableBlock = GOTBlocks.tableMossovy;
	}

	public static Block getRandomPieBlock(Random random) {
		int i = random.nextInt(3);
		switch (i) {
		case 0:
			return GOTBlocks.appleCrumble;
		case 1:
			return GOTBlocks.cherryPie;
		case 2:
			return GOTBlocks.berryPie;
		default:
			break;
		}
		return GOTBlocks.appleCrumble;
	}
}
