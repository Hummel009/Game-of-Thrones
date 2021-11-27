package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockReedBars extends GOTBlockPane {
	public GOTBlockReedBars() {
		super("", "", Material.grass, true);
		setHardness(0.5f);
		setStepSound(Block.soundTypeGrass);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon func_150097_e() {
		return blockIcon;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		blockIcon = iconregister.registerIcon(getTextureName());
	}
}
