package got.common.world.structure.westeros.crownlands;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public class GOTStructureCrownlandsVillageFarm extends GOTStructureWesterosVillageFarm {
	@SuppressWarnings("unused")
	public GOTStructureCrownlandsVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.CROWNLANDS;
	}

	public static class Animals extends GOTStructureWesterosVillageFarm.Animals {
		public Animals(boolean flag) {
			super(flag);
			kingdom = Kingdom.CROWNLANDS;
		}
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.CROWNLANDS;
		}
	}

	public static class Tree extends GOTStructureWesterosVillageFarm.Tree {
		public Tree(boolean flag) {
			super(flag);
			kingdom = Kingdom.CROWNLANDS;
		}
	}
}