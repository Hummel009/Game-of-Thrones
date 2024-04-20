package got.common.world.structure.essos.qarth;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureQarthFortWall extends GOTStructureEssosFortWall {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTStructureQarthFortWall(boolean flag) {
		super(flag);
		city = City.QARTH;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			city = City.QARTH;
		}
	}
}