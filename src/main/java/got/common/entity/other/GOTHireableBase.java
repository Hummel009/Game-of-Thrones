package got.common.entity.other;

import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;

public interface GOTHireableBase {
	boolean canTradeWith(EntityPlayer var1);

	GOTFaction getFaction();

	String getNPCName();

	void onUnitTrade(EntityPlayer var1);
}
