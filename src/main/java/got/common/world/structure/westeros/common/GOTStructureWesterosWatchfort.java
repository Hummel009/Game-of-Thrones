package got.common.world.structure.westeros.common;

import got.common.database.GOTBlocks;
import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityNPCRespawner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureWesterosWatchfort extends GOTStructureWesterosBase {
	public GOTStructureWesterosWatchfort(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 9);
		setupRandomBlocks(random);
		int j15;
		int k14;
		if (restrictions) {
			int x1 = -6;
			int x2 = 6;
			int z1 = -6;
			int z2 = 34;
			for (int i18 = x1; i18 <= x2; ++i18) {
				for (k14 = z1; k14 <= z2; ++k14) {
					j15 = getTopBlock(world, i18, k14) - 1;
					if (isSurface(world, i18, j15, k14)) {
						continue;
					}
					return false;
				}
			}
		}
		int i17;
		int j13;
		int k13;
		for (i17 = -5; i17 <= 5; ++i17) {
			for (j13 = 1; j13 <= 11; ++j13) {
				for (k13 = -5; k13 <= 5; ++k13) {
					if (Math.abs(i17) == 5 && Math.abs(k13) == 5) {
						setBlockAndMetadata(world, i17, j13, k13, pillar2Block, pillar2Meta);
						continue;
					}
					placeRandomBrick(world, random, i17, j13, k13);
				}
			}
		}
		for (i17 = -6; i17 <= 6; ++i17) {
			setBlockAndMetadata(world, i17, 1, -6, brick2StairBlock, 2);
			setBlockAndMetadata(world, i17, 1, 6, brick2StairBlock, 3);
		}
		for (int k15 = -5; k15 <= 5; ++k15) {
			setBlockAndMetadata(world, -6, 1, k15, brick2StairBlock, 1);
			setBlockAndMetadata(world, 6, 1, k15, brick2StairBlock, 0);
		}
		int j1;
		int k12;
		for (i17 = -6; i17 <= 6; ++i17) {
			for (k12 = -6; k12 <= 6; ++k12) {
				j1 = 0;
				while (!isOpaque(world, i17, j1, k12) && getY(j1) >= 0) {
					placeRandomBrick(world, random, i17, j1, k12);
					setGrassToDirt(world, i17, j1 - 1, k12);
					--j1;
				}
			}
		}
		for (i17 = -4; i17 <= 4; ++i17) {
			for (k12 = -4; k12 <= 4; ++k12) {
				for (j1 = 2; j1 <= 5; ++j1) {
					setAir(world, i17, j1, k12);
				}
				for (j1 = 7; j1 <= 10; ++j1) {
					setAir(world, i17, j1, k12);
				}
			}
		}
		int[] i19 = {4, 9};
		k12 = i19.length;
		int j12;
		for (j1 = 0; j1 < k12; ++j1) {
			j12 = i19[j1];
			setBlockAndMetadata(world, -4, j12, -2, Blocks.torch, 2);
			setBlockAndMetadata(world, -4, j12, 2, Blocks.torch, 2);
			setBlockAndMetadata(world, 4, j12, -2, Blocks.torch, 1);
			setBlockAndMetadata(world, 4, j12, 2, Blocks.torch, 1);
			setBlockAndMetadata(world, -2, j12, -4, Blocks.torch, 3);
			setBlockAndMetadata(world, 2, j12, -4, Blocks.torch, 3);
			setBlockAndMetadata(world, -2, j12, 4, Blocks.torch, 4);
			setBlockAndMetadata(world, 2, j12, 4, Blocks.torch, 4);
		}
		int i15;
		for (i15 = -4; i15 <= 4; ++i15) {
			for (j13 = 12; j13 <= 16; ++j13) {
				for (k13 = -4; k13 <= 4; ++k13) {
					if (Math.abs(i15) == 4 && Math.abs(k13) == 4) {
						setBlockAndMetadata(world, i15, j13, k13, pillar2Block, pillar2Meta);
						continue;
					}
					placeRandomBrick(world, random, i15, j13, k13);
				}
			}
		}
		for (i15 = -5; i15 <= 5; ++i15) {
			setBlockAndMetadata(world, i15, 12, -5, brick2StairBlock, 2);
			setBlockAndMetadata(world, i15, 12, 5, brick2StairBlock, 3);
		}
		int k1;
		for (k1 = -4; k1 <= 4; ++k1) {
			setBlockAndMetadata(world, -5, 12, k1, brick2StairBlock, 1);
			setBlockAndMetadata(world, 5, 12, k1, brick2StairBlock, 0);
		}
		for (i15 = -3; i15 <= 3; ++i15) {
			for (k12 = -3; k12 <= 3; ++k12) {
				for (j1 = 12; j1 <= 15; ++j1) {
					setAir(world, i15, j1, k12);
				}
			}
		}
		setBlockAndMetadata(world, -3, 14, -2, Blocks.torch, 2);
		setBlockAndMetadata(world, -3, 14, 2, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 14, -2, Blocks.torch, 1);
		setBlockAndMetadata(world, 3, 14, 2, Blocks.torch, 1);
		setBlockAndMetadata(world, -2, 14, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 14, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, -2, 14, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 14, 3, Blocks.torch, 4);
		for (i15 = -4; i15 <= 4; ++i15) {
			placeRandomWall(world, random, i15, 17, -4);
			placeRandomWall(world, random, i15, 17, 4);
		}
		for (k1 = -4; k1 <= 4; ++k1) {
			placeRandomWall(world, random, -4, 17, k1);
			placeRandomWall(world, random, 4, 17, k1);
		}
		int[] k16 = {-4, 4};
		k12 = k16.length;
		int i162;
		for (j1 = 0; j1 < k12; ++j1) {
			i162 = k16[j1];
			for (int k17 : new int[]{-4, 4}) {
				for (int j16 = 17; j16 <= 19; ++j16) {
					setBlockAndMetadata(world, i162, j16, k17, pillar2Block, pillar2Meta);
				}
				placeRandomBrick(world, random, i162, 20, k17);
			}
		}
		setBlockAndMetadata(world, -4, 19, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 4, 19, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, -4, 19, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, 4, 19, 3, Blocks.torch, 4);
		int i12;
		for (i12 = -2; i12 <= 2; ++i12) {
			for (k12 = -2; k12 <= 2; ++k12) {
				setBlockAndMetadata(world, i12, 21, k12, brick2Block, brick2Meta);
				if (Math.abs(i12) <= 1 && Math.abs(k12) <= 1) {
					setBlockAndMetadata(world, i12, 22, k12, brick2Block, brick2Meta);
					continue;
				}
				setBlockAndMetadata(world, i12, 22, k12, brick2SlabBlock, brick2SlabMeta);
			}
		}
		for (i12 = -5; i12 <= 5; ++i12) {
			for (k12 = -5; k12 <= 5; ++k12) {
				setBlockAndMetadata(world, i12, 21, k12, brick2SlabBlock, brick2SlabMeta);
			}
		}
		int[] i110 = {-4, 4};
		k12 = i110.length;
		for (j1 = 0; j1 < k12; ++j1) {
			i162 = i110[j1];
			for (int k17 : new int[]{-4, 4}) {
				setBlockAndMetadata(world, i162, 20, k17 - 1, brick2StairBlock, 6);
				setBlockAndMetadata(world, i162, 20, k17 + 1, brick2StairBlock, 7);
				for (int k2 = k17 - 1; k2 <= k17 + 1; ++k2) {
					setBlockAndMetadata(world, i162 - 1, 20, k2, brick2StairBlock, 5);
					setBlockAndMetadata(world, i162 + 1, 20, k2, brick2StairBlock, 4);
				}
			}
		}
		setBlockAndMetadata(world, -4, 21, -4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -4, 21, -3, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -4, 21, -2, brick2StairBlock, 1);
		setBlockAndMetadata(world, -4, 21, -1, brick2StairBlock, 3);
		setBlockAndMetadata(world, -4, 21, 0, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -4, 21, 1, brick2StairBlock, 2);
		setBlockAndMetadata(world, -4, 21, 2, brick2StairBlock, 1);
		setBlockAndMetadata(world, -4, 21, 3, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -4, 21, 4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -3, 21, -4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -3, 21, -3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -3, 21, -2, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -3, 21, -1, brick2StairBlock, 1);
		setBlockAndMetadata(world, -3, 21, 0, brick2StairBlock, 1);
		setBlockAndMetadata(world, -3, 21, 1, brick2StairBlock, 1);
		setBlockAndMetadata(world, -3, 21, 2, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -3, 21, 3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -3, 21, 4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 4, 21, -4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 4, 21, -3, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 4, 21, -2, brick2StairBlock, 0);
		setBlockAndMetadata(world, 4, 21, -1, brick2StairBlock, 3);
		setBlockAndMetadata(world, 4, 21, 0, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 4, 21, 1, brick2StairBlock, 2);
		setBlockAndMetadata(world, 4, 21, 2, brick2StairBlock, 0);
		setBlockAndMetadata(world, 4, 21, 3, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 4, 21, 4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 3, 21, -4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 3, 21, -3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, 3, 21, -2, brick2Block, brick2Meta);
		setBlockAndMetadata(world, 3, 21, -1, brick2StairBlock, 0);
		setBlockAndMetadata(world, 3, 21, 0, brick2StairBlock, 0);
		setBlockAndMetadata(world, 3, 21, 1, brick2StairBlock, 0);
		setBlockAndMetadata(world, 3, 21, 2, brick2Block, brick2Meta);
		setBlockAndMetadata(world, 3, 21, 3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, 3, 21, 4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -2, 21, 4, brick2StairBlock, 3);
		setBlockAndMetadata(world, -1, 21, 4, brick2StairBlock, 0);
		setBlockAndMetadata(world, 0, 21, 4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 1, 21, 4, brick2StairBlock, 1);
		setBlockAndMetadata(world, 2, 21, 4, brick2StairBlock, 3);
		setBlockAndMetadata(world, -2, 21, 3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -1, 21, 3, brick2StairBlock, 3);
		setBlockAndMetadata(world, 0, 21, 3, brick2StairBlock, 3);
		setBlockAndMetadata(world, 1, 21, 3, brick2StairBlock, 3);
		setBlockAndMetadata(world, 2, 21, 3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -2, 21, -4, brick2StairBlock, 2);
		setBlockAndMetadata(world, -1, 21, -4, brick2StairBlock, 0);
		setBlockAndMetadata(world, 0, 21, -4, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 1, 21, -4, brick2StairBlock, 1);
		setBlockAndMetadata(world, 2, 21, -4, brick2StairBlock, 2);
		setBlockAndMetadata(world, -2, 21, -3, brick2Block, brick2Meta);
		setBlockAndMetadata(world, -1, 21, -3, brick2StairBlock, 2);
		setBlockAndMetadata(world, 0, 21, -3, brick2StairBlock, 2);
		setBlockAndMetadata(world, 1, 21, -3, brick2StairBlock, 2);
		setBlockAndMetadata(world, 2, 21, -3, brick2Block, brick2Meta);
		placeBarredWindowOnZ(world, -5, 3, 0);
		placeBarredWindowOnZ(world, 5, 3, 0);
		placeBarredWindowOnX(world, 0, 3, -5);
		placeBarredWindowOnX(world, 0, 3, 5);
		placeBarredWindowOnZ(world, -5, 8, 0);
		placeBarredWindowOnZ(world, 5, 8, 0);
		placeBarredWindowOnX(world, 0, 8, -5);
		placeBarredWindowOnX(world, 0, 8, 5);
		placeBarredWindowOnZ(world, -4, 13, 0);
		placeBarredWindowOnZ(world, 4, 13, 0);
		placeBarredWindowOnX(world, 0, 13, -4);
		placeBarredWindowOnX(world, 0, 13, 4);
		int i1;
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k12 = -8; k12 <= -7; ++k12) {
				j1 = 0;
				while (!isOpaque(world, i1, j1, k12) && getY(j1) >= 0) {
					placeRandomBrick(world, random, i1, j1, k12);
					setGrassToDirt(world, i1, j1 - 1, k12);
					--j1;
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			if (Math.abs(i1) == 2) {
				for (j13 = 1; j13 <= 4; ++j13) {
					setBlockAndMetadata(world, i1, j13, -6, pillarBlock, pillarMeta);
				}
			}
			setBlockAndMetadata(world, i1, 5, -6, brick2SlabBlock, brick2SlabMeta);
			placeRandomStairs(world, random, i1, 1, -8, 2);
		}
		placeWallBanner(world, 0, 7, -5, bannerType, 2);
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k12 = -7; k12 <= -6; ++k12) {
				placeRandomBrick(world, random, i1, 1, k12);
			}
			placeRandomBrick(world, random, i1, 1, -5);
			setAir(world, i1, 2, -5);
			setAir(world, i1, 3, -5);
			setAir(world, i1, 4, -5);
		}
		placeRandomStairs(world, random, -2, 1, -7, 1);
		placeRandomStairs(world, random, 2, 1, -7, 0);
		for (i1 = -1; i1 <= 1; ++i1) {
			for (j13 = 2; j13 <= 4; ++j13) {
				setBlockAndMetadata(world, i1, j13, -6, gateBlock, 3);
			}
		}
		placeRandomSlab(world, random, -4, 2, -4, true);
		placeBarrel(world, random, -4, 3, -4, 4, GOTFoods.WESTEROS_DRINK);
		placeRandomSlab(world, random, -4, 2, -3, true);
		placeBarrel(world, random, -4, 3, -3, 4, GOTFoods.WESTEROS_DRINK);
		placeChest(world, random, -4, 2, -2, GOTBlocks.chestStone, 4, getChestContents());
		placeRandomSlab(world, random, 4, 2, -4, true);
		placeBarrel(world, random, 4, 3, -4, 5, GOTFoods.WESTEROS_DRINK);
		placeRandomSlab(world, random, 4, 2, -3, true);
		placeBarrel(world, random, 4, 3, -3, 5, GOTFoods.WESTEROS_DRINK);
		placeChest(world, random, 4, 2, -2, GOTBlocks.chestStone, 5, getChestContents());
		setBlockAndMetadata(world, -4, 2, 4, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 4, 2, 4, tableBlock, 0);
		int step;
		int j2;
		for (i1 = -1; i1 <= 1; ++i1) {
			for (step = 0; step <= 3; ++step) {
				k13 = -1 + step;
				j12 = 2 + step;
				setAir(world, i1, 6, k13);
				for (j2 = 2; j2 < j12; ++j2) {
					placeRandomBrick(world, random, i1, j2, k13);
				}
				placeRandomStairs(world, random, i1, j12, k13, 2);
			}
			placeRandomStairs(world, random, i1, 6, 3, 2);
		}
		placeChest(world, random, 0, 2, 2, GOTBlocks.chestStone, 3, getChestContents());
		setAir(world, 0, 3, 2);
		setBlockAndMetadata(world, 0, 7, -4, GOTBlocks.commandTable, 0);
		int[] i111 = {-3, 3};
		step = i111.length;
		for (k13 = 0; k13 < step; ++k13) {
			i162 = i111[k13];
			for (int step2 = 0; step2 <= 4; ++step2) {
				k14 = 2 - step2;
				j15 = 7 + step2;
				setAir(world, i162, 11, k14);
				for (int j22 = 7; j22 < j15; ++j22) {
					placeRandomBrick(world, random, i162, j22, k14);
				}
				placeRandomStairs(world, random, i162, j15, k14, 3);
			}
		}
		int i14;
		for (i14 = -1; i14 <= 1; ++i14) {
			for (step = 0; step <= 3; ++step) {
				k13 = -2 + step;
				j12 = 12 + step;
				setAir(world, i14, 16, k13);
				for (j2 = 12; j2 < j12; ++j2) {
					placeRandomBrick(world, random, i14, j2, k13);
				}
				placeRandomStairs(world, random, i14, j12, k13, 2);
			}
			placeRandomStairs(world, random, i14, 16, 2, 2);
		}
		for (int k18 = 5; k18 <= 28; ++k18) {
			for (j13 = 12; j13 <= 15; ++j13) {
				for (int i112 = -2; i112 <= 2; ++i112) {
					setAir(world, i112, j13, k18);
				}
			}
		}
		for (i14 = -1; i14 <= 1; ++i14) {
			placeRandomBrick(world, random, i14, 13, 4);
			placeRandomBrick(world, random, i14, 14, 4);
		}
		for (int i1621 : new int[]{-2, 2}) {
			placeRandomBrick(world, random, i1621, 12, 5);
			placeRandomBrick(world, random, i1621, 13, 5);
			setBlockAndMetadata(world, i1621, 14, 5, brick2WallBlock, brick2WallMeta);
			setBlockAndMetadata(world, i1621, 15, 5, Blocks.torch, 5);
		}
		setBlockAndMetadata(world, 0, 12, 4, doorBlock, 3);
		setBlockAndMetadata(world, 0, 13, 4, doorBlock, 8);
		for (int k19 = 6; k19 <= 28; ++k19) {
			for (int i113 = -1; i113 <= 1; ++i113) {
				placeRandomBrick(world, random, i113, 11, k19);
			}
			placeRandomWall(world, random, -2, 12, k19);
			placeRandomWall(world, random, 2, 12, k19);
			placeRandomStairs(world, random, -2, 11, k19, 5);
			placeRandomStairs(world, random, 2, 11, k19, 4);
		}
		int i13;
		for (i13 = -1; i13 <= 1; ++i13) {
			placeRandomStairs(world, random, i13, 10, 6, 7);
			placeRandomStairs(world, random, i13, 10, 16, 6);
			placeRandomStairs(world, random, i13, 10, 18, 7);
			placeRandomStairs(world, random, i13, 10, 28, 6);
			j13 = 10;
			while (!isOpaque(world, i13, j13, 17) && getY(j13) >= 0) {
				placeRandomBrick(world, random, i13, j13, 17);
				setGrassToDirt(world, i13, j13 - 1, 17);
				--j13;
			}
		}
		int j14;
		for (j14 = 12; j14 <= 13; ++j14) {
			placeRandomBrick(world, random, -2, j14, 11);
			placeRandomBrick(world, random, 2, j14, 11);
			placeRandomBrick(world, random, -2, j14, 23);
			placeRandomBrick(world, random, 2, j14, 23);
		}
		setBlockAndMetadata(world, -1, 13, 11, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 13, 11, Blocks.torch, 1);
		setBlockAndMetadata(world, -1, 13, 23, Blocks.torch, 2);
		setBlockAndMetadata(world, 1, 13, 23, Blocks.torch, 1);
		placeBanner(world, -2, 14, 11, bannerType, 3);
		placeBanner(world, 2, 14, 11, bannerType, 1);
		placeBanner(world, -2, 14, 23, bannerType, 3);
		placeBanner(world, 2, 14, 23, bannerType, 1);
		for (j14 = 12; j14 <= 14; ++j14) {
			placeRandomBrick(world, random, -2, j14, 17);
			placeRandomBrick(world, random, 2, j14, 17);
		}
		placeRandomBrick(world, random, -2, 15, 17);
		placeRandomBrick(world, random, 2, 15, 17);
		placeRandomStairs(world, random, -1, 15, 17, 4);
		placeRandomStairs(world, random, 1, 15, 17, 5);
		placeRandomSlab(world, random, 0, 15, 17, true);
		for (i13 = -1; i13 <= 1; ++i13) {
			setBlockAndMetadata(world, i13, 16, 17, brick2Block, brick2Meta);
		}
		setBlockAndMetadata(world, -2, 16, 17, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 2, 16, 17, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, 0, 17, 17, brick2SlabBlock, brick2SlabMeta);
		setBlockAndMetadata(world, -2, 14, 16, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 14, 16, Blocks.torch, 4);
		setBlockAndMetadata(world, -2, 14, 18, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 14, 18, Blocks.torch, 3);
		GOTStructureWesterosTower beaconTower = getTower(notifyChanges);
		beaconTower.restrictions = false;
		beaconTower.generateRoom = false;

		int beaconX = 0;
		int beaconY = 12;
		int beaconZ = 34;
		beaconTower.generateAndHeight(world, random, getX(beaconX, beaconZ), getY(beaconY), getZ(beaconX, beaconZ), (getRotationMode() + 2) % 4, -8);
		setAir(world, -1, 12, 29);
		setAir(world, 0, 12, 29);
		setAir(world, 1, 12, 29);
		GOTEntityNPC soldier = getSoldier(world);
		soldier.spawnRidingHorse = false;
		spawnNPCAndSetHome(soldier, world, 0, 2, -3, 32);
		GOTEntityNPC captain = getCaptain(world);
		captain.spawnRidingHorse = false;
		spawnNPCAndSetHome(captain, world, 0, 15, 0, 8);
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClasses(getSoldier(world).getClass(), getSoldierArcher(world).getClass());
		respawner.setCheckRanges(24, -8, 18, 12);
		respawner.setSpawnRanges(4, 2, 17, 32);
		placeNPCRespawner(respawner, world, 0, 2, 0);
		return true;
	}

	public void placeBarredWindowOnX(World world, int i, int j, int k) {
		for (int i1 = -1; i1 <= 1; ++i1) {
			for (int j1 = 0; j1 <= 1; ++j1) {
				setBlockAndMetadata(world, i + i1, j + j1, k, barsBlock, 0);
			}
		}
	}

	public void placeBarredWindowOnZ(World world, int i, int j, int k) {
		for (int k1 = -1; k1 <= 1; ++k1) {
			for (int j1 = 0; j1 <= 1; ++j1) {
				setBlockAndMetadata(world, i, j + j1, k + k1, barsBlock, 0);
			}
		}
	}

	public void placeRandomBrick(World world, Random random, int i, int j, int k) {
		if (random.nextInt(10) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, brickMossyBlock, brickMossyMeta);
			} else {
				setBlockAndMetadata(world, i, j, k, brickCrackedBlock, brickCrackedMeta);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, brickBlock, brickMeta);
		}
	}

	public void placeRandomSlab(World world, Random random, int i, int j, int k, boolean inverted) {
		int flag = inverted ? 8 : 0;
		if (random.nextInt(10) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, brickMossySlabBlock, brickMossySlabMeta | flag);
			} else {
				setBlockAndMetadata(world, i, j, k, brickCrackedSlabBlock, brickCrackedSlabMeta | flag);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, brickSlabBlock, brickSlabMeta | flag);
		}
	}

	public void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
		if (random.nextInt(10) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, brickMossyStairBlock, meta);
			} else {
				setBlockAndMetadata(world, i, j, k, brickCrackedStairBlock, meta);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, brickStairBlock, meta);
		}
	}

	public void placeRandomWall(World world, Random random, int i, int j, int k) {
		if (random.nextInt(10) == 0) {
			if (random.nextBoolean()) {
				setBlockAndMetadata(world, i, j, k, brickMossyWallBlock, brickMossyWallMeta);
			} else {
				setBlockAndMetadata(world, i, j, k, brickCrackedWallBlock, brickCrackedWallMeta);
			}
		} else {
			setBlockAndMetadata(world, i, j, k, brickWallBlock, brickWallMeta);
		}
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		barsBlock = Blocks.iron_bars;
	}
}
