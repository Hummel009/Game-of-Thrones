package got.common.world.structure.westeros.storage;

import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.basis.*;

public abstract class GOTStructureNorth extends GOTStructureBase {
	public static class GOTStructureNorthBarn extends GOTStructureWesterosBarn {
		public GOTStructureNorthBarn(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}
	
	public abstract static class GOTStructureNorthVillageFarm extends GOTStructureWesterosVillageFarm {
		public GOTStructureNorthVillageFarm(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}
	
	public static class GOTStructureNorthVillageFarmCrops extends GOTStructureWesterosVillageFarm.Crops {
		public GOTStructureNorthVillageFarmCrops(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthBath extends GOTStructureWesterosBath {
		public GOTStructureNorthBath(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthCottage extends GOTStructureWesterosCottage {
		public GOTStructureNorthCottage(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthFortress extends GOTStructureWesterosFortress {
		public GOTStructureNorthFortress(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthGatehouse extends GOTStructureWesterosGatehouse {
		public GOTStructureNorthGatehouse(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthHouse extends GOTStructureWesterosHouse {
		public GOTStructureNorthHouse(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthSmithy extends GOTStructureWesterosSmithy {
		public GOTStructureNorthSmithy(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthStables extends GOTStructureWesterosStables {
		public GOTStructureNorthStables(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthStoneHouse extends GOTStructureWesterosStoneHouse {
		public GOTStructureNorthStoneHouse(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthTavern extends GOTStructureWesterosTavern {
		public GOTStructureNorthTavern(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthTower extends GOTStructureWesterosTower {
		public GOTStructureNorthTower(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthWatchfort extends GOTStructureWesterosWatchfort {
		public GOTStructureNorthWatchfort(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}

	public static class GOTStructureNorthWatchtower extends GOTStructureWesterosWatchtower {
		public GOTStructureNorthWatchtower(boolean flag) {
			super(flag);
			isNorth = true;
		}
	}
}