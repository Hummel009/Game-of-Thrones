package got.common.util;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GOTBlockPos {
	public int x;
	public int y;
	public int z;

	public GOTBlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		GOTBlockPos other = (GOTBlockPos) obj;
		if ((x != other.x) || (y != other.y) || (z != other.z)) {
			return false;
		}
		return true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).

					append(x).append(y).append(z).toHashCode();
	}
}
