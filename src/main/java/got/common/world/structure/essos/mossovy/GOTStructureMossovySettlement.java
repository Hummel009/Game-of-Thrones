package got.common.world.structure.essos.mossovy;

import got.common.entity.essos.mossovy.GOTEntityMossovyMan;
import got.common.entity.essos.mossovy.GOTEntityMossovyWitcher;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.GOTStructureHayBales;
import got.common.world.structure.other.GOTStructureNPCRespawner;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureMossovySettlement extends GOTStructureBaseSettlement {
	public boolean isFort;

	public GOTStructureMossovySettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 5;
		fixedSettlementChunkRadius = 5;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance<GOTStructureMossovySettlement> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public GOTStructureMossovySettlement setIsFort() {
		isFort = true;
		settlementChunkRadius = 2;
		fixedSettlementChunkRadius = 2;
		return this;
	}

	public enum Type {
		VILLAGE, FORT
	}

	public class Instance extends GOTStructureBaseSettlement.AbstractInstance<GOTStructureMossovySettlement> {
		public Type type;

		public Instance(GOTStructureMossovySettlement settlement, World world, int i, int k, Random random, LocationInfo loc) {
			super(settlement, world, i, k, random, loc);
		}

		@Override
		public void addSettlementStructures(Random random) {
			switch (type) {
				case VILLAGE:
					setupSettlement(random);
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
			return null;
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		public void setupFort(Random random) {
			addStructure(new GOTStructureMossovyCastle(false), 0, 0, 0, true);
		}

		public void setupSettlement(Random random) {
			addStructure(new GOTStructureMossovyWell(false), 0, -4, 0, true);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityMossovyMan.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityMossovyWitcher.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureMossovyOffice(false), -21, 0, 1);
			addStructure(new GOTStructureMossovyBarn(false), 0, -21, 2);
			addStructure(new GOTStructureMossovySmithy(false), 21, 0, 3);
			addStructure(new GOTStructureMossovyInn(false), 0, 21, 0);
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
				if (turn8 >= 3.0f && turn8 < 5.0f) {
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
					addStructure(new GOTStructureMossovyHouse(false), i, k, r);
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
			addStructure(new GOTStructureMossovyLampPost(false), -signPos, -signPos + signDisp, 1);
			addStructure(new GOTStructureMossovyLampPost(false), signPos, -signPos + signDisp, 3);
			addStructure(new GOTStructureMossovyLampPost(false), -signPos, signPos - signDisp, 1);
			addStructure(new GOTStructureMossovyLampPost(false), signPos, signPos - signDisp, 3);
			int farmX = 38;
			int farmZ = 17;
			int farmSize = 6;
			addStructure(new GOTStructureMossovyTrampHouse(false), -farmX + farmSize, -farmZ, 1);
			addStructure(new GOTStructureMossovyTrampHouse(false), -farmZ + farmSize, -farmX, 1);
			addStructure(new GOTStructureMossovyTrampHouse(false), farmX - farmSize, -farmZ, 3);
			addStructure(new GOTStructureMossovyTrampHouse(false), farmZ - farmSize, -farmX, 3);
			addStructure(new GOTStructureMossovyTrampHouse(false), -farmX + farmSize, farmZ, 1);
			addStructure(new GOTStructureMossovyTrampHouse(false), farmX - farmSize, farmZ, 3);
		}

		@Override
		public void setupSettlementProperties(Random random) {
			if (isFort) {
				type = Type.FORT;
			} else {
				type = Type.VILLAGE;
			}
		}
	}
}