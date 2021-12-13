package got.common.world.fixed;

import java.util.Random;

import got.common.entity.westeros.legendary.captain.GOTEntityDenysMallister;
import got.common.entity.westeros.legendary.trader.GOTEntityMullin;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.gift.GOTStructureGiftCastle;
import net.minecraft.world.World;

public class GOTStructureShadowTower extends GOTVillageGen {
	public GOTStructureShadowTower(GOTBiome biome, float f) {
		super(biome);
		gridScale = 10;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 3;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class Castle extends GOTStructureGiftCastle {
		public Castle(boolean flag) {
			super(flag);
		}

		@Override
		public int getOriginZ() {
			return -30;
		}

		@Override
		public void spawnLegendaryMobs(World world) {
			spawnLegendaryNPC(new GOTEntityDenysMallister(world), world, 0, 1, 0);
			spawnLegendaryNPC(new GOTEntityMullin(world), world, 0, 1, 0);
		}
	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureShadowTower village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new Castle(false), 0, 0, 0, true);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupVillageProperties(Random random) {
		}
	}
}
