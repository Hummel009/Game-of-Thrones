package got.common.world.fixed;

import java.util.Random;

import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.essos.ghiscar.GOTStructureGhiscarPyramid;
import got.common.world.structure.other.*;
import net.minecraft.world.World;

public class GOTStructureGhiscarPyramid1 extends GOTVillageGen {
	public GOTStructureGhiscarPyramid1(GOTBiome biome, float f) {
		super(biome);
		gridScale = 10;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 3;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, GOTLocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureGhiscarPyramid1 village, World world, int i, int k, Random random, GOTLocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new GOTStructureGhiscarPyramid(false), 20, 0, 0, true);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isFlat() {
			return false;
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
