package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;

public class GOTItemLegendaryCrossbow extends GOTItemCrossbow {
	public GOTItemLegendaryCrossbow() {
		super(ToolMaterial.WOOD);
		setMaxDamage((int) Math.ceil(getMaxDamage() * 1.1f));
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}