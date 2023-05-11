package got.common.block.sapling;

import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class GOTBlockSapling7 extends GOTBlockSaplingBase {
	public GOTBlockSapling7() {
		setSaplingNames("aspen", "green_oak", "fotinia", "almond");
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
			block0:
			for (i1 = 0; i1 >= -1; --i1) {
				for (k1 = 0; k1 >= -1; --k1) {
					if (!this.isSameSapling(world, i + i1, j, k + k1, meta) || !this.isSameSapling(world, i + i1 + 1, j, k + k1, meta) || !this.isSameSapling(world, i + i1, j, k + k1 + 1, meta) || !this.isSameSapling(world, i + i1 + 1, j, k + k1 + 1, meta)) {
						continue;
					}
					treeGen = GOTTreeType.ASPEN_LARGE.create(true, random);
					trunkNeg = 0;
					trunkPos = 1;
					xOffset = i1;
					zOffset = k1;
					break block0;
				}
			}
			if (treeGen == null) {
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
				treeGen = GOTTreeType.ASPEN.create(true, random);
			}
		}
		if (meta == 1) {
			int[] partyTree = GOTBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
			if (partyTree != null) {
				treeGen = GOTTreeType.ULTHOS_GREEN_OAK_LARGE.create(true, random);
				trunkPos = 1;
				trunkNeg = 1;
				xOffset = partyTree[0];
				zOffset = partyTree[1];
			}
			if (treeGen == null) {
				treeGen = GOTTreeType.ULTHOS_GREEN_OAK.create(true, random);
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
			}
		}
		if (meta == 2) {
			treeGen = GOTTreeType.FOTINIA.create(true, random);
		}
		if (meta == 3) {
			treeGen = GOTTreeType.ALMOND.create(true, random);
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
}
