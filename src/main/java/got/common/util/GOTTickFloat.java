package got.common.util;

import got.client.model.GOTModelDragonAnimaton;

public class GOTTickFloat {

	private float min;
	private float max;
	private boolean clamp = false;
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
		current = clamp ? GOTModelDragonAnimaton.clamp(value, min, max) : value;
	}

	public GOTTickFloat setLimit(float min, float max) {
		clamp = true;
		setMin(min);
		setMax(max);
		set(current);
		return this;
	}

	private void setMax(float max) {
		this.max = max;
	}

	private void setMin(float min) {
		this.min = min;
	}

	public void sync() {
		previous = current;
	}
}
