package got.common.world.fixed;

import java.util.Random;

import got.common.entity.westeros.legendary.warrior.GOTEntityNightKing;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import got.common.world.structure.other.*;
import net.minecraft.world.World;

public class GOTStructureNightKing extends GOTVillageGen {
	public GOTStructureNightKing(GOTBiome biome, float f) {
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

	public static class Altar extends GOTStructureBase {
		public Altar(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0);
			setupRandomBlocks(random);
			loadStrScan("night_king");
			spawnNPCAndSetHome(new GOTEntityNightKing(world), world, 0, 10, 0, 10000);
			generateStrScan(world, random, 0, 0, 0);
			return true;
		}

		public static boolean fixedAt(World world, int i, int k) {
			return GOTFixedStructures.fixedAtMapImageCoords(i, k, 613, 314);
		}
	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureNightKing village, World world, int i, int k, Random random, GOTLocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new Altar(false), 0, 0, 0, true);
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
