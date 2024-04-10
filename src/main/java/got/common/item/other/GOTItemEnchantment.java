package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.enchant.GOTEnchantment;
import net.minecraft.item.Item;

public class GOTItemEnchantment extends Item {
	private final GOTEnchantment theEnchant;

	public GOTItemEnchantment(GOTEnchantment ench) {
		setCreativeTab(GOTCreativeTabs.TAB_MATERIALS);
		theEnchant = ench;
	}

	public GOTEnchantment getTheEnchant() {
		return theEnchant;
	}
}