package got.common.world.map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTWaypointInfo implements GOTAbstractWaypoint {
	private final GOTWaypoint waypoint;

	private final double shiftX;
	private final double shiftY;
	private final int rotation;

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
	public double getShiftX() {
		return shiftX;
	}

	@Override
	public double getShiftY() {
		return shiftY;
	}

	@Override
	public double getImgX() {
		return waypoint.getImgX() + shiftX;
	}

	@Override
	public int getCoordX() {
		return GOTWaypoint.mapToWorldX(waypoint.getImgX() + shiftX);
	}

	@Override
	public double getImgY() {
		return waypoint.getImgY() + shiftY;
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
	public int getCoordZ() {
		return GOTWaypoint.mapToWorldZ(waypoint.getImgY() + shiftY);
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