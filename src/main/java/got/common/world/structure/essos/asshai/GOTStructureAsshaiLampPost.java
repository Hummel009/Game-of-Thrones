package got.common.world.structure.essos.asshai;

import got.common.database.GOTRegistry;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureAsshaiLampPost extends GOTStructureAsshaiBase {
	public GOTStructureAsshaiLampPost(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int i1;
		this.setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions && !isSurface(world, i1 = 0, getTopBlock(world, i1, k1 = 0) - 1, k1)) {
			return false;
		}
		for (j1 = 0; (j1 >= 0 || !isOpaque(world, 0, j1, 0)) && getY(j1) >= 0; --j1) {
			setBlockAndMetadata(world, 0, j1, 0, brickBlock, brickMeta);
			setGrassToDirt(world, 0, j1 - 1, 0);
		}
		setBlockAndMetadata(world, 0, 1, 0, pillarBlock, pillarMeta);
		for (j1 = 2; j1 <= 4; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, brickWallBlock, brickWallMeta);
		}
		setBlockAndMetadata(world, 0, 5, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, -1, 5, 0, GOTRegistry.asshaiTorch, 1);
		setBlockAndMetadata(world, 1, 5, 0, GOTRegistry.asshaiTorch, 2);
		setBlockAndMetadata(world, 0, 5, -1, GOTRegistry.asshaiTorch, 4);
		setBlockAndMetadata(world, 0, 5, 1, GOTRegistry.asshaiTorch, 3);
		return true;
	}
}
