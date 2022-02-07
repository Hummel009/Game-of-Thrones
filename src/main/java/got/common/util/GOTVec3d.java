package got.common.util;

import javax.annotation.Nullable;

import net.minecraft.util.MathHelper;

public class GOTVec3d {
	private double xCoord;
	private double yCoord;
	private double zCoord;

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
		setxCoord(x);
		yCoord = y;
		setzCoord(z);
	}

	public double distanceTo(GOTVec3d vec) {
		double d0 = vec.getxCoord() - getxCoord();
		double d1 = vec.yCoord - yCoord;
		double d2 = vec.getzCoord() - getzCoord();
		return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
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
		return Double.compare(gOTVec3d.getxCoord(), getxCoord()) != 0 ? false : Double.compare(gOTVec3d.yCoord, yCoord) != 0 ? false : Double.compare(gOTVec3d.getzCoord(), getzCoord()) == 0;
	}

	@Nullable
	private GOTVec3d getIntermediateWithXValue(GOTVec3d vec, double x) {
		double d0 = vec.getxCoord() - getxCoord();
		double d1 = vec.yCoord - yCoord;
		double d2 = vec.getzCoord() - getzCoord();
		if (d0 * d0 < 1.0000000116860974E-7) {
			return null;
		}
		double d3 = (x - getxCoord()) / d0;
		return d3 >= 0.0 && d3 <= 1.0 ? new GOTVec3d(getxCoord() + d0 * d3, yCoord + d1 * d3, getzCoord() + d2 * d3) : null;
	}

	@Nullable
	private GOTVec3d getIntermediateWithYValue(GOTVec3d vec, double y) {
		double d0 = vec.getxCoord() - getxCoord();
		double d1 = vec.yCoord - yCoord;
		double d2 = vec.getzCoord() - getzCoord();
		if (d1 * d1 < 1.0000000116860974E-7) {
			return null;
		}
		double d3 = (y - yCoord) / d1;
		return d3 >= 0.0 && d3 <= 1.0 ? new GOTVec3d(getxCoord() + d0 * d3, yCoord + d1 * d3, getzCoord() + d2 * d3) : null;
	}

	@Nullable
	private GOTVec3d getIntermediateWithZValue(GOTVec3d vec, double z) {
		double d0 = vec.getxCoord() - getxCoord();
		double d1 = vec.yCoord - yCoord;
		double d2 = vec.getzCoord() - getzCoord();
		if (d2 * d2 < 1.0000000116860974E-7) {
			return null;
		}
		double d3 = (z - getzCoord()) / d2;
		return d3 >= 0.0 && d3 <= 1.0 ? new GOTVec3d(getxCoord() + d0 * d3, yCoord + d1 * d3, getzCoord() + d2 * d3) : null;
	}

	public double getxCoord() {
		return xCoord;
	}

	public double getzCoord() {
		return zCoord;
	}

	@Override
	public int hashCode() {
		long j = Double.doubleToLongBits(getxCoord());
		int i = (int) (j ^ j >>> 32);
		j = Double.doubleToLongBits(yCoord);
		i = 31 * i + (int) (j ^ j >>> 32);
		j = Double.doubleToLongBits(getzCoord());
		return 31 * i + (int) (j ^ j >>> 32);
	}

	public GOTVec3d rotateYaw(float yaw) {
		float f = MathHelper.cos(yaw);
		float f1 = MathHelper.sin(yaw);
		double d0 = getxCoord() * f + getzCoord() * f1;
		double d1 = yCoord;
		double d2 = getzCoord() * f - getxCoord() * f1;
		return new GOTVec3d(d0, d1, d2);
	}

	public void setxCoord(double xCoord) {
		this.xCoord = xCoord;
	}

	public void setzCoord(double zCoord) {
		this.zCoord = zCoord;
	}

	@Override
	public String toString() {
		return "(" + getxCoord() + ", " + yCoord + ", " + getzCoord() + ")";
	}
}
