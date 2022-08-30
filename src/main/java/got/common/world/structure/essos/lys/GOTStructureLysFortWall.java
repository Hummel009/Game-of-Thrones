package got.common.world.structure.essos.lys;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureLysFortWall extends GOTStructureEssosFortWall {
	public GOTStructureLysFortWall(boolean flag) {
		super(flag);
		isLys = true;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			isLys = true;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			isLys = true;
		}
	}
}