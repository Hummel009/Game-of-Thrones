package got.common.item.weapon;

import got.common.database.*;

public class GOTItemLegendaryHammer extends GOTItemHammer {
	public GOTItemLegendaryHammer(GOTMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 12.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
		gotMaterial = material;
	}
}
