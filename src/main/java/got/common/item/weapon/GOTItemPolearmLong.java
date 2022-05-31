package got.common.item.weapon;

import got.common.database.GOTMaterial;
import net.minecraft.item.Item;

public class GOTItemPolearmLong extends GOTItemPolearm {
	public GOTItemPolearmLong(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemPolearmLong(Item.ToolMaterial material) {
		super(material);
	}
}
