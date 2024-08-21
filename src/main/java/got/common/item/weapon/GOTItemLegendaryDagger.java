package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import net.minecraft.item.Item;

public class GOTItemLegendaryDagger extends GOTItemDagger {
	public GOTItemLegendaryDagger(Item.ToolMaterial material) {
		this(material, HitEffect.NONE);
	}

	private GOTItemLegendaryDagger(Item.ToolMaterial material, HitEffect e) {
		super(material, e);
		setMaxDamage((int) Math.ceil(getMaxDamage() * 1.1f));
		gotWeaponDamage = (int) Math.ceil(gotWeaponDamage * 1.1f);
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}