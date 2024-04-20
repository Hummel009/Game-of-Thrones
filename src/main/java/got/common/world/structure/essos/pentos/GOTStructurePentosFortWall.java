package got.common.world.structure.essos.pentos;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructurePentosFortWall extends GOTStructureEssosFortWall {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTStructurePentosFortWall(boolean flag) {
		super(flag);
		city = City.PENTOS;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			city = City.PENTOS;
		}
	}
}