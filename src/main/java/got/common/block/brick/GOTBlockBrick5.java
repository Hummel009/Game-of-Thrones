package got.common.block.brick;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class GOTBlockBrick5 extends GOTBlockBrickBase {
	public GOTBlockBrick5() {
		setBrickNames("mud", "mud", "mud", "rhyoliteCarved", "mud", "mud", "mud", "mud", "mud", "mud", "mud", "yiti", "yitiCarved", "yitiMossy", "yitiCracked", "yitiFlowers");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 11));
		list.add(new ItemStack(item, 1, 12));
		list.add(new ItemStack(item, 1, 13));
		list.add(new ItemStack(item, 1, 14));
		list.add(new ItemStack(item, 1, 15));
	}
}
