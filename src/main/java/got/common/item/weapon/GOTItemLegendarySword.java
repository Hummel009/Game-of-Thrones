package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import net.minecraft.item.Item;

public class GOTItemLegendarySword extends GOTItemSword {
	public GOTItemLegendarySword(Item.ToolMaterial material) {
		this(material, HitEffect.NONE);
	}

	public GOTItemLegendarySword(Item.ToolMaterial material, HitEffect e) {
		super(material, e);
		setMaxDamage((int) (getMaxDamage() * 1.1f));
		gotWeaponDamage = (int) (gotWeaponDamage * 1.1f);
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}