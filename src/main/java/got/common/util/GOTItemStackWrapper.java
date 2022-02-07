package got.common.util;

import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;

public class GOTItemStackWrapper {
	public boolean isNBTSensitive;
	public ItemStack stack;
	public Item item;
	public int damage;
	public NBTTagCompound compound;

	public GOTItemStackWrapper(ItemStack stack) {
		this(stack, false);
	}

	public GOTItemStackWrapper(ItemStack stack, boolean isNBTSensitive) {
		this.isNBTSensitive = isNBTSensitive;
		item = stack.getItem();
		damage = stack.getItemDamage();
		compound = stack.getTagCompound();
		this.stack = stack;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		GOTItemStackWrapper other = (GOTItemStackWrapper) obj;
		if ((item == null ? other.item != null : !item.equals(other.item)) || damage != other.damage || isNBTSensitive != other.isNBTSensitive) {
			return false;
		}
		return !isNBTSensitive || !(compound == null ? other.compound != null : !compound.equals(other.compound));
	}

	public int getDamage() {
		return damage;
	}

	public Item getItem() {
		return item;
	}

	public NBTTagCompound getTagCompound() {
		return compound;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (item == null ? 0 : item.hashCode());
		result = 31 * result + damage;
		result = 31 * result + (isNBTSensitive ? 1231 : 1237);
		if (isNBTSensitive) {
			result = 31 * result + (compound == null ? 0 : compound.hashCode());
		}
		return result;
	}

	public ItemStack toItemStack() {
		return stack;
	}
}
