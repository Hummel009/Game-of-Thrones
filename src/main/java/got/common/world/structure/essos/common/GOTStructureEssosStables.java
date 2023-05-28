package got.common.world.structure.essos.common;

import got.common.database.GOTBlocks;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureEssosStables extends GOTStructureEssosBase {
	public GOTStructureEssosStables(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -4; i1 <= 8; ++i1) {
				for (int k1 = -7; k1 <= 7; ++k1) {
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
		for (int i1 = -4; i1 <= 8; ++i1) {
			for (int k1 = -7; k1 <= 7; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 <= 4 || i1 >= 5 && k2 <= 6) {
					for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i1, j1, k1, stoneBlock, stoneMeta);
						setGrassToDirt(world, i1, j1 - 1, k1);
					}
					for (j1 = 1; j1 <= 8; ++j1) {
						setAir(world, i1, j1, k1);
					}
				}
				if ((i2 > 3 || k2 > 6) && (i1 < 4 || i1 > 7 || k2 > 5)) {
					continue;
				}
				if (hasSandstone()) {
					if (random.nextBoolean()) {
						setBlockAndMetadata(world, i1, 0, k1, Blocks.sand, 0);
					} else {
						setBlockAndMetadata(world, i1, 0, k1, GOTBlocks.dirtPath, 0);
					}
				} else if (random.nextBoolean()) {
					setBlockAndMetadata(world, i1, 0, k1, Blocks.dirt, 1);
				} else {
					setBlockAndMetadata(world, i1, 0, k1, GOTBlocks.dirtPath, 0);
				}
				if (random.nextInt(4) == 0) {
					setBlockAndMetadata(world, i1, 1, k1, GOTBlocks.thatchFloor, 0);
				}
			}
		}
		loadStrScan("essos_stable");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta4);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta8);
		generateStrScan(world, random, 0, 1, 0);
		placeChest(world, random, -3, 1, 6, GOTBlocks.chestBasket, 2, getChestContents());
		int numnpc = 1 + random.nextInt(2);
		for (int l = 0; l < numnpc; ++l) {
			GOTEntityNPC npc = getMan(world);
			spawnNPCAndSetHome(npc, world, 0, 1, 0, 8);
		}
		for (int k1 : new int[]{-4, 0, 4}) {
			int i1 = 5;
			int j12 = 1;
			GOTEntityHorse horse = new GOTEntityHorse(world);
			spawnNPCAndSetHome(horse, world, i1, j12, k1, 0);
			horse.setHorseType(0);
			horse.saddleMountForWorldGen();
			horse.detachHome();
		}
		return true;
	}
}
