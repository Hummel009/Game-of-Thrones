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
	@SideOnly(Side.CLIENT)
	private IIcon featherIcon;

	public GOTItemLeatherHat() {
		super(GOTMaterial.ROBES, 0);
		setCreativeTab(GOTCreativeTabs.TAB_MISC);
	}

	public static int getFeatherColor(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("FeatherColor")) {
			return itemstack.getTagCompound().getInteger("FeatherColor");
		}
		return -1;
	}

	public static int getHatColor(ItemStack itemstack) {
		int dye = getSavedDyeColor(itemstack);
		if (dye != -1) {
			return dye;
		}
		return 6834742;
	}

	private static int getSavedDyeColor(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("HatColor")) {
			return itemstack.getTagCompound().getInteger("HatColor");
		}
		return -1;
	}

	public static boolean hasFeather(ItemStack itemstack) {
		return getFeatherColor(itemstack) != -1;
	}

	public static boolean isFeatherDyed(ItemStack itemstack) {
		return hasFeather(itemstack) && getFeatherColor(itemstack) != 16777215;
	}

	public static boolean isHatDyed(ItemStack itemstack) {
		return getSavedDyeColor(itemstack) != -1;
	}

	public static void removeHatAndFeatherDye(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null) {
			itemstack.getTagCompound().removeTag("HatColor");
		}
		if (hasFeather(itemstack) && isFeatherDyed(itemstack)) {
			setFeatherColor(itemstack, 16777215);
		}
	}

	public static void setFeatherColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("FeatherColor", i);
	}

	@SuppressWarnings("JavaExistingMethodCanBeUsed")
	public static void setHatColor(ItemStack itemstack, int i) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setInteger("HatColor", i);
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		if (isHatDyed(itemstack)) {
			list.add(StatCollector.translateToLocal("item.got.hat.dyed"));
		}
		if (hasFeather(itemstack)) {
			list.add(StatCollector.translateToLocal("item.got.hat.feathered"));
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "got:textures/armor/hat.png";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass == 1 && hasFeather(itemstack)) {
			return getFeatherColor(itemstack);
		}
		return getHatColor(itemstack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		if (pass == 1 && hasFeather(itemstack)) {
			return featherIcon;
		}
		return itemIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		featherIcon = iconregister.registerIcon(getIconString() + "_feather");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}
