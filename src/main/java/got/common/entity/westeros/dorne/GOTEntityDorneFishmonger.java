package got.common.entity.westeros.dorne;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.item.other.GOTItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityDorneFishmonger extends GOTEntityDorneMarketTrader {
	public GOTEntityDorneFishmonger(World world) {
		super(world);
		canBeMarried = false;
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
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(Items.fishing_rod));
		ItemStack hat = new ItemStack(GOTItems.leatherHat);
		GOTItemLeatherHat.setHatColor(hat, 9013900);
		setCurrentItemOrArmor(4, hat);
		return data1;
	}
}
