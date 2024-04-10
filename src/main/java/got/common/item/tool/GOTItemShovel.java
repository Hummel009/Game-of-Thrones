package got.common.item.tool;

import got.common.database.GOTCreativeTabs;
import got.common.item.GOTMaterialFinder;
import net.minecraft.item.ItemSpade;

public class GOTItemShovel extends ItemSpade implements GOTMaterialFinder {
	public ToolMaterial gotMaterial;

	public GOTItemShovel(ToolMaterial material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.TAB_TOOLS);
		gotMaterial = material;
	}

	@Override
	public ToolMaterial getMaterial() {
		return gotMaterial;
	}
}
