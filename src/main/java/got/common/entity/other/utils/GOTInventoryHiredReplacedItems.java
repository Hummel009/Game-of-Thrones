package got.common.entity.other.utils;

import got.common.entity.other.GOTEntityNPC;
import got.common.inventory.GOTInventoryNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GOTInventoryHiredReplacedItems extends GOTInventoryNPC {
	private final boolean[] hasReplacedEquipment = new boolean[7];

	private boolean replacedMeleeWeapons;

	public GOTInventoryHiredReplacedItems(GOTEntityNPC npc) {
		super("HiredReplacedItems", npc, 7);
	}

	private static int getNPCArmorSlot(int i) {
		return 4 - i;
	}

	public void dropAllReplacedItems() {
		for (int i = 0; i < 7; ++i) {
			ItemStack itemstack;
			if (!hasReplacedEquipment(i) || (itemstack = getEquippedReplacement(i)) == null) {
				continue;
			}
			theNPC.npcDropItem(itemstack, 0.0f, false, true);
			equipReplacement(i, getReplacedEquipment(i));
			setReplacedEquipment(i, null, false);
		}
	}

	private void equipReplacement(int i, ItemStack itemstack) {
		switch (i) {
			case 4:
				boolean idleMelee = ItemStack.areItemStacksEqual(theNPC.getNpcItemsInv().getMeleeWeapon(), theNPC.getNpcItemsInv().getIdleItem());
				theNPC.getNpcItemsInv().setMeleeWeapon(itemstack);
				if (!replacedMeleeWeapons) {
					theNPC.getNpcItemsInv().setReplacedIdleItem(theNPC.getNpcItemsInv().getIdleItem());
					theNPC.getNpcItemsInv().setReplacedMeleeWeaponMounted(theNPC.getNpcItemsInv().getMeleeWeaponMounted());
					theNPC.getNpcItemsInv().setReplacedIdleItemMounted(theNPC.getNpcItemsInv().getIdleItemMounted());
					replacedMeleeWeapons = true;
				}
				theNPC.getNpcItemsInv().setMeleeWeaponMounted(itemstack);
				if (idleMelee) {
					theNPC.getNpcItemsInv().setIdleItem(itemstack);
					theNPC.getNpcItemsInv().setIdleItemMounted(itemstack);
				}
				updateHeldItem();
				break;
			case 5:
				theNPC.getNpcItemsInv().setBomb(itemstack);
				updateHeldItem();
				break;
			case 6:
				theNPC.getNpcItemsInv().setRangedWeapon(itemstack);
				updateHeldItem();
				break;
			default:
				theNPC.setCurrentItemOrArmor(getNPCArmorSlot(i), itemstack);
				break;
		}
	}

	public ItemStack getEquippedReplacement(int i) {
		switch (i) {
			case 4:
				return theNPC.getNpcItemsInv().getMeleeWeapon();
			case 5:
				return theNPC.getNpcItemsInv().getBomb();
			case 6:
				return theNPC.getNpcItemsInv().getRangedWeapon();
			default:
				return theNPC.getEquipmentInSlot(getNPCArmorSlot(i));
		}
	}

	private ItemStack getReplacedEquipment(int i) {
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

	private void setReplacedEquipment(int i, ItemStack item, boolean flag) {
		setInventorySlotContents(i, item);
		hasReplacedEquipment[i] = flag;
		if (!flag && i == 4) {
			if (replacedMeleeWeapons) {
				theNPC.getNpcItemsInv().setIdleItem(theNPC.getNpcItemsInv().getReplacedIdleItem());
				theNPC.getNpcItemsInv().setMeleeWeaponMounted(theNPC.getNpcItemsInv().getReplacedMeleeWeaponMounted());
				theNPC.getNpcItemsInv().setIdleItemMounted(theNPC.getNpcItemsInv().getReplacedIdleItemMounted());
				theNPC.getNpcItemsInv().setReplacedMeleeWeaponMounted(null);
				theNPC.getNpcItemsInv().setReplacedIdleItem(null);
				theNPC.getNpcItemsInv().setReplacedIdleItemMounted(null);
				replacedMeleeWeapons = false;
			}
			updateHeldItem();
		}
	}

	private void updateHeldItem() {
		if (!theNPC.getNpcItemsInv().getIsEating()) {
			theNPC.refreshCurrentAttackMode();
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