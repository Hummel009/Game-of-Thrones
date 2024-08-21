package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;

public class GOTItemLegendaryLongsword extends GOTItemLongsword {
	public GOTItemLegendaryLongsword(ToolMaterial material) {
		super(material);
		setMaxDamage((int) (getMaxDamage() * 1.1f));
		gotWeaponDamage = (int) (gotWeaponDamage * 1.1f);
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}