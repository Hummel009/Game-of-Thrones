package got.common.entity.sothoryos.summer;

import got.common.database.GOTRegistry;
import got.common.database.GOTTradeEntries;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySummerMason extends GOTEntitySummerTrader {
	public GOTEntitySummerMason(World world) {
		super(world);
		canBeMarried = false;
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
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.bronzePickaxe));
		npcItemsInv.setIdleItem(new ItemStack(GOTRegistry.brick1, 1, 15));
		return data;
	}
}
