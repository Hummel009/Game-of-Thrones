package got.common.world.structure.essos.ghiscar;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;

public abstract class GOTStructureGhiscarBase extends GOTStructureBase {
	public Block stoneBlock;
	public int stoneMeta;
	public Block stoneStairBlock;
	public Block stoneWallBlock;
	public int stoneWallMeta;
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block brick2Block;
	public int brick2Meta;
	public Block brick2SlabBlock;
	public int brick2SlabMeta;
	public Block brick2StairBlock;
	public Block brick2WallBlock;
	public int brick2WallMeta;
	public Block brick2CarvedBlock;
	public int brick2CarvedMeta;
	public Block pillar2Block;
	public int pillar2Meta;
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
	public Block woodBeamBlock;
	public int woodBeamMeta;
	public int woodBeamMeta4;
	public int woodBeamMeta8;
	public Block doorBlock;
	public Block plank2Block;
	public int plank2Meta;
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block gateMetalBlock;
	public Block bedBlock;
	public Block tableBlock;
	public Block cropBlock;
	public GOTItemBanner.BannerType bannerType;

	public GOTStructureGhiscarBase(boolean flag) {
		super(flag);
	}

	public boolean canUseRedBricks() {
		return true;
	}

	public boolean forceCedarWood() {
		return false;
	}

	public ItemStack getRandomItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.essosSword), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosSpear), new ItemStack(GOTRegistry.essosPike), new ItemStack(GOTRegistry.essosPolearm), new ItemStack(GOTRegistry.essosHammer), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTRegistry.gobletGold), new ItemStack(GOTRegistry.gobletSilver), new ItemStack(GOTRegistry.gobletCopper), new ItemStack(GOTRegistry.mug), new ItemStack(GOTRegistry.ceramicMug), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing), new ItemStack(GOTRegistry.doubleFlower, 1, 2), new ItemStack(GOTRegistry.doubleFlower, 1, 3), new ItemStack(GOTRegistry.gemsbokHorn), new ItemStack(GOTRegistry.lionFur) };
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomWeapon(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.essosSword), new ItemStack(GOTRegistry.essosDagger), new ItemStack(GOTRegistry.essosSpear), new ItemStack(GOTRegistry.essosPike), new ItemStack(GOTRegistry.essosPolearm), new ItemStack(GOTRegistry.essosHammer) };
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		stoneBlock = Blocks.sandstone;
		stoneMeta = 0;
		stoneStairBlock = Blocks.sandstone_stairs;
		stoneWallBlock = GOTRegistry.wallStoneV;
		stoneWallMeta = 4;
		if (canUseRedBricks() && random.nextInt(4) == 0) {
			brickBlock = GOTRegistry.brick3;
			brickMeta = 13;
			brickSlabBlock = GOTRegistry.slabSingle7;
			brickSlabMeta = 2;
			brickStairBlock = GOTRegistry.stairsSandstoneBrickRed;
			brickWallBlock = GOTRegistry.wallStone3;
			brickWallMeta = 4;
			pillarBlock = GOTRegistry.pillar1;
			pillarMeta = 15;
		} else {
			brickBlock = GOTRegistry.brick1;
			brickMeta = 15;
			brickSlabBlock = GOTRegistry.slabSingle4;
			brickSlabMeta = 0;
			brickStairBlock = GOTRegistry.stairsSandstoneBrick;
			brickWallBlock = GOTRegistry.wallStone1;
			brickWallMeta = 15;
			pillarBlock = GOTRegistry.pillar1;
			pillarMeta = 5;
		}
		brick2Block = GOTRegistry.brick3;
		brick2Meta = 13;
		brick2SlabBlock = GOTRegistry.slabSingle7;
		brick2SlabMeta = 2;
		brick2StairBlock = GOTRegistry.stairsSandstoneBrickRed;
		brick2WallBlock = GOTRegistry.wallStone3;
		brick2WallMeta = 4;
		brick2CarvedBlock = GOTRegistry.brick3;
		brick2CarvedMeta = 15;
		pillar2Block = GOTRegistry.pillar1;
		pillar2Meta = 15;
		roofBlock = GOTRegistry.thatch;
		roofMeta = 1;
		roofSlabBlock = GOTRegistry.slabSingleThatch;
		roofSlabMeta = 1;
		roofStairBlock = GOTRegistry.stairsReed;
		if (random.nextBoolean() || forceCedarWood()) {
			woodBlock = GOTRegistry.wood4;
			woodMeta = 2;
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
		} else {
			int randomWood = random.nextInt(3);
			switch (randomWood) {
			case 0:
				woodBlock = GOTRegistry.wood6;
				woodMeta = 3;
				plankBlock = GOTRegistry.planks2;
				plankMeta = 11;
				plankSlabBlock = GOTRegistry.woodSlabSingle4;
				plankSlabMeta = 3;
				plankStairBlock = GOTRegistry.stairsOlive;
				fenceBlock = GOTRegistry.fence2;
				fenceMeta = 11;
				fenceGateBlock = GOTRegistry.fenceGateOlive;
				woodBeamBlock = GOTRegistry.woodBeam6;
				woodBeamMeta = 3;
				doorBlock = GOTRegistry.doorOlive;
				break;
			case 1:
				woodBlock = GOTRegistry.wood3;
				woodMeta = 2;
				plankBlock = GOTRegistry.planks1;
				plankMeta = 14;
				plankSlabBlock = GOTRegistry.woodSlabSingle2;
				plankSlabMeta = 6;
				plankStairBlock = GOTRegistry.stairsDatePalm;
				fenceBlock = GOTRegistry.fence;
				fenceMeta = 14;
				fenceGateBlock = GOTRegistry.fenceGateDatePalm;
				woodBeamBlock = GOTRegistry.woodBeam3;
				woodBeamMeta = 2;
				doorBlock = GOTRegistry.doorDatePalm;
				break;
			case 2:
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
				woodBeamBlock = GOTRegistry.woodBeam8;
				woodBeamMeta = 3;
				doorBlock = GOTRegistry.doorPalm;
				break;
			default:
				break;
			}
		}
		woodBeamMeta4 = woodBeamMeta | 4;
		woodBeamMeta8 = woodBeamMeta | 8;
		plank2Block = GOTRegistry.planks2;
		plank2Meta = 11;
		gateMetalBlock = GOTRegistry.gateBronzeBars;
		bedBlock = GOTRegistry.strawBed;
		tableBlock = GOTRegistry.tableGhiscar;
		if (random.nextBoolean()) {
			cropBlock = Blocks.wheat;
		} else {
			int randomCrop = random.nextInt(3);
			if (randomCrop == 0 || randomCrop == 1) {
				cropBlock = Blocks.carrots;
			} else if (randomCrop == 2) {
				cropBlock = GOTRegistry.lettuceCrop;
			}
		}
		bannerType = GOTItemBanner.BannerType.BRAAVOS;
	}
}
