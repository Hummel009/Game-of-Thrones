package got.common.world.structure.westeros.dorne;

import got.common.world.structure.westeros.common.GOTStructureWesterosTownWall;

public class GOTStructureDorneTownWall extends GOTStructureWesterosTownWall {
	public GOTStructureDorneTownWall(boolean flag, int x0, int x1) {
		super(flag, x0, x1);
		isDorne = true;
	}

	public GOTStructureDorneTownWall(boolean flag, int x0, int x1, int xi0, int xi1) {
		super(flag, x0, x1, xi0, xi1);
		isDorne = true;
	}
}