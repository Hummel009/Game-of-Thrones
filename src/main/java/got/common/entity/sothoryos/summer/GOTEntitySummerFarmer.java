package got.common.entity.sothoryos.summer;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.*;
import got.common.item.other.GOTItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySummerFarmer extends GOTEntitySummerMan implements GOTTradeable, GOTUnitTradeable {
	public GOTEntitySummerFarmer(World world) {
		super(world);
		canBeMarried = false;
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
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.COMMON_FARMER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.COMMON_FARMER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "standart/civilized/usual_friendly";
			}
			return "standart/civilized/usual_neutral";
		}
		return "standart/civilized/usual_hostile";
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.SUMMER_FARMER;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return null;
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.bronzeHoe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		ItemStack turban = new ItemStack(GOTRegistry.robesHelmet);
		GOTItemLeatherHat.setHatColor(turban, 10390131);
		setCurrentItemOrArmor(4, turban);
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}
}
