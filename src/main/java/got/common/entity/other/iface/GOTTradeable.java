package got.common.entity.other.iface;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTTradeEntries;
import net.minecraft.entity.player.EntityPlayer;

public interface GOTTradeable extends GOTTradeCondition {
	GOTTradeEntries getSellsPool();

	GOTTradeEntries getBuysPool();

	default void onPlayerTrade(EntityPlayer entityPlayer) {
		GOTLevelData.getData(entityPlayer).addAchievement(GOTAchievement.trade);
	}
}