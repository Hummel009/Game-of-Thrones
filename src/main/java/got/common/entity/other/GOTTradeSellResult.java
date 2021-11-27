package got.common.entity.other;

import net.minecraft.item.ItemStack;

public class GOTTradeSellResult {
	public int tradeIndex;
	public int tradeValue;
	public int tradeStackSize;
	public int tradesMade;
	public int itemsSold;
	public int totalSellValue;

	public GOTTradeSellResult(int index, GOTTradeEntry trade, ItemStack sellItem) {
		ItemStack tradeItem = trade.createTradeItem();
		tradeIndex = index;
		tradeValue = trade.getCost();
		tradeStackSize = tradeItem.stackSize;
		tradesMade = !trade.isAvailable() ? 0 : sellItem.stackSize / tradeStackSize;
		itemsSold = tradesMade * tradeItem.stackSize;
		totalSellValue = tradesMade * tradeValue;
	}
}
