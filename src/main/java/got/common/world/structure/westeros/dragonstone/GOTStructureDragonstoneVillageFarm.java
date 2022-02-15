package got.common.world.structure.westeros.dragonstone;

import got.common.world.structure.westeros.common.GOTStructureWesterosVillageFarm;

public abstract class GOTStructureDragonstoneVillageFarm extends GOTStructureWesterosVillageFarm {
	public GOTStructureDragonstoneVillageFarm(boolean flag) {
		super(flag);
		isDragonstone = true;
	}
}