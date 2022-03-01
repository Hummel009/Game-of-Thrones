package got.common.item.weapon;

import got.common.database.*;
import net.minecraft.item.Item;

public class GOTItemLegendaryBattleaxe extends GOTItemBattleaxe {
	public GOTItemLegendaryBattleaxe(GOTMaterial material) {
		super(material.toToolMaterial());
		setMaxDamage(1500);
		gotWeaponDamage = 9.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
		gotMaterial = material;
	}

	public GOTItemLegendaryBattleaxe(Item.ToolMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 9.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
	}
}
