package got.common.item.weapon;

import got.common.database.GOTMaterial;
import got.common.item.GOTMaterialFinder;
import net.minecraft.item.Item;

public class GOTItemPike extends GOTItemPolearmLong implements GOTMaterialFinder {
	public GOTMaterial gotMaterial;

	public GOTItemPike(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemPike(Item.ToolMaterial material) {
		super(material);
	}

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
	}
}
