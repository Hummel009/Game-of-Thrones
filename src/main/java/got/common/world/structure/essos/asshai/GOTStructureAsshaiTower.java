package got.common.world.structure.essos.asshai;

import java.util.Random;

import got.GOT;
import got.common.database.*;
import got.common.entity.essos.asshai.*;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.structure.other.GOTStructureBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureAsshaiTower extends GOTStructureBase {
	public GOTStructureAsshaiTower(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		this.setOriginAndRotation(world, i, j, k, rotation, 19);
		setupRandomBlocks(random);
		int j1;
		int k1;
		int i1;
		int j12;
		int k12;
		int sections = 5;
		int equipmentSection = 1 + random.nextInt(sections);
		if (restrictions) {
			for (int i12 = i - 7; i12 <= i + 7; ++i12) {
				for (k12 = k - 7; k12 <= k + 7; ++k12) {
					j12 = world.getHeightValue(i12, k12) - 1;
					Block block = world.getBlock(i12, j12, k12);
					int meta = world.getBlockMetadata(i12, j12, k12);
					if (block == GOTRegistry.asshaiDirt || block == GOTRegistry.basaltGravel || block == GOTRegistry.rock && meta == 0 || block == Blocks.grass || block == Blocks.dirt) {
						continue;
					}
					return false;
				}
			}
		}
		for (k1 = k - 2; k1 <= k + 2; ++k1) {
			for (j1 = j; !GOT.isOpaque(world, i - 6, j1, k1) && j1 >= 0; --j1) {
				setBlockAndNotifyAdequately(world, i - 6, j1, k1, GOTRegistry.brick1, 0);
			}
			for (j1 = j; !GOT.isOpaque(world, i + 6, j1, k1) && j1 >= 0; --j1) {
				setBlockAndNotifyAdequately(world, i + 6, j1, k1, GOTRegistry.brick1, 0);
			}
		}
		for (k1 = k - 4; k1 <= k + 4; ++k1) {
			for (j1 = j; !GOT.isOpaque(world, i - 5, j1, k1) && j1 >= 0; --j1) {
				setBlockAndNotifyAdequately(world, i - 5, j1, k1, GOTRegistry.brick1, 0);
			}
			for (j1 = j; !GOT.isOpaque(world, i + 5, j1, k1) && j1 >= 0; --j1) {
				setBlockAndNotifyAdequately(world, i + 5, j1, k1, GOTRegistry.brick1, 0);
			}
		}
		for (k1 = k - 5; k1 <= k + 5; ++k1) {
			for (i1 = i - 4; i1 <= i - 3; ++i1) {
				for (j12 = j; !GOT.isOpaque(world, i1, j12, k1) && j12 >= 0; --j12) {
					setBlockAndNotifyAdequately(world, i1, j12, k1, GOTRegistry.brick1, 0);
				}
			}
			for (i1 = i + 3; i1 <= i + 4; ++i1) {
				for (j12 = j; !GOT.isOpaque(world, i1, j12, k1) && j12 >= 0; --j12) {
					setBlockAndNotifyAdequately(world, i1, j12, k1, GOTRegistry.brick1, 0);
				}
			}
		}
		for (k1 = k - 6; k1 <= k + 6; ++k1) {
			for (i1 = i - 2; i1 <= i + 2; ++i1) {
				for (j12 = j; !GOT.isOpaque(world, i1, j12, k1) && j12 >= 0; --j12) {
					setBlockAndNotifyAdequately(world, i1, j12, k1, GOTRegistry.brick1, 0);
				}
			}
		}
		for (int l = 0; l <= sections; ++l) {
			generateTowerSection(world, random, i, j, k, l, false, l == equipmentSection);
		}
		generateTowerSection(world, random, i, j, k, sections + 1, true, false);
		GOTEntityAsshaiCaptain trader = new GOTEntityAsshaiCaptain(world);
		trader.setLocationAndAngles(i + 0.5, j + (sections + 1) * 8 + 1, k - 4 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
		trader.onSpawnWithEgg(null);
		trader.setHomeArea(i, j + (sections + 1) * 8, k, 24);
		world.spawnEntityInWorld(trader);
		GOTEntityAsshaiAlchemist trader2 = new GOTEntityAsshaiAlchemist(world);
		trader2.setLocationAndAngles(i + 0.5, j + (sections + 1) * 8 + 1, k - 4 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
		trader2.onSpawnWithEgg(null);
		trader2.setHomeArea(i, j + (sections + 1) * 8, k, 24);
		world.spawnEntityInWorld(trader2);
		switch (rotation) {
		case 0: {
			for (i1 = i - 1; i1 <= i + 1; ++i1) {
				setBlockAndNotifyAdequately(world, i1, j, k - 6, GOTRegistry.slabDouble1, 0);
				for (j12 = j + 1; j12 <= j + 4; ++j12) {
					setBlockAndNotifyAdequately(world, i1, j12, k - 6, GOTRegistry.gateIronBars, 3);
				}
			}
			break;
		}
		case 1: {
			for (k12 = k - 1; k12 <= k + 1; ++k12) {
				setBlockAndNotifyAdequately(world, i + 6, j, k12, GOTRegistry.slabDouble1, 0);
				for (j12 = j + 1; j12 <= j + 4; ++j12) {
					setBlockAndNotifyAdequately(world, i + 6, j12, k12, GOTRegistry.gateIronBars, 4);
				}
			}
			break;
		}
		case 2: {
			for (i1 = i - 1; i1 <= i + 1; ++i1) {
				setBlockAndNotifyAdequately(world, i1, j, k + 6, GOTRegistry.slabDouble1, 0);
				for (j12 = j + 1; j12 <= j + 4; ++j12) {
					setBlockAndNotifyAdequately(world, i1, j12, k + 6, GOTRegistry.gateIronBars, 2);
				}
			}
			break;
		}
		case 3: {
			for (k12 = k - 1; k12 <= k + 1; ++k12) {
				setBlockAndNotifyAdequately(world, i - 6, j, k12, GOTRegistry.slabDouble1, 0);
				for (j12 = j + 1; j12 <= j + 4; ++j12) {
					setBlockAndNotifyAdequately(world, i - 6, j12, k12, GOTRegistry.gateIronBars, 5);
				}
			}
		}
		}
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass(GOTEntityAsshaiGuard.class);
		respawner.setCheckRanges(12, -8, 50, 20);
		respawner.setSpawnRanges(5, 1, 40, 16);
		placeNPCRespawner(respawner, world, i, j, k);
		return true;
	}

	public void generateTowerSection(World world, Random random, int i, int j, int k, int section, boolean isTop, boolean isEquipmentSection) {
		int j1;
		int i1;
		for (j1 = section == 0 ? j : (j += section * 8) + 1; j1 <= (isTop ? j + 10 : j + 8); ++j1) {
			int i12;
			int k1;
			Block fillBlock = Blocks.air;
			int fillMeta = 0;
			if (j1 == j) {
				fillBlock = GOTRegistry.slabDouble1;
				fillMeta = 0;
			} else if (j1 == j + 8 && !isTop) {
				fillBlock = GOTRegistry.slabSingle1;
				fillMeta = 8;
			} else {
				fillBlock = Blocks.air;
				fillMeta = 0;
			}
			for (k1 = k - 2; k1 <= k + 2; ++k1) {
				setBlockAndNotifyAdequately(world, i - 5, j1, k1, fillBlock, fillMeta);
				setBlockAndNotifyAdequately(world, i + 5, j1, k1, fillBlock, fillMeta);
			}
			for (k1 = k - 4; k1 <= k + 4; ++k1) {
				for (i12 = i - 4; i12 <= i - 3; ++i12) {
					setBlockAndNotifyAdequately(world, i12, j1, k1, fillBlock, fillMeta);
				}
				for (i12 = i + 3; i12 <= i + 4; ++i12) {
					setBlockAndNotifyAdequately(world, i12, j1, k1, fillBlock, fillMeta);
				}
			}
			for (k1 = k - 5; k1 <= k + 5; ++k1) {
				for (i12 = i - 2; i12 <= i + 2; ++i12) {
					setBlockAndNotifyAdequately(world, i12, j1, k1, fillBlock, fillMeta);
				}
			}
		}
		for (j1 = j + 1; j1 <= (isTop ? j + 1 : j + 8); ++j1) {
			for (int k1 = k - 2; k1 <= k + 2; ++k1) {
				setBlockAndNotifyAdequately(world, i - 6, j1, k1, GOTRegistry.brick1, 0);
				setBlockAndNotifyAdequately(world, i + 6, j1, k1, GOTRegistry.brick1, 0);
			}
			for (int i13 = i - 2; i13 <= i + 2; ++i13) {
				setBlockAndNotifyAdequately(world, i13, j1, k - 6, GOTRegistry.brick1, 0);
				setBlockAndNotifyAdequately(world, i13, j1, k + 6, GOTRegistry.brick1, 0);
			}
			setBlockAndNotifyAdequately(world, i - 5, j1, k - 4, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i - 5, j1, k - 3, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i - 5, j1, k + 3, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i - 5, j1, k + 4, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i - 4, j1, k - 5, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i - 4, j1, k + 5, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i - 3, j1, k - 5, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i - 3, j1, k + 5, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i + 3, j1, k - 5, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i + 3, j1, k + 5, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i + 4, j1, k - 5, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i + 4, j1, k + 5, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i + 5, j1, k - 4, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i + 5, j1, k - 3, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i + 5, j1, k + 3, GOTRegistry.brick1, 0);
			setBlockAndNotifyAdequately(world, i + 5, j1, k + 4, GOTRegistry.brick1, 0);
		}
		placeBigTorch(world, i - 5, j + 1, k - 2);
		placeBigTorch(world, i - 5, j + 1, k + 2);
		placeBigTorch(world, i + 5, j + 1, k - 2);
		placeBigTorch(world, i + 5, j + 1, k + 2);
		placeBigTorch(world, i - 2, j + 1, k - 5);
		placeBigTorch(world, i + 2, j + 1, k - 5);
		placeBigTorch(world, i - 2, j + 1, k + 5);
		placeBigTorch(world, i + 2, j + 1, k + 5);
		if (!isTop) {
			for (j1 = j + 2; j1 <= j + 4; ++j1) {
				for (int k1 = k - 1; k1 <= k + 1; ++k1) {
					setBlockAndNotifyAdequately(world, i - 6, j1, k1, GOTRegistry.asshaiBars, 0);
					setBlockAndNotifyAdequately(world, i + 6, j1, k1, GOTRegistry.asshaiBars, 0);
				}
				for (int i14 = i - 1; i14 <= i + 1; ++i14) {
					setBlockAndNotifyAdequately(world, i14, j1, k - 6, GOTRegistry.asshaiBars, 0);
					setBlockAndNotifyAdequately(world, i14, j1, k + 6, GOTRegistry.asshaiBars, 0);
				}
			}
			for (i1 = i - 2; i1 <= i + 2; ++i1) {
				for (int k1 = k - 2; k1 <= k + 2; ++k1) {
					setBlockAndNotifyAdequately(world, i1, j + 8, k1, Blocks.air, 0);
				}
			}
			setBlockAndNotifyAdequately(world, i - 2, j + 1, k + 1, GOTRegistry.slabSingle1, 0);
			setBlockAndNotifyAdequately(world, i - 2, j + 1, k + 2, GOTRegistry.slabSingle1, 8);
			setBlockAndNotifyAdequately(world, i - 1, j + 2, k + 2, GOTRegistry.slabSingle1, 0);
			setBlockAndNotifyAdequately(world, i, j + 2, k + 2, GOTRegistry.slabSingle1, 8);
			setBlockAndNotifyAdequately(world, i + 1, j + 3, k + 2, GOTRegistry.slabSingle1, 0);
			setBlockAndNotifyAdequately(world, i + 2, j + 3, k + 2, GOTRegistry.slabSingle1, 8);
			setBlockAndNotifyAdequately(world, i + 2, j + 4, k + 1, GOTRegistry.slabSingle1, 0);
			setBlockAndNotifyAdequately(world, i + 2, j + 4, k, GOTRegistry.slabSingle1, 8);
			setBlockAndNotifyAdequately(world, i + 2, j + 5, k - 1, GOTRegistry.slabSingle1, 0);
			setBlockAndNotifyAdequately(world, i + 2, j + 5, k - 2, GOTRegistry.slabSingle1, 8);
			setBlockAndNotifyAdequately(world, i + 1, j + 6, k - 2, GOTRegistry.slabSingle1, 0);
			setBlockAndNotifyAdequately(world, i, j + 6, k - 2, GOTRegistry.slabSingle1, 8);
			setBlockAndNotifyAdequately(world, i - 1, j + 7, k - 2, GOTRegistry.slabSingle1, 0);
			setBlockAndNotifyAdequately(world, i - 2, j + 7, k - 2, GOTRegistry.slabSingle1, 8);
			setBlockAndNotifyAdequately(world, i - 2, j + 8, k - 1, GOTRegistry.slabSingle1, 0);
			setBlockAndNotifyAdequately(world, i - 2, j + 8, k, GOTRegistry.slabSingle1, 8);
		}
		for (i1 = i - 1; i1 <= i + 1; ++i1) {
			for (int k1 = k - 1; k1 <= k + 1; ++k1) {
				for (int j12 = j + 1; j12 <= (isTop ? j + 3 : j + 8); ++j12) {
					setBlockAndNotifyAdequately(world, i1, j12, k1, GOTRegistry.brick1, 0);
				}
			}
		}
		if (isEquipmentSection) {
			int l = random.nextInt(4);
			switch (l) {
			case 0: {
				for (int i15 = i - 1; i15 <= i + 1; ++i15) {
					setBlockAndNotifyAdequately(world, i15, j + 1, k - 5, GOTRegistry.bomb, 0);
					setBlockAndNotifyAdequately(world, i15, j + 1, k + 5, GOTRegistry.slabSingle1, 9);
					this.placeBarrel(world, random, i15, j + 2, k + 5, 2, GOTFoods.ESSOS_DRINK);
				}
				break;
			}
			case 1: {
				for (int k1 = k - 1; k1 <= k + 1; ++k1) {
					setBlockAndNotifyAdequately(world, i + 5, j + 1, k1, GOTRegistry.bomb, 0);
					setBlockAndNotifyAdequately(world, i - 5, j + 1, k1, GOTRegistry.slabSingle1, 9);
					this.placeBarrel(world, random, i - 5, j + 2, k1, 5, GOTFoods.ESSOS_DRINK);
				}
				break;
			}
			case 2: {
				for (int i16 = i - 1; i16 <= i + 1; ++i16) {
					setBlockAndNotifyAdequately(world, i16, j + 1, k + 5, GOTRegistry.bomb, 0);
					setBlockAndNotifyAdequately(world, i16, j + 1, k - 5, GOTRegistry.slabSingle1, 9);
					this.placeBarrel(world, random, i16, j + 2, k - 5, 3, GOTFoods.ESSOS_DRINK);
				}
				break;
			}
			case 3: {
				for (int k1 = k - 1; k1 <= k + 1; ++k1) {
					setBlockAndNotifyAdequately(world, i - 5, j + 1, k1, GOTRegistry.bomb, 0);
					setBlockAndNotifyAdequately(world, i + 5, j + 1, k1, GOTRegistry.slabSingle1, 9);
					this.placeBarrel(world, random, i + 5, j + 2, k1, 4, GOTFoods.ESSOS_DRINK);
				}
				break;
			}
			}
		}
		if (isTop) {
			for (j1 = j + 1; j1 <= j + 8; ++j1) {
				for (int k1 = k - 1; k1 <= k + 1; ++k1) {
					setBlockAndNotifyAdequately(world, i - 7, j1, k1, GOTRegistry.brick1, 0);
					setBlockAndNotifyAdequately(world, i + 7, j1, k1, GOTRegistry.brick1, 0);
				}
				for (int i17 = i - 1; i17 <= i + 1; ++i17) {
					setBlockAndNotifyAdequately(world, i17, j1, k - 7, GOTRegistry.brick1, 0);
					setBlockAndNotifyAdequately(world, i17, j1, k + 7, GOTRegistry.brick1, 0);
				}
			}
			for (int k1 = k - 1; k1 <= k + 1; ++k1) {
				setBlockAndNotifyAdequately(world, i - 7, j, k1, GOTRegistry.stairsBasaltBrick, 4);
				setBlockAndNotifyAdequately(world, i - 6, j + 2, k1, GOTRegistry.stairsBasaltBrick, 1);
				setBlockAndNotifyAdequately(world, i - 7, j + 9, k1, GOTRegistry.stairsBasaltBrick, 0);
				setBlockAndNotifyAdequately(world, i - 6, j + 9, k1, GOTRegistry.stairsBasaltBrick, 5);
				setBlockAndNotifyAdequately(world, i - 6, j + 10, k1, GOTRegistry.stairsBasaltBrick, 0);
				setBlockAndNotifyAdequately(world, i + 7, j, k1, GOTRegistry.stairsBasaltBrick, 5);
				setBlockAndNotifyAdequately(world, i + 6, j + 2, k1, GOTRegistry.stairsBasaltBrick, 0);
				setBlockAndNotifyAdequately(world, i + 7, j + 9, k1, GOTRegistry.stairsBasaltBrick, 1);
				setBlockAndNotifyAdequately(world, i + 6, j + 9, k1, GOTRegistry.stairsBasaltBrick, 4);
				setBlockAndNotifyAdequately(world, i + 6, j + 10, k1, GOTRegistry.stairsBasaltBrick, 1);
			}
			for (i1 = i - 1; i1 <= i + 1; ++i1) {
				setBlockAndNotifyAdequately(world, i1, j, k - 7, GOTRegistry.stairsBasaltBrick, 6);
				setBlockAndNotifyAdequately(world, i1, j + 2, k - 6, GOTRegistry.stairsBasaltBrick, 3);
				setBlockAndNotifyAdequately(world, i1, j + 9, k - 7, GOTRegistry.stairsBasaltBrick, 2);
				setBlockAndNotifyAdequately(world, i1, j + 9, k - 6, GOTRegistry.stairsBasaltBrick, 7);
				setBlockAndNotifyAdequately(world, i1, j + 10, k - 6, GOTRegistry.stairsBasaltBrick, 2);
				setBlockAndNotifyAdequately(world, i1, j, k + 7, GOTRegistry.stairsBasaltBrick, 7);
				setBlockAndNotifyAdequately(world, i1, j + 2, k + 6, GOTRegistry.stairsBasaltBrick, 2);
				setBlockAndNotifyAdequately(world, i1, j + 9, k + 7, GOTRegistry.stairsBasaltBrick, 3);
				setBlockAndNotifyAdequately(world, i1, j + 9, k + 6, GOTRegistry.stairsBasaltBrick, 6);
				setBlockAndNotifyAdequately(world, i1, j + 10, k + 6, GOTRegistry.stairsBasaltBrick, 3);
			}
			for (j1 = j; j1 <= j + 4; ++j1) {
				setBlockAndNotifyAdequately(world, i - 5, j1, k - 5, GOTRegistry.brick1, 0);
				setBlockAndNotifyAdequately(world, i - 5, j1, k + 5, GOTRegistry.brick1, 0);
				setBlockAndNotifyAdequately(world, i + 5, j1, k - 5, GOTRegistry.brick1, 0);
				setBlockAndNotifyAdequately(world, i + 5, j1, k + 5, GOTRegistry.brick1, 0);
			}
			setBlockAndNotifyAdequately(world, i - 5, j + 2, k - 4, GOTRegistry.stairsBasaltBrick, 3);
			setBlockAndNotifyAdequately(world, i - 4, j + 2, k - 5, GOTRegistry.stairsBasaltBrick, 1);
			setBlockAndNotifyAdequately(world, i - 5, j + 2, k + 4, GOTRegistry.stairsBasaltBrick, 2);
			setBlockAndNotifyAdequately(world, i - 4, j + 2, k + 5, GOTRegistry.stairsBasaltBrick, 1);
			setBlockAndNotifyAdequately(world, i + 5, j + 2, k - 4, GOTRegistry.stairsBasaltBrick, 3);
			setBlockAndNotifyAdequately(world, i + 4, j + 2, k - 5, GOTRegistry.stairsBasaltBrick, 0);
			setBlockAndNotifyAdequately(world, i + 5, j + 2, k + 4, GOTRegistry.stairsBasaltBrick, 2);
			setBlockAndNotifyAdequately(world, i + 4, j + 2, k + 5, GOTRegistry.stairsBasaltBrick, 0);
		}
	}
}