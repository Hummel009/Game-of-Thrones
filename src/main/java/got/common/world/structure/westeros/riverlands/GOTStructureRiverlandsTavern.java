package got.common.world.structure.westeros.riverlands;

import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.common.GOTStructureWesterosTavern;

public class GOTStructureRiverlandsTavern extends GOTStructureWesterosTavern {
	public GOTStructureRiverlandsTavern(boolean flag) {
		super(flag);
		isRiverlands = true;
	}

	public GOTStructureBase setIsCrossroads() {
		isCrossroads = true;
		return this;
	}
}