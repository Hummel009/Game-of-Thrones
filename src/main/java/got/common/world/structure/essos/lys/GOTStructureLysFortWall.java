package got.common.world.structure.essos.lys;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureLysFortWall extends GOTStructureEssosFortWall {
	public GOTStructureLysFortWall(boolean flag) {
		super(flag);
		cityType = CityType.LYS;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			cityType = CityType.LYS;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			cityType = CityType.LYS;
		}
	}
}