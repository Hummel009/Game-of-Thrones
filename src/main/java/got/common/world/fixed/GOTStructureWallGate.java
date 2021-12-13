package got.common.world.fixed;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornBase;
import net.minecraft.world.World;

public class GOTStructureWallGate extends GOTVillageGen {
	public GOTStructureWallGate(GOTBiome biome, float f) {
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

	public static class Gate extends GOTStructureIronbornBase {
		public Gate(boolean flag) {
			super(flag);
		}

		@Override
		public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
			this.setOriginAndRotation(world, i, j, k, rotation, 0, 0);
			originY += 2;
			// Vozduh
			for (int x = -3; x <= 3; ++x) {
				for (int y = -3; y <= 3; ++y) {
					for (int z = 0; z <= 6; ++z) {
						setAir(world, x, z, y);
					}
				}
			}
			// Vorota
			for (int x = -3; x <= 3; ++x) {
				for (int z = 0; z <= 6; ++z) {
					setBlockAndMetadata(world, x, z, 3, GOTRegistry.gateIronBars, 2);
					setBlockAndMetadata(world, x, z, -3, GOTRegistry.gateWooden, 2);
				}
			}
			// Balki stojachije
			for (int z = 0; z <= 6; ++z) {
				setBlockAndMetadata(world, -4, z, 3, GOTRegistry.woodBeamV1, 1);
				setBlockAndMetadata(world, 4, z, 3, GOTRegistry.woodBeamV1, 1);
				setBlockAndMetadata(world, -4, z, -3, GOTRegistry.woodBeamV1, 1);
				setBlockAndMetadata(world, 4, z, -3, GOTRegistry.woodBeamV1, 1);
			}
			// Balki lezhachije
			for (int x = -3; x <= 3; ++x) {
				setBlockAndMetadata(world, x, 7, 3, GOTRegistry.woodBeamV1, 1 | 4);
				setBlockAndMetadata(world, x, 7, -3, GOTRegistry.woodBeamV1, 1 | 4);
			}
			return true;
		}
	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureWallGate village, World world, int i, int k, Random random, GOTLocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new Gate(false), 0, 0, 0, true);
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
