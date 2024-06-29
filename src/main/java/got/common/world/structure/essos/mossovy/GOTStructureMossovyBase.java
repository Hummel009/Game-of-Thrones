package got.common.world.structure.essos.mossovy;

import got.common.database.GOTBlocks;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.Random;

public abstract class GOTStructureMossovyBase extends GOTStructureBase {
	protected Block woodBeamBlock;
	protected int woodBeamMeta;
	protected Block brickBlock;
	protected int brickMeta;
	protected Block brick2Block;
	protected int brick2Meta;
	protected Block brick2SlabBlock;
	protected int brick2SlabMeta;
	protected Block brick2StairBlock;
	protected Block brick2WallBlock;
	protected int brick2WallMeta;
	protected Block floorBlock;
	protected int floorMeta;
	protected Block stoneWallBlock;
	protected int stoneWallMeta;
	protected Block plankBlock;
	protected int plankMeta;
	protected Block plankSlabBlock;
	protected int plankSlabMeta;
	protected Block plankStairBlock;
	protected Block fenceBlock;
	protected int fenceMeta;
	protected Block fenceGateBlock;
	protected Block doorBlock;
	protected Block trapdoorBlock;
	protected Block beamBlock;
	protected int beamMeta;
	protected Block roofBlock;
	protected int roofMeta;
	protected Block roofSlabBlock;
	protected int roofSlabMeta;
	protected Block roofStairBlock;
	protected Block carpetBlock;
	protected int carpetMeta;
	protected Block bedBlock;
	protected Block tableBlock;

	protected GOTStructureMossovyBase(boolean flag) {
		super(flag);
	}

	protected static Block getRandomPieBlock(Random random) {
		int i = random.nextInt(3);
		switch (i) {
			case 0:
				return GOTBlocks.cherryPie;
			case 1:
				return GOTBlocks.berryPie;
			case 2:
				return GOTBlocks.appleCrumble;
			default:
				return null;
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
}