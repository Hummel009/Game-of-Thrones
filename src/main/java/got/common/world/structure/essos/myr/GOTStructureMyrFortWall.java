package got.common.world.structure.essos.myr;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureMyrFortWall extends GOTStructureEssosFortWall {
	public GOTStructureMyrFortWall(boolean flag) {
		super(flag);
		city = City.MYR;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			city = City.MYR;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			city = City.MYR;
		}
	}
}