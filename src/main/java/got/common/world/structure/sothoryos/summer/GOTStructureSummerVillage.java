package got.common.world.structure.sothoryos.summer;

import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.sothoryos.summer.GOTEntitySummerArcher;
import got.common.entity.sothoryos.summer.GOTEntitySummerMan;
import got.common.entity.sothoryos.summer.GOTEntitySummerWarrior;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSummerVillage extends GOTVillageGen {
	public boolean isRuinedVillage;

	public GOTStructureSummerVillage(GOTBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 4;
		fixedVillageChunkRadius = 4;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public GOTStructureSummerVillage setIsRuined() {
		isRuinedVillage = true;
		return this;
	}

	public enum VillageType {
		VILLAGE, FORT
	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;

		public Instance(GOTStructureSummerVillage village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			switch (villageType) {
				case VILLAGE:
					setupVillage(random);
					break;
				case FORT:
					setupFortress(random);
					break;
			}
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			if (villageType == VillageType.VILLAGE) {
				if (isRuinedVillage && random.nextInt(4) == 0) {
					return null;
				}
				int dSq = i * i + k * k;
				int imn = 17 - random.nextInt(3);
				int imx = 22 + random.nextInt(3);
				if (dSq > imn * imn && dSq < imx * imx) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (k <= -imx && k >= -66 && i1 < 2 + random.nextInt(3)) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			return null;
		}

		public GOTStructureBase getRandomHouse(Random random) {
			if (isRuinedVillage) {
				return new GOTStructureSummerHouseRuined(false);
			}
			if (random.nextInt(5) == 0) {
				return new GOTStructureSummerSmithy(false);
			}
			if (random.nextInt(4) == 0) {
				return new GOTStructureSummerStables(false);
			}
			return new GOTStructureSummerHouse(false);
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		public void setupFortress(Random random) {
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntitySummerMan.class);
					spawner.setCheckRanges(64, -12, 12, 16);
					spawner.setSpawnRanges(24, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(50);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureSummerFort(false), 0, -12, 0, true);
			addStructure(new GOTStructureSummerTower(false), -24, -24, 0, true);
			addStructure(new GOTStructureSummerTower(false), 24, -24, 0, true);
			addStructure(new GOTStructureSummerTower(false), -24, 24, 2, true);
			addStructure(new GOTStructureSummerTower(false), 24, 24, 2, true);
			for (int l = -1; l <= 1; ++l) {
				int k = l * 10;
				int i = 24;
				addStructure(new GOTStructureSummerTent(false), -i, k, 1, true);
				addStructure(new GOTStructureSummerTent(false), i, k, 3, true);
			}
			int rSq = 1764;
			int rMax = 43;
			int rSqMax = rMax * rMax;
			for (int i = -42; i <= 42; ++i) {
				for (int k = -42; k <= 42; ++k) {
					int dSq;
					int i1 = Math.abs(i);
					if (i1 <= 4 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) {
						continue;
					}
					GOTStructureSummerPalisade palisade = new GOTStructureSummerPalisade(false);
					if (i1 == 5 && k < 0) {
						palisade.setTall();
					}
					addStructure(palisade, i, k, 0);
				}
			}
		}

		public void setupVillage(Random random) {
			if (!isRuinedVillage) {
				addStructure(new GOTStructureNPCRespawner(false) {

					@Override
					public void setupRespawner(GOTEntityNPCRespawner spawner) {
						spawner.setSpawnClass(GOTEntitySummerMan.class);
						spawner.setCheckRanges(64, -12, 12, 24);
						spawner.setSpawnRanges(32, -6, 6, 32);
						spawner.setBlockEnemySpawnRange(64);
					}
				}, 0, 0, 0);
				addStructure(new GOTStructureNPCRespawner(false) {

					@Override
					public void setupRespawner(GOTEntityNPCRespawner spawner) {
						spawner.setSpawnClasses(GOTEntitySummerWarrior.class, GOTEntitySummerArcher.class);
						spawner.setCheckRanges(64, -12, 12, 12);
						spawner.setSpawnRanges(32, -6, 6, 32);
						spawner.setBlockEnemySpawnRange(64);
					}
				}, 0, 0, 0);
			}
			if (isRuinedVillage) {
				addStructure(new GOTStructureSummerTavernRuined(false), 3, -7, 0, true);
			} else if (random.nextBoolean()) {
				addStructure(new GOTStructureSummerMarket(false), 0, -8, 0, true);
			} else {
				addStructure(new GOTStructureSummerTavern(false), 3, -7, 0, true);
			}
			float frac = 1.0f / 8;
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
				int l = 25;
				int i = Math.round(l * cos);
				int k = Math.round(l * sin);
				if (k < 0 && Math.abs(i) < 10) {
					continue;
				}
				addStructure(getRandomHouse(random), i, k, r);
			}
			if (!isRuinedVillage) {
				int numFarms = 8 * 2;
				frac = 1.0f / numFarms;
				turn = 0.0f;
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
					int l = 45;
					int i = Math.round(l * cos);
					int k = Math.round(l * sin);
					if (k < 0 && Math.abs(i) < 10) {
						continue;
					}
					if (random.nextInt(3) == 0) {
						addStructure(new GOTStructureHayBales(false), i, k, r);
						continue;
					}
					if (random.nextInt(3) == 0) {
						addStructure(new GOTStructureSummerPasture(false), i, k, r);
						continue;
					}
					addStructure(new GOTStructureSummerFarm(false), i, k, r);
				}
			}
			if (!isRuinedVillage) {
				addStructure(new GOTStructureSummerVillageSign(false), 5 * (random.nextBoolean() ? 1 : -1), -56, 0, true);
			}
			int rSq = 3721;
			int rMax = 62;
			int rSqMax = rMax * rMax;
			for (int i = -61; i <= 61; ++i) {
				for (int k = -61; k <= 61; ++k) {
					int dSq;
					GOTStructureSummerPalisade palisade;
					int i1 = Math.abs(i);
					if (i1 <= 4 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) {
						continue;
					}
					if (isRuinedVillage) {
						if (random.nextBoolean()) {
							continue;
						}
						palisade = new GOTStructureSummerPalisadeRuined(false);
					} else {
						palisade = new GOTStructureSummerPalisade(false);
					}
					if (i1 == 5 && k < 0) {
						palisade.setTall();
					}
					addStructure(palisade, i, k, 0);
				}
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			if (random.nextInt(4) == 0) {
				villageType = VillageType.FORT;
			} else {
				villageType = VillageType.VILLAGE;
			}
		}

	}
}