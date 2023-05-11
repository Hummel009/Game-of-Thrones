package got.common.block.sapling;

import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class GOTBlockSapling9 extends GOTBlockSaplingBase {
	public GOTBlockSapling9() {
		setSaplingNames("dragon", "kanuka", "weirwood", "oak");
	}

	@Override
	public void growTree(World world, int i, int j, int k, Random random) {
		int i1;
		int meta = world.getBlockMetadata(i, j, k) & 7;
		WorldGenAbstractTree treeGen = null;
		int trunkNeg = 0;
		int trunkPos = 0;
		int xOffset = 0;
		int zOffset = 0;
		if (meta == 0) {
			int[] tree3x3;
			int[] tree5x5 = GOTBlockSaplingBase.findSaplingSquare(world, i, j, k, this, 0, -2, 2, -4, 4);
			if (tree5x5 != null) {
				treeGen = GOTTreeType.DRAGONBLOOD_HUGE.create(true, random);
				trunkNeg = 2;
				trunkPos = 2;
				xOffset = tree5x5[0];
				zOffset = tree5x5[1];
			}
			if (treeGen == null && (tree3x3 = GOTBlockSaplingBase.findPartyTree(world, i, j, k, this, 0)) != null) {
				treeGen = GOTTreeType.DRAGONBLOOD_LARGE.create(true, random);
				trunkNeg = 1;
				trunkPos = 1;
				xOffset = tree3x3[0];
				zOffset = tree3x3[1];
			}
			if (treeGen == null) {
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
				treeGen = GOTTreeType.DRAGONBLOOD.create(true, random);
			}
		}
		if (meta == 1) {
			treeGen = GOTTreeType.KANUKA.create(true, random);
		}
		if (meta == 2) {
			treeGen = GOTTreeType.WEIRWOOD.create(true, random);
		}
		if (meta == 3) {
			treeGen = GOTTreeType.OAK_GIANT.create(true, random);
		}
		for (i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
			for (int k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
				world.setBlock(i + xOffset + i1, j, k + zOffset + k1, Blocks.air, 0, 4);
			}
		}
		if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset)) {
			for (i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
				for (int k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
					world.setBlock(i + xOffset + i1, j, k + zOffset + k1, this, meta, 4);
				}
			}
		}
	}
}
