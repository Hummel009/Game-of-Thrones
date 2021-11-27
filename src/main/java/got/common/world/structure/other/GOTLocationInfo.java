package got.common.world.structure.other;

import got.common.world.map.GOTWaypoint;

public class GOTLocationInfo {
	public static GOTLocationInfo RANDOM_GEN_HERE = new GOTLocationInfo(0, 0, 0, "RANDOM_GEN");
	public static GOTLocationInfo SPAWNED_BY_PLAYER = new GOTLocationInfo(0, 0, 0, "PLAYER_SPAWNED");
	public static GOTLocationInfo NONE_HERE = new GOTLocationInfo(0, 0, 0, "NONE") {

		@Override
		public boolean isPresent() {
			return false;
		}
	};
	public int posX;
	public int posZ;
	public int rotation;
	public String name;
	public boolean isFixedLocation = false;
	public GOTWaypoint associatedWaypoint;

	public GOTLocationInfo(int x, int z, int r, String s) {
		posX = x;
		posZ = z;
		rotation = r;
		name = s;
	}

	public GOTWaypoint getAssociatedWaypoint() {
		return associatedWaypoint;
	}

	public boolean isFixedLocation() {
		return isFixedLocation;
	}

	public boolean isPresent() {
		return true;
	}

	public GOTLocationInfo setFixedLocation(GOTWaypoint wp) {
		isFixedLocation = true;
		associatedWaypoint = wp;
		return this;
	}

}
