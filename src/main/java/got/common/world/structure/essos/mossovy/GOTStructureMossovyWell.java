package got.common.world.structure.essos.mossovy;

import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureMossovyWell extends GOTStructureMossovyBase {
	public GOTStructureMossovyWell(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -1; i1 <= 2; ++i1) {
				for (k1 = -1; k1 <= 2; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -1; i1 <= 2; ++i1) {
			for (k1 = -1; k1 <= 2; ++k1) {
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("mossovy_well");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		generateStrScan(world, random, 0, 0, 0);
		return true;
	}
}
