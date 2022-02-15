package got.common.world.structure.westeros.ironborn;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureIronbornVillageFarm extends GOTStructureWesterosVillageFarm {
	public GOTStructureIronbornVillageFarm(boolean flag) {
		super(flag);
		isIronborn = true;
	}
}