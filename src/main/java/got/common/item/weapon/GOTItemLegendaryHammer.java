package got.common.item.weapon;

import got.common.database.*;
import net.minecraft.item.Item;

public class GOTItemLegendaryHammer extends GOTItemHammer {
	public GOTItemLegendaryHammer(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemLegendaryHammer(Item.ToolMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 12.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
	}
}