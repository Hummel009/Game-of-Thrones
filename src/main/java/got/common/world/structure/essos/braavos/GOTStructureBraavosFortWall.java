package got.common.world.structure.essos.braavos;

import got.common.world.structure.essos.common.GOTStructureEssosFortWall;

public class GOTStructureBraavosFortWall extends GOTStructureEssosFortWall {
	public GOTStructureBraavosFortWall(boolean flag) {
		super(flag);
		isBraavos = true;
	}

	public static class Long extends GOTStructureEssosFortWall.Long {
		public Long(boolean flag) {
			super(flag);
			isLong = true;
			isBraavos = true;
		}
	}

	public static class Short extends GOTStructureEssosFortWall.Short {
		public Short(boolean flag) {
			super(flag);
			isLong = false;
			isBraavos = true;
		}
	}
}