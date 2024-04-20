package got.common.entity.dragon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public enum GOTDragonLifeStage {
	EGG(-36000), HATCHLING(-24000), JUVENILE(-12000), ADULT(0);

	private final int ageLimit;

	GOTDragonLifeStage(int ageLimit) {
		this.ageLimit = ageLimit;
	}

	public static List<String> getLifeStageNames() {
		List<String> names = new ArrayList<>();
		for (GOTDragonLifeStage s : values()) {
			names.add(s.name().toLowerCase(Locale.ROOT));
		}
		return names;
	}

	public int getAgeLimit() {
		return ageLimit;
	}
}