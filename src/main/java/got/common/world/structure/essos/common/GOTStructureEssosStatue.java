package got.common.world.structure.essos.common;

import java.util.Random;

import net.minecraft.world.World;

public class GOTStructureEssosStatue extends GOTStructureEssosBase {
	public GOTStructureEssosStatue(boolean flag) {
		super(flag);
	}

	public void assocStatueBlocks() {
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("BRICK2", brick2Block, brick2Meta);
		associateBlockMetaAlias("BRICK2_SLAB", brick2SlabBlock, brick2SlabMeta);
		associateBlockMetaAlias("BRICK2_SLAB_INV", brick2SlabBlock, brick2SlabMeta | 8);
		associateBlockAlias("BRICK2_STAIR", brick2StairBlock);
		associateBlockMetaAlias("BRICK2_WALL", brick2WallBlock, brick2WallMeta);
		associateBlockMetaAlias("BRICK2_CARVED", brick2CarvedBlock, brick2CarvedMeta);
		associateBlockMetaAlias("PILLAR2", pillar2Block, pillar2Meta);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -5; i1 <= 5; ++i1) {
				for (k1 = -5; k1 <= 5; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k1 = -5; k1 <= 5; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 == 5 && k2 == 5) {
					continue;
				}
				for (int j1 = 1; j1 <= 10; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("essos_statue_base");
		assocStatueBlocks();
		generateStrScan(world, random, 0, 0, 0);
		String statue = getRandomStatueStrscan(random);
		loadStrScan(statue);
		assocStatueBlocks();
		generateStrScan(world, random, 0, 4, 0);
		return true;
	}

	public String getRandomStatueStrscan(Random random) {
		String[] statues = { "mumak", "bird", "snake" };
		return "essos_statue_" + statues[random.nextInt(statues.length)];
	}

	@Override
	public boolean hasRedSandstone() {
		return false;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
	}
}
