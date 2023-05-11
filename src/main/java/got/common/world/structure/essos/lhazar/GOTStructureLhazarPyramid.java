package got.common.world.structure.essos.lhazar;

import got.common.database.GOTChestContents;
import got.common.database.GOTRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureLhazarPyramid extends GOTStructureLhazarBase {
	public GOTStructureLhazarPyramid(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		int i1;
		int j12;
		int j2;
		int step;
		int k1;
		setOriginAndRotation(world, i, j, k, rotation, 11);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i1 = -11; i1 <= 11; ++i1) {
				for (k1 = -11; k1 <= 11; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i1 = -10; i1 <= 10; ++i1) {
			for (k1 = -10; k1 <= 10; ++k1) {
				for (j1 = 1; j1 <= 20; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("lhazar_pyramid");
		associateBlockMetaAlias("STONE", Blocks.sandstone, 0);
		associateBlockAlias("STONE_STAIR", Blocks.sandstone_stairs);
		associateBlockMetaAlias("STONE2", GOTRegistry.redSandstone, 0);
		associateBlockAlias("STONE2_STAIR", GOTRegistry.stairsRedSandstone);
		addBlockMetaAliasOption("BRICK", 8, GOTRegistry.brick1, 15);
		addBlockMetaAliasOption("BRICK", 2, GOTRegistry.brick3, 11);
		addBlockAliasOption("BRICK_STAIR", 8, GOTRegistry.stairsSandstoneBrick);
		addBlockAliasOption("BRICK_STAIR", 2, GOTRegistry.stairsSandstoneBrickCracked);
		addBlockMetaAliasOption("BRICK_WALL", 8, GOTRegistry.wallStone1, 15);
		addBlockMetaAliasOption("BRICK_WALL", 2, GOTRegistry.wallStone3, 3);
		addBlockMetaAliasOption("PILLAR", 10, GOTRegistry.pillar1, 5);
		addBlockMetaAliasOption("BRICK2", 8, GOTRegistry.brick3, 13);
		addBlockMetaAliasOption("BRICK2", 2, GOTRegistry.brick3, 14);
		associateBlockMetaAlias("BRICK2_CARVED", GOTRegistry.brick3, 15);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("ROOF_SLAB", roofSlabBlock, roofSlabMeta);
		associateBlockMetaAlias("ROOF_SLAB_INV", roofSlabBlock, roofSlabMeta | 8);
		associateBlockAlias("ROOF_STAIR", roofStairBlock);
		generateStrScan(world, random, 0, 1, 0);
		for (i1 = -5; i1 <= 5; ++i1) {
			for (k1 = -5; k1 <= 5; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				int j13 = 11;
				if (i2 <= 2 && k2 <= 2 || !isOpaque(world, i1, j13 - 1, k1) || !isAir(world, i1, j13, k1) || random.nextInt(12) != 0) {
					continue;
				}
				placeChest(world, random, i1, j13, k1, GOTRegistry.chestBasket, MathHelper.getRandomIntegerInRange(random, 2, 5), GOTChestContents.TREASURE);
			}
		}
		int maxStep = 4;
		for (int k12 : new int[]{-11, 11}) {
			int i12;
			for (step = 0; step < maxStep && !isOpaque(world, i12 = -7 - step, j12 = 0 - step, k12); ++step) {
				setBlockAndMetadata(world, i12, j12, k12, Blocks.sandstone_stairs, 1);
				setGrassToDirt(world, i12, j12 - 1, k12);
				j2 = j12 - 1;
				while (!isOpaque(world, i12, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k12, Blocks.sandstone, 0);
					setGrassToDirt(world, i12, j2 - 1, k12);
					--j2;
				}
			}
			for (step = 0; step < maxStep && !isOpaque(world, i12 = 7 + step, j12 = 0 - step, k12); ++step) {
				setBlockAndMetadata(world, i12, j12, k12, Blocks.sandstone_stairs, 0);
				setGrassToDirt(world, i12, j12 - 1, k12);
				j2 = j12 - 1;
				while (!isOpaque(world, i12, j2, k12) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i12, j2, k12, Blocks.sandstone, 0);
					setGrassToDirt(world, i12, j2 - 1, k12);
					--j2;
				}
			}
		}
		for (int i13 : new int[]{-11, 11}) {
			int k13;
			for (step = 0; step < maxStep && !isOpaque(world, i13, j12 = 0 - step, k13 = -7 - step); ++step) {
				setBlockAndMetadata(world, i13, j12, k13, Blocks.sandstone_stairs, 2);
				setGrassToDirt(world, i13, j12 - 1, k13);
				j2 = j12 - 1;
				while (!isOpaque(world, i13, j2, k13) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i13, j2, k13, Blocks.sandstone, 0);
					setGrassToDirt(world, i13, j2 - 1, k13);
					--j2;
				}
			}
			for (step = 0; step < maxStep && !isOpaque(world, i13, j12 = 0 - step, k13 = 7 + step); ++step) {
				setBlockAndMetadata(world, i13, j12, k13, Blocks.sandstone_stairs, 3);
				setGrassToDirt(world, i13, j12 - 1, k13);
				j2 = j12 - 1;
				while (!isOpaque(world, i13, j2, k13) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i13, j2, k13, Blocks.sandstone, 0);
					setGrassToDirt(world, i13, j2 - 1, k13);
					--j2;
				}
			}
		}
		return true;
	}
}
