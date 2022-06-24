package got.common.item.weapon;

import net.minecraft.item.*;

public class GOTItemPolearm extends GOTItemSword {
	public GOTItemPolearm(Item.ToolMaterial material) {
		super(material);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}
}
