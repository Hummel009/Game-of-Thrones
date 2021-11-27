package got.common.block.brick;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class GOTBlockBrick3 extends GOTBlockBrickBase {
	public GOTBlockBrick3() {
		setBrickNames("dioriteCarved", "graniteCarved", "dioriteCarved", "dioriteCarved", "dioriteCarved", "dioriteCarved", "dioriteCarved", "dioriteCarved", "sandstoneCarved", "dioriteCarved", "lhazar", "sandstoneCracked", "dioriteCarved", "sandstoneRed", "sandstoneRedCracked", "sandstoneRedCarved");
	}

	@SideOnly(value = Side.CLIENT)
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
