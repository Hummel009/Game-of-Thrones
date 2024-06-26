package got.common.util;

import got.client.model.GOTModelDragonAnimaton;

public class GOTTickFloat {
	private boolean clamp;
	private float min;
	private float max;
	private float current;
	private float previous;

	public GOTTickFloat() {
		current = previous = 0;
	}

	public GOTTickFloat(float value) {
		current = previous = value;
	}

	public void add(float value) {
		sync();
		current += value;
		if (clamp) {
			current = GOTModelDragonAnimaton.clamp(current, min, max);
		}
	}

	public float get() {
		return current;
	}

	public float get(float x) {
		return GOTModelDragonAnimaton.lerp(previous, current, x);
	}

	public void set(float value) {
		sync();
		if (clamp) {
			current = GOTModelDragonAnimaton.clamp(value, min, max);
		} else {
			current = value;
		}
	}

	public GOTTickFloat setLimit(float min, float max) {
		clamp = true;
		this.min = min;
		this.max = max;
		set(current);
		return this;
	}

	public void sync() {
		previous = current;
	}
}