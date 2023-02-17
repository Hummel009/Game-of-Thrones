package got.common.world.structure.essos.lys;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureLysTownWall extends GOTStructureEssosTownWall {
	public GOTStructureLysTownWall(boolean flag) {
		super(flag);
		cityType = CityType.LYS;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			cityType = CityType.LYS;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			cityType = CityType.LYS;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			cityType = CityType.LYS;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			cityType = CityType.LYS;
		}
	}
}