package got.common.world.structure.essos.common;

import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureEssosTownGate extends GOTStructureEssosBase {
	public GOTStructureEssosTownGate(boolean flag) {
		super(flag);
	}

	@Override
	public boolean hasRedSandstone() {
		return false;
	}

	@Override
	public boolean hasMonotypeWood() {
		return true;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int i12;
		int step;
		int k1;
		int j1;
		int j12;
		int j2;
		int i1;
		int k12;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -8; i1 <= 8; ++i1) {
				for (k12 = -3; k12 <= 3; ++k12) {
					j12 = getTopBlock(world, i1, k12) - 1;
					if (isSurface(world, i1, j12, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -8; i1 <= 8; ++i1) {
			for (k12 = -3; k12 <= 3; ++k12) {
				for (j12 = 1; j12 <= 12; ++j12) {
					setAir(world, i1, j12, k12);
				}
			}
		}
		loadStrScan("essos_town_gate");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockAlias("STONE_STAIR", stoneStairBlock);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockMetaAlias("BRICK2_SLAB", brick2SlabBlock, brick2SlabMeta);
		associateBlockMetaAlias("BRICK2_SLAB_INV", brick2SlabBlock, brick2SlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta4);
		associateBlockAlias("GATE_METAL", gateMetalBlock);
		generateStrScan(world, random, 0, 0, 0);
		placeWallBanner(world, -6, 4, -2, bannerType, 2);
		placeWallBanner(world, 6, 4, -2, bannerType, 2);
		for (step = 0; step < 12; ++step) {
			i12 = -7 - step;
			j1 = 5 - step;
			k1 = 2;
			if (isOpaque(world, i12, j1, 2)) {
				break;
			}
			if (j1 <= 1) {
				setBlockAndMetadata(world, i12, j1, k1, stoneStairBlock, 1);
			} else {
				setBlockAndMetadata(world, i12, j1, k1, brickStairBlock, 1);
			}
			setGrassToDirt(world, i12, j1 - 1, k1);
			j2 = j1 - 1;
			while (!isOpaque(world, i12, j2, k1) && getY(j2) >= 0) {
				if (j2 <= 1) {
					setBlockAndMetadata(world, i12, j2, k1, stoneBlock, stoneMeta);
				} else {
					setBlockAndMetadata(world, i12, j2, k1, brickBlock, brickMeta);
				}
				setGrassToDirt(world, i12, j2 - 1, k1);
				--j2;
			}
		}
		for (step = 0; step < 12; ++step) {
			i12 = 7 + step;
			j1 = 5 - step;
			k1 = 2;
			if (isOpaque(world, i12, j1, 2)) {
				break;
			}
			if (j1 <= 1) {
				setBlockAndMetadata(world, i12, j1, k1, stoneStairBlock, 0);
			} else {
				setBlockAndMetadata(world, i12, j1, k1, brickStairBlock, 0);
			}
			setGrassToDirt(world, i12, j1 - 1, k1);
			j2 = j1 - 1;
			while (!isOpaque(world, i12, j2, k1) && getY(j2) >= 0) {
				if (j2 <= 1) {
					setBlockAndMetadata(world, i12, j2, k1, stoneBlock, stoneMeta);
				} else {
					setBlockAndMetadata(world, i12, j2, k1, brickBlock, brickMeta);
				}
				setGrassToDirt(world, i12, j2 - 1, k1);
				--j2;
			}
		}
		return true;
	}
}
