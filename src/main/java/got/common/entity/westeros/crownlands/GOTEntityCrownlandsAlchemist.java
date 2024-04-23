package got.common.entity.westeros.crownlands;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.iface.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityCrownlandsAlchemist extends GOTEntityCrownlandsMan implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityCrownlandsAlchemist(World world) {
		super(world);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.CROWNLANDS_ALCHEMIST_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_GOLDSMITH_SELL;
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.skullStaff));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return entityData;
	}
}