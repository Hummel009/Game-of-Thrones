package got.common.item.weapon;

import got.common.database.GOTMaterial;
import got.common.item.GOTMaterialFinder;
import net.minecraft.item.*;

public class GOTItemPolearm extends GOTItemSword implements GOTMaterialFinder {
	public GOTMaterial gotMaterial;

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

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
	}
}
