package got.common.entity.dragon;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class GOTDragonFlightWaypoint {
	private static final String NBT_WAYPOINT_X = "Waypoint-X";
	private static final String NBT_WAYPOINT_Y = "Waypoint-Y";
	private static final String NBT_WAYPOINT_Z = "Waypoint-Z";

	private final Entity entity;

	private int posX;
	private int posY;
	private int posZ;

	public GOTDragonFlightWaypoint(Entity entity) {
		this.entity = entity;
	}

	public void clear() {
		posX = (int) entity.posX;
		posY = (int) entity.posY;
		posZ = (int) entity.posZ;
	}

	private double getDeltaX() {
		return posX - entity.posX;
	}

	private double getDeltaY() {
		return posY - entity.posY;
	}

	private double getDeltaZ() {
		return posZ - entity.posZ;
	}

	public double getDistance() {
		return Math.sqrt(getDistanceSquare());
	}

	private double getDistanceSquare() {
		double deltaX = getDeltaX();
		double deltaY = getDeltaY();
		double deltaZ = getDeltaZ();
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
	}

	public boolean isNear() {
		return getDistanceSquare() < entity.width * entity.width;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		posX = nbt.getInteger(NBT_WAYPOINT_X);
		posY = nbt.getInteger(NBT_WAYPOINT_Y);
		posZ = nbt.getInteger(NBT_WAYPOINT_Z);
	}

	public void setEntity(Entity target) {
		posX = (int) target.posX;
		posY = (int) target.posY;
		posZ = (int) target.posZ;
	}

	public void setVector(Vec3 vec) {
		posX = (int) vec.xCoord;
		posY = (int) vec.yCoord;
		posZ = (int) vec.zCoord;
	}

	@Override
	public String toString() {
		return posX + ", " + posY + ", " + posZ;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger(NBT_WAYPOINT_X, posX);
		nbt.setInteger(NBT_WAYPOINT_Y, posY);
		nbt.setInteger(NBT_WAYPOINT_Z, posZ);
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosZ() {
		return posZ;
	}
}