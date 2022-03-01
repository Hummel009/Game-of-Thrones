package got.common.world.structure.westeros.wildling;

import java.util.Random;

import com.google.common.math.IntMath;

import got.common.database.*;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.westeros.legendary.GOTEntityCrasterWife;
import got.common.world.structure.westeros.common.GOTStructureWesterosBase;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureWildlingBarn extends GOTStructureWesterosBase {
	public GOTStructureWildlingBarn(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int beam;
		int i18;
		int k1;
		int j1;
		int i12;
		int i2;
		int i13;
		int j12;
		int i14;
		int i15;
		int k122;
		int k2;
		int i22;
		this.setOriginAndRotation(world, i, j, k, rotation, 1);
		setupRandomBlocks(random);
		if (restrictions) {
			int minHeight = 0;
			int maxHeight = 0;
			for (i12 = -7; i12 <= 7; ++i12) {
				for (k122 = -1; k122 <= 16; ++k122) {
					j12 = getTopBlock(world, i12, k122) - 1;
					if (!isSurface(world, i12, j12, k122)) {
						return false;
					}
					if (j12 < minHeight) {
						minHeight = j12;
					}
					if (j12 > maxHeight) {
						maxHeight = j12;
					}
					if (maxHeight - minHeight <= 6) {
						continue;
					}
					return false;
				}
			}
		}
		for (i14 = -5; i14 <= 5; ++i14) {
			for (int k13 = 0; k13 <= 15; ++k13) {
				int i23 = Math.abs(i14);
				int k22 = IntMath.mod(k13, 3);
				for (j12 = 0; (j12 >= 0 || !isOpaque(world, i14, j12, k13)) && getY(j12) >= 0; --j12) {
					setBlockAndMetadata(world, i14, j12, k13, brickBlock, brickMeta);
					setGrassToDirt(world, i14, j12 - 1, k13);
				}
				for (j12 = 1; j12 <= 11; ++j12) {
					setAir(world, i14, j12, k13);
				}
				beam = 0;
				if (i23 == 5 && k22 == 0) {
					beam = 1;
				}
				if ((k13 == 0 || k13 == 15) && i23 == 2) {
					beam = 1;
				}
				if (beam != 0) {
					for (j1 = 1; j1 <= 5; ++j1) {
						setBlockAndMetadata(world, i14, j1, k13, woodBeamBlock, woodBeamMeta);
					}
					if (k13 == 0 || k13 == 15) {
						for (j1 = 6; j1 <= 7; ++j1) {
							setBlockAndMetadata(world, i14, j1, k13, woodBeamBlock, woodBeamMeta);
						}
					}
				} else if (i23 == 5 || k13 == 0 || k13 == 15) {
					setBlockAndMetadata(world, i14, 1, k13, plankBlock, plankMeta);
					for (j1 = 2; j1 <= 5; ++j1) {
						setBlockAndMetadata(world, i14, j1, k13, plankBlock, plankMeta);
					}
					if (k13 == 0 || k13 == 15) {
						for (j1 = 6; j1 <= 7; ++j1) {
							setBlockAndMetadata(world, i14, j1, k13, plankBlock, plankMeta);
						}
					}
					setBlockAndMetadata(world, i14, 5, k13, woodBeamBlock, woodBeamMeta | 4);
					setBlockAndMetadata(world, i14, 8, k13, woodBeamBlock, woodBeamMeta | 4);
				}
				if (i23 > 4 || k13 < 1 || k13 > 14) {
					continue;
				}
				if (k13 >= 3 && k13 <= 12) {
					setBlockAndMetadata(world, i14, 0, k13, Blocks.dirt, 1);
				}
				if (random.nextBoolean()) {
					setBlockAndMetadata(world, i14, 1, k13, GOTRegistry.thatchFloor, 0);
				}
				if (i23 < 2 && k13 > 3) {
					continue;
				}
				setBlockAndMetadata(world, i14, 5, k13, plankBlock, plankMeta);
				if (!random.nextBoolean()) {
					continue;
				}
				setBlockAndMetadata(world, i14, 6, k13, GOTRegistry.thatchFloor, 0);
			}
		}
		for (i14 = -5; i14 <= 5; ++i14) {
			int j13;
			i22 = Math.abs(i14);
			if (i22 == 2 || i22 == 5) {
				for (int k14 = -1; k14 <= 16; ++k14) {
					setBlockAndMetadata(world, i14, 5, k14, woodBeamBlock, woodBeamMeta | 8);
					setBlockAndMetadata(world, i14, 8, k14, woodBeamBlock, woodBeamMeta | 8);
					if (k14 != -1 && k14 != 16) {
						continue;
					}
					setBlockAndMetadata(world, i14, 1, k14, woodBeamBlock, woodBeamMeta | 8);
					setGrassToDirt(world, i14, 0, k14);
					for (j13 = 2; j13 <= 4; ++j13) {
						setBlockAndMetadata(world, i14, j13, k14, fenceBlock, fenceMeta);
					}
					for (j13 = 6; j13 <= 7; ++j13) {
						setBlockAndMetadata(world, i14, j13, k14, fenceBlock, fenceMeta);
					}
				}
				continue;
			}
			for (int k15 : new int[] { 0, 15 }) {
				setBlockAndMetadata(world, i14, 3, k15, plankSlabBlock, plankSlabMeta);
				if (i14 == -4 || i14 == 3) {
					setBlockAndMetadata(world, i14, 4, k15, plankStairBlock, 4);
				} else if (i14 == -3 || i14 == 4) {
					setBlockAndMetadata(world, i14, 4, k15, plankStairBlock, 5);
				}
				switch (i14) {
				case -1:
					setBlockAndMetadata(world, i14, 4, k15, plankStairBlock, 4);
					break;
				case 1:
					setBlockAndMetadata(world, i14, 4, k15, plankStairBlock, 5);
					break;
				case 0:
					setBlockAndMetadata(world, i14, 4, k15, plankSlabBlock, plankSlabMeta | 8);
					break;
				default:
					break;
				}
				setBlockAndMetadata(world, i14, 7, k15, fenceBlock, fenceMeta);
			}
			int[] k14 = { -1, 16 };
			j13 = k14.length;
			for (beam = 0; beam < j13; ++beam) {
				int k15;
				k15 = k14[beam];
				if (i22 >= 3 || k15 != -1) {
					setBlockAndMetadata(world, i14, 1, k15, plankSlabBlock, plankSlabMeta | 8);
				}
				setBlockAndMetadata(world, i14, 5, k15, plankSlabBlock, plankSlabMeta | 8);
				setBlockAndMetadata(world, i14, 8, k15, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		for (int k16 = 0; k16 <= 15; ++k16) {
			k2 = IntMath.mod(k16, 3);
			if (k2 == 0) {
				for (i12 = -7; i12 <= 7; ++i12) {
					i2 = Math.abs(i12);
					if (i2 == 6) {
						setBlockAndMetadata(world, i12, 1, k16, woodBeamBlock, woodBeamMeta | 4);
						setGrassToDirt(world, i12, 0, k16);
						for (j12 = 2; j12 <= 4; ++j12) {
							setBlockAndMetadata(world, i12, j12, k16, fenceBlock, fenceMeta);
						}
					}
					if (i2 < 6) {
						continue;
					}
					setBlockAndMetadata(world, i12, 5, k16, woodBeamBlock, woodBeamMeta | 4);
				}
				continue;
			}
			for (int i16 : new int[] { -6, 6 }) {
				setBlockAndMetadata(world, i16, 1, k16, plankSlabBlock, plankSlabMeta | 8);
			}
			setBlockAndMetadata(world, -7, 5, k16, plankStairBlock, 1);
			setBlockAndMetadata(world, -6, 5, k16, plankStairBlock, 4);
			setBlockAndMetadata(world, 6, 5, k16, plankStairBlock, 5);
			setBlockAndMetadata(world, 7, 5, k16, plankStairBlock, 0);
			if (k16 < 3) {
				continue;
			}
			for (int i16 : new int[] { -5, 5 }) {
				setBlockAndMetadata(world, i16, 3, k16, plankSlabBlock, plankSlabMeta);
				if (k2 == 1) {
					setBlockAndMetadata(world, i16, 4, k16, plankStairBlock, 7);
					continue;
				}
				if (k2 != 2) {
					continue;
				}
				setBlockAndMetadata(world, i16, 4, k16, plankStairBlock, 6);
			}
		}
		int[] k16 = { -1, 16 };
		k2 = k16.length;
		for (i12 = 0; i12 < k2; ++i12) {
			k122 = k16[i12];
			setBlockAndMetadata(world, -7, 5, k122, plankStairBlock, 1);
			setBlockAndMetadata(world, -6, 5, k122, plankBlock, plankMeta);
			setBlockAndMetadata(world, 6, 5, k122, plankBlock, plankMeta);
			setBlockAndMetadata(world, 7, 5, k122, plankStairBlock, 0);
		}
		for (int i17 = -1; i17 <= 1; ++i17) {
			for (int j14 = 1; j14 <= 4; ++j14) {
				setBlockAndMetadata(world, i17, j14, 0, GOTRegistry.gateIronBars, 2);
			}
		}
		setBlockAndMetadata(world, 0, 3, 0, GOTRegistry.gateIronBars, 2);
		for (int k17 = 1; k17 <= 14; ++k17) {
			if (IntMath.mod(k17, 3) == 0) {
				setBlockAndMetadata(world, -6, 6, k17, plankBlock, plankMeta);
				setBlockAndMetadata(world, -6, 7, k17, plankBlock, plankMeta);
				setBlockAndMetadata(world, -6, 8, k17, plankStairBlock, 1);
				setBlockAndMetadata(world, -5, 9, k17, plankStairBlock, 1);
				setBlockAndMetadata(world, -4, 9, k17, plankSlabBlock, plankSlabMeta | 8);
				setBlockAndMetadata(world, -3, 10, k17, plankSlabBlock, plankSlabMeta);
				for (i15 = -2; i15 <= 2; ++i15) {
					setBlockAndMetadata(world, i15, 10, k17, plankSlabBlock, plankSlabMeta | 8);
				}
				setBlockAndMetadata(world, 3, 10, k17, plankSlabBlock, plankSlabMeta);
				setBlockAndMetadata(world, 4, 9, k17, plankSlabBlock, plankSlabMeta | 8);
				setBlockAndMetadata(world, 5, 9, k17, plankStairBlock, 0);
				setBlockAndMetadata(world, 6, 8, k17, plankStairBlock, 0);
				setBlockAndMetadata(world, 6, 6, k17, plankBlock, plankMeta);
				setBlockAndMetadata(world, 6, 7, k17, plankBlock, plankMeta);
				continue;
			}
			setBlockAndMetadata(world, -6, 6, k17, roofBlock, roofMeta);
			setBlockAndMetadata(world, -6, 7, k17, roofBlock, roofMeta);
			setBlockAndMetadata(world, -6, 8, k17, roofStairBlock, 1);
			setBlockAndMetadata(world, -5, 9, k17, roofStairBlock, 1);
			setBlockAndMetadata(world, -4, 9, k17, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, -3, 10, k17, roofSlabBlock, roofSlabMeta);
			for (i15 = -2; i15 <= 2; ++i15) {
				setBlockAndMetadata(world, i15, 10, k17, roofSlabBlock, roofSlabMeta | 8);
			}
			setBlockAndMetadata(world, 3, 10, k17, roofSlabBlock, roofSlabMeta);
			setBlockAndMetadata(world, 4, 9, k17, roofSlabBlock, roofSlabMeta | 8);
			setBlockAndMetadata(world, 5, 9, k17, roofStairBlock, 0);
			setBlockAndMetadata(world, 6, 8, k17, roofStairBlock, 0);
			setBlockAndMetadata(world, 6, 6, k17, roofBlock, roofMeta);
			setBlockAndMetadata(world, 6, 7, k17, roofBlock, roofMeta);
		}
		for (int k1221 : new int[] { 0, 15 }) {
			setBlockAndMetadata(world, -6, 6, k1221, plankBlock, plankMeta);
			setBlockAndMetadata(world, -6, 7, k1221, plankBlock, plankMeta);
			setBlockAndMetadata(world, -6, 8, k1221, plankStairBlock, 1);
			setBlockAndMetadata(world, -5, 9, k1221, plankStairBlock, 1);
			setBlockAndMetadata(world, -4, 9, k1221, plankBlock, plankMeta);
			setBlockAndMetadata(world, -3, 9, k1221, plankBlock, plankMeta);
			setBlockAndMetadata(world, -3, 10, k1221, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, -2, 9, k1221, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, -2, 10, k1221, plankBlock, plankMeta);
			for (i18 = -1; i18 <= 1; ++i18) {
				setBlockAndMetadata(world, i18, 10, k1221, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, 2, 9, k1221, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 2, 10, k1221, plankBlock, plankMeta);
			setBlockAndMetadata(world, 3, 9, k1221, plankBlock, plankMeta);
			setBlockAndMetadata(world, 3, 10, k1221, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, 4, 9, k1221, plankBlock, plankMeta);
			setBlockAndMetadata(world, 5, 9, k1221, plankStairBlock, 0);
			setBlockAndMetadata(world, 6, 8, k1221, plankStairBlock, 0);
			setBlockAndMetadata(world, 6, 6, k1221, plankBlock, plankMeta);
			setBlockAndMetadata(world, 6, 7, k1221, plankBlock, plankMeta);
		}
		int[] k17 = { -1, 16 };
		i15 = k17.length;
		for (i12 = 0; i12 < i15; ++i12) {
			k122 = k17[i12];
			setBlockAndMetadata(world, -6, 8, k122, plankStairBlock, 1);
			setBlockAndMetadata(world, -5, 9, k122, plankStairBlock, 1);
			setBlockAndMetadata(world, -4, 9, k122, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, -3, 9, k122, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, -3, 10, k122, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, -2, 10, k122, plankBlock, plankMeta);
			setBlockAndMetadata(world, -1, 10, k122, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, -1, 11, k122, plankStairBlock, 5);
			setBlockAndMetadata(world, 0, 11, k122, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, 1, 10, k122, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 1, 11, k122, plankStairBlock, 4);
			setBlockAndMetadata(world, 2, 10, k122, plankBlock, plankMeta);
			setBlockAndMetadata(world, 3, 9, k122, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 3, 10, k122, plankSlabBlock, plankSlabMeta);
			setBlockAndMetadata(world, 4, 9, k122, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 5, 9, k122, plankStairBlock, 0);
			setBlockAndMetadata(world, 6, 8, k122, plankStairBlock, 0);
		}
		for (k1 = 0; k1 <= 15; ++k1) {
			setBlockAndMetadata(world, 0, 11, k1, plankSlabBlock, plankSlabMeta);
		}
		setBlockAndMetadata(world, -4, 1, 1, Blocks.hay_block, 0);
		setBlockAndMetadata(world, -3, 1, 1, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 3, 1, 1, Blocks.hay_block, 0);
		setBlockAndMetadata(world, 4, 1, 1, Blocks.hay_block, 0);
		for (int j15 = 1; j15 <= 7; ++j15) {
			if (j15 >= 6) {
				setBlockAndMetadata(world, -5, j15, 2, plankBlock, plankMeta);
				setBlockAndMetadata(world, 5, j15, 2, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, -4, j15, 2, Blocks.ladder, 4);
			setBlockAndMetadata(world, 4, j15, 2, Blocks.ladder, 5);
		}
		for (k1 = 3; k1 <= 12; ++k1) {
			k2 = IntMath.mod(k1, 3);
			for (i12 = -4; i12 <= 4; ++i12) {
				i2 = Math.abs(i12);
				if (k2 == 0) {
					if (i2 >= 2) {
						setBlockAndMetadata(world, i12, 1, k1, fenceBlock, fenceMeta);
						setBlockAndMetadata(world, i12, 2, k1, fenceBlock, fenceMeta);
					}
					if (i2 == 2) {
						setBlockAndMetadata(world, i12, 3, k1, fenceBlock, fenceMeta);
						setBlockAndMetadata(world, i12, 4, k1, fenceBlock, fenceMeta);
					}
				}
				if (k2 == 1) {
					if (i2 == 2) {
						setBlockAndMetadata(world, i12, 1, k1, fenceBlock, fenceMeta);
					}
					if (i2 == 4) {
						setBlockAndMetadata(world, i12, 1, k1, Blocks.hay_block, 0);
						setBlockAndMetadata(world, i12, 2, k1, fenceBlock, fenceMeta);
					}
				}
				if (k2 == 2) {
					if (i2 == 2) {
						setBlockAndMetadata(world, i12, 1, k1, fenceGateBlock, i12 > 0 ? 3 : 1);
					}
					if (i2 == 4) {
						setBlockAndMetadata(world, i12, 1, k1, Blocks.cauldron, 3);
						setBlockAndMetadata(world, i12, 2, k1, fenceBlock, fenceMeta);
					}
					if (i2 == 3) {
						EntityAnimal animal = getRandomAnimal(world, random);
						spawnNPCAndSetHome(animal, world, i12, 1, k1, 0);
						animal.detachHome();
					}
				}
				if (i2 != 4) {
					continue;
				}
				setBlockAndMetadata(world, i12, 3, k1, plankSlabBlock, plankSlabMeta);
			}
		}
		for (i13 = -1; i13 <= 1; ++i13) {
			int hayHeight = 1 + random.nextInt(2);
			for (int j16 = 1; j16 <= hayHeight; ++j16) {
				setBlockAndMetadata(world, i13, j16, 14, Blocks.hay_block, 0);
			}
		}
		this.placeChest(world, random, -4, 1, 13, 4, GOTChestContents.BEYOND_WALL);
		this.placeChest(world, random, -4, 1, 14, 4, GOTChestContents.BEYOND_WALL);
		setBlockAndMetadata(world, 4, 1, 13, Blocks.crafting_table, 0);
		setBlockAndMetadata(world, 4, 1, 14, GOTRegistry.tableWildling, 0);
		setBlockAndMetadata(world, -2, 3, 1, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 3, 1, Blocks.torch, 3);
		setBlockAndMetadata(world, -2, 3, 14, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 3, 14, Blocks.torch, 4);
		for (k1 = 3; k1 <= 14; ++k1) {
			setBlockAndMetadata(world, -2, 6, k1, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 2, 6, k1, fenceBlock, fenceMeta);
		}
		for (i13 = -1; i13 <= 1; ++i13) {
			setBlockAndMetadata(world, i13, 6, 3, fenceBlock, fenceMeta);
		}
		setBlockAndMetadata(world, -2, 6, 1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, 2, 6, 1, fenceBlock, fenceMeta);
		setBlockAndMetadata(world, -2, 7, 1, Blocks.torch, 3);
		setBlockAndMetadata(world, 2, 7, 1, Blocks.torch, 3);
		setBlockAndMetadata(world, -2, 7, 14, Blocks.torch, 4);
		setBlockAndMetadata(world, 2, 7, 14, Blocks.torch, 4);
		for (int k1221 : new int[] { 1, 14 }) {
			for (i18 = -4; i18 <= 4; ++i18) {
				int i24 = Math.abs(i18);
				if (i24 > 1 && i24 < 3) {
					continue;
				}
				setBlockAndMetadata(world, i18, 8, k1221, plankSlabBlock, plankSlabMeta | 8);
			}
		}
		for (int k18 = 1; k18 <= 14; ++k18) {
			if (k18 == 1 || IntMath.mod(k18, 3) == 0) {
				for (int i181 : new int[] { -5, 5 }) {
					setBlockAndMetadata(world, i181, 6, k18, fenceBlock, fenceMeta);
					setBlockAndMetadata(world, i181, 7, k18, fenceBlock, fenceMeta);
				}
				continue;
			}
			if (k18 == 2) {
				continue;
			}
			for (int i181 : new int[] { -5, 5 }) {
				j1 = 6;
				if (!random.nextBoolean()) {
					continue;
				}
				int j2 = j1;
				if (random.nextBoolean()) {
					++j2;
				}
				for (int j3 = j1; j3 <= j2; ++j3) {
					setBlockAndMetadata(world, i181, j3, k18, Blocks.hay_block, 0);
				}
				if (j2 < j1 + 1 || !random.nextBoolean()) {
					continue;
				}
				int i25 = (Math.abs(i181) - 1) * Integer.signum(i181);
				j2 = j1;
				if (random.nextBoolean()) {
					++j2;
				}
				for (int j3 = j1; j3 <= j2; ++j3) {
					setBlockAndMetadata(world, i25, j3, k18, Blocks.hay_block, 0);
				}
			}
		}
		for (int i19 = -4; i19 <= 4; ++i19) {
			i22 = Math.abs(i19);
			if (i22 == 2 || !random.nextBoolean()) {
				continue;
			}
			setBlockAndMetadata(world, i19, 6, 1, Blocks.hay_block, 0);
		}
		for (int l = 0; l <= 10; ++l) {
			GOTEntityHumanBase wife = new GOTEntityCrasterWife(world);
			if (random.nextBoolean()) {
				wife.familyInfo.setChild();
			}
			spawnNPCAndSetHome(wife, world, 0, 1, 5, 16);
		}
		return true;
	}

	public static EntityAnimal getRandomAnimal(World world, Random random) {
		int animal = random.nextInt(4);
		switch (animal) {
		case 0:
			return new EntityCow(world);
		case 1:
			return new EntityPig(world);
		case 2:
			return new EntitySheep(world);
		case 3:
			return new EntityChicken(world);
		default:
			break;
		}
		return null;
	}
}