package got.common.world.structure.essos.common;

import java.util.Random;

import net.minecraft.world.World;

public class GOTStructureEssosWell extends GOTStructureEssosBase {
	public GOTStructureEssosWell(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -2; i1 <= 2; ++i1) {
				for (k1 = -2; k1 <= 2; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				for (j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("essos_well");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockMetaAlias("BRICK2_SLAB", brick2SlabBlock, brick2SlabMeta);
		associateBlockMetaAlias("BRICK2_SLAB_INV", brick2SlabBlock, brick2SlabMeta | 8);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		generateStrScan(world, random, 0, 0, 0);
		return true;
	}
}
