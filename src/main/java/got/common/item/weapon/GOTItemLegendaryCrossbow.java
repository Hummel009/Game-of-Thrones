package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import got.common.database.GOTMaterial;

public class GOTItemLegendaryCrossbow extends GOTItemCrossbow {
	public GOTItemLegendaryCrossbow() {
		super(GOTMaterial.COBALT_TOOL);
		setMaxDamage(1500);
		setCreativeTab(GOTCreativeTabs.tabStory);
	}
}
