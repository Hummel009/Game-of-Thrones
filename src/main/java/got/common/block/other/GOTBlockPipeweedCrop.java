package got.common.block.other;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.EnumPlantType;

public class GOTBlockPipeweedCrop extends BlockCrops {
	@SideOnly(value = Side.CLIENT)
	private IIcon[] pipeweedIcons;

	@Override
	public Item func_149865_P() {
		return GOTRegistry.pipeweedLeaf;
	}

	@Override
	public Item func_149866_i() {
		return GOTRegistry.pipeweedSeeds;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j < 7) {
			if (j == 6) {
				j = 5;
			}
			return pipeweedIcons[j >> 1];
		}
		return pipeweedIcons[3];
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return EnumPlantType.Crop;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (world.getBlockMetadata(i, j, k) == 7) {
			GOTRegistry.pipeweedPlant.randomDisplayTick(world, i, j, k, random);
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		pipeweedIcons = new IIcon[4];
		for (int i = 0; i < pipeweedIcons.length; ++i) {
			pipeweedIcons[i] = iconregister.registerIcon(getTextureName() + "_" + i);
		}
	}
}
