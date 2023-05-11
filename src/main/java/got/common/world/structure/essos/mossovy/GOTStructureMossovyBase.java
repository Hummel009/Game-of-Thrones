package got.common.world.structure.essos.mossovy;

import got.common.database.GOTRegistry;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

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

	public static Block getRandomPieBlock(Random random) {
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
		return GOTRegistry.appleCrumble;
	}

	public ItemStack getRandomBreeWeapon(Random random) {
		ItemStack[] items = {new ItemStack(Items.iron_sword), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.ironPike), new ItemStack(GOTRegistry.rollingPin)};
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomTavernItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTRegistry.rollingPin), new ItemStack(GOTRegistry.mug), new ItemStack(GOTRegistry.ceramicMug), new ItemStack(Items.bow), new ItemStack(Items.wooden_axe), new ItemStack(Items.fishing_rod), new ItemStack(Items.feather)};
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
			setBlockAndMetadata(world, i, j, k, GOTRegistry.dirtPath, 0);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		brickBlock = GOTRegistry.cobblebrick;
		brickMeta = 0;
		brick2Block = Blocks.stonebrick;
		brick2Meta = 0;
		brick2SlabBlock = Blocks.stone_slab;
		brick2SlabMeta = 5;
		brick2StairBlock = Blocks.stone_brick_stairs;
		brick2WallBlock = GOTRegistry.wallStoneV;
		brick2WallMeta = 1;
		floorBlock = Blocks.cobblestone;
		floorMeta = 0;
		stoneWallBlock = Blocks.cobblestone_wall;
		stoneWallMeta = 0;
		woodBeamBlock = GOTRegistry.woodBeamV1;
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
				doorBlock = Blocks.wooden_door;
				trapdoorBlock = Blocks.trapdoor;
				beamBlock = GOTRegistry.woodBeamV1;
				beamMeta = 0;
				break;
			case 1:
				plankBlock = GOTRegistry.planks1;
				plankMeta = 9;
				plankSlabBlock = GOTRegistry.woodSlabSingle2;
				plankSlabMeta = 1;
				plankStairBlock = GOTRegistry.stairsBeech;
				fenceBlock = GOTRegistry.fence;
				fenceMeta = 9;
				fenceGateBlock = GOTRegistry.fenceGateBeech;
				doorBlock = GOTRegistry.doorBeech;
				trapdoorBlock = GOTRegistry.trapdoorBeech;
				beamBlock = GOTRegistry.woodBeam2;
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
				fenceGateBlock = GOTRegistry.fenceGateBirch;
				doorBlock = GOTRegistry.doorBirch;
				trapdoorBlock = GOTRegistry.trapdoorBirch;
				beamBlock = GOTRegistry.woodBeamV1;
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
				fenceGateBlock = GOTRegistry.fenceGateSpruce;
				doorBlock = GOTRegistry.doorSpruce;
				trapdoorBlock = GOTRegistry.trapdoorSpruce;
				beamBlock = GOTRegistry.woodBeamV1;
				beamMeta = 1;
				break;
			case 4:
				woodBlock = GOTRegistry.wood4;
				woodMeta = 0;
				plankBlock = GOTRegistry.planks2;
				plankMeta = 0;
				plankSlabBlock = GOTRegistry.woodSlabSingle3;
				plankSlabMeta = 0;
				plankStairBlock = GOTRegistry.stairsChestnut;
				fenceBlock = GOTRegistry.fence2;
				fenceMeta = 0;
				fenceGateBlock = GOTRegistry.fenceGateChestnut;
				doorBlock = GOTRegistry.doorChestnut;
				trapdoorBlock = GOTRegistry.trapdoorChestnut;
				beamBlock = GOTRegistry.woodBeam4;
				beamMeta = 0;
				break;
			case 5:
				woodBlock = GOTRegistry.wood3;
				woodMeta = 0;
				plankBlock = GOTRegistry.planks1;
				plankMeta = 12;
				plankSlabBlock = GOTRegistry.woodSlabSingle2;
				plankSlabMeta = 4;
				plankStairBlock = GOTRegistry.stairsMaple;
				fenceBlock = GOTRegistry.fence;
				fenceMeta = 12;
				fenceGateBlock = GOTRegistry.fenceGateMaple;
				doorBlock = GOTRegistry.doorMaple;
				trapdoorBlock = GOTRegistry.trapdoorMaple;
				beamBlock = GOTRegistry.woodBeam3;
				beamMeta = 0;
				break;
			case 6:
				woodBlock = GOTRegistry.wood7;
				woodMeta = 0;
				plankBlock = GOTRegistry.planks2;
				plankMeta = 12;
				plankSlabBlock = GOTRegistry.woodSlabSingle4;
				plankSlabMeta = 4;
				plankStairBlock = GOTRegistry.stairsAspen;
				fenceBlock = GOTRegistry.fence2;
				fenceMeta = 12;
				fenceGateBlock = GOTRegistry.fenceGateAspen;
				doorBlock = GOTRegistry.doorAspen;
				trapdoorBlock = GOTRegistry.trapdoorAspen;
				beamBlock = GOTRegistry.woodBeam7;
				beamMeta = 0;
				break;
			default:
				break;
		}
		doorBlock = GOTRegistry.doorBeech;
		trapdoorBlock = GOTRegistry.trapdoorBeech;
		roofBlock = GOTRegistry.thatch;
		roofMeta = 0;
		roofSlabBlock = GOTRegistry.slabSingleThatch;
		roofSlabMeta = 0;
		roofStairBlock = GOTRegistry.stairsThatch;
		carpetBlock = Blocks.carpet;
		carpetMeta = 12;
		bedBlock = GOTRegistry.strawBed;
		tableBlock = GOTRegistry.tableMossovy;
	}
}
