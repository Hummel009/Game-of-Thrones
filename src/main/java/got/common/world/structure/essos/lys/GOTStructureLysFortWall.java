package got.common.world.structure.essos.lys;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureLysFortWall extends GOTStructureEssosFortWall {
	@SuppressWarnings("unused")
	public GOTStructureLysFortWall(boolean flag) {
		super(flag);
		city = City.LYS;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			city = City.LYS;
		}
	}
}