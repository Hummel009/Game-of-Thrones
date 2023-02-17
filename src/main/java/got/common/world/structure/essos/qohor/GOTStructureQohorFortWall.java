package got.common.world.structure.essos.qohor;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureQohorFortWall extends GOTStructureEssosFortWall {
	public GOTStructureQohorFortWall(boolean flag) {
		super(flag);
		cityType = CityType.QOHOR;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			cityType = CityType.QOHOR;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			cityType = CityType.QOHOR;
		}
	}
}