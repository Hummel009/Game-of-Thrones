package got.common.entity.other;

import got.common.inventory.GOTInventoryNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GOTInventoryHiredReplacedItems extends GOTInventoryNPC {
	public static int HELMET;
	public static int BODY = 1;
	public static int LEGS = 2;
	public static int BOOTS = 3;
	public static int MELEE = 4;
	public static int BOMB = 5;
	public static int RANGED = 6;
	public boolean[] hasReplacedEquipment = new boolean[7];
	public boolean replacedMeleeWeapons;

	public GOTInventoryHiredReplacedItems(GOTEntityNPC npc) {
		super("HiredReplacedItems", npc, 7);
	}

	public void dropAllReplacedItems() {
		for (int i = 0; i < 7; ++i) {
			ItemStack itemstack;
			if (!hasReplacedEquipment(i) || (itemstack = getEquippedReplacement(i)) == null) {
				continue;
			}
			getTheNPC().npcDropItem(itemstack, 0.0f, false, true);
			equipReplacement(i, getReplacedEquipment(i));
			setReplacedEquipment(i, null, false);
		}
	}

	public void equipReplacement(int i, ItemStack itemstack) {
		switch (i) {
			case 4:
				boolean idleMelee = ItemStack.areItemStacksEqual(getTheNPC().npcItemsInv.getMeleeWeapon(), getTheNPC().npcItemsInv.getIdleItem());
				getTheNPC().npcItemsInv.setMeleeWeapon(itemstack);
				if (!replacedMeleeWeapons) {
					getTheNPC().npcItemsInv.setReplacedIdleItem(getTheNPC().npcItemsInv.getIdleItem());
					getTheNPC().npcItemsInv.setReplacedMeleeWeaponMounted(getTheNPC().npcItemsInv.getMeleeWeaponMounted());
					getTheNPC().npcItemsInv.setReplacedIdleItemMounted(getTheNPC().npcItemsInv.getIdleItemMounted());
					replacedMeleeWeapons = true;
				}
				getTheNPC().npcItemsInv.setMeleeWeaponMounted(itemstack);
				if (idleMelee) {
					getTheNPC().npcItemsInv.setIdleItem(itemstack);
					getTheNPC().npcItemsInv.setIdleItemMounted(itemstack);
				}
				updateHeldItem();
				break;
			case 6:
				getTheNPC().npcItemsInv.setRangedWeapon(itemstack);
				updateHeldItem();
				break;
			case 5:
				getTheNPC().npcItemsInv.setBomb(itemstack);
				updateHeldItem();
				break;
			default:
				getTheNPC().setCurrentItemOrArmor(getNPCArmorSlot(i), itemstack);
				break;
		}
	}

	public ItemStack getEquippedReplacement(int i) {
		switch (i) {
			case 4:
				return getTheNPC().npcItemsInv.getMeleeWeapon();
			case 6:
				return getTheNPC().npcItemsInv.getRangedWeapon();
			case 5:
				return getTheNPC().npcItemsInv.getBomb();
			default:
				break;
		}
		return getTheNPC().getEquipmentInSlot(getNPCArmorSlot(i));
	}

	public int getNPCArmorSlot(int i) {
		return 4 - i;
	}

	public ItemStack getReplacedEquipment(int i) {
		ItemStack item = getStackInSlot(i);
		return item == null ? null : item.copy();
	}

	public boolean hasReplacedEquipment(int i) {
		return hasReplacedEquipment[i];
	}

	public void onEquipmentChanged(int i, ItemStack newItem) {
		if (newItem == null) {
			if (hasReplacedEquipment(i)) {
				ItemStack itemstack = getReplacedEquipment(i);
				equipReplacement(i, itemstack);
				setReplacedEquipment(i, null, false);
			}
		} else {
			if (!hasReplacedEquipment(i)) {
				ItemStack itemstack = getEquippedReplacement(i);
				setReplacedEquipment(i, itemstack, true);
			}
			equipReplacement(i, newItem.copy());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		for (int i = 0; i < hasReplacedEquipment.length; ++i) {
			hasReplacedEquipment[i] = nbt.getBoolean("ReplacedFlag_" + i);
		}
		replacedMeleeWeapons = nbt.getBoolean("ReplacedMelee");
	}

	public void setReplacedEquipment(int i, ItemStack item, boolean flag) {
		setInventorySlotContents(i, item);
		hasReplacedEquipment[i] = flag;
		if (!flag && i == 4) {
			if (replacedMeleeWeapons) {
				getTheNPC().npcItemsInv.setIdleItem(getTheNPC().npcItemsInv.getReplacedIdleItem());
				getTheNPC().npcItemsInv.setMeleeWeaponMounted(getTheNPC().npcItemsInv.getReplacedMeleeWeaponMounted());
				getTheNPC().npcItemsInv.setIdleItemMounted(getTheNPC().npcItemsInv.getReplacedIdleItemMounted());
				getTheNPC().npcItemsInv.setReplacedMeleeWeaponMounted(null);
				getTheNPC().npcItemsInv.setReplacedIdleItem(null);
				getTheNPC().npcItemsInv.setReplacedIdleItemMounted(null);
				replacedMeleeWeapons = false;
			}
			updateHeldItem();
		}
	}

	public void updateHeldItem() {
		if (!getTheNPC().npcItemsInv.getIsEating()) {
			getTheNPC().refreshCurrentAttackMode();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		for (int i = 0; i < hasReplacedEquipment.length; ++i) {
			boolean flag = hasReplacedEquipment[i];
			nbt.setBoolean("ReplacedFlag_" + i, flag);
		}
		nbt.setBoolean("ReplacedMelee", replacedMeleeWeapons);
	}
}
