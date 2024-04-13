package got.common.world.structure.other;

import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureEmptySettlement extends GOTStructureBaseSettlement {
	protected GOTStructureEmptySettlement() {
		super(GOTBiome.ocean);
		spawnChance = 0.0f;
		settlementChunkRadius = 3;
		fixedSettlementChunkRadius = 0;
	}

	protected GOTStructureEmptySettlement(int i) {
		super(GOTBiome.ocean);
		spawnChance = 0.0f;
		settlementChunkRadius = i;
		fixedSettlementChunkRadius = i;
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
		}
	}
}