package got.common.faction;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

public class GOTAlignmentBonusMap extends HashMap<GOTFaction, Float> {
	public Set<GOTFaction> getChangedFactions() {
		EnumSet<GOTFaction> changed = EnumSet.noneOf(GOTFaction.class);
		for (Entry<GOTFaction, Float> fac : entrySet()) {
			float bonus = fac.getValue();
			if (bonus == 0.0f) {
				continue;
			}
			changed.add(fac.getKey());
		}
		return changed;
	}
}
