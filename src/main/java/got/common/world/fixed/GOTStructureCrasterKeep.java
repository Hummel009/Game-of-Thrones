package got.common.world.fixed;

import java.util.*;

import com.google.common.math.IntMath;

import got.common.database.*;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.westeros.GOTEntityCrasterWife;
import got.common.entity.westeros.legendary.trader.GOTEntityCraster;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.north.GOTStructureNorthBase;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureCrasterKeep extends GOTVillageGen {
	public GOTStructureCrasterKeep(GOTBiome biome, float f) {
		super(biome);
		gridScale = 10;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 3;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class Barn extends GOTStructureNorthBase {
		public Barn(boolean flag) {
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
			for (int l = 0; l < 11; ++l) {
				spawnNPCAndSetHome(new GOTEntityCrasterWife(world), world, 0, 1, 5, 16);
			}
			GOTEntityNPCRespawner wifeSpawner = new GOTEntityNPCRespawner(world);
			wifeSpawner.setSpawnClass(GOTEntityCrasterWife.class);
			wifeSpawner.setCheckRanges(12, -8, 16, 10);
			wifeSpawner.setSpawnRanges(4, -4, 4, 24);
			placeNPCRespawner(wifeSpawner, world, 0, 1, 5);
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

	public static class Home extends GOTStructureNorthBase {
		public Home(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			int j1;
			int k16;
			int k12;
			int k13;
			int i1;
			int step;
			int roofEdge;
			int i12;
			int j12;
			int i13;
			int k14;
			int j13;
			this.setOriginAndRotation(world, i, j, k, rotation, 1);
			setupRandomBlocks(random);
			if (restrictions) {
				int minHeight = 0;
				int maxHeight = 0;
				for (int i14 = -6; i14 <= 5; ++i14) {
					for (k16 = -10; k16 <= 10; ++k16) {
						int j14 = getTopBlock(world, i14, k16) - 1;
						if (!isSurface(world, i14, j14, k16)) {
							return false;
						}
						if (j14 < minHeight) {
							minHeight = j14;
						}
						if (j14 > maxHeight) {
							maxHeight = j14;
						}
						if (maxHeight - minHeight <= 5) {
							continue;
						}
						return false;
					}
				}
			}

			for (i12 = -3; i12 <= 2; ++i12) {
				for (k14 = -5; k14 <= 4; ++k14) {
					if (k14 < -4 && (i12 < -1 || i12 > 0)) {
						continue;
					}
					for (j13 = 0; (j13 >= 0 || !isOpaque(world, i12, j13, k14)) && getY(j13) >= 0; --j13) {
						setBlockAndMetadata(world, i12, j13, k14, plankBlock, plankMeta);
						setGrassToDirt(world, i12, j13 - 1, k14);
					}
				}
			}

			for (i12 = -5; i12 <= 4; ++i12) {
				for (k14 = -7; k14 <= 7; ++k14) {
					for (j13 = 1; j13 <= 8; ++j13) {
						setAir(world, i12, j13, k14);
					}
				}
			}
			for (i12 = -3; i12 <= 2; ++i12) {
				for (k14 = -4; k14 <= 4; ++k14) {
					if (!random.nextBoolean()) {
						continue;
					}
					setBlockAndMetadata(world, i12, 1, k14, GOTRegistry.thatchFloor, 0);
				}
			}
			for (i12 = -4; i12 <= 3; ++i12) {
				for (k14 = -7; k14 <= 5; ++k14) {
					boolean beam = false;
					if (k14 == -7 && (i12 == -4 || i12 == -2 || i12 == 1 || i12 == 3)) {
						beam = true;
					} else if (Math.abs(k14) == 5 && (i12 == -4 || i12 == 3)) {
						beam = true;
					}
					if (beam) {
						for (j12 = 3; (j12 >= 1 || !isOpaque(world, i12, j12, k14)) && getY(j12) >= 0; --j12) {
							setBlockAndMetadata(world, i12, j12, k14, woodBeamBlock, woodBeamMeta);
							setGrassToDirt(world, i12, j12 - 1, k14);
						}
						continue;
					}
					if (k14 < -5) {
						continue;
					}
					if (i12 == -4 || i12 == 3) {
						setBlockAndMetadata(world, i12, 1, k14, plankBlock, plankMeta);
						setGrassToDirt(world, i12, 0, k14);
						for (j12 = 2; j12 <= 3; ++j12) {
							setBlockAndMetadata(world, i12, j12, k14, plankBlock, plankMeta);
						}
						continue;
					}
					if (Math.abs(k14) != 5) {
						continue;
					}
					setBlockAndMetadata(world, i12, 1, k14, plankBlock, plankMeta);
					setGrassToDirt(world, i12, 0, k14);
					for (j12 = 2; j12 <= 3; ++j12) {
						setBlockAndMetadata(world, i12, j12, k14, plankBlock, plankMeta);
					}
					setBlockAndMetadata(world, i12, 4, k14, woodBeamBlock, woodBeamMeta | 4);
				}
			}
			for (int k15 = -7; k15 <= 6; ++k15) {
				roofEdge = k15 == -7 || k15 == 6 ? 1 : 0;
				for (step = 0; step <= 4; ++step) {
					j12 = 3 + step;
					Block stairBlock = roofStairBlock;
					if (step == 4 || roofEdge != 0) {
						stairBlock = plankStairBlock;
					}
					setBlockAndMetadata(world, -5 + step, j12, k15, stairBlock, 1);
					setBlockAndMetadata(world, 4 - step, j12, k15, stairBlock, 0);
					if (roofEdge != 0 && step <= 3) {
						setBlockAndMetadata(world, -4 + step, j12, k15, stairBlock, 4);
						setBlockAndMetadata(world, 3 - step, j12, k15, stairBlock, 5);
					}
					if (k15 < -4 || k15 > 4 || step < 1 || step > 3) {
						continue;
					}
					setBlockAndMetadata(world, -4 + step, j12, k15, stairBlock, 4);
					setBlockAndMetadata(world, 3 - step, j12, k15, stairBlock, 5);
				}
			}
			for (int k161 : new int[] { -6, -5, 5 }) {
				setBlockAndMetadata(world, -2, 5, k161, plankBlock, plankMeta);
				setBlockAndMetadata(world, -1, 5, k161, plankStairBlock, 4);
				setBlockAndMetadata(world, 0, 5, k161, plankStairBlock, 5);
				setBlockAndMetadata(world, 1, 5, k161, plankBlock, plankMeta);
				setBlockAndMetadata(world, -1, 6, k161, plankBlock, plankMeta);
				setBlockAndMetadata(world, 0, 6, k161, plankBlock, plankMeta);
			}
			int[] k15 = { -7, 6 };
			roofEdge = k15.length;
			for (step = 0; step < roofEdge; ++step) {
				k16 = k15[step];
				setBlockAndMetadata(world, -1, 8, k16, plankStairBlock, 0);
				setBlockAndMetadata(world, 0, 8, k16, plankStairBlock, 1);
			}
			for (i1 = -4; i1 <= 3; ++i1) {
				if (i1 == -4 || i1 == -2 || i1 == 1 || i1 == 3) {
					setBlockAndMetadata(world, i1, 3, -7, plankBlock, plankMeta);
				} else {
					setBlockAndMetadata(world, i1, 3, -7, plankSlabBlock, plankSlabMeta | 8);
				}
				if (i1 < -3 || i1 > 2) {
					continue;
				}
				setBlockAndMetadata(world, i1, 3, 6, plankSlabBlock, plankSlabMeta | 8);
			}
			for (i1 = -3; i1 <= 2; ++i1) {
				setBlockAndMetadata(world, i1, 4, -6, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, -4, 3, -6, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 3, 3, -6, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, -1, 4, -6, rockSlabBlock, rockSlabMeta);
			setBlockAndMetadata(world, 0, 4, -6, rockSlabBlock, rockSlabMeta);
			setBlockAndMetadata(world, -2, 4, -7, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 1, 4, -7, fenceBlock, fenceMeta);
			for (i1 = -1; i1 <= 0; ++i1) {
				for (int j16 = 1; j16 <= 2; ++j16) {
					setAir(world, i1, j16, -5);
				}
			}
			setBlockAndMetadata(world, -1, 3, -5, plankStairBlock, 4);
			setBlockAndMetadata(world, 0, 3, -5, plankStairBlock, 5);
			for (int i15 : new int[] { -5, 4 }) {
				for (int k17 : new int[] { -7, 6 }) {
					for (j1 = 2; (j1 >= 1 || !isOpaque(world, i15, j1, k17)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i15, j1, k17, fenceBlock, fenceMeta);
					}
				}
			}
			for (int i15 : new int[] { -4, 3 }) {
				setAir(world, i15, 2, -2);
				setBlockAndMetadata(world, i15, 3, -2, plankSlabBlock, plankSlabMeta | 8);
				setBlockAndMetadata(world, i15, 4, -2, roofBlock, roofMeta);
				setBlockAndMetadata(world, i15, 2, -3, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, i15, 2, -1, fenceBlock, fenceMeta);
				setBlockAndMetadata(world, i15, 3, -3, plankStairBlock, 7);
				setBlockAndMetadata(world, i15, 3, -1, plankStairBlock, 6);
			}
			for (int i15 : new int[] { -5, 4 }) {
				setBlockAndMetadata(world, i15, 1, -3, plankStairBlock, 7);
				setBlockAndMetadata(world, i15, 1, -2, plankSlabBlock, plankSlabMeta | 8);
				setBlockAndMetadata(world, i15, 1, -1, plankStairBlock, 6);
				for (int k18 = -3; k18 <= -1; ++k18) {
					if (!random.nextBoolean()) {
						continue;
					}
					placeFlowerPot(world, i15, 2, k18, getRandomFlower(world, random));
				}
				setBlockAndMetadata(world, i15, 3, -4, roofBlock, roofMeta);
				setBlockAndMetadata(world, i15, 3, -3, roofSlabBlock, roofSlabMeta | 8);
				setBlockAndMetadata(world, i15, 4, -3, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i15, 4, -2, roofBlock, roofMeta);
				setAir(world, i15, 3, -2);
				setBlockAndMetadata(world, i15, 3, -1, roofSlabBlock, roofSlabMeta | 8);
				setBlockAndMetadata(world, i15, 4, -1, roofSlabBlock, roofSlabMeta);
				setBlockAndMetadata(world, i15, 3, 0, roofBlock, roofMeta);
				for (int k17 : new int[] { -4, 0 }) {
					for (j1 = 2; (j1 >= 1 || !isOpaque(world, i15, j1, k17)) && getY(j1) >= 0; --j1) {
						setBlockAndMetadata(world, i15, j1, k17, fenceBlock, fenceMeta);
					}
				}
			}
			setBlockAndMetadata(world, -4, 2, 3, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, -2, 2, 5, fenceBlock, fenceMeta);
			setBlockAndMetadata(world, 1, 2, 5, fenceBlock, fenceMeta);
			for (k12 = 1; k12 <= 3; ++k12) {
				for (i13 = 2; i13 <= 3; ++i13) {
					for (j13 = 5; (j13 >= 0 || !isOpaque(world, i13, j13, k12)) && getY(j13) >= 0; --j13) {
						setBlockAndMetadata(world, i13, j13, k12, brickBlock, brickMeta);
					}
				}
			}
			setBlockAndMetadata(world, 3, 5, 1, brickStairBlock, 2);
			setBlockAndMetadata(world, 3, 5, 3, brickStairBlock, 3);
			setBlockAndMetadata(world, 2, 6, 1, brickStairBlock, 2);
			setBlockAndMetadata(world, 2, 6, 3, brickStairBlock, 3);
			setBlockAndMetadata(world, 3, 6, 2, brickStairBlock, 0);
			setBlockAndMetadata(world, 1, 6, 2, brickBlock, brickMeta);
			for (int j17 = 6; j17 <= 8; ++j17) {
				setBlockAndMetadata(world, 2, j17, 2, brickBlock, brickMeta);
			}
			setBlockAndMetadata(world, 2, 9, 2, rockSlabBlock, rockSlabMeta);
			for (k12 = 0; k12 <= 4; ++k12) {
				setBlockAndMetadata(world, 2, 4, k12, brickBlock, brickMeta);
				for (int step2 = 0; step2 <= 1; ++step2) {
					setBlockAndMetadata(world, 1 - step2, 5 + step2, k12, brickStairBlock, 5);
				}
			}
			for (int k161 : new int[] { 0, 4 }) {
				for (int j18 = 1; j18 <= 3; ++j18) {
					setBlockAndMetadata(world, 2, j18, k161, rockWallBlock, rockWallMeta);
				}
			}
			setBlockAndMetadata(world, 2, 0, 2, GOTRegistry.hearth, 0);
			setBlockAndMetadata(world, 2, 1, 2, Blocks.fire, 0);
			setBlockAndMetadata(world, 2, 2, 2, Blocks.furnace, 5);
			setBlockAndMetadata(world, 2, 3, 2, brickBlock, brickMeta);
			setBlockAndMetadata(world, 1, 0, 2, rockSlabDoubleBlock, rockSlabDoubleMeta);
			setBlockAndMetadata(world, 1, 1, 1, brickBlock, brickMeta);
			setBlockAndMetadata(world, 1, 1, 2, barsBlock, 0);
			setBlockAndMetadata(world, 1, 1, 3, brickBlock, brickMeta);
			for (k13 = 1; k13 <= 3; ++k13) {
				setBlockAndMetadata(world, 1, 2, k13, rockSlabBlock, rockSlabMeta);
			}
			for (int i16 = -2; i16 <= 1; ++i16) {
				setBlockAndMetadata(world, i16, 5, -4, plankSlabBlock, plankSlabMeta);
			}
			setBlockAndMetadata(world, -3, 1, -4, plankStairBlock, 3);
			setBlockAndMetadata(world, -3, 1, -3, plankStairBlock, 2);
			setBlockAndMetadata(world, -3, 1, -2, Blocks.crafting_table, 0);
			setBlockAndMetadata(world, -3, 1, -1, GOTRegistry.tableWildling, 0);
			this.placeChest(world, random, -3, 1, 0, 4, GOTChestContents.WESTEROS);
			setBlockAndMetadata(world, 2, 1, -4, plankStairBlock, 7);
			setBlockAndMetadata(world, 2, 1, -3, plankSlabBlock, plankSlabMeta | 8);
			setBlockAndMetadata(world, 2, 1, -2, plankStairBlock, 6);
			setBlockAndMetadata(world, 2, 1, -1, Blocks.cauldron, 3);
			this.placeBarrel(world, random, 2, 2, -4, 5, GOTFoods.WESTEROS_DRINK);
			this.placeMug(world, random, 2, 2, -3, 1, GOTFoods.WESTEROS_DRINK);
			if (random.nextBoolean()) {
				placePlateWithCertainty(world, random, 2, 2, -2, plateBlock, GOTFoods.WILD);
			} else {
				setBlockAndMetadata(world, 2, 2, -2, plateBlock, 0);
			}
			for (k13 = 2; k13 <= 3; ++k13) {
				setBlockAndMetadata(world, -2, 1, k13, bedBlock, 3);
				setBlockAndMetadata(world, -3, 1, k13, bedBlock, 11);
				setBlockAndMetadata(world, -3, 3, k13, plankSlabBlock, plankSlabMeta | 8);
			}
			for (int k161 : new int[] { 1, 4 }) {
				for (int j19 = 1; j19 <= 2; ++j19) {
					setBlockAndMetadata(world, -3, j19, k161, fenceBlock, fenceMeta);
				}
				setBlockAndMetadata(world, -3, 3, k161, plankBlock, plankMeta);
			}
			setBlockAndMetadata(world, -3, 3, -4, Blocks.torch, 2);
			setBlockAndMetadata(world, 2, 3, -4, Blocks.torch, 1);
			setBlockAndMetadata(world, -2, 4, 4, Blocks.torch, 4);
			setBlockAndMetadata(world, 1, 4, 4, Blocks.torch, 4);
			if (random.nextInt(3) != 0) {
				boolean hayOrWood = random.nextBoolean();
				for (i13 = -1; i13 <= 1; ++i13) {
					for (int k19 = 6; k19 <= 7; ++k19) {
						if (k19 != 6 && i13 != 0) {
							continue;
						}
						j12 = 1;
						while (!isOpaque(world, i13, j12 - 1, k19) && getY(j12) >= 0) {
							--j12;
						}
						int j2 = j12;
						if (i13 == 0 && k19 == 6) {
							++j2;
						}
						for (int j3 = j12; j3 <= j2; ++j3) {
							if (hayOrWood) {
								setBlockAndMetadata(world, i13, j3, k19, Blocks.hay_block, 0);
								continue;
							}
							setBlockAndMetadata(world, i13, j3, k19, woodBeamBlock, woodBeamMeta | 8);
						}
						setGrassToDirt(world, i13, j12 - 1, k19);
					}
				}
			}
			if (random.nextBoolean()) {
				int i15;
				int j110 = 2;
				k14 = 6;
				ArrayList<Integer> chestCoords = new ArrayList<>();
				for (i15 = -4; i15 <= 3; ++i15) {
					if (isOpaque(world, i15, j110, k14)) {
						continue;
					}
					chestCoords.add(i15);
				}
				if (!chestCoords.isEmpty()) {
					i15 = chestCoords.get(random.nextInt(chestCoords.size()));
					while (!isOpaque(world, i15, j110 - 1, k14) && getY(j110) >= 0) {
						--j110;
					}
					this.placeChest(world, random, i15, j110, k14, 3, GOTChestContents.WESTEROS);
				}
			}
			for (int i17 = -1; i17 <= 0; ++i17) {
				for (int j14 = 1; j14 <= 3; ++j14) {
					setBlockAndMetadata(world, i17, j14, -5, GOTRegistry.gateWooden, 2);
				}
			}
			spawnNPCAndSetHome(new GOTEntityCraster(world), world, 0, 1, 0, 16);
			return true;
		}
	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureCrasterKeep village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new Home(false), -7, 0, 2, true);
			this.addStructure(new Barn(false), 7, 6, 2, true);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupVillageProperties(Random random) {
		}
	}

}
