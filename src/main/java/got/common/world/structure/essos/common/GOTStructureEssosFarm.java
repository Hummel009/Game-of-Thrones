package got.common.world.structure.essos.common;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTFarmhand;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureEssosFarm extends GOTStructureEssosBase {
	private Block crop1Block;
	private Item seed1;
	private Block crop2Block;
	private Item seed2;

	public GOTStructureEssosFarm(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		setOriginAndRotation(world, i, j, k, rotation, 6);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (int i1 = -4; i1 <= 4; ++i1) {
				for (int k1 = -6; k1 <= 6; ++k1) {
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
			for (int k1 = -6; k1 <= 6; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 == 4 && k2 == 6) {
					continue;
				}
				j1 = -1;
				while (!isOpaque(world, i1, j1, k1) && getY(j1) >= 0) {
					setBlockAndMetadata(world, i1, j1, k1, stoneBlock, stoneMeta);
					setGrassToDirt(world, i1, j1 - 1, k1);
					--j1;
				}
				for (j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("essos_farm");
		associateBlockMetaAlias("STONE", stoneBlock, stoneMeta);
		associateBlockMetaAlias("BRICK", brickBlock, brickMeta);
		associateBlockMetaAlias("BRICK_SLAB", brickSlabBlock, brickSlabMeta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockAlias("CROP1", crop1Block);
		associateBlockAlias("CROP2", crop2Block);
		generateStrScan(world, random, 0, 0, 0);
		placeSkull(world, random, -4, 4, 0);
		placeSkull(world, random, 4, 4, 0);
		if (random.nextInt(4) == 0) {
			GOTEntityNPC farmer = getFarmer(world);
			spawnNPCAndSetHome(farmer, world, 0, 1, 1, 8);
		}
		GOTEntityNPC jabroni1 = getFarmhand(world);
		((GOTFarmhand) jabroni1).setSeedsItem(seed1);
		spawnNPCAndSetHome(jabroni1, world, -2, 1, 0, 8);
		GOTEntityNPC jabroni2 = getFarmhand(world);
		((GOTFarmhand) jabroni2).setSeedsItem(seed2);
		spawnNPCAndSetHome(jabroni2, world, 2, 1, 0, 8);
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
				case 3:
					crop2Block = GOTBlocks.turnipCrop;
					seed2 = GOTItems.turnip;
					break;
				default:
					break;
			}
		}
	}
}