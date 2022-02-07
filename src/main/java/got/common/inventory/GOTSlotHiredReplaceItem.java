package got.common.inventory;

import cpw.mods.fml.relauncher.*;
import got.common.entity.other.*;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GOTSlotHiredReplaceItem extends Slot {
	private GOTEntityNPC theNPC;
	private GOTInventoryHiredReplacedItems npcInv;
	private Slot parentSlot;

	public GOTSlotHiredReplaceItem(Slot slot, GOTEntityNPC npc) {
		super(slot.inventory, slot.getSlotIndex(), slot.xDisplayPosition, slot.yDisplayPosition);
		int i;
		parentSlot = slot;
		theNPC = npc;
		npcInv = theNPC.hiredReplacedInv;
		if (!theNPC.worldObj.isRemote && npcInv.hasReplacedEquipment(i = getSlotIndex())) {
			inventory.setInventorySlotContents(i, npcInv.getEquippedReplacement(i));
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getBackgroundIconIndex() {
		return parentSlot.getBackgroundIconIndex();
	}

	@Override
	public int getSlotStackLimit() {
		return parentSlot.getSlotStackLimit();
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return parentSlot.isItemValid(itemstack) && theNPC.canReEquipHired(getSlotIndex(), itemstack);
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		if (!theNPC.worldObj.isRemote) {
			npcInv.onEquipmentChanged(getSlotIndex(), getStack());
		}
	}
}
