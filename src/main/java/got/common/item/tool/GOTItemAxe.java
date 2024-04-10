package got.common.item.tool;

import got.common.database.GOTCreativeTabs;
import got.common.item.GOTMaterialFinder;
import net.minecraft.item.ItemAxe;

public class GOTItemAxe extends ItemAxe implements GOTMaterialFinder {
	private final ToolMaterial gotMaterial;

	public GOTItemAxe(ToolMaterial material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.TAB_TOOLS);
		setHarvestLevel("axe", material.getHarvestLevel());
		gotMaterial = material;
	}

	@Override
	public ToolMaterial getMaterial() {
		return gotMaterial;
	}
}