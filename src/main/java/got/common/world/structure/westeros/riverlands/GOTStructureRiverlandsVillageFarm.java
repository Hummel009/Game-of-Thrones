package got.common.world.structure.westeros.riverlands;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureRiverlandsVillageFarm extends GOTStructureWesterosVillageFarm {
	public GOTStructureRiverlandsVillageFarm(boolean flag) {
		super(flag);
		isRiverlands = true;
	}
}