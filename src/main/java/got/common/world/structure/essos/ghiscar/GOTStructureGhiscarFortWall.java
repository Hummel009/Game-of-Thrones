package got.common.world.structure.essos.ghiscar;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureGhiscarFortWall extends GOTStructureEssosFortWall {
	public GOTStructureGhiscarFortWall(boolean flag) {
		super(flag);
		type = Type.GHISCAR;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			type = Type.GHISCAR;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			type = Type.GHISCAR;
		}
	}
}