package got.common.util;

import java.util.Arrays;

import got.client.model.GOTModelDragonAnimaton;

public class GOTCircularBuffer {
	private double buffer[];
	private int index = 0;

	public GOTCircularBuffer(int size) {
		buffer = new double[size];
	}

	public void fill(double value) {
		Arrays.fill(buffer, value);
	}

	private double get(float x, int offset) {
		int i = index - offset;
		int len = buffer.length - 1;
		return GOTModelDragonAnimaton.lerp(buffer[i - 1 & len], buffer[i & len], x);
	}

	public double get(float x, int offset1, int offset2) {
		return get(x, offset2) - get(x, offset1);
	}

	public void update(double value) {
		index++;
		index %= buffer.length;
		buffer[index] = value;
	}
}