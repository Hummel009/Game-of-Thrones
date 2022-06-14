package got.common.world.structure.sothoryos.summer;

import java.util.Random;

import got.common.database.*;
import got.common.entity.sothoryos.summer.GOTEntitySummerMan;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureSummerHouse extends GOTStructureSummerBase {
	public GOTStructureSummerHouse(boolean flag) {
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
			for (int i1 = -7; i1 <= 7; ++i1) {
				for (int k1 = -7; k1 <= 7; ++k1) {
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
		for (int i1 = -6; i1 <= 6; ++i1) {
			for (int k1 = -6; k1 <= 6; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if ((i2 > 2 || k2 > 6) && (k2 > 2 || i2 > 6)) {
					continue;
				}
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		if (isRuined()) {
			loadStrScan("summer_house_ruined");
		} else {
			loadStrScan("summer_house");
		}
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		if (isRuined()) {
			setBlockAliasChance("PLANK2", 0.8f);
		}
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		generateStrScan(world, random, 0, 1, 0);
		if (!isRuined()) {
			setBlockAndMetadata(world, 0, 1, 3, bedBlock, 0);
			setBlockAndMetadata(world, 0, 1, 4, bedBlock, 8);
			placeWeaponRack(world, 0, 3, -4, 4, getRandomWeapon(random));
			placeWeaponRack(world, 0, 3, 4, 6, getRandomWeapon(random));
			this.placeChest(world, random, -4, 1, 0, GOTRegistry.chestBasket, 4, GOTChestContents.SUMMER);
			placePlate(world, random, 4, 2, 0, GOTRegistry.ceramicPlateBlock, GOTFoods.SOTHORYOS);
			placePlate(world, random, -1, 2, 4, GOTRegistry.ceramicPlateBlock, GOTFoods.SOTHORYOS);
			this.placeMug(world, random, 1, 2, 4, 0, GOTFoods.SOTHORYOS_DRINK);
			GOTEntitySummerMan male = new GOTEntitySummerMan(world);
			male.familyInfo.setMale(true);
			male.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
			spawnNPCAndSetHome(male, world, 0, 1, -1, 16);
			GOTEntitySummerMan female = new GOTEntitySummerMan(world);
			female.familyInfo.setMale(false);
			female.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
			spawnNPCAndSetHome(female, world, 0, 1, -1, 16);
			GOTEntitySummerMan child = new GOTEntitySummerMan(world);
			child.familyInfo.setMale(random.nextBoolean());
			child.familyInfo.setChild();
			spawnNPCAndSetHome(child, world, 0, 1, -1, 16);
		}
		return true;
	}
}
