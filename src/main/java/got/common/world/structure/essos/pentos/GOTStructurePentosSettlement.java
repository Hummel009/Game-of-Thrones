package got.common.world.structure.essos.pentos;

import com.google.common.math.IntMath;
import got.common.database.GOTBlocks;
import got.common.entity.essos.pentos.GOTEntityPentosGuard;
import got.common.entity.essos.pentos.GOTEntityPentosMan;
import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
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

public class GOTStructurePentosSettlement extends GOTStructureBaseSettlement {
	private Type type;
	private boolean forcedType;

	public GOTStructurePentosSettlement(GOTBiome biome, float f) {
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
		private final boolean forcedType;
		private Type type;

		private Instance(World world, int i, int k, Random random, LocationInfo loc, Collection<GOTFixer.SpawnInfo> spawnInfos, Type t, boolean b) {
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
					return GOTBezierType.PATH_SANDY;
				}
				imn = 45 - random.nextInt(3);
				imx = 50 + random.nextInt(3);
				kmn = 45 - random.nextInt(3);
				kmx = 50 + random.nextInt(3);
				if (i1 <= imx && k1 <= kmx && (i1 > imn || k1 > kmn) && (k < 0 || i1 > 7)) {
					return GOTBezierType.PATH_SANDY;
				}
				if (k < 0) {
					imn = 14;
					imx = 45;
					if (i1 + k1 >= imn + imn && i1 + k1 <= imx + imx && Math.abs(i1 - k1) <= (int) (2.5f + random.nextInt(3) * 2.0f)) {
						return GOTBezierType.PATH_SANDY;
					}
				}
				if (k > 0) {
					imn = 10;
					imx = imn + 5 + random.nextInt(3);
					kmn = 14;
					kmx = 45;
					if (k1 >= kmn && k1 <= kmx && i1 >= imn - random.nextInt(3) && i1 <= imx) {
						return GOTBezierType.PATH_SANDY;
					}
				}
			} else if (type == Type.TOWN && i1 <= 72 && k1 <= 42) {
				return GOTBezierType.PATH_SANDY;
			} else if (type == Type.FORT) {
				if (i1 <= 3 && k >= -45 && k <= -15) {
					return GOTBezierType.PATH_SANDY;
				}
				if (i1 <= 36 && k >= -27 && k <= -20) {
					return GOTBezierType.PATH_SANDY;
				}
				if (i1 >= 29 && i1 <= 36 && k >= -27 && k <= 39 && (k < -7 || k > 7)) {
					return GOTBezierType.PATH_SANDY;
				}
				if (i1 <= 36 && k >= 20 && k <= 27) {
					return GOTBezierType.PATH_SANDY;
				}
			}
			return null;
		}

		private static GOTStructureBase getRandomFarm(Random random) {
			if (random.nextBoolean()) {
				return new GOTStructurePentosFarm(false);
			}
			return new GOTStructurePentosPasture(false);
		}

		private static GOTStructureBase getRandomHouse(Random random) {
			if (random.nextInt(6) == 0) {
				return new GOTStructurePentosSmithy(false);
			}
			if (random.nextInt(6) == 0) {
				return new GOTStructurePentosStables(false);
			}
			return new GOTStructurePentosHouse(false);
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			return block == Blocks.dirt && meta == 1 || block == GOTBlocks.dirtPath && meta == 0 || block == GOTBlocks.brick1 && meta == 15 || block == GOTBlocks.brick3 && meta == 11 || block == GOTBlocks.pillar1 && meta == 5 || block == Blocks.sand && meta == 0 || block == Blocks.sandstone && meta == 0 || block == GOTBlocks.slabSingleDirt && meta == 0 || block == GOTBlocks.slabSingleDirt && meta == 1 || block == GOTBlocks.slabSingleSand && meta == 0 || block == GOTBlocks.slabSingle4 && meta == 0 || block == GOTBlocks.slabSingle7 && meta == 1 || block == GOTBlocks.slabSingle4 && meta == 7;
		}

		private void placeChampionRespawner() {
			addStructure(new StructureRespawner(), 0, 0, 0);
		}

		private static void setCivilianSpawnClass(GOTEntityNPCRespawner spawner) {
			spawner.setSpawnClass1(GOTEntityPentosMan.class);
		}

		private void setupFort(Random random) {
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
					spawner.setBlockEnemySpawns(60);
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
							spawner.setBlockEnemySpawns(35);
						}
					}, i1, k1, 0);
				}
			}
			placeChampionRespawner();
			addStructure(new GOTStructurePentosFortress(false), 0, -15, 0, true);
			addStructure(new GOTStructurePentosBarracks(false), -33, -8, 0, true);
			addStructure(new GOTStructurePentosBarracks(false), 32, -8, 0, true);
			addStructure(new GOTStructurePentosTower(false), -43, -36, 2, true);
			addStructure(new GOTStructurePentosTower(false), 43, -36, 2, true);
			addStructure(new GOTStructurePentosTower(false), -43, 36, 0, true);
			addStructure(new GOTStructurePentosTower(false), 43, 36, 0, true);
			for (l = 0; l <= 2; ++l) {
				i = 10 + l * 11;
				k = -28;
				r = 2;
				addStructure(getRandomFarm(random), i, k, r);
				addStructure(getRandomFarm(random), -i, k, r);
			}
			addStructure(new GOTStructurePentosTraining(false), 0, 27, 0, true);
			addStructure(new GOTStructurePentosStables(false), -29, 33, 3, true);
			addStructure(new GOTStructurePentosStables(false), 29, 37, 1, true);
			addStructure(new GOTStructurePentosFortGate(false), 0, -47, 0, true);
			for (l = 0; l <= 9; ++l) {
				i = 8 + l * 4;
				k = -46;
				r = 0;
				addStructure(new GOTStructurePentosFortWall.Long(false), -i, k, r, true);
				addStructure(new GOTStructurePentosFortWall.Long(false), i, k, r, true);
			}
			for (l = -11; l <= 11; ++l) {
				i = l * 4;
				k = 46;
				r = 2;
				addStructure(new GOTStructurePentosFortWall.Long(false), i, k, r, true);
			}
			for (l = -10; l <= 10; ++l) {
				i = -50;
				k = l * 4;
				r = 3;
				addStructure(new GOTStructurePentosFortWall.Long(false), i, k, r, true);
				r = 1;
				addStructure(new GOTStructurePentosFortWall.Long(false), -i, k, r, true);
			}
			addStructure(new GOTStructurePentosFortCorner(false), -50, -46, 0, true);
			addStructure(new GOTStructurePentosFortCorner(false), 50, -46, 1, true);
			addStructure(new GOTStructurePentosFortCorner(false), -50, 46, 3, true);
			addStructure(new GOTStructurePentosFortCorner(false), 50, 46, 2, true);
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

		private void setupTown() {
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
					spawner.setBlockEnemySpawns(60);
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
							spawner.setBlockEnemySpawns(60);
						}
					}, i1, k1, 0);
				}
			}
			addStructure(new GOTStructurePentosBazaar(false), 1, -2, 0, true);
			addStructure(new GOTStructurePentosLamp(false), 15, -2, 0, true);
			addStructure(new GOTStructurePentosLamp(false), -13, -2, 0, true);
			addStructure(new GOTStructurePentosLamp(false), 15, 18, 0, true);
			addStructure(new GOTStructurePentosLamp(false), -13, 18, 0, true);
			addStructure(new GOTStructurePentosWell(false), -16, 12, 1, true);
			addStructure(new GOTStructurePentosWell(false), -16, 4, 1, true);
			addStructure(new GOTStructurePentosTownFlowers(false), 18, 13, 3, true);
			addStructure(new GOTStructurePentosTownFlowers(false), 18, 3, 3, true);
			for (l = 0; l <= 3; ++l) {
				i = -41 + l * 19;
				k = -7;
				r = 2;
				addStructure(new GOTStructurePentosMansion(false), i, k, r, true);
				addStructure(new GOTStructurePentosLamp(false), i + 6, k - 1, r, true);
				i = 24 - l * 19;
				k = 23;
				r = 0;
				addStructure(new GOTStructurePentosMansion(false), i, k, r, true);
				addStructure(new GOTStructurePentosLamp(false), i - 6, k + 1, r, true);
			}
			addStructure(new GOTStructurePentosSmithy(false), -25, 9, 1, true);
			addStructure(new GOTStructurePentosHouse(false), -25, 18, 1, true);
			addStructure(new GOTStructurePentosHouse(false), -25, -2, 1, true);
			addStructure(new GOTStructurePentosTownTree(false), -45, 8, 1, true);
			addStructure(new GOTStructurePentosHouse(false), -50, 18, 3, true);
			addStructure(new GOTStructurePentosHouse(false), -50, -2, 3, true);
			addStructure(new GOTStructurePentosWell(false), -51, -14, 2, true);
			addStructure(new GOTStructurePentosTownTree(false), -46, -29, 2, true);
			addStructure(new GOTStructurePentosTownFlowers(false), -42, -32, 3, true);
			addStructure(new GOTStructurePentosTownTree(false), -50, 30, 0, true);
			for (l = -3; l <= 3; ++l) {
				i = -56;
				k = -2 + l * 10;
				r = 1;
				addStructure(new GOTStructurePentosHouse(false), i, k, r, true);
			}
			addStructure(new GOTStructurePentosStatue(false), 26, 8, 3, true);
			addStructure(new GOTStructurePentosHouse(false), 26, 18, 3, true);
			addStructure(new GOTStructurePentosHouse(false), 26, -2, 3, true);
			for (l = -3; l <= 2; ++l) {
				i = 52;
				k = 8 + l * 10;
				r = 1;
				addStructure(new GOTStructurePentosHouse(false), i, k, r, true);
			}
			addStructure(new GOTStructurePentosSmithy(false), 41, -33, 3, true);
			for (l = -2; l <= 2; ++l) {
				i = 65;
				k = 3 + l * 14;
				r = 2;
				addStructure(new GOTStructurePentosHouse(false), i, k, r, true);
			}
			addStructure(new GOTStructurePentosWell(false), 57, -19, 2, true);
			addStructure(new GOTStructurePentosLamp(false), 57, -16, 2, true);
			addStructure(new GOTStructurePentosLamp(false), 57, -8, 2, true);
			addStructure(new GOTStructurePentosTownTree(false), 57, 1, 2, true);
			addStructure(new GOTStructurePentosLamp(false), 57, 4, 2, true);
			addStructure(new GOTStructurePentosLamp(false), 57, 12, 2, true);
			addStructure(new GOTStructurePentosTownTree(false), 57, 21, 2, true);
			addStructure(new GOTStructurePentosLamp(false), 57, 24, 2, true);
			addStructure(new GOTStructurePentosLamp(false), 57, 32, 2, true);
			for (l = 0; l <= 3; ++l) {
				i = 41 + l * 8;
				k = 34;
				r = 0;
				addStructure(new GOTStructurePentosTownFlowers(false), i, k, r, true);
			}
			addStructure(new GOTStructurePentosTownTree(false), 34, 25, 0, true);
			addStructure(new GOTStructurePentosStables(false), -20, -30, 1, true);
			addStructure(new GOTStructurePentosTavern(false), 17, -32, 1, true);
			addStructure(new GOTStructurePentosLamp(false), 19, -28, 1, true);
			addStructure(new GOTStructurePentosLamp(false), 19, -36, 1, true);
			addStructure(new GOTStructurePentosLamp(false), -16, -32, 3, true);
			addStructure(new GOTStructurePentosTownFlowers(false), 25, -32, 3, true);
			addStructure(new GOTStructurePentosTownTree(false), 34, -29, 2, true);
			addStructure(new GOTStructurePentosLamp(false), 34, -26, 2, true);
			addStructure(new GOTStructurePentosLamp(false), 34, -18, 2, true);
			addStructure(new GOTStructurePentosTownTree(false), 34, -9, 2, true);
			addStructure(new GOTStructurePentosTownGate(false), 34, -47, 0, true);
			addStructure(new GOTStructurePentosTownCorner(false), 73, -47, 0, true);
			addStructure(new GOTStructurePentosTownCorner(false), -77, -43, 3, true);
			addStructure(new GOTStructurePentosTownCorner(false), -73, 47, 2, true);
			addStructure(new GOTStructurePentosTownCorner(false), 77, 43, 1, true);
			for (l = 0; l <= 6; ++l) {
				i = 68 - l * 4;
				k = -44;
				r = 0;
				if (l % 2 == 0) {
					addStructure(new GOTStructurePentosTownWall.Short(false), i, k, r, true);
					continue;
				}
				addStructure(new GOTStructurePentosTownWall.Long(false), i, k, r, true);
			}
			addStructure(new GOTStructurePentosTownWall.Extra(false), 24, -44, 0, true);
			for (l = 0; l <= 22; ++l) {
				i = 20 - l * 4;
				k = -44;
				r = 0;
				if (l % 2 == 0) {
					addStructure(new GOTStructurePentosTownWall.Short(false), i, k, r, true);
					continue;
				}
				addStructure(new GOTStructurePentosTownWall.Long(false), i, k, r, true);
			}
			addStructure(new GOTStructurePentosTownWall.SideMid(false), 74, 0, 1, true);
			addStructure(new GOTStructurePentosTownWall.SideMid(false), -74, 0, 3, true);
			for (l = 1; l <= 9; ++l) {
				i = 74;
				k = 2 + l * 4;
				if (l % 2 == 1) {
					addStructure(new GOTStructurePentosTownWall.Short(false), i, k, 1, true);
					addStructure(new GOTStructurePentosTownWall.Short(false), i, -k, 1, true);
					addStructure(new GOTStructurePentosTownWall.Short(false), -i, k, 3, true);
					addStructure(new GOTStructurePentosTownWall.Short(false), -i, -k, 3, true);
					continue;
				}
				addStructure(new GOTStructurePentosTownWall.Long(false), i, k, 1, true);
				addStructure(new GOTStructurePentosTownWall.Long(false), i, -k, 1, true);
				addStructure(new GOTStructurePentosTownWall.Long(false), -i, k, 3, true);
				addStructure(new GOTStructurePentosTownWall.Long(false), -i, -k, 3, true);
			}
			for (l = -17; l <= 17; ++l) {
				i = l * 4;
				k = 44;
				r = 2;
				if (IntMath.mod(l, 2) == 1) {
					addStructure(new GOTStructurePentosTownWall.Short(false), i, k, r, true);
					continue;
				}
				addStructure(new GOTStructurePentosTownWall.Long(false), i, k, r, true);
			}
		}

		private void setupVillage(Random random) {
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					setCivilianSpawnClass(spawner);
					spawner.setCheckRanges(64, -12, 12, 24);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawns(64);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					setWarriorSpawnClasses(spawner);
					spawner.setCheckRanges(64, -12, 12, 12);
					spawner.setSpawnRanges(32, -6, 6, 32);
					spawner.setBlockEnemySpawns(64);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructurePentosWell(false), 0, -2, 0, true);
			int rSquareEdge = 17;
			addStructure(new GOTStructurePentosTavern(false), 0, rSquareEdge, 0, true);
			addStructure(new GOTStructurePentosMansion(false), -3, -rSquareEdge, 2, true);
			addStructure(new GOTStructurePentosMansion(false), -rSquareEdge, 3, 1, true);
			addStructure(new GOTStructurePentosMansion(false), rSquareEdge, -3, 3, true);
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

		private static void setWarriorSpawnClasses(GOTEntityNPCRespawner spawner) {
			spawner.setSpawnClass1(GOTEntityPentosGuard.class);
		}

		@SuppressWarnings("unused")
		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}

		private static class StructureRespawner extends GOTStructureNPCRespawner {
			private StructureRespawner() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityPentosGuard.class);
				spawner.setCheckRanges(60, -12, 12, 4);
				spawner.setSpawnRanges(24, -6, 6, 32);
			}
		}
	}
}