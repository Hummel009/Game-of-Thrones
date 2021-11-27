package got.common.item.tool;

import got.common.database.*;
import got.common.item.GOTMaterialFinder;
import net.minecraft.item.*;

public class GOTItemPickaxe extends ItemPickaxe implements GOTMaterialFinder {
	public GOTMaterial gotMaterial;

	public GOTItemPickaxe(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemPickaxe(Item.ToolMaterial material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabTools);
	}

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
	}
}
