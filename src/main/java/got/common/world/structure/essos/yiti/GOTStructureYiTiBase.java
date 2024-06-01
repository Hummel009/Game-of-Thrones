package got.common.world.structure.essos.yiti;

import got.common.database.GOTBlocks;
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
	protected Block brickBlock;
	protected int brickMeta;
	protected Block brickSlabBlock;
	protected int brickSlabMeta;
	protected Block brickStairBlock;
	protected Block brickWallBlock;
	protected int brickWallMeta;
	protected Block brickCarvedBlock;
	protected int brickCarvedMeta;
	protected Block brickFloweryBlock;
	protected int brickFloweryMeta;
	protected Block brickGoldBlock;
	protected int brickGoldMeta;
	protected Block brickRedBlock;
	protected int brickRedMeta;
	protected Block brickRedStairBlock;
	protected Block brickRedWallBlock;
	protected int brickRedWallMeta;
	protected Block brickRedCarvedBlock;
	protected int brickRedCarvedMeta;
	protected Block pillarBlock;
	protected int pillarMeta;
	protected Block pillarRedBlock;
	protected int pillarRedMeta;
	protected Block logBlock;
	protected int logMeta;
	protected Block plankBlock;
	protected int plankMeta;
	protected Block plankSlabBlock;
	protected int plankSlabMeta;
	protected Block plankStairBlock;
	protected Block fenceBlock;
	protected int fenceMeta;
	protected Block fenceGateBlock;
	protected Block woodBeamBlock;
	protected int woodBeamMeta;
	protected Block doorBlock;
	protected Block roofBlock;
	protected int roofMeta;
	protected Block roofSlabBlock;
	protected int roofSlabMeta;
	protected Block roofStairBlock;
	protected Block roofWallBlock;
	protected int roofWallMeta;
	protected Block barsBlock;
	protected Block tableBlock;
	protected Block gateBlock;
	protected Block bedBlock;
	protected Block plateBlock;
	protected Block cropBlock;
	protected int cropMeta;
	protected Item seedItem;
	protected GOTItemBanner.BannerType bannerType;

	protected GOTStructureYiTiBase(boolean flag) {
		super(flag);
	}

	protected static ItemStack getFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.yitiHelmet), new ItemStack(GOTItems.yitiChestplate), new ItemStack(GOTItems.yitiLeggings), new ItemStack(GOTItems.yitiBoots), new ItemStack(GOTItems.yitiHelmetSamurai), new ItemStack(GOTItems.yitiChestplateSamurai), new ItemStack(GOTItems.yitiLeggingsSamurai), new ItemStack(GOTItems.yitiBootsSamurai), new ItemStack(GOTItems.yitiDagger), new ItemStack(GOTItems.yitiSword), new ItemStack(GOTItems.yitiBattleaxe), new ItemStack(GOTItems.yitiSpear), new ItemStack(GOTItems.yitiBow), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTItems.gobletCopper), new ItemStack(GOTItems.mug), new ItemStack(GOTItems.goldRing)};
		return items[random.nextInt(items.length)].copy();
	}

	protected static ItemStack getWeaponItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.yitiSword), new ItemStack(GOTItems.yitiDagger), new ItemStack(GOTItems.yitiDaggerPoisoned), new ItemStack(GOTItems.yitiSpear), new ItemStack(GOTItems.yitiBattleaxe), new ItemStack(GOTItems.yitiPolearm), new ItemStack(GOTItems.yitiPike)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
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
		brickGoldBlock = GOTBlocks.brick6;
		brickGoldMeta = 0;
		brickRedBlock = GOTBlocks.brick6;
		brickRedMeta = 1;
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
				case 3:
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
				case 4:
					cropBlock = GOTBlocks.turnipCrop;
					cropMeta = 7;
					seedItem = GOTItems.turnip;
					break;
			}
		}
		bannerType = GOTItemBanner.BannerType.YITI;
	}

	protected boolean useTownBlocks() {
		return false;
	}
}