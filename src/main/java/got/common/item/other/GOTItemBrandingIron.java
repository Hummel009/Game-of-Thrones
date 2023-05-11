package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.entity.other.GOTEntityNPC;
import got.common.tileentity.GOTTileEntityAlloyForge;
import got.common.tileentity.GOTTileEntityOven;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class GOTItemBrandingIron extends Item {
	public static int HEAT_USES = 5;
	@SideOnly(Side.CLIENT)
	public IIcon iconCool;
	@SideOnly(Side.CLIENT)
	public IIcon iconHot;

	public GOTItemBrandingIron() {
		setCreativeTab(GOTCreativeTabs.tabTools);
		setMaxStackSize(1);
		setMaxDamage(100);
		setFull3D();
	}

	public static UUID getBrandingPlayer(Entity entity) {
		NBTTagCompound nbt = entity.getEntityData();
		if (nbt.hasKey("GOTBrander")) {
			String s = nbt.getString("GOTBrander");
			return UUID.fromString(s);
		}
		return null;
	}

	public static String getBrandName(ItemStack itemstack) {
		String s;
		if (itemstack.hasTagCompound() && !StringUtils.isBlank(s = itemstack.getTagCompound().getString("BrandName"))) {
			return s;
		}
		return null;
	}

	public static boolean hasBrandName(ItemStack itemstack) {
		return GOTItemBrandingIron.getBrandName(itemstack) != null;
	}

	public static boolean isHeated(ItemStack itemstack) {
		if (itemstack.hasTagCompound()) {
			return itemstack.getTagCompound().getBoolean("HotIron");
		}
		return false;
	}

	public static void setBrandingPlayer(Entity entity, UUID player) {
		String s = player.toString();
		entity.getEntityData().setString("GOTBrander", s);
	}

	public static void setBrandName(ItemStack itemstack, String s) {
		if (!itemstack.hasTagCompound()) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setString("BrandName", s);
	}

	public static void setHeated(ItemStack itemstack, boolean flag) {
		if (!itemstack.hasTagCompound()) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setBoolean("HotIron", flag);
	}

	public static String trimAcceptableBrandName(String s) {
		s = StringUtils.trim(s);
		int maxLength = 64;
		if (s.length() > maxLength) {
			s = s.substring(0, maxLength);
		}
		return s;
	}

	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		if (GOTItemBrandingIron.isHeated(itemstack)) {
			return iconHot;
		}
		return iconCool;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconIndex(ItemStack itemstack) {
		return this.getIcon(itemstack, 0);
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return repairItem.getItem() == Items.iron_ingot;
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack) {
		String name = super.getItemStackDisplayName(itemstack);
		if (GOTItemBrandingIron.hasBrandName(itemstack)) {
			String brandName = GOTItemBrandingIron.getBrandName(itemstack);
			name = StatCollector.translateToLocalFormatted("item.got.brandingIron.named", name, brandName);
		} else {
			name = StatCollector.translateToLocalFormatted("item.got.brandingIron.unnamed", name);
		}
		return name;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entity) {
		if (GOTItemBrandingIron.isHeated(itemstack) && GOTItemBrandingIron.hasBrandName(itemstack)) {
			String brandName = GOTItemBrandingIron.getBrandName(itemstack);
			if (entity instanceof EntityLiving) {
				EntityLiving entityliving = (EntityLiving) entity;
				boolean acceptableEntity = false;
				if (entityliving instanceof EntityAnimal || entityliving instanceof GOTEntityNPC && ((GOTEntityNPC) entityliving).canRenameNPC()) {
					acceptableEntity = true;
				}
				if (acceptableEntity && !entityliving.getCustomNameTag().equals(brandName)) {
					entityliving.setCustomNameTag(brandName);
					entityliving.func_110163_bv();
					entityliving.playLivingSound();
					entityliving.getJumpHelper().setJumping();
					World world = entityliving.worldObj;
					world.playSoundAtEntity(entityliving, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
					entityplayer.swingItem();
					int preDamage = itemstack.getItemDamage();
					itemstack.damageItem(1, entityplayer);
					int newDamage = itemstack.getItemDamage();
					if (preDamage / 5 != newDamage / 5) {
						GOTItemBrandingIron.setHeated(itemstack, false);
					}
					if (!world.isRemote) {
						GOTItemBrandingIron.setBrandingPlayer(entityliving, entityplayer.getUniqueID());
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!GOTItemBrandingIron.hasBrandName(itemstack)) {
			entityplayer.openGui(GOT.instance, 61, world, 0, 0, 0);
		}
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (GOTItemBrandingIron.hasBrandName(itemstack) && !GOTItemBrandingIron.isHeated(itemstack)) {
			boolean isHotBlock = false;
			TileEntity te = world.getTileEntity(i, j, k);
			if (te instanceof TileEntityFurnace && ((TileEntityFurnace) te).isBurning() || te instanceof GOTTileEntityAlloyForge && ((GOTTileEntityAlloyForge) te).isSmelting()) {
				isHotBlock = true;
			} else if (te instanceof GOTTileEntityOven && ((GOTTileEntityOven) te).isCooking()) {
				isHotBlock = true;
			}
			if (!isHotBlock) {
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				Block block = world.getBlock(i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ);
				if (block.getMaterial() == Material.fire) {
					isHotBlock = true;
				}
			}
			if (isHotBlock) {
				GOTItemBrandingIron.setHeated(itemstack, true);
				return true;
			}
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		iconCool = iconregister.registerIcon(getIconString());
		iconHot = iconregister.registerIcon(getIconString() + "_hot");
	}
}
