package got.common.faction;

import java.util.*;

public class GOTAlignmentBonusMap implements Map<GOTFaction, Float> {
	private final Map<GOTFaction, Float> bonusMap = new EnumMap<>(GOTFaction.class);

	public Set<GOTFaction> getChangedFactions() {
		EnumSet<GOTFaction> changed = EnumSet.noneOf(GOTFaction.class);
		for (Map.Entry<GOTFaction, Float> fac : entrySet()) {
			float bonus = fac.getValue();
			if (bonus == 0.0f) {
				continue;
			}
			changed.add(fac.getKey());
		}
		return changed;
	}

	@Override
	public int size() {
		return bonusMap.size();
	}

	@Override
	public boolean isEmpty() {
		return bonusMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return bonusMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return bonusMap.containsValue(value);
	}

	@Override
	public Float get(Object key) {
		return bonusMap.get(key);
	}

	@Override
	public Float put(GOTFaction key, Float value) {
		return bonusMap.put(key, value);
	}

	@Override
	public Float remove(Object key) {
		return bonusMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends GOTFaction, ? extends Float> m) {
		bonusMap.putAll(m);
	}

	@Override
	public void clear() {
		bonusMap.clear();
	}

	@Override
	public Set<GOTFaction> keySet() {
		return bonusMap.keySet();
	}

	@Override
	public Collection<Float> values() {
		return bonusMap.values();
	}

	@Override
	public Set<Entry<GOTFaction, Float>> entrySet() {
		return bonusMap.entrySet();
	}
}