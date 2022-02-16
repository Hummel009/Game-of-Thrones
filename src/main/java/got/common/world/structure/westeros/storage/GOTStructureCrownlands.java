package got.common.world.structure.westeros.storage;

import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.basis.*;

public abstract class GOTStructureCrownlands extends GOTStructureBase {
	public static class GOTStructureCrownlandsBarn extends GOTStructureWesterosBarn {
		public GOTStructureCrownlandsBarn(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsBath extends GOTStructureWesterosBath {
		public GOTStructureCrownlandsBath(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsCottage extends GOTStructureWesterosCottage {
		public GOTStructureCrownlandsCottage(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsFortress extends GOTStructureWesterosFortress {
		public GOTStructureCrownlandsFortress(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsGatehouse extends GOTStructureWesterosGatehouse {
		public GOTStructureCrownlandsGatehouse(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsHouse extends GOTStructureWesterosHouse {
		public GOTStructureCrownlandsHouse(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsSmithy extends GOTStructureWesterosSmithy {
		public GOTStructureCrownlandsSmithy(boolean flag) {
			super(flag);
			isCrownlands = true;
		}

		public GOTStructureBase setIsTobhoMott() {
			isTobhoMott = true;
			return this;
		}
	}

	public static class GOTStructureCrownlandsStables extends GOTStructureWesterosStables {
		public GOTStructureCrownlandsStables(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsStoneHouse extends GOTStructureWesterosStoneHouse {
		public GOTStructureCrownlandsStoneHouse(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsTavern extends GOTStructureWesterosTavern {
		public GOTStructureCrownlandsTavern(boolean flag) {
			super(flag);
			isCrownlands = true;
		}

		public GOTStructureBase setIsPetyrBaelish() {
			isPetyrBaelish = true;
			return this;
		}
	}

	public static class GOTStructureCrownlandsTower extends GOTStructureWesterosTower {
		public GOTStructureCrownlandsTower(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public abstract static class GOTStructureCrownlandsVillageFarm extends GOTStructureWesterosVillageFarm {
		public GOTStructureCrownlandsVillageFarm(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsVillageFarmCrops extends GOTStructureWesterosVillageFarm.Crops {
		public GOTStructureCrownlandsVillageFarmCrops(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsWatchfort extends GOTStructureWesterosWatchfort {
		public GOTStructureCrownlandsWatchfort(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}

	public static class GOTStructureCrownlandsWatchtower extends GOTStructureWesterosWatchtower {
		public GOTStructureCrownlandsWatchtower(boolean flag) {
			super(flag);
			isCrownlands = true;
		}
	}
}