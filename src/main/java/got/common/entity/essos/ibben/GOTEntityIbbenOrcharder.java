package got.common.entity.essos.ibben;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityIbbenOrcharder extends GOTEntityIbbenMarketTrader {
	public GOTEntityIbbenOrcharder(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.ESSOS_FLORIST_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.COMMON_FLORIST_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextBoolean()) {
			npcItemsInv.setIdleItem(new ItemStack(Items.apple));
		} else {
			npcItemsInv.setIdleItem(new ItemStack(GOTRegistry.appleGreen));
		}
		return data;
	}
}
