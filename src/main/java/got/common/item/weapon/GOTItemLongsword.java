package got.common.item.weapon;

import net.minecraft.item.Item;

public class GOTItemLongsword extends GOTItemSword {
	public GOTItemLongsword(Item.ToolMaterial material) {
		super(material);
		gotWeaponDamage += 1.5;
	}
}