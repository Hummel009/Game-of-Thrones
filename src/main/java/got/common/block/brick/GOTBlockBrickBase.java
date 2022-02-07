package got.common.block.brick;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public abstract class GOTBlockBrickBase extends Block {
	@SideOnly(value = Side.CLIENT)
	private IIcon[] brickIcons;
	protected String[] brickNames;

	public GOTBlockBrickBase() {
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

	public IIcon[] getBrickIcons() {
		return brickIcons;
	}

	public String[] getBrickNames() {
		return brickNames;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= getBrickNames().length) {
			j = 0;
		}
		return getBrickIcons()[j];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < getBrickNames().length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		setBrickIcons(new IIcon[getBrickNames().length]);
		for (int i = 0; i < getBrickNames().length; ++i) {
			getBrickIcons()[i] = iconregister.registerIcon(getTextureName() + "_" + getBrickNames()[i]);
		}
	}

	public void setBrickIcons(IIcon[] brickIcons) {
		this.brickIcons = brickIcons;
	}

	public void setBrickNames(String... names) {
		brickNames = names;
	}
}
