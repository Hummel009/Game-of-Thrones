package got.common.entity.westeros.arryn;

import got.common.database.GOTRegistry;
import got.common.database.GOTTradeEntries;
import got.common.item.other.GOTItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityArrynLumberman extends GOTEntityArrynMarketTrader {
	public GOTEntityArrynLumberman(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_LUMBERMAN_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_LUMBERMAN_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_axe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		ItemStack hat = new ItemStack(GOTRegistry.leatherHat);
		GOTItemLeatherHat.setHatColor(hat, 6834742);
		GOTItemLeatherHat.setFeatherColor(hat, 3916082);
		setCurrentItemOrArmor(4, hat);
		return data;
	}
}
