package got.common.world.structure.westeros.dorne;

import java.util.Random;

import com.google.common.math.IntMath;

import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.entity.westeros.dorne.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.common.GOTStructureWesterosVillageSign;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTStructureDorneCity extends GOTVillageGen {
	public boolean isTown;
	public boolean isCastle;

	public GOTStructureDorneCity(GOTBiome biome, float f) {
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

	public GOTStructureDorneCity setIsCastle() {
		isCastle = true;
		fixedVillageChunkRadius = 3;
		return this;
	}

	public GOTStructureDorneCity setIsTown() {
		isTown = true;
		fixedVillageChunkRadius = 6;
		return this;
	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;

		public Instance(GOTStructureDorneCity village, World world, int i, int k, Random random, LocationInfo loc) {
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
					return GOTBezierType.PATH_SANDY;
				}
			}
			if (villageType == VillageType.TOWN && i1 <= 80 && k1 <= 80) {
				return GOTBezierType.PATH_SANDY;
			}
			if (villageType == VillageType.FORT) {
				if (i1 <= 1 && (k >= 13 || k <= -12) && k1 <= 36) {
					return GOTBezierType.PATH_SANDY;
				}
				if (k1 <= 1 && i1 >= 12 && i1 <= 36) {
					return GOTBezierType.PATH_SANDY;
				}
				if (k >= 26 && k <= 28 && i1 <= 12) {
					return GOTBezierType.PATH_SANDY;
				}
			}
			return null;
		}

		public GOTStructureBase getRandomFarm(Random random) {
			if (random.nextBoolean()) {
				if (random.nextBoolean()) {
					return new GOTStructureDorneVillageFarm.Animals(false);
				}
				return new GOTStructureDorneVillageFarm.Crops(false);
			}
			return new GOTStructureDorneVillageFarm.Tree(false);
		}

		public GOTStructureBase getRandomHouse(Random random) {
			if (random.nextInt(5) == 0) {
				int i = random.nextInt(3);
				switch (i) {
				case 0:
					return new GOTStructureDorneStables(false);
				case 1:
					return new GOTStructureDorneSmithy(false);
				case 2:
					return new GOTStructureDorneBarn(false);
				default:
					break;
				}
			}
			return new GOTStructureDorneHouse(false);
		}

		public GOTStructureDorneTownWall getWallCentre(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -5, 5);
		}

		public GOTStructureDorneTownWall getWallLeft(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -9, 6);
		}

		public GOTStructureDorneTownWall getWallLeftEnd(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -6, 6, -5, 6);
		}

		public GOTStructureDorneTownWall getWallLeftEndShort(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -5, 6);
		}

		public GOTStructureDorneTownWall getWallRight(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -6, 9);
		}

		public GOTStructureDorneTownWall getWallRightEnd(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -6, 6, -6, 5);
		}

		public GOTStructureDorneTownWall getWallRightEndShort(boolean flag) {
			return new GOTStructureDorneTownWall(flag, -6, 5);
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			if (villageType == VillageType.TOWN || villageType == VillageType.FORT) {
				Block block = world.getBlock(i, j, k);
				int meta = world.getBlockMetadata(i, j, k);
				if (block == Blocks.dirt && meta == 1 || block == GOTRegistry.dirtPath && meta == 0 || block == GOTRegistry.brick1 && meta == 15 || block == GOTRegistry.brick3 && meta == 11 || block == GOTRegistry.pillar1 && meta == 5 || block == Blocks.sand && meta == 0 || block == Blocks.sandstone && meta == 0 || block == GOTRegistry.slabSingleDirt && meta == 0 || block == GOTRegistry.slabSingleDirt && meta == 1 || block == GOTRegistry.slabSingleSand && meta == 0 || block == GOTRegistry.slabSingle4 && meta == 0 || block == GOTRegistry.slabSingle7 && meta == 1 || block == GOTRegistry.slabSingle4 && meta == 7) {
					return true;
				}
			}
			return false;
		}

		public void setupCastle(Random random) {
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityDorneSoldier.class);
					spawner.setCheckRanges(50, -12, 12, 16);
					spawner.setSpawnRanges(30, -6, 6, 40);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[] { -20, 20 }) {
				for (int k1 : new int[] { -20, 20 }) {
					this.addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							spawner.setSpawnClass(GOTEntityDorneSoldier.class);
							spawner.setCheckRanges(20, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 40);
							spawner.setBlockEnemySpawnRange(40);
						}
					}, i1, k1, 0);
				}
			}
			this.addStructure(new GOTStructureDorneFortress(false), 0, 12, 2, true);
			this.addStructure(new GOTStructureDorneFortGate(false), 0, -37, 0, true);
			this.addStructure(new GOTStructureDorneFortWall.Right(false), -11, -37, 0, true);
			this.addStructure(new GOTStructureDorneFortWall.Left(false), 11, -37, 0, true);
			this.addStructure(new GOTStructureDorneWatchtower(false), -23, -33, 2, true);
			this.addStructure(new GOTStructureDorneWatchtower(false), 23, -33, 2, true);
			this.addStructure(new GOTStructureDorneFortGate(false), -37, 0, 3, true);
			this.addStructure(new GOTStructureDorneFortWall.Left(false), -37, -11, 3, true);
			this.addStructure(new GOTStructureDorneFortWall.Right(false), -37, 11, 3, true);
			this.addStructure(new GOTStructureDorneWatchtower(false), -33, -23, 1, true);
			this.addStructure(new GOTStructureDorneWatchtower(false), -33, 23, 1, true);
			this.addStructure(new GOTStructureDorneFortGate(false), 0, 37, 2, true);
			this.addStructure(new GOTStructureDorneFortWall.Left(false), -11, 37, 2, true);
			this.addStructure(new GOTStructureDorneFortWall.Right(false), 11, 37, 2, true);
			this.addStructure(new GOTStructureDorneWatchtower(false), -23, 33, 0, true);
			this.addStructure(new GOTStructureDorneWatchtower(false), 23, 33, 0, true);
			this.addStructure(new GOTStructureDorneFortGate(false), 37, 0, 1, true);
			this.addStructure(new GOTStructureDorneFortWall.Right(false), 37, -11, 1, true);
			this.addStructure(new GOTStructureDorneFortWall.Left(false), 37, 11, 1, true);
			this.addStructure(new GOTStructureDorneWatchtower(false), 33, -23, 3, true);
			this.addStructure(new GOTStructureDorneWatchtower(false), 33, 23, 3, true);
			this.addStructure(new GOTStructureDorneFortWallCorner(false), -30, -30, 3);
			this.addStructure(new GOTStructureDorneFortWallCorner(false), -30, 30, 2);
			this.addStructure(new GOTStructureDorneFortWallCorner(false), 30, 30, 1);
			this.addStructure(new GOTStructureDorneFortWallCorner(false), 30, -30, 2);
			this.addStructure(new GOTStructureDorneStables(false), -24, 2, 0);
			this.addStructure(new GOTStructureDorneStables(false), -24, -2, 2);
			this.addStructure(new GOTStructureDorneSmithy(false), 24, 1, 0);
			this.addStructure(new GOTStructureDorneSmithy(false), 24, -1, 2);
			this.addStructure(new GOTStructureDorneStoneHouse(false), -3, -25, 1);
			this.addStructure(new GOTStructureDorneStoneHouse(false), 3, -25, 3);
			this.addStructure(new GOTStructureDorneVillageFarm.Crops(false), -18, -21, 1);
			this.addStructure(new GOTStructureDorneVillageFarm.Crops(false), 18, -21, 3);
			this.addStructure(new GOTStructureDorneWell(false), -12, 27, 1);
			this.addStructure(new GOTStructureDorneWell(false), 12, 27, 3);
		}

		public void setupTown(Random random) {
			int l;
			int wallX;
			boolean outerTavern = random.nextBoolean();
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityDorneMan.class);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(60, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[] { -40, 40 }) {
				int[] arrn = { -40, 40 };
				int n = arrn.length;
				for (int i = 0; i < n; ++i) {
					int k1 = arrn[i];
					this.addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							spawner.setSpawnClass(GOTEntityDorneSoldier.class);
							spawner.setCheckRanges(40, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(60);
						}
					}, i1, k1, 0);
				}
			}
			this.addStructure(new GOTStructureDorneWell(false), 0, -4, 0, true);
			int stallPos = 12;
			for (int k1 = -1; k1 <= 1; ++k1) {
				int k2 = k1 * stallPos;
				if (random.nextInt(3) != 0) {
					this.addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), -stallPos + 3, k2, 1, true);
				}
				if (random.nextInt(3) == 0) {
					continue;
				}
				this.addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), stallPos - 3, k2, 3, true);
			}
			if (random.nextInt(3) != 0) {
				this.addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), 0, stallPos - 3, 0, true);
			}
			if (random.nextInt(3) != 0) {
				this.addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), 0, -stallPos + 3, 2, true);
			}
			int flowerX = 12;
			int flowerZ = 18;
			for (int i1 : new int[] { -flowerX, flowerX }) {
				this.addStructure(new GOTStructureDorneTownGarden(false), i1, flowerZ, 0, true);
				this.addStructure(new GOTStructureDorneTownGarden(false), i1, -flowerZ, 2, true);
				this.addStructure(new GOTStructureDorneTownGarden(false), -flowerZ, i1, 1, true);
				this.addStructure(new GOTStructureDorneTownGarden(false), flowerZ, i1, 3, true);
			}
			int lampZ = 21;
			for (int i1 : new int[] { -1, 1 }) {
				int lampX = i1 * 6;
				this.addStructure(new GOTStructureDorneLampPost(false), lampX, lampZ, 0, true);
				this.addStructure(new GOTStructureDorneLampPost(false), lampX, -lampZ, 2, true);
				if (i1 != -1) {
					this.addStructure(new GOTStructureDorneLampPost(false), -lampZ, lampX, 1, true);
				}
				this.addStructure(new GOTStructureDorneLampPost(false), lampZ, lampX, 3, true);
			}
			int houseX = 24;
			for (int k1 = -1; k1 <= 1; ++k1) {
				int houseZ = k1 * 12;
				if (k1 == 1) {
					this.addStructure(new GOTStructureDorneStoneHouse(false), -houseX, houseZ, 1, true);
					this.addStructure(new GOTStructureDorneStoneHouse(false), houseX, houseZ, 3, true);
				}
				if (k1 == 0) {
					continue;
				}
				this.addStructure(new GOTStructureDorneStoneHouse(false), houseZ, houseX, 0, true);
				this.addStructure(new GOTStructureDorneStoneHouse(false), houseZ, -houseX, 2, true);
			}
			this.addStructure(new GOTStructureDorneSmithy(false), 0, -26, 2, true);
			this.addStructure(new GOTStructureDorneObelisk(false), 0, 27, 0, true);
			this.addStructure(new GOTStructureDorneTavern(false), -houseX, -5, 1, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), -47, -13, 2, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), -47, 1, 0, true);
			for (int i1 : new int[] { -43, -51 }) {
				this.addStructure(new GOTStructureDorneTownBench(false), i1, -9, 2, true);
				this.addStructure(new GOTStructureDorneTownBench(false), i1, -3, 0, true);
			}
			this.addStructure(new GOTStructureDorneBath(false), houseX + 2, -6, 3, true);
			this.addStructure(new GOTStructureDorneTownGarden(false), 51, -13, 2, true);
			this.addStructure(new GOTStructureDorneTownGarden(false), 51, 1, 0, true);
			this.addStructure(new GOTStructureDorneTownGarden(false), 52, -6, 3, true);
			int wellX = 22;
			int wellZ = 31;
			for (int i1 : new int[] { -wellX, wellX }) {
				this.addStructure(new GOTStructureDorneWell(false), i1, -wellZ, 2, true);
				this.addStructure(new GOTStructureDorneWell(false), i1, wellZ, 0, true);
				this.addStructure(new GOTStructureDorneWell(false), -wellZ, i1, 1, true);
				this.addStructure(new GOTStructureDorneWell(false), wellZ, i1, 3, true);
			}
			houseX = 54;
			for (int k1 = -2; k1 <= 2; ++k1) {
				int houseZ = k1 * 12;
				if (k1 <= -2 || k1 >= 1) {
					this.addStructure(new GOTStructureDorneStoneHouse(false), -houseX, houseZ, 3, true);
					this.addStructure(new GOTStructureDorneStoneHouse(false), houseX, houseZ, 1, true);
				}
				this.addStructure(new GOTStructureDorneStoneHouse(false), houseZ, houseX, 2, true);
				this.addStructure(new GOTStructureDorneStoneHouse(false), houseZ, -houseX, 0, true);
			}
			int treeX = 47;
			int treeZ = 35;
			for (int i1 : new int[] { -treeX, treeX }) {
				this.addStructure(new GOTStructureDorneTownTrees(false), i1, -treeZ, 0, true);
				this.addStructure(new GOTStructureDorneTownTrees(false), i1, treeZ, 2, true);
				this.addStructure(new GOTStructureDorneTownTrees(false), -treeZ, i1, 3, true);
				this.addStructure(new GOTStructureDorneTownTrees(false), treeZ, i1, 1, true);
			}
			houseX = 64;
			int lampX = 59;
			for (int k1 = -4; k1 <= 4; ++k1) {
				boolean treepiece;
				int houseZ = k1 * 12;
				treepiece = IntMath.mod(k1, 2) == 1;
				if (treepiece) {
					this.addStructure(new GOTStructureDorneVillageFarm.Tree(false), -houseX - 2, houseZ, 1, true);
					this.addStructure(new GOTStructureDorneVillageFarm.Tree(false), houseX + 2, houseZ, 3, true);
				} else {
					this.addStructure(new GOTStructureDorneStoneHouse(false), -houseX, houseZ, 1, true);
					this.addStructure(new GOTStructureDorneStoneHouse(false), houseX, houseZ, 3, true);
				}
				if (treepiece) {
					this.addStructure(new GOTStructureDorneVillageFarm.Tree(false), houseZ, -houseX - 2, 2, true);
				} else {
					this.addStructure(new GOTStructureDorneStoneHouse(false), houseZ, -houseX, 2, true);
				}
				if (Math.abs(k1) >= 2 && (!outerTavern || k1 <= 2)) {
					if (treepiece) {
						this.addStructure(new GOTStructureDorneVillageFarm.Tree(false), houseZ, houseX + 2, 0, true);
					} else {
						this.addStructure(new GOTStructureDorneStoneHouse(false), houseZ, houseX, 0, true);
					}
				}
				this.addStructure(new GOTStructureDorneLampPost(false), -lampX, houseZ, 1, true);
				this.addStructure(new GOTStructureDorneLampPost(false), lampX, houseZ, 3, true);
				this.addStructure(new GOTStructureDorneLampPost(false), houseZ, lampX, 0, true);
				this.addStructure(new GOTStructureDorneLampPost(false), houseZ, -lampX, 2, true);
			}
			if (outerTavern) {
				this.addStructure(new GOTStructureDorneTavern(false), 44, houseX, 0, true);
			}
			int gardenX = 42;
			int gardenZ = 48;
			this.addStructure(new GOTStructureDorneVillageFarm.Tree(false), -gardenX, -gardenZ, 1, true);
			this.addStructure(new GOTStructureDorneVillageFarm.Tree(false), -gardenX, gardenZ, 1, true);
			this.addStructure(new GOTStructureDorneVillageFarm.Tree(false), gardenX, -gardenZ, 3, true);
			this.addStructure(new GOTStructureDorneVillageFarm.Tree(false), gardenX, gardenZ, 3, true);
			int obeliskX = 62;
			int obeliskZ = 66;
			this.addStructure(new GOTStructureDorneObelisk(false), -obeliskX, -obeliskZ, 1, true);
			this.addStructure(new GOTStructureDorneObelisk(false), -obeliskX, obeliskZ, 1, true);
			this.addStructure(new GOTStructureDorneObelisk(false), obeliskX, -obeliskZ, 3, true);
			this.addStructure(new GOTStructureDorneObelisk(false), obeliskX, obeliskZ, 3, true);
			wellX = 64;
			wellZ = 57;
			this.addStructure(new GOTStructureDorneWell(false), -wellX, -wellZ, 1, true);
			this.addStructure(new GOTStructureDorneWell(false), -wellX, wellZ, 1, true);
			this.addStructure(new GOTStructureDorneWell(false), wellX, -wellZ, 3, true);
			this.addStructure(new GOTStructureDorneWell(false), wellX, wellZ, 3, true);
			this.addStructure(new GOTStructureDorneWell(false), -wellZ, -wellX, 2, true);
			this.addStructure(new GOTStructureDorneWell(false), wellZ, -wellX, 2, true);
			this.addStructure(new GOTStructureDorneWell(false), -wellZ, wellX, 0, true);
			this.addStructure(new GOTStructureDorneWell(false), wellZ, wellX, 0, true);
			treeX = 75;
			treeZ = 61;
			this.addStructure(new GOTStructureDorneTownTrees(false), -treeX, -treeZ, 1, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), -treeX, treeZ, 1, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), treeX, -treeZ, 3, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), treeX, treeZ, 3, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), -treeZ, -treeX, 2, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), treeZ, -treeX, 2, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), -treeZ, treeX, 0, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), treeZ, treeX, 0, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), -14, 71, 1, true);
			this.addStructure(new GOTStructureDorneTownTrees(false), 14, 71, 3, true);
			for (int k1 : new int[] { 67, 75 }) {
				this.addStructure(new GOTStructureDorneTownBench(false), -10, k1, 1, true);
				this.addStructure(new GOTStructureDorneTownBench(false), 10, k1, 3, true);
			}
			this.addStructure(new GOTStructureDorneGatehouse(false), 0, 84, 2, true);
			this.addStructure(new GOTStructureDorneLampPost(false), -4, 73, 0, true);
			this.addStructure(new GOTStructureDorneLampPost(false), 4, 73, 0, true);
			int towerX = 78;
			int towerZ = 74;
			for (int i1 : new int[] { -towerX, towerX }) {
				this.addStructure(new GOTStructureDorneWatchtower(false), i1, -towerZ, 2, true);
				this.addStructure(new GOTStructureDorneWatchtower(false), i1, towerZ, 0, true);
			}
			int wallZ = 82;
			int wallEndX = 76;
			for (l = 0; l <= 3; ++l) {
				wallX = 12 + l * 16;
				this.addStructure(getWallLeft(false), -wallX, wallZ, 2, true);
				this.addStructure(getWallRight(false), wallX, wallZ, 2, true);
			}
			this.addStructure(getWallLeftEndShort(false), -wallEndX, wallZ, 2, true);
			this.addStructure(getWallRightEndShort(false), wallEndX, wallZ, 2, true);
			this.addStructure(getWallCentre(false), -wallZ, 0, 3, true);
			this.addStructure(getWallCentre(false), wallZ, 0, 1, true);
			this.addStructure(getWallCentre(false), 0, -wallZ, 0, true);
			for (l = 0; l <= 3; ++l) {
				wallX = 12 + l * 16;
				this.addStructure(getWallLeft(false), -wallZ, -wallX, 3, true);
				this.addStructure(getWallRight(false), -wallZ, wallX, 3, true);
				this.addStructure(getWallLeft(false), wallZ, wallX, 1, true);
				this.addStructure(getWallRight(false), wallZ, -wallX, 1, true);
				this.addStructure(getWallLeft(false), wallX, -wallZ, 0, true);
				this.addStructure(getWallRight(false), -wallX, -wallZ, 0, true);
			}
			this.addStructure(getWallLeftEnd(false), -wallZ, -wallEndX, 3, true);
			this.addStructure(getWallRightEnd(false), -wallZ, wallEndX, 3, true);
			this.addStructure(getWallLeftEnd(false), wallZ, wallEndX, 1, true);
			this.addStructure(getWallRightEnd(false), wallZ, -wallEndX, 1, true);
			this.addStructure(getWallLeftEndShort(false), wallEndX, -wallZ, 0, true);
			this.addStructure(getWallRightEndShort(false), -wallEndX, -wallZ, 0, true);
		}

		public void setupVillage(Random random) {
			this.addStructure(new GOTStructureDorneWell(false), 0, -4, 0, true);
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityDorneMan.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityDorneSoldier.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureDorneCottage(false), -21, 0, 1);
			this.addStructure(new GOTStructureDorneCottage(false), 0, -21, 2);
			this.addStructure(new GOTStructureDorneCottage(false), 21, 0, 3);
			this.addStructure(new GOTStructureDorneTavern(false), 0, 21, 0);
			if (random.nextBoolean()) {
				if (random.nextInt(3) == 0) {
					this.addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), -9, -12, 1);
				}
				if (random.nextInt(3) == 0) {
					this.addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), 9, -12, 3);
				}
				if (random.nextInt(3) == 0) {
					this.addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), -9, 12, 1);
				}
				if (random.nextInt(3) == 0) {
					this.addStructure(GOTStructureDorneMarketStall.getRandomStall(random, false), 9, 12, 3);
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
					this.addStructure(getRandomHouse(random), i, k, r);
					continue;
				}
				if (random.nextInt(3) == 0) {
					continue;
				}
				l = 65;
				i = Math.round(l * cos);
				k = Math.round(l * sin);
				this.addStructure(new GOTStructureHayBales(false), i, k, r);
			}
			int signPos = Math.round(50.0f * MathHelper.cos((float) Math.toRadians(45.0)));
			int signDisp = Math.round(7.0f * MathHelper.cos((float) Math.toRadians(45.0)));
			this.addStructure(new GOTStructureWesterosVillageSign(false), -signPos, -signPos + signDisp, 1);
			this.addStructure(new GOTStructureWesterosVillageSign(false), signPos, -signPos + signDisp, 3);
			this.addStructure(new GOTStructureWesterosVillageSign(false), -signPos, signPos - signDisp, 1);
			this.addStructure(new GOTStructureWesterosVillageSign(false), signPos, signPos - signDisp, 3);
			int farmX = 38;
			int farmZ = 17;
			int farmSize = 6;
			if (random.nextBoolean()) {
				this.addStructure(getRandomFarm(random), -farmX + farmSize, -farmZ, 1);
			}
			if (random.nextBoolean()) {
				this.addStructure(getRandomFarm(random), -farmZ + farmSize, -farmX, 1);
			}
			if (random.nextBoolean()) {
				this.addStructure(getRandomFarm(random), farmX - farmSize, -farmZ, 3);
			}
			if (random.nextBoolean()) {
				this.addStructure(getRandomFarm(random), farmZ - farmSize, -farmX, 3);
			}
			if (random.nextBoolean()) {
				this.addStructure(getRandomFarm(random), -farmX + farmSize, farmZ, 1);
			}
			if (random.nextBoolean()) {
				this.addStructure(getRandomFarm(random), farmX - farmSize, farmZ, 3);
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			if (isTown) {
				villageType = VillageType.TOWN;
			} else if (isCastle || (random.nextInt(4) == 0)) {
				villageType = VillageType.FORT;
			} else {
				villageType = VillageType.VILLAGE;
			}
		}

	}

	public enum VillageType {
		VILLAGE, TOWN, FORT;
	}

}