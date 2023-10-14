package got.common.item;

import got.common.item.other.GOTItemCoin;
import got.common.item.other.GOTItemGem;
import got.common.item.other.GOTItemRing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GOTValuableItems {
	public static List<ItemStack> toolMaterials = new ArrayList<>();
	public static boolean initTools;

	public static boolean canMagpieSteal(ItemStack itemstack) {
		Item item = itemstack.getItem();
		return item instanceof GOTItemCoin || item instanceof GOTItemRing || item instanceof GOTItemGem;
	}
}
