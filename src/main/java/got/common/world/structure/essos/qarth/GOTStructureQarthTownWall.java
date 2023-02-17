package got.common.world.structure.essos.qarth;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureQarthTownWall extends GOTStructureEssosTownWall {
	public GOTStructureQarthTownWall(boolean flag) {
		super(flag);
		cityType = CityType.QARTH;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			cityType = CityType.QARTH;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			cityType = CityType.QARTH;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			cityType = CityType.QARTH;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			cityType = CityType.QARTH;
		}
	}
}