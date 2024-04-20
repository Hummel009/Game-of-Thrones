package got.common.world.structure.essos.volantis;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureVolantisTownWall extends GOTStructureEssosTownWall {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTStructureVolantisTownWall(boolean flag) {
		super(flag);
		city = City.VOLANTIS;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			city = City.VOLANTIS;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			city = City.VOLANTIS;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			city = City.VOLANTIS;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			city = City.VOLANTIS;
		}
	}
}