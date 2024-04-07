package got.common.block.pillar;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GOTBlockPillar2 extends GOTBlockPillarBase {
	public GOTBlockPillar2() {
		setPillarNames("labradorite", "chalk", "stone", "brick", "stone", "stone", "stone", "stone", "yiti", "yiti_red", "stone", "sothoryos_gold", "sothoryos_obsidian", "stone", "stone");
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 8));
		list.add(new ItemStack(item, 1, 9));
		list.add(new ItemStack(item, 1, 11));
		list.add(new ItemStack(item, 1, 12));
	}
}