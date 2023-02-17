package got.common.world.structure.essos.ghiscar;

import java.util.Random;

import got.common.database.*;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarGladiator;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.structure.essos.common.GOTStructureEssosBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTStructureGhiscarFightingPit extends GOTStructureEssosBase {
	public GOTStructureGhiscarFightingPit(boolean flag) {
		super(flag);
		city = City.GHISCAR;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int j2;
		int step;
		int k12;
		int j12;
		int i1;
		int i12;
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 8, -10);
		originY -= 4;
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i1 = -13; i1 <= 12; ++i1) {
				for (k1 = -12; k1 <= 14; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
						return false;
					}
					if (j1 < minHeight) {
						minHeight = j1;
					}
					if (j1 <= maxHeight) {
						continue;
					}
					maxHeight = j1;
				}
			}
			if (maxHeight - minHeight > 12) {
				return false;
			}
		}
		int radius = 8;
		for (int i13 = -radius; i13 <= radius; ++i13) {
			for (int k13 = -radius; k13 <= radius; ++k13) {
				if (i13 * i13 + k13 * k13 >= radius * radius) {
					continue;
				}
				for (int j13 = 0; j13 <= 12; ++j13) {
					setAir(world, i13, j13, k13);
				}
			}
		}
		int r2 = 12;
		for (i1 = -r2; i1 <= r2; ++i1) {
			for (k1 = -r2; k1 <= r2; ++k1) {
				if (i1 * i1 + k1 * k1 >= r2 * r2 || k1 < -4 || i1 > 4) {
					continue;
				}
				for (j1 = 0; j1 <= 12; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (i1 = -12; i1 <= -8; ++i1) {
			for (k1 = -7; k1 <= -4; ++k1) {
				if (k1 == -7 && (i1 == -12 || i1 == -8)) {
					continue;
				}
				for (j1 = 5; j1 <= 12; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (i1 = -3; i1 <= 3; ++i1) {
			for (k1 = 8; k1 <= 12; ++k1) {
				for (j1 = 7; j1 <= 11; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (i1 = -1; i1 <= 1; ++i1) {
			for (k1 = -11; k1 <= -6; ++k1) {
				for (j1 = 0; j1 <= 3; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		for (i1 = 6; i1 <= 11; ++i1) {
			for (k1 = -1; k1 <= 1; ++k1) {
				for (j1 = 0; j1 <= 3; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("ghiscar_pit");
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB_INV", brickSlabBlock, brickSlabMeta | 8);
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("PILLAR", pillarBlock, pillarMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockAlias("PLANK_STAIR", plankStairBlock);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("BEAM", woodBeamBlock, woodBeamMeta);
		associateBlockMetaAlias("BEAM|4", woodBeamBlock, woodBeamMeta | 4);
		associateBlockMetaAlias("BEAM|8", woodBeamBlock, woodBeamMeta | 8);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("WOOL", roofBlock, roofMeta);
		addBlockMetaAliasOption("GROUND", 1, Blocks.dirt, 1);
		addBlockMetaAliasOption("GROUND", 2, GOTRegistry.pillar1, 5);
		addBlockMetaAliasOption("GROUND", 3, GOTRegistry.dirtPath, 0);
		addBlockMetaAliasOption("GROUND", 5, Blocks.sand, 0);
		addBlockMetaAliasOption("GROUND", 5, Blocks.sandstone, 0);
		addBlockMetaAliasOption("GROUND", 7, GOTRegistry.brick1, 15);
		addBlockMetaAliasOption("GROUND", 8, GOTRegistry.brick3, 11);
		addBlockMetaAliasOption("GROUND_SLAB", 1, GOTRegistry.slabSingleDirt, 0);
		addBlockMetaAliasOption("GROUND_SLAB", 2, GOTRegistry.slabSingle4, 7);
		addBlockMetaAliasOption("GROUND_SLAB", 3, GOTRegistry.slabSingleDirt, 1);
		addBlockMetaAliasOption("GROUND_SLAB", 5, GOTRegistry.slabSingleSand, 0);
		addBlockMetaAliasOption("GROUND_SLAB", 7, GOTRegistry.slabSingle4, 0);
		addBlockMetaAliasOption("GROUND_SLAB", 8, GOTRegistry.slabSingle7, 1);
		addBlockMetaAliasOption("GROUND_COVER", 1, GOTRegistry.thatchFloor, 0);
		setBlockAliasChance("GROUND_COVER", 0.25f);
		associateBlockMetaAlias("BARS", barsBlock, 0);
		associateBlockAlias("GATE_ORC", gateMetalBlock);
		associateBlockAlias("GATE_METAL", gateMetalBlock);
		associateBlockMetaAlias("TABLE", tableBlock, 0);
		generateStrScan(world, random, 0, 0, 0);
		placeWallBanner(world, -7, 5, 0, bannerType, 1);
		placeWallBanner(world, 7, 5, 0, bannerType, 3);
		placeWallBanner(world, 0, 5, -7, bannerType, 0);
		placeWallBanner(world, 0, 5, 7, bannerType, 2);
		placeBigTorch(world, 2, 4, -5);
		placeBigTorch(world, -2, 4, -5);
		placeBigTorch(world, 5, 4, -2);
		placeBigTorch(world, -5, 4, -2);
		placeBigTorch(world, 5, 4, 2);
		placeBigTorch(world, -5, 4, 2);
		placeBigTorch(world, 2, 4, 5);
		placeBigTorch(world, -2, 4, 5);
		placeBigTorch(world, 1, 7, 8);
		placeBigTorch(world, -1, 7, 8);
		placeBigTorch(world, 4, 8, -4);
		placeBigTorch(world, -4, 8, -4);
		placeBigTorch(world, 4, 8, 4);
		placeBigTorch(world, -4, 8, 4);
		placeBigTorch(world, -8, 10, -4);
		placeBigTorch(world, -12, 10, -4);
		this.placeChest(world, random, -7, 1, 0, 4, GOTChestContents.GHISCAR);
		this.placeChest(world, random, 1, 7, 12, 2, GOTChestContents.GHISCAR);
		setBlockAndMetadata(world, -2, 7, 9, bedBlock, 3);
		setBlockAndMetadata(world, -3, 7, 9, bedBlock, 11);
		setBlockAndMetadata(world, -2, 7, 11, bedBlock, 3);
		setBlockAndMetadata(world, -3, 7, 11, bedBlock, 11);
		this.placeBarrel(world, random, 3, 8, 11, 5, GOTFoods.ESSOS_DRINK);
		this.placeMug(world, random, 3, 8, 10, 1, GOTFoods.ESSOS_DRINK);
		placePlateWithCertainty(world, random, 3, 8, 9, GOTRegistry.woodPlateBlock, GOTFoods.ESSOS);
		int maxStep = 12;
		for (i12 = -1; i12 <= 1; ++i12) {
			for (step = 0; step < 2 && !isSideSolid(world, i12, j12 = 5 - step, k12 = -9 - step, ForgeDirection.UP); ++step) {
				setBlockAndMetadata(world, i12, j12, k12, brickStairBlock, 2);
				setGrassToDirt(world, i12, j12 - 1, k12);
				j2 = j12 - 1;
				while (!isSideSolid(world, i12, j2, k12, ForgeDirection.UP) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i12, j2 - 1, k12);
					--j2;
				}
			}
		}
		for (i12 = -1; i12 <= 1; ++i12) {
			for (step = 0; step < maxStep && !isOpaque(world, i12, j12 = 3 - step, k12 = -13 - step); ++step) {
				setBlockAndMetadata(world, i12, j12, k12, brickStairBlock, 2);
				setGrassToDirt(world, i12, j12 - 1, k12);
				j2 = j12 - 1;
				while (!isOpaque(world, i12, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k12, brickBlock, brickMeta);
					setGrassToDirt(world, i12, j2 - 1, k12);
					--j2;
				}
			}
		}
		GOTEntityNPCRespawner gladiatorSpawner = new GOTEntityNPCRespawner(world);
		gladiatorSpawner.setSpawnClass(GOTEntityGhiscarGladiator.class);
		gladiatorSpawner.setCheckRanges(12, -8, 16, 10);
		gladiatorSpawner.setSpawnRanges(4, -4, 4, 24);
		gladiatorSpawner.setSpawnInterval(1);
		gladiatorSpawner.setNoPlayerRange(1);
		placeNPCRespawner(gladiatorSpawner, world, 0, 0, 0);
		return true;
	}
}
