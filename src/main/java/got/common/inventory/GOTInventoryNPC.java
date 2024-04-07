package got.common.inventory;

import got.common.entity.other.GOTEntityNPC;
import net.minecraft.item.ItemStack;

public class GOTInventoryNPC extends GOTEntityInventory {
	private final GOTEntityNPC theNPC;

	public GOTInventoryNPC(String s, GOTEntityNPC npc, int i) {
		super(s, npc, i);
		theNPC = npc;
	}

	@Override
	public void dropItem(ItemStack itemstack) {
		theNPC.npcDropItem(itemstack, 0.0f, false, true);
	}

	public GOTEntityNPC getTheNPC() {
		return theNPC;
	}
}