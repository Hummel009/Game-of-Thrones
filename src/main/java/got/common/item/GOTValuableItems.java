package got.common.item;

import java.util.*;

import got.common.item.other.*;
import net.minecraft.item.*;

public class GOTValuableItems {
	public static List<ItemStack> toolMaterials = new ArrayList<>();
	public static boolean initTools;

	public static boolean canMagpieSteal(ItemStack itemstack) {
		Item item = itemstack.getItem();
		return item instanceof GOTItemCoin || item instanceof GOTItemRing || item instanceof GOTItemGem;
	}
}
