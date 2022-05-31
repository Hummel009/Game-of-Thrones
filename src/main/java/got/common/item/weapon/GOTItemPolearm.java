package got.common.item.weapon;

import got.common.database.GOTMaterial;
import net.minecraft.item.*;

public class GOTItemPolearm extends GOTItemSword {
	public GOTItemPolearm(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemPolearm(Item.ToolMaterial material) {
		super(material);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}
}
