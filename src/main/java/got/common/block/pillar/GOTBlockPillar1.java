package got.common.block.pillar;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

public class GOTBlockPillar1 extends GOTBlockPillarBase {
	public GOTBlockPillar1() {
		setPillarNames("diorite", "diorite", "diorite", "diorite", "granite", "sandstone", "andesite", "basalt", "rhyolite", "basaltWesteros", "diorite", "diorite", "diorite", "diorite", "sothoryos", "sandstoneRed");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
		list.add(new ItemStack(item, 1, 5));
		list.add(new ItemStack(item, 1, 6));
		list.add(new ItemStack(item, 1, 7));
		list.add(new ItemStack(item, 1, 8));
		list.add(new ItemStack(item, 1, 9));
		list.add(new ItemStack(item, 1, 14));
		list.add(new ItemStack(item, 1, 15));
	}
}
