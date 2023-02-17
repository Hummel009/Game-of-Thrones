package got.common.world.structure.essos.pentos;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructurePentosFortWall extends GOTStructureEssosFortWall {
	public GOTStructurePentosFortWall(boolean flag) {
		super(flag);
		cityType = CityType.PENTOS;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			cityType = CityType.PENTOS;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			cityType = CityType.PENTOS;
		}
	}
}