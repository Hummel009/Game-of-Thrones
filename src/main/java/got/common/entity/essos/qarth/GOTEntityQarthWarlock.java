package got.common.entity.essos.qarth;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityQarthWarlock extends GOTEntityQarthTrader implements GOTTradeable {
	public GOTEntityQarthWarlock(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return isFriendly(entityplayer);
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.ASSHAI_ALCHEMIST_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.COMMON_BOMZH_SELL;
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(4, null);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.skullStaff));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
