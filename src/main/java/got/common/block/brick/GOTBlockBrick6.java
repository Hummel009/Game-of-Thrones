package got.common.block.brick;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class GOTBlockBrick6 extends GOTBlockBrickBase {
	public GOTBlockBrick6() {
		setBrickNames("yitiGold", "yitiGranite", "yitiGraniteCarved", "swords", "labradorite", "labradoriteCarved", "labradoriteCracked", "labradoriteMossy", "yitiGold", "yitiGold", "yitiGold");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
		list.add(new ItemStack(item, 1, 5));
		list.add(new ItemStack(item, 1, 6));
		list.add(new ItemStack(item, 1, 7));
	}
}