package got.common.entity.essos.mossovy;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.entity.other.iface.GOTTradeable;
import got.common.item.other.GOTItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityMossovyFarmer extends GOTEntityMossovyMan implements GOTTradeable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMossovyFarmer(World world) {
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
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_FARMER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_FARMER_SELL;
	}

	@Override
	public void onPlayerTrade(EntityPlayer entityplayer, GOTTradeEntries.TradeType type, ItemStack itemstack) {
		GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.trade);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		ItemStack hat = new ItemStack(GOTItems.leatherHat);
		GOTItemLeatherHat.setHatColor(hat, 10390131);
		setCurrentItemOrArmor(4, hat);
		return entityData;
	}
}