package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import net.minecraft.item.Item;

public class GOTItemLegendaryDagger extends GOTItemDagger {
	public GOTItemLegendaryDagger(Item.ToolMaterial material) {
		this(material, HitEffect.NONE);
	}

	public GOTItemLegendaryDagger(Item.ToolMaterial material, HitEffect e) {
		super(material, e);
		setMaxDamage(1500);
		gotWeaponDamage = 6.0f;
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}