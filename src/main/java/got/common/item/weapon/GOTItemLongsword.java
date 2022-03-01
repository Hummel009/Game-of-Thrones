package got.common.item.weapon;

import got.common.database.GOTMaterial;
import net.minecraft.item.*;

public class GOTItemLongsword extends GOTItemSword {
	public GOTItemLongsword(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
		gotWeaponDamage += 1.0f;
	}

	public GOTItemLongsword(Item.ToolMaterial material) {
		super(material);
		gotWeaponDamage += 1.0f;
	}
}