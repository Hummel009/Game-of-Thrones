package got.common.world.structure.essos.asshai;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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

	public GOTStructureAsshaiBase(boolean flag) {
		super(flag);
	}

	public ItemStack getFramedItem(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.asshaiDagger), new ItemStack(GOTRegistry.asshaiSpear), new ItemStack(GOTRegistry.asshaiBow), new ItemStack(Items.arrow), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(GOTRegistry.ironDagger), new ItemStack(GOTRegistry.ironPike), new ItemStack(GOTRegistry.ironCrossbow), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing) };
		return items[random.nextInt(items.length)].copy();
	}

	@Override
	public void placeBigTorch(World world, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, GOTRegistry.fuse, 0);
		setBlockAndMetadata(world, i, j + 1, k, GOTRegistry.fuse, 1);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		cobbleBlock = GOTRegistry.rock;
		cobbleMeta = 0;
		wallBlock = GOTRegistry.brick1;
		wallMeta = 0;
		plateBlock = GOTRegistry.ceramicPlateBlock;
		fenceGateBlock = GOTRegistry.fenceGateDarkOak;
		doorBlock = GOTRegistry.doorDarkOak;
		rockBlock = GOTRegistry.rock;
		rockMeta = 0;
		cropBlock = GOTRegistry.asshaiFlower;
		cropMeta = 0;
		brickBlock = GOTRegistry.brick1;
		brickMeta = 0;
		brickSlabBlock = GOTRegistry.slabSingle1;
		brickSlabMeta = 1;
		brickStairBlock = GOTRegistry.stairsBasaltBrick;
		brickWallBlock = GOTRegistry.wallStone1;
		brickWallMeta = 1;
		brickCarvedBlock = GOTRegistry.brick1;
		brickCarvedMeta = 0;
		pillarBlock = GOTRegistry.pillar1;
		pillarMeta = 7;
		smoothBlock = GOTRegistry.smoothStone;
		smoothMeta = 0;
		smoothSlabBlock = GOTRegistry.slabSingle1;
		smoothSlabMeta = 0;
		tileBlock = GOTRegistry.clayTileDyed;
		tileMeta = 15;
		tileSlabBlock = GOTRegistry.slabClayTileDyedSingle2;
		tileSlabMeta = 7;
		tileStairBlock = GOTRegistry.stairsClayTileDyedBlack;
		plankBlock = GOTRegistry.planks1;
		plankMeta = 3;
		plankSlabBlock = GOTRegistry.woodSlabSingle1;
		plankSlabMeta = 3;
		plankStairBlock = GOTRegistry.stairsCharred;
		fenceBlock = GOTRegistry.fence;
		fenceMeta = 3;
		woodBeamBlock = GOTRegistry.woodBeam1;
		woodBeamMeta = 3;
		bedBlock = GOTRegistry.furBed;
		gateBlock = GOTRegistry.gateIronBars;
		barsBlock = GOTRegistry.asshaiBars;
		chandelierBlock = GOTRegistry.chandelier;
		chandelierMeta = 12;
	}
}
