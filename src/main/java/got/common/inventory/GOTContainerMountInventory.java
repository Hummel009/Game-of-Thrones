package got.common.inventory;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.entity.animal.GOTEntityHorse;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GOTContainerMountInventory extends ContainerHorseInventory {
	public GOTContainerMountInventory(IInventory playerInv, IInventory horseInv, GOTEntityHorse horse) {
		super(playerInv, horseInv, horse);
		ArrayList<Slot> slots = new ArrayList<Slot>(inventorySlots);
		inventorySlots.clear();
		inventoryItemStacks.clear();
		addSlotToContainer(slots.get(0));
		Slot armorSlot = slots.get(1);
		addSlotToContainer(new Slot(armorSlot.inventory, armorSlot.slotNumber, armorSlot.xDisplayPosition, armorSlot.yDisplayPosition) {

			@SideOnly(Side.CLIENT)
			@Override
			public boolean func_111238_b() {
				return horse.func_110259_cr();
			}

			@Override
			public boolean isItemValid(ItemStack itemstack) {
				return super.isItemValid(itemstack) && horse.func_110259_cr() && horse.isMountArmorValid(itemstack);
			}
		});
		for (int i = 2; i < slots.size(); ++i) {
			addSlotToContainer(slots.get(i));
		}
	}

}
