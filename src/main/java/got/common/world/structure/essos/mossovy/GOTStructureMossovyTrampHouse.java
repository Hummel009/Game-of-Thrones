package got.common.world.structure.essos.mossovy;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityLightSkinThief;
import got.common.entity.other.GOTEntityLightSkinTramp;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureMossovyTrampHouse extends GOTStructureMossovyBase {
	public GOTStructureMossovyTrampHouse(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int i1;
		int j2;
		int j12;
		int i12;
		int i13;
		int k12;
		int randPath;
		setOriginAndRotation(world, i, j, k, rotation, 9);
		setupRandomBlocks(random);
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
		for (i1 = 0; i1 <= 7; ++i1) {
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
		for (i1 = 0; i1 <= 5; ++i1) {
			for (k12 = -4; k12 <= 4; ++k12) {
				for (j1 = -2; j1 <= 0; ++j1) {
					setAir(world, i1, j1, k12);
				}
			}
		}
		for (i1 = -2; i1 <= -1; ++i1) {
			k12 = 0;
			for (j1 = -2; j1 <= 0; ++j1) {
				setAir(world, i1, j1, k12);
			}
		}
		loadStrScan("mossovy_ruffian_house");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		addBlockMetaAliasOption("COBBLE", Blocks.cobblestone, 0);
		addBlockMetaAliasOption("COBBLE", Blocks.mossy_cobblestone, 0);
		addBlockMetaAliasOption("COBBLE_SLAB_INV", Blocks.stone_slab, 11);
		addBlockMetaAliasOption("COBBLE_SLAB_INV", GOTBlocks.slabSingleV, 12);
		addBlockAliasOption("COBBLE_STAIR", Blocks.stone_stairs);
		addBlockAliasOption("COBBLE_STAIR", GOTBlocks.stairsCobblestoneMossy);
		addBlockMetaAliasOption("COBBLE_WALL", Blocks.cobblestone_wall, 0);
		addBlockMetaAliasOption("COBBLE_WALL", Blocks.cobblestone_wall, 1);
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
		addBlockMetaAliasOption("THATCH_FLOOR", GOTBlocks.thatchFloor, 0);
		setBlockAliasChance("THATCH_FLOOR", 0.2f);
		addBlockMetaAliasOption("LEAF_FLOOR", GOTBlocks.fallenLeaves1, 0);
		setBlockAliasChance("LEAF_FLOOR", 0.3f);
		addBlockMetaAliasOption("WEB", Blocks.web, 0);
		setBlockAliasChance("WEB", 0.3f);
		addBlockMetaAliasOption("PATH", Blocks.grass, 0);
		addBlockMetaAliasOption("PATH", Blocks.dirt, 1);
		addBlockMetaAliasOption("PATH", GOTBlocks.dirtPath, 0);
		addBlockMetaAliasOption("PATH", Blocks.cobblestone, 0);
		addBlockMetaAliasOption("PATH", Blocks.mossy_cobblestone, 0);
		associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
		generateStrScan(world, random, 0, 0, 0);
		for (i13 = 4; i13 <= 6; ++i13) {
			for (int step = 0; step < 12 && !isOpaque(world, i13, j12 = -1 - step, k1 = 5 + step); ++step) {
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
						if (random.nextBoolean()) {
							setBlockAndMetadata(world, i13, j12, k1, Blocks.cobblestone, 0);
						} else {
							setBlockAndMetadata(world, i13, j12, k1, Blocks.mossy_cobblestone, 0);
						}
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
					if (random.nextBoolean()) {
						setBlockAndMetadata(world, i12, j12, k1, Blocks.cobblestone, 0);
					} else {
						setBlockAndMetadata(world, i12, j12, k1, Blocks.mossy_cobblestone, 0);
					}
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
		setBlockAndMetadata(world, 4, -2, -1, bedBlock, 3);
		setBlockAndMetadata(world, 3, -2, -1, bedBlock, 11);
		setBlockAndMetadata(world, 5, -2, 1, bedBlock, 9);
		setBlockAndMetadata(world, 4, -2, 1, bedBlock, 1);
		setBlockAndMetadata(world, 0, 5, 0, bedBlock, 3);
		setBlockAndMetadata(world, -1, 5, 0, bedBlock, 11);
		placePlateWithCertainty(world, random, 1, -1, -4, GOTBlocks.ceramicPlate, GOTFoods.WESTEROS);
		placeMug(world, random, 0, -1, -4, 0, GOTFoods.WESTEROS_DRINK);
		placeBarrel(world, random, 5, -2, -4, 5, GOTFoods.WESTEROS_DRINK);
		placeBarrel(world, random, 4, -2, -3, 2, GOTFoods.WESTEROS_DRINK);
		placeChest(world, random, 3, -2, -3, 2, GOTChestContents.MOSSOVY);
		placeChest(world, random, -2, -2, 0, 4, GOTChestContents.MOSSOVY);
		placeChest(world, random, 3, -2, 1, 2, GOTChestContents.MOSSOVY);
		placePlateWithCertainty(world, random, 3, 2, -3, GOTBlocks.plate, GOTFoods.WESTEROS);
		placeMug(world, random, 3, 2, -2, 3, GOTFoods.WESTEROS_DRINK);
		placeChest(world, random, -1, 1, 1, 4, GOTChestContents.MOSSOVY);
		placeChest(world, random, 1, 5, 1, 2, GOTChestContents.MOSSOVY);
		for (i13 = -6; i13 <= -3; ++i13) {
			for (int k13 = -3; k13 <= 1; ++k13) {
				j12 = 1;
				Block gardenBlock = getBlock(world, i13, 0, k13);
				if (gardenBlock != Blocks.grass && gardenBlock != Blocks.dirt || random.nextInt(3) != 0) {
					continue;
				}
				if (random.nextInt(3) == 0) {
					setBlockAndMetadata(world, i13, j12, k13, Blocks.double_plant, 2);
					setBlockAndMetadata(world, i13, j12 + 1, k13, Blocks.double_plant, 10);
					continue;
				}
				plantTallGrass(world, random, i13, j12, k13);
			}
		}
		if (random.nextBoolean()) {
			spawnNPCAndSetHome(new GOTEntityLightSkinThief(world), world, 0, 1, 0, 16);
		} else {
			spawnNPCAndSetHome(new GOTEntityLightSkinTramp(world), world, 0, 1, 0, 16);
		}
		return true;
	}
}