package got.common.world.structure.westeros.ironborn;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public class GOTStructureIronbornVillageFarm extends GOTStructureWesterosVillageFarm {
	@SuppressWarnings("unused")
	public GOTStructureIronbornVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.IRONBORN;
	}

	public static class Animals extends GOTStructureWesterosVillageFarm.Animals {
		public Animals(boolean flag) {
			super(flag);
			kingdom = Kingdom.IRONBORN;
		}
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.IRONBORN;
		}
	}

	public static class Tree extends GOTStructureWesterosVillageFarm.Tree {
		public Tree(boolean flag) {
			super(flag);
			kingdom = Kingdom.IRONBORN;
		}
	}
}