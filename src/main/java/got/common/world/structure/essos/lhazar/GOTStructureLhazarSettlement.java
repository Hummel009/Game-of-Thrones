package got.common.world.structure.essos.lhazar;

import got.common.database.GOTBlocks;
import got.common.entity.essos.lhazar.GOTEntityLhazarArcher;
import got.common.entity.essos.lhazar.GOTEntityLhazarMan;
import got.common.entity.essos.lhazar.GOTEntityLhazarWarrior;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureLhazarSettlement extends GOTStructureBaseSettlement {
	public Type type;
	public boolean forcedType;

	public GOTStructureLhazarSettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 5;
		fixedSettlementChunkRadius = 5;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance<?> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc, type, forcedType);
	}

	public GOTStructureBaseSettlement type(Type t, int radius) {
		type = t;
		settlementChunkRadius = radius;
		fixedSettlementChunkRadius = radius;
		forcedType = true;
		return this;
	}

	public enum Type {
		VILLAGE, TOWN, FORT
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance<GOTStructureLhazarSettlement> {
		public Type type;
		public boolean forcedType;
		public int numOuterHouses;
		public boolean townWall = true;
		int rTownTower = 90;

		public Instance(GOTStructureLhazarSettlement settlement, World world, int i, int k, Random random, LocationInfo loc, Type t, boolean b) {
			super(settlement, world, i, k, random, loc);
			type = t;
			forcedType = b;
		}

		@Override
		public void addSettlementStructures(Random random) {
			switch (type) {
				case VILLAGE:
					setupVillage(random);
					break;
				case FORT:
					setupFort(random);
					break;
				case TOWN:
					setupTown(random);
					break;
			}
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			int dSq = i * i + k * k;
			if (type == Type.VILLAGE) {
				int imn = 16 - random.nextInt(3);
				int imx = 21 + random.nextInt(3);
				if (dSq > imn * imn && dSq < imx * imx) {
					return GOTBezierType.PATH_DIRTY;
				}
			} else if (type == Type.TOWN) {
				if (dSq < 576) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (k1 <= 3 && i1 <= 74 || i1 <= 3 && k <= 74) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			return null;
		}

		public GOTStructureBase getRandomFarm(Random random) {
			if (random.nextBoolean()) {
				return new GOTStructureLhazarFarm(false);
			}
			return new GOTStructureLhazarPasture(false);
		}

		public GOTStructureBase getRandomHouse(Random random) {
			if (random.nextInt(5) == 0) {
				return new GOTStructureLhazarSmithy(false);
			}
			return new GOTStructureLhazarHouse(false);
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			return block == GOTBlocks.slabSingleDirt && (meta == 1 || meta == 0) || block == GOTBlocks.slabSingleGravel && meta == 0 || block == GOTBlocks.dirtPath && meta == 0 || block == Blocks.dirt && meta == 1 || block == Blocks.gravel && meta == 0;
		}

		public void setupFort(Random random) {
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityLhazarMan.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(24, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureLhazarWarCamp(false), 0, -15, 0, true);
			int towerX = 36;
			addStructure(new GOTStructureLhazarTower(false), -towerX, -towerX + 4, 2, true);
			addStructure(new GOTStructureLhazarTower(false), towerX, -towerX + 4, 2, true);
			addStructure(new GOTStructureLhazarTower(false), -towerX, towerX - 4, 0, true);
			addStructure(new GOTStructureLhazarTower(false), towerX, towerX - 4, 0, true);
			for (int l = -1; l <= 1; ++l) {
				int i = l * 16;
				int k = 28;
				addStructure(getRandomFarm(random), i, k, 0);
				addStructure(getRandomFarm(random), -k, i, 1);
				addStructure(getRandomFarm(random), k, i, 3);
			}
		}

		@Override
		public void setupSettlementProperties(Random random) {
			if (!forcedType) {
				if (random.nextInt(4) == 0) {
					type = Type.FORT;
				} else {
					type = Type.VILLAGE;
				}
			}
			numOuterHouses = MathHelper.getRandomIntegerInRange(random, 5, 8);
		}

		public void setupTown(Random random) {
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityLhazarMan.class);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(40, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[]{-40, 40}) {
				for (int k1 : new int[]{-40, 40}) {
					addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							spawner.setSpawnClasses(GOTEntityLhazarWarrior.class, GOTEntityLhazarArcher.class);
							spawner.setCheckRanges(64, -12, 12, 20);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(64);
						}
					}, i1, k1, 0);
				}
			}
			addStructure(new GOTStructureLhazarPyramid(false), 0, -11, 0, true);
			int lightR = 15;
			addStructure(new GOTStructureLhazarVillageLight(false), -lightR, -lightR, 0, true);
			addStructure(new GOTStructureLhazarVillageLight(false), lightR, -lightR, 0, true);
			addStructure(new GOTStructureLhazarVillageLight(false), -lightR, lightR, 0, true);
			addStructure(new GOTStructureLhazarVillageLight(false), lightR, lightR, 0, true);
			addStructure(new GOTStructureLhazarBazaar(false), -74, 0, 1, true);
			addStructure(new GOTStructureLhazarAltar(false), 74, 0, 3, true);
			addStructure(new GOTStructureLhazarTotem(false), 0, 79, 0, true);
			for (int l = 0; l <= 2; ++l) {
				int i = 5;
				int k = 32 + l * 20;
				addStructure(new GOTStructureLhazarHouse(false), -i, -k, 1, true);
				addStructure(new GOTStructureLhazarHouse(false), i, -k, 3, true);
				addStructure(new GOTStructureLhazarHouse(false), -i, k, 1, true);
				addStructure(new GOTStructureLhazarHouse(false), i, k, 3, true);
				addStructure(new GOTStructureLhazarHouse(false), k, -i, 2, true);
				addStructure(new GOTStructureLhazarHouse(false), k, i, 0, true);
				if (l != 0) {
					continue;
				}
				addStructure(new GOTStructureLhazarSmithy(false), -k - 6, -i, 2, true);
				addStructure(new GOTStructureLhazarTavern(false), -k - 6, i, 0, true);
			}
			int xzTownTower = (int) (rTownTower / 1.4142135623730951);
			addStructure(new GOTStructureLhazarTower(false), -xzTownTower, -xzTownTower + 4, 2, true);
			addStructure(new GOTStructureLhazarTower(false), xzTownTower, -xzTownTower + 4, 2, true);
			addStructure(new GOTStructureLhazarTower(false), -xzTownTower, xzTownTower - 4, 0, true);
			addStructure(new GOTStructureLhazarTower(false), xzTownTower, xzTownTower - 4, 0, true);
			int turn = 0;
			int numTurns = 24;
			while (turn <= numTurns) {
				turn++;
				if (turn % 3 == 0) {
					continue;
				}
				float turnF = (float) turn / numTurns;
				float turnR = (float) Math.toRadians(turnF * 360.0f);
				float sin = MathHelper.sin(turnR);
				float cos = MathHelper.cos(turnR);
				int r = 0;
				float turn8 = turnF * 8.0f;
				if (turn8 >= 3.0f && turn8 < 5.0f) {
					r = 1;
				} else if (turn8 >= 5.0f && turn8 < 7.0f) {
					r = 2;
				} else if (turn8 >= 7.0f || turn8 < 1.0f) {
					r = 3;
				}
				int l = rTownTower - 6;
				int i = Math.round(l * cos);
				int k = Math.round(l * sin);
				if (random.nextInt(3) == 0) {
					addStructure(new GOTStructureHayBales(false), i, k, r);
					continue;
				}
				addStructure(getRandomFarm(random), i, k, r);
			}
			if (townWall) {
				int rSq = 9604;
				int rMax = 99;
				int rSqMax = rMax * rMax;
				for (int i = -98; i <= 98; ++i) {
					for (int k = -98; k <= 98; ++k) {
						int dSq;
						int i1 = Math.abs(i);
						if (i1 <= 6 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) {
							continue;
						}
						GOTStructureLhazarTownWall wall = new GOTStructureLhazarTownWall(false);
						if (i1 == 7 && k < 0) {
							wall.setTall();
						}
						addStructure(wall, i, k, 0);
					}
				}
			}
		}

		public void setupVillage(Random random) {
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityLhazarMan.class);
					spawner.setCheckRanges(64, -12, 12, 24);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClasses(GOTEntityLhazarWarrior.class, GOTEntityLhazarArcher.class);
					spawner.setCheckRanges(64, -12, 12, 12);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureLhazarTotem(false), 0, -2, 0, true);
			addStructure(new GOTStructureLhazarTavern(false), 0, 24, 0, true);
			for (int h = 0; h < numOuterHouses; ++h) {
				float turn = (float) h / (numOuterHouses - 1);
				float turnMin = 0.15f;
				float turnMax = 1.0f - turnMin;
				float turnInRange = turnMin + (turnMax - turnMin) * turn;
				float turnCorrected = (turnInRange + 0.25f) % 1.0f;
				float turnR = (float) Math.toRadians(turnCorrected * 360.0f);
				float sin = MathHelper.sin(turnR);
				float cos = MathHelper.cos(turnR);
				int r = 0;
				float turn8 = turnCorrected * 8.0f;
				if (turn8 >= 3.0f && turn8 < 5.0f) {
					r = 1;
				} else if (turn8 >= 5.0f && turn8 < 7.0f) {
					r = 2;
				} else if (turn8 >= 7.0f || turn8 < 1.0f) {
					r = 3;
				}
				int l = 24;
				int i = Math.round(l * cos);
				int k = Math.round(l * sin);
				addStructure(getRandomHouse(random), i, k, r);
			}
			int numFarms = numOuterHouses * 2;
			float frac = 1.0f / numFarms;
			float turn = 0.0f;
			while (turn < 1.0f) {
				float turnR = (float) Math.toRadians((turn += frac) * 360.0f);
				float sin = MathHelper.sin(turnR);
				float cos = MathHelper.cos(turnR);
				int r = 0;
				float turn8 = turn * 8.0f;
				if (turn8 >= 3.0f && turn8 < 5.0f) {
					r = 1;
				} else if (turn8 >= 5.0f && turn8 < 7.0f) {
					r = 2;
				} else if (turn8 >= 7.0f || turn8 < 1.0f) {
					r = 3;
				}
				int l = 52;
				int i = Math.round(l * cos);
				int k = Math.round(l * sin);
				if (random.nextInt(3) == 0) {
					addStructure(new GOTStructureHayBales(false), i, k, r);
					continue;
				}
				addStructure(getRandomFarm(random), i, k, r);
			}
		}

	}
}