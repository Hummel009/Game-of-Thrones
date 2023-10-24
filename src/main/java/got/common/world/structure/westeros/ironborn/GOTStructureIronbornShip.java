package got.common.world.structure.westeros.ironborn;

import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTFixer;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Random;

public class GOTStructureIronbornShip extends GOTStructureBaseSettlement {
	public GOTStructureIronbornShip() {
		super(GOTBiome.ocean);
		spawnChance = 0.0f;
		settlementChunkRadius = 3;
		fixedSettlementChunkRadius = 0;
	}

	public GOTStructureIronbornShip(int i) {
		super(GOTBiome.ocean);
		spawnChance = 0.0f;
		settlementChunkRadius = i;
		fixedSettlementChunkRadius = i;
	}

	@Override
	public AbstractInstance<GOTStructureIronbornShip> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos) {
		return new Instance(this, world, i, k, random, loc, filler, spawnInfos);
	}

	public static class Instance extends AbstractInstance<GOTStructureIronbornShip> {
		public Instance(GOTStructureIronbornShip settlement, World world, int i, int k, Random random, LocationInfo loc, Runnable filler, Collection<GOTFixer.SpawnInfo> spawnInfos) {
			super(settlement, world, i, k, random, loc, filler, spawnInfos);
		}

		@Override
		public void addSettlementStructures(Random random) {
			super.addSettlementStructures(random);
			addStructure(new GOTStructureBase() {
				@Override
				public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
					setOriginAndRotation(world, i, j, k, rotation, 0);
					loadStrScan("euron_ship");
					generateStrScan(world, random, 16, 0, -58);
					return true;
				}
			}, 0, -4, 0, true);
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