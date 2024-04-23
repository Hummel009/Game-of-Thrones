package got.common.entity.westeros.north;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.iface.GOTTradeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class GOTEntityNorthMarketTrader extends GOTEntityNorthMan implements GOTTradeable {
	protected GOTEntityNorthMarketTrader(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityplayer) {
		return GOTLevelData.getData(entityplayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityplayer);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}
}