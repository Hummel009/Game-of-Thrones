package got.common.world.structure.westeros.crownlands;

import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.common.GOTStructureWesterosSmithy;

public class GOTStructureCrownlandsSmithy extends GOTStructureWesterosSmithy {
	public GOTStructureCrownlandsSmithy(boolean flag) {
		super(flag);
		kingdom = Kingdom.CROWNLANDS;
	}

	public GOTStructureBase setIsKingsLanding() {
		issKingsLanding = true;
		return this;
	}
}
