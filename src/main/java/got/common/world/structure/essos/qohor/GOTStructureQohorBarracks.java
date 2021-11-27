package got.common.world.structure.essos.qohor;

import java.util.Random;

import got.common.database.*;
import got.common.entity.essos.qohor.GOTEntityQohorUnsullied;
import net.minecraft.world.World;

public class GOTStructureQohorBarracks extends GOTStructureQohorBase {
	public GOTStructureQohorBarracks(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		this.setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -6; i1 <= 5; ++i1) {
				for (int k12 = -8; k12 <= 8; ++k12) {
					int j1 = getTopBlock(world, i1, k12) - 1;
					if (!isSurface(world, i1, j1, k12)) {
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
		for (int i1 = -5; i1 <= 4; ++i1) {
			for (int k13 = -7; k13 <= 7; ++k13) {
				for (int j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i1, j1, k13);
				}
			}
		}
		loadStrScan("essos_barracks");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta4);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta8);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		generateStrScan(world, random, 0, 0, 0);
		for (k1 = -4; k1 <= 4; k1 += 2) {
			if (random.nextBoolean()) {
				this.placeChest(world, random, -4, 1, k1, GOTRegistry.chestBasket, 4, GOTChestContents.ESSOS, 1 + random.nextInt(2));
			} else {
				setBlockAndMetadata(world, -4, 1, k1, GOTRegistry.chestBasket, 4);
			}
			if (random.nextBoolean()) {
				this.placeChest(world, random, 3, 1, k1, GOTRegistry.chestBasket, 5, GOTChestContents.ESSOS, 1 + random.nextInt(2));
				continue;
			}
			setBlockAndMetadata(world, 3, 1, k1, GOTRegistry.chestBasket, 5);
		}
		for (k1 = -5; k1 <= 5; k1 += 2) {
			for (int j1 = 1; j1 <= 2; ++j1) {
				setBlockAndMetadata(world, -3, j1, k1, bedBlock, 3);
				setBlockAndMetadata(world, -4, j1, k1, bedBlock, 11);
				setBlockAndMetadata(world, 2, j1, k1, bedBlock, 1);
				setBlockAndMetadata(world, 3, j1, k1, bedBlock, 9);
			}
		}
		int warriors = 2 + random.nextInt(3);
		for (int l = 0; l < warriors; ++l) {
			GOTEntityQohorUnsullied npc = new GOTEntityQohorUnsullied(world);
			spawnNPCAndSetHome(npc, world, random.nextBoolean() ? -1 : 0, 1, 0, 16);
		}
		return true;
	}
}
