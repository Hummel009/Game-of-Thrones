package got.common.world.fixed;

import java.util.Random;

import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornBase;
import net.minecraft.world.World;

public class GOTStructureWallHole extends GOTVillageGen {
	public GOTStructureWallHole(GOTBiome biome, float f) {
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
		public Instance(GOTStructureWallHole village, World world, int i, int k, Random random, GOTLocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new Hole(false), 0, 0, 0, true);
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
	
	public static class Hole extends GOTStructureIronbornBase {
		public Hole(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0, 0);
	        int radius = 4;
	        for (int i13 = -radius; i13 <= radius; ++i13) {
	            for (int k13 = -radius; k13 <= radius; ++k13) {
	                if (i13 * i13 + k13 * k13 >= radius * radius) continue;
	                for (int j13 = 0; j13 <= 12; ++j13) {
	                    this.setAir(world, i13, j13, k13);
	                }
	            }
	        }
			return true;
		}
	}
}
