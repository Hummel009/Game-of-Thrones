package got.common.world.structure.essos.lhazar;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class GOTStructureLhazarBase extends GOTStructureBase {
	protected Block brickBlock;
	protected int brickMeta;
	protected Block brickStairBlock;
	protected Block brickWallBlock;
	protected int brickWallMeta;
	protected Block woodBlock;
	protected int woodMeta;
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
	protected Block plank2Block;
	protected int plank2Meta;
	protected Block plank2SlabBlock;
	protected int plank2SlabMeta;
	protected Block plank2StairBlock;
	protected Block beam2Block;
	protected int beam2Meta;
	protected Block roofBlock;
	protected int roofMeta;
	protected Block roofSlabBlock;
	protected int roofSlabMeta;
	protected Block roofStairBlock;
	protected Block flagBlock;
	protected int flagMeta;
	protected Block boneBlock;
	protected int boneMeta;
	protected Block boneWallBlock;
	protected int boneWallMeta;
	protected Block bedBlock;

	protected GOTStructureLhazarBase(boolean flag) {
		super(flag);
	}

	protected static ItemStack getRandomlhazarWeapon(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.lhazarSword), new ItemStack(GOTItems.lhazarSword), new ItemStack(GOTItems.lhazarDagger), new ItemStack(GOTItems.lhazarSpear), new ItemStack(GOTItems.lhazarClub)};
		return items[random.nextInt(items.length)].copy();
	}

	protected boolean canUseRedBrick() {
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (canUseRedBrick() && random.nextInt(3) == 0) {
			brickBlock = GOTBlocks.brick3;
			brickMeta = 13;
			brickStairBlock = GOTBlocks.stairsSandstoneBrickRed;
			brickWallBlock = GOTBlocks.wallStone3;
			brickWallMeta = 4;
		} else {
			brickBlock = GOTBlocks.brick1;
			brickMeta = 15;
			brickStairBlock = GOTBlocks.stairsSandstoneBrick;
			brickWallBlock = GOTBlocks.wallStone1;
			brickWallMeta = 15;
		}
		if (random.nextInt(5) == 0) {
			woodBlock = GOTBlocks.wood9;
			woodMeta = 0;
			plankBlock = GOTBlocks.planks3;
			plankMeta = 4;
			plankSlabBlock = GOTBlocks.woodSlabSingle5;
			plankSlabMeta = 4;
			plankStairBlock = GOTBlocks.stairsDragon;
			fenceBlock = GOTBlocks.fence3;
			fenceMeta = 4;
			fenceGateBlock = GOTBlocks.fenceGateDragon;
			doorBlock = GOTBlocks.doorDragon;
			trapdoorBlock = GOTBlocks.trapdoorDragon;
			beamBlock = GOTBlocks.woodBeam9;
			beamMeta = 0;
		} else {
			woodBlock = GOTBlocks.wood8;
			woodMeta = 3;
			plankBlock = GOTBlocks.planks3;
			plankMeta = 3;
			plankSlabBlock = GOTBlocks.woodSlabSingle5;
			plankSlabMeta = 3;
			plankStairBlock = GOTBlocks.stairsPalm;
			fenceBlock = GOTBlocks.fence3;
			fenceMeta = 3;
			fenceGateBlock = GOTBlocks.fenceGatePalm;
			doorBlock = GOTBlocks.doorPalm;
			trapdoorBlock = GOTBlocks.trapdoorPalm;
			beamBlock = GOTBlocks.woodBeam8;
			beamMeta = 3;
		}
		int randomWood2 = random.nextInt(3);
		switch (randomWood2) {
			case 0:
				plank2Block = Blocks.planks;
				plank2Meta = 4;
				plank2SlabBlock = Blocks.wooden_slab;
				plank2SlabMeta = 4;
				plank2StairBlock = Blocks.acacia_stairs;
				beam2Block = GOTBlocks.woodBeamV2;
				beam2Meta = 0;
				break;
			case 1:
				plank2Block = GOTBlocks.planks1;
				plank2Meta = 14;
				plank2SlabBlock = GOTBlocks.woodSlabSingle2;
				plank2SlabMeta = 6;
				plank2StairBlock = GOTBlocks.stairsDatePalm;
				beam2Block = GOTBlocks.woodBeam4;
				beam2Meta = 2;
				break;
			case 2:
				plank2Block = GOTBlocks.planks3;
				plank2Meta = 4;
				plank2SlabBlock = GOTBlocks.woodSlabSingle5;
				plank2SlabMeta = 4;
				plank2StairBlock = GOTBlocks.stairsDragon;
				beam2Block = GOTBlocks.woodBeam9;
				beam2Meta = 0;
				break;
		}
		roofBlock = GOTBlocks.thatch;
		roofMeta = 1;
		roofSlabBlock = GOTBlocks.slabSingleThatch;
		roofSlabMeta = 1;
		roofStairBlock = GOTBlocks.stairsReed;
		flagBlock = Blocks.wool;
		flagMeta = 14;
		boneBlock = GOTBlocks.boneBlock;
		boneMeta = 0;
		boneWallBlock = GOTBlocks.wallBone;
		boneWallMeta = 0;
		bedBlock = GOTBlocks.strawBed;
	}
}