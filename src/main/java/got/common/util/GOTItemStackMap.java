package got.common.util;

import java.util.Map;

import net.minecraft.item.ItemStack;

public interface GOTItemStackMap<V> extends Map<ItemStack, V> {
	static <T> GOTItemStackMap<T> create() {
		return new GOTItemStackMapImpl();
	}

	static <T> GOTItemStackMap<T> create(boolean isNBTSensitive) {
		return new GOTItemStackMapImpl(isNBTSensitive);
	}
}