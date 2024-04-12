package got.common.world.structure.sothoryos.summer;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class GOTStructureSummerBase extends GOTStructureBase {
	protected Block woodBlock;
	protected int woodMeta;
	protected Block plankBlock;
	protected int plankMeta;
	protected Block plankSlabBlock;
	protected int plankSlabMeta;
	protected Block plankStairBlock;
	protected Block fenceBlock;
	protected Block trapdoorBlock;
	protected int fenceMeta;
	protected Block fenceGateBlock;
	protected Block doorBlock;
	protected Block roofBlock;
	protected int roofMeta;
	protected Block plank2Block;
	protected int plank2Meta;
	protected Block plank2SlabBlock;
	protected int plank2SlabMeta;
	protected Block plank2StairBlock;
	protected Block boneBlock;
	protected int boneMeta;
	protected Block bedBlock;

	protected GOTStructureSummerBase(boolean flag) {
		super(flag);
	}

	protected ItemStack getFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.summerHelmet), new ItemStack(GOTItems.summerChestplate), new ItemStack(GOTItems.summerLeggings), new ItemStack(GOTItems.summerBoots), new ItemStack(GOTItems.summerDagger), new ItemStack(GOTItems.summerSword), new ItemStack(GOTItems.summerSpear), new ItemStack(GOTItems.summerPike), new ItemStack(GOTItems.essosBow), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTItems.gobletSilver), new ItemStack(GOTItems.mug), new ItemStack(GOTItems.ceramicMug), new ItemStack(GOTItems.goldRing), new ItemStack(GOTItems.silverRing), new ItemStack(GOTItems.copperRing), new ItemStack(GOTItems.bronzeRing), new ItemStack(GOTBlocks.doubleFlower, 1, 2), new ItemStack(GOTBlocks.doubleFlower, 1, 3)};
		return items[random.nextInt(items.length)].copy();
	}

	protected ItemStack getRandomWeapon(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.summerSword), new ItemStack(GOTItems.summerDagger), new ItemStack(GOTItems.summerSpear), new ItemStack(GOTItems.summerPike)};
		return items[random.nextInt(items.length)].copy();
	}

	protected boolean isRuined() {
		return false;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		woodBlock = GOTBlocks.wood4;
		woodMeta = 2;
		plankBlock = GOTBlocks.planks2;
		plankMeta = 2;
		plankSlabBlock = GOTBlocks.woodSlabSingle3;
		plankSlabMeta = 2;
		plankStairBlock = GOTBlocks.stairsCedar;
		fenceBlock = GOTBlocks.fence2;
		fenceMeta = 2;
		fenceGateBlock = GOTBlocks.fenceGateCedar;
		doorBlock = GOTBlocks.doorCedar;
		trapdoorBlock = GOTBlocks.trapdoorCedar;
		int randomWool = random.nextInt(3);
		switch (randomWool) {
			case 0:
				roofBlock = Blocks.wool;
				roofMeta = 13;
				break;
			case 1:
				roofBlock = Blocks.wool;
				roofMeta = 11;
				break;
			default:
				roofBlock = Blocks.wool;
				roofMeta = 8;
				break;
		}
		if (random.nextBoolean()) {
			plank2Block = GOTBlocks.planks2;
			plank2Meta = 11;
			plank2SlabBlock = GOTBlocks.woodSlabSingle4;
			plank2SlabMeta = 3;
			plank2StairBlock = GOTBlocks.stairsOlive;
		} else {
			plank2Block = GOTBlocks.planks3;
			plank2Meta = 0;
			plank2SlabBlock = GOTBlocks.woodSlabSingle5;
			plank2SlabMeta = 0;
			plank2StairBlock = GOTBlocks.stairsPlum;
		}
		boneBlock = GOTBlocks.boneBlock;
		boneMeta = 0;
		bedBlock = GOTBlocks.strawBed;
		if (isRuined()) {
			if (random.nextBoolean()) {
				woodBlock = GOTBlocks.wood1;
				woodMeta = 3;
				plankBlock = GOTBlocks.planks1;
				plankMeta = 3;
				plankSlabBlock = GOTBlocks.woodSlabSingle1;
				plankSlabMeta = 3;
				plankStairBlock = GOTBlocks.stairsCharred;
				fenceBlock = GOTBlocks.fence;
				fenceMeta = 3;
				fenceGateBlock = GOTBlocks.fenceGateCharred;
				doorBlock = GOTBlocks.doorCharred;
				trapdoorBlock = GOTBlocks.trapdoorCharred;
			}
			if (random.nextBoolean()) {
				plank2Block = GOTBlocks.planks1;
				plank2Meta = 3;
				plank2SlabBlock = GOTBlocks.woodSlabSingle1;
				plank2SlabMeta = 3;
				plank2StairBlock = GOTBlocks.stairsCharred;
			}
		}
	}
}
