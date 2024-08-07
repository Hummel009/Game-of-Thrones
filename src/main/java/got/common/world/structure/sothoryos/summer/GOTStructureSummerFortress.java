package got.common.world.structure.sothoryos.summer;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
import got.common.entity.sothoryos.summer.GOTEntitySummerCaptain;
import got.common.entity.sothoryos.summer.GOTEntitySummerSoldier;
import got.common.entity.sothoryos.summer.GOTEntitySummerSoldierArcher;
import got.common.item.other.GOTItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSummerFortress extends GOTStructureSummerBase {
	public GOTStructureSummerFortress(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 12);
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
				int bedRegion = i2 <= 3 && k1 >= 5 && k1 <= 9 || i2 <= 2 && k1 == 4 || i2 <= 1 && k1 == 3 ? 1 : 0;
				int airHeight = 7;
				for (j1 = 0; j1 <= airHeight; ++j1) {
					setAir(world, i1, j1, k1);
				}
				for (j1 = 0; !(j1 < -1 && isOpaque(world, i1, j1, k1) || getY(j1) < 0); --j1) {
					if (bedRegion != 0 && j1 == 0) {
						continue;
					}
					if (j1 == 0) {
						int randomGround;
						if (i2 <= 11 && k2 <= 11) {
							if (random.nextBoolean()) {
								setBlockAndMetadata(world, i1, 0, k1, GOTBlocks.dirtPath, 0);
							} else {
								randomGround = random.nextInt(3);
								if (randomGround == 0) {
									setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
								} else if (randomGround == 1) {
									setBlockAndMetadata(world, i1, 0, k1, Blocks.dirt, 1);
								} else {
									setBlockAndMetadata(world, i1, 0, k1, Blocks.sand, 0);
								}
							}
						} else {
							randomGround = random.nextInt(3);
							if (randomGround == 0) {
								setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
							} else if (randomGround == 1) {
								setBlockAndMetadata(world, i1, 0, k1, Blocks.dirt, 1);
							} else {
								setBlockAndMetadata(world, i1, 0, k1, Blocks.sand, 0);
							}
						}
					} else {
						setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 0);
					}
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				if (bedRegion != 0 || i2 > 10 || k2 > 10 || random.nextInt(5) != 0) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k1, GOTBlocks.thatchFloor, 0);
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
		associateBlockMetaAlias("PLANK2", plank2Block, plank2Meta);
		associateBlockMetaAlias("PLANK2_SLAB", plank2SlabBlock, plank2SlabMeta);
		associateBlockMetaAlias("PLANK2_SLAB_INV", plank2SlabBlock, plank2SlabMeta | 8);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
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
		placeChest(world, random, 0, 0, 7, GOTBlocks.chestBasket, 3, GOTChestContents.SUMMER);
		placeChest(world, random, -9, 1, 9, GOTBlocks.chestBasket, 2, GOTChestContents.SUMMER);
		placeChest(world, random, 9, 1, 9, GOTBlocks.chestBasket, 2, GOTChestContents.SUMMER);
		for (i1 = -2; i1 <= 0; ++i1) {
			int j13 = 1;
			int k1 = 9;
			if (random.nextBoolean()) {
				placePlate(world, random, i1, j13, k1, GOTBlocks.ceramicPlate, GOTFoods.DEFAULT);
				continue;
			}
			placeMug(world, random, i1, j13, k1, 0, GOTFoods.DEFAULT_DRINK);
		}
		placeWeaponRack(world, 4, 2, -1, 6, getRandWeaponItem(random));
		placeWeaponRack(world, 5, 2, 0, 5, getRandWeaponItem(random));
		placeWeaponRack(world, 4, 2, 1, 4, getRandWeaponItem(random));
		placeWeaponRack(world, 3, 2, 0, 7, getRandWeaponItem(random));
		placeWeaponRack(world, -4, 2, -1, 6, getRandWeaponItem(random));
		placeWeaponRack(world, -3, 2, 0, 5, getRandWeaponItem(random));
		placeWeaponRack(world, -4, 2, 1, 4, getRandWeaponItem(random));
		placeWeaponRack(world, -5, 2, 0, 7, getRandWeaponItem(random));
		placeArmorStand(world, 9, 1, -3, 1, getRandArmorItems(random));
		placeArmorStand(world, 9, 1, 0, 1, getRandArmorItems(random));
		placeArmorStand(world, 9, 1, 3, 1, getRandArmorItems(random));
		placeSkull(world, random, -8, 3, -4);
		placeSkull(world, random, -8, 3, 2);
		placeSkull(world, random, -10, 6, 0);
		placeSkull(world, random, 10, 6, 0);
		placeSkull(world, random, -13, 8, -15);
		placeSkull(world, random, 13, 8, -15);
		placeSkull(world, random, -13, 8, 15);
		placeSkull(world, random, 13, 8, 15);
		placeWallBanner(world, 0, 5, -13, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, -3, 4, -13, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, 3, 4, -13, GOTItemBanner.BannerType.SUMMER, 2);
		placeWallBanner(world, 0, 6, 8, GOTItemBanner.BannerType.SUMMER, 2);
		setBlockAndMetadata(world, 7, 1, 5, GOTBlocks.commandTable, 0);
		GOTEntityNPC warlord = new GOTEntitySummerCaptain(world);
		warlord.setSpawnRidingHorse(false);
		spawnNPCAndSetHome(warlord, world, 0, 3, 7, 4);
		int warriors = 4 + random.nextInt(4);
		for (int l = 0; l < warriors; ++l) {
			GOTEntityNPC warrior = random.nextInt(3) == 0 ? new GOTEntitySummerSoldierArcher(world) : new GOTEntitySummerSoldier(world);
			warrior.setSpawnRidingHorse(false);
			spawnNPCAndSetHome(warrior, world, 0, 1, 0, 16);
		}
		for (int i13 : new int[]{-4, 4}) {
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
		respawner.setSpawnClass1(GOTEntitySummerSoldier.class);
		respawner.setSpawnClass2(GOTEntitySummerSoldierArcher.class);
		respawner.setCheckRanges(16, -8, 12, 12);
		respawner.setSpawnRanges(8, -2, 2, 16);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}
}