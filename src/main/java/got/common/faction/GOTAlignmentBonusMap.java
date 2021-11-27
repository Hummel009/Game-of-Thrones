package got.common.faction;

import java.util.*;

public class GOTAlignmentBonusMap extends HashMap<GOTFaction, Float> {
	public Set<GOTFaction> getChangedFactions() {
		HashSet<GOTFaction> changed = new HashSet<>();
		for (GOTFaction fac : keySet()) {
			float bonus = get(fac);
			if (bonus == 0.0f) {
				continue;
			}
			changed.add(fac);
		}
		return changed;
	}
}
