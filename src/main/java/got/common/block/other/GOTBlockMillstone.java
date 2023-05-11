package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.tileentity.GOTTileEntityMillstone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class GOTBlockMillstone extends BlockContainer {
	@SideOnly(value = Side.CLIENT)
	public IIcon iconSide;
	@SideOnly(value = Side.CLIENT)
	public IIcon iconTop;
	@SideOnly(value = Side.CLIENT)
	public IIcon iconSideActive;
	@SideOnly(value = Side.CLIENT)
	public IIcon iconTopActive;

	public GOTBlockMillstone() {
		super(Material.rock);
		setCreativeTab(GOTCreativeTabs.tabUtil);
		setHardness(4.0f);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
		GOTTileEntityMillstone millstone = (GOTTileEntityMillstone) world.getTileEntity(i, j, k);
		if (millstone != null) {
			GOT.dropContainerItems(millstone, world, i, j, k);
			world.func_147453_f(i, j, k, block);
		}
		super.breakBlock(world, i, j, k, block, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new GOTTileEntityMillstone();
	}

	@Override
	public int getComparatorInputOverride(World world, int i, int j, int k, int direction) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(i, j, k));
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
		boolean active = GOTBlockMillstone.isMillstoneActive(world, i, j, k);
		if (side == 1 || side == 0) {
			return active ? iconTopActive : iconTop;
		}
		return active ? iconSideActive : iconSide;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return i == 1 || i == 0 ? iconTop : iconSide;
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		if (!world.isRemote) {
			entityplayer.openGui(GOT.instance, 52, world, i, j, k);
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
		if (itemstack.hasDisplayName()) {
			((GOTTileEntityMillstone) world.getTileEntity(i, j, k)).setSpecialMillstoneName(itemstack.getDisplayName());
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		if (GOTBlockMillstone.isMillstoneActive(world, i, j, k)) {
			for (int l = 0; l < 6; ++l) {
				float f10 = 0.5f + MathHelper.randomFloatClamp(random, -0.2f, 0.2f);
				float f11 = 0.5f + MathHelper.randomFloatClamp(random, -0.2f, 0.2f);
				float f12 = 0.9f + random.nextFloat() * 0.2f;
				world.spawnParticle("smoke", i + f10, j + f12, k + f11, 0.0, 0.0, 0.0);
			}
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		iconSide = iconregister.registerIcon(getTextureName() + "_side");
		iconTop = iconregister.registerIcon(getTextureName() + "_top");
		iconSideActive = iconregister.registerIcon(getTextureName() + "_side_active");
		iconTopActive = iconregister.registerIcon(getTextureName() + "_top_active");
	}

	public static boolean isMillstoneActive(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		return (meta & 8) != 0;
	}

	public static void toggleMillstoneActive(World world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockMetadataWithNotify(i, j, k, meta ^ 8, 2);
	}
}
