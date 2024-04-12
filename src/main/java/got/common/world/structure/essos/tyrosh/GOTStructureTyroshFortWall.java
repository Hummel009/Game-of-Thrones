package got.common.world.structure.essos.tyrosh;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureTyroshFortWall extends GOTStructureEssosFortWall {
	@SuppressWarnings("unused")
	public GOTStructureTyroshFortWall(boolean flag) {
		super(flag);
		city = City.TYROSH;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			city = City.TYROSH;
		}
	}
}