package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.tileentity.GOTTileEntityKebabStand;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

public class GOTItemKebabStand extends ItemBlock {
	public GOTItemKebabStand(Block block) {
		super(block);
	}

	public static NBTTagCompound getKebabData(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("GOTKebabData")) {
			return itemstack.getTagCompound().getCompoundTag("GOTKebabData");
		}
		return null;
	}

	public static void loadKebabData(ItemStack itemstack, GOTTileEntityKebabStand kebabStand) {
		NBTTagCompound kebabData = GOTItemKebabStand.getKebabData(itemstack);
		if (kebabData != null) {
			kebabStand.readKebabStandFromNBT(kebabData);
		}
	}

	public static void setKebabData(ItemStack itemstack, GOTTileEntityKebabStand kebabStand) {
		if (kebabStand.shouldSaveBlockData()) {
			NBTTagCompound kebabData = new NBTTagCompound();
			kebabStand.writeKebabStandToNBT(kebabData);
			GOTItemKebabStand.setKebabData(itemstack, kebabData);
		}
	}

	public static void setKebabData(ItemStack itemstack, NBTTagCompound kebabData) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setTag("GOTKebabData", kebabData);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
		NBTTagCompound kebabData = GOTItemKebabStand.getKebabData(itemstack);
		if (kebabData != null) {
			GOTTileEntityKebabStand kebabStand = new GOTTileEntityKebabStand();
			kebabStand.readKebabStandFromNBT(kebabData);
			int meats = kebabStand.getMeatAmount();
			list.add(StatCollector.translateToLocalFormatted("tile.got.kebabStand.meats", meats));
			if (kebabStand.isCooked()) {
				list.add(StatCollector.translateToLocal("tile.got.kebabStand.cooked"));
			}
		}
	}
}
