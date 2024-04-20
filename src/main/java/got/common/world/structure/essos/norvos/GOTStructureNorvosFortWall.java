package got.common.world.structure.essos.norvos;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureNorvosFortWall extends GOTStructureEssosFortWall {
	@SuppressWarnings({"WeakerAccess", "unused"})
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
}