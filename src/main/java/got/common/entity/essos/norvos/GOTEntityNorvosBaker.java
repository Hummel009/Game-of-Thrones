package got.common.entity.essos.norvos;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorvosBaker extends GOTEntityNorvosTrader {
	public GOTEntityNorvosBaker(World world) {
		super(world);
		canBeMarried = false;
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
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.rollingPin));
		npcItemsInv.setIdleItem(new ItemStack(GOTItems.oliveBread));
		return data;
	}
}