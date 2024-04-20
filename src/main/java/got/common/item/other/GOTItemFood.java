package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.item.ItemFood;

public class GOTItemFood extends ItemFood {
	public GOTItemFood(int healAmount, float saturation, boolean canWolfEat) {
		super(healAmount, saturation, canWolfEat);
		setCreativeTab(GOTCreativeTabs.TAB_FOOD);
	}
}