package got.common.item.tool;

import got.common.database.GOTCreativeTabs;
import got.common.item.GOTMaterialFinder;
import net.minecraft.item.ItemPickaxe;

public class GOTItemPickaxe extends ItemPickaxe implements GOTMaterialFinder {
	public ToolMaterial gotMaterial;

	public GOTItemPickaxe(ToolMaterial material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabTools);
		gotMaterial = material;
	}

	@Override
	public ToolMaterial getMaterial() {
		return gotMaterial;
	}
}
