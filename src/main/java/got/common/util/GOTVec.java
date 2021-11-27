package got.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class GOTVec {
	public double x;
	public double y;
	public double z;

	public GOTVec(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public GOTVec(Vec3 vec) {
		x = vec.xCoord;
		y = vec.yCoord;
		z = vec.zCoord;
	}

	public GOTVec add(double x, double y, double z) {
		return new GOTVec(this.x + x, this.y + y, this.z + z);
	}

	public GOTVec add(GOTVec v2) {
		return new GOTVec(x + v2.x, y + v2.y, z + v2.z);
	}

	public void add_ip(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public void add_ip(GOTVec v2) {
		x += v2.x;
		y += v2.y;
		z += v2.z;
	}

	public GOTVec changelen(double l) {
		double oldl = length();
		if (oldl != 0) {
			double changefactor = l / oldl;
			return mult(changefactor);
		}
		return this;
	}

	public void changelen_ip(double l) {
		double oldl = length();
		if (oldl != 0) {
			double changefactor = l / oldl;
			mult_ip(changefactor);
		}
	}

	public double dot(GOTVec v2) {
		return x * v2.x + y * v2.y + z * v2.z;
	}

	public double getPitch() {
		GOTVec norm = normalize();
		return Math.toDegrees(-Math.asin(norm.y));
	}

	public double getYaw() {
		GOTVec norm = normalize();
		return Math.toDegrees(-Math.atan2(norm.x, norm.z));
	}

	public double length() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

	public GOTVec mult(double changefactor) {
		return new GOTVec(x * changefactor, y * changefactor, z * changefactor);
	}

	public void mult_ip(double changefactor) {
		x *= changefactor;
		y *= changefactor;
		z *= changefactor;
	}

	public GOTVec normalize() {
		return mult(1.0 / length());
	}

	public void normalize_ip() {
		mult_ip(1.0 / length());
	}

	public GOTVec proj(GOTVec v2) {
		GOTVec v3 = v2.normalize();
		double dot = dot(v3);
		return v3.changelen(dot);
	}

	public GOTVec removealong(GOTVec v2) {
		return sub(proj(v2));
	}

	public GOTVec rotate_pitch(double pitch) {
		return new GOTVec(x, y * Math.cos(pitch) + z * Math.sin(pitch), z * Math.cos(pitch) - y * Math.sin(pitch));
	}

	public GOTVec rotate_yaw(double a) {
		return new GOTVec(x * Math.cos(a) - z * Math.sin(a), y, x * Math.sin(a) + z * Math.cos(a));
	}

	public GOTVec sub(GOTVec v2) {
		return new GOTVec(x - v2.x, y - v2.y, z - v2.z);
	}

	public void sub_ip(GOTVec v2) {
		x -= v2.x;
		y -= v2.y;
		z -= v2.z;
	}

	public static GOTVec motionvec(Entity e) {
		return new GOTVec(e.motionX, e.motionY, e.motionZ);
	}

	public static GOTVec positionvec(Entity e) {
		return new GOTVec(e.posX, e.posY, e.posZ);
	}
}
