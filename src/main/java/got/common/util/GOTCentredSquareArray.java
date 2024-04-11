package got.common.util;

import java.util.Arrays;

public class GOTCentredSquareArray<T> {
	private final Object[] array;
	private final int radius;
	private final int width;

	public GOTCentredSquareArray(int r) {
		radius = r;
		width = radius * 2 + 1;
		array = new Object[width * width];
	}

	public void fill(T val) {
		Arrays.fill(array, val);
	}

	public T get(int x, int y) {
		int index = getIndex(x, y);
		return (T) array[index];
	}

	private int getIndex(int x, int y) {
		return (y + radius) * width + x + radius;
	}

	public void set(int x, int y, T val) {
		int index = getIndex(x, y);
		array[index] = val;
	}
}