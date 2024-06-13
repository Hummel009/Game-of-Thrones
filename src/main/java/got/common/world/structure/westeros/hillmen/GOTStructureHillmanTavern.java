package got.common.world.structure.westeros.hillmen;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.database.GOTNames;
import got.common.entity.other.GOTEntityLightSkinThief;
import got.common.entity.other.GOTEntityLightSkinTramp;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.hillmen.GOTEntityHillman;
import got.common.entity.westeros.hillmen.GOTEntityHillmanBartender;
import got.common.item.other.GOTItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureHillmanTavern extends GOTStructureHillmanBase {
	public GOTStructureHillmanTavern(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 8);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -11; i1 <= 11; ++i1) {
				for (int k1 = -8; k1 <= 8; ++k1) {
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
					if (maxHeight - minHeight <= 12) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -9; i1 <= 9; ++i1) {
			for (int k1 = -7; k1 <= 7; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				for (j1 = 1; j1 <= 7; ++j1) {
					setAir(world, i1, j1, k1);
				}
				j1 = -1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, floorBlock, floorMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
				if (random.nextInt(4) != 0 || i2 <= 3 && k2 <= 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, GOTBlocks.thatchFloor, 0);
			}
		}
		loadStrScan("hillman_tavern");
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("WOOD|4", woodBlock, woodMeta | 4);
		associateBlockMetaAlias("WOOD|8", woodBlock, woodMeta | 8);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("BARS", barsBlock, barsMeta);
		generateStrScan(world, random, 0, 1, 0);
		placeFlowerPot(world, 8, 2, -5, getRandomFlower(world, random));
		placeFlowerPot(world, 8, 2, 5, getRandomFlower(world, random));
		placeFlowerPot(world, -8, 2, -4, getRandomFlower(world, random));
		placeFlowerPot(world, -8, 2, 4, getRandomFlower(world, random));
		placeChest(world, random, 7, 1, -5, GOTBlocks.chestBasket, 5, GOTChestContents.HILLMEN);
		placeBarrel(world, random, 7, 2, 6, 2, GOTFoods.WILD_DRINK);
		placeBarrel(world, random, 4, 2, 6, 2, GOTFoods.WILD_DRINK);
		placeFoodOrDrink(world, random, -6, 2, -6);
		placeFoodOrDrink(world, random, -5, 2, -6);
		placeFoodOrDrink(world, random, -6, 2, -1);
		placeFoodOrDrink(world, random, -5, 2, -1);
		placeFoodOrDrink(world, random, -6, 2, 0);
		placeFoodOrDrink(world, random, -5, 2, 0);
		placeFoodOrDrink(world, random, -6, 2, 1);
		placeFoodOrDrink(world, random, -5, 2, 1);
		placeFoodOrDrink(world, random, -6, 2, 6);
		placeFoodOrDrink(world, random, -5, 2, 6);
		placeFoodOrDrink(world, random, -1, 2, 6);
		placeFoodOrDrink(world, random, 0, 2, 6);
		placeFoodOrDrink(world, random, 1, 2, 6);
		placeFoodOrDrink(world, random, 5, 2, 3);
		placeFoodOrDrink(world, random, 6, 2, 3);
		placeFoodOrDrink(world, random, 8, 2, 4);
		placeFoodOrDrink(world, random, 5, 2, -3);
		placeFoodOrDrink(world, random, 6, 2, -3);
		placeFoodOrDrink(world, random, 8, 2, -4);
		placeFoodOrDrink(world, random, 4, 2, -6);
		placeFoodOrDrink(world, random, 7, 2, -6);
		String[] tavernName = GOTNames.getTavernName(random);
		placeSign(world, 0, 3, -8, Blocks.wall_sign, 2, new String[]{"", tavernName[0], tavernName[1], ""});
		placeWallBanner(world, -8, 6, 0, GOTItemBanner.BannerType.HILLMEN, 1);
		placeWallBanner(world, 8, 6, 0, GOTItemBanner.BannerType.HILLMEN, 3);
		for (int k1 : new int[]{-3, 3}) {
			placeHillmanItemFrame(world, random, -3, 2, k1, 1);
			placeHillmanItemFrame(world, random, 3, 2, k1, 3);
		}
		GOTEntityNPC bartender = new GOTEntityHillmanBartender(world);
		if (random.nextBoolean()) {
			spawnNPCAndSetHome(bartender, world, 5, 1, -4, 2);
		} else {
			spawnNPCAndSetHome(bartender, world, 5, 1, 4, 2);
		}
		int men = MathHelper.getRandomIntegerInRange(random, 3, 8);
		for (int l = 0; l < men; ++l) {
			GOTEntityNPC man = new GOTEntityHillman(world);
			spawnNPCAndSetHome(man, world, 0, 1, 0, 16);
		}
		spawnNPCAndSetHome(new GOTEntityLightSkinThief(world), world, 0, 1, 0, 16);
		spawnNPCAndSetHome(new GOTEntityLightSkinTramp(world), world, 0, 1, 0, 16);
		return true;
	}

	private void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				placeMug(world, random, i, j, k, random.nextInt(4), GOTFoods.WILD_DRINK);
			} else {
				Block plateBlock = random.nextBoolean() ? GOTBlocks.woodPlate : GOTBlocks.ceramicPlate;
				if (random.nextBoolean()) {
					setBlockAndMetadata(world, i, j, k, plateBlock, 0);
				} else {
					placePlateWithCertainty(world, random, i, j, k, plateBlock, GOTFoods.WILD);
				}
			}
		}
	}
}