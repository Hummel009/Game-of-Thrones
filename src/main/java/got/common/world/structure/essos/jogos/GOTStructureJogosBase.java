package got.common.world.structure.essos.jogos;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTStructureJogosBase extends GOTStructureBase {
	public Block tentBlock;
	public int tentMeta;
	public Block tent2Block;
	public int tent2Meta;
	public Block carpetBlock;
	public int carpetMeta;
	public Block carpet2Block;
	public int carpet2Meta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block fenceGateBlock;
	public Block beamBlock;
	public int beamMeta;
	public Block bedBlock;
	public Block trapdoorBlock;

	protected GOTStructureJogosBase(boolean flag) {
		super(flag);
	}

	public ItemStack getRandomNomadWeapon(Random random) {
		ItemStack[] items = {new ItemStack(GOTItems.nomadBattleaxe), new ItemStack(GOTItems.nomadSword), new ItemStack(GOTItems.nomadSpear)};
		return items[random.nextInt(items.length)].copy();
	}

	public void laySandBase(World world, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, Blocks.grass, 0);
		int j1 = j - 1;
		while (getY(j1) >= 0 && !isOpaque(world, i, j1, k)) {
			setBlockAndMetadata(world, i, j1, k, Blocks.grass, 0);
			setGrassToDirt(world, i, j1 - 1, k);
			--j1;
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		tentBlock = Blocks.wool;
		tentMeta = 0;
		tent2Block = Blocks.wool;
		tent2Meta = 12;
		carpetBlock = Blocks.carpet;
		carpetMeta = 0;
		carpet2Block = Blocks.carpet;
		carpet2Meta = 12;
		int randomWood = random.nextInt(3);
		switch (randomWood) {
			case 0:
				plankBlock = Blocks.planks;
				plankMeta = 0;
				plankSlabBlock = Blocks.wooden_slab;
				plankSlabMeta = 0;
				plankStairBlock = Blocks.oak_stairs;
				fenceBlock = Blocks.fence;
				fenceMeta = 0;
				fenceGateBlock = Blocks.fence_gate;
				beamBlock = GOTBlocks.woodBeamV1;
				beamMeta = 0;
				trapdoorBlock = Blocks.trapdoor;
				break;
			case 1:
				plankBlock = GOTBlocks.planks2;
				plankMeta = 2;
				plankSlabBlock = GOTBlocks.woodSlabSingle3;
				plankSlabMeta = 2;
				plankStairBlock = GOTBlocks.stairsCedar;
				fenceBlock = GOTBlocks.fence2;
				fenceMeta = 2;
				fenceGateBlock = GOTBlocks.fenceGateCedar;
				beamBlock = GOTBlocks.woodBeam4;
				beamMeta = 2;
				trapdoorBlock = GOTBlocks.trapdoorCedar;
				break;
			default:
				plankBlock = GOTBlocks.planks1;
				plankMeta = 14;
				plankSlabBlock = GOTBlocks.woodSlabSingle2;
				plankSlabMeta = 6;
				plankStairBlock = GOTBlocks.stairsDatePalm;
				fenceBlock = GOTBlocks.fence;
				fenceMeta = 14;
				fenceGateBlock = GOTBlocks.fenceGateDatePalm;
				beamBlock = GOTBlocks.woodBeam3;
				beamMeta = 2;
				trapdoorBlock = GOTBlocks.trapdoorDatePalm;
				break;
		}
		bedBlock = GOTBlocks.strawBed;
	}
}
