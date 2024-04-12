package got.common.world.structure.other;

public class LocationInfo {
	public static final LocationInfo RANDOM_GEN_HERE = new LocationInfo(0, 0, 0);
	public static final LocationInfo SPAWNED_BY_PLAYER = new LocationInfo(0, 0, 0);
	public static final LocationInfo NONE_HERE = new LocationInfo(0, 0, 0) {
		@Override
		public boolean isPresent() {
			return false;
		}
	};

	private final int posX;
	private final int posZ;
	private final int rotation;

	private boolean isFixedLocation;

	public LocationInfo(int x, int z, int r) {
		posX = x;
		posZ = z;
		rotation = r;
	}

	public boolean isFixedLocation() {
		return isFixedLocation;
	}

	public LocationInfo setFixedLocation() {
		isFixedLocation = true;
		return this;
	}

	public boolean isPresent() {
		return true;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosZ() {
		return posZ;
	}

	public int getRotation() {
		return rotation;
	}
}