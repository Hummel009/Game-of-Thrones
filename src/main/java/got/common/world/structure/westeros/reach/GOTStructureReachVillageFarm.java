package got.common.world.structure.westeros.reach;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public class GOTStructureReachVillageFarm extends GOTStructureWesterosVillageFarm {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTStructureReachVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.REACH;
	}

	public static class Animals extends GOTStructureWesterosVillageFarm.Animals {
		public Animals(boolean flag) {
			super(flag);
			kingdom = Kingdom.REACH;
		}
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.REACH;
		}
	}

	public static class Tree extends GOTStructureWesterosVillageFarm.Tree {
		public Tree(boolean flag) {
			super(flag);
			kingdom = Kingdom.REACH;
		}
	}
}