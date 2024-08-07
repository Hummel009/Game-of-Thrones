package got.common.world.structure.essos.golden;

import got.common.entity.essos.golden.GOTEntityGoldenCompanySpearman;
import got.common.entity.essos.golden.GOTEntityGoldenCompanyWarrior;
import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
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

public class GOTStructureGoldenCompanySettlement extends GOTStructureBaseSettlement {
	public GOTStructureGoldenCompanySettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 5;
		fixedSettlementChunkRadius = 5;
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
			addStructure(new GOTStructureGoldenCompanyWatchtower(false), 0, -4, 0, true);
			addStructure(new StructureRespawner(), 0, 0, 0);
			addStructure(new GOTStructureGoldenCompanyTent(false), -21, 0, 1);
			addStructure(new GOTStructureGoldenCompanyTent(false), 0, -21, 2);
			addStructure(new GOTStructureGoldenCompanyTent(false), 21, 0, 3);
			addStructure(new GOTStructureGoldenCompanyTent(false), 0, 21, 0);
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
					addStructure(new GOTStructureGoldenCompanyTent(false), i, k, r);
				}
			}
			int farmX = 38;
			int farmZ = 17;
			int farmSize = 6;
			addStructure(new GOTStructureGoldenCompanyTent(false), -farmX + farmSize, -farmZ, 1);
			addStructure(new GOTStructureGoldenCompanyTent(false), -farmZ + farmSize, -farmX, 1);
			addStructure(new GOTStructureGoldenCompanyTent(false), farmX - farmSize, -farmZ, 3);
			addStructure(new GOTStructureGoldenCompanyTent(false), farmZ - farmSize, -farmX, 3);
			addStructure(new GOTStructureGoldenCompanyTent(false), -farmX + farmSize, farmZ, 1);
			addStructure(new GOTStructureGoldenCompanyTent(false), farmX - farmSize, farmZ, 3);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			int dSq = i * i + k * k;
			int imn = 20 + random.nextInt(4);
			if (dSq < imn * imn) {
				return GOTBezierType.PATH_SANDY;
			}
			int omn = 53 - random.nextInt(4);
			int omx = 60 + random.nextInt(4);
			if (dSq > omn * omn && dSq < omx * omx || dSq < 2809 && Math.abs(i1 - k1) <= 2 + random.nextInt(4)) {
				return GOTBezierType.PATH_SANDY;
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

		private static class StructureRespawner extends GOTStructureNPCRespawner {
			private StructureRespawner() {
				super(false);
			}

			@Override
			public void setupRespawner(GOTEntityNPCRespawner spawner) {
				spawner.setSpawnClass1(GOTEntityGoldenCompanyWarrior.class);
				spawner.setSpawnClass2(GOTEntityGoldenCompanySpearman.class);
				spawner.setCheckRanges(40, -12, 12, 40);
				spawner.setSpawnRanges(20, -6, 6, 64);
				spawner.setBlockEnemySpawns(60);
			}
		}
	}
}