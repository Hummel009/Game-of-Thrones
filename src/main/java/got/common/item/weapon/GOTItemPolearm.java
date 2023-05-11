package got.common.item.weapon;

import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GOTItemPolearm extends GOTItemSword {
	public GOTItemPolearm(Item.ToolMaterial material) {
		super(material);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}
}
