package got.common.item.weapon;

import got.common.database.GOTCreativeTabs;
import net.minecraft.item.Item;

public class GOTItemLegendaryBattleaxe extends GOTItemBattleaxe {
	public GOTItemLegendaryBattleaxe(Item.ToolMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 12.0f;
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}
}