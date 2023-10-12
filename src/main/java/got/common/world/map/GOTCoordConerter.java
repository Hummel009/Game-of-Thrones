package got.common.world.map;

public class GOTCoordConerter {
	public static double toSettlement(double value, double radius) {
		if (value > 0.0) {
			return value - radius / 128.0;
		} else if (value < 0.0) {
			return value + radius / 128.0;
		} else {
			return value;
		}
	}

	public static double toEssosTown(double value) {
		return toSettlement(value, 46.0);
	}

	public static double toWesterosCastle(double value) {
		return toSettlement(value, 39.0);
	}

	public static double toYiTiTown(double value) {
		return toSettlement(value, 94.0);
	}

	public static double toYiTiFort(double value) {
		return toSettlement(value, 61.0);
	}

	public static double toWesterosTown(double value) {
		return toSettlement(value, 84.0);
	}

	public static double toLhazar(double value) {
		return toSettlement(value, 98.0);
	}

	public static double toEssosTownGate(double value, boolean xAxis) {
		return value + (34.0 / 128.0 * (xAxis ? 1 : -1));
	}
}