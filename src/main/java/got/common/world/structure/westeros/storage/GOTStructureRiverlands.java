package got.common.world.structure.westeros.storage;

import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.basis.*;

public abstract class GOTStructureRiverlands extends GOTStructureBase {
	public static class GOTStructureRiverlandsBarn extends GOTStructureWesterosBarn {
		public GOTStructureRiverlandsBarn(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsBath extends GOTStructureWesterosBath {
		public GOTStructureRiverlandsBath(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}
	
	public abstract static class GOTStructureRiverlandsVillageFarm extends GOTStructureWesterosVillageFarm {
		public GOTStructureRiverlandsVillageFarm(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}
	
	public static class GOTStructureRiverlandsVillageFarmCrops extends GOTStructureWesterosVillageFarm.Crops {
		public GOTStructureRiverlandsVillageFarmCrops(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsCottage extends GOTStructureWesterosCottage {
		public GOTStructureRiverlandsCottage(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsFortress extends GOTStructureWesterosFortress {
		public GOTStructureRiverlandsFortress(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsGatehouse extends GOTStructureWesterosGatehouse {
		public GOTStructureRiverlandsGatehouse(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsHouse extends GOTStructureWesterosHouse {
		public GOTStructureRiverlandsHouse(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsSmithy extends GOTStructureWesterosSmithy {
		public GOTStructureRiverlandsSmithy(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsStables extends GOTStructureWesterosStables {
		public GOTStructureRiverlandsStables(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsStoneHouse extends GOTStructureWesterosStoneHouse {
		public GOTStructureRiverlandsStoneHouse(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsTavern extends GOTStructureWesterosTavern {
		public GOTStructureRiverlandsTavern(boolean flag) {
			super(flag);
			isRiverlands = true;
		}

		public GOTStructureBase setIsCrossroads() {
			isCrossroads = true;
			return this;
		}
	}

	public static class GOTStructureRiverlandsTower extends GOTStructureWesterosTower {
		public GOTStructureRiverlandsTower(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsWatchfort extends GOTStructureWesterosWatchfort {
		public GOTStructureRiverlandsWatchfort(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}

	public static class GOTStructureRiverlandsWatchtower extends GOTStructureWesterosWatchtower {
		public GOTStructureRiverlandsWatchtower(boolean flag) {
			super(flag);
			isRiverlands = true;
		}
	}
}