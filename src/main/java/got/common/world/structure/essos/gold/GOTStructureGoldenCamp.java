package got.common.world.structure.essos.gold;

import got.common.entity.essos.gold.GOTEntityGoldenSpearman;
import got.common.entity.essos.gold.GOTEntityGoldenWarrior;
import got.common.entity.other.GOTEntityNPCRespawner;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.GOTStructureNPCRespawner;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureGoldenCamp extends GOTStructureBaseSettlement {
	public GOTStructureGoldenCamp(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 5;
		fixedSettlementChunkRadius = 5;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance<GOTStructureGoldenCamp> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos) {
		return new Instance(this, world, i, k, random, loc, filler, spawnInfos);
	}

	public static class Instance extends GOTStructureBaseSettlement.AbstractInstance<GOTStructureGoldenCamp> {
		public Instance(GOTStructureGoldenCamp settlement, World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos) {
			super(settlement, world, i, k, random, loc, filler, spawnInfos);
		}

		@Override
		public void addSettlementStructures(Random random) {
			super.addSettlementStructures(random);
			addStructure(new GOTStructureGoldenCampWatchtower(false), 0, -4, 0, true);
			addStructure(new GOTStructureNPCRespawner(false) {

				@Override
				public void setupRespawner(GOTEntityNPCRespawner spawner) {
					spawner.setSpawnClasses(GOTEntityGoldenWarrior.class, GOTEntityGoldenSpearman.class);
					spawner.setCheckRanges(40, -12, 12, 40);
					spawner.setSpawnRanges(20, -6, 6, 64);
					spawner.setBlockEnemySpawnRange(60);
				}
			}, 0, 0, 0);
			addStructure(new GOTStructureGoldenCampTent(false), -21, 0, 1);
			addStructure(new GOTStructureGoldenCampTent(false), 0, -21, 2);
			addStructure(new GOTStructureGoldenCampTent(false), 21, 0, 3);
			addStructure(new GOTStructureGoldenCampTent(false), 0, 21, 0);
			int houses = 20;
			float frac = 1.0f / houses;
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
				if (random.nextBoolean()) {
					int l = 61;
					int i = Math.round(l * cos);
					int k = Math.round(l * sin);
					addStructure(new GOTStructureGoldenCampTent(false), i, k, r);
				}
			}
			int farmX = 38;
			int farmZ = 17;
			int farmSize = 6;
			addStructure(new GOTStructureGoldenCampTent(false), -farmX + farmSize, -farmZ, 1);
			addStructure(new GOTStructureGoldenCampTent(false), -farmZ + farmSize, -farmX, 1);
			addStructure(new GOTStructureGoldenCampTent(false), farmX - farmSize, -farmZ, 3);
			addStructure(new GOTStructureGoldenCampTent(false), farmZ - farmSize, -farmX, 3);
			addStructure(new GOTStructureGoldenCampTent(false), -farmX + farmSize, farmZ, 1);
			addStructure(new GOTStructureGoldenCampTent(false), farmX - farmSize, farmZ, 3);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int dSq = i * i + k * k;
			int imn = 20 + random.nextInt(4);
			if (dSq < imn * imn) {
				return GOTBezierType.PATH_DIRTY;
			}
			int omn = 53 - random.nextInt(4);
			int omx = 60 + random.nextInt(4);
			int k1 = Math.abs(k);
			int i1 = Math.abs(i);
			if (dSq > omn * omn && dSq < omx * omx || dSq < 2809 && Math.abs(i1 - k1) <= 2 + random.nextInt(4)) {
				return GOTBezierType.PATH_DIRTY;
			}
			return null;
		}

		@Override
		public boolean isSettlementSpecificSurface(World world, int i, int j, int k) {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			return (block == Blocks.dirt || block == Blocks.sand) && meta == 1 || block == Blocks.sand && meta == 0;
		}

		@Override
		public void setupSettlementProperties(Random random) {
		}
	}
}