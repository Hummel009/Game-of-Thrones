package got.common.world.structure.westeros.crownlands;

import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.common.GOTStructureWesterosSmithy;

public class GOTStructureCrownlandsSmithy extends GOTStructureWesterosSmithy {
	public GOTStructureCrownlandsSmithy(boolean flag) {
		super(flag);
		isCrownlands = true;
	}

	public GOTStructureBase setIsTobhoMott() {
		isTobhoMott = true;
		return this;
	}
}
