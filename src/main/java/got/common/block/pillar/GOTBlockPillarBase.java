package got.common.block.pillar;

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

public abstract class GOTBlockPillarBase extends Block {
	@SideOnly(Side.CLIENT)
	public IIcon[] pillarFaceIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] pillarSideIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] pillarSideTopIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] pillarSideMiddleIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] pillarSideBottomIcons;
	public String[] pillarNames;

	protected GOTBlockPillarBase() {
		this(Material.rock);
		setHardness(1.5f);
		setResistance(10.0f);
		setStepSound(Block.soundTypeStone);
	}

	protected GOTBlockPillarBase(Material material) {
		super(material);
		setCreativeTab(GOTCreativeTabs.tabBlock);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		boolean pillarAbove = isPillarAt(world, i, j + 1, k);
		boolean pillarBelow = isPillarAt(world, i, j - 1, k);
		int meta = world.getBlockMetadata(i, j, k);
		if (meta >= pillarNames.length) {
			meta = 0;
		}
		if (side == 0 || side == 1) {
			return pillarFaceIcons[meta];
		}
		if (pillarAbove && pillarBelow) {
			return pillarSideMiddleIcons[meta];
		}
		if (pillarAbove) {
			return pillarSideBottomIcons[meta];
		}
		if (pillarBelow) {
			return pillarSideTopIcons[meta];
		}
		return pillarSideIcons[meta];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (j >= pillarNames.length) {
			j = 0;
		}
		if (i == 0 || i == 1) {
			return pillarFaceIcons[j];
		}
		return pillarSideIcons[j];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < pillarNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	public boolean isPillarAt(IBlockAccess world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		return block instanceof GOTBlockPillarBase;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		pillarFaceIcons = new IIcon[pillarNames.length];
		pillarSideIcons = new IIcon[pillarNames.length];
		pillarSideTopIcons = new IIcon[pillarNames.length];
		pillarSideMiddleIcons = new IIcon[pillarNames.length];
		pillarSideBottomIcons = new IIcon[pillarNames.length];
		for (int i = 0; i < pillarNames.length; ++i) {
			String s = getTextureName() + "_" + pillarNames[i];
			pillarFaceIcons[i] = iconregister.registerIcon(s + "_face");
			pillarSideIcons[i] = iconregister.registerIcon(s + "_side");
			pillarSideTopIcons[i] = iconregister.registerIcon(s + "_side_top");
			pillarSideMiddleIcons[i] = iconregister.registerIcon(s + "_side_middle");
			pillarSideBottomIcons[i] = iconregister.registerIcon(s + "_side_bottom");
		}
	}

	public void setPillarNames(String... names) {
		pillarNames = names;
	}
}
