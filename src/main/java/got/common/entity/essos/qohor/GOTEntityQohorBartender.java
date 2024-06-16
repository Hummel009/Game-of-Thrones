package got.common.entity.essos.qohor;

import got.common.GOTLevelData;
import got.common.database.GOTFoods;
import got.common.database.GOTItems;
import got.common.database.GOTTradeEntries;
import got.common.database.GOTUnitTradeEntries;
import got.common.entity.other.iface.GOTBartender;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityQohorBartender extends GOTEntityQohorMan implements GOTBartender {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityQohorBartender(World world) {
		super(world);
		addTargetTasks(false);
	}

	@Override
	public boolean canTradeWith(EntityPlayer entityPlayer) {
		return GOTLevelData.getData(entityPlayer).getAlignment(getFaction()) >= 0.0f && isFriendly(entityPlayer);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		super.dropFewItems(flag, i);
		int drinks = 1 + rand.nextInt(4) + i;
		for (int l = 0; l < drinks; ++l) {
			ItemStack drink = GOTFoods.DEFAULT_DRINK.getRandomFood(rand);
			entityDropItem(drink, 0.0f);
		}
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_BARTENDER_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_BARTENDER_SELL;
	}

	@Override
	public GOTUnitTradeEntries getUnits() {
		return GOTUnitTradeEntries.PROSTITUTE_KEEPER;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(GOTItems.gobletCopper));
		return entityData;
	}

	@Override
	public void onUnitTrade(EntityPlayer entityPlayer) {
	}
}