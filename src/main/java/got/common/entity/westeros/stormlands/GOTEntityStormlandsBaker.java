package got.common.entity.westeros.stormlands;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityStormlandsBaker extends GOTEntityStormlandsMarketTrader {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityStormlandsBaker(World world) {
		super(world);
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_BAKER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_BAKER_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.rollingPin));
		npcItemsInv.setIdleItem(new ItemStack(Items.bread));
		return entityData;
	}
}