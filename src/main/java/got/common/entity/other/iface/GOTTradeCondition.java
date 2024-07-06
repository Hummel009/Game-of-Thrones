package got.common.entity.other.iface;

import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;

public interface GOTTradeCondition {
	default boolean getTradeCondition(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.getFaction() == GOTFaction.UNALIGNED) {
			return npc.isFriendly(entityPlayer);
		}
		if (npc instanceof GOTHireableBase && !(npc instanceof GOTFarmer)) {
			return npc.isFriendlyAndStronglyAligned(entityPlayer);
		}
		return npc.isFriendlyAndAligned(entityPlayer);
	}
}