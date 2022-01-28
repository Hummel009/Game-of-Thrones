package got.common.world.structure.essos.yiti;

import java.util.Random;

import got.common.database.*;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;

public abstract class GOTStructureYiTiBase extends GOTStructureBase {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brickCarvedBlock;
	public int brickCarvedMeta;
	public Block brickFloweryBlock;
	public int brickFloweryMeta;
	public Block brickFlowerySlabBlock;
	public int brickFlowerySlabMeta;
	public Block brickGoldBlock;
	public int brickGoldMeta;
	public Block brickRedBlock;
	public int brickRedMeta;
	public Block brickRedSlabBlock;
	public int brickRedSlabMeta;
	public Block brickRedStairBlock;
	public Block brickRedWallBlock;
	public int brickRedWallMeta;
	public Block brickRedCarvedBlock;
	public int brickRedCarvedMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block pillarRedBlock;
	public int pillarRedMeta;
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
	public Block roofBlock;
	public int roofMeta;
	public Block roofSlabBlock;
	public int roofSlabMeta;
	public Block roofStairBlock;
	public Block roofWallBlock;
	public int roofWallMeta;
	public Block barsBlock;
	public Block tableBlock;
	public Block gateBlock;
	public Block bedBlock;
	public Block plateBlock;
	public Block cropBlock;
	public int cropMeta;
	public Item seedItem;
	public Block thatchBlock;
	public int thatchMeta;
	public Block thatchSlabBlock;
	public int thatchSlabMeta;
	public Block thatchStairBlock;
	public GOTItemBanner.BannerType bannerType;
	public GOTChestContents chestContents;

	public GOTStructureYiTiBase(boolean flag) {
		super(flag);
	}

