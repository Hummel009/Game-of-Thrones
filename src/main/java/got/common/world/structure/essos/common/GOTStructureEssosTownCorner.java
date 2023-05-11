package got.common.world.structure.essos.common;

import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureEssosTownCorner extends GOTStructureEssosBase {
	public GOTStructureEssosTownCorner(boolean flag) {
		super(flag);
	}

	@Override
	public boolean canUseRedBricks() {
		return false;
	}

	@Override
	public boolean forceMonotypeWood() {
		return true;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -3; i1 <= 3; ++i1) {
				for (k1 = -3; k1 <= 3; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				for (j1 = 1; j1 <= 12; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("essos_town_corner");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockMetaAlias("BRICK2_SLAB", brick2SlabBlock, brick2SlabMeta);
		associateBlockMetaAlias("BRICK2_SLAB_INV", brick2SlabBlock, brick2SlabMeta | 8);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("GATE_METAL", gateMetalBlock);
		generateStrScan(world, random, 0, 0, 0);
		return true;
	}
}
