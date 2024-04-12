package got.common.world.structure.essos.ghiscar;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureGhiscarTownWall extends GOTStructureEssosTownWall {
	@SuppressWarnings("unused")
	public GOTStructureGhiscarTownWall(boolean flag) {
		super(flag);
		city = City.GHISCAR;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			city = City.GHISCAR;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			city = City.GHISCAR;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			city = City.GHISCAR;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			city = City.GHISCAR;
		}
	}
}