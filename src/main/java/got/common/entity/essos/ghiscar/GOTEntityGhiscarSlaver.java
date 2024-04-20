package got.common.entity.essos.ghiscar;

import got.common.GOTLevelData;
import got.common.database.*;
import got.common.entity.other.GOTTradeable;
import got.common.entity.other.GOTUnitTradeable;
import got.common.item.other.GOTItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGhiscarSlaver extends GOTEntityGhiscarMan implements GOTTradeable, GOTUnitTradeable {
	public GOTEntityGhiscarSlaver(World world) {
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
		return GOTTradeEntries.C_FARMER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_FARMER_SELL;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (canTradeWith(entityplayer)) {
				return "standard/civilized/usual_friendly";
			}
			return "standard/civilized/usual_neutral";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.GHISCAR_SLAVER;
	}

	@Override
	public GOTInvasions getWarhorn() {
		return null;
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		if (type == GOTTradeEntries.TradeType.WE_CAN_BUY && itemstack.getItem() == Item.getItemFromBlock(GOTBlocks.pipeweedPlant)) {
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		ItemStack hat = new ItemStack(GOTItems.leatherHat);
		GOTItemLeatherHat.setHatColor(hat, 10390131);
		setCurrentItemOrArmor(4, hat);
		return data;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityplayer) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}
}