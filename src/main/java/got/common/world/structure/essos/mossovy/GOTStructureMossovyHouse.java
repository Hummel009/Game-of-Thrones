package got.common.world.structure.essos.mossovy;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.entity.essos.mossovy.GOTEntityMossovyMan;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureMossovyHouse extends GOTStructureMossovyBase {
	public GOTStructureMossovyHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 9);
		setupRandomBlocks(random);
		int k12;
		int i1;
		int j1;
		if (restrictions) {
			for (i1 = -7; i1 <= 8; ++i1) {
				for (k12 = -8; k12 <= 5; ++k12) {
					j1 = getTopBlock(world, i1, k12) - 1;
					if (isSurface(world, i1, j1, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -3; i1 <= 8; ++i1) {
			for (k12 = -5; k12 <= 3; ++k12) {
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k12);
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			for (k12 = -8; k12 <= -6; ++k12) {
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k12);
				}
			}
		}
		for (i1 = 2; i1 <= 7; ++i1) {
			for (k12 = 3; k12 <= 5; ++k12) {
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k12);
				}
			}
		}
		for (i1 = -7; i1 <= -3; ++i1) {
			for (k12 = -4; k12 <= 2; ++k12) {
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k12);
				}
			}
		}
		loadStrScan("mossovy_house");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("FLOOR", floorBlock, floorMeta);
		associateBlockMetaAlias("STONE_WALL", stoneWallBlock, stoneWallMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockAlias("TRAPDOOR", trapdoorBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM|4", beamBlock, beamMeta | 4);
		associateBlockMetaAlias("BEAM|8", beamBlock, beamMeta | 8);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		associateBlockMetaAlias("TABLE", tableBlock, 0);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		addBlockMetaAliasOption("PATH", 5, Blocks.grass, 0);
		addBlockMetaAliasOption("PATH", 5, Blocks.dirt, 1);
		addBlockMetaAliasOption("PATH", 5, GOTBlocks.dirtPath, 0);
		addBlockMetaAliasOption("PATH", 5, Blocks.cobblestone, 0);
		associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
		generateStrScan(world, random, 0, 0, 0);
		int randPath;
		int i13;
		int j12;
		int j2;
		int k1;
		for (i13 = 3; i13 <= 6; ++i13) {
			for (int step = 0; step < 12 && !isOpaque(world, i13, j12 = -1 - step, k1 = 6 + step); ++step) {
				randPath = random.nextInt(4);
				switch (randPath) {
					case 0:
						setBlockAndMetadata(world, i13, j12, k1, Blocks.grass, 0);
						break;
					case 1:
						setBlockAndMetadata(world, i13, j12, k1, Blocks.dirt, 1);
						break;
					case 2:
						setBlockAndMetadata(world, i13, j12, k1, GOTBlocks.dirtPath, 0);
						break;
					case 3:
						setBlockAndMetadata(world, i13, j12, k1, Blocks.cobblestone, 0);
						break;
					default:
						break;
				}
				setGrassToDirt(world, i13, j12 - 1, k1);
				j2 = j12 - 1;
				while (!isOpaque(world, i13, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i13, j2, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i13, j2 - 1, k1);
					--j2;
				}
			}
		}
		int i12;
		for (int step = 0; step < 12 && !isOpaque(world, i12 = -5, j12 = -step, k1 = -5 - step); ++step) {
			randPath = random.nextInt(4);
			switch (randPath) {
				case 0:
					setBlockAndMetadata(world, i12, j12, k1, Blocks.grass, 0);
					break;
				case 1:
					setBlockAndMetadata(world, i12, j12, k1, Blocks.dirt, 1);
					break;
				case 2:
					setBlockAndMetadata(world, i12, j12, k1, GOTBlocks.dirtPath, 0);
					break;
				case 3:
					setBlockAndMetadata(world, i12, j12, k1, Blocks.cobblestone, 0);
					break;
				default:
					break;
			}
			setGrassToDirt(world, i12, j12 - 1, k1);
			j2 = j12 - 1;
			while (!isOpaque(world, i12, j2, k1) && getY(j2) >= 0) {
				setBlockAndMetadata(world, i12, j2, k1, Blocks.dirt, 0);
				setGrassToDirt(world, i12, j2 - 1, k1);
				--j2;
			}
		}
		for (i13 = -6; i13 <= -3; ++i13) {
			for (int k13 = -3; k13 <= 1; ++k13) {
				j12 = 1;
				if (getBlock(world, i13, 0, k13) != Blocks.grass || random.nextInt(4) != 0) {
					continue;
				}
				plantFlower(world, random, i13, j12, k13);
			}
		}
		placeRandomFlowerPot(world, random, 6, 1, 3);
		placeRandomFlowerPot(world, random, 3, 1, 3);
		placeRandomFlowerPot(world, random, -1, 5, -1);
		placeRandomFlowerPot(world, random, 2, 5, 1);
		plantFlower(world, random, 0, 2, 3);
		plantFlower(world, random, 8, 6, -1);
		placeChest(world, random, -1, 1, 1, 4, GOTChestContents.MOSSOVY);
		placeChest(world, random, 1, 5, 1, 2, GOTChestContents.MOSSOVY);
		placeMug(world, random, 3, 2, -2, 3, GOTFoods.WESTEROS_DRINK);
		placePlateWithCertainty(world, random, 3, 2, -3, GOTBlocks.plate, GOTFoods.WESTEROS);
		setBlockAndMetadata(world, 0, 5, 0, bedBlock, 3);
		setBlockAndMetadata(world, -1, 5, 0, bedBlock, 11);
		if (random.nextBoolean()) {
			spawnItemFrame(world, 2, 3, 0, 3, new ItemStack(Items.clock));
		}
		GOTEntityMossovyMan male = new GOTEntityMossovyMan(world);
		male.familyInfo.setMale(true);
		male.setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldRing));
		spawnNPCAndSetHome(male, world, 0, 1, 0, 16);
		GOTEntityMossovyMan female = new GOTEntityMossovyMan(world);
		female.familyInfo.setMale(false);
		female.setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldRing));
		spawnNPCAndSetHome(female, world, 0, 1, 0, 16);
		GOTEntityMossovyMan child = new GOTEntityMossovyMan(world);
		child.familyInfo.setMale(random.nextBoolean());
		child.familyInfo.setChild();
		spawnNPCAndSetHome(child, world, 0, 1, 0, 16);
		return true;
	}
}
