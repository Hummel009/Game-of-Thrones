package got.common.world.structure.sothoryos.summer;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.sothoryos.summer.GOTEntitySummerBlacksmith;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSummerSmithy extends GOTStructureSummerBase {
	public GOTStructureSummerSmithy(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 5);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -12; i1 <= 8; ++i1) {
				for (int k1 = -6; k1 <= 6; ++k1) {
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
		for (int i1 = -10; i1 <= 6; ++i1) {
			for (int k1 = -6; k1 <= 6; ++k1) {
				int k2 = Math.abs(k1);
				if ((i1 < -8 || i1 > 4 || k2 != 4) && k2 > 3) {
					continue;
				}
				j1 = -1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, plank2Block, plank2Meta);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("summer_smithy");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		generateStrScan(world, random, 0, 1, 0);
		placeWeaponRack(world, -1, 2, -1, 5, getRandWeaponItem(random));
		placeWeaponRack(world, -1, 2, 1, 5, getRandWeaponItem(random));
		placeArmorStand(world, 3, 1, 3, 2, getRandArmorItems(random));
		placeArmorStand(world, 0, 1, 3, 0, getRandArmorItems(random));
		placeChest(world, random, 5, 1, -2, GOTBlocks.chestBasket, 5, GOTChestContents.SUMMER);
		placeChest(world, random, -7, 1, 3, GOTBlocks.chestBasket, 2, GOTChestContents.SUMMER);
		placeBarrel(world, random, -3, 2, -1, 5, GOTFoods.DEFAULT_DRINK);
		placeMug(world, random, -3, 2, 0, 2, GOTFoods.DEFAULT_DRINK);
		placeMug(world, random, -9, 2, -2, 3, GOTFoods.DEFAULT_DRINK);
		placePlate(world, random, -5, 2, 3, GOTBlocks.ceramicPlate, GOTFoods.DEFAULT);
		placePlate(world, random, -3, 2, 3, GOTBlocks.ceramicPlate, GOTFoods.DEFAULT);
		placeFlowerPot(world, -4, 2, 3, getRandomFlower(world, random));
		setBlockAndMetadata(world, -8, 1, 1, bedBlock, 3);
		setBlockAndMetadata(world, -9, 1, 1, bedBlock, 11);
		GOTEntityNPC smith = new GOTEntitySummerBlacksmith(world);
		spawnNPCAndSetHome(smith, world, 0, 1, 0, 8);
		return true;
	}
}