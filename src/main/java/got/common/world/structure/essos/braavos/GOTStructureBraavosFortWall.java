package got.common.world.structure.essos.braavos;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureBraavosFortWall extends GOTStructureEssosFortWall {
	public GOTStructureBraavosFortWall(boolean flag) {
		super(flag);
		type = Type.BRAAVOS;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			type = Type.BRAAVOS;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			type = Type.BRAAVOS;
		}
	}
}