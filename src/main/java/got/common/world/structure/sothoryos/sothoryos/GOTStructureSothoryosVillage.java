package got.common.world.structure.sothoryos.sothoryos;

import java.util.Random;

import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;
import net.minecraft.world.World;

public class GOTStructureSothoryosVillage extends GOTVillageGen {
	public GOTStructureSothoryosVillage(GOTBiome biome, float f) {
		super(biome);
		gridScale = 12;
		gridRandomDisplace = 1;
		spawnChance = f;
		villageChunkRadius = 3;
		fixedVillageChunkRadius = 3;
	}

	@Override
	public GOTVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc);
	}

	public static class Instance extends GOTVillageGen.AbstractInstance {
		public Instance(GOTStructureSothoryosVillage village, World world, int i, int k, Random random, LocationInfo loc) {
			super(village, world, i, k, random, loc);
		}

		@Override
		public void addVillageStructures(Random random) {
			int smithyPos = random.nextInt(4);
			this.addStructure(new GOTStructureSothoryosChieftainPyramid(false), 0, -11, 0, true);
			this.addStructure(new GOTStructureSothoryosVillageTree(false), 0, -16, 2);
			this.addStructure(new GOTStructureSothoryosVillageFarm(false), -16, -19, 2);
			this.addStructure(new GOTStructureSothoryosVillageFarm(false), 16, -19, 2);
			this.addStructure(new GOTStructureSothoryosHouseStilts(false), 0, 15, 0);
			this.addStructure(new GOTStructureSothoryosVillageFarm(false), -16, 19, 0);
			this.addStructure(new GOTStructureSothoryosVillageFarm(false), 16, 19, 0);
			this.addStructure(new GOTStructureSothoryosHouseLarge(false), -20, 0, 1);
			this.addStructure(new GOTStructureSothoryosHouseLarge(false), 20, 1, 3);
			this.addStructure(new GOTStructureSothoryosHouseSimple(false), -15, -36, 0);
			this.addStructure(new GOTStructureSothoryosHouseSimple(false), 15, -36, 0);
			if (smithyPos == 0) {
				this.addStructure(new GOTStructureSothoryosSmithy(false), -22, -13, 1);
			} else {
				this.addStructure(new GOTStructureSothoryosHouseSimple(false), -32, -22, 3);
				this.addStructure(new GOTStructureSothoryosHouseSimple(false), -32, -12, 3);
			}
			if (smithyPos == 1) {
				this.addStructure(new GOTStructureSothoryosSmithy(false), -22, 14, 1);
			} else {
				this.addStructure(new GOTStructureSothoryosHouseSimple(false), -32, 13, 3);
				this.addStructure(new GOTStructureSothoryosHouseSimple(false), -32, 23, 3);
			}
			if (smithyPos == 2) {
				this.addStructure(new GOTStructureSothoryosSmithy(false), 22, -13, 3);
			} else {
				this.addStructure(new GOTStructureSothoryosHouseSimple(false), 32, -22, 1);
				this.addStructure(new GOTStructureSothoryosHouseSimple(false), 32, -12, 1);
			}
			if (smithyPos == 3) {
				this.addStructure(new GOTStructureSothoryosSmithy(false), 22, 14, 3);
			} else {
				this.addStructure(new GOTStructureSothoryosHouseSimple(false), 32, 13, 1);
				this.addStructure(new GOTStructureSothoryosHouseSimple(false), 32, 23, 1);
			}
			this.addStructure(new GOTStructureSothoryosHouseSimple(false), -15, 36, 2);
			this.addStructure(new GOTStructureSothoryosHouseSimple(false), 0, 37, 2);
			this.addStructure(new GOTStructureSothoryosHouseSimple(false), 15, 36, 2);
			this.addStructure(new GOTStructureSothoryosWatchtower(false), -26, -36, 0);
			this.addStructure(new GOTStructureSothoryosWatchtower(false), 26, -36, 0);
			this.addStructure(new GOTStructureSothoryosWatchtower(false), -26, 37, 2);
			this.addStructure(new GOTStructureSothoryosWatchtower(false), 26, 37, 2);
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
