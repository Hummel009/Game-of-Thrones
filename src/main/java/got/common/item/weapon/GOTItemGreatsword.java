package got.common.item.weapon;

import got.common.database.GOTMaterial;
import net.minecraft.item.*;

public class GOTItemGreatsword extends GOTItemSword {
	public GOTItemGreatsword(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
		gotWeaponDamage += 2.0f;
	}

	public GOTItemGreatsword(Item.ToolMaterial material) {
		super(material);
		gotWeaponDamage += 2.0f;
	}
}
