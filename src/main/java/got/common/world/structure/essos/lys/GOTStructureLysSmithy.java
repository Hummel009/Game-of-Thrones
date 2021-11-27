package got.common.world.structure.essos.lys;

import java.util.Random;

import got.common.database.*;
import got.common.entity.essos.lys.GOTEntityLysBlacksmith;
import net.minecraft.world.World;

public class GOTStructureLysSmithy extends GOTStructureLysBase {
	public GOTStructureLysSmithy(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		this.setOriginAndRotation(world, i, j, k, rotation, 5, -1);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -4; i1 <= 4; ++i1) {
				for (int k1 = -5; k1 <= 13; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
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
		for (int i1 = -4; i1 <= 4; ++i1) {
			for (int k1 = -5; k1 <= 13; ++k1) {
				int i2 = Math.abs(i1);
				if ((i2 > 3 || k1 < -4 || k1 > 6) && (i2 > 4 || k1 < 7 || k1 > 12)) {
					continue;
				}
				int j1 = 0;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, stoneBlock, stoneMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("essos_smithy");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta4);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta8);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("TABLE", tableBlock, 0);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, -1, 5, -2, bedBlock, 2);
		setBlockAndMetadata(world, -2, 5, -2, bedBlock, 2);
		setBlockAndMetadata(world, -1, 5, -3, bedBlock, 10);
		setBlockAndMetadata(world, -2, 5, -3, bedBlock, 10);
		this.placeChest(world, random, 3, 1, 6, GOTRegistry.chestBasket, 5, GOTChestContents.ESSOS);
		this.placeChest(world, random, 2, 5, -3, GOTRegistry.chestBasket, 5, GOTChestContents.ESSOS);
		placePlateWithCertainty(world, random, -1, 6, 3, GOTRegistry.ceramicPlateBlock, GOTFoods.ESSOS);
		this.placeMug(world, random, -2, 6, 3, 0, GOTFoods.ESSOS_DRINK);
		placeWeaponRack(world, -3, 1, 8, 1, getRandomWeapon(random));
		placeWeaponRack(world, 3, 1, 8, 3, getRandomWeapon(random));
		GOTEntityLysBlacksmith npc = new GOTEntityLysBlacksmith(world);
		spawnNPCAndSetHome(npc, world, 0, 1, 6, 8);
		return true;
	}
}
