package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class GOTBlockMetal extends GOTBlockOreStorageBase {
	public GOTBlockMetal() {
		oreStorageNames = new String[]{"copper", "tin", "bronze", "silver", "valyrian", "copper", "copper", "copper", "copper", "copper", "copper", "copper", "copper", "sulfur", "saltpeter", "copper"};
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
		list.add(new ItemStack(item, 1, 13));
		list.add(new ItemStack(item, 1, 14));
	}

	@Override
	public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
		return world.getBlockMetadata(i, j, k) == 13 && side == ForgeDirection.UP;
	}
}