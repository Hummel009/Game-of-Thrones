package got.common.world.structure.essos.jogos;

import java.util.Random;

import got.common.entity.essos.jogos.GOTEntityJogos;
import got.common.entity.essos.jogos.GOTEntityJogosArcher;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.GOTStructureNPCRespawner;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTStructureJogosSettlement extends GOTStructureBaseSettlement {
	public Type type;
	public boolean forcedType;

	public GOTStructureJogosSettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 5;
		fixedSettlementChunkRadius = 5;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance<GOTStructureJogosSettlement> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc, type, forcedType);
	}

	public GOTStructureBaseSettlement type(Type t, int radius) {
		type = t;
		settlementChunkRadius = radius;
		fixedSettlementChunkRadius = radius;
		forcedType = true;
		return this;
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance<GOTStructureJogosSettlement> {
		public Type type;
		public boolean forcedType;
		public int numOuterHouses;

		public Instance(GOTStructureJogosSettlement settlement, World world, int i, int k, Random random, LocationInfo loc, Type t, boolean b) {
			super(settlement, world, i, k, random, loc);
			type = t;
			forcedType = b;
		}

		@Override
		public void addSettlementStructures(Random random) {
			if (type == Type.SMALL) {
				numOuterHouses = 6;
				addStructure(new GOTStructureNPCRespawner(false) {

					@Override
					public void setupRespawner(GOTEntityNPCRespawner spawner) {
						spawner.setSpawnClass(GOTEntityJogos.class);
						spawner.setCheckRanges(64, -12, 12, 24);
						spawner.setSpawnRanges(32, -6, 6, 32);
						spawner.setBlockEnemySpawnRange(40);
					}
				}, 0, 0, 0);
				addStructure(new GOTStructureNPCRespawner(false) {

					@Override
					public void setupRespawner(GOTEntityNPCRespawner spawner) {
						spawner.setSpawnClasses(GOTEntityJogos.class, GOTEntityJogosArcher.class);
						spawner.setCheckRanges(64, -12, 12, 12);
						spawner.setSpawnRanges(32, -6, 6, 32);
						spawner.setBlockEnemySpawnRange(40);
					}
				}, 0, 0, 0);
				addStructure(new GOTStructureJogosTentLarge(false), 0, -8, 0, true);
			} else if (type == Type.BIG) {
				numOuterHouses = 13;
				addStructure(new GOTStructureNPCRespawner(false) {

					@Override
					public void setupRespawner(GOTEntityNPCRespawner spawner) {
						spawner.setSpawnClass(GOTEntityJogos.class);
						spawner.setCheckRanges(80, -12, 12, 50);
						spawner.setSpawnRanges(40, -8, 8, 40);
						spawner.setBlockEnemySpawnRange(60);
					}
				}, 0, 0, 0);
				addStructure(new GOTStructureNPCRespawner(false) {

					@Override
					public void setupRespawner(GOTEntityNPCRespawner spawner) {
						spawner.setSpawnClasses(GOTEntityJogos.class, GOTEntityJogosArcher.class);
						spawner.setCheckRanges(80, -12, 12, 24);
						spawner.setSpawnRanges(40, -8, 8, 40);
						spawner.setBlockEnemySpawnRange(60);
					}
				}, 0, 0, 0);
				addStructure(new GOTStructureJogosWell(false), 0, 0, 0, true);
				addStructure(new GOTStructureJogosChiefTent(false), 0, 14, 0, true);
				addStructure(new GOTStructureJogosShamanTent(false), 0, -14, 2, true);
				addStructure(new GOTStructureJogosTentLarge(false), -14, 0, 1, true);
				addStructure(new GOTStructureJogosTentLarge(false), 14, 0, 3, true);
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
				addStructure(new GOTStructureJogosTent(false), i, k, r);
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
	}

	public enum Type {
		SMALL, BIG

	}
}
