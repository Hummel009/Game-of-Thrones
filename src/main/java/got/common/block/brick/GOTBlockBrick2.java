package got.common.block.brick;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class GOTBlockBrick2 extends GOTBlockBrickBase {
	public GOTBlockBrick2() {
		setBrickNames("granite", "granite", "granite", "granite", "granite", "granite", "granite", "granite", "granite", "granite", "basaltCarved", "basaltWesteros", "granite", "granite", "granite", "granite");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 10));
		list.add(new ItemStack(item, 1, 11));
	}
}
