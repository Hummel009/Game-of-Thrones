package got.common.item;

import java.util.UUID;

import got.common.item.other.GOTItemMug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;

public class GOTPoisonedDrinks {
	public static int POISON_DURATION = 3000;
	public static Potion killingPoison;

	public static void addPoisonEffect(EntityPlayer entityplayer, ItemStack itemstack) {
		int duration = 300;
		entityplayer.addPotionEffect(new PotionEffect(GOTPoisonedDrinks.killingPoison.id, duration));
	}

	public static boolean canPlayerSeePoisoned(ItemStack itemstack, EntityPlayer entityplayer) {
		UUID uuid = GOTPoisonedDrinks.getPoisonerUUID(itemstack);
		if (uuid == null) {
			return true;
		}
		return entityplayer.getUniqueID().equals(uuid) || entityplayer.capabilities.isCreativeMode;
	}

	public static boolean canPoison(ItemStack itemstack) {
		if (itemstack == null) {
			return false;
		}
		return GOTItemMug.isItemFullDrink(itemstack);
	}

	public static UUID getPoisonerUUID(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("PoisonerUUID")) {
			String s = itemstack.getTagCompound().getString("PoisonerUUID");
			return UUID.fromString(s);
		}
		return null;
	}

	public static boolean isDrinkPoisoned(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("PoisonDrink")) {
			return itemstack.getTagCompound().getBoolean("PoisonDrink");
		}
		return false;
	}

	public static void preInit() {
		killingPoison = new GOTPotionPoisonKilling();
	}

	public static void setDrinkPoisoned(ItemStack itemstack, boolean flag) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setBoolean("PoisonDrink", flag);
	}

	public static void setPoisonerPlayer(ItemStack itemstack, EntityPlayer entityplayer) {
		GOTPoisonedDrinks.setPoisonerUUID(itemstack, entityplayer.getUniqueID());
	}

	public static void setPoisonerUUID(ItemStack itemstack, UUID uuid) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setString("PoisonerUUID", uuid.toString());
	}
}
