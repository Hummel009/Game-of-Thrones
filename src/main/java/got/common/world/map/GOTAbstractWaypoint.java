package got.common.world.map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface GOTAbstractWaypoint {
	String getCodeName();

	String getDisplayName();

	int getID();

	GOTWaypoint getInstance();

	WaypointLockState getLockState(EntityPlayer entityplayer);

	String getLoreText(EntityPlayer entityplayer);

	int getRotation();

	double getShiftX();

	double getShiftY();

	double getImgX();

	int getCoordX();

	double getImgY();

	int getYCoord(World world, int i, int k);

	int getYCoordSaved();

	int getCoordZ();

	boolean hasPlayerUnlocked(EntityPlayer entityplayer);

	boolean isHidden();

	enum WaypointLockState {
		STANDARD_LOCKED(0, 200), STANDARD_UNLOCKED(4, 200), STANDARD_UNLOCKED_CONQUEST(8, 200), CUSTOM_LOCKED(0, 204), CUSTOM_UNLOCKED(4, 204), SHARED_CUSTOM_LOCKED(0, 208), SHARED_CUSTOM_UNLOCKED(4, 208);

		private final int iconU;
		private final int iconV;

		WaypointLockState(int u, int v) {
			iconU = u;
			iconV = v;
		}

		public int getIconU() {
			return iconU;
		}

		public int getIconV() {
			return iconV;
		}
	}
}