package got.common.world.structure.essos.mossovy;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.essos.mossovy.GOTEntityMossovyMan;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureMossovyStable extends GOTStructureMossovyBase {
	public GOTStructureMossovyStable(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int k1;
		int j2;
		int i1;
		int j12;
		int i12;
		int k12;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -9; i1 <= 9; ++i1) {
				for (k12 = -5; k12 <= 9; ++k12) {
					j1 = getTopBlock(world, i1, k12) - 1;
					if (isSurface(world, i1, j1, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -8; i1 <= 8; ++i1) {
			for (k12 = -5; k12 <= 5; ++k12) {
				for (j1 = 1; j1 <= 8; ++j1) {
					setAir(world, i1, j1, k12);
				}
			}
		}
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k12 = 6; k12 <= 8; ++k12) {
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k12);
				}
			}
		}
		for (i1 = -2; i1 <= 2; ++i1) {
			if (i1 == 0) {
				continue;
			}
			k12 = -5;
			for (j1 = 1; j1 <= 3; ++j1) {
				setAir(world, i1, j1, k12);
			}
		}
		for (int i13 = -2; i13 <= 2; ++i13) {
			if (i13 == 0) {
				continue;
			}
			for (int step = 0; step < 12 && !isOpaque(world, i13, j12 = -step, k1 = -5 - step); ++step) {
				setBlockAndMetadata(world, i13, j12, k1, Blocks.grass, 0);
				setGrassToDirt(world, i13, j12 - 1, k1);
				j2 = j12 - 1;
				while (!isOpaque(world, i13, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i13, j2, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i13, j2 - 1, k1);
					--j2;
				}
			}
		}
		for (int j13 = 1; j13 <= 2; ++j13) {
			setAir(world, 5, j13, 6);
		}
		for (int step = 0; step < 12 && !isOpaque(world, i12 = 5 + step, j12 = -step, k1 = 6); ++step) {
			setBlockAndMetadata(world, i12, j12, k1, Blocks.stone_stairs, 0);
			setGrassToDirt(world, i12, j12 - 1, k1);
			j2 = j12 - 1;
			while (!isOpaque(world, i12, j2, k1) && getY(j2) >= 0) {
				setBlockAndMetadata(world, i12, j2, k1, Blocks.cobblestone, 0);
				setGrassToDirt(world, i12, j2 - 1, k1);
				--j2;
			}
		}
		loadStrScan("mossovy_stable");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("COBBLE", Blocks.cobblestone, 0);
		associateBlockMetaAlias("COBBLE_WALL", Blocks.cobblestone_wall, 0);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM|4", beamBlock, beamMeta | 4);
		associateBlockMetaAlias("BEAM|8", beamBlock, beamMeta | 8);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		addBlockMetaAliasOption("GROUND", 1, Blocks.gravel, 0);
		addBlockMetaAliasOption("GROUND", 1, Blocks.grass, 0);
		addBlockMetaAliasOption("GROUND", 1, Blocks.dirt, 1);
		addBlockMetaAliasOption("GROUND", 1, GOTBlocks.dirtPath, 0);
		addBlockMetaAliasOption("THATCH_FLOOR", 1, GOTBlocks.thatchFloor, 0);
		setBlockAliasChance("THATCH_FLOOR", 0.15f);
		associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
		generateStrScan(world, random, 0, 0, 0);
		setBlockAndMetadata(world, -3, 1, 6, bedBlock, 2);
		setBlockAndMetadata(world, -3, 1, 5, bedBlock, 10);
		placeRandomFlowerPot(world, random, 3, 2, 5);
		placePlateWithCertainty(world, random, 1, 2, 7, GOTBlocks.ceramicPlate, GOTFoods.WESTEROS);
		placeMug(world, random, 0, 2, 7, 3, GOTFoods.WESTEROS_DRINK);
		placeBarrel(world, random, -1, 2, 7, 2, GOTFoods.WESTEROS_DRINK);
		placeChest(world, random, -3, 1, 7, 4, GOTChestContents.MOSSOVY);
		placeWeaponRack(world, 0, 2, 3, 6, getRandomBreeWeapon(random));
		GOTEntityMossovyMan stabler = new GOTEntityMossovyMan(world);
		spawnNPCAndSetHome(stabler, world, 0, 1, -1, 16);
		spawnHorse(world, random, -6, 1, -2);
		spawnHorse(world, random, 6, 1, -2);
		spawnHorse(world, random, -6, 1, 2);
		spawnHorse(world, random, 6, 1, 2);
		return true;
	}

	public void spawnHorse(World world, Random random, int i, int j, int k) {
		int horses = 1 + random.nextInt(2);
		for (int l = 0; l < horses; ++l) {
			GOTEntityHorse horse = new GOTEntityHorse(world);
			spawnNPCAndSetHome(horse, world, i, j, k, 0);
			horse.setHorseType(0);
			horse.saddleMountForWorldGen();
			horse.detachHome();
		}
	}
}
