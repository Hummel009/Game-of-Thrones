package got.common.world.structure.sothoryos.summer;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSummerVillageSign extends GOTStructureSummerBase {
	public GOTStructureSummerVillageSign(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions) {
			int i1 = 0;
			int k1 = 0;
			int j1 = getTopBlock(world, i1, 0) - 1;
			if (!isSurface(world, 0, j1, k1)) {
				return false;
			}
		}
		for (int j12 = 0; (j12 >= 0 || !isOpaque(world, 0, j12, 0)) && getY(j12) >= 0; --j12) {
			setBlockAndMetadata(world, 0, j12, 0, woodBlock, woodMeta);
			setGrassToDirt(world, 0, j12 - 1, 0);
		}
		setBlockAndMetadata(world, 0, 1, 0, woodBlock, woodMeta);
		setBlockAndMetadata(world, 0, 2, 0, woodBlock, woodMeta);
		setBlockAndMetadata(world, 0, 3, 0, woodBlock, woodMeta);
		setBlockAndMetadata(world, 0, 4, 0, fenceBlock, fenceMeta);
		placeSkull(world, random, 0, 5, 0);
		setBlockAndMetadata(world, -1, 3, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 1, 3, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 0, 3, -1, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 3, 1, Blocks.torch, 3);
		return true;
	}
}
