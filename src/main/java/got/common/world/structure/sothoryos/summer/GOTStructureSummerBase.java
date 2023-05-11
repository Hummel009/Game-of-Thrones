package got.common.world.structure.sothoryos.summer;

import got.common.database.GOTRegistry;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Random;

public abstract class GOTStructureSummerBase extends GOTStructureBase {
	public Block woodBlock;
	public int woodMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public Block trapdoorBlock;
	public int fenceMeta;
	public Block fenceGateBlock;
	public Block doorBlock;
	public Block roofBlock;
	public int roofMeta;
	public Block plank2Block;
	public int plank2Meta;
	public Block plank2SlabBlock;
	public int plank2SlabMeta;
	public Block plank2StairBlock;
	public Block boneBlock;
	public int boneMeta;
	public Block bedBlock;

	protected GOTStructureSummerBase(boolean flag) {
		super(flag);
	}

	public ItemStack getFramedItem(Random random) {
		ItemStack[] items = {new ItemStack(GOTRegistry.summerHelmet), new ItemStack(GOTRegistry.summerChestplate), new ItemStack(GOTRegistry.summerLeggings), new ItemStack(GOTRegistry.summerBoots), new ItemStack(GOTRegistry.summerDagger), new ItemStack(GOTRegistry.summerSword), new ItemStack(GOTRegistry.summerSpear), new ItemStack(GOTRegistry.summerPike), new ItemStack(GOTRegistry.essosBow), new ItemStack(Items.arrow), new ItemStack(Items.skull), new ItemStack(Items.bone), new ItemStack(GOTRegistry.gobletSilver), new ItemStack(GOTRegistry.mug), new ItemStack(GOTRegistry.ceramicMug), new ItemStack(GOTRegistry.goldRing), new ItemStack(GOTRegistry.silverRing), new ItemStack(GOTRegistry.copperRing), new ItemStack(GOTRegistry.bronzeRing), new ItemStack(GOTRegistry.doubleFlower, 1, 2), new ItemStack(GOTRegistry.doubleFlower, 1, 3)};
		return items[random.nextInt(items.length)].copy();
	}

	public ItemStack getRandomWeapon(Random random) {
		ItemStack[] items = {new ItemStack(GOTRegistry.summerSword), new ItemStack(GOTRegistry.summerDagger), new ItemStack(GOTRegistry.summerSpear), new ItemStack(GOTRegistry.summerPike)};
		return items[random.nextInt(items.length)].copy();
	}

	public boolean isRuined() {
		return false;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
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
		doorBlock = GOTRegistry.doorCedar;
		trapdoorBlock = GOTRegistry.trapdoorCedar;
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
			case 2:
				roofBlock = Blocks.wool;
				roofMeta = 8;
				break;
			default:
				break;
		}
		int randomFloorWood = random.nextInt(2);
		if (randomFloorWood == 0) {
			plank2Block = GOTRegistry.planks2;
			plank2Meta = 11;
			plank2SlabBlock = GOTRegistry.woodSlabSingle4;
			plank2SlabMeta = 3;
			plank2StairBlock = GOTRegistry.stairsOlive;
		} else if (randomFloorWood == 1) {
			plank2Block = GOTRegistry.planks3;
			plank2Meta = 0;
			plank2SlabBlock = GOTRegistry.woodSlabSingle5;
			plank2SlabMeta = 0;
			plank2StairBlock = GOTRegistry.stairsPlum;
		}
		boneBlock = GOTRegistry.boneBlock;
		boneMeta = 0;
		bedBlock = GOTRegistry.strawBed;
		if (isRuined()) {
			if (random.nextBoolean()) {
				woodBlock = GOTRegistry.wood1;
				woodMeta = 3;
				plankBlock = GOTRegistry.planks1;
				plankMeta = 3;
				plankSlabBlock = GOTRegistry.woodSlabSingle1;
				plankSlabMeta = 3;
				plankStairBlock = GOTRegistry.stairsCharred;
				fenceBlock = GOTRegistry.fence;
				fenceMeta = 3;
				fenceGateBlock = GOTRegistry.fenceGateCharred;
				doorBlock = GOTRegistry.doorCharred;
				trapdoorBlock = GOTRegistry.trapdoorCharred;
			}
			if (random.nextBoolean()) {
				plank2Block = GOTRegistry.planks1;
				plank2Meta = 3;
				plank2SlabBlock = GOTRegistry.woodSlabSingle1;
				plank2SlabMeta = 3;
				plank2StairBlock = GOTRegistry.stairsCharred;
			}
		}
	}
}
