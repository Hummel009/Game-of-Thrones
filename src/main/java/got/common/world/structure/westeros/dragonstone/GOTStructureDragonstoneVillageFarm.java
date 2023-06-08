package got.common.world.structure.westeros.dragonstone;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureDragonstoneVillageFarm extends GOTStructureWesterosVillageFarm {
	protected GOTStructureDragonstoneVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.DRAGONSTONE;
	}

	public static class Animals extends GOTStructureWesterosVillageFarm.Animals {
		public Animals(boolean flag) {
			super(flag);
			kingdom = Kingdom.DRAGONSTONE;
		}
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.DRAGONSTONE;
		}
	}

	public static class Tree extends GOTStructureWesterosVillageFarm.Tree {
		public Tree(boolean flag) {
			super(flag);
			kingdom = Kingdom.DRAGONSTONE;
		}
	}
}