package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import net.minecraft.item.Item;

public class GOTItemLegendaryCrowbar extends GOTItemSword {
	public GOTItemLegendaryCrowbar(Item.ToolMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 999.0f;
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}