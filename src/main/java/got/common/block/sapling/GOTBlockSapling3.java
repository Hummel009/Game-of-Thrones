package got.common.block.sapling;

import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class GOTBlockSapling3 extends GOTBlockSaplingBase {
	public GOTBlockSapling3() {
		setSaplingNames("maple", "larch", "date_palm", "mangrove");
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
		switch (meta) {
			case 0:
				int[] partyTree = findPartyTree(world, i, j, k, this, meta);
				if (partyTree != null) {
					treeGen = GOTTreeType.MAPLE_PARTY.create(true, random);
					trunkPos = 1;
					trunkNeg = 1;
					xOffset = partyTree[0];
					zOffset = partyTree[1];
				}
				if (treeGen == null) {
					treeGen = (random.nextInt(10) == 0 ? GOTTreeType.MAPLE_LARGE : GOTTreeType.MAPLE).create(true, random);
					trunkPos = 0;
					trunkNeg = 0;
					xOffset = 0;
					zOffset = 0;
				}
				break;
			case 1:
				treeGen = GOTTreeType.LARCH.create(true, random);
				break;
			case 2:
				treeGen = GOTTreeType.DATE_PALM.create(true, random);
				break;
			case 3:
				treeGen = GOTTreeType.MANGROVE.create(true, random);
				break;
			default:
				break;
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