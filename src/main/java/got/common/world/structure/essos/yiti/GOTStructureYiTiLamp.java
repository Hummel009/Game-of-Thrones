package got.common.world.structure.essos.yiti;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureYiTiLamp extends GOTStructureYiTiBase {
	public GOTStructureYiTiLamp(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -1; i1 <= 1; ++i1) {
				for (k1 = -1; k1 <= 1; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = -1; k1 <= 1; ++k1) {
				int j1;
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					if (i2 == 1 && k2 == 1) {
						setBlockAndMetadata(world, i1, j1, k1, pillarBlock, pillarMeta);
					} else {
						setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				if (i2 == 0 && k2 == 0) {
					setBlockAndMetadata(world, i1, 1, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 2, k1, brickBlock, brickMeta);
				}
				if (i2 + k2 != 1) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, brickWallBlock, brickWallMeta);
			}
		}
		setBlockAndMetadata(world, 0, 3, 0, Blocks.glowstone, 0);
		setBlockAndMetadata(world, 0, 3, -1, Blocks.trapdoor, 4);
		setBlockAndMetadata(world, 0, 3, 1, Blocks.trapdoor, 5);
		setBlockAndMetadata(world, -1, 3, 0, Blocks.trapdoor, 7);
		setBlockAndMetadata(world, 1, 3, 0, Blocks.trapdoor, 6);
		return true;
	}
}
