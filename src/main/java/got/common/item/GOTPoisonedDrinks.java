package got.common.item;

import got.common.item.other.GOTItemMug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.UUID;

@SuppressWarnings({"WeakerAccess", "PublicField"})
public class GOTPoisonedDrinks {
	public static Potion killingPoison;

	private GOTPoisonedDrinks() {
	}

	public static void addPoisonEffect(EntityPlayer entityplayer) {
		int duration = 300;
		entityplayer.addPotionEffect(new PotionEffect(killingPoison.id, duration));
	}

	public static boolean canPlayerSeePoisoned(ItemStack itemstack, EntityPlayer entityplayer) {
		UUID uuid = getPoisonerUUID(itemstack);
		return uuid == null || entityplayer.getUniqueID().equals(uuid) || entityplayer.capabilities.isCreativeMode;
	}

	public static boolean canPoison(ItemStack itemstack) {
		return GOTItemMug.isItemFullDrink(itemstack);
	}

	private static UUID getPoisonerUUID(ItemStack itemstack) {
		if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("PoisonerUUID")) {
			String s = itemstack.getTagCompound().getString("PoisonerUUID");
			return UUID.fromString(s);
		}
		return null;
	}

	public static boolean isDrinkPoisoned(ItemStack itemstack) {
		return itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("PoisonDrink") && itemstack.getTagCompound().getBoolean("PoisonDrink");
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
		setPoisonerUUID(itemstack, entityplayer.getUniqueID());
	}

	private static void setPoisonerUUID(ItemStack itemstack, UUID uuid) {
		if (itemstack.getTagCompound() == null) {
			itemstack.setTagCompound(new NBTTagCompound());
		}
		itemstack.getTagCompound().setString("PoisonerUUID", uuid.toString());
	}
}