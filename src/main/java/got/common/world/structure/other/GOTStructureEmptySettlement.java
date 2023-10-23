package got.common.world.structure.other;

import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureEmptySettlement extends GOTStructureBaseSettlement {
	public GOTStructureEmptySettlement() {
		super(GOTBiome.ocean);
		spawnChance = 0.0f;
		settlementChunkRadius = 3;
		fixedSettlementChunkRadius = 0;
	}

	public GOTStructureEmptySettlement(int i) {
		super(GOTBiome.ocean);
		spawnChance = 0.0f;
		settlementChunkRadius = i;
		fixedSettlementChunkRadius = i;
	}

	@Override
	public AbstractInstance<GOTStructureEmptySettlement> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos, GOTStructureBase specialStructure) {
		return new Instance(this, world, i, k, random, loc, filler, spawnInfos, specialStructure);
	}

	public static class Instance extends AbstractInstance<GOTStructureEmptySettlement> {
		public Instance(GOTStructureEmptySettlement settlement, World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos, GOTStructureBase specialStructure) {
			super(settlement, world, i, k, random, loc, filler, spawnInfos, specialStructure);
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