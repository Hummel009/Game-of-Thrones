package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockWoodBars extends GOTBlockPane {
	public GOTBlockWoodBars() {
		super("", "", Material.wood, true);
		setHardness(2.0f);
		setResistance(5.0f);
		setStepSound(Block.soundTypeWood);
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
