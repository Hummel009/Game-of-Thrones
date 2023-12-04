package got.common.world.structure.sothoryos.sothoryos;

import got.common.database.GOTBlocks;
import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSothoryosVillageTree extends GOTStructureSothoryosHouse {
	public GOTStructureSothoryosVillageTree(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		if (!super.generate(world, random, i, j, k, rotation)) {
			return false;
		}
		int i1;
		int k1;
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				layFoundation(world, i1, k1);
				for (int j1 = 1; j1 <= 12; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 == 3 || k2 == 3) {
					if (i2 == 3 && k2 == 3) {
						setBlockAndMetadata(world, i1, 1, k1, woodBlock, woodMeta);
						setBlockAndMetadata(world, i1, 2, k1, woodBlock, woodMeta);
						continue;
					}
					setBlockAndMetadata(world, i1, 1, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 2, k1, brickSlabBlock, brickSlabMeta);
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, GOTBlocks.mudGrass, 0);
				if (random.nextInt(2) != 0) {
					continue;
				}
				if (random.nextBoolean()) {
					setBlockAndMetadata(world, i1, 2, k1, Blocks.tallgrass, 1);
					continue;
				}
				setBlockAndMetadata(world, i1, 2, k1, Blocks.tallgrass, 2);
			}
		}
		int treeX = 0;
		int treeY = 2;
		int treeZ = 0;
		setAir(world, treeX, treeY, treeZ);
		for (int attempts = 0; attempts < 20; ++attempts) {
			GOTTreeType treeType = null;
			int randomTree = random.nextInt(4);
			if (randomTree == 0 || randomTree == 1) {
				treeType = GOTTreeType.JUNGLE;
			}
			if (randomTree == 2) {
				treeType = GOTTreeType.MANGO;
			}
			if (randomTree == 3) {
				treeType = GOTTreeType.BANANA;
			}
			if (treeType.create(notifyChanges, random).generate(world, random, getX(treeX, treeZ), getY(treeY), getZ(treeX, treeZ))) {
				break;
			}
		}
		return true;
	}

	@Override
	public int getOffset() {
		return 4;
	}
}
