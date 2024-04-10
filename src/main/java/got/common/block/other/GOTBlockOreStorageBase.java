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
import net.minecraft.world.IBlockAccess;

import java.util.List;

public abstract class GOTBlockOreStorageBase extends Block {
	@SideOnly(Side.CLIENT)
	protected IIcon[] oreStorageIcons;

	protected String[] oreStorageNames;

	protected GOTBlockOreStorageBase() {
		super(Material.iron);
		setCreativeTab(GOTCreativeTabs.TAB_BLOCK);
		setHardness(5.0f);
		setResistance(10.0f);
		setStepSound(soundTypeMetal);
	}

	@Override
	public boolean canSilkHarvest() {
		return true;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		if (j1 >= oreStorageNames.length) {
			j1 = 0;
		}
		return oreStorageIcons[j1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < oreStorageNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, int i, int j, int k, int beaconX, int beaconY, int beaconZ) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		oreStorageIcons = new IIcon[oreStorageNames.length];
		for (int i = 0; i < oreStorageNames.length; ++i) {
			oreStorageIcons[i] = iconregister.registerIcon(getTextureName() + '_' + oreStorageNames[i]);
		}
	}

	public String[] getOreStorageNames() {
		return oreStorageNames;
	}

	protected void setOreStorageNames(String... names) {
		oreStorageNames = names;
	}
}