package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;

public class GOTItemLegendaryLongsword extends GOTItemLongsword {
	public GOTItemLegendaryLongsword(ToolMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage *= 1.5f;
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}