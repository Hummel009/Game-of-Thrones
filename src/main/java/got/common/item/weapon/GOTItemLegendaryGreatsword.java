package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import net.minecraft.item.Item;

public class GOTItemLegendaryGreatsword extends GOTItemGreatsword {
	public GOTItemLegendaryGreatsword(Item.ToolMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage *= 1.5f;
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}