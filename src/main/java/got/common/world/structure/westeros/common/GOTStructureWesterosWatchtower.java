package got.common.world.structure.westeros.common;

import com.google.common.math.IntMath;
import got.common.database.GOTBlocks;
import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityNPCRespawner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureWesterosWatchtower extends GOTStructureWesterosBase {
	public GOTStructureWesterosWatchtower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i2 = -4; i2 <= 4; ++i2) {
				for (int k2 = -4; k2 <= 4; ++k2) {
					int j2 = getTopBlock(world, i2, k2) - 1;
					if (!isSurface(world, i2, j2, k2)) {
						return false;
					}
					if (j2 < minHeight) {
						minHeight = j2;
					}
					if (j2 > maxHeight) {
						maxHeight = j2;
					}
					if (maxHeight - minHeight > 8) {
						return false;
					}
				}
			}
		}
		for (int i3 = -3; i3 <= 3; ++i3) {
			for (int k3 = -3; k3 <= 3; ++k3) {
				int i4 = Math.abs(i3);
				int k4 = Math.abs(k3);
				if (i4 != 3 || k4 != 3) {
					for (int j2 = 0; !isOpaque(world, i3, j2, k3) && getY(j2) >= 0; --j2) {
						setBlockAndMetadata(world, i3, j2, k3, brickBlock, brickMeta);
						setGrassToDirt(world, i3, j2 - 1, k3);
					}
					if (i4 == 3 && k4 == 2 || k4 == 3 && i4 == 2) {
						for (int j2 = 1; j2 <= 9; ++j2) {
							setBlockAndMetadata(world, i3, j2, k3, pillarBlock, pillarMeta);
						}
					} else if (i4 == 3 || k4 == 3) {
						for (int j2 = 1; j2 <= 9; ++j2) {
							setBlockAndMetadata(world, i3, j2, k3, brickBlock, brickMeta);
						}
					} else {
						setBlockAndMetadata(world, i3, 0, k3, brickBlock, brickMeta);
						for (int j2 = 1; j2 <= 5; ++j2) {
							setAir(world, i3, j2, k3);
						}
						setBlockAndMetadata(world, i3, 6, k3, plankBlock, plankMeta);
						for (int j2 = 7; j2 <= 9; ++j2) {
							setAir(world, i3, j2, k3);
						}
					}
				}
			}
		}
		for (int i3 = -3; i3 <= 3; ++i3) {
			for (int k3 = -3; k3 <= 3; ++k3) {
				setBlockAndMetadata(world, i3, 10, k3, brickBlock, brickMeta);
			}
		}
		for (int i3 = -3; i3 <= 3; ++i3) {
			setBlockAndMetadata(world, i3, 10, -4, brickStairBlock, 6);
			setBlockAndMetadata(world, i3, 10, 4, brickStairBlock, 7);
		}
		for (int k5 = -2; k5 <= 2; ++k5) {
			setBlockAndMetadata(world, -4, 10, k5, brickStairBlock, 5);
			setBlockAndMetadata(world, 4, 10, k5, brickStairBlock, 4);
		}
		setBlockAndMetadata(world, -3, 10, -3, brickStairBlock, 5);
		setBlockAndMetadata(world, -4, 10, -3, brickStairBlock, 6);
		setBlockAndMetadata(world, 4, 10, -3, brickStairBlock, 6);
		setBlockAndMetadata(world, 3, 10, -3, brickStairBlock, 4);
		setBlockAndMetadata(world, -3, 10, 3, brickStairBlock, 5);
		setBlockAndMetadata(world, -4, 10, 3, brickStairBlock, 7);
		setBlockAndMetadata(world, 4, 10, 3, brickStairBlock, 7);
		setBlockAndMetadata(world, 3, 10, 3, brickStairBlock, 4);
		setBlockAndMetadata(world, 0, 0, -3, brickBlock, brickMeta);
		setBlockAndMetadata(world, 0, 1, -3, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -3, doorBlock, 8);
		for (int j3 = 1; j3 <= 2; ++j3) {
			setBlockAndMetadata(world, -1, j3, -3, brickCarved, brickCarvedMeta);
			setBlockAndMetadata(world, 1, j3, -3, brickCarved, brickCarvedMeta);
		}
		setBlockAndMetadata(world, -1, 3, -4, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 3, -4, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 6, -3, brickCarved, brickCarvedMeta);
		setBlockAndMetadata(world, 0, 6, 3, brickCarved, brickCarvedMeta);
		setBlockAndMetadata(world, -3, 6, 0, brickCarved, brickCarvedMeta);
		setBlockAndMetadata(world, 3, 6, 0, brickCarved, brickCarvedMeta);
		placeWallBanner(world, 0, 5, -3, bannerType, 2);
		for (int j3 = 1; j3 <= 9; ++j3) {
			setBlockAndMetadata(world, 0, j3, 2, Blocks.ladder, 2);
		}
		setBlockAndMetadata(world, 0, 10, 2, trapdoorBlock, 9);
		for (int k5 = -2; k5 <= 2; ++k5) {
			if (IntMath.mod(k5, 2) == 0) {
				placeChest(world, random, -2, 1, k5, 4, getChestContents());
				placeChest(world, random, 2, 1, k5, 5, getChestContents());
			} else {
				setBlockAndMetadata(world, -1, 1, k5, bedBlock, 3);
				setBlockAndMetadata(world, -2, 1, k5, bedBlock, 11);
				setBlockAndMetadata(world, 1, 1, k5, bedBlock, 1);
				setBlockAndMetadata(world, 2, 1, k5, bedBlock, 9);
			}
		}
		setBlockAndMetadata(world, -2, 3, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 3, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 0, 5, 0, GOTBlocks.chandelier, 1);
		placeChest(world, random, -2, 7, -2, getChest(), 4, getChestContents());
		setBlockAndMetadata(world, -2, 7, 0, GOTBlocks.armorStand, 3);
		setBlockAndMetadata(world, -2, 8, 0, GOTBlocks.armorStand, 7);
		setBlockAndMetadata(world, -2, 7, 2, Blocks.anvil, 0);
		spawnItemFrame(world, -3, 8, -1, 1, getFramedItem(random));
		spawnItemFrame(world, -3, 8, 1, 1, getFramedItem(random));
		setBlockAndMetadata(world, 2, 7, -2, tableBlock, 0);
		setBlockAndMetadata(world, 2, 7, -1, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 2, 7, 0, cobbleSlabBlock, cobbleSlabMeta | 8);
		setBlockAndMetadata(world, 2, 7, 1, cobbleSlabBlock, cobbleSlabMeta | 8);
		setBlockAndMetadata(world, 2, 7, 2, cobbleSlabBlock, cobbleSlabMeta | 8);
		placeMug(world, random, 2, 8, 0, 1, GOTFoods.WESTEROS_DRINK);
		placePlateWithCertainty(world, random, 2, 8, 1, plateBlock, GOTFoods.WESTEROS);
		placeBarrel(world, random, 2, 8, 2, 5, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, 0, 9, 0, GOTBlocks.chandelier, 1);
		for (int i3 = -4; i3 <= 4; ++i3) {
			for (int k3 = -4; k3 <= 4; ++k3) {
				int i4 = Math.abs(i3);
				int k4 = Math.abs(k3);
				if (i4 == 4 && k4 == 4) {
					setBlockAndMetadata(world, i3, 11, k3, brickBlock, brickMeta);
					setBlockAndMetadata(world, i3, 12, k3, brickBlock, brickMeta);
				} else if (i4 == 4 || k4 == 4) {
					if (IntMath.mod(i3 + k3, 2) == 1) {
						setBlockAndMetadata(world, i3, 11, k3, brickWallBlock, brickWallMeta);
					} else {
						setBlockAndMetadata(world, i3, 11, k3, brickBlock, brickMeta);
						setBlockAndMetadata(world, i3, 12, k3, brickSlabBlock, brickSlabMeta);
					}
				}
			}
		}
		setBlockAndMetadata(world, 0, 11, 0, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, 0, 12, 0, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, 0, 13, 0, brickCarved, brickCarvedMeta);
		placeBanner(world, 0, 14, 0, bannerType, 2);
		setBlockAndMetadata(world, 0, 11, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 0, 11, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, -3, 11, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 11, 0, Blocks.torch, 1);
		GOTEntityNPC soldier = getSoldier(world);
		soldier.setSpawnRidingHorse(false);
		spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
		GOTEntityNPC levyman = getSoldier(world);
		spawnNPCAndSetHome(levyman, world, 0, 11, 1, 16);
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass1(getSoldier(world).getClass());
		respawner.setCheckRanges(16, -12, 8, 6);
		respawner.setSpawnRanges(3, -6, 6, 16);
		placeNPCRespawner(respawner, world, 0, 6, 0);
		return true;
	}
}