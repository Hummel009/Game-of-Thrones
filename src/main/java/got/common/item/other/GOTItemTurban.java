package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class GOTItemTurban extends GOTItemRobes {
	@SideOnly(value = Side.CLIENT)
	public IIcon ornamentIcon;

	public GOTItemTurban() {
		super(0);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		super.addInformation(itemstack, entityplayer, list, flag);
		if (GOTItemTurban.hasOrnament(itemstack)) {
			list.add(StatCollector.translateToLocal("item.got.robes.ornament"));
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "got:textures/armor/robes_turban.png";
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass == 1 && GOTItemTurban.hasOrnament(itemstack)) {
			return 16777215;
		}
		return super.getColorFromItemStack(itemstack, pass);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		if (pass == 1 && GOTItemTurban.hasOrnament(itemstack)) {
			return ornamentIcon;
		}
		return itemIcon;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		ornamentIcon = iconregister.registerIcon(getIconString() + "_ornament");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public static boolean hasOrnament(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("TurbanOrnament")) {
			return itemstack.getTagCompound().getBoolean("TurbanOrnament");
		}
		return false;
	}

	public static void setHasOrnament(ItemStack itemstack, boolean flag) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setBoolean("TurbanOrnament", flag);
	}
}
