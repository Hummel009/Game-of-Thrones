package got.common.world.structure.essos.volantis;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureVolantisTownWall extends GOTStructureEssosTownWall {
	public GOTStructureVolantisTownWall(boolean flag) {
		super(flag);
		isVolantis = true;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			isVolantis = true;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			isVolantis = true;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			isVolantis = true;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			isVolantis = true;
		}
	}
}