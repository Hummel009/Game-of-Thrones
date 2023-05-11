package got.common.world.structure.westeros.common;

import got.common.entity.other.GOTEntityNPC;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public abstract class GOTStructureWesterosMarketStall extends GOTStructureWesterosBase {
	protected GOTStructureWesterosMarketStall(boolean flag) {
		super(flag);
	}

	public abstract GOTEntityNPC createTrader(World var1);

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 3);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -2; i1 <= 2; ++i1) {
				for (int k1 = -2; k1 <= 2; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 5) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -2; i1 <= 2; ++i1) {
			for (int k1 = -2; k1 <= 2; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
				if (i2 == 2 && k2 == 2) {
					for (j1 = 1; j1 <= 3; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, fenceBlock, fenceMeta);
					}
				} else if (i2 == 2 || k2 == 2) {
					setBlockAndMetadata(world, i1, 1, k1, plankBlock, plankMeta);
					setBlockAndMetadata(world, i1, 3, k1, fenceBlock, fenceMeta);
				}
				generateRoof(world, random, i1, 4, k1);
			}
		}
		setBlockAndMetadata(world, -2, 1, 0, fenceGateBlock, 1);
		setBlockAndMetadata(world, -1, 1, -1, tableBlock, 0);
		setBlockAndMetadata(world, 1, 1, -1, Blocks.chest, 3);
		GOTEntityNPC trader = createTrader(world);
		spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
		return true;
	}

	public abstract void generateRoof(World var1, Random var2, int var3, int var4, int var5);
}