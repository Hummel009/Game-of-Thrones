package got.common.world.structure.essos.lhazar;

import got.common.database.GOTBlocks;
import got.common.entity.essos.lhazar.GOTEntityLhazarFarmer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureLhazarFarm extends GOTStructureLhazarBase {
	private Block crop1Block;
	private Block crop2Block;

	public GOTStructureLhazarFarm(boolean flag) {
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
			for (int i1 = -5; i1 <= 5; ++i1) {
				for (int k1 = -5; k1 <= 5; ++k1) {
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
		for (int i1 = -5; i1 <= 5; ++i1) {
			for (int k1 = -5; k1 <= 5; ++k1) {
				int i2 = Math.abs(i1);
				int k2 = Math.abs(k1);
				if (i2 == 5 && k2 == 5) {
					continue;
				}
				for (j1 = 1; j1 <= 6; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("lhazar_farm");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("CROP1", crop1Block, 7);
		associateBlockMetaAlias("CROP2", crop2Block, 7);
		associateBlockMetaAlias("FLAG", flagBlock, flagMeta);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 0, 0);
		if (random.nextInt(4) == 0) {
			GOTEntityLhazarFarmer farmer = new GOTEntityLhazarFarmer(world);
			spawnNPCAndSetHome(farmer, world, 0, 1, -1, 8);
		}
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		int randomCrop;
		super.setupRandomBlocks(random);
		if (random.nextBoolean()) {
			crop1Block = Blocks.wheat;
		} else {
			randomCrop = random.nextInt(4);
			switch (randomCrop) {
				case 0:
					crop1Block = Blocks.carrots;
					break;
				case 1:
					crop1Block = Blocks.potatoes;
					break;
				case 2:
					crop1Block = GOTBlocks.lettuceCrop;
					break;
				default:
					crop1Block = GOTBlocks.turnipCrop;
					break;
			}
		}
		if (random.nextBoolean()) {
			crop2Block = Blocks.wheat;
		} else {
			randomCrop = random.nextInt(4);
			switch (randomCrop) {
				case 0:
					crop2Block = Blocks.carrots;
					break;
				case 1:
					crop2Block = Blocks.potatoes;
					break;
				case 2:
					crop2Block = GOTBlocks.lettuceCrop;
					break;
				case 3:
					crop2Block = GOTBlocks.turnipCrop;
					break;
				default:
					break;
			}
		}
	}
}