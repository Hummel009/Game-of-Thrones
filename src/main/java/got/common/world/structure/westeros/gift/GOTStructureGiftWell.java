package got.common.world.structure.westeros.gift;

import got.common.database.GOTBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureGiftWell extends GOTStructureGiftBase {
	public GOTStructureGiftWell(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int wellBottom;
		int i1;
		int j1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -2; i1 <= 2; ++i1) {
				for (k1 = -2; k1 <= 2; ++k1) {
					int j12 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j12, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k1 = -2; k1 <= 2; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, GOTBlocks.dirtPath, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i1, j1, k1);
				}
				if (i2 == 1 && k2 == 1) {
					setBlockAndMetadata(world, i1, 1, k1, logBlock, logMeta);
					for (j1 = 2; j1 <= 3; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, fenceBlock, fenceMeta);
					}
					setBlockAndMetadata(world, i1, 4, k1, plankSlabBlock, plankSlabMeta);
				}
				if (i2 == 0 && k2 == 1 || k2 == 0 && i2 == 1) {
					setBlockAndMetadata(world, i1, 4, k1, plankSlabBlock, plankSlabMeta | 8);
				}
				if (i2 != 0 || k2 != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 5, k1, plankSlabBlock, plankSlabMeta);
				for (j1 = 3; j1 <= 4; ++j1) {
					setBlockAndMetadata(world, i1, j1, k1, fenceBlock, fenceMeta);
				}
			}
		}
		setBlockAndMetadata(world, 0, 0, 0, GOTBlocks.gateWoodenCross, 0);
		int depth = random.nextInt(2);
		int waterDepth = 2 + random.nextInt(4);
		int wellTop = -1;
		for (j1 = wellBottom = wellTop - depth - waterDepth - 1; j1 <= wellTop; ++j1) {
			for (int i12 = -1; i12 <= 1; ++i12) {
				for (int k12 = -1; k12 <= 1; ++k12) {
					int i2 = Math.abs(i12);
					int k2 = Math.abs(k12);
					if (j1 == wellBottom) {
						setBlockAndMetadata(world, i12, j1, k12, GOTBlocks.dirtPath, 0);
						continue;
					}
					if (i2 == 0 && k2 == 0) {
						if (j1 <= wellBottom + waterDepth) {
							setBlockAndMetadata(world, i12, j1, k12, Blocks.water, 0);
							continue;
						}
						setAir(world, i12, j1, k12);
						continue;
					}
					setBlockAndMetadata(world, i12, j1, k12, GOTBlocks.dirtPath, 0);
				}
			}
		}
		for (j1 = wellBottom + waterDepth + 1; j1 <= wellTop; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, Blocks.ladder, 2);
		}
		return true;
	}
}
