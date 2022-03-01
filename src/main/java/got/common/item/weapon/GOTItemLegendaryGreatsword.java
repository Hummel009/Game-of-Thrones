package got.common.item.weapon;

import got.common.database.*;

public class GOTItemLegendaryGreatsword extends GOTItemGreatsword {
	public GOTItemLegendaryGreatsword(GOTMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 15.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
		gotMaterial = material;
	}
}
