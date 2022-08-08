package got.common.block.other;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class GOTBlockMetal2 extends GOTBlockOreStorageBase {
	public GOTBlockMetal2() {
		setOreStorageNames("gilded_iron", "gilded_iron", "gilded_iron", "salt", "alloy_steel");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return super.getIcon(i, j);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 4));
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		super.registerBlockIcons(iconregister);
	}
}
