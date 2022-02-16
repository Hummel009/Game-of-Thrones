package got.common.world.structure.westeros.storage;

import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.basis.*;

public abstract class GOTStructureDragonstone extends GOTStructureBase {
	public static class GOTStructureDragonstoneBarn extends GOTStructureWesterosBarn {
		public GOTStructureDragonstoneBarn(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}
	
	public abstract static class GOTStructureDragonstoneVillageFarm extends GOTStructureWesterosVillageFarm {
		public GOTStructureDragonstoneVillageFarm(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}
	
	public static class GOTStructureDragonstoneVillageFarmCrops extends GOTStructureWesterosVillageFarm.Crops {
		public GOTStructureDragonstoneVillageFarmCrops(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneBath extends GOTStructureWesterosBath {
		public GOTStructureDragonstoneBath(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneCottage extends GOTStructureWesterosCottage {
		public GOTStructureDragonstoneCottage(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneFortress extends GOTStructureWesterosFortress {
		public GOTStructureDragonstoneFortress(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneGatehouse extends GOTStructureWesterosGatehouse {
		public GOTStructureDragonstoneGatehouse(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneHouse extends GOTStructureWesterosHouse {
		public GOTStructureDragonstoneHouse(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneSmithy extends GOTStructureWesterosSmithy {
		public GOTStructureDragonstoneSmithy(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneStables extends GOTStructureWesterosStables {
		public GOTStructureDragonstoneStables(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneStoneHouse extends GOTStructureWesterosStoneHouse {
		public GOTStructureDragonstoneStoneHouse(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneTavern extends GOTStructureWesterosTavern {
		public GOTStructureDragonstoneTavern(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneTower extends GOTStructureWesterosTower {
		public GOTStructureDragonstoneTower(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneWatchfort extends GOTStructureWesterosWatchfort {
		public GOTStructureDragonstoneWatchfort(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}

	public static class GOTStructureDragonstoneWatchtower extends GOTStructureWesterosWatchtower {
		public GOTStructureDragonstoneWatchtower(boolean flag) {
			super(flag);
			isDragonstone = true;
		}
	}
}