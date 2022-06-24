package got.common.item.weapon;

import got.common.database.*;

public class GOTItemLegendaryCrossbow extends GOTItemCrossbow {
	public GOTItemLegendaryCrossbow() {
		super(GOTMaterial.COBALT_TOOL);
		setMaxDamage(1500);
		setCreativeTab(GOTCreativeTabs.tabStory);
	}
}
