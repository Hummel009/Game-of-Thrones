package got.common.entity.other.iface;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;

public interface GOTHireableBase extends GOTTradeCondition {
	static void onUnitTrade(EntityPlayer entityPlayer) {
		GOTLevelData.getData(entityPlayer).addAchievement(GOTAchievement.trade);
	}

	GOTFaction getFaction();

	String getNPCName();
}