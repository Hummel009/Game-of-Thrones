package got.common.util;

import net.minecraft.item.ItemStack;

import java.util.*;

public class GOTItemStackMap<V> implements Map<ItemStack, V> {
	private final boolean isNBTSensitive;
	private final Map<GOTItemStackWrapper, V> innerMap = new HashMap<>();

	public GOTItemStackMap() {
		this(false);
	}

	private GOTItemStackMap(boolean isNBTSensitive) {
		this.isNBTSensitive = isNBTSensitive;
	}

	@Override
	public void clear() {
		innerMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return key instanceof ItemStack && innerMap.containsKey(new GOTItemStackWrapper((ItemStack) key, isNBTSensitive));
	}

	@Override
	public boolean containsValue(Object value) {
		return value != null && innerMap.containsValue(value);
	}

	@Override
	public Set<Map.Entry<ItemStack, V>> entrySet() {
		Set<Map.Entry<ItemStack, V>> entrySet = new HashSet<>();
		for (Entry<GOTItemStackWrapper, V> entry : innerMap.entrySet()) {
			GOTItemStackWrapper key = entry.getKey();
			V value = entry.getValue();
			entrySet.add(new AbstractMap.SimpleEntry<>(key.toItemStack(), value));
		}
		return entrySet;
	}

	@Override
	public V get(Object key) {
		if (key instanceof ItemStack) {
			return innerMap.get(new GOTItemStackWrapper((ItemStack) key, isNBTSensitive));
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return innerMap.isEmpty();
	}

	@Override
	public Set<ItemStack> keySet() {
		Set<ItemStack> ret = new HashSet<>();
		for (GOTItemStackWrapper key : innerMap.keySet()) {
			ret.add(key.toItemStack());
		}
		return ret;
	}

	@Override
	public V put(ItemStack key, V value) {
		if (key == null || value == null) {
			throw new NullPointerException("Key or value is null");
		}
		return innerMap.put(new GOTItemStackWrapper(key, isNBTSensitive), value);
	}

	@Override
	public void putAll(Map<? extends ItemStack, ? extends V> map) {
		for (Entry<? extends ItemStack, ? extends V> entry : map.entrySet()) {
			ItemStack key = entry.getKey();
			V value = entry.getValue();
			put(key, value);
		}
	}

	@Override
	public V remove(Object key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}
		if (!(key instanceof ItemStack)) {
			throw new IllegalArgumentException("Key is not an instance of item stack");
		}
		return innerMap.remove(new GOTItemStackWrapper((ItemStack) key, isNBTSensitive));
	}

	@Override
	public int size() {
		return innerMap.size();
	}

	@Override
	public Collection<V> values() {
		return new ArrayList<>(innerMap.values());
	}
}