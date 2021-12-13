package got.common.world.fixed;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.world.World;

public class GOTStructureFiveFortsTower extends GOTVillageGen {
	public GOTStructureFiveFortsTower(GOTBiome biome, float f) {
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
		public Instance(GOTStructureFiveFortsTower village, World world, int i, int k, Random random, GOTLocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new Tower(false), 0, 0, 0, true);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			return null;
		}

		@Override
		public boolean isFlat() {
			return true;
		}

		@Override
		public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
			return false;
		}

		@Override
		public void setupVillageProperties(Random random) {
		}
	}

	public static class Tower extends GOTStructureBase {
		public Tower(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			rotationMode = 2;
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setupRandomBlocks(random);
			loadStrScan("lighthouse");
			associateBlockMetaAlias("BRICK", GOTRegistry.brick2, 11);
			generateStrScan(world, random, 0, 0, 0);
			return true;
		}
	}
}
