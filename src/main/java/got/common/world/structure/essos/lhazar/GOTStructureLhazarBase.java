package got.common.world.structure.essos.lhazar;

import java.util.Random;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public abstract class GOTStructureLhazarBase extends GOTStructureBase {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brick2Block;
	public int brick2Meta;
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
	public Block plank2Block;
	public int plank2Meta;
	public Block plank2SlabBlock;
	public int plank2SlabMeta;
	public Block plank2StairBlock;
	public Block beam2Block;
	public int beam2Meta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block flagBlock;
	public int flagMeta;
	public Block boneBlock;
	public int boneMeta;
	public Block boneWallBlock;
	public int boneWallMeta;
	public Block bedBlock;

	protected GOTStructureLhazarBase(boolean flag) {
		super(flag);
	}

	public boolean canUseRedBrick() {
		return true;
	}

	public ItemStack getRandomlhazarWeapon(Random random) {
		ItemStack[] items = { new ItemStack(GOTItems.lhazarSword), new ItemStack(GOTItems.lhazarSword), new ItemStack(GOTItems.lhazarDagger), new ItemStack(GOTItems.lhazarSpear), new ItemStack(GOTItems.lhazarClub) };
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (canUseRedBrick() && random.nextInt(3) == 0) {
			brickBlock = GOTBlocks.brick3;
			brickMeta = 13;
			brickSlabBlock = GOTBlocks.slabSingle7;
			brickSlabMeta = 2;
			brickStairBlock = GOTBlocks.stairsSandstoneBrickRed;
			brickWallBlock = GOTBlocks.wallStone3;
			brickWallMeta = 4;
		} else {
			brickBlock = GOTBlocks.brick1;
			brickMeta = 15;
			brickSlabBlock = GOTBlocks.slabSingle4;
			brickSlabMeta = 0;
			brickStairBlock = GOTBlocks.stairsSandstoneBrick;
			brickWallBlock = GOTBlocks.wallStone1;
			brickWallMeta = 15;
		}
		brick2Block = GOTBlocks.brick3;
		brick2Meta = 13;
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
		default:
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
