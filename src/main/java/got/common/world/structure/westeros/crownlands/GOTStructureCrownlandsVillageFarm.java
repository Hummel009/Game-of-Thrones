package got.common.world.structure.westeros.crownlands;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureCrownlandsVillageFarm extends GOTStructureWesterosVillageFarm {
	protected GOTStructureCrownlandsVillageFarm(boolean flag) {
		super(flag);
		kingdom = Kingdom.CROWNLANDS;
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			kingdom = Kingdom.CROWNLANDS;
		}
	}
}