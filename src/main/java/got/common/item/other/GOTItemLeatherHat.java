package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTMaterial;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class GOTItemLeatherHat extends GOTItemArmor {
	public static int HAT_LEATHER = 6834742;
	public static int HAT_SHIRRIFF_CHIEF = 2301981;
	public static int FEATHER_WHITE = 16777215;
	public static int FEATHER_SHIRRIFF_CHIEF = 3381529;
	public static int HAT_BLACK = 0;
	@SideOnly(value = Side.CLIENT)
	public IIcon featherIcon;

	public GOTItemLeatherHat() {
		super(GOTMaterial.ROBES, 0);
		setCreativeTab(GOTCreativeTabs.tabMisc);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (GOTItemLeatherHat.isHatDyed(itemstack)) {
			list.add(StatCollector.translateToLocal("item.got.hat.dyed"));
		}
		if (GOTItemLeatherHat.hasFeather(itemstack)) {
			list.add(StatCollector.translateToLocal("item.got.hat.feathered"));
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "got:textures/armor/hat.png";
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass == 1 && GOTItemLeatherHat.hasFeather(itemstack)) {
			return GOTItemLeatherHat.getFeatherColor(itemstack);
		}
		return GOTItemLeatherHat.getHatColor(itemstack);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		if (pass == 1 && GOTItemLeatherHat.hasFeather(itemstack)) {
			return featherIcon;
		}
		return itemIcon;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		featherIcon = iconregister.registerIcon(getIconString() + "_feather");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public static int getFeatherColor(ItemStack itemstack) {
		int i = -1;
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("FeatherColor")) {
			i = itemstack.getTagCompound().getInteger("FeatherColor");
		}
		return i;
	}

	public static int getHatColor(ItemStack itemstack) {
		int dye = GOTItemLeatherHat.getSavedDyeColor(itemstack);
		if (dye != -1) {
			return dye;
		}
		return 6834742;
	}

	public static int getSavedDyeColor(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("HatColor")) {
			return itemstack.getTagCompound().getInteger("HatColor");
		}
		return -1;
	}

	public static boolean hasFeather(ItemStack itemstack) {
		return GOTItemLeatherHat.getFeatherColor(itemstack) != -1;
	}

	public static boolean isFeatherDyed(ItemStack itemstack) {
		return GOTItemLeatherHat.hasFeather(itemstack) && GOTItemLeatherHat.getFeatherColor(itemstack) != 16777215;
	}

	public static boolean isHatDyed(ItemStack itemstack) {
		return GOTItemLeatherHat.getSavedDyeColor(itemstack) != -1;
	}

	public static void removeHatAndFeatherDye(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null) {
			itemstack.getTagCompound().removeTag("HatColor");
		}
		if (GOTItemLeatherHat.hasFeather(itemstack) && GOTItemLeatherHat.isFeatherDyed(itemstack)) {
			GOTItemLeatherHat.setFeatherColor(itemstack, 16777215);
		}
	}

	public static ItemStack setFeatherColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("FeatherColor", i);
		return itemstack;
	}

	public static ItemStack setHatColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("HatColor", i);
		return itemstack;
	}
}
