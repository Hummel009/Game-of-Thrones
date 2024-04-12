package got.common.world.structure.essos.yiti;

import got.common.database.GOTBlocks;
import got.common.entity.essos.yiti.GOTEntityYiTiMan;
import got.common.entity.essos.yiti.GOTEntityYiTiSoldier;
import got.common.entity.essos.yiti.GOTEntityYiTiSoldierCrossbower;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.structure.other.*;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureYiTiSettlement extends GOTStructureBaseSettlement {
	public Type type;
	public boolean forcedType;

	public GOTStructureYiTiSettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 6;
		fixedSettlementChunkRadius = 6;
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
		VILLAGE, TOWN, FORT, TOWER, GATE, GATE_ROAD
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance {
		public Type type;
		public boolean forcedType;

		public Instance(World world, int i, int k, Random random, LocationInfo loc, Collection<GOTFixer.SpawnInfo> spawnInfos, Type t, boolean b) {
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
					setupTown(random);
					break;
				case FORT:
					setupFort(random);
					break;
				case GATE:
					setupGate(random);
					break;
				case GATE_ROAD:
					setupGateRoad(random);
					break;
				case TOWER:
					addStructure(new GOTStructureYiTiLighthouse(), 10, -10, 2, true);
					break;
			}
		}

		public GOTStructureBase getOtherSettlementStructure(Random random) {
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
			if (type == Type.VILLAGE) {
				int dSq = i * i + k * k;
				int imn = 15 + random.nextInt(4);
				if (dSq < imn * imn || i1 <= 64 && k1 <= 3 + random.nextInt(2)) {
					return GOTBezierType.PATH_DIRTY;
				}
			} else if (type == Type.TOWN) {
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
			} else if (type == Type.FORT) {
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

		public GOTStructureBase getRandomVillageFarm(Random random) {
			if (random.nextBoolean()) {
				return new GOTStructureYiTiVillageFarm.Animals(false);
			}
			return new GOTStructureYiTiVillageFarm.Crops(false);
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			return block == GOTBlocks.slabSingleDirt && meta == 5 || block == GOTBlocks.dirtPath && meta == 2;
		}

		public void setupFort(Random random) {
			addStructure(new GOTStructureNPCRespawner(false) {

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
					addStructure(new GOTStructureNPCRespawner(false) {

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
			addStructure(new GOTStructureYiTiFortress(false), 0, 13, 2, true);
			int stableX = 26;
			int stableZ = 0;
			addStructure(new GOTStructureYiTiStables(false), -stableX, stableZ, 1, true);
			addStructure(new GOTStructureYiTiStables(false), stableX, stableZ, 3, true);
			int wellZ = 18;
			addStructure(new GOTStructureYiTiWell(false), -stableX, wellZ, 1, true);
			addStructure(new GOTStructureYiTiWell(false), stableX, wellZ, 3, true);
			int farmZ = 27;
			for (int l = -3; l <= 3; ++l) {
				int farmX = l * 10;
				if (random.nextInt(3) == 0) {
					addStructure(new GOTStructureHayBales(false), farmX, -farmZ - 5, 2);
					continue;
				}
				addStructure(getRandomVillageFarm(random), farmX, -farmZ, 2);
			}
			int statueX = 6;
			int statueZ = 36;
			addStructure(new GOTStructureYiTiStatue(false), -statueX, statueZ, 1, true);
			addStructure(new GOTStructureYiTiStatue(false), statueX, statueZ, 3, true);
			addStructure(new GOTStructureYiTiGatehouse(false), 0, 62, 2, true);
			int towerX = 58;
			addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setLeftLadder(), -towerX, -towerX - 3, 0, true);
			addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setRightLadder(), towerX, -towerX - 3, 0, true);
			addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setRightLadder(), -towerX, towerX + 3, 2, true);
			addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setLeftLadder(), towerX, towerX + 3, 2, true);
			addStructure(GOTStructureYiTiTownWall.Centre(false), 0, -towerX, 0);
			addStructure(GOTStructureYiTiTownWall.Centre(false), towerX, 0, 1);
			addStructure(GOTStructureYiTiTownWall.Centre(false), -towerX, 0, 3);
			for (int l = 0; l <= 5; ++l) {
				int wallX = 11 + l * 8;
				addStructure(GOTStructureYiTiTownWall.Left(false), wallX, -towerX, 0);
				addStructure(GOTStructureYiTiTownWall.Right(false), -wallX, -towerX, 0);
				addStructure(GOTStructureYiTiTownWall.Left(false), towerX, wallX, 1);
				addStructure(GOTStructureYiTiTownWall.Right(false), towerX, -wallX, 1);
				addStructure(GOTStructureYiTiTownWall.Left(false), -wallX, towerX, 2);
				addStructure(GOTStructureYiTiTownWall.Right(false), wallX, towerX, 2);
				addStructure(GOTStructureYiTiTownWall.Left(false), -towerX, -wallX, 3);
				addStructure(GOTStructureYiTiTownWall.Right(false), -towerX, wallX, 3);
			}
			int lampX = 17;
			addStructure(new GOTStructureYiTiLamp(false), -lampX, -lampX, 2, false);
			addStructure(new GOTStructureYiTiLamp(false), lampX, -lampX, 2, false);
			addStructure(new GOTStructureYiTiLamp(false), -lampX, lampX, 0, false);
			addStructure(new GOTStructureYiTiLamp(false), lampX, lampX, 0, false);
			lampX = 45;
			addStructure(new GOTStructureYiTiLamp(false), -lampX, -lampX, 2, false);
			addStructure(new GOTStructureYiTiLamp(false), lampX, -lampX, 2, false);
			addStructure(new GOTStructureYiTiLamp(false), -lampX, lampX, 0, false);
			addStructure(new GOTStructureYiTiLamp(false), lampX, lampX, 0, false);
			lampX = 7;
			int lampZ = 64;
			addStructure(new GOTStructureYiTiLamp(false), -lampX, lampZ, 2, false);
			addStructure(new GOTStructureYiTiLamp(false), lampX, lampZ, 2, false);
		}

		public void setupGate(Random random) {
			addStructure(new GOTStructureYiTiFortress(false), 0, 20, 0, true);
			addStructure(new GOTStructureYiTiGate(false), 0, 7, 0, true);
		}

		public void setupGateRoad(Random random) {
			addStructure(new GOTStructureYiTiFortress(false), -4, 25, 1, true);
			addStructure(new GOTStructureYiTiGate(false), 0, 7, 0, true);
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

		public void setupTown(Random random) {
			int marketZ;
			addStructure(new GOTStructureNPCRespawner(false) {

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
					addStructure(new GOTStructureNPCRespawner(false) {

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
				addStructure(new GOTStructureYiTiGarden(false), 0, 10, 2, true);
			} else {
				addStructure(new GOTStructureYiTiStatue(false), 0, 6, 2, true);
			}
			int mansionX = 12;
			int mansionZ = 20;
			addStructure(new GOTStructureYiTiLargeTownHouse(false), -mansionX, -mansionZ, 2, true);
			addStructure(new GOTStructureYiTiLargeTownHouse(false), mansionX, -mansionZ, 2, true);
			addStructure(new GOTStructureYiTiLargeTownHouse(false), -mansionX, mansionZ, 0, true);
			addStructure(new GOTStructureYiTiLargeTownHouse(false), mansionX, mansionZ, 0, true);
			addStructure(new GOTStructureYiTiLargeTownHouse(false), -mansionZ, -mansionX, 1, true);
			addStructure(new GOTStructureYiTiLargeTownHouse(false), -mansionZ, mansionX, 1, true);
			addStructure(new GOTStructureYiTiLargeTownHouse(false), mansionZ, -mansionX, 3, true);
			addStructure(new GOTStructureYiTiLargeTownHouse(false), mansionZ, mansionX, 3, true);
			for (int l = 0; l <= 3; ++l) {
				int houseX = 10 + 14 * l;
				int houseZ1 = 58;
				int houseZ2 = 68;
				if (l <= 2) {
					if (l >= 1) {
						if (l == 1) {
							addStructure(new GOTStructureYiTiTavernTown(false), -houseX - 7, -houseZ1, 0, true);
						}
					} else {
						addStructure(new GOTStructureYiTiTownHouse(false), -houseX, -houseZ1, 0, true);
					}
					addStructure(new GOTStructureYiTiTownHouse(false), houseX, -houseZ1, 0, true);
					if (l >= 1) {
						addStructure(new GOTStructureYiTiTownHouse(false), -houseX, houseZ1, 2, true);
						addStructure(new GOTStructureYiTiTownHouse(false), houseX, houseZ1, 2, true);
					}
					addStructure(new GOTStructureYiTiTownHouse(false), -houseZ1, -houseX, 3, true);
					addStructure(new GOTStructureYiTiTownHouse(false), -houseZ1, houseX, 3, true);
					addStructure(new GOTStructureYiTiTownHouse(false), houseZ1, -houseX, 1, true);
					addStructure(new GOTStructureYiTiTownHouse(false), houseZ1, houseX, 1, true);
				}
				if (l == 1) {
					addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -houseX, -houseZ2, 2, true);
					addStructure(new GOTStructureYiTiVillageFarm.Tree(false), houseX, -houseZ2, 2, true);
					addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -houseX, houseZ2, 0, true);
					addStructure(new GOTStructureYiTiVillageFarm.Tree(false), houseX, houseZ2, 0, true);
					addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -houseZ2, -houseX, 1, true);
					addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -houseZ2, houseX, 1, true);
					addStructure(new GOTStructureYiTiVillageFarm.Tree(false), houseZ2, -houseX, 3, true);
					addStructure(new GOTStructureYiTiVillageFarm.Tree(false), houseZ2, houseX, 3, true);
					continue;
				}
				addStructure(new GOTStructureYiTiTownHouse(false), -houseX, -houseZ2, 2, true);
				addStructure(l == 3 ? new GOTStructureYiTiSmithy(false) : new GOTStructureYiTiTownHouse(false), houseX, -houseZ2, 2, true);
				addStructure(new GOTStructureYiTiTownHouse(false), -houseX, houseZ2, 0, true);
				addStructure(new GOTStructureYiTiTownHouse(false), houseX, houseZ2, 0, true);
				addStructure(new GOTStructureYiTiTownHouse(false), -houseZ2, -houseX, 1, true);
				addStructure(new GOTStructureYiTiTownHouse(false), -houseZ2, houseX, 1, true);
				addStructure(new GOTStructureYiTiTownHouse(false), houseZ2, -houseX, 3, true);
				addStructure(new GOTStructureYiTiTownHouse(false), houseZ2, houseX, 3, true);
			}
			int marketX = 4;
			for (int l = 0; l <= 2; ++l) {
				marketZ = 56 - l * 7;
				addStructure(GOTStructureYiTiMarketStall.getRandomStall(random, false), -marketX, marketZ, 1, true);
				addStructure(GOTStructureYiTiMarketStall.getRandomStall(random, false), marketX, marketZ, 3, true);
			}
			marketX = 14;
			marketZ = 59;
			addStructure(GOTStructureYiTiMarketStall.getRandomStall(random, false), -marketX, marketZ, 2, true);
			addStructure(GOTStructureYiTiMarketStall.getRandomStall(random, false), marketX, marketZ, 2, true);
			int gardenX = 58;
			addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -gardenX + 5, -gardenX, 0, true);
			addStructure(new GOTStructureYiTiVillageFarm.Tree(false), gardenX - 5, -gardenX, 0, true);
			addStructure(new GOTStructureYiTiVillageFarm.Tree(false), -gardenX + 5, gardenX, 2, true);
			addStructure(new GOTStructureYiTiVillageFarm.Tree(false), gardenX - 5, gardenX, 2, true);
			int wellX = 69;
			int wellZ = 63;
			addStructure(new GOTStructureYiTiWell(false), -wellX, -wellZ, 1, true);
			addStructure(new GOTStructureYiTiWell(false), -wellZ, -wellX, 2, true);
			addStructure(new GOTStructureYiTiWell(false), wellX, -wellZ, 3, true);
			addStructure(new GOTStructureYiTiWell(false), wellZ, -wellX, 2, true);
			addStructure(new GOTStructureYiTiWell(false), -wellX, wellZ, 1, true);
			addStructure(new GOTStructureYiTiWell(false), -wellZ, wellX, 0, true);
			addStructure(new GOTStructureYiTiWell(false), wellX, wellZ, 3, true);
			addStructure(new GOTStructureYiTiWell(false), wellZ, wellX, 0, true);
			addStructure(new GOTStructureYiTiGatehouse(false), 0, 94, 2, true);
			int towerX = 90;
			addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setLeftLadder(), -towerX, -towerX - 3, 0, true);
			addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setRightLadder(), towerX, -towerX - 3, 0, true);
			addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setRightLadder(), -towerX, towerX + 3, 2, true);
			addStructure(new GOTStructureYiTiTower(false).disableDoor().setBackLadder().setLeftLadder(), towerX, towerX + 3, 2, true);
			addStructure(GOTStructureYiTiTownWall.Centre(false), 0, -towerX, 0);
			addStructure(GOTStructureYiTiTownWall.Centre(false), towerX, 0, 1);
			addStructure(GOTStructureYiTiTownWall.Centre(false), -towerX, 0, 3);
			for (int l = 0; l <= 9; ++l) {
				int wallX = 11 + l * 8;
				addStructure(GOTStructureYiTiTownWall.Left(false), wallX, -towerX, 0);
				addStructure(GOTStructureYiTiTownWall.Right(false), -wallX, -towerX, 0);
				addStructure(GOTStructureYiTiTownWall.Left(false), towerX, wallX, 1);
				addStructure(GOTStructureYiTiTownWall.Right(false), towerX, -wallX, 1);
				addStructure(GOTStructureYiTiTownWall.Left(false), -wallX, towerX, 2);
				addStructure(GOTStructureYiTiTownWall.Right(false), wallX, towerX, 2);
				addStructure(GOTStructureYiTiTownWall.Left(false), -towerX, -wallX, 3);
				addStructure(GOTStructureYiTiTownWall.Right(false), -towerX, wallX, 3);
			}
			int lampX = 7;
			int lampZ = 96;
			addStructure(new GOTStructureYiTiLamp(false), -lampX, lampZ, 2, false);
			addStructure(new GOTStructureYiTiLamp(false), lampX, lampZ, 2, false);
		}

		public void setupVillage(Random random) {
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityYiTiMan.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureNPCRespawner(false) {

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
			addStructure(new GOTStructureYiTiWell(false), 0, -2, 0, true);
			int signX = 12;
			addStructure(new GOTStructureYiTiVillageSign(false), -signX, 0, 1, true);
			addStructure(new GOTStructureYiTiVillageSign(false), signX, 0, 3, true);
			addStructure(new GOTStructureYiTiLargeTownHouse(false), 0, -centreSide, 2, true);
			if (random.nextBoolean()) {
				addStructure(new GOTStructureYiTiTavern(false), -pathEnd, 0, 1, true);
				addStructure(getOtherSettlementStructure(random), pathEnd, 0, 3, true);
			} else {
				addStructure(getOtherSettlementStructure(random), -pathEnd, 0, 1, true);
				addStructure(new GOTStructureYiTiTavern(false), pathEnd, 0, 3, true);
			}
			int rowHouses = 3;
			for (int l = -rowHouses; l <= rowHouses; ++l) {
				int i1 = l * 18;
				int k1 = pathSide;
				if (Math.abs(i1) <= 15) {
					k1 += 15 - pathSide;
				}
				if (Math.abs(l) >= 1) {
					addStructure(new GOTStructureYiTiHouse(false), i1, -k1, 2);
				}
				addStructure(new GOTStructureYiTiHouse(false), i1, k1, 0);
				int k2 = k1 + 20;
				if (l != 0) {
					if (random.nextInt(3) == 0) {
						addStructure(getRandomVillageFarm(random), i1, -k2, 2);
					} else {
						addStructure(new GOTStructureHayBales(false), i1, -k2, 2);
					}
				}
				if (random.nextInt(3) == 0) {
					addStructure(getRandomVillageFarm(random), i1, k2, 0);
					continue;
				}
				addStructure(new GOTStructureHayBales(false), i1, k2, 0);
			}
		}

	}
}