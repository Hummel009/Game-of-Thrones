package got.common.world.structure.essos.qarth;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureQarthFortWall extends GOTStructureEssosFortWall {
	public GOTStructureQarthFortWall(boolean flag) {
		super(flag);
		cityType = CityType.QARTH;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			cityType = CityType.QARTH;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			cityType = CityType.QARTH;
		}
	}
}