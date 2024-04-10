package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class GOTBlockPane extends BlockPane {
	protected GOTBlockPane(String s, String s1, Material material, boolean flag) {
		super(s, s1, material, flag);
		setCreativeTab(GOTCreativeTabs.TAB_DECO);
	}
}