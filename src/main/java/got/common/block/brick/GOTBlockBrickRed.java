package got.common.block.brick;

import net.minecraft.creativetab.CreativeTabs;

public class GOTBlockBrickRed extends GOTBlockBrickBase {
	public GOTBlockBrickRed() {
		brickNames = new String[]{"mossy", "cracked"};
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(2.0f);
		setResistance(10.0f);
	}
}