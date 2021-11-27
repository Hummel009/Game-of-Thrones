package got.common.world.structure.other;

import got.common.database.GOTRegistry;

public class GOTStructureRottenHouse extends GOTStructureRuinedHouse {
	public GOTStructureRottenHouse(boolean flag) {
		super(flag);
		woodBlock = GOTRegistry.rottenLog;
		woodMeta = 0;
		plankBlock = GOTRegistry.planksRotten;
		plankMeta = 0;
		fenceBlock = GOTRegistry.fenceRotten;
		fenceMeta = 0;
		stairBlock = GOTRegistry.stairsRotten;
	}
}
