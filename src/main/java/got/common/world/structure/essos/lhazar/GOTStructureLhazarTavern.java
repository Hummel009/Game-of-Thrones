package got.common.world.structure.essos.lhazar;

import java.util.Random;

import got.common.database.*;
import got.common.entity.essos.lhazar.*;
import got.common.item.other.GOTItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTStructureLhazarTavern extends GOTStructureLhazarBase {
	public GOTStructureLhazarTavern(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int step;
		int j2;
		int k1;
		int i1;
		int j1;
		int i12;
		int j12;
		this.setOriginAndRotation(world, i, j, k, rotation, 10);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i12 = -10; i12 <= 10; ++i12) {
				for (int k12 = -10; k12 <= 10; ++k12) {
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
					if (maxHeight - minHeight <= 8) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i13 = -10; i13 <= 10; ++i13) {
			for (int k13 = -10; k13 <= 10; ++k13) {
				int k2;
				int i2 = Math.abs(i13);
				k2 = Math.abs(k13);
				if (i2 * i2 + k2 * k2 >= 100) {
					continue;
				}
				for (j12 = 1; j12 <= 6; ++j12) {
					setAir(world, i13, j12, k13);
				}
			}
		}
		loadStrScan("lhazar_tavern");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		associateBlockAlias("PLANK2_STAIR", plank2StairBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		generateStrScan(world, random, 0, 0, 0);
		String[] tavernName = GOTNames.getTavernName(random);
		placeSign(world, 0, 3, -10, Blocks.wall_sign, 2, new String[] { "", tavernName[0], tavernName[1], "" });
		placeSign(world, 0, 3, 10, Blocks.wall_sign, 3, new String[] { "", tavernName[0], tavernName[1], "" });
		this.placeBarrel(world, random, -3, 2, -2, 4, GOTFoods.NOMAD_DRINK);
		this.placeBarrel(world, random, 3, 2, 1, 5, GOTFoods.NOMAD_DRINK);
		placeFlowerPot(world, 3, 2, -2, getRandomFlower(world, random));
		placeFlowerPot(world, -3, 2, 1, getRandomFlower(world, random));
		placeKebabStand(world, random, -2, 2, 2, GOTRegistry.kebabStand, 2);
		placeKebabStand(world, random, 2, 2, 2, GOTRegistry.kebabStand, 2);
		placeWallBanner(world, -2, 4, -3, GOTItemBanner.BannerType.LHAZAR, 2);
		placeWallBanner(world, 2, 4, -3, GOTItemBanner.BannerType.LHAZAR, 2);
		placeWallBanner(world, -2, 4, 3, GOTItemBanner.BannerType.LHAZAR, 0);
		placeWallBanner(world, 2, 4, 3, GOTItemBanner.BannerType.LHAZAR, 0);
		placeFoodOrDrink(world, random, -5, 2, -7);
		placeFoodOrDrink(world, random, -5, 2, -6);
		placeFoodOrDrink(world, random, -6, 2, -6);
		placeFoodOrDrink(world, random, -6, 2, -5);
		placeFoodOrDrink(world, random, -7, 2, -5);
		placeFoodOrDrink(world, random, -6, 2, -1);
		placeFoodOrDrink(world, random, -6, 2, 0);
		placeFoodOrDrink(world, random, -6, 2, 1);
		placeFoodOrDrink(world, random, -5, 2, 7);
		placeFoodOrDrink(world, random, -5, 2, 6);
		placeFoodOrDrink(world, random, -6, 2, 6);
		placeFoodOrDrink(world, random, -6, 2, 5);
		placeFoodOrDrink(world, random, -7, 2, 5);
		placeFoodOrDrink(world, random, 5, 2, -7);
		placeFoodOrDrink(world, random, 5, 2, -6);
		placeFoodOrDrink(world, random, 6, 2, -6);
		placeFoodOrDrink(world, random, 6, 2, -5);
		placeFoodOrDrink(world, random, 7, 2, -5);
		placeFoodOrDrink(world, random, 6, 2, -1);
		placeFoodOrDrink(world, random, 6, 2, 0);
		placeFoodOrDrink(world, random, 6, 2, 1);
		placeFoodOrDrink(world, random, 5, 2, 7);
		placeFoodOrDrink(world, random, 5, 2, 6);
		placeFoodOrDrink(world, random, 6, 2, 6);
		placeFoodOrDrink(world, random, 6, 2, 5);
		placeFoodOrDrink(world, random, 7, 2, 5);
		for (i12 = -2; i12 <= 2; ++i12) {
			placeFoodOrDrink(world, random, i12, 2, -3);
			placeFoodOrDrink(world, random, i12, 2, 3);
		}
		GOTEntityLhazarBartender bartender = new GOTEntityLhazarBartender(world);
		spawnNPCAndSetHome(bartender, world, 0, 1, 0, 4);
		int men = MathHelper.getRandomIntegerInRange(random, 3, 8);
		for (int l = 0; l < men; ++l) {
			GOTEntityLhazarMan haradrim = new GOTEntityLhazarMan(world);
			spawnNPCAndSetHome(haradrim, world, random.nextBoolean() ? -5 : 5, 1, 0, 16);
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (step = 0; step < 12 && !isOpaque(world, i1, j1 = 0 - step, k1 = -10 - step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.stairsRedSandstone, 2);
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, GOTRegistry.redSandstone, 0);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (step = 0; step < 12 && !isOpaque(world, i1, j1 = 0 - step, k1 = 10 + step); ++step) {
				setBlockAndMetadata(world, i1, j1, k1, GOTRegistry.stairsRedSandstone, 3);
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, GOTRegistry.redSandstone, 0);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
			}
		}
		return true;
	}

	public void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				this.placeMug(world, random, i, j, k, random.nextInt(4), GOTFoods.NOMAD_DRINK);
			} else {
				Block plateBlock;
				plateBlock = random.nextBoolean() ? GOTRegistry.woodPlateBlock : GOTRegistry.ceramicPlateBlock;
				if (random.nextBoolean()) {
					setBlockAndMetadata(world, i, j, k, plateBlock, 0);
				} else {
					placePlateWithCertainty(world, random, i, j, k, plateBlock, GOTFoods.NOMAD);
				}
			}
		}
	}
}
