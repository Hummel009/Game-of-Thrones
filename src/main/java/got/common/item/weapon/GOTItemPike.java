package got.common.item.weapon;

import got.common.database.GOTMaterial;
import net.minecraft.item.Item;

public class GOTItemPike extends GOTItemPolearmLong {
	public GOTItemPike(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemPike(Item.ToolMaterial material) {
		super(material);
	}
}
