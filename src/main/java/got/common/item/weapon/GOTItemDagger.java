package got.common.item.weapon;

import got.common.database.GOTMaterial;
import net.minecraft.item.Item;

public class GOTItemDagger extends GOTItemSword {
	public GOTItemDagger(GOTMaterial material) {
		this(material.toToolMaterial(), HitEffect.NONE);
		gotMaterial = material;
	}

	public GOTItemDagger(GOTMaterial material, HitEffect e) {
		this(material.toToolMaterial(), e);
		gotMaterial = material;
	}

	public GOTItemDagger(Item.ToolMaterial material) {
		this(material, HitEffect.NONE);
	}

	public GOTItemDagger(Item.ToolMaterial material, HitEffect e) {
		super(material, e);
		gotWeaponDamage -= 3.0f;
		effect = e;
	}
}
