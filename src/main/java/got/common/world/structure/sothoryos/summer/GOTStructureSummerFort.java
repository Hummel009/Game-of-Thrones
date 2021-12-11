package got.common.world.structure.sothoryos.summer;

import java.util.Random;

import got.common.database.*;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.sothoryos.summer.*;
import got.common.item.other.GOTItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTStructureSummerFort extends GOTStructureSummerBase {
	public GOTStructureSummerFort(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		this.setOriginAndRotation(world, i, j, k, rotation, 12);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i12 = -15; i12 <= 15; ++i12) {
				for (int k1 = -15; k1 <= 15; ++k1) {
					int j12 = getTopBlock(world, i12, k1) - 1;
					if (!isSurface(world, i12, j12, k1)) {
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
		for (i1 = -15; i1 <= 15; ++i1) {
			for (int k1 = -15; k1 <= 15; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				boolean bedRegion = i2 <= 3 && k1 >= 5 && k1 <= 9 || i2 <= 2 && k1 == 4 || i2 <= 1 && k1 == 3;
				int airHeight = 7;
				for (j1 = 0; (j1 >= -1 || !isOpaque(world, i1, j1, k1)) && getY(j1) >= 0; --j1) {
					if (bedRegion && j1 == 0) {
						setAir(world, i1, j1, k1);
						continue;
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				if (bedRegion || i2 > 10 || k2 > 10 || random.nextInt(5) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, GOTRegistry.thatchFloor, 0);
			}
		}
		loadStrScan("summer_fort");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("WOOD|4", woodBlock, woodMeta | 4);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		associateBlockMetaAlias("PLANK2_SLAB", plank2SlabBlock, plank2SlabMeta);
		associateBlockMetaAlias("PLANK2_SLAB_INV", plank2SlabBlock, plank2SlabMeta | 8);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 1, 0);
		setBlockAndMetadata(world, 2, 0, 8, bedBlock, 0);
		setBlockAndMetadata(world, 2, 0, 9, bedBlock, 8);
		setBlockAndMetadata(world, 10, 1, 7, bedBlock, 0);
		setBlockAndMetadata(world, 10, 1, 8, bedBlock, 8);
		setBlockAndMetadata(world, 7, 1, 10, bedBlock, 1);
		setBlockAndMetadata(world, 8, 1, 10, bedBlock, 9);
		setBlockAndMetadata(world, -10, 1, 7, bedBlock, 0);
		setBlockAndMetadata(world, -10, 1, 8, bedBlock, 8);
		setBlockAndMetadata(world, -7, 1, 10, bedBlock, 3);
		setBlockAndMetadata(world, -8, 1, 10, bedBlock, 11);
		this.placeChest(world, random, 0, 0, 7, GOTRegistry.chestBasket, 3, GOTChestContents.ESSOS);
		this.placeChest(world, random, -9, 1, 9, GOTRegistry.chestBasket, 2, GOTChestContents.ESSOS);
		this.placeChest(world, random, 9, 1, 9, GOTRegistry.chestBasket, 2, GOTChestContents.ESSOS);
		for (i1 = -2; i1 <= 0; ++i1) {
			int j13 = 1;
			int k1 = 9;
			if (random.nextBoolean()) {
				placePlate(world, random, i1, j13, k1, GOTRegistry.ceramicPlateBlock, GOTFoods.SOTHORYOS);
				continue;
			}
			this.placeMug(world, random, i1, j13, k1, 0, GOTFoods.SOTHORYOS_DRINK);
		}
		placeWeaponRack(world, 4, 2, -1, 6, getRandomWeapon(random));
		placeWeaponRack(world, 5, 2, 0, 5, getRandomWeapon(random));
		placeWeaponRack(world, 4, 2, 1, 4, getRandomWeapon(random));
		placeWeaponRack(world, 3, 2, 0, 7, getRandomWeapon(random));
		placeWeaponRack(world, -4, 2, -1, 6, getRandomWeapon(random));
		placeWeaponRack(world, -3, 2, 0, 5, getRandomWeapon(random));
		placeWeaponRack(world, -4, 2, 1, 4, getRandomWeapon(random));
		placeWeaponRack(world, -5, 2, 0, 7, getRandomWeapon(random));
		placeSummerArmor(world, random, 9, 1, -3, 1);
		placeSummerArmor(world, random, 9, 1, 0, 1);
		placeSummerArmor(world, random, 9, 1, 3, 1);
		this.placeSkull(world, random, -8, 3, -4);
		this.placeSkull(world, random, -8, 3, 2);
		this.placeSkull(world, random, -10, 6, 0);
		this.placeSkull(world, random, 10, 6, 0);
		this.placeSkull(world, random, -13, 8, -15);
		this.placeSkull(world, random, 13, 8, -15);
		this.placeSkull(world, random, -13, 8, 15);
		this.placeSkull(world, random, 13, 8, 15);
		placeWallBanner(world, 0, 5, -13, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, -3, 4, -13, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, 3, 4, -13, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, 0, 6, 8, GOTItemBanner.BannerType.SUMMER, 2);
		setBlockAndMetadata(world, 7, 1, 5, GOTRegistry.commandTable, 0);
		GOTEntitySummerWarlord warlord = new GOTEntitySummerWarlord(world);
		warlord.spawnRidingHorse = false;
		spawnNPCAndSetHome(warlord, world, 0, 3, 7, 4);
		int warriors = 4 + random.nextInt(4);
		for (int l = 0; l < warriors; ++l) {
			GOTEntitySummerWarrior warrior = random.nextInt(3) == 0 ? new GOTEntitySummerArcher(world) : new GOTEntitySummerWarrior(world);
			warrior.spawnRidingHorse = false;
			spawnNPCAndSetHome(warrior, world, 0, 1, 0, 16);
		}
		for (int i13 : new int[] { -4, 4 }) {
			j1 = 1;
			int k1 = -6;
			GOTEntityHorse horse = new GOTEntityHorse(world);
			spawnNPCAndSetHome(horse, world, i13, j1, k1, 0);
			horse.setHorseType(0);
			horse.saddleMountForWorldGen();
			horse.detachHome();
			leashEntityTo(horse, world, i13, j1, k1);
		}
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClasses(GOTEntitySummerWarrior.class, GOTEntitySummerArcher.class);
		respawner.setCheckRanges(16, -8, 12, 12);
		respawner.setSpawnRanges(8, -2, 2, 16);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}

	public void placeSummerArmor(World world, Random random, int i, int j, int k, int meta) {
		ItemStack[] arritemStack;
		if (random.nextInt(3) != 0) {
			ItemStack[] arritemStack2 = new ItemStack[4];
			arritemStack2[0] = null;
			arritemStack2[1] = null;
			arritemStack2[2] = null;
			arritemStack = arritemStack2;
			arritemStack2[3] = null;
		} else {
			ItemStack[] arritemStack3 = new ItemStack[4];
			arritemStack3[0] = new ItemStack(GOTRegistry.summerHelmet);
			arritemStack3[1] = new ItemStack(GOTRegistry.summerChestplate);
			arritemStack3[2] = new ItemStack(GOTRegistry.summerLeggings);
			arritemStack = arritemStack3;
			arritemStack3[3] = new ItemStack(GOTRegistry.summerBoots);
		}
		ItemStack[] armor = arritemStack;
		placeArmorStand(world, i, j, k, meta, armor);
	}
}
