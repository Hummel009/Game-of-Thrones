package got.common.world.structure.essos.yiti;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTItems;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

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

	protected GOTStructureYiTiBase(boolean flag) {
		super(flag);
	}

	public ItemStack getFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.yitiHelmet), new ItemStack(GOTItems.yitiChestplate), new ItemStack(GOTItems.yitiLeggings), new ItemStack(GOTItems.yitiBoots), new ItemStack(GOTItems.yitiHelmetSamurai), new ItemStack(GOTItems.yitiChestplateSamurai), new ItemStack(GOTItems.yitiLeggingsSamurai), new ItemStack(GOTItems.yitiBootsSamurai), new ItemStack(GOTItems.yitiDagger), new ItemStack(GOTItems.yitiSword), new ItemStack(GOTItems.yitiBattleaxe), new ItemStack(GOTItems.yitiSpear), new ItemStack(GOTItems.yitiBow), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTItems.gobletSilver), new ItemStack(GOTItems.mug), new ItemStack(GOTItems.goldRing)};
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getWeaponItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.yitiSword), new ItemStack(GOTItems.yitiDagger), new ItemStack(GOTItems.yitiDaggerPoisoned), new ItemStack(GOTItems.yitiSpear), new ItemStack(GOTItems.yitiBattleaxe), new ItemStack(GOTItems.yitiPolearm), new ItemStack(GOTItems.yitiPike)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		thatchBlock = GOTBlocks.thatch;
		thatchMeta = 1;
		thatchSlabBlock = GOTBlocks.slabSingleThatch;
		thatchSlabMeta = 1;
		thatchStairBlock = GOTBlocks.stairsReed;
		brickBlock = GOTBlocks.brick5;
		brickMeta = 11;
		brickSlabBlock = GOTBlocks.slabSingle12;
		brickSlabMeta = 0;
		brickStairBlock = GOTBlocks.stairsYiTiBrick;
		brickWallBlock = GOTBlocks.wallStone3;
		brickWallMeta = 15;
		brickCarvedBlock = GOTBlocks.brick5;
		brickCarvedMeta = 12;
		brickFloweryBlock = GOTBlocks.brick5;
		brickFloweryMeta = 15;
		brickFlowerySlabBlock = GOTBlocks.slabSingle12;
		brickFlowerySlabMeta = 3;
		brickGoldBlock = GOTBlocks.brick6;
		brickGoldMeta = 0;
		brickRedBlock = GOTBlocks.brick6;
		brickRedMeta = 1;
		brickRedSlabBlock = GOTBlocks.slabSingle12;
		brickRedSlabMeta = 5;
		brickRedStairBlock = GOTBlocks.stairsYiTiBrickRed;
		brickRedWallBlock = GOTBlocks.wallStone4;
		brickRedWallMeta = 13;
		brickRedCarvedBlock = GOTBlocks.brick6;
		brickRedCarvedMeta = 2;
		pillarBlock = GOTBlocks.pillar2;
		pillarMeta = 8;
		pillarRedBlock = GOTBlocks.pillar2;
		pillarRedMeta = 9;
		if (random.nextBoolean()) {
			logBlock = GOTBlocks.wood8;
			logMeta = 1;
			plankBlock = GOTBlocks.planks3;
			plankMeta = 1;
			plankSlabBlock = GOTBlocks.woodSlabSingle5;
			plankSlabMeta = 1;
			plankStairBlock = GOTBlocks.stairsRedwood;
			fenceBlock = GOTBlocks.fence3;
			fenceMeta = 1;
			fenceGateBlock = GOTBlocks.fenceGateRedwood;
			woodBeamBlock = GOTBlocks.woodBeam8;
			woodBeamMeta = 1;
			doorBlock = GOTBlocks.doorRedwood;
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
					woodBeamBlock = GOTBlocks.woodBeamV1;
					woodBeamMeta = 0;
					doorBlock = Blocks.wooden_door;
					break;
				case 1:
					logBlock = GOTBlocks.wood2;
					logMeta = 1;
					plankBlock = GOTBlocks.planks1;
					plankMeta = 9;
					plankSlabBlock = GOTBlocks.woodSlabSingle2;
					plankSlabMeta = 1;
					plankStairBlock = GOTBlocks.stairsBeech;
					fenceBlock = GOTBlocks.fence;
					fenceMeta = 9;
					fenceGateBlock = GOTBlocks.fenceGateBeech;
					woodBeamBlock = GOTBlocks.woodBeam2;
					woodBeamMeta = 1;
					doorBlock = GOTBlocks.doorBeech;
					break;
				case 2:
					logBlock = GOTBlocks.wood6;
					logMeta = 2;
					plankBlock = GOTBlocks.planks2;
					plankMeta = 10;
					plankSlabBlock = GOTBlocks.woodSlabSingle4;
					plankSlabMeta = 2;
					plankStairBlock = GOTBlocks.stairsCypress;
					fenceBlock = GOTBlocks.fence2;
					fenceMeta = 10;
					fenceGateBlock = GOTBlocks.fenceGateCypress;
					woodBeamBlock = GOTBlocks.woodBeam6;
					woodBeamMeta = 2;
					doorBlock = GOTBlocks.doorCypress;
					break;
				default:
					logBlock = GOTBlocks.wood6;
					logMeta = 3;
					plankBlock = GOTBlocks.planks2;
					plankMeta = 11;
					plankSlabBlock = GOTBlocks.woodSlabSingle4;
					plankSlabMeta = 3;
					plankStairBlock = GOTBlocks.stairsOlive;
					fenceBlock = GOTBlocks.fence2;
					fenceMeta = 11;
					fenceGateBlock = GOTBlocks.fenceGateOlive;
					woodBeamBlock = GOTBlocks.woodBeam6;
					woodBeamMeta = 3;
					doorBlock = GOTBlocks.doorOlive;
					break;
			}
		}
		if (useTownBlocks()) {
			if (random.nextBoolean()) {
				roofBlock = GOTBlocks.clayTileDyed;
				roofMeta = 14;
				roofSlabBlock = GOTBlocks.slabClayTileDyedSingle2;
				roofSlabMeta = 6;
				roofStairBlock = GOTBlocks.stairsClayTileDyedRed;
				roofWallBlock = GOTBlocks.wallClayTileDyed;
				roofWallMeta = 14;
			} else if (random.nextBoolean()) {
				roofBlock = GOTBlocks.clayTileDyed;
				roofMeta = 12;
				roofSlabBlock = GOTBlocks.slabClayTileDyedSingle2;
				roofSlabMeta = 4;
				roofStairBlock = GOTBlocks.stairsClayTileDyedBrown;
				roofWallBlock = GOTBlocks.wallClayTileDyed;
				roofWallMeta = 12;
			} else {
				roofBlock = GOTBlocks.clayTileDyed;
				roofMeta = 1;
				roofSlabBlock = GOTBlocks.slabClayTileDyedSingle1;
				roofSlabMeta = 1;
				roofStairBlock = GOTBlocks.stairsClayTileDyedOrange;
				roofWallBlock = GOTBlocks.wallClayTileDyed;
				roofWallMeta = 1;
			}
		} else {
			roofBlock = GOTBlocks.thatch;
			roofMeta = 0;
			roofSlabBlock = GOTBlocks.slabSingleThatch;
			roofSlabMeta = 0;
			roofStairBlock = GOTBlocks.stairsThatch;
			roofWallBlock = fenceBlock;
			roofWallMeta = fenceMeta;
		}
		barsBlock = random.nextBoolean() ? Blocks.iron_bars : GOTBlocks.bronzeBars;
		tableBlock = GOTBlocks.tableYiTi;
		gateBlock = GOTBlocks.gateYiTi;
		bedBlock = useTownBlocks() ? Blocks.bed : GOTBlocks.strawBed;
		plateBlock = useTownBlocks() ? GOTBlocks.ceramicPlate : random.nextBoolean() ? GOTBlocks.ceramicPlate : GOTBlocks.woodPlate;
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
					cropBlock = GOTBlocks.lettuceCrop;
					cropMeta = 7;
					seedItem = GOTItems.lettuce;
					break;
				case 3:
					cropBlock = GOTBlocks.leekCrop;
					cropMeta = 7;
					seedItem = GOTItems.leek;
					break;
				default:
					cropBlock = GOTBlocks.turnipCrop;
					cropMeta = 7;
					seedItem = GOTItems.turnip;
					break;
			}
		}
		bannerType = GOTItemBanner.BannerType.YITI;
	}

	public boolean useTownBlocks() {
		return false;
	}
}
