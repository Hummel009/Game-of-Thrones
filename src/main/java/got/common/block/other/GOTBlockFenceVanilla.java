package got.common.block.other;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class GOTBlockFenceVanilla extends GOTBlockFence {
	public GOTBlockFenceVanilla() {
		super(Blocks.planks);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
}
