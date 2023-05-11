package got.common.world.structure.westeros.reach;

import com.google.common.math.IntMath;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.westeros.reach.GOTEntityReachMan;
import got.common.entity.westeros.reach.GOTEntityReachSoldier;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.common.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureReachCity extends GOTVillageGen {
	public boolean isTown;
	public boolean isCastle;

	public GOTStructureReachCity(GOTBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 6;
		fixedVillageChunkRadius = 5;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public GOTStructureReachCity setIsCastle() {
		isCastle = true;
		fixedVillageChunkRadius = 3;
		return this;
	}

	public GOTStructureReachCity setIsTown() {
		isTown = true;
		fixedVillageChunkRadius = 6;
		return this;
	}

	public enum VillageType {
		VILLAGE, TOWN, FORT
	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;

		public Instance(GOTStructureReachCity village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			switch (villageType) {
				case TOWN:
					setupTown(random);
					break;
				case FORT:
					setupCastle(random);
					break;
				case VILLAGE:
					setupVillage(random);
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
				int omn = 53 - random.nextInt(4);
				int omx = 60 + random.nextInt(4);
				if (dSq > omn * omn && dSq < omx * omx || dSq < 2809 && Math.abs(i1 - k1) <= 2 + random.nextInt(4)) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			if (villageType == VillageType.TOWN && i1 <= 80 && k1 <= 80) {
				return GOTBezierType.PATH_DIRTY;
			}
			if (villageType == VillageType.FORT) {
				if (i1 <= 1 && (k >= 13 || k <= -12) && k1 <= 36 || k1 <= 1 && i1 >= 12 && i1 <= 36 || k >= 26 && k <= 28 && i1 <= 12) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			return null;
		}

		public GOTStructureBase getRandomFarm(Random random) {
			if (random.nextBoolean()) {
				if (random.nextBoolean()) {
					return new GOTStructureReachVillageFarm.Animals(false);
				}
				return new GOTStructureReachVillageFarm.Crops(false);
			}
			return new GOTStructureReachVillageFarm.Tree(false);
		}

		public GOTStructureBase getRandomHouse(Random random) {
			if (random.nextInt(5) == 0) {
				int i = random.nextInt(3);
				switch (i) {
					case 0:
						return new GOTStructureReachStables(false);
					case 1:
						return new GOTStructureReachSmithy(false);
					case 2:
						return new GOTStructureReachBarn(false);
					default:
						break;
				}
			}
			return new GOTStructureReachHouse(false);
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		public void setupCastle(Random random) {
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityReachSoldier.class);
					spawner.setCheckRanges(50, -12, 12, 16);
					spawner.setSpawnRanges(30, -6, 6, 40);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[]{-20, 20}) {
				for (int k1 : new int[]{-20, 20}) {
					addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							spawner.setSpawnClass(GOTEntityReachSoldier.class);
							spawner.setCheckRanges(20, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 40);
							spawner.setBlockEnemySpawnRange(40);
						}
					}, i1, k1, 0);
				}
			}
			addStructure(new GOTStructureReachFortress(false), 0, 12, 2, true);
			addStructure(new GOTStructureWesterosFortGate(false), 0, -37, 0, true);
			addStructure(new GOTStructureWesterosFortWall.Right(false), -11, -37, 0, true);
			addStructure(new GOTStructureWesterosFortWall.Left(false), 11, -37, 0, true);
			addStructure(new GOTStructureReachWatchtower(false), -23, -33, 2, true);
			addStructure(new GOTStructureReachWatchtower(false), 23, -33, 2, true);
			addStructure(new GOTStructureWesterosFortGate(false), -37, 0, 3, true);
			addStructure(new GOTStructureWesterosFortWall.Left(false), -37, -11, 3, true);
			addStructure(new GOTStructureWesterosFortWall.Right(false), -37, 11, 3, true);
			addStructure(new GOTStructureReachWatchtower(false), -33, -23, 1, true);
			addStructure(new GOTStructureReachWatchtower(false), -33, 23, 1, true);
			addStructure(new GOTStructureWesterosFortGate(false), 0, 37, 2, true);
			addStructure(new GOTStructureWesterosFortWall.Left(false), -11, 37, 2, true);
			addStructure(new GOTStructureWesterosFortWall.Right(false), 11, 37, 2, true);
			addStructure(new GOTStructureReachWatchtower(false), -23, 33, 0, true);
			addStructure(new GOTStructureReachWatchtower(false), 23, 33, 0, true);
			addStructure(new GOTStructureWesterosFortGate(false), 37, 0, 1, true);
			addStructure(new GOTStructureWesterosFortWall.Right(false), 37, -11, 1, true);
			addStructure(new GOTStructureWesterosFortWall.Left(false), 37, 11, 1, true);
			addStructure(new GOTStructureReachWatchtower(false), 33, -23, 3, true);
			addStructure(new GOTStructureReachWatchtower(false), 33, 23, 3, true);
			addStructure(new GOTStructureWesterosFortWallCorner(false), -30, -30, 3);
			addStructure(new GOTStructureWesterosFortWallCorner(false), -30, 30, 2);
			addStructure(new GOTStructureWesterosFortWallCorner(false), 30, 30, 1);
			addStructure(new GOTStructureWesterosFortWallCorner(false), 30, -30, 0);
			addStructure(new GOTStructureReachStables(false), -24, 2, 0);
			addStructure(new GOTStructureReachStables(false), -24, -2, 2);
			addStructure(new GOTStructureReachSmithy(false), 24, 1, 0);
			addStructure(new GOTStructureReachSmithy(false), 24, -1, 2);
			addStructure(new GOTStructureReachStoneHouse(false), -3, -25, 1);
			addStructure(new GOTStructureReachStoneHouse(false), 3, -25, 3);
			addStructure(new GOTStructureReachVillageFarm.Crops(false), -18, -21, 1);
			addStructure(new GOTStructureReachVillageFarm.Crops(false), 18, -21, 3);
			addStructure(new GOTStructureWesterosWell(false), -12, 27, 1);
			addStructure(new GOTStructureWesterosWell(false), 12, 27, 3);
		}

		public void setupTown(Random random) {
			int l;
			int wallX;
			boolean outerTavern = random.nextBoolean();
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityReachMan.class);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(60, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[]{-40, 40}) {
				int[] arrn = {-40, 40};
				int n = arrn.length;
				for (int k1 : arrn) {
					addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							spawner.setSpawnClass(GOTEntityReachSoldier.class);
							spawner.setCheckRanges(40, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(60);
						}
					}, i1, k1, 0);
				}
			}
			addStructure(new GOTStructureWesterosWell(false), 0, -4, 0, true);
			int stallPos = 12;
			for (int k1 = -1; k1 <= 1; ++k1) {
				int k2 = k1 * stallPos;
				if (random.nextInt(3) != 0) {
					addStructure(GOTStructureReachMarketStall.getRandomStall(random, false), -stallPos + 3, k2, 1, true);
				}
				if (random.nextInt(3) == 0) {
					continue;
				}
				addStructure(GOTStructureReachMarketStall.getRandomStall(random, false), stallPos - 3, k2, 3, true);
			}
			if (random.nextInt(3) != 0) {
				addStructure(GOTStructureReachMarketStall.getRandomStall(random, false), 0, stallPos - 3, 0, true);
			}
			if (random.nextInt(3) != 0) {
				addStructure(GOTStructureReachMarketStall.getRandomStall(random, false), 0, -stallPos + 3, 2, true);
			}
			int flowerX = 12;
			int flowerZ = 18;
			for (int i1 : new int[]{-flowerX, flowerX}) {
				addStructure(new GOTStructureWesterosTownGarden(false), i1, flowerZ, 0, true);
				addStructure(new GOTStructureWesterosTownGarden(false), i1, -flowerZ, 2, true);
				addStructure(new GOTStructureWesterosTownGarden(false), -flowerZ, i1, 1, true);
				addStructure(new GOTStructureWesterosTownGarden(false), flowerZ, i1, 3, true);
			}
			int lampZ = 21;
			for (int i1 : new int[]{-1, 1}) {
				int lampX = i1 * 6;
				addStructure(new GOTStructureWesterosLampPost(false), lampX, lampZ, 0, true);
				addStructure(new GOTStructureWesterosLampPost(false), lampX, -lampZ, 2, true);
				if (i1 != -1) {
					addStructure(new GOTStructureWesterosLampPost(false), -lampZ, lampX, 1, true);
				}
				addStructure(new GOTStructureWesterosLampPost(false), lampZ, lampX, 3, true);
			}
			int houseX = 24;
			for (int k1 = -1; k1 <= 1; ++k1) {
				int houseZ = k1 * 12;
				if (k1 == 1) {
					addStructure(new GOTStructureReachStoneHouse(false), -houseX, houseZ, 1, true);
					addStructure(new GOTStructureReachStoneHouse(false), houseX, houseZ, 3, true);
				}
				if (k1 == 0) {
					continue;
				}
				addStructure(new GOTStructureReachStoneHouse(false), houseZ, houseX, 0, true);
				addStructure(new GOTStructureReachStoneHouse(false), houseZ, -houseX, 2, true);
			}
			addStructure(new GOTStructureReachSmithy(false), 0, -26, 2, true);
			addStructure(new GOTStructureWesterosObelisk(false), 0, 27, 0, true);
			addStructure(new GOTStructureReachTavern(false), -houseX, -5, 1, true);
			addStructure(new GOTStructureWesterosTownTrees(false), -47, -13, 2, true);
			addStructure(new GOTStructureWesterosTownTrees(false), -47, 1, 0, true);
			for (int i1 : new int[]{-43, -51}) {
				addStructure(new GOTStructureWesterosTownBench(false), i1, -9, 2, true);
				addStructure(new GOTStructureWesterosTownBench(false), i1, -3, 0, true);
			}
			addStructure(new GOTStructureReachBath(false), houseX + 2, -6, 3, true);
			addStructure(new GOTStructureWesterosTownGarden(false), 51, -13, 2, true);
			addStructure(new GOTStructureWesterosTownGarden(false), 51, 1, 0, true);
			addStructure(new GOTStructureWesterosTownGarden(false), 52, -6, 3, true);
			int wellX = 22;
			int wellZ = 31;
			for (int i1 : new int[]{-wellX, wellX}) {
				addStructure(new GOTStructureWesterosWell(false), i1, -wellZ, 2, true);
				addStructure(new GOTStructureWesterosWell(false), i1, wellZ, 0, true);
				addStructure(new GOTStructureWesterosWell(false), -wellZ, i1, 1, true);
				addStructure(new GOTStructureWesterosWell(false), wellZ, i1, 3, true);
			}
			houseX = 54;
			for (int k1 = -2; k1 <= 2; ++k1) {
				int houseZ = k1 * 12;
				if (k1 == -2 || k1 >= 1) {
					addStructure(new GOTStructureReachStoneHouse(false), -houseX, houseZ, 3, true);
					addStructure(new GOTStructureReachStoneHouse(false), houseX, houseZ, 1, true);
				}
				addStructure(new GOTStructureReachStoneHouse(false), houseZ, houseX, 2, true);
				addStructure(new GOTStructureReachStoneHouse(false), houseZ, -houseX, 0, true);
			}
			int treeX = 47;
			int treeZ = 35;
			for (int i1 : new int[]{-treeX, treeX}) {
				addStructure(new GOTStructureWesterosTownTrees(false), i1, -treeZ, 0, true);
				addStructure(new GOTStructureWesterosTownTrees(false), i1, treeZ, 2, true);
				addStructure(new GOTStructureWesterosTownTrees(false), -treeZ, i1, 3, true);
				addStructure(new GOTStructureWesterosTownTrees(false), treeZ, i1, 1, true);
			}
			houseX = 64;
			int lampX = 59;
			for (int k1 = -4; k1 <= 4; ++k1) {
				boolean treepiece;
				int houseZ = k1 * 12;
				treepiece = IntMath.mod(k1, 2) == 1;
				if (treepiece) {
					addStructure(new GOTStructureReachVillageFarm.Tree(false), -houseX - 2, houseZ, 1, true);
					addStructure(new GOTStructureReachVillageFarm.Tree(false), houseX + 2, houseZ, 3, true);
				} else {
					addStructure(new GOTStructureReachStoneHouse(false), -houseX, houseZ, 1, true);
					addStructure(new GOTStructureReachStoneHouse(false), houseX, houseZ, 3, true);
				}
				if (treepiece) {
					addStructure(new GOTStructureReachVillageFarm.Tree(false), houseZ, -houseX - 2, 2, true);
				} else {
					addStructure(new GOTStructureReachStoneHouse(false), houseZ, -houseX, 2, true);
				}
				if (Math.abs(k1) >= 2 && (!outerTavern || k1 <= 2)) {
					if (treepiece) {
						addStructure(new GOTStructureReachVillageFarm.Tree(false), houseZ, houseX + 2, 0, true);
					} else {
						addStructure(new GOTStructureReachStoneHouse(false), houseZ, houseX, 0, true);
					}
				}
				addStructure(new GOTStructureWesterosLampPost(false), -lampX, houseZ, 1, true);
				addStructure(new GOTStructureWesterosLampPost(false), lampX, houseZ, 3, true);
				addStructure(new GOTStructureWesterosLampPost(false), houseZ, lampX, 0, true);
				addStructure(new GOTStructureWesterosLampPost(false), houseZ, -lampX, 2, true);
			}
			if (outerTavern) {
				addStructure(new GOTStructureReachTavern(false), 44, houseX, 0, true);
			}
			int gardenX = 42;
			int gardenZ = 48;
			addStructure(new GOTStructureReachVillageFarm.Tree(false), -gardenX, -gardenZ, 1, true);
			addStructure(new GOTStructureReachVillageFarm.Tree(false), -gardenX, gardenZ, 1, true);
			addStructure(new GOTStructureReachVillageFarm.Tree(false), gardenX, -gardenZ, 3, true);
			addStructure(new GOTStructureReachVillageFarm.Tree(false), gardenX, gardenZ, 3, true);
			int obeliskX = 62;
			int obeliskZ = 66;
			addStructure(new GOTStructureWesterosObelisk(false), -obeliskX, -obeliskZ, 1, true);
			addStructure(new GOTStructureWesterosObelisk(false), -obeliskX, obeliskZ, 1, true);
			addStructure(new GOTStructureWesterosObelisk(false), obeliskX, -obeliskZ, 3, true);
			addStructure(new GOTStructureWesterosObelisk(false), obeliskX, obeliskZ, 3, true);
			wellX = 64;
			wellZ = 57;
			addStructure(new GOTStructureWesterosWell(false), -wellX, -wellZ, 1, true);
			addStructure(new GOTStructureWesterosWell(false), -wellX, wellZ, 1, true);
			addStructure(new GOTStructureWesterosWell(false), wellX, -wellZ, 3, true);
			addStructure(new GOTStructureWesterosWell(false), wellX, wellZ, 3, true);
			addStructure(new GOTStructureWesterosWell(false), -wellZ, -wellX, 2, true);
			addStructure(new GOTStructureWesterosWell(false), wellZ, -wellX, 2, true);
			addStructure(new GOTStructureWesterosWell(false), -wellZ, wellX, 0, true);
			addStructure(new GOTStructureWesterosWell(false), wellZ, wellX, 0, true);
			treeX = 75;
			treeZ = 61;
			addStructure(new GOTStructureWesterosTownTrees(false), -treeX, -treeZ, 1, true);
			addStructure(new GOTStructureWesterosTownTrees(false), -treeX, treeZ, 1, true);
			addStructure(new GOTStructureWesterosTownTrees(false), treeX, -treeZ, 3, true);
			addStructure(new GOTStructureWesterosTownTrees(false), treeX, treeZ, 3, true);
			addStructure(new GOTStructureWesterosTownTrees(false), -treeZ, -treeX, 2, true);
			addStructure(new GOTStructureWesterosTownTrees(false), treeZ, -treeX, 2, true);
			addStructure(new GOTStructureWesterosTownTrees(false), -treeZ, treeX, 0, true);
			addStructure(new GOTStructureWesterosTownTrees(false), treeZ, treeX, 0, true);
			addStructure(new GOTStructureWesterosTownTrees(false), -14, 71, 1, true);
			addStructure(new GOTStructureWesterosTownTrees(false), 14, 71, 3, true);
			for (int k1 : new int[]{67, 75}) {
				addStructure(new GOTStructureWesterosTownBench(false), -10, k1, 1, true);
				addStructure(new GOTStructureWesterosTownBench(false), 10, k1, 3, true);
			}
			addStructure(new GOTStructureReachGatehouse(false), 0, 84, 2, true);
			addStructure(new GOTStructureWesterosLampPost(false), -4, 73, 0, true);
			addStructure(new GOTStructureWesterosLampPost(false), 4, 73, 0, true);
			int towerX = 78;
			int towerZ = 74;
			for (int i1 : new int[]{-towerX, towerX}) {
				addStructure(new GOTStructureReachWatchtower(false), i1, -towerZ, 2, true);
				addStructure(new GOTStructureReachWatchtower(false), i1, towerZ, 0, true);
			}
			int wallZ = 82;
			int wallEndX = 76;
			for (l = 0; l <= 3; ++l) {
				wallX = 12 + l * 16;
				addStructure(GOTStructureWesterosTownWall.Left(false), -wallX, wallZ, 2, true);
				addStructure(GOTStructureWesterosTownWall.Right(false), wallX, wallZ, 2, true);
			}
			addStructure(GOTStructureWesterosTownWall.LeftEndShort(false), -wallEndX, wallZ, 2, true);
			addStructure(GOTStructureWesterosTownWall.RightEndShort(false), wallEndX, wallZ, 2, true);
			addStructure(GOTStructureWesterosTownWall.Centre(false), -wallZ, 0, 3, true);
			addStructure(GOTStructureWesterosTownWall.Centre(false), wallZ, 0, 1, true);
			addStructure(GOTStructureWesterosTownWall.Centre(false), 0, -wallZ, 0, true);
			for (l = 0; l <= 3; ++l) {
				wallX = 12 + l * 16;
				addStructure(GOTStructureWesterosTownWall.Left(false), -wallZ, -wallX, 3, true);
				addStructure(GOTStructureWesterosTownWall.Right(false), -wallZ, wallX, 3, true);
				addStructure(GOTStructureWesterosTownWall.Left(false), wallZ, wallX, 1, true);
				addStructure(GOTStructureWesterosTownWall.Right(false), wallZ, -wallX, 1, true);
				addStructure(GOTStructureWesterosTownWall.Left(false), wallX, -wallZ, 0, true);
				addStructure(GOTStructureWesterosTownWall.Right(false), -wallX, -wallZ, 0, true);
			}
			addStructure(GOTStructureWesterosTownWall.LeftEnd(false), -wallZ, -wallEndX, 3, true);
			addStructure(GOTStructureWesterosTownWall.RightEnd(false), -wallZ, wallEndX, 3, true);
			addStructure(GOTStructureWesterosTownWall.LeftEnd(false), wallZ, wallEndX, 1, true);
			addStructure(GOTStructureWesterosTownWall.RightEnd(false), wallZ, -wallEndX, 1, true);
			addStructure(GOTStructureWesterosTownWall.LeftEndShort(false), wallEndX, -wallZ, 0, true);
			addStructure(GOTStructureWesterosTownWall.RightEndShort(false), -wallEndX, -wallZ, 0, true);
		}

		public void setupVillage(Random random) {
			addStructure(new GOTStructureWesterosWell(false), 0, -4, 0, true);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityReachMan.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityReachSoldier.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureReachCottage(false), -21, 0, 1);
			addStructure(new GOTStructureReachCottage(false), 0, -21, 2);
			addStructure(new GOTStructureReachCottage(false), 21, 0, 3);
			addStructure(new GOTStructureReachTavern(false), 0, 21, 0);
			if (random.nextBoolean()) {
				if (random.nextInt(3) == 0) {
					addStructure(GOTStructureReachMarketStall.getRandomStall(random, false), -9, -12, 1);
				}
				if (random.nextInt(3) == 0) {
					addStructure(GOTStructureReachMarketStall.getRandomStall(random, false), 9, -12, 3);
				}
				if (random.nextInt(3) == 0) {
					addStructure(GOTStructureReachMarketStall.getRandomStall(random, false), -9, 12, 1);
				}
				if (random.nextInt(3) == 0) {
					addStructure(GOTStructureReachMarketStall.getRandomStall(random, false), 9, 12, 3);
				}
			}
			int houses = 20;
			float frac = 1.0f / houses;
			float turn = 0.0f;
			while (turn < 1.0f) {
				int k;
				int l;
				int i;
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
				if (random.nextBoolean()) {
					l = 61;
					i = Math.round(l * cos);
					k = Math.round(l * sin);
					addStructure(getRandomHouse(random), i, k, r);
					continue;
				}
				if (random.nextInt(3) == 0) {
					continue;
				}
				l = 65;
				i = Math.round(l * cos);
				k = Math.round(l * sin);
				addStructure(new GOTStructureHayBales(false), i, k, r);
			}
			int signPos = Math.round(50.0f * MathHelper.cos(0.7853981633974483f));
			int signDisp = Math.round(7.0f * MathHelper.cos(0.7853981633974483f));
			addStructure(new GOTStructureWesterosVillageSign(false), -signPos, -signPos + signDisp, 1);
			addStructure(new GOTStructureWesterosVillageSign(false), signPos, -signPos + signDisp, 3);
			addStructure(new GOTStructureWesterosVillageSign(false), -signPos, signPos - signDisp, 1);
			addStructure(new GOTStructureWesterosVillageSign(false), signPos, signPos - signDisp, 3);
			int farmX = 38;
			int farmZ = 17;
			int farmSize = 6;
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), -farmX + farmSize, -farmZ, 1);
			}
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), -farmZ + farmSize, -farmX, 1);
			}
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), farmX - farmSize, -farmZ, 3);
			}
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), farmZ - farmSize, -farmX, 3);
			}
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), -farmX + farmSize, farmZ, 1);
			}
			if (random.nextBoolean()) {
				addStructure(getRandomFarm(random), farmX - farmSize, farmZ, 3);
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			if (isTown) {
				villageType = VillageType.TOWN;
			} else if (isCastle || random.nextInt(4) == 0) {
				villageType = VillageType.FORT;
			} else {
				villageType = VillageType.VILLAGE;
			}
		}

	}

}
