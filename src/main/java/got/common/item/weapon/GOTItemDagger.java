package got.common.item.weapon;

import net.minecraft.item.Item;

public class GOTItemDagger extends GOTItemSword {
	public GOTItemDagger(Item.ToolMaterial material) {
		this(material, HitEffect.NONE);
	}

	public GOTItemDagger(Item.ToolMaterial material, HitEffect e) {
		super(material, e);
		gotWeaponDamage -= 3.0f;
		effect = e;
	}
}
