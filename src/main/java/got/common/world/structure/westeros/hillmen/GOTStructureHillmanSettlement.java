package got.common.world.structure.westeros.hillmen;

import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
import got.common.entity.westeros.hillmen.GOTEntityHillman;
import got.common.entity.westeros.hillmen.GOTEntityHillmanArcher;
import got.common.entity.westeros.hillmen.GOTEntityHillmanWarrior;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.structure.essos.mossovy.GOTStructureMossovyWell;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.GOTStructureHayBales;
import got.common.world.structure.other.GOTStructureNPCRespawner;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureHillmanSettlement extends GOTStructureBaseSettlement {
	public GOTStructureHillmanSettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 6;
		fixedSettlementChunkRadius = 6;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc, Collection<GOTFixer.SpawnInfo> spawnInfos) {
		return new Instance(world, i, k, random, loc, spawnInfos);
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance {

		private Instance(World world, int i, int k, Random random, LocationInfo loc, Collection<GOTFixer.SpawnInfo> spawnInfos) {
			super(world, i, k, random, loc, spawnInfos);
		}

		@Override
		public void addSettlementStructures(Random random) {
			super.addSettlementStructures(random);
			addStructure(new StructureRespawner1(), 0, 0, 0);
			addStructure(new StructureRespawner2(), 0, 0, 0);
			int pathEnd = 68;
			int pathSide = 7;
			int centreSide = 19;
			addStructure(new GOTStructureMossovyWell(false), 0, -2, 0, true);
			addStructure(new GOTStructureHillmanHouse(false), 0, -centreSide, 2, true);
			addStructure(new GOTStructureHillmanFortress(false), -pathEnd, 0, 1, true);
			addStructure(new GOTStructureHillmanTavern(false), pathEnd, 0, 3, true);
			int rowHouses = 3;
			for (int l = -rowHouses; l <= rowHouses; ++l) {
				int i1 = l * 18;
				int k1 = pathSide;
				if (Math.abs(i1) <= 15) {
					k1 += 15 - pathSide;
				}
				if (Math.abs(l) >= 1) {
					addStructure(new GOTStructureHillmanHouse(false), i1, -k1, 2);
				}
				addStructure(new GOTStructureHillmanHouse(false), i1, k1, 0);
				int k2 = k1 + 20;
				if (l != 0) {
					addStructure(new GOTStructureHayBales(false), i1, -k2, 2);
				}
				addStructure(new GOTStructureHayBales(false), i1, k2, 0);
			}
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			int dSq = i * i + k * k;
			int imn = 15 + random.nextInt(4);
			if (dSq < imn * imn || i1 <= 64 && k1 <= 3 + random.nextInt(2)) {
				return GOTBezierType.PATH_DIRTY;
			}
			return null;
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupSettlementProperties(Random random) {
		}

		private static class StructureRespawner1 extends GOTStructureNPCRespawner {
			private StructureRespawner1() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityHillman.class);
				spawner.setCheckRanges(40, -12, 12, 40);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner2 extends GOTStructureNPCRespawner {
			private StructureRespawner2() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityHillmanWarrior.class);
				spawner.setSpawnClass2(GOTEntityHillmanArcher.class);
				spawner.setCheckRanges(40, -12, 12, 16);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}
	}
}