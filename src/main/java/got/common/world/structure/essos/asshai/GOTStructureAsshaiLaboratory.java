package got.common.world.structure.essos.asshai;

import java.util.Random;

import got.common.database.*;
import got.common.entity.essos.asshai.GOTEntityAsshaiAlchemist;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureAsshaiLaboratory extends GOTStructureAsshaiBase {
	public GOTStructureAsshaiLaboratory(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int k1;
		int i12;
		this.setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i13 = -4; i13 <= 4; ++i13) {
				for (int k12 = 1; k12 <= 11; ++k12) {
					int j12 = getTopBlock(world, i13, k12) - 1;
					if (!isSurface(world, i13, j12, k12)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (k1 = 1; k1 <= 11; ++k1) {
			for (i1 = -4; i1 <= 4; ++i1) {
				boolean pillar = Math.abs(i1) == 4 && (k1 == 1 || k1 == 11);
				if (pillar) {
					for (j1 = 4; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i1, j1, k1, pillarBlock, pillarMeta);
						setGrassToDirt(world, i1, j1 - 1, k1);
					}
					continue;
				}
				for (j1 = 0; (j1 >= 0 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				if (Math.abs(i1) == 4 || k1 == 1 || k1 == 11) {
					for (j1 = 1; j1 <= 3; ++j1) {
						setBlockAndMetadata(world, i1, j1, k1, brickBlock, brickMeta);
					}
					setBlockAndMetadata(world, i1, 4, k1, brickWallBlock, brickWallMeta);
					continue;
				}
				for (j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (k1 = 3; k1 <= 7; k1 += 4) {
			for (i1 = -2; i1 <= 1; i1 += 3) {
				for (int k2 = k1; k2 <= k1 + 2; ++k2) {
					for (int i2 = i1; i2 <= i1 + 1; ++i2) {
						setBlockAndMetadata(world, i2, 0, k2, rockBlock, rockMeta);
					}
				}
			}
		}
		for (i12 = -1; i12 <= 1; ++i12) {
			for (j1 = 1; j1 <= 3; ++j1) {
				setBlockAndMetadata(world, i12, j1, 1, rockBlock, rockMeta);
			}
		}
		setBlockAndMetadata(world, 0, 1, 1, fenceGateBlock, 0);
		setAir(world, 0, 2, 1);
		for (k1 = 2; k1 <= 10; ++k1) {
			setBlockAndMetadata(world, -4, 4, k1, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, 4, 4, k1, brickWallBlock, brickWallMeta);
		}
		for (i12 = -3; i12 <= 3; ++i12) {
			setBlockAndMetadata(world, i12, 4, 1, brickWallBlock, brickWallMeta);
			setBlockAndMetadata(world, i12, 4, 11, brickWallBlock, brickWallMeta);
		}
		for (k1 = 2; k1 <= 10; ++k1) {
			for (i1 = -3; i1 <= 3; ++i1) {
				setBlockAndMetadata(world, i1, 5, k1, brickBlock, brickMeta);
			}
		}
		for (k1 = 2; k1 <= 10; ++k1) {
			setBlockAndMetadata(world, -4, 5, k1, brickStairBlock, 1);
			setBlockAndMetadata(world, 4, 5, k1, brickStairBlock, 0);
		}
		for (i12 = -4; i12 <= 4; ++i12) {
			setBlockAndMetadata(world, i12, 5, 1, brickStairBlock, 2);
			setBlockAndMetadata(world, i12, 5, 11, brickStairBlock, 3);
		}
		setBlockAndMetadata(world, -3, 1, 2, GOTRegistry.tableAsshai, 0);
		setBlockAndMetadata(world, -3, 1, 3, GOTRegistry.tableAsshai, 0);
		setBlockAndMetadata(world, -3, 1, 4, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 2, 4, brickWallBlock, brickWallMeta);
		for (k1 = 2; k1 <= 4; ++k1) {
			setBlockAndMetadata(world, -3, 3, k1, brickStairBlock, 0);
		}
		for (k1 = 2; k1 <= 6; k1 += 2) {
			setBlockAndMetadata(world, 3, 1, k1, Blocks.anvil, 0);
		}
		this.placeChest(world, random, 3, 1, 8, Blocks.chest, 5, GOTChestContents.ASSHAI);
		this.placeChest(world, random, 3, 1, 9, Blocks.chest, 5, GOTChestContents.ASSHAI);
		setBlockAndMetadata(world, -1, 2, 2, GOTRegistry.asshaiTorch, 3);
		setBlockAndMetadata(world, 1, 2, 2, GOTRegistry.asshaiTorch, 3);
		setBlockAndMetadata(world, -3, 2, 6, GOTRegistry.asshaiTorch, 2);
		setBlockAndMetadata(world, 3, 2, 6, GOTRegistry.asshaiTorch, 1);
		setBlockAndMetadata(world, -1, 1, 8, brickBlock, brickMeta);
		setBlockAndMetadata(world, -1, 2, 8, brickBlock, brickMeta);
		setBlockAndMetadata(world, -3, 1, 9, Blocks.lava, 0);
		setBlockAndMetadata(world, -2, 1, 9, Blocks.lava, 0);
		setBlockAndMetadata(world, -3, 1, 10, Blocks.lava, 0);
		setBlockAndMetadata(world, -2, 1, 10, Blocks.lava, 0);
		setBlockAndMetadata(world, -3, 3, 8, brickStairBlock, 2);
		setBlockAndMetadata(world, -2, 3, 8, brickStairBlock, 2);
		setBlockAndMetadata(world, -1, 3, 8, brickStairBlock, 2);
		setBlockAndMetadata(world, -1, 3, 9, brickStairBlock, 0);
		setBlockAndMetadata(world, -1, 3, 10, brickStairBlock, 0);
		setBlockAndMetadata(world, -3, 1, 8, GOTRegistry.alloyForge, 2);
		setBlockAndMetadata(world, -2, 1, 8, GOTRegistry.alloyForge, 2);
		setBlockAndMetadata(world, -1, 1, 9, GOTRegistry.alloyForge, 4);
		setBlockAndMetadata(world, -1, 1, 10, GOTRegistry.alloyForge, 4);
		world.setBlockMetadataWithNotify(-3, 1, 8, 2, 3);
		world.setBlockMetadataWithNotify(-2, 1, 8, 2, 3);
		world.setBlockMetadataWithNotify(-1, 1, 9, 5, 3);
		world.setBlockMetadataWithNotify(-1, 1, 10, 5, 3);
		setBlockAndMetadata(world, -3, 2, 8, barsBlock, 0);
		setBlockAndMetadata(world, -2, 2, 8, barsBlock, 0);
		setBlockAndMetadata(world, -1, 2, 9, barsBlock, 0);
		setBlockAndMetadata(world, -1, 2, 10, barsBlock, 0);
		for (i12 = -1; i12 <= 1; ++i12) {
			for (int k13 = -1; k13 <= 1; ++k13) {
				if (i12 == 0 && k13 == 0) {
					continue;
				}
				setBlockAndMetadata(world, -3 + i12, 4, 10 + k13, brickBlock, brickMeta);
				setBlockAndMetadata(world, -3 + i12, 5, 10 + k13, brickBlock, brickMeta);
				setBlockAndMetadata(world, -3 + i12, 6, 10 + k13, brickSlabBlock, brickSlabMeta);
			}
		}
		setAir(world, -3, 5, 10);
		GOTEntityAsshaiAlchemist blacksmith = new GOTEntityAsshaiAlchemist(world);
		spawnNPCAndSetHome(blacksmith, world, 0, 1, 6, 4);
		return true;
	}
}
