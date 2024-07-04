package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;

public class GOTItemLegendaryCrossbow extends GOTItemCrossbow {
	public GOTItemLegendaryCrossbow() {
		super(ToolMaterial.WOOD);
		setMaxDamage(1500);
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}