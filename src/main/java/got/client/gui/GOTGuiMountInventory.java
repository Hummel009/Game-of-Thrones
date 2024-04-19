package got.client.gui;

import got.common.entity.animal.GOTEntityHorse;
import got.common.inventory.GOTContainerMountInventory;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.inventory.IInventory;

public class GOTGuiMountInventory extends GuiScreenHorseInventory {
	public GOTGuiMountInventory(IInventory playerInv, IInventory horseInv, GOTEntityHorse horse) {
		super(playerInv, horseInv, horse);
		inventorySlots = new GOTContainerMountInventory(playerInv, horseInv, horse);
	}
}