package got.common.world.structure.westeros.dorne;

import got.common.world.structure.westeros.common.GOTStructureWesterosFortWall;

public abstract class GOTStructureDorneFortWall extends GOTStructureWesterosFortWall {
	public GOTStructureDorneFortWall(boolean flag) {
		super(flag);
		isDorne = true;
	}

	public static class Left extends GOTStructureWesterosFortWall.Left {
		public Left(boolean flag) {
			super(flag);
			isDorne = true;
		}
	}

	public static class Right extends GOTStructureWesterosFortWall.Right {
		public Right(boolean flag) {
			super(flag);
			isDorne = true;
		}
	}
}