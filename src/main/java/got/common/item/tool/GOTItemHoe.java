package got.common.item.tool;

import got.common.database.GOTCreativeTabs;
import got.common.item.GOTMaterialFinder;
import net.minecraft.item.ItemHoe;

public class GOTItemHoe extends ItemHoe implements GOTMaterialFinder {
	public ToolMaterial gotMaterial;

	public GOTItemHoe(ToolMaterial material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.TAB_TOOLS);
		gotMaterial = material;
	}

	@Override
	public ToolMaterial getMaterial() {
		return gotMaterial;
	}
}
