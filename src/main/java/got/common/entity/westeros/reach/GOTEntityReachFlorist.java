package got.common.entity.westeros.reach;

import got.common.database.GOTTradeEntries;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityReachFlorist extends GOTEntityReachMarketTrader {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityReachFlorist(World world) {
		super(world);
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
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(Blocks.red_flower, 1, 0));
		return entityData;
	}
}