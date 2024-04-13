package got.common.block.sapling;

import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class GOTBlockSapling8 extends GOTBlockSaplingBase {
	public GOTBlockSapling8() {
		saplingNames = new String[]{"plum", "redwood", "pomegranate", "palm"};
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
			treeGen = GOTTreeType.PLUM.create(true, random);
		}
		if (meta == 1) {
			int[] tree2x2;
			int[] tree4x4;
			int[] tree3x3;
			int[] tree5x5 = findSaplingSquare(world, i, j, k, this, 1, -2, 2, -4, 4);
			if (tree5x5 != null) {
				treeGen = GOTTreeType.REDWOOD_5.create(true, random);
				trunkNeg = 2;
				trunkPos = 2;
				xOffset = tree5x5[0];
				zOffset = tree5x5[1];
			}
			if (treeGen == null && (tree4x4 = findSaplingSquare(world, i, j, k, this, 1, -1, 2, -2, 1)) != null) {
				treeGen = GOTTreeType.REDWOOD_4.create(true, random);
				trunkNeg = 1;
				trunkPos = 2;
				xOffset = tree4x4[0];
				zOffset = tree4x4[1];
			}
			if (treeGen == null && (tree3x3 = findPartyTree(world, i, j, k, this, 1)) != null) {
				treeGen = GOTTreeType.REDWOOD_3.create(true, random);
				trunkNeg = 1;
				trunkPos = 1;
				xOffset = tree3x3[0];
				zOffset = tree3x3[1];
			}
			if (treeGen == null && (tree2x2 = findSaplingSquare(world, i, j, k, this, 1, 0, 1, -1, 0)) != null) {
				treeGen = GOTTreeType.REDWOOD_2.create(true, random);
				trunkNeg = 0;
				trunkPos = 1;
				xOffset = tree2x2[0];
				zOffset = tree2x2[1];
			}
			if (treeGen == null) {
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
				treeGen = GOTTreeType.REDWOOD.create(true, random);
			}
		}
		if (meta == 2) {
			treeGen = GOTTreeType.POMEGRANATE.create(true, random);
		}
		if (meta == 3) {
			treeGen = GOTTreeType.PALM.create(true, random);
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