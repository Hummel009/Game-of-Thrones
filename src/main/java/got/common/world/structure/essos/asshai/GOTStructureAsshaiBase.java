package got.common.world.structure.essos.asshai;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class GOTStructureAsshaiBase extends GOTStructureBase {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block brickCarvedBlock;
	public int brickCarvedMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block smoothBlock;
	public int smoothMeta;
	public Block smoothSlabBlock;
	public int smoothSlabMeta;
	public Block tileBlock;
	public int tileMeta;
	public Block tileSlabBlock;
	public int tileSlabMeta;
	public Block tileStairBlock;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block woodBeamBlock;
	public int woodBeamMeta;
	public Block bedBlock;
	public Block gateBlock;
	public Block barsBlock;
	public Block chandelierBlock;
	public int chandelierMeta;
	public Block rockBlock;
	public Block doorBlock;
	public Block fenceGateBlock;
	public Block cropBlock;
	public int rockMeta;
	public int cropMeta;
	public Block wallBlock;
	public int wallMeta;
	public Block cobbleBlock;
	public int cobbleMeta;
	public Block plateBlock;
	public int brick2Meta;
	public Block brick2Block;
	public Block brick2StairBlock;
	public Block brick2SlabBlock;
	public int brick2SlabMeta;

	protected GOTStructureAsshaiBase(boolean flag) {
		super(flag);
	}

	public ItemStack getFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.asshaiBattleaxe), new ItemStack(GOTItems.asshaiBow), new ItemStack(GOTItems.asshaiDagger), new ItemStack(GOTItems.asshaiHammer), new ItemStack(GOTItems.asshaiSpear), new ItemStack(GOTItems.asshaiSword)};
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		cobbleBlock = GOTBlocks.rock;
		cobbleMeta = 0;
		wallBlock = GOTBlocks.brick1;
		wallMeta = 0;
		plateBlock = GOTBlocks.ceramicPlateBlock;
		fenceGateBlock = GOTBlocks.fenceGateDarkOak;
		doorBlock = GOTBlocks.doorCharred;
		rockBlock = GOTBlocks.rock;
		rockMeta = 0;
		cropBlock = GOTBlocks.asshaiFlower;
		cropMeta = 0;
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
