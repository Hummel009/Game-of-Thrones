package got.common.item.weapon;

import got.common.database.GOTMaterial;
import got.common.item.GOTMaterialFinder;
import net.minecraft.item.*;

public class GOTItemHammer extends GOTItemSword implements GOTMaterialFinder {
	public GOTItemHammer(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemHammer(Item.ToolMaterial material) {
		super(material);
		gotWeaponDamage += 2.0f;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.none;
	}
}
