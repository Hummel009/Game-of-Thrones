package got.common.entity.sothoryos.summer;

import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySummerMason extends GOTEntitySummerTrader {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySummerMason(World world) {
		super(world);
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.SUD_MASON_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_MINER_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bronzePickaxe));
		npcItemsInv.setIdleItem(new ItemStack(GOTBlocks.brick1, 1, 15));
		return entityData;
	}
}