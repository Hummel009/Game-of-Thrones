package got.common.world.structure.westeros.storage;

import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.westeros.basis.*;

public abstract class GOTStructureIronborn extends GOTStructureBase {
	public static class GOTStructureIronbornBarn extends GOTStructureWesterosBarn {
		public GOTStructureIronbornBarn(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}
	
	public abstract static class GOTStructureIronbornVillageFarm extends GOTStructureWesterosVillageFarm {
		public GOTStructureIronbornVillageFarm(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}
	
	public static class GOTStructureIronbornVillageFarmCrops extends GOTStructureWesterosVillageFarm.Crops {
		public GOTStructureIronbornVillageFarmCrops(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornBath extends GOTStructureWesterosBath {
		public GOTStructureIronbornBath(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornCottage extends GOTStructureWesterosCottage {
		public GOTStructureIronbornCottage(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornFortress extends GOTStructureWesterosFortress {
		public GOTStructureIronbornFortress(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornGatehouse extends GOTStructureWesterosGatehouse {
		public GOTStructureIronbornGatehouse(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornHouse extends GOTStructureWesterosHouse {
		public GOTStructureIronbornHouse(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornSmithy extends GOTStructureWesterosSmithy {
		public GOTStructureIronbornSmithy(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornStables extends GOTStructureWesterosStables {
		public GOTStructureIronbornStables(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornStoneHouse extends GOTStructureWesterosStoneHouse {
		public GOTStructureIronbornStoneHouse(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornTavern extends GOTStructureWesterosTavern {
		public GOTStructureIronbornTavern(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornTower extends GOTStructureWesterosTower {
		public GOTStructureIronbornTower(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornWatchfort extends GOTStructureWesterosWatchfort {
		public GOTStructureIronbornWatchfort(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}

	public static class GOTStructureIronbornWatchtower extends GOTStructureWesterosWatchtower {
		public GOTStructureIronbornWatchtower(boolean flag) {
			super(flag);
			isIronborn = true;
		}
	}
}