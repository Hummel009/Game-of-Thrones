package got.common.world.structure.westeros.reach;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureReachVillageFarm extends GOTStructureWesterosVillageFarm {
	public GOTStructureReachVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.REACH;
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.REACH;
		}
	}
}