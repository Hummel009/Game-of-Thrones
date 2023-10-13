package got.common.util;

import java.util.Objects;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		GOTItemStackWrapper other = (GOTItemStackWrapper) obj;
		if (!Objects.equals(item, other.item) || damage != other.damage || isNBTSensitive != other.isNBTSensitive) {
			return false;
		}
		return !isNBTSensitive || Objects.equals(compound, other.compound);
	}

	@Override
	public int hashCode() {
		int result = 1;
		if (item == null) {
			result = 31 * result;
		} else {
			result = 31 * result + item.hashCode();
		}
		result = 31 * result + damage;
		if (isNBTSensitive) {
			result = 31 * result + 1231;
			if (compound == null) {
				result = 31 * result;
			} else {
				result = 31 * result + compound.hashCode();
			}
		} else {
			result = 31 * result + 1237;
		}
		return result;
	}

	public ItemStack toItemStack() {
		return stack;
	}
}
