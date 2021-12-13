package got.common.world.structure.essos.mossovy;

import java.util.Random;

import got.common.entity.essos.mossovy.*;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTStructureMossovyVillage extends GOTVillageGen {
	public GOTStructureMossovyVillage(GOTBiome biome, float f) {
		super(biome);
		gridScale = 16;
		gridRandomDisplace = 2;
		spawnChance = f;
		villageChunkRadius = 6;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;

		public Instance(GOTStructureMossovyVillage village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addStructure(GOTStructureBase structure, int x, int z, int r, boolean force) {
			super.addStructure(structure, x, z, r, force);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new GOTStructureMossovyWell(false), 0, -4, 0, true);
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityMossovyMan.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClass(GOTEntityMossovyWitcher.class);
					spawner.setCheckRanges(40, -12, 12, 16);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			this.addStructure(new GOTStructureMossovyOffice(false), -21, 0, 1);
			this.addStructure(new GOTStructureMossovyBarn(false), 0, -21, 2);
			this.addStructure(new GOTStructureMossovySmithy(false), 21, 0, 3);
			this.addStructure(new GOTStructureMossovyInn(false), 0, 21, 0);
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
					this.addStructure(new GOTStructureMossovyHouse(false), i, k, r);
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
			this.addStructure(new GOTStructureMossovyLampPost(false), -signPos, -signPos + signDisp, 1);
			this.addStructure(new GOTStructureMossovyLampPost(false), signPos, -signPos + signDisp, 3);
			this.addStructure(new GOTStructureMossovyLampPost(false), -signPos, signPos - signDisp, 1);
			this.addStructure(new GOTStructureMossovyLampPost(false), signPos, signPos - signDisp, 3);
			int farmX = 38;
			int farmZ = 17;
			int farmSize = 6;
			this.addStructure(new GOTStructureMossovyThiefHouse(false), -farmX + farmSize, -farmZ, 1);
			this.addStructure(new GOTStructureMossovyThiefHouse(false), -farmZ + farmSize, -farmX, 1);
			this.addStructure(new GOTStructureMossovyThiefHouse(false), farmX - farmSize, -farmZ, 3);
			this.addStructure(new GOTStructureMossovyThiefHouse(false), farmZ - farmSize, -farmX, 3);
			this.addStructure(new GOTStructureMossovyThiefHouse(false), -farmX + farmSize, farmZ, 1);
			this.addStructure(new GOTStructureMossovyThiefHouse(false), farmX - farmSize, farmZ, 3);

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
				if ((dSq > omn * omn && dSq < omx * omx) || (dSq < 2809 && Math.abs(i1 - k1) <= 2 + random.nextInt(4))) {
					return GOTBezierType.PATH_DIRTY;
				}
			}
			return null;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupVillageProperties(Random random) {
			villageType = VillageType.VILLAGE;
		}

	}

	public enum VillageType {
		VILLAGE;
	}
}