package got.common.world.structure.essos.myr;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureMyrTownWall extends GOTStructureEssosTownWall {
	public GOTStructureMyrTownWall(boolean flag) {
		super(flag);
		type = Type.MYR;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			type = Type.MYR;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			type = Type.MYR;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			type = Type.MYR;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			type = Type.MYR;
		}
	}
}