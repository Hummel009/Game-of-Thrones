package got.common.entity.essos.pentos;

import got.common.database.GOTTradeEntries;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityPentosFishmonger extends GOTEntityPentosTrader {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityPentosFishmonger(World world) {
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
		npcItemsInv.setIdleItem(new ItemStack(Items.fishing_rod));
		return entityData;
	}
}