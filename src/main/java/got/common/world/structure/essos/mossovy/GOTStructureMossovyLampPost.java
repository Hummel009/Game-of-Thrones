/*
 * Decompiled with CFR 0.148.
 *
 * Could not onInit the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.world.World
 */
package got.common.world.structure.essos.mossovy;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureMossovyLampPost extends GOTStructureMossovyBase {
	public GOTStructureMossovyLampPost(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions && !isSurface(world, i1 = 0, getTopBlock(world, i1, k1 = 0) - 1, k1)) {
			return false;
		}
		for (j1 = 0; (j1 >= 0 || !isOpaque(world, 0, j1, 0)) && getY(j1) >= 0; --j1) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, 0, j1, 0, Blocks.cobblestone, 0);
			} else {
				setBlockAndMetadata(world, 0, j1, 0, Blocks.mossy_cobblestone, 0);
			}
			setGrassToDirt(world, 0, j1 - 1, 0);
		}
		if (random.nextBoolean()) {
			setBlockAndMetadata(world, 0, 1, 0, Blocks.cobblestone_wall, 0);
		} else {
			setBlockAndMetadata(world, 0, 1, 0, Blocks.cobblestone_wall, 1);
		}
		for (j1 = 2; j1 <= 3; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, 0, 4, 0, Blocks.torch, 5);
		return true;
	}
}
