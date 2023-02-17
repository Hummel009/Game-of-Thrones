package got.common.world.structure.essos.lorath;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureLorathFortWall extends GOTStructureEssosFortWall {
	public GOTStructureLorathFortWall(boolean flag) {
		super(flag);
		cityType = CityType.LORATH;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			cityType = CityType.LORATH;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			cityType = CityType.LORATH;
		}
	}
}