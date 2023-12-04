package got.common.world.structure.essos.common;

import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureEssosFortGate extends GOTStructureEssosBase {
	public GOTStructureEssosFortGate(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		int j1;
		int i1;
		int k1;
		if (restrictions) {
			for (i1 = -5; i1 <= 5; ++i1) {
				for (k1 = -2; k1 <= 2; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k1 = 1; k1 <= 1; ++k1) {
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("essos_fortress_gate");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta8);
		associateBlockAlias("GATE_METAL", gateMetalBlock);
		generateStrScan(world, random, 0, 0, 0);
		placeWallBanner(world, -3, 5, -1, bannerType, 2);
		placeWallBanner(world, 3, 5, -1, bannerType, 2);
		return true;
	}

	@Override
	public boolean hasRedSandstone() {
		return false;
	}
}
