package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class GOTBlockThatch extends Block {
	private static final String[] THATCH_NAMES = {"thatch", "reed"};

	@SideOnly(Side.CLIENT)
	private IIcon[] thatchIcons;

	public GOTBlockThatch() {
		super(Material.grass);
		setHardness(0.5f);
		setStepSound(soundTypeGrass);
		setCreativeTab(GOTCreativeTabs.tabBlock);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		if (j1 >= THATCH_NAMES.length) {
			j1 = 0;
		}
		return thatchIcons[j1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < THATCH_NAMES.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		thatchIcons = new IIcon[THATCH_NAMES.length];
		for (int i = 0; i < THATCH_NAMES.length; ++i) {
			thatchIcons[i] = iconregister.registerIcon(getTextureName() + '_' + THATCH_NAMES[i]);
		}
	}
}
