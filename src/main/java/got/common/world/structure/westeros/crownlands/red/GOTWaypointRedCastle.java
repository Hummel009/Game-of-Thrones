package got.common.world.structure.westeros.crownlands.red;

import java.util.Random;

import got.common.world.biome.GOTBiome;
import got.common.world.fixed.GOTStructureRedCastle;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.world.World;

public class GOTWaypointRedCastle extends GOTVillageGen {
	public GOTWaypointRedCastle(GOTBiome biome, float f) {
		super(biome);
		gridScale = 16;
		gridRandomDisplace = 2;
		spawnChance = f;
		villageChunkRadius = 6;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, GOTLocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public class Instance extends GOTVillageGen.AbstractInstance {
		public VillageType villageType;

		public Instance(GOTWaypointRedCastle village, World world, int i, int k, Random random, GOTLocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addStructure(GOTStructureBase structure, int x, int z, int r, boolean force) {
			super.addStructure(structure, x, z, r, force);
		}

		@Override
		public void addVillageStructures(Random random) {
			this.addStructure(new GOTStructureRedCastle(false), 0, 12, 2, true);
			this.addStructure(new GOTStructureRedFortGate(false), 0, -37, 0, true);
			this.addStructure(new GOTStructureRedFortWall.Right(false), -11, -37, 0, true);
			this.addStructure(new GOTStructureRedFortWall.Left(false), 11, -37, 0, true);
			this.addStructure(new GOTStructureRedWatchtower(false), -23, -33, 2, true);
			this.addStructure(new GOTStructureRedWatchtower(false), 23, -33, 2, true);
			this.addStructure(new GOTStructureRedFortGate(false), -37, 0, 3, true);
			this.addStructure(new GOTStructureRedFortWall.Left(false), -37, -11, 3, true);
			this.addStructure(new GOTStructureRedFortWall.Right(false), -37, 11, 3, true);
			this.addStructure(new GOTStructureRedWatchtower(false), -33, -23, 1, true);
			this.addStructure(new GOTStructureRedWatchtower(false), -33, 23, 1, true);
			this.addStructure(new GOTStructureRedFortGate(false), 0, 37, 2, true);
			this.addStructure(new GOTStructureRedFortWall.Left(false), -11, 37, 2, true);
			this.addStructure(new GOTStructureRedFortWall.Right(false), 11, 37, 2, true);
			this.addStructure(new GOTStructureRedWatchtower(false), -23, 33, 0, true);
			this.addStructure(new GOTStructureRedWatchtower(false), 23, 33, 0, true);
			this.addStructure(new GOTStructureRedFortGate(false), 37, 0, 1, true);
			this.addStructure(new GOTStructureRedFortWall.Right(false), 37, -11, 1, true);
			this.addStructure(new GOTStructureRedFortWall.Left(false), 37, 11, 1, true);
			this.addStructure(new GOTStructureRedWatchtower(false), 33, -23, 3, true);
			this.addStructure(new GOTStructureRedWatchtower(false), 33, 23, 3, true);
			this.addStructure(new GOTStructureRedFortWallCorner(false), -30, -30, 3);
			this.addStructure(new GOTStructureRedFortWallCorner(false), -30, 30, 2);
			this.addStructure(new GOTStructureRedFortWallCorner(false), 30, 30, 1);
			this.addStructure(new GOTStructureRedFortWallCorner(false), 30, -30, 2);
			this.addStructure(new GOTStructureRedStables(false), -24, 2, 0);
			this.addStructure(new GOTStructureRedStables(false), -24, -2, 2);
			this.addStructure(new GOTStructureRedSmithy(false), 24, 1, 0);
			this.addStructure(new GOTStructureRedSmithy(false), 24, -1, 2);
			this.addStructure(new GOTStructureRedStoneHouse(false), -3, -25, 1);
			this.addStructure(new GOTStructureRedStoneHouse(false), 3, -25, 3);
			this.addStructure(new GOTStructureRedVillageFarm.Crops(false), -18, -21, 1);
			this.addStructure(new GOTStructureRedVillageFarm.Crops(false), 18, -21, 3);
			this.addStructure(new GOTStructureRedWell(false), -12, 27, 1);
			this.addStructure(new GOTStructureRedWell(false), 12, 27, 3);
		}

		@Override
		public GOTBezierType getPath(Random random, int i, int k) {
			int i1 = Math.abs(i);
			int k1 = Math.abs(k);
			if (villageType == VillageType.FORT) {
				if (i1 <= 1 && (k >= 13 || k <= -12) && k1 <= 36) {
					return GOTBezierType.PAVING;
				}
				if (k1 <= 1 && i1 >= 12 && i1 <= 36) {
					return GOTBezierType.PAVING;
				}
				if (k >= 26 && k <= 28 && i1 <= 12) {
					return GOTBezierType.PAVING;
				}
			}
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
			villageType = VillageType.FORT;
		}
	}

	public enum VillageType {
		FORT;
	}
}