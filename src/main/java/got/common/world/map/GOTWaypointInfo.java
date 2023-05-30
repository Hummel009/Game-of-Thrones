package got.common.world.map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTWaypointInfo implements GOTAbstractWaypoint {
	public double shiftedX;
	public double shiftedY;
	public int rotation;
	public int xCoord;
	public int zCoord;
	public GOTWaypoint waypoint;

	public GOTWaypointInfo(GOTWaypoint waypoint, double shiftedX, double shiftedY, int rotation) {
		this.shiftedX = shiftedX;
		this.shiftedY = shiftedY;
		this.waypoint = waypoint;
		this.rotation = rotation;
		xCoord = GOTWaypoint.mapToWorldX(this.shiftedX);
		zCoord = GOTWaypoint.mapToWorldZ(this.shiftedY);
	}

	@Override
	public String getCodeName() {
		return null;
	}

	@Override
	public String getDisplayName() {
		return null;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public GOTWaypoint getItself() {
		return waypoint;
	}

	@Override
	public int getRotation() {
		return rotation;
	}

	@Override
	public WaypointLockState getLockState(EntityPlayer var1) {
		return null;
	}

	@Override
	public String getLoreText(EntityPlayer var1) {
		return null;
	}

	@Override
	public double getX() {
		return shiftedX;
	}

	@Override
	public int getXCoord() {
		return xCoord;
	}

	@Override
	public double getY() {
		return shiftedY;
	}

	@Override
	public int getYCoord(World var1, int var2, int var3) {
		return 0;
	}

	@Override
	public int getYCoordSaved() {
		return 0;
	}

	@Override
	public int getZCoord() {
		return zCoord;
	}

	@Override
	public boolean hasPlayerUnlocked(EntityPlayer var1) {
		return false;
	}

	@Override
	public boolean isHidden() {
		return false;
	}
}