	public ItemStack getFramedItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.yitiHelmet), new ItemStack(GOTRegistry.yitiChestplate), new ItemStack(GOTRegistry.yitiLeggings), new ItemStack(GOTRegistry.yitiBoots), new ItemStack(GOTRegistry.yitiHelmetSamurai), new ItemStack(GOTRegistry.yitiChestplateSamurai), new ItemStack(GOTRegistry.yitiLeggingsSamurai), new ItemStack(GOTRegistry.yitiBootsSamurai), new ItemStack(GOTRegistry.yitiDagger), new ItemStack(GOTRegistry.yitiSword), new ItemStack(GOTRegistry.yitiBattleaxe), new ItemStack(GOTRegistry.yitiSpear), new ItemStack(GOTRegistry.yitiBow), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTRegistry.gobletGold), new ItemStack(GOTRegistry.gobletSilver), new ItemStack(GOTRegistry.mug), new ItemStack(GOTRegistry.goldRing) };
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getWeaponItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.yitiSword), new ItemStack(GOTRegistry.yitiDagger), new ItemStack(GOTRegistry.yitiDaggerPoisoned), new ItemStack(GOTRegistry.yitiSpear), new ItemStack(GOTRegistry.yitiBattleaxe), new ItemStack(GOTRegistry.yitiPolearm), new ItemStack(GOTRegistry.yitiPike) };
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		thatchBlock = GOTRegistry.thatch;
		thatchMeta = 1;
		thatchSlabBlock = GOTRegistry.slabSingleThatch;
		thatchSlabMeta = 1;
		thatchStairBlock = GOTRegistry.stairsReed;
		brickBlock = GOTRegistry.brick5;
		brickMeta = 11;
		brickSlabBlock = GOTRegistry.slabSingle12;
		brickSlabMeta = 0;
		brickStairBlock = GOTRegistry.stairsYiTiBrick;
		brickWallBlock = GOTRegistry.wallStone3;
		brickWallMeta = 15;
		brickCarvedBlock = GOTRegistry.brick5;
		brickCarvedMeta = 12;
		brickFloweryBlock = GOTRegistry.brick5;
		brickFloweryMeta = 15;
		brickFlowerySlabBlock = GOTRegistry.slabSingle12;
		brickFlowerySlabMeta = 3;
		brickGoldBlock = GOTRegistry.brick6;
		brickGoldMeta = 0;
		brickRedBlock = GOTRegistry.brick6;
		brickRedMeta = 1;
		brickRedSlabBlock = GOTRegistry.slabSingle12;
		brickRedSlabMeta = 5;
		brickRedStairBlock = GOTRegistry.stairsYiTiBrickRed;
		brickRedWallBlock = GOTRegistry.wallStone4;
		brickRedWallMeta = 13;
		brickRedCarvedBlock = GOTRegistry.brick6;
		brickRedCarvedMeta = 2;
		pillarBlock = GOTRegistry.pillar2;
		pillarMeta = 8;
		pillarRedBlock = GOTRegistry.pillar2;
		pillarRedMeta = 9;
		if (random.nextBoolean()) {
			logBlock = GOTRegistry.wood8;
			logMeta = 1;
			plankBlock = GOTRegistry.planks3;
			plankMeta = 1;
			plankSlabBlock = GOTRegistry.woodSlabSingle5;
			plankSlabMeta = 1;
			plankStairBlock = GOTRegistry.stairsRedwood;
			fenceBlock = GOTRegistry.fence3;
			fenceMeta = 1;
			fenceGateBlock = GOTRegistry.fenceGateRedwood;
			woodBeamBlock = GOTRegistry.woodBeam8;
			woodBeamMeta = 1;
			doorBlock = GOTRegistry.doorRedwood;
		} else {
			int randomWood = random.nextInt(4);
			switch (randomWood) {
			case 0:
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
			case 1:
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
			case 2:
				logBlock = GOTRegistry.wood6;
				logMeta = 2;
				plankBlock = GOTRegistry.planks2;
				plankMeta = 10;
				plankSlabBlock = GOTRegistry.woodSlabSingle4;
				plankSlabMeta = 2;
				plankStairBlock = GOTRegistry.stairsCypress;
				fenceBlock = GOTRegistry.fence2;
				fenceMeta = 10;
				fenceGateBlock = GOTRegistry.fenceGateCypress;
				woodBeamBlock = GOTRegistry.woodBeam6;
				woodBeamMeta = 2;
				doorBlock = GOTRegistry.doorCypress;
				break;
			case 3:
				logBlock = GOTRegistry.wood6;
				logMeta = 3;
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
			default:
				break;
			}
		}
		if (useTownBlocks()) {
			if (random.nextBoolean()) {
				roofBlock = GOTRegistry.clayTileDyed;
				roofMeta = 14;
				roofSlabBlock = GOTRegistry.slabClayTileDyedSingle2;
				roofSlabMeta = 6;
				roofStairBlock = GOTRegistry.stairsClayTileDyedRed;
				roofWallBlock = GOTRegistry.wallClayTileDyed;
				roofWallMeta = 14;
			} else {
				int randomClay = random.nextInt(2);
				if (randomClay == 0) {
					roofBlock = GOTRegistry.clayTileDyed;
					roofMeta = 12;
					roofSlabBlock = GOTRegistry.slabClayTileDyedSingle2;
					roofSlabMeta = 4;
					roofStairBlock = GOTRegistry.stairsClayTileDyedBrown;
					roofWallBlock = GOTRegistry.wallClayTileDyed;
					roofWallMeta = 12;
				} else if (randomClay == 1) {
					roofBlock = GOTRegistry.clayTileDyed;
					roofMeta = 1;
					roofSlabBlock = GOTRegistry.slabClayTileDyedSingle1;
					roofSlabMeta = 1;
					roofStairBlock = GOTRegistry.stairsClayTileDyedOrange;
					roofWallBlock = GOTRegistry.wallClayTileDyed;
					roofWallMeta = 1;
				}
			}
		} else {
			roofBlock = GOTRegistry.thatch;
			roofMeta = 0;
			roofSlabBlock = GOTRegistry.slabSingleThatch;
			roofSlabMeta = 0;
			roofStairBlock = GOTRegistry.stairsThatch;
			roofWallBlock = fenceBlock;
			roofWallMeta = fenceMeta;
		}
		barsBlock = random.nextBoolean() ? Blocks.iron_bars : GOTRegistry.bronzeBars;
		tableBlock = GOTRegistry.tableYiTi;
		gateBlock = GOTRegistry.gateYiTi;
		bedBlock = useTownBlocks() ? Blocks.bed : GOTRegistry.strawBed;
		plateBlock = useTownBlocks() ? GOTRegistry.ceramicPlateBlock : random.nextBoolean() ? GOTRegistry.ceramicPlateBlock : GOTRegistry.woodPlateBlock;
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
		bannerType = GOTItemBanner.BannerType.YITI;
	}

	public boolean useTownBlocks() {
		return false;
	}
}
