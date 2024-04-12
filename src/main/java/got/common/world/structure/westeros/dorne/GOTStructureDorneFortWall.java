package got.common.world.structure.westeros.dorne;

import got.common.world.structure.westeros.common.GOTStructureWesterosFortWall;

@SuppressWarnings("AbstractClassNeverImplemented")
public abstract class GOTStructureDorneFortWall extends GOTStructureWesterosFortWall {
	@SuppressWarnings("unused")
	protected GOTStructureDorneFortWall(boolean flag) {
		super(flag);
		kingdom = Kingdom.DORNE;
	}

	public static class Left extends GOTStructureWesterosFortWall.Left {
		public Left(boolean flag) {
			super(flag);
			kingdom = Kingdom.DORNE;
		}
	}

	public static class Right extends GOTStructureWesterosFortWall.Right {
		public Right(boolean flag) {
			super(flag);
			kingdom = Kingdom.DORNE;
		}
	}
}