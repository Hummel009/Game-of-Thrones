package got.common.world.structure.essos.ibben;

import got.common.entity.essos.ibben.GOTEntityIbbenArcher;
import got.common.entity.essos.ibben.GOTEntityIbbenMan;
import got.common.entity.essos.ibben.GOTEntityIbbenWarlord;
import got.common.entity.essos.ibben.GOTEntityIbbenWarrior;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureIbbenVillage extends GOTVillageGen {
	public GOTStructureIbbenVillage(GOTBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 5;
		fixedVillageChunkRadius = 5;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public enum VillageType {
		VILLAGE, FORT

	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;
		public boolean palisade;

		public Instance(GOTStructureIbbenVillage village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			switch (villageType) {
				case VILLAGE:
					setupVillage(random);
					break;
				case FORT:
					setupFort(random);
					break;
			}
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (villageType == VillageType.VILLAGE) {
				int dSq = i * i + k * k;
				int imn = 20 + random.nextInt(4);
				if (dSq < imn * imn) {
					return GOTBezierType.PATH_DIRTY;
				}
				int omn = 50 - random.nextInt(4);
				int omx = 56 + random.nextInt(4);
				if (dSq > omn * omn && dSq < omx * omx || dSq < 2500 && Math.abs(i1 - k1) <= 2 + random.nextInt(4)) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (palisade && k < -56 && k > -81 && i1 <= 2 + random.nextInt(4)) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			if (villageType == VillageType.FORT) {
				if (k <= -14 && k >= -49 && i1 <= 2) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (k <= -14 && k >= -17 && i1 <= 37) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (k >= -14 && k <= 20 && i1 >= 19 && i1 <= 22) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (k >= 20 && k <= 23 && i1 <= 37) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			return null;
		}

		public GOTStructureBase getRandomFarm(Random random) {
			if (random.nextInt(3) == 0) {
				return new GOTStructureIbbenVillagePasture(false);
			}
			return new GOTStructureIbbenVillageFarm(false);
		}

		public GOTStructureBase getRandomHouse(Random random) {
			if (random.nextInt(4) == 0) {
				int i = random.nextInt(3);
				switch (i) {
					case 0:
						return new GOTStructureIbbenSmithy(false);
					case 1:
						return new GOTStructureIbbenStables(false);
					case 2:
						return new GOTStructureIbbenBarn(false);
					default:
						break;
				}
			}
			return new GOTStructureIbbenHouse(false);
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		public void setupFort(Random random) {
			int wallX;
			int l;
			int wallZ;
			int farmX;
			int l2;
			addStructure(new GOTStructureIbbenFortress(false), 0, -13, 0, true);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClasses(GOTEntityIbbenWarrior.class, GOTEntityIbbenArcher.class);
					spawner.setCheckRanges(40, -12, 12, 30);
					spawner.setSpawnRanges(32, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureIbbenGatehouse(false), 0, -53, 0, true);
			int towerX = 46;
			for (int i1 : new int[]{-towerX, towerX}) {
				addStructure(new GOTStructureIbbenWatchtower(false), i1, -towerX, 0, true);
				addStructure(new GOTStructureIbbenWatchtower(false), i1, towerX, 2, true);
			}
			for (int i1 : new int[]{-35, 35}) {
				addStructure(new GOTStructureIbbenStables(false), i1, -14, 0, true);
			}
			int farmZ = -20;
			for (l2 = 0; l2 <= 1; ++l2) {
				farmX = 30 - l2 * 12;
				addStructure(new GOTStructureIbbenVillageFarm(false), -farmX, farmZ, 2);
				addStructure(new GOTStructureIbbenVillageFarm(false), farmX, farmZ, 2);
			}
			farmZ = 26;
			for (l2 = -2; l2 <= 2; ++l2) {
				farmX = l2 * 12;
				addStructure(new GOTStructureIbbenVillageFarm(false), -farmX, farmZ, 0);
				addStructure(new GOTStructureIbbenVillageFarm(false), farmX, farmZ, 0);
			}
			for (int i1 : new int[]{-51, 51}) {
				for (int k1 : new int[]{-51, 51}) {
					addStructure(new GOTStructureIbbenFortCorner(false), i1, k1, 0, true);
				}
			}
			for (l = 0; l <= 4; ++l) {
				wallX = 13 + l * 8;
				wallZ = -51;
				addStructure(new GOTStructureIbbenFortWall(false, -3, 4), -wallX, wallZ, 0, true);
				addStructure(new GOTStructureIbbenFortWall(false, -4, 3), wallX, wallZ, 0, true);
			}
			for (l = -5; l <= 5; ++l) {
				wallX = l * 9;
				wallZ = 51;
				addStructure(new GOTStructureIbbenFortWall(false), wallX, wallZ, 2, true);
				addStructure(new GOTStructureIbbenFortWall(false), -wallZ, wallX, 3, true);
				addStructure(new GOTStructureIbbenFortWall(false), wallZ, wallX, 1, true);
			}
		}

		public void setupVillage(Random random) {
			addStructure(new GOTStructureIbbenTavern(false), 0, 2, 0, true);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityIbbenMan.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityIbbenWarlord.class);
					spawner.setCheckRanges(2, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityIbbenWarrior.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			int houses = 20;
			float frac = 1.0f / houses;
			float turn = 0.0f;
			while (turn < 1.0f) {
				int i;
				int l;
				int k;
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
				if (palisade && sin < 0.0f && Math.abs(cos) <= 0.25f) {
					continue;
				}
				if (random.nextBoolean()) {
					l = 57;
					i = Math.round(l * cos);
					k = Math.round(l * sin);
					addStructure(getRandomHouse(random), i, k, r);
					continue;
				}
				if (random.nextInt(3) == 0) {
					continue;
				}
				l = 61;
				i = Math.round(l * cos);
				k = Math.round(l * sin);
				addStructure(new GOTStructureHayBales(false), i, k, r);
			}
			int farmX = 25;
			for (int k = -1; k <= 1; ++k) {
				int farmZ = k * 14;
				addStructure(getRandomFarm(random), -farmX, farmZ, 1);
				addStructure(getRandomFarm(random), farmX, farmZ, 3);
			}
			int gardenX = 14;
			for (int k = 0; k <= 2; ++k) {
				int gardenZ = 24 + k * 8;
				addStructure(new GOTStructureIbbenVillageGarden(false), -gardenX, gardenZ, 3);
				addStructure(new GOTStructureIbbenVillageGarden(false), gardenX, gardenZ, 1);
			}
			int gardenZ = 41;
			for (int i = -1; i <= 1; ++i) {
				gardenX = i * 6;
				if (i == 0) {
					continue;
				}
				addStructure(new GOTStructureIbbenVillageGarden(false), gardenX, gardenZ, 0);
			}
			addStructure(new GOTStructureIbbenWell(false), 0, -23, 2, true);
			if (random.nextBoolean()) {
				int marketX = 8;
				for (int k = 0; k <= 1; ++k) {
					int marketZ = 25 + k * 10;
					if (random.nextBoolean()) {
						addStructure(GOTStructureIbbenMarketStall.getRandomStall(random, false), -marketX, -marketZ, 1);
					}
					if (!random.nextBoolean()) {
						continue;
					}
					addStructure(GOTStructureIbbenMarketStall.getRandomStall(random, false), marketX, -marketZ, 3);
				}
			}
			if (palisade) {
				int rPalisade = 81;
				int rSq = rPalisade * rPalisade;
				int rMax = rPalisade + 1;
				int rSqMax = rMax * rMax;
				for (int i = -rPalisade; i <= rPalisade; ++i) {
					for (int k = -rPalisade; k <= rPalisade; ++k) {
						int dSq;
						if (Math.abs(i) <= 9 && k < 0 || (dSq = i * i + k * k) < rSq || dSq >= rSqMax) {
							continue;
						}
						addStructure(new GOTStructureIbbenVillagePalisade(false), i, k, 0);
					}
				}
				addStructure(new GOTStructureIbbenGatehouse(false), 0, -rPalisade - 2, 0);
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			villageType = random.nextInt(3) == 0 ? VillageType.FORT : VillageType.VILLAGE;
			palisade = random.nextBoolean();
		}

	}

}
