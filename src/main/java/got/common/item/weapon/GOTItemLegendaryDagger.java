package got.common.item.weapon;

import got.common.database.*;
import net.minecraft.item.Item;

public class GOTItemLegendaryDagger extends GOTItemDagger {
	public GOTItemLegendaryDagger(GOTMaterial material) {
		this(material.toToolMaterial(), HitEffect.NONE);
		gotMaterial = material;
	}

	public GOTItemLegendaryDagger(GOTMaterial material, HitEffect e) {
		this(material.toToolMaterial(), e);
		gotMaterial = material;
	}

	public GOTItemLegendaryDagger(Item.ToolMaterial material) {
		this(material, HitEffect.NONE);
	}

	public GOTItemLegendaryDagger(Item.ToolMaterial material, HitEffect e) {
		super(material, e);
		setMaxDamage(1500);
		gotWeaponDamage = 6.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
	}
}