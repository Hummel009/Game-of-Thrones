package got.common.item.weapon;

import got.common.database.GOTMaterial;
import got.common.item.GOTMaterialFinder;
import net.minecraft.item.Item;

public class GOTItemPolearmLong extends GOTItemPolearm implements GOTMaterialFinder {
	public GOTMaterial gotMaterial;

	public GOTItemPolearmLong(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemPolearmLong(Item.ToolMaterial material) {
		super(material);
	}

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
	}
}
