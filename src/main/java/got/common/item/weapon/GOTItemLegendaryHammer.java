package got.common.item.weapon;

import got.common.database.*;
import got.common.item.GOTMaterialFinder;

public class GOTItemLegendaryHammer extends GOTItemHammer implements GOTMaterialFinder {
	public GOTMaterial gotMaterial;

	public GOTItemLegendaryHammer(GOTMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 12.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
		gotMaterial = material;
	}

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
	}
}
