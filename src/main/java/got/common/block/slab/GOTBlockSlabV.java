package got.common.block.slab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockSlabV extends GOTBlockSlabBase {
	public GOTBlockSlabV(boolean flag) {
		super(flag, Material.rock, 6);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		j &= 7;
		switch (j) {
			case 0:
				return Blocks.stonebrick.getIcon(i, 1);
			case 1:
				return Blocks.stonebrick.getIcon(i, 2);
			case 2:
				return GOTRegistry.redBrick.getIcon(i, 0);
			case 3:
				return GOTRegistry.redBrick.getIcon(i, 1);
			case 4:
				return Blocks.mossy_cobblestone.getIcon(i, 0);
			case 5:
				return Blocks.stone.getIcon(i, 0);
			default:
				break;
		}
		return super.getIcon(i, j);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
