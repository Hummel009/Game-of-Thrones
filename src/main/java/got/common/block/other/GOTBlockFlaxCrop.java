package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTRegistry;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class GOTBlockFlaxCrop extends BlockCrops {
	@SideOnly(Side.CLIENT)
	public IIcon[] flaxIcons;

	@Override
	public Item func_149865_P() {
		return GOTRegistry.flax;
	}

	@Override
	public Item func_149866_i() {
		return GOTRegistry.flaxSeeds;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j < 7) {
			if (j == 6) {
				j = 5;
			}
			return flaxIcons[j >> 1];
		}
		return GOTRegistry.flaxPlant.getIcon(i, j);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return EnumPlantType.Crop;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		flaxIcons = new IIcon[3];
		for (int i = 0; i < flaxIcons.length; ++i) {
			flaxIcons[i] = iconregister.registerIcon(getTextureName() + "_" + i);
		}
	}
}
