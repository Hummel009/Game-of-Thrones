package got.common.entity.other;

import got.common.inventory.GOTInventoryNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;

public interface IBandit {
	boolean canTargetPlayerForTheft(EntityPlayer var1);

	GOTEntityNPC getBanditAsNPC();

	GOTInventoryNPC getBanditInventory();

	int getMaxThefts();

	IChatComponent getTheftChatMsg(EntityPlayer var1);

	String getTheftSpeechBank(EntityPlayer var1);

	class Helper {
		public static boolean canStealFromPlayerInv(IBandit bandit, EntityPlayer entityplayer) {
			for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
				if (slot == entityplayer.inventory.currentItem || entityplayer.inventory.getStackInSlot(slot) == null) {
					continue;
				}
				return true;
			}
			return false;
		}

		public static GOTInventoryNPC createInv(IBandit bandit) {
			return new GOTInventoryNPC("BanditInventory", bandit.getBanditAsNPC(), bandit.getMaxThefts());
		}
	}
}