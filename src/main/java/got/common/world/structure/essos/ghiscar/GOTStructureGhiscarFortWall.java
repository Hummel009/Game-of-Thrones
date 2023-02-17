package got.common.world.structure.essos.ghiscar;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureGhiscarFortWall extends GOTStructureEssosFortWall {
	public GOTStructureGhiscarFortWall(boolean flag) {
		super(flag);
		cityType = CityType.GHISCAR;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			cityType = CityType.GHISCAR;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			cityType = CityType.GHISCAR;
		}
	}
}