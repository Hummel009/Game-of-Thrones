package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.GOTDamage;
import got.common.util.GOTCrashHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GOTBlockTallGrass extends GOTBlockGrass {
	public static final boolean[] GRASS_OVERLAY = {false, true, true, true, false, false};

	private static final String[] GRASS_NAMES = {"short", "flower", "wheat", "thistle", "nettle", "fernsprout"};

	@SideOnly(Side.CLIENT)
	private IIcon[] grassIcons;

	@SideOnly(Side.CLIENT)
	private IIcon[] overlayIcons;

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		return GOTCrashHandler.getBiomeGenForCoords(world, i, k).getBiomeGrassColor(i, j, k);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockColor() {
		return Blocks.tallgrass.getBlockColor();
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		if (meta == 3) {
			ArrayList<ItemStack> thistles = new ArrayList<>();
			thistles.add(new ItemStack(this, 1, 3));
			return thistles;
		}
		return super.getDrops(world, i, j, k, meta, fortune);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int j1 = j;
		if (j1 >= GRASS_NAMES.length) {
			j1 = 0;
		}
		if (i == -1) {
			return overlayIcons[j1];
		}
		return grassIcons[j1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int meta) {
		return getBlockColor();
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j < GRASS_NAMES.length; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		int meta = world.getBlockMetadata(i, j, k);
		if (meta == 3 && entity.isSprinting() || meta == 4 && entity instanceof EntityPlayer) {
			EntityLivingBase living;
			boolean bootsLegs = entity instanceof EntityLivingBase && (living = (EntityLivingBase) entity).getEquipmentInSlot(1) != null && living.getEquipmentInSlot(2) != null;
			if (!bootsLegs) {
				entity.attackEntityFrom(GOTDamage.PLANT_HURT, 0.25f);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		grassIcons = new IIcon[GRASS_NAMES.length];
		overlayIcons = new IIcon[GRASS_NAMES.length];
		for (int i = 0; i < GRASS_NAMES.length; ++i) {
			grassIcons[i] = iconregister.registerIcon(getTextureName() + '_' + GRASS_NAMES[i]);
			if (!GRASS_OVERLAY[i]) {
				continue;
			}
			overlayIcons[i] = iconregister.registerIcon(getTextureName() + '_' + GRASS_NAMES[i] + "_overlay");
		}
	}
}