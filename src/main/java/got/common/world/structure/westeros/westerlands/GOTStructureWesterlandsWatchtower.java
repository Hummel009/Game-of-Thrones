package got.common.world.structure.westeros.westerlands;

import java.util.Random;

import com.google.common.math.IntMath;

import got.common.database.*;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.westeros.westerlands.GOTEntityWesterlandsSoldier;
import got.common.item.other.GOTItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureWesterlandsWatchtower extends GOTStructureWesterlandsBase {
	public GOTStructureWesterlandsWatchtower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int i4;
		int j1;
		int n;
		int i2;
		this.setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i22 = -4; i22 <= 4; ++i22) {
				for (int i3 = -4; i3 <= 4; ++i3) {
					i4 = getTopBlock(world, i22, i3) - 1;
					if (!isSurface(world, i22, i4, i3)) {
						return false;
					}
					if (i4 < minHeight) {
						minHeight = i4;
					}
					if (i4 > maxHeight) {
						maxHeight = i4;
					}
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (n = -3; n <= 3; ++n) {
			for (i2 = -3; i2 <= 3; ++i2) {
				int i3 = Math.abs(n);
				int k2 = Math.abs(i2);
				if (i3 == 3 && k2 == 3) {
					continue;
				}
				i4 = 0;
				while (!isOpaque(world, n, i4, i2) && getY(i4) >= 0) {
					setBlockAndMetadata(world, n, i4, i2, brickBlock, brickMeta);
					setGrassToDirt(world, n, i4 - 1, i2);
					--i4;
				}
				if (i3 == 3 && k2 == 2 || k2 == 3 && i3 == 2) {
					for (i4 = 1; i4 <= 9; ++i4) {
						setBlockAndMetadata(world, n, i4, i2, pillarBlock, pillarMeta);
					}
					continue;
				}
				if (i3 == 3 || k2 == 3) {
					for (i4 = 1; i4 <= 9; ++i4) {
						setBlockAndMetadata(world, n, i4, i2, brickBlock, brickMeta);
					}
					continue;
				}
				setBlockAndMetadata(world, n, 0, i2, brickBlock, brickMeta);
				for (i4 = 1; i4 <= 5; ++i4) {
					setAir(world, n, i4, i2);
				}
				setBlockAndMetadata(world, n, 6, i2, plankBlock, plankMeta);
				for (i4 = 7; i4 <= 9; ++i4) {
					setAir(world, n, i4, i2);
				}
			}
		}
		for (n = -3; n <= 3; ++n) {
			for (i2 = -3; i2 <= 3; ++i2) {
				setBlockAndMetadata(world, n, 10, i2, brickBlock, brickMeta);
			}
		}
		for (n = -3; n <= 3; ++n) {
			setBlockAndMetadata(world, n, 10, -4, brickStairBlock, 6);
			setBlockAndMetadata(world, n, 10, 4, brickStairBlock, 7);
		}
		for (int m = -2; m <= 2; ++m) {
			setBlockAndMetadata(world, -4, 10, m, brickStairBlock, 5);
			setBlockAndMetadata(world, 4, 10, m, brickStairBlock, 4);
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
		for (j1 = 1; j1 <= 2; ++j1) {
			setBlockAndMetadata(world, -1, j1, -3, GOTRegistry.brick1, 5);
			setBlockAndMetadata(world, 1, j1, -3, GOTRegistry.brick1, 5);
		}
		setBlockAndMetadata(world, -1, 3, -4, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 3, -4, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 6, -3, GOTRegistry.brick1, 5);
		setBlockAndMetadata(world, 0, 6, 3, GOTRegistry.brick1, 5);
		setBlockAndMetadata(world, -3, 6, 0, GOTRegistry.brick1, 5);
		setBlockAndMetadata(world, 3, 6, 0, GOTRegistry.brick1, 5);
		placeWallBanner(world, 0, 5, -3, GOTItemBanner.BannerType.LANNISTER, 2);
		for (j1 = 1; j1 <= 9; ++j1) {
			setBlockAndMetadata(world, 0, j1, 2, Blocks.ladder, 2);
		}
		setBlockAndMetadata(world, 0, 10, 2, Blocks.trapdoor, 9);
		for (int k1 = -2; k1 <= 2; ++k1) {
			if (IntMath.mod(k1, 2) == 0) {
				this.placeChest(world, random, -2, 1, k1, 4, GOTChestContents.WESTEROS);
				this.placeChest(world, random, 2, 1, k1, 5, GOTChestContents.WESTEROS);
				continue;
			}
			setBlockAndMetadata(world, -1, 1, k1, bedBlock, 3);
			setBlockAndMetadata(world, -2, 1, k1, bedBlock, 11);
			setBlockAndMetadata(world, 1, 1, k1, bedBlock, 1);
			setBlockAndMetadata(world, 2, 1, k1, bedBlock, 9);
		}
		setBlockAndMetadata(world, -2, 3, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 3, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 0, 5, 0, GOTRegistry.chandelier, 1);
		this.placeChest(world, random, -2, 7, -2, GOTRegistry.chestStone, 4, GOTChestContents.WESTEROS);
		setBlockAndMetadata(world, -2, 7, 0, GOTRegistry.armorStand, 3);
		setBlockAndMetadata(world, -2, 8, 0, GOTRegistry.armorStand, 7);
		setBlockAndMetadata(world, -2, 7, 2, Blocks.anvil, 0);
		spawnItemFrame(world, -3, 8, -1, 1, getFramedItem(random));
		spawnItemFrame(world, -3, 8, 1, 1, getFramedItem(random));
		setBlockAndMetadata(world, 2, 7, -2, tableBlock, 0);
		setBlockAndMetadata(world, 2, 7, -1, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 2, 7, 0, Blocks.stone_slab, 8);
		setBlockAndMetadata(world, 2, 7, 1, Blocks.stone_slab, 8);
		setBlockAndMetadata(world, 2, 7, 2, Blocks.stone_slab, 8);
		this.placeMug(world, random, 2, 8, 0, 1, GOTFoods.WESTEROS_DRINK);
		placePlateWithCertainty(world, random, 2, 8, 1, plateBlock, GOTFoods.WESTEROS);
		this.placeBarrel(world, random, 2, 8, 2, 5, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, 0, 9, 0, GOTRegistry.chandelier, 1);
		for (int i1 = -4; i1 <= 4; ++i1) {
			for (int i23 = -4; i23 <= 4; ++i23) {
				int i3 = Math.abs(i1);
				int k2 = Math.abs(i23);
				if (i3 == 4 && k2 == 4) {
					setBlockAndMetadata(world, i1, 11, i23, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 12, i23, brickBlock, brickMeta);
					continue;
				}
				if (i3 != 4 && k2 != 4) {
					continue;
				}
				if (IntMath.mod(i1 + i23, 2) == 1) {
					setBlockAndMetadata(world, i1, 11, i23, brickWallBlock, brickWallMeta);
					continue;
				}
				setBlockAndMetadata(world, i1, 11, i23, brickBlock, brickMeta);
				setBlockAndMetadata(world, i1, 12, i23, brickSlabBlock, brickSlabMeta);
			}
		}
		setBlockAndMetadata(world, 0, 11, 0, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, 0, 12, 0, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, 0, 13, 0, GOTRegistry.brick1, 5);
		this.placeBanner(world, 0, 14, 0, GOTItemBanner.BannerType.LANNISTER, 2);
		setBlockAndMetadata(world, 0, 11, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 0, 11, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, -3, 11, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 11, 0, Blocks.torch, 1);
		GOTEntityWesterlandsSoldier soldier = new GOTEntityWesterlandsSoldier(world);
		soldier.spawnRidingHorse = false;
		spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
		GOTEntityWesterlandsSoldier levyman = new GOTEntityWesterlandsSoldier(world);
		spawnNPCAndSetHome(levyman, world, 0, 11, 1, 16);
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass(GOTEntityWesterlandsSoldier.class);
		respawner.setCheckRanges(16, -12, 8, 6);
		respawner.setSpawnRanges(3, -6, 6, 16);
		placeNPCRespawner(respawner, world, 0, 6, 0);
		return true;
	}
}
