package got.common.world.structure.westeros.wildling;

import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
import got.common.entity.westeros.wildling.GOTEntityWildlingArcher;
import got.common.entity.westeros.wildling.GOTEntityWildlingAxeThrower;
import got.common.entity.westeros.wildling.GOTEntityWildlingWarrior;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennArcher;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennAxeThrower;
import got.common.entity.westeros.wildling.thenn.GOTEntityThennWarrior;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.structure.essos.mossovy.GOTStructureMossovyWell;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.GOTStructureHayBales;
import got.common.world.structure.other.GOTStructureNPCRespawner;
import got.common.world.structure.other.LocationInfo;
import got.common.world.structure.westeros.wildling.thenn.GOTStructureThennChieftainHouse;
import got.common.world.structure.westeros.wildling.thenn.GOTStructureThennHouse;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureWildlingSettlement extends GOTStructureBaseSettlement {
	private Type type;
	private boolean forcedType;

	public GOTStructureWildlingSettlement(GOTBiome biome, float f) {
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
		DEFAULT, HARDHOME, CRASTER, THENN
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
				case HARDHOME:
					setupHardhome();
					break;
				case DEFAULT:
					setupVillage();
					break;
				case THENN:
					setupThenn();
					break;
				case CRASTER:
					addStructure(new GOTStructureWildlingKeep(false), -7, 0, 2, true);
					addStructure(new GOTStructureWildlingBarn(false), 7, 6, 2, true);
					break;
			}
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int innerOut;
			int outerOut;
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (type == Type.DEFAULT || type == Type.THENN) {
				int dSq = i * i + k * k;
				int imn = 15 + random.nextInt(4);
				if (dSq < imn * imn || i1 <= 64 && k1 <= 3 + random.nextInt(2)) {
					return GOTBezierType.PATH_DIRTY;
				}
			} else if (type == Type.HARDHOME) {
				innerOut = 18;
				if (i1 <= innerOut && k1 <= innerOut && (i1 >= 12 || k1 >= 12)) {
					return GOTBezierType.PATH_DIRTY;
				}
				if (i1 <= 3 && k1 >= innerOut && k1 <= 86 || k1 <= 3 && i1 >= innerOut && i1 <= 86) {
					return GOTBezierType.PATH_DIRTY;
				}
				outerOut = 66;
				if (i1 <= outerOut && k1 <= outerOut && (i1 >= 60 || k1 >= 60)) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			return null;
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			return block == Blocks.snow || block == Blocks.ice;
		}

		private void setupHardhome() {
			addStructure(new StructureRespawner1(), 0, 0, 0);
			int spawnerX = 60;
			for (int i1 : new int[]{-spawnerX, spawnerX}) {
				for (int k1 : new int[]{-spawnerX, spawnerX}) {
					addStructure(new StructureRespawner2(), i1, k1, 0);
				}
			}
			addStructure(new GOTStructureWildlingChieftainHouse(false).setIsHardhome(), 0, 6, 2, true);
			int mansionX = 12;
			int mansionZ = 20;
			addStructure(new GOTStructureWildlingHouse(false), -mansionX, -mansionZ, 2, true);
			addStructure(new GOTStructureWildlingHouse(false), mansionX, -mansionZ, 2, true);
			addStructure(new GOTStructureWildlingHouse(false), -mansionX, mansionZ, 0, true);
			addStructure(new GOTStructureWildlingHouse(false), mansionX, mansionZ, 0, true);
			addStructure(new GOTStructureWildlingHouse(false), -mansionZ, -mansionX, 1, true);
			addStructure(new GOTStructureWildlingHouse(false), -mansionZ, mansionX, 1, true);
			addStructure(new GOTStructureWildlingHouse(false), mansionZ, -mansionX, 3, true);
			addStructure(new GOTStructureWildlingHouse(false), mansionZ, mansionX, 3, true);
			for (int l = 0; l <= 3; ++l) {
				int houseX = 10 + 14 * l;
				int houseZ1 = 58;
				int houseZ2 = 68;
				if (l <= 2) {
					if (l >= 1) {
						if (l == 1) {
							addStructure(new GOTStructureWildlingHouse(false), -houseX - 7, -houseZ1, 0, true);
						}
					} else {
						addStructure(new GOTStructureWildlingHouse(false), -houseX, -houseZ1, 0, true);
					}
					addStructure(new GOTStructureWildlingHouse(false), houseX, -houseZ1, 0, true);
					if (l >= 1) {
						addStructure(new GOTStructureWildlingHouse(false), -houseX, houseZ1, 2, true);
						addStructure(new GOTStructureWildlingHouse(false), houseX, houseZ1, 2, true);
					}
					addStructure(new GOTStructureWildlingHouse(false), -houseZ1, -houseX, 3, true);
					addStructure(new GOTStructureWildlingHouse(false), -houseZ1, houseX, 3, true);
					addStructure(new GOTStructureWildlingHouse(false), houseZ1, -houseX, 1, true);
					addStructure(new GOTStructureWildlingHouse(false), houseZ1, houseX, 1, true);
				}
				addStructure(new GOTStructureWildlingHouse(false), -houseX, -houseZ2, 2, true);
				addStructure(new GOTStructureWildlingHouse(false), houseX, -houseZ2, 2, true);
				addStructure(new GOTStructureWildlingHouse(false), -houseX, houseZ2, 0, true);
				addStructure(new GOTStructureWildlingHouse(false), houseX, houseZ2, 0, true);
				addStructure(new GOTStructureWildlingHouse(false), -houseZ2, -houseX, 1, true);
				addStructure(new GOTStructureWildlingHouse(false), -houseZ2, houseX, 1, true);
				addStructure(new GOTStructureWildlingHouse(false), houseZ2, -houseX, 3, true);
				addStructure(new GOTStructureWildlingHouse(false), houseZ2, houseX, 3, true);
			}
			int gardenX = 58;
			addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), -gardenX + 5, -gardenX, 0, true);
			addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), gardenX - 5, -gardenX, 0, true);
			addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), -gardenX + 5, gardenX, 2, true);
			addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), gardenX - 5, gardenX, 2, true);
		}

		@Override
		public void setupSettlementProperties(Random random) {
			if (!forcedType) {
				type = Type.DEFAULT;
			}
		}

		private void setupThenn() {
			addStructure(new StructureRespawner3(), 0, 0, 0);
			addStructure(new StructureRespawner4(), 0, 0, 0);
			int pathEnd = 68;
			int pathSide = 7;
			int centreSide = 19;
			addStructure(new GOTStructureMossovyWell(false), 0, -2, 0, true);
			addStructure(new GOTStructureThennHouse(false).setIsBlacksmith(), 0, -centreSide, 2, true);
			addStructure(new GOTStructureThennHouse(false).setIsBlacksmith(), -pathEnd, 0, 1, true);
			addStructure(new GOTStructureThennChieftainHouse(false), pathEnd, 0, 3, true);
			int rowHouses = 3;
			for (int l = -rowHouses; l <= rowHouses; ++l) {
				int i1 = l * 18;
				int k1 = pathSide;
				if (Math.abs(i1) <= 15) {
					k1 += 15 - pathSide;
				}
				if (Math.abs(l) >= 1) {
					addStructure(new GOTStructureThennHouse(false), i1, -k1, 2);
				}
				addStructure(new GOTStructureThennHouse(false), i1, k1, 0);
				int k2 = k1 + 20;
				if (l != 0) {
					addStructure(new GOTStructureHayBales(false), i1, -k2, 2);
				}
				addStructure(new GOTStructureHayBales(false), i1, k2, 0);
			}
		}

		private void setupVillage() {
			addStructure(new StructureRespawner5(), 0, 0, 0);
			addStructure(new StructureRespawner6(), 0, 0, 0);
			int pathEnd = 68;
			int pathSide = 7;
			int centreSide = 19;
			addStructure(new GOTStructureMossovyWell(false), 0, -2, 0, true);
			addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), 0, -centreSide, 2, true);
			addStructure(new GOTStructureWildlingHouse(false).setIsTramp(), -pathEnd, 0, 1, true);
			addStructure(new GOTStructureWildlingChieftainHouse(false), pathEnd, 0, 3, true);
			int rowHouses = 3;
			for (int l = -rowHouses; l <= rowHouses; ++l) {
				int i1 = l * 18;
				int k1 = pathSide;
				if (Math.abs(i1) <= 15) {
					k1 += 15 - pathSide;
				}
				if (Math.abs(l) >= 1) {
					addStructure(new GOTStructureWildlingHouse(false), i1, -k1, 2);
				}
				addStructure(new GOTStructureWildlingHouse(false), i1, k1, 0);
				int k2 = k1 + 20;
				if (l != 0) {
					addStructure(new GOTStructureHayBales(false), i1, -k2, 2);
				}
				addStructure(new GOTStructureHayBales(false), i1, k2, 0);
			}
		}

		public void setType(Type type) {
			this.type = type;
		}

		private static class StructureRespawner1 extends GOTStructureNPCRespawner {
			private StructureRespawner1() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityWildlingWarrior.class);
				spawner.setCheckRanges(80, -12, 12, 100);
				spawner.setSpawnRanges(60, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner2 extends GOTStructureNPCRespawner {
			private StructureRespawner2() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityWildlingArcher.class);
				spawner.setSpawnClass2(GOTEntityWildlingAxeThrower.class);
				spawner.setCheckRanges(50, -12, 12, 16);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner3 extends GOTStructureNPCRespawner {
			private StructureRespawner3() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityThennWarrior.class);
				spawner.setCheckRanges(40, -12, 12, 40);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner4 extends GOTStructureNPCRespawner {
			private StructureRespawner4() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityThennArcher.class);
				spawner.setSpawnClass2(GOTEntityThennAxeThrower.class);
				spawner.setCheckRanges(40, -12, 12, 16);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner5 extends GOTStructureNPCRespawner {
			private StructureRespawner5() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityWildlingWarrior.class);
				spawner.setCheckRanges(40, -12, 12, 40);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner6 extends GOTStructureNPCRespawner {
			private StructureRespawner6() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityWildlingArcher.class);
				spawner.setSpawnClass2(GOTEntityWildlingAxeThrower.class);
				spawner.setCheckRanges(40, -12, 12, 16);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}
	}
}