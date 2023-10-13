package got.common.world.structure.essos.mossovy;

import java.util.Random;

import com.google.common.math.IntMath;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.animal.GOTEntityHorse;
import got.common.entity.essos.mossovy.GOTEntityMossovyBlacksmith;
import got.common.entity.essos.mossovy.GOTEntityMossovyWitcher;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.item.other.GOTItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureMossovyCastle extends GOTStructureMossovyBase {
	public GOTStructureMossovyCastle(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int k1;
		int i1;
		int i12;
		int k2;
		int k12;
		int i13;
		int k13;
		int j1;
		int j12;
		int i14;
		int i15;
		int i2;
		int j13;
		setOriginAndRotation(world, i, j, k, rotation, 13);
		setupRandomBlocks(random);
		if (restrictions) {
			for (i15 = -12; i15 <= 12; ++i15) {
				for (k12 = -12; k12 <= 12; ++k12) {
					j12 = getTopBlock(world, i15, k12) - 1;
					if (isSurface(world, i15, j12, k12)) {
						continue;
					}
					return false;
				}
			}
		}
		for (i15 = -12; i15 <= 12; ++i15) {
			for (k12 = -12; k12 <= 12; ++k12) {
				int j14;
				i2 = Math.abs(i15);
				k2 = Math.abs(k12);
				for (j14 = 1; j14 <= 10; ++j14) {
					setAir(world, i15, j14, k12);
				}
				for (j14 = 0; (j14 >= 0 || !isOpaque(world, i15, j14, k12)) && getY(j14) >= 0; --j14) {
					if (i2 == 12 && (k2 == 12 || k2 == 9 || k2 == 2) || k2 == 12 && (i2 == 9 || i2 == 2)) {
						setBlockAndMetadata(world, i15, j14, k12, woodBeamBlock, woodBeamMeta);
					} else if (i2 > 9 || k2 > 9) {
						setBlockAndMetadata(world, i15, j14, k12, plankBlock, plankMeta);
					} else if (j14 == 0) {
						int randomGround = random.nextInt(3);
						switch (randomGround) {
						case 0:
							setBlockAndMetadata(world, i15, 0, k12, Blocks.grass, 0);
							break;
						case 1:
							setBlockAndMetadata(world, i15, 0, k12, Blocks.dirt, 1);
							break;
						case 2:
							setBlockAndMetadata(world, i15, 0, k12, GOTBlocks.dirtPath, 0);
							break;
						default:
							break;
						}
						if (random.nextInt(3) == 0) {
							setBlockAndMetadata(world, i15, 1, k12, GOTBlocks.thatchFloor, 0);
						}
					} else {
						setBlockAndMetadata(world, i15, j14, k12, Blocks.dirt, 0);
					}
					setGrassToDirt(world, i15, j14 - 1, k12);
				}
			}
		}
		for (i15 = -12; i15 <= 12; ++i15) {
			for (k12 = -12; k12 <= 12; ++k12) {
				int j15;
				i2 = Math.abs(i15);
				k2 = Math.abs(k12);
				int yBoost = 0;
				if (k12 < 8 && i2 < 7) {
					yBoost = 1;
				}
				if ((i2 == 9 || i2 == 12) && (k2 == 9 || k2 == 12)) {
					for (j15 = 1; j15 <= 8; ++j15) {
						setBlockAndMetadata(world, i15, j15, k12, woodBeamBlock, woodBeamMeta);
					}
					continue;
				}
				if ((i2 == 12 || k2 == 12) && (k2 == 2 || i2 == 2)) {
					for (j15 = 1; j15 <= 6 + yBoost; ++j15) {
						setBlockAndMetadata(world, i15, j15, k12, woodBeamBlock, woodBeamMeta);
					}
					continue;
				}
				if (i2 == 12 || k2 == 12) {
					for (j15 = 1; j15 <= 5 + yBoost; ++j15) {
						setBlockAndMetadata(world, i15, j15, k12, plankBlock, plankMeta);
					}
					if (i2 == 12 && k2 >= 10 && k2 <= 11 || k2 == 12 && i2 >= 10 && i2 <= 11) {
						setBlockAndMetadata(world, i15, 5 + yBoost, k12, fenceBlock, fenceMeta);
						continue;
					}
					if (IntMath.mod(i2 + k2, 2) == 0) {
						setBlockAndMetadata(world, i15, 6 + yBoost, k12, plankBlock, plankMeta);
						continue;
					}
					setBlockAndMetadata(world, i15, 6 + yBoost, k12, plankSlabBlock, plankSlabMeta);
					continue;
				}
				if (i2 > 9 || k2 > 9) {
					for (j15 = 1; j15 <= 4 + yBoost; ++j15) {
						setBlockAndMetadata(world, i15, j15, k12, plankBlock, plankMeta);
					}
					continue;
				}
				if (i2 != 9 && k2 != 9) {
					continue;
				}
				setBlockAndMetadata(world, i15, 5 + yBoost, k12, fenceBlock, fenceMeta);
				if (i2 == 9 && IntMath.mod(k12, 3) == 0 || k2 == 9 && IntMath.mod(i15, 3) == 0) {
					setBlockAndMetadata(world, i15, 6 + yBoost, k12, Blocks.torch, 5);
				}
				if (k12 == -9) {
					setBlockAndMetadata(world, i15, 4 + yBoost, -9, plankStairBlock, 7);
					continue;
				}
				if (k12 == 9) {
					setBlockAndMetadata(world, i15, 4 + yBoost, 9, plankStairBlock, 6);
					continue;
				}
				if (i15 == -9) {
					setBlockAndMetadata(world, -9, 4 + yBoost, k12, plankStairBlock, 4);
					continue;
				}
				if (i15 != 9) {
					continue;
				}
				setBlockAndMetadata(world, i15, 4 + yBoost, k12, plankStairBlock, 5);
			}
		}
		int[] i16 = { -12, 9 };
		k12 = i16.length;
		for (i2 = 0; i2 < k12; ++i2) {
			i13 = i16[i2];
			for (int k14 : new int[] { -12, 9 }) {
				int i22;
				setBlockAndMetadata(world, i13 + 1, 8, k14, plankStairBlock, 4);
				setBlockAndMetadata(world, i13 + 2, 8, k14, plankStairBlock, 5);
				setBlockAndMetadata(world, i13 + 1, 8, k14 + 3, plankStairBlock, 4);
				setBlockAndMetadata(world, i13 + 2, 8, k14 + 3, plankStairBlock, 5);
				setBlockAndMetadata(world, i13, 8, k14 + 1, plankStairBlock, 7);
				setBlockAndMetadata(world, i13, 8, k14 + 2, plankStairBlock, 6);
				setBlockAndMetadata(world, i13 + 3, 8, k14 + 1, plankStairBlock, 7);
				setBlockAndMetadata(world, i13 + 3, 8, k14 + 2, plankStairBlock, 6);
				for (i22 = i13; i22 <= i13 + 3; ++i22) {
					setBlockAndMetadata(world, i22, 9, k14 - 1, roofSlabBlock, roofSlabMeta);
					setBlockAndMetadata(world, i22, 9, k14 + 4, roofSlabBlock, roofSlabMeta);
				}
				for (int k22 = k14; k22 <= k14 + 3; ++k22) {
					setBlockAndMetadata(world, i13 - 1, 9, k22, roofSlabBlock, roofSlabMeta);
					setBlockAndMetadata(world, i13 + 4, 9, k22, roofSlabBlock, roofSlabMeta);
				}
				for (i22 = i13; i22 <= i13 + 3; ++i22) {
					for (int k23 = k14; k23 <= k14 + 3; ++k23) {
						if (i22 >= i13 + 1 && i22 <= i13 + 2 && k23 >= k14 + 1 && k23 <= k14 + 2) {
							setBlockAndMetadata(world, i22, 9, k23, roofSlabBlock, roofSlabMeta | 8);
							setBlockAndMetadata(world, i22, 10, k23, roofSlabBlock, roofSlabMeta);
							continue;
						}
						setBlockAndMetadata(world, i22, 9, k23, roofBlock, roofMeta);
					}
				}
			}
		}
		for (k1 = -12; k1 <= 12; ++k1) {
			int k24 = Math.abs(k1);
			if (k24 >= 10 && k24 <= 11 || k24 >= 3 && k24 <= 8) {
				setBlockAndMetadata(world, -12, 1, k1, plankStairBlock, 1);
				for (j12 = 2; j12 <= 3; ++j12) {
					setAir(world, -12, j12, k1);
				}
				setBlockAndMetadata(world, -12, 4, k1, plankStairBlock, 5);
				setBlockAndMetadata(world, 12, 1, k1, plankStairBlock, 0);
				for (j12 = 2; j12 <= 3; ++j12) {
					setAir(world, 12, j12, k1);
				}
				setBlockAndMetadata(world, 12, 4, k1, plankStairBlock, 4);
			}
			if (k24 != 12 || k1 <= 0) {
				continue;
			}
			for (i12 = -1; i12 <= 1; ++i12) {
				setBlockAndMetadata(world, i12, 1, k1, plankBlock, plankMeta);
				setBlockAndMetadata(world, i12, 4, k1, plankBlock, plankMeta);
				setBlockAndMetadata(world, i12, 5, k1, woodBeamBlock, woodBeamMeta | 4);
				setBlockAndMetadata(world, i12, 6, k1, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, i12, 5, k1 - Integer.signum(k1), plankSlabBlock, plankSlabMeta);
			}
			setBlockAndMetadata(world, -2, 7, k1, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, 2, 7, k1, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, -1, 2, k1, plankStairBlock, 0);
			setAir(world, 0, 2, k1);
			setBlockAndMetadata(world, 1, 2, k1, plankStairBlock, 1);
			setBlockAndMetadata(world, -1, 3, k1, plankStairBlock, 4);
			setAir(world, 0, 3, k1);
			setBlockAndMetadata(world, 1, 3, k1, plankStairBlock, 5);
		}
		for (i14 = -12; i14 <= 12; ++i14) {
			int i23 = Math.abs(i14);
			if (i23 >= 10 && i23 <= 11 || i23 >= 3 && i23 <= 8) {
				setBlockAndMetadata(world, i14, 1, -12, plankStairBlock, 2);
				for (j12 = 2; j12 <= 3; ++j12) {
					setAir(world, i14, j12, -12);
				}
				setBlockAndMetadata(world, i14, 4, -12, plankStairBlock, 6);
				setBlockAndMetadata(world, i14, 1, 12, plankStairBlock, 3);
				for (j12 = 2; j12 <= 3; ++j12) {
					setAir(world, i14, j12, 12);
				}
				setBlockAndMetadata(world, i14, 4, 12, plankStairBlock, 7);
			}
			if (i23 != 12) {
				continue;
			}
			for (k13 = -1; k13 <= 1; ++k13) {
				setBlockAndMetadata(world, i14, 1, k13, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, 4, k13, plankBlock, plankMeta);
				setBlockAndMetadata(world, i14, 5, k13, woodBeamBlock, woodBeamMeta | 8);
				setBlockAndMetadata(world, i14, 6, k13, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, i14 - Integer.signum(i14), 5, k13, plankSlabBlock, plankSlabMeta);
			}
			setBlockAndMetadata(world, i14, 7, -2, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, i14, 7, 2, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, i14, 2, -1, plankStairBlock, 3);
			setAir(world, i14, 2, 0);
			setBlockAndMetadata(world, i14, 2, 1, plankStairBlock, 2);
			setBlockAndMetadata(world, i14, 3, -1, plankStairBlock, 7);
			setAir(world, i14, 3, 0);
			setBlockAndMetadata(world, i14, 3, 1, plankStairBlock, 6);
		}
		for (k1 = -11; k1 <= -10; ++k1) {
			setAir(world, -6, 5, k1);
			setBlockAndMetadata(world, -5, 5, k1, plankStairBlock, 1);
			setBlockAndMetadata(world, 5, 5, k1, plankStairBlock, 0);
			setAir(world, 6, 5, k1);
		}
		for (j1 = 4; j1 <= 7; ++j1) {
			setBlockAndMetadata(world, -6, j1, -9, woodBeamBlock, woodBeamMeta);
			setBlockAndMetadata(world, 6, j1, -9, woodBeamBlock, woodBeamMeta);
		}
		setBlockAndMetadata(world, -6, 8, -9, Blocks.torch, 5);
		setBlockAndMetadata(world, 6, 8, -9, Blocks.torch, 5);
		for (k1 = -12; k1 <= -10; ++k1) {
			for (j13 = 1; j13 <= 4; ++j13) {
				for (i12 = -1; i12 <= 1; ++i12) {
					setAir(world, i12, j13, k1);
				}
				setBlockAndMetadata(world, -2, j13, k1, woodBeamBlock, woodBeamMeta);
				setBlockAndMetadata(world, 2, j13, k1, woodBeamBlock, woodBeamMeta);
			}
			for (i1 = -1; i1 <= 1; ++i1) {
				setBlockAndMetadata(world, i1, 4, k1, woodBeamBlock, woodBeamMeta | 4);
			}
		}
		for (j1 = 1; j1 <= 3; ++j1) {
			for (i1 = -1; i1 <= 1; ++i1) {
				setBlockAndMetadata(world, i1, j1, -11, GOTBlocks.gateIronBars, 3);
			}
		}
		for (i14 = -1; i14 <= 1; ++i14) {
			setBlockAndMetadata(world, i14, 4, -12, plankStairBlock, 6);
			setBlockAndMetadata(world, i14, 5, -12, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i14, 6, -12, woodBeamBlock, woodBeamMeta | 4);
			setBlockAndMetadata(world, i14, 6, -11, plankSlabBlock, plankSlabMeta);
		}
		for (j1 = 6; j1 <= 8; ++j1) {
			setBlockAndMetadata(world, 0, j1, -12, woodBeamBlock, woodBeamMeta);
		}
		setBlockAndMetadata(world, -2, 8, -12, plankStairBlock, 1);
		setBlockAndMetadata(world, 0, 9, -12, plankSlabBlock, plankSlabMeta);
		setBlockAndMetadata(world, 2, 8, -12, plankStairBlock, 0);
		setBlockAndMetadata(world, -1, 7, -12, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 1, 7, -12, fenceBlock, fenceMeta);
		placeWallBanner(world, -2, 6, -12, GOTItemBanner.BannerType.MOSSOVY, 2);
		placeWallBanner(world, 0, 7, -12, GOTItemBanner.BannerType.MOSSOVY, 2);
		placeWallBanner(world, 2, 6, -12, GOTItemBanner.BannerType.MOSSOVY, 2);
		setBlockAndMetadata(world, -2, 3, -13, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 3, -13, Blocks.torch, 4);
		setBlockAndMetadata(world, -2, 3, -9, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 3, -9, Blocks.torch, 3);
		for (k1 = -13; k1 <= 9; ++k1) {
			for (i1 = -1; i1 <= 1; ++i1) {
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i1, j12, k1)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i1, j12, k1, brickBlock, brickMeta);
					setGrassToDirt(world, i1, j12 - 1, k1);
				}
			}
			if (k1 <= -10) {
				continue;
			}
			setBlockAndMetadata(world, -2, 0, k1, brickBlock, brickMeta);
			setBlockAndMetadata(world, 2, 0, k1, brickBlock, brickMeta);
			if (IntMath.mod(k1, 4) != 2) {
				continue;
			}
			setBlockAndMetadata(world, -2, 1, k1, GOTBlocks.wallStoneV, 1);
			setBlockAndMetadata(world, -2, 2, k1, Blocks.torch, 5);
			setBlockAndMetadata(world, 2, 1, k1, GOTBlocks.wallStoneV, 1);
			setBlockAndMetadata(world, 2, 2, k1, Blocks.torch, 5);
		}
		for (j1 = 1; j1 <= 3; ++j1) {
			setBlockAndMetadata(world, -2, j1, 10, woodBeamBlock, woodBeamMeta);
			setBlockAndMetadata(world, 2, j1, 10, woodBeamBlock, woodBeamMeta);
		}
		setBlockAndMetadata(world, -2, 3, 9, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 3, 9, Blocks.torch, 4);
		int[] j16 = { -7, 7 };
		i1 = j16.length;
		for (j12 = 0; j12 < i1; ++j12) {
			i13 = j16[j12];
			setBlockAndMetadata(world, i13, 1, 0, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i13, 2, 0, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, i13, 3, 0, Blocks.torch, 5);
			for (int l = 0; l < 2; ++l) {
				GOTEntityHorse horse = new GOTEntityHorse(world);
				spawnNPCAndSetHome(horse, world, i13 - Integer.signum(i13) * 3, 1, 0, 0);
				horse.setHorseType(0);
				horse.saddleMountForWorldGen();
				horse.detachHome();
				leashEntityTo(horse, world, i13, 2, 0);
			}
		}
		for (int k15 = -9; k15 <= -5; ++k15) {
			for (i1 = -9; i1 <= -5; ++i1) {
				setBlockAndMetadata(world, i1, 3, k15, plankSlabBlock, plankSlabMeta);
			}
		}
		setBlockAndMetadata(world, -9, 3, -9, plankBlock, plankMeta);
		setBlockAndMetadata(world, -6, 3, -9, plankBlock, plankMeta);
		for (int j17 = 1; j17 <= 2; ++j17) {
			if (j17 == 1) {
				setBlockAndMetadata(world, -7, 1, -9, Blocks.furnace, 3);
				setBlockAndMetadata(world, -9, 1, -7, Blocks.furnace, 4);
			} else {
				setBlockAndMetadata(world, -7, j17, -9, GOTBlocks.alloyForge, 3);
				setBlockAndMetadata(world, -9, j17, -7, GOTBlocks.alloyForge, 4);
			}
			setBlockAndMetadata(world, -8, j17, -9, plankBlock, plankMeta);
			setBlockAndMetadata(world, -9, j17, -9, plankBlock, plankMeta);
			setBlockAndMetadata(world, -9, j17, -8, plankBlock, plankMeta);
			setBlockAndMetadata(world, -5, j17, -5, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -5, 1, -9, plankBlock, plankMeta);
		setBlockAndMetadata(world, -5, 2, -9, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -6, 1, -9, plankBlock, plankMeta);
		setBlockAndMetadata(world, -6, 2, -9, Blocks.torch, 3);
		setBlockAndMetadata(world, -9, 1, -6, plankBlock, plankMeta);
		setBlockAndMetadata(world, -9, 2, -6, Blocks.torch, 2);
		setBlockAndMetadata(world, -9, 1, -5, plankBlock, plankMeta);
		setBlockAndMetadata(world, -9, 2, -5, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -6, 1, -5, Blocks.anvil, 1);
		setBlockAndMetadata(world, -5, 1, -6, Blocks.cauldron, 3);
		GOTEntityMossovyBlacksmith blacksmith = new GOTEntityMossovyBlacksmith(world);
		spawnNPCAndSetHome(blacksmith, world, -4, 1, -4, 8);
		for (k12 = 5; k12 <= 9; ++k12) {
			for (i12 = -9; i12 <= -5; ++i12) {
				setBlockAndMetadata(world, i12, 3, k12, plankSlabBlock, plankSlabMeta);
			}
		}
		setBlockAndMetadata(world, -9, 3, 9, plankBlock, plankMeta);
		for (j13 = 1; j13 <= 2; ++j13) {
			setBlockAndMetadata(world, -9, j13, 9, plankBlock, plankMeta);
			setBlockAndMetadata(world, -5, j13, 5, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -5, 1, 9, plankBlock, plankMeta);
		setBlockAndMetadata(world, -5, 2, 9, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -6, 1, 9, plankBlock, plankMeta);
		setBlockAndMetadata(world, -6, 2, 9, Blocks.torch, 4);
		placeChest(world, random, -7, 1, 9, 2, GOTChestContents.MOSSOVY);
		placeChest(world, random, -8, 1, 9, 2, GOTChestContents.MOSSOVY);
		setBlockAndMetadata(world, -9, 1, 8, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, -9, 1, 7, tableBlock, 0);
		setBlockAndMetadata(world, -9, 1, 6, plankBlock, plankMeta);
		setBlockAndMetadata(world, -9, 2, 6, Blocks.torch, 2);
		setBlockAndMetadata(world, -9, 1, 5, plankBlock, plankMeta);
		setBlockAndMetadata(world, -9, 2, 5, fenceBlock, fenceMeta);
		for (k12 = 5; k12 <= 10; ++k12) {
			for (i12 = 5; i12 <= 10; ++i12) {
				setBlockAndMetadata(world, i12, 0, k12, plankBlock, plankMeta);
				setAir(world, i12, 1, k12);
				setAir(world, i12, 2, k12);
				setBlockAndMetadata(world, i12, 3, k12, plankBlock, plankMeta);
			}
		}
		for (k12 = 4; k12 <= 9; ++k12) {
			setBlockAndMetadata(world, 4, 3, k12, plankStairBlock, 1);
		}
		for (i1 = 5; i1 <= 9; ++i1) {
			setBlockAndMetadata(world, i1, 3, 4, plankStairBlock, 2);
		}
		for (k12 = 5; k12 <= 10; ++k12) {
			setBlockAndMetadata(world, 5, 1, k12, plankBlock, plankMeta);
			setBlockAndMetadata(world, 5, 2, k12, plankBlock, plankMeta);
		}
		for (i1 = 6; i1 <= 10; ++i1) {
			setBlockAndMetadata(world, i1, 1, 5, plankBlock, plankMeta);
			setBlockAndMetadata(world, i1, 2, 5, plankBlock, plankMeta);
		}
		setBlockAndMetadata(world, 5, 0, 8, plankBlock, plankMeta);
		setBlockAndMetadata(world, 5, 1, 8, doorBlock, 2);
		setBlockAndMetadata(world, 5, 2, 8, doorBlock, 8);
		setBlockAndMetadata(world, 5, 1, 5, woodBeamBlock, woodBeamMeta);
		setBlockAndMetadata(world, 5, 2, 5, woodBeamBlock, woodBeamMeta);
		for (i1 = 6; i1 <= 10; ++i1) {
			if (IntMath.mod(i1, 2) == 0) {
				setBlockAndMetadata(world, i1, 2, 6, Blocks.torch, 3);
				setBlockAndMetadata(world, i1, 2, 10, Blocks.torch, 4);
			}
			for (k13 = 6; k13 <= 10; ++k13) {
				if (!random.nextBoolean()) {
					continue;
				}
				setBlockAndMetadata(world, i1, 1, k13, GOTBlocks.thatchFloor, 0);
			}
		}
		for (int k16 : new int[] { 6, 10 }) {
			setBlockAndMetadata(world, 7, 1, k16, bedBlock, 3);
			setBlockAndMetadata(world, 6, 1, k16, bedBlock, 11);
			setBlockAndMetadata(world, 9, 1, k16, bedBlock, 1);
			setBlockAndMetadata(world, 10, 1, k16, bedBlock, 9);
		}
		placeChest(world, random, 8, 1, 6, 3, GOTChestContents.MOSSOVY);
		placeChest(world, random, 8, 1, 10, 2, GOTChestContents.MOSSOVY);
		setBlockAndMetadata(world, 10, 1, 8, plankBlock, plankMeta);
		placeBarrel(world, random, 10, 2, 8, 5, GOTFoods.WESTEROS_DRINK);
		for (int j18 = 1; j18 <= 4; ++j18) {
			setBlockAndMetadata(world, 6, j18, -9, woodBeamBlock, woodBeamMeta);
			setBlockAndMetadata(world, 7, j18, -9, plankBlock, plankMeta);
			setBlockAndMetadata(world, 8, j18, -9, plankBlock, plankMeta);
			setBlockAndMetadata(world, 9, j18, -9, woodBeamBlock, woodBeamMeta);
		}
		for (k1 = -8; k1 <= -7; ++k1) {
			for (i12 = 6; i12 <= 9; ++i12) {
				int stairHeight = i12 - 5;
				for (int j19 = 0; j19 < stairHeight; ++j19) {
					setBlockAndMetadata(world, i12, j19, k1, plankBlock, plankMeta);
				}
				setBlockAndMetadata(world, i12, stairHeight, k1, plankStairBlock, 1);
			}
			setAir(world, 9, 5, k1);
		}
		placeWallBanner(world, -10, 3, 0, GOTItemBanner.BannerType.MOSSOVY, 1);
		placeWallBanner(world, 10, 3, 0, GOTItemBanner.BannerType.MOSSOVY, 3);
		for (i1 = -1; i1 <= 1; ++i1) {
			setBlockAndMetadata(world, i1, 0, 10, brickBlock, brickMeta);
			for (j12 = 1; j12 <= 3; ++j12) {
				setAir(world, i1, j12, 10);
			}
		}
		setBlockAndMetadata(world, 0, 1, 9, GOTBlocks.commandTable, 0);
		placeWallBanner(world, 0, 3, 11, GOTItemBanner.BannerType.MOSSOVY, 2);
		for (int l = 0; l < 10; ++l) {
			spawnNPCAndSetHome(new GOTEntityMossovyWitcher(world), world, 0, 1, 0, 20);
		}
		GOTEntityNPCRespawner respawner = new GOTEntityNPCRespawner(world);
		respawner.setSpawnClass(GOTEntityMossovyWitcher.class);
		respawner.setCheckRanges(16, -8, 10, 12);
		respawner.setSpawnRanges(11, 1, 6, 20);
		placeNPCRespawner(respawner, world, 0, 0, 0);
		return true;
	}
}