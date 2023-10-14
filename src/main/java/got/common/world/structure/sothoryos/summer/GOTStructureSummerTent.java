package got.common.world.structure.sothoryos.summer;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.sothoryos.summer.GOTEntitySummerWarrior;
import got.common.item.other.GOTItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSummerTent extends GOTStructureSummerBase {
	public GOTStructureSummerTent(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j12;
		int k1;
		int i1;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 4);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -3; i1 <= 3; ++i1) {
				for (k1 = -3; k1 <= 3; ++k1) {
					j12 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j12, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				int j13;
				j12 = 0;
				while (!isOpaque(world, i1, j12, k1) && getY(j12) >= 0) {
					setBlockAndMetadata(world, i1, j12, k1, Blocks.sandstone, 0);
					setGrassToDirt(world, i1, j12 - 1, k1);
					--j12;
				}
				for (j12 = 1; j12 <= 6; ++j12) {
					setAir(world, i1, j12, k1);
				}
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 == 3 && k2 == 3) {
					for (j13 = 1; j13 <= 5; ++j13) {
						setBlockAndMetadata(world, i1, j13, k1, fenceBlock, fenceMeta);
					}
					setBlockAndMetadata(world, i1, 6, k1, Blocks.torch, 5);
				} else if (i2 == 3 || k2 == 3) {
					for (j13 = 1; j13 <= 4; ++j13) {
						setBlockAndMetadata(world, i1, j13, k1, roofBlock, roofMeta);
					}
				}
				if (i2 == 2 && k2 <= 2 || k2 == 2 && i2 <= 2) {
					for (j13 = 4; j13 <= 5; ++j13) {
						setBlockAndMetadata(world, i1, j13, k1, Blocks.wool, 15);
					}
					setBlockAndMetadata(world, i1, 1, k1, Blocks.carpet, 15);
				}
				if (i2 == 2 && k2 == 2) {
					for (j13 = 1; j13 <= 4; ++j13) {
						setBlockAndMetadata(world, i1, j13, k1, fenceBlock, fenceMeta);
					}
				}
				if ((i2 != 1 || k2 > 1) && (k2 != 1 || i2 > 1)) {
					continue;
				}
				setBlockAndMetadata(world, i1, 6, k1, roofBlock, roofMeta);
				setBlockAndMetadata(world, i1, 1, k1, Blocks.carpet, 14);
			}
		}
		for (j1 = 1; j1 <= 5; ++j1) {
			setBlockAndMetadata(world, 0, j1, 0, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, 0, 6, 0, plankBlock, plankMeta);
		placeBanner(world, 0, 7, 0, GOTItemBanner.BannerType.SUMMER, 0);
		for (j1 = 2; j1 <= 3; ++j1) {
			for (int i12 = -1; i12 <= 1; ++i12) {
				setBlockAndMetadata(world, i12, j1, -3, Blocks.wool, 15);
				setBlockAndMetadata(world, i12, j1, 3, Blocks.wool, 15);
			}
			for (k1 = -1; k1 <= 1; ++k1) {
				setBlockAndMetadata(world, -3, j1, k1, Blocks.wool, 15);
				setBlockAndMetadata(world, 3, j1, k1, Blocks.wool, 15);
			}
		}
		setAir(world, 0, 2, -3);
		setAir(world, 0, 2, 3);
		setAir(world, -3, 2, 0);
		setAir(world, 3, 2, 0);
		setBlockAndMetadata(world, -1, 1, -3, Blocks.wool, 15);
		setBlockAndMetadata(world, 0, 1, -3, Blocks.carpet, 14);
		setBlockAndMetadata(world, 0, 1, -2, Blocks.carpet, 14);
		setBlockAndMetadata(world, 1, 1, -3, Blocks.wool, 15);
		setBlockAndMetadata(world, -2, 1, 0, bedBlock, 0);
		setBlockAndMetadata(world, -2, 1, 1, bedBlock, 8);
		placeBarrel(world, random, -1, 1, 2, 2, GOTFoods.SOTHORYOS_DRINK);
		setBlockAndMetadata(world, 0, 1, 2, GOTBlocks.tableSummer, 0);
		placeChest(world, random, 1, 1, 2, GOTBlocks.chestBasket, 2, GOTChestContents.SUMMER);
		placeChest(world, random, 2, 1, 1, GOTBlocks.chestBasket, 5, GOTChestContents.SUMMER);
		setBlockAndMetadata(world, 2, 1, 0, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 0, 3, -2, Blocks.torch, 3);
		setBlockAndMetadata(world, 0, 3, 2, Blocks.torch, 4);
		placeWallBanner(world, -1, 3, 3, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, 1, 3, 3, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, 3, 3, 1, GOTItemBanner.BannerType.SUMMER, 3);
		placeWallBanner(world, 3, 3, -1, GOTItemBanner.BannerType.SUMMER, 3);
		placeWallBanner(world, -1, 3, -3, GOTItemBanner.BannerType.SUMMER, 0);
		placeWallBanner(world, 1, 3, -3, GOTItemBanner.BannerType.SUMMER, 0);
		placeWallBanner(world, -3, 3, 1, GOTItemBanner.BannerType.SUMMER, 1);
		placeWallBanner(world, -3, 3, -1, GOTItemBanner.BannerType.SUMMER, 1);
		GOTEntitySummerWarrior npc = new GOTEntitySummerWarrior(world);
		npc.spawnRidingHorse = false;
		spawnNPCAndSetHome(npc, world, 0, 1, -1, 16);
		return true;
	}
}
