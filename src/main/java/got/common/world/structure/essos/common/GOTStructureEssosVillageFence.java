package got.common.world.structure.essos.common;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.world.structure.essos.braavos.GOTStructureBraavosBase;
import net.minecraft.world.World;

public class GOTStructureEssosVillageFence extends GOTStructureBraavosBase {
	public int leftExtent;
	public int rightExtent;

	public GOTStructureEssosVillageFence(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		this.setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		for (int i1 = -leftExtent; i1 <= rightExtent; ++i1) {
			int k1 = 0;
			int j1 = getTopBlock(world, i1, k1) - 1;
			if (!isSurface(world, i1, j1, k1) || isOpaque(world, i1, j1 + 1, k1)) {
				continue;
			}
			setBlockAndMetadata(world, i1, j1 + 1, k1, fenceBlock, fenceMeta);
		}
		return true;
	}

	public GOTStructureEssosVillageFence setLeftRightExtent(int l, int r) {
		leftExtent = l;
		rightExtent = r;
		return this;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		fenceBlock = GOTRegistry.fence2;
		fenceMeta = 2;
	}
}
