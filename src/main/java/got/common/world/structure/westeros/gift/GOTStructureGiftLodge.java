package got.common.world.structure.westeros.gift;

import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.database.GOTRegistry;
import got.common.entity.westeros.gift.GOTEntityGiftMan;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureGiftLodge extends GOTStructureGiftBase {
	public GOTStructureGiftLodge(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k13;
		int k12;
		int j1;
		int j12;
		int i2;
		int j13;
		this.setOriginAndRotation(world, i, j, k, rotation, 5);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -5; i1 <= 6; ++i1) {
				for (k13 = -4; k13 <= 4; ++k13) {
					j1 = getTopBlock(world, i1, k13) - 1;
					if (!isSurface(world, i1, j1, k13)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 > maxHeight) {
						maxHeight = j1;
					}
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		for (int i1 = -5; i1 <= 5; ++i1) {
			for (k12 = -4; k12 <= 4; ++k12) {
				i2 = Math.abs(i1);
				int k2 = Math.abs(k12);
				if (i2 > 4 && k2 > 3) {
					continue;
				}
				for (j1 = 0; (j1 >= -3 || !isOpaque(world, i1, j1, k12)) && getY(j1) >= 0; --j1) {
					setBlockAndMetadata(world, i1, j1, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j1 - 1, k12);
				}
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k12);
				}
				if (k2 == 4 || i2 == 5) {
					int j14;
					boolean beam = false;
					if (k12 == -4 && (i2 == 1 || i2 == 4)) {
						beam = true;
					}
					if (k12 == 4 && (i2 == 0 || i2 == 4)) {
						beam = true;
					}
					if (i2 == 5 && (k2 == 0 || k2 == 3)) {
						beam = true;
					}
					if (beam) {
						for (j14 = 1; j14 <= 3; ++j14) {
							setBlockAndMetadata(world, i1, j14, k12, woodBeamBlock, woodBeamMeta);
						}
					} else {
						for (j14 = 1; j14 <= 3; ++j14) {
							setBlockAndMetadata(world, i1, j14, k12, wallBlock, wallMeta);
						}
					}
				}
				if (k2 > 3 || i2 > 4) {
					continue;
				}
				setBlockAndMetadata(world, i1, 0, k12, plankSlabBlock, plankSlabMeta | 8);
				if (random.nextInt(3) == 0) {
					setBlockAndMetadata(world, i1, 1, k12, GOTRegistry.thatchFloor, 0);
				}
				for (j1 = -2; j1 <= -1; ++j1) {
					setAir(world, i1, j1, k12);
				}
			}
		}
		for (int k131 : new int[]{-4, 4}) {
			for (int i1 = -4; i1 <= 4; ++i1) {
				setBlockAndMetadata(world, i1, 4, k131, woodBeamBlock, woodBeamMeta | 4);
			}
		}
		for (int i1 : new int[]{-5, 5}) {
			for (int k14 = -3; k14 <= 3; ++k14) {
				int k2 = Math.abs(k14);
				if (k2 == 0) {
					for (int j15 = 4; j15 <= 6; ++j15) {
						setBlockAndMetadata(world, i1, j15, k14, woodBeamBlock, woodBeamMeta);
					}
					continue;
				}
				setBlockAndMetadata(world, i1, 4, k14, woodBeamBlock, woodBeamMeta | 8);
				if (k2 > 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 5, k14, wallBlock, wallMeta);
			}
		}
		for (int i1 = -5; i1 <= 5; ++i1) {
			setBlockAndMetadata(world, i1, 5, -3, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i1, 6, -1, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i1, 7, 0, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i1, 6, 1, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i1, 5, 3, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i1, 5, -4, roofStairBlock, 2);
			setBlockAndMetadata(world, i1, 6, -3, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i1, 6, -2, roofBlock, roofMeta);
			setBlockAndMetadata(world, i1, 7, -1, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i1, 7, 1, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i1, 6, 2, roofBlock, roofMeta);
			setBlockAndMetadata(world, i1, 6, 3, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, i1, 5, 4, roofStairBlock, 3);
		}
		for (int k15 = -4; k15 <= 4; ++k15) {
			setBlockAndMetadata(world, 0, 4, k15, woodBeamBlock, woodBeamMeta | 8);
		}
		setBlockAndMetadata(world, 0, 1, -4, doorBlock, 1);
		setBlockAndMetadata(world, 0, 2, -4, doorBlock, 8);
		setBlockAndMetadata(world, 0, 4, -5, Blocks.torch, 4);
		setBlockAndMetadata(world, -3, 2, -4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 3, 2, -4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 2, 4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 2, 4, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -5, 2, -1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -5, 2, 1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 0, 3, 3, Blocks.torch, 4);
		setBlockAndMetadata(world, -4, 4, 0, Blocks.torch, 2);
		for (int i1 : new int[]{-4, 4}) {
			for (int k16 : new int[]{-3, 3}) {
				setBlockAndMetadata(world, i1, 1, k16, plankBlock, plankMeta);
				for (j13 = 2; j13 <= 4; ++j13) {
					setBlockAndMetadata(world, i1, j13, k16, fenceBlock, fenceMeta);
				}
			}
		}
		setBlockAndMetadata(world, -2, 1, -3, plankBlock, plankMeta);
		placePlate(world, random, -2, 2, -3, plateBlock, GOTFoods.WESTEROS);
		setBlockAndMetadata(world, -3, 1, -3, plankBlock, plankMeta);
		placePlate(world, random, -3, 2, -3, plateBlock, GOTFoods.WESTEROS);
		setBlockAndMetadata(world, -4, 1, -2, plankBlock, plankMeta);
		this.placeMug(world, random, -4, 2, -2, 3, GOTFoods.WESTEROS_DRINK);
		this.placeChest(world, random, -4, 1, -1, 4, GOTChestContents.GIFT);
		setBlockAndMetadata(world, -4, 1, 0, Blocks.crafting_table, 0);
		this.placeChest(world, random, -4, 1, 1, 4, GOTChestContents.GIFT);
		setBlockAndMetadata(world, -4, 1, 2, plankBlock, plankMeta);
		this.placeBarrel(world, random, -4, 2, 2, 4, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, -3, 1, 3, plankBlock, plankMeta);
		this.placeBarrel(world, random, -3, 2, 3, 2, GOTFoods.WESTEROS_DRINK);
		setBlockAndMetadata(world, -2, 1, 3, plankBlock, plankMeta);
		this.placeMug(world, random, -2, 2, 3, 0, GOTFoods.WESTEROS_DRINK);
		int[] k15 = {-3, 3};
		k12 = k15.length;
		for (i2 = 0; i2 < k12; ++i2) {
			k13 = k15[i2];
			setBlockAndMetadata(world, 2, 1, k13, bedBlock, 1);
			setBlockAndMetadata(world, 3, 1, k13, bedBlock, 9);
		}
		setBlockAndMetadata(world, 4, 1, -2, plankBlock, plankMeta);
		for (int i1 = 4; i1 <= 6; ++i1) {
			for (k12 = -1; k12 <= 1; ++k12) {
				for (int j16 = 5; (j16 >= 0 || !isOpaque(world, i1, j16, k12)) && getY(j16) >= 0; --j16) {
					setBlockAndMetadata(world, i1, j16, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j16 - 1, k12);
				}
			}
		}
		setBlockAndMetadata(world, 4, 6, 0, brickBlock, brickMeta);
		setBlockAndMetadata(world, 6, 5, -1, brickStairBlock, 2);
		setBlockAndMetadata(world, 6, 5, 1, brickStairBlock, 3);
		setBlockAndMetadata(world, 6, 6, 0, brickStairBlock, 0);
		for (j12 = 6; j12 <= 8; ++j12) {
			setBlockAndMetadata(world, 5, j12, 0, brickBlock, brickMeta);
		}
		for (j12 = 9; j12 <= 10; ++j12) {
			setBlockAndMetadata(world, 5, j12, 0, brickWallBlock, brickWallMeta);
		}
		setBlockAndMetadata(world, 5, 0, 0, GOTRegistry.hearth, 0);
		setBlockAndMetadata(world, 5, 1, 0, Blocks.fire, 0);
		for (j12 = 2; j12 <= 3; ++j12) {
			setAir(world, 5, j12, 0);
		}
		setBlockAndMetadata(world, 4, 1, 0, barsBlock, 0);
		setBlockAndMetadata(world, 4, 2, 0, Blocks.furnace, 5);
		spawnItemFrame(world, 4, 4, 0, 3, getRangerFramedItem(random));
		setBlockAndMetadata(world, 4, 1, 2, Blocks.trapdoor, 3);
		for (j12 = -2; j12 <= 0; ++j12) {
			setBlockAndMetadata(world, 4, j12, 2, Blocks.ladder, 3);
		}
		for (int i1 : new int[]{-4, 4}) {
			for (int k16 : new int[]{-3, 3}) {
				setBlockAndMetadata(world, i1, 0, k16, plankBlock, plankMeta);
				for (j13 = -2; j13 <= -1; ++j13) {
					setBlockAndMetadata(world, i1, j13, k16, woodBeamBlock, woodBeamMeta);
				}
			}
		}
		setBlockAndMetadata(world, -3, -1, -3, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, -1, -3, Blocks.torch, 1);
		setBlockAndMetadata(world, -3, -1, 3, Blocks.torch, 2);
		setBlockAndMetadata(world, 3, -1, 3, Blocks.torch, 1);
		for (int i1 : new int[]{-2, 0, 2}) {
			setBlockAndMetadata(world, i1, -2, -2, bedBlock, 2);
			setBlockAndMetadata(world, i1, -2, -3, bedBlock, 10);
		}
		for (int k131 : new int[]{-2, 2}) {
			ItemStack[] armor = null;
			if (random.nextInt(3) == 0) {
				armor = new ItemStack[]{new ItemStack(GOTRegistry.giftHelmet), new ItemStack(GOTRegistry.giftChestplate), new ItemStack(GOTRegistry.giftLeggings), new ItemStack(GOTRegistry.giftBoots)};
			}
			placeArmorStand(world, -4, -2, k131, 3, armor);
		}
		for (int k131 : new int[]{-1, 1}) {
			spawnItemFrame(world, -5, -1, k131, 1, getRangerFramedItem(random));
		}
		setBlockAndMetadata(world, 0, -2, 3, tableBlock, 0);
		for (int i1 : new int[]{-1, 1}) {
			int amount = 2 + random.nextInt(5);
			this.placeChest(world, random, i1, -2, 3, 2, GOTChestContents.GIFT, amount);
		}
		GOTEntityGiftMan male = new GOTEntityGiftMan(world);
		male.familyInfo.setMale(true);
		male.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(male, world, 0, 1, 0, 16);
		GOTEntityGiftMan female = new GOTEntityGiftMan(world);
		female.familyInfo.setMale(false);
		female.setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.goldRing));
		spawnNPCAndSetHome(female, world, 0, 1, 0, 16);
		GOTEntityGiftMan child = new GOTEntityGiftMan(world);
		child.familyInfo.setMale(random.nextBoolean());
		child.familyInfo.setChild();
		spawnNPCAndSetHome(child, world, 0, 1, 0, 16);
		return true;
	}
}
