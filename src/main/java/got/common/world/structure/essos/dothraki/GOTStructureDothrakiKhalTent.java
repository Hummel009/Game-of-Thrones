package got.common.world.structure.essos.dothraki;

import java.util.Random;

import got.common.database.*;
import got.common.entity.animal.GOTEntityCamel;
import got.common.entity.essos.dothraki.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureDothrakiKhalTent extends GOTStructureDothrakiBase {
	public GOTStructureDothrakiKhalTent(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 9);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -12; i1 <= 12; ++i1) {
				for (int k1 = -8; k1 <= 8; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -12; i1 <= 12; ++i1) {
			for (int k1 = -8; k1 <= 8; ++k1) {
				Math.abs(i1);
				Math.abs(k1);
				if (!isSurface(world, i1, 0, k1)) {
					laySandBase(world, i1, 0, k1);
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("nomad_tent_chieftain");
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockMetaAlias("TENT", tentBlock, tentMeta);
		associateBlockMetaAlias("TENT2", tent2Block, tent2Meta);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		associateBlockMetaAlias("CARPET2", carpet2Block, carpet2Meta);
		associateBlockAlias("TABLE", GOTRegistry.tableDothraki);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, -6, 1, 4, bedBlock, 0);
		setBlockAndMetadata(world, -6, 1, 5, bedBlock, 8);
		setBlockAndMetadata(world, -5, 1, 4, bedBlock, 0);
		setBlockAndMetadata(world, -5, 1, 5, bedBlock, 8);
		setBlockAndMetadata(world, 5, 1, 4, bedBlock, 0);
		setBlockAndMetadata(world, 5, 1, 5, bedBlock, 8);
		setBlockAndMetadata(world, 6, 1, 4, bedBlock, 0);
		setBlockAndMetadata(world, 6, 1, 5, bedBlock, 8);
		this.placeChest(world, random, -11, 1, 0, GOTRegistry.chestBasket, 4, GOTChestContents.DOTHRAKI);
		this.placeChest(world, random, 11, 1, 0, GOTRegistry.chestBasket, 5, GOTChestContents.DOTHRAKI);
		this.placeMug(world, random, -4, 2, -5, 2, GOTFoods.NOMAD_DRINK);
		placePlateWithCertainty(world, random, -6, 2, -5, GOTRegistry.ceramicPlateBlock, GOTFoods.NOMAD);
		placePlateWithCertainty(world, random, 6, 2, -5, GOTRegistry.ceramicPlateBlock, GOTFoods.NOMAD);
		this.placeMug(world, random, 4, 2, -5, 2, GOTFoods.NOMAD_DRINK);
		setBlockAndMetadata(world, -1, 4, -9, Blocks.skull, 2);
		setBlockAndMetadata(world, 1, 4, -9, Blocks.skull, 2);
		GOTEntityDothrakiKhal chief = new GOTEntityDothrakiKhal(world);
		spawnNPCAndSetHome(chief, world, 0, 1, 0, 8);
		int warriors = 2 + random.nextInt(2);
		for (int l = 0; l < warriors; ++l) {
			GOTEntityDothraki warrior = new GOTEntityDothraki(world);
			warrior.spawnRidingHorse = false;
			spawnNPCAndSetHome(warrior, world, random.nextBoolean() ? -6 : 6, 1, 0, 8);
		}
		for (int i1 : new int[] { -5, 5 }) {
			int j12 = 1;
			int k1 = -8;
			if (!isOpaque(world, i1, j12 - 1, k1) || !isAir(world, i1, j12, k1)) {
				continue;
			}
			setBlockAndMetadata(world, i1, j12, k1, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i1, j12 + 1, k1, fenceBlock, fenceMeta);
			GOTEntityCamel camel = new GOTEntityCamel(world);
			spawnNPCAndSetHome(camel, world, i1, j12, k1, 0);
			camel.saddleMountForWorldGen();
			camel.detachHome();
			camel.setNomadChestAndCarpet();
			leashEntityTo(camel, world, i1, j12, k1);
		}
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		bedBlock = GOTRegistry.lionBed;
	}
}
