package got.common.entity.sothoryos.summer;

import got.common.database.GOTTradeEntries;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySummerFlorist extends GOTEntitySummerTrader {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySummerFlorist(World world) {
		super(world);
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_FISHMONGER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_FISHMONGER_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(Blocks.red_flower));
		return entityData;
	}
}