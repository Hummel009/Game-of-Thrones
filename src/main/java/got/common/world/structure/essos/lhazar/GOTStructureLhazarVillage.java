package got.common.world.structure.essos.lhazar;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.entity.essos.lhazar.*;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTStructureLhazarVillage extends GOTVillageGen {
	public GOTStructureLhazarVillage(GOTBiome biome, float f) {
		super(biome);
		gridScale = 14;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 6;
	}

	@Override
	public GOTVillageGen.AbstractInstance<?> createVillageInstance(World world, int i, int k, Random random, GOTLocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class Instance extends GOTVillageGen.AbstractInstance<GOTStructureLhazarVillage> {
		public VillageType villageType;
		public int numOuterHouses;
		public boolean townWall = true;
		int rTownTower = 90;

		public Instance(GOTStructureLhazarVillage village, World world, int i, int k, Random random, GOTLocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			if (villageType == VillageType.VILLAGE) {
				setupVillage(random);
			} else if (villageType == VillageType.TOWN) {
				setupTown(random);
			} else if (villageType == VillageType.FORT) {
				setupFort(random);
			}
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int dSq;
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (villageType == VillageType.VILLAGE) {
				dSq = i * i + k * k;
				int imn = 16 - random.nextInt(3);
				int imx = 21 + random.nextInt(3);
				if (dSq > imn * imn && dSq < imx * imx) {
					return GOTBezierType.ESSOS;
				}
			}
			if (villageType == VillageType.TOWN) {
				dSq = i * i + k * k;
				if (dSq < 576) {
					return GOTBezierType.ESSOS;
				}
				if (k1 <= 3 && i1 <= 74 || i1 <= 3 && k <= 74) {
					return GOTBezierType.ESSOS;
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
		public boolean isFlat() {
			return false;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			if (villageType == VillageType.TOWN) {
				Block block = world.getBlock(i, j, k);
				int meta = world.getBlockMetadata(i, j, k);
				if ((block == GOTRegistry.brick3 && (meta == 13 || meta == 14))) {
					return true;
				}
			}
			return false;
		}

		public void setupFort(Random random) {
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityLhazarMan.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(24, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureLhazarWarCamp(false), 0, -15, 0, true);
			int towerX = 36;
			this.addStructure(new GOTStructureLhazarTower(false), -towerX, -towerX + 4, 2, true);
			this.addStructure(new GOTStructureLhazarTower(false), towerX, -towerX + 4, 2, true);
			this.addStructure(new GOTStructureLhazarTower(false), -towerX, towerX - 4, 0, true);
			this.addStructure(new GOTStructureLhazarTower(false), towerX, towerX - 4, 0, true);
			for (int l = -1; l <= 1; ++l) {
				int i = l * 16;
				int k = 28;
				this.addStructure(getRandomFarm(random), i, k, 0);
				this.addStructure(getRandomFarm(random), -k, i, 1);
				this.addStructure(getRandomFarm(random), k, i, 3);
			}
		}

		public void setupTown(Random random) {
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityLhazarMan.class);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(40, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[] { -40, 40 }) {
				for (int k1 : new int[] { -40, 40 }) {
					this.addStructure(new GOTStructureNPCRespawner(false) {

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
			this.addStructure(new GOTStructureLhazarPyramid(false), 0, -11, 0, true);
			int lightR = 15;
			this.addStructure(new GOTStructureLhazarVillageLight(false), -lightR, -lightR, 0, true);
			this.addStructure(new GOTStructureLhazarVillageLight(false), lightR, -lightR, 0, true);
			this.addStructure(new GOTStructureLhazarVillageLight(false), -lightR, lightR, 0, true);
			this.addStructure(new GOTStructureLhazarVillageLight(false), lightR, lightR, 0, true);
			this.addStructure(new GOTStructureLhazarBazaar(false), -74, 0, 1, true);
			this.addStructure(new GOTStructureLhazarAltar(false), 74, 0, 3, true);
			this.addStructure(new GOTStructureLhazarTotem(false), 0, 79, 0, true);
			for (int l = 0; l <= 2; ++l) {
				int i = 5;
				int k = 32 + l * 20;
				this.addStructure(new GOTStructureLhazarHouse(false), -i, -k, 1, true);
				this.addStructure(new GOTStructureLhazarHouse(false), i, -k, 3, true);
				this.addStructure(new GOTStructureLhazarHouse(false), -i, k, 1, true);
				this.addStructure(new GOTStructureLhazarHouse(false), i, k, 3, true);
				this.addStructure(new GOTStructureLhazarHouse(false), k, -i, 2, true);
				this.addStructure(new GOTStructureLhazarHouse(false), k, i, 0, true);
				if (l != 0) {
					continue;
				}
				this.addStructure(new GOTStructureLhazarSmithy(false), -k - 6, -i, 2, true);
				this.addStructure(new GOTStructureLhazarTavern(false), -k - 6, i, 0, true);
			}
			int xzTownTower = (int) (rTownTower / Math.sqrt(2.0));
			this.addStructure(new GOTStructureLhazarTower(false), -xzTownTower, -xzTownTower + 4, 2, true);
			this.addStructure(new GOTStructureLhazarTower(false), xzTownTower, -xzTownTower + 4, 2, true);
			this.addStructure(new GOTStructureLhazarTower(false), -xzTownTower, xzTownTower - 4, 0, true);
			this.addStructure(new GOTStructureLhazarTower(false), xzTownTower, xzTownTower - 4, 0, true);
			int turn = 0;
			int numTurns = 24;
			while (turn <= numTurns) {
				if (++turn % 3 == 0) {
					continue;
				}
				float turnF = (float) turn / (float) numTurns;
				float turnR = (float) Math.toRadians(turnF * 360.0f);
				float sin = MathHelper.sin(turnR);
				float cos = MathHelper.cos(turnR);
				int r = 0;
				float turn8 = turnF * 8.0f;
				if (turn8 >= 1.0f && turn8 < 3.0f) {
					r = 0;
				} else if (turn8 >= 3.0f && turn8 < 5.0f) {
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
					this.addStructure(new GOTStructureHayBales(false), i, k, r);
					continue;
				}
				this.addStructure(getRandomFarm(random), i, k, r);
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
						this.addStructure(wall, i, k, 0);
					}
				}
			}
		}

		public void setupVillage(Random random) {
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityLhazarMan.class);
					spawner.setCheckRanges(64, -12, 12, 24);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClasses(GOTEntityLhazarWarrior.class, GOTEntityLhazarArcher.class);
					spawner.setCheckRanges(64, -12, 12, 12);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureLhazarTotem(false), 0, -2, 0, true);
			this.addStructure(new GOTStructureLhazarTavern(false), 0, 24, 0, true);
			for (int h = 0; h < numOuterHouses; ++h) {
				float turn = (float) h / (float) (numOuterHouses - 1);
				float turnMin = 0.15f;
				float turnMax = 1.0f - turnMin;
				float turnInRange = turnMin + (turnMax - turnMin) * turn;
				float turnCorrected = (turnInRange + 0.25f) % 1.0f;
				float turnR = (float) Math.toRadians(turnCorrected * 360.0f);
				float sin = MathHelper.sin(turnR);
				float cos = MathHelper.cos(turnR);
				int r = 0;
				float turn8 = turnCorrected * 8.0f;
				if (turn8 >= 1.0f && turn8 < 3.0f) {
					r = 0;
				} else if (turn8 >= 3.0f && turn8 < 5.0f) {
					r = 1;
				} else if (turn8 >= 5.0f && turn8 < 7.0f) {
					r = 2;
				} else if (turn8 >= 7.0f || turn8 < 1.0f) {
					r = 3;
				}
				int l = 24;
				int i = Math.round(l * cos);
				int k = Math.round(l * sin);
				this.addStructure(getRandomHouse(random), i, k, r);
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
				if (turn8 >= 1.0f && turn8 < 3.0f) {
					r = 0;
				} else if (turn8 >= 3.0f && turn8 < 5.0f) {
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
					this.addStructure(new GOTStructureHayBales(false), i, k, r);
					continue;
				}
				this.addStructure(getRandomFarm(random), i, k, r);
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			villageType = random.nextInt(4) == 0 ? VillageType.FORT : (random.nextInt(3) == 0 ? VillageType.TOWN : VillageType.VILLAGE);
			numOuterHouses = MathHelper.getRandomIntegerInRange(random, 5, 8);
		}

	}

	public enum VillageType {
		VILLAGE, TOWN, FORT;
	}
}