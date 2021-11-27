package got.common.world.structure.essos.asshai;

import java.util.Random;

import com.google.common.math.IntMath;

import got.common.database.*;
import got.common.entity.essos.asshai.*;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.item.other.GOTItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureAsshaiWatchtower extends GOTStructureAsshaiBase {
	public GOTStructureAsshaiWatchtower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k2;
		int k1;
		int i1;
		int j1;
		int j12;
		int k12;
		int i2;
		this.setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -4; i12 <= 4; ++i12) {
				for (int k13 = -4; k13 <= 4; ++k13) {
					j12 = getTopBlock(world, i12, k13) - 1;
					if (!isSurface(world, i12, j12, k13)) {
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
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 == 3 && k2 == 3) {
					continue;
				}
				j12 = 0;
				while (!isOpaque(world, i1, j12, k1) && getY(j12) >= 0) {
					setBlockAndMetadata(world, i1, j12, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j12 - 1, k1);
					--j12;
				}
				if (i2 == 3 && k2 == 2 || k2 == 3 && i2 == 2) {
					for (j12 = 1; j12 <= 9; ++j12) {
						setBlockAndMetadata(world, i1, j12, k1, pillarBlock, pillarMeta);
					}
					continue;
				}
				if (i2 == 3 || k2 == 3) {
					for (j12 = 1; j12 <= 9; ++j12) {
						setBlockAndMetadata(world, i1, j12, k1, brickBlock, brickMeta);
					}
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k1, brickBlock, brickMeta);
				for (j12 = 1; j12 <= 5; ++j12) {
					setAir(world, i1, j12, k1);
				}
				setBlockAndMetadata(world, i1, 6, k1, plankBlock, plankMeta);
				for (j12 = 7; j12 <= 9; ++j12) {
					setAir(world, i1, j12, k1);
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				setBlockAndMetadata(world, i1, 10, k1, brickBlock, brickMeta);
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			setBlockAndMetadata(world, i1, 10, -4, brickStairBlock, 6);
			setBlockAndMetadata(world, i1, 10, 4, brickStairBlock, 7);
		}
		for (k12 = -2; k12 <= 2; ++k12) {
			setBlockAndMetadata(world, -4, 10, k12, brickStairBlock, 5);
			setBlockAndMetadata(world, 4, 10, k12, brickStairBlock, 4);
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
			setBlockAndMetadata(world, -1, j1, -3, GOTRegistry.brick1, 0);
			setBlockAndMetadata(world, 1, j1, -3, GOTRegistry.brick1, 0);
		}
		setBlockAndMetadata(world, -1, 3, -4, Blocks.torch, 4);
		setBlockAndMetadata(world, 1, 3, -4, Blocks.torch, 4);
		setBlockAndMetadata(world, 0, 6, -3, GOTRegistry.brick1, 0);
		setBlockAndMetadata(world, 0, 6, 3, GOTRegistry.brick1, 0);
		setBlockAndMetadata(world, -3, 6, 0, GOTRegistry.brick1, 0);
		setBlockAndMetadata(world, 3, 6, 0, GOTRegistry.brick1, 0);
		placeWallBanner(world, 0, 5, -3, GOTItemBanner.BannerType.ASSHAI, 2);
		for (j1 = 1; j1 <= 9; ++j1) {
			setBlockAndMetadata(world, 0, j1, 2, Blocks.ladder, 2);
		}
		setBlockAndMetadata(world, 0, 10, 2, Blocks.trapdoor, 9);
		for (k12 = -2; k12 <= 2; ++k12) {
			if (IntMath.mod(k12, 2) == 0) {
				this.placeChest(world, random, -2, 1, k12, 4, GOTChestContents.WESTEROS);
				this.placeChest(world, random, 2, 1, k12, 5, GOTChestContents.WESTEROS);
				continue;
			}
			setBlockAndMetadata(world, -1, 1, k12, bedBlock, 3);
			setBlockAndMetadata(world, -2, 1, k12, bedBlock, 11);
			setBlockAndMetadata(world, 1, 1, k12, bedBlock, 1);
			setBlockAndMetadata(world, 2, 1, k12, bedBlock, 9);
		}
		setBlockAndMetadata(world, -2, 3, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 2, 3, 0, Blocks.torch, 1);
		setBlockAndMetadata(world, 0, 5, 0, GOTRegistry.chandelier, 12);
		this.placeChest(world, random, -2, 7, -2, GOTRegistry.chestStone, 4, GOTChestContents.WESTEROS);
		setBlockAndMetadata(world, -2, 7, 0, GOTRegistry.armorStand, 3);
		setBlockAndMetadata(world, -2, 8, 0, GOTRegistry.armorStand, 7);
		setBlockAndMetadata(world, -2, 7, 2, Blocks.anvil, 0);
		spawnItemFrame(world, -3, 8, -1, 1, getFramedItem(random));
		spawnItemFrame(world, -3, 8, 1, 1, getFramedItem(random));
		setBlockAndMetadata(world, 2, 7, -2, GOTRegistry.tableAsshai, 0);
		setBlockAndMetadata(world, 2, 7, -1, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 2, 7, 0, Blocks.stone_slab, 8);
		setBlockAndMetadata(world, 2, 7, 1, Blocks.stone_slab, 8);
		setBlockAndMetadata(world, 2, 7, 2, Blocks.stone_slab, 8);
		this.placeMug(world, random, 2, 8, 0, 1, GOTFoods.WESTEROS_DRINK);
		placePlateWithCertainty(world, random, 2, 8, 1, plateBlock, GOTFoods.WESTEROS);
		this.placeBarrel(world, random, 2, 8, 2, 5, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, 0, 9, 0, GOTRegistry.chandelier, 12);
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -4; k1 <= 4; ++k1) {
				i2 = Math.abs(i1);
				k2 = Math.abs(k1);
				if (i2 == 4 && k2 == 4) {
					setBlockAndMetadata(world, i1, 11, k1, brickBlock, brickMeta);
					setBlockAndMetadata(world, i1, 12, k1, brickBlock, brickMeta);
					continue;
				}
				if (i2 != 4 && k2 != 4) {
					continue;
				}
				if (IntMath.mod(i1 + k1, 2) == 1) {
					setBlockAndMetadata(world, i1, 11, k1, brickWallBlock, brickWallMeta);
					continue;
				}
				setBlockAndMetadata(world, i1, 11, k1, brickBlock, brickMeta);
				setBlockAndMetadata(world, i1, 12, k1, brickSlabBlock, brickSlabMeta);
			}
		}
		setBlockAndMetadata(world, 0, 11, 0, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, 0, 12, 0, pillarBlock, pillarMeta);
		setBlockAndMetadata(world, 0, 13, 0, GOTRegistry.brick1, 0);
		this.placeBanner(world, 0, 14, 0, GOTItemBanner.BannerType.ASSHAI, 2);
		setBlockAndMetadata(world, 0, 11, -3, Blocks.torch, 3);
		setBlockAndMetadata(world, 0, 11, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, -3, 11, 0, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, 11, 0, Blocks.torch, 1);
		GOTEntityAsshaiGuard soldier = new GOTEntityAsshaiGuard(world);
		soldier.spawnRidingHorse = false;
		spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
		GOTEntityAsshaiShadowbinder levyman = new GOTEntityAsshaiShadowbinder(world);
		spawnNPCAndSetHome(levyman, world, 0, 11, 1, 16);
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass(GOTEntityAsshaiGuard.class);
		respawner.setCheckRanges(16, -12, 8, 6);
		respawner.setSpawnRanges(3, -6, 6, 16);
		placeNPCRespawner(respawner, world, 0, 6, 0);
		return true;
	}
}
