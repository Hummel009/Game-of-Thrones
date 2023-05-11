package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class GOTBlockBarrier extends Block {
	public GOTBlockBarrier() {
		super(Material.portal);
		setCreativeTab(CreativeTabs.tabAllSearch);
		setBlockUnbreakable();
		setResistance(Float.MAX_VALUE);
		setHardness(Float.MAX_VALUE);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode) {
			return true;
		}
		return false;
	}
}