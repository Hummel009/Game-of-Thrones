package got.common.world.structure.westeros.storage;

import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.basis.*;

public abstract class GOTStructureStormlands extends GOTStructureBase {
	public static class GOTStructureStormlandsBarn extends GOTStructureWesterosBarn {
		public GOTStructureStormlandsBarn(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}
	
	public abstract static class GOTStructureStormlandsVillageFarm extends GOTStructureWesterosVillageFarm {
		public GOTStructureStormlandsVillageFarm(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}
	
	public static class GOTStructureStormlandsVillageFarmCrops extends GOTStructureWesterosVillageFarm.Crops {
		public GOTStructureStormlandsVillageFarmCrops(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsBath extends GOTStructureWesterosBath {
		public GOTStructureStormlandsBath(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsCottage extends GOTStructureWesterosCottage {
		public GOTStructureStormlandsCottage(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsFortress extends GOTStructureWesterosFortress {
		public GOTStructureStormlandsFortress(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsGatehouse extends GOTStructureWesterosGatehouse {
		public GOTStructureStormlandsGatehouse(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsHouse extends GOTStructureWesterosHouse {
		public GOTStructureStormlandsHouse(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsSmithy extends GOTStructureWesterosSmithy {
		public GOTStructureStormlandsSmithy(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsStables extends GOTStructureWesterosStables {
		public GOTStructureStormlandsStables(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsStoneHouse extends GOTStructureWesterosStoneHouse {
		public GOTStructureStormlandsStoneHouse(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsTavern extends GOTStructureWesterosTavern {
		public GOTStructureStormlandsTavern(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsTower extends GOTStructureWesterosTower {
		public GOTStructureStormlandsTower(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsWatchfort extends GOTStructureWesterosWatchfort {
		public GOTStructureStormlandsWatchfort(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}

	public static class GOTStructureStormlandsWatchtower extends GOTStructureWesterosWatchtower {
		public GOTStructureStormlandsWatchtower(boolean flag) {
			super(flag);
			isStormlands = true;
		}
	}
}