package got.common.world.structure.sothoryos.summer;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.entity.sothoryos.summer.GOTEntitySummerFarmer;
import got.common.entity.sothoryos.summer.GOTEntitySummerFarmhand;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSummerFarm extends GOTStructureSummerBase {
	public Block crop1Block;
	public Item seed1;
	public Block crop2Block;
	public Item seed2;

	public GOTStructureSummerFarm(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 5);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -4; i1 <= 4; ++i1) {
				for (int k1 = -4; k1 <= 4; ++k1) {
					j1 = getTopBlock(world, i1, k1) - 1;
					if (!isSurface(world, i1, j1, k1)) {
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
		for (int i1 = -4; i1 <= 4; ++i1) {
			for (int k1 = -4; k1 <= 4; ++k1) {
				int j12 = -1;
				while (!isOpaque(world, i1, j12, k1) && getY(j12) >= 0) {
					setBlockAndMetadata(world, i1, j12, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i1, j12 - 1, k1);
					--j12;
				}
				for (j12 = 1; j12 <= 4; ++j12) {
					setAir(world, i1, j12, k1);
				}
			}
		}
		loadStrScan("summer_farm");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("PLANK", plankBlock, plankMeta);
		associateBlockMetaAlias("PLANK_SLAB", plankSlabBlock, plankSlabMeta);
		associateBlockMetaAlias("PLANK_SLAB_INV", plankSlabBlock, plankSlabMeta | 8);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("ROOF", roofBlock, roofMeta);
		associateBlockAlias("CROP1", crop1Block);
		associateBlockAlias("CROP2", crop2Block);
		generateStrScan(world, random, 0, 0, 0);
		placeSkull(world, random, 0, 4, 0);
		block6:
		for (int i1 : new int[]{-2, 2}) {
			j1 = 0;
			for (int step = 0; step < 6; ++step) {
				int j2;
				int k1 = -5 - step;
				if (isOpaque(world, i1, j1 + 1, k1)) {
					setAir(world, i1, j1 + 1, k1);
					setAir(world, i1, j1 + 2, k1);
					setAir(world, i1, j1 + 3, k1);
					setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
					setGrassToDirt(world, i1, j1 - 1, k1);
					j2 = j1 - 1;
					while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
						setBlockAndMetadata(world, i1, j2, k1, Blocks.dirt, 0);
						setGrassToDirt(world, i1, j2 - 1, k1);
						--j2;
					}
					++j1;
					continue;
				}
				if (isOpaque(world, i1, j1, k1)) {
					continue block6;
				}
				setAir(world, i1, j1 + 1, k1);
				setAir(world, i1, j1 + 2, k1);
				setAir(world, i1, j1 + 3, k1);
				setBlockAndMetadata(world, i1, j1, k1, Blocks.grass, 0);
				setGrassToDirt(world, i1, j1 - 1, k1);
				j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, Blocks.dirt, 0);
					setGrassToDirt(world, i1, j2 - 1, k1);
					--j2;
				}
				--j1;
			}
		}
		if (random.nextInt(4) == 0) {
			GOTEntitySummerFarmer farmer = new GOTEntitySummerFarmer(world);
			spawnNPCAndSetHome(farmer, world, 0, 1, 1, 8);
		}
		GOTEntitySummerFarmhand farmhand1 = new GOTEntitySummerFarmhand(world);
		farmhand1.seedsItem = seed1;
		spawnNPCAndSetHome(farmhand1, world, -2, 1, 0, 8);
		GOTEntitySummerFarmhand farmhand2 = new GOTEntitySummerFarmhand(world);
		farmhand2.seedsItem = seed2;
		spawnNPCAndSetHome(farmhand2, world, 2, 1, 0, 8);
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		int randomCrop;
		super.setupRandomBlocks(random);
		if (random.nextBoolean()) {
			crop1Block = Blocks.wheat;
			seed1 = Items.wheat_seeds;
		} else {
			randomCrop = random.nextInt(4);
			switch (randomCrop) {
				case 0:
					crop1Block = Blocks.carrots;
					seed1 = Items.carrot;
					break;
				case 1:
					crop1Block = Blocks.potatoes;
					seed1 = Items.potato;
					break;
				case 2:
					crop1Block = GOTBlocks.lettuceCrop;
					seed1 = GOTItems.lettuce;
					break;
				default:
					crop1Block = GOTBlocks.turnipCrop;
					seed1 = GOTItems.turnip;
					break;
			}
		}
		if (random.nextBoolean()) {
			crop2Block = Blocks.wheat;
			seed2 = Items.wheat_seeds;
		} else {
			randomCrop = random.nextInt(4);
			switch (randomCrop) {
				case 0:
					crop2Block = Blocks.carrots;
					seed2 = Items.carrot;
					break;
				case 1:
					crop2Block = Blocks.potatoes;
					seed2 = Items.potato;
					break;
				case 2:
					crop2Block = GOTBlocks.lettuceCrop;
					seed2 = GOTItems.lettuce;
					break;
				default:
					crop2Block = GOTBlocks.turnipCrop;
					seed2 = GOTItems.turnip;
					break;
			}
		}
	}
}
