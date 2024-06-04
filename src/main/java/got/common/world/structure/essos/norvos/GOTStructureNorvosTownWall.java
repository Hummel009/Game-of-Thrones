package got.common.world.structure.essos.norvos;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureNorvosTownWall extends GOTStructureEssosTownWall {
	@SuppressWarnings("unused")
	public GOTStructureNorvosTownWall(boolean flag) {
		super(flag);
		city = City.NORVOS;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			city = City.NORVOS;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			city = City.NORVOS;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			city = City.NORVOS;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			city = City.NORVOS;
		}
	}
}