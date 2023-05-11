package got.common.block.sapling;

import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class GOTBlockSapling2 extends GOTBlockSaplingBase {
	public GOTBlockSapling2() {
		setSaplingNames("aramant", "beech", "holly", "banana");
	}

	@Override
	public void growTree(World world, int i, int j, int k, Random random) {
		int k1;
		int i1;
		int meta = world.getBlockMetadata(i, j, k) & 7;
		WorldGenAbstractTree treeGen = null;
		int trunkNeg = 0;
		int trunkPos = 0;
		int xOffset = 0;
		int zOffset = 0;
		switch (meta) {
			case 0:
				treeGen = GOTTreeType.ARAMANT.create(true, random);
				break;
			case 1:
				int[] partyTree = GOTBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
				if (partyTree != null) {
					treeGen = GOTTreeType.BEECH_PARTY.create(true, random);
					trunkPos = 1;
					trunkNeg = 1;
					xOffset = partyTree[0];
					zOffset = partyTree[1];
				}
				if (treeGen == null) {
					treeGen = random.nextInt(10) == 0 ? GOTTreeType.BEECH_LARGE.create(true, random) : GOTTreeType.BEECH.create(true, random);
					trunkPos = 0;
					trunkNeg = 0;
					xOffset = 0;
					zOffset = 0;
				}
				break;
			case 2:
				for (int i12 = 0; i12 >= -1; --i12) {
					for (k1 = 0; k1 >= -1; --k1) {
						if (!isSameSapling(world, i + i12, j, k + k1, meta) || !isSameSapling(world, i + i12 + 1, j, k + k1, meta) || !isSameSapling(world, i + i12, j, k + k1 + 1, meta) || !isSameSapling(world, i + i12 + 1, j, k + k1 + 1, meta)) {
							continue;
						}
						treeGen = GOTTreeType.HOLLY_LARGE.create(true, random);
						trunkNeg = 0;
						trunkPos = 1;
						xOffset = i12;
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
					treeGen = GOTTreeType.HOLLY.create(true, random);
				}
				break;
			case 3:
				treeGen = GOTTreeType.BANANA.create(true, random);
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
