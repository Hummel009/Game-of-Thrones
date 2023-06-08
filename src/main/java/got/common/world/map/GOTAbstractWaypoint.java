package got.common.world.map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface GOTAbstractWaypoint {
	String getCodeName();

	String getDisplayName();

	int getID();

	GOTWaypoint getItself();

	WaypointLockState getLockState(EntityPlayer var1);

	String getLoreText(EntityPlayer var1);

	int getRotation();

	double getX();

	int getXCoord();

	double getY();

	int getYCoord(World var1, int var2, int var3);

	int getYCoordSaved();

	int getZCoord();

	boolean hasPlayerUnlocked(EntityPlayer var1);

	boolean isHidden();

	enum WaypointLockState {
		STANDARD_LOCKED(0, 200), STANDARD_UNLOCKED(4, 200), STANDARD_UNLOCKED_CONQUEST(8, 200), CUSTOM_LOCKED(0, 204), CUSTOM_UNLOCKED(4, 204), SHARED_CUSTOM_LOCKED(0, 208), SHARED_CUSTOM_UNLOCKED(4, 208);

		public int iconU;
		public int iconV;

		WaypointLockState(int u, int v) {
			iconU = u;
			iconV = v;
		}
	}

}
