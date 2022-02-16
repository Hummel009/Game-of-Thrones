package got.common.world.structure.westeros.dorne;

import got.common.world.structure.westeros.common.GOTStructureWesterosTownWall;

public class GOTStructureDorneTownWall extends GOTStructureWesterosTownWall {
	public GOTStructureDorneTownWall(boolean flag, int x0, int x1, int xi0, int xi1) {
		super(flag, x0, x1, xi0, xi1);
		isDorne = true;
	}
	public GOTStructureDorneTownWall(boolean flag, int x0, int x1) {
		super(flag, x0, x1);
		isDorne = true;
	}

	public static GOTStructureDorneTownWall Centre(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -5, 5);
	}

	public static GOTStructureDorneTownWall Left(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -9, 6);
	}

	public static GOTStructureDorneTownWall LeftEnd(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -6, 6, -5, 6);
	}

	public static GOTStructureDorneTownWall LeftEndShort(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -5, 6);
	}

	public static GOTStructureDorneTownWall Right(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -6, 9);
	}

	public static GOTStructureDorneTownWall RightEnd(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -6, 6, -6, 5);
	}

	public static GOTStructureDorneTownWall RightEndShort(boolean flag) {
		return new GOTStructureDorneTownWall(flag, -6, 5);
	}
}