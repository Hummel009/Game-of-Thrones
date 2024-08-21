package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;

public class GOTItemLegendaryLongsword extends GOTItemLongsword {
	public GOTItemLegendaryLongsword(ToolMaterial material) {
		super(material);
		setMaxDamage((int) Math.ceil(getMaxDamage() * 1.1f));
		gotWeaponDamage = (int) Math.ceil(gotWeaponDamage * 1.1f);
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}