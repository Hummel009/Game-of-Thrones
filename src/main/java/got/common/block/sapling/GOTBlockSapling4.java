package got.common.block.sapling;

import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class GOTBlockSapling4 extends GOTBlockSaplingBase {
	public GOTBlockSapling4() {
		setSaplingNames("chestnut", "baobab", "cedar", "fir");
	}

	@Override
	public void growTree(World world, int i, int j, int k, Random random) {
		int i1;
		int k1;
		int meta = world.getBlockMetadata(i, j, k) & 7;
		WorldGenAbstractTree treeGen = null;
		int trunkNeg = 0;
		int trunkPos = 0;
		int xOffset = 0;
		int zOffset = 0;
		if (meta == 0) {
			int[] partyTree = GOTBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
			if (partyTree != null) {
				treeGen = GOTTreeType.CHESTNUT_PARTY.create(true, random);
				trunkPos = 1;
				trunkNeg = 1;
				xOffset = partyTree[0];
				zOffset = partyTree[1];
			}
			if (treeGen == null) {
				treeGen = random.nextInt(10) == 0 ? GOTTreeType.CHESTNUT_LARGE.create(true, random) : GOTTreeType.CHESTNUT.create(true, random);
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
			}
		}
		if (meta == 1) {
			treeGen = GOTTreeType.BAOBAB.create(true, random);
		}
		if (meta == 2) {
			treeGen = GOTTreeType.CEDAR.create(true, random);
		}
		if (meta == 3) {
			treeGen = GOTTreeType.FIR.create(true, random);
		}
		for (i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
			for (k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
				world.setBlock(i + xOffset + i1, j, k + zOffset + k1, Blocks.air, 0, 4);
			}
		}
		if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset)) {
			for (i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
				for (k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
					world.setBlock(i + xOffset + i1, j, k + zOffset + k1, this, meta, 4);
				}
			}
		}
	}

	@Override
	public void incrementGrowth(World world, int i, int j, int k, Random random) {
		int meta = world.getBlockMetadata(i, j, k) & 7;
		if (meta == 1 && random.nextInt(4) > 0) {
			return;
		}
		super.incrementGrowth(world, i, j, k, random);
	}
}
