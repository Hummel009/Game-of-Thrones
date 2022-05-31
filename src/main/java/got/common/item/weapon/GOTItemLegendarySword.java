package got.common.item.weapon;

import got.common.database.*;
import net.minecraft.item.Item;

public class GOTItemLegendarySword extends GOTItemSword {
	public GOTItemLegendarySword(GOTMaterial material) {
		this(material.toToolMaterial(), HitEffect.NONE);
		gotMaterial = material;
	}

	public GOTItemLegendarySword(GOTMaterial material, HitEffect e) {
		this(material.toToolMaterial(), e);
		gotMaterial = material;
	}

	public GOTItemLegendarySword(Item.ToolMaterial material) {
		this(material, HitEffect.NONE);
	}

	public GOTItemLegendarySword(Item.ToolMaterial material, HitEffect e) {
		super(material, e);
		setMaxDamage(1500);
		gotWeaponDamage = 9.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
	}
}