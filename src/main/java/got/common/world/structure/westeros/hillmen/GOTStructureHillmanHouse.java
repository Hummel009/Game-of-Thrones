package got.common.world.structure.westeros.hillmen;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.hillmen.GOTEntityHillman;
import got.common.item.other.GOTItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureHillmanHouse extends GOTStructureHillmanBase {
	public GOTStructureHillmanHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i13 = -4; i13 <= 4; ++i13) {
				for (int k12 = -6; k12 <= 6; ++k12) {
					int j13 = getTopBlock(world, i13, k12);
					getBlock(world, i13, j13 - 1, k12);
					if (j13 < minHeight) {
						minHeight = j13;
					}
					if (j13 > maxHeight) {
						maxHeight = j13;
					}
					if (maxHeight - minHeight <= 4) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -4; i1 <= 4; ++i1) {
			for (int k1 = -6; k1 <= 5; ++k1) {
				if (k1 >= -5) {
					j1 = -1;
					while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
						setBlockAndMetadata(world, i1, j1, k1, floorBlock, floorMeta);
						setGrassToDirt(world, i1, j1 - 1, k1);
						--j1;
					}
				}
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("hillman_house");
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("WOOD|8", woodBlock, woodMeta | 8);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("BARS", barsBlock, barsMeta);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, 0, 1, 3, bedBlock, 0);
		setBlockAndMetadata(world, 0, 1, 4, bedBlock, 8);
		placeChest(world, random, -2, 1, 4, GOTBlocks.chestBasket, 2, GOTChestContents.HILLMEN);
		placeChest(world, random, 2, 1, 4, GOTBlocks.chestBasket, 2, GOTChestContents.HILLMEN);
		placeBarrel(world, random, -3, 2, -3, 4, GOTFoods.DEFAULT_DRINK);
		placePlate(world, random, -3, 2, -2, GOTBlocks.woodPlate, GOTFoods.DEFAULT);
		placePlate(world, random, -3, 2, -1, GOTBlocks.woodPlate, GOTFoods.DEFAULT);
		placeMug(world, random, 3, 2, -3, 1, GOTFoods.DEFAULT_DRINK);
		placePlate(world, random, 3, 2, -2, GOTBlocks.woodPlate, GOTFoods.DEFAULT);
		placePlate(world, random, 3, 2, -1, GOTBlocks.woodPlate, GOTFoods.DEFAULT);
		placeFlowerPot(world, -3, 2, 1, getRandomFlower(world, random));
		placeWeaponRack(world, 0, 3, -4, 4, getRandWeaponItem(random));
		spawnItemFrame(world, -2, 2, -5, 0, getRandFrameItem(random));
		spawnItemFrame(world, 2, 2, -5, 0, getRandFrameItem(random));
		spawnItemFrame(world, -2, 2, 5, 2, getRandFrameItem(random));
		spawnItemFrame(world, 2, 2, 5, 2, getRandFrameItem(random));
		placeWallBanner(world, -2, 4, -6, GOTItemBanner.BannerType.HILLMEN, 2);
		placeWallBanner(world, 2, 4, -6, GOTItemBanner.BannerType.HILLMEN, 2);
		GOTEntityNPC male = new GOTEntityHillman(world);
		male.getFamilyInfo().setMale(true);
		male.setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldRing));
		spawnNPCAndSetHome(male, world, 0, 1, 0, 16);
		GOTEntityNPC female = new GOTEntityHillman(world);
		female.getFamilyInfo().setMale(false);
		female.setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldRing));
		spawnNPCAndSetHome(female, world, 0, 1, 0, 16);
		GOTEntityNPC child = new GOTEntityHillman(world);
		child.getFamilyInfo().setMale(random.nextBoolean());
		child.getFamilyInfo().setChild();
		spawnNPCAndSetHome(child, world, 0, 1, 0, 16);
		return true;
	}
}