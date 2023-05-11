package got.common.entity.essos.ibben;

import got.common.database.GOTRegistry;
import got.common.database.GOTTradeEntries;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityIbbenFlorist extends GOTEntityIbbenMarketTrader {
	public GOTEntityIbbenFlorist(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_FLORIST_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_FARMER_SELL;
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
