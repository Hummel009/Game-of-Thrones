package got.common.world.map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTWaypointInfo implements GOTAbstractWaypoint {
	private double shiftX;
	private double shiftY;
	private int rotation;
	private GOTWaypoint waypoint;

	public GOTWaypointInfo(GOTWaypoint waypoint, double shiftX, double shiftY, int rotation) {
		this.shiftX = shiftX;
		this.shiftY = shiftY;
		this.waypoint = waypoint;
		this.rotation = rotation;
	}

	@Override
	public String getCodeName() {
		return waypoint.getCodeName();
	}

	@Override
	public String getDisplayName() {
		return waypoint.getDisplayName();
	}

	@Override
	public int getID() {
		return waypoint.getID();
	}

	@Override
	public double getShiftX() {
		return shiftX;
	}

	@Override
	public double getShiftY() {
		return shiftY;
	}

	@Override
	public GOTWaypoint getInstance() {
		return waypoint;
	}

	@Override
	public WaypointLockState getLockState(EntityPlayer entityplayer) {
		return waypoint.getLockState(entityplayer);
	}

	@Override
	public String getLoreText(EntityPlayer entityplayer) {
		return waypoint.getLoreText(entityplayer);
	}

	@Override
	public int getRotation() {
		return rotation;
	}

	@Override
	public double getX() {
		return waypoint.getX() + shiftX;
	}

	@Override
	public int getXCoord() {
		return GOTWaypoint.mapToWorldX(waypoint.getX() + shiftX);
	}

	@Override
	public double getY() {
		return waypoint.getY() + shiftY;
	}

	@Override
	public int getYCoord(World world, int i, int k) {
		return waypoint.getYCoord(world, i, k);
	}

	@Override
	public int getYCoordSaved() {
		return waypoint.getYCoordSaved();
	}

	@Override
	public int getZCoord() {
		return GOTWaypoint.mapToWorldZ(waypoint.getY() + shiftY);
	}

	@Override
	public boolean hasPlayerUnlocked(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public boolean isHidden() {
		return true;
	}
}
