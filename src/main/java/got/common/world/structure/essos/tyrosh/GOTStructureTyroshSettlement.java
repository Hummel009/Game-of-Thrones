package got.common.world.structure.essos.tyrosh;

import com.google.common.math.IntMath;
import got.common.entity.essos.tyrosh.GOTEntityTyroshMan;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSoldier;
import got.common.entity.essos.tyrosh.GOTEntityTyroshSoldierArcher;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.structure.essos.common.GOTStructureEssosVillageFence;
import got.common.world.structure.essos.common.GOTStructureEssosVillagePost;
import got.common.world.structure.other.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureTyroshSettlement extends GOTStructureBaseSettlement {
	private Type type;
	private boolean forcedType;

	public GOTStructureTyroshSettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 5;
		fixedSettlementChunkRadius = 5;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc, Collection<GOTFixer.SpawnInfo> spawnInfos) {
		return new Instance(world, i, k, random, loc, spawnInfos, type, forcedType);
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

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance {
		private Type type;
		protected boolean forcedType;

		protected Instance(World world, int i, int k, Random random, LocationInfo loc, Collection<GOTFixer.SpawnInfo> spawnInfos, Type t, boolean b) {
			super(world, i, k, random, loc, spawnInfos);
			type = t;
			forcedType = b;
		}

		@Override
		public void addSettlementStructures(Random random) {
			super.addSettlementStructures(random);
			switch (type) {
				case VILLAGE:
					setupVillage(random);
					break;
				case TOWN:
					setupTown();
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
			if (type == Type.VILLAGE) {
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
					if (k1 >= kmn && k1 <= kmx && i1 >= imn - random.nextInt(3) && i1 <= imx) {
						return GOTBezierType.PATH_DIRTY;
					}
				}
			} else if (type == Type.TOWN && i1 <= 72 && k1 <= 42) {
				return GOTBezierType.PATH_DIRTY;
			} else if (type == Type.FORT) {
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

		protected GOTStructureBase getRandomFarm(Random random) {
			if (random.nextBoolean()) {
				return new GOTStructureTyroshFarm(false);
			}
			return new GOTStructureTyroshPasture(false);
		}

		protected GOTStructureBase getRandomHouse(Random random) {
			if (random.nextInt(6) == 0) {
				return new GOTStructureTyroshSmithy(false);
			}
			if (random.nextInt(6) == 0) {
				return new GOTStructureTyroshStables(false);
			}
			return new GOTStructureTyroshHouse(false);
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			return (block == Blocks.dirt || block == Blocks.sand) && meta == 1 || block == Blocks.sand && meta == 0;
		}

		protected void placeChampionRespawner() {
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityTyroshSoldier.class);
					spawner.setCheckRanges(60, -12, 12, 4);
					spawner.setSpawnRanges(24, -6, 6, 32);
				}
			}, 0, 0, 0);
		}

		protected void setCivilianSpawnClass(GOTEntityNPCRespawner spawner) {
			spawner.setSpawnClass(GOTEntityTyroshMan.class);
		}

		protected void setupFort(Random random) {
			int k;
			int i;
			int r;
			int l;
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					setCivilianSpawnClass(spawner);
					spawner.setCheckRanges(60, -12, 12, 16);
					spawner.setSpawnRanges(24, -6, 6, 40);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[]{-25, 25}) {
				for (int k1 : new int[]{-25, 25}) {
					addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							setWarriorSpawnClasses(spawner);
							spawner.setCheckRanges(35, -12, 12, 16);
							spawner.setSpawnRanges(15, -6, 6, 40);
							spawner.setBlockEnemySpawnRange(35);
						}
					}, i1, k1, 0);
				}
			}
			placeChampionRespawner();
			addStructure(new GOTStructureTyroshFortress(false), 0, -15, 0, true);
			addStructure(new GOTStructureTyroshBarracks(false), -33, -8, 0, true);
			addStructure(new GOTStructureTyroshBarracks(false), 32, -8, 0, true);
			addStructure(new GOTStructureTyroshTower(false), -43, -36, 2, true);
			addStructure(new GOTStructureTyroshTower(false), 43, -36, 2, true);
			addStructure(new GOTStructureTyroshTower(false), -43, 36, 0, true);
			addStructure(new GOTStructureTyroshTower(false), 43, 36, 0, true);
			for (l = 0; l <= 2; ++l) {
				i = 10 + l * 11;
				k = -28;
				r = 2;
				addStructure(getRandomFarm(random), i, k, r);
				addStructure(getRandomFarm(random), -i, k, r);
			}
			addStructure(new GOTStructureTyroshTraining(false), 0, 27, 0, true);
			addStructure(new GOTStructureTyroshStables(false), -29, 33, 3, true);
			addStructure(new GOTStructureTyroshStables(false), 29, 37, 1, true);
			addStructure(new GOTStructureTyroshFortGate(false), 0, -47, 0, true);
			for (l = 0; l <= 9; ++l) {
				i = 8 + l * 4;
				k = -46;
				r = 0;
				addStructure(new GOTStructureTyroshFortWall.Long(false), -i, k, r, true);
				addStructure(new GOTStructureTyroshFortWall.Long(false), i, k, r, true);
			}
			for (l = -11; l <= 11; ++l) {
				i = l * 4;
				k = 46;
				r = 2;
				addStructure(new GOTStructureTyroshFortWall.Long(false), i, k, r, true);
			}
			for (l = -10; l <= 10; ++l) {
				i = -50;
				k = l * 4;
				r = 3;
				addStructure(new GOTStructureTyroshFortWall.Long(false), i, k, r, true);
				r = 1;
				addStructure(new GOTStructureTyroshFortWall.Long(false), -i, k, r, true);
			}
			addStructure(new GOTStructureTyroshFortCorner(false), -50, -46, 0, true);
			addStructure(new GOTStructureTyroshFortCorner(false), 50, -46, 1, true);
			addStructure(new GOTStructureTyroshFortCorner(false), -50, 46, 3, true);
			addStructure(new GOTStructureTyroshFortCorner(false), 50, 46, 2, true);
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
		}

		protected void setupTown() {
			int k;
			int i;
			int r;
			int l;
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					setCivilianSpawnClass(spawner);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(40, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[]{-30, 30}) {
				for (int k1 : new int[]{-30, 30}) {
					addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							setWarriorSpawnClasses(spawner);
							spawner.setCheckRanges(40, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(60);
						}
					}, i1, k1, 0);
				}
			}
			addStructure(new GOTStructureTyroshBazaar(false), 1, -2, 0, true);
			addStructure(new GOTStructureTyroshLamp(false), 15, -2, 0, true);
			addStructure(new GOTStructureTyroshLamp(false), -13, -2, 0, true);
			addStructure(new GOTStructureTyroshLamp(false), 15, 18, 0, true);
			addStructure(new GOTStructureTyroshLamp(false), -13, 18, 0, true);
			addStructure(new GOTStructureTyroshWell(false), -16, 12, 1, true);
			addStructure(new GOTStructureTyroshWell(false), -16, 4, 1, true);
			addStructure(new GOTStructureTyroshTownFlowers(false), 18, 13, 3, true);
			addStructure(new GOTStructureTyroshTownFlowers(false), 18, 3, 3, true);
			for (l = 0; l <= 3; ++l) {
				i = -41 + l * 19;
				k = -7;
				r = 2;
				addStructure(new GOTStructureTyroshMansion(false), i, k, r, true);
				addStructure(new GOTStructureTyroshLamp(false), i + 6, k - 1, r, true);
				i = 24 - l * 19;
				k = 23;
				r = 0;
				addStructure(new GOTStructureTyroshMansion(false), i, k, r, true);
				addStructure(new GOTStructureTyroshLamp(false), i - 6, k + 1, r, true);
			}
			addStructure(new GOTStructureTyroshSmithy(false), -25, 9, 1, true);
			addStructure(new GOTStructureTyroshHouse(false), -25, 18, 1, true);
			addStructure(new GOTStructureTyroshHouse(false), -25, -2, 1, true);
			addStructure(new GOTStructureTyroshTownTree(false), -45, 8, 1, true);
			addStructure(new GOTStructureTyroshHouse(false), -50, 18, 3, true);
			addStructure(new GOTStructureTyroshHouse(false), -50, -2, 3, true);
			addStructure(new GOTStructureTyroshWell(false), -51, -14, 2, true);
			addStructure(new GOTStructureTyroshTownTree(false), -46, -29, 2, true);
			addStructure(new GOTStructureTyroshTownFlowers(false), -42, -32, 3, true);
			addStructure(new GOTStructureTyroshTownTree(false), -50, 30, 0, true);
			for (l = -3; l <= 3; ++l) {
				i = -56;
				k = -2 + l * 10;
				r = 1;
				addStructure(new GOTStructureTyroshHouse(false), i, k, r, true);
			}
			addStructure(new GOTStructureTyroshStatue(false), 26, 8, 3, true);
			addStructure(new GOTStructureTyroshHouse(false), 26, 18, 3, true);
			addStructure(new GOTStructureTyroshHouse(false), 26, -2, 3, true);
			for (l = -3; l <= 2; ++l) {
				i = 52;
				k = 8 + l * 10;
				r = 1;
				addStructure(new GOTStructureTyroshHouse(false), i, k, r, true);
			}
			addStructure(new GOTStructureTyroshSmithy(false), 41, -33, 3, true);
			for (l = -2; l <= 2; ++l) {
				i = 65;
				k = 3 + l * 14;
				r = 2;
				addStructure(new GOTStructureTyroshHouse(false), i, k, r, true);
			}
			addStructure(new GOTStructureTyroshWell(false), 57, -19, 2, true);
			addStructure(new GOTStructureTyroshLamp(false), 57, -16, 2, true);
			addStructure(new GOTStructureTyroshLamp(false), 57, -8, 2, true);
			addStructure(new GOTStructureTyroshTownTree(false), 57, 1, 2, true);
			addStructure(new GOTStructureTyroshLamp(false), 57, 4, 2, true);
			addStructure(new GOTStructureTyroshLamp(false), 57, 12, 2, true);
			addStructure(new GOTStructureTyroshTownTree(false), 57, 21, 2, true);
			addStructure(new GOTStructureTyroshLamp(false), 57, 24, 2, true);
			addStructure(new GOTStructureTyroshLamp(false), 57, 32, 2, true);
			for (l = 0; l <= 3; ++l) {
				i = 41 + l * 8;
				k = 34;
				r = 0;
				addStructure(new GOTStructureTyroshTownFlowers(false), i, k, r, true);
			}
			addStructure(new GOTStructureTyroshTownTree(false), 34, 25, 0, true);
			addStructure(new GOTStructureTyroshStables(false), -20, -30, 1, true);
			addStructure(new GOTStructureTyroshTavern(false), 17, -32, 1, true);
			addStructure(new GOTStructureTyroshLamp(false), 19, -28, 1, true);
			addStructure(new GOTStructureTyroshLamp(false), 19, -36, 1, true);
			addStructure(new GOTStructureTyroshLamp(false), -16, -32, 3, true);
			addStructure(new GOTStructureTyroshTownFlowers(false), 25, -32, 3, true);
			addStructure(new GOTStructureTyroshTownTree(false), 34, -29, 2, true);
			addStructure(new GOTStructureTyroshLamp(false), 34, -26, 2, true);
			addStructure(new GOTStructureTyroshLamp(false), 34, -18, 2, true);
			addStructure(new GOTStructureTyroshTownTree(false), 34, -9, 2, true);
			addStructure(new GOTStructureTyroshTownGate(false), 34, -47, 0, true);
			addStructure(new GOTStructureTyroshTownCorner(false), 73, -47, 0, true);
			addStructure(new GOTStructureTyroshTownCorner(false), -77, -43, 3, true);
			addStructure(new GOTStructureTyroshTownCorner(false), -73, 47, 2, true);
			addStructure(new GOTStructureTyroshTownCorner(false), 77, 43, 1, true);
			for (l = 0; l <= 6; ++l) {
				i = 68 - l * 4;
				k = -44;
				r = 0;
				if (l % 2 == 0) {
					addStructure(new GOTStructureTyroshTownWall.Short(false), i, k, r, true);
					continue;
				}
				addStructure(new GOTStructureTyroshTownWall.Long(false), i, k, r, true);
			}
			addStructure(new GOTStructureTyroshTownWall.Extra(false), 24, -44, 0, true);
			for (l = 0; l <= 22; ++l) {
				i = 20 - l * 4;
				k = -44;
				r = 0;
				if (l % 2 == 0) {
					addStructure(new GOTStructureTyroshTownWall.Short(false), i, k, r, true);
					continue;
				}
				addStructure(new GOTStructureTyroshTownWall.Long(false), i, k, r, true);
			}
			addStructure(new GOTStructureTyroshTownWall.SideMid(false), 74, 0, 1, true);
			addStructure(new GOTStructureTyroshTownWall.SideMid(false), -74, 0, 3, true);
			for (l = 1; l <= 9; ++l) {
				i = 74;
				k = 2 + l * 4;
				if (l % 2 == 1) {
					addStructure(new GOTStructureTyroshTownWall.Short(false), i, k, 1, true);
					addStructure(new GOTStructureTyroshTownWall.Short(false), i, -k, 1, true);
					addStructure(new GOTStructureTyroshTownWall.Short(false), -i, k, 3, true);
					addStructure(new GOTStructureTyroshTownWall.Short(false), -i, -k, 3, true);
					continue;
				}
				addStructure(new GOTStructureTyroshTownWall.Long(false), i, k, 1, true);
				addStructure(new GOTStructureTyroshTownWall.Long(false), i, -k, 1, true);
				addStructure(new GOTStructureTyroshTownWall.Long(false), -i, k, 3, true);
				addStructure(new GOTStructureTyroshTownWall.Long(false), -i, -k, 3, true);
			}
			for (l = -17; l <= 17; ++l) {
				i = l * 4;
				k = 44;
				r = 2;
				if (IntMath.mod(l, 2) == 1) {
					addStructure(new GOTStructureTyroshTownWall.Short(false), i, k, r, true);
					continue;
				}
				addStructure(new GOTStructureTyroshTownWall.Long(false), i, k, r, true);
			}
		}

		protected void setupVillage(Random random) {
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					setCivilianSpawnClass(spawner);
					spawner.setCheckRanges(64, -12, 12, 24);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					setWarriorSpawnClasses(spawner);
					spawner.setCheckRanges(64, -12, 12, 12);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawnRange(64);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureTyroshWell(false), 0, -2, 0, true);
			int rSquareEdge = 17;
			addStructure(new GOTStructureTyroshTavern(false), 0, rSquareEdge, 0, true);
			addStructure(new GOTStructureTyroshMansion(false), -3, -rSquareEdge, 2, true);
			addStructure(new GOTStructureTyroshMansion(false), -rSquareEdge, 3, 1, true);
			addStructure(new GOTStructureTyroshMansion(false), rSquareEdge, -3, 3, true);
			int backFenceX = 0;
			int backFenceZ = rSquareEdge + 19;
			int backFenceWidth = 12;
			int sideFenceX = 13;
			int sideFenceZ = rSquareEdge + 11;
			int sideFenceWidth = 8;
			int frontPostZ = sideFenceZ - sideFenceWidth - 1;
			addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), backFenceX, -backFenceZ, 0);
			addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), -sideFenceX, -sideFenceZ, 1);
			addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), sideFenceX, -sideFenceZ, 3);
			addStructure(new GOTStructureEssosVillagePost(false), -sideFenceX, -frontPostZ, 0);
			addStructure(new GOTStructureEssosVillagePost(false), sideFenceX, -frontPostZ, 0);
			addStructure(new GOTStructureEssosVillagePost(false), -sideFenceX, -backFenceZ, 0);
			addStructure(new GOTStructureEssosVillagePost(false), sideFenceX, -backFenceZ, 0);
			addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), -backFenceZ, backFenceX, 1);
			addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), -sideFenceZ, sideFenceX, 0);
			addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), -sideFenceZ, -sideFenceX, 2);
			addStructure(new GOTStructureEssosVillagePost(false), -frontPostZ, sideFenceX, 0);
			addStructure(new GOTStructureEssosVillagePost(false), -frontPostZ, -sideFenceX, 0);
			addStructure(new GOTStructureEssosVillagePost(false), -backFenceZ, sideFenceX, 0);
			addStructure(new GOTStructureEssosVillagePost(false), -backFenceZ, -sideFenceX, 0);
			addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(backFenceWidth, backFenceWidth), backFenceZ, backFenceX, 3);
			addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth, sideFenceWidth - 1), sideFenceZ, -sideFenceX, 2);
			addStructure(new GOTStructureEssosVillageFence(false).setLeftRightExtent(sideFenceWidth - 1, sideFenceWidth), sideFenceZ, sideFenceX, 0);
			addStructure(new GOTStructureEssosVillagePost(false), frontPostZ, -sideFenceX, 0);
			addStructure(new GOTStructureEssosVillagePost(false), frontPostZ, sideFenceX, 0);
			addStructure(new GOTStructureEssosVillagePost(false), backFenceZ, -sideFenceX, 0);
			addStructure(new GOTStructureEssosVillagePost(false), backFenceZ, sideFenceX, 0);
			int farmRange = 3;
			int farmStep = 14;
			int farmX = 55;
			for (int l = -farmRange; l <= farmRange; ++l) {
				int k = l * farmStep;
				int i = -farmX;
				int r = 1;
				if (random.nextInt(3) == 0) {
					addStructure(new GOTStructureHayBales(false), i, k, r);
				} else {
					addStructure(getRandomFarm(random), i, k, r);
				}
				i = farmX;
				r = 3;
				if (random.nextInt(3) == 0) {
					addStructure(new GOTStructureHayBales(false), i, k, r);
					continue;
				}
				addStructure(getRandomFarm(random), i, k, r);
			}
			int houseRange = 3;
			int houseStep = 17;
			int houseZ = 55;
			for (int l = -houseRange; l <= houseRange; ++l) {
				int i = l * houseStep;
				int k = -houseZ;
				int r = 2;
				addStructure(getRandomHouse(random), i, k, r);
				k = houseZ;
				r = 0;
				if (Math.abs(i) < 7) {
					continue;
				}
				addStructure(getRandomHouse(random), i, k, r);
			}
		}

		protected void setWarriorSpawnClasses(GOTEntityNPCRespawner spawner) {
			spawner.setSpawnClasses(GOTEntityTyroshSoldier.class, GOTEntityTyroshSoldierArcher.class);
		}

		@SuppressWarnings("unused")
		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}
	}

}
