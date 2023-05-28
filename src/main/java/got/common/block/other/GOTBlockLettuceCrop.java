package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class GOTBlockLettuceCrop extends BlockCrops {
	@SideOnly(Side.CLIENT)
	public IIcon[] lettuceIcons;

	@Override
	public Item func_149865_P() {
		return GOTItems.lettuce;
	}

	@Override
	public Item func_149866_i() {
		return GOTItems.lettuce;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j < 7) {
			if (j == 6) {
				j = 5;
			}
			return lettuceIcons[j >> 1];
		}
		return lettuceIcons[3];
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return EnumPlantType.Crop;
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		lettuceIcons = new IIcon[4];
		for (int i = 0; i < lettuceIcons.length; ++i) {
			lettuceIcons[i] = iconregister.registerIcon(getTextureName() + "_" + i);
		}
	}
}
