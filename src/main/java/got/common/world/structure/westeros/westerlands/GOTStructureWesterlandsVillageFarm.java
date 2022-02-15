package got.common.world.structure.westeros.westerlands;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureWesterlandsVillageFarm extends GOTStructureWesterosVillageFarm {
	public GOTStructureWesterlandsVillageFarm(boolean flag) {
		super(flag);
		isWesterlands = true;
	}
}