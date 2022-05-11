package got.common.item.weapon;

import got.common.database.*;

public class GOTItemLegendaryCrossbow extends GOTItemCrossbow {
	public GOTItemLegendaryCrossbow() {
		super(GOTMaterial.ALLOY_STEEL);
		setMaxDamage(1500);
		setCreativeTab(GOTCreativeTabs.tabStory);
	}
}
