package got.common.util;

import net.minecraft.util.MathHelper;

import javax.annotation.Nullable;

public class GOTVec3d {
	public static GOTVec3d ZERO = new GOTVec3d(0.0, 0.0, 0.0);
	public double xCoord;
	public double yCoord;
	public double zCoord;

	public GOTVec3d(double x, double y, double z) {
		if (x == -0.0) {
			x = 0.0;
		}
		if (y == -0.0) {
			y = 0.0;
		}
		if (z == -0.0) {
			z = 0.0;
		}
		xCoord = x;
		yCoord = y;
		zCoord = z;
	}

	public static GOTVec3d fromPitchYaw(float p_189986_0_, float p_189986_1_) {
		float f = MathHelper.cos(-p_189986_1_ * 0.017453292f - 3.1415927f);
		float f1 = MathHelper.sin(-p_189986_1_ * 0.017453292f - 3.1415927f);
		float f2 = -MathHelper.cos(-p_189986_0_ * 0.017453292f);
		float f3 = MathHelper.sin(-p_189986_0_ * 0.017453292f);
		return new GOTVec3d(f1 * f2, f3, f * f2);
	}

	public GOTVec3d add(GOTVec3d vec) {
		return addVector(vec.xCoord, vec.yCoord, vec.zCoord);
	}

	public GOTVec3d addVector(double x, double y, double z) {
		return new GOTVec3d(xCoord + x, yCoord + y, zCoord + z);
	}

	public GOTVec3d crossProduct(GOTVec3d vec) {
		return new GOTVec3d(yCoord * vec.zCoord - zCoord * vec.yCoord, zCoord * vec.xCoord - xCoord * vec.zCoord, xCoord * vec.yCoord - yCoord * vec.xCoord);
	}

	public double distanceTo(GOTVec3d vec) {
		double d0 = vec.xCoord - xCoord;
		double d1 = vec.yCoord - yCoord;
		double d2 = vec.zCoord - zCoord;
		return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
	}

	public double dotProduct(GOTVec3d vec) {
		return xCoord * vec.xCoord + yCoord * vec.yCoord + zCoord * vec.zCoord;
	}

	@Override
	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		}
		if (!(p_equals_1_ instanceof GOTVec3d)) {
			return false;
		}
		GOTVec3d gOTVec3d = (GOTVec3d) p_equals_1_;
		return Double.compare(gOTVec3d.xCoord, xCoord) == 0 && Double.compare(gOTVec3d.yCoord, yCoord) == 0 && Double.compare(gOTVec3d.zCoord, zCoord) == 0;
	}

	@Nullable
	public GOTVec3d getIntermediateWithXValue(GOTVec3d vec, double x) {
		double d0 = vec.xCoord - xCoord;
		double d1 = vec.yCoord - yCoord;
		double d2 = vec.zCoord - zCoord;
		if (d0 * d0 < 1.0000000116860974E-7) {
			return null;
		}
		double d3 = (x - xCoord) / d0;
		if (d3 >= 0.0 && d3 <= 1.0) {
			return new GOTVec3d(xCoord + d0 * d3, yCoord + d1 * d3, zCoord + d2 * d3);
		}
		return null;
	}

	@Nullable
	public GOTVec3d getIntermediateWithYValue(GOTVec3d vec, double y) {
		double d0 = vec.xCoord - xCoord;
		double d1 = vec.yCoord - yCoord;
		double d2 = vec.zCoord - zCoord;
		if (d1 * d1 < 1.0000000116860974E-7) {
			return null;
		}
		double d3 = (y - yCoord) / d1;
		if (d3 >= 0.0 && d3 <= 1.0) {
			return new GOTVec3d(xCoord + d0 * d3, yCoord + d1 * d3, zCoord + d2 * d3);
		}
		return null;
	}

	@Nullable
	public GOTVec3d getIntermediateWithZValue(GOTVec3d vec, double z) {
		double d0 = vec.xCoord - xCoord;
		double d1 = vec.yCoord - yCoord;
		double d2 = vec.zCoord - zCoord;
		if (d2 * d2 < 1.0000000116860974E-7) {
			return null;
		}
		double d3 = (z - zCoord) / d2;
		if (d3 >= 0.0 && d3 <= 1.0) {
			return new GOTVec3d(xCoord + d0 * d3, yCoord + d1 * d3, zCoord + d2 * d3);
		}
		return null;
	}

	@Override
	public int hashCode() {
		long j = Double.doubleToLongBits(xCoord);
		int i = (int) (j ^ j >>> 32);
		j = Double.doubleToLongBits(yCoord);
		i = 31 * i + (int) (j ^ j >>> 32);
		j = Double.doubleToLongBits(zCoord);
		return 31 * i + (int) (j ^ j >>> 32);
	}

	public double lengthSquared() {
		return xCoord * xCoord + yCoord * yCoord + zCoord * zCoord;
	}

	public double lengthVector() {
		return MathHelper.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
	}

	public GOTVec3d normalize() {
		double d0 = MathHelper.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
		if (d0 < 1.0E-4) {
			return ZERO;
		}
		return new GOTVec3d(xCoord / d0, yCoord / d0, zCoord / d0);
	}

	public GOTVec3d rotateYaw(float yaw) {
		float f = MathHelper.cos(yaw);
		float f1 = MathHelper.sin(yaw);
		double d0 = xCoord * f + zCoord * f1;
		double d1 = yCoord;
		double d2 = zCoord * f - xCoord * f1;
		return new GOTVec3d(d0, d1, d2);
	}

	public GOTVec3d scale(double p_186678_1_) {
		return new GOTVec3d(xCoord * p_186678_1_, yCoord * p_186678_1_, zCoord * p_186678_1_);
	}

	public GOTVec3d subtract(double x, double y, double z) {
		return addVector(-x, -y, -z);
	}

	public GOTVec3d subtract(GOTVec3d vec) {
		return subtract(vec.xCoord, vec.yCoord, vec.zCoord);
	}

	@Override
	public String toString() {
		return "(" + xCoord + ", " + yCoord + ", " + zCoord + ')';
	}
}
