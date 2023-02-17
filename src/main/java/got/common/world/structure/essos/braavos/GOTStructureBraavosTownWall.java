package got.common.world.structure.essos.braavos;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureBraavosTownWall extends GOTStructureEssosTownWall {
	public GOTStructureBraavosTownWall(boolean flag) {
		super(flag);
		type = Type.BRAAVOS;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			type = Type.BRAAVOS;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			type = Type.BRAAVOS;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			type = Type.BRAAVOS;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			type = Type.BRAAVOS;
		}
	}
}