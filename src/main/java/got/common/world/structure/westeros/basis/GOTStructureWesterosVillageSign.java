package got.common.world.structure.westeros.basis;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureWesterosVillageSign extends GOTStructureWesterosBase {

	public GOTStructureWesterosVillageSign(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		this.setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions && !isSurface(world, i1 = 0, getTopBlock(world, i1, k1 = 0) - 1, k1)) {
			return false;
		}
		for (int j12 = 0; (j12 >= 0 || !isOpaque(world, 0, j12, 0)) && getY(j12) >= 0; --j12) {
			setBlockAndMetadata(world, 0, j12, 0, woodBeamBlock, woodBeamMeta);
			setGrassToDirt(world, 0, j12 - 1, 0);
		}
		setBlockAndMetadata(world, 0, 1, 0, woodBeamBlock, woodBeamMeta);
		setBlockAndMetadata(world, 0, 2, 0, plankBlock, plankMeta);
		setBlockAndMetadata(world, 0, 3, 0, woodBeamBlock, woodBeamMeta);
		setBlockAndMetadata(world, 0, 4, 0, plankSlabBlock, plankSlabMeta);
		setBlockAndMetadata(world, -1, 3, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 1, 3, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 0, 3, -1, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 3, 1, Blocks.torch, 3);
		return true;
	}
}
