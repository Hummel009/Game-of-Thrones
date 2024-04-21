package got.common.entity.essos.jogos;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.GOTTradeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityJogosShaman extends GOTEntityJogos implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityJogosShaman(World world) {
		super(world);
		addTargetTasks(false);
		spawnRidingHorse = false;
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
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_FARMER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_FARMER_SELL;
	}

	@Override
	public void onAttackModeChange(AttackMode mode, boolean mounted) {
		if (mode == AttackMode.IDLE) {
			setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(1, null);
		setCurrentItemOrArmor(2, null);
		setCurrentItemOrArmor(3, null);
		setCurrentItemOrArmor(4, null);
		npcItemsInv.setIdleItem(new ItemStack(GOTItems.mugMead));
		return data1;
	}
}