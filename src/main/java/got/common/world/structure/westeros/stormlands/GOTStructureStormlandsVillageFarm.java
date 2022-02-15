package got.common.world.structure.westeros.stormlands;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureStormlandsVillageFarm extends GOTStructureWesterosVillageFarm {
	public GOTStructureStormlandsVillageFarm(boolean flag) {
		super(flag);
		isStormlands = true;
	}

	public static class Crops extends GOTStructureWesterosVillageFarm.Crops {
		public Crops(boolean flag) {
			super(flag);
			isStormlands = true;	
		}
	}
}