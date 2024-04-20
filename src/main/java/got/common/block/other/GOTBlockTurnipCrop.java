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

public class GOTBlockTurnipCrop extends BlockCrops {
	@SideOnly(Side.CLIENT)
	private IIcon[] turnipIcons;

	@Override
	public Item func_149865_P() {
		return GOTItems.turnip;
	}

	@Override
	public Item func_149866_i() {
		return GOTItems.turnip;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		if (j1 < 7) {
			if (j1 == 6) {
				j1 = 5;
			}
			return turnipIcons[j1 >> 1];
		}
		return turnipIcons[3];
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return EnumPlantType.Crop;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		turnipIcons = new IIcon[4];
		for (int i = 0; i < turnipIcons.length; ++i) {
			turnipIcons[i] = iconregister.registerIcon(getTextureName() + '_' + i);
		}
	}
}