package got.common.world.structure.westeros.westerlands;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureWesterlandsVillageFarm extends GOTStructureWesterosVillageFarm {
	protected GOTStructureWesterlandsVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.WESTERLANDS;
	}

	public static class Animals extends GOTStructureWesterosVillageFarm.Animals {
		public Animals(boolean flag) {
			super(flag);
			kingdom = Kingdom.WESTERLANDS;
		}
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.WESTERLANDS;
		}
	}

	public static class Tree extends GOTStructureWesterosVillageFarm.Tree {
		public Tree(boolean flag) {
			super(flag);
			kingdom = Kingdom.WESTERLANDS;
		}
	}
}