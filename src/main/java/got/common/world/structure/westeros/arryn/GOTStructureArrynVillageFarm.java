package got.common.world.structure.westeros.arryn;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public class GOTStructureArrynVillageFarm extends GOTStructureWesterosVillageFarm {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTStructureArrynVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.ARRYN;
	}

	public static class Animals extends GOTStructureWesterosVillageFarm.Animals {
		public Animals(boolean flag) {
			super(flag);
			kingdom = Kingdom.ARRYN;
		}
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.ARRYN;
		}
	}

	public static class Tree extends GOTStructureWesterosVillageFarm.Tree {
		public Tree(boolean flag) {
			super(flag);
			kingdom = Kingdom.ARRYN;
		}
	}
}