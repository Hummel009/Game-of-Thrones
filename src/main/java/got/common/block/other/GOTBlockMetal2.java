package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GOTBlockMetal2 extends GOTBlockOreStorageBase {
	public GOTBlockMetal2() {
		oreStorageNames = new String[]{"gilded_iron", "gilded_iron", "gilded_iron", "salt", "alloy_steel"};
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
	}
}