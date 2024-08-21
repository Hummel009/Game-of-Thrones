package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import net.minecraft.item.Item;

public class GOTItemLegendaryGreatsword extends GOTItemGreatsword {
	public GOTItemLegendaryGreatsword(Item.ToolMaterial material) {
		super(material);
		setMaxDamage((int) (getMaxDamage() * 1.1f));
		gotWeaponDamage *= 1.1f;
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}