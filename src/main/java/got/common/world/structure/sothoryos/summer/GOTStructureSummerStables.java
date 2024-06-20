package got.common.world.structure.sothoryos.summer;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.sothoryos.summer.GOTEntitySummerMan;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSummerStables extends GOTStructureSummerBase {
	public GOTStructureSummerStables(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 9);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -7; i1 <= 7; ++i1) {
				for (int k1 = -10; k1 <= 10; ++k1) {
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
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -7; i1 <= 7; ++i1) {
			for (int k1 = -10; k1 <= 10; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if ((i2 > 5 || k2 > 4) && (i2 > 4 || k2 > 6) && (i2 > 3 || k2 > 7) && (i2 > 2 || k2 > 8) && (i2 > 1 || k2 > 9)) {
					continue;
				}
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("summer_stables");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		generateStrScan(world, random, 0, 0, 0);
		placeWallBanner(world, -2, 4, -4, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, 2, 4, -4, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, -2, 4, 4, GOTItemBanner.BannerType.SUMMER, 0);
		placeWallBanner(world, 2, 4, 4, GOTItemBanner.BannerType.SUMMER, 0);
		spawnItemFrame(world, -2, 2, 0, 1, getRandFrameItem(random));
		spawnItemFrame(world, 2, 2, 0, 3, getRandFrameItem(random));
		setBlockAndMetadata(world, -3, 1, 6, bedBlock, 0);
		setBlockAndMetadata(world, -3, 1, 7, bedBlock, 8);
		placeChest(world, random, -4, 1, 6, GOTBlocks.chestBasket, 4, GOTChestContents.SUMMER);
		placePlateWithCertainty(world, random, 4, 2, 6, GOTBlocks.woodPlate, GOTFoods.DEFAULT);
		placeMug(world, random, 4, 2, 5, 1, GOTFoods.DEFAULT_DRINK);
		GOTEntityNPC summer = new GOTEntitySummerMan(world);
		spawnNPCAndSetHome(summer, world, 0, 1, 0, 12);
		for (int k1 : new int[]{-2, 2}) {
			for (int i1 : new int[]{-4, 4}) {
				int j12 = 1;
				GOTEntityHorse horse = new GOTEntityHorse(world);
				spawnNPCAndSetHome(horse, world, i1, j12, k1, 0);
				horse.setHorseType(0);
				horse.saddleMountForWorldGen();
				horse.detachHome();
			}
		}
		return true;
	}
}