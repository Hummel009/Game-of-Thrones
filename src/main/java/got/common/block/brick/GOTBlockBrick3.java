package got.common.block.brick;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GOTBlockBrick3 extends GOTBlockBrickBase {
	public GOTBlockBrick3() {
		setBrickNames("diorite_carved", "granite_carved", "diorite_carved", "diorite_carved", "diorite_carved", "diorite_carved", "diorite_carved", "diorite_carved", "sandstone_carved", "diorite_carved", "lhazar", "sandstone_cracked", "diorite_carved", "sandstone_red", "sandstone_red_cracked", "sandstone_red_carved");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 8));
		list.add(new ItemStack(item, 1, 10));
		list.add(new ItemStack(item, 1, 11));
		list.add(new ItemStack(item, 1, 13));
		list.add(new ItemStack(item, 1, 14));
		list.add(new ItemStack(item, 1, 15));
	}
}
