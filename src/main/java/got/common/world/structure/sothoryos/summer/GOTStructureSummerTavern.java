package got.common.world.structure.sothoryos.summer;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.database.GOTNames;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.sothoryos.summer.GOTEntitySummerBartender;
import got.common.entity.sothoryos.summer.GOTEntitySummerMan;
import got.common.item.other.GOTItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSummerTavern extends GOTStructureSummerBase {
	public GOTStructureSummerTavern(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int j2;
		int step;
		int j12;
		int j1;
		int i1;
		setOriginAndRotation(world, i, j, k, rotation, 7, -3);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -15; i12 <= 15; ++i12) {
				for (int k12 = -8; k12 <= 8; ++k12) {
					j12 = getTopBlock(world, i12, k12) - 1;
					if (!isSurface(world, i12, j12, k12)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 12) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i13 = -13; i13 <= 13; ++i13) {
			for (int k13 = -6; k13 <= 6; ++k13) {
				int i2 = Math.abs(i13);
				int k2 = Math.abs(k13);
				if ((i2 > 8 || k2 != 6) && (i2 > 11 || k2 > 5) && k2 > 4) {
					continue;
				}
				for (j12 = 1; j12 <= 8; ++j12) {
					setAir(world, i13, j12, k13);
				}
				j12 = -1;
				while (!isOpaque(world, i13, j12, k13) && getY(j12) >= 0) {
					setBlockAndMetadata(world, i13, j12, k13, plank2Block, plank2Meta);
					setGrassToDirt(world, i13, j12 - 1, k13);
					--j12;
				}
			}
		}
		if (isRuined()) {
			loadStrScan("summer_tavern_ruined");
		} else {
			loadStrScan("summer_tavern");
		}
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		if (isRuined()) {
			setBlockAliasChance("PLANK2", 0.8f);
		}
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		generateStrScan(world, random, 0, 1, 0);
		if (!isRuined()) {
			placeWeaponRack(world, -3, 3, -1, 6, getRandomWeapon(random));
			spawnItemFrame(world, -3, 3, 0, 0, getFramedItem(random));
			placeWeaponRack(world, 3, 3, 1, 4, getRandomWeapon(random));
			spawnItemFrame(world, 3, 3, 0, 2, getFramedItem(random));
			placeFoodOrDrink(world, random, -4, 2, -1);
			placeFoodOrDrink(world, random, -3, 2, -1);
			placeFoodOrDrink(world, random, -2, 2, -1);
			placeFoodOrDrink(world, random, -2, 2, 0);
			placeFoodOrDrink(world, random, -2, 2, 1);
			placeFoodOrDrink(world, random, -3, 2, 1);
			placeFoodOrDrink(world, random, -4, 2, 1);
			placeFoodOrDrink(world, random, -4, 2, 0);
			placeFoodOrDrink(world, random, 4, 2, -1);
			placeFoodOrDrink(world, random, 3, 2, -1);
			placeFoodOrDrink(world, random, 2, 2, -1);
			placeFoodOrDrink(world, random, 2, 2, 0);
			placeFoodOrDrink(world, random, 2, 2, 1);
			placeFoodOrDrink(world, random, 3, 2, 1);
			placeFoodOrDrink(world, random, 4, 2, 1);
			placeFoodOrDrink(world, random, 4, 2, 0);
			placeFoodOrDrink(world, random, -7, 2, -5);
			placeFoodOrDrink(world, random, -8, 2, 5);
			placeFoodOrDrink(world, random, -7, 2, 5);
			placeFoodOrDrink(world, random, -6, 2, 5);
			placeFoodOrDrink(world, random, 6, 2, -5);
			placeFoodOrDrink(world, random, 7, 2, -5);
			placeFoodOrDrink(world, random, 6, 2, 5);
			placeFoodOrDrink(world, random, 7, 2, 5);
			placeFoodOrDrink(world, random, -9, 2, -2);
			placeFoodOrDrink(world, random, -9, 2, -1);
			placeFoodOrDrink(world, random, -9, 2, 1);
			placeFoodOrDrink(world, random, -9, 2, 2);
			placeFlowerPot(world, -12, 2, -3, getRandomFlower(world, random));
			placeFoodOrDrink(world, random, -12, 2, -2);
			placeFoodOrDrink(world, random, -12, 2, 1);
			placeFoodOrDrink(world, random, -12, 2, 2);
			placeBarrel(world, random, -12, 2, 3, 4, GOTFoods.SOTHORYOS_DRINK);
			placeBarrel(world, random, -11, 2, 4, 2, GOTFoods.SOTHORYOS_DRINK);
			placeKebabStand(world, random, -10, 2, -4, GOTBlocks.kebabStand, 3);
			setBlockAndMetadata(world, 11, 1, -3, bedBlock, 2);
			setBlockAndMetadata(world, 11, 1, -4, bedBlock, 10);
			setBlockAndMetadata(world, 11, 1, 3, bedBlock, 0);
			setBlockAndMetadata(world, 11, 1, 4, bedBlock, 8);
			placeChest(world, random, 12, 1, -3, GOTBlocks.chestBasket, 3, GOTChestContents.SUMMER);
			placeChest(world, random, 12, 1, 3, GOTBlocks.chestBasket, 2, GOTChestContents.SUMMER);
			placeFlowerPot(world, 12, 2, -1, getRandomFlower(world, random));
			placeFoodOrDrink(world, random, 11, 2, -1);
			placeFlowerPot(world, 11, 2, 1, getRandomFlower(world, random));
			placeFoodOrDrink(world, random, 12, 2, 1);
			String[] tavernName = GOTNames.getTavernName(random);
			placeSign(world, -1, 2, -6, Blocks.wall_sign, 5, new String[]{"", tavernName[0], tavernName[1], ""});
			placeSign(world, 1, 2, -6, Blocks.wall_sign, 4, new String[]{"", tavernName[0], tavernName[1], ""});
			placeSign(world, -1, 2, 6, Blocks.wall_sign, 5, new String[]{"", tavernName[0], tavernName[1], ""});
			placeSign(world, 1, 2, 6, Blocks.wall_sign, 4, new String[]{"", tavernName[0], tavernName[1], ""});
			placeWallBanner(world, -6, 4, -8, GOTItemBanner.BannerType.SUMMER, 2);
			placeWallBanner(world, 6, 4, -8, GOTItemBanner.BannerType.SUMMER, 2);
			placeWallBanner(world, -6, 4, 8, GOTItemBanner.BannerType.SUMMER, 0);
			placeWallBanner(world, 6, 4, 8, GOTItemBanner.BannerType.SUMMER, 0);
			placeWallBanner(world, 0, 6, -4, GOTItemBanner.BannerType.SUMMER, 0);
			placeWallBanner(world, 0, 6, 4, GOTItemBanner.BannerType.SUMMER, 2);
			placeWallBanner(world, -9, 5, 0, GOTItemBanner.BannerType.SUMMER, 1);
			placeWallBanner(world, 9, 5, 0, GOTItemBanner.BannerType.SUMMER, 3);
			GOTEntityNPC bartender = new GOTEntitySummerBartender(world);
			spawnNPCAndSetHome(bartender, world, -10, 1, 0, 4);
			int numnpc = MathHelper.getRandomIntegerInRange(random, 3, 8);
			for (int l = 0; l < numnpc; ++l) {
				GOTEntityNPC npc = new GOTEntitySummerMan(world);
				spawnNPCAndSetHome(npc, world, 0, 1, 0, 16);
			}
		}
		for (i1 = -5; i1 <= -1; ++i1) {
			for (step = 0; step < 12 && !isOpaque(world, i1, j1 = -step, k1 = -7 - step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, plank2StairBlock, 2);
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (i1 = 1; i1 <= 5; ++i1) {
			for (step = 0; step < 12 && !isOpaque(world, i1, j1 = -step, k1 = -7 - step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, plank2StairBlock, 2);
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (i1 = -5; i1 <= -1; ++i1) {
			for (step = 0; step < 12 && !isOpaque(world, i1, j1 = -step, k1 = 7 + step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, plank2StairBlock, 3);
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (i1 = 1; i1 <= 5; ++i1) {
			for (step = 0; step < 12 && !isOpaque(world, i1, j1 = -step, k1 = 7 + step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, plank2StairBlock, 3);
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		return true;
	}

	private void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				placeMug(world, random, i, j, k, random.nextInt(4), GOTFoods.SOTHORYOS_DRINK);
			} else {
				Block plateBlock = random.nextBoolean() ? GOTBlocks.woodPlate : GOTBlocks.ceramicPlate;
				if (random.nextBoolean()) {
					setBlockAndMetadata(world, i, j, k, plateBlock, 0);
				} else {
					placePlateWithCertainty(world, random, i, j, k, plateBlock, GOTFoods.SOTHORYOS);
				}
			}
		}
	}
}