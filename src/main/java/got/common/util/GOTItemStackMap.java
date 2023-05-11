package got.common.util;

import net.minecraft.item.ItemStack;

import java.util.Map;

public interface GOTItemStackMap<V> extends Map<ItemStack, V> {
	static <T> GOTItemStackMap<T> create() {
		return new GOTItemStackMapImpl<>();
	}

	static <T> GOTItemStackMap<T> create(boolean isNBTSensitive) {
		return new GOTItemStackMapImpl<>(isNBTSensitive);
	}
}