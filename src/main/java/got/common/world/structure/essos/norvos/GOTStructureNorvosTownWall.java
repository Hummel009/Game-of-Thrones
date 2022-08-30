package got.common.world.structure.essos.norvos;

import got.common.world.structure.essos.common.GOTStructureEssosTownWall;

public class GOTStructureNorvosTownWall extends GOTStructureEssosTownWall {
	public GOTStructureNorvosTownWall(boolean flag) {
		super(flag);
		isNorvos = true;
	}

	public static class Extra extends GOTStructureEssosTownWall.Extra {
		public Extra(boolean flag) {
			super(flag);
			isNorvos = true;
		}
	}

	public static class Long extends GOTStructureEssosTownWall.Long {
		public Long(boolean flag) {
			super(flag);
			isNorvos = true;
		}
	}

	public static class Short extends GOTStructureEssosTownWall.Short {
		public Short(boolean flag) {
			super(flag);
			isNorvos = true;
		}
	}

	public static class SideMid extends GOTStructureEssosTownWall.SideMid {
		public SideMid(boolean flag) {
			super(flag);
			isNorvos = true;
		}
	}
}