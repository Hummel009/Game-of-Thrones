package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class GOTBlockCucumberCrop extends BlockCrops {
	@SideOnly(value = Side.CLIENT)
	private IIcon[] cucumberIcons;

	@Override
	public Item func_149865_P() {
		return GOTRegistry.cucumber;
	}

	@Override
	public Item func_149866_i() {
		return GOTRegistry.cucumberSeeds;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j < 7) {
			if (j == 6) {
				j = 5;
			}
			return cucumberIcons[j >> 1];
		}
		return GOTRegistry.cucumberPlant.getIcon(i, j);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return EnumPlantType.Crop;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		cucumberIcons = new IIcon[3];
		for (int i = 0; i < cucumberIcons.length; ++i) {
			cucumberIcons[i] = iconregister.registerIcon(getTextureName() + "_" + i);
		}
	}
}
