package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class GOTBlockCucumberCrop extends BlockCrops {
	@SideOnly(Side.CLIENT)
	public IIcon[] cucumberIcons;

	@Override
	public Item func_149865_P() {
		return GOTItems.cucumber;
	}

	@Override
	public Item func_149866_i() {
		return GOTItems.cucumberSeeds;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		if (j1 < 7) {
			if (j1 == 6) {
				j1 = 5;
			}
			return cucumberIcons[j1 >> 1];
		}
		return GOTBlocks.cucumberPlant.getIcon(i, j1);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return EnumPlantType.Crop;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		cucumberIcons = new IIcon[3];
		for (int i = 0; i < cucumberIcons.length; ++i) {
			cucumberIcons[i] = iconregister.registerIcon(getTextureName() + '_' + i);
		}
	}
}
