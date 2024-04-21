package got.common.entity.essos.ibben;

import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.item.other.GOTItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityIbbenLumberman extends GOTEntityIbbenMarketTrader {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityIbbenLumberman(World world) {
		super(world);
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
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_axe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		ItemStack hat = new ItemStack(GOTItems.leatherHat);
		GOTItemLeatherHat.setHatColor(hat, 6834742);
		GOTItemLeatherHat.setFeatherColor(hat, 3916082);
		setCurrentItemOrArmor(4, hat);
		return data1;
	}
}