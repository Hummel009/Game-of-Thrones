package got.common.world.structure.essos.tyrosh;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureTyroshTownWall extends GOTStructureEssosTownWall {
	@SuppressWarnings("unused")
	public GOTStructureTyroshTownWall(boolean flag) {
		super(flag);
		city = City.TYROSH;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			city = City.TYROSH;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			city = City.TYROSH;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			city = City.TYROSH;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			city = City.TYROSH;
		}
	}
}