package got.common.world.structure.essos.volantis;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureVolantisFortWall extends GOTStructureEssosFortWall {
	public GOTStructureVolantisFortWall(boolean flag) {
		super(flag);
		city = City.VOLANTIS;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			city = City.VOLANTIS;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			city = City.VOLANTIS;
		}
	}
}