package got.common.entity.other;

import got.common.inventory.GOTInventoryNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;

public interface IBandit {
	GOTEntityNPC getBanditAsNPC();

	GOTInventoryNPC getBanditInventory();

	IChatComponent getTheftChatMsg();

	String getTheftSpeechBank(EntityPlayer var1);

	class Helper {
		private Helper() {
		}

		public static boolean canStealFromPlayerInv(EntityPlayer entityplayer) {
			for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
				if (slot == entityplayer.inventory.currentItem || entityplayer.inventory.getStackInSlot(slot) == null) {
					continue;
				}
				return true;
			}
			return false;
		}
	}
}