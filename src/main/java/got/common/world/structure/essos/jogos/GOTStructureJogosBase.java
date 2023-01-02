package got.common.world.structure.essos.jogos;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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

	public GOTStructureJogosBase(boolean flag) {
		super(flag);
	}

	public ItemStack getRandomNomadWeapon(Random random) {
		ItemStack[] items = { new ItemStack(GOTRegistry.nomadBattleaxe), new ItemStack(GOTRegistry.nomadSword), new ItemStack(GOTRegistry.nomadSpear) };
		return items[random.nextInt(items.length)].copy();
	}

	public void laySandBase(World world, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, Blocks.grass, 0);
		int j1 = j - 1;
		while (getY(j1) >= 0 && !isOpaque(world, i, j1, k)) {
			if (isOpaque(world, i, j1 - 1, k)) {
			}
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
			beamBlock = GOTRegistry.woodBeamV1;
			beamMeta = 0;
			trapdoorBlock = Blocks.trapdoor;
			break;
		case 1:
			plankBlock = GOTRegistry.planks2;
			plankMeta = 2;
			plankSlabBlock = GOTRegistry.woodSlabSingle3;
			plankSlabMeta = 2;
			plankStairBlock = GOTRegistry.stairsCedar;
			fenceBlock = GOTRegistry.fence2;
			fenceMeta = 2;
			fenceGateBlock = GOTRegistry.fenceGateCedar;
			beamBlock = GOTRegistry.woodBeam4;
			beamMeta = 2;
			trapdoorBlock = GOTRegistry.trapdoorCedar;
			break;
		case 2:
			plankBlock = GOTRegistry.planks1;
			plankMeta = 14;
			plankSlabBlock = GOTRegistry.woodSlabSingle2;
			plankSlabMeta = 6;
			plankStairBlock = GOTRegistry.stairsDatePalm;
			fenceBlock = GOTRegistry.fence;
			fenceMeta = 14;
			fenceGateBlock = GOTRegistry.fenceGateDatePalm;
			beamBlock = GOTRegistry.woodBeam3;
			beamMeta = 2;
			trapdoorBlock = GOTRegistry.trapdoorDatePalm;
			break;
		default:
			break;
		}
		bedBlock = GOTRegistry.strawBed;
	}
}
