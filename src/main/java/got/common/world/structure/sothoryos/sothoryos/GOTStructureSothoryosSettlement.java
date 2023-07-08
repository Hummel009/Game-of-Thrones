package got.common.world.structure.sothoryos.sothoryos;

import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSothoryosSettlement extends GOTStructureBaseSettlement {
	public Type type;
	public boolean forcedType;

	public GOTStructureSothoryosSettlement(GOTBiome biome, float f) {
		super(biome);
		spawnChance = f;
		settlementChunkRadius = 3;
		fixedSettlementChunkRadius = 3;
	}

	@Override
	public GOTStructureBaseSettlement.AbstractInstance<GOTStructureSothoryosSettlement> createSettlementInstance(World world, int i, int k, Random random, LocationInfo loc) {
		return new Instance(this, world, i, k, random, loc, type, forcedType);
	}

	public GOTStructureBaseSettlement type(Type t, int radius) {
		type = t;
		settlementChunkRadius = radius;
		fixedSettlementChunkRadius = radius;
		forcedType = true;
		return this;
	}

	public enum Type {
		VILLAGE, PYRAMID
	}

	public class Instance extends GOTStructureBaseSettlement.AbstractInstance<GOTStructureSothoryosSettlement> {
		public Type type;
		public boolean forcedType;

		public Instance(GOTStructureSothoryosSettlement settlement, World world, int i, int k, Random random, LocationInfo loc, Type t, boolean b) {
			super(settlement, world, i, k, random, loc);
			type = t;
			forcedType = b;
		}

		@Override
		public void addSettlementStructures(Random random) {
			switch (type) {
				case PYRAMID:
					addStructure(new GOTStructureSothoryosPyramid(false), 0, 0, 0, true);
					break;
				case VILLAGE:
					setupVillage(random);
					break;
			}
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
			if (!forcedType) {
				type = Type.VILLAGE;
			}
		}

		public void setupVillage(Random random) {
			int smithyPos = random.nextInt(4);
			addStructure(new GOTStructureSothoryosChieftainPyramid(false), 0, -11, 0, true);
			addStructure(new GOTStructureSothoryosVillageTree(false), 0, -16, 2);
			addStructure(new GOTStructureSothoryosVillageFarm(false), -16, -19, 2);
			addStructure(new GOTStructureSothoryosVillageFarm(false), 16, -19, 2);
			addStructure(new GOTStructureSothoryosHouseStilts(false), 0, 15, 0);
			addStructure(new GOTStructureSothoryosVillageFarm(false), -16, 19, 0);
			addStructure(new GOTStructureSothoryosVillageFarm(false), 16, 19, 0);
			addStructure(new GOTStructureSothoryosHouseLarge(false), -20, 0, 1);
			addStructure(new GOTStructureSothoryosHouseLarge(false), 20, 1, 3);
			addStructure(new GOTStructureSothoryosHouseSimple(false), -15, -36, 0);
			addStructure(new GOTStructureSothoryosHouseSimple(false), 15, -36, 0);
			if (smithyPos == 0) {
				addStructure(new GOTStructureSothoryosSmithy(false), -22, -13, 1);
			} else {
				addStructure(new GOTStructureSothoryosHouseSimple(false), -32, -22, 3);
				addStructure(new GOTStructureSothoryosHouseSimple(false), -32, -12, 3);
			}
			if (smithyPos == 1) {
				addStructure(new GOTStructureSothoryosSmithy(false), -22, 14, 1);
			} else {
				addStructure(new GOTStructureSothoryosHouseSimple(false), -32, 13, 3);
				addStructure(new GOTStructureSothoryosHouseSimple(false), -32, 23, 3);
			}
			if (smithyPos == 2) {
				addStructure(new GOTStructureSothoryosSmithy(false), 22, -13, 3);
			} else {
				addStructure(new GOTStructureSothoryosHouseSimple(false), 32, -22, 1);
				addStructure(new GOTStructureSothoryosHouseSimple(false), 32, -12, 1);
			}
			if (smithyPos == 3) {
				addStructure(new GOTStructureSothoryosSmithy(false), 22, 14, 3);
			} else {
				addStructure(new GOTStructureSothoryosHouseSimple(false), 32, 13, 1);
				addStructure(new GOTStructureSothoryosHouseSimple(false), 32, 23, 1);
			}
			addStructure(new GOTStructureSothoryosHouseSimple(false), -15, 36, 2);
			addStructure(new GOTStructureSothoryosHouseSimple(false), 0, 37, 2);
			addStructure(new GOTStructureSothoryosHouseSimple(false), 15, 36, 2);
			addStructure(new GOTStructureSothoryosWatchtower(false), -26, -36, 0);
			addStructure(new GOTStructureSothoryosWatchtower(false), 26, -36, 0);
			addStructure(new GOTStructureSothoryosWatchtower(false), -26, 37, 2);
			addStructure(new GOTStructureSothoryosWatchtower(false), 26, 37, 2);
		}
	}
}