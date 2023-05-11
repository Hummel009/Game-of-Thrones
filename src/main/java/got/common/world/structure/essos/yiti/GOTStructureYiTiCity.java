package got.common.world.structure.essos.yiti;

import got.common.database.GOTRegistry;
import got.common.entity.essos.yiti.GOTEntityYiTiMan;
import got.common.entity.essos.yiti.GOTEntityYiTiSoldier;
import got.common.entity.essos.yiti.GOTEntityYiTiSoldierCrossbower;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureYiTiCity extends GOTVillageGen {
	public boolean isTown;
	public boolean isTower;
	public boolean isWall;
	public boolean side;

	public GOTStructureYiTiCity(GOTBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 6;
		fixedVillageChunkRadius = 6;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public GOTStructureYiTiCity setIsTower() {
		isTower = true;
		fixedVillageChunkRadius = 9;
		return this;
	}

	public GOTStructureYiTiCity setIsTown() {
		isTown = true;
		fixedVillageChunkRadius = 7;
		return this;
	}

	public GOTStructureYiTiCity setIsWall(boolean b) {
		isWall = true;
		fixedVillageChunkRadius = 2;
		side = b;
		return this;
	}

	public enum VillageType {
		VILLAGE, TOWN, FORT, TOWER, WALL;
	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;

		public Instance(GOTStructureYiTiCity village, World world, int i, int k, Random random, LocationInfo loc) {
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
				case WALL:
					setupGate(random);
					break;
				case TOWER:
					this.addStructure(new GOTStructureYiTiLighthouse(), 0, 0, 0, true);
					break;
			}
		}

		public GOTStructureBase getOtherVillageStructure(Random random) {
			if (random.nextBoolean()) {
				return new GOTStructureYiTiStables(false);
			}
			return new GOTStructureYiTiSmithy(false);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int innerOut;
			int outerOut;
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (villageType == VillageType.VILLAGE) {
				int dSq = i * i + k * k;
				int imn = 15 + random.nextInt(4);
				if (dSq < imn * imn || i1 <= 64 && k1 <= 3 + random.nextInt(2)) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			if (villageType == VillageType.TOWN) {
				innerOut = 18;
				if (i1 <= innerOut && k1 <= innerOut && (i1 >= 12 || k1 >= 12)) {
					return GOTBezierType.TOWN_YITI;
				}
				if (i1 <= 3 && k1 >= innerOut && k1 <= 86 || k1 <= 3 && i1 >= innerOut && i1 <= 86) {
					return GOTBezierType.TOWN_YITI;
				}
				outerOut = 66;
				if (i1 <= outerOut && k1 <= outerOut && (i1 >= 60 || k1 >= 60)) {
					return GOTBezierType.TOWN_YITI;
				}
			}
			if (villageType == VillageType.FORT) {
				innerOut = 24;
				if (i1 <= innerOut && k1 <= innerOut && (i1 >= 20 || k1 >= 20)) {
					return GOTBezierType.TOWN_YITI;
				}
				if (k >= 14 && k <= 54 && i1 <= 2) {
					return GOTBezierType.TOWN_YITI;
				}
				outerOut = 52;
				if (i1 <= outerOut && k1 <= outerOut && (i1 >= 48 || k1 >= 48)) {
					return GOTBezierType.TOWN_YITI;
				}
			}
			return null;
		}

		public GOTStructureBase getRandomHouse(Random random) {
			return new GOTStructureYiTiHouse(false);
		}

		public GOTStructureBase getRandomVillageFarm(Random random) {
			if (random.nextBoolean()) {
				return new GOTStructureYiTiVillageFarm.Animals(false);
			}
			return new GOTStructureYiTiVillageFarm.Crops(false);
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			if (block == GOTRegistry.slabSingleDirt && meta == 5 || block == GOTRegistry.dirtPath && meta == 2) {
				return true;
			}
			return false;
		}

		public void setupFort(Random random) {
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityYiTiMan.class);
					spawner.setCheckRanges(50, -12, 12, 16);
					spawner.setSpawnRanges(30, -6, 6, 40);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			for (int i1 : new int[]{-48, 48}) {
				for (int k1 : new int[]{-48, 48}) {
					this.addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							spawner.setSpawnClasses(GOTEntityYiTiSoldier.class, GOTEntityYiTiSoldierCrossbower.class);
							spawner.setCheckRanges(32, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 40);
							spawner.setBlockEnemySpawnRange(40);
						}
					}, i1, k1, 0);
				}
			}
			this.addStructure(new GOTStructureYiTiFortress(false), 0, 13, 2, true);
			int stableX = 26;
			int stableZ = 0;
			this.addStructure(new GOTStructureYiTiStables(false), -stableX, stableZ, 1, true);
			this.addStructure(new GOTStructureYiTiStables(false), stableX, stableZ, 3, true);
			int wellX = stableX;
			int wellZ = 18;
			this.addStructure(new GOTStructureYiTiWell(false), -wellX, wellZ, 1, true);
			this.addStructure(new GOTStructureYiTiWell(false), wellX, wellZ, 3, true);
			int farmZ = 27;
			for (int l = -3; l <= 3; ++l) {
				int farmX = l * 10;
				if (random.nextInt(3) == 0) {
					this.addStructure(new GOTStructureHayBales(false), farmX, -farmZ - 5, 2);
					continue;
				}
				this.addStructure(getRandomVillageFarm(random), farmX, -farmZ, 2);
			}
			int statueX = 6;
			int statueZ = 36;
			this.addStructure(new GOTStructureYiTiStatue(false), -statueX, statueZ, 1, true);
			this.addStructure(new GOTStructureYiTiStatue(false), statueX, statueZ, 3, true);
			this.addStructure(new GOTStructureYiTiGatehouse(false), 0, 62, 2, true);
			int towerX = 58;
			this.addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setLeftLadder(), -towerX, -towerX - 3, 0, true);
			this.addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setRightLadder(), towerX, -towerX - 3, 0, true);
			this.addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setRightLadder(), -towerX, towerX + 3, 2, true);
			this.addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setLeftLadder(), towerX, towerX + 3, 2, true);
			int wallZ = towerX;
			this.addStructure(GOTStructureYiTiTownWall.Centre(false), 0, -wallZ, 0);
			this.addStructure(GOTStructureYiTiTownWall.Centre(false), wallZ, 0, 1);
			this.addStructure(GOTStructureYiTiTownWall.Centre(false), -wallZ, 0, 3);
			for (int l = 0; l <= 5; ++l) {
				int wallX = 11 + l * 8;
				this.addStructure(GOTStructureYiTiTownWall.Left(false), wallX, -wallZ, 0);
				this.addStructure(GOTStructureYiTiTownWall.Right(false), -wallX, -wallZ, 0);
				this.addStructure(GOTStructureYiTiTownWall.Left(false), wallZ, wallX, 1);
				this.addStructure(GOTStructureYiTiTownWall.Right(false), wallZ, -wallX, 1);
				this.addStructure(GOTStructureYiTiTownWall.Left(false), -wallX, wallZ, 2);
				this.addStructure(GOTStructureYiTiTownWall.Right(false), wallX, wallZ, 2);
				this.addStructure(GOTStructureYiTiTownWall.Left(false), -wallZ, -wallX, 3);
				this.addStructure(GOTStructureYiTiTownWall.Right(false), -wallZ, wallX, 3);
			}
			int lampX = 17;
			this.addStructure(new GOTStructureYiTiLamp(false), -lampX, -lampX, 2, false);
			this.addStructure(new GOTStructureYiTiLamp(false), lampX, -lampX, 2, false);
			this.addStructure(new GOTStructureYiTiLamp(false), -lampX, lampX, 0, false);
			this.addStructure(new GOTStructureYiTiLamp(false), lampX, lampX, 0, false);
			lampX = 45;
			this.addStructure(new GOTStructureYiTiLamp(false), -lampX, -lampX, 2, false);
			this.addStructure(new GOTStructureYiTiLamp(false), lampX, -lampX, 2, false);
			this.addStructure(new GOTStructureYiTiLamp(false), -lampX, lampX, 0, false);
			this.addStructure(new GOTStructureYiTiLamp(false), lampX, lampX, 0, false);
			lampX = 7;
			int lampZ = 64;
			this.addStructure(new GOTStructureYiTiLamp(false), -lampX, lampZ, 2, false);
			this.addStructure(new GOTStructureYiTiLamp(false), lampX, lampZ, 2, false);
		}

		public void setupGate(Random random) {
			if (side) {
				this.addStructure(new GOTStructureYiTiFortress(false), -4, 25, 1, true);
			} else {
				this.addStructure(new GOTStructureYiTiFortress(false), 0, 20, 0, true);
			}
			this.addStructure(new GOTStructureYiTiGate(false), 0, 7, 0, true);
		}

		public void setupTown(Random random) {
			int marketZ;
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityYiTiMan.class);
					spawner.setCheckRanges(80, -12, 12, 100);
					spawner.setSpawnRanges(60, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			int spawnerX = 60;
			for (int i1 : new int[]{-spawnerX, spawnerX}) {
				for (int k1 : new int[]{-spawnerX, spawnerX}) {
					this.addStructure(new GOTStructureNPCRespawner(false) {

						@Override
						public void setupRespawner(GOTEntityNPCRespawner spawner) {
							spawner.setSpawnClasses(GOTEntityYiTiSoldier.class, GOTEntityYiTiSoldierCrossbower.class);
							spawner.setCheckRanges(50, -12, 12, 16);
							spawner.setSpawnRanges(20, -6, 6, 64);
							spawner.setBlockEnemySpawnRange(60);
						}
					}, i1, k1, 0);
				}
			}
			if (random.nextBoolean()) {
				this.addStructure(new GOTStructureYiTiGarden(false), 0, 10, 2, true);
			} else {
				this.addStructure(new GOTStructureYiTiStatue(false), 0, 6, 2, true);
			}
			int mansionX = 12;
			int mansionZ = 20;
			this.addStructure(new GOTStructureYiTiLargeTownHouse(false), -mansionX, -mansionZ, 2, true);
			this.addStructure(new GOTStructureYiTiLargeTownHouse(false), mansionX, -mansionZ, 2, true);
			this.addStructure(new GOTStructureYiTiLargeTownHouse(false), -mansionX, mansionZ, 0, true);
			this.addStructure(new GOTStructureYiTiLargeTownHouse(false), mansionX, mansionZ, 0, true);
			this.addStructure(new GOTStructureYiTiLargeTownHouse(false), -mansionZ, -mansionX, 1, true);
			this.addStructure(new GOTStructureYiTiLargeTownHouse(false), -mansionZ, mansionX, 1, true);
			this.addStructure(new GOTStructureYiTiLargeTownHouse(false), mansionZ, -mansionX, 3, true);
			this.addStructure(new GOTStructureYiTiLargeTownHouse(false), mansionZ, mansionX, 3, true);
			for (int l = 0; l <= 3; ++l) {
				int houseX = 10 + 14 * l;
				int houseZ1 = 58;
				int houseZ2 = 68;
				if (l <= 2) {
					if (l >= 1 && l <= 2) {
						if (l == 1) {
							this.addStructure(new GOTStructureYiTiTavernTown(false), -houseX - 7, -houseZ1, 0, true);
						}
					} else {
						this.addStructure(new GOTStructureYiTiTownHouse(false), -houseX, -houseZ1, 0, true);
					}
					this.addStructure(new GOTStructureYiTiTownHouse(false), houseX, -houseZ1, 0, true);
					if (l >= 1) {
						this.addStructure(new GOTStructureYiTiTownHouse(false), -houseX, houseZ1, 2, true);
						this.addStructure(new GOTStructureYiTiTownHouse(false), houseX, houseZ1, 2, true);
					}
					this.addStructure(new GOTStructureYiTiTownHouse(false), -houseZ1, -houseX, 3, true);
					this.addStructure(new GOTStructureYiTiTownHouse(false), -houseZ1, houseX, 3, true);
					this.addStructure(new GOTStructureYiTiTownHouse(false), houseZ1, -houseX, 1, true);
					this.addStructure(new GOTStructureYiTiTownHouse(false), houseZ1, houseX, 1, true);
				}
				if (l == 1) {
					this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -houseX, -houseZ2, 2, true);
					this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), houseX, -houseZ2, 2, true);
					this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -houseX, houseZ2, 0, true);
					this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), houseX, houseZ2, 0, true);
					this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -houseZ2, -houseX, 1, true);
					this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -houseZ2, houseX, 1, true);
					this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), houseZ2, -houseX, 3, true);
					this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), houseZ2, houseX, 3, true);
					continue;
				}
				this.addStructure(new GOTStructureYiTiTownHouse(false), -houseX, -houseZ2, 2, true);
				this.addStructure(l == 3 ? new GOTStructureYiTiSmithy(false) : new GOTStructureYiTiTownHouse(false), houseX, -houseZ2, 2, true);
				this.addStructure(new GOTStructureYiTiTownHouse(false), -houseX, houseZ2, 0, true);
				this.addStructure(new GOTStructureYiTiTownHouse(false), houseX, houseZ2, 0, true);
				this.addStructure(new GOTStructureYiTiTownHouse(false), -houseZ2, -houseX, 1, true);
				this.addStructure(new GOTStructureYiTiTownHouse(false), -houseZ2, houseX, 1, true);
				this.addStructure(new GOTStructureYiTiTownHouse(false), houseZ2, -houseX, 3, true);
				this.addStructure(new GOTStructureYiTiTownHouse(false), houseZ2, houseX, 3, true);
			}
			int marketX = 4;
			for (int l = 0; l <= 2; ++l) {
				marketZ = 56 - l * 7;
				this.addStructure(GOTStructureYiTiMarketStall.getRandomStall(random, false), -marketX, marketZ, 1, true);
				this.addStructure(GOTStructureYiTiMarketStall.getRandomStall(random, false), marketX, marketZ, 3, true);
			}
			marketX = 14;
			marketZ = 59;
			this.addStructure(GOTStructureYiTiMarketStall.getRandomStall(random, false), -marketX, marketZ, 2, true);
			this.addStructure(GOTStructureYiTiMarketStall.getRandomStall(random, false), marketX, marketZ, 2, true);
			int gardenX = 58;
			this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -gardenX + 5, -gardenX, 0, true);
			this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), gardenX - 5, -gardenX, 0, true);
			this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -gardenX + 5, gardenX, 2, true);
			this.addStructure(new GOTStructureYiTiVillageFarm.Tree(false), gardenX - 5, gardenX, 2, true);
			int wellX = 69;
			int wellZ = 63;
			this.addStructure(new GOTStructureYiTiWell(false), -wellX, -wellZ, 1, true);
			this.addStructure(new GOTStructureYiTiWell(false), -wellZ, -wellX, 2, true);
			this.addStructure(new GOTStructureYiTiWell(false), wellX, -wellZ, 3, true);
			this.addStructure(new GOTStructureYiTiWell(false), wellZ, -wellX, 2, true);
			this.addStructure(new GOTStructureYiTiWell(false), -wellX, wellZ, 1, true);
			this.addStructure(new GOTStructureYiTiWell(false), -wellZ, wellX, 0, true);
			this.addStructure(new GOTStructureYiTiWell(false), wellX, wellZ, 3, true);
			this.addStructure(new GOTStructureYiTiWell(false), wellZ, wellX, 0, true);
			this.addStructure(new GOTStructureYiTiGatehouse(false), 0, 94, 2, true);
			int towerX = 90;
			this.addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setLeftLadder(), -towerX, -towerX - 3, 0, true);
			this.addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setRightLadder(), towerX, -towerX - 3, 0, true);
			this.addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setRightLadder(), -towerX, towerX + 3, 2, true);
			this.addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setLeftLadder(), towerX, towerX + 3, 2, true);
			int wallZ = towerX;
			this.addStructure(GOTStructureYiTiTownWall.Centre(false), 0, -wallZ, 0);
			this.addStructure(GOTStructureYiTiTownWall.Centre(false), wallZ, 0, 1);
			this.addStructure(GOTStructureYiTiTownWall.Centre(false), -wallZ, 0, 3);
			for (int l = 0; l <= 9; ++l) {
				int wallX = 11 + l * 8;
				this.addStructure(GOTStructureYiTiTownWall.Left(false), wallX, -wallZ, 0);
				this.addStructure(GOTStructureYiTiTownWall.Right(false), -wallX, -wallZ, 0);
				this.addStructure(GOTStructureYiTiTownWall.Left(false), wallZ, wallX, 1);
				this.addStructure(GOTStructureYiTiTownWall.Right(false), wallZ, -wallX, 1);
				this.addStructure(GOTStructureYiTiTownWall.Left(false), -wallX, wallZ, 2);
				this.addStructure(GOTStructureYiTiTownWall.Right(false), wallX, wallZ, 2);
				this.addStructure(GOTStructureYiTiTownWall.Left(false), -wallZ, -wallX, 3);
				this.addStructure(GOTStructureYiTiTownWall.Right(false), -wallZ, wallX, 3);
			}
			int lampX = 7;
			int lampZ = 96;
			this.addStructure(new GOTStructureYiTiLamp(false), -lampX, lampZ, 2, false);
			this.addStructure(new GOTStructureYiTiLamp(false), lampX, lampZ, 2, false);
		}

		public void setupVillage(Random random) {
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityYiTiMan.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClasses(GOTEntityYiTiSoldier.class, GOTEntityYiTiSoldierCrossbower.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			int pathEnd = 68;
			int pathSide = 7;
			int centreSide = 19;
			this.addStructure(new GOTStructureYiTiWell(false), 0, -2, 0, true);
			int signX = 12;
			this.addStructure(new GOTStructureYiTiVillageSign(false), -signX, 0, 1, true);
			this.addStructure(new GOTStructureYiTiVillageSign(false), signX, 0, 3, true);
			this.addStructure(new GOTStructureYiTiLargeTownHouse(false), 0, -centreSide, 2, true);
			if (random.nextBoolean()) {
				this.addStructure(new GOTStructureYiTiTavern(false), -pathEnd, 0, 1, true);
				this.addStructure(getOtherVillageStructure(random), pathEnd, 0, 3, true);
			} else {
				this.addStructure(getOtherVillageStructure(random), -pathEnd, 0, 1, true);
				this.addStructure(new GOTStructureYiTiTavern(false), pathEnd, 0, 3, true);
			}
			int rowHouses = 3;
			for (int l = -rowHouses; l <= rowHouses; ++l) {
				int i1 = l * 18;
				int k1 = pathSide;
				if (Math.abs(i1) <= 15) {
					k1 += 15 - pathSide;
				}
				if (Math.abs(l) >= 1) {
					this.addStructure(getRandomHouse(random), i1, -k1, 2);
				}
				this.addStructure(getRandomHouse(random), i1, k1, 0);
				int k2 = k1 + 20;
				if (l != 0) {
					if (random.nextInt(3) == 0) {
						this.addStructure(getRandomVillageFarm(random), i1, -k2, 2);
					} else {
						this.addStructure(new GOTStructureHayBales(false), i1, -k2, 2);
					}
				}
				if (random.nextInt(3) == 0) {
					this.addStructure(getRandomVillageFarm(random), i1, k2, 0);
					continue;
				}
				this.addStructure(new GOTStructureHayBales(false), i1, k2, 0);
			}
		}

		@Override
		public void setupVillageProperties(Random random) {
			if (isTown) {
				villageType = VillageType.TOWN;
			} else if (isTower) {
				villageType = VillageType.TOWER;
			} else if (isWall) {
				villageType = VillageType.WALL;
			} else if (random.nextInt(4) == 0) {
				villageType = VillageType.FORT;
			} else {
				villageType = VillageType.VILLAGE;
			}
		}

	}
}