package got.common.util;

import java.util.*;

import net.minecraft.item.ItemStack;

public class GOTItemStackMapImpl<V> implements GOTItemStackMap<V> {
	private boolean isNBTSensitive;
	private HashMap<GOTItemStackWrapper, V> innerMap = new HashMap();

	public GOTItemStackMapImpl() {
		this(false);
	}

	public GOTItemStackMapImpl(boolean isNBTSensitive) {
		this.isNBTSensitive = isNBTSensitive;
	}

	@Override
	public void clear() {
		this.innerMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return key instanceof ItemStack && this.innerMap.containsKey(new GOTItemStackWrapper((ItemStack) key, this.isNBTSensitive));
	}

	@Override
	public boolean containsValue(Object value) {
		return value != null && this.innerMap.containsValue(value);
	}

	@Override
	public Set<Map.Entry<ItemStack, V>> entrySet() {
		Set<Map.Entry<ItemStack, V>> entrySet = new HashSet<>();
		this.innerMap.forEach((key, value) -> entrySet.add(new AbstractMap.SimpleEntry<>(key.toItemStack(), value)));
		return entrySet;
	}

	@Override
	public V get(Object key) {
		return key instanceof ItemStack ? (V) this.innerMap.get(new GOTItemStackWrapper((ItemStack) key, this.isNBTSensitive)) : null;
	}

	@Override
	public boolean isEmpty() {
		return this.innerMap.isEmpty();
	}

	@Override
	public Set<ItemStack> keySet() {
		HashSet<ItemStack> ret = new HashSet<>();
		for (GOTItemStackWrapper key : this.innerMap.keySet()) {
			ret.add(key.toItemStack());
		}
		return ret;
	}

	@Override
	public V put(ItemStack key, V value) {
		if (key == null || value == null) {
			throw new NullPointerException("Key or value is null");
		}
		return this.innerMap.put(new GOTItemStackWrapper(key, this.isNBTSensitive), value);
	}

	@Override
	public void putAll(Map<? extends ItemStack, ? extends V> map) {
		map.forEach(this::put);
	}

	@Override
	public V remove(Object key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}
		if (!(key instanceof ItemStack)) {
			throw new IllegalArgumentException("Key is not an instance of item stack");
		}
		return this.innerMap.remove(new GOTItemStackWrapper((ItemStack) key, this.isNBTSensitive));
	}

	@Override
	public int size() {
		return this.innerMap.size();
	}

	@Override
	public Collection<V> values() {
		return new ArrayList<>(this.innerMap.values());
	}
}
