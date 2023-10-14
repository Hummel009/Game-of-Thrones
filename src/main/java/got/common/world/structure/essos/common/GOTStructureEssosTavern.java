package got.common.world.structure.essos.common;

import got.common.database.GOTBlocks;
import got.common.database.GOTFoods;
import got.common.database.GOTNames;
import got.common.entity.essos.GOTEntityDarkSkinScrapTrader;
import got.common.entity.essos.GOTEntityDarkSkinThief;
import got.common.entity.essos.gold.GOTEntityGoldenCaptain;
import got.common.entity.other.GOTEntityProstitute;
import got.common.entity.westeros.GOTEntityLightSkinScrapTrader;
import got.common.entity.westeros.GOTEntityLightSkinThief;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureEssosTavern extends GOTStructureEssosBase {
	public GOTStructureEssosTavern(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 16);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i1 = -6; i1 <= 6; ++i1) {
				for (k1 = -16; k1 <= 16; ++k1) {
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
					if (maxHeight - minHeight <= 10) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i12 = -5; i12 <= 5; ++i12) {
			for (int k12 = -15; k12 <= 15; ++k12) {
				j1 = 0;
				while (!isOpaque(world, i12, j1, k12) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i12, j1, k12, stoneBlock, stoneMeta);
					setGrassToDirt(world, i12, j1 - 1, k12);
					--j1;
				}
				for (j1 = 1; j1 <= 9; ++j1) {
					setAir(world, i12, j1, k12);
				}
			}
		}
		loadStrScan("essos_tavern");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta4);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta8);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		generateStrScan(world, random, 0, 0, 0);
		String[] tavernName = GOTNames.getTavernName(random);
		placeWeaponRack(world, 4, 3, -4, 7, getRandomWeapon(random));
		placeWeaponRack(world, -4, 3, 4, 5, getRandomWeapon(random));
		spawnItemFrame(world, 5, 3, -8, 3, getRandomItem(random));
		spawnItemFrame(world, -5, 3, -4, 1, getRandomItem(random));
		spawnItemFrame(world, 5, 3, 4, 3, getRandomItem(random));
		placeFoodOrDrink(world, random, -2, 2, -12);
		placeFoodOrDrink(world, random, -2, 2, -11);
		for (i1 = 0; i1 <= 2; ++i1) {
			for (k1 = -9; k1 <= -7; ++k1) {
				placeFoodOrDrink(world, random, i1, 2, k1);
			}
		}
		placeFoodOrDrink(world, random, -2, 2, -5);
		placeFoodOrDrink(world, random, -2, 2, -4);
		placeFoodOrDrink(world, random, 1, 2, -4);
		placeFoodOrDrink(world, random, 2, 2, -4);
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = 1; k1 <= 3; ++k1) {
				if (i1 == 0 && k1 == 2) {
					setBlockAndMetadata(world, 0, 2, 2, GOTBlocks.lemonCake, 0);
					continue;
				}
				placeFoodOrDrink(world, random, i1, 2, k1);
			}
		}
		placeFoodOrDrink(world, random, -3, 2, 7);
		placeFoodOrDrink(world, random, -2, 2, 7);
		placeFoodOrDrink(world, random, -1, 2, 7);
		placeKebabStand(world, random, -4, 2, 9, GOTBlocks.kebabStand, 4);
		placeChest(world, random, 3, 1, 14, GOTBlocks.chestBasket, 2, getChestContents());
		placeBarrel(world, random, 4, 2, 11, 5, GOTFoods.ESSOS_DRINK);
		placeBarrel(world, random, 4, 2, 12, 5, GOTFoods.ESSOS_DRINK);
		setBlockAndMetadata(world, -3, 8, -13, bedBlock, 2);
		setBlockAndMetadata(world, -3, 8, -14, bedBlock, 10);
		setBlockAndMetadata(world, -4, 8, -13, bedBlock, 2);
		setBlockAndMetadata(world, -4, 8, -14, bedBlock, 10);
		placeFlowerPot(world, -1, 9, -14, getRandomFlower(world, random));
		setBlockAndMetadata(world, -3, 8, -5, bedBlock, 0);
		setBlockAndMetadata(world, -3, 8, -4, bedBlock, 8);
		setBlockAndMetadata(world, -4, 8, -5, bedBlock, 0);
		setBlockAndMetadata(world, -4, 8, -4, bedBlock, 8);
		placeFlowerPot(world, -1, 9, -4, getRandomFlower(world, random));
		setBlockAndMetadata(world, -3, 8, -1, bedBlock, 2);
		setBlockAndMetadata(world, -3, 8, -2, bedBlock, 10);
		setBlockAndMetadata(world, -4, 8, -1, bedBlock, 2);
		setBlockAndMetadata(world, -4, 8, -2, bedBlock, 10);
		placeFlowerPot(world, -1, 9, -2, getRandomFlower(world, random));
		setBlockAndMetadata(world, -3, 8, 7, bedBlock, 0);
		setBlockAndMetadata(world, -3, 8, 8, bedBlock, 8);
		setBlockAndMetadata(world, -4, 8, 7, bedBlock, 0);
		setBlockAndMetadata(world, -4, 8, 8, bedBlock, 8);
		placeFlowerPot(world, -1, 9, 8, getRandomFlower(world, random));
		placeFlowerPot(world, 1, 9, -3, getRandomFlower(world, random));
		placeWallBanner(world, -2, 5, -15, bannerType, 0);
		placeWallBanner(world, 2, 5, -15, bannerType, 0);
		spawnNPCAndSetHome(getBartender(world), world, -2, 1, 8, 4);
		spawnNPCAndSetHome(new GOTEntityGoldenCaptain(world), world, 0, 1, 0, 16);
		if (hasDarkSkinPeople()) {
			spawnNPCAndSetHome(new GOTEntityDarkSkinThief(world), world, 0, 1, 0, 16);
			spawnNPCAndSetHome(new GOTEntityDarkSkinScrapTrader(world), world, 0, 1, 0, 16);
		} else {
			spawnNPCAndSetHome(new GOTEntityLightSkinThief(world), world, 0, 1, 0, 16);
			spawnNPCAndSetHome(new GOTEntityLightSkinScrapTrader(world), world, 0, 1, 0, 16);
		}
		for (int l = 0; l < 5; ++l) {
			spawnNPCAndSetHome(new GOTEntityProstitute(world), world, 0, 1, 0, 16);
		}
		block11:
		for (int i13 = -1; i13 <= 1; ++i13) {
			int j12 = 0;
			for (int step = 0; step < 12; ++step) {
				int j2;
				int k13 = -17 - step;
				if (isOpaque(world, i13, j12 + 1, k13)) {
					j12++;
					setAir(world, i13, j12 + 1, k13);
					setAir(world, i13, j12 + 2, k13);
					setAir(world, i13, j12 + 3, k13);
					setBlockAndMetadata(world, i13, j12, k13, stoneStairBlock, 3);
					setGrassToDirt(world, i13, j12 - 1, k13);
					j2 = j12 - 1;
					while (!isOpaque(world, i13, j2, k13) && getY(j2) >= 0) {
						setBlockAndMetadata(world, i13, j2, k13, stoneBlock, stoneMeta);
						setGrassToDirt(world, i13, j2 - 1, k13);
						--j2;
					}
					continue;
				}
				if (isOpaque(world, i13, j12, k13)) {
					continue block11;
				}
				setAir(world, i13, j12 + 1, k13);
				setAir(world, i13, j12 + 2, k13);
				setAir(world, i13, j12 + 3, k13);
				setBlockAndMetadata(world, i13, j12, k13, stoneStairBlock, 2);
				setGrassToDirt(world, i13, j12 - 1, k13);
				j2 = j12 - 1;
				while (!isOpaque(world, i13, j2, k13) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i13, j2, k13, stoneBlock, stoneMeta);
					setGrassToDirt(world, i13, j2 - 1, k13);
					--j2;
				}
				--j12;
			}
		}
		setBlockAndMetadata(world, 0, 5, -16, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 5, -17, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 4, -17, plankBlock, plankMeta);
		placeSign(world, -1, 4, -17, Blocks.wall_sign, 5, new String[]{"", tavernName[0], tavernName[1], ""});
		placeSign(world, 0, 4, -18, Blocks.wall_sign, 2, new String[]{"", tavernName[0], tavernName[1], ""});
		placeSign(world, 1, 4, -17, Blocks.wall_sign, 4, new String[]{"", tavernName[0], tavernName[1], ""});
		return true;
	}

	public void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				placeMug(world, random, i, j, k, random.nextInt(4), GOTFoods.ESSOS_DRINK);
			} else {
				Block plateBlock;
				plateBlock = random.nextBoolean() ? GOTBlocks.woodPlate : GOTBlocks.ceramicPlate;
				if (random.nextBoolean()) {
					setBlockAndMetadata(world, i, j, k, plateBlock, 0);
				} else {
					placePlateWithCertainty(world, random, i, j, k, plateBlock, GOTFoods.ESSOS);
				}
			}
		}
	}
}
