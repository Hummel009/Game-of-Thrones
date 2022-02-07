package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class GOTBlockTurnipCrop extends BlockCrops {
	@SideOnly(value = Side.CLIENT)
	private IIcon[] turnipIcons;

	@Override
	public Item func_149865_P() {
		return GOTRegistry.turnip;
	}

	@Override
	public Item func_149866_i() {
		return GOTRegistry.turnip;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j < 7) {
			if (j == 6) {
				j = 5;
			}
			return turnipIcons[j >> 1];
		}
		return turnipIcons[3];
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return EnumPlantType.Crop;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		turnipIcons = new IIcon[4];
		for (int i = 0; i < turnipIcons.length; ++i) {
			turnipIcons[i] = iconregister.registerIcon(getTextureName() + "_" + i);
		}
	}
}
