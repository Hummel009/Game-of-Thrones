package got.common.world.structure.essos.lys;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.entity.essos.lys.GOTEntityLysSlaver;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class GOTStructureLysFarm extends GOTStructureLysBase {
	public Block crop1Block;
	public Item seed1;
	public Block crop2Block;
	public Item seed2;

	public GOTStructureLysFarm(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int j1;
		this.setOriginAndRotation(world, i, j, k, rotation, 6);
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
		this.placeSkull(world, random, -4, 4, 0);
		this.placeSkull(world, random, 4, 4, 0);
		GOTEntityLysSlaver farmer = new GOTEntityLysSlaver(world);
		spawnNPCAndSetHome(farmer, world, 0, 1, 1, 8);
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
				crop1Block = GOTRegistry.lettuceCrop;
				seed1 = GOTRegistry.lettuce;
				break;
			case 3:
				crop1Block = GOTRegistry.turnipCrop;
				seed1 = GOTRegistry.turnip;
				break;
			default:
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
				crop2Block = GOTRegistry.lettuceCrop;
				seed2 = GOTRegistry.lettuce;
				break;
			case 3:
				crop2Block = GOTRegistry.turnipCrop;
				seed2 = GOTRegistry.turnip;
				break;
			default:
				break;
			}
		}
	}
}
