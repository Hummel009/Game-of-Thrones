package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;

public class GOTBlockPressurePlate extends BlockPressurePlate {
	public GOTBlockPressurePlate(String iconPath, Material material, BlockPressurePlate.Sensitivity triggerType) {
		super(iconPath, material, triggerType);
		setCreativeTab(GOTCreativeTabs.TAB_MISC);
	}
}
