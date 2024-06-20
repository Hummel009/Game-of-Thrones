package got.common.world.structure.essos.lhazar;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.essos.lhazar.GOTEntityLhazarBlacksmith;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureLhazarSmithy extends GOTStructureLhazarBase {
	public GOTStructureLhazarSmithy(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int k1;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -14; i12 <= 6; ++i12) {
				for (int k12 = -6; k12 <= 6; ++k12) {
					j1 = getTopBlock(world, i12, k12) - 1;
					if (!isSurface(world, i12, j1, k12)) {
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
		for (int i13 = -14; i13 <= 6; ++i13) {
			for (int k13 = -6; k13 <= 6; ++k13) {
				int i2 = Math.abs(i13);
				int k2 = Math.abs(k13);
				if (i2 * i2 + k2 * k2 >= 25 && (i13 > -7 || k2 > 5)) {
					continue;
				}
				for (j1 = 1; j1 <= 5; ++j1) {
					setAir(world, i13, j1, k13);
				}
			}
		}
		loadStrScan("lhazar_smithy");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		associateBlockMetaAlias("PLANK2_SLAB", plank2SlabBlock, plank2SlabMeta);
		associateBlockMetaAlias("PLANK2_SLAB_INV", plank2SlabBlock, plank2SlabMeta | 8);
		associateBlockAlias("PLANK2_STAIR", plank2StairBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("FLAG", flagBlock, flagMeta);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 0, 0);
		setBlockAndMetadata(world, 0, 1, 3, bedBlock, 0);
		setBlockAndMetadata(world, 0, 1, 4, bedBlock, 8);
		placeChest(world, random, -4, 1, -2, GOTBlocks.chestBasket, 3, GOTChestContents.LHAZAR);
		placeFlowerPot(world, 2, 2, -4, getRandomFlower(world, random));
		placeFlowerPot(world, -2, 2, 4, getRandomFlower(world, random));
		placeFlowerPot(world, -4, 1, 1, new ItemStack(Blocks.cactus));
		placeMug(world, random, 4, 2, -1, 1, GOTFoods.DEFAULT_DRINK);
		placeMug(world, random, 2, 2, 4, 0, GOTFoods.DEFAULT_DRINK);
		placePlate(world, random, 4, 2, 0, GOTBlocks.woodPlate, GOTFoods.DEFAULT);
		placePlate(world, random, 4, 2, 1, GOTBlocks.woodPlate, GOTFoods.DEFAULT);
		placeArmorStand(world, -7, 1, -2, 1, getRandArmorItems(random));
		placeWeaponRack(world, -13, 3, 0, 5, getRandWeaponItem(random));
		GOTEntityNPC smith = new GOTEntityLhazarBlacksmith(world);
		spawnNPCAndSetHome(smith, world, -6, 1, 0, 8);
		int maxSteps = 12;
		for (int step = 0; step < maxSteps && !isOpaque(world, i1 = -9, j1 = -step, k1 = -5 - step); ++step) {
			setBlockAndMetadata(world, i1, j1, k1, GOTBlocks.stairsRedSandstone, 2);
			setGrassToDirt(world, i1, j1 - 1, k1);
			int j2 = j1 - 1;
			while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
				setBlockAndMetadata(world, i1, j2, k1, GOTBlocks.redSandstone, 0);
				setGrassToDirt(world, i1, j2 - 1, k1);
				--j2;
			}
		}
		return true;
	}
}