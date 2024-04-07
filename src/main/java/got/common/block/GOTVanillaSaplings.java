package got.common.block;

import got.common.block.sapling.GOTBlockSaplingBase;
import got.common.world.feature.GOTTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class GOTVanillaSaplings {
	private GOTVanillaSaplings() {
	}

	public static void growTree(World world, int i, int j, int k, Random random) {
		int[] partyTree;
		int i1;
		int k1;
		Block block = world.getBlock(i, j, k);
		int meta = world.getBlockMetadata(i, j, k) & 7;
		WorldGenAbstractTree treeGen = null;
		int trunkNeg = 0;
		int trunkPos = 0;
		int xOffset = 0;
		int zOffset = 0;
		if (meta == 0) {
			partyTree = GOTBlockSaplingBase.findPartyTree(world, i, j, k, block, 0);
			if (partyTree != null) {
				treeGen = GOTTreeType.OAK_PARTY.create(true, random);
				trunkPos = 1;
				trunkNeg = 1;
				xOffset = partyTree[0];
				zOffset = partyTree[1];
			}
			if (treeGen == null) {
				treeGen = random.nextInt(10) == 0 ? GOTTreeType.OAK_LARGE.create(true, random) : GOTTreeType.OAK.create(true, random);
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
			}
		}
		if (meta == 1) {
			for (int i12 = 0; i12 >= -1; --i12) {
				for (k1 = 0; k1 >= -1; --k1) {
					if (!isSameSapling(world, i + i12, j, k + k1, 1) || !isSameSapling(world, i + i12 + 1, j, k + k1, 1) || !isSameSapling(world, i + i12, j, k + k1 + 1, 1) || !isSameSapling(world, i + i12 + 1, j, k + k1 + 1, 1)) {
						continue;
					}
					treeGen = random.nextBoolean() ? GOTTreeType.SPRUCE_MEGA.create(true, random) : GOTTreeType.SPRUCE_MEGA_THIN.create(true, random);
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
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
				treeGen = GOTTreeType.SPRUCE.create(true, random);
			}
		}
		if (meta == 2) {
			partyTree = GOTBlockSaplingBase.findPartyTree(world, i, j, k, block, 2);
			if (partyTree != null) {
				treeGen = GOTTreeType.BIRCH_PARTY.create(true, random);
				trunkPos = 1;
				trunkNeg = 1;
				xOffset = partyTree[0];
				zOffset = partyTree[1];
			}
			if (treeGen == null) {
				treeGen = random.nextInt(10) == 0 ? GOTTreeType.BIRCH_LARGE.create(true, random) : GOTTreeType.BIRCH.create(true, random);
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
			}
		}
		if (meta == 3) {
			for (int i13 = 0; i13 >= -1; --i13) {
				for (k1 = 0; k1 >= -1; --k1) {
					if (!isSameSapling(world, i + i13, j, k + k1, 3) || !isSameSapling(world, i + i13 + 1, j, k + k1, 3) || !isSameSapling(world, i + i13, j, k + k1 + 1, 3) || !isSameSapling(world, i + i13 + 1, j, k + k1 + 1, 3)) {
						continue;
					}
					treeGen = GOTTreeType.JUNGLE_LARGE.create(true, random);
					trunkNeg = 0;
					trunkPos = 1;
					xOffset = i13;
					zOffset = k1;
					break;
				}
				if (treeGen != null) {
					break;
				}
			}
			if (treeGen == null) {
				trunkPos = 0;
				trunkNeg = 0;
				xOffset = 0;
				zOffset = 0;
				treeGen = GOTTreeType.JUNGLE.create(true, random);
			}
		}
		if (meta == 4) {
			treeGen = GOTTreeType.ACACIA.create(true, random);
		}
		if (meta == 5) {
			partyTree = GOTBlockSaplingBase.findPartyTree(world, i, j, k, block, 5);
			if (partyTree != null) {
				treeGen = GOTTreeType.DARK_OAK_PARTY.create(true, random);
				trunkPos = 1;
				trunkNeg = 1;
				xOffset = partyTree[0];
				zOffset = partyTree[1];
			}
			if (treeGen == null) {
				for (int i14 = 0; i14 >= -1; --i14) {
					for (int k12 = 0; k12 >= -1; --k12) {
						if (!isSameSapling(world, i + i14, j, k + k12, 5) || !isSameSapling(world, i + i14 + 1, j, k + k12, 5) || !isSameSapling(world, i + i14, j, k + k12 + 1, 5) || !isSameSapling(world, i + i14 + 1, j, k + k12 + 1, 5)) {
							continue;
						}
						treeGen = GOTTreeType.DARK_OAK.create(true, random);
						trunkNeg = 0;
						trunkPos = 1;
						xOffset = i14;
						zOffset = k12;
						break;
					}
					if (treeGen != null) {
						break;
					}
				}
			}
			if (treeGen == null) {
				return;
			}
		}
		for (i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
			for (k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
				world.setBlock(i + xOffset + i1, j, k + zOffset + k1, Blocks.air, 0, 4);
			}
		}
		if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset)) {
			for (i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
				for (k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
					world.setBlock(i + xOffset + i1, j, k + zOffset + k1, Blocks.sapling, meta, 4);
				}
			}
		}
	}

	public static boolean isSameSapling(IBlockAccess world, int i, int j, int k, int meta) {
		return GOTBlockSaplingBase.isSameSapling(world, i, j, k, Blocks.sapling, meta);
	}
}