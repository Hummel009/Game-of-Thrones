package got.common.block.planks;

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

public abstract class GOTBlockPlanksBase extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon[] plankIcons;
	public String[] plankTypes;

	protected GOTBlockPlanksBase() {
		super(Material.wood);
		setHardness(2.0f);
		setResistance(5.0f);
		setStepSound(Block.soundTypeWood);
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
		if (j1 >= plankTypes.length) {
			j1 = 0;
		}
		return plankIcons[j1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < plankTypes.length; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		plankIcons = new IIcon[plankTypes.length];
		for (int i = 0; i < plankTypes.length; ++i) {
			plankIcons[i] = iconregister.registerIcon(getTextureName() + '_' + plankTypes[i]);
		}
	}

	public void setPlankTypes(String... types) {
		plankTypes = types;
	}
}
