package got.common.world.structure.westeros.arryn;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureArrynVillageFarm extends GOTStructureWesterosVillageFarm {
	protected GOTStructureArrynVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.ARRYN;
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.ARRYN;
		}
	}
}