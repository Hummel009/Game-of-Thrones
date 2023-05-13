package got.common.world.structure.essos.jogos;

import got.common.entity.essos.jogos.GOTEntityJogos;
import got.common.entity.essos.jogos.GOTEntityJogosArcher;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.GOTStructureNPCRespawner;
import got.common.world.structure.other.GOTVillageGen;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureJogosVillage extends GOTVillageGen {
	public boolean isBig;

	public GOTStructureJogosVillage(GOTBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 5;
		fixedVillageChunkRadius = 5;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public GOTStructureJogosVillage setIsBig() {
		isBig = true;
		return this;
	}

	public enum VillageType {
		SMALL, BIG

	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;
		public int numOuterHouses;

		public Instance(GOTStructureJogosVillage village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			setupVillage(random);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		public void setupVillage(Random random) {
			if (villageType == VillageType.SMALL) {
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
			} else if (villageType == VillageType.BIG) {
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
			if (villageType == VillageType.SMALL) {
				minOuterSize = MathHelper.getRandomIntegerInRange(random, 15, 25);
			} else if (villageType == VillageType.BIG) {
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
		public void setupVillageProperties(Random random) {
			if (isBig) {
				villageType = VillageType.BIG;
				numOuterHouses = 13;
			}
			if (random.nextInt(3) == 0) {
				villageType = VillageType.BIG;
				numOuterHouses = MathHelper.getRandomIntegerInRange(random, 8, 14);
			} else {
				villageType = VillageType.SMALL;
				numOuterHouses = MathHelper.getRandomIntegerInRange(random, 4, 7);
			}
		}

	}

}
