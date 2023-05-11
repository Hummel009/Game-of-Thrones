package got.common.world.structure.other;

import got.common.world.map.GOTWaypoint;

public class LocationInfo {
	public static LocationInfo RANDOM_GEN_HERE = new LocationInfo(0, 0, 0, "RANDOM_GEN");
	public static LocationInfo SPAWNED_BY_PLAYER = new LocationInfo(0, 0, 0, "PLAYER_SPAWNED");
	public static LocationInfo NONE_HERE = new LocationInfo(0, 0, 0, "NONE") {

		@Override
		public boolean isPresent() {
			return false;
		}
	};
	public int posX;
	public int posZ;
	public int rotation;
	public String name;
	public boolean isFixedLocation;
	public GOTWaypoint associatedWaypoint;

	public LocationInfo(int x, int z, int r, String s) {
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

	public LocationInfo setFixedLocation(GOTWaypoint wp) {
		isFixedLocation = true;
		associatedWaypoint = wp;
		return this;
	}

	public boolean isPresent() {
		return true;
	}

}
