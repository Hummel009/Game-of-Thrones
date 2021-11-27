package got.common.entity.westeros.dorne;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityDorneGreengrocer extends GOTEntityDorneMarketTrader {
	public GOTEntityDorneGreengrocer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.WESTEROS_GREENGROCER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.COMMON_FLORIST_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(3);
		switch (i) {
		case 0:
			npcItemsInv.setIdleItem(new ItemStack(Items.apple));
			break;
		case 1:
			npcItemsInv.setIdleItem(new ItemStack(GOTRegistry.appleGreen));
			break;
		case 2:
			npcItemsInv.setIdleItem(new ItemStack(GOTRegistry.pear));
			break;
		default:
			break;
		}
		return data;
	}
}
