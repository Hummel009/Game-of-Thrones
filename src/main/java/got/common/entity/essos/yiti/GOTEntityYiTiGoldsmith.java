package got.common.entity.essos.yiti;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.item.other.GOTItemRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiGoldsmith extends GOTEntityYiTiMarketTrader {
	public GOTEntityYiTiGoldsmith(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_GOLDSMITH_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_GOLDSMITH_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(GOTItems.silverRing));
		int robeColor = 16237060;
		ItemStack body = new ItemStack(GOTItems.kaftanChestplate);
		ItemStack legs = new ItemStack(GOTItems.kaftanLeggings);
		GOTItemRobes.setRobesColor(body, robeColor);
		GOTItemRobes.setRobesColor(legs, robeColor);
		setCurrentItemOrArmor(3, body);
		setCurrentItemOrArmor(2, legs);
		return data;
	}
}