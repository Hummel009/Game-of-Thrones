package got.common.entity.other;

import got.common.database.GOTTradeEntries;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface GOTBartender extends GOTUnitTradeable {
	GOTTradeEntries getBuyPool();

	GOTTradeEntries getSellPool();

	void onPlayerTrade(EntityPlayer var1, GOTTradeEntries.TradeType var2, ItemStack var3);
}