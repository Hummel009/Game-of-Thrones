package got.common.world.structure.westeros.other;

import java.util.Random;

import got.common.database.*;
import got.common.entity.westeros.hillmen.GOTEntityHillman;
import got.common.item.other.GOTItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureHillmanHouse extends GOTStructureHillmanBase {
	public GOTStructureHillmanHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 6);
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
				Math.abs(i1);
				Math.abs(k1);
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
		this.placeChest(world, random, -2, 1, 4, GOTRegistry.chestBasket, 2, GOTChestContents.WESTEROS);
		this.placeChest(world, random, 2, 1, 4, GOTRegistry.chestBasket, 2, GOTChestContents.WESTEROS);
		this.placeBarrel(world, random, -3, 2, -3, 4, GOTFoods.WILD_DRINK);
		placePlate(world, random, -3, 2, -2, GOTRegistry.woodPlateBlock, GOTFoods.WILD);
		placePlate(world, random, -3, 2, -1, GOTRegistry.woodPlateBlock, GOTFoods.WILD);
		this.placeMug(world, random, 3, 2, -3, 1, GOTFoods.WILD_DRINK);
		placePlate(world, random, 3, 2, -2, GOTRegistry.woodPlateBlock, GOTFoods.WILD);
		placePlate(world, random, 3, 2, -1, GOTRegistry.woodPlateBlock, GOTFoods.WILD);
		placeFlowerPot(world, -3, 2, 1, getRandomFlower(world, random));
		placeWeaponRack(world, 0, 3, -4, 4, getRandomHillmanWeapon(random));
		placeHillmanItemFrame(world, random, -2, 2, -5, 0);
		placeHillmanItemFrame(world, random, 2, 2, -5, 0);
		placeHillmanItemFrame(world, random, -2, 2, 5, 2);
		placeHillmanItemFrame(world, random, 2, 2, 5, 2);
		placeWallBanner(world, -2, 4, -6, GOTItemBanner.BannerType.HILLMEN, 2);
		placeWallBanner(world, 2, 4, -6, GOTItemBanner.BannerType.HILLMEN, 2);
		GOTEntityHillman male = new GOTEntityHillman(world);
		male.familyInfo.setMale(true);
		male.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(male, world, 0, 1, 0, 16);
		GOTEntityHillman female = new GOTEntityHillman(world);
		female.familyInfo.setMale(false);
		female.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(female, world, 0, 1, 0, 16);
		GOTEntityHillman child = new GOTEntityHillman(world);
		child.familyInfo.setMale(random.nextBoolean());
		child.familyInfo.setChild();
		spawnNPCAndSetHome(child, world, 0, 1, 0, 16);
		return true;
	}
}
