package got.common.world.structure.essos.norvos;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureNorvosFortWall extends GOTStructureEssosFortWall {
	public GOTStructureNorvosFortWall(boolean flag) {
		super(flag);
		city = City.NORVOS;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			city = City.NORVOS;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			city = City.NORVOS;
		}
	}
}