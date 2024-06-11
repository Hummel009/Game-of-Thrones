package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class GOTBlockConcrete extends Block {
	public GOTBlockConcrete() {
		super(Material.rock);
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
		setHardness(1.5f);
		setHarvestLevel("pickaxe", 0);
		setResistance(10.0f);
		setStepSound(soundTypeStone);
	}

	@SideOnly(Side.CLIENT)
	private IIcon[] concreteIcons;

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		if (j1 >= 16) {
			j1 = 0;
		}
		return concreteIcons[j1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 16; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		concreteIcons = new IIcon[16];
		for (int i = 0; i < concreteIcons.length; ++i) {
			int dyeMeta = BlockColored.func_150031_c(i);
			if ("lightBlue".equals(ItemDye.field_150923_a[dyeMeta])) {
				concreteIcons[i] = iconregister.registerIcon(getTextureName() + "_light_blue");
			} else {
				concreteIcons[i] = iconregister.registerIcon(getTextureName() + '_' + ItemDye.field_150923_a[dyeMeta]);
			}
		}
	}
}