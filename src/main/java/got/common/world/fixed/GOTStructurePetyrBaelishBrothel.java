package got.common.world.fixed;

import java.util.Random;

import got.common.database.*;
import got.common.entity.other.GOTEntityProstitute;
import got.common.entity.westeros.legendary.quest.GOTEntityTyrionLannister;
import got.common.entity.westeros.legendary.reborn.GOTEntityLancelLannister;
import got.common.entity.westeros.legendary.trader.GOTEntityPetyrBaelish;
import got.common.entity.westeros.legendary.warrior.*;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.westeros.north.GOTStructureNorthBase;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class GOTStructurePetyrBaelishBrothel extends GOTStructureNorthBase {
	public GOTStructurePetyrBaelishBrothel(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		int k1;
		this.setOriginAndRotation(world, i, j, k, rotation, 16);
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
					setBlockAndMetadata(world, i12, j1, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i12, j1 - 1, k12);
					--j1;
				}
				for (j1 = 1; j1 <= 9; ++j1) {
					setAir(world, i12, j1, k12);
				}
			}
		}
		loadStrScan("essos_tavern");
		associateBlockMetaAlias("STONE", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		generateStrScan(world, random, 0, 0, 0);
		placeWeaponRack(world, 4, 3, -4, 7, getFramedItem(random));
		placeWeaponRack(world, -4, 3, 4, 5, getFramedItem(random));
		spawnItemFrame(world, 5, 3, -8, 3, getFramedItem(random));
		spawnItemFrame(world, -5, 3, -4, 1, getFramedItem(random));
		spawnItemFrame(world, 5, 3, 4, 3, getFramedItem(random));
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
					setBlockAndMetadata(world, i1, 2, k1, GOTRegistry.lemonCake, 0);
					continue;
				}
				placeFoodOrDrink(world, random, i1, 2, k1);
			}
		}
		placeFoodOrDrink(world, random, -3, 2, 7);
		placeFoodOrDrink(world, random, -2, 2, 7);
		placeFoodOrDrink(world, random, -1, 2, 7);
		placeKebabStand(world, random, -4, 2, 9, GOTRegistry.kebabStand, 4);
		this.placeChest(world, random, 3, 1, 14, GOTRegistry.chestBasket, 2, GOTChestContents.WESTEROS);
		this.placeBarrel(world, random, 4, 2, 11, 5, GOTFoods.WESTEROS_DRINK);
		this.placeBarrel(world, random, 4, 2, 12, 5, GOTFoods.WESTEROS_DRINK);
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
		placeWallBanner(world, -2, 5, -15, GOTItemBanner.BannerType.BAELISH, 0);
		placeWallBanner(world, 2, 5, -15, GOTItemBanner.BannerType.BAELISH, 0);
		spawnLegendaryNPC(new GOTEntityPetyrBaelish(world), world, -2, 1, 8);
		for (int l = 0; l < 5; ++l) {
			spawnNPCAndSetHome(new GOTEntityProstitute(world), world, 0, 1, 0, 16);
		}
		spawnLegendaryNPC(new GOTEntityTyrionLannister(world), world, 2, 1, -2);
		spawnLegendaryNPC(new GOTEntityLancelLannister.Normal(world), world, -2, 1, 2);
		spawnLegendaryNPC(new GOTEntityBronn(world), world, 2, 1, 2);
		spawnLegendaryNPC(new GOTEntityPodrickPayne(world), world, -2, 1, -2);
		block11: for (int i13 = -1; i13 <= 1; ++i13) {
			int j12 = 0;
			for (int step = 0; step < 12; ++step) {
				int j2;
				int k13 = -17 - step;
				if (isOpaque(world, i13, j12 + 1, k13)) {
					setAir(world, i13, ++j12 + 1, k13);
					setAir(world, i13, j12 + 2, k13);
					setAir(world, i13, j12 + 3, k13);
					setBlockAndMetadata(world, i13, j12, k13, brickStairBlock, 3);
					setGrassToDirt(world, i13, j12 - 1, k13);
					j2 = j12 - 1;
					while (!isOpaque(world, i13, j2, k13) && getY(j2) >= 0) {
						setBlockAndMetadata(world, i13, j2, k13, brickBlock, brickMeta);
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
				setBlockAndMetadata(world, i13, j12, k13, brickStairBlock, 2);
				setGrassToDirt(world, i13, j12 - 1, k13);
				j2 = j12 - 1;
				while (!isOpaque(world, i13, j2, k13) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i13, j2, k13, brickBlock, brickMeta);
					setGrassToDirt(world, i13, j2 - 1, k13);
					--j2;
				}
				--j12;
			}
		}
		setBlockAndMetadata(world, 0, 5, -16, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 5, -17, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 4, -17, plankBlock, plankMeta);
		return true;
	}

	public void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				this.placeMug(world, random, i, j, k, random.nextInt(4), GOTFoods.WESTEROS_DRINK);
			} else {
				Block plateBlock;
				plateBlock = random.nextBoolean() ? GOTRegistry.woodPlateBlock : GOTRegistry.ceramicPlateBlock;
				if (random.nextBoolean()) {
					setBlockAndMetadata(world, i, j, k, plateBlock, 0);
				} else {
					placePlateWithCertainty(world, random, i, j, k, plateBlock, GOTFoods.WESTEROS);
				}
			}
		}
	}
}