package got.common.entity.essos.asshai;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityAsshaiAlchemist extends GOTEntityAsshaiMan implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAsshaiAlchemist(World world) {
		super(world);
		cape = GOTCapes.ASSHAI;
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_ALCHEMIST_BUY;
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
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.skullStaff));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data1;
	}
}