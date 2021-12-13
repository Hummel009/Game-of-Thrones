package got.common.world.fixed;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.ironborn.GOTEntityIronbornSoldier;
import got.common.entity.westeros.legendary.warrior.GOTEntityVictarionGreyjoy;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornTent;
import net.minecraft.world.World;

public class GOTStructureVictarionLanding extends GOTVillageGen {
	public GOTStructureVictarionLanding(GOTBiome biome, float f) {
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

	public static class Camp extends GOTStructureCampBase {
		public Camp(boolean flag) {
			super(flag);
		}

		@Override
		public GOTStructureBase createTent(boolean flag, Random random) {
			return new GOTStructureIronbornTent(false);
		}

		@Override
		public boolean generateFarm() {
			return false;
		}

		@Override
		public GOTEntityNPC getCampCaptain(World world, Random random) {
			return new GOTEntityVictarionGreyjoy(world);
		}

		@Override
		public void placeNPCRespawner(World world, Random random, int i, int j, int k) {
			for (int l = 0; l < 20; ++l) {
				spawnNPCAndSetHome(new GOTEntityIronbornSoldier(world), world, 0, 1, 0, 16);
			}
		}

		@Override
		public void setupRandomBlocks(Random random) {
			super.setupRandomBlocks(random);
			tableBlock = GOTRegistry.commandTable;
		}
	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureVictarionLanding village, World world, int i, int k, Random random, GOTLocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new Camp(false), 0, 0, 0, true);
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