package got.common.world.structure.essos.dothraki;

import got.common.entity.essos.dothraki.GOTEntityDothraki;
import got.common.entity.essos.dothraki.GOTEntityDothrakiArcher;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.GOTStructureNPCRespawner;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureDothrakiSettlement extends GOTStructureBaseSettlement {
	private Type type;
	private boolean forcedType;

	public GOTStructureDothrakiSettlement(GOTBiome biome, float f) {
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
		SMALL, BIG
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance {
		private final boolean forcedType;
		private int numOuterHouses;
		private Type type;

		private Instance(World world, int i, int k, Random random, LocationInfo loc, Collection<GOTFixer.SpawnInfo> spawnInfos, Type t, boolean b) {
			super(world, i, k, random, loc, spawnInfos);
			type = t;
			forcedType = b;
		}

		@Override
		public void addSettlementStructures(Random random) {
			super.addSettlementStructures(random);
			if (type == Type.SMALL) {
				numOuterHouses = 6;
				addStructure(new StructureRespawner1(), 0, 0, 0);
				addStructure(new StructureRespawner2(), 0, 0, 0);
				addStructure(new GOTStructureDothrakiTentLarge(false), 0, -8, 0, true);
			} else if (type == Type.BIG) {
				numOuterHouses = 13;
				addStructure(new StructureRespawner3(), 0, 0, 0);
				addStructure(new StructureRespawner4(), 0, 0, 0);
				addStructure(new GOTStructureDothrakiWell(false), 0, 0, 0, true);
				addStructure(new GOTStructureDothrakiKhalTent(false), 0, 14, 0, true);
				addStructure(new GOTStructureDothrakiKhalinTent(false), 0, -14, 2, true);
				addStructure(new GOTStructureDothrakiTentLarge(false), -14, 0, 1, true);
				addStructure(new GOTStructureDothrakiTentLarge(false), 14, 0, 3, true);
			}
			int minOuterSize = 0;
			if (type == Type.SMALL) {
				minOuterSize = MathHelper.getRandomIntegerInRange(random, 15, 25);
			} else if (type == Type.BIG) {
				minOuterSize = MathHelper.getRandomIntegerInRange(random, 35, 45);
			}
			float frac = 1.0f / numOuterHouses;
			float turn = 0.0f;
			while (turn < 1.0f) {
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
				int l = minOuterSize + random.nextInt(5);
				int i = Math.round(l * cos);
				int k = Math.round(l * sin);
				addStructure(new GOTStructureDothrakiTent(false), i, k, r);
			}
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupSettlementProperties(Random random) {
			if (!forcedType) {
				if (random.nextInt(4) == 0) {
					type = Type.BIG;
				} else {
					type = Type.SMALL;
				}
			}
		}

		@SuppressWarnings("unused")
		public Type getType() {
			return type;
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
				spawner.setSpawnClass1(GOTEntityDothraki.class);
				spawner.setCheckRanges(64, -12, 12, 24);
				spawner.setSpawnRanges(32, -6, 6, 32);
				spawner.setBlockEnemySpawns(40);
			}
		}

		private static class StructureRespawner2 extends GOTStructureNPCRespawner {
			private StructureRespawner2() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityDothraki.class);
				spawner.setSpawnClass2(GOTEntityDothrakiArcher.class);
				spawner.setCheckRanges(64, -12, 12, 12);
				spawner.setSpawnRanges(32, -6, 6, 32);
				spawner.setBlockEnemySpawns(40);
			}
		}

		private static class StructureRespawner3 extends GOTStructureNPCRespawner {
			private StructureRespawner3() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityDothraki.class);
				spawner.setCheckRanges(80, -12, 12, 50);
				spawner.setSpawnRanges(40, -8, 8, 40);
				spawner.setBlockEnemySpawns(60);
			}
		}

		private static class StructureRespawner4 extends GOTStructureNPCRespawner {
			private StructureRespawner4() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityDothraki.class);
				spawner.setSpawnClass2(GOTEntityDothrakiArcher.class);
				spawner.setCheckRanges(80, -12, 12, 24);
				spawner.setSpawnRanges(40, -8, 8, 40);
				spawner.setBlockEnemySpawns(60);
			}
		}
	}
}