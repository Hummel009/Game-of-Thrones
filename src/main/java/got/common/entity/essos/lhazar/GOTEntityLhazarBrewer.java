package got.common.entity.essos.lhazar;

import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.item.other.GOTItemMug;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLhazarBrewer extends GOTEntityLhazarTrader {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLhazarBrewer(World world) {
		super(world);
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_BREWER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_BREWER_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		ItemStack drink = new ItemStack(GOTItems.mugAraq);
		GOTItemMug.setVessel(drink, GOTFoods.NOMAD_DRINK.getRandomVessel(rand), true);
		npcItemsInv.setIdleItem(drink);
		return data1;
	}
}