package got.common.world.structure.westeros.common;

import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class GOTStructureWesterosTownTrees extends GOTStructureWesterosBase {
	public GOTStructureWesterosTownTrees(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -6; i1 <= 6; ++i1) {
				for (k1 = -2; k1 <= 2; ++k1) {
					int j12 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j12, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -6; i1 <= 6; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, rockSlabDoubleBlock, rockSlabDoubleMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 10; ++j1) {
					setAir(world, i1, j1, k1);
				}
				if (i2 % 4 != 2 && k2 <= 1) {
					setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
				}
				if (i2 % 4 != 2 || k2 != 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, rockWallBlock, rockWallMeta);
				setBlockAndMetadata(world, i1, 2, k1, Blocks.torch, 5);
			}
		}
		GOTTreeType tree;
		if (hasSouthernWood()) {
			tree = getRandomSouthernTree(random);
		} else if (hasNorthernWood()) {
			tree = getRandomNorthernTree(random);
		} else {
			tree = getRandomStandardTree(random);
		}
		WorldGenAbstractTree treeGen = tree.create(notifyChanges, random);
		treeGen.generate(world, random, getX(0, 0), getY(1), getZ(0, 0));
		return true;
	}
}