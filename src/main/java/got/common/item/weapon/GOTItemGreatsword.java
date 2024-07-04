package got.common.item.weapon;

import net.minecraft.item.Item;

public class GOTItemGreatsword extends GOTItemSword {
	public GOTItemGreatsword(Item.ToolMaterial material) {
		super(material);
		gotWeaponDamage *= 1.5F;
	}
}