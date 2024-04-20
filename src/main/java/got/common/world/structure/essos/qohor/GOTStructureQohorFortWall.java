package got.common.world.structure.essos.qohor;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureQohorFortWall extends GOTStructureEssosFortWall {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTStructureQohorFortWall(boolean flag) {
		super(flag);
		city = City.QOHOR;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			city = City.QOHOR;
		}
	}
}