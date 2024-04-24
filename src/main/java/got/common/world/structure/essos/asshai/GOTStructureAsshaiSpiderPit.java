package got.common.world.structure.essos.asshai;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityUlthosSpider;
import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class GOTStructureAsshaiSpiderPit extends GOTStructureAsshaiBase {
	public GOTStructureAsshaiSpiderPit(boolean flag) {
		super(flag);
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
		setOriginAndRotation(world, i, j, k, rotation, 8, -10);
		originY -= 4;
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i12 = -13; i12 <= 12; ++i12) {
				for (k12 = -12; k12 <= 14; ++k12) {
					j12 = getTopBlock(world, i12, k12) - 1;
					if (!isSurface(world, i12, j12, k12)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 <= maxHeight) {
						continue;
					}
					maxHeight = j12;
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
		associateBlockMetaAlias("WOOL", plankBlock, plankMeta);
		associateBlockAlias("GROUND", rockBlock);
		associateBlockAlias("GROUND_SLAB", brickSlabBlock);
		addBlockMetaAliasOption("GROUND_COVER", GOTBlocks.thatchFloor, 0);
		setBlockAliasChance("GROUND_COVER", 0.25f);
		associateBlockMetaAlias("BARS", barsBlock, 0);
		associateBlockAlias("GATE_ORC", gateBlock);
		associateBlockAlias("GATE_METAL", gateBlock);
		associateBlockMetaAlias("TABLE", GOTBlocks.tableAsshai, 0);
		generateStrScan(world, random, 0, 0, 0);
		placeWallBanner(world, -7, 5, 0, GOTItemBanner.BannerType.ASSHAI, 1);
		placeWallBanner(world, 7, 5, 0, GOTItemBanner.BannerType.ASSHAI, 3);
		placeWallBanner(world, 0, 5, -7, GOTItemBanner.BannerType.ASSHAI, 0);
		placeWallBanner(world, 0, 5, 7, GOTItemBanner.BannerType.ASSHAI, 2);
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
		placeChest(world, random, 0, -7, 1, GOTBlocks.chestStone, 4, GOTChestContents.ASSHAI);
		placeChest(world, random, 1, 7, 12, GOTBlocks.chestStone, 2, GOTChestContents.ASSHAI);
		setBlockAndMetadata(world, -2, 7, 9, bedBlock, 3);
		setBlockAndMetadata(world, -3, 7, 9, bedBlock, 11);
		setBlockAndMetadata(world, -2, 7, 11, bedBlock, 3);
		setBlockAndMetadata(world, -3, 7, 11, bedBlock, 11);
		placeBarrel(world, random, 3, 8, 11, 5, GOTFoods.RICH_DRINK);
		placeMug(world, random, 3, 8, 10, 1, GOTFoods.RICH_DRINK);
		placePlateWithCertainty(world, random, 3, 8, 9, GOTBlocks.woodPlate, GOTFoods.WESTEROS);
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
		GOTEntityNPCRespawner spiderSpawner = new GOTEntityNPCRespawner(world);
		spiderSpawner.setSpawnClass1(GOTEntityUlthosSpider.class);
		spiderSpawner.setCheckRanges(12, -8, 16, 10);
		spiderSpawner.setSpawnRanges(4, -4, 4, 24);
		spiderSpawner.setSpawnInterval(1);
		spiderSpawner.setNoPlayerRange(1);
		placeNPCRespawner(spiderSpawner, world, 0, 0, 0);
		return true;
	}
}