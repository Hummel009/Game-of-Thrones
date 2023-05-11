package got.common.block.brick;

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

public abstract class GOTBlockBrickBase extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon[] brickIcons;
	public String[] brickNames;

	protected GOTBlockBrickBase() {
		super(Material.rock);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(1.5f);
		setResistance(10.0f);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= brickNames.length) {
			j = 0;
		}
		return brickIcons[j];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < brickNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		brickIcons = new IIcon[brickNames.length];
		for (int i = 0; i < brickNames.length; ++i) {
			brickIcons[i] = iconregister.registerIcon(getTextureName() + "_" + brickNames[i]);
		}
	}

	public void setBrickNames(String... names) {
		brickNames = names;
	}
}
