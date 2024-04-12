package got.common.world.structure.other;

import got.common.database.GOTBlocks;

public class GOTStructureRottenHouse extends GOTStructureRuinedHouse {
	public GOTStructureRottenHouse(boolean flag) {
		super(flag);
		woodBlock = GOTBlocks.rottenLog;
		woodMeta = 0;
		plankBlock = GOTBlocks.planksRotten;
		plankMeta = 0;
		fenceBlock = GOTBlocks.fenceRotten;
		fenceMeta = 0;
		stairBlock = GOTBlocks.stairsRotten;
	}
}