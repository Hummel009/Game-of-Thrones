package got.common.world.structure.essos.pentos;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructurePentosTownWall extends GOTStructureEssosTownWall {
	public GOTStructurePentosTownWall(boolean flag) {
		super(flag);
		city = City.PENTOS;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			city = City.PENTOS;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			city = City.PENTOS;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			city = City.PENTOS;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			city = City.PENTOS;
		}
	}
}