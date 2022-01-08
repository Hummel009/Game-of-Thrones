package got.common.world.structure.essos.qohor;

import java.util.Random;

import com.google.common.math.IntMath;

import got.common.database.GOTRegistry;
import got.common.entity.essos.qohor.*;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.essos.common.*;
import got.common.world.structure.other.*;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class GOTStructureQohorCity extends GOTVillageGen {
	public boolean isTown;

	public GOTStructureQohorCity(GOTBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 6;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public GOTStructureQohorCity setIsTown() {
		isTown = true;
		return this;
	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;

		public Instance(GOTStructureQohorCity village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			switch (villageType) {
			case VILLAGE:
				setupVillage(random);
				break;
			case TOWN:
				setupTown(random);
				break;
			case FORT:
				setupFort(random);
				break;
			}
		}

		public GOTStructureBase getBarracks(Random random) {
			return new GOTStructureQohorBarracks(false);
		}

		public GOTStructureBase getBazaar(Random random) {
			return new GOTStructureQohorBazaar(false);
		}

		public GOTStructureBase getFlowers(Random random) {
			return new GOTStructureEssosTownFlowers(false);
		}

		public GOTStructureBase getFortCorner(Random random) {
			return new GOTStructureEssosFortCorner(false);
		}

		public GOTStructureBase getFortGate(Random random) {
			return new GOTStructureQohorFortGate(false);
		}

		public GOTStructureBase getFortress(Random random) {
			return new GOTStructureQohorFortress(false);
		}

		public GOTStructureBase getFortWallLong(Random random) {
			return new GOTStructureEssosFortWall.Long(false);
		}

		public GOTStructureBase getFortWallShort(Random random) {
			return new GOTStructureEssosFortWall.Short(false);
		}

		public GOTStructureBase getHouse(Random random) {
			return new GOTStructureQohorHouse(false);
		}

		public GOTStructureBase getLamp(Random random) {
			return new GOTStructureEssosLamp(false);
		}

		public GOTStructureBase getMansion(Random random) {
			return new GOTStructureQohorMansion(false);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (villageType == VillageType.VILLAGE) {
				int imn = 2;
				int imx = 14 + random.nextInt(3);
				int kmn = 2;
				int kmx = 14 + random.nextInt(3);
				if (i1 <= imx && k1 <= kmx && (i1 > imn || k1 > kmn)) {
					return GOTBezierType.PATH_DIRTY;
				}
				imn = 45 - random.nextInt(3);
				imx = 50 + random.nextInt(3);
				kmn = 45 - random.nextInt(3);
				kmx = 50 + random.nextInt(3);
				if (i1 <= imx && k1 <= kmx && (i1 > imn || k1 > kmn) && (k < 0 || i1 > 7)) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (k < 0) {
					imn = 14;
					imx = 45;
					if (i1 + k1 >= imn + imn && i1 + k1 <= imx + imx && Math.abs(i1 - k1) <= (int) (2.5f + random.nextInt(3) * 2.0f)) {
						return GOTBezierType.PATH_DIRTY;
					}
				}
				if (k > 0) {
					imn = 10;
					imx = imn + 5 + random.nextInt(3);
					kmn = 14;
					kmx = 45;
					if (k1 >= kmn && k1 <= kmx && i1 >= (imn -= random.nextInt(3)) && i1 <= imx) {
						return GOTBezierType.PATH_DIRTY;
					}
				}
			}
			if (villageType == VillageType.TOWN && i1 <= 72 && k1 <= 42) {
				return GOTBezierType.PATH_SANDY;
			}
			if (villageType == VillageType.FORT) {
				if (i1 <= 3 && k >= -45 && k <= -15) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (i1 <= 36 && k >= -27 && k <= -20) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (i1 >= 29 && i1 <= 36 && k >= -27 && k <= 39 && (k < -7 || k > 7)) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (i1 <= 36 && k >= 20 && k <= 27) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			return null;
		}

		public GOTStructureBase getRandomFarm(Random random) {
			if (random.nextBoolean()) {
				return new GOTStructureQohorFarm(false);
			}
			return new GOTStructureQohorPasture(false);
		}

		public GOTStructureBase getRandomHouse(Random random) {
			if (random.nextInt(6) == 0) {
				return new GOTStructureQohorSmithy(false);
			}
			if (random.nextInt(6) == 0) {
				return new GOTStructureQohorStables(false);
			}
			return new GOTStructureQohorHouse(false);
		}

		public GOTStructureBase getSmithy(Random random) {
			return new GOTStructureQohorSmithy(false);
		}

		public GOTStructureBase getStables(Random random) {
			return new GOTStructureQohorStables(false);
		}

		public GOTStructureBase getStatue(Random random) {
			return new GOTStructureEssosStatue(false);
		}

		public GOTStructureBase getTavern(Random random) {
			return new GOTStructureQohorTavern(false);
		}

		public GOTStructureBase getTower(Random random) {
			return new GOTStructureQohorTower(false);
		}

		public GOTStructureQohorTownGate getTownGate(Random random) {
			return new GOTStructureQohorTownGate(false);
		}

		public GOTStructureBase getTownWallCorner(Random random) {
			return new GOTStructureEssosTownCorner(false);
		}

		public GOTStructureBase getTownWallExtra(Random random) {
			return new GOTStructureEssosTownWall.Extra(false);
		}

		public GOTStructureBase getTownWallLong(Random random) {
			return new GOTStructureEssosTownWall.Long(false);
		}

		public GOTStructureBase getTownWallShort(Random random) {
			return new GOTStructureEssosTownWall.Short(false);
		}

		public GOTStructureBase getTownWallSideMid(Random random) {
			return new GOTStructureEssosTownWall.SideMid(false);
		}

		public GOTStructureBase getTraining(Random random) {
			return new GOTStructureQohorTraining(false);
		}

		public GOTStructureBase getTree(Random random) {
			return new GOTStructureEssosTownTree(false);
		}

		public GOTStructureBase getWell(Random random) {
			return new GOTStructureEssosWell(false);
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			if (villageType == VillageType.TOWN) {
				Block block = world.getBlock(i, j, k);
				int meta = world.getBlockMetadata(i, j, k);
				if (block == GOTRegistry.brick1 && meta == 15 || block == GOTRegistry.brick3 && meta == 11 || block == GOTRegistry.pillar1 && meta == 5) {
					return true;
				}
			}
			return false;
		}

		public void placeChampionRespawner() {
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityQohorGuard.class);
					spawner.setCheckRanges(60, -12, 12, 4);
					spawner.setSpawnRanges(24, -6, 6, 32);
				}
			}, 0, 0, 0);
		}

		public void setCivilianSpawnClass(GOTEntityNPCRespawner spawner) {
			spawner.setSpawnClass(GOTEntityQohorMan.class);
		}

		public void setupFort(Random random) {
			int k;
			int i;
			int r;
			int l;
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					Instance.this.setCivilianSpawnClass(spawner);
					spawner.setCheckRanges(60, -12, 12, 16);
					spawner.setSpawnRanges(24, -6, 6, 40);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[] { -25, 25 }) {
				for (int k1 : new int[] { -25, 25 }) {
					this.addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							Instance.this.setWarriorSpawnClasses(spawner);
							spawner.setCheckRanges(35, -12, 12, 16);
							spawner.setSpawnRanges(15, -6, 6, 40);
							spawner.setBlockEnemySpawnRange(35);
						}
					}, i1, k1, 0);
				}
			}
			placeChampionRespawner();
			this.addStructure(getFortress(random), 0, -15, 0, true);
			this.addStructure(getBarracks(random), -33, -8, 0, true);
			this.addStructure(getBarracks(random), 32, -8, 0, true);
			this.addStructure(getTower(random), -43, -36, 2, true);
			this.addStructure(getTower(random), 43, -36, 2, true);
			this.addStructure(getTower(random), -43, 36, 0, true);
			this.addStructure(getTower(random), 43, 36, 0, true);
			for (l = 0; l <= 2; ++l) {
				i = 10 + l * 11;
				k = -28;
				r = 2;
				this.addStructure(getRandomFarm(random), i, k, r);
				this.addStructure(getRandomFarm(random), -i, k, r);
			}
			this.addStructure(getTraining(random), 0, 27, 0, true);
			this.addStructure(getStables(random), -29, 33, 3, true);
			this.addStructure(getStables(random), 29, 37, 1, true);
			this.addStructure(getFortGate(random), 0, -47, 0, true);
			for (l = 0; l <= 9; ++l) {
				i = 8 + l * 4;
				k = -46;
				r = 0;
				if (l % 2 == 0) {
				}
				this.addStructure(getFortWallLong(random), -i, k, r, true);
				this.addStructure(getFortWallLong(random), i, k, r, true);
			}
			for (l = -11; l <= 11; ++l) {
				i = l * 4;
				k = 46;
				r = 2;
				if (l % 2 == 0) {
				}
				this.addStructure(getFortWallLong(random), i, k, r, true);
			}
			for (l = -10; l <= 10; ++l) {
				i = -50;
				k = l * 4;
				r = 3;
				this.addStructure(getFortWallLong(random), i, k, r, true);
				r = 1;
				if (l % 2 == 0) {
				}
				this.addStructure(getFortWallLong(random), -i, k, r, true);
			}
			this.addStructure(getFortCorner(random), -50, -46, 0, true);
			this.addStructure(getFortCorner(random), 50, -46, 1, true);
			this.addStructure(getFortCorner(random), -50, 46, 3, true);
			this.addStructure(getFortCorner(random), 50, 46, 2, true);
		}

		public void setupTown(Random random) {
			int k;
			int i;
			int r;
			int l;
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					Instance.this.setCivilianSpawnClass(spawner);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(40, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[] { -30, 30 }) {
				for (int k1 : new int[] { -30, 30 }) {
					this.addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							Instance.this.setWarriorSpawnClasses(spawner);
							spawner.setCheckRanges(40, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(60);
						}
					}, i1, k1, 0);
				}
			}
			this.addStructure(getBazaar(random), 1, -2, 0, true);
			this.addStructure(getLamp(random), 15, -2, 0, true);
			this.addStructure(getLamp(random), -13, -2, 0, true);
			this.addStructure(getLamp(random), 15, 18, 0, true);
			this.addStructure(getLamp(random), -13, 18, 0, true);
			this.addStructure(getWell(random), -16, 12, 1, true);
			this.addStructure(getWell(random), -16, 4, 1, true);
			this.addStructure(getFlowers(random), 18, 13, 3, true);
			this.addStructure(getFlowers(random), 18, 3, 3, true);
			for (l = 0; l <= 3; ++l) {
				i = -41 + l * 19;
				k = -7;
				r = 2;
				this.addStructure(getMansion(random), i, k, r, true);
				this.addStructure(getLamp(random), i + 6, k - 1, r, true);
				i = 24 - l * 19;
				k = 23;
				r = 0;
				this.addStructure(getMansion(random), i, k, r, true);
				this.addStructure(getLamp(random), i - 6, k + 1, r, true);
			}
			this.addStructure(getSmithy(random), -25, 9, 1, true);
			this.addStructure(getHouse(random), -25, 18, 1, true);
			this.addStructure(getHouse(random), -25, -2, 1, true);
			this.addStructure(getTree(random), -45, 8, 1, true);
			this.addStructure(getHouse(random), -50, 18, 3, true);
			this.addStructure(getHouse(random), -50, -2, 3, true);
			this.addStructure(getWell(random), -51, -14, 2, true);
			this.addStructure(getTree(random), -46, -29, 2, true);
			this.addStructure(getFlowers(random), -42, -32, 3, true);
			this.addStructure(getTree(random), -50, 30, 0, true);
			for (l = -3; l <= 3; ++l) {
				i = -56;
				k = -2 + l * 10;
				r = 1;
				this.addStructure(getHouse(random), i, k, r, true);
			}
			this.addStructure(getStatue(random), 26, 8, 3, true);
			this.addStructure(getHouse(random), 26, 18, 3, true);
			this.addStructure(getHouse(random), 26, -2, 3, true);
			for (l = -3; l <= 2; ++l) {
				i = 52;
				k = 8 + l * 10;
				r = 1;
				this.addStructure(getHouse(random), i, k, r, true);
			}
			this.addStructure(getSmithy(random), 41, -33, 3, true);
			for (l = -2; l <= 2; ++l) {
				i = 65;
				k = 3 + l * 14;
				r = 2;
				this.addStructure(getHouse(random), i, k, r, true);
			}
			this.addStructure(getWell(random), 57, -19, 2, true);
			this.addStructure(getLamp(random), 57, -16, 2, true);
			this.addStructure(getLamp(random), 57, -8, 2, true);
			this.addStructure(getTree(random), 57, 1, 2, true);
			this.addStructure(getLamp(random), 57, 4, 2, true);
			this.addStructure(getLamp(random), 57, 12, 2, true);
			this.addStructure(getTree(random), 57, 21, 2, true);
			this.addStructure(getLamp(random), 57, 24, 2, true);
			this.addStructure(getLamp(random), 57, 32, 2, true);
			for (l = 0; l <= 3; ++l) {
				i = 41 + l * 8;
				k = 34;
				r = 0;
				this.addStructure(getFlowers(random), i, k, r, true);
			}
			this.addStructure(getTree(random), 34, 25, 0, true);
			this.addStructure(getStables(random), -20, -30, 1, true);
			this.addStructure(getTavern(random), 17, -32, 1, true);
			this.addStructure(getLamp(random), 19, -28, 1, true);
			this.addStructure(getLamp(random), 19, -36, 1, true);
			this.addStructure(getLamp(random), -16, -32, 3, true);
			this.addStructure(getFlowers(random), 25, -32, 3, true);
			this.addStructure(getTree(random), 34, -29, 2, true);
			this.addStructure(getLamp(random), 34, -26, 2, true);
			this.addStructure(getLamp(random), 34, -18, 2, true);
			this.addStructure(getTree(random), 34, -9, 2, true);
			this.addStructure(getTownGate(random), 34, -47, 0, true);
			this.addStructure(getTownWallCorner(random), 73, -47, 0, true);
			this.addStructure(getTownWallCorner(random), -77, -43, 3, true);
			this.addStructure(getTownWallCorner(random), -73, 47, 2, true);
			this.addStructure(getTownWallCorner(random), 77, 43, 1, true);
			for (l = 0; l <= 6; ++l) {
				i = 68 - l * 4;
				k = -44;
				r = 0;
				if (l % 2 == 0) {
					this.addStructure(getTownWallShort(random), i, k, r, true);
					continue;
				}
				this.addStructure(getTownWallLong(random), i, k, r, true);
			}
			this.addStructure(getTownWallExtra(random), 24, -44, 0, true);
			for (l = 0; l <= 22; ++l) {
				i = 20 - l * 4;
				k = -44;
				r = 0;
				if (l % 2 == 0) {
					this.addStructure(getTownWallShort(random), i, k, r, true);
					continue;
				}
				this.addStructure(getTownWallLong(random), i, k, r, true);
			}
			this.addStructure(getTownWallSideMid(random), 74, 0, 1, true);
			this.addStructure(getTownWallSideMid(random), -74, 0, 3, true);
			for (l = 1; l <= 9; ++l) {
				i = 74;
				k = 2 + l * 4;
				if (l % 2 == 1) {
					this.addStructure(getTownWallShort(random), i, k, 1, true);
					this.addStructure(getTownWallShort(random), i, -k, 1, true);
					this.addStructure(getTownWallShort(random), -i, k, 3, true);
					this.addStructure(getTownWallShort(random), -i, -k, 3, true);
					continue;
				}
				this.addStructure(getTownWallLong(random), i, k, 1, true);
				this.addStructure(getTownWallLong(random), i, -k, 1, true);
				this.addStructure(getTownWallLong(random), -i, k, 3, true);
				this.addStructure(getTownWallLong(random), -i, -k, 3, true);
			}
			for (l = -17; l <= 17; ++l) {
				i = 0 + l * 4;
				k = 44;
				r = 2;
				if (IntMath.mod(l, 2) == 1) {
					this.addStructure(getTownWallShort(random), i, k, r, true);
					continue;
				}
				this.addStructure(getTownWallLong(random), i, k, r, true);
			}
		}

		public void setupVillage(Random random) {
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					Instance.this.setCivilianSpawnClass(spawner);
					spawner.setCheckRanges(64, -12, 12, 24);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					Instance.this.setWarriorSpawnClasses(spawner);
					spawner.setCheckRanges(64, -12, 12, 12);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			this.addStructure(getWell(random), 0, -2, 0, true);
			int rSquareEdge = 17;
			this.addStructure(getTavern(random), 0, rSquareEdge, 0, true);
			this.addStructure(getMansion(random), -3, -rSquareEdge, 2, true);
			this.addStructure(getMansion(random), -rSquareEdge, 3, 1, true);
			this.addStructure(getMansion(random), rSquareEdge, -3, 3, true);
			int backFenceX = 0;
			int backFenceZ = rSquareEdge + 19;
			int backFenceWidth = 12;
			int sideFenceX = 13;
			int sideFenceZ = rSquareEdge + 11;
			int sideFenceWidth = 8;
			int frontPostZ = sideFenceZ - sideFenceWidth - 1;
			this.addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), backFenceX, -backFenceZ, 0);
			this.addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), -sideFenceX, -sideFenceZ, 1);
			this.addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), sideFenceX, -sideFenceZ, 3);
			this.addStructure(new GOTStructureEssosVillagePost(false), -sideFenceX, -frontPostZ, 0);
			this.addStructure(new GOTStructureEssosVillagePost(false), sideFenceX, -frontPostZ, 0);
			this.addStructure(new GOTStructureEssosVillagePost(false), -sideFenceX, -backFenceZ, 0);
			this.addStructure(new GOTStructureEssosVillagePost(false), sideFenceX, -backFenceZ, 0);
			this.addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), -backFenceZ, backFenceX, 1);
			this.addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), -sideFenceZ, sideFenceX, 0);
			this.addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), -sideFenceZ, -sideFenceX, 2);
			this.addStructure(new GOTStructureEssosVillagePost(false), -frontPostZ, sideFenceX, 0);
			this.addStructure(new GOTStructureEssosVillagePost(false), -frontPostZ, -sideFenceX, 0);
			this.addStructure(new GOTStructureEssosVillagePost(false), -backFenceZ, sideFenceX, 0);
			this.addStructure(new GOTStructureEssosVillagePost(false), -backFenceZ, -sideFenceX, 0);
			this.addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), backFenceZ, backFenceX, 3);
			this.addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), sideFenceZ, -sideFenceX, 2);
			this.addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), sideFenceZ, sideFenceX, 0);
			this.addStructure(new GOTStructureEssosVillagePost(false), frontPostZ, -sideFenceX, 0);
			this.addStructure(new GOTStructureEssosVillagePost(false), frontPostZ, sideFenceX, 0);
			this.addStructure(new GOTStructureEssosVillagePost(false), backFenceZ, -sideFenceX, 0);
			this.addStructure(new GOTStructureEssosVillagePost(false), backFenceZ, sideFenceX, 0);
			int farmRange = 3;
			int farmStep = 14;
			int farmX = 55;
			for (int l = -farmRange; l <= farmRange; ++l) {
				int k = l * farmStep;
				int i = -farmX;
				int r = 1;
				if (random.nextInt(3) == 0) {
					this.addStructure(new GOTStructureHayBales(false), i, k, r);
				} else {
					this.addStructure(getRandomFarm(random), i, k, r);
				}
				i = farmX;
				r = 3;
				if (random.nextInt(3) == 0) {
					this.addStructure(new GOTStructureHayBales(false), i, k, r);
					continue;
				}
				this.addStructure(getRandomFarm(random), i, k, r);
			}
			int houseRange = 3;
			int houseStep = 17;
			int houseZ = 55;
			for (int l = -houseRange; l <= houseRange; ++l) {
				int i = l * houseStep;
				int k = -houseZ;
				int r = 2;
				this.addStructure(getRandomHouse(random), i, k, r);
				k = houseZ;
				r = 0;
				if (Math.abs(i) < 7) {
					continue;
				}
				this.addStructure(getRandomHouse(random), i, k, r);
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			if (isTown) {
				villageType = VillageType.TOWN;
			} else if (random.nextInt(4) == 0) {
				villageType = VillageType.FORT;
			} else {
				villageType = VillageType.VILLAGE;
			}
		}

		public void setWarriorSpawnClasses(GOTEntityNPCRespawner spawner) {
			spawner.setSpawnClasses(GOTEntityQohorUnsullied.class, GOTEntityQohorLevymanArcher.class);
		}

	}

	public enum VillageType {
		VILLAGE, TOWN, FORT;
	}

}
