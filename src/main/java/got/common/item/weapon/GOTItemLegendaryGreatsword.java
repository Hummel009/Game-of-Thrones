package got.common.item.weapon;

import got.common.database.*;
import net.minecraft.item.Item;

public class GOTItemLegendaryGreatsword extends GOTItemGreatsword {
	public GOTItemLegendaryGreatsword(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemLegendaryGreatsword(Item.ToolMaterial material) {
		super(material);
		setMaxDamage(1500);
		gotWeaponDamage = 15.0f;
		setCreativeTab(GOTCreativeTabs.tabStory);
	}
}