package got.common.item.weapon;

import net.minecraft.item.*;

public class GOTItemHammer extends GOTItemSword {
	public GOTItemHammer(Item.ToolMaterial material) {
		super(material);
		gotWeaponDamage += 2.0f;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}
}
