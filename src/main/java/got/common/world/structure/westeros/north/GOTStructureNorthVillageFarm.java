package got.common.world.structure.westeros.north;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureNorthVillageFarm extends GOTStructureWesterosVillageFarm {
	protected GOTStructureNorthVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.NORTH;
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.NORTH;
		}
	}
}