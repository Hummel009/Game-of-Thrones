package got.common.world.structure.essos.qohor;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureQohorTownWall extends GOTStructureEssosTownWall {
	public GOTStructureQohorTownWall(boolean flag) {
		super(flag);
		city = City.QOHOR;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			city = City.QOHOR;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			city = City.QOHOR;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			city = City.QOHOR;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			city = City.QOHOR;
		}
	}
}