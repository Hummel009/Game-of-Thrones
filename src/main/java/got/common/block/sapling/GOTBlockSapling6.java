package got.common.block.sapling;

import java.util.Random;

import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class GOTBlockSapling6 extends GOTBlockSaplingBase {
	public GOTBlockSapling6() {
		setSaplingNames("mahogany", "willow", "cypress", "olive");
	}

	@Override
	public void growTree(World world, int i, int j, int k, Random random) {
		int k1;
		int i1;
		int meta = world.getBlockMetadata(i, j, k) & 7;
		WorldGenAbstractTree treeGen = null;
		int extraTrunkWidth = 0;
		int xOffset = 0;
		int zOffset = 0;
		switch (meta) {
		case 0:
			treeGen = GOTTreeType.MAHOGANY.create(true, random);
			break;
		case 1:
			treeGen = GOTTreeType.WILLOW.create(true, random);
			break;
		case 2:
			for (i1 = 0; i1 >= -1; --i1) {
				for (k1 = 0; k1 >= -1; --k1) {
					if (!this.isSameSapling(world, i + i1, j, k + k1, meta) || !this.isSameSapling(world, i + i1 + 1, j, k + k1, meta) || !this.isSameSapling(world, i + i1, j, k + k1 + 1, meta) || !this.isSameSapling(world, i + i1 + 1, j, k + k1 + 1, meta)) {
						continue;
					}
					treeGen = GOTTreeType.CYPRESS_LARGE.create(true, random);
					extraTrunkWidth = 1;
					xOffset = i1;
					zOffset = k1;
					break;
				}
				if (treeGen != null) {
					break;
				}
			}
			if (treeGen == null) {
				xOffset = 0;
				zOffset = 0;
				treeGen = GOTTreeType.CYPRESS.create(true, random);
			}
			break;
		case 3:
			for (i1 = 0; i1 >= -1; --i1) {
				for (k1 = 0; k1 >= -1; --k1) {
					if (!this.isSameSapling(world, i + i1, j, k + k1, meta) || !this.isSameSapling(world, i + i1 + 1, j, k + k1, meta) || !this.isSameSapling(world, i + i1, j, k + k1 + 1, meta) || !this.isSameSapling(world, i + i1 + 1, j, k + k1 + 1, meta)) {
						continue;
					}
					treeGen = GOTTreeType.OLIVE_LARGE.create(true, random);
					extraTrunkWidth = 1;
					xOffset = i1;
					zOffset = k1;
					break;
				}
				if (treeGen != null) {
					break;
				}
			}
			if (treeGen == null) {
				xOffset = 0;
				zOffset = 0;
				treeGen = GOTTreeType.OLIVE.create(true, random);
			}
			break;
		default:
			break;
		}
		for (i1 = 0; i1 <= extraTrunkWidth; ++i1) {
			for (k1 = 0; k1 <= extraTrunkWidth; ++k1) {
				world.setBlock(i + xOffset + i1, j, k + zOffset + k1, Blocks.air, 0, 4);
			}
		}
		if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset)) {
			for (i1 = 0; i1 <= extraTrunkWidth; ++i1) {
				for (k1 = 0; k1 <= extraTrunkWidth; ++k1) {
					world.setBlock(i + xOffset + i1, j, k + zOffset + k1, this, meta, 4);
				}
			}
		}
	}
}
