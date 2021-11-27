package got.common.world.structure.essos.ghiscar;

import java.util.Random;

import got.common.database.*;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarGladiator;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.item.other.GOTItemBanner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTStructureGhiscarFightingPit extends GOTStructureBase {
	public Block brickBlock;
	public int brickMeta;
	public Block brickSlabBlock;
	public int brickSlabMeta;
	public Block brickStairBlock;
	public Block brickWallBlock;
	public int brickWallMeta;
	public Block pillarBlock;
	public int pillarMeta;
	public Block plankBlock;
	public int plankMeta;
	public Block plankSlabBlock;
	public int plankSlabMeta;
	public Block plankStairBlock;
	public Block fenceBlock;
	public int fenceMeta;
	public Block beamBlock;
	public int beamMeta;
	public Block doorBlock;
	public Block woolBlock;
	public int woolMeta;
	public Block carpetBlock;
	public int carpetMeta;
	public Block barsBlock;
	public Block gateStandartBlock;
	public Block gateMetalBlock;
	public Block tableBlock;
	public Block bedBlock;
	public GOTItemBanner.BannerType banner;
	public GOTChestContents chestContents;

	public GOTStructureGhiscarFightingPit(boolean flag) {
		super(flag);
	}

	public void associateGroundBlocks() {
		addBlockMetaAliasOption("GROUND", 4, Blocks.dirt, 1);
		addBlockMetaAliasOption("GROUND", 4, GOTRegistry.dirtPath, 0);
		addBlockMetaAliasOption("GROUND", 4, Blocks.gravel, 0);
		addBlockMetaAliasOption("GROUND", 4, Blocks.cobblestone, 0);
		addBlockMetaAliasOption("GROUND_SLAB", 4, GOTRegistry.slabSingleDirt, 0);
		addBlockMetaAliasOption("GROUND_SLAB", 4, GOTRegistry.slabSingleDirt, 1);
		addBlockMetaAliasOption("GROUND_SLAB", 4, GOTRegistry.slabSingleGravel, 0);
		addBlockMetaAliasOption("GROUND_SLAB", 4, Blocks.stone_slab, 3);
		addBlockMetaAliasOption("GROUND_COVER", 1, GOTRegistry.thatchFloor, 0);
		setBlockAliasChance("GROUND_COVER", 0.25f);
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
		associateBlockMetaAlias("BEAM", beamBlock, beamMeta);
		associateBlockMetaAlias("BEAM|4", beamBlock, beamMeta | 4);
		associateBlockMetaAlias("BEAM|8", beamBlock, beamMeta | 8);
		associateBlockAlias("DOOR", doorBlock);
		associateBlockMetaAlias("WOOL", woolBlock, woolMeta);
		associateBlockMetaAlias("CARPET", carpetBlock, carpetMeta);
		associateGroundBlocks();
		associateBlockMetaAlias("BARS", barsBlock, 0);
		associateBlockAlias("GATE_ORC", gateStandartBlock);
		associateBlockAlias("GATE_METAL", gateMetalBlock);
		associateBlockMetaAlias("TABLE", tableBlock, 0);
		generateStrScan(world, random, 0, 0, 0);
		placeWallBanner(world, -7, 5, 0, banner, 1);
		placeWallBanner(world, 7, 5, 0, banner, 3);
		placeWallBanner(world, 0, 5, -7, banner, 0);
		placeWallBanner(world, 0, 5, 7, banner, 2);
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
		this.placeChest(world, random, -7, 1, 0, 4, GOTChestContents.ESSOS);
		this.placeChest(world, random, 1, 7, 12, 2, GOTChestContents.ESSOS);
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

	@Override
	public void placeBigTorch(World world, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, GOTRegistry.fuse, 0);
		setBlockAndMetadata(world, i, j + 1, k, GOTRegistry.fuse, 1);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		brickBlock = GOTRegistry.brick1;
		brickMeta = 15;
		brickSlabBlock = GOTRegistry.slabSingle4;
		brickSlabMeta = 0;
		brickStairBlock = GOTRegistry.stairsSandstoneBrick;
		brickWallBlock = GOTRegistry.wallStone1;
		brickWallMeta = 15;
		pillarBlock = GOTRegistry.pillar1;
		pillarMeta = 5;
		plankBlock = GOTRegistry.planks1;
		plankMeta = 3;
		plankSlabBlock = GOTRegistry.woodSlabSingle1;
		plankSlabMeta = 3;
		plankStairBlock = GOTRegistry.stairsCharred;
		fenceBlock = GOTRegistry.fence;
		fenceMeta = 3;
		beamBlock = GOTRegistry.woodBeam1;
		beamMeta = 3;
		doorBlock = GOTRegistry.doorDarkOak;
		gateMetalBlock = GOTRegistry.gateBronzeBars;
		bedBlock = GOTRegistry.strawBed;
		woolBlock = Blocks.wool;
		woolMeta = 12;
		carpetBlock = Blocks.carpet;
		carpetMeta = 12;
		barsBlock = GOTRegistry.bronzeBars;
		gateStandartBlock = GOTRegistry.gateBronzeBars;
		tableBlock = GOTRegistry.tableGhiscar;
		banner = GOTItemBanner.BannerType.GHISCAR;
	}
}
