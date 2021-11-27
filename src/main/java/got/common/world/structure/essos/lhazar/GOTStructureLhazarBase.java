package got.common.world.structure.essos.lhazar;

import java.util.Random;

import got.common.database.GOTRegistry;
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

	public GOTStructureLhazarBase(boolean flag) {
		super(flag);
	}

	public boolean canUseRedBrick() {
		return true;
	}

	public ItemStack getRandomlhazarWeapon(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.lhazarSword), new ItemStack(GOTRegistry.lhazarSword), new ItemStack(GOTRegistry.lhazarDagger), new ItemStack(GOTRegistry.lhazarSpear), new ItemStack(GOTRegistry.lhazarClub) };
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (canUseRedBrick() && random.nextInt(3) == 0) {
			brickBlock = GOTRegistry.brick3;
			brickMeta = 13;
			brickSlabBlock = GOTRegistry.slabSingle7;
			brickSlabMeta = 2;
			brickStairBlock = GOTRegistry.stairsSandstoneBrickRed;
			brickWallBlock = GOTRegistry.wallStone3;
			brickWallMeta = 4;
		} else {
			brickBlock = GOTRegistry.brick1;
			brickMeta = 15;
			brickSlabBlock = GOTRegistry.slabSingle4;
			brickSlabMeta = 0;
			brickStairBlock = GOTRegistry.stairsSandstoneBrick;
			brickWallBlock = GOTRegistry.wallStone1;
			brickWallMeta = 15;
		}
		brick2Block = GOTRegistry.brick3;
		brick2Meta = 13;
		if (random.nextInt(5) == 0) {
			woodBlock = GOTRegistry.wood9;
			woodMeta = 0;
			plankBlock = GOTRegistry.planks3;
			plankMeta = 4;
			plankSlabBlock = GOTRegistry.woodSlabSingle5;
			plankSlabMeta = 4;
			plankStairBlock = GOTRegistry.stairsDragon;
			fenceBlock = GOTRegistry.fence3;
			fenceMeta = 4;
			fenceGateBlock = GOTRegistry.fenceGateDragon;
			doorBlock = GOTRegistry.doorDragon;
			trapdoorBlock = GOTRegistry.trapdoorDragon;
			beamBlock = GOTRegistry.woodBeam9;
			beamMeta = 0;
		} else {
			woodBlock = GOTRegistry.wood8;
			woodMeta = 3;
			plankBlock = GOTRegistry.planks3;
			plankMeta = 3;
			plankSlabBlock = GOTRegistry.woodSlabSingle5;
			plankSlabMeta = 3;
			plankStairBlock = GOTRegistry.stairsPalm;
			fenceBlock = GOTRegistry.fence3;
			fenceMeta = 3;
			fenceGateBlock = GOTRegistry.fenceGatePalm;
			doorBlock = GOTRegistry.doorPalm;
			trapdoorBlock = GOTRegistry.trapdoorPalm;
			beamBlock = GOTRegistry.woodBeam8;
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
			beam2Block = GOTRegistry.woodBeamV2;
			beam2Meta = 0;
			break;
		case 1:
			plank2Block = GOTRegistry.planks1;
			plank2Meta = 14;
			plank2SlabBlock = GOTRegistry.woodSlabSingle2;
			plank2SlabMeta = 6;
			plank2StairBlock = GOTRegistry.stairsDatePalm;
			beam2Block = GOTRegistry.woodBeam4;
			beam2Meta = 2;
			break;
		case 2:
			plank2Block = GOTRegistry.planks3;
			plank2Meta = 4;
			plank2SlabBlock = GOTRegistry.woodSlabSingle5;
			plank2SlabMeta = 4;
			plank2StairBlock = GOTRegistry.stairsDragon;
			beam2Block = GOTRegistry.woodBeam9;
			beam2Meta = 0;
			break;
		default:
			break;
		}
		roofBlock = GOTRegistry.thatch;
		roofMeta = 1;
		roofSlabBlock = GOTRegistry.slabSingleThatch;
		roofSlabMeta = 1;
		roofStairBlock = GOTRegistry.stairsReed;
		flagBlock = Blocks.wool;
		flagMeta = 14;
		boneBlock = GOTRegistry.boneBlock;
		boneMeta = 0;
		boneWallBlock = GOTRegistry.wallBone;
		boneWallMeta = 0;
		bedBlock = GOTRegistry.strawBed;
	}
}
