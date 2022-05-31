package got.common.item.weapon;

import got.common.database.*;
import net.minecraft.item.Item;

public class GOTItemLegendaryCrowbar extends GOTItemSword {
	public GOTItemLegendaryCrowbar(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemLegendaryCrowbar(Item.ToolMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 999.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
	}
}