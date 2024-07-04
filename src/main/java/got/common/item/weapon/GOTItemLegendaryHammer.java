package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import net.minecraft.item.Item;

public class GOTItemLegendaryHammer extends GOTItemHammer {
	public GOTItemLegendaryHammer(Item.ToolMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage *= 1.5f;
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}