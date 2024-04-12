package got.common.world.structure.westeros.reach;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

@SuppressWarnings("AbstractClassNeverImplemented")
public abstract class GOTStructureReachVillageFarm extends GOTStructureWesterosVillageFarm {
	@SuppressWarnings("unused")
	protected GOTStructureReachVillageFarm(boolean flag) {
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