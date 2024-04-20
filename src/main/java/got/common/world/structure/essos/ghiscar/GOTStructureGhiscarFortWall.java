package got.common.world.structure.essos.ghiscar;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureGhiscarFortWall extends GOTStructureEssosFortWall {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTStructureGhiscarFortWall(boolean flag) {
		super(flag);
		city = City.GHISCAR;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			city = City.GHISCAR;
		}
	}
}