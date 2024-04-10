package got.common.item;

import got.common.item.other.GOTItemCoin;
import got.common.item.other.GOTItemGem;
import got.common.item.other.GOTItemRing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GOTValuableItems {
	private GOTValuableItems() {
	}

	public static boolean canMagpieSteal(ItemStack itemstack) {
		Item item = itemstack.getItem();
		return item instanceof GOTItemCoin || item instanceof GOTItemRing || item instanceof GOTItemGem;
	}
}