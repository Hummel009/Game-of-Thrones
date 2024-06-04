package got.common.world.structure.westeros.riverlands;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public class GOTStructureRiverlandsVillageFarm extends GOTStructureWesterosVillageFarm {
	@SuppressWarnings("unused")
	public GOTStructureRiverlandsVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.RIVERLANDS;
	}

	public static class Animals extends GOTStructureWesterosVillageFarm.Animals {
		public Animals(boolean flag) {
			super(flag);
			kingdom = Kingdom.RIVERLANDS;
		}
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.RIVERLANDS;
		}
	}

	public static class Tree extends GOTStructureWesterosVillageFarm.Tree {
		public Tree(boolean flag) {
			super(flag);
			kingdom = Kingdom.RIVERLANDS;
		}
	}
}