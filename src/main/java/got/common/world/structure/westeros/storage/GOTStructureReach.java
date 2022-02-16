package got.common.world.structure.westeros.storage;

import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.basis.*;

public abstract class GOTStructureReach extends GOTStructureBase {
	public static class GOTStructureReachBarn extends GOTStructureWesterosBarn {
		public GOTStructureReachBarn(boolean flag) {
			super(flag);
			isReach = true;
		}
	}
	
	public abstract static class GOTStructureReachVillageFarm extends GOTStructureWesterosVillageFarm {
		public GOTStructureReachVillageFarm(boolean flag) {
			super(flag);
			isReach = true;
		}
	}
	
	public static class GOTStructureReachVillageFarmCrops extends GOTStructureWesterosVillageFarm.Crops {
		public GOTStructureReachVillageFarmCrops(boolean flag) {
			super(flag);
			isReach = true;
		}
	}
	
	public static class GOTStructureReachBrewery extends GOTStructureWesterosBrewery {
		public GOTStructureReachBrewery(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachBath extends GOTStructureWesterosBath {
		public GOTStructureReachBath(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachCottage extends GOTStructureWesterosCottage {
		public GOTStructureReachCottage(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachFortress extends GOTStructureWesterosFortress {
		public GOTStructureReachFortress(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachGatehouse extends GOTStructureWesterosGatehouse {
		public GOTStructureReachGatehouse(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachHouse extends GOTStructureWesterosHouse {
		public GOTStructureReachHouse(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachSmithy extends GOTStructureWesterosSmithy {
		public GOTStructureReachSmithy(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachStables extends GOTStructureWesterosStables {
		public GOTStructureReachStables(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachStoneHouse extends GOTStructureWesterosStoneHouse {
		public GOTStructureReachStoneHouse(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachTavern extends GOTStructureWesterosTavern {
		public GOTStructureReachTavern(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachTower extends GOTStructureWesterosTower {
		public GOTStructureReachTower(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachWatchfort extends GOTStructureWesterosWatchfort {
		public GOTStructureReachWatchfort(boolean flag) {
			super(flag);
			isReach = true;
		}
	}

	public static class GOTStructureReachWatchtower extends GOTStructureWesterosWatchtower {
		public GOTStructureReachWatchtower(boolean flag) {
			super(flag);
			isReach = true;
		}
	}
}