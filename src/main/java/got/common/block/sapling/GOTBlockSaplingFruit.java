package got.common.block.sapling;

import got.common.world.feature.GOTTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class GOTBlockSaplingFruit extends GOTBlockSaplingBase {
	public GOTBlockSaplingFruit() {
		setSaplingNames("apple", "pear", "cherry", "mango");
	}

	@Override
	public void growTree(World world, int i, int j, int k, Random random) {
		int meta = world.getBlockMetadata(i, j, k) & 7;
		WorldGenAbstractTree treeGen = null;
		switch (meta) {
			case 0:
				treeGen = GOTTreeType.APPLE.create(true, random);
				break;
			case 1:
				treeGen = GOTTreeType.PEAR.create(true, random);
				break;
			case 2:
				treeGen = GOTTreeType.CHERRY.create(true, random);
				break;
			case 3:
				treeGen = GOTTreeType.MANGO.create(true, random);
				break;
			default:
				break;
		}
		world.setBlock(i, j, k, Blocks.air, 0, 4);
		if (treeGen != null && !treeGen.generate(world, random, i, j, k)) {
			world.setBlock(i, j, k, this, meta, 4);
		}
	}
}