package got.common.block.brick;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GOTBlockBrick2 extends GOTBlockBrickBase {
	public GOTBlockBrick2() {
		brickNames = new String[]{"granite", "granite", "granite", "granite", "granite", "granite", "granite", "granite", "granite", "granite", "basalt_carved", "basalt_westeros", "granite", "granite", "granite", "granite"};
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 10));
		list.add(new ItemStack(item, 1, 11));
	}
}
