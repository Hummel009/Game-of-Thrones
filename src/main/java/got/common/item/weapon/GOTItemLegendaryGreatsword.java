package got.common.item.weapon;

import got.common.database.*;
import got.common.item.*;

public class GOTItemLegendaryGreatsword extends GOTItemSword implements GOTMaterialFinder {
	public GOTMaterial gotMaterial;

	public GOTItemLegendaryGreatsword(GOTMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 15.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
		GOTWeaponStats.registerMeleeSpeed(GOTItemLegendaryGreatsword.class, 0.8f);
		GOTWeaponStats.registerMeleeReach(GOTItemLegendaryGreatsword.class, 1.2f);
		gotMaterial = material;
	}

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
	}
}
