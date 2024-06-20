package got.common.world.structure.essos.asshai;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class GOTStructureAsshaiBase extends GOTStructureBase {
	protected Block brickBlock;
	protected int brickMeta;
	protected Block brickSlabBlock;
	protected int brickSlabMeta;
	protected Block brickStairBlock;
	protected Block brickWallBlock;
	protected int brickWallMeta;
	protected Block brickCarvedBlock;
	protected int brickCarvedMeta;
	protected Block pillarBlock;
	protected int pillarMeta;
	protected Block smoothBlock;
	protected int smoothMeta;
	protected Block smoothSlabBlock;
	protected int smoothSlabMeta;
	protected Block tileBlock;
	protected int tileMeta;
	protected Block tileSlabBlock;
	protected int tileSlabMeta;
	protected Block tileStairBlock;
	protected Block plankBlock;
	protected int plankMeta;
	protected Block plankSlabBlock;
	protected int plankSlabMeta;
	protected Block plankStairBlock;
	protected Block fenceBlock;
	protected int fenceMeta;
	protected Block woodBeamBlock;
	protected int woodBeamMeta;
	protected Block bedBlock;
	protected Block gateBlock;
	protected Block barsBlock;
	protected Block chandelierBlock;
	protected int chandelierMeta;
	protected Block rockBlock;
	protected Block doorBlock;
	protected Block fenceGateBlock;
	protected int rockMeta;
	protected Block wallBlock;
	protected int wallMeta;
	protected Block cobbleBlock;
	protected int cobbleMeta;
	protected Block plateBlock;
	protected int brick2Meta;
	protected Block brick2Block;
	protected Block brick2StairBlock;
	protected Block brick2SlabBlock;
	protected int brick2SlabMeta;

	protected GOTStructureAsshaiBase(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		cobbleBlock = GOTBlocks.rock;
		cobbleMeta = 0;
		wallBlock = GOTBlocks.brick1;
		wallMeta = 0;
		plateBlock = GOTBlocks.ceramicPlate;
		fenceGateBlock = GOTBlocks.fenceGateDarkOak;
		doorBlock = GOTBlocks.doorCharred;
		rockBlock = GOTBlocks.rock;
		rockMeta = 0;
		brickBlock = GOTBlocks.brick1;
		brickMeta = 0;
		brickSlabBlock = GOTBlocks.slabSingle1;
		brickSlabMeta = 1;
		brickStairBlock = GOTBlocks.stairsBasaltBrick;
		brickWallBlock = GOTBlocks.wallStone1;
		brickWallMeta = 1;
		brickCarvedBlock = GOTBlocks.brick2;
		brickCarvedMeta = 10;
		pillarBlock = GOTBlocks.pillar1;
		pillarMeta = 7;
		smoothBlock = GOTBlocks.smoothStone;
		smoothMeta = 0;
		smoothSlabBlock = GOTBlocks.slabSingle1;
		smoothSlabMeta = 0;
		tileBlock = GOTBlocks.planks1;
		tileMeta = 3;
		brick2Meta = 3;
		brick2Block = GOTBlocks.planks1;
		brick2SlabMeta = 3;
		brick2SlabBlock = GOTBlocks.woodSlabSingle1;
		brick2StairBlock = GOTBlocks.stairsCharred;
		tileSlabBlock = GOTBlocks.woodSlabSingle1;
		tileSlabMeta = 3;
		tileStairBlock = GOTBlocks.stairsCharred;
		plankBlock = GOTBlocks.planks1;
		plankMeta = 3;
		plankSlabBlock = GOTBlocks.woodSlabSingle1;
		plankSlabMeta = 3;
		plankStairBlock = GOTBlocks.stairsCharred;
		fenceBlock = GOTBlocks.fence;
		fenceMeta = 3;
		woodBeamBlock = GOTBlocks.woodBeam1;
		woodBeamMeta = 3;
		bedBlock = GOTBlocks.furBed;
		gateBlock = GOTBlocks.gateIronBars;
		barsBlock = GOTBlocks.asshaiBars;
		chandelierBlock = GOTBlocks.chandelier;
		chandelierMeta = 12;
	}
}