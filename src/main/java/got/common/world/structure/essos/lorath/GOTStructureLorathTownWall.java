package got.common.world.structure.essos.lorath;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureLorathTownWall extends GOTStructureEssosTownWall {
	public GOTStructureLorathTownWall(boolean flag) {
		super(flag);
		isLorath = true;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			isLorath = true;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLorath = true;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLorath = true;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			isLorath = true;
		}
	}
}