package got.common.world.structure.westeros.crownlands;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureCrownlandsVillageFarm extends GOTStructureWesterosVillageFarm {
	public GOTStructureCrownlandsVillageFarm(boolean flag) {
		super(flag);
		isCrownlands = true;
	}
}