package got.common.world.structure.essos.gold;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.entity.essos.gold.GOTEntityGoldenCaptain;
import got.common.entity.essos.gold.GOTEntityGoldenSpearman;
import got.common.entity.essos.gold.GOTEntityGoldenWarrior;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureGoldenCampWatchtower extends GOTStructureBase {
	private Block woodBlock;
	private int woodMeta;

	public GOTStructureGoldenCampWatchtower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int i12;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 0);
		if (restrictions) {
			for (int i13 = -4; i13 <= 4; ++i13) {
				for (int k12 = -4; k12 <= 4; ++k12) {
					int j12 = getTopBlock(world, i13, k12);
					Block block = getBlock(world, i13, j12 - 1, k12);
					if (block == Blocks.grass) {
						continue;
					}
					return false;
				}
			}
		}
		int randomWood = random.nextInt(4);
		Block trapdoorBlock;
		Block stairBlock;
		int fenceMeta;
		Block fenceBlock;
		int plankMeta;
		Block plankBlock;
		if (randomWood == 0) {
			woodBlock = Blocks.log;
			woodMeta = 0;
			plankBlock = Blocks.planks;
			plankMeta = 0;
			fenceBlock = Blocks.fence;
			fenceMeta = 0;
			stairBlock = Blocks.oak_stairs;
			trapdoorBlock = Blocks.trapdoor;
		} else {
			switch (randomWood) {
				case 1:
					woodBlock = Blocks.log;
					woodMeta = 1;
					plankBlock = Blocks.planks;
					plankMeta = 1;
					fenceBlock = Blocks.fence;
					fenceMeta = 0;
					stairBlock = Blocks.spruce_stairs;
					trapdoorBlock = GOTBlocks.trapdoorSpruce;
					break;
				case 2:
					woodBlock = GOTBlocks.wood2;
					woodMeta = 1;
					plankBlock = GOTBlocks.planks1;
					plankMeta = 9;
					fenceBlock = GOTBlocks.fence;
					fenceMeta = 9;
					stairBlock = GOTBlocks.stairsBeech;
					trapdoorBlock = GOTBlocks.trapdoorBeech;
					break;
				default:
					woodBlock = GOTBlocks.wood3;
					woodMeta = 0;
					plankBlock = GOTBlocks.planks1;
					plankMeta = 12;
					fenceBlock = GOTBlocks.fence;
					fenceMeta = 12;
					stairBlock = GOTBlocks.stairsMaple;
					trapdoorBlock = GOTBlocks.trapdoorMaple;
					break;
			}
		}
		generateSupportPillar(world, -3, 4, -3);
		generateSupportPillar(world, -3, 4, 3);
		generateSupportPillar(world, 3, 4, -3);
		generateSupportPillar(world, 3, 4, 3);
		for (i12 = -2; i12 <= 2; ++i12) {
			for (k1 = -2; k1 <= 2; ++k1) {
				for (int j13 = 5; j13 <= 19; ++j13) {
					setAir(world, i12, j13, k1);
				}
			}
		}
		for (j1 = 6; j1 <= 19; ++j1) {
			setBlockAndMetadata(world, -2, j1, -2, woodBlock, woodMeta);
			setBlockAndMetadata(world, -2, j1, 2, woodBlock, woodMeta);
			setBlockAndMetadata(world, 2, j1, -2, woodBlock, woodMeta);
			setBlockAndMetadata(world, 2, j1, 2, woodBlock, woodMeta);
		}
		for (j1 = 5; j1 <= 10; j1 += 5) {
			for (i1 = -3; i1 <= 3; ++i1) {
				for (int k13 = -3; k13 <= 3; ++k13) {
					setBlockAndMetadata(world, i1, j1, k13, plankBlock, plankMeta);
				}
			}
			for (i1 = -4; i1 <= 4; ++i1) {
				setBlockAndMetadata(world, i1, j1, -4, stairBlock, 2);
				setBlockAndMetadata(world, i1, j1, 4, stairBlock, 3);
			}
			for (k1 = -3; k1 <= 3; ++k1) {
				setBlockAndMetadata(world, -4, j1, k1, stairBlock, 1);
				setBlockAndMetadata(world, 4, j1, k1, stairBlock, 0);
			}
			for (i1 = -2; i1 <= 2; ++i1) {
				setBlockAndMetadata(world, i1, j1 + 1, -3, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, i1, j1 + 1, 3, fenceBlock, fenceMeta);
			}
			for (k1 = -2; k1 <= 2; ++k1) {
				setBlockAndMetadata(world, -3, j1 + 1, k1, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, 3, j1 + 1, k1, fenceBlock, fenceMeta);
			}
			setBlockAndMetadata(world, 0, j1 + 2, -3, Blocks.torch, 5);
			setBlockAndMetadata(world, 0, j1 + 2, 3, Blocks.torch, 5);
			setBlockAndMetadata(world, -3, j1 + 2, 0, Blocks.torch, 5);
			setBlockAndMetadata(world, 3, j1 + 2, 0, Blocks.torch, 5);
			spawnNPCAndSetHome(new GOTEntityGoldenCaptain(world), world, -1, j1 + 1, 0, 8);
		}
		for (i12 = -2; i12 <= 2; ++i12) {
			for (k1 = -2; k1 <= 2; ++k1) {
				int i2 = Math.abs(i12);
				int k2 = Math.abs(k1);
				if (i2 >= 2 && k2 >= 2) {
					continue;
				}
				setBlockAndMetadata(world, i12, 15, k1, plankBlock, plankMeta);
				if ((i2 >= 2 || k2 != 2) && i2 != 2) {
					continue;
				}
				setBlockAndMetadata(world, i12, 16, k1, fenceBlock, fenceMeta);
			}
		}
		setGrassToDirt(world, 0, 0, 0);
		for (j1 = 1; j1 <= 25; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, woodBlock, woodMeta);
			if (j1 > 15) {
				continue;
			}
			setBlockAndMetadata(world, 0, j1, -1, Blocks.ladder, 2);
		}
		setBlockAndMetadata(world, 0, 6, -1, trapdoorBlock, 0);
		setBlockAndMetadata(world, 0, 11, -1, trapdoorBlock, 0);
		setBlockAndMetadata(world, 0, 17, -2, Blocks.torch, 5);
		setBlockAndMetadata(world, 0, 17, 2, Blocks.torch, 5);
		setBlockAndMetadata(world, -2, 17, 0, Blocks.torch, 5);
		setBlockAndMetadata(world, 2, 17, 0, Blocks.torch, 5);
		placeChest(world, random, 0, 16, 1, 0, GOTChestContents.GOLDEN);
		setBlockAndMetadata(world, 0, 11, 1, GOTBlocks.alloyForge, 0);
		for (j1 = 17; j1 <= 18; ++j1) {
			setBlockAndMetadata(world, -2, j1, -2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, -2, j1, 2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 2, j1, -2, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 2, j1, 2, fenceBlock, fenceMeta);
		}
		for (int step = 0; step <= 1; ++step) {
			for (i1 = -2 + step; i1 <= 2 - step; ++i1) {
				setBlockAndMetadata(world, i1, 20 + step, -2 + step, stairBlock, 2);
				setBlockAndMetadata(world, i1, 20 + step, 2 - step, stairBlock, 3);
			}
			for (k1 = -1 + step; k1 <= 1 - step; ++k1) {
				setBlockAndMetadata(world, -2 + step, 20 + step, k1, stairBlock, 1);
				setBlockAndMetadata(world, 2 - step, 20 + step, k1, stairBlock, 0);
			}
		}
		placeWallBanner(world, -2, 15, 0, GOTItemBanner.BannerType.GOLDENCOMPANY, 3);
		placeWallBanner(world, 2, 15, 0, GOTItemBanner.BannerType.GOLDENCOMPANY, 1);
		placeWallBanner(world, 0, 15, -2, GOTItemBanner.BannerType.GOLDENCOMPANY, 2);
		placeWallBanner(world, 0, 15, 2, GOTItemBanner.BannerType.GOLDENCOMPANY, 0);
		for (j1 = 24; j1 <= 25; ++j1) {
			setBlockAndMetadata(world, 1, j1, 0, Blocks.wool, 10);
			setBlockAndMetadata(world, 2, j1, 1, Blocks.wool, 10);
			setBlockAndMetadata(world, 2, j1, 2, Blocks.wool, 10);
			setBlockAndMetadata(world, 3, j1, 3, Blocks.wool, 10);
		}
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass1(GOTEntityGoldenWarrior.class);
		respawner.setSpawnClass2(GOTEntityGoldenSpearman.class);
		respawner.setCheckRanges(80, -12, 0, 30);
		respawner.setSpawnRanges(80, -12, 0, 80);
		respawner.setSpawnInterval(1);
		respawner.setNoPlayerRange(80);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}

	private void generateSupportPillar(World world, int i, int j, int k) {
		int j1 = j;
		while (!isOpaque(world, i, j1, k) && getY(j1) >= 0) {
			setBlockAndMetadata(world, i, j1, k, woodBlock, woodMeta);
			setGrassToDirt(world, i, j1 - 1, i);
			--j1;
		}
	}
}