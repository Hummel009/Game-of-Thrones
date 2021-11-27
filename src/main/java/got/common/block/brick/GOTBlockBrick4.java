package got.common.block.brick;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class GOTBlockBrick4 extends GOTBlockBrickBase {
	public GOTBlockBrick4() {
		setBrickNames("sothoryos", "sothoryosMossy", "sothoryosCracked", "sothoryosGold", "sothoryosObsidian", "sothoryos", "basaltWesterosCarved", "sandstoneLapis", "sothoryos", "sothoryos", "sothoryos", "sothoryos", "sothoryos", "sothoryos", "sothoryos", "chalk");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
		list.add(new ItemStack(item, 1, 6));
		list.add(new ItemStack(item, 1, 7));
		list.add(new ItemStack(item, 1, 15));
	}
}
