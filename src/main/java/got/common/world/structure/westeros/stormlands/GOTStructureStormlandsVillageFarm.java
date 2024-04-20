package got.common.world.structure.westeros.stormlands;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public class GOTStructureStormlandsVillageFarm extends GOTStructureWesterosVillageFarm {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTStructureStormlandsVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.STORMLANDS;
	}

	public static class Animals extends GOTStructureWesterosVillageFarm.Animals {
		public Animals(boolean flag) {
			super(flag);
			kingdom = Kingdom.STORMLANDS;
		}
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.STORMLANDS;
		}
	}

	public static class Tree extends GOTStructureWesterosVillageFarm.Tree {
		public Tree(boolean flag) {
			super(flag);
			kingdom = Kingdom.STORMLANDS;
		}
	}
}