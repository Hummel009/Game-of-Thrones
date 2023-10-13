package got.common.block.brick;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GOTBlockBrick2 extends GOTBlockBrickBase {
	public GOTBlockBrick2() {
		setBrickNames("granite", "granite", "granite", "granite", "granite", "granite", "granite", "granite", "granite", "granite", "basalt_carved", "basalt_westeros", "granite", "granite", "granite", "granite");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 10));
		list.add(new ItemStack(item, 1, 11));
	}
}
