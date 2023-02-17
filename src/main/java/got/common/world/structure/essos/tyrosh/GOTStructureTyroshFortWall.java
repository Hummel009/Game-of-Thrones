package got.common.world.structure.essos.tyrosh;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureTyroshFortWall extends GOTStructureEssosFortWall {
	public GOTStructureTyroshFortWall(boolean flag) {
		super(flag);
		type = Type.TYROSH;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			type = Type.TYROSH;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			type = Type.TYROSH;
		}
	}
}