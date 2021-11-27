package got.common.item.tool;

import got.common.database.*;
import got.common.item.GOTMaterialFinder;
import net.minecraft.item.*;

public class GOTItemHoe extends ItemHoe implements GOTMaterialFinder {
	public GOTMaterial gotMaterial;

	public GOTItemHoe(GOTMaterial material) {
		this(material.toToolMaterial());
		gotMaterial = material;
	}

	public GOTItemHoe(Item.ToolMaterial material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabTools);
	}

	@Override
	public GOTMaterial getMaterial() {
		return gotMaterial;
	}
}
