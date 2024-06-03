package got.common.util;

import net.minecraft.util.MathHelper;

import java.util.Objects;

public class GOTVertex {
	private final double x;
	private final double y;
	private final double z;

	public GOTVertex(double x, double y, double z) {
		double x1 = x;
		double y1 = y;
		double z1 = z;
		if (x1 == -0.0) {
			x1 = 0.0;
		}
		if (y1 == -0.0) {
			y1 = 0.0;
		}
		if (z1 == -0.0) {
			z1 = 0.0;
		}
		this.x = x1;
		this.y = y1;
		this.z = z1;
	}

	public double distanceTo(GOTVertex vec) {
		double d0 = vec.x - x;
		double d1 = vec.y - y;
		double d2 = vec.z - z;
		return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		GOTVertex vertex = (GOTVertex) o;
		return Double.compare(x, vertex.x) == 0 && Double.compare(y, vertex.y) == 0 && Double.compare(z, vertex.z) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	public GOTVertex rotateYaw(float yaw) {
		float f = MathHelper.cos(yaw);
		float f1 = MathHelper.sin(yaw);
		double d0 = x * f + z * f1;
		double d2 = z * f - x * f1;
		return new GOTVertex(d0, y, d2);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ')';
	}

	public double getX() {
		return x;
	}

	public double getZ() {
		return z;
	}
}