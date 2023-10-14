package got.common.world.structure.essos.asshai;

import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureAsshaiTownBench extends GOTStructureAsshaiBase {
	public GOTStructureAsshaiTownBench(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions) {
			for (int i12 = -2; i12 <= 2; ++i12) {
				for (int k1 = 0; k1 <= 2; ++k1) {
					int j1 = getTopBlock(world, i12, k1) - 1;
					if (isSurface(world, i12, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		int k1 = 0;
		int j1 = getTopBlock(world, 0, k1);
		setBlockAndMetadata(world, 0, j1, k1, brickSlabBlock, brickSlabMeta);
		setBlockAndMetadata(world, -1, j1, k1, brickStairBlock, 0);
		setBlockAndMetadata(world, 1, j1, k1, brickStairBlock, 1);
		for (i1 = -1; i1 <= 1; ++i1) {
			setGrassToDirt(world, i1, j1 - 1, k1);
			layFoundation(world, i1, j1 - 1, k1);
		}
		k1 = 2;
		j1 = getTopBlock(world, 0, k1);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, j1, k1, brickSlabBlock, brickSlabMeta | 8);
		}
		for (int i13 : new int[]{-2, 2}) {
			setBlockAndMetadata(world, i13, j1, k1, brickBlock, brickMeta);
			setGrassToDirt(world, i13, j1 - 1, k1);
			layFoundation(world, i13, j1 - 1, k1);
		}
		return true;
	}

	public void layFoundation(World world, int i, int j, int k) {
		for (int j1 = j; (j1 >= j || !isOpaque(world, i, j1, k)) && getY(j1) >= 0; --j1) {
			setBlockAndMetadata(world, i, j1, k, brickBlock, brickMeta);
			setGrassToDirt(world, i, j1 - 1, k);
		}
	}
}
